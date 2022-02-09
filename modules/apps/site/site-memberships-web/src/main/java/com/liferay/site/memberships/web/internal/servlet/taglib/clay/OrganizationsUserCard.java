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

package com.liferay.site.memberships.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseBaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.UserCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.site.memberships.web.internal.servlet.taglib.util.OrganizationActionDropdownItemsProvider;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class OrganizationsUserCard
	extends BaseBaseClayCard implements UserCard {

	public OrganizationsUserCard(
		Organization organization, boolean showActions,
		RenderRequest renderRequest, RenderResponse renderResponse,
		RowChecker rowChecker) {

		super(organization, rowChecker);

		_organization = organization;
		_showActions = showActions;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		if (!_showActions) {
			return null;
		}

		try {
			OrganizationActionDropdownItemsProvider
				organizationActionDropdownItemsProvider =
					new OrganizationActionDropdownItemsProvider(
						_organization, _renderRequest, _renderResponse);

			return organizationActionDropdownItemsProvider.
				getActionDropdownItems();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	@Override
	public String getImageSrc() {
		return _organization.getLogoURL();
	}

	@Override
	public String getName() {
		return _organization.getName();
	}

	@Override
	public String getSubtitle() {
		return LanguageUtil.get(_httpServletRequest, _organization.getType());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationsUserCard.class);

	private final HttpServletRequest _httpServletRequest;
	private final Organization _organization;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final boolean _showActions;

}