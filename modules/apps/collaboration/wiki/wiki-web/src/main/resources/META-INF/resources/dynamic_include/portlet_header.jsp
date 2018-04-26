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

<%@ include file="/dynamic_include/init.jsp" %>

<%
PortletResponse portletResponse = (PortletResponse)request.getAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE);

WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);

WikiWebComponentProvider wikiWebComponentProvider = WikiWebComponentProvider.getWikiWebComponentProvider();

WikiGroupServiceConfiguration wikiGroupServiceConfiguration = wikiWebComponentProvider.getWikiGroupServiceConfiguration();

WikiRequestHelper wikiRequestHelper = new WikiRequestHelper(request);

WikiURLHelper wikiURLHelper = new WikiURLHelper(wikiRequestHelper, portletResponse, wikiGroupServiceConfiguration);

PortletURL searchURL = wikiURLHelper.getSearchURL();
%>

<aui:form action="<%= searchURL %>" method="get" name="searchFm">
	<liferay-portlet:renderURLParams portletURL="<%= searchURL %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="nodeId" type="hidden" value="<%= node.getNodeId() %>" />

	<liferay-ui:input-search
		id='<%= portletResponse.getNamespace() + "keywords1" %>'
		markupView="lexicon"
		name='<%= portletResponse.getNamespace() + "keywords" %>'
		useNamespace="<%= false %>"
	/>
</aui:form>