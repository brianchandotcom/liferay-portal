/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;VirtualHost&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see VirtualHost
 * @generated
 */
public class VirtualHostTable extends BaseTable<VirtualHostTable> {

	public static final VirtualHostTable INSTANCE = new VirtualHostTable();

	public final Column<VirtualHostTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<VirtualHostTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<VirtualHostTable, Long> virtualHostId = createColumn(
		"virtualHostId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<VirtualHostTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<VirtualHostTable, Long> layoutSetId = createColumn(
		"layoutSetId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<VirtualHostTable, String> hostname = createColumn(
		"hostname", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<VirtualHostTable, Boolean> defaultVirtualHost =
		createColumn(
			"defaultVirtualHost", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<VirtualHostTable, String> languageId = createColumn(
		"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private VirtualHostTable() {
		super("VirtualHost", VirtualHostTable::new);
	}

}