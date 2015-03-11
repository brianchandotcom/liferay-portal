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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.deploy.hot.HotDeployException;

/**
 * @author Zoltán Takács
 */
public class DuplicateCustomJspException extends HotDeployException {

	public DuplicateCustomJspException() {
	}

	public DuplicateCustomJspException(String msg) {
		super(msg);
	}

	public DuplicateCustomJspException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DuplicateCustomJspException(Throwable cause) {
		super(cause);
	}

}