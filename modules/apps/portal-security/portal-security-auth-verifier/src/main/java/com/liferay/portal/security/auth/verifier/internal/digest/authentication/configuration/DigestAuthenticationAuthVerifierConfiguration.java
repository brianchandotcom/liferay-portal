/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth.verifier.internal.digest.authentication.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.security.auth.verifier.internal.configuration.BaseAuthVerifierConfiguration;

/**
 * @author Tomas Polesovsky
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Deprecated
@ExtendedObjectClassDefinition(
	category = "api-authentication",
	factoryInstanceLabelAttribute = "urlsIncludes"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.security.auth.verifier.internal.digest.authentication.configuration.DigestAuthenticationAuthVerifierConfiguration",
	localization = "content/Language",
	name = "digest-authentication-auth-verifier-configuration-name"
)
public interface DigestAuthenticationAuthVerifierConfiguration
	extends BaseAuthVerifierConfiguration {

	@Meta.AD(name = "force-digest-auth", required = false)
	public boolean forceDigestAuth();

}