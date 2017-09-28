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

package com.liferay.message.boards.internal.service;

import com.liferay.message.boards.internal.util.MBSubscriptionHelper;
import com.liferay.message.boards.kernel.model.MBCategory;
import com.liferay.message.boards.kernel.service.MBCategoryService;
import com.liferay.message.boards.kernel.service.MBCategoryServiceWrapper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceWrapper;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class SubscriptionMBCategoryServiceWrapper
	extends MBCategoryServiceWrapper {

	public SubscriptionMBCategoryServiceWrapper() {
		super(null);
	}

	public SubscriptionMBCategoryServiceWrapper(
		MBCategoryService mbCategoryService) {

		super(mbCategoryService);
	}

	@Override
	public List<MBCategory> getSubscribedCategories(
		long groupId, long userId, int start, int end) {

		List<MBCategory> categories = super.getSubscribedCategories(
			groupId, userId, start, end);

		int count = getSubscribedCategoriesCount(groupId, userId);

		if ((start != QueryUtil.ALL_POS) && (end != QueryUtil.ALL_POS) &&
			((end < count) || ((end - start) == categories.size()))) {

			return categories;
		}

		try {
			return _mbSubscriptionHelper.addSubscribedRootCategory(
				groupId, userId, categories);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	@Override
	public int getSubscribedCategoriesCount(long groupId, long userId) {
		int count = super.getSubscribedCategoriesCount(groupId, userId);

		try {
			return _mbSubscriptionHelper.addSubscribedRootCategoryCount(
				groupId, userId, count);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	@Reference
	private MBSubscriptionHelper _mbSubscriptionHelper;

}