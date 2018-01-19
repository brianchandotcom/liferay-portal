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

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.registry.UADRegistryUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Noah Sherrill
 */
public abstract class BaseUADEntityAggregatorTestCase {

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		_uadEntityAggregator = UADRegistryUtil.getUADEntityAggregator(
			getUADRegistryKey());
	}

	@Test
	public void testCount() throws Exception {
		addDataObject(_user.getUserId());

		Assert.assertEquals(1, _uadEntityAggregator.count(_user.getUserId()));
	}

	@Test
	public void testGetUADEntities() throws Exception {
		addDataObject(TestPropsValues.getUserId());
		addDataObject(_user.getUserId());

		List<UADEntity> uadEntities = _uadEntityAggregator.getUADEntities(
			_user.getUserId());

		Assert.assertEquals(uadEntities.toString(), 1, uadEntities.size());

		UADEntity uadEntity = uadEntities.get(0);

		Assert.assertEquals(_user.getUserId(), uadEntity.getUserId());
	}

	@Test
	public void testGetUADEntitiesByStatusByUserId() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasStatusByUserIdField);

		WhenHasStatusByUserIdField whenHasStatusByUserIdField =
			(WhenHasStatusByUserIdField)this;

		whenHasStatusByUserIdField.addDataObjectWithStatusByUserId(
			TestPropsValues.getUserId(), _user.getUserId());

		List<UADEntity> uadEntities = _uadEntityAggregator.getUADEntities(
			_user.getUserId());

		Assert.assertEquals(uadEntities.toString(), 1, uadEntities.size());

		UADEntity uadEntity = uadEntities.get(0);

		Assert.assertEquals(_user.getUserId(), uadEntity.getUserId());
	}

	@Test
	public void testGetUADEntitiesWithNoDataObject() throws Exception {
		List<UADEntity> uadEntities = _uadEntityAggregator.getUADEntities(
			_user.getUserId());

		Assert.assertEquals(uadEntities.toString(), 0, uadEntities.size());
	}

	@Test
	public void testGetUADEntity() throws Exception {
		addDataObject(_user.getUserId());

		List<UADEntity> uadEntities = _uadEntityAggregator.getUADEntities(
			_user.getUserId());

		UADEntity uadEntity = uadEntities.get(0);

		String uadEntityId = uadEntity.getUADEntityId();

		uadEntity = _uadEntityAggregator.getUADEntity(uadEntityId);

		Assert.assertEquals(_user.getUserId(), uadEntity.getUserId());
		Assert.assertEquals(uadEntityId, uadEntity.getUADEntityId());
	}

	protected abstract void addDataObject(long userId) throws Exception;

	protected abstract String getUADRegistryKey();

	private UADEntityAggregator _uadEntityAggregator;

	@DeleteAfterTestRun
	private User _user;

}