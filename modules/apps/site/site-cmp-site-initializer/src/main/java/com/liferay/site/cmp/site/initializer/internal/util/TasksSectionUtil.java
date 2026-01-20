/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import java.time.LocalDate;
import java.time.ZoneId;

import java.util.Map;

/**
 * @author Carolina Barbosa
 */
public class TasksSectionUtil {

	public static String getSearchURL(
		GroupedModel groupedModel, ObjectDefinition taskObjectDefinition) {

		StringBundler sb = new StringBundler(11);

		sb.append("/o/search/v1.0/search?emptySearch=true");

		if (groupedModel == null) {
			sb.append("&entryClassNames=");
			sb.append(HtmlUtil.escapeURL(taskObjectDefinition.getClassName()));
			sb.append(StringPool.COMMA);
			sb.append(KaleoTaskInstanceToken.class.getName());
		}

		sb.append("&filter=(objectDefinitionId eq ");
		sb.append(taskObjectDefinition.getObjectDefinitionId());

		if (groupedModel != null) {
			sb.append(" and scopeGroupId eq ");
			sb.append(groupedModel.getGroupId());
		}
		else {
			sb.append(" or keywords/any(k:startswith(k, '");
			sb.append(taskObjectDefinition.getExternalReferenceCode());
			sb.append("'))");
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	public static Map<String, Object> getSearchURLProperties(
		GroupedModel groupedModel, ObjectDefinition taskObjectDefinition) {

		String searchURL = getSearchURL(groupedModel, taskObjectDefinition);

		return HashMapBuilder.<String, Object>put(
			"blockedCountURL", searchURL + " and cmpState eq 'blocked'"
		).put(
			"inProgressCountURL", searchURL + " and cmpState eq 'inProgress'"
		).put(
			"overdueCountURL",
			StringBundler.concat(
				searchURL, " and cmpDueDate lt ",
				LocalDate.now(
				).atStartOfDay(
					ZoneId.systemDefault()
				).toInstant(),
				" and cmpState ne 'done'")
		).put(
			"totalCountURL", searchURL
		).build();
	}

}