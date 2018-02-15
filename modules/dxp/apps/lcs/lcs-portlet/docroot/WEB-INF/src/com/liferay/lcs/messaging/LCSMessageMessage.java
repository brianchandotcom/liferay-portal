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

/**
 * @author Ivica Cardic
 */
public class LCSMessageMessage extends Message {

	public static final String KEY_CONTENT = "content";

	public static final String KEY_CORP_PROJECT_ID = "corpProjectId";

	public static final String KEY_SOURCE_MESSAGE_ID = "sourceMessageId";

	public static final String KEY_TYPE = "type";

	public boolean isDelete() {
		return _delete;
	}

	public void setDelete(boolean delete) {
		_delete = delete;
	}

	private boolean _delete;

}