/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.contacts.model.constants;

import com.liferay.petra.string.StringPool;

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

	private static final Map<String, Integer> _cardTypes =
		new HashMap<String, Integer>() {
			{
				put("activityHistory", TYPE_ACTIVITY_HISTORY);
				put("associatedSegments", TYPE_ASSOCIATED_SEGMENTS);
				put("closedWon", TYPE_CLOSED_WON);
				put("conversionHealth", TYPE_CONVERSION_HEALTH);
				put("coworkers", TYPE_COWORKERS);
				put("interests", TYPE_INTEREST);
				put("lifetimeValue", TYPE_LIFETIME_VALUE);
				put("netSales", TYPE_NET_SALES);
				put("profile", TYPE_PROFILE);
				put("recentActivities", TYPE_RECENT_ACTIVITIES);
				put("segmentDistribution", TYPE_SEGMENT_DISTRIBUTION);
				put("segmentMembership", TYPE_SEGMENT_MEMBERSHIP);
				put("similar", TYPE_SIMILAR);
				put("touchpointAttribution", TYPE_TOUCHPOINT_ATTRIBUTION);
			}
		};
	private static final Map<String, Object> _constants = new HashMap<>();

	static {
		Map<String, String> filterTypes = new HashMap<>();

		filterTypes.put("all", SETTINGS_FILTER_ALL);

		Map<String, Integer> graphTypes = new HashMap<>();

		graphTypes.put("bar", SETTINGS_GRAPH_TYPE_BAR);
		graphTypes.put("line", SETTINGS_GRAPH_TYPE_LINE);
		graphTypes.put("pie", SETTINGS_GRAPH_TYPE_PIE);

		Map<String, Integer> profileCardLayoutTypes = new HashMap<>();

		profileCardLayoutTypes.put(
			"horizontal", SETTINGS_PROFILE_CARD_LAYOUT_TYPE_HORIZONTAL);
		profileCardLayoutTypes.put(
			"noAvatar", SETTINGS_PROFILE_CARD_LAYOUT_TYPE_NO_AVATAR);
		profileCardLayoutTypes.put(
			"vertical", SETTINGS_PROFILE_CARD_LAYOUT_TYPE_VERTICAL);

		Map<String, Integer> segmentsMembershipCardOrders = new HashMap<>();

		segmentsMembershipCardOrders.put(
			"alphabetical", SETTINGS_SEGMENTS_MEMBERSHIP_CARD_ORDER_BY_NAME);
		segmentsMembershipCardOrders.put(
			"numberOfMembers",
			SETTINGS_SEGMENTS_MEMBERSHIP_CARD_ORDER_BY_COUNT);

		Map<String, Integer> units = new HashMap<>();

		units.put("count", SETTINGS_UNIT_COUNT);
		units.put("percentage", SETTINGS_UNIT_PERCENTAGE);

		_constants.put("cardTypes", _cardTypes);
		_constants.put("fiterTypes", filterTypes);
		_constants.put("graphTypes", graphTypes);
		_constants.put("profileCardLayoutTypes", profileCardLayoutTypes);
		_constants.put(
			"segmentsMembershipCardOrders", segmentsMembershipCardOrders);
		_constants.put("units", units);
	}

}