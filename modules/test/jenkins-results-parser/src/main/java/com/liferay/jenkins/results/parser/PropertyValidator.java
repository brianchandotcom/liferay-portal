/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brittney Nguyen
 */
public class PropertyValidator {

	public static void main(String[] args) throws IOException {
		File jenkinsRepositoryDir = null;

		if (args.length > 0) {
			jenkinsRepositoryDir = new File(args[0]);
		}
		else {
			jenkinsRepositoryDir =
				JenkinsResultsParserUtil.getJenkinsRepositoryDir();
		}

		File jenkinsResultsParserSourceDir = null;

		if (args.length > 1) {
			jenkinsResultsParserSourceDir = new File(args[1]);
		}
		else {
			jenkinsResultsParserSourceDir = new File(
				"modules/test/jenkins-results-parser/src/main/java");
		}

		ValidationResult validationResult = validate(
			jenkinsRepositoryDir, jenkinsResultsParserSourceDir);

		for (String unconsumedKey : validationResult.getUnconsumedKeys()) {
			System.out.println(
				"WARNING: Unconsumed build property \"" + unconsumedKey + "\"");
		}

		for (ConsumptionFailure consumptionFailure :
				validationResult.getConsumptionFailures()) {

			System.err.println(consumptionFailure.getMessage());
		}

		if (validationResult.hasConsumptionFailures()) {
			System.exit(1);
		}
	}

	public static ValidationResult validate(
			File jenkinsRepositoryDir, File jenkinsResultsParserSourceDir)
		throws IOException {

		TreeSet<String> definedKeys = _getDefinedKeys(jenkinsRepositoryDir);

		Set<String> consumedKeys = new TreeSet<>();
		List<String> consumedPrefixes = new ArrayList<>();
		List<ConsumptionFailure> consumptionFailures = new ArrayList<>();

		for (PropertyScanner propertyScanner : _propertyScanners) {
			for (File file :
					propertyScanner.findFiles(
						jenkinsRepositoryDir, jenkinsResultsParserSourceDir)) {

				String content = JenkinsResultsParserUtil.read(file);

				for (ConsumedKey consumedKey :
						propertyScanner.getConsumedKeys(content, file)) {

					String key = consumedKey.getKey();

					if (consumedKey.isPrefix()) {
						consumedPrefixes.add(key);
					}
					else {
						consumedKeys.add(key);

						int index = key.indexOf('[');

						if (index != -1) {
							consumedPrefixes.add(key.substring(0, index + 1));
						}
					}

					if (consumedKey.isStrict() && !consumedKey.isPrefix() &&
						!_isDefined(definedKeys, key)) {

						consumptionFailures.add(
							new ConsumptionFailure(consumedKey));
					}
				}
			}
		}

		List<String> unconsumedKeys = new ArrayList<>();

		for (String definedKey : definedKeys) {
			if (!_isConsumed(consumedKeys, consumedPrefixes, definedKey)) {
				unconsumedKeys.add(definedKey);
			}
		}

		Collections.sort(unconsumedKeys);

		return new ValidationResult(consumptionFailures, unconsumedKeys);
	}

	public static class ConsumedKey {

		public ConsumedKey(
			String key, int lineNumber, File sourceFile, boolean strict,
			String surface) {

			_key = key;
			_lineNumber = lineNumber;
			_sourceFile = sourceFile;
			_strict = strict;
			_surface = surface;

			_prefix =
				key.endsWith("(") || key.endsWith(".") || key.endsWith("/") ||
				key.endsWith("[");
		}

		public String getKey() {
			return _key;
		}

		public int getLineNumber() {
			return _lineNumber;
		}

		public File getSourceFile() {
			return _sourceFile;
		}

		public String getSurface() {
			return _surface;
		}

		public boolean isPrefix() {
			return _prefix;
		}

		public boolean isStrict() {
			return _strict;
		}

		private final String _key;
		private final int _lineNumber;
		private final boolean _prefix;
		private final File _sourceFile;
		private final boolean _strict;
		private final String _surface;

	}

	public static class ConsumptionFailure {

		public ConsumptionFailure(ConsumedKey consumedKey) {
			_consumedKey = consumedKey;
		}

		public String getMessage() {
			return JenkinsResultsParserUtil.combine(
				"[", _consumedKey.getSurface(), "] Undefined build property \"",
				_consumedKey.getKey(), "\" consumed at ",
				JenkinsResultsParserUtil.getCanonicalPath(
					_consumedKey.getSourceFile()),
				":", String.valueOf(_consumedKey.getLineNumber()));
		}

