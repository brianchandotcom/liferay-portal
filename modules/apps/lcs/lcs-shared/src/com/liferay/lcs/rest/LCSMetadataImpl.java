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
public class LCSMetadataImpl implements LCSMetadata {

	@Override
	public long getAvailabilityIndex() {
		return _availabilityIndex;
	}

	@Override
	public int getBuildNumber() {
		return _buildNumber;
	}

	@Override
	public String getGitTag() {
		return _gitTag;
	}

	@Override
	public long getLCSMetadataId() {
		return _lcsMetadataId;
	}

	@Override
	public String getPortalEdition() {
		return _portalEdition;
	}

	@Override
	public int getSupportedLCSPortlet() {
		return _supportedLCSPortlet;
	}

	@Override
	public int getSupportedPatchingTool() {
		return _supportedPatchingTool;
	}

	@Override
	public void setAvailabilityIndex(long availabilityIndex) {
		_availabilityIndex = availabilityIndex;
	}

	@Override
	public void setBuildNumber(int buildNumber) {
		_buildNumber = buildNumber;
	}

	@Override
	public void setGitTag(String gitTag) {
		_gitTag = gitTag;
	}

	@Override
	public void setLCSMetadataId(long lcsMetadataId) {
		_lcsMetadataId = lcsMetadataId;
	}

	@Override
	public void setPortalEdition(String portalEdition) {
		_portalEdition = portalEdition;
	}

	@Override
	public void setSupportedLCSPortlet(int supportedLCSPortlet) {
		_supportedLCSPortlet = supportedLCSPortlet;
	}

	@Override
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