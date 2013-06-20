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
String buttonLabel = (String)request.getAttribute("liferay-ui:search-toggle:buttonLabel");
DisplayTerms displayTerms = (DisplayTerms)request.getAttribute("liferay-ui:search-toggle:displayTerms");
String id = (String)request.getAttribute("liferay-ui:search-toggle:id");
int width = GetterUtil.getInteger(request.getAttribute("liferay-ui:search-toggle:width"), 248);
%>