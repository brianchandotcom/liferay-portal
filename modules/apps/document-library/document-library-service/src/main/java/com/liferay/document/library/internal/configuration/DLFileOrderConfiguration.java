/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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