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

package com.liferay.dynamic.data.mapping.data.provider.rest.internal;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.annotations.DDMFormRule;

/**
 * @author Marcellus Tavares
 */
@DDMForm(
	rules = {
		@DDMFormRule(
			actions = {
				"setVisible('filterParameterName', true)",
				"setRequired('filterParameterName', true)"
			},
			condition = "equals(getValue('filterable'), true)"
		)
	}
)
@DDMFormLayout(
	{
		@DDMFormLayoutPage(
			{
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"url", "key", "value", "username", "password",
								"filterable", "filterParameterName", "cacheable"
							}
						)
					}
				)
			}
		)
	}
)
public interface DDMRESTDataProviderSettings {

	@DDMFormField(
		label = "%cache-data-on-the-first-request",
		properties = "showAsSwitcher=true"
	)
	public boolean cacheable();

	@DDMFormField(
		label = "%support-filtering-by-keyword",
		properties = "showAsSwitcher=true"
	)
	public boolean filterable();

	@DDMFormField(
		label = "%filter-parameter-name",
		properties = {
			"placeholder=%enter-a-name-that-matches-one-of-the-rest-providers-parameters",
			"tooltip=%the-parameter-whose-value-will-be-used-as-a-filter-by-the-rest-provider"
		}
	)
	public String filterParameterName();

	@DDMFormField(
		label = "%displayed-json-attribute",
		properties = {
			"placeholder=%enter-the-attribute-to-be-displayed",
			"tooltip=%the-attribute-whose-value-is-displayed-to-the-end-user-for-selection"
		},
		required = true
	)
	public String key();

	@DDMFormField(
		label = "%password",
		properties = {
			"placeholder=%enter-a-password",
			"tooltip=%provide-the-password-for-authenticating-to-the-rest-provider"
		}
	)
	public String password();

	@DDMFormField(
		label = "%url",
		properties = {"placeholder=%enter-the-rest-service-url"},
		required = true
	)
	public String url();

	@DDMFormField(
		label = "%user-name",
		properties = {
			"placeholder=%enter-a-user-name",
			"tooltip=%provide-the-user-name-for-authenticating-to-the-rest-provider"
		}
	)
	public String username();

	@DDMFormField(
		label = "%stored-json-attribute",
		properties = {
			"placeholder=%enter-the-attribute-to-be-stored",
			"tooltip=%the-attribute-whose-value-is-stored-in-the-database-when-selected-by-a-user"
		},
		required = true
	)
	public String value();

}