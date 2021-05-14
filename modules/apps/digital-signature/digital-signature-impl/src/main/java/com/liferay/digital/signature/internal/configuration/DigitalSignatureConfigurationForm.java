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

package com.liferay.digital.signature.internal.configuration;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;

/**
 * @author José Abelenda
 */
@DDMForm
@DDMFormLayout(
	paginationMode = com.liferay.dynamic.data.mapping.model.DDMFormLayout.SINGLE_PAGE_MODE,
	value = {
		@DDMFormLayoutPage(
			{
				@DDMFormLayoutRow(
					{@DDMFormLayoutColumn(size = 12, value = "enable")}
				),
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(size = 6, value = "apiUsername"),
						@DDMFormLayoutColumn(size = 6, value = "apiAccountId")
					}
				),
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 6, value = "accountBaseURI"
						),
						@DDMFormLayoutColumn(size = 6, value = "integrationKey")
					}
				),
				@DDMFormLayoutRow(
					{@DDMFormLayoutColumn(size = 12, value = "rsaPrivateKey")}
				)
			}
		)
	}
)
public interface DigitalSignatureConfigurationForm {

	@DDMFormField(label = "%account's-base-uri", required = false)
	public String accountBaseURI();

	@DDMFormField(label = "%api-account-id", required = false)
	public String apiAccountId();

	@DDMFormField(label = "%api-username")
	public String apiUsername();

	@DDMFormField(label = "%enable", properties = "showAsSwitcher=true")
	public boolean enable();

	@DDMFormField(label = "%integration-key", required = false)
	public String integrationKey();

	@DDMFormField(
		label = "%rsa-private-key", properties = "displayStyle=multiline",
		required = false, type = "text"
	)
	public String rsaPrivateKey();

}