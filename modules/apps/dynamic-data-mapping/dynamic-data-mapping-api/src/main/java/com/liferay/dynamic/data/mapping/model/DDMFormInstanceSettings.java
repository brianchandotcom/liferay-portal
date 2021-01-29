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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.annotations.DDMFormRule;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bruno Basto
 */
@DDMForm(
	rules = {
		@DDMFormRule(
			actions = {
				"setVisible('emailFromAddress', getValue('sendEmailNotification'))",
				"setVisible('emailFromName', getValue('sendEmailNotification'))",
				"setVisible('emailSubject', getValue('sendEmailNotification'))",
				"setVisible('emailToAddress', getValue('sendEmailNotification'))",
				"setVisible('published', FALSE)"
			},
			condition = "TRUE"
		)
	}
)
@DDMFormLayout(
	paginationMode = com.liferay.dynamic.data.mapping.model.DDMFormLayout.TABBED_MODE,
	value = {
		@DDMFormLayoutPage(
			title = "%form-options",
			value = {
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"requireAuthentication", "requireCaptcha",
								"autosaveEnabled", "redirectURL", "storageType",
								"workflowDefinition", "submitLabel"
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
@ProviderType
public interface DDMFormInstanceSettings {

	@DDMFormField(
		label = "%save-answers-automatically", predefinedValue = "true",
		properties = "showAsSwitcher=true"
	)
	public boolean autosaveEnabled();

	@DDMFormField(
		label = "%from-address",
		validationErrorMessage = "%please-enter-a-valid-email-address",
		validationExpression = "isEmailAddress(emailFromAddress)"
	)
	public String emailFromAddress();

	@DDMFormField(label = "%from-name")
	public String emailFromName();

	@DDMFormField(label = "%subject")
	public String emailSubject();

	@DDMFormField(
		label = "%to-address",
		validationErrorMessage = "%please-enter-valid-email-addresses-separated-by-commas",
		validationExpression = "isEmailAddress(emailToAddress)"
	)
	public String emailToAddress();

	@DDMFormField
	public boolean published();

	@DDMFormField(
		label = "%redirect-url-on-success",
		properties = "placeholder=%enter-a-valid-url",
		validationErrorMessage = "%please-enter-a-valid-url",
		validationExpression = "isEmpty(redirectURL) OR isURL(redirectURL)"
	)
	public String redirectURL();

	@DDMFormField(
		label = "%require-user-authentication", predefinedValue = "false",
		properties = "showAsSwitcher=true"
	)
	public boolean requireAuthentication();

	@DDMFormField(
		label = "%require-captcha", properties = "showAsSwitcher=true",
		type = "checkbox"
	)
	public boolean requireCaptcha();

	@DDMFormField(
		label = "%send-an-email-notification-for-each-entry",
		properties = "showAsSwitcher=true", type = "checkbox"
	)
	public boolean sendEmailNotification();

	@DDMFormField(
		label = "%select-a-storage-type", predefinedValue = "[\"default\"]",
		properties = {
			"dataSourceType=data-provider",
			"ddmDataProviderInstanceId=ddm-storage-types"
		},
		type = "select"
	)
	public String storageType();

	@DDMFormField(
		label = "%submit-button-label", properties = "placeholder=%submit-form",
		type = "localizable_text"
	)
	public String submitLabel();

	@DDMFormField(
		label = "%select-a-workflow", predefinedValue = "[\"no-workflow\"]",
		properties = {
			"dataSourceType=data-provider",
			"ddmDataProviderInstanceId=workflow-definitions"
		},
		type = "select"
	)
	public String workflowDefinition();

}