/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Mariano Álvaro Sáiz
 */
@ExtendedObjectClassDefinition(category = "infrastructure", generateUI = false)
@Meta.OCD(
	id = "com.liferay.portal.db.partition.internal.configuration.DBPartitionCompanyActivationConfiguration",
	localization = "content/Language",
	name = "db-partition-company-activation-configuration-name"
)
public interface DBPartitionCompanyActivationConfiguration {

	@Meta.AD(name = "old-company-web-id")
	public String oldCompanyWebId();

	@Meta.AD(name = "new-company-web-id")
	public String newCompanyWebId();

}