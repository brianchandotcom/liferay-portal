/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Igor Beslic
 */
@ExtendedObjectClassDefinition(category = "dispatch")
@Meta.OCD(
	id = "com.liferay.dispatch.configuration.DispatchConfiguration",
	localization = "content/Language", name = "dispatch-configuration-name"
)
public interface DispatchConfiguration {

	@Meta.AD(
		deflt = ".zip,.rar,.jar,.properties", name = "file-extensions",
		required = false
	)
	public String[] fileExtensions();

	@Meta.AD(deflt = "50242880", name = "file-max-size", required = false)
	public long fileMaxSize();

}