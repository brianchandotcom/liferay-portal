/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.model;

import java.util.Date;

/**
 * @author Adolfo Pérez
 * @author Sergio González
 */
public interface RepositoryEntry {

	public long getCompanyId();

	public Date getCreateDate();

	public long getGroupId();

	public Date getModifiedDate();

	public long getUserId();

	public String getUserName();

	public String getUserUuid();

}