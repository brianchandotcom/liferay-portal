/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.util;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public interface UpgradeColumn {

	public String getName();

	public Integer getNewColumnType(Integer defaultType);

	public Object getNewValue();

	public Object getNewValue(Object oldValue) throws Exception;

	public Integer getOldColumnType(Integer defaultType);

	public Object getOldValue();

	public boolean isApplicable(String name);

	public void setNewValue(Object newValue);

	public void setOldValue(Object oldValue);

}