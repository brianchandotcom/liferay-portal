/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository;

import java.util.Collection;
import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public interface RepositoryConfiguration {

	public Collection<Parameter> getParameters();

	public interface Parameter {

		public String getLabel(Locale locale);

		public String getName();

	}

}