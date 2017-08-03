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
public interface LCSMetadata {

	public long getAvailabilityIndex();

	public int getBuildNumber();

	public String getGitTag();

	public long getLCSMetadataId();

	public String getPortalEdition();

	public int getSupportedLCSPortlet();

	public int getSupportedPatchingTool();

	public void setAvailabilityIndex(long availabilityIndex);

	public void setBuildNumber(int buildNumber);

	public void setGitTag(String gitTag);

	public void setLCSMetadataId(long lcsMetadataId);

	public void setPortalEdition(String portalEdition);

	public void setSupportedLCSPortlet(int supportedLCSPortlet);

	public void setSupportedPatchingTool(int supportedPatchingTool);

}