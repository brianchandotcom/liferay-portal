/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.oauth2.provider.web.internal.constants.OAuth2ProviderPortletKeys;
import com.liferay.oauth2.provider.web.internal.constants.OAuth2ProviderWebKeys;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.SearchDisplayStyleUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;

/**
 * @author Tomas Polesovsky
 */
public class OAuth2ApplicationsManagementToolbarDisplayContext
	extends BaseOAuth2ManagementToolbarDisplayContext {

	public OAuth2ApplicationsManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<?> searchContainer) {

		super(
			liferayPortletRequest.getHttpServletRequest(),
			liferayPortletRequest, liferayPortletResponse, searchContainer);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteOAuth2Applications");
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	@Override
	public Map<String, Object> getAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"deleteOAuth2ApplicationsURL",
			() -> PortletURLBuilder.createActionURL(
				liferayPortletResponse
			).setActionName(
				"/oauth2_provider/delete_oauth2_applications"
			).buildString()
		).build();
	}

	@Override
	public CreationMenu getCreationMenu() {
		OAuth2AdminPortletDisplayContext oAuth2AdminPortletDisplayContext =
			(OAuth2AdminPortletDisplayContext)httpServletRequest.getAttribute(
				OAuth2ProviderWebKeys.OAUTH2_ADMIN_PORTLET_DISPLAY_CONTEXT);

		if (!oAuth2AdminPortletDisplayContext.hasAddApplicationPermission()) {
			return null;
		}

		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					liferayPortletResponse.createRenderURL(),
					"mvcRenderCommandName",
					"/oauth2_provider/update_oauth2_application", "redirect",
					currentURLObj.toString());
				dropdownItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "add-o-auth2-application"));
			}
		).build();
	}

	@Override
	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = SearchDisplayStyleUtil.getDisplayStyle(
			httpServletRequest, OAuth2ProviderPortletKeys.OAUTH2_ADMIN,
			"entries-display-style", "list", true);

		return _displayStyle;
	}

	@Override
	public List<DropdownItem> getOrderByDropdownItems() {
		return getOrderByDropdownItems(
			HashMapBuilder.put(
				"clientId", "client-id"
			).put(
				"createDate", "createDate"
			).put(
				"name", "name"
			).build());
	}

	@Override
	public String getSearchContainerId() {
		return "oAuth2ApplicationsSearchContainer";
	}

	@Override
	public Boolean isSelectable() {
		return true;
	}

	@Override
	public Boolean isShowSearch() {
		return false;
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list", "descriptive"};
	}

	private String _displayStyle;

}