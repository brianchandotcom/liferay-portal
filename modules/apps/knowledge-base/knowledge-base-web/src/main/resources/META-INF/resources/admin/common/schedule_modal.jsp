<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/common/init.jsp" %>

<%
KBArticle kbArticle = KBArticleServiceUtil.getLatestKBArticle(ParamUtil.getLong(request, "resourcePrimKey"), WorkflowConstants.STATUS_ANY);

String displayDateString = StringPool.BLANK;

if (kbArticle.getDisplayDate() != null) {
	displayDateString = dateFormatDateTime.format(kbArticle.getDisplayDate());
}
%>

<div>
	<react:component
		module="admin/js/components/ScheduleModal"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"displayDate", displayDateString
			).put(
				"isScheduled", kbArticle.isScheduled()
			).build()
		%>'
	/>
</div>