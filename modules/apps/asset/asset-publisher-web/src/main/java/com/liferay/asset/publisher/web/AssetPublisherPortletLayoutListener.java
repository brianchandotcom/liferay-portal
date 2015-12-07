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

package com.liferay.asset.publisher.web;

import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.web.util.AssetPublisherUtil;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.portlet.PortletLayoutListenerException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.SubscriptionLocalService;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.util.AssetUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zsolt Berentey
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetPublisherPortletKeys.ASSET_PUBLISHER
	},
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

			if (AssetUtil.isDefaultAssetPublisher(
					layout, portletId, StringPool.BLANK)) {

				_journalArticleLocalService.deleteLayoutArticleReferences(
					layout.getGroupId(), layout.getUuid());
			}

			long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
			int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

			if (PortletConstants.hasUserId(portletId)) {
				ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
				ownerId = PortletConstants.getUserId(portletId);
			}

			_subscriptionLocalService.deleteSubscriptions(
				layout.getCompanyId(), PortletPreferences.class.getName(),
				AssetPublisherUtil.getSubscriptionClassPK(
					ownerId, ownerType, plid, portletId));
		}
		catch (Exception e) {
			throw new PortletLayoutListenerException(e);
		}
	}

	@Override
	public void onSetup(String portletId, long plid) {
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

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setSubscriptionLocalService(
		SubscriptionLocalService subscriptionLocalService) {

		_subscriptionLocalService = subscriptionLocalService;
	}

	private volatile JournalArticleLocalService _journalArticleLocalService;
	private volatile LayoutLocalService _layoutLocalService;
	private volatile SubscriptionLocalService _subscriptionLocalService;

}