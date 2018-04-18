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

package com.liferay.message.boards.uad.display;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.uad.constants.MBUADConstants;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import com.liferay.user.associated.data.display.UADEntityDisplay;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + MBUADConstants.CLASS_NAME_MB_CATEGORY}, service = UADEntityDisplay.class)
public class MBCategoryUADEntityDisplay implements UADEntityDisplay<MBCategory> {
	public String getApplicationName() {
		return MBUADConstants.APPLICATION_NAME;
	}

	public String[] getDisplayFieldNames() {
		return _mbCategoryUADEntityDisplayHelper.getDisplayFieldNames();
	}

	@Override
	public String getEditURL(MBCategory mbCategory,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse)
		throws Exception {
		return _mbCategoryUADEntityDisplayHelper.getMBCategoryEditURL(mbCategory,
			liferayPortletRequest, liferayPortletResponse);
	}

	public String getKey() {
		return MBUADConstants.CLASS_NAME_MB_CATEGORY;
	}

	@Override
	public Map<String, Object> getNonanonymizableFieldValues(
		MBCategory mbCategory) {
		return _mbCategoryUADEntityDisplayHelper.getUADEntityNonanonymizableFieldValues(mbCategory);
	}

	@Override
	public String getTypeDescription() {
		return "";
	}

	@Override
	public String getTypeName() {
		return "MBCategory";
	}

	@Reference
	private MBCategoryUADEntityDisplayHelper _mbCategoryUADEntityDisplayHelper;
}