/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Raymond Augé
 */
public class RemoteOptionsException extends PortalException {

	public static final int REMOTE_ADDRESS = 1;

	public static final int REMOTE_GROUP_ID = 3;

	public static final int REMOTE_PATH_CONTEXT = 4;

	public static final int REMOTE_PORT = 2;

	public RemoteOptionsException(int type) {
		_type = type;
	}

	public String getRemoteAddress() {
		return _remoteAddress;
	}

	public long getRemoteGroupId() {
		return _remoteGroupId;
	}

	public String getRemotePathContext() {
		return _remotePathContext;
	}

	public int getRemotePort() {
		return _remotePort;
	}

	public int getType() {
		return _type;
	}

	public void setRemoteAddress(String remoteAddress) {
		_remoteAddress = remoteAddress;
	}

	public void setRemoteGroupId(long remoteGroupId) {
		_remoteGroupId = remoteGroupId;
	}

	public void setRemotePathContext(String remotePathContext) {
		_remotePathContext = remotePathContext;
	}

	public void setRemotePort(int remotePort) {
		_remotePort = remotePort;
	}

	private String _remoteAddress;
	private long _remoteGroupId;
	private String _remotePathContext;
	private int _remotePort;
	private final int _type;

}