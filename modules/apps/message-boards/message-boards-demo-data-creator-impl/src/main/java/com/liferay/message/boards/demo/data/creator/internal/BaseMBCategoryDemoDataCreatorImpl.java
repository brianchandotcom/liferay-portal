/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.demo.data.creator.internal;

import com.liferay.message.boards.demo.data.creator.BaseMBCategoryDemoDataCreator;
import com.liferay.message.boards.exception.NoSuchMessageException;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.IOException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
public abstract class BaseMBCategoryDemoDataCreatorImpl
	implements BaseMBCategoryDemoDataCreator {

	public MBCategory createCategory(
			long userId, long groupId, long categoryId, String name,
			String description)
		throws IOException, PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		MBCategory category = mbCategoryLocalService.addCategory(
			userId, categoryId, name, description, serviceContext);

		categoryIds.add(category.getCategoryId());

		return category;
	}

	@Override
	public void delete() throws PortalException {
		for (long categoryId : categoryIds) {
			try {
				mbCategoryLocalService.deleteCategory(categoryId);
			}
			catch (NoSuchMessageException noSuchMessageException) {
				if (_log.isWarnEnabled()) {
					_log.warn(noSuchMessageException);
				}
			}

			categoryIds.remove(categoryId);
		}
	}

	protected final List<Long> categoryIds = new CopyOnWriteArrayList<>();

	@Reference
	protected MBCategoryLocalService mbCategoryLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseMBCategoryDemoDataCreatorImpl.class);

}