/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sites.kernel.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
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

	public static final String LAST_MERGE_LAYOUT_MODIFIED_TIME =
		"last-merge-layout-modified-time";

	public static final String LAST_MERGE_TIME = "last-merge-time";

	public static final String LAST_MERGE_VERSION = "last-merge-version";

	public static final String LAST_RESET_TIME = "last-reset-time";

	public static final String LAYOUT_UPDATEABLE = "layoutUpdateable";

	public static final String MERGE_FAIL_COUNT = "merge-fail-count";

	public static final String MERGE_FAIL_FRIENDLY_URL_LAYOUTS =
		"merge-fail-friendly-url-layouts";

	public static final String SHOW_SITE_NAME = "showSiteName";

	public void addMergeFailFriendlyURLLayout(Layout layout)
		throws PortalException;

	public void applyLayoutPrototype(
			LayoutPrototype layoutPrototype, Layout targetLayout,
			boolean linkEnabled)
		throws Exception;

	public default void copyExpandoBridgeAttributes(
		Layout sourceLayout, Layout targetLayout) {

		ExpandoBridge sourceExpandoBridge =
			ExpandoBridgeFactoryUtil.getExpandoBridge(
				sourceLayout.getCompanyId(), Layout.class.getName(),
				sourceLayout.getPlid());

		ExpandoBridge targetExpandoBridge =
			ExpandoBridgeFactoryUtil.getExpandoBridge(
				targetLayout.getCompanyId(), Layout.class.getName(),
				targetLayout.getPlid());

		Map<String, Serializable> attributes =
			sourceExpandoBridge.getAttributes();

		for (Map.Entry<String, Serializable> entry : attributes.entrySet()) {
			targetExpandoBridge.setAttribute(entry.getKey(), entry.getValue());
		}
	}

	public void copyLayout(
			long userId, Layout sourceLayout, Layout targetLayout,
			ServiceContext serviceContext)
		throws Exception;

	public void copyPortletPermissions(Layout targetLayout, Layout sourceLayout)
		throws Exception;

	public void copyPortletSetups(Layout sourceLayout, Layout targetLayout)
		throws Exception;

	public void copyTypeSettings(Group sourceGroup, Group targetGroup)
		throws Exception;

	public Object[] deleteLayout(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

	public Object[] deleteLayout(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception;

	public void deleteLayout(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception;

	public Long[] filterGroups(List<Group> groups, String[] names);

	public Map<String, String[]> getLayoutSetPrototypeParameters(
		ServiceContext serviceContext);

	public boolean isLayoutModifiedSinceLastMerge(Layout layout)
		throws PortalException;

	public boolean isLayoutSetMergeable(Group group, LayoutSet layoutSet)
		throws PortalException;

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