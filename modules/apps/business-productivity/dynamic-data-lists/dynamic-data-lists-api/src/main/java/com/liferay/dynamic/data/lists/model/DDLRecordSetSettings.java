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

package com.liferay.dynamic.data.lists.model;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;

/**
 * @author Bruno Basto
 */
@DDMForm
@DDMFormLayout(
	{
		@DDMFormLayoutPage(
			title = "%form-options",
			value = {
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"requireCaptcha", "redirectURL", "storageType",
								"workflowDefinition"
							}
						)
					}
				)
			}
		),
		@DDMFormLayoutPage(
			title = "%email-notifications",
			value = {
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"sendEmailNotification", "emailFromName",
								"emailFromAddress", "emailToAddress",
								"emailSubject", "published"
							}
						)
					}
				)
			}
		)
	}
)
public interface DDLRecordSetSettings {

	@DDMFormField(
		label = "%from-address",
		validationErrorMessage = "%please-enter-a-valid-email-address",
		validationExpression = "isEmailAddress(emailFromAddress)",
		visibilityExpression = "sendEmailNotification == TRUE"
	)
	public String emailFromAddress();

	@DDMFormField(
		label = "%from-name",
		visibilityExpression = "sendEmailNotification == TRUE"
	)
	public String emailFromName();

	@DDMFormField(
		label = "%subject",
		visibilityExpression = "sendEmailNotification == TRUE"
	)
	public String emailSubject();

	@DDMFormField(
		label = "%to-address",
		validationErrorMessage = "%please-enter-a-valid-email-address",
		validationExpression = "isEmailAddress(emailToAddress)",
		visibilityExpression = "sendEmailNotification == TRUE"
	)
	public String emailToAddress();

	@DDMFormField(visibilityExpression = "FALSE")
	public boolean published();

	@DDMFormField(
		label = "%redirect-url-on-success",
		properties = {"placeholder=%enter-a-valid-url"},
		validationErrorMessage = "%please-enter-a-valid-url",
		validationExpression = "isURL(redirectURL)"
	)
	public String redirectURL();

	@DDMFormField(
		label = "%require-captcha", properties = {"showAsSwitcher=true"},
		type = "checkbox"
	)
	public boolean requireCaptcha();

	@DDMFormField(
		label = "%send-an-email-notification-for-each-entry",
		properties = {"showAsSwitcher=true"}, type = "checkbox"
	)
	public boolean sendEmailNotification();

	@DDMFormField(
		label = "%select-a-storage-type", properties = {"dataSourceType=manual"},
		type = "select"
	)
	public String storageType();

	@DDMFormField(
		label = "%select-a-workflow", properties = {"dataSourceType=manual"},
		type = "select"
	)
	public String workflowDefinition();

}