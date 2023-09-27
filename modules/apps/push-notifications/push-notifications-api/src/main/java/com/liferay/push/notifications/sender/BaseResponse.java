/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.sender;

/**
 * @author Bruno Farache
 */
public class BaseResponse implements Response {

	public BaseResponse(String platform) {
		this(platform, null);
	}

	public BaseResponse(String platform, Exception exception) {
		this.platform = platform;

		if (exception != null) {
			succeeded = false;
			status = exception.getMessage();
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getPayload() {
		return payload;
	}

	@Override
	public String getPlatform() {
		return platform;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public String getToken() {
		return token;
	}

	@Override
	public boolean isSucceeded() {
		return succeeded;
	}

	protected String id;
	protected String payload;
	protected String platform;
	protected String status;
	protected boolean succeeded;
	protected String token;

}