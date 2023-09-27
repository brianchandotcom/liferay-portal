/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.web.internal.auth.publicpath;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mika Koivisto
 */
@Component(
	property = {
		"auth.public.path=/portal/saml/acs",
		"auth.public.path=/portal/saml/auth_redirect",
		"auth.public.path=/portal/saml/login",
		"auth.public.path=/portal/saml/metadata",
		"auth.public.path=/portal/saml/slo",
		"auth.public.path=/portal/saml/slo_logout",
		"auth.public.path=/portal/saml/slo_redirect",
		"auth.public.path=/portal/saml/slo_soap",
		"auth.public.path=/portal/saml/sso"
	},
	service = Object.class
)
public class AuthPublicPath {
}