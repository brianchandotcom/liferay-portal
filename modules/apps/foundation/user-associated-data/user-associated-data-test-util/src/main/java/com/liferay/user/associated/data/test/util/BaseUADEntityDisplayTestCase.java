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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.user.associated.data.aggregator.UADAggregator;
import com.liferay.user.associated.data.display.UADEntityDisplay;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Noah Sherrill
 */
public abstract class BaseUADEntityDisplayTestCase<T> {

	@Before
	public void setUp() throws Exception {
		_uadAggregator = getUADAggregator();
		_uadEntityDisplay = getUADEntityDisplay();
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testGetApplicationName() {
		Assert.assertEquals(
			getApplicationName(), _uadEntityDisplay.getApplicationName());
	}

	@Test
	public void testGetTypeDescription() {
		Assert.assertEquals(
			getTypeDescription(), _uadEntityDisplay.getTypeDescription());
	}

	@Test
	public void testGetTypeName() throws Exception {
		BaseModel baseModel = addBaseModel(_user.getUserId());

		String simpleClassName = StringUtil.extractLast(
			baseModel.getModelClassName(), StringPool.PERIOD);

		Assert.assertEquals(simpleClassName, _uadEntityDisplay.getTypeName());
	}

	protected abstract BaseModel<?> addBaseModel(long userId) throws Exception;

	protected abstract String getApplicationName();

	protected abstract String getTypeDescription();

	protected abstract UADAggregator<T> getUADAggregator();

	protected abstract UADEntityDisplay<T> getUADEntityDisplay();

	private T _createBaseModel() throws Exception {
		addBaseModel(_user.getUserId());

		List<T> baseModels = _uadAggregator.getAll(_user.getUserId());

		return baseModels.get(0);
	}

	private UADAggregator<T> _uadAggregator;
	private UADEntityDisplay<T> _uadEntityDisplay;

	@DeleteAfterTestRun
	private User _user;

}