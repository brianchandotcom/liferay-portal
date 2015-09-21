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
java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:icon:cssClass"));
java.util.Map<java.lang.String, java.lang.Object> data = (java.util.Map<java.lang.String, java.lang.Object>)request.getAttribute("aui:icon:data");
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:icon:id"));
java.lang.String image = GetterUtil.getString((java.lang.String)request.getAttribute("aui:icon:image"));
java.lang.String label = GetterUtil.getString((java.lang.String)request.getAttribute("aui:icon:label"));
java.lang.String markupView = GetterUtil.getString((java.lang.String)request.getAttribute("aui:icon:markupView"));
java.lang.String target = GetterUtil.getString((java.lang.String)request.getAttribute("aui:icon:target"));
java.lang.String url = GetterUtil.getString((java.lang.String)request.getAttribute("aui:icon:url"));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:icon:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("aui:icon:scopedAttributes");
%>

<%@ include file="/html/taglib/aui/icon/init-ext.jspf" %>