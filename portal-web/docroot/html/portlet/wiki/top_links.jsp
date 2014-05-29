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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String strutsAction = ParamUtil.getString(request, "struts_action");

String strutsPath = StringPool.BLANK;

if (Validator.isNotNull(strutsAction)) {
	int pos = strutsAction.indexOf(StringPool.SLASH, 1);

	if (pos != -1) {
		strutsPath = strutsAction.substring(0, pos);
	}
}

WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

List<WikiNode> nodes = WikiUtil.getNodes(wikiConfigurationDisplayContext.getAllNodes(), wikiPortletInstanceSettings.getHiddenNodes(), permissionChecker);

boolean print = ParamUtil.getString(request, "viewMode").equals(Constants.PRINT);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("nodeName", node.getName());

long categoryId = ParamUtil.getLong(request, "categoryId");

if (categoryId > 0) {
	portletURL.setParameter("categoryId", "0");
}
%>

<c:if test='<%= !strutsAction.endsWith("view_page_activities") && !strutsAction.endsWith("view_page_attachments") %>'>
	<portlet:actionURL var="undoTrashURL">
		<portlet:param name="struts_action" value="/wiki/edit_page" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
	</portlet:actionURL>

	<liferay-ui:trash-undo portletURL="<%= undoTrashURL %>" />
</c:if>

<c:if test="<%= portletName.equals(PortletKeys.WIKI_ADMIN) %>">
	<liferay-ui:header
		backURL="<%= portletURL.toString() %>"
		localizeTitle="<%= false %>"
		title="<%= node.getName() %>"
	/>
</c:if>

<c:if test="<%= !print %>">
	<c:if test="<%= (nodes.size() > 1) && portletName.equals(PortletKeys.WIKI) %>">
		<aui:nav cssClass="nav-tabs">

			<%
			for (int i = 0; i < nodes.size(); i++) {
				WikiNode curNode = nodes.get(i);

				String cssClass = StringPool.BLANK;

				if (curNode.getNodeId() == node.getNodeId()) {
					cssClass = "active";
				}
			%>

				<portlet:renderURL var="viewPageURL">
					<portlet:param name="struts_action" value="/wiki/view" />
					<portlet:param name="nodeName" value="<%= curNode.getName() %>" />
					<portlet:param name="title" value="<%= WikiPageConstants.FRONT_PAGE %>" />
				</portlet:renderURL>

				<aui:nav-item cssClass="<%= cssClass %>" href="<%= viewPageURL %>" label="<%= HtmlUtil.escape(curNode.getName()) %>" />

			<%
			}
			%>

		</aui:nav>
	</c:if>

	<aui:nav-bar>
		<aui:nav cssClass="navbar-nav">

			<%
			PortletURL frontPageURL = PortletURLUtil.clone(portletURL, renderResponse);

			String label = WikiPageConstants.FRONT_PAGE;
			boolean selected = (Validator.isNull(strutsAction) || (wikiPage != null) && wikiPage.getTitle().equals(label));

			frontPageURL.setParameter("struts_action", "/wiki/view");
			frontPageURL.setParameter("title", WikiPageConstants.FRONT_PAGE);
			frontPageURL.setParameter("tag", StringPool.BLANK);
			%>

			<aui:nav-item cssClass='<%= selected ? "active" : StringPool.BLANK %>' href="<%= frontPageURL.toString() %>" label="<%= label %>" selected="<%= selected %>" />

			<%
			label = "recent-changes";
			selected = strutsAction.equals(strutsPath + "/view_recent_changes");

			portletURL.setParameter("struts_action", "/wiki/view_recent_changes");
			%>

			<aui:nav-item cssClass='<%= selected ? "active" : StringPool.BLANK %>' href="<%= portletURL.toString() %>" label="<%= label %>" selected="<%= selected %>" />

			<%
			label = "all-pages";
			selected = strutsAction.equals(strutsPath + "/view_all_pages");

			portletURL.setParameter("struts_action", "/wiki/view_all_pages");
			%>

			<aui:nav-item cssClass='<%= selected ? "active" : StringPool.BLANK %>' href="<%= portletURL.toString() %>" label="<%= label %>" selected="<%= selected %>" />

			<%
			label = "orphan-pages";
			selected = strutsAction.equals(strutsPath + "/view_orphan_pages");

			portletURL.setParameter("struts_action", "/wiki/view_orphan_pages");
			%>

			<aui:nav-item cssClass='<%= selected ? "active" : StringPool.BLANK %>' href="<%= portletURL.toString() %>" label="<%= label %>" selected="<%= selected %>" />

			<%
			label = "draft-pages";
			selected = strutsAction.equals(strutsPath + "/view_draft_pages");

			portletURL.setParameter("struts_action", "/wiki/view_draft_pages");
			%>

			<aui:nav-item cssClass='<%= selected ? "active" : StringPool.BLANK %>' href="<%= portletURL.toString() %>" label="<%= label %>" selected="<%= selected %>" />
		</aui:nav>

		<liferay-portlet:renderURL varImpl="searchURL">
			<portlet:param name="struts_action" value="/wiki/search" />
		</liferay-portlet:renderURL>

		<aui:nav-bar-search>
			<div class="col-xs-12 form-search">
				<aui:form action="<%= searchURL %>" method="get" name="searchFm">
					<liferay-portlet:renderURLParams varImpl="searchURL" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
					<aui:input name="nodeId" type="hidden" value="<%= node.getNodeId() %>" />

					<liferay-ui:input-search id="keywords1" />
				</aui:form>
			</div>
		</aui:nav-bar-search>
	</aui:nav-bar>

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		<aui:script>
			Liferay.Util.focusFormField(document.getElementById('<portlet:namespace />keywords1'));
		</aui:script>
	</c:if>
</c:if>