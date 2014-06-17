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

package com.liferay.portlet.sites.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 * @author Ryan Park
 * @author Zsolt Berentey
 */
public class SitesUtil {

	public static void addMergeFailFriendlyURLLayout(Layout layout)
		throws PortalException {

		getSites().addMergeFailFriendlyURLLayout(layout);
	}

	public static void addPortletBreadcrumbEntries(
			Group group, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		getSites().addPortletBreadcrumbEntries(group, request, renderResponse);
	}

	public static void addPortletBreadcrumbEntries(
			Group group, String pagesName, PortletURL redirectURL,
			HttpServletRequest request, RenderResponse renderResponse)
		throws Exception {

		getSites().addPortletBreadcrumbEntries(
			group, pagesName, redirectURL, request, renderResponse);
	}

	public static void applyLayoutPrototype(
			LayoutPrototype layoutPrototype, Layout targetLayout,
			boolean linkEnabled)
		throws Exception {

		getSites().applyLayoutPrototype(
			layoutPrototype, targetLayout, linkEnabled);
	}

	public static void copyLayout(
			long userId, Layout sourceLayout, Layout targetLayout,
			ServiceContext serviceContext)
		throws Exception {

		getSites().copyLayout(
			userId, sourceLayout, targetLayout, serviceContext);
	}

	public static void copyLookAndFeel(Layout targetLayout, Layout sourceLayout)
		throws Exception {

		getSites().copyLookAndFeel(targetLayout, sourceLayout);
	}

	public static void copyPortletPermissions(
			Layout targetLayout, Layout sourceLayout)
		throws Exception {

		getSites().copyPortletPermissions(targetLayout, sourceLayout);
	}

	public static void copyPortletSetups(
			Layout sourceLayout, Layout targetLayout)
		throws Exception {

		getSites().copyPortletSetups(sourceLayout, targetLayout);
	}

	public static void copyTypeSettings(Group sourceGroup, Group targetGroup)
		throws Exception {

		getSites().copyTypeSettings(sourceGroup, targetGroup);
	}

	public static Object[] deleteLayout(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		return getSites().deleteLayout(actionRequest, actionResponse);
	}

	public static Object[] deleteLayout(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		return getSites().deleteLayout(request, response);
	}

	public static void deleteLayout(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		getSites().deleteLayout(renderRequest, renderResponse);
	}

	public static File exportLayoutSetPrototype(
			LayoutSetPrototype layoutSetPrototype,
			ServiceContext serviceContext)
		throws PortalException {

		return getSites().exportLayoutSetPrototype(
			layoutSetPrototype, serviceContext);
	}

	public static Long[] filterGroups(List<Group> groups, String[] names) {
		return getSites().filterGroups(groups, names);
	}

	public static Layout getLayoutSetPrototypeLayout(Layout layout) {
		return getSites().getLayoutSetPrototypeLayout(layout);
	}

	public static Map<String, String[]> getLayoutSetPrototypeParameters(
		ServiceContext serviceContext) {

		return getSites().getLayoutSetPrototypeParameters(serviceContext);
	}

	public static int getMergeFailCount(LayoutPrototype layoutPrototype)
		throws PortalException {

		return getSites().getMergeFailCount(layoutPrototype);
	}

	public static int getMergeFailCount(LayoutSetPrototype layoutSetPrototype)
		throws PortalException {

		return getSites().getMergeFailCount(layoutSetPrototype);
	}

	public static List<Layout> getMergeFailFriendlyURLLayouts(
			LayoutSet layoutSet)
		throws PortalException {

		return getSites().getMergeFailFriendlyURLLayouts(layoutSet);
	}

	public static List<String> getOrganizationNames(Group group, User user)
		throws Exception {

		return getSites().getOrganizationNames(group, user);
	}

	public static Sites getSites() {
		PortalRuntimePermission.checkGetBeanProperty(SitesUtil.class);

		return _sites;
	}

	public static List<String> getUserGroupNames(Group group, User user)
		throws Exception {

		return getSites().getUserGroupNames(group, user);
	}

	public static void importLayoutSetPrototype(
			LayoutSetPrototype layoutSetPrototype, InputStream inputStream,
			ServiceContext serviceContext)
		throws PortalException {

		getSites().importLayoutSetPrototype(
			layoutSetPrototype, inputStream, serviceContext);
	}

