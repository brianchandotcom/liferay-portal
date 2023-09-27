/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.permission;

import java.io.Serializable;

import java.util.Collection;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jorge Ferrer
 */
@ProviderType
public interface ModelPermissions extends Cloneable, Serializable {

	public void addRolePermissions(String roleName, String... actionIds);

	public ModelPermissions clone();

	public String[] getActionIds(String roleName);

	public String getResourceName();

	public Collection<String> getRoleNames();

	public void setResourceName(String resourceName);

}