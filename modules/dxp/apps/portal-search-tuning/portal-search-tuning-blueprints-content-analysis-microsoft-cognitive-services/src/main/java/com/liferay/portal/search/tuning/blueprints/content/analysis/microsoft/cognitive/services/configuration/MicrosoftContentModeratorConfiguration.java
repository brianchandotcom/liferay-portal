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

package com.liferay.portal.search.tuning.blueprints.content.analysis.microsoft.cognitive.services.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Petteri Karttunen
 */
@ExtendedObjectClassDefinition(
	category = "search-experiences",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.portal.search.tuning.blueprints.content.analysis.microsoft.cognitive.services.configuration.MicrosoftContentModeratorConfiguration",
	localization = "content/Language",
	name = "microsoft-content-moderator-configuration-name"
)
public interface MicrosoftContentModeratorConfiguration {

	@Meta.AD(
		deflt = "false", description = "is-enabled-desc",
		name = "is-enabled-name", required = false
	)
	public boolean isEnabled();

	@Meta.AD(
		deflt = "", description = "subscription-key-desc",
		name = "subscription-key-name", required = false
	)
	public String subscriptionKey();

	@Meta.AD(
		deflt = "https://westus.api.cognitive.microsoft.com/contentmoderator/moderate/v1.0/ProcessText/Screen",
		description = "endpoint-desc", name = "endpoint-name", required = false
	)
	public String apiEndpoint();

	@Meta.AD(
		deflt = "true", description = "classify-by-default-desc",
		name = "classify-by-default-name", required = false
	)
	public boolean classifyByDefault();

	@Meta.AD(
		deflt = "true", description = "pii-by-default-desc",
		name = "pii-by-default-name", required = false
	)
	public boolean pIIByDefault();

}