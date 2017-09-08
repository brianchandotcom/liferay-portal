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

package com.liferay.adaptive.media.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Adolfo Pérez
 */
public class AMException extends PortalException {

	public AMException() {
	}

	public AMException(String s) {
		super(s);
	}

	public AMException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public AMException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * This exception is raised when a requested media type or instance is not found.
	 */
	public static final class AMNotFound extends AMException {

		public AMNotFound() {
		}

		public AMNotFound(String s) {
			super(s);
		}

		public AMNotFound(String s, Throwable throwable) {
			super(s, throwable);
		}

		public AMNotFound(Throwable throwable) {
			super(throwable);
		}

	}

}