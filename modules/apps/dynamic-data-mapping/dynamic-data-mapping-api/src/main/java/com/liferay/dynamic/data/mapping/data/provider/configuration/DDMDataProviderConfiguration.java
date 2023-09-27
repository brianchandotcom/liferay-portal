/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Leonardo Barros
 */
@ExtendedObjectClassDefinition(
	category = "data-providers",
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.dynamic.data.mapping.data.provider.configuration.DDMDataProviderConfiguration",
	localization = "content/Language",
	name = "ddm-data-provider-configuration-name"
)
public interface DDMDataProviderConfiguration {

	@Meta.AD(
		deflt = "false", description = "access-local-network-description",
		name = "access-local-network-name", required = false
	)
	public boolean accessLocalNetwork();

	@Meta.AD(
		deflt = "false",
		description = "trust-self-signed-certificates-description",
		name = "trust-self-signed-certificates-name", required = false
	)
	public default boolean trustSelfSignedCertificates() {
		return false;
	}

}