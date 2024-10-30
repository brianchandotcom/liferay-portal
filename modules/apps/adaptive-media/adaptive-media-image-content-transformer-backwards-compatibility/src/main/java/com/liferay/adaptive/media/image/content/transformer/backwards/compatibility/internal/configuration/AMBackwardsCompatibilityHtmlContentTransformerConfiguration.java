/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.content.transformer.backwards.compatibility.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(
	category = "adaptive-media",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.adaptive.media.image.content.transformer.backwards.compatibility.internal.configuration.AMBackwardsCompatibilityHtmlContentTransformerConfiguration",
	localization = "content/Language",
	name = "adaptive-media-backwards-compatibility-html-content-transformer-configuration-name"
)
public interface AMBackwardsCompatibilityHtmlContentTransformerConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "adaptive-media-backwards-compatibility-html-content-transformer-enabled-description",
		name = "enabled", required = false
	)
	public boolean enabled();

}