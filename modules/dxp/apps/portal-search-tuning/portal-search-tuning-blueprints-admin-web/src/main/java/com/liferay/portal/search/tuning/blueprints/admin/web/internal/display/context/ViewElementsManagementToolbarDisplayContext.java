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

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.security.permission.resource.BlueprintsAdminPermission;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintsActionKeys;
import com.liferay.portal.search.tuning.blueprints.constants.ElementTypes;
import com.liferay.portal.search.tuning.blueprints.model.Element;

/**
 * @author Petteri Karttunen
 */
public class ViewElementsManagementToolbarDisplayContext
	extends ViewEntriesManagementToolbarDisplayContext {

	public ViewElementsManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<Element> searchContainer, String displayStyle) {

		super(
			liferayPortletRequest.getHttpServletRequest(),
			liferayPortletRequest, liferayPortletResponse, searchContainer,
			displayStyle);
	}

	@Override
	public CreationMenu getCreationMenu() {
		if (!BlueprintsAdminPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				BlueprintsActionKeys.ADD_ELEMENT)) {

			return null;
		}

		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addElement");
				dropdownItem.putData(
					"defaultLocale",
					LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

				dropdownItem.putData(
					"editElementURL",
					createActionURL(
						BlueprintsAdminMVCCommandNames.EDIT_ELEMENT,
						Constants.ADD));

				dropdownItem.putData(
					"type", String.valueOf(ElementTypes.QUERY_ELEMENT));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "add-element"));
			}
		).build();
	}

}