/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.modules.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;

/**
 * @author Andrea Di Giorgi
 */
public class ModulesStructureTestUtil {

	public static int compare(String moduleVersion1, String moduleVersion2) {
		int major1 = 0;
		int minor1 = 0;
		int micro1 = 0;

		Matcher matcher = _moduleVersionPattern.matcher(moduleVersion1);

		if (matcher.matches()) {
			major1 = GetterUtil.getInteger(matcher.group(1));
			minor1 = GetterUtil.getInteger(matcher.group(3));
			micro1 = GetterUtil.getInteger(matcher.group(5));
		}

		int major2 = 0;
		int minor2 = 0;
		int micro2 = 0;

		matcher = _moduleVersionPattern.matcher(moduleVersion2);

		if (matcher.matches()) {
			major2 = GetterUtil.getInteger(matcher.group(1));
			minor2 = GetterUtil.getInteger(matcher.group(3));
			micro2 = GetterUtil.getInteger(matcher.group(5));
		}

		int result = Integer.compare(major1, major2);

		if (result != 0) {
			return result;
		}

		result = Integer.compare(minor1, minor2);

		if (result != 0) {
			return result;
		}

		return Integer.compare(micro1, micro2);
	}

	public static boolean contains(Path path, String... strings)
		throws IOException {

		try (FileReader fileReader = new FileReader(path.toFile());
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(fileReader)) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				for (String s : strings) {
					if (line.contains(s)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public static String getAbsolutePath(Path path) {
		Path absolutePath = path.toAbsolutePath();

		absolutePath = absolutePath.normalize();

		return StringUtil.replace(
			absolutePath.toString(), File.separatorChar, CharPool.SLASH);
	}

	public static List<GradleDependency> getGradleDependencies(
			String gradleContent, Path gradlePath, Path rootDirPath)
		throws IOException {

		List<GradleDependency> gradleDependencies = new ArrayList<>();

		_addGradleModuleDependencies(
			gradleDependencies, gradleContent, gradlePath);
		_addGradleProjectDependencies(
			gradleDependencies, gradleContent, gradlePath, rootDirPath);

		return gradleDependencies;
	}

	public static String read(Path path) throws IOException {
		Assert.assertTrue("Missing " + path, Files.exists(path));

		String s = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

		return StringUtil.replace(
			s, System.lineSeparator(), StringPool.NEW_LINE);
	}

	private static void _addGradleModuleDependencies(
		List<GradleDependency> gradleDependencies, String gradleContent,
		Path gradlePath) {

		Matcher matcher = _gradleModuleDependencyPattern.matcher(gradleContent);

		while (matcher.find()) {
			String dependency = matcher.group();

			String configuration = matcher.group(1);
			String moduleGroup = matcher.group(2);
			String moduleName = matcher.group(3);
			String moduleVersion = matcher.group(4);

			try {
				GradleDependency gradleDependency = new GradleDependency(
					dependency, configuration, moduleGroup, moduleName,
					moduleVersion, false);

				gradleDependencies.add(gradleDependency);
			}
			catch (IllegalArgumentException illegalArgumentException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Ignoring dependency in ", gradlePath,
							" since version ", moduleVersion,
							" cannot be parsed: ", dependency),
						illegalArgumentException);
				}
			}
		}
	}

	private static void _addGradleProjectDependencies(
			List<GradleDependency> gradleDependencies, String gradleContent,
			Path gradlePath, Path rootDirPath)
		throws IOException {

		Matcher matcher = _gradleProjectDependencyPattern.matcher(
			gradleContent);

		while (matcher.find()) {
			String dependency = matcher.group();

			String projectPath = matcher.group(2);

			int pathEndQuote = projectPath.indexOf('"');

			if (pathEndQuote == -1) {
				pathEndQuote = projectPath.indexOf("'");
			}

			if (pathEndQuote > -1) {
				projectPath = projectPath.substring(0, pathEndQuote);
			}

			String projectDirName = StringUtil.replace(
				projectPath.substring(1), CharPool.COLON, File.separatorChar);

			Path projectDirPath = rootDirPath.resolve(projectDirName);

			Assert.assertTrue(
				StringBundler.concat(
					"Dependency in ", gradlePath,
					" points to nonexistent project directory ", projectDirPath,
					": ", matcher.group()),
				Files.exists(projectDirPath));

			Path bndBndPath = projectDirPath.resolve("bnd.bnd");

			Properties bndProperties = new Properties();

			try (InputStream inputStream = Files.newInputStream(bndBndPath)) {
				bndProperties.load(inputStream);
			}
			catch (NoSuchFileException noSuchFileException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Ignoring dependency in ", gradlePath,
							" since it points to a non-OSGi project: ",
							matcher.group()),
						noSuchFileException);
				}

				continue;
			}

			String moduleGroup = "com.liferay";
			String moduleName = bndProperties.getProperty(
				"Bundle-SymbolicName");
			String moduleVersion = bndProperties.getProperty("Bundle-Version");

			String configuration = matcher.group(1);

			GradleDependency gradleDependency = new GradleDependency(
				dependency, configuration, moduleGroup, moduleName,
				moduleVersion, true);

			gradleDependencies.add(gradleDependency);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ModulesStructureTestUtil.class);

	private static final Pattern _gradleModuleDependencyPattern =
		Pattern.compile(
			"(\\w+)(?:\\s|\\()+group:\\s*['\"](.+)['\"],\\s*" +
				"name:\\s*['\"](.+)['\"],\\s*(?:transitive:\\s*\\w+,\\s*)?" +
					"version:\\s*['\"](.+)['\"]");
	private static final Pattern _gradleProjectDependencyPattern =
		Pattern.compile("(\\w+)\\s+project\\(['\"](.+)['\"]\\)");
	private static final Pattern _moduleVersionPattern = Pattern.compile(
		"(\\d{1,10})(\\.(\\d{1,10})(\\.(\\d{1,10})(\\.([-_\\da-zA-Z]+))?)?)?");

}