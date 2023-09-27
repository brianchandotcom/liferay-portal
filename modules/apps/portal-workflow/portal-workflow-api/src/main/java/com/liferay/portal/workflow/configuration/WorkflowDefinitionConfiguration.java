/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tomas Polesovsky
 */
@ExtendedObjectClassDefinition(category = "workflow")
@Meta.OCD(
	id = "com.liferay.portal.workflow.configuration.WorkflowDefinitionConfiguration",
	localization = "content/Language",
	name = "workflow-definition-configuration-name"
)
public interface WorkflowDefinitionConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "allow-administrators-to-publish-and-edit-workflows-description",
		id = "company.administrator.can.publish",
		name = "allow-administrators-to-publish-and-edit-workflows",
		required = false
	)
	public boolean companyAdministratorCanPublish();

}