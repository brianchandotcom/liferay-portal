/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CPDefinitionVirtualSetting&quot; database table.
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSetting
 * @generated
 */
public class CPDefinitionVirtualSettingTable
	extends BaseTable<CPDefinitionVirtualSettingTable> {

	public static final CPDefinitionVirtualSettingTable INSTANCE =
		new CPDefinitionVirtualSettingTable();

	public final Column<CPDefinitionVirtualSettingTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CPDefinitionVirtualSettingTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, Long>
		CPDefinitionVirtualSettingId = createColumn(
			"CPDefinitionVirtualSettingId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CPDefinitionVirtualSettingTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, Long> fileEntryId =
		createColumn(
			"fileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, String> url =
		createColumn("url", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, Integer>
		activationStatus = createColumn(
			"activationStatus", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, Long> duration =
		createColumn("duration", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, Integer> maxUsages =
		createColumn(
			"maxUsages", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, Boolean> useSample =
		createColumn(
			"useSample", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, Long>
		sampleFileEntryId = createColumn(
			"sampleFileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, String> sampleURL =
		createColumn(
			"sampleURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, Boolean>
		termsOfUseRequired = createColumn(
			"termsOfUseRequired", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, String>
		termsOfUseContent = createColumn(
			"termsOfUseContent", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, Long>
		termsOfUseJournalArticleResourcePrimKey = createColumn(
			"termsOfUseArticleResourcePK", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, Boolean> override =
		createColumn(
			"override", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionVirtualSettingTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private CPDefinitionVirtualSettingTable() {
		super(
			"CPDefinitionVirtualSetting", CPDefinitionVirtualSettingTable::new);
	}

}