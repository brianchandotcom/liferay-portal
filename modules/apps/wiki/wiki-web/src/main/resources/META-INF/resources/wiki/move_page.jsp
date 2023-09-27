<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/wiki/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);

WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

String title = wikiPage.getTitle();

String newTitle = ParamUtil.get(request, "newTitle", StringPool.BLANK);
%>

<liferay-util:include page="/wiki/top_links.jsp" servletContext="<%= application %>" />

<div class="container-fluid container-fluid-max-xl container-form-lg">
	<div class="sheet">
		<liferay-ui:error exception="<%= DuplicatePageException.class %>" message="there-is-already-a-page-with-the-specified-title" />

		<liferay-ui:error exception="<%= PageTitleException.class %>" message="please-enter-a-valid-title" />

		<%@ include file="/wiki/page_name.jspf" %>

		<portlet:actionURL name="/wiki/move_page" var="movePageURL" />

		<liferay-ui:tabs
			names="rename,change-parent"
			refresh="<%= false %>"
		>

			<%
			boolean pending = false;

			boolean hasWorkflowDefinitionLink = WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, WikiPage.class.getName());

			if (hasWorkflowDefinitionLink) {
				WikiPage latestWikiPage = WikiPageServiceUtil.getPage(wikiPage.getNodeId(), wikiPage.getTitle(), null);

				pending = latestWikiPage.isPending();
			}
			%>

			<liferay-ui:section>
				<%@ include file="/wiki/rename_page.jspf" %>
			</liferay-ui:section>

			<liferay-ui:section>
				<%@ include file="/wiki/change_page_parent.jspf" %>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</div>
</div>