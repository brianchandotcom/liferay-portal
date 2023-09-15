/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class ObjectRelationshipLabelException extends PortalException {

	public ObjectRelationshipLabelException() {
	}

	public ObjectRelationshipLabelException(String msg) {
		super(msg);
	}

	public ObjectRelationshipLabelException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ObjectRelationshipLabelException(Throwable throwable) {
		super(throwable);
	}

}