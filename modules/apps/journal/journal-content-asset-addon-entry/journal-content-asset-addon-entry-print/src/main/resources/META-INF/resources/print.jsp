<%--
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

		<div class="autofit-col print-action user-tool-asset-addon-entry">
			<liferay-ui:icon
				icon="print"
				label="<%= true %>"
				linkCssClass="btn btn-monospaced btn-outline-borderless btn-outline-secondary btn-sm"
				markupView="lexicon"
				message='<%= LanguageUtil.format(request, "print-x-x", new Object[] {"hide-accessible", HtmlUtil.escape(articleDisplay.getTitle())}, false) %>'
				url='<%= "javascript:" + renderResponse.getNamespace() + "printPage();" %>'
			/>
		</div>

		<aui:script>
			function <portlet:namespace />printPage() {
				window.open('<%= printPageURL %>', '', 'directories=0,height=480,left=80,location=1,menubar=1,resizable=1,scrollbars=yes,status=0,toolbar=0,top=180,width=640');
			}
		</aui:script>
	</c:otherwise>
</c:choose>