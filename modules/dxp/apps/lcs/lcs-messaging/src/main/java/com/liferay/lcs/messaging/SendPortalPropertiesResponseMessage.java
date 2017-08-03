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

package com.liferay.lcs.messaging;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class SendPortalPropertiesResponseMessage extends ResponseMessage {

	public String getHashCode() {
		return _hashCode;
	}

	public Map<String, String> getPortalProperties() {
		return _portalProperties;
	}

	public void setHashCode(String hashCode) {
		_hashCode = hashCode;
	}

	public void setPortalProperties(Map<String, String> portalProperties) {
		_portalProperties = portalProperties;
	}

	private String _hashCode;
	private Map<String, String> _portalProperties =
		new HashMap<String, String>();

}