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

package com.liferay.object.internal.action.settings;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;

/**
 * @author Marco Leo
 */
@DDMForm
public interface AddObjectEntryObjectActionSettings {

	@DDMFormField(
		label = "%select-object",
		properties = {
			"dataSourceType=data-provider", "ddmDataProviderInstanceId=objects"
		},
		type = "select"
	)
	public String objectDefinitionId();

	@DDMFormField(label = "%object-field", type = "object_field")
	public String objectFieldName();

}