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

<%@ include file="/html/taglib/init.jsp" %>

<%
boolean accordion = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:panel-floating-container:accordion"));
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:panel-floating-container:cssClass"));
Boolean extended = (Boolean)request.getAttribute("liferay-ui:panel-floating-container:extended");
String id = (String)request.getAttribute("liferay-ui:panel-floating-container:id");
boolean paging = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:panel-floating-container:paging"));
String pagingElements = (String)request.getAttribute("liferay-ui:panel-floating-container:pagingElements");
boolean persistState = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:panel-floating-container:persistState"));
int resultsPerPage = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:panel-floating-container:resultsPerPage"));
String trigger = (String)request.getAttribute("liferay-ui:panel-floating-container:trigger");
int width = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:panel-floating-container:width"));
%>