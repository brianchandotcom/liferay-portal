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

<%@ page import="com.liferay.portal.search.web.internal.facet.display.context.ScopeSearchFacetDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.facet.display.context.ScopeSearchFacetTermDisplayContext" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, _RANDOM_KEY_INPUT) + StringPool.UNDERLINE;

Facet facet = (Facet)request.getAttribute("search.jsp-facet");

String fieldParam = ParamUtil.getString(request, facet.getFieldId());

FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

JSONObject dataJSONObject = facetConfiguration.getData();

FacetCollector facetCollector = facet.getFacetCollector();

List<TermCollector> termCollectors = facetCollector.getTermCollectors();

String cssClass = "search-facet search-".concat(HtmlUtil.escapeAttribute(facetConfiguration.getDisplayStyle()));
%>

<%!
private static final String _RANDOM_KEY_INPUT = "portlet_search_facets_" + StringUtil.randomString();
%>