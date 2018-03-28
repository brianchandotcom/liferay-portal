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

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;

import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Noah Sherrill
 */
public abstract class BaseUADEntityAggregatorTestCase<T extends BaseModel> {

	@Before
	public void setUp() throws Exception {
		_uadEntityAggregator = getUADEntityAggregator();
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testCount() throws Exception {
		addBaseModel(_user.getUserId());

		Assert.assertEquals(1, _uadEntityAggregator.count(_user.getUserId()));
	}

	@Test
	public void testGetUADEntities() throws Exception {
		addBaseModel(TestPropsValues.getUserId());

		T baseModel = addBaseModel(_user.getUserId());

		List<T> entities = _uadEntityAggregator.getEntities(_user.getUserId());

		Assert.assertEquals(entities.toString(), 1, entities.size());

		Assert.assertEquals(baseModel, entities.get(0));
	}

	@Test
	public void testGetUADEntitiesByStatusByUserId() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasStatusByUserIdField);

		WhenHasStatusByUserIdField<T> whenHasStatusByUserIdField =
			(WhenHasStatusByUserIdField)this;

		T baseModel = whenHasStatusByUserIdField.addBaseModelWithStatusByUserId(
			TestPropsValues.getUserId(), _user.getUserId());

		List<T> entities = _uadEntityAggregator.getEntities(_user.getUserId());

		Assert.assertEquals(entities.toString(), 1, entities.size());

		Assert.assertEquals(baseModel, entities.get(0));
	}

	@Test
	public void testGetUADEntitiesWithNoBaseModel() throws Exception {
		Assert.assertEquals(0, _uadEntityAggregator.count(_user.getUserId()));
	}

	protected abstract T addBaseModel(long userId) throws Exception;

	protected abstract UADEntityAggregator<T> getUADEntityAggregator();

	private UADEntityAggregator<T> _uadEntityAggregator;

	@DeleteAfterTestRun
	private User _user;

}