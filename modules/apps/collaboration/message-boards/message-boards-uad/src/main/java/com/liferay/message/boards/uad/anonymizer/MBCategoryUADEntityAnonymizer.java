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

package com.liferay.message.boards.uad.anonymizer;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.uad.constants.MBUADConstants;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import com.liferay.user.associated.data.anonymizer.DynamicQueryUADEntityAnonymizer;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.util.UADAnonymizerHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Arrays;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + MBUADConstants.CLASS_NAME_MB_CATEGORY}, service = UADEntityAnonymizer.class)
public class MBCategoryUADEntityAnonymizer
	extends DynamicQueryUADEntityAnonymizer<MBCategory> {
	@Override
	public void autoAnonymize(MBCategory mbCategory, long userId)
		throws PortalException {
		User anonymousUser = _uadAnonymizerHelper.getAnonymousUser();

		if (mbCategory.getUserId() == userId) {
			mbCategory.setUserId(anonymousUser.getUserId());
			mbCategory.setUserName(anonymousUser.getFullName());
		}

		if (mbCategory.getStatusByUserId() == userId) {
			mbCategory.setStatusByUserId(anonymousUser.getUserId());
			mbCategory.setStatusByUserName(anonymousUser.getFullName());
		}

		_mbCategoryLocalService.updateMBCategory(mbCategory);
	}

	@Override
	public void delete(MBCategory mbCategory) throws PortalException {
		_mbCategoryLocalService.deleteCategory(mbCategory);
	}

	@Override
	public List<String> getNonanonymizableFieldNames() {
		return Arrays.asList("name", "description");
	}

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return _mbCategoryLocalService.getActionableDynamicQuery();
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return MBUADConstants.USER_ID_FIELD_NAMES_MB_CATEGORY;
	}

	@Reference
	private MBCategoryLocalService _mbCategoryLocalService;
	@Reference
	private UADAnonymizerHelper _uadAnonymizerHelper;
}