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

package com.liferay.users.admin.configuration.definition;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;

/**
 * @author Pei-Jung Lan
 */
@DDMForm
@DDMFormLayout(
	paginationMode = com.liferay.dynamic.data.mapping.model.DDMFormLayout.SINGLE_PAGE_MODE,
	value = {
		@DDMFormLayoutPage(
			{
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"imageCheckToken", "imageDefaultUseInitials",
								"imageMaxSize"
							}
						)
					}
				),
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 6, value = "imageMaxHeight"
						),
						@DDMFormLayoutColumn(size = 6, value = "imageMaxWidth")
					}
				)
			}
		)
	}
)
public interface UserFileUploadsConfigurationForm {

	@DDMFormField(
		label = "%check-token", properties = "showAsSwitcher=true",
		tip = "%users-image-check-token-help"
	)
	public boolean imageCheckToken();

	@DDMFormField(
		label = "%use-initials-for-default-user-portrait",
		properties = "showAsSwitcher=true",
		tip = "%use-initials-for-default-user-portrait-help"
	)
	public boolean imageDefaultUseInitials();

	@DDMFormField(
		label = "%maximum-height", tip = "%users-image-maximum-height-help"
	)
	public int imageMaxHeight();

}