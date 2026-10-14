/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.schema.manifest.builder;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.tools.ToolsUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Version;

/**
 * @author Luis Ortiz
 */
public class SchemaManifestBuilder {

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String baseDirName = GetterUtil.getString(
			arguments.get("schema.manifest.base.dir"), ".");
		String manifestFileName = GetterUtil.getString(
			arguments.get("schema.manifest.file"),
			"src/main/resources/META-INF/schema/manifest.json");
		String module = arguments.get("schema.manifest.module");

		try {
			SchemaManifestBuilder schemaManifestBuilder =
				new SchemaManifestBuilder(
					baseDirName, manifestFileName, module);

			schemaManifestBuilder.build();
		}
		catch (Exception exception) {
			ArgumentsUtil.processMainException(arguments, exception);
		}
	}

	public SchemaManifestBuilder(
		String baseDirName, String manifestFileName, String module) {

		_module = module;

		_baseDir = new File(baseDirName);

		_manifestFile = new File(_baseDir, manifestFileName);
	}

	public void build() throws IOException {
		JSONArray historyJSONArray = getHistoryJSONArray(_baseDir);

		String module = _module;

		if (Validator.isNull(module)) {
			module = getBundleSymbolicName(new File(_baseDir, "bnd.bnd"));
		}

		write(_manifestFile, getManifestJSONObject(historyJSONArray, module));
	}

	protected String getBundleSymbolicName(File bndFile) throws IOException {
		if (!bndFile.exists()) {
			throw new IllegalArgumentException(
				"Unable to find " + bndFile.getAbsolutePath());
		}

		Properties properties = new Properties();

		try (InputStream inputStream = Files.newInputStream(bndFile.toPath())) {
			properties.load(inputStream);
		}

		String bundleSymbolicName = properties.getProperty(
			"Bundle-SymbolicName");

		if (Validator.isNull(bundleSymbolicName)) {
			throw new IllegalArgumentException(
				"Unable to find \"Bundle-SymbolicName\" in " +
					bndFile.getAbsolutePath());
		}

		return bundleSymbolicName;
	}

	protected JSONArray getHistoryJSONArray(File baseDir) throws IOException {
		JSONArray historyJSONArray = new JSONArrayImpl();

		for (File upgradeStepRegistratorFile :
				_getUpgradeStepRegistratorFiles(baseDir)) {

			_addHistory(historyJSONArray, upgradeStepRegistratorFile);
		}

		return historyJSONArray;
	}

	protected JSONObject getManifestJSONObject(
		JSONArray historyJSONArray, String module) {

		JSONObject manifestJSONObject = new JSONObjectImpl();

		manifestJSONObject.put(
			"description",
			StringBundler.concat(
				"Database changes manifest of one Liferay module, generated ",
				"by Liferay Schema Manifest Builder from its upgrade steps. ",
				"Do not edit it by hand. Top level keys: 'history' stores the ",
				"upgrade steps that move a database between schema versions; ",
				"'module' is the module's Bundle-SymbolicName, or 'portal' ",
				"for portal core; 'schemaVersion' is the current schema ",
				"version; 'timestamp' is when the content last changed. ",
				"Rules: 'history' is a SET of edges, not a sequence: array ",
				"order is not meaningful, several edges may share a ",
				"'toSchemaVersion', and only one applies to any given ",
				"database. Compute the upgrade path by following ",
				"'fromSchemaVersion'/'toSchemaVersion'. 'changes' inside an ",
				"edge IS ordered, in execution order. Schema versions are ",
				"opaque strings: never compare or sort them. A change's ",
				"'className' is where the step is implemented; the edge's ",
				"'registrator' plus the change's 'registratorLine' is where ",
				"it is declared. 'customChange' means the step's effect could ",
				"NOT be determined, not that it made no change. A database's ",
				"own schemaVersion for this module is in its Release_ table, ",
				"in the row where servletContextName equals 'module'.")
		).put(
			"history", historyJSONArray
		).put(
			"module", module
		);

		String schemaVersion = getSchemaVersion(historyJSONArray);

		if (schemaVersion != null) {
			manifestJSONObject.put("schemaVersion", schemaVersion);
		}

		return manifestJSONObject;
	}

	protected String getSchemaVersion(JSONArray historyJSONArray) {
		Version schemaVersion = null;

		for (int i = 0; i < historyJSONArray.length(); i++) {
			JSONObject edgeJSONObject = historyJSONArray.getJSONObject(i);

			Version toSchemaVersion = Version.parseVersion(
				edgeJSONObject.getString("toSchemaVersion"));

			if ((schemaVersion != null) &&
				(schemaVersion.compareTo(toSchemaVersion) >= 0)) {

				continue;
			}

			schemaVersion = toSchemaVersion;
		}

		if (schemaVersion == null) {
			return null;
		}

		return schemaVersion.toString();
	}

	protected String getTimestamp(String content) {
		Matcher matcher = _timestampPattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
	}

	protected void write(File manifestFile, JSONObject manifestJSONObject)
		throws IOException {

		Path manifestPath = manifestFile.toPath();

		if (manifestFile.exists()) {
			String content = StringUtil.replace(
				new String(
					Files.readAllBytes(manifestPath), StandardCharsets.UTF_8),
				StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);

			String timestamp = getTimestamp(content);

			if (timestamp != null) {
				manifestJSONObject.put("timestamp", timestamp);

				if (content.equals(JSONUtil.toString(manifestJSONObject))) {
					return;
				}
			}
		}

		Instant instant = Instant.now();

		manifestJSONObject.put(
			"timestamp",
			String.valueOf(instant.truncatedTo(ChronoUnit.SECONDS)));

		Files.createDirectories(manifestPath.getParent());

		String json = JSONUtil.toString(manifestJSONObject);

		Files.write(manifestPath, json.getBytes(StandardCharsets.UTF_8));

		System.out.println("Writing " + manifestFile);
	}

	private void _addHistory(
			JSONArray historyJSONArray, File upgradeStepRegistratorFile)
		throws IOException {

		String content = _read(upgradeStepRegistratorFile);

		Matcher matcher = _upgradeStepRegistratorPattern.matcher(content);

		if (!matcher.find()) {
			return;
		}

		String fileName = upgradeStepRegistratorFile.getName();

		String packageName = ToolsUtil.getPackagePath(
			upgradeStepRegistratorFile);

		String registrator =
			packageName + StringPool.PERIOD +
				StringUtil.removeLast(fileName, ".java");

		Map<String, String> classNames = _getClassNames(content);

		int registerPosition = -1;

		while (true) {
			registerPosition = content.indexOf(
				".register(", registerPosition + 1);

			if (registerPosition == -1) {
				break;
			}

			List<String> parameters = _getParameterList(
				content.substring(registerPosition));

			if (parameters.size() < 3) {
				continue;
			}

			JSONArray changesJSONArray = new JSONArrayImpl();

			int parameterPosition = content.indexOf(
				parameters.get(1), registerPosition);

			for (int i = 2; i < parameters.size(); i++) {
				String parameter = parameters.get(i);

				parameterPosition = content.indexOf(
					parameter, parameterPosition);

				int newLineCount = StringUtil.count(
					content, 0, parameterPosition, CharPool.NEW_LINE);

				changesJSONArray.put(
					new JSONObjectImpl(
					).put(
						"className",
						_getClassName(
							classNames, content, packageName, parameter,
							registrator)
					).put(
						"op", "customChange"
					).put(
						"registratorLine", newLineCount + 1
					));

				parameterPosition += parameter.length();
			}

			historyJSONArray.put(
				new JSONObjectImpl(
				).put(
					"changes", changesJSONArray
				).put(
					"fromSchemaVersion", StringUtil.unquote(parameters.get(0))
				).put(
					"registrator", registrator
				).put(
					"toSchemaVersion", StringUtil.unquote(parameters.get(1))
				));
		}
	}

	private String _getClassName(
		Map<String, String> classNames, String content, String packageName,
		String parameter, String registrator) {

		Matcher matcher = _lambdaPattern.matcher(parameter);

		if (matcher.find()) {
			return registrator;
		}

		matcher = _castPattern.matcher(parameter);

		if (matcher.find()) {
			parameter = parameter.substring(matcher.end());
		}

		int openParenthesisIndex = parameter.indexOf(CharPool.OPEN_PARENTHESIS);

		if (parameter.startsWith("new ")) {
			String parameters = _getParameters(parameter);

			String trailingContent = StringUtil.trim(
				parameter.substring(
					openParenthesisIndex + parameters.length() + 2));

			if (trailingContent.startsWith(StringPool.OPEN_CURLY_BRACE)) {
				return registrator;
			}

			String className = parameter.substring(4, openParenthesisIndex);

			int index = className.indexOf(CharPool.LESS_THAN);

			if (index != -1) {
				className = className.substring(0, index);
			}

			return _getClassName(className, classNames, packageName);
		}

		if (openParenthesisIndex != -1) {
			String methodName = parameter.substring(0, openParenthesisIndex);

			int index = methodName.lastIndexOf(CharPool.PERIOD);

			if (index == -1) {
				return registrator;
			}

			String receiver = StringUtil.trim(methodName.substring(0, index));

			if (_isVariableName(receiver)) {
				return _getClassName(
					_getVariableType(content, receiver), classNames,
					packageName);
			}

			return _getClassName(receiver, classNames, packageName);
		}

		String initializer = _getVariableInitializer(content, parameter);

		if (initializer == null) {
			return _getClassName(
				_getVariableType(content, parameter), classNames, packageName);
		}

		return _getClassName(
			classNames, content, packageName, initializer, registrator);
	}

	private String _getClassName(
		String className, Map<String, String> classNames, String packageName) {

		className = StringUtil.removeChars(
			className, CharPool.NEW_LINE, CharPool.SPACE, CharPool.TAB);

		if (className.contains(StringPool.PERIOD)) {
			return className;
		}

		String fullyQualifiedClassName = classNames.get(className);

		if (fullyQualifiedClassName != null) {
			return fullyQualifiedClassName;
		}

		return packageName + StringPool.PERIOD + className;
	}

	private Map<String, String> _getClassNames(String content) {
		Map<String, String> classNames = new HashMap<>();

		Matcher matcher = _importPattern.matcher(content);

		while (matcher.find()) {
			classNames.put(matcher.group(2), matcher.group(1));
		}

		return classNames;
	}

	private List<String> _getParameterList(String methodCall) {
		List<String> parameterList = new ArrayList<>();

		String parameters = StringUtil.trim(_getParameters(methodCall));

		if (parameters.equals(StringPool.BLANK)) {
			return parameterList;
		}

		int index = -1;

		while (true) {
			index = parameters.indexOf(StringPool.COMMA, index + 1);

			if (index == -1) {
				parameterList.add(StringUtil.trim(parameters));

				return parameterList;
			}

			if (ToolsUtil.isInsideQuotes(parameters, index)) {
				continue;
			}

			String linePart = StringUtil.replace(
				parameters.substring(0, index), "->",
				_LAMBDA_SYMBOL_PLACEHOLDER);

			if ((ToolsUtil.getLevel(linePart, "(", ")") != 0) ||
				(ToolsUtil.getLevel(linePart, "<", ">") != 0) ||
				(ToolsUtil.getLevel(linePart, "{", "}") != 0)) {

				continue;
			}

			linePart = StringUtil.replace(
				linePart, _LAMBDA_SYMBOL_PLACEHOLDER, "->");

			parameterList.add(StringUtil.trim(linePart));

			parameters = parameters.substring(index + 1);

			index = -1;
		}
	}

	private String _getParameters(String methodCall) {
		String parameters = null;

		int index = -1;

		while (true) {
			index = methodCall.indexOf(StringPool.CLOSE_PARENTHESIS, index + 1);

			parameters = methodCall.substring(0, index + 1);

			if ((ToolsUtil.getLevel(parameters, "(", ")") == 0) &&
				(ToolsUtil.getLevel(parameters, "{", "}") == 0)) {

				break;
			}
		}

		index = parameters.indexOf(StringPool.OPEN_PARENTHESIS);

		return parameters.substring(index + 1, parameters.length() - 1);
	}

	private List<File> _getUpgradeStepRegistratorFiles(File baseDir)
		throws IOException {

		List<File> files = new ArrayList<>();

		File javaDir = new File(baseDir, "src/main/java");

		if (!javaDir.isDirectory()) {
			return files;
		}

		FileSystem fileSystem = FileSystems.getDefault();

		PathMatcher pathMatcher = fileSystem.getPathMatcher(
			"glob:**/upgrade/registry/*UpgradeStepRegistrator.java");

		Files.walkFileTree(
			javaDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					if (pathMatcher.matches(filePath)) {
						files.add(filePath.toFile());
					}

					return FileVisitResult.CONTINUE;
				}

			});

		Collections.sort(files);

		return files;
	}

	private String _getVariableInitializer(String content, String name) {
		Pattern pattern = Pattern.compile(
			"\\b" + Pattern.quote(name) + "\\s*=(?!=)\\s*");

		Matcher matcher = pattern.matcher(content);

		if (!matcher.find()) {
			return null;
		}

		int position = matcher.end();

		int index = position;

		while (true) {
			index = content.indexOf(CharPool.SEMICOLON, index + 1);

			String initializer = content.substring(position, index);

			if ((ToolsUtil.getLevel(initializer, "(", ")") == 0) &&
				(ToolsUtil.getLevel(initializer, "{", "}") == 0)) {

				return StringUtil.trim(initializer);
			}
		}
	}

	private String _getVariableType(String content, String name) {
		Pattern pattern = Pattern.compile(
			"([\\w.]+)(?:<[^;=]*>)?\\s+" + Pattern.quote(name) + "\\s*[;=]");

		Matcher matcher = pattern.matcher(content);

		if (!matcher.find()) {
			throw new IllegalArgumentException(
				"Unable to find the type of " + name);
		}

		return matcher.group(1);
	}

	private boolean _isVariableName(String name) {
		char c = name.charAt(0);

		if (((c == CharPool.UNDERLINE) || Character.isLowerCase(c)) &&
			!name.contains(StringPool.PERIOD)) {

			return true;
		}

		return false;
	}

	private String _read(File file) throws IOException {
		String content = new String(
			Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

		return StringUtil.replace(
			content, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
	}

	private static final String _LAMBDA_SYMBOL_PLACEHOLDER =
		"_LAMBDA_SYMBOL_PLACEHOLDER_";

	private static final Pattern _castPattern = Pattern.compile(
		"^\\(\\s*[\\w.]+\\s*\\)\\s*");
	private static final Pattern _importPattern = Pattern.compile(
		"^import ([\\w.]+\\.(\\w+));", Pattern.MULTILINE);
	private static final Pattern _lambdaPattern = Pattern.compile(
		"^(\\([\\w\\s,]*\\)|\\w+)\\s*->");
	private static final Pattern _timestampPattern = Pattern.compile(
		"^\t\"timestamp\": \"([^\"]*)\"", Pattern.MULTILINE);
	private static final Pattern _upgradeStepRegistratorPattern =
		Pattern.compile("\\bimplements\\s[^{]*\\bUpgradeStepRegistrator\\b");

	private final File _baseDir;
	private final File _manifestFile;
	private final String _module;

}