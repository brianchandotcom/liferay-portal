/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.site;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.site.SitesProvider;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ResourceRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gustavo Lima
 */
@Component(service = SitesProvider.class)
public class SitesProviderImpl implements SitesProvider {

	@Override
	public JSONObject getSiteByExternalReferenceCodeJSONObject(
			ResourceRequest resourceRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = _groupService.fetchGroupByExternalReferenceCode(
			ParamUtil.getString(resourceRequest, "externalReferenceCode"),
			themeDisplay.getCompanyId());

		if (group == null) {
			return null;
		}

		return JSONUtil.put(
			"descriptiveName",
			group.getDescriptiveName(themeDisplay.getLocale())
		).put(
			"externalReferenceCode", group.getExternalReferenceCode()
		).put(
			"groupId", group.getGroupId()
		).put(
			"name", group.getName(themeDisplay.getLocale())
		);
	}

	@Override
	public JSONObject getSitesJSONObject(ResourceRequest resourceRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<Group> allGroups = new ArrayList<>();

		_addGroupsWithChildren(
			allGroups,
			_groupService.getGroups(
				themeDisplay.getCompanyId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID, true));

		allGroups.sort(
			(g1, g2) -> {
				try {
					return g1.getDescriptiveName(
						themeDisplay.getLocale()
					).compareTo(
						g2.getDescriptiveName(themeDisplay.getLocale())
					);
				}
				catch (PortalException portalException) {
					_log.error(portalException);
				}

				return 0;
			});

		int pageSize = ParamUtil.getInteger(resourceRequest, "pageSize");
		int totalCount = allGroups.size();

		int lastPage = (int)Math.ceil((double)totalCount / pageSize);

		int page = ParamUtil.getInteger(resourceRequest, "page");

		if (page > lastPage) {
			page = lastPage;
		}

		int end = pageSize * page;

		int start = end - pageSize;

		if (end > totalCount) {
			end = totalCount;
		}

		return JSONUtil.put(
			"items",
			JSONUtil.toJSONArray(
				allGroups.subList(start, end),
				group -> JSONUtil.put(
					"descriptiveName",
					group.getDescriptiveName(themeDisplay.getLocale())
				).put(
					"externalReferenceCode", group.getExternalReferenceCode()
				).put(
					"groupId", group.getGroupId()
				).put(
					"name", group.getName(themeDisplay.getLocale())
				))
		).put(
			"lastPage", lastPage
		).put(
			"page", page
		).put(
			"pageSize", pageSize
		).put(
			"totalCount", totalCount
		);
	}

	private void _addGroupsWithChildren(
		List<Group> allGroups, List<Group> groups) {

		groups.forEach(
			group -> {
				if (!group.isActive()) {
					return;
				}

				_addGroupsWithChildren(allGroups, group.getChildren(true));

				allGroups.add(group);
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SitesProviderImpl.class);

	@Reference
	private GroupService _groupService;

}