/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.security.permission;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.blogs.model.BlogsEntry",
	service = PermissionUpdateHandler.class
)
public class BlogsEntryPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		BlogsEntry blogsEntry = _blogsEntryLocalService.fetchBlogsEntry(
			GetterUtil.getLong(primKey));

		if (blogsEntry == null) {
			return;
		}

		blogsEntry.setModifiedDate(new Date());

		_blogsEntryLocalService.updateBlogsEntry(blogsEntry);
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

}