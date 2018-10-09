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

package com.liferay.asset.list.web.internal.display.context;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.list.service.AssetListEntryUsageLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class AssetListUsagesDisplayContext {

	public AssetListUsagesDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_request = PortalUtil.getHttpServletRequest(renderRequest);
	}

	public int getAllUsageCount() {
		return AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsagesCount(
			getAssetListEntryId());
	}

	public long getAssetListEntryId() {
		if (_assetListEntryId != null) {
			return _assetListEntryId;
		}

		_assetListEntryId = ParamUtil.getLong(
			_renderRequest, "assetListEntryId");

		return _assetListEntryId;
	}

	public int getDisplayPagesUsageCount() {
		return AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsagesCount(
			getAssetListEntryId(),
			PortalUtil.getClassNameId(AssetDisplayPageEntry.class));
	}

	public SearchContainerManagementToolbarDisplayContext
		getManagementToolbarDisplayContext() throws PortalException {

		return new SearchContainerManagementToolbarDisplayContext(
			PortalUtil.getLiferayPortletRequest(_renderRequest),
			PortalUtil.getLiferayPortletResponse(_renderResponse), _request,
			getSearchContainer()) {

			@Override
			public List<DropdownItem> getOrderByDropdownItems() {
				return new DropdownItemList() {
					{
						add(
							dropdownItem -> {
								dropdownItem.setActive(true);
								dropdownItem.setHref(
									getPortletURL(), "orderByCol",
									"modified-date");
								dropdownItem.setLabel(
									LanguageUtil.get(
										_request, "modified-date"));
							});
					}
				};
			}

		};
	}

	public String getNavigation() {
		if (Validator.isNotNull(_navigation)) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_renderRequest, "navigation", "all");

		return _navigation;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return _orderByType;
	}

	public int getPagesUsageCount() {
		return AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsagesCount(
			getAssetListEntryId(), PortalUtil.getClassNameId(Layout.class));
	}

	public int getPageTemplatesUsageCount() {
		return AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsagesCount(
			getAssetListEntryId(),
			PortalUtil.getClassNameId(LayoutPageTemplateEntry.class));
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_usages.jsp");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter(
			"assetListEntryId", String.valueOf(getAssetListEntryId()));

		return portletURL;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_renderRequest, "redirect");

		return _redirect;
	}

	public SearchContainer getSearchContainer() {
		return null;
	}

	private Long _assetListEntryId;
	private String _navigation;
	private String _orderByType;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}