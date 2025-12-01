/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.resource.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.util.CommentUtil;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Map;

/**
 * @author Jhosseph Gonzalez
 */
public class CommentResourceUtil {

	public static Page<com.liferay.headless.delivery.dto.v1_0.Comment>
			getComments(
				Map<String, Map<String, String>> actions, Long commentId,
				long companyId, CommentManager commentManager, String search,
				Aggregation aggregation, Filter filter, Pagination pagination,
				Portal portal, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			actions,
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"parentMessageId", String.valueOf(commentId)),
					BooleanClauseOccur.MUST);
			},
			filter, MBMessage.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setAttribute("discussion", Boolean.TRUE);
				searchContext.setAttribute(
					"searchPermissionContext", StringPool.BLANK);
				searchContext.setCompanyId(companyId);
				searchContext.setVulcanCheckPermissions(false);
			},
			sorts,
			document -> CommentUtil.toComment(
				commentManager.fetchComment(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))),
				commentManager, portal));
	}

	public static boolean isAssociated(
		String className, long classPK, Comment comment) {

		if (className.equals(comment.getClassName()) &&
			(classPK == comment.getClassPK())) {

			return true;
		}

		return false;
	}

}