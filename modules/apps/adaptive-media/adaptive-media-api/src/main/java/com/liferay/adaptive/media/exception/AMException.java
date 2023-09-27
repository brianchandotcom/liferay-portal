/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	 * This exception is raised when a requested media type or instance is not
	 * found.
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