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

<%@ include file="/html/taglib/aui/form/init.jsp" %>

<%
	String encType = (String)dynamicAttributes.get("enctype");
	String authToken = HttpUtil.getParameter(action, "p_auth", false);

	boolean moveAuthToken = false;

	if (Validator.isNotNull(authToken) && StringUtil.equalsIgnoreCase(method, "post") && !StringUtil.equalsIgnoreCase(encType, "multipart/form-data")) {
		moveAuthToken = true;
	}

	if (moveAuthToken) {
		action = HttpUtil.removeParameter(action, "p_auth");
	}
%>

<form action="<%= HtmlUtil.escape(action) %>" class="form <%= cssClass %> <%= inlineLabels ? "field-labels-inline" : StringPool.BLANK %>" data-fm-namespace="<%= namespace %>"  id="<%= namespace + name %>" method="<%= method %>" name="<%= namespace + name %>" <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>>
	<c:if test="<%= Validator.isNotNull(onSubmit) %>">
		<fieldset class="input-container" disabled="disabled">
	</c:if>

	<c:if test="<%= moveAuthToken %>">
		<input name="p_auth" type="hidden" value="<%= authToken %>" />
	</c:if>

	<aui:input name="formDate" type="hidden" value="<%= System.currentTimeMillis() %>" />