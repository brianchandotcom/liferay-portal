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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.search.experiences.constants.SXPActionKeys;
import com.liferay.search.experiences.constants.SXPElementTypes;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.web.internal.security.permission.resource.SXPBlueprintPermission;

import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author Petteri Karttunen
 */
public class ViewSXPElementsManagementToolbarDisplayContext
	extends BaseBlueprintManagementToolbarDisplayContext {

	public ViewSXPElementsManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<SXPElement> searchContainer, String displayStyle) {

		super(
			liferayPortletRequest.getHttpServletRequest(),
			liferayPortletRequest, liferayPortletResponse, searchContainer,
			displayStyle);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "hideSXPElements");

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "hide"));

				dropdownItem.setQuickAction(true);
			}
		).add(
			dropdownItem -> {
				dropdownItem.putData("action", "showSXPElements");

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "show"));

				dropdownItem.setQuickAction(true);
			}
		).add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteSXPElements");

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));

				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	@Override
	public CreationMenu getCreationMenu() {
		if (!SXPBlueprintPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				SXPActionKeys.ADD_SXP_ELEMENT)) {

			return null;
		}

		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addSXPElement");
				dropdownItem.putData(
					"defaultLocale",
					LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

				dropdownItem.putData(
					"editElementURL",
					createActionURL(
						"/sxp_blueprint_admin/edit_sxp_element",
						Constants.ADD));

				dropdownItem.putData(
					"type", String.valueOf(SXPElementTypes.QUERY_SXP_ELEMENT));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "add-element"));
			}
		).build();
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterVisibilityDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "filter-by-visibility"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterReadOnlyDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "filter-by-type"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "order-by"));
			}
		).build();
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
			liferayPortletRequest, "readOnly", "");

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(readOnly.equals(""));
				dropdownItem.setHref(_getFilterReadOnlyURL(""));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "all"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(
					readOnly.equals(Boolean.TRUE.toString()));
				dropdownItem.setHref(
					_getFilterReadOnlyURL(Boolean.TRUE.toString()));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "default"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(
					readOnly.equals(Boolean.FALSE.toString()));
				dropdownItem.setHref(
					_getFilterReadOnlyURL(Boolean.FALSE.toString()));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "custom"));
			}
		).build();
	}

	private PortletURL _getFilterReadOnlyURL(String readOnly) {
		return PortletURLBuilder.create(
			getPortletURL()
		).setParameter(
			"readOnly", readOnly
		).build();
	}

	private List<DropdownItem> _getFilterVisibilityDropdownItems() {
		boolean hidden = ParamUtil.getBoolean(
			liferayPortletRequest, "hidden", Boolean.FALSE);

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(!hidden);
				dropdownItem.setHref(_getFilterVisibilityURL(Boolean.FALSE));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "visible"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(hidden);
				dropdownItem.setHref(_getFilterVisibilityURL(Boolean.TRUE));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "hidden"));
			}
		).build();
	}

	private PortletURL _getFilterVisibilityURL(Boolean hidden) {
		return PortletURLBuilder.create(
			getPortletURL()
		).setParameter(
			"hidden", hidden
		).build();
	}

}