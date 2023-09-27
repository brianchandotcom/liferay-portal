/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.definition;

import java.util.List;
import java.util.Locale;

/**
 * @author Alejandro Tardín  Provides option labels and values for configuration
 *         fields. Implementing this interface has the same effect as defining
 *         values for {@link aQute.bnd.annotation.metatype.Meta.AD.optionLabels}
 *         and {@link aQute.bnd.annotation.metatype.Meta.AD.optionValues} with
 *         the benefit of doing it at runtime.  Implementations must be
 *         registered as a ConfigurationFieldOptionsProvider service, and must
 *         have the property "configuration.pid" whose value matches the ID of
 *         the corresponding configuration interface (usually the fully
 *         qualified class name) and the property "configuration.field.name"
 *         whose value matches the name of the attribute in the configuration
 *         interface.
 * @review
 */
public interface ConfigurationFieldOptionsProvider {

	public List<Option> getOptions();

	public interface Option {

		public String getLabel(Locale locale);

		public String getValue();

	}

}