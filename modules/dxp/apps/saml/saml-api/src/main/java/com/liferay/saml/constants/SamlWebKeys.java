/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.saml.constants;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Mika Koivisto
 */
@ProviderType
public interface SamlWebKeys {

	public static final String FORCE_REAUHENTICATION = "FORCE_REAUTHENTICATION";

	public static final String SAML_ASSERTION_LIFETIME =
		"SAML_ASSERTION_LIFETIME";

	public static final String SAML_CERTIFICATE_TOOL = "SAML_CERTIFICATE_TOOL";

	public static final String SAML_CLOCK_SKEW = "SAML_CLOCK_SKEW";

	public static final String SAML_ENTITY_ID = "SAML_ENTITY_ID";

	public static final String SAML_IDP_SP_CONNECTION =
		"SAML_IDP_SP_CONNECTION";

	public static final String SAML_IDP_SP_CONNECTIONS =
		"SAML_IDP_SP_CONNECTIONS";

	public static final String SAML_IDP_SP_CONNECTIONS_COUNT =
		"SAML_IDP_SP_CONNECTIONS_COUNT";

	public static final String SAML_KEEP_ALIVE_URL = "SAML_KEEP_ALIVE_URL";

	public static final String SAML_KEEP_ALIVE_URLS = "SAML_KEEP_ALIVE_URLS";

	public static final String SAML_SLO_CONTEXT = "SAML_SLO_CONTEXT";

	public static final String SAML_SLO_REQUEST_INFO = "SAML_SLO_REQUEST_INFO";

	public static final String SAML_SP_ATTRIBUTES = "SAML_SP_ATTRIBUTES";

	public static final String SAML_SP_IDP_CONNECTION =
		"SAML_SP_IDP_CONNECTION";

	public static final String SAML_SP_NAME_ID_FORMAT =
		"SAML_SP_NAME_ID_FORMAT";

	public static final String SAML_SP_NAME_ID_VALUE = "SAML_SP_NAME_ID_VALUE";

	public static final String SAML_SP_SESSION_KEY = "SAML_SP_SESSION_KEY";

	public static final String SAML_SSO_REQUEST_CONTEXT =
		"SAML_SSO_REQUEST_CONTEXT";

	public static final String SAML_SSO_SESSION_ID = "SAML_SSO_SESSION_ID";

	public static final String SAML_X509_CERTIFICATE = "SAML_X509_CERTIFICATE";

	public static final String SAML_X509_CERTIFICATE_AUTH_NEEDED =
		"SAML_X509_CERTIFICATE_AUTH_NEEDED";

}