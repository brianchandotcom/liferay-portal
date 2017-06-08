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
	public String getDescription() {
		return _description;
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
	public String getPortalEdition() {
		return _portalEdition;
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
	public void setDescription(String description) {
		_description = description;
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
	public void setPortalEdition(String portalEdition) {
		_portalEdition = portalEdition;
	}

	private boolean _archived;
	private int _buildNumber;
	private String _description;
	private long _installationId;
	private String _key;
	private long _lcsClusterEntryId;
	private long _lcsClusterNodeId;
	private String _location;
	private String _name;
	private String _portalEdition;

}