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

import org.junit.Test;

/**
 * @author Alan Huang
 */
public class JSONSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testCheckMissingScripts() throws Exception {
		test(
			"CheckMissingScripts1/package.testjson",
			new String[] {
				"For Using 'liferay-npm-scripts', 'csf' should be enforced",
				"For Using 'liferay-npm-scripts', 'format' should be enforced"
			});

		test(
			"CheckMissingScripts2/package.testjson",
			new String[] {
				"For Using 'liferay-npm-scripts', 'csf' should be enforced",
				"For Using 'liferay-npm-scripts', 'format' should be enforced"
			});

		test(
			"CheckMissingScripts3/package.testjson",
			new String[] {
				"For Using 'liferay-npm-scripts', 'csf' should be enforced",
				"For Using 'liferay-npm-scripts', 'format' should be enforced"
			});

		test(
			"CheckMissingScripts4/package.testjson",
			new String[] {
				"For Using 'liferay-npm-scripts', 'format' should be enforced"
			});
	}

	@Test
	public void testJSONDeprecatedPackagesCheck() throws Exception {
		test(
			"JSONDeprecatedPackages/package.testjson",
			new String[] {
				"Do not use deprecated package " +
					"'liferay-module-config-generator'",
				"Do not use deprecated package 'metal-cli'"
			},
			new Integer[] {4, 5});
	}

}