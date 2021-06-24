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

package com.liferay.asset.publisher.web.internal.portlet.layout.listener;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryUsage;
import com.liferay.asset.list.service.AssetListEntryUsageLocalService;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.asset.publisher.web.internal.constants.AssetPublisherSelectionStyleConstants;
import com.liferay.asset.publisher.web.internal.helper.AssetPublisherWebHelper;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.portlet.PortletLayoutListenerException;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the implementation of <code>PortletLayoutListener</code> (in
 * <code>com.liferay.portal.kernel</code>) for the Asset Publisher portlet so
 * email subscriptions can be removed when the Asset Publisher is removed from
 * the page.
 *
 * @author Zsolt Berentey
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + AssetPublisherPortletKeys.ASSET_PUBLISHER,
	service = PortletLayoutListener.class
)
public class AssetPublisherPortletLayoutListener
	implements PortletLayoutListener {

	@Override
	public void onAddToLayout(String portletId, long plid) {
	}

	@Override
	public void onMoveInLayout(String portletId, long plid) {
	}

	@Override
	public void onRemoveFromLayout(String portletId, long plid)
		throws PortletLayoutListenerException {

		try {
			Layout layout = _layoutLocalService.getLayout(plid);

			if (_assetPublisherWebHelper.isDefaultAssetPublisher(
					layout, portletId, StringPool.BLANK)) {

				_journalArticleLocalService.deleteLayoutArticleReferences(
					layout.getGroupId(), layout.getUuid());
			}

			long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
			int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

			if (PortletIdCodec.hasUserId(portletId)) {
				ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
				ownerId = PortletIdCodec.decodeUserId(portletId);
			}

			_subscriptionLocalService.deleteSubscriptions(
				layout.getCompanyId(), PortletPreferences.class.getName(),
				_assetPublisherWebHelper.getSubscriptionClassPK(
					ownerId, ownerType, plid, portletId));

			_deleteLayoutClassedModelUsages(layout, portletId);

			_deleteAssetListEntryUsage(plid, portletId);
		}
		catch (Exception exception) {
			throw new PortletLayoutListenerException(exception);
		}
	}

	@Override
	public void onSetup(String portletId, long plid) {
		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return;
		}

		javax.portlet.PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portletId);

		String selectionStyle = portletPreferences.getValue(
			"selectionStyle",
			AssetPublisherSelectionStyleConstants.TYPE_DYNAMIC);

		long assetListEntryId = GetterUtil.getLong(
			portletPreferences.getValue("assetListEntryId", null));

		String infoListProviderKey = portletPreferences.getValue(
			"infoListProviderKey", StringPool.BLANK);

		if (Objects.equals(
				selectionStyle,
				AssetPublisherSelectionStyleConstants.TYPE_ASSET_LIST) &&
			(assetListEntryId > 0)) {

			_addAssetListEntryUsage(
				_portal.getClassNameId(AssetListEntry.class),
				String.valueOf(assetListEntryId), plid, portletId);
		}
		else if (Objects.equals(
					selectionStyle,
					AssetPublisherSelectionStyleConstants.
						TYPE_ASSET_LIST_PROVIDER) &&
				 Validator.isNotNull(infoListProviderKey)) {

			_addAssetListEntryUsage(
				_portal.getClassNameId(InfoCollectionProvider.class),
				infoListProviderKey, plid, portletId);
		}
		else if (Objects.equals(
					selectionStyle,
					AssetPublisherSelectionStyleConstants.TYPE_MANUAL)) {

			_deleteAssetListEntryUsage(plid, portletId);
			_deleteLayoutClassedModelUsages(layout, portletId);

			_addLayoutClassedModelUsages(plid, portletId, portletPreferences);
		}
		else {
			_deleteAssetListEntryUsage(plid, portletId);
		}

		if (!Objects.equals(
				selectionStyle,
				AssetPublisherSelectionStyleConstants.TYPE_MANUAL)) {

			_deleteLayoutClassedModelUsages(layout, portletId);
		}
	}

	@Override
	public void updatePropertiesOnRemoveFromLayout(
			String portletId, UnicodeProperties typeSettingsUnicodeProperties)
		throws PortletLayoutListenerException {

		String defaultAssetPublisherPortletId =
			typeSettingsUnicodeProperties.getProperty(
				LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID);

		if (portletId.equals(defaultAssetPublisherPortletId)) {
			typeSettingsUnicodeProperties.setProperty(
				LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID,
				StringPool.BLANK);
		}
	}

	private void _addAssetListEntryUsage(
		long classNameId, String key, long plid, String portletId) {

		List<AssetListEntryUsage> assetListEntryUsages =
			_assetListEntryUsageLocalService.getAssetListEntryUsages(
				portletId, _portal.getClassNameId(Portlet.class), plid);

		if (ListUtil.isNotEmpty(assetListEntryUsages)) {
			for (AssetListEntryUsage assetListEntryUsage :
					assetListEntryUsages) {

				if (Objects.equals(key, assetListEntryUsage.getKey())) {
					continue;
				}

				assetListEntryUsage.setKey(key);
				assetListEntryUsage.setClassNameId(classNameId);

				_assetListEntryUsageLocalService.updateAssetListEntryUsage(
					assetListEntryUsage);
			}

			return;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		try {
			_assetListEntryUsageLocalService.addAssetListEntryUsage(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				classNameId, portletId, _portal.getClassNameId(Portlet.class),
				key, plid, serviceContext);
		}
		catch (PortalException portalException) {
			_log.error("Unable to add asset list entry usage", portalException);
		}
	}

	private void _addLayoutClassedModelUsages(
			long plid, String portletId,
			javax.portlet.PortletPreferences portletPreferences)
		throws PortletLayoutListenerException {

		try {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

			PortletRequest portletRequest =
				serviceContext.getLiferayPortletRequest();

			long[] groupIds = _assetPublisherHelper.getGroupIds(
				portletPreferences, themeDisplay.getScopeGroupId(),
				themeDisplay.getLayout());

			List<AssetEntry> assetEntries =
				_assetPublisherHelper.getAssetEntries(
					portletRequest, portletPreferences,
					themeDisplay.getPermissionChecker(), groupIds, false, true);

			for (AssetEntry assetEntry : assetEntries) {
				_layoutClassedModelUsageLocalService.addLayoutClassedModelUsage(
					themeDisplay.getScopeGroupId(), assetEntry.getClassNameId(),
					assetEntry.getClassPK(), portletId,
					_portal.getClassNameId(Portlet.class), plid,
					serviceContext);
			}
		}
		catch (Exception exception) {
			throw new PortletLayoutListenerException(exception);
		}
	}

	private void _deleteAssetListEntryUsage(long plid, String portletId) {
		_assetListEntryUsageLocalService.deleteAssetListEntryUsages(
			portletId, _portal.getClassNameId(Portlet.class), plid);
	}

	private void _deleteLayoutClassedModelUsages(
		Layout layout, String portletId) {

		_layoutClassedModelUsageLocalService.deleteLayoutClassedModelUsages(
			portletId, _portal.getClassNameId(Portlet.class), layout.getPlid());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetPublisherPortletLayoutListener.class);

	@Reference
	private AssetListEntryUsageLocalService _assetListEntryUsageLocalService;

	@Reference
	private AssetPublisherHelper _assetPublisherHelper;

	@Reference
	private AssetPublisherWebHelper _assetPublisherWebHelper;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}