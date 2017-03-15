/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.source.formatter;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ImportsFormatter;
import com.liferay.portal.tools.JavaImportsFormatter;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.FileCheck;
import com.liferay.source.formatter.checks.JavaCombineLinesCheck;
import com.liferay.source.formatter.checks.JavaDiamondOperatorCheck;
import com.liferay.source.formatter.checks.JavaEmptyLinesCheck;
import com.liferay.source.formatter.checks.JavaIfStatementCheck;
import com.liferay.source.formatter.checks.JavaLineBreakCheck;
import com.liferay.source.formatter.checks.JavaLogLevelCheck;
import com.liferay.source.formatter.checks.JavaLongLinesCheck;
import com.liferay.source.formatter.checks.JavaPackagePathCheck;
import com.liferay.source.formatter.checks.JavaUpgradeClassCheck;
import com.liferay.source.formatter.checks.JavaVerifyUpgradeConnectionCheck;
import com.liferay.source.formatter.checks.JavaXMLSecurityCheck;
import com.liferay.source.formatter.checkstyle.util.CheckStyleUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.artifact.versioning.ComparableVersion;

/**
 * @author Hugo Huijser
 */
public class JavaSourceProcessor extends BaseSourceProcessor {

	protected String checkAnnotationLineBreaks(
		String content, String annotation) {

		Matcher matcher = _annotationLineBreakPattern1.matcher(annotation);

		if (matcher.find()) {
			String replacement = StringUtil.replaceFirst(
				annotation, matcher.group(1), StringPool.BLANK,
				matcher.start());

			return StringUtil.replace(content, annotation, replacement);
		}

		matcher = _annotationLineBreakPattern2.matcher(annotation);

		if (matcher.find()) {
			String replacement = StringUtil.replaceFirst(
				annotation, matcher.group(1), StringPool.SPACE,
				matcher.start());

			return StringUtil.replace(content, annotation, replacement);
		}

		return content;
	}

	protected String checkAnnotationMetaTypeProperties(
		String content, String annotation) {

		if (!annotation.contains("@Meta.")) {
			return content;
		}

		Matcher matcher = _annotationMetaTypePattern.matcher(annotation);

		if (!matcher.find()) {
			return content;
		}

		String newAnnotation = StringUtil.replaceFirst(
			annotation, StringPool.PERCENT, StringPool.BLANK, matcher.start());

		return StringUtil.replace(content, annotation, newAnnotation);
	}

	protected String checkAnnotationParameterProperties(
		String content, String annotation) {

		int x = annotation.indexOf("property = {");

		if (x == -1) {
			return content;
		}

		int y = x;

		while (true) {
			y = annotation.indexOf(CharPool.CLOSE_CURLY_BRACE, y + 1);

			if (!ToolsUtil.isInsideQuotes(annotation, y)) {
				break;
			}
		}

		String parameterProperties = annotation.substring(x + 12, y);

		parameterProperties = StringUtil.replace(
			parameterProperties, StringPool.NEW_LINE, StringPool.SPACE);

		String[] parameterPropertiesArray = StringUtil.split(
			parameterProperties, StringPool.COMMA_AND_SPACE);

		String previousPropertyName = null;
		String previousPropertyNameAndValue = null;

		for (String parameterProperty : parameterPropertiesArray) {
			x = parameterProperty.indexOf(CharPool.QUOTE);
			y = parameterProperty.indexOf(CharPool.EQUAL, x);

			int z = x;

			while (true) {
				z = parameterProperty.indexOf(CharPool.QUOTE, z + 1);

				if ((z == -1) ||
					!ToolsUtil.isInsideQuotes(parameterProperty, z)) {

					break;
				}
			}

			if ((x == -1) || (y == -1) || (z == -1)) {
				return content;
			}

			String propertyName = parameterProperty.substring(x + 1, y);
			String propertyNameAndValue = parameterProperty.substring(x + 1, z);

			if (Validator.isNotNull(previousPropertyName) &&
				(previousPropertyName.compareToIgnoreCase(propertyName) > 0)) {

				content = StringUtil.replaceFirst(
					content, previousPropertyNameAndValue,
					propertyNameAndValue);
				content = StringUtil.replaceLast(
					content, propertyNameAndValue,
					previousPropertyNameAndValue);

				return content;
			}

			previousPropertyName = propertyName;
			previousPropertyNameAndValue = propertyNameAndValue;
		}

		return content;
	}

	protected void checkBndInheritAnnotationOption() {
		Map<String, BNDSettings> bndSettingsMap = getBNDSettingsMap();

		for (Map.Entry<String, BNDSettings> entry : bndSettingsMap.entrySet()) {
			BNDSettings bndSettings = entry.getValue();

			String content = bndSettings.getContent();
			String fileLocation = bndSettings.getFileLocation();
			boolean inheritRequired = bndSettings.isInheritRequired();

			if (content.contains("-dsannotations-options: inherit")) {
				/*
				if (!inheritRequired) {
					printError(
						fileLocation,
						"Redundant '-dsannotations-options: inherit': " +
							fileLocation + "bnd.bnd");
				}
				*/
			}
			else if (inheritRequired) {
				printError(
					fileLocation,
					"Add '-dsannotations-options: inherit': " + fileLocation +
						"bnd.bnd");
			}
		}
	}

	protected void checkDeserializationSecurity(
		String fileName, String content, boolean isRunOutsidePortalExclusion) {

		for (Pattern vulnerabilityPattern :
				_javaSerializationVulnerabilityPatterns) {

			Matcher matcher = vulnerabilityPattern.matcher(content);

			if (!matcher.matches()) {
				continue;
			}

			StringBundler sb = new StringBundler(3);

			if (isRunOutsidePortalExclusion) {
				sb.append("Possible Java Serialization Remote Code Execution ");
				sb.append("vulnerability using ");
			}
			else {
				sb.append("Use ProtectedObjectInputStream instead of ");
			}

			sb.append(matcher.group(1));

			processMessage(fileName, sb.toString());
		}
	}

	protected void checkFinderCacheInterfaceMethod(
		String fileName, String content) {

		if (!fileName.endsWith("FinderImpl.java") ||
			!content.contains("public static final FinderPath")) {

			return;
		}

		Matcher matcher = _fetchByPrimaryKeysMethodPattern.matcher(content);

		if (!matcher.find()) {
			processMessage(
				fileName,
				"Missing override of BasePersistenceImpl." +
					"fetchByPrimaryKeys(Set<Serializable>), see LPS-49552");
		}
	}

	protected void checkInternalImports(
		String fileName, String absolutePath, String content) {

		if (absolutePath.contains("/modules/core/") ||
			absolutePath.contains("/modules/util/") ||
			fileName.contains("/test/") ||
			fileName.contains("/testIntegration/")) {

			return;
		}

		Matcher matcher = _internalImportPattern.matcher(content);

		int pos = -1;

		while (matcher.find()) {
			if (pos == -1) {
				pos = absolutePath.lastIndexOf("/com/liferay/");
			}

			String expectedImportFileLocation =
				absolutePath.substring(0, pos + 13) +
					StringUtil.replace(matcher.group(1), ".", "/") + ".java";

			File file = new File(expectedImportFileLocation);

			if (!file.exists()) {
				processMessage(
					fileName,
					"Do not import internal class from another module",
					getLineCount(content, matcher.start(1)));
			}
		}
	}

