/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.activity.service.test.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.social.kernel.model.SocialActivityCounter;
import com.liferay.social.kernel.model.SocialActivityCounterConstants;
import com.liferay.social.kernel.model.SocialActivityLimit;
import com.liferay.social.kernel.service.SocialActivityCounterLocalServiceUtil;
import com.liferay.social.kernel.service.SocialActivityLimitLocalServiceUtil;
import com.liferay.social.kernel.service.SocialActivityLocalServiceUtil;

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

	public static AssetEntry addAssetEntry(User user, Group group)
		throws Exception {

		return AssetEntryLocalServiceUtil.updateEntry(
			user.getUserId(), group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLong(), null, null);
	}

	public static AssetEntry addAssetEntry(
			User user, Group group, AssetEntry assetEntry)
		throws Exception {

		if (assetEntry != null) {
			AssetEntryLocalServiceUtil.deleteEntry(assetEntry);
		}

		return AssetEntryLocalServiceUtil.updateEntry(
			user.getUserId(), group.getGroupId(), _TEST_MODEL, 1, null, null);
	}

	public static String createExtraDataJSON(String key, String value) {
		JSONObject extraDataJSONObject = JSONUtil.put(key, value);

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

			User user = (User)owner;

			classPK = user.getUserId();
		}
		else if (owner instanceof AssetEntry) {
			AssetEntry assetEntry = (AssetEntry)owner;

			classNameId = assetEntry.getClassNameId();
			classPK = assetEntry.getClassPK();

			ownerType = SocialActivityCounterConstants.TYPE_ASSET;
		}

		if (name.equals(SocialActivityCounterConstants.NAME_CONTRIBUTION)) {
			ownerType = SocialActivityCounterConstants.TYPE_CREATOR;
		}

		return SocialActivityCounterLocalServiceUtil.fetchLatestActivityCounter(
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

	private static final String _TEST_MODEL = "test-model";

}