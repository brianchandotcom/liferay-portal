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

package com.liferay.commerce.order.web.internal.frontend.taglib.clay.data.set.view.table;

import com.liferay.commerce.order.web.internal.frontend.constants.CommerceOrderDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"clay.data.set.display.name=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_COMPLETED_ORDERS,
		"clay.data.set.display.name=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PENDING_ORDERS,
		"clay.data.set.display.name=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PROCESSING_ORDERS
	},
	service = ClayDataSetDisplayView.class
)
public class CommerceOrderClayTableDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField orderIdClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"orderId", "order-id");

		orderIdClayTableSchemaField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addClayTableSchemaField("account", "account");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"accountCode", "account-number");

		clayTableSchemaBuilder.addClayTableSchemaField("channel", "channel");

		clayTableSchemaBuilder.addClayTableSchemaField("amount", "amount");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"orderDate", "order-date");

		ClayTableSchemaField orderStatusClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"orderStatus", "order-status");

		orderStatusClayTableSchemaField.setContentRenderer("label");

		ClayTableSchemaField fulfillmentWorkflowClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"fulfillmentWorkflow", "acceptance-workflow-status");

		fulfillmentWorkflowClayTableSchemaField.setContentRenderer("label");

		return clayTableSchemaBuilder.build();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

}