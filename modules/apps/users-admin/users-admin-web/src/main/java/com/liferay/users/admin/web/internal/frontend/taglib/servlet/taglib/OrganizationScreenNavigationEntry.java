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

package com.liferay.users.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.users.admin.constants.UserScreenNavigationEntryConstants;
import com.liferay.users.admin.web.internal.constants.UsersAdminWebKeys;
import com.liferay.users.admin.web.internal.display.context.OrganizationScreenNavigationDisplayContext;

import java.io.IOException;

import java.util.Locale;
import java.util.function.BiFunction;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Drew Brokke
 */
public class OrganizationScreenNavigationEntry
	implements ScreenNavigationEntry<Organization> {

	public OrganizationScreenNavigationEntry(
		JSPRenderer jspRenderer, OrganizationService organizationService,
		Portal portal, PortletURLFactory portletURLFactory, String entryKey,
		String categoryKey, String jspPath, String mvcActionCommandName,
		boolean showControls, boolean showTitle,
		BiFunction<User, Organization, Boolean> isVisibleBiFunction) {

		_jspRenderer = jspRenderer;
		_organizationService = organizationService;
		_portal = portal;
		_portletURLFactory = portletURLFactory;
		_entryKey = entryKey;
		_categoryKey = categoryKey;
		_jspPath = jspPath;
		_mvcActionCommandName = mvcActionCommandName;
		_showControls = showControls;
		_showTitle = showTitle;
		_isVisibleBiFunction = isVisibleBiFunction;
	}

	@Override
	public String getCategoryKey() {
		return _categoryKey;
	}

	@Override
	public String getEntryKey() {
		return _entryKey;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getBundle(
				locale, OrganizationScreenNavigationEntry.class),
			_entryKey);
	}

	@Override
	public String getScreenNavigationKey() {
		return UserScreenNavigationEntryConstants.
			SCREEN_NAVIGATION_KEY_ORGANIZATIONS;
	}

	@Override
	public boolean isVisible(User user, Organization organization) {
		return _isVisibleBiFunction.apply(user, organization);
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		OrganizationScreenNavigationDisplayContext
			organizationScreenNavigationDisplayContext =
				new OrganizationScreenNavigationDisplayContext();

		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		String backURL = ParamUtil.getString(
			httpServletRequest, "backURL", redirect);

		organizationScreenNavigationDisplayContext.setBackURL(backURL);

		organizationScreenNavigationDisplayContext.setFormLabel(
			getLabel(httpServletRequest.getLocale()));
		organizationScreenNavigationDisplayContext.setJspPath(_jspPath);

		long organizationId = ParamUtil.getLong(
			httpServletRequest, "organizationId");

		Organization organization = null;

		try {
			organization = _organizationService.fetchOrganization(
				organizationId);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		organizationScreenNavigationDisplayContext.setOrganization(
			organization);
		organizationScreenNavigationDisplayContext.setOrganizationId(
			organizationId);
		organizationScreenNavigationDisplayContext.setShowControls(
			_showControls);
		organizationScreenNavigationDisplayContext.setShowTitle(_showTitle);

		String portletId = _portal.getPortletId(httpServletRequest);

		PortletURL editOrganizationActionURL = _portletURLFactory.create(
			httpServletRequest, portletId, PortletRequest.ACTION_PHASE);

		editOrganizationActionURL.setParameter(
			ActionRequest.ACTION_NAME, _mvcActionCommandName);

		PortletURL editOrganizationRenderURL = _portletURLFactory.create(
			httpServletRequest, portletId, PortletRequest.RENDER_PHASE);

		editOrganizationRenderURL.setParameter(
			"mvcRenderCommandName", "/users_admin/edit_organization");
		editOrganizationRenderURL.setParameter("backURL", backURL);
		editOrganizationRenderURL.setParameter(
			"organizationId", String.valueOf(organizationId));
		editOrganizationRenderURL.setParameter(
			"screenNavigationCategoryKey", _categoryKey);
		editOrganizationRenderURL.setParameter(
			"screenNavigationEntryKey", _entryKey);

		editOrganizationActionURL.setParameter(
			"redirect", editOrganizationRenderURL.toString());

		editOrganizationActionURL.setParameter("backURL", backURL);
		editOrganizationActionURL.setParameter(
			"organizationId", String.valueOf(organizationId));
		editOrganizationActionURL.setParameter(
			"screenNavigationCategoryKey", _categoryKey);
		editOrganizationActionURL.setParameter(
			"screenNavigationEntryKey", _entryKey);

		organizationScreenNavigationDisplayContext.setEditOrganizationActionURL(
			editOrganizationActionURL.toString());

		httpServletRequest.setAttribute(
			UsersAdminWebKeys.ORGANIZATION_SCREEN_NAVIGATION_DISPLAY_CONTEXT,
			organizationScreenNavigationDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/edit_organization_navigation.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationScreenNavigationEntry.class);

	private final String _categoryKey;
	private final String _entryKey;
	private final BiFunction<User, Organization, Boolean> _isVisibleBiFunction;
	private final String _jspPath;
	private final JSPRenderer _jspRenderer;
	private final String _mvcActionCommandName;
	private final OrganizationService _organizationService;
	private final Portal _portal;
	private final PortletURLFactory _portletURLFactory;
	private final boolean _showControls;
	private final boolean _showTitle;

}