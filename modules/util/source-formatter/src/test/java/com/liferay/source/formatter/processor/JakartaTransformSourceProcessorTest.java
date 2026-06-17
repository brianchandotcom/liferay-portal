/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import com.liferay.source.formatter.SourceFormatterArgs;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alan Huang
 */
public class JakartaTransformSourceProcessorTest
	extends BaseSourceProcessorTestCase {

	@Test
	public void testBNDJakartaTransform() throws Exception {
		test("jakartatransform/JakartaTransform.testbnd");
	}

	@Test
	public void testFTLJakartaTransform() throws Exception {
		test("jakartatransform/JakartaTransform.testftl");
	}

	@Test
	public void testGradleJakartaTransform() throws Exception {
		_testJakartaTransformDependenciesFile(
			null, "jakartatransform/JakartaTransform.testgradle");

		_testJakartaTransformDependenciesFile(
			"src/test/resources/com/liferay/source/formatter/dependencies" +
				"/jakartatransform/jakarta-transform-dependencies.txt",
			"jakartatransform/JakartaTransformExternalDependencies.testgradle");

		_testInvalidJakartaTransformDependenciesFile(
			"/jakarta-transform-dependencies-does-not-exist.txt",
			"Unable to read file");

		_testInvalidJakartaTransformDependenciesFile(
			"src/test/resources/com/liferay/source/formatter/dependencies" +
				"/jakartatransform/jakarta-transform-dependencies-invalid.txt",
			"Invalid line");
	}

	@Test
	public void testJavaJakartaTransform() throws Exception {
		test("jakartatransform/JakartaTransform.testjava");
	}

	@Test
	public void testJSPJakartaTransform() throws Exception {
		test("jakartatransform/JakartaTransform.testjsp");
	}

	@Test
	public void testXMLJakartaTransform() throws Exception {
		test("jakartatransform/JakartaTransform.testxml");
	}

	@Override
	protected SourceFormatterArgs getSourceFormatterArgs() {
		SourceFormatterArgs sourceFormatterArgs =
			super.getSourceFormatterArgs();

		sourceFormatterArgs.setCheckCategoryNames(
			Arrays.asList("JakartaTransform"));

		if (_jakartaTransformDependenciesFilePath != null) {
			sourceFormatterArgs.setSourceFormatterProperties(
				Collections.singletonList(
					"jakarta.transform.dependencies.file.path=" +
						_jakartaTransformDependenciesFilePath));
		}

		return sourceFormatterArgs;
	}

	private void _testInvalidJakartaTransformDependenciesFile(
			String jakartaTransformDependenciesFilePath, String expectedMessage)
		throws Exception {

		try {
			_testJakartaTransformDependenciesFile(
				jakartaTransformDependenciesFilePath,
				"jakartatransform/JakartaTransform.testgradle");

			Assert.fail();
		}
		catch (Exception exception) {
			Throwable throwable = exception;

			while (throwable.getCause() != null) {
				throwable = throwable.getCause();
			}

			Assert.assertTrue(throwable instanceof IOException);

			String message = throwable.getMessage();

			Assert.assertTrue(message.contains(expectedMessage));
		}
	}

	private void _testJakartaTransformDependenciesFile(
			String jakartaTransformDependenciesFilePath, String testFileName)
		throws Exception {

		_jakartaTransformDependenciesFilePath =
			jakartaTransformDependenciesFilePath;

		test(testFileName);
	}

	private String _jakartaTransformDependenciesFilePath;

}