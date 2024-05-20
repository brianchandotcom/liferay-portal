/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Thiago Buarque
 */
@ExtendedObjectClassDefinition(category = "other")
@Meta.OCD(
	id = "com.liferay.configuration.admin.internal.configuration.TestCompanyConfiguration"
)
public interface TestCompanyConfiguration {

	@Meta.AD(deflt = "", name = "company-configuration-key", required = false)
	public String companyConfigurationKey();

}