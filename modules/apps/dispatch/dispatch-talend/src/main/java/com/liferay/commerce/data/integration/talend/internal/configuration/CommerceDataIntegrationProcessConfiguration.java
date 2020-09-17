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

package com.liferay.commerce.data.integration.talend.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author guywandji
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "data-integration",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.commerce.data.integration.talend.internal.configuration.CommerceDataIntegrationProcessConfiguration",
	localization = "content/Language",
	name = "commerce-data-integration-process-configuration-name"
)
public interface CommerceDataIntegrationProcessConfiguration {

	@Meta.AD(
		deflt = ".zip,.rar,.jar,.properties", name = "file-extensions",
		required = false
	)
	public String[] imageExtensions();

	@Meta.AD(deflt = "50242880", name = "file-max-size", required = false)
	public long imageMaxSize();

}