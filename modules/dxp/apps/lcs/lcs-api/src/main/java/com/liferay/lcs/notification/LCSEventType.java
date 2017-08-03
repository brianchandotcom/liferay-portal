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
		"a-connection-between-nodes-in-the-cluster-x-is-broken", true,
		60 * 60 * 1000, 3, 7),
	MEMBERSHIP_INVITATION_ACCEPTED(
		null, "x-joined-the-project-x", false, 0, 1, 13),
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
		null, "a-user-requested-membership-on-project-x", false, 0, 1, 11),
	NEW_PATCH_AVAILABLE(
		"new-fix-pack-is-available",
		"new-fix-pack-is-available-to-install-on-x", false, 0, 1, 1),
	NEW_PATCHING_TOOL_AVAILABLE(
		"new-patching-tool-is-available",
		"new-patching-tool-is-available-to-install-on-x", false, 0, 1, 5),
	NEW_PROJECT_MEMBER(null, "x-was-added-to-the-project-x", false, 0, 1, 14),
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
		List<LCSEventType> lcsEventTypes = new ArrayList<LCSEventType>();

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
		for (LCSEventType lcsEventType : values()) {
			if (type == lcsEventType.getType()) {
				return lcsEventType;
			}
		}

		return null;
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
	private final int _type;

}