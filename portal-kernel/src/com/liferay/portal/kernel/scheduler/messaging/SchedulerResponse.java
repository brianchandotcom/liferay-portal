/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.scheduler.messaging;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;

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
		return StringBundler.concat(
			"{groupName=", getGroupName(), ", jobName=", getJobName(),
			", storageType=", getStorageType(), "}");
	}

	private String _description;
	private String _destinationName;
	private String _groupName;
	private String _jobName;
	private Message _message;
	private StorageType _storageType;
	private Trigger _trigger;

}