	protected void checkSystemEventAnnotations(String content, String fileName)
		throws Exception {

		if ((!portalSource && !subrepository) ||
			!fileName.endsWith("PortletDataHandler.java")) {

			return;
		}

		int pos = content.indexOf("setDeletionSystemEventStagedModelTypes");

		if (pos == -1) {
			return;
		}

		String deletionSystemEventStagedModelTypes = content.substring(
			pos, content.indexOf(");", pos));

		Matcher matcher = _stagedModelTypesPattern.matcher(
			deletionSystemEventStagedModelTypes);

		while (matcher.find()) {
			String stagedModelTypeClassName = matcher.group(1);

			pos = stagedModelTypeClassName.indexOf(".class");

			if (pos == -1) {
				pos = stagedModelTypeClassName.indexOf("Constants");
			}

			if (pos == -1) {
				return;
			}

			String className = stagedModelTypeClassName.substring(0, pos);

			Pattern packageNamePattern = Pattern.compile(
				"import (com\\.liferay\\.[a-zA-Z\\.]*)\\.model\\." + className +
					";");

			Matcher packageNameMatcher = packageNamePattern.matcher(content);

			if (!packageNameMatcher.find()) {
				return;
			}

			StringBundler sb = new StringBundler(5);

			sb.append(fileName.substring(0, fileName.indexOf("/src/") + 5));
			sb.append(
				StringUtil.replace(
					packageNameMatcher.group(1), StringPool.PERIOD,
					StringPool.SLASH));
			sb.append("/service/impl/");
			sb.append(className);
			sb.append("LocalServiceImpl.java");

			String localServiceImplFileName = sb.toString();

			String localServiceImplContent = FileUtil.read(
				new File(localServiceImplFileName));

			if (localServiceImplContent == null) {
				return;
			}

			if (!localServiceImplContent.contains("@SystemEvent")) {
				processMessage(
					fileName,
					"Missing deletion system event '" +
						localServiceImplFileName + "', see LPS-46632");
			}
		}
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		if (hasGeneratedTag(content)) {
			return content;
		}

		_ungeneratedFiles.add(file);

		String className = file.getName();

		int pos = className.lastIndexOf(CharPool.PERIOD);

		className = className.substring(0, pos);

		String packagePath = StringPool.BLANK;

		Matcher matcher = _packagePattern.matcher(content);

		if (matcher.find()) {
			packagePath = matcher.group(2);
		}

		if (packagePath.endsWith(".model")) {
			if (content.contains("extends " + className + "Model")) {
				return content;
			}
		}

		String newContent = trimContent(content, false);

		if (newContent.contains("$\n */")) {
			processMessage(fileName, "*");

			newContent = StringUtil.replace(newContent, "$\n */", "$\n *\n */");
		}

		newContent = fixCopyright(
			newContent, absolutePath, fileName, className);

		if (newContent.contains(className + ".java.html")) {
			processMessage(fileName, "Java2HTML");
		}

		if (newContent.contains(" * @author Raymond Aug") &&
			!newContent.contains(" * @author Raymond Aug\u00e9")) {

			newContent = newContent.replaceFirst(
				"Raymond Aug.++", "Raymond Aug\u00e9");

			processMessage(fileName, "UTF-8");
		}

		newContent = fixDataAccessConnection(className, newContent);
		newContent = fixSessionKey(fileName, newContent, sessionKeyPattern);

		newContent = StringUtil.replace(
			newContent,
			new String[] {
				"com.liferay.portal.PortalException",
				"com.liferay.portal.SystemException",
				"com.liferay.util.LocalizationUtil"
			},
			new String[] {
				"com.liferay.portal.kernel.exception.PortalException",
				"com.liferay.portal.kernel.exception.SystemException",
				"com.liferay.portal.kernel.util.LocalizationUtil"
			});

		newContent = StringUtil.replace(
			newContent, " final static ", " static final ");

		newContent = fixCompatClassImports(absolutePath, newContent);

		ImportsFormatter importsFormatter = new JavaImportsFormatter();

		newContent = importsFormatter.format(
			newContent, packagePath, className);

		newContent = StringUtil.replace(
			newContent,
			new String[] {";\n/**", "\t/*\n\t *", ";;\n", "\n/**\n *\n *"},
			new String[] {";\n\n/**", "\t/**\n\t *", ";\n", "\n/**\n *"});

		newContent = formatAnnotations(
			fileName, StringPool.BLANK, newContent, StringPool.BLANK, true);

		matcher = _logPattern.matcher(newContent);

		if (matcher.find()) {
			String logClassName = matcher.group(1);

			if (!logClassName.equals(className)) {
				newContent = StringUtil.replaceLast(
					newContent, logClassName + ".class)",
					className + ".class)");
			}
		}

		if (!isExcludedPath(_STATIC_LOG_EXCLUDES, absolutePath)) {
			newContent = StringUtil.replace(
				newContent, "private Log _log",
				"private static final Log _log");
		}

		newContent = StringUtil.replace(
			newContent,
			new String[] {"!Validator.isNotNull(", "!Validator.isNull("},
			new String[] {"Validator.isNull(", "Validator.isNotNull("});

		if (newContent.contains("*/\npackage ")) {
			processMessage(fileName, "package");
		}

		if ((portalSource ||subrepository) &&
			!_allowUseServiceUtilInServiceImpl &&
			!fileName.contains("/wsrp/internal/bind/") &&
			!className.equals("BaseServiceImpl") &&
			className.endsWith("ServiceImpl") &&
			newContent.contains("ServiceUtil.")) {

			processMessage(
				fileName,
				"Do not use *ServiceUtil in *ServiceImpl class, create a " +
					"reference via service.xml instead");
		}

		boolean isRunOutsidePortalExclusion = isExcludedPath(
			RUN_OUTSIDE_PORTAL_EXCLUDES, absolutePath);

		if (!isRunOutsidePortalExclusion &&
			!isExcludedPath(_PROXY_EXCLUDES, absolutePath) &&
			newContent.contains("import java.lang.reflect.Proxy;")) {

			processMessage(
				fileName, "Use ProxyUtil instead of java.lang.reflect.Proxy");
		}

		if (newContent.contains("import edu.emory.mathcs.backport.java")) {
			processMessage(
				fileName, "Illegal import: edu.emory.mathcs.backport.java");
		}

		if (newContent.contains("import jodd.util.StringPool")) {
			processMessage(fileName, "Illegal import: jodd.util.StringPool");
		}

		// LPS-45027

		if (newContent.contains(
				"com.liferay.portal.kernel.util.UnmodifiableList")) {

			processMessage(
				fileName,
				"Use java.util.Collections.unmodifiableList instead of " +
					"com.liferay.portal.kernel.util.UnmodifiableList");
		}

		// LPS-70963

		if (newContent.contains("java.util.WeakHashMap")) {
			processMessage(
				fileName,
				"Do not use java.util.WeakHashMap because it is not " +
					"thread-safe");
		}

		// LPS-28266

		for (int pos1 = -1;;) {
			pos1 = newContent.indexOf(StringPool.TAB + "try {", pos1 + 1);

			if (pos1 == -1) {
				break;
			}

			int pos2 = newContent.indexOf(StringPool.TAB + "try {", pos1 + 1);
			int pos3 = newContent.indexOf("\"select count(", pos1);

			if ((pos2 != -1) && (pos3 != -1) && (pos2 < pos3)) {
				continue;
			}

			int pos4 = newContent.indexOf("rs.getLong(1)", pos1);
			int pos5 = newContent.indexOf(StringPool.TAB + "finally {", pos1);

			if ((pos3 == -1) || (pos4 == -1) || (pos5 == -1)) {
				break;
			}

			if ((pos3 < pos4) && (pos4 < pos5)) {
				processMessage(
					fileName, "Use rs.getInt(1) for count, see LPS-28266");
			}
		}

		// LPS-33070

		matcher = _processCallablePattern.matcher(content);

		if (matcher.find() &&
			!content.contains("private static final long serialVersionUID")) {

			processMessage(
				fileName,
				"Assign ProcessCallable implementation a serialVersionUID");
		}

		checkLanguageKeys(
			fileName, absolutePath, newContent, languageKeyPattern);

		newContent = sortMethodCalls(absolutePath, newContent);

		newContent = formatStringBundler(fileName, newContent, _maxLineLength);

		newContent = StringUtil.replace(
			newContent, StringPool.TAB + "for (;;) {",
			StringPool.TAB + "while (true) {");

		newContent = formatExceptions(newContent, file, packagePath, fileName);

		// LPS-39508

		if (!isRunOutsidePortalExclusion &&
			!isExcludedPath(_SECURE_RANDOM_EXCLUDES, absolutePath) &&
			content.contains("java.security.SecureRandom") &&
			!content.contains("javax.crypto.KeyGenerator")) {

			processMessage(
				fileName,
				"Use SecureRandomUtil or com.liferay.portal.kernel.security." +
					"SecureRandom instead of java.security.SecureRandom");
		}

		// LPS-46632

		checkSystemEventAnnotations(newContent, fileName);

		// LPS-46017

		newContent = StringUtil.replace(
			newContent, " static interface ", " interface ");

		// LPS-47055

		newContent = fixSystemExceptions(newContent);

		// LPS-47648

		if ((portalSource || subrepository) &&
			(fileName.contains("/test/integration/") ||
			 fileName.contains("/testIntegration/java"))) {

			newContent = StringUtil.replace(
				newContent, "FinderCacheUtil.clearCache();", StringPool.BLANK);
		}

		// LPS-47682

		newContent = fixIncorrectParameterTypeForLanguageUtil(
			newContent, false, fileName);

		if (portalSource && fileName.contains("/portal-kernel/") &&
			content.contains("import javax.servlet.jsp.")) {

			processMessage(
				fileName,
				"Never import javax.servlet.jsp.* from portal-kernel, see " +
					"LPS-47682");
		}

		// LPS-49552

		checkFinderCacheInterfaceMethod(fileName, newContent);

		// LPS-60358

		if (!fileName.contains("/test/") &&
			!fileName.contains("/testIntegration/") &&
			!isExcludedPath(_SECURE_DESERIALIZATION_EXCLUDES, absolutePath)) {

			checkDeserializationSecurity(
				fileName, content, isRunOutsidePortalExclusion);
		}

		// LPS-55690

		if (newContent.contains("org.testng.Assert")) {
			processMessage(
				fileName,
				"Use org.junit.Assert instead of org.testng.Assert, see " +
					"LPS-55690");
		}

		if ((portalSource || subrepository) && isModulesFile(absolutePath) &&
			packagePath.startsWith("com.liferay")) {

			newContent = formatModulesFile(
				fileName, absolutePath, className, packagePath, newContent);
		}

		// LPS-48156

		newContent = checkPrincipalException(newContent);

		// LPS-60473

		if (newContent.contains(".supportsBatchUpdates()") &&
			!fileName.endsWith("AutoBatchPreparedStatementUtil.java")) {

			processMessage(
				fileName,
				"Use AutoBatchPreparedStatementUtil instead of " +
					"DatabaseMetaData.supportsBatchUpdates, see LPS-60473");
		}

		// LPS-64056

		if (newContent.contains("Configurable.createConfigurable(") &&
			!fileName.endsWith("ConfigurableUtil.java")) {

			processMessage(
				fileName,
				"Use ConfigurableUtil.createConfigurable instead of " +
					"Configurable.createConfigurable, see LPS-64056");
		}

		// LPS-62786

		checkPropertyUtils(fileName, newContent);

		// LPS-63953

		if (!absolutePath.contains("poshi") &&
			!fileName.endsWith("StringUtilTest.java")) {

			checkStringUtilReplace(fileName, newContent);
		}

		// LPS-65229

		if (fileName.endsWith("ResourceCommand.java") &&
			newContent.contains("ServletResponseUtil.sendFile(")) {

			processMessage(
				fileName,
				"Use PortletResponseUtil.sendFile instead of " +
					"ServletResponseUtil.sendFile");
		}

		if (!fileName.endsWith("GetterUtilTest.java")) {
			checkGetterUtilGet(fileName, newContent);
		}

		newContent = fixIncorrectBooleanUse(newContent, "setAttribute");

		// LPS-69494

		if (!fileName.endsWith("AbstractExtender.java") &&
			newContent.contains(
				"org.apache.felix.utils.extender.AbstractExtender")) {

			StringBundler sb = new StringBundler(4);

			sb.append("Use com.liferay.osgi.felix.util.AbstractExtender ");
			sb.append("instead of ");
			sb.append("org.apache.felix.utils.extender.AbstractExtender, see ");
			sb.append("LPS-69494");

			processMessage(fileName, sb.toString());
		}

		if (_addMissingDeprecationReleaseVersion) {
			newContent = formatDeprecatedJavadoc(
				fileName, absolutePath, newContent);
		}

		newContent = formatAssertEquals(fileName, newContent);

		newContent = formatValidatorEquals(newContent);

		newContent = fixUnparameterizedClassType(newContent);

		newContent = sortExceptions(newContent);

		newContent = formatArray(newContent);

		newContent = formatClassLine(newContent);

		matcher = _incorrectSynchronizedPattern.matcher(newContent);

		newContent = matcher.replaceAll("$1$3 $2");

		pos = newContent.indexOf("\npublic ");

		if (pos != -1) {
			String javaClassContent = newContent.substring(pos + 1);

			int javaClassLineCount = getLineCount(newContent, pos + 1);

			newContent = formatJavaTerms(
				className, packagePath, file, fileName, absolutePath,
				newContent, javaClassContent, javaClassLineCount,
				StringPool.BLANK, _CHECK_JAVA_FIELD_TYPES_EXCLUDES,
				_JAVATERM_SORT_EXCLUDES, _TEST_ANNOTATIONS_EXCLUDES);
		}

		matcher = _anonymousClassPattern.matcher(newContent);

		while (matcher.find()) {
			if (getLevel(matcher.group()) != 0) {
				continue;
			}

			int x = matcher.start() + 1;
			int y = matcher.end();

			while (true) {
				String javaClassContent = newContent.substring(x, y);

				if (getLevel(javaClassContent, "{", "}") != 0) {
					y++;

					continue;
				}

				int javaClassLineCount = getLineCount(
					newContent, matcher.start() + 1);

				newContent = formatJavaTerms(
					StringPool.BLANK, StringPool.BLANK, file, fileName,
					absolutePath, newContent, javaClassContent,
					javaClassLineCount, matcher.group(1),
					_CHECK_JAVA_FIELD_TYPES_EXCLUDES, _JAVATERM_SORT_EXCLUDES,
					_TEST_ANNOTATIONS_EXCLUDES);

				break;
			}
		}

		newContent = formatJava(fileName, absolutePath, newContent);

		return StringUtil.replace(newContent, "\n\n\n", "\n\n");
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		String[] includes = getIncludes();

		if (ArrayUtil.isEmpty(includes)) {
			return new ArrayList<>();
		}

		Collection<String> fileNames = null;

		if (portalSource || subrepository) {
			fileNames = getPortalJavaFiles(includes);

			_checkRegistryInTestClasses = GetterUtil.getBoolean(
				System.getProperty(
					"source.formatter.check.registry.in.test.classes"));
		}
		else {
			fileNames = getPluginJavaFiles(includes);
		}

		return new ArrayList<>(fileNames);
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	protected String fixDataAccessConnection(String className, String content) {
		int x = content.indexOf("package ");

		int y = content.indexOf(CharPool.SEMICOLON, x);

		if ((x == -1) || (y == -1)) {
			return content;
		}

		String packageName = content.substring(x + 8, y);

		if (!packageName.startsWith("com.liferay.portal.kernel.upgrade") &&
			!packageName.startsWith("com.liferay.portal.kernel.verify") &&
			!packageName.startsWith("com.liferay.portal.upgrade") &&
			!packageName.startsWith("com.liferay.portal.verify")) {

			return content;
		}

		content = StringUtil.replace(
			content, "DataAccess.getConnection",
			"DataAccess.getUpgradeOptimizedConnection");

		return content;
	}

	protected String fixIncorrectBooleanUse(
		String content, String... methodNames) {

		for (String methodName : methodNames) {
			Pattern pattern = Pattern.compile(
				"\\." + methodName + "\\((.*?)\\);\n", Pattern.DOTALL);

			Matcher matcher = pattern.matcher(content);

			while (matcher.find()) {
				if (ToolsUtil.isInsideQuotes(content, matcher.start())) {
					continue;
				}

				String match = matcher.group();

				List<String> parametersList = getParameterList(match);

				if (parametersList.size() != 2) {
					continue;
				}

				String secondParameterName = parametersList.get(1);

				if (secondParameterName.equals("false") ||
					secondParameterName.equals("true")) {

					String replacement = StringUtil.replaceLast(
						match, secondParameterName,
						"Boolean." +
							StringUtil.toUpperCase(secondParameterName));

					return StringUtil.replace(content, match, replacement);
				}
			}
		}

		return content;
	}

	protected String fixSystemExceptions(String content) {
		if (!content.contains("SystemException")) {
			return content;
		}

		Matcher matcher = _throwsSystemExceptionPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		String match = matcher.group();
		String replacement = null;

		String afterException = matcher.group(3);
		String beforeException = matcher.group(2);

		if (Validator.isNull(beforeException) &&
			Validator.isNull(afterException)) {

			replacement = matcher.group(4);

			String beforeThrows = matcher.group(1);

			if (Validator.isNotNull(StringUtil.trim(beforeThrows))) {
				replacement = beforeThrows + replacement;
			}
		}
		else if (Validator.isNull(beforeException)) {
			replacement = StringUtil.replaceFirst(
				match, "SystemException, ", StringPool.BLANK);
		}
		else {
			replacement = StringUtil.replaceFirst(
				match, ", SystemException", StringPool.BLANK);
		}

		if (match.equals(replacement)) {
			return content;
		}

		return fixSystemExceptions(
			StringUtil.replaceFirst(content, match, replacement));
	}

	protected String formatAnnotations(
			String fileName, String javaTermName, String content, String indent,
			boolean sortAnnotations)
		throws IOException {

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));

