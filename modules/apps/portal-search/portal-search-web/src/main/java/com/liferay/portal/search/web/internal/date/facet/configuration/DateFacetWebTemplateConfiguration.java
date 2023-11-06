/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.date.facet.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Olivia Yu
 */
@ExtendedObjectClassDefinition(generateUI = false)
@Meta.OCD(
	id = "com.liferay.portal.search.web.internal.date.facet.configuration.DateFacetWebTemplateConfiguration"
)
public interface DateFacetWebTemplateConfiguration {

	@Meta.AD(
		deflt = "date-facet-link-ftl", name = "date-facet-template-key-default",
		required = false
	)
	public String dateFacetTemplateKeyDefault();

}