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

package com.liferay.sites.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
public interface Sites {

	public static final String ANALYTICS_PREFIX = "analytics_";

	public static final int CONTENT_SHARING_WITH_CHILDREN_DEFAULT_VALUE = -1;

	public static final int CONTENT_SHARING_WITH_CHILDREN_DISABLED = 0;

	public static final int CONTENT_SHARING_WITH_CHILDREN_DISABLED_BY_DEFAULT =
		1;

	public static final int CONTENT_SHARING_WITH_CHILDREN_ENABLED = 3;

	public static final int CONTENT_SHARING_WITH_CHILDREN_ENABLED_BY_DEFAULT =
		2;

	public static final String LAST_MERGE_TIME = "last-merge-time";

	public static final String LAST_RESET_TIME = "last-reset-time";

	public static final String LAYOUT_UPDATEABLE = "layoutUpdateable";

	public static final String MERGE_FAIL_COUNT = "merge-fail-count";

	public static final String MERGE_FAIL_FRIENDLY_URL_LAYOUTS =
		"merge-fail-friendly-url-layouts";

	public static final String SHOW_SITE_NAME = "showSiteName";

	public void addMergeFailFriendlyURLLayout(Layout layout)
		throws PortalException;

	public void addPortletBreadcrumbEntries(
			Group group, HttpServletRequest request, PortletURL portletURL)
		throws Exception;

	public void addPortletBreadcrumbEntries(
			Group group, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception;

	public void addPortletBreadcrumbEntries(
			Group group, String pagesName, PortletURL redirectURL,
			HttpServletRequest request, RenderResponse renderResponse)
		throws Exception;

	public void applyLayoutPrototype(
			LayoutPrototype layoutPrototype, Layout targetLayout,
			boolean linkEnabled)
		throws Exception;

	public void copyLayout(
			long userId, Layout sourceLayout, Layout targetLayout,
			ServiceContext serviceContext)
		throws Exception;

	public void copyLookAndFeel(Layout targetLayout, Layout sourceLayout)
		throws Exception;

	public void copyPortletPermissions(Layout targetLayout, Layout sourceLayout)
		throws Exception;

	public void copyPortletSetups(Layout sourceLayout, Layout targetLayout)
		throws Exception;

	public void copyTypeSettings(Group sourceGroup, Group targetGroup)
		throws Exception;

	public Object[] deleteLayout(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception;

	public Object[] deleteLayout(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception;

	public void deleteLayout(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception;

	public File exportLayoutSetPrototype(
			LayoutSetPrototype layoutSetPrototype,
			ServiceContext serviceContext)
		throws PortalException;

	public Long[] filterGroups(List<Group> groups, String[] names);

	public Layout getLayoutSetPrototypeLayout(Layout layout);

	public Map<String, String[]> getLayoutSetPrototypeParameters(
		ServiceContext serviceContext);

	public int getMergeFailCount(LayoutPrototype layoutPrototype)
		throws PortalException;

	public int getMergeFailCount(LayoutSetPrototype layoutSetPrototype)
		throws PortalException;

	public List<Layout> getMergeFailFriendlyURLLayouts(LayoutSet layoutSet)
		throws PortalException;

	public List<String> getOrganizationNames(Group group, User user)
		throws Exception;

	public List<String> getUserGroupNames(Group group, User user)
		throws Exception;

	public void importLayoutSetPrototype(
			LayoutSetPrototype layoutSetPrototype, InputStream inputStream,
			ServiceContext serviceContext)
		throws PortalException;

	public boolean isContentSharingWithChildrenEnabled(Group group);

	public boolean isFirstLayout(
		long groupId, boolean privateLayout, long layoutId);

	public boolean isLayoutDeleteable(Layout layout);

	public boolean isLayoutModifiedSinceLastMerge(Layout layout)
		throws PortalException;

	public boolean isLayoutSetMergeable(Group group, LayoutSet layoutSet)
		throws PortalException;

	public boolean isLayoutSetPrototypeUpdateable(LayoutSet layoutSet);

	public boolean isLayoutSortable(Layout layout);

	public boolean isLayoutUpdateable(Layout layout);

	public boolean isUserGroupLayout(Layout layout) throws PortalException;

	public boolean isUserGroupLayoutSetViewable(
			PermissionChecker permissionChecker, Group userGroupGroup)
		throws PortalException;

	public void mergeLayoutPrototypeLayout(Group group, Layout layout)
		throws Exception;

	public void mergeLayoutSetPrototypeLayouts(Group group, LayoutSet layoutSet)
		throws Exception;

	public void removeMergeFailFriendlyURLLayouts(LayoutSet layoutSet)
		throws PortalException;

	public void resetPrototype(Layout layout) throws PortalException;

	public void resetPrototype(LayoutSet layoutSet) throws PortalException;

	public void setMergeFailCount(
			LayoutPrototype layoutPrototype, int newMergeFailCount)
		throws PortalException;

	public void setMergeFailCount(
			LayoutSetPrototype layoutSetPrototype, int newMergeFailCount)
		throws PortalException;

	public void updateLayoutScopes(
			long userId, Layout sourceLayout, Layout targetLayout,
			PortletPreferences sourcePreferences,
			PortletPreferences targetPreferences, String sourcePortletId,
			String languageId)
		throws Exception;

	public void updateLayoutSetPrototypesLinks(
			Group group, long publicLayoutSetPrototypeId,
			long privateLayoutSetPrototypeId,
			boolean publicLayoutSetPrototypeLinkEnabled,
			boolean privateLayoutSetPrototypeLinkEnabled)
		throws Exception;

}