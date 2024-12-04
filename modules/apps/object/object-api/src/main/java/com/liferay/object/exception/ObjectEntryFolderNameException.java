/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Adolfo Pérez
 */
public class ObjectEntryFolderNameException extends PortalException {

	public static class MustNotBeDuplicate
		extends ObjectEntryFolderNameException {

		public MustNotBeDuplicate(String name) {
			super("Duplicate name " + name);
		}

	}

	public static class MustNotBeNull extends ObjectEntryFolderNameException {

		public MustNotBeNull() {
			super("Name is null");
		}

	}

	private ObjectEntryFolderNameException(String msg) {
		super(msg);
	}

}