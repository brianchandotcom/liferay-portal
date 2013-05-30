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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityCounter;
import com.liferay.portlet.social.model.SocialActivityCounterConstants;
import com.liferay.portlet.social.model.SocialActivityLimit;
import com.liferay.portlet.social.model.impl.SocialActivityImpl;
import com.liferay.portlet.social.service.SocialActivityCounterLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivityLimitLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityTestUtil {

	public static void addActivity(
			User user, Group group, AssetEntry assetEntry, int type)
		throws Exception {

		addActivity(user, group, assetEntry, type, StringPool.BLANK);
	}

	public static void addActivity(
			User user, Group group, AssetEntry assetEntry, int type,
			String extraData)
		throws Exception {

		SocialActivityLocalServiceUtil.addActivity(
			user.getUserId(), group.getGroupId(), assetEntry.getClassName(),
			assetEntry.getClassPK(), type, extraData, 0);
	}

	public static AssetEntry addAsset(User user, Group group) throws Exception {
		return AssetEntryLocalServiceUtil.updateEntry(
			user.getUserId(), group.getGroupId(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomPK(), null,
			null);
	}

	public static AssetEntry addAsset(
			User user, Group group, AssetEntry assetEntry)
		throws Exception {

		if (assetEntry != null) {
			AssetEntryLocalServiceUtil.deleteEntry(assetEntry);
		}

		return AssetEntryLocalServiceUtil.updateEntry(
			user.getUserId(), group.getGroupId(), _TEST_MODEL, 1, null, null);
	}

	public static String createExtraDataJSON(String key, String value) {
		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put(key, value);

		return extraDataJSONObject.toString();
	}

	public static SocialActivityCounter getActivityCounter(
			long groupId, String name, Object owner)
		throws Exception {

		long classNameId = 0;
		long classPK = 0;
		int ownerType = SocialActivityCounterConstants.TYPE_ACTOR;

		if (owner instanceof User) {
			classNameId = PortalUtil.getClassNameId(User.class.getName());
			classPK = ((User)owner).getUserId();
		}
		else if (owner instanceof AssetEntry) {
			classNameId = ((AssetEntry)owner).getClassNameId();
			classPK = ((AssetEntry)owner).getClassPK();
			ownerType = SocialActivityCounterConstants.TYPE_ASSET;
		}

		if (name.equals(SocialActivityCounterConstants.NAME_CONTRIBUTION)) {
			ownerType = SocialActivityCounterConstants.TYPE_CREATOR;
		}

		return
			SocialActivityCounterLocalServiceUtil.fetchLatestActivityCounter(
				groupId, classNameId, classPK, name, ownerType);
	}

	public static SocialActivityLimit getActivityLimit(
			long groupId, User user, AssetEntry assetEntry, int activityType,
			String activityCounterName)
		throws Exception {

		long classPK = assetEntry.getClassPK();

		if (activityCounterName.equals(
				SocialActivityCounterConstants.NAME_PARTICIPATION)) {

			classPK = 0;
		}

		return SocialActivityLimitLocalServiceUtil.fetchActivityLimit(
			groupId, user.getUserId(), assetEntry.getClassNameId(), classPK,
			activityType, activityCounterName);
	}

	protected static SocialActivity createActivity(
		User user, Group group, AssetEntry assetEntry, int type) {

		SocialActivity activity = new SocialActivityImpl();

		activity.setGroupId(group.getGroupId());
		activity.setCompanyId(group.getCompanyId());
		activity.setUserId(user.getUserId());
		activity.setCreateDate(System.currentTimeMillis());
		activity.setClassNameId(assetEntry.getClassNameId());
		activity.setClassPK(assetEntry.getClassPK());
		activity.setType(type);

		activity.setAssetEntry(assetEntry);
		activity.setUserUuid(user.getUuid());

		return activity;
	}

	private static final String _TEST_MODEL = "test-model";

}