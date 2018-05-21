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

package com.liferay.saml.runtime.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Carlos Sierra Andrés
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
	id = "com.liferay.saml.runtime.configuration.SamlConfiguration",
	localization = "content/Language", name = "saml-configuration-name"
)
public interface SamlConfiguration {

	public static String KEYSTORE_PATH_DEFAULT =
		"${liferay.home}/data/keystore.jks";

	@Meta.AD(
		deflt = KEYSTORE_PATH_DEFAULT,
		description = "saml-key-store-path-description",
		id = "saml.keystore.path", name = "saml-key-store-path",
		required = false
	)
	public String keyStorePath();

	@Meta.AD(
		deflt = "liferay", id = "saml.keystore.password",
		name = "saml-key-store-password", required = false
	)
	public String keyStorePassword();

	@Meta.AD(
		deflt = "jks", id = "saml.keystore.type", name = "saml-key-store-type",
		required = false
	)
	public String keyStoreType();

	/**
	 * Set the interval in minutes on how often SamlIdpSsoSessionMessageListener
	 * will be run to check for and delete SAML IDP SSO sessions that are older
	 * than the maximum age set in the property "saml.idp.sso.session.max.age".
	 */
	@Meta.AD(
		deflt = "60",
		description = "saml-idp-sso-session-check-interval-description",
		id = "saml.idp.sso.session.check.interval",
		name = "saml-idp-sso-session-check-interval", required = false
	)
	public int getIdpSsoSessionCheckInterval();

	@Meta.AD(
		deflt = "300",
		description = "saml-metadata-refresh-interval-description",
		id = "saml.metadata.refresh.interval",
		name = "saml-metadata-refresh-interval", required = false
	)
	public int getMetadataRefreshInterval();

	/**
	 * Set the duration in milliseconds to prevent replaying messages.
	 */
	@Meta.AD(
		deflt = "3600000",
		description = "saml-replay-cache-duration-description",
		id = "saml.replay.cache.duration", name = "saml-replay-cache-duration",
		required = false
	)
	public int getReplayChacheDuration();

	/**
	 * Set the interval in minutes on how often SamlSpAuthRequestMessageListener
	 * will be run to check for and delete SAML SP authentication requests that
	 * are older than the maximum age set in the property
	 * "saml.sp.auth.request.max.age".
	 */
	@Meta.AD(
		deflt = "60",
		description = "saml-sp-auth-request-check-interval-description",
		id = "saml.sp.auth.request.check.interval",
		name = "saml-sp-auth-request-check-interval", required = false
	)
	public int getSpAuthRequestCheckInterval();

	/**
	 * Set the duration in milliseconds to remove and expire SAML SP
	 * authentication requests.
	 */
	@Meta.AD(
		deflt = "86400000",
		description = "saml-sp-auth-request-max-age-description",
		id = "saml.sp.auth.request.max.age",
		name = "saml-sp-auth-request-max-age", required = false
	)
	public int getSpAuthRequestMaxAge();

	/**
	 * Set the interval in minutes on how often SamlSpMessageMessageListener
	 * will be run to check for and delete expired SAML SP messages.
	 */
	@Meta.AD(
		deflt = "60",
		description = "saml-sp-message-check-interval-description",
		id = "saml.sp.message.check.interval",
		name = "saml-sp-message-check-interval", required = false
	)
	public int getSpMessageCheckInterval();

}