/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.memory;

/**
 * @author Shuyang Zhou
 */
public interface PoolAction<O, I> {

	public O onBorrow(O output, I input);

	public O onCreate(I input);

	public void onReturn(O output);

}