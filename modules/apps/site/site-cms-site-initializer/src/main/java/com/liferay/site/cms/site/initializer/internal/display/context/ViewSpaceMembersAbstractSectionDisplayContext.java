/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.TabsItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.TabsItemListBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.site.cms.site.initializer.internal.constants.CMSSpaceConstants;
import com.liferay.site.cms.site.initializer.internal.util.SpaceAbstractHeaderUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Roberto Díaz
 */
public class ViewSpaceMembersAbstractSectionDisplayContext {

	public ViewSpaceMembersAbstractSectionDisplayContext(
		long groupId, HttpServletRequest httpServletRequest, Language language,
		UserGroupLocalService userGroupLocalService,
		UserLocalService userLocalService) {

		_groupId = groupId;
		_httpServletRequest = httpServletRequest;
		_language = language;
		_userGroupLocalService = userGroupLocalService;
		_userLocalService = userLocalService;
	}

	public String getAPIURL(String type) {
		StringBundler sb = new StringBundler(9);

		sb.append("/o/headless-asset-library/v1.0/asset-libraries/");
		sb.append(_groupId);
		sb.append("/");
		sb.append(type);
		sb.append("?page=");
		sb.append(CMSSpaceConstants.SPACE_ABSTRACT_PAGE);
		sb.append("&pageSize=");
		sb.append(CMSSpaceConstants.SPACE_ABSTRACT_PAGE_SIZE);

		if (type.equals("user-accounts")) {
			sb.append("&nestedFields=assetLibraryCreator");
		}
		else {
			sb.append("&nestedFields=numberOfUserAccounts");
		}

		return sb.toString();
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addMembers");
				dropdownItem.putData("title", _getSpaceMembersHeaderTitle());
				dropdownItem.setLabel(
					_language.get(_httpServletRequest, "add-members"));
			}
		).build();
	}

	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			_language.get(_httpServletRequest, "add-members-to-this-space")
		).put(
			"image", "/states/cms_empty_state.svg"
		).put(
			"title", _language.get(_httpServletRequest, "no-members-yet")
		).build();
	}

	public Map<String, Object> getHeaderProps() throws Exception {
		return SpaceAbstractHeaderUtil.getSpaceAbstractHeaderProps(
			_httpServletRequest, "view-all-members",
			_getSpaceMembersHeaderTitle(), StringPool.BLANK);
	}

	public List<TabsItem> getTabsItems() {
		return TabsItemListBuilder.add(
			tabsItem -> {
				tabsItem.setActive(true);
				tabsItem.setLabel(_language.get(_httpServletRequest, "users"));
			}
		).add(
			tabsItem -> {
				tabsItem.setActive(true);
				tabsItem.setLabel(
					_language.get(_httpServletRequest, "user-groups"));
			}
		).build();
	}

	private String _getSpaceMembersHeaderTitle() {
		return StringBundler.concat(
			_language.get(_httpServletRequest, "members"), StringPool.SPACE,
			StringPool.OPEN_PARENTHESIS,
			_userGroupLocalService.getGroupUserGroupsCount(_groupId) +
				_userLocalService.getGroupUsersCount(_groupId),
			StringPool.CLOSE_PARENTHESIS);
	}

	private final long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final UserGroupLocalService _userGroupLocalService;
	private final UserLocalService _userLocalService;

}