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
			catch (NoSuchMessageException nsme) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsme, nsme);
				}
			}

			categoryIds.remove(categoryId);
		}
	}

	@Reference(unbind = "-")
	protected void setMBCategoryLocalService(
		MBCategoryLocalService mbCategoryLocalService) {

		this.mbCategoryLocalService = mbCategoryLocalService;
	}

	protected final List<Long> categoryIds = new CopyOnWriteArrayList<>();
	protected MBCategoryLocalService mbCategoryLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseMBCategoryDemoDataCreatorImpl.class);

}