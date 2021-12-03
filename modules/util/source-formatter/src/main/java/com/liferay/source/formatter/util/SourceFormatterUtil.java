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

package com.liferay.source.formatter.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.java.parser.JavaParser;
import com.liferay.source.formatter.ExcludeSyntax;
import com.liferay.source.formatter.ExcludeSyntaxPattern;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.SourceFormatterExcludes;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.JavaVariable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Igor Spasic
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class SourceFormatterUtil {

	public static final String CHECKSTYLE_DOCUMENTATION_URL_BASE =
		"https://checkstyle.sourceforge.io/";

	public static final String GIT_LIFERAY_PORTAL_BRANCH =
		"git.liferay.portal.branch";

	public static final String GIT_LIFERAY_PORTAL_URL =
		"https://raw.githubusercontent.com/liferay/liferay-portal/";

	public static final String SOURCE_FORMATTER_TEST_PATH =
		"/source/formatter/dependencies/";

	public static final String UPGRADE_FROM_VERSION = "upgrade.from.version";

	public static final String UPGRADE_TO_VERSION = "upgrade.to.version";

	public static List<String> filterFileNames(
		List<String> allFileNames, String[] excludes, String[] includes,
		SourceFormatterExcludes sourceFormatterExcludes,
		boolean forceIncludeAllFiles) {

		List<String> excludeRegexList = new ArrayList<>();
		Map<String, List<String>> excludeRegexMap = new HashMap<>();
		List<String> includeRegexList = new ArrayList<>();

		for (String exclude : excludes) {
			if (!exclude.contains(StringPool.DOLLAR)) {
				excludeRegexList.add(_createRegex(exclude));
			}
		}

		if (!forceIncludeAllFiles) {
			Map<String, List<ExcludeSyntaxPattern>> excludeSyntaxPatternsMap =
				sourceFormatterExcludes.getExcludeSyntaxPatternsMap();

			for (Map.Entry<String, List<ExcludeSyntaxPattern>> entry :
					excludeSyntaxPatternsMap.entrySet()) {

				List<ExcludeSyntaxPattern> excludeSyntaxPatterns =
					entry.getValue();

				List<String> regexList = new ArrayList<>();

				for (ExcludeSyntaxPattern excludeSyntaxPattern :
						excludeSyntaxPatterns) {

					String excludePattern =
						excludeSyntaxPattern.getExcludePattern();
					ExcludeSyntax excludeSyntax =
						excludeSyntaxPattern.getExcludeSyntax();

					if (excludeSyntax.equals(ExcludeSyntax.REGEX)) {
						regexList.add(excludePattern);
					}
					else if (!excludePattern.contains(StringPool.DOLLAR)) {
						regexList.add(_createRegex(excludePattern));
					}
				}

				excludeRegexMap.put(entry.getKey(), regexList);
			}
		}

		for (String include : includes) {
			if (!include.contains(StringPool.DOLLAR)) {
				includeRegexList.add(_createRegex(include));
			}
		}

		List<String> fileNames = new ArrayList<>();

		outerLoop:
		for (String fileName : allFileNames) {
			String encodedFileName = SourceUtil.getAbsolutePath(fileName);

			for (String includeRegex : includeRegexList) {
				if (!encodedFileName.matches(includeRegex)) {
					continue;
				}

				for (String excludeRegex : excludeRegexList) {
					if (encodedFileName.matches(excludeRegex)) {
						continue outerLoop;
					}
				}

				for (Map.Entry<String, List<String>> entry :
						excludeRegexMap.entrySet()) {

					String propertiesFileLocation = entry.getKey();

					if (encodedFileName.startsWith(propertiesFileLocation)) {
						for (String excludeRegex : entry.getValue()) {
							if (encodedFileName.matches(excludeRegex)) {
								continue outerLoop;
							}
						}
					}
				}

				fileNames.add(fileName);

				continue outerLoop;
			}
		}

		return fileNames;
	}

	public static List<String> filterRecentChangesFileNames(
			Set<String> recentChangesFileNames, String[] excludes,
			String[] includes, SourceFormatterExcludes sourceFormatterExcludes)
		throws IOException {

		if (ArrayUtil.isEmpty(includes)) {
			return new ArrayList<>();
		}

		PathMatchers pathMatchers = _getPathMatchers(
			excludes, includes, sourceFormatterExcludes);

		return _filterRecentChangesFileNames(
			recentChangesFileNames, pathMatchers);
	}

	public static String getDocumentationURLString(Class<?> checkClass) {
		String documentationURLString = _getDocumentationURLString(
			checkClass.getSimpleName());

		if (documentationURLString != null) {
			return documentationURLString;
		}

		Class<?> superclass = checkClass.getSuperclass();

		String className = superclass.getSimpleName();

		documentationURLString = _getDocumentationURLString(className);

		if ((documentationURLString != null) || !className.startsWith("Base")) {
			return documentationURLString;
		}

		return _getDocumentationURLString(className.substring(4));
	}

	public static File getFile(String baseDirName, String fileName, int level) {
		for (int i = 0; i < level; i++) {
			File file = new File(baseDirName + fileName);

			if (file.exists()) {
				return file;
			}

			fileName = "../" + fileName;
		}

		return null;
	}

	public static String getGitContent(String fileName, String branchName) {
		URL url = getPortalGitURL(fileName, branchName);

		if (url == null) {
			return null;
		}

		try {
			return StringUtil.read(url.openStream());
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException, ioException);
			}

			return null;
		}
	}

	public static String getMarkdownFileName(String camelCaseName) {
		camelCaseName = StringUtil.replace(camelCaseName, "OSGi", "OSGI");

		camelCaseName = camelCaseName.replaceAll("([A-Z])s([A-Z])", "$1S$2");

		String markdownFileName = TextFormatter.format(
			camelCaseName, TextFormatter.K);

		markdownFileName = TextFormatter.format(
			markdownFileName, TextFormatter.N);

		return markdownFileName + ".markdown";
	}

	public static File getPortalDir(String baseDirName, int maxDirLevel) {
		File portalImplDir = getFile(baseDirName, "portal-impl", maxDirLevel);

		if (portalImplDir == null) {
			return null;
		}

		return portalImplDir.getParentFile();
	}

	public static URL getPortalGitURL(
		String fileName, String portalBranchName) {

		if (Validator.isNull(portalBranchName)) {
			return null;
		}

		try {
			return new URL(
				StringBundler.concat(
					SourceFormatterUtil.GIT_LIFERAY_PORTAL_URL,
					portalBranchName, StringPool.SLASH, fileName));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return null;
		}
	}

	public static JSONObject getPortalJSONObject(String dirName)
		throws Exception {

		return getPortalJSONObject(
			dirName, new SourceFormatterExcludes(),
			SourceFormatterArgs.MAX_LINE_LENGTH);
	}

	public static JSONObject getPortalJSONObject(
			String dirName, SourceFormatterExcludes sourceFormatterExcludes,
			int maxLineLength)
		throws Exception {

		ExecutorService executorService = Executors.newFixedThreadPool(1);

		List<Future<Tuple>> futures = new ArrayList<>();

		JSONObject taglibsJSONObject = new JSONObjectImpl();
		JSONObject xmlDefinitionsJSONObject = new JSONObjectImpl();

		List<String> fileNames = scanForFiles(
			dirName, new String[0],
			new String[] {"**/*.dtd", "**/*.java", "**/*.tld"},
			sourceFormatterExcludes, true);

		for (String fileName : fileNames) {
			if (fileName.endsWith(".dtd")) {
				xmlDefinitionsJSONObject = _addXMLdefinition(
					xmlDefinitionsJSONObject, fileName);
			}

			if (fileName.endsWith(".tld")) {
				taglibsJSONObject = _addTaglib(taglibsJSONObject, fileName);
			}

			if (!fileName.contains("/com/liferay/")) {
				continue;
			}

			Future<Tuple> future = executorService.submit(
				new Callable<Tuple>() {

					@Override
					public Tuple call() throws Exception {
						return _getClassTuple(fileName, maxLineLength);
					}

				});

			futures.add(future);
		}

		JSONObject javaClassesJSONObject = new JSONObjectImpl();

		for (Future<Tuple> future : futures) {
			try {
				Tuple tuple = future.get(1, TimeUnit.MINUTES);

				if (tuple != null) {
					javaClassesJSONObject.put(
						(String)tuple.getObject(0),
						(JSONObject)tuple.getObject(1));
				}
			}
			catch (Exception exception) {
				future.cancel(true);
			}
		}

		executorService.shutdown();

		JSONObject portalJSONObject = new JSONObjectImpl();

		return portalJSONObject.put(
			"javaClasses", javaClassesJSONObject
		).put(
			"taglibs", taglibsJSONObject
		).put(
			"xmlDefinitions", xmlDefinitionsJSONObject
		);
	}

	public static JSONObject getPortalJSONObjectByVersion(String version) {

		// TODO: Grab from URL (need to figure out what that URL is first)

		return new JSONObjectImpl();
	}

	public static String getSimpleName(String name) {
		int pos = name.lastIndexOf(CharPool.PERIOD);

		if (pos != -1) {
			return name.substring(pos + 1);
		}

		return name;
	}

	public static List<File> getSuppressionsFiles(
		String baseDirName, List<String> allFileNames,
		SourceFormatterExcludes sourceFormatterExcludes, int maxDirLevel) {

		List<File> suppressionsFiles = new ArrayList<>();

		// Find suppressions files in any parent directory

		String parentDirName = baseDirName;

		for (int j = 0; j < maxDirLevel; j++) {
			File suppressionsFile = new File(
				parentDirName + _SUPPRESSIONS_FILE_NAME);

			if (suppressionsFile.exists()) {
				suppressionsFiles.add(suppressionsFile);
			}

			parentDirName += "../";
		}

		// Find suppressions files in any child directory

		List<String> moduleSuppressionsFileNames = filterFileNames(
			allFileNames, new String[0],
			new String[] {"**/" + _SUPPRESSIONS_FILE_NAME},
			sourceFormatterExcludes, true);

		for (String moduleSuppressionsFileName : moduleSuppressionsFileNames) {
			moduleSuppressionsFileName = StringUtil.replace(
				moduleSuppressionsFileName, CharPool.BACK_SLASH,
				CharPool.SLASH);

			suppressionsFiles.add(new File(moduleSuppressionsFileName));
		}

		return suppressionsFiles;
	}

	public static void printError(String fileName, File file) {
		printError(fileName, file.toString());
	}

	public static void printError(String fileName, String message) {
		System.out.println(message);
	}

	public static List<String> scanForFiles(
			String baseDirName, String[] excludes, String[] includes,
			SourceFormatterExcludes sourceFormatterExcludes,
			boolean includeSubrepositories)
		throws IOException {

		if (ArrayUtil.isEmpty(includes)) {
			return new ArrayList<>();
		}

		PathMatchers pathMatchers = _getPathMatchers(
			excludes, includes, sourceFormatterExcludes);

		return _scanForFiles(baseDirName, pathMatchers, includeSubrepositories);
	}

	private static JSONArray _addElementValues(
		JSONArray elementJSONArray, String s) {

		s = s.replaceAll("^\\s*\\(?(.+?)[?*+]?\\)?[?*+]?\\s*$", "$1");

		if (s.equals("#PCDATA")) {
			return elementJSONArray;
		}

		if (s.contains(StringPool.COMMA)) {
			String[] parts = StringUtil.split(s, CharPool.COMMA);

			for (String part : parts) {
				elementJSONArray = _addElementValues(elementJSONArray, part);
			}

			return elementJSONArray;
		}

		if (s.contains(StringPool.PIPE)) {
			String[] parts = StringUtil.split(s, CharPool.PIPE);

			for (String part : parts) {
				elementJSONArray = _addElementValues(elementJSONArray, part);
			}

			return elementJSONArray;
		}

		if (s.matches("[\\w-]+")) {
			elementJSONArray.put(s);
		}

		return elementJSONArray;
	}

	private static JSONObject _addTaglib(
		JSONObject taglibsJSONObject, String fileName) {

		File tldFile = new File(fileName);

		Document document = null;

		try {
			document = SourceUtil.readXML(FileUtil.read(tldFile));
		}
		catch (Exception exception) {
			return taglibsJSONObject;
		}

		Element rootElement = document.getRootElement();

		if (!Objects.equals(rootElement.getName(), "taglib")) {
			return taglibsJSONObject;
		}

		Element shortNameElement = rootElement.element("short-name");

		if (shortNameElement == null) {
			return taglibsJSONObject;
		}

		JSONObject taglibJSONObject = new JSONObjectImpl();

		List<Element> tagElements = rootElement.elements("tag");

		for (Element tagElement : tagElements) {
			Element tagClassElement = tagElement.element("tag-class");

			String tagClassName = tagClassElement.getStringValue();

			if (!tagClassName.startsWith("com.liferay")) {
				continue;
			}

			JSONObject tagJSONObject = new JSONObjectImpl();

			JSONArray tagAttributeNamesJSONArray = new JSONArrayImpl();

			List<Element> attributeElements = tagElement.elements("attribute");

			for (Element attributeElement : attributeElements) {
				JSONObject attributeJSONObject = new JSONObjectImpl();

				Element attributeDescriptionElement = attributeElement.element(
					"description");

				if (attributeDescriptionElement != null) {
					String description =
						attributeDescriptionElement.getStringValue();

					if (description.startsWith("Deprecated")) {
						attributeJSONObject.put("deprecated", true);
					}
				}

				Element attributeNameElement = attributeElement.element("name");

				attributeJSONObject.put(
					"name", attributeNameElement.getStringValue());

				tagAttributeNamesJSONArray.put(attributeJSONObject);
			}

			if (tagAttributeNamesJSONArray.length() > 0) {
				tagJSONObject.put("attributes", tagAttributeNamesJSONArray);
			}

			Element tagDescriptionElement = tagElement.element("description");

			if (tagDescriptionElement != null) {
				String description = tagDescriptionElement.getStringValue();

				if (description.startsWith("Deprecated")) {
					tagJSONObject.put("deprecated", true);
				}
			}

			Element tagNameElement = tagElement.element("name");

			taglibJSONObject.put(
				tagNameElement.getStringValue(), tagJSONObject);
		}

		taglibsJSONObject.put(
			shortNameElement.getStringValue(), taglibJSONObject);

		return taglibsJSONObject;
	}

	private static JSONObject _addXMLdefinition(
			JSONObject xmlDefinitionsJSONObject, String fileName)
		throws Exception {

		if (!fileName.matches(".*_[0-9]_[0-9]_[0-9]\\.dtd")) {
			return xmlDefinitionsJSONObject;
		}

		JSONObject xmlDefinitionJSONObject = new JSONObjectImpl();

		File dtdFile = new File(fileName);

		String content = FileUtil.read(dtdFile);

		Matcher matcher = _elementPattern.matcher(content);

		while (matcher.find()) {
			int x = content.indexOf(">", matcher.end());

			if (x == -1) {
				return xmlDefinitionJSONObject;
			}

			JSONArray elementJSONArray = _addElementValues(
				new JSONArrayImpl(), content.substring(matcher.end() - 1, x));

			if (elementJSONArray.length() > 0) {
				xmlDefinitionJSONObject.put(matcher.group(1), elementJSONArray);
			}
		}

		if (xmlDefinitionJSONObject.length() > 0) {
			int x = fileName.lastIndexOf(StringPool.SLASH);

			xmlDefinitionsJSONObject.put(
				fileName.substring(x + 1), xmlDefinitionJSONObject);
		}

		return xmlDefinitionsJSONObject;
	}

	private static String _createRegex(String s) {
		if (!s.startsWith("**/")) {
			s = "**/" + s;
		}

		s = StringUtil.replace(s, CharPool.PERIOD, "\\.");

		StringBundler sb = new StringBundler();

		for (int i = 0; i < s.length(); i++) {
			char c1 = s.charAt(i);

			if (c1 != CharPool.STAR) {
				sb.append(c1);

				continue;
			}

			if (i == (s.length() - 1)) {
				sb.append("[^/]*");

				continue;
			}

			char c2 = s.charAt(i + 1);

			if (c2 == CharPool.STAR) {
				sb.append(".*");

				i++;

				continue;
			}

			sb.append("[^/]*");
		}

		return sb.toString();
	}

	private static List<String> _filterRecentChangesFileNames(
			Set<String> recentChangesFileNames, PathMatchers pathMatchers)
		throws IOException {

		List<String> fileNames = new ArrayList<>();

		recentChangesFileNamesLoop:
		for (String fileName : recentChangesFileNames) {
			File file = new File(fileName);

			File canonicalFile = file.getCanonicalFile();

			Path filePath = canonicalFile.toPath();

			for (PathMatcher pathMatcher :
					pathMatchers.getExcludeFilePathMatchers()) {

				if (pathMatcher.matches(filePath)) {
					continue recentChangesFileNamesLoop;
				}
			}

			String currentFilePath = SourceUtil.getAbsolutePath(filePath);

			Map<String, List<PathMatcher>> excludeFilePathMatchersMap =
				pathMatchers.getExcludeFilePathMatchersMap();

			for (Map.Entry<String, List<PathMatcher>> entry :
					excludeFilePathMatchersMap.entrySet()) {

				String propertiesFileLocation = entry.getKey();

				if (currentFilePath.startsWith(propertiesFileLocation)) {
					for (PathMatcher pathMatcher : entry.getValue()) {
						if (pathMatcher.matches(filePath)) {
							continue recentChangesFileNamesLoop;
						}
					}
				}
			}

			File dir = file.getParentFile();

			while (true) {
				File canonicalDir = dir.getCanonicalFile();

				Path dirPath = canonicalDir.toPath();

				for (PathMatcher pathMatcher :
						pathMatchers.getExcludeDirPathMatchers()) {

					if (pathMatcher.matches(dirPath)) {
						continue recentChangesFileNamesLoop;
					}
				}

				String currentDirPath = SourceUtil.getAbsolutePath(dirPath);

				Map<String, List<PathMatcher>> excludeDirPathMatchersMap =
					pathMatchers.getExcludeDirPathMatchersMap();

				for (Map.Entry<String, List<PathMatcher>> entry :
						excludeDirPathMatchersMap.entrySet()) {

					String propertiesFileLocation = entry.getKey();

					if (currentDirPath.startsWith(propertiesFileLocation)) {
						for (PathMatcher pathMatcher : entry.getValue()) {
							if (pathMatcher.matches(dirPath)) {
								continue recentChangesFileNamesLoop;
							}
						}
					}
				}

				if (Files.exists(dirPath.resolve("source_formatter.ignore"))) {
					continue recentChangesFileNamesLoop;
				}

				dir = dir.getParentFile();

				if (dir == null) {
					break;
				}
			}

			for (PathMatcher pathMatcher :
					pathMatchers.getIncludeFilePathMatchers()) {

				if (pathMatcher.matches(filePath)) {
					Path curFilePath = Paths.get(fileName);

					fileNames.add(curFilePath.toString());

					continue recentChangesFileNamesLoop;
				}
			}
		}

		return fileNames;
	}

	private static Path _getCanonicalPath(Path path) {
		try {
			File file = path.toFile();

			File canonicalFile = file.getCanonicalFile();

			return canonicalFile.toPath();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static Tuple _getClassTuple(String fileName, int maxLineLength)
		throws Exception {

		String content = JavaParser.parse(
			new File(fileName), maxLineLength, false);

		JavaClass javaClass = null;

		try {
			javaClass = JavaClassParser.parseJavaClass(fileName, content);
		}
		catch (Exception exception) {
			return null;
		}

		String className = javaClass.getName(true);

		if (!className.startsWith("com.liferay.")) {
			return null;
		}

		JSONObject classJSONObject = new JSONObjectImpl();

		if (javaClass.hasAnnotation("Deprecated")) {
			classJSONObject.put("deprecated", true);
		}

		JSONArray extendedClassesJSONArray = _getExtendedClassesJSONArray(
			javaClass);

		if (extendedClassesJSONArray.length() > 0) {
			classJSONObject.put("extendedClassNames", extendedClassesJSONArray);
		}

		classJSONObject.put("fileName", fileName);

		JSONArray implementedClassesJSONArray = _getImplementedClassesJSONArray(
			javaClass);

		if (implementedClassesJSONArray.length() > 0) {
			classJSONObject.put(
				"implementedClassNames", implementedClassesJSONArray);
		}

		JSONArray methodsJSONArray = _getMethodsJSONArray(javaClass);

		if (methodsJSONArray.length() > 0) {
			classJSONObject.put("methods", methodsJSONArray);
		}

		JSONArray variablesJSONArray = _getVariablesJSONArray(javaClass);

		if (variablesJSONArray.length() > 0) {
			classJSONObject.put("variables", variablesJSONArray);
		}

		return new Tuple(javaClass.getName(true), classJSONObject);
	}

	private static String _getDocumentationURLString(String checkName) {
		String markdownFileName = getMarkdownFileName(checkName);

		ClassLoader classLoader = SourceFormatterUtil.class.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"documentation/checks/" + markdownFileName);

		if (inputStream != null) {
			return _DOCUMENTATION_URL + markdownFileName;
		}

		return null;
	}

	private static JSONArray _getExtendedClassesJSONArray(JavaClass javaClass) {
		JSONArray extendedClassesJSONArray = new JSONArrayImpl();

		for (String extendedClassName : javaClass.getExtendedClassNames(true)) {
			extendedClassesJSONArray.put(extendedClassName);
		}

		return extendedClassesJSONArray;
	}

	private static JSONArray _getImplementedClassesJSONArray(
		JavaClass javaClass) {

		JSONArray implementedClassesJSONArray = new JSONArrayImpl();

		for (String implementedClassName :
				javaClass.getImplementedClassNames(true)) {

			implementedClassesJSONArray.put(implementedClassName);
		}

		return implementedClassesJSONArray;
	}

	private static JSONArray _getMethodsJSONArray(JavaClass javaClass) {
		JSONArray methodsJSONArray = new JSONArrayImpl();

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod() || childJavaTerm.isPrivate()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			JSONObject methodJSONObject = new JSONObjectImpl();

			methodJSONObject.put(
				"accessModifier", javaMethod.getAccessModifier());

			if (javaMethod.hasAnnotation("Deprecated")) {
				methodJSONObject.put("deprecated", true);
			}

			methodJSONObject.put("name", javaMethod.getName());

			if (javaMethod.hasAnnotation("Override")) {
				methodJSONObject.put("override", true);
			}

			JavaSignature javaSignature = null;

			try {
				javaSignature = javaMethod.getSignature();
			}
			catch (Exception exception) {
				continue;
			}

			List<JavaParameter> parameters = javaSignature.getParameters();

			if (!parameters.isEmpty()) {
				JSONArray parametersJSONArray = new JSONArrayImpl();

				for (JavaParameter javaParameter : parameters) {
					parametersJSONArray.put(
						javaParameter.getParameterType(true));
				}

				methodJSONObject.put("parameters", parametersJSONArray);
			}

			methodJSONObject.put(
				"returnType", javaSignature.getReturnType(true));

			methodsJSONArray.put(methodJSONObject);
		}

		return methodsJSONArray;
	}

	private static PathMatchers _getPathMatchers(
		String[] excludes, String[] includes,
		SourceFormatterExcludes sourceFormatterExcludes) {

		PathMatchers pathMatchers = new PathMatchers(FileSystems.getDefault());

		for (String exclude : excludes) {
			pathMatchers.addExcludeSyntaxPattern(
				new ExcludeSyntaxPattern(ExcludeSyntax.GLOB, exclude));
		}

		for (ExcludeSyntaxPattern excludeSyntaxPattern :
				sourceFormatterExcludes.getDefaultExcludeSyntaxPatterns()) {

			pathMatchers.addExcludeSyntaxPattern(excludeSyntaxPattern);
		}

		Map<String, List<ExcludeSyntaxPattern>> excludeSyntaxPatternsMap =
			sourceFormatterExcludes.getExcludeSyntaxPatternsMap();

		for (Map.Entry<String, List<ExcludeSyntaxPattern>> entry :
				excludeSyntaxPatternsMap.entrySet()) {

			pathMatchers.addExcludeSyntaxPatterns(
				entry.getKey(), entry.getValue());
		}

		for (String include : includes) {
			pathMatchers.addInclude(include);
		}

		return pathMatchers;
	}

	private static JSONArray _getVariablesJSONArray(JavaClass javaClass) {
		JSONArray variablesJSONArray = new JSONArrayImpl();

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaVariable() || childJavaTerm.isPrivate()) {
				continue;
			}

			JavaVariable javaVariable = (JavaVariable)childJavaTerm;

			JSONObject variableJSONObject = new JSONObjectImpl();

			variableJSONObject.put(
				"accessModifier", javaVariable.getAccessModifier()
			).put(
				"deprecated",
				() -> {
					if (javaVariable.hasAnnotation("Deprecated")) {
						return true;
					}

					return null;
				}
			).put(
				"lineNumber", javaVariable.getLineNumber()
			).put(
				"name", javaVariable.getName()
			);

			variablesJSONArray.put(variableJSONObject);
		}

		return variablesJSONArray;
	}

	private static List<String> _scanForFiles(
			final String baseDirName, final PathMatchers pathMatchers,
			final boolean includeSubrepositories)
		throws IOException {

		final List<String> fileNames = new ArrayList<>();

		Files.walkFileTree(
			Paths.get(baseDirName),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					if (Files.exists(
							dirPath.resolve("source_formatter.ignore"))) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					String currentDirPath = SourceUtil.getAbsolutePath(dirPath);

					if (!includeSubrepositories) {
						String baseDirPath = SourceUtil.getAbsolutePath(
							baseDirName);

						if (!baseDirPath.equals(currentDirPath)) {
							Path gitRepoPath = dirPath.resolve(".gitrepo");

							if (Files.exists(gitRepoPath)) {
								try {
									String content = FileUtil.read(
										gitRepoPath.toFile());

									if (content.contains("autopull = true")) {
										return FileVisitResult.SKIP_SUBTREE;
									}
								}
								catch (Exception exception) {
									if (_log.isDebugEnabled()) {
										_log.debug(exception, exception);
									}
								}
							}
						}
					}

					dirPath = _getCanonicalPath(dirPath);

					for (PathMatcher pathMatcher :
							pathMatchers.getExcludeDirPathMatchers()) {

						if (pathMatcher.matches(dirPath)) {
							return FileVisitResult.SKIP_SUBTREE;
						}
					}

					Map<String, List<PathMatcher>> excludeDirPathMatchersMap =
						pathMatchers.getExcludeDirPathMatchersMap();

					for (Map.Entry<String, List<PathMatcher>> entry :
							excludeDirPathMatchersMap.entrySet()) {

						String propertiesFileLocation = entry.getKey();

						if (currentDirPath.startsWith(propertiesFileLocation)) {
							for (PathMatcher pathMatcher : entry.getValue()) {
								if (pathMatcher.matches(dirPath)) {
									return FileVisitResult.SKIP_SUBTREE;
								}
							}
						}
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					Path canonicalPath = _getCanonicalPath(filePath);

					for (PathMatcher pathMatcher :
							pathMatchers.getExcludeFilePathMatchers()) {

						if (pathMatcher.matches(canonicalPath)) {
							return FileVisitResult.CONTINUE;
						}
					}

					String currentFilePath = SourceUtil.getAbsolutePath(
						filePath);

					Map<String, List<PathMatcher>> excludeFilePathMatchersMap =
						pathMatchers.getExcludeFilePathMatchersMap();

					for (Map.Entry<String, List<PathMatcher>> entry :
							excludeFilePathMatchersMap.entrySet()) {

						String propertiesFileLocation = entry.getKey();

						if (currentFilePath.startsWith(
								propertiesFileLocation)) {

							for (PathMatcher pathMatcher : entry.getValue()) {
								if (pathMatcher.matches(canonicalPath)) {
									return FileVisitResult.CONTINUE;
								}
							}
						}
					}

					for (PathMatcher pathMatcher :
							pathMatchers.getIncludeFilePathMatchers()) {

						if (!pathMatcher.matches(canonicalPath)) {
							continue;
						}

						fileNames.add(filePath.toString());

						return FileVisitResult.CONTINUE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return fileNames;
	}

	private static final String _DOCUMENTATION_URL =
		"https://github.com/liferay/liferay-portal/blob/master/modules/util" +
			"/source-formatter/src/main/resources/documentation/checks/";

	private static final String _SUPPRESSIONS_FILE_NAME =
		"source-formatter-suppressions.xml";

	private static final Log _log = LogFactoryUtil.getLog(
		SourceFormatterUtil.class);

	private static final Pattern _elementPattern = Pattern.compile(
		"<!ELEMENT ([\\w-]+) \\(");

	private static class PathMatchers {

		public PathMatchers(FileSystem fileSystem) {
			_fileSystem = fileSystem;
		}

		public void addExcludeSyntaxPattern(
			ExcludeSyntaxPattern excludeSyntaxPattern) {

			String excludePattern = excludeSyntaxPattern.getExcludePattern();
			ExcludeSyntax excludeSyntax =
				excludeSyntaxPattern.getExcludeSyntax();

			if (excludeSyntax.equals(ExcludeSyntax.GLOB) &&
				!excludePattern.startsWith("**/")) {

				excludePattern = "**/" + excludePattern;
			}

			if (excludeSyntax.equals(ExcludeSyntax.GLOB) &&
				excludePattern.endsWith("/**")) {

				excludePattern = excludePattern.substring(
					0, excludePattern.length() - 3);

				_excludeDirPathMatchers.add(
					_fileSystem.getPathMatcher(
						excludeSyntax.getValue() + ":" + excludePattern));
			}
			else if (excludeSyntax.equals(ExcludeSyntax.REGEX) &&
					 excludePattern.endsWith(
						 Pattern.quote(File.separator) + ".*")) {

				excludePattern = StringUtil.replaceLast(
					excludePattern, Pattern.quote(File.separator) + ".*",
					StringPool.BLANK);

				_excludeDirPathMatchers.add(
					_fileSystem.getPathMatcher(
						excludeSyntax.getValue() + ":" + excludePattern));
			}
			else {
				_excludeFilePathMatchers.add(
					_fileSystem.getPathMatcher(
						excludeSyntax.getValue() + ":" + excludePattern));
			}
		}

		public void addExcludeSyntaxPatterns(
			String propertiesFileLocation,
			List<ExcludeSyntaxPattern> excludeSyntaxPatterns) {

			List<PathMatcher> excludeDirPathMatcherList = new ArrayList<>();
			List<PathMatcher> excludeFilePathMatcherList = new ArrayList<>();

			for (ExcludeSyntaxPattern excludeSyntaxPattern :
					excludeSyntaxPatterns) {

				String excludePattern =
					excludeSyntaxPattern.getExcludePattern();
				ExcludeSyntax excludeSyntax =
					excludeSyntaxPattern.getExcludeSyntax();

				if (excludeSyntax.equals(ExcludeSyntax.GLOB) &&
					!excludePattern.startsWith("**/")) {

					excludePattern = "**/" + excludePattern;
				}

				if (excludeSyntax.equals(ExcludeSyntax.GLOB) &&
					excludePattern.endsWith("/**")) {

					excludePattern = excludePattern.substring(
						0, excludePattern.length() - 3);

					excludeDirPathMatcherList.add(
						_fileSystem.getPathMatcher(
							excludeSyntax.getValue() + ":" + excludePattern));
				}
				else if (excludeSyntax.equals(ExcludeSyntax.REGEX) &&
						 excludePattern.endsWith(
							 Pattern.quote(File.separator) + ".*")) {

					excludePattern = StringUtil.replaceLast(
						excludePattern, Pattern.quote(File.separator) + ".*",
						StringPool.BLANK);

					excludeDirPathMatcherList.add(
						_fileSystem.getPathMatcher(
							excludeSyntax.getValue() + ":" + excludePattern));
				}
				else {
					excludeFilePathMatcherList.add(
						_fileSystem.getPathMatcher(
							excludeSyntax.getValue() + ":" + excludePattern));
				}
			}

			_excludeDirPathMatchersMap.put(
				propertiesFileLocation, excludeDirPathMatcherList);
			_excludeFilePathMatchersMap.put(
				propertiesFileLocation, excludeFilePathMatcherList);
		}

		public void addInclude(String include) {
			_includeFilePathMatchers.add(
				_fileSystem.getPathMatcher("glob:" + include));
		}

		public List<PathMatcher> getExcludeDirPathMatchers() {
			return _excludeDirPathMatchers;
		}

		public Map<String, List<PathMatcher>> getExcludeDirPathMatchersMap() {
			return _excludeDirPathMatchersMap;
		}

		public List<PathMatcher> getExcludeFilePathMatchers() {
			return _excludeFilePathMatchers;
		}

		public Map<String, List<PathMatcher>> getExcludeFilePathMatchersMap() {
			return _excludeFilePathMatchersMap;
		}

		public List<PathMatcher> getIncludeFilePathMatchers() {
			return _includeFilePathMatchers;
		}

		private List<PathMatcher> _excludeDirPathMatchers = new ArrayList<>();
		private Map<String, List<PathMatcher>> _excludeDirPathMatchersMap =
			new HashMap<>();
		private List<PathMatcher> _excludeFilePathMatchers = new ArrayList<>();
		private Map<String, List<PathMatcher>> _excludeFilePathMatchersMap =
			new HashMap<>();
		private final FileSystem _fileSystem;
		private List<PathMatcher> _includeFilePathMatchers = new ArrayList<>();

	}

}