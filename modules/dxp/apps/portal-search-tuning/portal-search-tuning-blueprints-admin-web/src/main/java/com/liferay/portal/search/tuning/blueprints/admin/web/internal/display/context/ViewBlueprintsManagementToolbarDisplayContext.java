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

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminTabNames;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.security.permission.resource.BlueprintPermission;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintTypes;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintsActionKeys;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;

import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Petteri Karttunen
 */
public class ViewBlueprintsManagementToolbarDisplayContext
	extends ViewEntriesManagementToolbarDisplayContext {

	public ViewBlueprintsManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<Blueprint> searchContainer, String displayStyle) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer, displayStyle, BlueprintsAdminTabNames.BLUEPRINTS);
	}

	@Override
	public CreationMenu getCreationMenu() {
		if (!BlueprintPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				BlueprintsActionKeys.ADD_BLUEPRINT)) {

			return null;
		}

		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addBlueprint");
				dropdownItem.putData(
					"contextPath", portletRequest.getContextPath());
				dropdownItem.putData(
					"defaultLocale",
					LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

				dropdownItem.putData(
					"editBlueprintURL",
					createActionURL(
						BlueprintsAdminMVCCommandNames.EDIT_BLUEPRINT,
						Constants.ADD));

				dropdownItem.putData(
					"searchableAssetTypesString",
					_getSearchableAssetTypesString());

				dropdownItem.putData(
					"type", String.valueOf(BlueprintTypes.BLUEPRINT));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "add-blueprint"));
			}
		).build();
	}

	private String _getSearchableAssetTypesString() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<AssetRendererFactory<?>> assetRendererFactories =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				themeDisplay.getCompanyId(), false);

		Stream<AssetRendererFactory<?>> stream =
			assetRendererFactories.stream();

		stream.filter(
			item -> item.isSearchable()
		).forEach(
			item -> jsonArray.put(item.getClassName())
		);

		return jsonArray.toString();
	}

}