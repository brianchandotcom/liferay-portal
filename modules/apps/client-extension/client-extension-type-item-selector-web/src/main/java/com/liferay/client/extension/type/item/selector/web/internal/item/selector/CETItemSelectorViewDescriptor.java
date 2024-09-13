/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.item.selector.web.internal.item.selector;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.GlobalJSCET;
import com.liferay.client.extension.type.item.selector.CETItemSelectorReturnType;
import com.liferay.client.extension.type.item.selector.criterion.CETItemSelectorCriterion;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class CETItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<CET> {

	public CETItemSelectorViewDescriptor(
		CETManager cetManager,
		CETItemSelectorCriterion cetItemSelectorCriterion,
		HttpServletRequest httpServletRequest, PortletURL portletURL) {

		_cetManager = cetManager;
		_cetItemSelectorCriterion = cetItemSelectorCriterion;
		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
	}

	@Override
	public String getDefaultDisplayStyle() {
		return "descriptive";
	}

	@Override
	public ItemDescriptor getItemDescriptor(CET cet) {
		return new CETItemDescriptor(cet);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return _supportedItemSelectorReturnType;
	}

	@Override
	public SearchContainer<CET> getSearchContainer() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<CET> searchContainer = new SearchContainer<>(
			(PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST),
			_portletURL, null, "there-are-no-items-to-display");

		List<CET> cets = _cetManager.getCETs(
			themeDisplay.getCompanyId(), null,
			_cetItemSelectorCriterion.getType(),
			Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS), null);

		if (Objects.equals(
				_cetItemSelectorCriterion.getType(),
				ClientExtensionEntryConstants.TYPE_GLOBAL_JS)) {

			List<CET> filteredCETs = new ArrayList<>();

			for (CET cet : cets) {
				GlobalJSCET globalJSCET = (GlobalJSCET)cet;

				if (!Objects.equals(globalJSCET.getScope(), "instance")) {
					filteredCETs.add(cet);
				}
			}

			cets = filteredCETs;
		}

		searchContainer.setResultsAndTotal(cets);

		return searchContainer;
	}

	@Override
	public boolean isMultipleSelection() {
		return _cetItemSelectorCriterion.isMultipleSelection();
	}

	@Override
	public boolean isShowBreadcrumb() {
		return false;
	}

	private static final ItemSelectorReturnType
		_supportedItemSelectorReturnType = new CETItemSelectorReturnType();

	private final CETItemSelectorCriterion _cetItemSelectorCriterion;
	private final CETManager _cetManager;
	private final HttpServletRequest _httpServletRequest;
	private final PortletURL _portletURL;

}