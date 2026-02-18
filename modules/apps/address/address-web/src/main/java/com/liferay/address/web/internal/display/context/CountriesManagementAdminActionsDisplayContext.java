/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.CountryPermissionUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author Tancredi Covioli
 */
public class CountriesManagementAdminActionsDisplayContext {

	public CountriesManagementAdminActionsDisplayContext(
		Country country, HttpServletRequest httpServletRequest,
		PermissionChecker permissionChecker, RenderResponse renderResponse) {

		_country = country;
		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;

		_hasDeletePermission = CountryPermissionUtil.contains(
			permissionChecker, country, ActionKeys.DELETE);
		_hasUpdatePermission = CountryPermissionUtil.contains(
			permissionChecker, country, ActionKeys.UPDATE);
	}

	public List<DropdownItem> getActionDropdownItems() {
		String backURL = ParamUtil.getString(
			_httpServletRequest, "backURL",
			String.valueOf(_renderResponse.createRenderURL()));
		String currentURL = PortalUtil.getCurrentURL(_httpServletRequest);
		String navigation = ParamUtil.getString(
			_httpServletRequest, "navigation", "all");

		return DropdownItemListBuilder.add(
			() -> _hasUpdatePermission || _hasDeletePermission,
			dropDownItem -> {
				dropDownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_renderResponse
					).setMVCRenderCommandName(
						"/address/edit_country"
					).setBackURL(
						backURL
					).setParameter(
						"countryId", _country.getCountryId()
					).buildString());
				dropDownItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest,
						_hasUpdatePermission ? "edit" : "view"));
			}
		).add(
			() -> _hasUpdatePermission,
			dropDownItem -> {
				dropDownItem.setData(
					HashMapBuilder.<String, Object>put(
						"action",
						_country.isActive() ? "deactivate" : "activate"
					).put(
						"confirmationMessage",
						LanguageUtil.get(
							_httpServletRequest,
							"are-you-sure-you-want-to-deactivate-this")
					).put(
						"updateStatusURL",
						PortletURLBuilder.createActionURL(
							_renderResponse
						).setActionName(
							"/address/update_country_status"
						).setCMD(
							_country.isActive() ? Constants.DEACTIVATE :
								Constants.RESTORE
						).setRedirect(
							currentURL
						).setNavigation(
							navigation
						).setParameter(
							"countryIds", _country.getCountryId()
						).buildString()
					).build());
				dropDownItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest,
						_country.isActive() ? "deactivate" : "activate"));
			}
		).add(
			() -> _hasDeletePermission,
			dropDownItem -> {
				dropDownItem.setData(
					HashMapBuilder.<String, Object>put(
						"action", "delete"
					).put(
						"confirmationMessage",
						LanguageUtil.get(
							_httpServletRequest,
							"are-you-sure-you-want-to-delete-this")
					).put(
						"deleteCountryURL",
						PortletURLBuilder.createActionURL(
							_renderResponse
						).setActionName(
							"/address/delete_country"
						).setRedirect(
							currentURL
						).setParameter(
							"countryIds", _country.getCountryId()
						).buildString()
					).build());
				dropDownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "delete"));
			}
		).build();
	}

	private final Country _country;
	private final boolean _hasDeletePermission;
	private final boolean _hasUpdatePermission;
	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;

}