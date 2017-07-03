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

package com.liferay.asset.publisher.web.internal.messaging;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.publisher.web.configuration.AssetPublisherWebConfiguration;
import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.web.util.AssetPublisherUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.TimeZoneThreadLocal;
import com.liferay.portlet.asset.service.permission.AssetEntryPermission;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.configuration.kernel.util.PortletConfigurationUtil;
import com.liferay.subscription.model.Subscription;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	configurationPid = "com.liferay.asset.publisher.web.configuration.AssetPublisherWebConfiguration",
	immediate = true, service = AssetEntriesCheckerUtil.class
)
@ProviderType
public class AssetEntriesCheckerUtil {

	public void checkAssetEntries() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			_portletPreferencesLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property property = PropertyFactoryUtil.forName(
						"portletId");

					dynamicQuery.add(
						property.like(
							PortletIdCodec.encode(
								AssetPublisherPortletKeys.ASSET_PUBLISHER,
								StringPool.PERCENT)));
				}

			});
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<com.liferay.portal.kernel.model.PortletPreferences>() {

				@Override
				public void performAction(
						com.liferay.portal.kernel.model.PortletPreferences
							portletPreferences)
					throws PortalException {

					_checkAssetEntries(portletPreferences);
				}

			});

		actionableDynamicQuery.performActions();
	}

	@Reference(unbind = "-")
	protected void setAssetPublisherUtil(
		AssetPublisherUtil assetPublisherUtil) {

		_assetPublisherUtil = assetPublisherUtil;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletPreferencesLocalService(
		PortletPreferencesLocalService portletPreferencesLocalService) {

		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	@Reference(unbind = "-")
	protected void setSubscriptionLocalService(
		SubscriptionLocalService subscriptionLocalService) {

		_subscriptionLocalService = subscriptionLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static List<AssetEntry> _filterAssetEntries(
		long userId, List<AssetEntry> assetEntries) {

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			return Collections.emptyList();
		}

		PermissionChecker permissionChecker = null;

		try {
			permissionChecker = PermissionCheckerFactoryUtil.create(user);
		}
		catch (Exception e) {
			return Collections.emptyList();
		}

		List<AssetEntry> filteredAssetEntries = new ArrayList<>();

		for (AssetEntry assetEntry : assetEntries) {
			try {
				if (AssetEntryPermission.contains(
						permissionChecker, assetEntry, ActionKeys.VIEW)) {

					filteredAssetEntries.add(assetEntry);
				}
			}
			catch (Exception e) {
				continue;
			}
		}

		return filteredAssetEntries;
	}

	private static SubscriptionSender _getSubscriptionSender(
		PortletPreferences portletPreferences, List<AssetEntry> assetEntries) {

		if (assetEntries.isEmpty()) {
			return null;
		}

		AssetEntry assetEntry = assetEntries.get(0);

		String fromName = _assetPublisherUtil.getEmailFromName(
			portletPreferences, assetEntry.getCompanyId());
		String fromAddress = _assetPublisherUtil.getEmailFromAddress(
			portletPreferences, assetEntry.getCompanyId());

		Map<Locale, String> localizedSubjectMap =
			_assetPublisherUtil.getEmailAssetEntryAddedSubjectMap(
				portletPreferences);
		Map<Locale, String> localizedBodyMap =
			_assetPublisherUtil.getEmailAssetEntryAddedBodyMap(
				portletPreferences);

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setCompanyId(assetEntry.getCompanyId());
		subscriptionSender.setContextAttributes(
			"[$ASSET_ENTRIES$]",
			ListUtil.toString(
				assetEntries, _titleAccessor, StringPool.COMMA_AND_SPACE));
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setLocalizedBodyMap(localizedBodyMap);
		subscriptionSender.setLocalizedPortletTitleMap(
			PortletConfigurationUtil.getPortletTitleMap(portletPreferences));
		subscriptionSender.setLocalizedSubjectMap(localizedSubjectMap);
		subscriptionSender.setMailId("asset_entry", assetEntry.getEntryId());
		subscriptionSender.setPortletId(
			AssetPublisherPortletKeys.ASSET_PUBLISHER);
		subscriptionSender.setReplyToAddress(fromAddress);

		return subscriptionSender;
	}

	private static void _notifySubscribers(
		List<Subscription> subscriptions, PortletPreferences portletPreferences,
		List<AssetEntry> assetEntries) {

		if (!_assetPublisherUtil.getEmailAssetEntryAddedEnabled(
				portletPreferences)) {

			return;
		}

		Map<List<AssetEntry>, List<User>> assetEntriesToUsersMap =
			new HashMap<>();

		for (Subscription subscription : subscriptions) {
			long userId = subscription.getUserId();

			User user = _userLocalService.fetchUser(userId);

			if ((user == null) || !user.isActive()) {
				continue;
			}

			List<AssetEntry> filteredAssetEntries = _filterAssetEntries(
				userId, assetEntries);

			if (filteredAssetEntries.isEmpty()) {
				continue;
			}

			List<User> users = assetEntriesToUsersMap.get(filteredAssetEntries);

			if (users == null) {
				users = new LinkedList<>();

				assetEntriesToUsersMap.put(filteredAssetEntries, users);
			}

			users.add(user);
		}

		for (Map.Entry<List<AssetEntry>, List<User>> entry :
				assetEntriesToUsersMap.entrySet()) {

			List<AssetEntry> filteredAssetEntries = entry.getKey();
			List<User> users = entry.getValue();

			SubscriptionSender subscriptionSender = _getSubscriptionSender(
				portletPreferences, filteredAssetEntries);

			if (subscriptionSender == null) {
				continue;
			}

			for (User user : users) {
				subscriptionSender.addRuntimeSubscribers(
					user.getEmailAddress(), user.getFullName());
			}

			subscriptionSender.flushNotificationsAsync();
		}
	}

	private void _checkAssetEntries(
			com.liferay.portal.kernel.model.PortletPreferences
				portletPreferencesModel)
		throws PortalException {

		Layout layout = _layoutLocalService.fetchLayout(
			portletPreferencesModel.getPlid());

		if (layout == null) {
			return;
		}

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				layout.getCompanyId(), portletPreferencesModel.getOwnerId(),
				portletPreferencesModel.getOwnerType(),
				portletPreferencesModel.getPlid(),
				portletPreferencesModel.getPortletId(),
				portletPreferencesModel.getPreferences());

		if (!_assetPublisherUtil.getEmailAssetEntryAddedEnabled(
				portletPreferences)) {

			return;
		}

		List<AssetEntry> assetEntries = _getAssetEntries(
			portletPreferences, layout);

		if (assetEntries.isEmpty()) {
			return;
		}

		long[] notifiedAssetEntryIds = GetterUtil.getLongValues(
			portletPreferences.getValues("notifiedAssetEntryIds", null));

		ArrayList<AssetEntry> newAssetEntries = new ArrayList<>();

		for (AssetEntry assetEntry : assetEntries) {
			if (!ArrayUtil.contains(
					notifiedAssetEntryIds, assetEntry.getEntryId())) {

				newAssetEntries.add(assetEntry);
			}
		}

		List<Subscription> subscriptions =
			_subscriptionLocalService.getSubscriptions(
				portletPreferencesModel.getCompanyId(),
				com.liferay.portal.kernel.model.PortletPreferences.class.
					getName(),
				_assetPublisherUtil.getSubscriptionClassPK(
					portletPreferencesModel.getPlid(),
					portletPreferencesModel.getPortletId()));

		_notifySubscribers(subscriptions, portletPreferences, newAssetEntries);

		try {
			portletPreferences.setValues(
				"notifiedAssetEntryIds",
				StringUtil.split(
					ListUtil.toString(
						assetEntries, AssetEntry.ENTRY_ID_ACCESSOR)));

			portletPreferences.store();
		}
		catch (IOException | PortletException e) {
			throw new PortalException(e);
		}
	}

	private List<AssetEntry> _getAssetEntries(
			PortletPreferences portletPreferences, Layout layout)
		throws PortalException {

		AssetPublisherWebConfiguration assetPublisherWebConfiguration =
			_configurationProvider.getCompanyConfiguration(
				AssetPublisherWebConfiguration.class, layout.getCompanyId());

		long[] groupIds = _assetPublisherUtil.getGroupIds(
			portletPreferences, layout.getGroupId(), layout);

		AssetEntryQuery assetEntryQuery =
			_assetPublisherUtil.getAssetEntryQuery(
				portletPreferences, groupIds, null, null);

		assetEntryQuery.setGroupIds(groupIds);

		boolean anyAssetType = GetterUtil.getBoolean(
			portletPreferences.getValue("anyAssetType", null), true);

		if (!anyAssetType) {
			long[] availableClassNameIds =
				AssetRendererFactoryRegistryUtil.getClassNameIds(
					layout.getCompanyId());

			long[] classNameIds = _assetPublisherUtil.getClassNameIds(
				portletPreferences, availableClassNameIds);

			assetEntryQuery.setClassNameIds(classNameIds);
		}

		long[] classTypeIds = GetterUtil.getLongValues(
			portletPreferences.getValues("classTypeIds", null));

		assetEntryQuery.setClassTypeIds(classTypeIds);

		boolean enablePermissions = GetterUtil.getBoolean(
			portletPreferences.getValue("enablePermissions", null));

		assetEntryQuery.setEnablePermissions(enablePermissions);

		assetEntryQuery.setEnd(
			assetPublisherWebConfiguration.dynamicSubscriptionLimit());

		boolean excludeZeroViewCount = GetterUtil.getBoolean(
			portletPreferences.getValue("excludeZeroViewCount", null));

		assetEntryQuery.setExcludeZeroViewCount(excludeZeroViewCount);

		boolean showOnlyLayoutAssets = GetterUtil.getBoolean(
			portletPreferences.getValue("showOnlyLayoutAssets", null));

		if (showOnlyLayoutAssets) {
			assetEntryQuery.setLayout(layout);
		}

		String orderByColumn1 = GetterUtil.getString(
			portletPreferences.getValue("orderByColumn1", "modifiedDate"));

		assetEntryQuery.setOrderByCol1(orderByColumn1);

		String orderByColumn2 = GetterUtil.getString(
			portletPreferences.getValue("orderByColumn2", "title"));

		assetEntryQuery.setOrderByCol2(orderByColumn2);

		String orderByType1 = GetterUtil.getString(
			portletPreferences.getValue("orderByType1", "DESC"));

		assetEntryQuery.setOrderByType1(orderByType1);

		String orderByType2 = GetterUtil.getString(
			portletPreferences.getValue("orderByType2", "ASC"));

		assetEntryQuery.setOrderByType2(orderByType2);

		assetEntryQuery.setStart(0);

		try {
			SearchContext searchContext = SearchContextFactory.getInstance(
				new long[0], new String[0], null, layout.getCompanyId(),
				StringPool.BLANK, layout,
				LocaleThreadLocal.getSiteDefaultLocale(), layout.getGroupId(),
				TimeZoneThreadLocal.getDefaultTimeZone(), 0);

			BaseModelSearchResult<AssetEntry> baseModelSearchResult =
				AssetUtil.searchAssetEntries(
					searchContext, assetEntryQuery, 0,
					assetPublisherWebConfiguration.dynamicSubscriptionLimit());

			return baseModelSearchResult.getBaseModels();
		}
		catch (Exception e) {
			return Collections.emptyList();
		}
	}

	private static AssetPublisherUtil _assetPublisherUtil;
	private static LayoutLocalService _layoutLocalService;
	private static PortletPreferencesLocalService
		_portletPreferencesLocalService;
	private static SubscriptionLocalService _subscriptionLocalService;

	private static final Accessor<AssetEntry, String> _titleAccessor =
		new Accessor<AssetEntry, String>() {

			@Override
			public String get(AssetEntry assetEntry) {
				return assetEntry.getTitle(LocaleUtil.getSiteDefault());
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<AssetEntry> getTypeClass() {
				return AssetEntry.class;
			}

		};

	private static UserLocalService _userLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

}