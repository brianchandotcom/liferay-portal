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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Adam Brandizzi
 */
@XmlRootElement
public class WorkflowUserModel {

	public WorkflowUserModel() {
		_name = null;
		_portraitURL = null;
		_userId = 0;
	}

	public WorkflowUserModel(User user) throws PortalException {
		_name = user.getFullName();
		_portraitURL = UserConstants.getPortraitURL(
			PortalUtil.getPathImage(), user.isMale(), user.getPortraitId(),
			user.getUserUuid());
		_userId = user.getUserId();
	}

	@XmlElement
	public String getName() {
		return _name;
	}

	@XmlElement
	public String getPortraitURL() {
		return _portraitURL;
	}

	@XmlElement(name = "id")
	public long getUserId() {
		return _userId;
	}

	private final String _name;
	private final String _portraitURL;
	private final long _userId;

}