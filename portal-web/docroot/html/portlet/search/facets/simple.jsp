<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/search/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_search_facets_simple") + StringPool.UNDERLINE;

Facet facet = (Facet)request.getAttribute("view.jsp-facet");

FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

String panelLabel = facetConfiguration.getLabel();
String facetDisplayStyle = facetConfiguration.getDisplayStyle();
String cssClass = "search-facet search-".concat(facetDisplayStyle);

String fieldParam = ParamUtil.getString(request, facet.getFieldName());
%>

<div class="<%= cssClass %>" id='<%= randomNamespace + "facet" %>'>
	<aui:input name="<%= facet.getFieldName() %>" label="" type="text" value="<%= fieldParam %>" />
</div>