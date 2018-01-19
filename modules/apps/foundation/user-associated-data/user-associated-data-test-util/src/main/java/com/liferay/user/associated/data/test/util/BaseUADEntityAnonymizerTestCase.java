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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.entity.UADEntity;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Noah Sherrill
 */
public abstract class BaseUADEntityAnonymizerTestCase {

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		Bundle bundle = FrameworkUtil.getBundle(
			BaseUADEntityAnonymizerTestCase.class);

		_uadEntityAggregatorServiceTracker = ServiceTrackerFactory.open(
			bundle.getBundleContext(),
			"(&(objectClass=" + UADEntityAggregator.class.getName() +
				")(model.class.name=" + getUADRegistryKey() + "))");

		_uadEntityAggregator =
			_uadEntityAggregatorServiceTracker.waitForService(5000);

		_uadEntityAnonymizerServiceTracker = ServiceTrackerFactory.open(
			bundle.getBundleContext(),
			"(&(objectClass=" + UADEntityAnonymizer.class.getName() +
				")(model.class.name=" + getUADRegistryKey() + "))");

		_uadEntityAnonymizer =
			_uadEntityAnonymizerServiceTracker.waitForService(5000);
	}

	@After
	public void tearDown() throws Exception {
		_uadEntityAggregatorServiceTracker.close();
		_uadEntityAnonymizerServiceTracker.close();
	}

	@Test
	public void testAutoAnonymize() throws Exception {
		Object dataObject = addDataObject(_user.getUserId());

		List<UADEntity> uadEntities = _uadEntityAggregator.getUADEntities(
			_user.getUserId());

		_uadEntityAnonymizer.autoAnonymize(uadEntities.get(0));

		long dataObjectId = getDataObjectId(dataObject);

		Assert.assertTrue(isDataObjectAutoAnonymized(dataObjectId, _user));
	}

	@Test
	public void testAutoAnonymizeAll() throws Exception {
		Object dataObject = addDataObject(TestPropsValues.getUserId());
		Object dataObjectAutoAnonymize = addDataObject(_user.getUserId());

		_uadEntityAnonymizer.autoAnonymizeAll(_user.getUserId());

		long dataObjectId = getDataObjectId(dataObject);

		Assert.assertFalse(
			isDataObjectAutoAnonymized(
				dataObjectId, TestPropsValues.getUser()));

		long dataObjectAutoAnoymizeId = getDataObjectId(
			dataObjectAutoAnonymize);

		Assert.assertTrue(
			isDataObjectAutoAnonymized(dataObjectAutoAnoymizeId, _user));
	}

	@Test
	public void testAutoAnonymizeAllWithNoDataObject() throws Exception {
		_uadEntityAnonymizer.autoAnonymizeAll(_user.getUserId());
	}

	@Test
	public void testAutoAnonymizeStatusByUserOnly() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasStatusByUserIdField);

		WhenHasStatusByUserIdField whenHasStatusByUserIdField =
			(WhenHasStatusByUserIdField)this;

		Object dataObject =
			whenHasStatusByUserIdField.addDataObjectWithStatusByUserId(
				TestPropsValues.getUserId(), _user.getUserId());

		List<UADEntity> uadEntities = _uadEntityAggregator.getUADEntities(
			_user.getUserId());

		_uadEntityAnonymizer.autoAnonymize(uadEntities.get(0));

		long dataObjectId = getDataObjectId(dataObject);

		Assert.assertTrue(isDataObjectAutoAnonymized(dataObjectId, _user));
	}

	@Test
	public void testAutoAnonymizeUserOnly() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasStatusByUserIdField);

		WhenHasStatusByUserIdField whenHasStatusByUserIdField =
			(WhenHasStatusByUserIdField)this;

		Object dataObject =
			whenHasStatusByUserIdField.addDataObjectWithStatusByUserId(
				TestPropsValues.getUserId(), _user.getUserId());

		List<UADEntity> uadEntities = _uadEntityAggregator.getUADEntities(
			_user.getUserId());

		_uadEntityAnonymizer.autoAnonymize(uadEntities.get(0));

		long dataObjectId = getDataObjectId(dataObject);

		Assert.assertTrue(isDataObjectAutoAnonymized(dataObjectId, _user));
	}

	@Test
	public void testDelete() throws Exception {
		Object dataObject = addDataObject(_user.getUserId());

		List<UADEntity> uadEntities = _uadEntityAggregator.getUADEntities(
			_user.getUserId());

		_uadEntityAnonymizer.delete(uadEntities.get(0));

		long dataObjectId = getDataObjectId(dataObject);

		Assert.assertTrue(isDataObjectDeleted(dataObjectId));
	}

	@Test
	public void testDeleteAll() throws Exception {
		Object dataObject = addDataObject(TestPropsValues.getUserId());
		Object dataObjectDelete = addDataObject(_user.getUserId());

		_uadEntityAnonymizer.deleteAll(_user.getUserId());

		long dataObjectId = getDataObjectId(dataObject);

		Assert.assertFalse(isDataObjectDeleted(dataObjectId));

		long dataObjectDeleteId = getDataObjectId(dataObjectDelete);

		Assert.assertTrue(isDataObjectDeleted(dataObjectDeleteId));
	}

	@Test
	public void testDeleteAllWithNoDataObject() throws Exception {
		_uadEntityAnonymizer.deleteAll(_user.getUserId());
	}

	protected abstract Object addDataObject(long userId) throws Exception;

	protected abstract long getDataObjectId(Object dataObject);

	protected abstract String getUADRegistryKey();

	protected abstract boolean isDataObjectAutoAnonymized(
			long dataObjectId, User user)
		throws Exception;

	protected abstract boolean isDataObjectDeleted(long dataObjectId);

	private UADEntityAggregator _uadEntityAggregator;
	private ServiceTracker<UADEntityAggregator, UADEntityAggregator>
		_uadEntityAggregatorServiceTracker;
	private UADEntityAnonymizer _uadEntityAnonymizer;
	private ServiceTracker<UADEntityAnonymizer, UADEntityAnonymizer>
		_uadEntityAnonymizerServiceTracker;

	@DeleteAfterTestRun
	private User _user;

}