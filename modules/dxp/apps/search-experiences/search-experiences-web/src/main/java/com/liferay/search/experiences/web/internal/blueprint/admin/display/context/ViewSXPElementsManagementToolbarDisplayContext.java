/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.web.internal.blueprint.admin.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownGroupItemBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.search.experiences.constants.SXPActionKeys;
import com.liferay.search.experiences.constants.SXPElementConstants;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.web.internal.security.permission.resource.SXPPermission;

import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author Petteri Karttunen
 */
public class ViewSXPElementsManagementToolbarDisplayContext
	extends BaseManagementToolbarDisplayContext {

	public ViewSXPElementsManagementToolbarDisplayContext(
		String displayStyle, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<SXPElement> searchContainer) {

		super(
			displayStyle, liferayPortletRequest.getHttpServletRequest(),
			liferayPortletRequest, liferayPortletResponse, searchContainer);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemList.of(
			DropdownItemBuilder.putData(
				"action", "hideSXPElements"
			).setLabel(
				LanguageUtil.get(httpServletRequest, "hide")
			).setQuickAction(
				true
			).build(),
			DropdownItemBuilder.putData(
				"action", "showSXPElements"
			).setLabel(
				LanguageUtil.get(httpServletRequest, "show")
			).setQuickAction(
				true
			).build(),
			DropdownItemBuilder.putData(
				"action", "deleteSXPElements"
			).setLabel(
				LanguageUtil.get(httpServletRequest, "delete")
			).setQuickAction(
				true
			).build());
	}

	@Override
	public CreationMenu getCreationMenu() {
		if (!SXPPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				SXPActionKeys.ADD_SXP_ELEMENT)) {

			return null;
		}

		return CreationMenuBuilder.addDropdownItem(
			DropdownItemBuilder.putData(
				"action", "addSXPElement"
			).putData(
				"defaultLocale",
				LocaleUtil.toLanguageId(LocaleUtil.getDefault())
			).putData(
				"type", String.valueOf(SXPElementConstants.TYPE_QUERY)
			).setLabel(
				LanguageUtil.get(httpServletRequest, "add-element")
			).build()
		).build();
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemList.of(
			DropdownGroupItemBuilder.setDropdownItems(
				_getFilterVisibilityDropdownItems()
			).setLabel(
				LanguageUtil.get(httpServletRequest, "filter-by-visibility")
			).build(),
			DropdownGroupItemBuilder.setDropdownItems(
				_getFilterReadOnlyDropdownItems()
			).setLabel(
				LanguageUtil.get(httpServletRequest, "filter-by-type")
			).build(),
			DropdownGroupItemBuilder.setDropdownItems(
				getOrderByDropdownItems()
			).setLabel(
				LanguageUtil.get(httpServletRequest, "order-by")
			).build());
	}

	@Override
	public Boolean isDisabled() {
		return false;
	}

	@Override
	protected String getMVCRenderCommandName() {
		return "/sxp_blueprint_admin/view_sxp_elements";
	}

	private List<DropdownItem> _getFilterReadOnlyDropdownItems() {
		String readOnly = ParamUtil.getString(
			liferayPortletRequest, "readOnly");

		return DropdownItemList.of(
			DropdownItemBuilder.setActive(
				readOnly.equals("")
			).setHref(
				_getFilterReadOnlyURL("")
			).setLabel(
				LanguageUtil.get(httpServletRequest, "all")
			).build(),
			DropdownItemBuilder.setActive(
				readOnly.equals(Boolean.TRUE.toString())
			).setHref(
				_getFilterReadOnlyURL(Boolean.TRUE.toString())
			).setLabel(
				LanguageUtil.get(httpServletRequest, "default")
			).build(),
			DropdownItemBuilder.setActive(
				readOnly.equals(Boolean.FALSE.toString())
			).setHref(
				_getFilterReadOnlyURL(Boolean.FALSE.toString())
			).setLabel(
				LanguageUtil.get(httpServletRequest, "custom")
			).build());
	}

	private PortletURL _getFilterReadOnlyURL(String readOnly) {
		return PortletURLBuilder.create(
			getPortletURL()
		).setParameter(
			"readOnly", readOnly
		).build();
	}

	private List<DropdownItem> _getFilterVisibilityDropdownItems() {
		boolean hidden = ParamUtil.getBoolean(liferayPortletRequest, "hidden");

		return DropdownItemList.of(
			DropdownItemBuilder.setActive(
				!hidden
			).setHref(
				_getFilterVisibilityURL(false)
			).setLabel(
				LanguageUtil.get(httpServletRequest, "visible")
			).build(),
			DropdownItemBuilder.setActive(
				hidden
			).setHref(
				_getFilterVisibilityURL(true)
			).setLabel(
				LanguageUtil.get(httpServletRequest, "hidden")
			).build());
	}

	private PortletURL _getFilterVisibilityURL(boolean hidden) {
		return PortletURLBuilder.create(
			getPortletURL()
		).setParameter(
			"hidden", hidden
		).build();
	}

}