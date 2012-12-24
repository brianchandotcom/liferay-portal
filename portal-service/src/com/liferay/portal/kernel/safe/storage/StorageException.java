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

package com.liferay.portal.kernel.safe.storage;

import com.liferay.portal.kernel.safe.PortalSafeException;

/**
 * Thrown in a case of internal exception in a {@link Storage} implementation.
 *
 * @author Tomas Polesovsky
 */
public class StorageException extends PortalSafeException {
	public StorageException() {
	}

	public StorageException(String msg) {
		super(msg);
	}

	public StorageException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public StorageException(Throwable cause) {
		super(cause);
	}

}