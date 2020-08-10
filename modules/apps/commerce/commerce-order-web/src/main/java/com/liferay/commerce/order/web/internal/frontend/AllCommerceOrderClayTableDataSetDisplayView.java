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

package com.liferay.commerce.order.web.internal.frontend;

import com.liferay.commerce.frontend.clay.data.set.ClayDataSetDisplayView;
import com.liferay.commerce.frontend.clay.table.ClayTableDataSetDisplayView;
import com.liferay.commerce.frontend.clay.table.ClayTableSchema;
import com.liferay.commerce.frontend.clay.table.ClayTableSchemaBuilder;
import com.liferay.commerce.frontend.clay.table.ClayTableSchemaBuilderFactory;
import com.liferay.commerce.frontend.clay.table.ClayTableSchemaField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.data.set.display.name=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_ALL_ORDERS,
	service = ClayDataSetDisplayView.class
)
public class AllCommerceOrderClayTableDataSetDisplayView
	extends ClayTableDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.clayTableSchemaBuilder();

		ClayTableSchemaField orderIdField = clayTableSchemaBuilder.addField(
			"id", "order-id");

		orderIdField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addField("account.name", "account");

		clayTableSchemaBuilder.addField("accountId", "account-number");

		clayTableSchemaBuilder.addField("channel.name", "channel");

		clayTableSchemaBuilder.addField("totalFormatted", "amount");

		ClayTableSchemaField dateClayTableSchemaField =
			clayTableSchemaBuilder.addField("createDate", "create-date");

		dateClayTableSchemaField.setContentRenderer("date");
		dateClayTableSchemaField.setSortable(true);

		ClayTableSchemaField orderStatusField = clayTableSchemaBuilder.addField(
			"orderStatusInfo", "order-status");

		orderStatusField.setContentRenderer("status");

		ClayTableSchemaField fulfillmentWorkflowField =
			clayTableSchemaBuilder.addField(
				"workflowStatusInfo", "acceptance-workflow-status");

		fulfillmentWorkflowField.setContentRenderer("status");

		return clayTableSchemaBuilder.build();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

}