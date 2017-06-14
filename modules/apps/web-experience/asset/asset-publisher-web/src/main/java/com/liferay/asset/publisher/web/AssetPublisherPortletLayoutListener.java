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

import com.liferay.asset.publisher.web.util.AssetPublisherUtil;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.portlet.PortletLayoutListenerException;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.SubscriptionLocalService;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.asset.util.AssetUtil;

/**
 * Provides the implementation of <code>PortletLayoutListener</code> (in
 * <code>com.liferay.portal.kernel</code>) for the Asset Publisher portlet so
 * email subscriptions can be removed when the Asset Publisher is removed from
 * the page.
 *
 * @author Zsolt Berentey
 * @deprecated As of 2.0.0, with not direct replacement
 */
@Deprecated
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

			if (PortletIdCodec.hasUserId(portletId)) {
				ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
				ownerId = PortletIdCodec.decodeUserId(portletId);
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

	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	protected void setSubscriptionLocalService(
		SubscriptionLocalService subscriptionLocalService) {

		_subscriptionLocalService = subscriptionLocalService;
	}

	private JournalArticleLocalService _journalArticleLocalService;
	private LayoutLocalService _layoutLocalService;
	private SubscriptionLocalService _subscriptionLocalService;

}