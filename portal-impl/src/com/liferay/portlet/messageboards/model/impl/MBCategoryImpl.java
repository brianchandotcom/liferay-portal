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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MBCategoryImpl extends MBCategoryBaseImpl {

	@Override
	public List<Long> getAncestorCategoryIds() throws PortalException {
		List<Long> ancestorCategoryIds = new ArrayList<Long>();

		MBCategory category = this;

		while (!category.isRoot()) {
			category = MBCategoryLocalServiceUtil.getCategory(
				category.getParentCategoryId());

			ancestorCategoryIds.add(category.getCategoryId());
		}

		return ancestorCategoryIds;
	}

	@Override
	public List<MBCategory> getAncestors() throws PortalException {
		List<MBCategory> ancestors = new ArrayList<MBCategory>();

		MBCategory category = this;

		while (!category.isRoot()) {
			category = category.getParentCategory();

			ancestors.add(category);
		}

		return ancestors;
	}

	@Override
	public MBCategory getParentCategory() throws PortalException {
		long parentCategoryId = getParentCategoryId();

		if ((parentCategoryId ==
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(parentCategoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return null;
		}

		return MBCategoryLocalServiceUtil.getCategory(getParentCategoryId());
	}

	@Override
	public boolean isRoot() {
		if (getParentCategoryId() ==
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			return true;
		}

		return false;
	}

}