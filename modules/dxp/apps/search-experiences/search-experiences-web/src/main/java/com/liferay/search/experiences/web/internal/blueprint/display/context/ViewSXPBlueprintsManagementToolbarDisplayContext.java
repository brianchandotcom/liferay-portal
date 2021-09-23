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

package com.liferay.search.experiences.web.internal.blueprint.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.search.experiences.constants.SXPActionKeys;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintMVCCommandNames;
import com.liferay.search.experiences.web.internal.blueprint.security.permission.resource.SXPBlueprintPermission;
import com.liferay.search.experiences.web.internal.blueprint.util.SXPBlueprintAssetUtil;
import com.liferay.search.experiences.web.internal.blueprint.util.SXPBlueprintContributorUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public class ViewSXPBlueprintsManagementToolbarDisplayContext
	extends ViewEntriesManagementToolbarDisplayContext {

	public ViewSXPBlueprintsManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<SXPBlueprint> searchContainer, String displayStyle) {

		super(
			liferayPortletRequest.getHttpServletRequest(),
			liferayPortletRequest, liferayPortletResponse, searchContainer,
			displayStyle);
	}

	@Override
	public CreationMenu getCreationMenu() {
		if (!SXPBlueprintPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				SXPActionKeys.ADD_SXP_BLUEPRINT)) {

			return null;
		}

		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addSXPBlueprint");
				dropdownItem.putData(
					"contextPath", liferayPortletRequest.getContextPath());
				dropdownItem.putData(
					"defaultLocale",
					LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

				dropdownItem.putData(
					"editSXPBlueprintURL",
					createActionURL(
						SXPBlueprintMVCCommandNames.EDIT_SXP_BLUEPRINT,
						Constants.ADD));

				dropdownItem.putData(
					"searchableAssetTypesString",
					_convertListToString(
						Arrays.asList(
							SXPBlueprintAssetUtil.getSearchableAssetNames(
								themeDisplay.getCompanyId()))));

				dropdownItem.putData(
					"keywordQueryContributorsString",
					_convertListToString(
						SXPBlueprintContributorUtil.
							getKeywordQueryContributors()));

				dropdownItem.putData(
					"modelPrefilterContributorsString",
					_convertListToString(
						SXPBlueprintContributorUtil.
							getModelPrefilterContributors()));

				dropdownItem.putData(
					"queryPrefilterContributorsString",
					_convertListToString(
						SXPBlueprintContributorUtil.
							getQueryPrefilterContributors()));

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "add-sxpBlueprint"));
			}
		).build();
	}

	private String _convertListToString(List<String> arrayListItems) {
		Stream<String> stream = arrayListItems.stream();

		return stream.collect(Collectors.joining("\",\"", "[\"", "\"]"));
	}

}