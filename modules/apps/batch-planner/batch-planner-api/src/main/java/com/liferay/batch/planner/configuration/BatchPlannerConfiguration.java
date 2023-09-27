/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Igor Beslic
 */
@ExtendedObjectClassDefinition(category = "batch-planner")
@Meta.OCD(
	id = "com.liferay.batch.planner.configuration.BatchPlannerConfiguration",
	localization = "content/Language", name = "batch-planner-configuration-name"
)
public interface BatchPlannerConfiguration {

	@Meta.AD(deflt = "102400", name = "import-file-max-size", required = false)
	public long importFileMaxSize();

}