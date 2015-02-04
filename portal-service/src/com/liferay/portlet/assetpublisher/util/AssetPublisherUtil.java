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

package com.liferay.portlet.assetpublisher.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.ClassType;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.asset.util.AssetEntryQueryProcessor;

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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil#getEntries(
	 *             long[], long[], String, String, String, String, boolean,
	 *             boolean, int, int, String, String, String, String)}
	 */
	@Deprecated
	public static List<AssetEntry> getAssetEntries(
		long[] groupIds, long[] classNameIds, String keywords, String userName,
		String title, String description, boolean advancedSearch,
		boolean andOperator, int start, int end, String orderByCol1,
		String orderByCol2, String orderByType1, String orderByType2) {

		return getAssetPublisher().getAssetEntries(
			groupIds, classNameIds, keywords, userName, title, description,
			advancedSearch, andOperator, start, end, orderByCol1, orderByCol2,
			orderByType1, orderByType2);
	}

	public static List<AssetEntry> getAssetEntries(
			PortletPreferences portletPreferences, Layout layout,
			long scopeGroupId, int max, boolean checkPermission)
		throws PortalException {

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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil#getEntriesCount(
	 *             long[], long[], String, String, String, String, boolean,
	 *             boolean, int, int)}
	 */
	@Deprecated
	public static int getAssetEntriesCount(
		long[] groupIds, long[] classNameIds, String keywords, String userName,
		String title, String description, boolean advancedSearch,
		boolean andOperator, int start, int end) {

		return getAssetPublisher().getAssetEntriesCount(
			groupIds, classNameIds, keywords, userName, title, description,
			advancedSearch, andOperator, start, end);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             AssetPublisherUtil#getAssetEntryQuery(PortletPreferences,
	 *             long[], long[], String[])}
	 */
	@Deprecated
	public static AssetEntryQuery getAssetEntryQuery(
			PortletPreferences portletPreferences, long[] siteGroupIds)
		throws PortalException {

		return getAssetPublisher().getAssetEntryQuery(
			portletPreferences, siteGroupIds);
	}

	public static AssetEntryQuery getAssetEntryQuery(
			PortletPreferences portletPreferences, long[] scopeGroupIds,
			long[] overrideAllAssetCategoryIds,
			String[] overrideAllAssetTagNames)
		throws PortalException {

		return getAssetPublisher().getAssetEntryQuery(
			portletPreferences, scopeGroupIds, overrideAllAssetCategoryIds,
			overrideAllAssetTagNames);
	}

	public static AssetPublisher getAssetPublisher() {
		PortalRuntimePermission.checkGetBeanProperty(AssetPublisherUtil.class);

		return _assetPublisher;
	}

	public static String[] getAssetTagNames(
			PortletPreferences portletPreferences)
		throws Exception {

		return getAssetPublisher().getAssetTagNames(portletPreferences);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             AssetPublisherUtil#getAssetTagNames(PortletPreferences)}
	 */
	@Deprecated
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
		List<ClassType> availableClassTypes) {

		return getAssetPublisher().getClassTypeIds(
			portletPreferences, className, availableClassTypes);
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

	public static Map<String, String> getEmailDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName) {

		return getAssetPublisher().getEmailDefinitionTerms(
			portletRequest, emailFromAddress, emailFromName);
	}

	public static String getEmailFromAddress(
		PortletPreferences portletPreferences, long companyId) {

		return getAssetPublisher().getEmailFromAddress(
			portletPreferences, companyId);
	}

	public static String getEmailFromName(
		PortletPreferences portletPreferences, long companyId) {

		return getAssetPublisher().getEmailFromName(
			portletPreferences, companyId);
	}

	public static long getGroupIdFromScopeId(
			String scopeId, long siteGroupId, boolean privateLayout)
		throws PortalException {

		return getAssetPublisher().getGroupIdFromScopeId(
			scopeId, siteGroupId, privateLayout);
	}

	public static long[] getGroupIds(
		PortletPreferences portletPreferences, long scopeGroupId,
		Layout layout) {

		return getAssetPublisher().getGroupIds(
			portletPreferences, scopeGroupId, layout);
	}

	public static String getScopeId(Group group, long scopeGroupId)
		throws PortalException {

		return getAssetPublisher().getScopeId(group, scopeGroupId);
	}

	public static long getSubscriptionClassPK(long plid, String portletId)
		throws PortalException {

		return getAssetPublisher().getSubscriptionClassPK(plid, portletId);
	}

	public static boolean isScopeIdSelectable(
			PermissionChecker permissionChecker, String scopeId,
			long companyGroupId, Layout layout)
		throws PortalException {

		return getAssetPublisher().isScopeIdSelectable(
			permissionChecker, scopeId, companyGroupId, layout);
	}

	public static boolean isSubscribed(
			long companyId, long userId, long plid, String portletId)
		throws PortalException {

		return getAssetPublisher().isSubscribed(
			companyId, userId, plid, portletId);
	}

	public static void notifySubscribers(
			PortletPreferences portletPreferences, long plid, String portletId,
			List<AssetEntry> assetEntries)
		throws PortalException {

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

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
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

	public static void subscribe(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId)
		throws PortalException {

		getAssetPublisher().subscribe(
			permissionChecker, groupId, plid, portletId);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static void unregisterAssetQueryProcessor(
		String assetQueryProcessorClassName) {

		getAssetPublisher().unregisterAssetQueryProcessor(
			assetQueryProcessorClassName);
	}

	public static void unsubscribe(
			PermissionChecker permissionChecker, long plid, String portletId)
		throws PortalException {

		getAssetPublisher().unsubscribe(permissionChecker, plid, portletId);
	}

	public void setAssetPublisher(AssetPublisher assetPublisher) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_assetPublisher = assetPublisher;
	}

	private static AssetPublisher _assetPublisher;

}