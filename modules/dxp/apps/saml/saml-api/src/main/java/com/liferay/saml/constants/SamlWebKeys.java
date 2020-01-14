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

package com.liferay.saml.constants;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Mika Koivisto
 */
@ProviderType
public interface SamlWebKeys {

	public static final String FORCE_REAUTHENTICATION =
		"FORCE_REAUTHENTICATION";

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

	public static final String SAML_SP_IDP_CONNECTIONS =
		"SAML_SP_IDP_CONNECTIONS";

	public static final String SAML_SP_IDP_CONNECTIONS_COUNT =
		"SAML_SP_IDP_CONNECTIONS_COUNT";

	public static final String SAML_SP_NAME_ID_FORMAT =
		"SAML_SP_NAME_ID_FORMAT";

	public static final String SAML_SP_NAME_ID_VALUE = "SAML_SP_NAME_ID_VALUE";

	public static final String SAML_SP_SESSION_KEY = "SAML_SP_SESSION_KEY";

	public static final String SAML_SSO_ERROR = "SAML_SSO_ERROR";

	public static final String SAML_SSO_LOGIN_CONTEXT =
		"SAML_SSO_LOGIN_CONTEXT";

	public static final String SAML_SSO_REQUEST_CONTEXT =
		"SAML_SSO_REQUEST_CONTEXT";

	public static final String SAML_SSO_SESSION_ID = "SAML_SSO_SESSION_ID";

	public static final String SAML_SUBJECT_SCREEN_NAME =
		"SAML_SUBJECT_SCREEN_NAME";

	public static final String SAML_X509_CERTIFICATE = "SAML_X509_CERTIFICATE";

}