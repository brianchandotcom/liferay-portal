/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Raymond Augé
 */
public class RemoteExportException extends PortalException {

	public static final int BAD_CONNECTION = 1;

	public static final int INVALID_GROUP = 2;

	public static final int INVALID_STATE = 6;

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