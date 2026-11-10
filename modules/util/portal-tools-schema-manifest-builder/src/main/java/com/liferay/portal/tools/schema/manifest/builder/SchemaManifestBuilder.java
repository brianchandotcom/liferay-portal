/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.schema.manifest.builder;

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
import com.liferay.portal.tools.java.parser.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;

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

	public void build() throws CheckstyleException, IOException {
		String module = _module;

		if (Validator.isNull(module)) {
			module = getBundleSymbolicName(new File(_baseDir, "bnd.bnd"));
		}

		write(
			_manifestFile,
			getManifestJSONObject(
				getHistoryJSONArray(_baseDir),
				isInitializationRegistered(_baseDir), module));
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

	protected JSONArray getHistoryJSONArray(File baseDir)
		throws CheckstyleException, IOException {

		JSONArray historyJSONArray = new JSONArrayImpl();

		for (DetailAST upgradeStepRegistratorDetailAST :
				_getUpgradeStepRegistratorDetailASTs(baseDir)) {

			_addHistory(historyJSONArray, upgradeStepRegistratorDetailAST);
		}

		return historyJSONArray;
	}

	protected JSONObject getManifestJSONObject(
		JSONArray historyJSONArray, boolean initializationRegistered,
		String module) {

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
				"'fromSchemaVersion'/'toSchemaVersion'. 'history' only holds ",
				"the steps registered with 'register': a fresh install is ",
				"created directly at 'schemaVersion'. 'changes' inside an ",
				"edge IS ordered, in execution order. Schema versions are ",
				"opaque strings: never compare or sort them. A change's ",
				"'className' is the class that implements the step; for an ",
				"anonymous class it is its binary name, '<registrator>$<n>'; ",
				"for a step returned by a method call it is the class or type ",
				"the method is called on, such as UpgradeProcessFactory; for ",
				"a lambda, or a step whose class cannot be resolved, it is ",
				"the registrator. The edge's 'registratorClassName' plus the ",
				"change's 'registratorLine' is where the step is declared; ",
				"for a step created by UpgradeProcessFactory directly in the ",
				"'register' call, an upgrade log entry '<class>:<line> - ...' ",
				"matches the change whose edge 'registratorClassName' is ",
				"<class> and whose 'registratorLine' is <line>. ",
				"'customChange' means the step's effect could NOT be ",
				"determined, not that it made no change. A database's own ",
				"schemaVersion for this module is in its Release_ table, in ",
				"the row where servletContextName equals 'module'.")
		).put(
			"history", historyJSONArray
		).put(
			"module", module
		);

		String schemaVersion = getSchemaVersion(
			historyJSONArray, initializationRegistered);

		if (schemaVersion != null) {
			manifestJSONObject.put("schemaVersion", schemaVersion);
		}

		return manifestJSONObject;
	}

	protected String getSchemaVersion(
		JSONArray historyJSONArray, boolean initializationRegistered) {

		if (historyJSONArray.length() == 0) {
			if (initializationRegistered) {
				return "1.0.0";
			}

			return null;
		}

		Version schemaVersion = Version.emptyVersion;

		for (int i = 0; i < historyJSONArray.length(); i++) {
			JSONObject edgeJSONObject = historyJSONArray.getJSONObject(i);

			Version toSchemaVersion = Version.parseVersion(
				edgeJSONObject.getString("toSchemaVersion"));

			if (toSchemaVersion.compareTo(schemaVersion) <= 0) {
				continue;
			}

			schemaVersion = toSchemaVersion;
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

	protected boolean isInitializationRegistered(File baseDir)
		throws CheckstyleException, IOException {

		for (DetailAST upgradeStepRegistratorDetailAST :
				_getUpgradeStepRegistratorDetailASTs(baseDir)) {

			List<DetailAST> methodCallDetailASTs = _getMethodCallDetailASTs(
				"registerInitialization", upgradeStepRegistratorDetailAST);

			if (!methodCallDetailASTs.isEmpty()) {
				return true;
			}
		}

		return false;
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
		JSONArray historyJSONArray, DetailAST upgradeStepRegistratorDetailAST) {

		DetailAST classDefDetailAST =
			upgradeStepRegistratorDetailAST.findFirstToken(
				TokenTypes.CLASS_DEF);

		DetailAST identDetailAST = classDefDetailAST.findFirstToken(
			TokenTypes.IDENT);

		String upgradeStepRegistratorClassName =
			_getPackageName(upgradeStepRegistratorDetailAST) +
				StringPool.PERIOD + identDetailAST.getText();

		for (DetailAST methodCallDetailAST :
				_getMethodCallDetailASTs(
					"register", upgradeStepRegistratorDetailAST)) {

			List<DetailAST> parameterDetailASTs = _getParameterDetailASTs(
				methodCallDetailAST);

			if (parameterDetailASTs.size() < 3) {
				continue;
			}

			for (DetailAST schemaVersionDetailAST :
					parameterDetailASTs.subList(0, 2)) {

				if (schemaVersionDetailAST.getType() !=
						TokenTypes.STRING_LITERAL) {

					throw new IllegalArgumentException(
						StringBundler.concat(
							"Unable to parse schema version at ",
							upgradeStepRegistratorClassName, ":",
							methodCallDetailAST.getLineNo()));
				}
			}

			JSONArray changesJSONArray = new JSONArrayImpl();

			for (DetailAST upgradeStepDetailAST :
					parameterDetailASTs.subList(
						2, parameterDetailASTs.size())) {

				DetailAST firstDetailAST = CheckUtil.getFirstNode(
					upgradeStepDetailAST);

				changesJSONArray.put(
					new JSONObjectImpl(
					).put(
						"className",
						_getUpgradeStepClassName(
							upgradeStepDetailAST,
							upgradeStepRegistratorClassName,
							upgradeStepRegistratorDetailAST)
					).put(
						"op", "customChange"
					).put(
						"registratorLine", firstDetailAST.getLineNo()
					));
			}

			DetailAST fromSchemaVersionDetailAST = parameterDetailASTs.get(0);
			DetailAST toSchemaVersionDetailAST = parameterDetailASTs.get(1);

			historyJSONArray.put(
				new JSONObjectImpl(
				).put(
					"changes", changesJSONArray
				).put(
					"fromSchemaVersion",
					StringUtil.unquote(fromSchemaVersionDetailAST.getText())
				).put(
					"registratorClassName", upgradeStepRegistratorClassName
				).put(
					"toSchemaVersion",
					StringUtil.unquote(toSchemaVersionDetailAST.getText())
				));
		}
	}

	private List<DetailAST> _getAnonymousClassDetailASTs(
		DetailAST upgradeStepRegistratorDetailAST) {

		List<DetailAST> anonymousClassDetailASTs = new ArrayList<>();

		DetailAST classDefDetailAST =
			upgradeStepRegistratorDetailAST.findFirstToken(
				TokenTypes.CLASS_DEF);

		for (DetailAST literalNewDetailAST :
				DetailASTUtil.getAllChildTokens(
					upgradeStepRegistratorDetailAST, true,
					TokenTypes.LITERAL_NEW)) {

			if (literalNewDetailAST.findFirstToken(TokenTypes.OBJBLOCK) ==
					null) {

				continue;
			}

			DetailAST objBlockDetailAST = literalNewDetailAST.getParent();

			while (objBlockDetailAST.getType() != TokenTypes.OBJBLOCK) {
				objBlockDetailAST = objBlockDetailAST.getParent();
			}

			if (objBlockDetailAST.getParent() != classDefDetailAST) {
				continue;
			}

			anonymousClassDetailASTs.add(literalNewDetailAST);
		}

		return anonymousClassDetailASTs;
	}

	private DetailAST _getExpressionDetailAST(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.EXPR) {
			return detailAST.getFirstChild();
		}

		return detailAST;
	}

	private String _getFullyQualifiedClassName(
		String className, DetailAST upgradeStepRegistratorDetailAST) {

		if (className.contains(StringPool.PERIOD)) {
			return className;
		}

		for (DetailAST importDetailAST :
				DetailASTUtil.getAllChildTokens(
					upgradeStepRegistratorDetailAST, false,
					TokenTypes.IMPORT)) {

			FullIdent fullIdent = FullIdent.createFullIdentBelow(
				importDetailAST);

			String importedClassName = fullIdent.getText();

			if (importedClassName.endsWith(StringPool.PERIOD + className)) {
				return importedClassName;
			}
		}

		return _getPackageName(upgradeStepRegistratorDetailAST) +
			StringPool.PERIOD + className;
	}

	private List<DetailAST> _getMethodCallDetailASTs(
		String methodName, DetailAST upgradeStepRegistratorDetailAST) {

		List<DetailAST> methodCallDetailASTs = new ArrayList<>();

		for (DetailAST methodCallDetailAST :
				DetailASTUtil.getAllChildTokens(
					upgradeStepRegistratorDetailAST, true,
					TokenTypes.METHOD_CALL)) {

			FullIdent fullIdent = FullIdent.createFullIdentBelow(
				methodCallDetailAST);

			String methodCallName = fullIdent.getText();

			if (!methodCallName.endsWith(StringPool.PERIOD + methodName)) {
				continue;
			}

			methodCallDetailASTs.add(methodCallDetailAST);
		}

		return methodCallDetailASTs;
	}

	private String _getPackageName(DetailAST upgradeStepRegistratorDetailAST) {
		DetailAST packageDefDetailAST =
			upgradeStepRegistratorDetailAST.findFirstToken(
				TokenTypes.PACKAGE_DEF);

		DetailAST lastChildDetailAST = packageDefDetailAST.getLastChild();

		FullIdent fullIdent = FullIdent.createFullIdent(
			lastChildDetailAST.getPreviousSibling());

		return fullIdent.getText();
	}

	private List<DetailAST> _getParameterDetailASTs(
		DetailAST methodCallDetailAST) {

		List<DetailAST> parameterDetailASTs = new ArrayList<>();

		DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.ELIST);

		for (DetailAST detailAST = elistDetailAST.getFirstChild();
			 detailAST != null; detailAST = detailAST.getNextSibling()) {

			if (detailAST.getType() == TokenTypes.COMMA) {
				continue;
			}

			parameterDetailASTs.add(_getExpressionDetailAST(detailAST));
		}

		return parameterDetailASTs;
	}

	private String _getUpgradeStepClassName(
		DetailAST upgradeStepDetailAST, String upgradeStepRegistratorClassName,
		DetailAST upgradeStepRegistratorDetailAST) {

		if (upgradeStepDetailAST.getType() == TokenTypes.TYPECAST) {
			upgradeStepDetailAST = upgradeStepDetailAST.getLastChild();
		}

		if (upgradeStepDetailAST.getType() == TokenTypes.IDENT) {
			DetailAST variableDefDetailAST = _getVariableDefDetailAST(
				upgradeStepDetailAST.getText(),
				upgradeStepRegistratorDetailAST);

			if (variableDefDetailAST == null) {
				return upgradeStepRegistratorClassName;
			}

			DetailAST assignDetailAST = variableDefDetailAST.findFirstToken(
				TokenTypes.ASSIGN);

			if (assignDetailAST == null) {
				return upgradeStepRegistratorClassName;
			}

			return _getUpgradeStepClassName(
				_getExpressionDetailAST(assignDetailAST.getFirstChild()),
				upgradeStepRegistratorClassName,
				upgradeStepRegistratorDetailAST);
		}

		if (upgradeStepDetailAST.getType() == TokenTypes.LITERAL_NEW) {
			List<DetailAST> anonymousClassDetailASTs =
				_getAnonymousClassDetailASTs(upgradeStepRegistratorDetailAST);

			int index = anonymousClassDetailASTs.indexOf(upgradeStepDetailAST);

			if (index != -1) {
				return upgradeStepRegistratorClassName + StringPool.DOLLAR +
					(index + 1);
			}

			FullIdent fullIdent = FullIdent.createFullIdentBelow(
				upgradeStepDetailAST);

			return _getFullyQualifiedClassName(
				fullIdent.getText(), upgradeStepRegistratorDetailAST);
		}

		DetailAST firstChildDetailAST = upgradeStepDetailAST.getFirstChild();

		if ((upgradeStepDetailAST.getType() != TokenTypes.METHOD_CALL) ||
			(firstChildDetailAST.getType() != TokenTypes.DOT)) {

			return upgradeStepRegistratorClassName;
		}

		FullIdent fullIdent = FullIdent.createFullIdentBelow(
			firstChildDetailAST);

		String receiverName = fullIdent.getText();

		DetailAST variableDefDetailAST = _getVariableDefDetailAST(
			receiverName, upgradeStepRegistratorDetailAST);

		if (variableDefDetailAST != null) {
			FullIdent typeFullIdent = FullIdent.createFullIdentBelow(
				variableDefDetailAST.findFirstToken(TokenTypes.TYPE));

			receiverName = typeFullIdent.getText();
		}

		return _getFullyQualifiedClassName(
			receiverName, upgradeStepRegistratorDetailAST);
	}

	private List<DetailAST> _getUpgradeStepRegistratorDetailASTs(File baseDir)
		throws CheckstyleException, IOException {

		List<DetailAST> upgradeStepRegistratorDetailASTs = new ArrayList<>();

		File javaDir = new File(baseDir, "src/main/java");

		if (!javaDir.isDirectory()) {
			return upgradeStepRegistratorDetailASTs;
		}

		FileSystem fileSystem = FileSystems.getDefault();

		PathMatcher pathMatcher = fileSystem.getPathMatcher(
			"glob:**/upgrade/registry/*UpgradeStepRegistrator.java");

		List<File> files = new ArrayList<>();

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

		for (File file : files) {
			DetailAST detailAST = JavaParser.parseFile(
				file, JavaParser.Options.WITHOUT_COMMENTS);

			if (detailAST.findFirstToken(TokenTypes.CLASS_DEF) == null) {
				continue;
			}

			upgradeStepRegistratorDetailASTs.add(detailAST);
		}

		return upgradeStepRegistratorDetailASTs;
	}

	private DetailAST _getVariableDefDetailAST(
		String name, DetailAST upgradeStepRegistratorDetailAST) {

		for (DetailAST variableDefDetailAST :
				DetailASTUtil.getAllChildTokens(
					upgradeStepRegistratorDetailAST, true,
					TokenTypes.VARIABLE_DEF)) {

			DetailAST identDetailAST = variableDefDetailAST.findFirstToken(
				TokenTypes.IDENT);

			if (name.equals(identDetailAST.getText())) {
				return variableDefDetailAST;
			}
		}

		return null;
	}

	private static final Pattern _timestampPattern = Pattern.compile(
		"^\t\"timestamp\": \"([^\"]*)\"", Pattern.MULTILINE);

	private final File _baseDir;
	private final File _manifestFile;
	private final String _module;

}