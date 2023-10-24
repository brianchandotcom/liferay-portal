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
	id = "com.liferay.portal.db.partition.internal.configuration.DBPartitionVirtualInstanceInsertionConfiguration",
	localization = "content/Language",
	name = "db-partition-virtual-instance-insertion-configuration-name"
)
public interface DBPartitionVirtualInstanceInsertionConfiguration {

	@Meta.AD(name = "new-name", required = false)
	public String newName();

	@Meta.AD(name = "new-virtual-host", required = false)
	public String newVirtualHost();

	@Meta.AD(name = "new-web-id", required = false)
	public String newWebId();

	@Meta.AD(name = "web-id")
	public String webId();

}