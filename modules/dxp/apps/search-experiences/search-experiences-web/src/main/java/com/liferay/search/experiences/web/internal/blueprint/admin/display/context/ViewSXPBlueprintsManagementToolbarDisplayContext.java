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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.search.experiences.constants.SXPActionKeys;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.web.internal.security.permission.resource.SXPPermission;

import java.util.List;

/**
 * @author Petteri Karttunen
 */
public class ViewSXPBlueprintsManagementToolbarDisplayContext
	extends BaseManagementToolbarDisplayContext {

	public ViewSXPBlueprintsManagementToolbarDisplayContext(
		String displayStyle, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<SXPBlueprint> searchContainer) {

		super(
			displayStyle, liferayPortletRequest.getHttpServletRequest(),
			liferayPortletRequest, liferayPortletResponse, searchContainer);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemList.of(
			DropdownItemBuilder.putData(
				"action", "deleteSXPBlueprints"
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
				SXPActionKeys.ADD_SXP_BLUEPRINT)) {

			return null;
		}

		return CreationMenuBuilder.addDropdownItem(
			DropdownItemBuilder.putData(
				"action", "addSXPBlueprint"
			).putData(
				"contextPath", liferayPortletRequest.getContextPath()
			).putData(
				"defaultLocale",
				LocaleUtil.toLanguageId(LocaleUtil.getDefault())
			).setLabel(
				LanguageUtil.get(httpServletRequest, "add-blueprint")
			).build()
		).build();
	}

	@Override
	protected String getMVCRenderCommandName() {
		return "/sxp_blueprint_admin/view_sxp_blueprints";
	}

}