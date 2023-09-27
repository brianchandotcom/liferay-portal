/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.contacts.model.constants;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shinn Lok
 */
public class ContactsCardTemplateConstants {

	public static final String SETTINGS_FILTER_ALL = StringPool.BLANK;

	public static final int SETTINGS_GRAPH_TYPE_BAR = 0;

	public static final int SETTINGS_GRAPH_TYPE_LINE = 1;

	public static final int SETTINGS_GRAPH_TYPE_PIE = 2;

	public static final int SETTINGS_PROFILE_CARD_LAYOUT_TYPE_HORIZONTAL = 0;

	public static final int SETTINGS_PROFILE_CARD_LAYOUT_TYPE_NO_AVATAR = 1;

	public static final int SETTINGS_PROFILE_CARD_LAYOUT_TYPE_VERTICAL = 2;

	public static final int SETTINGS_SEGMENTS_MEMBERSHIP_CARD_ORDER_BY_COUNT =
		0;

	public static final int SETTINGS_SEGMENTS_MEMBERSHIP_CARD_ORDER_BY_NAME = 1;

	public static final int SETTINGS_UNIT_COUNT = 0;

	public static final int SETTINGS_UNIT_PERCENTAGE = 1;

	public static final int TYPE_ACTIVITY_HISTORY = 6;

	public static final int TYPE_ASSOCIATED_SEGMENTS = 7;

	public static final int TYPE_CLOSED_WON = 8;

	public static final int TYPE_CONVERSION_HEALTH = 9;

	public static final int TYPE_COWORKERS = 10;

	public static final int TYPE_EMPLOYEES = 11;

	public static final int TYPE_INTEREST = 13;

	public static final int TYPE_LIFETIME_VALUE = 14;

	public static final int TYPE_NET_SALES = 15;

	public static final int TYPE_PROFILE = 1;

	public static final int TYPE_RECENT_ACTIVITIES = 16;

	public static final int TYPE_SEGMENT_DISTRIBUTION = 5;

	public static final int TYPE_SEGMENT_MEMBERSHIP = 4;

	public static final int TYPE_SIMILAR = 17;

	public static final int TYPE_TOUCHPOINT_ATTRIBUTION = 18;

	public static Map<String, Integer> getCardTypes() {
		return _cardTypes;
	}

	public static Map<String, Object> getConstants() {
		return _constants;
	}

	private static final Map<String, Integer> _cardTypes = HashMapBuilder.put(
		"activityHistory", TYPE_ACTIVITY_HISTORY
	).put(
		"associatedSegments", TYPE_ASSOCIATED_SEGMENTS
	).put(
		"closedWon", TYPE_CLOSED_WON
	).put(
		"conversionHealth", TYPE_CONVERSION_HEALTH
	).put(
		"coworkers", TYPE_COWORKERS
	).put(
		"interests", TYPE_INTEREST
	).put(
		"lifetimeValue", TYPE_LIFETIME_VALUE
	).put(
		"netSales", TYPE_NET_SALES
	).put(
		"profile", TYPE_PROFILE
	).put(
		"recentActivities", TYPE_RECENT_ACTIVITIES
	).put(
		"segmentDistribution", TYPE_SEGMENT_DISTRIBUTION
	).put(
		"segmentMembership", TYPE_SEGMENT_MEMBERSHIP
	).put(
		"similar", TYPE_SIMILAR
	).put(
		"touchpointAttribution", TYPE_TOUCHPOINT_ATTRIBUTION
	).build();
	private static final Map<String, Object> _constants = new HashMap<>();

	static {
		_constants.put("cardTypes", _cardTypes);
		_constants.put(
			"fiterTypes",
			HashMapBuilder.<String, String>put(
				"all", SETTINGS_FILTER_ALL
			).build());
		_constants.put(
			"graphTypes",
			HashMapBuilder.<String, Integer>put(
				"bar", SETTINGS_GRAPH_TYPE_BAR
			).put(
				"line", SETTINGS_GRAPH_TYPE_LINE
			).put(
				"pie", SETTINGS_GRAPH_TYPE_PIE
			).build());
		_constants.put(
			"profileCardLayoutTypes",
			HashMapBuilder.<String, Integer>put(
				"horizontal", SETTINGS_PROFILE_CARD_LAYOUT_TYPE_HORIZONTAL
			).put(
				"noAvatar", SETTINGS_PROFILE_CARD_LAYOUT_TYPE_NO_AVATAR
			).put(
				"vertical", SETTINGS_PROFILE_CARD_LAYOUT_TYPE_VERTICAL
			).build());
		_constants.put(
			"segmentsMembershipCardOrders",
			HashMapBuilder.<String, Integer>put(
				"alphabetical", SETTINGS_SEGMENTS_MEMBERSHIP_CARD_ORDER_BY_NAME
			).put(
				"numberOfMembers",
				SETTINGS_SEGMENTS_MEMBERSHIP_CARD_ORDER_BY_COUNT
			).build());
		_constants.put(
			"units",
			HashMapBuilder.<String, Integer>put(
				"count", SETTINGS_UNIT_COUNT
			).put(
				"percentage", SETTINGS_UNIT_PERCENTAGE
			).build());
	}

}