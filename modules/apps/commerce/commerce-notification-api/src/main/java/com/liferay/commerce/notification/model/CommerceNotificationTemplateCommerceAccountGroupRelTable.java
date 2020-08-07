/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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