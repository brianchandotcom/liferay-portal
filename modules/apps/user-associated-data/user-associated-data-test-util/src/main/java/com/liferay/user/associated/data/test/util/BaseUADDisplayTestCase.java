/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.test.util;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.user.associated.data.display.UADDisplay;

import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Noah Sherrill
 */
public abstract class BaseUADDisplayTestCase<T> {

	@Before
	public void setUp() throws Exception {
		user = UserTestUtil.addUser();

		_uadDisplay = getUADDisplay();
	}

	@Test
	public void testCount() throws Exception {
		addBaseModel(user.getUserId());

		Assert.assertEquals(1, _uadDisplay.count(user.getUserId()));
	}

	@Test
	public void testGetAllByStatusByUserId() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasStatusByUserIdField);

		WhenHasStatusByUserIdField<T> whenHasStatusByUserIdField =
			(WhenHasStatusByUserIdField)this;

		T baseModel = whenHasStatusByUserIdField.addBaseModelWithStatusByUserId(
			TestPropsValues.getUserId(), user.getUserId());

		List<T> baseModels = _uadDisplay.getRange(
			user.getUserId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(baseModels.toString(), 1, baseModels.size());

		Assert.assertEquals(baseModel, baseModels.get(0));
	}

	@Test
	public void testGetAllWithNoBaseModel() throws Exception {
		Assert.assertEquals(0, _uadDisplay.count(user.getUserId()));
	}

	@Test
	public void testGetTypeName() {
		Assert.assertTrue(
			"The type name should not be null",
			Validator.isNotNull(_uadDisplay.getTypeName(LocaleUtil.US)));
	}

	protected abstract BaseModel<?> addBaseModel(long userId) throws Exception;

	protected abstract UADDisplay<T> getUADDisplay();

	@DeleteAfterTestRun
	protected User user;

	private UADDisplay<T> _uadDisplay;

}