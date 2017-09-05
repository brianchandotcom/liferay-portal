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

import java.util.List;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public interface LCSClusterNodeService {

	public LCSClusterNode addLCSClusterNode(
		long lcsClusterEntryId, String name, String description,
		int buildNumber, long heartbeatInterval, long lastHeartbeat, String key,
		String location, int patchingToolVersion, String portalEdition,
		int processorCoresTotal, String protocolVersion);

	public LCSClusterNode addLCSClusterNode(
		long lcsClusterEntryId, String name, String description,
		int buildNumber, String key, String location, int processorCoresTotal);

	public LCSClusterNode fetchLCSClusterNode(String key);

	public List<LCSClusterNode> getLCSClusterEntryLCSClusterNodes(
		long lcsClusterEntryId);

	public List<LCSClusterNode> getLCSClusterNodes(
		int status, int start, int end);

	public void mergeStatus(String key, int status);

	public void updateBuildNumber(String key, int buildNumber);

	public void updateConfigurationModifiedDate(String key);

	public void updateStatus(String key, int status);

	public void verifyLCSClusterEntryLCSClusterNodesPropertiesDifferences(
		String key);

	public void verifyLCSClusterNodeClusterLink(String key, String siblingKeys);

}