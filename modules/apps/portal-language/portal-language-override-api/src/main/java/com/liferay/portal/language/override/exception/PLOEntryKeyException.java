/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.override.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Drew Brokke
 */
public class PLOEntryKeyException extends PortalException {

	public static class MustBeShorter extends PLOEntryKeyException {

		public MustBeShorter(long maxLength) {
			super(
				String.format(
					"Key must not have more than %s characters", maxLength));

			this.maxLength = maxLength;
		}

		public final long maxLength;

	}

	public static class MustNotBeNull extends PLOEntryKeyException {

		public MustNotBeNull() {
			super("Key must not be null");
		}

	}

	private PLOEntryKeyException(String msg) {
		super(msg);
	}

}