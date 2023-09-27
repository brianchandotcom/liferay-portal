/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.display.context.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;

/**
 * @author Marcela Cunha
 */
public class LocalizedJSONArrayUtil {

	public static JSONObject getFilterOperatorsJSONObject(Locale locale) {
		return JSONUtil.put(
			"dateOperators",
			JSONUtil.put(
				JSONUtil.put(
					"label", LanguageUtil.get(locale, "range")
				).put(
					"value", "range"
				))
		).put(
			"numericOperators",
			JSONUtil.putAll(
				JSONUtil.put(
					"label", LanguageUtil.get(locale, "is-equal-to")
				).put(
					"value", "eq"
				),
				JSONUtil.put(
					"label", LanguageUtil.get(locale, "is-not-equal-to")
				).put(
					"value", "ne"
				))
		).put(
			"picklistOperators",
			JSONUtil.putAll(
				JSONUtil.put(
					"label", LanguageUtil.get(locale, "excludes")
				).put(
					"value", "excludes"
				),
				JSONUtil.put(
					"label", LanguageUtil.get(locale, "includes")
				).put(
					"value", "includes"
				))
		);
	}

	public static JSONArray getWorkflowStatusJSONArray(Locale locale) {
		return JSONUtil.putAll(
			JSONUtil.put(
				"label",
				LanguageUtil.get(locale, WorkflowConstants.LABEL_APPROVED)
			).put(
				"value", WorkflowConstants.STATUS_APPROVED
			),
			JSONUtil.put(
				"label",
				LanguageUtil.get(locale, WorkflowConstants.LABEL_DENIED)
			).put(
				"value", WorkflowConstants.STATUS_DENIED
			),
			JSONUtil.put(
				"label", LanguageUtil.get(locale, WorkflowConstants.LABEL_DRAFT)
			).put(
				"value", WorkflowConstants.STATUS_DRAFT
			),
			JSONUtil.put(
				"label",
				LanguageUtil.get(locale, WorkflowConstants.LABEL_EXPIRED)
			).put(
				"value", WorkflowConstants.STATUS_EXPIRED
			),
			JSONUtil.put(
				"label",
				LanguageUtil.get(locale, WorkflowConstants.LABEL_INACTIVE)
			).put(
				"value", WorkflowConstants.STATUS_INACTIVE
			),
			JSONUtil.put(
				"label",
				LanguageUtil.get(locale, WorkflowConstants.LABEL_INCOMPLETE)
			).put(
				"value", WorkflowConstants.STATUS_INCOMPLETE
			),
			JSONUtil.put(
				"label",
				LanguageUtil.get(locale, WorkflowConstants.LABEL_IN_TRASH)
			).put(
				"value", WorkflowConstants.STATUS_IN_TRASH
			),
			JSONUtil.put(
				"label",
				LanguageUtil.get(locale, WorkflowConstants.LABEL_PENDING)
			).put(
				"value", WorkflowConstants.STATUS_PENDING
			),
			JSONUtil.put(
				"label",
				LanguageUtil.get(locale, WorkflowConstants.LABEL_SCHEDULED)
			).put(
				"value", WorkflowConstants.STATUS_SCHEDULED
			));
	}

}