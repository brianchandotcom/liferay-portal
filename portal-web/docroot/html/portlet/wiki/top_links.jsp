<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

String redirect = ParamUtil.getString(request, "redirect");

WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

String keywords = ParamUtil.getString(request, "keywords");

List<WikiNode> nodes = WikiUtil.getNodes(allNodes, hiddenNodes, permissionChecker);

boolean print = ParamUtil.getString(request, "viewMode").equals(Constants.PRINT);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("nodeName", node.getName());

long categoryId = ParamUtil.getLong(request, "categoryId");

if (categoryId > 0) {
	portletURL.setParameter("categoryId", "0");
}
%>

<c:if test="<%= portletName.equals(PortletKeys.WIKI_ADMIN) %>">
	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= false %>"
		title="<%= node.getName() %>"
	/>
</c:if>

<c:if test="<%= !print %>">
	<div class="top-links-container">
		<c:if test="<%= (nodes.size() > 1) && portletName.equals(PortletKeys.WIKI) %>">
			<ul class="top-links-nodes">

				<%
				for (int i = 0; i < nodes.size(); i++) {
					WikiNode curNode = nodes.get(i);

					String cssClass = StringPool.BLANK;

					if (curNode.getNodeId() == node.getNodeId()) {
						cssClass = "node-current";
					}
				%>

					<portlet:renderURL var="viewPageURL">
						<portlet:param name="struts_action" value="/wiki/view" />
						<portlet:param name="nodeName" value="<%= curNode.getName() %>" />
						<portlet:param name="title" value="<%= WikiPageConstants.FRONT_PAGE %>" />
					</portlet:renderURL>

					<li class="top-link-node <%= (i == (nodes.size() - 1)) ? "last" : StringPool.BLANK %>">
						<aui:a cssClass="<%= cssClass %>" href="<%= viewPageURL %>" label="<%= curNode.getName() %>" />
					</li>

				<%
				}
				%>

			</ul>
		</c:if>

		<div class="top-links">
			<ul class="top-links-navigation">
			    <li class="top-link first">

					<%
					PortletURL frontPageURL = PortletURLUtil.clone(portletURL, renderResponse);

					frontPageURL.setParameter("struts_action", "/wiki/view");
					frontPageURL.setParameter("title", WikiPageConstants.FRONT_PAGE);
					frontPageURL.setParameter("tag", StringPool.BLANK);
					%>

					<liferay-ui:icon
						image="../aui/home"
						label="<%= true %>"
						message="<%= WikiPageConstants.FRONT_PAGE %>"
						url="<%= Validator.isNull(strutsAction) || ((wikiPage != null) && wikiPage.getTitle().equals(WikiPageConstants.FRONT_PAGE)) ? StringPool.BLANK : frontPageURL.toString() %>"
					/>
				</li>

				<li class="top-link">

					<%
					portletURL.setParameter("struts_action", "/wiki/view_recent_changes");
					%>

					<liferay-ui:icon
						image="../aui/clock"
						label="<%= true %>"
						message="recent-changes"
						url='<%= strutsAction.equals(strutsPath + "/view_recent_changes") ? StringPool.BLANK : portletURL.toString() %>'
					/>
				</li>

				<li class="top-link">

					<%
					portletURL.setParameter("struts_action", "/wiki/view_all_pages");
					%>

					<liferay-ui:icon
						image="../aui/document" label="<%= true %>"
						message="all-pages" url='<%= strutsAction.equals(strutsPath + "/view_all_pages") ? StringPool.BLANK : portletURL.toString() %>'
					/>
				</li>

				<li class="top-link">

					<%
					portletURL.setParameter("struts_action", "/wiki/view_orphan_pages");
					%>

					<liferay-ui:icon
						image="../aui/document-b"
						label="<%= true %>"
						message="orphan-pages"
						url='<%= strutsAction.equals(strutsPath + "/view_orphan_pages") ? StringPool.BLANK : portletURL.toString() %>'
					/>
				</li>

				<li class="top-link last">

					<%
					portletURL.setParameter("struts_action", "/wiki/view_draft_pages");
					%>

					<liferay-ui:icon
						image="../aui/document-b"
						label="<%= true %>"
						message="draft-pages"
						url='<%= strutsAction.equals(strutsPath + "/view_draft_pages") ? StringPool.BLANK : portletURL.toString() %>'
					/>
				</li>
			</ul>

			<liferay-portlet:renderURL varImpl="searchURL">
				<portlet:param name="struts_action" value="/wiki/search" />
			</liferay-portlet:renderURL>

			<div class="page-search">
				<aui:form action="<%= searchURL %>" method="get" name="searchFm">
					<liferay-portlet:renderURLParams varImpl="searchURL" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
					<aui:input name="nodeId" type="hidden" value="<%= node.getNodeId() %>" />

					<span class="aui-search-bar">
						<aui:input inlineField="<%= true %>" label="" name="keywords" size="30" title="search-pages" type="text" value="<%= keywords %>" />

						<aui:button type="submit" value="search" />
					</span>
				</aui:form>
			</div>
		</div>
	</div>

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		<aui:script>
			Liferay.Util.focusFormField(document.<portlet:namespace />searchFm.<portlet:namespace />keywords);
		</aui:script>
	</c:if>
</c:if>