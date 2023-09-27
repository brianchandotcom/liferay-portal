/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.constants;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Map;

/**
 * @author Igor Beslic
 */
public class BatchPlannerPolicyConstants {

	public static final Map<String, String> exportPlanPolicyNameTypes =
		Collections.unmodifiableMap(
			HashMapBuilder.put(
				"containsHeaders", "checkbox"
			).put(
				"headlessEndpoint", "text"
			).put(
				"siteId", "text"
			).build());
	public static final Map<String, String> importPlanPolicyNameTypes =
		Collections.unmodifiableMap(
			HashMapBuilder.put(
				"containsHeaders", "checkbox"
			).put(
				"createStrategy", "text"
			).put(
				"delimiter", "text"
			).put(
				"enclosingCharacter", "text"
			).put(
				"headlessEndpoint", "text"
			).put(
				"onErrorFail", "checkbox"
			).put(
				"siteId", "text"
			).put(
				"updateStrategy", "text"
			).build());

}