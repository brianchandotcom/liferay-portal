/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.test.util;

/**
 * @author Cristina González
 */
public interface WhenHasParent {

	public String getParentBaseModelClassName();

	public void moveParentBaseModelToTrash(long primaryKey) throws Exception;

}