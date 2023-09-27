/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;DDMTemplateLink&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateLink
 * @generated
 */
public class DDMTemplateLinkTable extends BaseTable<DDMTemplateLinkTable> {

	public static final DDMTemplateLinkTable INSTANCE =
		new DDMTemplateLinkTable();

	public final Column<DDMTemplateLinkTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDMTemplateLinkTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMTemplateLinkTable, Long> templateLinkId =
		createColumn(
			"templateLinkId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMTemplateLinkTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateLinkTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateLinkTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateLinkTable, Long> templateId = createColumn(
		"templateId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private DDMTemplateLinkTable() {
		super("DDMTemplateLink", DDMTemplateLinkTable::new);
	}

}