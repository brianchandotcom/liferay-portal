/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.model.listener;

import com.liferay.message.boards.service.MBBanLocalService;
import com.liferay.message.boards.service.MBThreadFlagLocalService;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onBeforeRemove(User user) {
		_mbBanLocalService.deleteBansByBanUserId(user.getUserId());
		_mbThreadFlagLocalService.deleteThreadFlagsByUserId(user.getUserId());
	}

	@Reference
	private MBBanLocalService _mbBanLocalService;

	@Reference
	private MBThreadFlagLocalService _mbThreadFlagLocalService;

}