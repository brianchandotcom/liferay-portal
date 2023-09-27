/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.function;

/**
 * @author Shuyang Zhou
 */
@FunctionalInterface
public interface UnsafeBiFunction<T, U, R, E extends Throwable> {

	public R apply(T t, U u) throws E;

}