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

package com.liferay.saml.util;

/**
 * @author Mika Koivisto
 */
public interface PortletPropsKeys {

	public static final String SAML_ENABLED = "saml.enabled";

	public static final String SAML_ENTITY_ID = "saml.entity.id";

	public static final String SAML_IDP_ASSERTION_LIFETIME =
		"saml.idp.assertion.lifetime";

	public static final String SAML_IDP_AUTHN_REQUEST_SIGNATURE_REQUIRED =
		"saml.idp.authn.request.signature.required";

	public static final String SAML_IDP_METADATA_ATTRIBUTE_NAMES =
		"saml.idp.metadata.attribute.names";

	public static final String SAML_IDP_METADATA_ATTRIBUTES_ENABLED =
		"saml.idp.metadata.attributes.enabled";

	public static final String SAML_IDP_METADATA_ATTRIBUTES_NAMESPACE_ENABLED =
		"saml.idp.metadata.attributes.namespace.enabled";

	public static final String SAML_IDP_METADATA_NAME_ID_ATTRIBUTE =
		"saml.idp.metadata.name.id.attribute";

	public static final String SAML_IDP_METADATA_NAME_ID_FORMAT =
		"saml.idp.metadata.name.id.format";

	public static final String SAML_IDP_METADATA_SALESFORCE_ATTRIBUTES_ENABLED =
		"saml.idp.metadata.salesforce.attributes.enabled";

	public static final String SAML_IDP_METADATA_SALESFORCE_LOGOUT_URL =
		"saml.idp.metadata.salesforce.logout.url";

	public static final String SAML_IDP_METADATA_SALESFORCE_SSO_START_PAGE =
		"saml.idp.metadata.salesforce.sso.start.page";

	public static final String SAML_IDP_METADATA_SESSION_KEEP_ALIVE_URL =
		"saml.idp.metadata.session.keepalive.url";

	public static final String SAML_IDP_SESSION_MAXIMUM_AGE =
		"saml.idp.session.maximum.age";

	public static final String SAML_IDP_SESSION_TIMEOUT =
		"saml.idp.session.timeout";

	public static final String SAML_IDP_SSO_SESSION_MAX_AGE =
		"saml.idp.sso.session.max.age";

	public static final String SAML_KEYSTORE_CREDENTIAL_PASSWORD =
		"saml.keystore.credential.password";

	public static final String SAML_KEYSTORE_MANAGER_IMPL =
		"saml.keystore.manager.impl";

	public static final String SAML_KEYSTORE_PASSWORD =
		"saml.keystore.password";

	public static final String SAML_KEYSTORE_PATH = "saml.keystore.path";

	public static final String SAML_KEYSTORE_TYPE = "saml.keystore.type";

	public static final String SAML_METADATA_PATHS =
		"saml.runtime.metadata.paths";

	public static final String SAML_REPLAY_CACHE_DURATION =
		"saml.replay.cache.duration";

	public static final String SAML_ROLE = "saml.role";

	public static final String SAML_SIGN_METADATA = "saml.sign.metadata";

	public static final String SAML_SP_ASSERTION_SIGNATURE_REQUIRED =
		"saml.sp.assertion.signature.required";

	public static final String SAML_SP_AUTH_REQUEST_MAX_AGE =
		"saml.sp.auth.request.max.age";

	public static final String SAML_SP_CLOCK_SKEW = "saml.sp.clock.skew";

	public static final String SAML_SP_DEFAULT_IDP_ENTITY_ID =
		"saml.sp.default.idp.entity.id";

	public static final String SAML_SP_LDAP_IMPORT_ENABLED =
		"saml.sp.ldap.import.enabled";

	public static final String SAML_SP_NAME_ID_FORMAT =
		"saml.sp.name.id.format";

	public static final String SAML_SP_SIGN_AUTHN_REQUEST =
		"saml.sp.sign.authn.request";

	public static final String SAML_SP_USER_ATTRIBUTE_MAPPINGS =
		"saml.sp.user.attribute.mappings";

	public static final String SAML_SSL_REQUIRED = "saml.ssl.required";

}