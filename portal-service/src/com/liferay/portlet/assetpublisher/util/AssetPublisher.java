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
public interface AssetPublisher {

	public static final String SCOPE_ID_CHILD_GROUP_PREFIX = "ChildGroup_";

	public static final String SCOPE_ID_GROUP_PREFIX = "Group_";

	public static final String SCOPE_ID_LAYOUT_PREFIX = "Layout_";

	public static final String SCOPE_ID_LAYOUT_UUID_PREFIX = "LayoutUuid_";

	public static final String SCOPE_ID_PARENT_GROUP_PREFIX = "ParentGroup_";

	public void addAndStoreSelection(
			PortletRequest portletRequest, String className, long classPK,
			int assetEntryOrder)
		throws Exception;

	public void addRecentFolderId(
		PortletRequest portletRequest, String className, long classPK);

	public void addSelection(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences, String portletId)
		throws Exception;

	public void addSelection(
			ThemeDisplay themeDisplay, PortletPreferences portletPreferences,
			String portletId, long assetEntryId, int assetEntryOrder,
			String assetEntryType)
		throws Exception;

	public void addUserAttributes(
			User user, String[] customUserAttributeNames,
			AssetEntryQuery assetEntryQuery)
		throws Exception;

	public void checkAssetEntries() throws Exception;

	public long[] getAssetCategoryIds(PortletPreferences portletPreferences)
		throws Exception;

	public List<AssetEntry> getAssetEntries(
			PortletPreferences portletPreferences, Layout layout,
			long scopeGroupId, int max, boolean checkPermission)
		throws PortalException, SystemException;

	public List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences,
			PermissionChecker permissionChecker, long[] groupIds,
			boolean deleteMissingAssetEntries, boolean checkPermission)
		throws Exception;

	public List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences,
			PermissionChecker permissionChecker, long[] groupIds,
			long[] allCategoryIds, String[] allTagNames,
			boolean deleteMissingAssetEntries, boolean checkPermission)
		throws Exception;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             AssetPublisher#getAssetEntries( PortletRequest,
	 *             PortletPreferences, PermissionChecker, long[], long[],
	 *             String[], boolean , boolean)}
	 */
	@Deprecated
	public List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences,
			PermissionChecker permissionChecker, long[] groupIds,
			long[] assetCategoryIds, String[] assetEntryXmls,
			String[] assetTagNames, boolean deleteMissingAssetEntries,
			boolean checkPermission)
		throws Exception;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             AssetPublisher#getAssetEntries( PortletRequest,
	 *             PortletPreferences, PermissionChecker, long[], boolean,
	 *             boolean)}
	 */
	@Deprecated
	public List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences,
			PermissionChecker permissionChecker, long[] groupIds,
			String[] assetEntryXmls, boolean deleteMissingAssetEntries,
			boolean checkPermission)
		throws Exception;

	public AssetEntryQuery getAssetEntryQuery(
			PortletPreferences portletPreferences, long[] scopeGroupIds)
		throws PortalException, SystemException;

	public String[] getAssetTagNames(
			PortletPreferences portletPreferences, long scopeGroupId)
		throws Exception;

	public String getClassName(AssetRendererFactory assetRendererFactory);

	public long[] getClassNameIds(
		PortletPreferences portletPreferences, long[] availableClassNameIds);

	public Long[] getClassTypeIds(
		PortletPreferences portletPreferences, String className,
		Long[] availableClassTypeIds);

	public Map<Locale, String> getEmailAssetEntryAddedBodyMap(
		PortletPreferences portletPreferences);

	public boolean getEmailAssetEntryAddedEnabled(
		PortletPreferences portletPreferences);

	public Map<Locale, String> getEmailAssetEntryAddedSubjectMap(
		PortletPreferences portletPreferences);

	public String getEmailFromAddress(
			PortletPreferences portletPreferences, long companyId)
		throws SystemException;

	public String getEmailFromName(
			PortletPreferences portletPreferences, long companyId)
		throws SystemException;

	public long getGroupIdFromScopeId(
			String scopeId, long siteGroupId, boolean privateLayout)
		throws PortalException, SystemException;

	public long[] getGroupIds(
		PortletPreferences portletPreferences, long scopeGroupId,
		Layout layout);

	public long getRecentFolderId(
		PortletRequest portletRequest, String className);

	public String getScopeId(Group group, long scopeGroupId)
		throws PortalException, SystemException;

	public boolean isScopeIdSelectable(
			PermissionChecker permissionChecker, String scopeId,
			long companyGroupId, Layout layout)
		throws PortalException, SystemException;

	public boolean isSubscribed(
			long companyId, long userId, long plid, String portletId)
		throws PortalException, SystemException;

	public void notifySubscribers(
			PortletPreferences portletPreferences, long plid, String portletId,
			List<AssetEntry> assetEntries)
		throws PortalException, SystemException;

	public void processAssetEntryQuery(
			User user, PortletPreferences portletPreferences,
			AssetEntryQuery assetEntryQuery)
		throws Exception;

	public void registerAssetQueryProcessor(
		String name, AssetEntryQueryProcessor assetQueryProcessor);

	public void removeAndStoreSelection(
			List<String> assetEntryUuids, PortletPreferences portletPreferences)
		throws Exception;

	public void removeRecentFolderId(
		PortletRequest portletRequest, String className, long classPK);

	public void subscribe(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId)
		throws PortalException, SystemException;

	public void unregisterAssetQueryProcessor(
		String assetQueryProcessorClassName);

	public void unsubscribe(
			PermissionChecker permissionChecker, long plid, String portletId)
		throws PortalException, SystemException;

}