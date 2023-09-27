/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.click.to.chat.web.internal.configuration;

import com.liferay.configuration.admin.category.ConfigurationCategory;

import org.osgi.service.component.annotations.Component;

/**
 * @author José Abelenda
 */
@Component(service = ConfigurationCategory.class)
public class ClickToChatConfigurationCategory implements ConfigurationCategory {

	@Override
	public String getCategoryIcon() {
		return "chatbot";
	}

	@Override
	public String getCategoryKey() {
		return "click-to-chat";
	}

	@Override
	public String getCategorySection() {
		return "content-and-data";
	}

}