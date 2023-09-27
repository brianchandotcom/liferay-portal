/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CNTemplateCAccountGroupRel&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateCommerceAccountGroupRel
 * @generated
 */
public class CommerceNotificationTemplateCommerceAccountGroupRelTable
	extends BaseTable
		<CommerceNotificationTemplateCommerceAccountGroupRelTable> {

	public static final CommerceNotificationTemplateCommerceAccountGroupRelTable
		INSTANCE =
			new CommerceNotificationTemplateCommerceAccountGroupRelTable();

	public final Column
		<CommerceNotificationTemplateCommerceAccountGroupRelTable, Long>
			mvccVersion = createColumn(
				"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column
		<CommerceNotificationTemplateCommerceAccountGroupRelTable, Long>
			commerceNotificationTemplateCommerceAccountGroupRelId =
				createColumn(
					"CNTemplateCAccountGroupRelId", Long.class, Types.BIGINT,
					Column.FLAG_PRIMARY);
	public final Column
		<CommerceNotificationTemplateCommerceAccountGroupRelTable, Long>
			groupId = createColumn(
				"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column
		<CommerceNotificationTemplateCommerceAccountGroupRelTable, Long>
			companyId = createColumn(
				"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column
		<CommerceNotificationTemplateCommerceAccountGroupRelTable, Long>
			userId = createColumn(
				"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column
		<CommerceNotificationTemplateCommerceAccountGroupRelTable, String>
			userName = createColumn(
				"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column
		<CommerceNotificationTemplateCommerceAccountGroupRelTable, Date>
			createDate = createColumn(
				"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column
		<CommerceNotificationTemplateCommerceAccountGroupRelTable, Date>
			modifiedDate = createColumn(
				"modifiedDate", Date.class, Types.TIMESTAMP,
				Column.FLAG_DEFAULT);
	public final Column
		<CommerceNotificationTemplateCommerceAccountGroupRelTable, Long>
			commerceNotificationTemplateId = createColumn(
				"commerceNotificationTemplateId", Long.class, Types.BIGINT,
				Column.FLAG_DEFAULT);
	public final Column
		<CommerceNotificationTemplateCommerceAccountGroupRelTable, Long>
			commerceAccountGroupId = createColumn(
				"commerceAccountGroupId", Long.class, Types.BIGINT,
				Column.FLAG_DEFAULT);

	private CommerceNotificationTemplateCommerceAccountGroupRelTable() {
		super(
			"CNTemplateCAccountGroupRel",
			CommerceNotificationTemplateCommerceAccountGroupRelTable::new);
	}

}