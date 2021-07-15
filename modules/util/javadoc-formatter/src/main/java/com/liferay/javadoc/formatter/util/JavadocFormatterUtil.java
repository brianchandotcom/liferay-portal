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

package com.liferay.javadoc.formatter.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaAnnotatedElement;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaConstructor;
import com.thoughtworks.qdox.model.JavaExecutable;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaType;

import java.io.File;
import java.io.IOException;

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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class JavadocFormatterUtil {

	public static Document getDeprecationsDocument(String dirName)
		throws Exception {

		Document document = DocumentHelper.createDocument();

		Element rootElement = document.addElement("deprecations");

		List<String> fileNames = scanForFiles(
			dirName,
			new String[] {
				"**/.git/**", "**/.gradle/**", "**/bin/**", "**/build/**",
				"**/classes/**", "**/node_modules/**",
				"**/node_modules_cache/**", "**/portal-client/**", "**/tmp/**"
			},
			new String[] {"**/*.java"});

		for (String fileName : fileNames) {
			fileName = StringUtil.replace(
				fileName, CharPool.BACK_SLASH, CharPool.SLASH);

			File file = new File(dirName + fileName);

			String content = read(file);

			JavaProjectBuilder javaProjectBuilder = new JavaProjectBuilder();

			try {
				javaProjectBuilder.addSource(new UnsyncStringReader(content));
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}

				continue;
			}

			String fullyQualifiedName = _getFullyQualifiedName(
				fileName, content);

			JavaClass javaClass = javaProjectBuilder.getClassByName(
				fullyQualifiedName);

			_parseClass(rootElement, javaClass, fullyQualifiedName);
		}

		return document;
	}

	public static String read(File file) throws IOException {
		String s = new String(
			Files.readAllBytes(file.toPath()), StringPool.UTF8);

		return StringUtil.replace(
			s, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
	}

	public static List<String> scanForFiles(
			String dirName, String[] excludes, String[] includes)
		throws Exception {

		final List<String> fileNames = new ArrayList<>();

		FileSystem fileSystem = FileSystems.getDefault();

		final List<PathMatcher> excludeDirPathMatchers = new ArrayList<>();

		for (String exclude : excludes) {
			if (exclude.endsWith("/**")) {
				exclude = exclude.substring(0, exclude.length() - 3);
			}

			excludeDirPathMatchers.add(
				fileSystem.getPathMatcher("glob:" + exclude));
		}

		final List<PathMatcher> includeFilePathMatchers = new ArrayList<>();

		for (String include : includes) {
			includeFilePathMatchers.add(
				fileSystem.getPathMatcher("glob:" + include));
		}

		Files.walkFileTree(
			Paths.get(dirName),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					for (PathMatcher pathMatcher : excludeDirPathMatchers) {
						if (pathMatcher.matches(_getCanonicalPath(dirPath))) {
							return FileVisitResult.SKIP_SUBTREE;
						}
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					for (PathMatcher pathMatcher : includeFilePathMatchers) {
						if (!pathMatcher.matches(_getCanonicalPath(filePath))) {
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

	public static String syncDeprecatedVersion(
		String value, JavaAnnotatedElement javaAnnotatedElement,
		String annotatedElementName, String fullyQualifiedName,
		Document deprecationsDocument) {

		Element rootElement = deprecationsDocument.getRootElement();

		if (rootElement == null) {
			return value;
		}

		String version = _getDeprecatedVersion(value);

		if (version == null) {
			return value;
		}

		String expectedVersion = _getExpectedVersion(
			rootElement, javaAnnotatedElement, annotatedElementName,
			fullyQualifiedName);

		if (expectedVersion == null) {
			return value;
		}

		if (!expectedVersion.equals(version)) {
			return StringUtil.replaceFirst(value, version, expectedVersion);
		}

		return value;
	}

	private static void _addDeprecation(
		Element rootElement, String name,
		JavaAnnotatedElement javaAnnotatedElement, String fullyQualifiedName) {

		List<DocletTag> docletTags = javaAnnotatedElement.getTagsByName(
			"deprecated");

		if (docletTags.isEmpty()) {
			return;
		}

		DocletTag docletTag = docletTags.get(0);

		String version = _getDeprecatedVersion(docletTag.getValue());

		if (version == null) {
			return;
		}

		Element deprecatedElement = rootElement.addElement("deprecated");

		deprecatedElement.addAttribute(
			"fullyQualifiedName", fullyQualifiedName);
		deprecatedElement.addAttribute("name", name);

		Class<?> clazz = javaAnnotatedElement.getClass();

		deprecatedElement.addAttribute("type", clazz.getSimpleName());

		deprecatedElement.addAttribute("version", version);

		if (javaAnnotatedElement instanceof JavaExecutable) {
			JavaExecutable javaExecutable =
				(JavaExecutable)javaAnnotatedElement;

			List<JavaType> javaTypes = javaExecutable.getParameterTypes();

			deprecatedElement.addAttribute("signature", javaTypes.toString());
		}
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

	private static String _getDeprecatedVersion(String value) {
		Matcher matcher = _deprecatedVersionPattern.matcher(value);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
	}

	private static String _getExpectedVersion(
		Element rootElement, JavaAnnotatedElement javaAnnotatedElement,
		String annotatedElementName, String fullyQualifiedName) {

		for (Element deprecatedElement :
				(List<Element>)rootElement.elements("deprecated")) {

			if (!annotatedElementName.equals(
					deprecatedElement.attributeValue("name")) ||
				!fullyQualifiedName.equals(
					deprecatedElement.attributeValue("fullyQualifiedName"))) {

				continue;
			}

			Class<?> clazz = javaAnnotatedElement.getClass();

			String type = clazz.getSimpleName();

			if (!type.equals(deprecatedElement.attributeValue("type"))) {
				continue;
			}

			if (javaAnnotatedElement instanceof JavaExecutable) {
				JavaExecutable javaExecutable =
					(JavaExecutable)javaAnnotatedElement;

				String signature = String.valueOf(
					javaExecutable.getParameterTypes());

				if (!signature.equals(
						deprecatedElement.attributeValue("signature"))) {

					continue;
				}
			}

			return deprecatedElement.attributeValue("version");
		}

		return null;
	}

	private static String _getFullyQualifiedName(
		String fileName, String content) {

		Matcher matcher = _packagePattern.matcher(content);

		if (!matcher.find()) {
			return StringPool.BLANK;
		}

		int x = fileName.lastIndexOf(CharPool.SLASH);
		int y = fileName.lastIndexOf(CharPool.PERIOD);

		return StringBundler.concat(
			matcher.group(2), StringPool.PERIOD, fileName.substring(x + 1, y));
	}

	private static void _parseClass(
		Element rootElement, JavaClass javaClass, String fullyQualifiedName) {

		_addDeprecation(
			rootElement, javaClass.getName(), javaClass, fullyQualifiedName);

		for (JavaConstructor javaConstructor : javaClass.getConstructors()) {
			_addDeprecation(
				rootElement, javaConstructor.getName(), javaConstructor,
				fullyQualifiedName);
		}

		for (JavaMethod javaMethod : javaClass.getMethods()) {
			_addDeprecation(
				rootElement, javaMethod.getName(), javaMethod,
				fullyQualifiedName);
		}

		for (JavaField javaField : javaClass.getFields()) {
			_addDeprecation(
				rootElement, javaField.getName(), javaField,
				fullyQualifiedName);
		}

		for (JavaClass nestedJavaClass : javaClass.getNestedClasses()) {
			_parseClass(rootElement, nestedJavaClass, fullyQualifiedName);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JavadocFormatterUtil.class);

	private static final Pattern _deprecatedVersionPattern = Pattern.compile(
		"As of (\\w+ \\([\\w.]+\\))");
	private static final Pattern _packagePattern = Pattern.compile(
		"(\n|^)\\s*package (.*);\n");

}