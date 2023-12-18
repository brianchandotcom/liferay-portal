/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Marco Leo
 */
public class ObjectFolderItemObjectDefinitionIdException
	extends PortalException {

	public ObjectFolderItemObjectDefinitionIdException(
		List<String> objectDefinitionNames) {

		_objectDefinitionNames = objectDefinitionNames;
	}

	public List<String> getObjectDefinitionNames() {
		return _objectDefinitionNames;
	}

	private final List<String> _objectDefinitionNames;

}