/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author István András Dézsi
 */
@ExtendedObjectClassDefinition(
	category = "upgrades", featureFlagKey = "LPD-10959"
)
@Meta.OCD(
	id = "com.liferay.portal.db.partition.internal.configuration.DBPartitionCopyVirtualInstanceConfiguration",
	localization = "content/Language",
	name = "db-partition-copy-virtual-instance-configuration-name"
)
public interface DBPartitionCopyVirtualInstanceConfiguration {

	@Meta.AD(name = "source-partition-company-id", type = Meta.Type.Long)
	public long sourcePartitionCompanyId();

	@Meta.AD(
		name = "destination-partition-company-id", required = false,
		type = Meta.Type.Long
	)
	public long destinationPartitionCompanyId();

	@Meta.AD(name = "name")
	public String name();

	@Meta.AD(name = "virtual-hostname")
	public String virtualHostname();

	@Meta.AD(name = "web-id")
	public String webId();

}