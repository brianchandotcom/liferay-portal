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

package com.liferay.user.associated.data.test.util;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.entity.UADEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Noah Sherrill
 */
public abstract class BaseUADEntityDisplayTestCase {

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		Bundle bundle = FrameworkUtil.getBundle(
			BaseUADEntityDisplayTestCase.class);

		_uadEntityAggregatorServiceTracker = ServiceTrackerFactory.open(
			bundle.getBundleContext(),
			"(&(objectClass=" + UADEntityAggregator.class.getName() +
				")(model.class.name=" + getUADRegistryKey() + "))");

		_uadEntityAggregator =
			_uadEntityAggregatorServiceTracker.waitForService(5000);

		_uadEntityDisplayServiceTracker = ServiceTrackerFactory.open(
			bundle.getBundleContext(),
			"(&(objectClass=" + UADEntityDisplay.class.getName() +
				")(model.class.name=" + getUADRegistryKey() + "))");

		_uadEntityDisplay = _uadEntityDisplayServiceTracker.waitForService(
			5000);
	}

	@After
	public void tearDown() throws Exception {
		_uadEntityAggregatorServiceTracker.close();
		_uadEntityDisplayServiceTracker.close();
	}

	@Test
	public void testGetEntityNonanonymizableFieldValues() throws Exception {
		UADEntity uadEntity = _createUADEntity();

		String entityNonanonymizableFieldValues =
			_uadEntityDisplay.getEntityNonanonymizableFieldValues(uadEntity);

		Map<String, Object> entityNonanonymizableFieldValuesMap =
			uadEntity.getEntityNonanonymizableFieldValues();

		for (Map.Entry<String, Object> entry :
				entityNonanonymizableFieldValuesMap.entrySet()) {

			Assert.assertTrue(
				entityNonanonymizableFieldValues.contains(
					entry.getKey() + ": " + entry.getValue()));
		}
	}

	@Test
	public void testGetEntityTypeDescription() {
		Assert.assertEquals(
			getEntityTypeDescription(),
			_uadEntityDisplay.getEntityTypeDescription());
	}

	@Test
	public void testGetEntityTypeName() throws Exception {
		BaseModel baseModel = addBaseModel(_user.getUserId());

		String simpleClassName = StringUtil.extractLast(
			baseModel.getModelClassName(), StringPool.PERIOD);

		Assert.assertEquals(
			simpleClassName, _uadEntityDisplay.getEntityTypeName());
	}

	@Test
	public void testGetEntityTypeNonanonymizableFieldNames() {
		String entityTypeNonanonymizableFieldNames =
			_uadEntityDisplay.getEntityTypeNonanonymizableFieldNames();

		for (String entityTypeNonanonymizableFieldName :
				_uadEntityDisplay.
					getEntityTypeNonanonymizableFieldNamesList()) {

			Assert.assertTrue(
				entityTypeNonanonymizableFieldNames.contains(
					entityTypeNonanonymizableFieldName));
		}
	}

	@Test
	public void testGetEntityTypeNonanonymizableFieldNamesList()
		throws Exception {

		UADEntity uadEntity = _createUADEntity();

		Map<String, Object> entityNonanonymizableFieldValuesMap =
			uadEntity.getEntityNonanonymizableFieldValues();

		AssertUtils.assertEquals(
			new ArrayList<>(entityNonanonymizableFieldValuesMap.keySet()),
			_uadEntityDisplay.getEntityTypeNonanonymizableFieldNamesList());
	}

	protected abstract BaseModel<?> addBaseModel(long userId) throws Exception;

	protected abstract String getEntityTypeDescription();

	protected abstract String getUADRegistryKey();

	private UADEntity _createUADEntity() throws Exception {
		addBaseModel(_user.getUserId());

		List<UADEntity> uadEntities = _uadEntityAggregator.getUADEntities(
			_user.getUserId());

		return uadEntities.get(0);
	}

	private UADEntityAggregator _uadEntityAggregator;
	private ServiceTracker<UADEntityAggregator, UADEntityAggregator>
		_uadEntityAggregatorServiceTracker;
	private UADEntityDisplay _uadEntityDisplay;
	private ServiceTracker<UADEntityDisplay, UADEntityDisplay>
		_uadEntityDisplayServiceTracker;

	@DeleteAfterTestRun
	private User _user;

}