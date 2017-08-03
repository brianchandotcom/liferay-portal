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

package com.liferay.lcs.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Beslic
 * @author Marko Cikos
 */
public enum LCSClusterNodeStatus {

	ACTIVE("online", "offline", 0x01),
	DATA_INITIALIZED("data-initialized", "data-not-initialized", 0x02),
	HEARTBEAT_DELAYED("heartbeat-delayed", "heartbeat-not-delayed", 0x08),
	LCS_CLUSTER_NODE_CLUSTER_LINK_HEALTHY(
		"lcs-cluster-node-cluster-link-healthy",
		"lcs-cluster-node-cluster-link-broken", 0x200),
	METRICS_DATA_INITIALIZED(
		"metrics-data-initialized", "metrics-data-not-initialized", 0x04),
	METRICS_LCS_SERVICE_ENABLED(
		"metrics-lcs-service-enabled", "metrics-lcs-service-disabled", 0x40),
	MONITORING_ENABLED("monitoring-enabled", "monitoring-disabled", 0x10),
	PATCHES_LCS_SERVICE_ENABLED(
		"patches-lcs-service-enabled", "patches-lcs-service-disabled", 0x80),
	PATCHING_TOOL_ENABLED(
		"patching-tool-enabled", "patching-tool-disabled", 0x20),
	PORTAL_PROPERTIES_LCS_SERVICE_ENABLED(
		"portal-properties-lcs-service-enabled",
		"portal-properties-lcs-service-disabled", 0x100),
	SERVER_MANUALLY_SHUT_DOWN(
		"the-server-is-manually-shut-down", "the-server-unexpectedly-shut-down",
		0x400);

	public static LCSClusterNodeStatus[] getLCSClusterNodeStatuses(int status) {
		List<LCSClusterNodeStatus> lcsClusterNodeStatusesList =
			new ArrayList<LCSClusterNodeStatus>();

		for (LCSClusterNodeStatus lcsClusterNodeStatus : values()) {
			if (lcsClusterNodeStatus.hasStatus(status)) {
				lcsClusterNodeStatusesList.add(lcsClusterNodeStatus);
			}
		}

		LCSClusterNodeStatus[] lcsClusterNodeStatuses =
			new LCSClusterNodeStatus[lcsClusterNodeStatusesList.size()];

		for (int i = 0; i < lcsClusterNodeStatusesList.size(); i++) {
			lcsClusterNodeStatuses[i] = lcsClusterNodeStatusesList.get(i);
		}

		return lcsClusterNodeStatuses;
	}

	public static Map<LCSClusterNodeStatus, Object[]> getObjectArrays(
		int status) {

		Map<LCSClusterNodeStatus, Object[]> lcsClusterNodeStatusObjectArrays =
			new LinkedHashMap<LCSClusterNodeStatus, Object[]>();

		for (LCSClusterNodeStatus lcsClusterNodeStatus : values()) {
			Object[] objectArray = new Object[3];

			if (lcsClusterNodeStatus.hasStatus(status)) {
				objectArray[0] = lcsClusterNodeStatus.getLabel();
				objectArray[1] = true;
			}
			else {
				objectArray[0] = lcsClusterNodeStatus.getOppositeLabel();
				objectArray[1] = false;
			}

			if (HEARTBEAT_DELAYED.hasStatus(lcsClusterNodeStatus.getStatus())) {
				objectArray[2] = false;
			}
			else {
				objectArray[2] = true;
			}

			lcsClusterNodeStatusObjectArrays.put(
				lcsClusterNodeStatus, objectArray);
		}

		return lcsClusterNodeStatusObjectArrays;
	}

	/**
	 * Returns <code>true</code> if the portal instance is active and running.
	 *
	 * @param  status the portal instance's status
	 * @return <code>true</code> if the portal instance is active and running;
	 *         <code>false</code> otherwise
	 * @since  LCS 0.1
	 */
	public static boolean isActive(int status) {
		if ((status & ACTIVE._status) == ACTIVE._status) {
			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the latest portal properties and
	 * installation environment data are available.
	 *
	 * <p>
	 * After the portal instance connects to LCS, this method can be used to
	 * check whether the portal properties and installation environment data
	 * have been refreshed. If the second bit of the <code>status</code> integer
	 * is <code>1</code> (i.e., <code>00000010</code>), then the latest data is
	 * available.
	 * </p>
	 *
	 * @param  status the portal instance's status
	 * @return <code>true</code> if the latest portal properties and
	 *         installation environment data are available; <code>false</code>
	 *         otherwise
	 * @since  LCS 0.1
	 */
	public static boolean isDataInitalized(int status) {
		if ((status & DATA_INITIALIZED._status) == DATA_INITIALIZED._status) {
			return true;
		}

		return false;
	}

	public static boolean isHeartbeatDelayed(int status) {
		if ((status & HEARTBEAT_DELAYED._status) == HEARTBEAT_DELAYED._status) {
			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the portal instance is inactive.
	 *
	 * @param  status the portal instance's status
	 * @return <code>true</code> if the portal instance is inactive;
	 *         <code>false</code> otherwise
	 * @since  LCS 0.1
	 */
	public static boolean isInactive(int status) {
		if ((status & ACTIVE._status) != ACTIVE._status) {
			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the portal instance broadcasts metrics data.
	 *
	 * <p>
	 * After the portal instance connects to LCS, this method can be used to
	 * check if metrics data is sent to LCS. If the third bit of the
	 * <code>status</code> integer is <code>1</code> (i.e.,
	 * <code>00000100</code>), then the portal instance is broadcasting metrics
	 * data.
	 * </p>
	 *
	 * @param  status the portal instance's status
	 * @return <code>true</code> if the portal instance broadcasts metrics data;
	 *         <code>false</code> otherwise
	 * @since  LCS 0.1
	 */
	public static boolean isMetricsDataInitalized(int status) {
		if ((status & METRICS_DATA_INITIALIZED._status) ==
				METRICS_DATA_INITIALIZED._status) {

			return true;
		}

		return false;
	}

	public static int resetHeartbeatDelayed(int status) {
		return status & _HEARTBEAT_DELAYED_RESET;
	}

	/**
	 * Returns the label language key to associate with the cluster node status.
	 *
	 * @param  status the cluster node status
	 * @return the label language key to associate with the cluster node status
	 * @since  LCS 0.1
	 */
	public static String[] toLabels(int status) {
		LCSClusterNodeStatus[] lcsClusterNodeStatuses =
			getLCSClusterNodeStatuses(status);

		String[] labels = new String[lcsClusterNodeStatuses.length];

		for (int i = 0; i < labels.length; i++) {
			labels[i] = lcsClusterNodeStatuses[i]._label;
		}

		return labels;
	}

	public String getLabel() {
		return _label;
	}

	public String getOppositeLabel() {
		return _oppositeLabel;
	}

	public int getStatus() {
		return _status;
	}

	public boolean hasStatus(int status) {
		if ((_status & status) == _status) {
			return true;
		}

		return false;
	}

	public int merge(int status) {
		return _status | status;
	}

	public int reset(int status) {
		return ~_status & status;
	}

	private LCSClusterNodeStatus(
		String label, String oppositeLabel, int status) {

		_label = label;
		_oppositeLabel = oppositeLabel;
		_status = status;
	}

	private static final int _HEARTBEAT_DELAYED_RESET = 0xfffffff7;

	private final String _label;
	private final String _oppositeLabel;
	private final int _status;

}