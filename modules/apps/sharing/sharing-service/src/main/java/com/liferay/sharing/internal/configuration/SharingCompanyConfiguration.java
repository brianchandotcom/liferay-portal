/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(
	category = "sharing", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.sharing.internal.configuration.SharingCompanyConfiguration",
	localization = "content/Language",
	name = "sharing-company-configuration-name"
)
public interface SharingCompanyConfiguration {

	/**
	 * Enables sharing.
	 *
	 * @review
	 */
	@Meta.AD(deflt = "true", name = "enabled", required = false)
	public boolean enabled();

}