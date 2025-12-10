/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.configuration.test.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.headless.admin.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Thiago Buarque
 */
@ExtendedObjectClassDefinition(
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(id = ConfigurationTestUtil.TEST_CONFIGURATION_PID)
public interface TestConfiguration {

	@Meta.AD(required = false)
	public String[] arrayKey();

	@Meta.AD(required = false, type = Meta.Type.Password)
	public String passwordStringKey();

	@Meta.AD
	public boolean requiredBooleanKey();

}