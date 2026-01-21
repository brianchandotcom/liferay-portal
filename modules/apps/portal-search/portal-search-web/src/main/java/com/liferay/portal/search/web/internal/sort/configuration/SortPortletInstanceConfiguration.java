/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.sort.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Olivia Yu
 */
@ExtendedObjectClassDefinition(
	category = "search",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.portal.search.web.internal.sort.configuration.SortPortletInstanceConfiguration",
	localization = "content/Language",
	name = "sort-portlet-instance-configuration-name"
)
public interface SortPortletInstanceConfiguration {

	@Meta.AD(name = "display-style", required = false)
	public String displayStyle();

	@Meta.AD(
		deflt = "", name = "display-style-group-external-reference-code",
		required = false
	)
	public String displayStyleGroupExternalReferenceCode();

	@Meta.AD(
		deflt = "0", description = "display-style-group-id-description",
		name = "display-style-group-id", required = false
	)
	public long displayStyleGroupId();

}