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

<%
String key = (String)request.getAttribute("liferay-ui:success:key");
String message = (String)request.getAttribute("liferay-ui:success:message");
String targetNode = (String)request.getAttribute("liferay-ui:success:targetNode");
int timeOut = GetterUtil.getInteger(request.getAttribute("liferay-ui:success:timeOut"));
boolean translateMessage = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:success:translateMessage"));

String bodyContentString = StringPool.BLANK;

Object bodyContent = request.getAttribute("liferay-ui:success:bodyContent");

if (bodyContent != null) {
	bodyContentString = bodyContent.toString();
}

if (Validator.isNotNull(bodyContentString)) {
	message = bodyContentString;
}
else if (translateMessage) {
	message = LanguageUtil.get(resourceBundle, message);
}
%>

<c:if test="<%= MultiSessionMessages.contains(portletRequest, key) %>">
	<liferay-ui:alert
		icon="check"
		message="<%= message %>"
		targetNode="<%= targetNode %>"
		timeout="<%= timeOut %>"
		type="success"
	/>
</c:if>