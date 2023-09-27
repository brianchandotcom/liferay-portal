/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Christian Chiappa
 */
@ExtendedObjectClassDefinition(
	category = "orders", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.configuration.CommerceOrderImporterDateFormatConfiguration",
	localization = "content/Language",
	name = "order-importer-date-format-configuration-name"
)
public interface CommerceOrderImporterDateFormatConfiguration {

	@Meta.AD(
		deflt = "yyyy-MM-dd", name = "order-importer-date-format",
		required = false
	)
	public String orderImporterDateFormat();

}