		private final ConsumedKey _consumedKey;

	}

	public static class ValidationResult {

		public ValidationResult(
			List<ConsumptionFailure> consumptionFailures,
			List<String> unconsumedKeys) {

			_consumptionFailures = consumptionFailures;
			_unconsumedKeys = unconsumedKeys;
		}

		public List<ConsumptionFailure> getConsumptionFailures() {
			return _consumptionFailures;
		}

		public List<String> getUnconsumedKeys() {
			return _unconsumedKeys;
		}

		public boolean hasConsumptionFailures() {
			return !_consumptionFailures.isEmpty();
		}

		private final List<ConsumptionFailure> _consumptionFailures;
		private final List<String> _unconsumedKeys;

	}

	private static List<ConsumedKey> _getConsumedKeys(
		List<int[]> beanshellRegions, String content, File file,
		Pattern pattern, boolean strict, String surface) {

		List<ConsumedKey> consumedKeys = new ArrayList<>();

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			if ((beanshellRegions != null) &&
				!_isWithinBeanshellRegions(beanshellRegions, matcher.start())) {

				continue;
			}

			String key = matcher.group(1);

			int index = key.indexOf("${");

			int macroIndex = key.indexOf("@{");

			if ((macroIndex != -1) && ((index == -1) || (macroIndex < index))) {
				index = macroIndex;
			}

			if (index != -1) {
				key = key.substring(0, index);
			}

			consumedKeys.add(
				new ConsumedKey(
					key, _getLineNumber(content, matcher.start()), file, strict,
					surface));
		}

