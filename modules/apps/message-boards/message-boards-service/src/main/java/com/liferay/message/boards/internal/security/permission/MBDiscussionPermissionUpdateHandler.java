/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.security.permission;

import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.service.MBDiscussionLocalService;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.message.boards.model.MBDiscussion",
	service = PermissionUpdateHandler.class
)
public class MBDiscussionPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		MBDiscussion mbDiscussion = _mbDiscussionLocalService.fetchMBDiscussion(
			GetterUtil.getLong(primKey));

		if (mbDiscussion == null) {
			return;
		}

		mbDiscussion.setModifiedDate(new Date());

		_mbDiscussionLocalService.updateMBDiscussion(mbDiscussion);
	}

	@Reference
	private MBDiscussionLocalService _mbDiscussionLocalService;

}