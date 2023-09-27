/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Jonathan McCann
 */
@ExtendedObjectClassDefinition(category = "dynamic-data-lists")
@Meta.OCD(
	id = "com.liferay.dynamic.data.lists.internal.configuration.DDLServiceConfiguration",
	localization = "content/Language", name = "ddl-service-configuration-name"
)
public interface DDLServiceConfiguration {

	@Meta.AD(deflt = "true", name = "add-default-structures", required = false)
	public boolean addDefaultStructures();

}