		String line = null;

		String annotation = StringPool.BLANK;
		String previousAnnotation = StringPool.BLANK;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (line.equals(indent + StringPool.CLOSE_CURLY_BRACE)) {
				return content;
			}

			int tabCount = StringUtil.count(line, CharPool.TAB);

			if ((tabCount < indent.length()) && Validator.isNull(annotation)) {
				continue;
			}

			if ((tabCount < indent.length()) ||
				((tabCount == indent.length()) &&
				 !line.startsWith(indent + StringPool.CLOSE_PARENTHESIS))) {

				if (Validator.isNotNull(annotation) &&
					annotation.contains(StringPool.OPEN_PARENTHESIS)) {

					Matcher matcher = _annotationPattern.matcher(annotation);

					if (matcher.find()) {
						String match = matcher.group();

						if ((getLevel(match) == 0) &&
							!match.endsWith("\n)\n") &&
							!match.endsWith("\t)\n")) {

							String tabs = matcher.group(1);

							String replacement = StringUtil.replaceLast(
								match, ")", "\n" + tabs + ")");

							return StringUtil.replace(
								content, match, replacement);
						}
					}

					String newContent = checkAnnotationParameterProperties(
						content, annotation);

					newContent = checkAnnotationMetaTypeProperties(
						newContent, annotation);

					newContent = checkAnnotationLineBreaks(
						newContent, annotation);

					if (!newContent.equals(content)) {
						return formatAnnotations(
							fileName, javaTermName, newContent, indent,
							sortAnnotations);
					}

					String newAnnotation = formatAnnotations(
						fileName, javaTermName, annotation, indent + "\t\t",
						false);

					if (!newAnnotation.equals(annotation)) {
						return StringUtil.replace(
							content, annotation, newAnnotation);
					}
				}

				if (sortAnnotations &&
					Validator.isNotNull(previousAnnotation) &&
					(previousAnnotation.compareToIgnoreCase(annotation) > 0)) {

					content = StringUtil.replaceFirst(
						content, previousAnnotation, annotation);
					content = StringUtil.replaceLast(
						content, annotation, previousAnnotation);

					return formatAnnotations(
						fileName, javaTermName, content, indent,
						sortAnnotations);
				}

				if (line.startsWith(indent + StringPool.AT)) {
					if (Validator.isNotNull(annotation)) {
						previousAnnotation = annotation;
					}

					annotation = line + "\n";
				}
				else {
					annotation = StringPool.BLANK;
					previousAnnotation = StringPool.BLANK;
				}
			}
			else {
				if (Validator.isNull(annotation)) {
					return content;
				}

				annotation += line + "\n";
			}
		}

		return content;
	}

	protected String formatArray(String content) {
		Matcher matcher = _arrayPattern.matcher(content);

		while (matcher.find()) {
			String newLine =
				matcher.group(3) + matcher.group(2) + matcher.group(4) +
					matcher.group(5);

			if (getLineLength(newLine) <= _maxLineLength) {
				return StringUtil.replace(
					content, matcher.group(),
					matcher.group(1) + "\n" + newLine + "\n");
			}
		}

		return content;
	}

	protected String formatAssertEquals(String fileName, String content) {
		if (!fileName.endsWith("Test.java")) {
			return content;
		}

		Matcher matcher = _assertEqualsPattern.matcher(content);

		while (matcher.find()) {
			String parameters = StringUtil.trim(matcher.group(1));

			List<String> parametersList = splitParameters(parameters);

			if (parametersList.size() != 2) {
				continue;
			}

			String actualParameter = parametersList.get(1);

			String strippedQuotesActualParameter = stripQuotes(actualParameter);

			if (!actualParameter.startsWith("expected") &&
				!Validator.isDigit(actualParameter) &&
				Validator.isNotNull(strippedQuotesActualParameter)) {

				continue;
			}

			String assertEquals = matcher.group();
			String expectedParameter = parametersList.get(0);

			String newAssertEquals = StringUtil.replaceFirst(
				assertEquals, expectedParameter, actualParameter,
				assertEquals.indexOf(CharPool.OPEN_PARENTHESIS));

			newAssertEquals = StringUtil.replaceLast(
				newAssertEquals, actualParameter, expectedParameter);

			return StringUtil.replace(content, assertEquals, newAssertEquals);
		}

		return content;
	}

	protected String formatClassLine(String content) {
		Matcher matcher = _classPattern.matcher(content);

		while (matcher.find()) {
			String firstTrailingNonWhitespace = matcher.group(9);
			String match = matcher.group(1);
			String trailingWhitespace = matcher.group(8);

			if (!trailingWhitespace.contains("\n") &&
				!firstTrailingNonWhitespace.equals("}")) {

				return StringUtil.replace(content, match, match + "\n");
			}

			String formattedClassLine = getFormattedClassLine(
				matcher.group(2), match);

			if (formattedClassLine != null) {
				content = StringUtil.replace(
					content, match, formattedClassLine);
			}
		}

		return content;
	}

	protected String formatDeprecatedJavadoc(
			String fileName, String absolutePath, String content)
		throws Exception {

		ComparableVersion mainReleaseComparableVersion =
			getMainReleaseComparableVersion(fileName, absolutePath, true);

		if (mainReleaseComparableVersion == null) {
			return content;
		}

		Matcher matcher = _deprecatedPattern.matcher(content);

		while (matcher.find()) {
			if (matcher.group(2) == null) {
				return StringUtil.insert(
					content,
					" As of " + mainReleaseComparableVersion.toString(),
					matcher.end(1));
			}

			String version = matcher.group(3);

			ComparableVersion comparableVersion = new ComparableVersion(
				version);

			if (comparableVersion.compareTo(mainReleaseComparableVersion) > 0) {
				return StringUtil.replaceFirst(
					content, version, mainReleaseComparableVersion.toString(),
					matcher.start());
			}

			if (StringUtil.count(version, CharPool.PERIOD) == 1) {
				return StringUtil.insert(content, ".0", matcher.end(3));
			}

			String deprecatedInfo = matcher.group(4);

			if (Validator.isNull(deprecatedInfo)) {
				return content;
			}

			if (!deprecatedInfo.startsWith(StringPool.COMMA)) {
				return StringUtil.insert(
					content, StringPool.COMMA, matcher.end(3));
			}

			if (deprecatedInfo.endsWith(StringPool.PERIOD) &&
				!deprecatedInfo.matches("[\\S\\s]*\\.[ \n][\\S\\s]*")) {

				return StringUtil.replaceFirst(
					content, StringPool.PERIOD, StringPool.BLANK,
					matcher.end(4) - 1);
			}
		}

		return content;
	}

	protected String formatDuplicateReferenceMethods(
			String fileName, String content, String className,
			String packagePath)
		throws Exception {

		String moduleSuperClassContent = getModuleSuperClassContent(
			content, className, packagePath);

		if (Validator.isNull(moduleSuperClassContent) ||
			!moduleSuperClassContent.contains("@Component") ||
			!moduleSuperClassContent.contains("@Reference")) {

			setBNDInheritRequiredValue(fileName, false);

			return content;
		}

		boolean bndInheritRequired = false;

		Matcher matcher = _referenceMethodPattern.matcher(
			moduleSuperClassContent);

		while (matcher.find()) {
			String referenceMethod = matcher.group();

			int pos = content.indexOf(referenceMethod);

			if (pos != -1) {
				String referenceMethodContent = matcher.group(6);

				Matcher referenceMethodContentMatcher =
					_referenceMethodContentPattern.matcher(
						referenceMethodContent);

				if (referenceMethodContentMatcher.find()) {
					String variableName = referenceMethodContentMatcher.group(
						1);

					if (StringUtil.count(content, variableName) > 1) {
						continue;
					}
				}

				int x = content.lastIndexOf("\n\n", pos);
				int y = pos + referenceMethod.length();

				String entireMethod = content.substring(x + 1, y);

				content = StringUtil.replace(
					content, entireMethod, StringPool.BLANK);

				bndInheritRequired = true;
			}
			else {
				String referenceMethodModifierAndName = matcher.group(2);

				Pattern duplicateReferenceMethodPattern = Pattern.compile(
					referenceMethodModifierAndName +
						"\\(\\s*([ ,<>\\w]+)\\s+\\w+\\) \\{\\s+([\\s\\S]*?)" +
							"\\s*?\n\t\\}\n");

				Matcher duplicateReferenceMethodMatcher =
					duplicateReferenceMethodPattern.matcher(content);

				if (!duplicateReferenceMethodMatcher.find()) {
					bndInheritRequired = true;

					continue;
				}

				String methodContent = duplicateReferenceMethodMatcher.group(2);
				String referenceMethodName = matcher.group(4);

				if (methodContent.startsWith("super." + referenceMethodName)) {
					int x = content.lastIndexOf(
						"\n\n", duplicateReferenceMethodMatcher.start());
					int y = duplicateReferenceMethodMatcher.end();

					String entireMethod = content.substring(x + 1, y);

					content = StringUtil.replace(
						content, entireMethod, StringPool.BLANK);

					bndInheritRequired = true;
				}
			}
		}

		setBNDInheritRequiredValue(fileName, bndInheritRequired);

		return content;
	}

	protected String formatExceptions(
			String content, File file, String packagePath, String fileName)
		throws IOException {

		Matcher matcher = _catchExceptionPattern.matcher(content);

		int skipVariableNameCheckEndPos = -1;

		while (matcher.find()) {
			String exceptionClassName = matcher.group(2);
			String exceptionVariableName = matcher.group(3);
			String tabs = matcher.group(1);

			String expectedExceptionVariableName = "e";

			if (!exceptionClassName.contains(" |")) {
				Matcher lowerCaseNumberOrPeriodMatcher =
					_lowerCaseNumberOrPeriodPattern.matcher(exceptionClassName);

				expectedExceptionVariableName = StringUtil.toLowerCase(
					lowerCaseNumberOrPeriodMatcher.replaceAll(
						StringPool.BLANK));
			}

			Pattern exceptionVariablePattern = Pattern.compile(
				"(\\W)" + exceptionVariableName + "(\\W)");

			int pos = content.indexOf(
				"\n" + tabs + StringPool.CLOSE_CURLY_BRACE, matcher.end() - 1);

			String insideCatchCode = content.substring(matcher.end(), pos + 1);

			if (insideCatchCode.contains("catch (" + exceptionClassName)) {
				skipVariableNameCheckEndPos = pos;
			}

			if ((skipVariableNameCheckEndPos < matcher.start()) &&
				!expectedExceptionVariableName.equals(exceptionVariableName)) {

				String catchExceptionCodeBlock = content.substring(
					matcher.start(), pos + 1);

				Matcher exceptionVariableMatcher =
					exceptionVariablePattern.matcher(catchExceptionCodeBlock);

				String catchExceptionReplacement =
					exceptionVariableMatcher.replaceAll(
						"$1" + expectedExceptionVariableName + "$2");

				return StringUtil.replaceFirst(
					content, catchExceptionCodeBlock, catchExceptionReplacement,
					matcher.start() - 1);
			}
		}

		return content;
	}

	protected String formatJava(
			String fileName, String absolutePath, String content)
		throws Exception {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;
			String previousLine = StringPool.BLANK;

			int lineCount = 0;

			String packageName = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				if (line.startsWith("package ")) {
					packageName = line.substring(8, line.length() - 1);
				}

				if (line.startsWith("import ")) {
					int pos = line.lastIndexOf(CharPool.PERIOD);

					if (pos != -1) {
						String importPackageName = line.substring(7, pos);

						if (importPackageName.equals(packageName)) {
							continue;
						}
					}
				}

				String trimmedLine = StringUtil.trimLeading(line);

				if (!trimmedLine.startsWith(StringPool.DOUBLE_SLASH) &&
					!trimmedLine.startsWith(StringPool.STAR)) {

					if (line.contains(StringPool.TAB + StringPool.SPACE) &&
						!previousLine.matches(".*[&|^]") &&
						!previousLine.contains(StringPool.TAB + "((") &&
						!previousLine.contains(
							StringPool.TAB + StringPool.LESS_THAN) &&
						!previousLine.contains(
							StringPool.TAB + StringPool.SPACE) &&
						!previousLine.contains(StringPool.TAB + "for (") &&
						!previousLine.contains(
							StringPool.TAB + "implements ") &&
						!previousLine.contains(StringPool.TAB + "throws ")) {

						line = StringUtil.replace(
							line, StringPool.TAB + StringPool.SPACE,
							StringPool.TAB);
					}

					line = formatIncorrectSyntax(line, ",}", "}", false);

					line = formatWhitespace(line, trimmedLine, true);
				}

				// LPS-42599

				if (!isExcludedPath(
						_HIBERNATE_SQL_QUERY_EXCLUDES, absolutePath) &&
					line.contains("= session.createSQLQuery(") &&
					content.contains(
						"com.liferay.portal.kernel.dao.orm.Session")) {

					line = StringUtil.replace(
						line, "createSQLQuery", "createSynchronizedSQLQuery");
				}

				line = replacePrimitiveWrapperInstantiation(line);

				// LPS-45649

				if (trimmedLine.startsWith("throw new IOException(") &&
					line.contains("e.getMessage()")) {

					line = StringUtil.replace(
						line, ".getMessage()", StringPool.BLANK);
				}

				// LPS-45492

				if (trimmedLine.contains("StopWatch stopWatch = null;")) {
					processMessage(
						fileName, "Do not set stopwatch to null, see LPS-45492",
						lineCount);
				}

				checkEmptyCollection(trimmedLine, fileName, lineCount);

				line = formatEmptyArray(line);

				// LPS-58529

				checkResourceUtil(line, fileName, absolutePath, lineCount);

				if (trimmedLine.startsWith("* @see ") &&
					(StringUtil.count(trimmedLine, CharPool.AT) > 1)) {

					processMessage(
						fileName, "Do not use @see with another annotation",
						lineCount);
				}

				checkInefficientStringMethods(
					line, fileName, absolutePath, lineCount, true);

				if (line.contains("ActionForm form")) {
					processMessage(
						fileName, "Rename 'form' to 'actionForm'", lineCount);
				}

				if (line.contains("ActionMapping mapping")) {
					processMessage(
						fileName, "Rename 'mapping' to 'ActionMapping'",
						lineCount);
				}

				if (!trimmedLine.equals("{") && line.endsWith("{") &&
					!line.endsWith(" {")) {

					line = StringUtil.replaceLast(line, "{", " {");
				}

				int lineLeadingTabCount = getLeadingTabCount(line);
				int previousLineLeadingTabCount = getLeadingTabCount(
					previousLine);

				if (!trimmedLine.startsWith(StringPool.DOUBLE_SLASH) &&
					!trimmedLine.startsWith(StringPool.STAR)) {

					String strippedQuotesLine = stripQuotes(trimmedLine);

					String indent = StringPool.BLANK;

					if (!trimmedLine.startsWith(StringPool.CLOSE_CURLY_BRACE) &&
						strippedQuotesLine.contains(
							StringPool.CLOSE_CURLY_BRACE)) {

						if ((getLevel(strippedQuotesLine, "{", "}") < 0) &&
							(lineLeadingTabCount > 0)) {

							for (int i = 0; i < lineLeadingTabCount - 1; i++) {
								indent += StringPool.TAB;
							}

							int x = line.lastIndexOf(
								CharPool.CLOSE_CURLY_BRACE);

							return StringUtil.replace(
								content, "\n" + line + "\n",
								"\n" + line.substring(0, x) + "\n" + indent +
									line.substring(x) + "\n");
						}
					}

					if (!previousLine.contains("\tthrows ") &&
						!previousLine.contains(" throws ") &&
						(previousLineLeadingTabCount ==
							(lineLeadingTabCount - 1))) {

						int x = -1;

						while (true) {
							x = previousLine.indexOf(", ", x + 1);

							if (x == -1) {
								break;
							}

							if (ToolsUtil.isInsideQuotes(previousLine, x)) {
								continue;
							}

							String linePart = previousLine.substring(0, x);

							linePart = stripQuotes(linePart);

							if ((getLevel(linePart, "(", ")") != 0) ||
								(getLevel(linePart, "<", ">") != 0)) {

								continue;
							}

							linePart = previousLine.substring(x);

							linePart = stripQuotes(linePart, CharPool.QUOTE);

							if ((getLevel(linePart, "(", ")") != 0) ||
								(getLevel(linePart, "<", ">") != 0)) {

								continue;
							}

							if (Validator.isNull(indent)) {
								for (int i = 0; i < lineLeadingTabCount - 1;
										i++) {

									indent += StringPool.TAB;
								}
							}

							return StringUtil.replace(
								content, "\n" + previousLine + "\n",
								"\n" + previousLine.substring(0, x + 1) + "\n" +
									indent + previousLine.substring(x + 2) +
										"\n");
						}
					}
				}

				if (line.contains(StringPool.FOUR_SPACES) &&
					!line.matches("\\s*\\*.*")) {

					if (!fileName.endsWith("StringPool.java")) {
						processMessage(
							fileName, "Use tabs instead of spaces", lineCount);
					}
				}

				if (line.contains("  {") && !line.matches("\\s*\\*.*")) {
					processMessage(fileName, "{", lineCount);
				}

				if (!trimmedLine.startsWith("//") &&
					((lineLeadingTabCount - 2) ==
						previousLineLeadingTabCount) &&
					(previousLineLeadingTabCount > 0) &&
					line.endsWith(StringPool.SEMICOLON) &&
					!previousLine.contains("\tfor (") &&
					!previousLine.contains("\ttry (")) {

					line = StringUtil.replaceFirst(
						line, StringPool.TAB, StringPool.BLANK);
				}

				if (lineCount > 1) {
					sb.append(previousLine);
					sb.append("\n");

					if (addExtraEmptyLine(previousLine, line, true)) {
						sb.append("\n");
					}
				}

				previousLine = line;
			}

			sb.append(previousLine);
		}

		String newContent = sb.toString();

		if (newContent.endsWith("\n")) {
			newContent = newContent.substring(0, newContent.length() - 1);
		}

		return newContent;
	}

	protected String formatModulesFile(
			String fileName, String absolutePath, String className,
			String packagePath, String content)
		throws Exception {

		// LPS-56706 and LPS-57722

		if (fileName.endsWith("Test.java")) {
			if (absolutePath.contains("/src/testIntegration/java/") ||
				absolutePath.contains("/test/integration/")) {

				if (content.contains("@RunWith(Arquillian.class)") &&
					content.contains("import org.powermock.")) {

					processMessage(
						fileName,
						"Do not use PowerMock inside Arquillian tests, see " +
							"LPS-56706");
				}

				if (!packagePath.endsWith(".test")) {
					processMessage(
						fileName,
						"Module integration test must be under a test " +
							"subpackage, see LPS-57722");
				}
			}
			else if ((absolutePath.contains("/test/unit/") ||
					  absolutePath.contains("/src/test/java/")) &&
					 packagePath.endsWith(".test")) {

				processMessage(
					fileName,
					"Module unit test should not be under a test subpackage, " +
						"see LPS-57722");
			}
		}

		// LPS-57358

		if (content.contains(
			"ServiceProxyFactory.newServiceTrackedInstance(")) {

			processMessage(
				fileName,
				"Do not use ServiceProxyFactory.newServiceTrackedInstance in " +
					"modules, see LPS-57358");
		}

		// LPS-59076

		if (content.contains("@Component")) {
			content = formatOSGIComponents(
				fileName, absolutePath, content, className, packagePath);
		}

		// LPS-62989

		if (!absolutePath.contains("/modules/core/jaxws-osgi-bridge") &&
			!absolutePath.contains("/modules/core/portal-bootstrap") &&
			!absolutePath.contains("/modules/core/registry-") &&
			!absolutePath.contains("/modules/core/slim-runtime") &&
			(_checkRegistryInTestClasses ||
			 (!absolutePath.contains("/test/") &&
			  !absolutePath.contains("/testIntegration/")))) {

			Matcher matcher = _registryImportPattern.matcher(content);

			if (matcher.find()) {
				processMessage(
					fileName,
					"Do not use com.liferay.registry classes in modules, " +
						"see LPS-62989");
			}
		}

		// LPS-60186

		if (!absolutePath.contains("/test/") && content.contains("@Meta.OCD") &&
			!content.contains("@ExtendedObjectClassDefinition")) {

			processMessage(
				fileName,
				"Specify category using @ExtendedObjectClassDefinition, see " +
					"LPS-60186");
		}

		// LPS-64238

		if (content.contains("import com.liferay.util.dao.orm.CustomSQLUtil")) {
			processMessage(
				fileName,
				"Do not use com.liferay.util.dao.orm.CustomSQLUtil in " +
					"modules, see LPS-64238");
		}

		// LPS-64335

		if (content.contains("import com.liferay.util.ContentUtil")) {
			processMessage(
				fileName,
				"Do not use com.liferay.util.ContentUtil in modules, see " +
					"LPS-64335");
		}

		// LPS-67042

		checkInternalImports(fileName, absolutePath, content);

		return content;
	}

	protected String formatOSGIComponents(
			String fileName, String absolutePath, String content,
			String className, String packagePath)
		throws Exception {

		String moduleServicePackagePath = null;

		Matcher matcher = _serviceUtilImportPattern.matcher(content);

		while (matcher.find()) {
			String serviceUtilClassName = matcher.group(2);

			if (moduleServicePackagePath == null) {
				moduleServicePackagePath = getModuleServicePackagePath(
					fileName);
			}

			if (Validator.isNotNull(moduleServicePackagePath)) {
				String serviceUtilClassPackagePath = matcher.group(1);

				if (serviceUtilClassPackagePath.startsWith(
						moduleServicePackagePath)) {

					continue;
				}
			}

			processMessage(
				fileName,
				"Use @Reference instead of calling " + serviceUtilClassName +
					" directly, see LPS-59076");
		}

		matcher = _referenceMethodPattern.matcher(content);

		while (matcher.find()) {
			String methodName = matcher.group(4);

			if (!methodName.startsWith("set")) {
				continue;
			}

			String annotationParameters = matcher.group(1);

			if (!annotationParameters.contains("unbind =")) {
				if (!content.contains("un" + methodName + "(")) {
					if (Validator.isNull(annotationParameters)) {
						return StringUtil.insert(
							content, "(unbind = \"-\")", matcher.start(1));
					}

					if (!annotationParameters.contains(StringPool.NEW_LINE)) {
						return StringUtil.insert(
							content, ", unbind = \"-\"", matcher.end(1) - 1);
					}

					if (!annotationParameters.contains("\n\n")) {
						String indent = "\t\t";

						int x = content.lastIndexOf("\n", matcher.end(1) - 1);

						return StringUtil.replaceFirst(
							content, "\n",
							",\n" + indent + "unbind = \"-\"" + "\n", x - 1);
					}
				}
			}

			String methodContent = matcher.group(6);

			Matcher referenceMethodContentMatcher =
				_referenceMethodContentPattern.matcher(methodContent);

			if (!referenceMethodContentMatcher.find()) {
				continue;
			}

			String typeName = matcher.group(5);
			String variableName = referenceMethodContentMatcher.group(1);

			StringBundler sb = new StringBundler(5);

			sb.append("private volatile ");
			sb.append(typeName);
			sb.append("\\s+");
			sb.append(variableName);
			sb.append(StringPool.SEMICOLON);

			Pattern privateVarPattern = Pattern.compile(sb.toString());

			Matcher privateVarMatcher = privateVarPattern.matcher(content);

			if (privateVarMatcher.find()) {
				String match = privateVarMatcher.group();

				String replacement = StringUtil.replace(
					match, "private volatile ", "private ");

				return StringUtil.replace(content, match, replacement);
			}
		}

		return formatDuplicateReferenceMethods(
			fileName, content, className, packagePath);
	}

	protected String formatValidatorEquals(String content) {
		Matcher matcher = validatorEqualsPattern.matcher(content);

		if (!matcher.find()) {
			return content;
		}

		content = StringUtil.replaceFirst(
			content, "Validator.equals(", "Objects.equals(");

		if (content.contains("import java.util.Objects;")) {
			return content;
		}

		int pos = content.indexOf("\npackage ");

		pos = content.indexOf("\n", pos + 1);

		return StringUtil.insert(
			content, "import java.util.Objects;\n", pos + 1);
	}

	@Override
	protected List<FileCheck> getFileChecks() {
		List<FileCheck> fileChecks = new ArrayList<>();

		fileChecks.add(
			new JavaCombineLinesCheck(
				_fitOnSingleLineExcludes, sourceFormatterArgs));
		fileChecks.add(new JavaDiamondOperatorCheck(_diamondOperatorExcludes));
		fileChecks.add(new JavaEmptyLinesCheck());
		fileChecks.add(new JavaIfStatementCheck(sourceFormatterArgs));
		fileChecks.add(new JavaLineBreakCheck(sourceFormatterArgs));
		fileChecks.add(new JavaLogLevelCheck());
		fileChecks.add(
			new JavaLongLinesCheck(_lineLengthExcludes, sourceFormatterArgs));
		fileChecks.add(new JavaPackagePathCheck());

		if (portalSource || subrepository) {
			fileChecks.add(
				new JavaVerifyUpgradeConnectionCheck(
					_upgradeDataAccessConnectionExcludes));
			fileChecks.add(
				new JavaUpgradeClassCheck(_upgradeServiceUtilExcludes));
			fileChecks.add(
				new JavaXMLSecurityCheck(
					_runOutsidePortalExcludes, _secureXMLExcludes));
		}

		return fileChecks;
	}

	protected String getFormattedClassLine(String indent, String classLine) {
		while (classLine.contains(StringPool.TAB + StringPool.SPACE)) {
			classLine = StringUtil.replace(
				classLine, StringPool.TAB + StringPool.SPACE, StringPool.TAB);
		}

		String classSingleLine = StringUtil.replace(
			classLine.substring(1),
			new String[] {StringPool.TAB, StringPool.NEW_LINE},
			new String[] {StringPool.BLANK, StringPool.SPACE});

		classSingleLine = indent + classSingleLine;

		List<String> lines = new ArrayList<>();

		outerWhile:
		while (true) {
			if (getLineLength(classSingleLine) <= _maxLineLength) {
				lines.add(classSingleLine);

				break;
			}

			String newIndent = indent;
			String newLine = classSingleLine;

			int x = -1;

			while (true) {
				int y = newLine.indexOf(" extends ", x + 1);

				if (y == -1) {
					x = newLine.indexOf(" implements ", x + 1);
				}
				else {
					x = y;
				}

				if (x == -1) {
					break;
				}

				String linePart = newLine.substring(0, x);

				if ((getLevel(linePart, "<", ">") == 0) &&
					(getLineLength(linePart) <= _maxLineLength)) {

					if (lines.isEmpty()) {
						newIndent = newIndent + StringPool.TAB;
					}

					lines.add(linePart);

					newLine = newIndent + newLine.substring(x + 1);

					if (getLineLength(newLine) <= _maxLineLength) {
						lines.add(newLine);

						break outerWhile;
					}

					x = -1;
				}
			}

			if (lines.isEmpty()) {
				return null;
			}

			x = newLine.length();

			while (true) {
				x = newLine.lastIndexOf(", ", x - 1);

				if (x == -1) {
					return null;
				}

				String linePart = newLine.substring(0, x + 1);

				if ((getLevel(linePart, "<", ">") == 0) &&
					(getLineLength(linePart) <= _maxLineLength)) {

					lines.add(linePart);

					if (linePart.contains("\textends")) {
						newIndent = newIndent + "\t\t";
					}
					else if (linePart.contains("\timplements")) {
						newIndent = newIndent + "\t\t   ";
					}

					newLine = newIndent + newLine.substring(x + 2);

					if (getLineLength(newLine) <= _maxLineLength) {
						lines.add(newLine);

						break outerWhile;
					}

					x = newLine.length();
				}
			}
		}

		String formattedClassLine = null;

		for (String line : lines) {
			if (formattedClassLine == null) {
				formattedClassLine = "\n" + line;
			}
			else {
				formattedClassLine = formattedClassLine + "\n" + line;
			}
		}

		return formattedClassLine;
	}

	protected String getModuleClassContent(String fullClassName)
		throws Exception {

		String classContent = _moduleFileContentsMap.get(fullClassName);

		if (classContent != null) {
			return classContent;
		}

		Map<String, String> moduleFileNamesMap = getModuleFileNamesMap();

		String moduleFileName = moduleFileNamesMap.get(fullClassName);

		if (moduleFileName == null) {
			_moduleFileContentsMap.put(fullClassName, StringPool.BLANK);

			return StringPool.BLANK;
		}

		File file = new File(moduleFileName);

		classContent = FileUtil.read(file);

		if (classContent != null) {
			_moduleFileContentsMap.put(fullClassName, classContent);
		}

		return classContent;
	}

	protected Map<String, String> getModuleFileNamesMap() throws Exception {
		if (_moduleFileNamesMap != null) {
			return _moduleFileNamesMap;
		}

		Map<String, String> moduleFileNamesMap = new HashMap<>();

		List<String> fileNames = new ArrayList<>();

		String moduleRootDirLocation = "modules/";

		for (int i = 0; i < 6; i++) {
			File file = new File(
				sourceFormatterArgs.getBaseDirName() + moduleRootDirLocation);

			if (file.exists()) {
				fileNames = getFileNames(
					sourceFormatterArgs.getBaseDirName() +
						moduleRootDirLocation,
					null, new String[0], getIncludes());

				break;
			}

			moduleRootDirLocation = "../" + moduleRootDirLocation;
		}

		for (String fileName : fileNames) {
			fileName = StringUtil.replace(
				fileName, CharPool.BACK_SLASH, CharPool.SLASH);

			String className = StringUtil.replace(
				fileName, CharPool.SLASH, CharPool.PERIOD);

			int pos = className.lastIndexOf(".com.liferay.");

			className = className.substring(pos + 1, fileName.length() - 5);

			moduleFileNamesMap.put(className, fileName);
		}

		_moduleFileNamesMap = moduleFileNamesMap;

		return _moduleFileNamesMap;
	}

	protected String getModuleServicePackagePath(String fileName) {
		String serviceDirLocation = fileName;

		while (true) {
			int pos = serviceDirLocation.lastIndexOf(StringPool.SLASH);

			if (pos == -1) {
				return StringPool.BLANK;
			}

			serviceDirLocation = serviceDirLocation.substring(0, pos + 1);

			File file = new File(serviceDirLocation + "service");

			if (file.exists()) {
				serviceDirLocation = serviceDirLocation + "service";

				break;
			}

			file = new File(serviceDirLocation + "liferay");

			if (file.exists()) {
				return StringPool.BLANK;
			}

			serviceDirLocation = StringUtil.replaceLast(
				serviceDirLocation, StringPool.SLASH, StringPool.BLANK);
		}

		serviceDirLocation = StringUtil.replace(
			serviceDirLocation, StringPool.SLASH, StringPool.PERIOD);

		int pos = serviceDirLocation.lastIndexOf(".com.");

		return serviceDirLocation.substring(pos + 1);
	}

	protected String getModuleSuperClassContent(
			String content, String className, String packagePath)
		throws Exception {

		Pattern pattern = Pattern.compile(
			" class " + className + "\\s+extends\\s+([\\w.]+) ");

		Matcher matcher = pattern.matcher(content);

		if (!matcher.find()) {
			return null;
		}

		String superClassName = matcher.group(1);

		if (superClassName.contains(StringPool.PERIOD)) {
			if (!superClassName.startsWith("com.liferay")) {
				return null;
			}

			return getModuleClassContent(superClassName);
		}

		String superClassPackagePath = packagePath;

		pattern = Pattern.compile("\nimport (.+?)\\." + superClassName + ";");

		matcher = pattern.matcher(content);

		if (matcher.find()) {
			superClassPackagePath = matcher.group(1);
		}

		if (!superClassPackagePath.startsWith("com.liferay")) {
			return null;
		}

		String superClassFullClassName =
			superClassPackagePath + StringPool.PERIOD + superClassName;

		return getModuleClassContent(superClassFullClassName);
	}

	protected String[] getPluginExcludes(String pluginDirectoryName) {
		return new String[] {
			pluginDirectoryName + "**/model/*Clp.java",
			pluginDirectoryName + "**/model/impl/*BaseImpl.java",
			pluginDirectoryName + "**/model/impl/*Model.java",
			pluginDirectoryName + "**/model/impl/*ModelImpl.java",
			pluginDirectoryName + "**/service/**/service/*Service.java",
			pluginDirectoryName + "**/service/**/service/*ServiceClp.java",
			pluginDirectoryName + "**/service/**/service/*ServiceFactory.java",
			pluginDirectoryName + "**/service/**/service/*ServiceUtil.java",
			pluginDirectoryName + "**/service/**/service/*ServiceWrapper.java",
			pluginDirectoryName + "**/service/**/service/ClpSerializer.java",
			pluginDirectoryName +
				"**/service/**/service/messaging/*ClpMessageListener.java",
			pluginDirectoryName +
				"**/service/**/service/persistence/*Finder.java",
			pluginDirectoryName +
				"**/service/**/service/persistence/*Util.java",
			pluginDirectoryName + "**/service/base/*ServiceBaseImpl.java",
			pluginDirectoryName + "**/service/base/*ServiceClpInvoker.java",
			pluginDirectoryName + "**/service/http/*JSONSerializer.java",
			pluginDirectoryName + "**/service/http/*ServiceHttp.java",
			pluginDirectoryName + "**/service/http/*ServiceJSON.java",
			pluginDirectoryName + "**/service/http/*ServiceSoap.java",
			pluginDirectoryName + "**/tools/templates/**"
		};
	}

	protected Collection<String> getPluginJavaFiles(String[] includes)
		throws Exception {

		Collection<String> fileNames = new TreeSet<>();

		String[] excludes = getPluginExcludes(StringPool.BLANK);

		fileNames.addAll(getFileNames(excludes, includes));

		return fileNames;
	}

	protected String getPortalCustomSQLContent() throws Exception {
		if (_portalCustomSQLContent != null) {
			return _portalCustomSQLContent;
		}

		File portalCustomSQLFile = getFile(
			"portal-impl/src/custom-sql/default.xml", PORTAL_MAX_DIR_LEVEL);

		String portalCustomSQLContent = FileUtil.read(portalCustomSQLFile);

		Matcher matcher = _customSQLFilePattern.matcher(portalCustomSQLContent);

		while (matcher.find()) {
			File customSQLFile = getFile(
				"portal-impl/src/" + matcher.group(1), PORTAL_MAX_DIR_LEVEL);

			if (customSQLFile != null) {
				portalCustomSQLContent += FileUtil.read(customSQLFile);
			}
		}

		_portalCustomSQLContent = portalCustomSQLContent;

		return _portalCustomSQLContent;
	}

	protected Collection<String> getPortalJavaFiles(String[] includes)
		throws Exception {

		Collection<String> fileNames = new TreeSet<>();

		String[] excludes = new String[] {
			"**/*_IW.java", "**/counter/service/**", "**/jsp/*",
			"**/model/impl/*Model.java", "**/model/impl/*ModelImpl.java",
			"**/portal/service/**", "**/portal-client/**",
			"**/portal-web/test/**/*Test.java", "**/test/*-generated/**"
		};

		for (String directoryName : getPluginsInsideModulesDirectoryNames()) {
			excludes = ArrayUtil.append(
				excludes, getPluginExcludes("**" + directoryName));
		}

		fileNames.addAll(getFileNames(excludes, includes));

		excludes = new String[] {
			"**/portal-client/**", "**/tools/ext_tmpl/**", "**/*_IW.java",
			"**/test/**/*PersistenceTest.java"
		};
		includes = new String[] {
			"**/com/liferay/portal/kernel/service/ServiceContext*.java",
			"**/model/BaseModel.java", "**/model/impl/BaseModelImpl.java",
			"**/portal-test/**/portal/service/**/*.java",
			"**/portal-test-integration/**/portal/service/**/*.java",
			"**/service/Base*.java",
			"**/service/PersistedModelLocalService*.java",
			"**/service/configuration/**/*.java",
			"**/service/http/*HttpTest.java", "**/service/http/*SoapTest.java",
			"**/service/http/TunnelUtil.java", "**/service/impl/*.java",
			"**/service/jms/*.java", "**/service/permission/*.java",
			"**/service/persistence/BasePersistence.java",
			"**/service/persistence/BatchSession*.java",
			"**/service/persistence/*FinderImpl.java",
			"**/service/persistence/*Query.java",
			"**/service/persistence/impl/*.java",
			"**/portal-impl/test/**/*.java", "**/util-bridges/**/*.java"
		};

		fileNames.addAll(getFileNames(excludes, includes));

		return fileNames;
	}

	protected List<File> getSuppressionsFiles() throws Exception {
		String fileName = "checkstyle-suppressions.xml";

		List<File> suppressionsFiles = new ArrayList<>();

		// Find suppressions file in portal-impl/src/

		if (portalSource) {
			File suppressionsFile = getFile(
				"portal-impl/src/" + fileName, PORTAL_MAX_DIR_LEVEL);

			if (suppressionsFile != null) {
				suppressionsFiles.add(suppressionsFile);
			}
		}

		// Find suppressions files in any parent directory

		int maxDirLevel = PLUGINS_MAX_DIR_LEVEL;
		String parentDirName = sourceFormatterArgs.getBaseDirName();

		if (portalSource || subrepository) {
			maxDirLevel = PORTAL_MAX_DIR_LEVEL - 1;
			parentDirName += "../";
		}

		for (int i = 0; i < maxDirLevel; i++) {
			File suppressionsFile = new File(parentDirName + fileName);

			if (suppressionsFile.exists()) {
				suppressionsFiles.add(suppressionsFile);
			}

			parentDirName += "../";
		}

		if (!portalSource && !subrepository) {
			return suppressionsFiles;
		}

		// Find suppressions files in any child directory

		List<String> moduleSuppressionsFileNames = getFileNames(
			sourceFormatterArgs.getBaseDirName(), null, new String[0],
			new String[] {"**/modules/**/" + fileName});

		for (String moduleSuppressionsFileName : moduleSuppressionsFileNames) {
			moduleSuppressionsFileName = StringUtil.replace(
				moduleSuppressionsFileName, CharPool.BACK_SLASH,
				CharPool.SLASH);

			suppressionsFiles.add(new File(moduleSuppressionsFileName));
		}

		return suppressionsFiles;
	}

	protected boolean hasGeneratedTag(String content) {
		if ((content.contains("* @generated") || content.contains("$ANTLR")) &&
			!content.contains("hasGeneratedTag")) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	protected void postFormat() throws Exception {
		checkBndInheritAnnotationOption();
		processCheckStyle();
	}

	@Override
	protected void preFormat() {
		_maxLineLength = sourceFormatterArgs.getMaxLineLength();

		_addMissingDeprecationReleaseVersion = GetterUtil.getBoolean(
			getProperty("add.missing.deprecation.release.version"));
		_allowUseServiceUtilInServiceImpl = GetterUtil.getBoolean(
			getProperty("allow.use.service.util.in.service.impl"));

		_diamondOperatorExcludes = getExcludes(_DIAMOND_OPERATOR_EXCLUDES);
		_fitOnSingleLineExcludes = getExcludes(_FIT_ON_SINGLE_LINE_EXCLUDES);
		_lineLengthExcludes = getExcludes(_LINE_LENGTH_EXCLUDES);
		_runOutsidePortalExcludes = getExcludes(RUN_OUTSIDE_PORTAL_EXCLUDES);
		_secureXMLExcludes = getExcludes(_SECURE_XML_EXCLUDES);
		_upgradeDataAccessConnectionExcludes = getExcludes(
			_UPGRADE_DATA_ACCESS_CONNECTION_EXCLUDES);
		_upgradeServiceUtilExcludes = getExcludes(
			_UPGRADE_SERVICE_UTIL_EXCLUDES);
	}

	protected void processCheckStyle() throws Exception {
		if (_ungeneratedFiles.isEmpty()) {
			return;
		}

		Set<SourceFormatterMessage> sourceFormatterMessages =
			CheckStyleUtil.process(
				_ungeneratedFiles, getSuppressionsFiles(),
				sourceFormatterArgs.getBaseDirName());

		for (SourceFormatterMessage sourceFormatterMessage :
				sourceFormatterMessages) {

			processMessage(
				sourceFormatterMessage.getFileName(),
				sourceFormatterMessage.getMessage(),
				sourceFormatterMessage.getLineCount());

			printError(
				sourceFormatterMessage.getFileName(),
				sourceFormatterMessage.toString());
		}
	}

	protected void setBNDInheritRequiredValue(
			String fileName, boolean bndInheritRequired)
		throws Exception {

		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return;
		}

		if (bndInheritRequired) {
			bndSettings.setInheritRequired(bndInheritRequired);
		}

		putBNDSettings(bndSettings);
	}

	protected String sortExceptions(String content) {
		Matcher matcher = _throwsExceptionsPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			String exceptions = matcher.group(1);

			exceptions = StringUtil.replace(
				exceptions, new String[] {StringPool.TAB, StringPool.NEW_LINE},
				new String[] {StringPool.SPACE, StringPool.SPACE});

			String previousException = StringPool.BLANK;

			for (String exception :
					StringUtil.split(exceptions, StringPool.COMMA_AND_SPACE)) {

				exception = StringUtil.trim(exception);

				if (Validator.isNotNull(previousException) &&
					(previousException.compareToIgnoreCase(exception) > 0)) {

					String replacement = match.replaceAll(
						"(\\W)" + exception + "(\\W)",
						"$1" + previousException + "$2");

					replacement = replacement.replaceFirst(
						"(\\W)" + previousException + "(\\W)",
						"$1" + exception + "$2");

					return sortExceptions(
						StringUtil.replace(content, match, replacement));
				}

				previousException = exception;
			}
		}

		return content;
	}

	private static final String _CHECK_JAVA_FIELD_TYPES_EXCLUDES =
		"check.java.field.types.excludes";

	private static final String _DIAMOND_OPERATOR_EXCLUDES =
		"diamond.operator.excludes";

	private static final String _FIT_ON_SINGLE_LINE_EXCLUDES =
		"fit.on.single.line.excludes";

	private static final String _HIBERNATE_SQL_QUERY_EXCLUDES =
		"hibernate.sql.query.excludes";

	private static final String[] _INCLUDES = new String[] {"**/*.java"};

	private static final String _JAVATERM_SORT_EXCLUDES =
		"javaterm.sort.excludes";

	private static final String _LINE_LENGTH_EXCLUDES = "line.length.excludes";

	private static final String _PROXY_EXCLUDES = "proxy.excludes";

	private static final String _SECURE_DESERIALIZATION_EXCLUDES =
		"secure.deserialization.excludes";

	private static final String _SECURE_RANDOM_EXCLUDES =
		"secure.random.excludes";

	private static final String _SECURE_XML_EXCLUDES = "secure.xml.excludes";

	private static final String _STATIC_LOG_EXCLUDES = "static.log.excludes";

	private static final String _TEST_ANNOTATIONS_EXCLUDES =
		"test.annotations.excludes";

	private static final String _UPGRADE_DATA_ACCESS_CONNECTION_EXCLUDES =
		"upgrade.data.access.connection.excludes";

	private static final String _UPGRADE_SERVICE_UTIL_EXCLUDES =
		"upgrade.service.util.excludes";

	private static final Pattern _annotationPattern = Pattern.compile(
		"(\t*)@(.+)\\(\n([\\s\\S]*?)\\)\n");

	private boolean _addMissingDeprecationReleaseVersion;
	private boolean _allowUseServiceUtilInServiceImpl;
	private final Pattern _annotationLineBreakPattern1 = Pattern.compile(
		"[{=]\n.*(\" \\+\n\t*\")");
	private final Pattern _annotationLineBreakPattern2 = Pattern.compile(
		"=(\n\t*)\"");
	private final Pattern _annotationMetaTypePattern = Pattern.compile(
		"[\\s\\(](name|description) = \"%");
	private final Pattern _anonymousClassPattern = Pattern.compile(
		"\n(\t+)(\\S.* )?new (.|\\(\n)*\\) \\{\n\n");
	private final Pattern _arrayPattern = Pattern.compile(
		"(\n\t*.* =) (new \\w*\\[\\] \\{)\n(\t*)(.+)\n\t*(\\};)\n");
	private final Pattern _assertEqualsPattern = Pattern.compile(
		"Assert\\.assertEquals\\((.*?)\\);\n", Pattern.DOTALL);
	private final Pattern _catchExceptionPattern = Pattern.compile(
		"\n(\t+)catch \\((.+Exception) (.+)\\) \\{\n");
	private boolean _checkRegistryInTestClasses;
	private final Pattern _classPattern = Pattern.compile(
		"(\n(\t*)(private|protected|public) ((abstract|static) )*" +
			"(class|enum|interface) ([\\s\\S]*?) \\{)\n(\\s*)(\\S)");
	private final Pattern _customSQLFilePattern = Pattern.compile(
		"<sql file=\"(.*)\" \\/>");
	private final Pattern _deprecatedPattern = Pattern.compile(
		"(\n\\s*\\* @deprecated)( As of ([0-9\\.]+)(.*?)\n\\s*\\*( @|/))?",
		Pattern.DOTALL);
	private List<String> _diamondOperatorExcludes;
	private final Pattern _fetchByPrimaryKeysMethodPattern = Pattern.compile(
		"@Override\n\tpublic Map<(.+)> fetchByPrimaryKeys\\(");
	private List<String> _fitOnSingleLineExcludes;
	private final Pattern _incorrectSynchronizedPattern = Pattern.compile(
		"([\n\t])(synchronized) (private|public|protected)");
	private final Pattern _internalImportPattern = Pattern.compile(
		"\nimport com\\.liferay\\.(.*\\.internal\\.([a-z].*?\\.)?[A-Z].*?)" +
			"[\\.|;]");
	private final Pattern[] _javaSerializationVulnerabilityPatterns =
		new Pattern[] {
			Pattern.compile(
				".*(new [a-z\\.\\s]*ObjectInputStream).*", Pattern.DOTALL),
			Pattern.compile(
				".*(extends [a-z\\.\\s]*ObjectInputStream).*", Pattern.DOTALL)
	};
	private List<String> _lineLengthExcludes;
	private final Pattern _logPattern = Pattern.compile(
		"\n\tprivate static final Log _log = LogFactoryUtil.getLog\\(\n*" +
			"\t*(.+)\\.class\\)");
	private final Pattern _lowerCaseNumberOrPeriodPattern = Pattern.compile(
		"[a-z0-9.]");
	private int _maxLineLength;
	private final Map<String, String> _moduleFileContentsMap =
		new ConcurrentHashMap<>();
	private Map<String, String> _moduleFileNamesMap;
	private final Pattern _packagePattern = Pattern.compile(
		"(\n|^)\\s*package (.*);\n");
	private String _portalCustomSQLContent;
	private final Pattern _processCallablePattern = Pattern.compile(
		"implements ProcessCallable\\b");
	private final Pattern _referenceMethodContentPattern = Pattern.compile(
		"^(\\w+) =\\s+\\w+;$");
	private final Pattern _referenceMethodPattern = Pattern.compile(
		"\n\t@Reference([\\s\\S]*?)\\s+((protected|public) void (\\w+?))\\(" +
			"\\s*([ ,<>\\w]+)\\s+\\w+\\) \\{\\s+([\\s\\S]*?)\\s*?\n\t\\}\n");
	private final Pattern _registryImportPattern = Pattern.compile(
		"\nimport (com\\.liferay\\.registry\\..+);");
	private List<String> _runOutsidePortalExcludes;
	private List<String> _secureXMLExcludes;
	private final Pattern _serviceUtilImportPattern = Pattern.compile(
		"\nimport ([A-Za-z1-9\\.]*)\\.([A-Za-z1-9]*ServiceUtil);");
	private final Pattern _stagedModelTypesPattern = Pattern.compile(
		"StagedModelType\\(([a-zA-Z.]*(class|getClassName[\\(\\)]*))\\)");
	private final Pattern _throwsExceptionsPattern = Pattern.compile(
		"\\sthrows ([\\s\\w,]*)[;{]\n");
	private final Pattern _throwsSystemExceptionPattern = Pattern.compile(
		"(\n\t+.*)throws(.*) SystemException(.*)( \\{|;\n)");
	private final Set<File> _ungeneratedFiles = new CopyOnWriteArraySet<>();
	private List<String> _upgradeDataAccessConnectionExcludes;
	private List<String> _upgradeServiceUtilExcludes;

}