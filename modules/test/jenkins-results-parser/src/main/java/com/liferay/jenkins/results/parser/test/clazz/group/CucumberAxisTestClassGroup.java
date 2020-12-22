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

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.test.clazz.group.CucumberBatchTestClassGroup.CucumberTestClass;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class CucumberAxisTestClassGroup extends AxisTestClassGroup {

	public List<CucumberTestClass> getCucumberTestClasses() {
		List<CucumberTestClass> cucumberTestClasses = new ArrayList<>();

		for (TestClass testClass : getTestClasses()) {
			if (!(testClass instanceof CucumberTestClass)) {
				continue;
			}

			cucumberTestClasses.add((CucumberTestClass)testClass);
		}

		return cucumberTestClasses;
	}

	public List<String> getScenarioNames() {
		List<String> scenarioNames = new ArrayList<>();

		for (CucumberTestClass cucumberTestClass : getCucumberTestClasses()) {
			scenarioNames.add(
				JenkinsResultsParserUtil.combine(
					cucumberTestClass.getFeatureName(), " > ",
					cucumberTestClass.getScenarioName()));
		}

		return scenarioNames;
	}

	protected CucumberAxisTestClassGroup(
		CucumberBatchTestClassGroup cucumberBatchTestClassGroup) {

		super(cucumberBatchTestClassGroup);
	}

}