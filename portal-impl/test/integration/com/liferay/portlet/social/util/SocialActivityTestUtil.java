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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
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

/**
 * @author Zsolt Berentey
 */
public class SocialActivityTestUtil {

	public static SocialActivity addActivity(
			Group group, User user, AssetEntry assetEntry, int type)
		throws PortalException, SystemException {

		SocialActivity activity = new SocialActivityImpl();

		activity.setAssetEntry(assetEntry);
		activity.setClassNameId(assetEntry.getClassNameId());
		activity.setClassPK(assetEntry.getClassPK());
		activity.setCompanyId(group.getCompanyId());
		activity.setGroupId(group.getGroupId());
		activity.setType(type);
		activity.setUserId(user.getUserId());
		activity.setUserUuid(user.getUuid());

		return activity;
	}

	public static AssetEntry addAsset(
			Group group, User creatorUser, AssetEntry assetEntry)
		throws Exception {

		if (assetEntry != null) {
			AssetEntryLocalServiceUtil.deleteEntry(assetEntry);
		}

		return AssetEntryLocalServiceUtil.updateEntry(
			creatorUser.getUserId(), group.getGroupId(), _TEST_MODEL, 1, null,
			null);
	}

	public static SocialActivityCounter getActivityCounter(
			long groupId, String name, Object owner)
		throws Exception {

		long classNameId = 0;
		long classPk = 0;
		int ownerType = SocialActivityCounterConstants.TYPE_ACTOR;

		if (owner instanceof User) {
			classNameId = PortalUtil.getClassNameId(User.class.getName());
			classPk = ((User)owner).getUserId();
		}
		else if (owner instanceof AssetEntry) {
			classNameId = ((AssetEntry)owner).getClassNameId();
			classPk = ((AssetEntry)owner).getClassPK();
			ownerType = SocialActivityCounterConstants.TYPE_ASSET;
		}

		if (name.equals(SocialActivityCounterConstants.NAME_CONTRIBUTION)) {
			ownerType = SocialActivityCounterConstants.TYPE_CREATOR;
		}

		return
			SocialActivityCounterLocalServiceUtil.fetchLatestActivityCounter(
				groupId, classNameId, classPk, name, ownerType);
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

	private static final String _TEST_MODEL = "test-model";

}