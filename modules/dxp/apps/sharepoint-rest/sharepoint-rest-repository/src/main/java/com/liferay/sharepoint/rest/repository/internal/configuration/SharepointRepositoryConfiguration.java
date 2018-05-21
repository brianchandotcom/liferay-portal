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

package com.liferay.sharepoint.rest.repository.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(
	category = "collaboration", factoryInstanceLabelAttribute = "name"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.sharepoint.rest.repository.internal.configuration.SharepointRepositoryConfiguration",
	localization = "content/Language",
	name = "sharepoint-repository-configuration-name"
)
public interface SharepointRepositoryConfiguration {

	@Meta.AD(name = "name", required = false)
	public String name();

	@Meta.AD(name = "authorization-grant-endpoint", required = false)
	public String authorizationGrantEndpoint();

	@Meta.AD(name = "authorization-token-endpoint", required = false)
	public String authorizationTokenEndpoint();

	@Meta.AD(name = "client-id", required = false)
	public String clientId();

	@Meta.AD(name = "client-secret", required = false)
	public String clientSecret();

	@Meta.AD(name = "scope", required = false)
	public String scope();

	@Meta.AD(name = "tenant-id", required = false)
	public String tenantId();

	@Meta.AD(name = "site-domain", required = false)
	public String siteDomain();

	@Meta.AD(name = "resource", required = false)
	public String resource();

}