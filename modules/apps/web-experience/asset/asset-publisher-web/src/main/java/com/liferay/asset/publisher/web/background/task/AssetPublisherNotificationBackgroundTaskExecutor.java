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

package com.liferay.asset.publisher.web.background.task;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.publisher.web.util.AssetPublisherUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.asset.service.permission.AssetEntryPermission;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

/**
 * @author Pavel Savinov
 */
public class AssetPublisherNotificationBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	@Override
	public BackgroundTaskExecutor clone() {
		AssetPublisherNotificationBackgroundTaskExecutor
			assetPublisherNotificationBackgroundTaskExecutor =
				new AssetPublisherNotificationBackgroundTaskExecutor();

		return assetPublisherNotificationBackgroundTaskExecutor;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws PortalException {

		BackgroundTaskResult backgroundTaskResult = new BackgroundTaskResult();

		Map<String, Serializable> context = backgroundTask.getTaskContextMap();

		List<AssetEntry> assetEntries = (List<AssetEntry>)context.get(
			"assetEntries");

		long companyId = GetterUtil.getLong(context.get("companyId"));

		com.liferay.portal.kernel.model.PortletPreferences
			portletPreferencesModel =
				(com.liferay.portal.kernel.model.PortletPreferences)
					context.get("portletPreferences");

		List<Subscription> subscriptions =
			SubscriptionLocalServiceUtil.getSubscriptions(
				companyId,
				com.liferay.portal.kernel.model.PortletPreferences.class.
					getName(),
				AssetPublisherUtil.getSubscriptionClassPK(
					portletPreferencesModel.getPlid(),
					portletPreferencesModel.getPortletId()));

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, portletPreferencesModel.getOwnerId(),
				portletPreferencesModel.getOwnerType(),
				portletPreferencesModel.getPlid(),
				portletPreferencesModel.getPortletId(),
				portletPreferencesModel.getPreferences());

		for (Subscription subscription : subscriptions) {
			AssetPublisherUtil.notifySubscriber(
				subscription.getUserId(), portletPreferences,
				filterAssetEntries(subscription.getUserId(), assetEntries));
		}

		try {
			portletPreferences.setValues(
				"notifiedAssetEntryIds",
				StringUtil.split(
					ListUtil.toString(
						assetEntries, AssetEntry.ENTRY_ID_ACCESSOR)));

			portletPreferences.store();

			backgroundTaskResult.setStatus(
				BackgroundTaskConstants.STATUS_SUCCESSFUL);
		}
		catch (IOException ioe) {
			backgroundTaskResult.setStatus(
				BackgroundTaskConstants.STATUS_FAILED);

			throw new SystemException(ioe);
		}
		catch (PortletException pe) {
			backgroundTaskResult.setStatus(
				BackgroundTaskConstants.STATUS_FAILED);

			throw new SystemException(pe);
		}

		return backgroundTaskResult;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

	protected static boolean hasPermission(long userId, AssetEntry assetEntry)
		throws PortalException {

		User user = UserLocalServiceUtil.getUser(userId);

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			return AssetEntryPermission.contains(
				permissionChecker, assetEntry, ActionKeys.VIEW);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	protected List<AssetEntry> filterAssetEntries(
			long userId, List<AssetEntry> assetEntries)
		throws PortalException {

		List<AssetEntry> filteredAssetEntries = new ArrayList<>();

		for (AssetEntry assetEntry : assetEntries) {
			if (hasPermission(userId, assetEntry)) {
				filteredAssetEntries.add(assetEntry);
			}
		}

		return filteredAssetEntries;
	}

}