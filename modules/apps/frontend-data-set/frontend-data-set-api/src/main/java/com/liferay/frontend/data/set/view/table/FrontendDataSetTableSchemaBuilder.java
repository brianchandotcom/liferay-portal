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

package com.liferay.frontend.data.set.view.table;

/**
 * @author Marco Leo
 */
public interface FrontendDataSetTableSchemaBuilder {

	public void addFrontendDataSetTableSchemaField(
		FrontendDataSetTableSchemaField frontendDataSetTableSchemaField);

	public FrontendDataSetTableSchemaField addFrontendDataSetTableSchemaField(
		String fieldName);

	public FrontendDataSetTableSchemaField addFrontendDataSetTableSchemaField(
		String fieldName, String label);

	public FrontendDataSetTableSchema build();

	public void removeFrontendDataSetTableSchemaField(String fieldName);

	public void setFrontendDataSetTableSchema(
		FrontendDataSetTableSchema frontendDataSetTableSchema);

}