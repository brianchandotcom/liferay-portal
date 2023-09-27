/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.function;

/**
 * @author Brian Wing Shun Chan
 */
@FunctionalInterface
public interface UnsafeBiConsumer<A, B, T extends Throwable> {

	public void accept(A a, B b) throws T;

}