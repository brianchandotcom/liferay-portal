/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Tomas Polesovsky
 */
public class OAuth2AuthorizationsManagementToolbarDisplayContext
	extends BaseOAuth2ManagementToolbarDisplayContext {

	public OAuth2AuthorizationsManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, long oAuth2ApplicationId,
		SearchContainer<?> searchContainer) {

		super(
			liferayPortletRequest.getHttpServletRequest(),
			liferayPortletRequest, liferayPortletResponse, searchContainer);

		_oAuth2ApplicationId = oAuth2ApplicationId;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "revokeOAuth2Authorizations");
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "revoke-authorizations"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	@Override
	public Map<String, Object> getAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"revokeOAuth2AuthorizationsURL",
			PortletURLBuilder.createActionURL(
				liferayPortletResponse
			).setActionName(
				"/admin/revoke_oauth2_authorizations"
			).setMVCRenderCommandName(
				"/oauth2_provider/view_oauth2_authorizations"
			).setBackURL(
				ParamUtil.getString(httpServletRequest, "redirect")
			).setNavigation(
				"application_authorizations"
			).setParameter(
				"oAuth2ApplicationId", _oAuth2ApplicationId
			).buildString()
		).build();
	}

	@Override
	public List<DropdownItem> getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				for (String orderByCol : _ORDER_BY_COLUMNS) {
					add(
						dropdownItem -> {
							dropdownItem.setActive(
								orderByCol.equals(getOrderByCol()));
							dropdownItem.setHref(
								getCurrentSortingURL(), "orderByCol",
								orderByCol);
							dropdownItem.setLabel(
								LanguageUtil.get(
									httpServletRequest, orderByCol));
						});
				}
			}
		};
	}

	@Override
	public String getSearchContainerId() {
		return "oAuth2AuthorizationsSearchContainer";
	}

	@Override
	public Boolean isSelectable() {
		return true;
	}

	@Override
	public Boolean isShowSearch() {
		return false;
	}

	private static final String[] _ORDER_BY_COLUMNS = {
		"createDate", "userId", "userName", "accessTokenCreateDate",
		"accessTokenExpirationDate", "refreshTokenCreateDate",
		"refreshTokenExpirationDate", "remoteIPInfo"
	};

	private final long _oAuth2ApplicationId;

}