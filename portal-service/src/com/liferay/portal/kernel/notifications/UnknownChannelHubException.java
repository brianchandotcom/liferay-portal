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

package com.liferay.portal.kernel.notifications;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class UnknownChannelHubException extends ChannelException {

	public UnknownChannelHubException() {
	}

	public UnknownChannelHubException(String msg) {
		super(msg);
	}

	public UnknownChannelHubException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public UnknownChannelHubException(Throwable cause) {
		super(cause);
	}

}