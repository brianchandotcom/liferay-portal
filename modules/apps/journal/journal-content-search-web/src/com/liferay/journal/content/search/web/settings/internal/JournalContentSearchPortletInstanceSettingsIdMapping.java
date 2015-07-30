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

package com.liferay.journal.content.search.web.settings.internal;

import com.liferay.journal.content.search.web.configuration.JournalContentSearchPortletInstanceConfiguration;
import com.liferay.journal.content.search.web.constants.JournalContentSearchPortletKeys;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;

import org.osgi.service.component.annotations.Component;

/**
 * @author Juergen Kappler
 */
@Component
public class JournalContentSearchPortletInstanceSettingsIdMapping
	implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return JournalContentSearchPortletInstanceConfiguration.class;
	}

	@Override
	public String getSettingsId() {
		return JournalContentSearchPortletKeys.JOURNAL_CONTENT_SEARCH;
	}

}