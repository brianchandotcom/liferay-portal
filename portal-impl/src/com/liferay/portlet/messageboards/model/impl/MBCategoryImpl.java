/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MBCategoryImpl extends MBCategoryBaseImpl {

	public MBCategoryImpl() {
	}

	public List<Long> getAncestorCategoryIds()
		throws PortalException, SystemException {

		List<Long> ancestorCategoryIds = new ArrayList<Long>();

		MBCategory category = this;

		while (!category.isRoot()) {
			category = MBCategoryLocalServiceUtil.getCategory(
				category.getParentCategoryId());

			ancestorCategoryIds.add(category.getCategoryId());
		}

		return ancestorCategoryIds;
	}

	public List<MBCategory> getAncestors()
		throws PortalException, SystemException {

		List<MBCategory> ancestors = new ArrayList<MBCategory>();

		MBCategory category = this;

		while (!category.isRoot()) {
			category = category.getParentCategory();

			ancestors.add(category);
		}

		return ancestors;
	}

	public MBCategory getParentCategory()
		throws PortalException, SystemException {

		if (getParentCategoryId() ==
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			return null;
		}

		return MBCategoryLocalServiceUtil.getCategory(getParentCategoryId());
	}

	public MBCategory getTrashCategory() {
		MBCategory category = null;

		try {
			category = getParentCategory();
		}
		catch (Exception e) {
			return null;
		}

		while (category != null) {
			if (category.isInTrash()) {
				return category;
			}

			try {
				category = category.getParentCategory();
			}
			catch (Exception e) {
				return null;
			}
		}

		return null;
	}

	public boolean isInTrashCategory() {
		if (getTrashCategory() != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isRoot() {
		if (getParentCategoryId() ==
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			return true;
		}

		return false;
	}

}