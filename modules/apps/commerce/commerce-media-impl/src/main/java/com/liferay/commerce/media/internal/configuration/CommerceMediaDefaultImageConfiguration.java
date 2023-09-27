/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.media.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.commerce.media.constants.CommerceMediaConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alec Sloan
 */
@ExtendedObjectClassDefinition(
	category = "catalog", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	description = "commerce-default-images-configuration-description",
	id = CommerceMediaConstants.SERVICE_NAME, localization = "content/Language",
	name = "commerce-default-images-configuration-name"
)
public interface CommerceMediaDefaultImageConfiguration {

	@Meta.AD(deflt = "0", name = "default-catalog-image", required = false)
	public long defaultFileEntryId();

}