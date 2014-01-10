/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.assetpublisher.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Eudaldo Alonso
 */
@ProviderType
public class AssetPublisherUtil {

	public static void addAndStoreSelection(
			PortletRequest portletRequest, String className, long classPK,
			int assetEntryOrder)
		throws Exception {

		getAssetPublisher().addAndStoreSelection(
			portletRequest, className, classPK, assetEntryOrder);
	}

	public static void addRecentFolderId(
		PortletRequest portletRequest, String className, long classPK) {

		getAssetPublisher().addRecentFolderId(
			portletRequest, className, classPK);
	}

	public static void addSelection(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences, String portletId)
		throws Exception {

		getAssetPublisher().addSelection(
			portletRequest, portletPreferences, portletId);
	}

	public static void addSelection(
			ThemeDisplay themeDisplay, PortletPreferences portletPreferences,
			String portletId, long assetEntryId, int assetEntryOrder,
			String assetEntryType)
		throws Exception {

		getAssetPublisher().addSelection(
			themeDisplay, portletPreferences, portletId, assetEntryId,
			assetEntryOrder, assetEntryType);
	}

	public static void addUserAttributes(
			User user, String[] customUserAttributeNames,
			AssetEntryQuery assetEntryQuery)
		throws Exception {

		getAssetPublisher().addUserAttributes(
			user, customUserAttributeNames, assetEntryQuery);
	}

	public static void checkAssetEntries() throws Exception {
		getAssetPublisher().checkAssetEntries();
	}

	public static long[] getAssetCategoryIds(
			PortletPreferences portletPreferences)
		throws Exception {

		return getAssetPublisher().getAssetCategoryIds(portletPreferences);
	}

	public static List<AssetEntry> getAssetEntries(
			PortletPreferences portletPreferences, Layout layout,
			long scopeGroupId, int max, boolean checkPermission)
		throws PortalException, SystemException {

		return getAssetPublisher().getAssetEntries(
			portletPreferences, layout, scopeGroupId, max, checkPermission);
	}