	public static boolean isContentSharingWithChildrenEnabled(Group group) {
		return getSites().isContentSharingWithChildrenEnabled(group);
	}

	public static boolean isFirstLayout(
		long groupId, boolean privateLayout, long layoutId) {

		return getSites().isFirstLayout(groupId, privateLayout, layoutId);
	}

	public static boolean isLayoutDeleteable(Layout layout) {
		return getSites().isLayoutDeleteable(layout);
	}

	public static boolean isLayoutModifiedSinceLastMerge(Layout layout)
		throws PortalException {

		return getSites().isLayoutModifiedSinceLastMerge(layout);
	}

	public static boolean isLayoutSetMergeable(Group group, LayoutSet layoutSet)
		throws PortalException {

		return getSites().isLayoutSetMergeable(group, layoutSet);
	}

	public static boolean isLayoutSetPrototypeUpdateable(LayoutSet layoutSet) {
		return getSites().isLayoutSetPrototypeUpdateable(layoutSet);
	}

	public static boolean isLayoutSortable(Layout layout) {
		return getSites().isLayoutSortable(layout);
	}

	public static boolean isLayoutUpdateable(Layout layout) {
		return getSites().isLayoutUpdateable(layout);
	}

	public static boolean isUserGroupLayoutSetViewable(
			PermissionChecker permissionChecker, Group userGroupGroup)
		throws PortalException {

		return getSites().isUserGroupLayoutSetViewable(
			permissionChecker, userGroupGroup);
	}

	public static void mergeLayoutPrototypeLayout(Group group, Layout layout)
		throws Exception {

		getSites().mergeLayoutPrototypeLayout(group, layout);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #mergeLayoutPrototypeLayout(Group, Layout)}
	 */
	@Deprecated
	public static void mergeLayoutProtypeLayout(Group group, Layout layout)
		throws Exception {

		getSites().mergeLayoutProtypeLayout(group, layout);
	}

	public static void mergeLayoutSetPrototypeLayouts(
			Group group, LayoutSet layoutSet)
		throws Exception {

		getSites().mergeLayoutSetPrototypeLayouts(group, layoutSet);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #mergeLayoutSetPrototypeLayouts(Group, LayoutSet)}
	 */
	@Deprecated
	public static void mergeLayoutSetProtypeLayouts(
			Group group, LayoutSet layoutSet)
		throws Exception {

		getSites().mergeLayoutSetProtypeLayouts(group, layoutSet);
	}

	public static void removeMergeFailFriendlyURLLayouts(LayoutSet layoutSet) {
		getSites().removeMergeFailFriendlyURLLayouts(layoutSet);
	}

	public static void resetPrototype(Layout layout) throws PortalException {
		getSites().resetPrototype(layout);
	}

	public static void resetPrototype(LayoutSet layoutSet)
		throws PortalException {

		getSites().resetPrototype(layoutSet);
	}

	public static void setMergeFailCount(
			LayoutPrototype layoutPrototype, int newMergeFailCount)
		throws PortalException {

		getSites().setMergeFailCount(layoutPrototype, newMergeFailCount);
	}

	public static void setMergeFailCount(
			LayoutSetPrototype layoutSetPrototype, int newMergeFailCount)
		throws PortalException {

		getSites().setMergeFailCount(layoutSetPrototype, newMergeFailCount);
	}

	public static void updateLayoutScopes(
			long userId, Layout sourceLayout, Layout targetLayout,
			PortletPreferences sourcePreferences,
			PortletPreferences targetPreferences, String sourcePortletId,
			String languageId)
		throws Exception {

		getSites().updateLayoutScopes(
			userId, sourceLayout, targetLayout, sourcePreferences,
			targetPreferences, sourcePortletId, languageId);
	}

	public static void updateLayoutSetPrototypesLinks(
			Group group, long publicLayoutSetPrototypeId,
			long privateLayoutSetPrototypeId,
			boolean publicLayoutSetPrototypeLinkEnabled,
			boolean privateLayoutSetPrototypeLinkEnabled)
		throws Exception {

		getSites().updateLayoutSetPrototypesLinks(
			group, publicLayoutSetPrototypeId, privateLayoutSetPrototypeId,
			publicLayoutSetPrototypeLinkEnabled,
			privateLayoutSetPrototypeLinkEnabled);
	}

	public void setSites(Sites sites) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_sites = sites;
	}

	private static Sites _sites;

}