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

package com.liferay.portal.workflow.rest.internal.model;

import com.liferay.portal.kernel.exception.PortalException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Adam Brandizzi
 */
@XmlRootElement
public class WorkflowActivityModel {

	public WorkflowActivityModel() {
		_createTime = 0;
		_message = null;
		_details = null;
	}

	public WorkflowActivityModel(
			long createTime, String message, String details)
		throws PortalException {

		_createTime = createTime;
		_message = message;
		_details = details;
	}

	@XmlElement
	public long getCreateTime() {
		return _createTime;
	}

	@XmlElement
	public String getDetails() {
		return _details;
	}

	@XmlElement
	public String getMessage() {
		return _message;
	}

	private final long _createTime;
	private final String _details;
	private final String _message;

}