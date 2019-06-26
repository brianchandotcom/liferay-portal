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

package com.liferay.configuration.admin.definition;

import java.util.List;
import java.util.Locale;

/**
 * @author Alejandro Tardín
 *
 * Provides option labels and values for configuration fields. Implementing this
 * interface has the same effect as defining values for
 * {@link aQute.bnd.annotation.metatype.Meta.AD.optionLabels} and
 * {@link aQute.bnd.annotation.metatype.Meta.AD.optionValues} with the benefit
 * of doing it at runtime.
 *
 * Implementations must be registered as a ConfigurationFieldOptionsProvider
 * service, and must have the property "configuration.pid" whose value matches
 * the ID of the corresponding configuration interface (usually the fully
 * qualified class name) and the property "configuration.field.name" whose value
 * matches the name of the attribute in the configuration interface.
 *
 * @review
 */
public interface ConfigurationFieldOptionsProvider {

	public List<Option> getOptions();

	public interface Option {

		public String getLabel(Locale locale);

		public String getValue();

	}

}