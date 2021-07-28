/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.tuning.synonyms.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;STSynonymsEntry&quot; database table.
 *
 * @author Bryan Engler
 * @see STSynonymsEntry
 * @generated
 */
public class STSynonymsEntryTable extends BaseTable<STSynonymsEntryTable> {

	public static final STSynonymsEntryTable INSTANCE =
		new STSynonymsEntryTable();

	public final Column<STSynonymsEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<STSynonymsEntryTable, Long> STSynonymsEntryId =
		createColumn(
			"STSynonymsEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<STSynonymsEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private STSynonymsEntryTable() {
		super("STSynonymsEntry", STSynonymsEntryTable::new);
	}

}