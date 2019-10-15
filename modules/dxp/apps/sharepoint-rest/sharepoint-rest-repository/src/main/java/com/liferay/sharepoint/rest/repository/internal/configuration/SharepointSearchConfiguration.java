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
	category = "documents-and-media", factoryInstanceLabelAttribute = "name"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.sharepoint.rest.repository.internal.configuration.SharepointSearchConfiguration",
	localization = "content/Language",
	name = "sharepoint-search-configuration-name"
)
public interface SharepointSearchConfiguration {

	@Meta.AD(
		deflt = "8413cd39-2156-4e00-b54d-11efd9abdb89",
		name = "sharepoint-results-source-id", required = false
	)
	public String sharepointResultsSourceId();

}