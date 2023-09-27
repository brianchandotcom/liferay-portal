/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;ResourceAction&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ResourceAction
 * @generated
 */
public class ResourceActionTable extends BaseTable<ResourceActionTable> {

	public static final ResourceActionTable INSTANCE =
		new ResourceActionTable();

	public final Column<ResourceActionTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ResourceActionTable, Long> resourceActionId =
		createColumn(
			"resourceActionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ResourceActionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ResourceActionTable, String> actionId = createColumn(
		"actionId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ResourceActionTable, Long> bitwiseValue = createColumn(
		"bitwiseValue", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private ResourceActionTable() {
		super("ResourceAction", ResourceActionTable::new);
	}

}