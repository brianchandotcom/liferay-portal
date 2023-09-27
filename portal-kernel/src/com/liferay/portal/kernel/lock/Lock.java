/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.lock;

import java.util.Date;

/**
 * @author Tina Tian
 */
public interface Lock {

	public String getClassName();

	public long getCompanyId();

	public Date getCreateDate();

	public Date getExpirationDate();

	public long getExpirationTime();

	public boolean getInheritable();

	public String getKey();

	public long getLockId();

	public String getOwner();

	public long getUserId();

	public String getUserName();

	public String getUserUuid();

	public String getUuid();

	public boolean isCachedModel();

	public boolean isEscapedModel();

	public boolean isExpired();

	public boolean isInheritable();

	public boolean isNeverExpires();

	public boolean isNew();

}