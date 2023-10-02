/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.questions.web.internal.configuration.admin.definition;

import com.liferay.configuration.admin.definition.ConfigurationDDMFormDeclaration;

import org.osgi.service.component.annotations.Component;

/**
 * @author José Abelenda
 */
@Component(
	property = "configurationPid=com.liferay.questions.web.internal.configuration.QuestionsConfiguration",
	service = ConfigurationDDMFormDeclaration.class
)
public class QuestionsConfigurationDDMFormDeclaration
	implements ConfigurationDDMFormDeclaration {

	@Override
	public Class<?> getDDMFormClass() {
		return QuestionsConfigurationForm.class;
	}

}