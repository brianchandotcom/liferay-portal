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

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.ParallelExecutor;
import com.liferay.jenkins.results.parser.PortalFixpackRelease;
import com.liferay.jenkins.results.parser.PortalRelease;
import com.liferay.jenkins.results.parser.spira.SpiraCustomProperty;
import com.liferay.jenkins.results.parser.spira.SpiraCustomPropertyValue;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseProductVersion;
import com.liferay.jenkins.results.parser.spira.SpiraTestCaseRun;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.json.JSONArray;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseSpiraTestResultValues
	implements SpiraTestResultValues {

	@Override
	public JSONArray getCustomPropertyValuesJSONArray() {
		JSONArray customPropertyValuesJSONArray = new JSONArray();

		for (SpiraCustomPropertyValue spiraCustomPropertyValue :
				getSpiraCustomPropertyValues()) {

			customPropertyValuesJSONArray.put(
				spiraCustomPropertyValue.getCustomPropertyJSONObject());
		}

		return customPropertyValuesJSONArray;
	}

	@Override
	public List<SpiraCustomPropertyValue> getSpiraCustomPropertyValues() {
		ParallelExecutor<List<SpiraCustomPropertyValue>> parallelExecutor =
			new ParallelExecutor<>(getCallables(), _executorService);

		List<SpiraCustomPropertyValue> spiraCustomPropertyValues =
			new ArrayList<>();

		for (List<SpiraCustomPropertyValue> values :
				parallelExecutor.execute()) {

			spiraCustomPropertyValues.addAll(values);
		}

		spiraCustomPropertyValues.removeAll(Collections.singleton(null));

		return spiraCustomPropertyValues;
	}

	protected BaseSpiraTestResultValues(SpiraTestResult spiraTestResult) {
		_spiraTestResult = spiraTestResult;

		_spiraBuildResult = _spiraTestResult.getSpiraBuildResult();
	}

	protected List<Callable<List<SpiraCustomPropertyValue>>> getCallables() {
		List<Callable<List<SpiraCustomPropertyValue>>> callables =
			new ArrayList<>();

		callables.add(
			new Callable<List<SpiraCustomPropertyValue>>() {

				@Override
				public List<SpiraCustomPropertyValue> call() throws Exception {
					return Collections.singletonList(_getDurationStringValue());
				}

			});
		callables.add(
			new Callable<List<SpiraCustomPropertyValue>>() {

				@Override
				public List<SpiraCustomPropertyValue> call() throws Exception {
					return Collections.singletonList(_getDurationValue());
				}

			});
		callables.add(
			new Callable<List<SpiraCustomPropertyValue>>() {

				@Override
				public List<SpiraCustomPropertyValue> call() throws Exception {
					return Collections.singletonList(
						_getPortalFixpackReleaseValue());
				}

			});
		callables.add(
			new Callable<List<SpiraCustomPropertyValue>>() {

				@Override
				public List<SpiraCustomPropertyValue> call() throws Exception {
					return Collections.singletonList(_getPortalReleaseValue());
				}

			});
		callables.add(
			new Callable<List<SpiraCustomPropertyValue>>() {

				@Override
				public List<SpiraCustomPropertyValue> call() throws Exception {
					return Collections.singletonList(_getPortalSHAValue());
				}

			});
		callables.add(
			new Callable<List<SpiraCustomPropertyValue>>() {

				@Override
				public List<SpiraCustomPropertyValue> call() throws Exception {
					return Collections.singletonList(
						(SpiraCustomPropertyValue)_getProductVersionValue());
				}

			});

		return callables;
	}

	protected SpiraBuildResult getSpiraBuildResult() {
		return _spiraBuildResult;
	}

	protected static final int TEST_CASE_ERROR_MAX_LINES = 50;

	private SpiraCustomPropertyValue _getDurationStringValue() {
		SpiraCustomProperty spiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				_spiraBuildResult.getSpiraProject(), SpiraTestCaseRun.class,
				"Duration String", SpiraCustomProperty.Type.TEXT);

		return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
			spiraCustomProperty,
			JenkinsResultsParserUtil.toDurationString(
				_spiraTestResult.getDuration()));
	}

	private SpiraCustomPropertyValue _getDurationValue() {
		SpiraCustomProperty spiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				_spiraBuildResult.getSpiraProject(), SpiraTestCaseRun.class,
				"Duration", SpiraCustomProperty.Type.INTEGER);

		return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
			spiraCustomProperty,
			String.valueOf(_spiraTestResult.getDuration()));
	}

	private SpiraCustomPropertyValue _getPortalFixpackReleaseValue() {
		PortalFixpackRelease portalFixpackRelease =
			_spiraBuildResult.getPortalFixpackRelease();

		if (portalFixpackRelease == null) {
			return null;
		}

		SpiraCustomProperty spiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				_spiraBuildResult.getSpiraProject(), SpiraTestCaseRun.class,
				"Portal Fixpack Release", SpiraCustomProperty.Type.TEXT);

		return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
			spiraCustomProperty,
			portalFixpackRelease.getPortalFixpackVersion());
	}

	private SpiraCustomPropertyValue _getPortalReleaseValue() {
		PortalRelease portalRelease = _spiraBuildResult.getPortalRelease();

		if (portalRelease == null) {
			return null;
		}

		SpiraCustomProperty spiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				_spiraBuildResult.getSpiraProject(), SpiraTestCaseRun.class,
				"Portal Release", SpiraCustomProperty.Type.TEXT);

		return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
			spiraCustomProperty, portalRelease.getPortalVersion());
	}

	private SpiraCustomPropertyValue _getPortalSHAValue() {
		SpiraCustomProperty spiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				_spiraBuildResult.getSpiraProject(), SpiraTestCaseRun.class,
				"Portal SHA", SpiraCustomProperty.Type.TEXT);

		return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
			spiraCustomProperty, _spiraTestResult.getPortalSHA());
	}

	private SpiraTestCaseProductVersion _getProductVersionValue() {
		SpiraTestCaseProductVersion spiraTestCaseProductVersion =
			_spiraBuildResult.getSpiraTestCaseProductVersion();

		return SpiraTestCaseProductVersion.createSpiraTestCaseProductVersion(
			_spiraBuildResult.getSpiraProject(), SpiraTestCaseRun.class,
			spiraTestCaseProductVersion.getValueString());
	}

	private static final Integer _THREAD_COUNT = 100;

	private static final ExecutorService _executorService =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(_THREAD_COUNT, true);

	private final SpiraBuildResult _spiraBuildResult;
	private final SpiraTestResult _spiraTestResult;

}