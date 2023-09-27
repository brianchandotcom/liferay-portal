/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.demo.data.creator.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Cheryl Tang
 */
@ExtendedObjectClassDefinition(generateUI = false)
@Meta.OCD(
	id = "com.liferay.analytics.demo.data.creator.configuration.AnalyticsDemoDataCreatorConfiguration"
)
public interface AnalyticsDemoDataCreatorConfiguration {

	@Meta.AD
	public String pathToUsersCSV();

	@Meta.AD
	public String virtualHostname();

}