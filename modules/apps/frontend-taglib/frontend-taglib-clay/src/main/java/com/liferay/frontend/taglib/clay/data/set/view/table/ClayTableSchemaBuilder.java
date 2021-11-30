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

package com.liferay.frontend.taglib.clay.data.set.view.table;

/**
 * @author Marco Leo
 */
public interface ClayTableSchemaBuilder {

	public <T extends ClayTableSchemaField> T addClayTableSchemaField(
		Class<T> clazz, String fieldName);

	public <T extends ClayTableSchemaField> T addClayTableSchemaField(
		Class<T> clazz, String fieldName, String label);

	public void addClayTableSchemaField(
		ClayTableSchemaField clayTableSchemaField);

	public ClayTableSchemaField addClayTableSchemaField(String fieldName);

	public ClayTableSchemaField addClayTableSchemaField(
		String fieldName, String label);

	public ClayTableSchema build();

	public void removeClayTableSchemaField(String fieldName);

	public void setClayTableSchema(ClayTableSchema clayTableSchema);

}