/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;LPTSREVAudienceEntryRel&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureRelElementVariationAudienceEntryRel
 * @generated
 */
public class LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable
	extends BaseTable
		<LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable> {

	public static final
		LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable
			INSTANCE =
				new LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable();

	public final Column
		<LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable,
		 Long> mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column
		<LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable,
		 Long> ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column
		<LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable,
		 String> uuid = createColumn(
			"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column
		<LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable,
		 String> externalReferenceCode = createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column
		<LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable,
		 Long>
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelId =
				createColumn(
					"lptsrevAudienceEntryRelId", Long.class, Types.BIGINT,
					Column.FLAG_PRIMARY);
	public final Column
		<LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable,
		 Long> groupId = createColumn(
			"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column
		<LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable,
		 Long> companyId = createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column
		<LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable,
		 Long> userId = createColumn(
			"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column
		<LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable,
		 String> userName = createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column
		<LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable,
		 Date> createDate = createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column
		<LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable,
		 Date> modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column
		<LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable,
		 String> audienceEntryERC = createColumn(
			"audienceEntryERC", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column
		<LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable,
		 String> layoutPageTemplateStructureRelElementVariationERC =
			createColumn(
				"lptsRelElementVariationERC", String.class, Types.VARCHAR,
				Column.FLAG_DEFAULT);

	private LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable() {
		super(
			"LPTSREVAudienceEntryRel",
			LayoutPageTemplateStructureRelElementVariationAudienceEntryRelTable::
				new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1910196062