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

package com.liferay.lcs.notification;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Beslic
 * @author Marko Cikos
 */
public enum LCSEventType {

	LCS_CLUSTER_NODE_CLUSTER_LINK_FAILED(
		"a-connection-between-nodes-in-a-cluster-is-broken",
		"a-connection-between-nodes-in-the-cluster-x-is-broken", true, 3,
		60 * 60 * 1000, 7),
	MEMBERSHIP_REQUEST_ACCEPTED(
		null, "your-request-for-membership-on-the-project-x-was-accepted",
		false, 0, 1, 12),
	MONITORING_UNAVAILABLE(
		"monitoring-is-unavailable",
		"monitoring-is-unavailable-on-the-server-x", false, 0, 2, 0),
	NEW_LCS_PORTLET_AVAILABLE(
		"new-liferay-connected-services-client-is-available",
		"new-liferay-connected-services-client-is-available-to-install-on-x",
		false, 0, 1, 4),
	NEW_LCS_PROJECT_AVAILABLE(
		"new-project-is-available", "new-project-x-is-available", false, 0, 1,
		9),
	NEW_MEMBERSHIP_INVITATION(null, null, false, 0, 1, 10),
	NEW_MEMBERSHIP_REQUEST(
		null, "a-user-requested-membership-on-the-project-x", false, 0, 1, 11),
	NEW_PATCH_AVAILABLE(
		"new-fix-pack-is-available",
		"new-fix-pack-is-available-to-install-on-x", false, 0, 1, 1),
	NEW_PATCHING_TOOL_AVAILABLE(
		"new-patching-tool-is-available",
		"new-patching-tool-is-available-to-install-on-x", false, 0, 1,
		5),
	OSB_SUBSCRIPTION_STATUS_RECEIVED(null, null, false, 0, 2, 8),
	PATCHING_TOOL_UNAVAILABLE(
		"the-patching-tool-is-unavailable",
		"the-patching-tool-is-unavailable-on-the-server-x", false, 0, 2, 2),
	SERVER_MANUALLY_SHUTDOWN(
		"the-server-is-manually-shut-down",
		"the-server-x-was-manually-shut-down", false, 0, 2, 6),
	SERVER_UNEXPECTEDLY_SHUTDOWN(
		"the-server-unexpectedly-shut-down",
		"the-server-x-unexpectedly-shut-down", false, 0, 3, 3);

	public static List<LCSEventType> getSupported() {
		List<LCSEventType> lcsEventTypes = new ArrayList<>();

		lcsEventTypes.add(LCS_CLUSTER_NODE_CLUSTER_LINK_FAILED);
		lcsEventTypes.add(MONITORING_UNAVAILABLE);
		lcsEventTypes.add(NEW_LCS_PORTLET_AVAILABLE);
		lcsEventTypes.add(NEW_PATCH_AVAILABLE);
		lcsEventTypes.add(NEW_PATCHING_TOOL_AVAILABLE);
		lcsEventTypes.add(PATCHING_TOOL_UNAVAILABLE);
		lcsEventTypes.add(SERVER_MANUALLY_SHUTDOWN);
		lcsEventTypes.add(SERVER_UNEXPECTEDLY_SHUTDOWN);

		return lcsEventTypes;
	}

	public static LCSEventType valueOf(int type) {
		if (type == 0) {
			return MONITORING_UNAVAILABLE;
		}
		else if (type == 1) {
			return NEW_PATCH_AVAILABLE;
		}
		else if (type == 2) {
			return PATCHING_TOOL_UNAVAILABLE;
		}
		else if (type == 3) {
			return SERVER_UNEXPECTEDLY_SHUTDOWN;
		}
		else if (type == 4) {
			return NEW_LCS_PORTLET_AVAILABLE;
		}
		else if (type == 5) {
			return NEW_PATCHING_TOOL_AVAILABLE;
		}
		else if (type == 6) {
			return SERVER_MANUALLY_SHUTDOWN;
		}
		else if (type == 7) {
			return LCS_CLUSTER_NODE_CLUSTER_LINK_FAILED;
		}
		else if (type == 8) {
			return OSB_SUBSCRIPTION_STATUS_RECEIVED;
		}
		else if (type == 9) {
			return NEW_LCS_PROJECT_AVAILABLE;
		}
		else if (type == 10) {
			return NEW_MEMBERSHIP_INVITATION;
		}
		else if (type == 11) {
			return NEW_MEMBERSHIP_REQUEST;
		}
		else if (type == 12) {
			return MEMBERSHIP_REQUEST_ACCEPTED;
		}
		else {
			return null;
		}
	}

	public String getLabel() {
		return _label;
	}

	public String getMessage() {
		return _message;
	}

	public int getMinimumSupportedLCSPortletVersion() {
		if (_type == 6) {
			return 150;
		}

		return 10;
	}

	public long getRepeatPeriod() {
		return _repeatPeriod;
	}

	public int getSeverityLevel() {
		return _severityLevel;
	}

	public int getType() {
		return _type;
	}

	public boolean isEnterpriseSubscriptionRequired() {
		if ((_type == 7) || (_type == 8)) {
			return true;
		}

		return false;
	}

	public boolean isRepeatable() {
		return _repeatable;
	}

	private LCSEventType(
		String label, String message, boolean repeatable, long repeatPeriod,
		int severityLevel, int type) {

		_label = label;
		_message = message;
		_repeatable = repeatable;
		_repeatPeriod = repeatPeriod;
		_severityLevel = severityLevel;
		_type = type;
	}

	private final String _label;
	private final String _message;
	private final boolean _repeatable;
	private final long _repeatPeriod;
	private final int _severityLevel;
	private int _type;

}