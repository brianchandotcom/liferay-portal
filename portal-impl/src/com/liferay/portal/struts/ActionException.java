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

package com.liferay.portal.struts;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of 6.2.0, moved to {@link
 *             com.liferay.portal.kernel.events.ActionException}
 */
@Deprecated
public class ActionException
	extends com.liferay.portal.kernel.events.ActionException {

	public ActionException() {
	}

	public ActionException(String msg) {
		super(msg);
	}

	public ActionException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ActionException(Throwable cause) {
		super(cause);
	}

}