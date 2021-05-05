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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry" %><%@
page import="com.liferay.taglib.ui.SearchIteratorTag" %>

<%
SearchContainer<?> searchContainer = (SearchContainer<?>)request.getAttribute("liferay-ui:search:searchContainer");

boolean compactEmptyResultsMessage = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:search:compactEmptyResultsMessage"));
String displayStyle = GetterUtil.getString((String)request.getAttribute("liferay-ui:search-iterator:displayStyle"), SearchIteratorTag.DEFAULT_DISPLAY_STYLE);
boolean fixedHeader = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:search-iterator:fixedHeader"));
String markupView = (String)request.getAttribute("liferay-ui:search-iterator:markupView");
boolean paginate = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:search-iterator:paginate"));
ResultRowSplitter resultRowSplitter = (ResultRowSplitter)request.getAttribute("liferay-ui:search-iterator:resultRowSplitter");
String rowIdProperty = (String)request.getAttribute("liferay-ui:search-container-row:rowIdProperty");
String searchContainerRowCssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:search-container-row:cssClass"));
String searchResultCssClass = (String)request.getAttribute("liferay-ui:search-iterator:searchResultCssClass");
String type = (String)request.getAttribute("liferay-ui:search:type");

String id = searchContainer.getId(request, namespace);

String emptyResultsMessage = searchContainer.getEmptyResultsMessage();
String emptyResultsMessageCssClass = searchContainer.getEmptyResultsMessageCssClass();
List<String> headerNames = searchContainer.getHeaderNames();
Map<String, String> helpMessages = searchContainer.getHelpMessages();
List<String> normalizedHeaderNames = searchContainer.getNormalizedHeaderNames();
Map orderableHeaders = searchContainer.getOrderableHeaders();
RowChecker rowChecker = searchContainer.getRowChecker();
RowMover rowMover = searchContainer.getRowMover();
List resultRows = searchContainer.getResultRows();
String summary = searchContainer.getSummary();

JSONArray primaryKeysJSONArray = JSONFactoryUtil.createJSONArray();
%>