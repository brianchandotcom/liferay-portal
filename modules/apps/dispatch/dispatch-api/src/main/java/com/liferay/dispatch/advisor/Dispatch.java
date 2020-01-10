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

package com.liferay.dispatch.advisor;

import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Igor Beslic
 */
public class Dispatch {

	public Dispatch(
		long dispatchTriggerId, String groupName, String jobName,
		String storageTypeName) {

		_dispatchTriggerId = dispatchTriggerId;
		_groupName = groupName;
		_jobName = jobName;
		_storageTypeName = storageTypeName;
	}

	public long getDispatchTriggerId() {
		return _dispatchTriggerId;
	}

	public String getGroupName() {
		return _groupName;
	}

	public String getJobName() {
		return _jobName;
	}

	public String getStorageTypeName() {
		return _storageTypeName;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{dispatchTriggerId=");
		sb.append(_dispatchTriggerId);
		sb.append(", groupName=");
		sb.append(_groupName);
		sb.append(", jobName=");
		sb.append(_jobName);
		sb.append(", storageTypeName=");
		sb.append(_storageTypeName);
		sb.append("}");

		return sb.toString();
	}

	private final long _dispatchTriggerId;
	private final String _groupName;
	private final String _jobName;
	private final String _storageTypeName;

}