/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.test.util;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Noah Sherrill
 */
public abstract class BaseUADAnonymizerTestCase<T extends BaseModel> {

	@Before
	public void setUp() throws Exception {
		anonymousUser = UserTestUtil.addUser();
		uadAnonymizer = getUADAnonymizer();
		user = UserTestUtil.addUser();
	}

	@Test
	public void testAutoAnonymize() throws Exception {
		T baseModel = addBaseModel(user.getUserId());

		testAutoAnonymize(baseModel);
	}

	@Test
	public void testAutoAnonymizeAll() throws Exception {
		T baseModel = addBaseModel(TestPropsValues.getUserId());
		T autoAnonymizedBaseModel = addBaseModel(user.getUserId());

		uadAnonymizer.autoAnonymizeAll(user.getUserId(), anonymousUser);

		long baseModelPK = getBaseModelPrimaryKey(baseModel);

		Assert.assertFalse(
			isBaseModelAutoAnonymized(baseModelPK, TestPropsValues.getUser()));

		long autoAnonymizedBaseModelPK = getBaseModelPrimaryKey(
			autoAnonymizedBaseModel);

		Assert.assertTrue(
			isBaseModelAutoAnonymized(autoAnonymizedBaseModelPK, user));
	}

	@Test
	public void testAutoAnonymizeAllWithNoBaseModel() throws Exception {
		uadAnonymizer.autoAnonymizeAll(user.getUserId(), anonymousUser);
	}

	@Test
	public void testAutoAnonymizeStatusByUserOnly() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasStatusByUserIdField);

		WhenHasStatusByUserIdField<T> whenHasStatusByUserIdField =
			(WhenHasStatusByUserIdField)this;

		T baseModel = whenHasStatusByUserIdField.addBaseModelWithStatusByUserId(
			TestPropsValues.getUserId(), user.getUserId());

		testAutoAnonymize(baseModel);
	}

	@Test
	public void testAutoAnonymizeUserOnly() throws Exception {
		Assume.assumeTrue(this instanceof WhenHasStatusByUserIdField);

		WhenHasStatusByUserIdField<T> whenHasStatusByUserIdField =
			(WhenHasStatusByUserIdField)this;

		T baseModel = whenHasStatusByUserIdField.addBaseModelWithStatusByUserId(
			user.getUserId(), TestPropsValues.getUserId());

		testAutoAnonymize(baseModel);
	}

	@Test
	public void testDelete() throws Exception {
		T baseModel = addBaseModel(user.getUserId(), false);

		uadAnonymizer.delete(baseModel);

		deleteBaseModels(Arrays.asList(baseModel));

		long baseModelPK = getBaseModelPrimaryKey(baseModel);

		Assert.assertTrue(isBaseModelDeleted(baseModelPK));
	}

	@Test
	public void testDeleteAll() throws Exception {
		T baseModel = addBaseModel(TestPropsValues.getUserId());
		T deletedBaseModel = addBaseModel(user.getUserId(), false);

		uadAnonymizer.deleteAll(user.getUserId());

		deleteBaseModels(ListUtil.fromArray(deletedBaseModel));

		long baseModelPK = getBaseModelPrimaryKey(baseModel);

		Assert.assertFalse(isBaseModelDeleted(baseModelPK));

		long deletedBaseModelPK = getBaseModelPrimaryKey(deletedBaseModel);

		Assert.assertTrue(isBaseModelDeleted(deletedBaseModelPK));
	}

	@Test
	public void testDeleteAllWithNoBaseModel() throws Exception {
		uadAnonymizer.deleteAll(user.getUserId());
	}

	protected abstract T addBaseModel(long userId) throws Exception;

	protected abstract T addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception;

	protected void deleteBaseModels(List<T> baseModels) throws Exception {
	}

	protected long getBaseModelPrimaryKey(BaseModel<?> baseModel) {
		return (long)baseModel.getPrimaryKeyObj();
	}

	protected abstract UADAnonymizer<T> getUADAnonymizer();

	protected abstract boolean isBaseModelAutoAnonymized(
			long baseModelPK, User user)
		throws Exception;

	protected abstract boolean isBaseModelDeleted(long baseModelPK);

	protected void testAutoAnonymize(T baseModel) throws Exception {
		long userId = user.getUserId();

		Assert.assertEquals(1, uadAnonymizer.count(userId));

		uadAnonymizer.autoAnonymize(
			(T)baseModel.clone(), user.getUserId(), anonymousUser);

		long baseModelPK = getBaseModelPrimaryKey(baseModel);

		Assert.assertEquals(0, uadAnonymizer.count(userId));

		Assert.assertTrue(isBaseModelAutoAnonymized(baseModelPK, user));
	}

	@DeleteAfterTestRun
	protected User anonymousUser;

	protected UADAnonymizer<T> uadAnonymizer;

	@DeleteAfterTestRun
	protected User user;

}