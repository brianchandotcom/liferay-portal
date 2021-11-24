/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.web.internal.configuration.admin.category;

import com.liferay.configuration.admin.category.ConfigurationCategory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(enabled = false, service = ConfigurationCategory.class)
public class SXPConfigurationCategory implements ConfigurationCategory {

	@Override
	public String getCategoryIcon() {
		return "search-experiences";
	}

	@Override
	public String getCategoryKey() {
		return "search-experiences";
	}

	@Override
	public String getCategorySection() {
		return "platform";
	}

}