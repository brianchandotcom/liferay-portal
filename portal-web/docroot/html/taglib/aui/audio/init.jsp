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

@generated
--%>

<%@ include file="/html/taglib/taglib-init.jsp" %>

<%
java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:audio:cssClass"));
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:audio:id"));
java.lang.String oggUrl = GetterUtil.getString((java.lang.String)request.getAttribute("aui:audio:oggUrl"));
java.lang.String swfUrl = GetterUtil.getString((java.lang.String)request.getAttribute("aui:audio:swfUrl"));
java.lang.String type = GetterUtil.getString((java.lang.String)request.getAttribute("aui:audio:type"), "mp3");
java.lang.String url = GetterUtil.getString((java.lang.String)request.getAttribute("aui:audio:url"));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:audio:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("aui:audio:scopedAttributes");
%>

<%@ include file="/html/taglib/aui/audio/init-ext.jspf" %>