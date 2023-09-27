/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Blob;
import java.sql.Types;

/**
 * The table class for the &quot;EagerBlobEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see EagerBlobEntry
 * @generated
 */
public class EagerBlobEntryTable extends BaseTable<EagerBlobEntryTable> {

	public static final EagerBlobEntryTable INSTANCE =
		new EagerBlobEntryTable();

	public final Column<EagerBlobEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<EagerBlobEntryTable, Long> eagerBlobEntryId =
		createColumn(
			"eagerBlobEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<EagerBlobEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<EagerBlobEntryTable, Blob> blob = createColumn(
		"blob_", Blob.class, Types.BLOB, Column.FLAG_DEFAULT);

	private EagerBlobEntryTable() {
		super("EagerBlobEntry", EagerBlobEntryTable::new);
	}

}