/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.organization.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Luca Pellizzon
 */
@ExtendedObjectClassDefinition(
	category = "users",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.commerce.organization.web.internal.configuration.CommerceOrganizationPortletInstanceConfiguration",
	localization = "content/Language",
	name = "commerce-organization-web-portlet-instance-configuration-name"
)
public interface CommerceOrganizationPortletInstanceConfiguration {

	@Meta.AD(deflt = "0", name = "root-organization-id", required = false)
	public String rootOrganizationId();

}