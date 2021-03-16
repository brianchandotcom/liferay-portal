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

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants;

import com.liferay.portal.search.tuning.blueprints.constants.BlueprintTypes;

/**
 * @author Petteri Karttunen
 */
public class BlueprintsAdminTabNames {

	public static final String BLUEPRINTS = "blueprints";

	public static final String ELEMENTS = "elements";

	public static final String getTabName(int blueprintType) {
		if (blueprintType == BlueprintTypes.BLUEPRINT) {
			return BLUEPRINTS;
		}

		return ELEMENTS;
	}

}