/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.internal.model.listener;

import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Costa
 */
@Component(service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onAfterRemove(User user) throws ModelListenerException {
		try {
			User defaultServiceAccountUser =
				_userLocalService.fetchUserByScreenName(
					user.getCompanyId(), "default-service-account");

			if (defaultServiceAccountUser == null) {
				return;
			}

			_listTypeDefinitionLocalService.updateUserId(
				user.getCompanyId(), user.getUserId(),
				defaultServiceAccountUser.getUserId());

			_listTypeEntryLocalService.updateUserId(
				user.getCompanyId(), user.getUserId(),
				defaultServiceAccountUser.getUserId());
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}