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
import com.liferay.asset.list.model.AssetListEntryUsage;
import com.liferay.asset.list.service.AssetListEntryUsageLocalService;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.asset.publisher.web.internal.util.AssetPublisherWebUtil;
import com.liferay.asset.service.AssetEntryUsageLocalService;
import com.liferay.journal.service.JournalArticleLocalService;
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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
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

			if (_assetPublisherWebUtil.isDefaultAssetPublisher(
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
				_assetPublisherWebUtil.getSubscriptionClassPK(
					ownerId, ownerType, plid, portletId));

			_removeAssetEntryUsages(layout, portletId);

			_removeAssetListEntryUsage(plid, portletId);
		}
		catch (Exception e) {
			throw new PortletLayoutListenerException(e);
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
			"selectionStyle", "dynamic");

		long assetListEntryId = GetterUtil.getLong(
			portletPreferences.getValue("assetListEntryId", null));

		if (Objects.equals(selectionStyle, "asset-list") &&
			(assetListEntryId > 0)) {

			_addAssetListEntryUsage(assetListEntryId, plid, portletId);
		}
		else if (Objects.equals(selectionStyle, "manual")) {
			_removeAssetEntryUsages(layout, portletId);

			_addAssetEntryUsages(plid, portletId, portletPreferences);
		}
		else {
			_removeAssetListEntryUsage(plid, portletId);
		}

		if (!Objects.equals(selectionStyle, "manual")) {
			_removeAssetEntryUsages(layout, portletId);
		}
	}

	@Override
	public void updatePropertiesOnRemoveFromLayout(
			String portletId, UnicodeProperties typeSettingsProperties)
		throws PortletLayoutListenerException {

		String defaultAssetPublisherPortletId =
			typeSettingsProperties.getProperty(
				LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID);

		if (portletId.equals(defaultAssetPublisherPortletId)) {
			typeSettingsProperties.setProperty(
				LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID,
				StringPool.BLANK);
		}
	}

	private void _addAssetEntryUsages(
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
				_assetEntryUsageLocalService.addAssetEntryUsage(
					themeDisplay.getScopeGroupId(), assetEntry.getEntryId(),
					_portal.getClassNameId(Portlet.class), portletId, plid,
					serviceContext);
			}
		}
		catch (Exception e) {
			throw new PortletLayoutListenerException(e);
		}
	}

	private void _addAssetListEntryUsage(
		long assetListEntryId, long plid, String portletId) {

		AssetListEntryUsage assetListEntryUsage =
			_assetListEntryUsageLocalService.fetchAssetListEntryUsage(
				_portal.getClassNameId(Layout.class), plid, portletId);

		if (assetListEntryUsage != null) {
			assetListEntryUsage.setAssetListEntryId(assetListEntryId);

			_assetListEntryUsageLocalService.updateAssetListEntryUsage(
				assetListEntryUsage);

			return;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		try {
			_assetListEntryUsageLocalService.addAssetListEntryUsage(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				assetListEntryId, _portal.getClassNameId(Layout.class), plid,
				portletId, serviceContext);
		}
		catch (PortalException pe) {
			_log.error("Unable to add asset list entry usage", pe);
		}
	}

	private void _removeAssetEntryUsages(Layout layout, String portletId) {
		_assetEntryUsageLocalService.deleteAssetEntryUsages(
			_portal.getClassNameId(Portlet.class), portletId, layout.getPlid());
	}

	private void _removeAssetListEntryUsage(long plid, String portletId) {
		AssetListEntryUsage assetListEntryUsage =
			_assetListEntryUsageLocalService.fetchAssetListEntryUsage(
				_portal.getClassNameId(Layout.class), plid, portletId);

		if (assetListEntryUsage != null) {
			_assetListEntryUsageLocalService.deleteAssetListEntryUsage(
				assetListEntryUsage);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetPublisherPortletLayoutListener.class);

	@Reference
	private AssetEntryUsageLocalService _assetEntryUsageLocalService;

	@Reference
	private AssetListEntryUsageLocalService _assetListEntryUsageLocalService;

	@Reference
	private AssetPublisherHelper _assetPublisherHelper;

	@Reference
	private AssetPublisherWebUtil _assetPublisherWebUtil;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}