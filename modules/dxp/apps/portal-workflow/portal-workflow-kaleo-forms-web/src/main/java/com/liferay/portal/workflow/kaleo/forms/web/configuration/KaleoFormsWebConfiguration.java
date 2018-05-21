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

package com.liferay.portal.workflow.kaleo.forms.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Inácio Nery
 */
@ExtendedObjectClassDefinition(category = "forms-and-workflow")
@Meta.OCD(
	id = "com.liferay.portal.workflow.kaleo.forms.web.configuration.KaleoFormsWebConfiguration",
	localization = "content/Language", name = "kaleo-web-configuration-name"
)
public interface KaleoFormsWebConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "changeable-default-language-description",
		name = "changeable-default-language", required = false
	)
	public boolean changeableDefaultLanguage();

	@Meta.AD(
		deflt = "list", name = "default-display-view", optionLabels = "List",
		optionValues = "list", required = false
	)
	public String defaultDisplayView();

}