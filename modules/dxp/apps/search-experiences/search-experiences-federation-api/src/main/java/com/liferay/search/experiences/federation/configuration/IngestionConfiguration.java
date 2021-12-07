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

package com.liferay.search.experiences.federation.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Gustavo Lima
 */
@ExtendedObjectClassDefinition(category = "search-experiences")
@Meta.OCD(
	id = "com.liferay.search.experiences.federation.configuration.IngestionConfiguration",
	localization = "content/Language"
)
@ProviderType
public interface IngestionConfiguration {

	@Meta.AD(required = false)
	public String tagTransformations();

	@Meta.AD(required = false)
	public String tagEquivalences();

}