/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.cmis.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(category = "file-storage")
@Meta.OCD(
	id = "com.liferay.document.library.repository.cmis.configuration.CMISRepositoryConfiguration",
	localization = "content/Language",
	name = "cmis-repository-configuration-name"
)
public interface CMISRepositoryConfiguration {

	@Meta.AD(
		deflt = "1", description = "delete-depth-description",
		name = "delete-depth-name", required = false
	)
	public int deleteDepth();

}