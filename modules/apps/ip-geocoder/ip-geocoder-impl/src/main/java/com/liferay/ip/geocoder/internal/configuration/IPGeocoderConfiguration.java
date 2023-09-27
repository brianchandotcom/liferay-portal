/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ip.geocoder.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Brian Wing Shun Chan
 */
@ExtendedObjectClassDefinition(category = "third-party")
@Meta.OCD(
	id = "com.liferay.ip.geocoder.internal.configuration.IPGeocoderConfiguration",
	localization = "content/Language", name = "ip-geocoder-configuration-name"
)
public interface IPGeocoderConfiguration {

	@ExtendedAttributeDefinition(
		descriptionArguments = "maxmind.com", requiredInput = true
	)
	@Meta.AD(
		description = "file-path-description[ip-geocoder]", name = "file-path",
		required = false
	)
	public String filePath();

}