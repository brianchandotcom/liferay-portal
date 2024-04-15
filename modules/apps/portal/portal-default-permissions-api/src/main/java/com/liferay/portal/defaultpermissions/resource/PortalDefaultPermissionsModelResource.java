/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.defaultpermissions.resource;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Stefano Motta
 */
public interface PortalDefaultPermissionsModelResource {

	public String getClassName();

	public String getLabel();

	public ExtendedObjectClassDefinition.Scope getScope();

}