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

import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

/**
 * @author László Csontos
 */
public class ReceiverKey implements Serializable {

	/**
	 * The empty constructor is required by {@link
	 * com.liferay.portal.json.jabsorb.serializer.LiferaySerializer}. Do not use
	 * this for any other purpose.
	 */
	public ReceiverKey() {
		this(null, null);
	}

	public ReceiverKey(String jobName, String groupName) {
		_jobName = jobName;
		_groupName = groupName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ReceiverKey)) {
			return false;
		}

		ReceiverKey receiverKey = (ReceiverKey)obj;

		if (Validator.equals(_jobName, receiverKey._jobName) &&
			Validator.equals(_groupName, receiverKey._groupName)) {

			return true;
		}

		return false;
	}

	public String getGroupName() {
		return _groupName;
	}

	public String getJobName() {
		return _jobName;
	}

	@Override
	public int hashCode() {
		return _jobName.hashCode() + (_groupName.hashCode() * 11);
	}

	private final String _groupName;
	private final String _jobName;

}