	public static List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences,
			PermissionChecker permissionChecker, long[] groupIds,
			boolean deleteMissingAssetEntries, boolean checkPermission)
		throws Exception {

		return getAssetPublisher().getAssetEntries(
			portletRequest, portletPreferences, permissionChecker, groupIds,
			deleteMissingAssetEntries, checkPermission);
	}

	public static List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences,
			PermissionChecker permissionChecker, long[] groupIds,
			long[] allCategoryIds, String[] allTagNames,
			boolean deleteMissingAssetEntries, boolean checkPermission)
		throws Exception {

		return getAssetPublisher().getAssetEntries(
			portletRequest, portletPreferences, permissionChecker, groupIds,
			allCategoryIds, allTagNames, deleteMissingAssetEntries,
			checkPermission);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             AssetPublisherUtil#getAssetEntries( PortletRequest,
	 *             PortletPreferences, PermissionChecker, long[], long[],
	 *             String[], boolean , boolean)}
	 */
	@Deprecated
	public static List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences,
			PermissionChecker permissionChecker, long[] groupIds,
			long[] assetCategoryIds, String[] assetEntryXmls,
			String[] assetTagNames, boolean deleteMissingAssetEntries,
			boolean checkPermission)
		throws Exception {

		return getAssetPublisher().getAssetEntries(
			portletRequest, portletPreferences, permissionChecker, groupIds,
			assetCategoryIds, assetEntryXmls, assetTagNames,
			deleteMissingAssetEntries, checkPermission);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             AssetPublisherUtil#getAssetEntries( PortletRequest,
	 *             PortletPreferences, PermissionChecker, long[], boolean,
	 *             boolean)}
	 */
	@Deprecated
	public static List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences,
			PermissionChecker permissionChecker, long[] groupIds,
			String[] assetEntryXmls, boolean deleteMissingAssetEntries,
			boolean checkPermission)
		throws Exception {

		return getAssetPublisher().getAssetEntries(
			portletRequest, portletPreferences, permissionChecker, groupIds,
			assetEntryXmls, deleteMissingAssetEntries, checkPermission);
	}

	public static AssetEntryQuery getAssetEntryQuery(
			PortletPreferences portletPreferences, long[] scopeGroupIds)
		throws PortalException, SystemException {

		return getAssetPublisher().getAssetEntryQuery(
			portletPreferences, scopeGroupIds);
	}

	public static AssetPublisher getAssetPublisher() {
		PortalRuntimePermission.checkGetBeanProperty(AssetPublisherUtil.class);

		return _assetPublisher;
	}

	public static String[] getAssetTagNames(
			PortletPreferences portletPreferences, long scopeGroupId)
		throws Exception {

		return getAssetPublisher().getAssetTagNames(
			portletPreferences, scopeGroupId);
	}

	public static String getClassName(
		AssetRendererFactory assetRendererFactory) {

		return getAssetPublisher().getClassName(assetRendererFactory);
	}

	public static long[] getClassNameIds(
		PortletPreferences portletPreferences, long[] availableClassNameIds) {

		return getAssetPublisher().getClassNameIds(
			portletPreferences, availableClassNameIds);
	}

	public static Long[] getClassTypeIds(
		PortletPreferences portletPreferences, String className,
		Long[] availableClassTypeIds) {

		return getAssetPublisher().getClassTypeIds(
			portletPreferences, className, availableClassTypeIds);
	}

	public static Map<Locale, String> getEmailAssetEntryAddedBodyMap(
		PortletPreferences portletPreferences) {

		return getAssetPublisher().getEmailAssetEntryAddedBodyMap(
			portletPreferences);
	}

	public static boolean getEmailAssetEntryAddedEnabled(
		PortletPreferences portletPreferences) {

		return getAssetPublisher().getEmailAssetEntryAddedEnabled(
			portletPreferences);
	}

	public static Map<Locale, String> getEmailAssetEntryAddedSubjectMap(
		PortletPreferences portletPreferences) {

		return getAssetPublisher().getEmailAssetEntryAddedSubjectMap(
			portletPreferences);
	}

	public static String getEmailFromAddress(
			PortletPreferences portletPreferences, long companyId)
		throws SystemException {

		return getAssetPublisher().getEmailFromAddress(
			portletPreferences, companyId);
	}

	public static String getEmailFromName(
			PortletPreferences portletPreferences, long companyId)
		throws SystemException {

		return getAssetPublisher().getEmailFromName(
			portletPreferences, companyId);
	}

	public static long getGroupIdFromScopeId(
			String scopeId, long siteGroupId, boolean privateLayout)
		throws PortalException, SystemException {

		return getAssetPublisher().getGroupIdFromScopeId(
			scopeId, siteGroupId, privateLayout);
	}

	public static long[] getGroupIds(
		PortletPreferences portletPreferences, long scopeGroupId,
		Layout layout) {

		return getAssetPublisher().getGroupIds(
			portletPreferences, scopeGroupId, layout);
	}

	public static long getRecentFolderId(
		PortletRequest portletRequest, String className) {

		return getAssetPublisher().getRecentFolderId(portletRequest, className);
	}

	public static String getScopeId(Group group, long scopeGroupId)
		throws PortalException, SystemException {

		return getAssetPublisher().getScopeId(group, scopeGroupId);
	}

	public static boolean isScopeIdSelectable(
			PermissionChecker permissionChecker, String scopeId,
			long companyGroupId, Layout layout)
		throws PortalException, SystemException {

		return getAssetPublisher().isScopeIdSelectable(
			permissionChecker, scopeId, companyGroupId, layout);
	}

	public static boolean isSubscribed(
			long companyId, long userId, long plid, String portletId)
		throws PortalException, SystemException {

		return getAssetPublisher().isSubscribed(
			companyId, userId, plid, portletId);
	}

	public static void notifySubscribers(
			PortletPreferences portletPreferences, long plid, String portletId,
			List<AssetEntry> assetEntries)
		throws PortalException, SystemException {

		getAssetPublisher().notifySubscribers(
			portletPreferences, plid, portletId, assetEntries);
	}

	public static void processAssetEntryQuery(
			User user, PortletPreferences portletPreferences,
			AssetEntryQuery assetEntryQuery)
		throws Exception {

		getAssetPublisher().processAssetEntryQuery(
			user, portletPreferences, assetEntryQuery);
	}

	public static void registerAssetQueryProcessor(
		String name, AssetEntryQueryProcessor assetQueryProcessor) {

		getAssetPublisher().registerAssetQueryProcessor(
				name, assetQueryProcessor);
	}

	public static void removeAndStoreSelection(
			List<String> assetEntryUuids, PortletPreferences portletPreferences)
		throws Exception {

		getAssetPublisher().removeAndStoreSelection(
			assetEntryUuids, portletPreferences);
	}

	public static void removeRecentFolderId(
		PortletRequest portletRequest, String className, long classPK) {

		getAssetPublisher().removeRecentFolderId(
			portletRequest, className, classPK);
	}

	public static void subscribe(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId)
		throws PortalException, SystemException {

		getAssetPublisher().subscribe(
			permissionChecker, groupId, plid, portletId);
	}

	public static void unregisterAssetQueryProcessor(
		String assetQueryProcessorClassName) {

		getAssetPublisher().unregisterAssetQueryProcessor(
			assetQueryProcessorClassName);
	}

	public static void unsubscribe(
			PermissionChecker permissionChecker, long plid, String portletId)
		throws PortalException, SystemException {

		getAssetPublisher().unsubscribe(permissionChecker, plid, portletId);
	}

	public void setAssetPublisher(AssetPublisher assetPublisher) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_assetPublisher = assetPublisher;
	}

	private static AssetPublisher _assetPublisher;

}