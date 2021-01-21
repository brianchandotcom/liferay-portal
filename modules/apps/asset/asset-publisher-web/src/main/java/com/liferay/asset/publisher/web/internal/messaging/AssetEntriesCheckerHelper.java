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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.kernel.util.NotifiedAssetEntryThreadLocal;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.asset.publisher.web.internal.configuration.AssetPublisherWebConfiguration;
import com.liferay.asset.publisher.web.internal.helper.AssetPublisherWebHelper;
import com.liferay.asset.util.AssetHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.TimeZoneThreadLocal;
import com.liferay.portlet.asset.service.permission.AssetEntryPermission;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	configurationPid = "com.liferay.asset.publisher.web.internal.configuration.AssetPublisherWebConfiguration",
	immediate = true, service = AssetEntriesCheckerHelper.class
)
public class AssetEntriesCheckerHelper {

	public void checkAssetEntries() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			_portletPreferencesLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property property = PropertyFactoryUtil.forName("portletId");

				dynamicQuery.add(
					property.like(
						PortletIdCodec.encode(
							AssetPublisherPortletKeys.ASSET_PUBLISHER,
							StringPool.PERCENT)));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(com.liferay.portal.kernel.model.PortletPreferences
				portletPreferences) -> _checkAssetEntries(portletPreferences));

		actionableDynamicQuery.performActions();
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
			_portletPreferenceValueLocalService.getPreferences(
				portletPreferencesModel);

		if (!_assetPublisherWebHelper.getEmailAssetEntryAddedEnabled(
				portletPreferences)) {

			return;
		}

		List<AssetEntry> assetEntries = _getAssetEntries(
			portletPreferences, layout);

		if (assetEntries.isEmpty()) {
			return;
		}

		Stream<AssetEntry> streamAssetEntries = assetEntries.stream();

		assetEntries = streamAssetEntries.distinct(
		).collect(
			Collectors.toList()
		);

		long[] notifiedAssetEntryIds = GetterUtil.getLongValues(
			portletPreferences.getValues("notifiedAssetEntryIds", null));

		ArrayList<AssetEntry> newAssetEntries = new ArrayList<>();

		for (AssetEntry assetEntry : assetEntries) {
			if (!ArrayUtil.contains(
					notifiedAssetEntryIds, assetEntry.getEntryId())) {

				newAssetEntries.add(assetEntry);
			}
		}

		if (newAssetEntries.isEmpty()) {
			return;
		}

		List<Subscription> subscriptions =
			_subscriptionLocalService.getSubscriptions(
				portletPreferencesModel.getCompanyId(),
				com.liferay.portal.kernel.model.PortletPreferences.class.
					getName(),
				_assetPublisherWebHelper.getSubscriptionClassPK(
					portletPreferencesModel.getPlid(),
					portletPreferencesModel.getPortletId()));

		_notifySubscribers(subscriptions, portletPreferences, newAssetEntries);

		NotifiedAssetEntryThreadLocal.setNotifiedAssetEntryIdsModified(true);

		try {
			portletPreferences.setValues(
				"notifiedAssetEntryIds",
				StringUtil.split(
					ListUtil.toString(
						assetEntries, AssetEntry.ENTRY_ID_ACCESSOR)));

			portletPreferences.store();
		}
		catch (IOException | PortletException exception) {
			throw new PortalException(exception);
		}
		finally {
			NotifiedAssetEntryThreadLocal.setNotifiedAssetEntryIdsModified(
				false);
		}
	}

	private List<AssetEntry> _filterAssetEntries(
		long userId, List<AssetEntry> assetEntries) {

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			return Collections.emptyList();
		}

		PermissionChecker permissionChecker = null;

		try {
			permissionChecker = PermissionCheckerFactoryUtil.create(user);
		}
		catch (Exception exception) {
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
			catch (Exception exception) {
			}
		}

		return filteredAssetEntries;
	}

	private List<AssetEntry> _getAssetEntries(
			PortletPreferences portletPreferences, Layout layout)
		throws PortalException {

		AssetPublisherWebConfiguration assetPublisherWebConfiguration =
			_configurationProvider.getCompanyConfiguration(
				AssetPublisherWebConfiguration.class, layout.getCompanyId());

		AssetEntryQuery assetEntryQuery =
			_assetPublisherHelper.getAssetEntryQuery(
				portletPreferences, layout.getGroupId(), layout, null, null);

		assetEntryQuery.setEnd(
			assetPublisherWebConfiguration.dynamicSubscriptionLimit());
		assetEntryQuery.setStart(0);

		try {
			SearchContext searchContext = SearchContextFactory.getInstance(
				new long[0], new String[0], null, layout.getCompanyId(),
				StringPool.BLANK, layout,
				LocaleThreadLocal.getSiteDefaultLocale(), layout.getGroupId(),
				TimeZoneThreadLocal.getDefaultTimeZone(), 0);

			BaseModelSearchResult<AssetEntry> baseModelSearchResult =
				_assetHelper.searchAssetEntries(
					searchContext, assetEntryQuery, 0,
					assetPublisherWebConfiguration.dynamicSubscriptionLimit());

			return baseModelSearchResult.getBaseModels();
		}
		catch (Exception exception) {
			return Collections.emptyList();
		}
	}

	private SubscriptionSender _getSubscriptionSender(
		PortletPreferences portletPreferences, List<AssetEntry> assetEntries) {

		if (assetEntries.isEmpty()) {
			return null;
		}

		AssetEntry assetEntry = assetEntries.get(0);

		String fromName = _assetPublisherWebHelper.getEmailFromName(
			portletPreferences, assetEntry.getCompanyId());
		String fromAddress = _assetPublisherWebHelper.getEmailFromAddress(
			portletPreferences, assetEntry.getCompanyId());

		Map<Locale, String> localizedSubjectMap =
			_assetPublisherWebHelper.getEmailAssetEntryAddedSubjectMap(
				portletPreferences);
		Map<Locale, String> localizedBodyMap =
			_assetPublisherWebHelper.getEmailAssetEntryAddedBodyMap(
				portletPreferences);

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setCompanyId(assetEntry.getCompanyId());
		subscriptionSender.setContextAttributes(
			"[$ASSET_ENTRIES$]",
			com.liferay.petra.string.StringUtil.merge(
				assetEntries,
				entry -> entry.getTitle(LocaleUtil.getSiteDefault()),
				StringPool.COMMA_AND_SPACE));
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setGroupId(assetEntry.getGroupId());
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

	private void _notifySubscribers(
		List<Subscription> subscriptions, PortletPreferences portletPreferences,
		List<AssetEntry> assetEntries) {

		if (!_assetPublisherWebHelper.getEmailAssetEntryAddedEnabled(
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

			if (ListUtil.isEmpty(users)) {
				users = new LinkedList<>();

				assetEntriesToUsersMap.put(filteredAssetEntries, users);
			}

			users.add(user);
		}

		for (Map.Entry<List<AssetEntry>, List<User>> entry :
				assetEntriesToUsersMap.entrySet()) {

			SubscriptionSender subscriptionSender = _getSubscriptionSender(
				portletPreferences, entry.getKey());

			if (subscriptionSender == null) {
				continue;
			}

			for (User user : entry.getValue()) {
				subscriptionSender.addRuntimeSubscribers(
					user.getEmailAddress(), user.getFullName());
			}

			subscriptionSender.flushNotificationsAsync();
		}
	}

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private AssetPublisherHelper _assetPublisherHelper;

	@Reference
	private AssetPublisherWebHelper _assetPublisherWebHelper;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private PortletPreferenceValueLocalService
		_portletPreferenceValueLocalService;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}