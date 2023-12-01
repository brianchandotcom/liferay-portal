/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.forms.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Inácio Nery
 */
@ExtendedObjectClassDefinition(category = "workflow")
@Meta.OCD(
	id = "com.liferay.portal.workflow.kaleo.forms.web.internal.configuration.KaleoFormsWebConfiguration",
	localization = "content/Language", name = "kaleo-web-configuration-name"
)
public interface KaleoFormsWebConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "if-checked,-the-default-language-of-kaleo-forms-will-be-changeable",
		name = "changeable-default-language", required = false
	)
	public boolean changeableDefaultLanguage();

	@Meta.AD(
		deflt = "list", name = "default-display-view", optionLabels = "list",
		optionValues = "list", required = false
	)
	public String defaultDisplayView();

}