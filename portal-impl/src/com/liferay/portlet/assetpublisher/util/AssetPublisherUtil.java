/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrimitiveLongList;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.IOException;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Raymond Augé
 */
public class AssetPublisherUtil {

	public static final String SCOPE_ID_GROUP_PREFIX = "Group_";

	public static final String SCOPE_ID_LAYOUT_PREFIX = "Layout_";

	public static void addAndStoreSelection(
			PortletRequest portletRequest, String className, long classPK,
			int assetEntryOrder)
		throws Exception {

		String referringPortletResource = ParamUtil.getString(
			portletRequest, "referringPortletResource");

		if (Validator.isNull(referringPortletResource)) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = LayoutLocalServiceUtil.getLayout(
			themeDisplay.getRefererPlid());

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				themeDisplay.getScopeGroupId(), layout,
				referringPortletResource, null);

		String selectionStyle = portletPreferences.getValue(
			"selectionStyle", "dynamic");

		if (selectionStyle.equals("dynamic")) {
			return;
		}

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			className, classPK);

		addSelection(
			className, assetEntry.getEntryId(), assetEntryOrder,
			portletPreferences);

		portletPreferences.store();
	}

	public static void addRecentFolderId(
		PortletRequest portletRequest, String className, long classPK) {

		_getRecentFolderIds(portletRequest).put(className, classPK);
	}

	public static void addSelection(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences)
		throws Exception {

		String assetEntryType = ParamUtil.getString(
			portletRequest, "assetEntryType");
		long assetEntryId = ParamUtil.getLong(portletRequest, "assetEntryId");
		int assetEntryOrder = ParamUtil.getInteger(
			portletRequest, "assetEntryOrder");

		addSelection(
			assetEntryType, assetEntryId, assetEntryOrder, portletPreferences);
	}

	public static void addSelection(
			String assetEntryType, long assetEntryId, int assetEntryOrder,
			PortletPreferences portletPreferences)
		throws Exception {

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			assetEntryId);

		String[] assetEntryXmls = portletPreferences.getValues(
			"assetEntryXml", new String[0]);

		String assetEntryXml = _getAssetEntryXml(
			assetEntryType, assetEntry.getClassUuid());

		if (!ArrayUtil.contains(assetEntryXmls, assetEntryXml)) {
			if (assetEntryOrder > -1) {
				assetEntryXmls[assetEntryOrder] = assetEntryXml;
			}
			else {
				assetEntryXmls = ArrayUtil.append(
					assetEntryXmls, assetEntryXml);
			}

			portletPreferences.setValues("assetEntryXml", assetEntryXmls);
		}
	}

	public static void addUserAttributes(
			User user, String[] customUserAttributeNames,
			AssetEntryQuery assetEntryQuery)
		throws Exception {

		if ((user == null) || (customUserAttributeNames.length == 0)) {
			return;
		}

		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			user.getCompanyId());

		long[] allCategoryIds = assetEntryQuery.getAllCategoryIds();

		PrimitiveLongList allCategoryIdsList = new PrimitiveLongList(
			allCategoryIds.length + customUserAttributeNames.length);

		allCategoryIdsList.addAll(allCategoryIds);

		for (String customUserAttributeName : customUserAttributeNames) {
			ExpandoBridge userCustomAttributes = user.getExpandoBridge();

			Serializable userCustomFieldValue =
				userCustomAttributes.getAttribute(customUserAttributeName);

			if (userCustomFieldValue == null) {
				continue;
			}

			String userCustomFieldValueString = userCustomFieldValue.toString();

			List<AssetCategory> assetCategories =
				AssetCategoryLocalServiceUtil.search(
					companyGroup.getGroupId(), userCustomFieldValueString,
					new String[0], QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (AssetCategory assetCategory : assetCategories) {
				allCategoryIdsList.add(assetCategory.getCategoryId());
			}
		}

		assetEntryQuery.setAllCategoryIds(allCategoryIdsList.getArray());
	}

	public static AssetEntryQuery getAssetEntryQuery(
			PortletPreferences portletPreferences, long[] scopeGroupIds)
		throws Exception {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		long[] allAssetCategoryIds = new long[0];
		long[] anyAssetCategoryIds = new long[0];
		long[] notAllAssetCategoryIds = new long[0];
		long[] notAnyAssetCategoryIds = new long[0];

		String[] allAssetTagNames = new String[0];
		String[] anyAssetTagNames = new String[0];
		String[] notAllAssetTagNames = new String[0];
		String[] notAnyAssetTagNames = new String[0];

		for (int i = 0; true; i++) {
			String[] queryValues = portletPreferences.getValues(
				"queryValues" + i, null);

			if ((queryValues == null) || (queryValues.length == 0)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryAndOperator" + i, StringPool.BLANK));
			String queryName = portletPreferences.getValue(
				"queryName" + i, StringPool.BLANK);

			if (Validator.equals(queryName, "assetCategories")) {
				long[] assetCategoryIds = GetterUtil.getLongValues(queryValues);

				if (queryContains &&
					(queryAndOperator || (assetCategoryIds.length == 1))) {

					allAssetCategoryIds = assetCategoryIds;
				}
				else if (queryContains && !queryAndOperator) {
					anyAssetCategoryIds = assetCategoryIds;
				}
				else if (!queryContains && queryAndOperator) {
					notAllAssetCategoryIds = assetCategoryIds;
				}
				else {
					notAnyAssetCategoryIds = assetCategoryIds;
				}
			}
			else {
				if (queryContains && queryAndOperator) {
					allAssetTagNames = queryValues;
				}
				else if (queryContains && !queryAndOperator) {
					anyAssetTagNames = queryValues;
				}
				else if (!queryContains && queryAndOperator) {
					notAllAssetTagNames = queryValues;
				}
				else {
					notAnyAssetTagNames = queryValues;
				}
			}
		}

		assetEntryQuery.setAllCategoryIds(allAssetCategoryIds);

		for (String assetTagName : allAssetTagNames) {
			long[] allAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
				scopeGroupIds, assetTagName);

			assetEntryQuery.addAllTagIdsArray(allAssetTagIds);
		}

		assetEntryQuery.setAnyCategoryIds(anyAssetCategoryIds);

		long[] anyAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
			scopeGroupIds, anyAssetTagNames);

		assetEntryQuery.setAnyTagIds(anyAssetTagIds);

		assetEntryQuery.setNotAllCategoryIds(notAllAssetCategoryIds);

		for (String assetTagName : notAllAssetTagNames) {
			long[] notAllAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
				scopeGroupIds, assetTagName);

			assetEntryQuery.addNotAllTagIdsArray(notAllAssetTagIds);
		}

		assetEntryQuery.setNotAnyCategoryIds(notAnyAssetCategoryIds);

		long[] notAnyAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
			scopeGroupIds, notAnyAssetTagNames);

		assetEntryQuery.setNotAnyTagIds(notAnyAssetTagIds);

		return assetEntryQuery;
	}

	public static String[] getAssetTagNames(
			PortletPreferences portletPreferences, long scopeGroupId)
		throws Exception {

		String[] allAssetTagNames = new String[0];

		for (int i = 0; true; i++) {
			String[] queryValues = portletPreferences.getValues(
				"queryValues" + i, null);

			if ((queryValues == null) || (queryValues.length == 0)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryAndOperator" + i, StringPool.BLANK));
			String queryName = portletPreferences.getValue(
				"queryName" + i, StringPool.BLANK);

			if (!Validator.equals(queryName, "assetCategories") &&
				queryContains &&
				(queryAndOperator || (queryValues.length == 1))) {

				allAssetTagNames = queryValues;
			}
		}

		return allAssetTagNames;
	}

	public static String getClassName(
		AssetRendererFactory assetRendererFactory) {

		Class<?> clazz = assetRendererFactory.getClass();

		String className = clazz.getName();

		int pos = className.lastIndexOf(StringPool.PERIOD);

		return className.substring(pos + 1);
	}

	public static long[] getClassNameIds(
		PortletPreferences portletPreferences, long[] availableClassNameIds) {

		boolean anyAssetType = GetterUtil.getBoolean(
			portletPreferences.getValue(
				"anyAssetType", Boolean.TRUE.toString()));

		if (anyAssetType) {
			return availableClassNameIds;
		}

		long defaultClassNameId = GetterUtil.getLong(
			portletPreferences.getValue("anyAssetType", null));

		if (defaultClassNameId > 0) {
			return new long[] {defaultClassNameId};
		}

		long[] classNameIds = GetterUtil.getLongValues(
			portletPreferences.getValues("classNameIds", null));

		if (classNameIds != null) {
			return classNameIds;
		}
		else {
			return availableClassNameIds;
		}
	}

	public static Long[] getClassTypeIds(
		PortletPreferences portletPreferences, String className,
		Long[] availableClassTypeIds) {

		boolean anyAssetType = GetterUtil.getBoolean(
			portletPreferences.getValue(
				"anyClassType" + className, Boolean.TRUE.toString()));

		if (anyAssetType) {
			return availableClassTypeIds;
		}

		long defaultClassTypeId = GetterUtil.getLong(
			portletPreferences.getValue("anyClassType" + className, null));

		if (defaultClassTypeId > 0) {
			return new Long[] {defaultClassTypeId};
		}

		Long[] classTypeIds = ArrayUtil.toArray(
			StringUtil.split(
				portletPreferences.getValue(
					"classTypeIds" + className, null), 0L));

		if (classTypeIds != null) {
			return classTypeIds;
		}
		else {
			return availableClassTypeIds;
		}
	}

	public static boolean getEmailAssetEntryAddedEnabled(
		PortletPreferences preferences) {

		String emailAssetEntryAddedEnabled = preferences.getValue(
			"emailAssetEntryAddedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailAssetEntryAddedEnabled)) {
			return GetterUtil.getBoolean(emailAssetEntryAddedEnabled);
		}
		else {
			return PropsValues.ASSET_PUBLISHER_EMAIL_ASSET_ENTRY_ADDED_ENABLED;
		}
	}

	public static String getEmailFromAddress(
			PortletPreferences preferences, long companyId)
		throws SystemException {

		return PortalUtil.getEmailFromAddress(
			preferences, companyId,
			PropsValues.ASSET_PUBLISHER_EMAIL_FROM_ADDRESS);
	}

	public static String getEmailFromName(
			PortletPreferences preferences, long companyId)
		throws SystemException {

		return PortalUtil.getEmailFromName(
			preferences, companyId,
			PropsValues.ASSET_PUBLISHER_EMAIL_FROM_NAME);
	}

	public static long[] getGroupIds(
		PortletPreferences portletPreferences, long scopeGroupId,
		Layout layout) {

		String defaultScopeId = GetterUtil.getString(
			portletPreferences.getValue("defaultScope", null));

		if (Validator.isNull(defaultScopeId) ||
			defaultScopeId.equals(StringPool.FALSE)) {

			String[] scopeIds = portletPreferences.getValues(
				"scopeIds",
				new String[] {SCOPE_ID_GROUP_PREFIX + scopeGroupId});

			long[] groupIds = new long[scopeIds.length];

			for (int i = 0; i < scopeIds.length; i++) {
				try {
					groupIds[i] = _getGroupId(
						scopeIds[i], scopeGroupId, layout.isPrivateLayout());
				}
				catch (Exception e) {
					continue;
				}
			}

			return groupIds;
		}

		if (defaultScopeId.equals(StringPool.TRUE)) {
			return new long[] {scopeGroupId};
		}

		try {
			long groupId = _getGroupId(
				defaultScopeId, scopeGroupId, layout.isPrivateLayout());

			return new long[] {groupId};
		}
		catch (Exception e) {
			return new long[0];
		}
	}

	public static long getRecentFolderId(
		PortletRequest portletRequest, String className) {

		Long classPK = _getRecentFolderIds(portletRequest).get(className);

		if (classPK == null) {
			return 0;
		}
		else {
			return classPK.longValue();
		}
	}

	public static String getScopeId(Group group, long scopeGroupId)
		throws Exception {

		String key = null;

		if (group.isLayout()) {
			Layout layout = LayoutLocalServiceUtil.getLayout(
				group.getClassPK());

			key =
				AssetPublisherUtil.SCOPE_ID_LAYOUT_PREFIX +
					layout.getLayoutId();
		}
		else if (group.isLayoutPrototype() ||
				(group.getGroupId() == scopeGroupId)) {

			key =
				AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX +
					GroupConstants.DEFAULT;
		}
		else {
			key = AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX + group.getGroupId();
		}

		return key;
	}

	public static boolean isSubscribed(
			long companyId, long userId, long plid, String portletId)
		throws Exception {

		com.liferay.portal.model.PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId);

		return SubscriptionLocalServiceUtil.isSubscribed(
			companyId, userId,
			com.liferay.portal.model.PortletPreferences.class.getName(),
			portletPreferences.getPortletPreferencesId());
	}

	public static void removeAndStoreSelection(
			List<String> assetEntryUuids, PortletPreferences portletPreferences)
		throws Exception {

		if (assetEntryUuids.size() == 0) {
			return;
		}

		String[] assetEntryXmls = portletPreferences.getValues(
			"assetEntryXml", new String[0]);

		List<String> assetEntryXmlsList = ListUtil.fromArray(assetEntryXmls);

		Iterator<String> itr = assetEntryXmlsList.iterator();

		while (itr.hasNext()) {
			String assetEntryXml = itr.next();

			Document document = SAXReaderUtil.read(assetEntryXml);

			Element rootElement = document.getRootElement();

			String assetEntryUuid = rootElement.elementText("asset-entry-uuid");

			if (assetEntryUuids.contains(assetEntryUuid)) {
				itr.remove();
			}
		}

		portletPreferences.setValues(
			"assetEntryXml",
			assetEntryXmlsList.toArray(new String[assetEntryXmlsList.size()]));

		portletPreferences.store();
	}

	public static void removeRecentFolderId(
		PortletRequest portletRequest, String className, long classPK) {

		if (getRecentFolderId(portletRequest, className) == classPK) {
			_getRecentFolderIds(portletRequest).remove(className);
		}
	}

	public static void subscribe(
			long userId, long groupId, long portletPreferencesId,
			String portletId, PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			permissionChecker, portletId, ActionKeys.SUBSCRIBE);

		SubscriptionLocalServiceUtil.addSubscription(
			userId, groupId,
			com.liferay.portal.model.PortletPreferences.class.getName(),
			portletPreferencesId);
	}

	public static void unsubscribe(
			long userId, long portletPreferencesId, String portletId,
			PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			permissionChecker, portletId, ActionKeys.SUBSCRIBE);

		SubscriptionLocalServiceUtil.deleteSubscription(
			userId, com.liferay.portal.model.PortletPreferences.class.getName(),
			portletPreferencesId);
	}

	private static String _getAssetEntryXml(
		String assetEntryType, String assetEntryUuid) {

		String xml = null;

		try {
			Document document = SAXReaderUtil.createDocument(StringPool.UTF8);

			Element assetEntryElement = document.addElement("asset-entry");

			Element assetEntryTypeElement = assetEntryElement.addElement(
				"asset-entry-type");

			assetEntryTypeElement.addText(assetEntryType);

			Element assetEntryUuidElement = assetEntryElement.addElement(
				"asset-entry-uuid");

			assetEntryUuidElement.addText(assetEntryUuid);

			xml = document.formattedString(StringPool.BLANK);
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe);
			}
		}

		return xml;
	}

	private static long _getGroupId(
			String scopeId, long scopeGroupId, boolean privateLayout)
		throws Exception {

		if (scopeId.startsWith(SCOPE_ID_GROUP_PREFIX)) {
			String scopeIdSuffix = scopeId.substring(
				SCOPE_ID_GROUP_PREFIX.length());

			if (scopeIdSuffix.equals(GroupConstants.DEFAULT)) {
				return scopeGroupId;
			}

			return GetterUtil.getLong(scopeIdSuffix);
		}
		else if (scopeId.startsWith(SCOPE_ID_LAYOUT_PREFIX)) {
			String scopeIdSuffix = scopeId.substring(
				SCOPE_ID_LAYOUT_PREFIX.length());

			long scopeIdLayoutId = GetterUtil.getLong(scopeIdSuffix);

			Layout scopeIdLayout = LayoutLocalServiceUtil.getLayout(
				scopeGroupId, privateLayout, scopeIdLayoutId);

			Group scopeIdGroup = scopeIdLayout.getScopeGroup();

			return scopeIdGroup.getGroupId();
		}
		else {
			throw new IllegalArgumentException("Invalid scope ID " + scopeId);
		}
	}

	private static Map<String, Long> _getRecentFolderIds(
		PortletRequest portletRequest) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);
		HttpSession session = request.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String key =
			AssetPublisherUtil.class + StringPool.UNDERLINE +
				themeDisplay.getScopeGroupId();

		Map<String, Long> recentFolderIds =
			(Map<String, Long>)session.getAttribute(key);

		if (recentFolderIds == null) {
			recentFolderIds = new HashMap<String, Long>();
		}

		session.setAttribute(key, recentFolderIds);

		return recentFolderIds;
	}

	private static Log _log = LogFactoryUtil.getLog(AssetPublisherUtil.class);

}