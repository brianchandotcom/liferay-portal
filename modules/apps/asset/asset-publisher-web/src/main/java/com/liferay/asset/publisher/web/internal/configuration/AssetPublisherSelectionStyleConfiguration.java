/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.asset.publisher.web.internal.constants.AssetPublisherSelectionStyleConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Lourdes Fernández Besada
 */
@ExtendedObjectClassDefinition(generateUI = false)
@Meta.OCD(
	id = "com.liferay.asset.publisher.web.internal.configuration.AssetPublisherSelectionStyleConfiguration"
)
public interface AssetPublisherSelectionStyleConfiguration {

	@Meta.AD(
		deflt = AssetPublisherSelectionStyleConstants.TYPE_ASSET_LIST,
		required = false
	)
	public String defaultSelectionStyle();

}