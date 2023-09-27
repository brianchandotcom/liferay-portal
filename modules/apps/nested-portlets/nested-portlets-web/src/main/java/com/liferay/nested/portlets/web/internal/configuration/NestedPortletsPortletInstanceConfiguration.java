/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.nested.portlets.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Juergen Kappler
 */
@ExtendedObjectClassDefinition(
	category = "display-content",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.nested.portlets.web.internal.configuration.NestedPortletsPortletInstanceConfiguration",
	localization = "content/Language",
	name = "nested-portlets-portlet-instance-configuration-name"
)
public interface NestedPortletsPortletInstanceConfiguration {

	@Meta.AD(
		deflt = "2_columns_i", id = "layout.template.default",
		name = "layout-template-id", required = false
	)
	public String layoutTemplateId();

	@Meta.AD(
		deflt = "1_column|1_column_dynamic", id = "layout.template.unsupported",
		name = "layout-templates-unsupported", required = false
	)
	public String[] layoutTemplatesUnsupported();

}