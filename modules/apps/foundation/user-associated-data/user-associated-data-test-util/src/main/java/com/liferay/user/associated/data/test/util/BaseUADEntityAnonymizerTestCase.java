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
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;

import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Noah Sherrill
 */
public abstract class BaseUADEntityAnonymizerTestCase<T extends BaseModel> {

	@Before
	public void setUp() throws Exception {
		_uadEntityAggregator = getUADEntityAggregator();
		_uadEntityAnonymizer = getUADEntityAnonymizer();
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testAutoAnonymize() throws Exception {
		T baseModel = addBaseModel(_user.getUserId());

		_testAutoAnonymize(baseModel);
	}

	@Test
	public void testAutoAnonymizeAll() throws Exception {
		T baseModel = addBaseModel(TestPropsValues.getUserId());
		T autoAnonymizedBaseModel = addBaseModel(_user.getUserId());

		_uadEntityAnonymizer.autoAnonymizeAll(_user.getUserId());

		long baseModelPK = getBaseModelPrimaryKey(baseModel);

		Assert.assertFalse(
			isBaseModelAutoAnonymized(baseModelPK, TestPropsValues.getUser()));

		long autoAnonymizedBaseModelPK = getBaseModelPrimaryKey(
			autoAnonymizedBaseModel);

		Assert.assertTrue(
			isBaseModelAutoAnonymized(autoAnonymizedBaseModelPK, _user));
	}

	@Test
	public void testAutoAnonymizeAllWithNoBaseModel() throws Exception {
		_uadEntityAnonymizer.autoAnonymizeAll(_user.getUserId());
	}

	@Test
	public void testAutoAnonymizeStatusByUserOnly() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasStatusByUserIdField);

		WhenHasStatusByUserIdField<T> whenHasStatusByUserIdField =
			(WhenHasStatusByUserIdField)this;

		T baseModel = whenHasStatusByUserIdField.addBaseModelWithStatusByUserId(
			TestPropsValues.getUserId(), _user.getUserId());

		_testAutoAnonymize(baseModel);
	}

	@Test
	public void testAutoAnonymizeUserOnly() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasStatusByUserIdField);

		WhenHasStatusByUserIdField<T> whenHasStatusByUserIdField =
			(WhenHasStatusByUserIdField)this;

		T baseModel = whenHasStatusByUserIdField.addBaseModelWithStatusByUserId(
			_user.getUserId(), TestPropsValues.getUserId());

		_testAutoAnonymize(baseModel);
	}

	@Test
	public void testDelete() throws Exception {
		BaseModel baseModel = addBaseModel(_user.getUserId(), false);

		List<T> entities = _uadEntityAggregator.getEntities(_user.getUserId());

		_uadEntityAnonymizer.delete(entities.get(0));

		long baseModelPK = getBaseModelPrimaryKey(baseModel);

		Assert.assertTrue(isBaseModelDeleted(baseModelPK));
	}

	@Test
	public void testDeleteAll() throws Exception {
		T baseModel = addBaseModel(TestPropsValues.getUserId());
		T deletedBaseModel = addBaseModel(_user.getUserId(), false);

		_uadEntityAnonymizer.deleteAll(_user.getUserId());

		long baseModelPK = getBaseModelPrimaryKey(baseModel);

		Assert.assertFalse(isBaseModelDeleted(baseModelPK));

		long deletedBaseModelPK = getBaseModelPrimaryKey(deletedBaseModel);

		Assert.assertTrue(isBaseModelDeleted(deletedBaseModelPK));
	}

	@Test
	public void testDeleteAllWithNoBaseModel() throws Exception {
		_uadEntityAnonymizer.deleteAll(_user.getUserId());
	}

	protected abstract T addBaseModel(long userId) throws Exception;

	protected abstract T addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception;

	protected long getBaseModelPrimaryKey(BaseModel baseModel) {
		return (long)baseModel.getPrimaryKeyObj();
	}

	protected abstract UADEntityAggregator<T> getUADEntityAggregator();

	protected abstract UADEntityAnonymizer<T> getUADEntityAnonymizer();

	protected abstract boolean isBaseModelAutoAnonymized(
			long baseModelPK, User user)
		throws Exception;

	protected abstract boolean isBaseModelDeleted(long baseModelPK);

	private void _testAutoAnonymize(BaseModel baseModel) throws Exception {
		List<T> entities = _uadEntityAggregator.getEntities(_user.getUserId());

		_uadEntityAnonymizer.autoAnonymize(entities.get(0), _user.getUserId());

		long baseModelPK = getBaseModelPrimaryKey(baseModel);

		Assert.assertTrue(isBaseModelAutoAnonymized(baseModelPK, _user));
	}

	private UADEntityAggregator<T> _uadEntityAggregator;
	private UADEntityAnonymizer<T> _uadEntityAnonymizer;

	@DeleteAfterTestRun
	private User _user;

}