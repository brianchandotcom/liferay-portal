/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.push.notifications.model.PushNotificationsDevice;

/**
 * @author Javier Gamarra
 * @author Salva Tejero
 */
public class PushNotificationsDevicePlatformComparator
	extends OrderByComparator<PushNotificationsDevice> {

	public static String ORDER_BY_ASC = "platform ASC";

	public static String ORDER_BY_DESC = "platform DESC";

	public static final String[] ORDER_BY_FIELDS = {"platform"};

	public PushNotificationsDevicePlatformComparator() {
		this(true);
	}

	public PushNotificationsDevicePlatformComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		PushNotificationsDevice pushNotificationsDevice1,
		PushNotificationsDevice pushNotificationsDevice2) {

		String platform1 = pushNotificationsDevice1.getPlatform();
		String platform2 = pushNotificationsDevice2.getPlatform();

		int value = platform1.compareToIgnoreCase(platform2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}