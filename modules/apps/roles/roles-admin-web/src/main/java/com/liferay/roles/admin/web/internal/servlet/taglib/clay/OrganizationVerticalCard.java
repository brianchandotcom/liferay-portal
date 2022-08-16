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

package com.liferay.roles.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.BaseVerticalCard;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class OrganizationVerticalCard extends BaseVerticalCard {

	public OrganizationVerticalCard(
		Organization organization, RenderRequest renderRequest,
		RowChecker rowChecker) {

		super(organization, renderRequest, rowChecker);

		_organization = organization;
	}

	@Override
	public String getIcon() {
		return "users";
	}

	@Override
	public String getInputValue() {
		return String.valueOf(_organization.getGroupId());
	}

	@Override
	public String getSubtitle() {
		return LanguageUtil.get(
			themeDisplay.getLocale(), _organization.getType());
	}

	@Override
	public String getTitle() {
		return _organization.getName();
	}

	private final Organization _organization;

}