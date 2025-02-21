/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.local.transform;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.util.Objects;

import org.gradle.api.GradleException;
import org.gradle.api.artifacts.transform.InputArtifact;
import org.gradle.api.artifacts.transform.TransformAction;
import org.gradle.api.artifacts.transform.TransformOutputs;
import org.gradle.api.artifacts.transform.TransformParameters;
import org.gradle.api.file.FileSystemLocation;
import org.gradle.api.logging.Logger;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Classpath;

/**
 * @author Drew Brokke
 */
public abstract class JakartaTransformerTransformAction
	implements TransformAction<TransformParameters.None> {

	public static final String ARTIFACT_TYPE = "jakarta-transformed-jar";

	public static void setClassLoader(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	public static void setLogger(Logger logger) {
		_logger = logger;
	}

	public static void setPropertiesFile(File propertiesFile) {
		_propertiesFile = propertiesFile;
	}

	@Classpath
	@InputArtifact
	public abstract Provider<FileSystemLocation> getInputArtifact();

	@Override
	public void transform(TransformOutputs outputs) {
		Provider<FileSystemLocation> inputArtifact = getInputArtifact();

		FileSystemLocation fileSystemLocation = inputArtifact.get();

		File inputJarFile = fileSystemLocation.getAsFile();

		String inputJarFileName = inputJarFile.getName();

		String fileName = inputJarFileName;

		if (fileName.contains(".")) {
			fileName = fileName.replaceFirst(
				"(\\.[^.]+)$", "-JAKARTA-SNAPSHOT$1");
		}

		File outputJarFile = outputs.file(fileName);

		try {
			Class<?> jakartaTransformerCLIClass = Class.forName(
				"org.eclipse.transformer.cli.JakartaTransformerCLI", true,
				_classLoader);

			Constructor<?> jakartaTransformerCLIClassConstructor =
				jakartaTransformerCLIClass.getConstructor(
					PrintStream.class, PrintStream.class, String[].class);
			Method runMethod = jakartaTransformerCLIClass.getMethod("run");

			PrintStream errorPrintStream = new PrintStream(
				new ByteArrayOutputStream());
			PrintStream standardPrintStream = new PrintStream(
				new ByteArrayOutputStream());

			Object resultCode = runMethod.invoke(
				jakartaTransformerCLIClassConstructor.newInstance(
					standardPrintStream, errorPrintStream,
					new String[] {
						"--quiet", "--versions",
						_propertiesFile.getAbsolutePath(),
						inputJarFile.getAbsolutePath(),
						outputJarFile.getAbsolutePath()
					}));

			if (!Objects.equals(resultCode.toString(), "Success")) {
				throw new GradleException(
					String.format(
						"Jakarta: ERROR %s\n%s", resultCode, errorPrintStream));
			}

			_logger.info("Jakarta:  {} -> {}", inputJarFile, outputJarFile);
		}
		catch (Exception exception) {
			throw new GradleException(
				"Transform failed for artifact " + inputJarFileName, exception);
		}
	}

	private static Logger _logger;

	private static ClassLoader _classLoader;
	private static File _propertiesFile;

}