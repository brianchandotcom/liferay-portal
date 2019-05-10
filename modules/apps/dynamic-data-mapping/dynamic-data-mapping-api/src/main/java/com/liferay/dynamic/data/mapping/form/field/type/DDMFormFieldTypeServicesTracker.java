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

package com.liferay.dynamic.data.mapping.form.field.type;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Marcellus Tavares
 */
@ProviderType
public interface DDMFormFieldTypeServicesTracker {

	public DDMFormFieldRenderer getDDMFormFieldRenderer(String name);

	public DDMFormFieldTemplateContextContributor
		getDDMFormFieldTemplateContextContributor(String name);

	public DDMFormFieldType getDDMFormFieldType(String name);

	public Set<String> getDDMFormFieldTypeNames();

	public Map<String, Object> getDDMFormFieldTypeProperties(String name);

	public List<DDMFormFieldType> getDDMFormFieldTypes();

	public List<DDMFormFieldType> getDDMFormFieldTypesByDataDomain(
		String dataDomain);

	public <T> DDMFormFieldValueAccessor<T> getDDMFormFieldValueAccessor(
		String name);

	public DDMFormFieldValueRenderer getDDMFormFieldValueRenderer(String name);

}