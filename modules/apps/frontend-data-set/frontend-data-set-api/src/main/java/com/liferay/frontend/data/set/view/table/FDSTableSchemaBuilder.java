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
public interface FDSTableSchemaBuilder {

	public <T extends FDSTableSchemaField> T addFDSTableSchemaField(
		Class<T> clazz, String fieldName);

	public <T extends FDSTableSchemaField> T addFDSTableSchemaField(
		Class<T> clazz, String fieldName, String label);

	public void addFDSTableSchemaField(FDSTableSchemaField fdsTableSchemaField);

	public FDSTableSchemaField addFDSTableSchemaField(String fieldName);

	public FDSTableSchemaField addFDSTableSchemaField(
		String fieldName, String label);

	public FDSTableSchema build();

	public void removeFDSTableSchemaField(String fieldName);

	public void setFDSTableSchema(FDSTableSchema fdsTableSchema);

}