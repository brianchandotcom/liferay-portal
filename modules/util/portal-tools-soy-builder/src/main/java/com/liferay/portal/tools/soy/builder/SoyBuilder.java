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

package com.liferay.portal.tools.soy.builder;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import com.liferay.portal.tools.soy.builder.internal.util.FileUtil;
import com.liferay.portal.tools.soy.builder.internal.util.Validator;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Gregory Amerson
 */
public class SoyBuilder {

	public static void main(String[] args) throws Exception {
		SoyBuilderArgs soyBuilderArgs = new SoyBuilderArgs();

		JCommander jCommander = new JCommander(soyBuilderArgs);

		try {
			File jarFile = FileUtil.getJarFile();

			if (jarFile.isFile()) {
				jCommander.setProgramName("java -jar " + jarFile.getName());
			}
			else {
				jCommander.setProgramName(SoyBuilder.class.getName());
			}

			jCommander.parse(args);

			if (soyBuilderArgs.isHelp()) {
				_printHelp(jCommander);
			}
			else {
				SoyBuilder soyBuilder = new SoyBuilder(soyBuilderArgs);

				soyBuilder.build();
			}
		}
		catch (ParameterException pe) {
			System.err.println(pe.getMessage());

			_printHelp(jCommander);
		}
	}

	public SoyBuilder(
		File baseDir, File nodeExecutableFile, File nodeModulesDir,
		File outputDir) {

		_baseDir = baseDir;
		_nodeExecutableFile = nodeExecutableFile;
		_nodeModulesDir = nodeModulesDir;
		_outputDir = outputDir;
	}

	public SoyBuilder(SoyBuilderArgs soyBuilderArgs) {
		this(
			soyBuilderArgs.getBaseDir(), soyBuilderArgs.getNodeExecutableFile(),
			soyBuilderArgs.getNodeModulesDir(), soyBuilderArgs.getOutputDir());
	}

	public void build() throws Exception {
		_transpileJS();

		_configJSModules();

		_replaceSoyTranslation();
	}

	private static void _printHelp(JCommander jCommander) throws Exception {
		jCommander.usage();
	}

	private void _appendArgumentReplaces(
		StringBuilder sb, String argumentsObject, String variableName) {

		int i = 0;

		Matcher matcher = _argumentsObjectPattern.matcher(argumentsObject);

		while (matcher.find()) {
			sb.append(System.lineSeparator());

			sb.append(variableName);
			sb.append(" = ");
			sb.append(variableName);
			sb.append(".replace('{");
			sb.append(i);
			sb.append("}', ");
			sb.append(matcher.group(1));
			sb.append(");");

			i++;
		}
	}

	private String _call(
		String variableName, String languageKey, String argumentsObject) {

		StringBuilder sb = new StringBuilder();

		sb.append("var ");
		sb.append(variableName);

		// Split string to avoid SF error

		sb.append(" = Liferay.Language");
		sb.append(".get('");

		sb.append(_fixLanguageKey(languageKey));
		sb.append("');");

		if (Validator.isNotNull(argumentsObject)) {
			_appendArgumentReplaces(sb, argumentsObject, variableName);
		}

		return sb.toString();
	}

	private void _configJSModules() throws Exception {
		File jsFile = new File(
			_nodeModulesDir, "liferay-module-config-generator/bin/index.js");

		if (!jsFile.exists()) {
			throw new IllegalStateException(
				"Could not file " + jsFile.getName() + " in node_modules.");
		}

		File packageJsonFile = new File(_baseDir, "package.json");

		if (!packageJsonFile.exists()) {
			throw new IllegalStateException(
				"Could not file package.json file in basedir.");
		}

		List<String> commands = new ArrayList<>();

		commands.add(_nodeExecutableFile.getCanonicalPath());
		commands.add(jsFile.getCanonicalPath());
		commands.add("--config");
		commands.add("");
		commands.add("--extension");
		commands.add("");
		commands.add("--filePattern");
		commands.add("**/resources/*.js");
		commands.add("--format");
		commands.add("/_/g,-");
		commands.add("--ignorePath");
		commands.add("true");
		commands.add("--moduleConfig");
		commands.add(_baseDir.getAbsolutePath() + "/package.json");
		commands.add("--output");
		commands.add(_outputDir.getAbsolutePath() + "/META-INF/config.json");
		commands.add("--moduleRoot");
		commands.add(_outputDir.getAbsolutePath() + "/META-INF/resources");
		commands.add(_outputDir.getAbsolutePath() + "/META-INF");

		ProcessBuilder processBuilder = new ProcessBuilder(commands);

		processBuilder.directory(_baseDir.getAbsoluteFile());

		processBuilder.inheritIO();

		Process process = processBuilder.start();

		int exitCode = process.waitFor();

		if (exitCode > 0) {
			throw new IllegalStateException(
				"Node executable returned error: " + exitCode);
		}
	}

