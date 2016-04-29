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
public class LCSClusterNodeServiceUtil {

	public static LCSClusterNode addLCSClusterNode(
		long lcsClusterEntryId, String name, String description,
		int buildNumber, String key, String location, int processorCoresTotal) {

		return _lcsClusterNodeService.addLCSClusterNode(
			lcsClusterEntryId, name, description, buildNumber, key, location,
			processorCoresTotal);
	}

	public static LCSClusterNode addLCSClusterNode(
		String siblingKey, String name, String description, String key,
		String location, int processorCoresTotal) {

		return _lcsClusterNodeService.addLCSClusterNode(
			siblingKey, name, description, key, location, processorCoresTotal);
	}

	public static LCSClusterNode fetchLCSClusterNode(String key) {
		return _lcsClusterNodeService.fetchLCSClusterNode(key);
	}

	public void setLCSClusterNodeService(
		LCSClusterNodeService lcsClusterNodeService) {

		_lcsClusterNodeService = lcsClusterNodeService;
	}

	private static LCSClusterNodeService _lcsClusterNodeService;

}