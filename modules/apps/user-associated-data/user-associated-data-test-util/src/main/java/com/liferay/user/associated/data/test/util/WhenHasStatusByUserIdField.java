/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.test.util;

/**
 * @author Noah Sherrill
 */
public interface WhenHasStatusByUserIdField<T> {

	public T addBaseModelWithStatusByUserId(long userId, long statusByUserId)
		throws Exception;

}