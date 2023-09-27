<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalArticleDisplay articleDisplay = (JournalArticleDisplay)request.getAttribute(WebKeys.JOURNAL_ARTICLE_DISPLAY);

String viewMode = ParamUtil.getString(request, "viewMode");
%>

<c:choose>
	<c:when test="<%= viewMode.equals(Constants.PRINT) %>">
		<aui:script>
			print();
		</aui:script>
	</c:when>
	<c:otherwise>
		<portlet:renderURL var="printPageURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="groupId" value="<%= String.valueOf(articleDisplay.getGroupId()) %>" />
			<portlet:param name="articleId" value="<%= articleDisplay.getArticleId() %>" />
			<portlet:param name="page" value="<%= String.valueOf(articleDisplay.getCurrentPage()) %>" />
			<portlet:param name="viewMode" value="<%= Constants.PRINT %>" />
		</portlet:renderURL>

		<clay:content-col
			cssClass="p-1 print-action user-tool-asset-addon-entry"
		>

			<%
			String title = LanguageUtil.format(request, "print-x", HtmlUtil.escape(articleDisplay.getTitle()));
			%>

			<clay:button
				additionalProps='<%=
					HashMapBuilder.<String, Object>put(
						"printPageURL", printPageURL
					).build()
				%>'
				aria-label="<%= title %>"
				borderless="<%= true %>"
				displayType="secondary"
				icon="print"
				propsTransformer="js/printPageButtonPropsTransformer"
				small="<%= true %>"
				title="<%= title %>"
				type="button"
			/>
		</clay:content-col>
	</c:otherwise>
</c:choose>