/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.expression;

import com.liferay.petra.sql.dsl.Table;

/**
 * @author Preston Crary
 */
public interface ColumnAlias<T extends Table<T>, C> extends Alias<C> {

	public T getTable();

}