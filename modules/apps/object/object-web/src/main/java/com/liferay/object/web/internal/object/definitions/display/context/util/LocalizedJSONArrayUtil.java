/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.web.internal.object.definitions.display.context.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;

/**
 * @author Marcela Cunha
 */
public class LocalizedJSONArrayUtil {

	public static JSONArray getDateOperatorsJSONArray(Locale locale) {
		return JSONUtil.put(
			JSONUtil.put(
				"label", LanguageUtil.get(locale, "range")
			).put(
				"value", "range"
			));
	}

	public static JSONArray getNumericOperatorsJSONArray(Locale locale) {
		return JSONUtil.putAll(
			JSONUtil.put(
				"label", LanguageUtil.get(locale, "equals-to")
			).put(
				"value", "eq"
			),
			JSONUtil.put(
				"label", LanguageUtil.get(locale, "not-equals-to")
			).put(
				"value", "ne"
			));
	}

	public static JSONArray getPicklistOperatorsJSONArray(Locale locale) {
		return JSONUtil.putAll(
			JSONUtil.put(
				"label", LanguageUtil.get(locale, "excludes")
			).put(
				"value", "excludes"
			),
			JSONUtil.put(
				"label", LanguageUtil.get(locale, "includes")
			).put(
				"value", "includes"
			));
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