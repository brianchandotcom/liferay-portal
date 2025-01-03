/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.test;

import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Carolina Barbosa
 */
public abstract class BaseStaleUserIdUpgradeProcessTestCase {

	@Test
	public void testUpgrade() throws Exception {
		User user = UserTestUtil.addUser();

		AuditedModel auditedModel = addAuditedModel(user);

		Assert.assertEquals(user.getUserId(), auditedModel.getUserId());

		_userLocalService.deleteUser(user);

		long primaryKey = (Long)auditedModel.getPrimaryKeyObj();

		updateUserId(_getAuditedModel(primaryKey), user.getUserId());

		Assert.assertEquals(user.getUserId(), _getUserId(primaryKey));

		runUpgrade();

		_multiVMPool.clear();

		Assert.assertEquals(
			_userLocalService.getUserIdByScreenName(
				TestPropsValues.getCompanyId(), "default-service-account"),
			_getUserId(primaryKey));
	}

	protected abstract AuditedModel addAuditedModel(User user) throws Exception;

	protected abstract PersistedModelLocalService
		getPersistedModelLocalService();

	protected abstract void runUpgrade() throws Exception;

	protected abstract void updateUserId(AuditedModel auditedModel, long userId)
		throws Exception;

	private AuditedModel _getAuditedModel(long primaryKey) throws Exception {
		PersistedModelLocalService persistedModelLocalService =
			getPersistedModelLocalService();

		return (AuditedModel)persistedModelLocalService.getPersistedModel(
			primaryKey);
	}

	private long _getUserId(long primaryKey) throws Exception {
		AuditedModel auditedModel = _getAuditedModel(primaryKey);

		return auditedModel.getUserId();
	}

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private UserLocalService _userLocalService;

}