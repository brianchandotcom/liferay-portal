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

import com.liferay.petra.function.UnsafeConsumer;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Marco Leo
 */
@ProviderType
public interface FDSTableSchemaBuilder {

	public FDSTableSchemaBuilder add(FDSTableSchemaField fdsTableSchemaField);

	public FDSTableSchemaBuilder add(String fieldName);

	public FDSTableSchemaBuilder add(String fieldName, String label);

	public FDSTableSchemaBuilder add(
		String fieldName, String label,
		UnsafeConsumer<FDSTableSchemaField, Throwable> unsafeConsumer);

	public FDSTableSchemaBuilder add(
		String fieldName,
		UnsafeConsumer<FDSTableSchemaField, Throwable> unsafeConsumer);

	public FDSTableSchema build();

}