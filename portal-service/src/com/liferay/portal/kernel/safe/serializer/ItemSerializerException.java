/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.safe.serializer;

import com.liferay.portal.kernel.safe.PortalSafeException;

/**
 * Thrown when an exception occurs while serialization or de-serialization
 * process.
 *
 * @author Tomas Polesovsky
 */
public class ItemSerializerException extends PortalSafeException {
	public ItemSerializerException() {
	}

	public ItemSerializerException(String msg) {
		super(msg);
	}

	public ItemSerializerException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ItemSerializerException(Throwable cause) {
		super(cause);
	}

}