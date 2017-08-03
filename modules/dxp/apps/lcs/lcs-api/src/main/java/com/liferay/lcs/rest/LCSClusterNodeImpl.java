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

package com.liferay.lcs.rest;

import java.util.Date;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class LCSClusterNodeImpl implements LCSClusterNode {

	@Override
	public boolean getArchived() {
		return _archived;
	}

	@Override
	public int getBuildNumber() {
		return _buildNumber;
	}

	@Override
	public Date getConfigurationModifiedDate() {
		return _configurationModifiedDate;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public long getHeartbeatInterval() {
		return _heartbeatInterval;
	}

	@Override
	public long getInstallationId() {
		return _installationId;
	}

	@Override
	public String getKey() {
		return _key;
	}

	@Override
	public long getLastHeartbeat() {
		return _lastHeartbeat;
	}

	@Override
	public long getLcsClusterEntryId() {
		return _lcsClusterEntryId;
	}

	@Override
	public long getLcsClusterNodeId() {
		return _lcsClusterNodeId;
	}

	@Override
	public String getLocation() {
		return _location;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int getPatchingToolVersion() {
		return _patchingToolVersion;
	}

	@Override
	public String getPortalEdition() {
		return _portalEdition;
	}

	@Override
	public String getProtocolVersion() {
		return _protocolVersion;
	}

	@Override
	public int getStatus() {
		return _status;
	}

	@Override
	public boolean isArchived() {
		return _archived;
	}

	@Override
	public void setArchived(boolean archived) {
		_archived = archived;
	}

	@Override
	public void setBuildNumber(int buildNumber) {
		_buildNumber = buildNumber;
	}

	@Override
	public void setConfigurationModifiedDate(Date configurationModifiedDate) {
		_configurationModifiedDate = configurationModifiedDate;
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@Override
	public void setHeartbeatInterval(long heartbeatInterval) {
		_heartbeatInterval = heartbeatInterval;
	}

	@Override
	public void setInstallationId(long installationId) {
		_installationId = installationId;
	}

	@Override
	public void setKey(String key) {
		_key = key;
	}

	@Override
	public void setLastHeartbeat(long lastHeartbeat) {
		_lastHeartbeat = lastHeartbeat;
	}

	@Override
	public void setLcsClusterEntryId(long lcsClusterEntryId) {
		_lcsClusterEntryId = lcsClusterEntryId;
	}

	@Override
	public void setLcsClusterNodeId(long lcsClusterNodeId) {
		_lcsClusterNodeId = lcsClusterNodeId;
	}

	@Override
	public void setLocation(String location) {
		_location = location;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setPatchingToolVersion(int patchingToolVersion) {
		_patchingToolVersion = patchingToolVersion;
	}

	@Override
	public void setPortalEdition(String portalEdition) {
		_portalEdition = portalEdition;
	}

	@Override
	public void setProtocolVersion(String protocolVersion) {
		_protocolVersion = protocolVersion;
	}

	@Override
	public void setStatus(int status) {
		_status = status;
	}

	private boolean _archived;
	private int _buildNumber;
	private Date _configurationModifiedDate;
	private String _description;
	private long _heartbeatInterval;
	private long _installationId;
	private String _key;
	private long _lastHeartbeat;
	private long _lcsClusterEntryId;
	private long _lcsClusterNodeId;
	private String _location;
	private String _name;
	private int _patchingToolVersion;
	private String _portalEdition;
	private String _protocolVersion;
	private int _status;

}