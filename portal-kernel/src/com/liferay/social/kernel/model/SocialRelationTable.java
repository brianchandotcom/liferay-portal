/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;SocialRelation&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SocialRelation
 * @generated
 */
public class SocialRelationTable extends BaseTable<SocialRelationTable> {

	public static final SocialRelationTable INSTANCE =
		new SocialRelationTable();

	public final Column<SocialRelationTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<SocialRelationTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SocialRelationTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SocialRelationTable, Long> relationId = createColumn(
		"relationId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SocialRelationTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialRelationTable, Long> createDate = createColumn(
		"createDate", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialRelationTable, Long> userId1 = createColumn(
		"userId1", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialRelationTable, Long> userId2 = createColumn(
		"userId2", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialRelationTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private SocialRelationTable() {
		super("SocialRelation", SocialRelationTable::new);
	}

}