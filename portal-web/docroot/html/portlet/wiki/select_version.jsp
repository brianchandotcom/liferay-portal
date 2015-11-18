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
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectVersion");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/wiki/select_version");
portletURL.setParameter("redirect", currentURL);
portletURL.setParameter("nodeId", String.valueOf(wikiPage.getNodeId()));
portletURL.setParameter("title", HtmlUtil.unescape(wikiPage.getTitle()));
portletURL.setParameter("sourceVersion", String.valueOf(sourceVersion));
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="selectVersionFm">
	<liferay-ui:search-container
		id="wikiPageVersionSearchContainer"
		iteratorURL="<%= portletURL %>"
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
					double curTargetVersion = curWikiPage.getVersion();

					if (curTargetVersion < curSourceVersion) {
						double tempVersion = curTargetVersion;

						curTargetVersion = curSourceVersion;
						curSourceVersion = tempVersion;
					}

					Map<String, Object> data = new HashMap<String, Object>();

					data.put("sourceversion", curSourceVersion);
					data.put("targetversion", curTargetVersion);
					%>

					<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectVersionFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>