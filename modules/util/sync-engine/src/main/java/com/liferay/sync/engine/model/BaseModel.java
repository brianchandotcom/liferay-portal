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

package com.liferay.sync.engine.model;

import com.j256.ormlite.field.DatabaseField;

import com.liferay.sync.engine.util.JSONUtil;

import java.io.IOException;

/**
 * @author Shinn Lok
 */
public class BaseModel {

	public static final int UI_EVENT_NONE = 0;

	public int getUiEvent() {
		return uiEvent;
	}

	public void setUiEvent(int uiEvent) {
		this.uiEvent = uiEvent;
	}

	@Override
	public String toString() {
		try {
			return JSONUtil.writeValueAsString(this);
		}
		catch (IOException ioe) {
			return super.toString();
		}
	}

	@DatabaseField(index = true, useGetSet = true)
	protected int uiEvent;

}