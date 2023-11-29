/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.manager;

import org.wso2.charon3.core.exceptions.ConflictException;
import org.wso2.charon3.core.extensions.UserManager;
import org.wso2.charon3.core.protocol.SCIMResponse;
import org.wso2.charon3.core.protocol.endpoints.AbstractResourceManager;
import org.wso2.charon3.core.protocol.endpoints.UserResourceManager;

/**
 * @author Rafael Praxedes
 */
public class UserResourceManagerImpl extends UserResourceManager {

	@Override
	public SCIMResponse delete(String id, UserManager userManager) {
		try {
			return super.delete(id, userManager);
		}
		catch (Exception exception) {
			if (exception instanceof ConflictException) {
				return AbstractResourceManager.encodeSCIMException(
					(ConflictException)exception);
			}

			throw exception;
		}
	}

	@Override
	public SCIMResponse get(
		String id, UserManager userManager, String attributes,
		String excludeAttributes) {

		try {
			return super.get(id, userManager, attributes, excludeAttributes);
		}
		catch (Exception exception) {
			if (exception instanceof ConflictException) {
				return AbstractResourceManager.encodeSCIMException(
					(ConflictException)exception);
			}

			throw exception;
		}
	}

	@Override
	public SCIMResponse updateWithPUT(
		String existingId, String scimObjectString, UserManager userManager,
		String attributes, String excludeAttributes) {

		try {
			return super.updateWithPUT(
				existingId, scimObjectString, userManager, attributes,
				excludeAttributes);
		}
		catch (Exception exception) {
			if (exception instanceof ConflictException) {
				return AbstractResourceManager.encodeSCIMException(
					(ConflictException)exception);
			}

			throw exception;
		}
	}

}