/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

/**
 * @author Brian Wing Shun Chan
 * @see    com.liferay.portal.model.impl.PermissionImpl
 */
public interface Permission {

	public String getActionId();

	public String getName();

	public String getPrimKey();

	public int getScope();

	public void setActionId(String actionId);

	public void setName(String name);

	public void setPrimKey(String primKey);

	public void setScope(int scope);

}