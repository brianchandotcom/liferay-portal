/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.questions.web.internal.configuration.definition;

import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;
import com.liferay.questions.web.internal.configuration.QuestionsConfiguration;
import com.liferay.questions.web.internal.constants.QuestionsPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier Gamarra
 */
@Component(service = ConfigurationPidMapping.class)
public class QuestionsPortletInstanceConfigurationPidMapping
	implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return QuestionsConfiguration.class;
	}

	@Override
	public String getConfigurationPid() {
		return QuestionsPortletKeys.QUESTIONS;
	}

}