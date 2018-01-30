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

package com.liferay.portal.kernel.scheduler.messaging;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

/**
 * @author Tina Tian
 */
public class SchedulerResponse implements Serializable {

	public String getDescription() {
		return _description;
	}

	public String getDestinationName() {
		return _destinationName;
	}

	public String getGroupName() {
		if (_trigger != null) {
			return _trigger.getGroupName();
		}

		return _groupName;
	}

	public String getJobName() {
		if (_trigger != null) {
			return _trigger.getJobName();
		}

		return _jobName;
	}

	public Message getMessage() {
		return _message;
	}

	public StorageType getStorageType() {
		return _storageType;
	}

	public Trigger getTrigger() {
		return _trigger;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDestinationName(String destinationName) {
		_destinationName = destinationName;
	}

	public void setGroupName(String groupName) {
		_groupName = groupName;
	}

	public void setJobName(String jobName) {
		_jobName = jobName;
	}

	public void setMessage(Message message) {
		_message = message;
	}

	public void setStorageType(StorageType storageType) {
		_storageType = storageType;
	}

	public void setTrigger(Trigger trigger) {
		_trigger = trigger;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{groupName=");
		sb.append(getGroupName());
		sb.append(", jobName=");
		sb.append(getJobName());
		sb.append(", storageType=");
		sb.append(getStorageType());
		sb.append("}");

		return sb.toString();
	}

	private String _description;
	private String _destinationName;
	private String _groupName;
	private String _jobName;
	private Message _message;
	private StorageType _storageType;
	private Trigger _trigger;

}