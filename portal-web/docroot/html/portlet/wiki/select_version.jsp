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

		<liferay-ui:search-container-column-text
			name="date"
			value='<%= LanguageUtil.format(pageContext, "x-ago", LanguageUtil.getTimeDescription(pageContext, System.currentTimeMillis() - curWikiPage.getModifiedDate().getTime(), true)) %>'
		/>

		<liferay-ui:search-container-column-text
			name=""
		>
			<c:if test="<%= sourceVersion != curWikiPage.getVersion() %>">
				<aui:button cssClass="select-wiki-page-version" data-sourceversion="<%= sourceVersion %>" data-targetversion="<%= curWikiPage.getVersion() %>" href="javascript:;" value="choose" />
			</c:if>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<aui:script use="liferay-portlet-url,liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />wikiPageVersionSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var sourceVersion = parseFloat(link.getAttribute('data-sourceversion'));
			var targetVersion = parseFloat(link.getAttribute('data-targetversion'));

			if(targetVersion < sourceVersion) {
				var tempVersion = targetVersion;

				targetVersion = sourceVersion;
				sourceVersion = tempVersion;
			}

			var redirect = Liferay.PortletURL.createURL('<portlet:renderURL />');

			redirect.setParameter('struts_action', '/wiki/view_page_history');

			<%
			WikiNode node = wikiPage.getNode();
			%>

			redirect.setParameter('nodeId', '<%= wikiPage.getNode().getNodeId() %>');
			redirect.setParameter("title", '<%= wikiPage.getTitle() %>');

			var portletURL = Liferay.PortletURL.createURL('<portlet:renderURL />');

			portletURL.setParameter('struts_action', '/wiki/compare_versions');
			portletURL.setParameter('redirect', redirect);
			portletURL.setParameter('nodeId', '<%= node.getNodeId() %>');
			portletURL.setParameter('title', '<%= wikiPage.getTitle() %>');
			portletURL.setParameter('sourceVersion', sourceVersion);
			portletURL.setParameter('targetVersion', targetVersion);
			portletURL.setParameter('type', 'html');

			Liferay.Util.getOpener().location.href = portletURL.toString();
		},
		'.select-wiki-page-version'
	);
</aui:script>