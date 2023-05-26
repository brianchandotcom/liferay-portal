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

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.web.internal.security.permission.resource.FragmentPermission;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ContributedFragmentManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ContributedFragmentManagementToolbarDisplayContext(
		FragmentDisplayContext fragmentDisplayContext,
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			fragmentDisplayContext.getContributedEntriesSearchContainer());

		_fragmentDisplayContext = fragmentDisplayContext;
		_liferayPortletResponse = liferayPortletResponse;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return DropdownItemListBuilder.add(
			() -> FragmentPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES),
			dropdownItem -> {
				dropdownItem.putData(
					"action", "copyContributedEntriesToFragmentCollection");
				dropdownItem.setIcon("copy");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "make-a-copy"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).buildString();
	}

	public Map<String, Object> getComponentContext() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return HashMapBuilder.<String, Object>put(
			"addFragmentCollectionURL",
			() -> {
				LiferayPortletURL addFragmentCollectionURL =
					(LiferayPortletURL)
						_liferayPortletResponse.createResourceURL();

				addFragmentCollectionURL.setCopyCurrentRenderParameters(false);
				addFragmentCollectionURL.setResourceID(
					"/fragment/add_fragment_collection");

				return addFragmentCollectionURL.toString();
			}
		).put(
			"copyContributedEntryURL",
			() -> PortletURLBuilder.createActionURL(
				liferayPortletResponse
			).setActionName(
				"/fragment/copy_fragment_entry"
			).setRedirect(
				themeDisplay.getURLCurrent()
			).buildString()
		).put(
			"fragmentCollections",
			() -> {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				for (FragmentCollection fragmentCollection :
						FragmentCollectionLocalServiceUtil.
							getFragmentCollections(
								themeDisplay.getScopeGroupId(),
								QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

					jsonArray.put(
						JSONUtil.put(
							"fragmentCollectionId",
							fragmentCollection.getFragmentCollectionId()
						).put(
							"name", fragmentCollection.getName()
						));
				}

				return jsonArray;
			}
		).put(
			"selectFragmentCollectionURL",
			() -> PortletURLBuilder.createActionURL(
				liferayPortletResponse
			).setMVCRenderCommandName(
				"/fragment/select_fragment_collection"
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString()
		).build();
	}

	@Override
	public String getComponentId() {
		return "contributedFragmentEntriesManagementToolbar" +
			_fragmentDisplayContext.getFragmentCollectionKey();
	}

	@Override
	public String getSortingURL() {
		return null;
	}

	private final FragmentDisplayContext _fragmentDisplayContext;
	private final LiferayPortletResponse _liferayPortletResponse;

}