/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.scheduler.helper;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.kernel.util.NotifiedAssetEntryThreadLocal;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.asset.publisher.web.internal.configuration.AssetPublisherSelectionStyleConfigurationUtil;
import com.liferay.asset.publisher.web.internal.configuration.AssetPublisherWebConfiguration;
import com.liferay.asset.publisher.web.internal.constants.AssetPublisherSelectionStyleConstants;
import com.liferay.asset.publisher.web.internal.helper.AssetPublisherWebHelper;
import com.liferay.asset.util.AssetHelper;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.TimeZoneThreadLocal;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.asset.service.permission.AssetEntryPermission;
import com.liferay.portlet.configuration.kernel.util.PortletConfigurationUtil;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;
import com.liferay.segments.constants.SegmentsEntryConstants;
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
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	configurationPid = "com.liferay.asset.publisher.web.internal.configuration.AssetPublisherWebConfiguration",
	service = AssetEntriesCheckerHelper.class
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

		ListUtil.distinct(assetEntries);

		long[] notifiedAssetEntryIds = GetterUtil.getLongValues(
			portletPreferences.getValues("notifiedAssetEntryIds", null));

		ArrayList<AssetEntry> newAssetEntries = new ArrayList<>();

		for (AssetEntry assetEntry : assetEntries) {
			if (assetEntry.isVisible() &&
				!ArrayUtil.contains(
					notifiedAssetEntryIds, assetEntry.getEntryId())) {

				newAssetEntries.add(assetEntry);
			}
		}

		if (newAssetEntries.isEmpty()) {
			return;
		}

		_notifySubscribers(
			_portal.getLayoutFullURL(
				layout.getGroupId(), portletPreferencesModel.getPortletId()),
			_subscriptionLocalService.getSubscriptions(
				portletPreferencesModel.getCompanyId(),
				com.liferay.portal.kernel.model.PortletPreferences.class.
					getName(),
				_assetPublisherWebHelper.getSubscriptionClassPK(
					portletPreferencesModel.getPlid(),
					portletPreferencesModel.getPortletId())),
			portletPreferences, newAssetEntries);

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
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

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
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		return filteredAssetEntries;
	}

	private List<AssetEntry> _getAssetEntries(
			PortletPreferences portletPreferences, Layout layout)
		throws PortalException {

		String selectionStyle = GetterUtil.getString(
			portletPreferences.getValue("selectionStyle", null),
			AssetPublisherSelectionStyleConfigurationUtil.
				defaultSelectionStyle());

		if (Objects.equals(
				selectionStyle,
				AssetPublisherSelectionStyleConstants.TYPE_ASSET_LIST)) {

			return _getAssetListEntrySelectedAssetEntries(
				portletPreferences, layout.getGroupId());
		}
		else if (Objects.equals(
					selectionStyle,
					AssetPublisherSelectionStyleConstants.TYPE_DYNAMIC)) {

			return _getDynamicSelectedAssetEntries(portletPreferences, layout);
		}

		return _getManuallySelectedAssetEntries(
			portletPreferences, layout.getGroupId());
	}

	private List<AssetEntry> _getAssetListEntrySelectedAssetEntries(
		PortletPreferences portletPreferences, long groupId) {

		long assetListEntryId = GetterUtil.getLong(
			portletPreferences.getValue("assetListEntryId", null));

		if (assetListEntryId <= 0) {
			return Collections.emptyList();
		}

		List<AssetEntry> assetEntries = new ArrayList<>();

		try {
			AssetListEntry assetListEntry =
				_assetListEntryLocalService.fetchAssetListEntry(
					assetListEntryId);

			if (assetListEntry == null) {
				return Collections.emptyList();
			}

			long[] segmentsEntryIds = {SegmentsEntryConstants.ID_DEFAULT};

			try {
				if (_segmentsConfigurationProvider.isSegmentationEnabled(
						groupId)) {

					segmentsEntryIds = ArrayUtil.toLongArray(
						TransformUtil.transform(
							_assetListEntrySegmentsEntryRelLocalService.
								getAssetListEntrySegmentsEntryRels(
									assetListEntryId, QueryUtil.ALL_POS,
									QueryUtil.ALL_POS),
							assetListEntrySegmentsEntryRel ->
								assetListEntrySegmentsEntryRel.
									getSegmentsEntryId()));
				}
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get segments entry IDs for asset list " +
							"entry " + assetListEntryId);
				}

				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}

			assetEntries = _assetListAssetEntryProvider.getAssetEntries(
				assetListEntry, segmentsEntryIds, null, null, StringPool.BLANK,
				StringPool.BLANK, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return assetEntries;
	}

	private List<AssetEntry> _getDynamicSelectedAssetEntries(
			PortletPreferences portletPreferences, Layout layout)
		throws PortalException {

		AssetPublisherWebConfiguration assetPublisherWebConfiguration =
			_configurationProvider.getCompanyConfiguration(
				AssetPublisherWebConfiguration.class, layout.getCompanyId());

		AssetEntryQuery assetEntryQuery =
			_assetPublisherHelper.getAssetEntryQuery(
				portletPreferences, layout.getGroupId(), layout, null, null);

		int end = assetPublisherWebConfiguration.dynamicSubscriptionLimit();
		int start = 0;

		if (end == 0) {
			end = QueryUtil.ALL_POS;
			start = QueryUtil.ALL_POS;
		}

		assetEntryQuery.setEnd(end);
		assetEntryQuery.setStart(start);

		try {
			SearchContext searchContext = SearchContextFactory.getInstance(
				new long[0], new String[0], null, layout.getCompanyId(),
				StringPool.BLANK, layout,
				LocaleThreadLocal.getSiteDefaultLocale(), layout.getGroupId(),
				TimeZoneThreadLocal.getDefaultTimeZone(), 0);

			BaseModelSearchResult<AssetEntry> baseModelSearchResult =
				_assetHelper.searchAssetEntries(
					searchContext, assetEntryQuery, start, end);

			return baseModelSearchResult.getBaseModels();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return Collections.emptyList();
		}
	}

	private List<AssetEntry> _getManuallySelectedAssetEntries(
		PortletPreferences portletPreferences, long groupId) {

		List<AssetEntry> assetEntries = new ArrayList<>();

		String[] assetEntryXmls = portletPreferences.getValues(
			"assetEntryXml", new String[0]);

		for (String assetEntryXml : assetEntryXmls) {
			try {
				Group group = _groupLocalService.fetchGroup(groupId);

				if (group.isStagingGroup()) {
					groupId = group.getLiveGroupId();
				}

				Document document = SAXReaderUtil.read(assetEntryXml);

				Element rootElement = document.getRootElement();

				String assetEntryUuid = rootElement.elementText(
					"asset-entry-uuid");

				AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
					groupId, assetEntryUuid);

				if ((assetEntry == null) || !assetEntry.isVisible()) {
					continue;
				}

				assetEntries.add(assetEntry);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		return assetEntries;
	}

	private SubscriptionSender _getSubscriptionSender(
		String layoutURL, PortletPreferences portletPreferences,
		List<AssetEntry> assetEntries) {

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
		subscriptionSender.setEntryURL(layoutURL);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setGroupId(assetEntry.getGroupId());
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setLocalizedBodyMap(localizedBodyMap);
		subscriptionSender.setLocalizedPortletTitleMap(
			PortletConfigurationUtil.getPortletTitleMap(portletPreferences));
		subscriptionSender.setLocalizedSubjectMap(localizedSubjectMap);
		subscriptionSender.setMailId("asset_entry", assetEntry.getEntryId());
		subscriptionSender.setNotificationType(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY);
		subscriptionSender.setPortletId(
			AssetPublisherPortletKeys.ASSET_PUBLISHER);
		subscriptionSender.setReplyToAddress(fromAddress);

		return subscriptionSender;
	}

	private void _notifySubscribers(
		String layoutURL, List<Subscription> subscriptions,
		PortletPreferences portletPreferences, List<AssetEntry> assetEntries) {

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
				layoutURL, portletPreferences, entry.getKey());

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

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntriesCheckerHelper.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private AssetListAssetEntryProvider _assetListAssetEntryProvider;

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference
	private AssetListEntrySegmentsEntryRelLocalService
		_assetListEntrySegmentsEntryRelLocalService;

	@Reference
	private AssetPublisherHelper _assetPublisherHelper;

	@Reference
	private AssetPublisherWebHelper _assetPublisherWebHelper;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private PortletPreferenceValueLocalService
		_portletPreferenceValueLocalService;

	@Reference
	private SegmentsConfigurationProvider _segmentsConfigurationProvider;

	@Reference
	private SegmentsEntryRetriever _segmentsEntryRetriever;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}