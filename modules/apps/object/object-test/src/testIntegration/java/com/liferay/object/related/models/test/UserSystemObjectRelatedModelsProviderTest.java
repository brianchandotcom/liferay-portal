/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.related.models.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.runner.RunWith;

/**
 * @author Pedro Leite
 */
@RunWith(Arquillian.class)
public class UserSystemObjectRelatedModelsProviderTest
	extends BaseSystemObjectRelatedModelsProviderTestCase {

	@Override
	protected long[] addSystemObjectEntry(int count) throws Exception {
		long[] userIds = new long[count];

		for (int i = 0; i < count; i++) {
			User user = UserTestUtil.addUser();

			userIds[i] = user.getUserId();
		}

		return userIds;
	}

	@Override
	protected void assertFailureNoSuchException(long primaryKey) {
		AssertUtils.assertFailure(
			NoSuchUserException.class,
			"No User exists with the primary key " + primaryKey,
			() -> _userLocalService.getUser(primaryKey));
	}

	@Override
	protected void deleteSystemObjectEntry(long primaryKey) throws Exception {
		_userLocalService.deleteUser(primaryKey);
	}

	@Override
	protected Object fetchSystemObjectEntry(long primaryKey) {
		return _userLocalService.fetchUser(primaryKey);
	}

	@Override
	protected ObjectDefinition getSystemObjectDefinition() throws Exception {
		return _objectDefinitionLocalService.fetchObjectDefinitionByClassName(
			TestPropsValues.getCompanyId(), User.class.getName());
	}

	@Override
	protected String getSystemObjectEntryName(long primaryKey)
		throws Exception {

		User user = _userLocalService.getUser(primaryKey);

		return user.getFirstName();
	}

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private UserLocalService _userLocalService;

}