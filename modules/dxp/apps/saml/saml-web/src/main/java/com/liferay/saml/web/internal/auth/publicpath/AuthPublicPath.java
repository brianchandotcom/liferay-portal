/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.web.internal.auth.publicpath;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mika Koivisto
 */
@Component(
	immediate = true,
	property = {
		"auth.public.path=/portal/saml/acs",
		"auth.public.path=/portal/saml/auth_redirect",
		"auth.public.path=/portal/saml/metadata",
		"auth.public.path=/portal/saml/slo_logout",
		"auth.public.path=/portal/saml/slo_redirect",
		"auth.public.path=/portal/saml/slo_soap",
		"auth.public.path=/portal/saml/sso"
	},
	service = Object.class
)
public class AuthPublicPath {
}