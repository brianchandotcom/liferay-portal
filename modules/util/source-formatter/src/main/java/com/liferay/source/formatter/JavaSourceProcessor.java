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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.JavaImportsFormatter;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.util.FileUtil;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaSource;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaSourceProcessor extends BaseSourceProcessor {

	@Override
	public String[] getIncludes() {
		return _INCLUDES;
	}

	protected static String checkAnnotationParameterProperties(
		String content, String annotation) {

		int x = annotation.indexOf("property = {");

		if (x == -1) {
			return null;
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
				return null;
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

		return null;
	}

	protected String applyDiamondOperator(String content) {
		Matcher matcher = _diamondOperatorPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();
			String whitespace = matcher.group(4);
			String parameterType = matcher.group(5);

			String replacement = StringUtil.replaceFirst(
				match, whitespace + "<" + parameterType + ">", "<>");

			content = StringUtil.replace(content, match, replacement);
		}

		return content;
	}

	protected void checkAnnotationParameters(
		String fileName, String javaTermName, String annotation) {

		int x = annotation.indexOf(CharPool.OPEN_PARENTHESIS);

		String annotationParameters = stripQuotes(
			annotation.substring(x + 1), CharPool.QUOTE);

		x = -1;
		int y = -1;

		String previousParameterName = StringPool.BLANK;

		while (true) {
			x = annotationParameters.indexOf(CharPool.EQUAL, x + 1);

			if (x == -1) {
				return;
			}

			if (Validator.isNotNull(previousParameterName)) {
				y = annotationParameters.lastIndexOf(CharPool.COMMA, x);

				if (y == -1) {
					return;
				}
			}

			String parameterName = StringUtil.trim(
				annotationParameters.substring(y + 1, x));

			if (Validator.isNull(previousParameterName) ||
				(previousParameterName.compareToIgnoreCase(parameterName) <=
					0)) {

				previousParameterName = parameterName;

				continue;
			}

			x = annotation.indexOf(CharPool.AT);
			y = annotation.indexOf(CharPool.OPEN_PARENTHESIS);

			if ((x == -1) || (x > y)) {
				return;
			}

			StringBundler sb = new StringBundler(8);

			sb.append("sort: ");

			if (Validator.isNotNull(javaTermName)) {
				sb.append(javaTermName);
				sb.append(StringPool.POUND);
			}

			String annotationName = annotation.substring(x, y);

			sb.append(annotationName);

			sb.append(StringPool.POUND);
			sb.append(parameterName);
			sb.append(StringPool.SPACE);
			sb.append(fileName);

			processErrorMessage(fileName, sb.toString());

			return;
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
			processErrorMessage(
				fileName,
				"LPS-49552: Missing override of BasePersistenceImpl." +
					"fetchByPrimaryKeys(Set<Serializable>): " + fileName);
		}
	}

	protected void checkLogLevel(String content, String fileName) {
		if (fileName.contains("Log")) {
			return;
		}

		Matcher matcher = _logLevelPattern.matcher(content);

		while (matcher.find()) {
			int pos = matcher.start();

			while (true) {
				pos = content.lastIndexOf(
					StringPool.NEW_LINE + StringPool.TAB, pos - 1);

				char c = content.charAt(pos + 2);

				if (c != CharPool.TAB) {
					break;
				}
			}

			String codeBlock = content.substring(pos, matcher.start());
			String s =
				"_log.is" + StringUtil.upperCaseFirstLetter(matcher.group(2)) +
					"Enabled()";

			if (!codeBlock.contains(s)) {
				int lineCount = StringUtil.count(
					content.substring(0, matcher.start(1)),
					StringPool.NEW_LINE);

				lineCount += 1;

				processErrorMessage(
					fileName, "Use " + s + ": " + fileName + " " + lineCount);
			}
		}
	}

	protected String checkModulesFile(
		String fileName, String absolutePath, String packagePath,
		String content) {

		// LPS-56706 and LPS-57722

		if (fileName.endsWith("Test.java")) {
			if (absolutePath.contains("/src/testIntegration/java/") ||
				absolutePath.contains("/test/integration/")) {

				if (content.contains("@RunWith(Arquillian.class)") &&
					content.contains("import org.powermock.")) {

					processErrorMessage(
						fileName,
						"Do not use PowerMock inside Arquillian tests: " +
							fileName);
				}

				if (!packagePath.endsWith(".test")) {
					processErrorMessage(
						fileName,
						"Module integration test must be under a test " +
							"subpackage" + fileName);
				}
			}
			else if ((absolutePath.contains("/test/unit/") ||
					  absolutePath.contains("/src/test/java/")) &&
					 packagePath.endsWith(".test")) {

				processErrorMessage(
					fileName,
					"Module unit test should not be under a test subpackage" +
						fileName);
			}
		}

		// LPS-57358

		if (content.contains("ProxyFactory.newServiceTrackedInstance(")) {
			processErrorMessage(
				fileName,
				"Do not use ProxyFactory.newServiceTrackedInstance in " +
					"modules: " + fileName);
		}

		// LPS-59076

		if (_checkModulesServiceUtil) {
			if (content.contains("@Component")) {
				content = checkOSGIComponents(fileName, absolutePath, content);
			}
		}

		if (!absolutePath.contains("/modules/core/") &&
			!absolutePath.contains("/test/") &&
			!absolutePath.contains("/testIntegration/") &&
			content.contains("import com.liferay.registry.Registry")) {

			processErrorMessage(
				fileName, "Do not use Registry in modules: " + fileName);
		}

		return content;
	}

	protected String checkOSGIComponents(
		String fileName, String absolutePath, String content) {

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

					processErrorMessage(
						fileName,
						"LPS-59076: Convert OSGi Component to Spring bean: " +
							fileName);

					continue;
				}
			}

			processErrorMessage(
				fileName,
				"LPS-59076: Use @Reference instead of calling " +
					serviceUtilClassName + " directly: " + fileName);
		}

		matcher = _setReferenceMethodPattern.matcher(content);

		while (matcher.find()) {
			String annotationParameters = matcher.group(2);

			if (!annotationParameters.contains("unbind =")) {
				String setMethodName = matcher.group(4);

				if (!content.contains("un" + setMethodName + "(")) {
					if (Validator.isNull(annotationParameters)) {
						return StringUtil.insert(
							content, "(unbind = \"-\")", matcher.start(2));
					}

					if (!annotationParameters.contains(StringPool.NEW_LINE)) {
						return StringUtil.insert(
							content, ", unbind = \"-\"", matcher.end(2) - 1);
					}

					if (!annotationParameters.contains("\n\n")) {
						String indent = matcher.group(1) + StringPool.TAB;

						int x = content.lastIndexOf("\n", matcher.end(2) - 1);

						return StringUtil.replaceFirst(
							content, "\n",
							",\n" + indent + "unbind = \"-\"" + "\n", x - 1);
					}
				}
			}

			String methodContent = matcher.group(6);

			Matcher referenceMethodContentMatcher =
				_setReferenceMethodContentPattern.matcher(methodContent);

			if (!referenceMethodContentMatcher.find()) {
				continue;
			}

			if (moduleServicePackagePath == null) {
				moduleServicePackagePath = getModuleServicePackagePath(
					fileName);
			}

			if (Validator.isNotNull(moduleServicePackagePath)) {
				String typeName = matcher.group(5);

				StringBundler sb = new StringBundler(5);

				sb.append("\nimport ");
				sb.append(moduleServicePackagePath);
				sb.append(".*\\.");
				sb.append(typeName);
				sb.append(StringPool.SEMICOLON);

				Pattern importPattern = Pattern.compile(sb.toString());

				Matcher importMatcher = importPattern.matcher(content);

				if (importMatcher.find()) {
					processErrorMessage(
						fileName,
						"LPS-59076: Convert OSGi Component to Spring bean: " +
							fileName);

					break;
				}
			}
		}

		return content;
	}

	protected void checkRegexPattern(
		String regexPattern, String fileName, int lineCount) {

		int i = regexPattern.indexOf("Pattern.compile(");

		if (i == -1) {
			return;
		}

		regexPattern = regexPattern.substring(i + 16);

		regexPattern = stripQuotes(regexPattern, CharPool.QUOTE);

		i = regexPattern.indexOf(CharPool.COMMA);

		if (i != -1) {
			regexPattern = regexPattern.substring(0, i);
		}
		else {
			regexPattern = StringUtil.replaceLast(
				regexPattern, ");", StringPool.BLANK);
		}

		regexPattern = StringUtil.replace(
			regexPattern, StringPool.PLUS, StringPool.BLANK);

		if (Validator.isNull(regexPattern)) {
			processErrorMessage(
				fileName,
				"create pattern as global var: " + fileName + " " + lineCount);
		}
	}

	protected void checkSystemEventAnnotations(String content, String fileName)
		throws Exception {

		if (!portalSource || !fileName.endsWith("PortletDataHandler.java")) {
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
				"import (com\\.liferay\\.[a-zA-Z\\.]*)\\.model\\." +
					className + ";");

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
				processErrorMessage(
					fileName,
					"Missing deletion system event: " +
						localServiceImplFileName);
			}
		}
	}

	protected void checkUnprocessedExceptions(
			String content, File file, String packagePath, String fileName)
		throws IOException {

		List<String> importedExceptionClassNames = null;
		JavaDocBuilder javaDocBuilder = null;

		for (int lineCount = 1;;) {
			Matcher catchExceptionMatcher = _catchExceptionPattern.matcher(
				content);

			if (!catchExceptionMatcher.find()) {
				return;
			}

			String beforeCatchCode = content.substring(
				0, catchExceptionMatcher.start());

			lineCount = lineCount + StringUtil.count(beforeCatchCode, "\n") + 1;

			String exceptionClassName = catchExceptionMatcher.group(2);
			String exceptionVariableName = catchExceptionMatcher.group(3);
			String tabs = catchExceptionMatcher.group(1);

			int pos = content.indexOf(
				"\n" + tabs + StringPool.CLOSE_CURLY_BRACE,
				catchExceptionMatcher.end() - 1);

			String insideCatchCode = content.substring(
				catchExceptionMatcher.end(), pos + 1);

			Pattern exceptionVariablePattern = Pattern.compile(
				"\\W" + exceptionVariableName + "\\W");

			Matcher exceptionVariableMatcher = exceptionVariablePattern.matcher(
				insideCatchCode);

			if (exceptionVariableMatcher.find()) {
				content = content.substring(catchExceptionMatcher.start() + 1);

				continue;
			}

			if (javaDocBuilder == null) {
				javaDocBuilder = new JavaDocBuilder();

				javaDocBuilder.addSource(file);
			}

			if (importedExceptionClassNames == null) {
				importedExceptionClassNames = getImportedExceptionClassNames(
					javaDocBuilder);
			}

			String originalExceptionClassName = exceptionClassName;

			if (!exceptionClassName.contains(StringPool.PERIOD)) {
				for (String exceptionClass : importedExceptionClassNames) {
					if (exceptionClass.endsWith(
							StringPool.PERIOD + exceptionClassName)) {

						exceptionClassName = exceptionClass;

						break;
					}
				}
			}

			if (!exceptionClassName.contains(StringPool.PERIOD)) {
				exceptionClassName =
					packagePath + StringPool.PERIOD + exceptionClassName;
			}

			com.thoughtworks.qdox.model.JavaClass exceptionClass =
				javaDocBuilder.getClassByName(exceptionClassName);

			while (true) {
				String packageName = exceptionClass.getPackageName();

				if (!packageName.contains("com.liferay")) {
					break;
				}

				exceptionClassName = exceptionClass.getName();

				if (exceptionClassName.equals("PortalException") ||
					exceptionClassName.equals("SystemException")) {

					processErrorMessage(
						fileName,
						"Unprocessed " + originalExceptionClassName + ": " +
							fileName + " " + lineCount);

					break;
				}

				com.thoughtworks.qdox.model.JavaClass exceptionSuperClass =
					exceptionClass.getSuperJavaClass();

				if (exceptionSuperClass == null) {
					break;
				}

				exceptionClass = exceptionSuperClass;
			}

			content = content.substring(catchExceptionMatcher.start() + 1);
		}
	}

	protected void checkXMLSecurity(
		String fileName, String content, boolean isRunOutsidePortalExclusion) {

		String[] xmlVulnerabitilies = new String[] {
			"DocumentBuilderFactory.newInstance",
			"new javax.xml.parsers.SAXParser",
			"new org.apache.xerces.parsers.SAXParser",
			"new org.dom4j.io.SAXReader", "new SAXParser", "new SAXReader",
			"SAXParserFactory.newInstance", "saxParserFactory.newInstance",
			"SAXParserFactory.newSAXParser", "saxParserFactory.newSAXParser",
			"XMLInputFactory.newFactory", "xmlInputFactory.newFactory",
			"XMLInputFactory.newInstance", "xmlInputFactory.newInstance"
		};

		for (String xmlVulnerabitily : xmlVulnerabitilies) {
			if (!content.contains(xmlVulnerabitily)) {
				continue;
			}

			StringBundler sb = new StringBundler(5);

			if (isRunOutsidePortalExclusion) {
				sb.append("Possible XXE or Quadratic Blowup security ");
				sb.append("vulnerablity using ");
			}
			else {
				sb.append("Use SecureXMLBuilderUtil.newDocumentBuilderFactory");
				sb.append(" instead of ");
			}

			sb.append(xmlVulnerabitily);
			sb.append(": ");
			sb.append(fileName);

			processErrorMessage(fileName, sb.toString());
		}
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		if (hasGeneratedTag(content)) {
			return content;
		}

		String className = file.getName();

		int pos = className.lastIndexOf(CharPool.PERIOD);

		className = className.substring(0, pos);

		String packagePath = ToolsUtil.getPackagePath(file);

		if (!content.contains(
				"package " + packagePath + StringPool.SEMICOLON)) {

			processErrorMessage(
				fileName, "Incorrect package path: " + fileName);
		}

		if (packagePath.endsWith(".model")) {
			if (content.contains("extends " + className + "Model")) {
				return content;
			}
		}

		String newContent = trimContent(content, false);

		if (newContent.contains("$\n */")) {
			processErrorMessage(fileName, "*: " + fileName);

			newContent = StringUtil.replace(newContent, "$\n */", "$\n *\n */");
		}

		newContent = fixCopyright(newContent, absolutePath, fileName);

		if (newContent.contains(className + ".java.html")) {
			processErrorMessage(fileName, "Java2HTML: " + fileName);
		}

		if (newContent.contains(" * @author Raymond Aug") &&
			!newContent.contains(" * @author Raymond Aug\u00e9")) {

			newContent = newContent.replaceFirst(
				"Raymond Aug.++", "Raymond Aug\u00e9");

			processErrorMessage(fileName, "UTF-8: " + fileName);
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

		newContent = JavaImportsFormatter.stripJavaImports(
			newContent, packagePath, className);

		newContent = StringUtil.replace(
			newContent, new String[] {";\n/**", "\t/*\n\t *", ";;\n"},
			new String[] {";\n\n/**", "\t/**\n\t *", ";\n"});

		while (true) {
			Matcher matcher = _incorrectLineBreakPattern1.matcher(newContent);

			if (matcher.find()) {
				newContent = StringUtil.replaceFirst(
					newContent, StringPool.NEW_LINE, StringPool.BLANK,
					matcher.start());

				continue;
			}

			matcher = _incorrectLineBreakPattern2.matcher(newContent);

			if (matcher.find()) {
				newContent = StringUtil.replaceFirst(
					newContent, StringPool.NEW_LINE, StringPool.BLANK,
					matcher.start());

				continue;
			}

			matcher = _redundantCommaPattern.matcher(newContent);

			if (matcher.find()) {
				newContent = StringUtil.replaceFirst(
					newContent, StringPool.COMMA, StringPool.BLANK,
					matcher.start());

				continue;
			}

			break;
		}

		newContent = formatAnnotations(
			fileName, StringPool.BLANK, newContent, StringPool.BLANK);

		Matcher matcher = _logPattern.matcher(newContent);

		if (matcher.find()) {
			String logClassName = matcher.group(1);

			if (!logClassName.equals(className)) {
				newContent = StringUtil.replaceLast(
					newContent, logClassName + ".class)",
					className + ".class)");
			}
		}

		if (!isExcludedFile(_staticLogVariableExclusionFiles, absolutePath)) {
			newContent = StringUtil.replace(
				newContent, "private Log _log",
				"private static final Log _log");
		}

		if (newContent.contains("*/\npackage ")) {
			processErrorMessage(fileName, "package: " + fileName);
		}

		if (portalSource && !_allowUseServiceUtilInServiceImpl &&
			!className.equals("BaseServiceImpl") &&
			className.endsWith("ServiceImpl") &&
			newContent.contains("ServiceUtil.")) {

			processErrorMessage(fileName, "ServiceUtil: " + fileName);
		}

		// LPS-34911

		if (portalSource &&
			!isExcludedFile(_upgradeServiceUtilExclusionFiles, absolutePath) &&
			fileName.contains("/portal/upgrade/") &&
			!fileName.contains("/test/") &&
			!fileName.contains("/testIntegration/") &&
			newContent.contains("ServiceUtil.")) {

			processErrorMessage(fileName, "ServiceUtil: " + fileName);
		}

		boolean isRunOutsidePortalExclusion = isExcludedPath(
			getRunOutsidePortalExclusionPaths(), absolutePath);

		if (!isRunOutsidePortalExclusion &&
			!isExcludedFile(_proxyExclusionFiles, absolutePath) &&
			newContent.contains("import java.lang.reflect.Proxy;")) {

			processErrorMessage(fileName, "Proxy: " + fileName);
		}

		if (newContent.contains("import edu.emory.mathcs.backport.java")) {
			processErrorMessage(
				fileName, "edu.emory.mathcs.backport.java: " + fileName);
		}

		if (newContent.contains("import jodd.util.StringPool")) {
			processErrorMessage(fileName, "jodd.util.StringPool: " + fileName);
		}

		// LPS-45027

		if (newContent.contains(
				"com.liferay.portal.kernel.util.UnmodifiableList")) {

			processErrorMessage(
				fileName,
				"Use java.util.Collections.unmodifiableList instead of " +
					"com.liferay.portal.kernel.util.UnmodifiableList: " +
						fileName);
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
				processErrorMessage(
					fileName, "Use getInt(1) for count: " + fileName);
			}
		}

		// LPS-33070

		matcher = _processCallablePattern.matcher(content);

		if (matcher.find() &&
			!content.contains("private static final long serialVersionUID")) {

			processErrorMessage(
				fileName,
				"Assign ProcessCallable implementation a serialVersionUID: " +
					fileName);
		}

		checkLanguageKeys(fileName, newContent, languageKeyPattern);

		newContent = StringUtil.replace(
			newContent, StringPool.TAB + "for (;;) {",
			StringPool.TAB + "while (true) {");

		// LPS-36174

		if (_checkUnprocessedExceptions && !fileName.contains("/test/") &&
			!fileName.contains("/testIntegration/")) {

			checkUnprocessedExceptions(newContent, file, packagePath, fileName);
		}

		// LPS-39508

		if (!isRunOutsidePortalExclusion &&
			!isExcludedFile(_secureRandomExclusionFiles, absolutePath) &&
			content.contains("java.security.SecureRandom") &&
			!content.contains("javax.crypto.KeyGenerator")) {

			processErrorMessage(
				fileName,
				"Use SecureRandomUtil or com.liferay.portal.kernel.security." +
					"SecureRandom instead of java.security.SecureRandom: " +
						fileName);
		}

		// LPS-41315

		checkLogLevel(newContent, fileName);

		// LPS-46632

		checkSystemEventAnnotations(newContent, fileName);

		// LPS-41205

		if (fileName.contains("/upgrade/") &&
			newContent.contains("LocaleUtil.getDefault()")) {

			processErrorMessage(
				fileName,
				"Use UpgradeProcessUtil.getDefaultLanguageId(companyId) " +
					"instead of LocaleUtil.getDefault(): " + fileName);
		}

		// LPS-46017

		newContent = StringUtil.replace(
			newContent, " static interface ", " interface ");

		// LPS-47055

		newContent = fixSystemExceptions(newContent);

		// LPS-47648

		if (portalSource &&
			(fileName.contains("/test/integration/") ||
			 fileName.contains("/testIntegration/java"))) {

			newContent = StringUtil.replace(
				newContent, "FinderCacheUtil.clearCache();", StringPool.BLANK);
		}

		// LPS-47682

		newContent = fixIncorrectParameterTypeForLanguageUtil(
			newContent, false, fileName);

		if (portalSource && fileName.contains("/portal-service/") &&
			content.contains("import javax.servlet.jsp.")) {

			processErrorMessage(
				fileName,
				"Never import javax.servlet.jsp.* from portal-service " +
					fileName);
		}

		// LPS-48153

		if (!isExcludedFile(_diamondOperatorExclusionFiles, absolutePath) &&
			!isExcludedPath(_diamondOperatorExclusionPaths, absolutePath)) {

			newContent = applyDiamondOperator(newContent);
		}

		// LPS-49552

		checkFinderCacheInterfaceMethod(fileName, newContent);

		// LPS-50479

		if (!fileName.contains("/test/") &&
			!fileName.contains("/testIntegration/") &&
			!isExcludedFile(_secureXmlExclusionFiles, absolutePath)) {

			checkXMLSecurity(fileName, content, isRunOutsidePortalExclusion);
		}

		// LPS-55690

		if (newContent.contains("org.testng.Assert")) {
			processErrorMessage(
				fileName,
				"Use org.junit.Assert instead of org.testng.Assert: " +
					fileName);
		}

		if (portalSource && isModulesFile(absolutePath)) {
			newContent = checkModulesFile(
				fileName, absolutePath, packagePath, newContent);
		}

		// LPS-48156

		newContent = checkPrincipalException(newContent);

		// LPS-60473

		if (newContent.contains(".supportsBatchUpdates()") &&
			!fileName.endsWith("AutoBatchPreparedStatementUtil.java")) {

			processErrorMessage(
				fileName,
				"Use AutoBatchPreparedStatementUtil instead of " +
					"DatabaseMetaData.supportsBatchUpdates: " + fileName);
		}

		newContent = getCombinedLinesContent(
			newContent, _combinedLinesPattern1);
		newContent = getCombinedLinesContent(
			newContent, _combinedLinesPattern2);

		newContent = formatClassLine(newContent);

		newContent = fixIncorrectEmptyLineBeforeCloseCurlyBrace(
			newContent, fileName);

		pos = newContent.indexOf("\npublic ");

		if (pos != -1) {
			String javaClassContent = newContent.substring(pos + 1);

			String beforeJavaClass = newContent.substring(0, pos + 1);

			int javaClassLineCount =
				StringUtil.count(beforeJavaClass, "\n") + 1;

			newContent = formatJavaTerms(
				className, packagePath, file, fileName, absolutePath,
				newContent, javaClassContent, javaClassLineCount,
				_checkJavaFieldTypesExclusionFiles,
				_javaTermAccessLevelModifierExclusionFiles,
				_javaTermSortExclusionFiles, _testAnnotationsExclusionFiles);
		}

		newContent = formatJava(fileName, absolutePath, newContent);

		return StringUtil.replace(newContent, "\n\n\n", "\n\n");
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		Collection<String> fileNames = null;

		if (portalSource) {
			fileNames = getPortalJavaFiles();

			_checkModulesServiceUtil = GetterUtil.getBoolean(
				System.getProperty(
					"source.formatter.check.modules.service.util"));
			_checkUnprocessedExceptions = GetterUtil.getBoolean(
				System.getProperty(
					"source.formatter.check.unprocessed.exceptions"));
		}
		else {
			fileNames = getPluginJavaFiles();
		}

		_addMissingDeprecationReleaseVersion = GetterUtil.getBoolean(
			getProperty("add.missing.deprecation.release.version"));
		_allowUseServiceUtilInServiceImpl = GetterUtil.getBoolean(
			getProperty("allow.use.service.util.in.service.impl"));
		_checkJavaFieldTypesExclusionFiles = getPropertyList(
			"check.java.field.types.excludes.files");
		_diamondOperatorExclusionFiles = getPropertyList(
			"diamond.operator.excludes.files");
		_diamondOperatorExclusionPaths = getPropertyList(
			"diamond.operator.excludes.paths");
		_fitOnSingleLineExclusionFiles = getPropertyList(
			"fit.on.single.line.excludes.files");
		_hibernateSQLQueryExclusionFiles = getPropertyList(
			"hibernate.sql.query.excludes.files");
		_javaTermAccessLevelModifierExclusionFiles = getPropertyList(
			"javaterm.access.level.modifier.excludes.files");
		_javaTermSortExclusionFiles = getPropertyList(
			"javaterm.sort.excludes.files");
		_lineLengthExclusionFiles = getPropertyList(
			"line.length.excludes.files");
		_proxyExclusionFiles = getPropertyList("proxy.excludes.files");
		_secureRandomExclusionFiles = getPropertyList(
			"secure.random.excludes.files");
		_secureXmlExclusionFiles = getPropertyList("secure.xml.excludes.files");
		_staticLogVariableExclusionFiles = getPropertyList(
			"static.log.excludes.files");
		_testAnnotationsExclusionFiles = getPropertyList(
			"test.annotations.excludes.files");
		_upgradeServiceUtilExclusionFiles = getPropertyList(
			"upgrade.service.util.excludes.files");

		return new ArrayList<>(fileNames);
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

	protected String fixIfClause(String ifClause, String line, int delta) {
		if (StringUtil.count(ifClause, line) > 1) {
			return ifClause;
		}

		String newLine = line;

		String whitespace = StringPool.BLANK;
		int whitespaceLength = Math.abs(delta);

		while (whitespaceLength > 0) {
			if (whitespaceLength >= 4) {
				whitespace += StringPool.TAB;

				whitespaceLength -= 4;
			}
			else {
				whitespace += StringPool.SPACE;

				whitespaceLength -= 1;
			}
		}

		if (delta > 0) {
			if (!line.contains(StringPool.TAB + whitespace)) {
				newLine = StringUtil.replaceLast(
					newLine, StringPool.TAB, StringPool.FOUR_SPACES);
			}

			newLine = StringUtil.replaceLast(
				newLine, StringPool.TAB + whitespace, StringPool.TAB);
		}
		else {
			newLine = StringUtil.replaceLast(
				newLine, StringPool.TAB, StringPool.TAB + whitespace);
		}

		newLine = StringUtil.replaceLast(
			newLine, StringPool.FOUR_SPACES, StringPool.TAB);

		return StringUtil.replace(ifClause, line, newLine);
	}

	protected String fixIncorrectEmptyLineBeforeCloseCurlyBrace(
		String content, String fileName) {

		Matcher matcher1 = _incorrectCloseCurlyBracePattern1.matcher(content);

		while (matcher1.find()) {
			String lastLine = StringUtil.trimLeading(matcher1.group(1));

			if (lastLine.startsWith("// ")) {
				continue;
			}

			String tabs = matcher1.group(2);
			int tabCount = tabs.length();

			int pos = matcher1.start();

			while (true) {
				pos = content.lastIndexOf("\n" + tabs, pos - 1);

				if (content.charAt(pos + tabCount + 1) == CharPool.TAB) {
					continue;
				}

				String codeBlock = content.substring(pos + 1, matcher1.end());

				String firstLine = codeBlock.substring(
					0, codeBlock.indexOf(CharPool.NEW_LINE));

				Matcher matcher2 = _incorrectCloseCurlyBracePattern2.matcher(
					firstLine);

				if (matcher2.find()) {
					break;
				}

				return StringUtil.replaceFirst(
					content, "\n\n" + tabs + "}\n", "\n" + tabs + "}\n", pos);
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
			String fileName, String javaTermName, String content, String indent)
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

			if ((StringUtil.count(line, StringPool.TAB) == indent.length()) &&
				!line.startsWith(indent + StringPool.CLOSE_PARENTHESIS)) {

				if (Validator.isNotNull(annotation) &&
					annotation.contains(StringPool.OPEN_PARENTHESIS)) {

					Matcher matcher = _annotationPattern.matcher(
						"\n" + annotation);

					if (matcher.find()) {
						String match = matcher.group();

						match = match.substring(1);

						if (!match.endsWith("\n)\n") &&
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

					if (newContent != null) {
						return formatAnnotations(
							fileName, javaTermName, newContent, indent);
					}

					checkAnnotationParameters(
						fileName, javaTermName, annotation);
				}

				if (Validator.isNotNull(previousAnnotation) &&
					(previousAnnotation.compareToIgnoreCase(annotation) > 0)) {

					content = StringUtil.replaceFirst(
						content, previousAnnotation, annotation);
					content = StringUtil.replaceLast(
						content, annotation, previousAnnotation);

					return formatAnnotations(
						fileName, javaTermName, content, indent);
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

	protected String formatClassLine(String content) {
		Matcher matcher = _classPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			match = match.substring(0, match.length() - 1);

			String formattedClassLine = getFormattedClassLine(
				matcher.group(1), match);

			if (formattedClassLine != null) {
				content = StringUtil.replace(
					content, match, formattedClassLine);
			}
		}

		return content;
	}

	protected String formatIfClause(String ifClause) throws IOException {
		String strippedQuotesIfClause = stripQuotes(ifClause, CharPool.QUOTE);

		if (strippedQuotesIfClause.contains("!(") ||
			strippedQuotesIfClause.contains("//")) {

			return ifClause;
		}

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(ifClause));

		String line = null;

		String previousLine = null;
		int previousLineLength = 0;

		int previousLineCloseParenthesesCount = 0;
		int previousLineLeadingWhitespace = 0;
		int previousLineOpenParenthesesCount = 0;

		int baseLeadingWhitespace = 0;
		int insideMethodCallExpectedWhitespace = 0;
		int level = -1;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			String originalLine = line;

			String trimmedLine = StringUtil.trimLeading(line);

			int x = getIncorrectLineBreakPos(line, previousLine);

			if (x != -1) {
				String leadingWhitespace = line.substring(
					0, line.indexOf(trimmedLine));

				return StringUtil.replace(
					ifClause, line,
					line.substring(0, x) + "\n" + leadingWhitespace +
						line.substring(x + 1));
			}

			line = StringUtil.replace(
				line, StringPool.TAB, StringPool.FOUR_SPACES);

			String strippedQuotesLine = stripQuotes(
				trimmedLine, CharPool.QUOTE);

			strippedQuotesLine = stripQuotes(
				strippedQuotesLine, CharPool.APOSTROPHE);

			int closeParenthesesCount = StringUtil.count(
				strippedQuotesLine, StringPool.CLOSE_PARENTHESIS);
			int openParenthesesCount = StringUtil.count(
				strippedQuotesLine, StringPool.OPEN_PARENTHESIS);

			if ((previousLineLength > 0) &&
				(line.endsWith("|") || line.endsWith("&&") ||
				 line.endsWith(") {")) &&
				(previousLine.endsWith("|") || previousLine.endsWith("&&")) &&
				(previousLineLength + trimmedLine.length() <
					_MAX_LINE_LENGTH) &&
				(openParenthesesCount <= closeParenthesesCount) &&
				(previousLineCloseParenthesesCount <=
					previousLineOpenParenthesesCount)) {

				return StringUtil.replace(
					ifClause, previousLine + "\n" + originalLine,
					previousLine + StringPool.SPACE + trimmedLine);
			}

			int leadingWhitespace = line.length() - trimmedLine.length();

			if (Validator.isNull(previousLine)) {
				baseLeadingWhitespace =
					line.indexOf(CharPool.OPEN_PARENTHESIS) + 1;
			}
			else if (previousLine.endsWith("|") || previousLine.endsWith("&") ||
					 previousLine.endsWith("^")) {

				int expectedLeadingWhitespace = baseLeadingWhitespace + level;

				if (leadingWhitespace != expectedLeadingWhitespace) {
					return fixIfClause(
						ifClause, originalLine,
						leadingWhitespace - expectedLeadingWhitespace);
				}
			}
			else {
				int expectedLeadingWhitespace = 0;

				if (previousLine.contains(StringPool.TAB + "else if (")) {
					expectedLeadingWhitespace = baseLeadingWhitespace + 3;
				}
				else if (previousLine.contains(StringPool.TAB + "if (")) {
					expectedLeadingWhitespace = baseLeadingWhitespace + 4;
				}
				else if (previousLine.contains(StringPool.TAB + "while (")) {
					expectedLeadingWhitespace = baseLeadingWhitespace + 5;
				}

				if (previousLine.endsWith(StringPool.COMMA) &&
					(insideMethodCallExpectedWhitespace > 0)) {

					if (previousLineCloseParenthesesCount >
							previousLineOpenParenthesesCount) {

						insideMethodCallExpectedWhitespace -= 4;
					}

					expectedLeadingWhitespace =
						insideMethodCallExpectedWhitespace;
				}
				else {
					if (expectedLeadingWhitespace == 0) {
						expectedLeadingWhitespace =
							previousLineLeadingWhitespace + 4;
					}

					if (previousLine.endsWith(StringPool.OPEN_PARENTHESIS)) {
						insideMethodCallExpectedWhitespace =
							expectedLeadingWhitespace;
					}
				}

				if (leadingWhitespace != expectedLeadingWhitespace) {
					return fixIfClause(
						ifClause, originalLine,
						leadingWhitespace - expectedLeadingWhitespace);
				}
			}

			if (line.endsWith(") {")) {
				return ifClause;
			}

			level = level + openParenthesesCount - closeParenthesesCount;

			previousLine = originalLine;
			previousLineLength = line.length();

			previousLineCloseParenthesesCount = closeParenthesesCount;
			previousLineLeadingWhitespace = leadingWhitespace;
			previousLineOpenParenthesesCount = openParenthesesCount;
		}

		return ifClause;
	}

	protected String formatIfClause(
			String ifClause, String fileName, int lineCount)
		throws IOException {

		String ifClauseSingleLine = StringUtil.replace(
			ifClause,
			new String[] {
				StringPool.TAB + StringPool.SPACE, StringPool.TAB,
				StringPool.OPEN_PARENTHESIS + StringPool.NEW_LINE,
				StringPool.NEW_LINE
			},
			new String[] {
				StringPool.TAB, StringPool.BLANK, StringPool.OPEN_PARENTHESIS,
				StringPool.SPACE
			});

		checkIfClauseParentheses(ifClauseSingleLine, fileName, lineCount);

		return formatIfClause(ifClause);
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

			String ifClause = StringPool.BLANK;
			String packageName = StringPool.BLANK;
			String regexPattern = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				if (line.startsWith("package ")) {
					packageName = line.substring(8, line.length() - 1);
				}

				if (line.startsWith("import ")) {
					if (line.endsWith(".*;")) {
						processErrorMessage(
							fileName, "import: " + fileName + " " + lineCount);
					}

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
						!previousLine.endsWith("&&") &&
						!previousLine.endsWith("|") &&
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

				if (line.contains(StringPool.TAB + "for (") &&
					line.contains(":") && !line.contains(" :")) {

					line = StringUtil.replace(line, ":", " :");
				}

				// LPS-42924

				if (line.contains("PortalUtil.getClassNameId(") &&
					fileName.endsWith("ServiceImpl.java")) {

					processErrorMessage(
						fileName,
						"Use classNameLocalService.getClassNameId: " +
							fileName + " " + lineCount);
				}

				// LPS-42599

				if (!isExcludedFile(
						_hibernateSQLQueryExclusionFiles, absolutePath) &&
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
					processErrorMessage(
						fileName,
						"Do not set stopwatch to null: " + fileName + " " +
							lineCount);
				}

				checkStringBundler(trimmedLine, fileName, lineCount);

				checkEmptyCollection(trimmedLine, fileName, lineCount);

				// LPS-58529

				checkResourceUtil(line, fileName, lineCount);

				if (trimmedLine.startsWith("* @deprecated") &&
					_addMissingDeprecationReleaseVersion) {

					if (!trimmedLine.startsWith("* @deprecated As of ")) {
						line = StringUtil.replace(
							line, "* @deprecated",
							"* @deprecated As of " + getMainReleaseVersion());
					}
					else {
						String version = trimmedLine.substring(20);

						version = StringUtil.split(
							version, StringPool.SPACE)[0];

						version = StringUtil.replace(
							version, StringPool.COMMA, StringPool.BLANK);

						if (StringUtil.count(version, StringPool.PERIOD) == 1) {
							line = StringUtil.replaceFirst(
								line, version, version + ".0");
						}
					}
				}

				if (trimmedLine.startsWith("* @see ") &&
					(StringUtil.count(trimmedLine, StringPool.AT) > 1)) {

					processErrorMessage(
						fileName,
						"Do not use @see with another annotation: " + fileName +
							" " + lineCount);
				}

				checkInefficientStringMethods(
					line, fileName, absolutePath, lineCount);

				if (trimmedLine.startsWith(StringPool.EQUAL)) {
					processErrorMessage(
						fileName, "line break: " + fileName + " " + lineCount);
				}

				if (line.contains("ActionForm form")) {
					processErrorMessage(
						fileName,
						"Rename form to actionForm: " + fileName + " " +
							lineCount);
				}

				if (line.contains("ActionMapping mapping")) {
					processErrorMessage(
						fileName,
						"Rename mapping to ActionMapping: " + fileName + " " +
							lineCount);
				}

				if (fileName.contains("/upgrade/") &&
					line.contains("rs.getDate(")) {

					processErrorMessage(
						fileName,
						"Use rs.getTimeStamp: " + fileName + " " + lineCount);
				}

				if (!trimmedLine.equals("{") && line.endsWith("{") &&
					!line.endsWith(" {")) {

					line = StringUtil.replaceLast(line, "{", " {");
				}

				line = sortExceptions(line);

				if (trimmedLine.startsWith("Pattern ") ||
					Validator.isNotNull(regexPattern)) {

					regexPattern = regexPattern + trimmedLine;

					if (trimmedLine.endsWith(");")) {

						// LPS-41084

						checkRegexPattern(regexPattern, fileName, lineCount);

						regexPattern = StringPool.BLANK;
					}
				}

				if (!trimmedLine.startsWith(StringPool.DOUBLE_SLASH) &&
					!trimmedLine.startsWith(StringPool.STAR)) {

					String strippedQuotesLine = stripQuotes(
						trimmedLine, CharPool.QUOTE);

					strippedQuotesLine = stripQuotes(
						strippedQuotesLine, CharPool.APOSTROPHE);

					if (line.endsWith(StringPool.OPEN_PARENTHESIS)) {
						if (line.contains(" && ") || line.contains(" || ")) {
							processErrorMessage(
								fileName,
								"line break: " + fileName + " " + lineCount);
						}

						int pos = strippedQuotesLine.indexOf(" + ");

						if (pos != -1) {
							String linePart = strippedQuotesLine.substring(
								0, pos);

							int closeBracketCount = StringUtil.count(
								linePart, StringPool.CLOSE_BRACKET);
							int closeParenthesisCount = StringUtil.count(
								linePart, StringPool.CLOSE_PARENTHESIS);
							int openBracketCount = StringUtil.count(
								linePart, StringPool.OPEN_BRACKET);
							int openParenthesisCount = StringUtil.count(
								linePart, StringPool.OPEN_PARENTHESIS);

							if ((openBracketCount == closeBracketCount) &&
								(openParenthesisCount ==
									closeParenthesisCount)) {

								processErrorMessage(
									fileName,
									"line break: " + fileName + " " +
										lineCount);
							}
						}
					}

					if (!trimmedLine.startsWith(StringPool.CLOSE_CURLY_BRACE) &&
						strippedQuotesLine.contains(
							StringPool.CLOSE_CURLY_BRACE)) {

						int closeCurlyBraceCount = StringUtil.count(
							strippedQuotesLine, StringPool.CLOSE_CURLY_BRACE);
						int openCurlyBraceCount = StringUtil.count(
							strippedQuotesLine, StringPool.OPEN_CURLY_BRACE);

						int leadingTabCount = getLeadingTabCount(line);

						if ((closeCurlyBraceCount > openCurlyBraceCount) &&
							(leadingTabCount > 0)) {

							String indent = StringPool.BLANK;

							for (int i = 0; i < leadingTabCount - 1; i++) {
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

					if (trimmedLine.endsWith(StringPool.PLUS) &&
						!trimmedLine.startsWith(StringPool.OPEN_PARENTHESIS)) {

						int closeParenthesisCount = StringUtil.count(
							strippedQuotesLine, StringPool.CLOSE_PARENTHESIS);
						int openParenthesisCount = StringUtil.count(
							strippedQuotesLine, StringPool.OPEN_PARENTHESIS);

						if (openParenthesisCount > closeParenthesisCount) {
							processErrorMessage(
								fileName,
								"line break: " + fileName + " " + lineCount);
						}
					}

					if (strippedQuotesLine.endsWith(StringPool.PLUS)) {
						int x = -1;

						while (true) {
							x = strippedQuotesLine.indexOf(
								StringPool.COMMA, x + 1);

							if (x == -1) {
								break;
							}

							int closeParenthesisCount = StringUtil.count(
								strippedQuotesLine.substring(x),
								StringPool.CLOSE_PARENTHESIS);
							int openParenthesisCount = StringUtil.count(
								strippedQuotesLine.substring(x),
								StringPool.OPEN_PARENTHESIS);

							if (openParenthesisCount >= closeParenthesisCount) {
								processErrorMessage(
									fileName,
									"line break: " + fileName + " " +
										lineCount);
							}
						}
					}

					if (previousLine.endsWith(StringPool.OPEN_PARENTHESIS) ||
						previousLine.endsWith(StringPool.PLUS)) {

						int x = -1;

						while (true) {
							x = strippedQuotesLine.indexOf(
								StringPool.COMMA_AND_SPACE, x + 1);

							if (x == -1) {
								break;
							}

							int closeParenthesisCount = StringUtil.count(
								strippedQuotesLine.substring(0, x),
								StringPool.CLOSE_PARENTHESIS);
							int openParenthesisCount = StringUtil.count(
								strippedQuotesLine.substring(0, x),
								StringPool.OPEN_PARENTHESIS);

							if ((previousLine.endsWith(
									StringPool.OPEN_PARENTHESIS) &&
								 (openParenthesisCount <
									 closeParenthesisCount)) ||
								(previousLine.endsWith(StringPool.PLUS) &&
								 (openParenthesisCount <=
									 closeParenthesisCount))) {

								processErrorMessage(
									fileName,
									"line break: " + fileName + " " +
										lineCount);
							}
						}
					}

					int x = strippedQuotesLine.indexOf(", ");

					if (x != -1) {
						String linePart = strippedQuotesLine.substring(0, x);

						int closeParenthesisCount = StringUtil.count(
							linePart, StringPool.CLOSE_PARENTHESIS);
						int openParenthesisCount = StringUtil.count(
							linePart, StringPool.OPEN_PARENTHESIS);

						if (closeParenthesisCount > openParenthesisCount) {
							processErrorMessage(
								fileName,
								"line break: " + fileName + " " + lineCount);
						}
					}
					else if (trimmedLine.endsWith(StringPool.COMMA) &&
							 !trimmedLine.startsWith("for (")) {

						int closeParenthesisCount = StringUtil.count(
							strippedQuotesLine, StringPool.CLOSE_PARENTHESIS);
						int openParenthesisCount = StringUtil.count(
							strippedQuotesLine, StringPool.OPEN_PARENTHESIS);

						if (closeParenthesisCount < openParenthesisCount) {
							processErrorMessage(
								fileName,
								"line break: " + fileName + " " + lineCount);
						}
					}

					if (line.contains(StringPool.COMMA) &&
						!line.contains(StringPool.CLOSE_PARENTHESIS) &&
						!line.contains(StringPool.GREATER_THAN) &&
						!line.contains(StringPool.QUOTE) &&
						line.endsWith(StringPool.OPEN_PARENTHESIS)) {

						processErrorMessage(
							fileName,
							"line break: " + fileName + " " + lineCount);
					}

					if (line.endsWith(" +") || line.endsWith(" -") ||
						line.endsWith(" *") || line.endsWith(" /")) {

						x = line.indexOf(" = ");

						if (x != -1) {
							int y = line.indexOf(CharPool.QUOTE);

							if ((y == -1) || (x < y)) {
								processErrorMessage(
									fileName,
									"line break: " + fileName + " " +
										lineCount);
							}
						}
					}

					if (line.endsWith(" throws") ||
						(previousLine.endsWith(
							StringPool.OPEN_PARENTHESIS) &&
						 line.contains(" throws " ) &&
						 line.endsWith(StringPool.OPEN_CURLY_BRACE))) {

						processErrorMessage(
							fileName,
							"line break: " + fileName + " " + lineCount);
					}

					if (trimmedLine.startsWith(StringPool.PERIOD) ||
						(line.endsWith(StringPool.PERIOD) &&
						 line.contains(StringPool.EQUAL))) {

						processErrorMessage(
							fileName,
							"line break: " + fileName + " " + lineCount);
					}

					if (trimmedLine.startsWith(StringPool.CLOSE_CURLY_BRACE) &&
						line.endsWith(StringPool.OPEN_CURLY_BRACE)) {

						Matcher matcher = _lineBreakPattern.matcher(
							trimmedLine);

						if (!matcher.find()) {
							processErrorMessage(
								fileName,
								"line break: " + fileName + " " + lineCount);
						}
					}
				}

				if (line.contains("    ") && !line.matches("\\s*\\*.*")) {
					if (!fileName.endsWith("StringPool.java")) {
						processErrorMessage(
							fileName, "tab: " + fileName + " " + lineCount);
					}
				}

				if (line.contains("  {") && !line.matches("\\s*\\*.*")) {
					processErrorMessage(
						fileName, "{:" + fileName + " " + lineCount);
				}

				if (trimmedLine.startsWith("catch (") ||
					trimmedLine.startsWith("if (") ||
					trimmedLine.startsWith("else if (") ||
					trimmedLine.startsWith("while (") ||
					Validator.isNotNull(ifClause)) {

					ifClause = ifClause + line + StringPool.NEW_LINE;

					if (line.endsWith(") {")) {
						String newIfClause = formatIfClause(
							ifClause, fileName, lineCount);

						if (!ifClause.equals(newIfClause) &&
							content.contains(ifClause)) {

							return StringUtil.replace(
								content, ifClause, newIfClause);
						}

						ifClause = StringPool.BLANK;
					}
					else if (line.endsWith(StringPool.SEMICOLON)) {
						ifClause = StringPool.BLANK;
					}
				}

				int lineLength = getLineLength(line);

				if (!line.startsWith("import ") &&
					!line.startsWith("package ") &&
					!line.matches("\\s*\\*.*")) {

					if (fileName.endsWith("Table.java") &&
						line.contains("String TABLE_SQL_CREATE = ")) {
					}
					else if (fileName.endsWith("Table.java") &&
							 line.contains("String TABLE_SQL_DROP = ")) {
					}
					else if (fileName.endsWith("Table.java") &&
							 line.contains(" index IX_")) {
					}
					else if (lineLength > _MAX_LINE_LENGTH) {
						if (!isExcludedFile(
								_lineLengthExclusionFiles, absolutePath,
								lineCount) &&
							!isAnnotationParameter(content, trimmedLine)) {

							String truncateLongLinesContent =
								getTruncateLongLinesContent(
									content, line, trimmedLine, lineCount);

							if ((truncateLongLinesContent != null) &&
								!truncateLongLinesContent.equals(content)) {

								return truncateLongLinesContent;
							}

							processErrorMessage(
								fileName,
								"> 80: " + fileName + " " + lineCount);
						}
					}
					else {
						int lineLeadingTabCount = getLeadingTabCount(line);
						int previousLineLeadingTabCount = getLeadingTabCount(
							previousLine);

						if (!trimmedLine.startsWith("//")) {
							if (previousLine.endsWith(StringPool.COMMA) &&
								previousLine.contains(
									StringPool.OPEN_PARENTHESIS) &&
								!previousLine.contains("for (") &&
								(lineLeadingTabCount >
									previousLineLeadingTabCount)) {

								processErrorMessage(
									fileName,
									"line break: " + fileName + " " +
										lineCount);
							}

							if ((lineLeadingTabCount ==
									previousLineLeadingTabCount) &&
								(previousLine.endsWith(StringPool.EQUAL) ||
								 previousLine.endsWith(
									 StringPool.OPEN_PARENTHESIS))) {

								processErrorMessage(
									fileName,
									"tab: " + fileName + " " + lineCount);
							}

							if (Validator.isNotNull(trimmedLine)) {
								if (((previousLine.endsWith(StringPool.COLON) &&
									  previousLine.contains(
										  StringPool.TAB + "for ")) ||
									 (previousLine.endsWith(
										 StringPool.OPEN_PARENTHESIS) &&
									  previousLine.contains(
										  StringPool.TAB + "if "))) &&
									((previousLineLeadingTabCount + 2) !=
										lineLeadingTabCount)) {

									processErrorMessage(
										fileName,
										"line break: " + fileName + " " +
											lineCount);
								}

								if (previousLine.endsWith(
										StringPool.OPEN_CURLY_BRACE) &&
									!trimmedLine.startsWith(
										StringPool.CLOSE_CURLY_BRACE) &&
									((previousLineLeadingTabCount + 1) !=
										lineLeadingTabCount)) {

									processErrorMessage(
										fileName,
										"tab: " + fileName + " " + lineCount);
								}
							}

							if (previousLine.endsWith(StringPool.PERIOD)) {
								int x = trimmedLine.indexOf(
									CharPool.OPEN_PARENTHESIS);

								if ((x != -1) &&
									((getLineLength(previousLine) + x) <
										_MAX_LINE_LENGTH) &&
									(trimmedLine.endsWith(
										StringPool.OPEN_PARENTHESIS) ||
									 (trimmedLine.charAt(x + 1) !=
										 CharPool.CLOSE_PARENTHESIS))) {

									processErrorMessage(
										fileName,
										"line break: " + fileName + " " +
											lineCount);
								}
							}

							int diff =
								lineLeadingTabCount -
								previousLineLeadingTabCount;

							if (trimmedLine.startsWith("throws ") &&
								((diff == 0) || (diff > 1))) {

								processErrorMessage(
									fileName,
									"tab: " + fileName + " " + lineCount);
							}

							if ((diff == 2) &&
								(previousLineLeadingTabCount > 0) &&
								line.endsWith(StringPool.SEMICOLON) &&
								!previousLine.contains(
									StringPool.TAB + "for (") &&
								!previousLine.contains(
									StringPool.TAB + "try (")) {

								line = StringUtil.replaceFirst(
									line, StringPool.TAB, StringPool.BLANK);
							}

							if ((previousLine.contains(" class " ) ||
								 previousLine.contains(" enum ")) &&
								previousLine.endsWith(
									StringPool.OPEN_CURLY_BRACE) &&
								Validator.isNotNull(line) &&
								!trimmedLine.startsWith(
									StringPool.CLOSE_CURLY_BRACE)) {

								processErrorMessage(
									fileName,
									"line break: " + fileName + " " +
										lineCount);
							}
						}

						String combinedLinesContent = getCombinedLinesContent(
							content, fileName, absolutePath, line, trimmedLine,
							lineLength, lineCount, previousLine,
							lineLeadingTabCount, previousLineLeadingTabCount);

						if ((combinedLinesContent != null) &&
							!combinedLinesContent.equals(content)) {

							return combinedLinesContent;
						}
					}
				}

				if (lineCount > 1) {
					sb.append(previousLine);

					if (Validator.isNotNull(previousLine) &&
						Validator.isNotNull(trimmedLine) &&
						!previousLine.contains("/*") &&
						!previousLine.endsWith("*/")) {

						String trimmedPreviousLine = StringUtil.trimLeading(
							previousLine);

						trimmedLine = StringUtil.trimLeading(line);

						if ((trimmedPreviousLine.startsWith("// ") &&
							 !trimmedLine.startsWith("// ")) ||
							(!trimmedPreviousLine.startsWith("// ") &&
							 trimmedLine.startsWith("// "))) {

							sb.append("\n");
						}
						else if (!trimmedPreviousLine.endsWith(
									StringPool.OPEN_CURLY_BRACE) &&
								 !trimmedPreviousLine.endsWith(
									 StringPool.COLON) &&
								 (trimmedLine.startsWith("for (") ||
								  trimmedLine.startsWith("if (") ||
								  trimmedLine.startsWith("try {"))) {

							sb.append("\n");
						}
						else if (previousLine.endsWith(
									StringPool.TAB +
										StringPool.CLOSE_CURLY_BRACE) &&
								 !trimmedLine.startsWith(
									 StringPool.CLOSE_CURLY_BRACE) &&
								 !trimmedLine.startsWith(
									 StringPool.CLOSE_PARENTHESIS) &&
								 !trimmedLine.startsWith(
									 StringPool.DOUBLE_SLASH) &&
								 !trimmedLine.equals("*/") &&
								 !trimmedLine.startsWith("catch ") &&
								 !trimmedLine.startsWith("else ") &&
								 !trimmedLine.startsWith("finally ") &&
								 !trimmedLine.startsWith("while ")) {

							sb.append("\n");
						}
					}

					sb.append("\n");
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

	protected String getCombinedLinesContent(String content, Pattern pattern) {
		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String tabs = matcher.group(1);

			int x = matcher.start(1);

			String openChar = matcher.group(matcher.groupCount());

			int y = -1;

			if (openChar.equals(StringPool.OPEN_CURLY_BRACE)) {
				y = content.indexOf(
					StringPool.NEW_LINE + tabs + StringPool.CLOSE_CURLY_BRACE,
					x);
			}
			else if (openChar.equals(StringPool.OPEN_PARENTHESIS)) {
				y = content.indexOf(
					StringPool.NEW_LINE + tabs + StringPool.CLOSE_PARENTHESIS,
					x);
			}

			y = content.indexOf(CharPool.NEW_LINE, y + 1);

			if (y < x) {
				return content;
			}

			String match = content.substring(x, y);

			String replacement = match;

			while (replacement.contains("\n\t")) {
				replacement = StringUtil.replace(replacement, "\n\t", "\n");
			}

			replacement = StringUtil.replace(
				replacement, new String[] {",\n", "\n"},
				new String[] {StringPool.COMMA_AND_SPACE, StringPool.BLANK});

			if (getLineLength(replacement) <= _MAX_LINE_LENGTH) {
				return getCombinedLinesContent(
					StringUtil.replace(content, match, replacement), pattern);
			}
		}

		return content;
	}

	protected String getCombinedLinesContent(
		String content, String fileName, String line, String trimmedLine,
		int lineLength, int lineCount, String previousLine, String linePart,
		int tabDiff, boolean addToPreviousLine, boolean extraSpace,
		int numNextLinesRemoveLeadingTab) {

		int previousLineStartPos = getLineStartPos(content, lineCount - 1);

		if (linePart == null) {
			String combinedLine = previousLine;

			if (extraSpace) {
				combinedLine += StringPool.SPACE;
			}

			combinedLine += trimmedLine;

			String nextLine = getNextLine(content, lineCount);

			if (nextLine == null) {
				return null;
			}

			if (line.endsWith(StringPool.OPEN_CURLY_BRACE) &&
				(tabDiff != 0) && !previousLine.contains(" class ") &&
				Validator.isNull(nextLine)) {

				return StringUtil.replaceFirst(
					content, previousLine + "\n" + line + "\n", combinedLine,
					previousLineStartPos);
			}

			if (numNextLinesRemoveLeadingTab > 0) {
				int nextLineStartPos = getLineStartPos(content, lineCount + 1);

				for (int i = 0; i < numNextLinesRemoveLeadingTab; i++) {
					content = StringUtil.replaceFirst(
						content, StringPool.TAB, StringPool.BLANK,
						nextLineStartPos);

					nextLineStartPos =
						content.indexOf(CharPool.NEW_LINE, nextLineStartPos) +
							1;
				}
			}

			return StringUtil.replaceFirst(
				content, previousLine + "\n" + line, combinedLine,
				previousLineStartPos);
		}

		String firstLine = previousLine;
		String secondLine = line;

		if (addToPreviousLine) {
			if (extraSpace) {
				firstLine += StringPool.SPACE;
			}

			firstLine += linePart;

			secondLine = StringUtil.replaceFirst(
				line, linePart, StringPool.BLANK);
		}
		else {
			if (((linePart.length() + lineLength) <= _MAX_LINE_LENGTH) &&
				(line.endsWith(StringPool.OPEN_CURLY_BRACE) ||
				 line.endsWith(StringPool.SEMICOLON))) {

				firstLine = StringUtil.replaceLast(
					firstLine, StringUtil.trim(linePart), StringPool.BLANK);

				if (extraSpace) {
					secondLine = StringUtil.replaceLast(
						line, StringPool.TAB,
						StringPool.TAB + linePart + StringPool.SPACE);
				}
				else {
					secondLine = StringUtil.replaceLast(
						line, StringPool.TAB, StringPool.TAB + linePart);
				}
			}
			else {
				processErrorMessage(
					fileName, "line break: " + fileName + " " + lineCount);

				return null;
			}
		}

		firstLine = StringUtil.trimTrailing(firstLine);

		return StringUtil.replaceFirst(
			content, previousLine + "\n" + line, firstLine + "\n" + secondLine,
			previousLineStartPos);
	}

	protected String getCombinedLinesContent(
		String content, String fileName, String absolutePath, String line,
		String trimmedLine, int lineLength, int lineCount, String previousLine,
		int lineTabCount, int previousLineTabCount) {

		if (Validator.isNull(line) || Validator.isNull(previousLine) ||
			isExcludedFile(
				_fitOnSingleLineExclusionFiles, absolutePath, lineCount)) {

			return null;
		}

		String trimmedPreviousLine = StringUtil.trimLeading(previousLine);

		if (line.contains("// ") || line.contains("*/") ||
			line.contains("*/") || previousLine.contains("// ") ||
			previousLine.contains("*/") || previousLine.contains("*/")) {

			return null;
		}

		int tabDiff = lineTabCount - previousLineTabCount;

		if (previousLine.endsWith("= new")) {
			return getCombinedLinesContent(
				content, fileName, line, trimmedLine, lineLength, lineCount,
				previousLine, "new", tabDiff, false, true, 0);
		}

		if (trimmedLine.startsWith("+ ") || trimmedLine.startsWith("- ") ||
			trimmedLine.startsWith("|| ") || trimmedLine.startsWith("&& ")) {

			int pos = trimmedLine.indexOf(CharPool.SPACE);

			String linePart = trimmedLine.substring(0, pos);

			return getCombinedLinesContent(
				content, fileName, line, trimmedLine, lineLength, lineCount,
				previousLine, linePart, tabDiff, true, true, 0);
		}

		if (previousLine.endsWith("<") && !previousLine.endsWith(" <")) {
			return getCombinedLinesContent(
				content, fileName, line, trimmedLine, lineLength, lineCount,
				previousLine, "<", tabDiff, false, false, 0);
		}

		int previousLineLength = getLineLength(previousLine);

		if ((trimmedLine.length() + previousLineLength) < _MAX_LINE_LENGTH) {
			if (trimmedPreviousLine.startsWith("for ") &&
				previousLine.endsWith(StringPool.COLON) &&
				line.endsWith(StringPool.OPEN_CURLY_BRACE)) {

				return getCombinedLinesContent(
					content, fileName, line, trimmedLine, lineLength, lineCount,
					previousLine, null, tabDiff, false, true, 0);
			}

			if (line.endsWith(StringPool.SEMICOLON) &&
				!previousLine.endsWith(StringPool.COLON) &&
				!previousLine.endsWith(StringPool.OPEN_BRACKET) &&
				!previousLine.endsWith(StringPool.OPEN_CURLY_BRACE) &&
				!previousLine.endsWith(StringPool.OPEN_PARENTHESIS) &&
				!previousLine.endsWith(StringPool.PERIOD) &&
				(lineTabCount == (previousLineTabCount + 1))) {

				return getCombinedLinesContent(
					content, fileName, line, trimmedLine, lineLength, lineCount,
					previousLine, null, tabDiff, false, true, 0);
			}

			if ((trimmedPreviousLine.startsWith("if ") ||
				 trimmedPreviousLine.startsWith("else ")) &&
				(previousLine.endsWith("||") || previousLine.endsWith("&&")) &&
				line.endsWith(StringPool.OPEN_CURLY_BRACE)) {

				return getCombinedLinesContent(
					content, fileName, line, trimmedLine, lineLength, lineCount,
					previousLine, null, tabDiff, false, true, 0);
			}

			if (trimmedLine.startsWith("throws") &&
				(line.endsWith(StringPool.OPEN_CURLY_BRACE) ||
				 line.endsWith(StringPool.SEMICOLON)) &&
				(lineTabCount == (previousLineTabCount + 1))) {

				return getCombinedLinesContent(
					content, fileName, line, trimmedLine, lineLength, lineCount,
					previousLine, null, tabDiff, false, true, 0);
			}

			if (previousLine.endsWith(StringPool.EQUAL)) {
				if (line.endsWith(StringPool.OPEN_CURLY_BRACE)) {
					processErrorMessage(
						fileName, "line break: " + fileName + " " + lineCount);

					return null;
				}

				if (previousLine.endsWith(" =") &&
					line.endsWith(StringPool.OPEN_PARENTHESIS)) {

					for (int i = 0;; i++) {
						String nextLine = getNextLine(content, lineCount + i);

						if (Validator.isNull(nextLine) ||
							nextLine.endsWith(") {")) {

							processErrorMessage(
								fileName,
								"line break: " + fileName + " " + lineCount);

							return null;
						}

						if (nextLine.endsWith(") +")) {
							return null;
						}

						if (nextLine.endsWith(StringPool.SEMICOLON)) {
							return getCombinedLinesContent(
								content, fileName, line, trimmedLine,
								lineLength, lineCount, previousLine, null,
								tabDiff, false, true, i + 1);
						}
					}
				}
			}
		}

		if (((trimmedLine.length() + previousLineLength) <= _MAX_LINE_LENGTH) &&
			(previousLine.endsWith(StringPool.OPEN_BRACKET) ||
			 previousLine.endsWith(StringPool.OPEN_PARENTHESIS) ||
			 previousLine.endsWith(StringPool.PERIOD)) &&
			line.endsWith(StringPool.SEMICOLON)) {

			return getCombinedLinesContent(
				content, fileName, line, trimmedLine, lineLength, lineCount,
				previousLine, null, tabDiff, false, false, 0);
		}

		if (previousLine.endsWith(StringPool.EQUAL) &&
			line.endsWith(StringPool.SEMICOLON)) {

			String tempLine = trimmedLine;

			for (int pos = 0;;) {
				pos = tempLine.indexOf(CharPool.DASH);

				if (pos == -1) {
					pos = tempLine.indexOf(CharPool.PLUS);
				}

				if (pos == -1) {
					pos = tempLine.indexOf(CharPool.SLASH);
				}

				if (pos == -1) {
					pos = tempLine.indexOf(CharPool.STAR);
				}

				if (pos == -1) {
					pos = tempLine.indexOf("||");
				}

				if (pos == -1) {
					pos = tempLine.indexOf("&&");
				}

				if (pos == -1) {
					break;
				}

				String linePart = tempLine.substring(0, pos);

				int openParenthesisCount = StringUtil.count(
					linePart, StringPool.OPEN_PARENTHESIS);
				int closeParenthesisCount = StringUtil.count(
					linePart, StringPool.CLOSE_PARENTHESIS);

				if (openParenthesisCount == closeParenthesisCount) {
					return null;
				}

				tempLine =
					tempLine.substring(0, pos) + tempLine.substring(pos + 1);
			}

			int x = trimmedLine.indexOf(CharPool.OPEN_PARENTHESIS);

			if (x == 0) {
				x = trimmedLine.indexOf(CharPool.OPEN_PARENTHESIS, 1);
			}

			if (x != -1) {
				int y = trimmedLine.indexOf(CharPool.CLOSE_PARENTHESIS, x);
				int z = trimmedLine.indexOf(CharPool.QUOTE);

				if (((x + 1) != y) && ((z == -1) || (z > x))) {
					char previousChar = trimmedLine.charAt(x - 1);

					if ((previousChar != CharPool.CLOSE_PARENTHESIS) &&
						(previousChar != CharPool.OPEN_PARENTHESIS) &&
						(previousChar != CharPool.SPACE) &&
						(previousLineLength + 1 + x) < _MAX_LINE_LENGTH) {

						String linePart = trimmedLine.substring(0, x + 1);

						if (linePart.startsWith(StringPool.OPEN_PARENTHESIS) &&
							!linePart.contains(
								StringPool.CLOSE_PARENTHESIS)) {

							return null;
						}

						return getCombinedLinesContent(
							content, fileName, line, trimmedLine, lineLength,
							lineCount, previousLine, linePart, tabDiff, true,
							true, 0);
					}
				}
			}
		}

		if (previousLine.endsWith(StringPool.COMMA) &&
			(previousLineTabCount == lineTabCount) &&
			!previousLine.contains(StringPool.CLOSE_CURLY_BRACE) &&
			!line.endsWith(StringPool.EQUAL) &&
			(line.endsWith(") {") ||
			 !line.endsWith(StringPool.OPEN_CURLY_BRACE))) {

			int x = trimmedLine.indexOf(CharPool.COMMA);

			if (x != -1) {
				while ((previousLineLength + 1 + x) < _MAX_LINE_LENGTH) {
					String linePart = trimmedLine.substring(0, x + 1);

					if (isValidJavaParameter(linePart)) {
						if (trimmedLine.equals(linePart)) {
							return getCombinedLinesContent(
								content, fileName, line, trimmedLine,
								lineLength, lineCount, previousLine, null,
								tabDiff, false, true, 0);
						}
						else {
							return getCombinedLinesContent(
								content, fileName, line, trimmedLine,
								lineLength, lineCount, previousLine,
								linePart + StringPool.SPACE, tabDiff, true,
								true, 0);
						}
					}

					String partAfterComma = trimmedLine.substring(x + 1);

					int pos = partAfterComma.indexOf(CharPool.COMMA);

					if (pos == -1) {
						break;
					}

					x = x + pos + 1;
				}
			}
			else if (!line.endsWith(StringPool.OPEN_PARENTHESIS) &&
					 !line.endsWith(StringPool.PLUS) &&
					 !line.endsWith(StringPool.PERIOD) &&
					 !Validator.isVariableName(trimmedLine) &&
					 (!trimmedLine.startsWith("new ") ||
					  !line.endsWith(StringPool.OPEN_CURLY_BRACE)) &&
					 ((trimmedLine.length() + previousLineLength) <
						 _MAX_LINE_LENGTH)) {

				return getCombinedLinesContent(
					content, fileName, line, trimmedLine, lineLength, lineCount,
					previousLine, null, tabDiff, false, true, 0);
			}
		}

		if (!previousLine.endsWith(StringPool.OPEN_PARENTHESIS)) {
			return null;
		}

		if (StringUtil.count(previousLine, StringPool.OPEN_PARENTHESIS) > 1) {
			int pos = trimmedPreviousLine.lastIndexOf(
				CharPool.OPEN_PARENTHESIS, trimmedPreviousLine.length() - 2);

			if ((pos > 0) &&
				Character.isLetterOrDigit(
					trimmedPreviousLine.charAt(pos -1 ))) {

				String filePart = trimmedPreviousLine.substring(pos + 1);

				if (!filePart.contains(StringPool.CLOSE_PARENTHESIS) &&
					!filePart.contains(StringPool.QUOTE)) {

					return getCombinedLinesContent(
						content, fileName, line, trimmedLine, lineLength,
						lineCount, previousLine, filePart, tabDiff, false,
						false, 0);
				}
			}
		}

		if ((trimmedLine.length() + previousLineLength) > _MAX_LINE_LENGTH) {
			return null;
		}

		if (line.endsWith(StringPool.COMMA)) {
			String strippedQuotesLine = stripQuotes(
				trimmedLine, CharPool.QUOTE);

			int openParenthesisCount = StringUtil.count(
				strippedQuotesLine, StringPool.OPEN_PARENTHESIS);
			int closeParenthesisCount = StringUtil.count(
				strippedQuotesLine, StringPool.CLOSE_PARENTHESIS);

			if (closeParenthesisCount > openParenthesisCount) {
				return getCombinedLinesContent(
					content, fileName, line, trimmedLine, lineLength, lineCount,
					previousLine, null, tabDiff, false, false, 0);
			}
		}

		if (((line.endsWith(StringPool.OPEN_CURLY_BRACE) &&
			  !trimmedLine.startsWith("new ")) ||
			 line.endsWith(StringPool.CLOSE_PARENTHESIS)) &&
			(trimmedPreviousLine.startsWith("else ") ||
			 trimmedPreviousLine.startsWith("if ") ||
			 trimmedPreviousLine.startsWith("private ") ||
			 trimmedPreviousLine.startsWith("protected ") ||
			 trimmedPreviousLine.startsWith("public "))) {

			return getCombinedLinesContent(
				content, fileName, line, trimmedLine, lineLength, lineCount,
				previousLine, null, tabDiff, false, false, 0);
		}

		return null;
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
			if (getLineLength(classSingleLine) <= _MAX_LINE_LENGTH) {
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

				if ((StringUtil.count(linePart, StringPool.GREATER_THAN) ==
						StringUtil.count(linePart, StringPool.LESS_THAN)) &&
					(getLineLength(linePart) <= _MAX_LINE_LENGTH)) {

					if (lines.isEmpty()) {
						newIndent = newIndent + StringPool.TAB;
					}

					lines.add(linePart);

					newLine = newIndent + newLine.substring(x + 1);

					if (getLineLength(newLine) <= _MAX_LINE_LENGTH) {
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

				if ((StringUtil.count(linePart, StringPool.GREATER_THAN) ==
						StringUtil.count(linePart, StringPool.LESS_THAN)) &&
					(getLineLength(linePart) <= _MAX_LINE_LENGTH)) {

					lines.add(linePart);

					if (linePart.contains("\textends")) {
						newIndent = newIndent + "\t\t";
					}
					else if (linePart.contains("\timplements")) {
						newIndent = newIndent + "\t\t   ";
					}

					newLine = newIndent + newLine.substring(x + 2);

					if (getLineLength(newLine) <= _MAX_LINE_LENGTH) {
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

	protected int getIfClauseLineBreakPos(String line) {
		int x = line.lastIndexOf(" || ", _MAX_LINE_LENGTH - 3);
		int y = line.lastIndexOf(" && ", _MAX_LINE_LENGTH - 3);

		int z = Math.max(x, y);

		if (z != -1) {
			return z + 3;
		}

		if (!line.endsWith(" ||") && !line.endsWith(" &&") &&
			!line.endsWith(") {")) {

			return -1;
		}

		x = line.indexOf("= ");

		if (x != -1) {
			return x + 1;
		}

		x = line.indexOf("> ");

		if (x != -1) {
			return x + 1;
		}

		x = line.indexOf("< ");

		if (x != -1) {
			return x + 1;
		}

		for (x = _MAX_LINE_LENGTH + 1;;) {
			x = line.lastIndexOf(StringPool.COMMA_AND_SPACE, x - 1);

			if (x == -1) {
				break;
			}

			String linePart = line.substring(0, x);

			if (StringUtil.count(linePart, StringPool.CLOSE_PARENTHESIS) ==
					StringUtil.count(linePart, StringPool.OPEN_PARENTHESIS)) {

				return x + 1;
			}
		}

		for (x = 0;;) {
			x = line.indexOf(CharPool.OPEN_PARENTHESIS, x + 1);

			if (x == -1) {
				break;
			}

			if (Character.isLetterOrDigit(line.charAt(x - 1))) {
				return x + 1;
			}
		}

		x = line.indexOf(CharPool.PERIOD);

		if (x != -1) {
			return x + 1;
		}

		return -1;
	}

	protected List<String> getImportedExceptionClassNames(
		JavaDocBuilder javaDocBuilder) {

		List<String> exceptionClassNames = new ArrayList<>();

		JavaSource javaSource = javaDocBuilder.getSources()[0];

		for (String importClassName : javaSource.getImports()) {
			if (importClassName.endsWith("Exception") &&
				!exceptionClassNames.contains(importClassName)) {

				exceptionClassNames.add(importClassName);
			}
		}

		return exceptionClassNames;
	}

	protected int getIncorrectLineBreakPos(String line, String previousLine) {
		for (int x = line.length();;) {
			int y = line.lastIndexOf(" || ", x - 1);
			int z = line.lastIndexOf(" && ", x - 1);

			x = Math.max(y, z);

			if (x == -1) {
				return x;
			}

			if (ToolsUtil.isInsideQuotes(line, x)) {
				continue;
			}

			if (Validator.isNotNull(previousLine)) {
				String linePart1 = stripQuotes(
					line.substring(0, x), CharPool.QUOTE);

				int closeParenthesesCount = StringUtil.count(
					linePart1, StringPool.CLOSE_PARENTHESIS);
				int openParenthesesCount = StringUtil.count(
					linePart1, StringPool.OPEN_PARENTHESIS);

				if (closeParenthesesCount > openParenthesesCount) {
					return x + 3;
				}
			}

			if (!line.endsWith(" ||") && !line.endsWith(" &&")) {
				continue;
			}

			String linePart2 = stripQuotes(line.substring(x), CharPool.QUOTE);

			int closeParenthesesCount = StringUtil.count(
				linePart2, StringPool.CLOSE_PARENTHESIS);
			int openParenthesesCount = StringUtil.count(
				linePart2, StringPool.OPEN_PARENTHESIS);

			if (openParenthesesCount > closeParenthesesCount) {
				return x + 3;
			}
		}
	}

	protected int getLineLength(String line) {
		int lineLength = 0;

		int tabLength = 4;

		for (char c : line.toCharArray()) {
			if (c == CharPool.TAB) {
				for (int i = 0; i < tabLength; i++) {
					lineLength++;
				}

				tabLength = 4;
			}
			else {
				lineLength++;

				tabLength--;

				if (tabLength <= 0) {
					tabLength = 4;
				}
			}
		}

		return lineLength;
	}

	protected int getLineStartPos(String content, int lineCount) {
		int x = 0;

		for (int i = 1; i < lineCount; i++) {
			x = content.indexOf(CharPool.NEW_LINE, x + 1);

			if (x == -1) {
				return x;
			}
		}

		return x + 1;
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

	protected String getNextLine(String content, int lineCount) {
		int nextLineStartPos = getLineStartPos(content, lineCount + 1);

		if (nextLineStartPos == -1) {
			return null;
		}

		int nextLineEndPos = content.indexOf(
			CharPool.NEW_LINE, nextLineStartPos);

		if (nextLineEndPos == -1) {
			return content.substring(nextLineStartPos);
		}

		return content.substring(nextLineStartPos, nextLineEndPos);
	}

	protected Collection<String> getPluginJavaFiles() throws Exception {
		Collection<String> fileNames = new TreeSet<>();

		String[] excludes = new String[] {
			"**/model/*Clp.java", "**/model/impl/*BaseImpl.java",
			"**/model/impl/*Model.java", "**/model/impl/*ModelImpl.java",
			"**/service/**/service/*Service.java",
			"**/service/**/service/*ServiceClp.java",
			"**/service/**/service/*ServiceFactory.java",
			"**/service/**/service/*ServiceUtil.java",
			"**/service/**/service/*ServiceWrapper.java",
			"**/service/**/service/ClpSerializer.java",
			"**/service/**/service/messaging/*ClpMessageListener.java",
			"**/service/**/service/persistence/*Finder.java",
			"**/service/**/service/persistence/*Util.java",
			"**/service/base/*ServiceBaseImpl.java",
			"**/service/base/*ServiceClpInvoker.java",
			"**/service/http/*JSONSerializer.java",
			"**/service/http/*ServiceHttp.java",
			"**/service/http/*ServiceJSON.java",
			"**/service/http/*ServiceSoap.java"
		};
		String[] includes = new String[] {"**/*.java"};

		fileNames.addAll(getFileNames(excludes, includes));

		return fileNames;
	}

	protected Collection<String> getPortalJavaFiles() throws Exception {
		Collection<String> fileNames = new TreeSet<>();

		String[] excludes = new String[] {
			"**/*_IW.java", "**/PropsValues.java", "**/counter/service/**",
			"**/jsp/*", "**/model/impl/*BaseImpl.java",
			"**/model/impl/*Model.java", "**/model/impl/*ModelImpl.java",
			"**/portal/service/**", "**/portal-client/**",
			"**/portal-web/test/**/*Test.java", "**/portlet/**/service/**",
			"**/test/*-generated/**", "**/source/formatter/**"
		};
		String[] includes = getIncludes();

		fileNames.addAll(getFileNames(excludes, includes));

		excludes = new String[] {
			"**/portal-client/**", "**/tools/ext_tmpl/**", "**/*_IW.java",
			"**/test/**/*PersistenceTest.java", "**/source/formatter/**"
		};
		includes = new String[] {
			"**/com/liferay/portal/service/ServiceContext*.java",
			"**/model/BaseModel.java", "**/model/impl/BaseModelImpl.java",
			"**/portal-test/**/portal/service/**/*.java",
			"**/portal-test-internal/**/portal/service/**/*.java",
			"**/service/Base*.java",
			"**/service/PersistedModelLocalService*.java",
			"**/service/base/PrincipalBean.java",
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

	protected String getTruncateLongLinesContent(
		String content, String line, String trimmedLine, int lineCount) {

		String indent = StringPool.BLANK;

		for (int i = 0; i < getLeadingTabCount(line); i++) {
			indent += StringPool.TAB;
		}

		if (line.endsWith(StringPool.OPEN_PARENTHESIS) ||
			line.endsWith(StringPool.SEMICOLON)) {

			int x = line.indexOf(" = ");

			if (x != -1) {
				String firstLine = line.substring(0, x + 2);

				if (firstLine.contains(StringPool.QUOTE)) {
					return null;
				}

				String secondLine =
					indent + StringPool.TAB + line.substring(x + 3);

				if (line.endsWith(StringPool.SEMICOLON)) {
					return StringUtil.replace(
						content, "\n" + line + "\n",
						"\n" + firstLine + "\n" + secondLine + "\n");
				}
				else if (Validator.isNotNull(getNextLine(content, lineCount))) {
					return StringUtil.replace(
						content, "\n" + line + "\n",
						"\n" + firstLine + "\n" + secondLine + "\n" +
							StringPool.TAB);
				}
			}
		}

		if (line.endsWith(StringPool.CLOSE_PARENTHESIS) ||
			line.endsWith(StringPool.COMMA) ||
			line.endsWith(StringPool.OPEN_CURLY_BRACE) ||
			line.endsWith(StringPool.SEMICOLON)) {

			int x = 0;

			while (true) {
				x = line.indexOf(", ", x + 1);

				if (x == -1) {
					break;
				}

				if (isValidJavaParameter(line.substring(0, x))) {
					String firstLine = line.substring(0, x + 1);
					String secondLine = indent + line.substring(x + 2);

					return StringUtil.replace(
						content, "\n" + line + "\n",
						"\n" + firstLine + "\n" + secondLine + "\n");
				}
			}
		}

		if ((line.endsWith(StringPool.OPEN_CURLY_BRACE) ||
			 line.endsWith(StringPool.SEMICOLON)) &&
			(trimmedLine.startsWith("private ") ||
			 trimmedLine.startsWith("protected ") ||
			 trimmedLine.startsWith("public "))) {

			int x = line.indexOf(" throws ");

			if (x != -1) {
				String firstLine = line.substring(0, x);
				String secondLine =
					indent + StringPool.TAB + line.substring(x + 1);

				return StringUtil.replace(
					content, "\n" + line + "\n",
					"\n" + firstLine + "\n" + secondLine + "\n");
			}
		}

		if ((line.endsWith(StringPool.CLOSE_PARENTHESIS) ||
			 line.endsWith(StringPool.OPEN_CURLY_BRACE)) &&
			(trimmedLine.startsWith("private ") ||
			 trimmedLine.startsWith("protected ") ||
			 trimmedLine.startsWith("public "))) {

			int x = line.indexOf(CharPool.OPEN_PARENTHESIS);

			if ((x != -1) &&
				(line.charAt(x + 1) != CharPool.CLOSE_PARENTHESIS)) {

				String secondLineIndent = indent + StringPool.TAB;

				if (line.endsWith(StringPool.CLOSE_PARENTHESIS)) {
					secondLineIndent += StringPool.TAB;
				}

				String firstLine = line.substring(0, x + 1);
				String secondLine = secondLineIndent + line.substring(x + 1);

				return StringUtil.replace(
					content, "\n" + line + "\n",
					"\n" + firstLine + "\n" + secondLine + "\n");
			}
		}

		if (line.endsWith(StringPool.SEMICOLON)) {
			int x = line.indexOf(CharPool.OPEN_PARENTHESIS);

			if (x != -1) {
				char c = line.charAt(x - 1);

				if ((c != CharPool.SPACE) && (c != CharPool.TAB) &&
					(line.charAt(x + 1) != CharPool.CLOSE_PARENTHESIS)) {

					String firstLine = line.substring(0, x + 1);

					if (firstLine.contains(StringPool.QUOTE)) {
						return null;
					}

					String secondLine =
						indent + StringPool.TAB + line.substring(x + 1);

					return StringUtil.replace(
						content, "\n" + line + "\n",
						"\n" + firstLine + "\n" + secondLine + "\n");
				}
			}
		}

		if (line.contains(StringPool.TAB + "for (") && line.endsWith(" {")) {
			int x = line.indexOf(" : ");

			if (x != -1) {
				String firstLine = line.substring(0, x + 2);
				String secondLine =
					indent + StringPool.TAB + StringPool.TAB +
						line.substring(x + 3);

				return StringUtil.replace(
					content, "\n" + line + "\n",
					"\n" + firstLine + "\n" + secondLine + "\n\n");
			}
		}

		int i = getIfClauseLineBreakPos(line);

		if (i == -1) {
			return null;
		}

		String firstLine = line.substring(0, i);
		String secondLine = indent + line.substring(i);

		if (secondLine.endsWith(") {")) {
			return StringUtil.replace(
				content, "\n" + line + "\n",
				"\n" + firstLine + "\n" + secondLine + "\n\n");
		}

		return StringUtil.replace(
			content, "\n" + line + "\n",
			"\n" + firstLine + "\n" + secondLine + "\n");
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

	protected boolean isAnnotationParameter(String content, String line) {
		Matcher matcher = _annotationPattern.matcher(content);

		while (matcher.find()) {
			String annotationParameters = matcher.group(3);

			if (annotationParameters.contains(line)) {
				return true;
			}
		}

		return false;
	}

	protected boolean isValidJavaParameter(String javaParameter) {
		if (javaParameter.contains(" implements ") ||
			javaParameter.contains(" throws ")) {

			return false;
		}

		int quoteCount = StringUtil.count(javaParameter, StringPool.QUOTE);

		if ((quoteCount % 2) == 1) {
			return false;
		}

		javaParameter = stripQuotes(javaParameter, CharPool.QUOTE);

		int openParenthesisCount = StringUtil.count(
			javaParameter, StringPool.OPEN_PARENTHESIS);
		int closeParenthesisCount = StringUtil.count(
			javaParameter, StringPool.CLOSE_PARENTHESIS);
		int lessThanCount = StringUtil.count(
			javaParameter, StringPool.LESS_THAN);
		int greaterThanCount = StringUtil.count(
			javaParameter, StringPool.GREATER_THAN);
		int openCurlyBraceCount = StringUtil.count(
			javaParameter, StringPool.OPEN_CURLY_BRACE);
		int closeCurlyBraceCount = StringUtil.count(
			javaParameter, StringPool.CLOSE_CURLY_BRACE);

		if ((openParenthesisCount == closeParenthesisCount) &&
			(lessThanCount == greaterThanCount) &&
			(openCurlyBraceCount == closeCurlyBraceCount)) {

			return true;
		}

		return false;
	}

	protected String sortExceptions(String line) {
		if (!line.endsWith(StringPool.OPEN_CURLY_BRACE) &&
			!line.endsWith(StringPool.SEMICOLON)) {

			return line;
		}

		int x = line.indexOf("throws ");

		if (x == -1) {
			return line;
		}

		String previousException = StringPool.BLANK;

		String[] exceptions = StringUtil.split(
			line.substring(x), CharPool.SPACE);

		for (int i = 1; i < exceptions.length; i++) {
			String exception = exceptions[i];

			if (exception.equals(StringPool.OPEN_CURLY_BRACE)) {
				break;
			}

			if (exception.endsWith(StringPool.COMMA) ||
				exception.endsWith(StringPool.SEMICOLON)) {

				exception = exception.substring(0, exception.length() - 1);
			}

			if (Validator.isNotNull(previousException) &&
				(previousException.compareToIgnoreCase(exception) > 0)) {

				line = StringUtil.replace(
					line, previousException + ", " + exception,
					exception + ", " + previousException);

				return sortExceptions(line);
			}

			previousException = exception;
		}

		return line;
	}

	private static final String[] _INCLUDES = new String[] {"**/*.java"};

	private static final int _MAX_LINE_LENGTH = 80;

	private static Pattern _annotationPattern = Pattern.compile(
		"\n(\t*)@(.+)\\(\n([\\s\\S]*?)\\)\n");

	private boolean _addMissingDeprecationReleaseVersion;
	private boolean _allowUseServiceUtilInServiceImpl;
	private Pattern _catchExceptionPattern = Pattern.compile(
		"\n(\t+)catch \\((.+Exception) (.+)\\) \\{\n");
	private List<String> _checkJavaFieldTypesExclusionFiles;
	private boolean _checkModulesServiceUtil;
	private boolean _checkUnprocessedExceptions;
	private final Pattern _classPattern = Pattern.compile(
		"\n(\t*)(private|protected|public) ((abstract|static) )*" +
			"(class|enum|interface) ([\\s\\S]*?) \\{\n");
	private Pattern _combinedLinesPattern1 = Pattern.compile(
		"\n(\t*).+(=|\\]) (\\{)\n");
	private Pattern _combinedLinesPattern2 = Pattern.compile(
		"\n(\t*)@.+(\\()\n");
	private List<String> _diamondOperatorExclusionFiles;
	private List<String> _diamondOperatorExclusionPaths;
	private Pattern _diamondOperatorPattern = Pattern.compile(
		"(return|=)\n?(\t+| )new ([A-Za-z]+)(\\s*)<(.+)>\\(\n*\t*.*\\);\n");
	private Pattern _fetchByPrimaryKeysMethodPattern = Pattern.compile(
		"@Override\n\tpublic Map<(.+)> fetchByPrimaryKeys\\(");
	private List<String> _fitOnSingleLineExclusionFiles;
	private List<String> _hibernateSQLQueryExclusionFiles;
	private Pattern _incorrectCloseCurlyBracePattern1 = Pattern.compile(
		"\n(.+)\n\n(\t+)}\n");
	private Pattern _incorrectCloseCurlyBracePattern2 = Pattern.compile(
		"(\t| )@?(class |enum |interface |new )");
	private Pattern _incorrectLineBreakPattern1 = Pattern.compile(
		"\t(catch |else |finally |for |if |try |while ).*\\{\n\n\t+\\w");
	private Pattern _incorrectLineBreakPattern2 = Pattern.compile(
		"\\{\n\n\t*\\}");
	private List<String> _javaTermAccessLevelModifierExclusionFiles;
	private List<String> _javaTermSortExclusionFiles;
	private Pattern _lineBreakPattern = Pattern.compile("\\}(\\)+) \\{");
	private List<String> _lineLengthExclusionFiles;
	private Pattern _logLevelPattern = Pattern.compile(
		"\n(\t+)_log.(debug|info|trace|warn)\\(");
	private Pattern _logPattern = Pattern.compile(
		"\n\tprivate static final Log _log = LogFactoryUtil.getLog\\(\n*" +
			"\t*(.+)\\.class\\)");
	private Pattern _processCallablePattern = Pattern.compile(
		"implements ProcessCallable\\b");
	private List<String> _proxyExclusionFiles;
	private Pattern _redundantCommaPattern = Pattern.compile(",\n\t+\\}");
	private List<String> _secureRandomExclusionFiles;
	private List<String> _secureXmlExclusionFiles;
	private Pattern _serviceUtilImportPattern = Pattern.compile(
		"\nimport ([A-Za-z1-9\\.]*)\\.([A-Za-z1-9]*ServiceUtil);");
	private Pattern _setReferenceMethodContentPattern = Pattern.compile(
		"^\\w+ =\\s+\\w+;$");
	private Pattern _setReferenceMethodPattern = Pattern.compile(
		"\n(\t+)@Reference([\\s\\S]*?)\\s+(protected|public) void (set\\w+?)" +
			"\\(\\s*([ ,<>\\w]+)\\s+\\w+\\) \\{\\s+([\\s\\S]*?)\\s*?\\}");
	private Pattern _stagedModelTypesPattern = Pattern.compile(
		"StagedModelType\\(([a-zA-Z.]*(class|getClassName[\\(\\)]*))\\)");
	private List<String> _staticLogVariableExclusionFiles;
	private List<String> _testAnnotationsExclusionFiles;
	private Pattern _throwsSystemExceptionPattern = Pattern.compile(
		"(\n\t+.*)throws(.*) SystemException(.*)( \\{|;\n)");
	private List<String> _upgradeServiceUtilExclusionFiles;

}