/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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