	private String _fixLanguageKey(String languageKey) {
		Matcher matcher = _languageKeyPlaceholderPattern.matcher(languageKey);

		return matcher.replaceAll("x");
	}

	private void _replaceSoyTranslation() throws IOException {
		final List<Path> soyJsFiles = new ArrayList<>();

		Files.walkFileTree(
			_outputDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path file, BasicFileAttributes attrs)
					throws IOException {

					if (file.toString().endsWith(".soy.js")) {
						soyJsFiles.add(file);
					}

					return super.visitFile(file, attrs);
				}

			});

		for (Path soyJsFile : soyJsFiles) {
			String content = new String(
				Files.readAllBytes(soyJsFile), StandardCharsets.UTF_8);

			Matcher matcher = _pattern.matcher(content);

			StringBuffer sb = new StringBuffer();

			boolean found = false;

			while (matcher.find()) {
				found = true;

				String replacement = _call(
					matcher.group(1), matcher.group(2), matcher.group(3));

				matcher.appendReplacement(sb, replacement);
			}

			matcher.appendTail(sb);

			if (found) {
				content = sb.toString();

				Files.write(
					soyJsFile, content.getBytes(StandardCharsets.UTF_8));
			}
		}
	}

	private void _transpileJS() throws Exception {
		File jsFile = new File(_nodeModulesDir, "metal-cli/index.js");

		if (!jsFile.exists()) {
			throw new IllegalStateException(
				"Could not file metal-cli/index.js in node_modules.");
		}

		List<String> commands = new ArrayList<>();

		commands.add(_nodeExecutableFile.getAbsolutePath());
		commands.add(jsFile.getAbsolutePath());
		commands.add("build");
		commands.add("--bundleFileName");
		commands.add("");
		commands.add("--dest");
		commands.add(_outputDir.getAbsolutePath() + "/META-INF/resources");
		commands.add("--format");
		commands.add("amd");
		commands.add("--globalName");
		commands.add("");
		commands.add("--moduleName");
		commands.add("");
		commands.add("--soyDeps");
		commands.add(
			_nodeModulesDir.getAbsolutePath() + "/lexicon*/src/**/*.soy");
		commands.add(
			_nodeModulesDir.getAbsolutePath() + "/metal*/src/**/*.soy");
		commands.add("--soyDest");
		commands.add(_outputDir.getAbsolutePath() + "/META-INF/resources");
		commands.add("--soySrc");
		commands.add("**/*.soy");
		commands.add("--src");
		commands.add("**/*.es.js");
		commands.add("**/*.soy.js");

		ProcessBuilder processBuilder = new ProcessBuilder(commands);

		processBuilder.directory(
			new File(_outputDir.getAbsoluteFile(), "META-INF/resources"));

		processBuilder.inheritIO();

		Process process = processBuilder.start();

		int exitCode = process.waitFor();

		if (exitCode > 0) {
			throw new IllegalStateException(
				"Node executable returned error: " + exitCode);
		}
	}

	private static final Pattern _argumentsObjectPattern = Pattern.compile(
		"'.+'\\s*:\\s*([\\d\\w\\._]+)+");
	private static final Pattern _languageKeyPlaceholderPattern =
		Pattern.compile("\\{\\$\\w+\\}");
	private static final Pattern _pattern = Pattern.compile(
		"var (MSG_EXTERNAL_\\d+) = goog\\.getMsg\\(\\s*'([\\w-\\{\\}\\$]+)'" +
			"\\s*(?:,\\s*\\{([\\s\\S]+?)\\})?\\);");

	private final File _baseDir;
	private final File _nodeExecutableFile;
	private final File _nodeModulesDir;
	private final File _outputDir;

}