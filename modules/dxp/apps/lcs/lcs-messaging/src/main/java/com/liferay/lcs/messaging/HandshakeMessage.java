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

package com.liferay.lcs.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Liferay Cloud Services Protocol handshake message. This message
 * is always sent from the LCS system to the LCS client. A successful handshake
 * results in a virtual session that lasts as long as the LCS system receives
 * heartbeat messages from the client.
 *
 * @author  Miguel Pastor
 * @author  Ivica Cardic
 * @author  Igor Beslic
 * @version 2.1.1
 * @since   LCS 0.1
 */
public class HandshakeMessage extends Message {

	public int getBuildNumber() {
		return _buildNumber;
	}

	public Map<Integer, String> getCompanyIdsWebIds() {
		return _companyIdsWebIds;
	}

	public int getHashCode() {
		return _hashCode;
	}

	public long getHeartbeatInterval() {
		return _heartbeatInterval;
	}

	public int getLCSPortletBuildNumber() {
		return _lcsPortletBuildNumber;
	}

	public int getPatchingToolVersion() {
		return _patchingToolVersion;
	}

	public String getPortalEdition() {
		return _portalEdition;
	}

	public List<Map<String, Long>> getUptimes() {
		return _uptimes;
	}

	public boolean isClusterExecutorEnabled() {
		return _clusterExecutorEnabled;
	}

	public boolean isDeregister() {
		return _deregister;
	}

	public boolean isMetricsLCSServiceEnabled() {
		return _metricsLCSServiceEnabled;
	}

	public boolean isMonitoringEnabled() {
		return _monitoringEnabled;
	}

	public boolean isPatchesLCSServiceEnabled() {
		return _patchesLCSServiceEnabled;
	}

	public boolean isPatchingToolEnabled() {
		return _patchingToolEnabled;
	}

	public boolean isPortalPropertiesLCSServiceEnabled() {
		return _portalPropertiesLCSServiceEnabled;
	}

	public boolean isServerManuallyShutdown() {
		return _serverManuallyShutdown;
	}

	public boolean isSignOff() {
		return _signOff;
	}

	public void setBuildNumber(int buildNumber) {
		_buildNumber = buildNumber;
	}

	public void setClusterExecutorEnabled(boolean clusterExecutorEnabled) {
		_clusterExecutorEnabled = clusterExecutorEnabled;
	}

	public void setCompanyIdsWebIds(Map<Integer, String> companyIdsWebIds) {
		_companyIdsWebIds = companyIdsWebIds;
	}

	public void setDeregister(boolean deregister) {
		_deregister = deregister;
	}

	public void setHashCode(int hashCode) {
		_hashCode = hashCode;
	}

	public void setHeartbeatInterval(long heartbeatInterval) {
		_heartbeatInterval = heartbeatInterval;
	}

	public void setLcsPortletBuildNumber(int lcsPortletBuildNumber) {
		_lcsPortletBuildNumber = lcsPortletBuildNumber;
	}

	public void setLCSPortletBuildNumber(int lcsPortletBuildNumber) {
		_lcsPortletBuildNumber = lcsPortletBuildNumber;
	}

	public void setMetricsLCSServiceEnabled(boolean metricsLCSServiceEnabled) {
		_metricsLCSServiceEnabled = metricsLCSServiceEnabled;
	}

	public void setMonitoringEnabled(boolean monitoringEnabled) {
		_monitoringEnabled = monitoringEnabled;
	}

	public void setPatchesLCSServiceEnabled(boolean patchesLCSServiceEnabled) {
		_patchesLCSServiceEnabled = patchesLCSServiceEnabled;
	}

	public void setPatchingToolEnabled(boolean patchingToolEnabled) {
		_patchingToolEnabled = patchingToolEnabled;
	}

	public void setPatchingToolVersion(int patchingToolVersion) {
		_patchingToolVersion = patchingToolVersion;
	}

	public void setPortalEdition(String portalEdition) {
		_portalEdition = portalEdition;
	}

	public void setPortalPropertiesLCSServiceEnabled(
		boolean portalPropertiesLCSServiceEnabled) {

		_portalPropertiesLCSServiceEnabled = portalPropertiesLCSServiceEnabled;
	}

	public void setServerManuallyShutdown(boolean serverManuallyShutdown) {
		_serverManuallyShutdown = serverManuallyShutdown;
	}

	public void setSignOff(boolean signOff) {
		_signOff = signOff;
	}

	public void setUptimes(List<Map<String, Long>> uptimes) {
		_uptimes = uptimes;
	}

	private int _buildNumber;
	private boolean _clusterExecutorEnabled;
	private Map<Integer, String> _companyIdsWebIds =
		new HashMap<Integer, String>();
	private boolean _deregister;
	private int _hashCode;
	private long _heartbeatInterval;
	private int _lcsPortletBuildNumber;
	private boolean _metricsLCSServiceEnabled;
	private boolean _monitoringEnabled;
	private boolean _patchesLCSServiceEnabled;
	private boolean _patchingToolEnabled;
	private int _patchingToolVersion;
	private String _portalEdition;
	private boolean _portalPropertiesLCSServiceEnabled;
	private boolean _serverManuallyShutdown;
	private boolean _signOff;
	private List<Map<String, Long>> _uptimes =
		new ArrayList<Map<String, Long>>();

}