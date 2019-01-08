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

package com.liferay.change.tracking.rest.internal.dto.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author Máté Thurzó
 */
@JsonRootName("configuration")
public class CTConfigurationRequestDTO {

	public CTConfigurationRequestDTO() {
	}

	public long getUserId() {
		return _userId;
	}

	public boolean isChangeTrackingEnabled() {
		return _changeTrackingEnabled;
	}

	public void setChangeTrackingEnabled(boolean changeTrackingEnabled) {
		_changeTrackingEnabled = changeTrackingEnabled;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	@JsonProperty("changeTrackingEnabled")
	private boolean _changeTrackingEnabled;

	@JsonProperty("userId")
	private long _userId;

}