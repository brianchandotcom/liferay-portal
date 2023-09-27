/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.runtime.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Stian Sigvartsen
 */
public class EntityInteractionException extends PortalException {

	public EntityInteractionException(
		String entityId, String nameIdValue, Throwable throwable) {

		super(
			"Failed interaction with entity ID \"" + entityId + "\"",
			throwable);

		_entityId = entityId;
		_nameIdValue = nameIdValue;
	}

	public EntityInteractionException(Throwable throwable) {
		super(throwable);
	}

	public String getEntityId() {
		return _entityId;
	}

	public String getNameIdValue() {
		return _nameIdValue;
	}

	private String _entityId;
	private String _nameIdValue;

}