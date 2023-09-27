/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Sam Ziemer
 */
@ExtendedObjectClassDefinition(
	category = "documents-and-media",
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.document.library.internal.configuration.DLFileOrderConfiguration",
	localization = "content/Language", name = "dl-file-order-configuration-name"
)
public interface DLFileOrderConfiguration {

	@Meta.AD(
		deflt = "modifiedDate", name = "order-by",
		optionLabels = {
			"create-date", "downloads", "modified-date", "size", "name"
		},
		optionValues = {
			"creationDate", "downloads", "modifiedDate", "size", "title"
		},
		required = false
	)
	public String orderByColumn();

	@Meta.AD(
		deflt = "desc", name = "sort-direction",
		optionLabels = {"ascending", "descending"},
		optionValues = {"asc", "desc"}, required = false
	)
	public String sortBy();

}