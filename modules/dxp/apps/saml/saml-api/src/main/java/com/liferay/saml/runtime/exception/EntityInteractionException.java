/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
			"Failed interaction with entityId \"" + entityId + "\"", throwable);

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