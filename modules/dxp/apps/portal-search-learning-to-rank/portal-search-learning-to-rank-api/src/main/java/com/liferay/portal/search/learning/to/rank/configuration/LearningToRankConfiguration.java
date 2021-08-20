/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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