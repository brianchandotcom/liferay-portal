/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Mateus Santana
 */
public class ObjectDefinitionModifiableException extends PortalException {

	public static class MustBeModifiable
		extends ObjectDefinitionModifiableException {

		public MustBeModifiable() {
			super("A modifiable object definition is required");
		}

	}

	private ObjectDefinitionModifiableException(String msg) {
		super(msg);
	}

}