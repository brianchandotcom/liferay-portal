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

import com.liferay.jenkins.results.parser.spira.MultiListSpiraCustomPropertyValue;
import com.liferay.jenkins.results.parser.spira.SpiraCustomProperty;
import com.liferay.jenkins.results.parser.spira.SpiraCustomPropertyValue;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseRun;
import com.liferay.jenkins.results.parser.test.clazz.group.CucumberBatchTestClassGroup;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Michael Hashimoto
 */
public class CucumberAxisSpiraTestResultValues
	extends BaseAxisSpiraTestResultValues {

	protected CucumberAxisSpiraTestResultValues(
		CucumberAxisSpiraTestResult cucumberAxisSpiraTestResult) {

		super(cucumberAxisSpiraTestResult);

		_cucumberAxisSpiraTestResult = cucumberAxisSpiraTestResult;
	}

	@Override
	protected List<Callable<List<SpiraCustomPropertyValue>>> getCallables() {
		List<Callable<List<SpiraCustomPropertyValue>>> callables =
			super.getCallables();

		callables.add(
			new Callable<List<SpiraCustomPropertyValue>>() {

				@Override
				public List<SpiraCustomPropertyValue> call() throws Exception {
					return Collections.singletonList(_getCucumberTagsValue());
				}

			});

		return callables;
	}

	private SpiraCustomPropertyValue _getCucumberTagsValue() {
		CucumberBatchTestClassGroup.CucumberTestClass cucumberTestClass =
			_cucumberAxisSpiraTestResult.getCucumberTestClass();

		SpiraBuildResult spiraBuildResult =
			_cucumberAxisSpiraTestResult.getSpiraBuildResult();

		SpiraCustomProperty spiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				spiraBuildResult.getSpiraProject(), SpiraTestCaseRun.class,
				"Cucumber Tags", SpiraCustomProperty.Type.MULTILIST);

		SpiraCustomPropertyValue cucumberTagsValue = null;

		for (String featureTag : cucumberTestClass.getFeatureTags()) {
			SpiraCustomPropertyValue spiraCustomPropertyValue =
				SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
					spiraCustomProperty, featureTag);

			if (cucumberTagsValue == null) {
				cucumberTagsValue = spiraCustomPropertyValue;
			}
			else {
				cucumberTagsValue =
					MultiListSpiraCustomPropertyValue.
						combineMultiListSpiraCustomPropertyValues(
							(MultiListSpiraCustomPropertyValue)
								cucumberTagsValue,
							(MultiListSpiraCustomPropertyValue)
								spiraCustomPropertyValue);
			}
		}

		return cucumberTagsValue;
	}

	private final CucumberAxisSpiraTestResult _cucumberAxisSpiraTestResult;

}