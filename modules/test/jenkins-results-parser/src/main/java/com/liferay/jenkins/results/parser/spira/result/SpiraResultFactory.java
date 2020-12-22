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

package com.liferay.jenkins.results.parser.spira.result;

import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.CucumberAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.CucumberBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.JUnitAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Peter Yoo
 */
public class SpiraResultFactory {

	public static SpiraBuildResult newSpiraBuildResult(
		TopLevelBuild topLevelBuild) {

		String key = topLevelBuild.getBuildURL();

		SpiraBuildResult spiraBuildResult = _spiraBuildResults.get(key);

		if (spiraBuildResult != null) {
			return spiraBuildResult;
		}

		spiraBuildResult = new DefaultSpiraBuildResult(topLevelBuild);

		_spiraBuildResults.put(key, spiraBuildResult);

		return spiraBuildResult;
	}

	public static SpiraTestResult newSpiraTestResult(
		SpiraBuildResult spiraBuildResult,
		AxisTestClassGroup axisTestClassGroup,
		TestClassGroup.TestClass testClass) {

		if (axisTestClassGroup == null) {
			return new TopLevelSpiraTestResult(spiraBuildResult);
		}

		if (axisTestClassGroup instanceof CucumberAxisTestClassGroup) {
			return new CucumberAxisSpiraTestResult(
				spiraBuildResult,
				(CucumberAxisTestClassGroup)axisTestClassGroup,
				(CucumberBatchTestClassGroup.CucumberTestClass)testClass);
		}

		if (axisTestClassGroup instanceof FunctionalAxisTestClassGroup) {
			return new FunctionalAxisSpiraTestResult(
				spiraBuildResult,
				(FunctionalAxisTestClassGroup)axisTestClassGroup,
				(FunctionalBatchTestClassGroup.FunctionalTestClass)testClass);
		}

		if (axisTestClassGroup instanceof JUnitAxisTestClassGroup) {
			return new JUnitAxisSpiraTestResult(
				spiraBuildResult, (JUnitAxisTestClassGroup)axisTestClassGroup,
				testClass);
		}

		return new BatchAxisSpiraTestResult(
			spiraBuildResult, axisTestClassGroup);
	}

	public static SpiraTestResultDetails newSpiraTestResultDetails(
		SpiraTestResult spiraTestResult) {

		if (spiraTestResult instanceof FunctionalAxisSpiraTestResult) {
			return new FunctionalAxisSpiraTestResultDetails(
				(FunctionalAxisSpiraTestResult)spiraTestResult);
		}

		if (spiraTestResult instanceof JUnitAxisSpiraTestResult) {
			return new JUnitAxisSpiraTestResultDetails(
				(JUnitAxisSpiraTestResult)spiraTestResult);
		}

		if (spiraTestResult instanceof AxisSpiraTestResult) {
			return new DefaultAxisSpiraTestResultDetails(
				(AxisSpiraTestResult)spiraTestResult);
		}

		return new DefaultSpiraTestResultDetails(spiraTestResult);
	}

	public static SpiraTestResultValues newSpiraTestResultValues(
		SpiraTestResult spiraTestResult) {

		if (spiraTestResult instanceof FunctionalAxisSpiraTestResult) {
			return new FunctionalAxisSpiraTestResultValues(
				(FunctionalAxisSpiraTestResult)spiraTestResult);
		}

		if (spiraTestResult instanceof JUnitAxisSpiraTestResult) {
			return new JUnitAxisSpiraTestResultValues(
				(JUnitAxisSpiraTestResult)spiraTestResult);
		}

		if (spiraTestResult instanceof AxisSpiraTestResult) {
			return new DefaultAxisSpiraTestResultValues(
				(AxisSpiraTestResult)spiraTestResult);
		}

		return new DefaultSpiraTestResultValues(spiraTestResult);
	}

	private static final Map<String, SpiraBuildResult> _spiraBuildResults =
		new HashMap<>();

}