<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

double sourceVersion = ParamUtil.getDouble(request, "sourceVersion");

WikiNode node = wikiPage.getNode();

PortletURL redirect = renderResponse.createRenderURL();

redirect.setParameter("struts_action", "/wiki/view_page_history");
redirect.setParameter("nodeId", String.valueOf(node.getNodeId()));
redirect.setParameter("title", wikiPage.getTitle());
%>

<liferay-ui:search-container
	id="wikiPageVersionSearchContainer"
	total="<%= WikiPageLocalServiceUtil.getPagesCount(wikiPage.getNodeId(), wikiPage.getTitle()) %>"
>
	<liferay-ui:search-container-results
		results="<%= WikiPageLocalServiceUtil.getPages(wikiPage.getNodeId(), wikiPage.getTitle(), searchContainer.getStart(), searchContainer.getEnd(), new PageVersionComparator()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.wiki.model.WikiPage"
		modelVar="curWikiPage"
	>
		<liferay-ui:search-container-column-text
			name="version"
			value="<%= String.valueOf(curWikiPage.getVersion()) %>"
		/>

		<liferay-ui:search-container-column-date
			name="date"
			value="<%= curWikiPage.getModifiedDate() %>"
		/>

		<liferay-ui:search-container-column-text
			name=""
		>
			<c:if test="<%= sourceVersion != curWikiPage.getVersion() %>">

				<%
				double curSourceVersion = sourceVersion;
				double targetVersion = curWikiPage.getVersion();

				if (targetVersion < curSourceVersion) {
					double tempVersion = targetVersion;

					targetVersion = curSourceVersion;
					curSourceVersion = tempVersion;
				}
				%>

				<portlet:renderURL var="compareVersionURL">
					<portlet:param name="struts_action" value="/wiki/compare_versions" />
					<portlet:param name="redirect" value="<%= redirect.toString() %>" />
					<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
					<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
					<portlet:param name="sourceVersion" value="<%= String.valueOf(curSourceVersion) %>" />
					<portlet:param name="targetVersion" value="<%= String.valueOf(targetVersion) %>" />
					<portlet:param name="type" value="html" />
				</portlet:renderURL>

				<aui:button cssClass="select-wiki-page-version" data-uri="<%= compareVersionURL %>" href="javascript:;" value="choose" />
			</c:if>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />wikiPageVersionSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var uri = link.getAttribute('data-uri');

			Liferay.Util.getOpener().location.href = uri;
		},
		'.select-wiki-page-version'
	);
</aui:script>