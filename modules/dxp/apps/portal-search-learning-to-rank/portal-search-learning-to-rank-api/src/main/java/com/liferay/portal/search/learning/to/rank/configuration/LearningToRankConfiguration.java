/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.learning.to.rank.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adam Brandizzi
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.learning.to.rank.configuration.LearningToRankConfiguration",
	localization = "content/Language",
	name = "learning-to-rank-configuration-name"
)
public interface LearningToRankConfiguration {

	@Meta.AD(
		deflt = "false", description = "enabled-help[learning-to-rank]",
		name = "enabled", required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "test_6", description = "model-help", name = "model",
		required = false
	)
	public String model();

}