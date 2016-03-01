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

package com.liferay.exportimport.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Raymond Augé
 */
public class RemoteExportException extends PortalException {

	public static final int BAD_CONNECTION = 1;

	public static final int INVALID_GROUP = 2;

	public static final int NO_GROUP = 3;

	public static final int NO_PERMISSIONS = 5;

	public static final int SAME_GROUP = 4;

	public RemoteExportException(int type) {
		_type = type;
	}

	public RemoteExportException(int type, String msg) {
		super(msg);

		_type = type;
	}

	public long getGroupId() {
		return _groupId;
	}

	public int getType() {
		return _type;
	}

	public String getURL() {
		return _url;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setURL(String url) {
		_url = url;
	}

	private long _groupId;
	private final int _type;
	private String _url;

}