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

package com.liferay.commerce.user.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "commerce", factoryInstanceLabelAttribute = "roleName",
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.commerce.user.web.internal.configuration.CommerceRoleGroupServiceConfiguration",
	localization = "content/Language",
	name = "commerce-role-group-service-configuration-name"
)
public interface CommerceRoleGroupServiceConfiguration {

	@Meta.AD(required = false)
	public String roleName();

}