		return consumedKeys;
	}

	private static TreeSet<String> _getDefinedKeys(File jenkinsRepositoryDir)
		throws IOException {

		TreeSet<String> definedKeys = new TreeSet<>();

		for (File file :
				JenkinsResultsParserUtil.findFiles(
					new File(jenkinsRepositoryDir, "commands"),
					"build-.+\\.properties")) {

			Properties properties = new Properties();

			properties.load(
				new StringReader(JenkinsResultsParserUtil.read(file)));

			definedKeys.addAll(properties.stringPropertyNames());
		}

		return definedKeys;
	}

	private static int _getLineNumber(String content, int offset) {
		int lineNumber = 1;

		for (int i = 0; i < offset; i++) {
			if (content.charAt(i) == '\n') {
				lineNumber++;
			}
		}

		return lineNumber;
	}

	private static boolean _isBasePropertyNameDefined(
		String basePropertyName, TreeSet<String> definedKeys) {

		if (definedKeys.contains(basePropertyName)) {
			return true;
		}

		String prefix = basePropertyName + "[";

		String ceilingKey = definedKeys.ceiling(prefix);

		if ((ceilingKey != null) && ceilingKey.startsWith(prefix)) {
			return true;
		}

		return false;
	}

	private static boolean _isConsumed(
		Set<String> consumedKeys, List<String> consumedPrefixes,
		String definedKey) {

		for (String consumedKey : consumedKeys) {
			if (_isMatchingKey(definedKey, consumedKey)) {
				return true;
			}
		}

		for (String consumedPrefix : consumedPrefixes) {
			if (definedKey.startsWith(consumedPrefix)) {
				return true;
			}
		}

		return false;
	}

	private static boolean _isDefined(TreeSet<String> definedKeys, String key) {
		if (_isBasePropertyNameDefined(key, definedKeys)) {
			return true;
		}

		int index = key.indexOf('[');

		if (index != -1) {
			String baseKey = key.substring(0, index);

			if (_isBasePropertyNameDefined(baseKey, definedKeys)) {
				return true;
			}
		}

		return _documentedDefaultPropertyNames.contains(key);
	}

	private static boolean _isMatchingKey(String key1, String key2) {
		if (key1.equals(key2) || key1.startsWith(key2 + "[") ||
			key2.startsWith(key1 + "[")) {

			return true;
		}

		return false;
	}

	private static boolean _isWithinBeanshellRegions(
		List<int[]> beanshellRegions, int offset) {

		for (int[] beanshellRegion : beanshellRegions) {
			if ((offset >= beanshellRegion[0]) &&
				(offset < beanshellRegion[1])) {

				return true;
			}
		}

		return false;
	}

	private static final Pattern _antReferencePattern = Pattern.compile(
		"\\$\\{([^${}@]+?)\\}");
	private static final Pattern _beanshellBlockPattern = Pattern.compile(
		"<beanshell\\b.*?</beanshell>", Pattern.DOTALL);
	private static final Pattern _buildPropertyGetterPattern = Pattern.compile(
		"BuildPropertyGetter\\s+\"?([^\\s\"`$]+)");
	private static final Set<String> _documentedDefaultPropertyNames =
		new HashSet<>(
			Arrays.asList(
				"cloud.fleet.primary.label", "dist.node.axis.count",
				"failure.report.compare.length.max",
				"failure.report.similarity.threshold",
				"jenkins.osb.jenkins.web.master.url",
				"master.auto.scaling.group.name",
				"one.password.access.token.key", "one.password.connect.url",
				"scancode.s3.bucket", "test.history.batch.maximum.duration",
				"test.history.test.maximum.duration",
				"test.history.test.maximum.overhead.duration",
				"test.results.consistency.report.suites",
				"testray.cloud.bucket"));
	private static final Pattern _getBuildPropertyPattern = Pattern.compile(
		"\\bgetBuildProperty(?:AsList)?\\(\\s*(?:[\\w.()]+\\s*,\\s*)?\"" +
			"([^\"]+)\"");
	private static final Pattern _getPropertyPattern = Pattern.compile(
		"\\bgetProperty\\(\\s*(?:[\\w.]+\\.)?getBuildProperties\\(" +
			"[^)]*\\)\\s*,\\s*\"([^\"]+)\"");
	private static final Pattern _projectGetPropertyPattern = Pattern.compile(
		"\\bproject\\.getProperty\\(\\s*\"([^\"]+)\"");
	private static final Pattern _propertiesFileKeyPattern = Pattern.compile(
		"(?m)^[ \\t]*([a-z][\\w.-]*(?:\\[[^\\]=\\s]*\\])*)=");
	private static final Pattern _propertiesGetPropertyPattern =
		Pattern.compile("(?<!System)\\.getProperty\\(\\s*\"([^\"]+)\"");
	private static final Pattern _propertyKeyLiteralPattern = Pattern.compile(
		"\"([a-z][a-z0-9]*(?:[.-][a-z0-9]+)+(?:\\[[^\"]*\\])*[.\\[]?)\"");
	private static final List<PropertyScanner> _propertyScanners =
		Arrays.asList(
			new AntPropertyValueScanner(), new CommandsXMLScanner(),
			new JenkinsResultsParserJavaScanner(), new PortalScanner(),
			new ShellScanner());
	private static final Pattern _shellGetBuildPropertyPattern =
		Pattern.compile("get_build_property(?:_value)?\\s+\"([^\"$]+)");

	private static class AntPropertyValueScanner implements PropertyScanner {

		@Override
		public List<File> findFiles(
			File jenkinsRepositoryDir, File jenkinsResultsParserSourceDir) {

			return JenkinsResultsParserUtil.findFiles(
				new File(jenkinsRepositoryDir, "commands"),
				"build-.+\\.properties");
		}

		@Override
		public List<ConsumedKey> getConsumedKeys(String content, File file) {
			return _getConsumedKeys(
				null, content, file, _antReferencePattern, false,
				"ANT_PROPERTY_VALUE");
		}

	}

	private static class CommandsXMLScanner implements PropertyScanner {

		@Override
		public List<File> findFiles(
			File jenkinsRepositoryDir, File jenkinsResultsParserSourceDir) {

			List<File> files = new ArrayList<>();

			for (File file :
					JenkinsResultsParserUtil.findFiles(
						new File(jenkinsRepositoryDir, "commands"),
						".*\\.xml")) {

				String fileName = file.getName();

				if (!fileName.equals("build-properties.xml")) {
					files.add(file);
				}
			}

			return files;
		}

		@Override
		public List<ConsumedKey> getConsumedKeys(String content, File file) {
			List<ConsumedKey> consumedKeys = new ArrayList<>();

			consumedKeys.addAll(
				_getConsumedKeys(
					null, content, file, _antReferencePattern, false,
					"ANT_XML"));
			consumedKeys.addAll(
				_getConsumedKeys(
					null, content, file, _propertyKeyLiteralPattern, false,
					"ANT_XML"));

			List<int[]> beanshellRegions = new ArrayList<>();

			Matcher matcher = _beanshellBlockPattern.matcher(content);

			while (matcher.find()) {
				beanshellRegions.add(
					new int[] {matcher.start(), matcher.end()});
			}

			if (beanshellRegions.isEmpty()) {
				return consumedKeys;
			}

			consumedKeys.addAll(
				_getConsumedKeys(
					beanshellRegions, content, file, _getBuildPropertyPattern,
					true, "BEANSHELL"));
			consumedKeys.addAll(
				_getConsumedKeys(
					beanshellRegions, content, file, _getPropertyPattern, true,
					"BEANSHELL"));
			consumedKeys.addAll(
				_getConsumedKeys(
					beanshellRegions, content, file, _projectGetPropertyPattern,
					false, "BEANSHELL_PROJECT"));

			return consumedKeys;
		}

	}

	private static class JenkinsResultsParserJavaScanner
		implements PropertyScanner {

		@Override
		public List<File> findFiles(
			File jenkinsRepositoryDir, File jenkinsResultsParserSourceDir) {

			return JenkinsResultsParserUtil.findFiles(
				jenkinsResultsParserSourceDir, ".*\\.java");
		}

		@Override
		public List<ConsumedKey> getConsumedKeys(String content, File file) {
			List<ConsumedKey> consumedKeys = new ArrayList<>();

			consumedKeys.addAll(
				_getConsumedKeys(
					null, content, file, _getBuildPropertyPattern, true,
					"JAVA"));
			consumedKeys.addAll(
				_getConsumedKeys(
					null, content, file, _getPropertyPattern, true, "JAVA"));
			consumedKeys.addAll(
				_getConsumedKeys(
					null, content, file, _propertiesGetPropertyPattern, false,
					"JAVA_PROPERTIES"));
			consumedKeys.addAll(
				_getConsumedKeys(
					null, content, file, _propertyKeyLiteralPattern, false,
					"JAVA_STRING"));

			return consumedKeys;
		}

	}

	private static class PortalScanner implements PropertyScanner {

		@Override
		public List<File> findFiles(
			File jenkinsRepositoryDir, File jenkinsResultsParserSourceDir) {

			File portalDir = jenkinsResultsParserSourceDir.getAbsoluteFile();

			while (portalDir != null) {
				File buildTestXMLFile = new File(portalDir, "build-test.xml");

				if (buildTestXMLFile.exists()) {
					break;
				}

				portalDir = portalDir.getParentFile();
			}

			if (portalDir == null) {
				return Collections.emptyList();
			}

			List<File> files = new ArrayList<>();

			File[] portalFiles = portalDir.listFiles();

			if (portalFiles == null) {
				return files;
			}

			for (File file : portalFiles) {
				String fileName = file.getName();

				if (fileName.matches(".*\\.properties|build.*\\.xml")) {
					files.add(file);
				}
			}

			return files;
		}

		@Override
		public List<ConsumedKey> getConsumedKeys(String content, File file) {
			List<ConsumedKey> consumedKeys = new ArrayList<>();

			consumedKeys.addAll(
				_getConsumedKeys(
					null, content, file, _antReferencePattern, false,
					"PORTAL"));

			String fileName = file.getName();

			if (fileName.endsWith(".properties")) {
				consumedKeys.addAll(
					_getConsumedKeys(
						null, content, file, _propertiesFileKeyPattern, false,
						"PORTAL"));
			}
			else {
				consumedKeys.addAll(
					_getConsumedKeys(
						null, content, file, _propertyKeyLiteralPattern, false,
						"PORTAL"));
			}

			return consumedKeys;
		}

	}

	private static class ShellScanner implements PropertyScanner {

		@Override
		public List<File> findFiles(
			File jenkinsRepositoryDir, File jenkinsResultsParserSourceDir) {

			return JenkinsResultsParserUtil.findFiles(
				jenkinsRepositoryDir, ".*\\.sh");
		}

		@Override
		public List<ConsumedKey> getConsumedKeys(String content, File file) {
			List<ConsumedKey> consumedKeys = new ArrayList<>();

			consumedKeys.addAll(
				_getConsumedKeys(
					null, content, file, _buildPropertyGetterPattern, false,
					"SHELL"));
			consumedKeys.addAll(
				_getConsumedKeys(
					null, content, file, _shellGetBuildPropertyPattern, false,
					"SHELL"));

			return consumedKeys;
		}

	}

	private interface PropertyScanner {

		public List<File> findFiles(
			File jenkinsRepositoryDir, File jenkinsResultsParserSourceDir);

		public List<ConsumedKey> getConsumedKeys(String content, File file);

	}

}