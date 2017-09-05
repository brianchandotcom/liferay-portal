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
 * @author Riccardo Ferrari
 */
public class LCSMetadataImpl {

	public long getAvailabilityIndex() {
		return _availabilityIndex;
	}

	public int getBuildNumber() {
		return _buildNumber;
	}

	public String getGitTag() {
		return _gitTag;
	}

	public long getLCSMetadataId() {
		return _lcsMetadataId;
	}

	public String getPortalEdition() {
		return _portalEdition;
	}

	public int getSupportedLCSPortlet() {
		return _supportedLCSPortlet;
	}

	public int getSupportedPatchingTool() {
		return _supportedPatchingTool;
	}

	public void setAvailabilityIndex(long availabilityIndex) {
		_availabilityIndex = availabilityIndex;
	}

	public void setBuildNumber(int buildNumber) {
		_buildNumber = buildNumber;
	}

	public void setGitTag(String gitTag) {
		_gitTag = gitTag;
	}

	public void setLCSMetadataId(long lcsMetadataId) {
		_lcsMetadataId = lcsMetadataId;
	}

	public void setPortalEdition(String portalEdition) {
		_portalEdition = portalEdition;
	}

	public void setSupportedLCSPortlet(int supportedLCSPortlet) {
		_supportedLCSPortlet = supportedLCSPortlet;
	}

	public void setSupportedPatchingTool(int supportedPatchingTool) {
		_supportedPatchingTool = supportedPatchingTool;
	}

	private long _availabilityIndex;
	private int _buildNumber;
	private String _gitTag;
	private long _lcsMetadataId;
	private String _portalEdition;
	private int _supportedLCSPortlet;
	private int _supportedPatchingTool;

}