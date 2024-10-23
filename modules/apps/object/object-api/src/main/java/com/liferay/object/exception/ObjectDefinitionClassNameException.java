/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class ObjectDefinitionClassNameException extends PortalException {

	public static class MustNotBeDuplicate
		extends ObjectDefinitionClassNameException {

		public MustNotBeDuplicate(String className) {
			super("Duplicate class name " + className);
		}

	}

	public static class MustStartWithPrefix
		extends ObjectDefinitionClassNameException {

		public MustStartWithPrefix() {
			super(
				"Class name must start with " +
					ObjectDefinitionConstants.
						CLASS_NAME_PREFIX_CUSTOM_OBJECT_DEFINITION);
		}

	}

	private ObjectDefinitionClassNameException(String msg) {
		super(msg);
	}

}