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
String key = (String)request.getAttribute("liferay-ui:error:key");
String message = (String)request.getAttribute("liferay-ui:error:message");
String rowBreak = (String)request.getAttribute("liferay-ui:error:rowBreak");
String targetNode = GetterUtil.getString((String)request.getAttribute("liferay-ui:error:targetNode"));
boolean translateMessage = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:error:translateMessage"));

String bodyContentString = StringPool.BLANK;

Object bodyContent = request.getAttribute("liferay-ui:error:bodyContent");

if (bodyContent != null) {
	bodyContentString = bodyContent.toString();
}
%>

<c:choose>
	<c:when test="<%= (key != null) && Validator.isNull(message) %>">
		<c:if test="<%= SessionErrors.contains(portletRequest, key) %>">
			<c:if test="<%= Validator.isNotNull(bodyContentString) %>">
				<liferay-ui:alert
					icon="exclamation-full"
					message="<%= bodyContentString %>"
					targetNode="<%= targetNode %>"
					timeout="<%= 0 %>"
					type="danger"
				/>

				<%= rowBreak %>
			</c:if>
		</c:if>
	</c:when>
	<c:when test='<%= SessionErrors.contains(portletRequest, "warning") %>'>
		<liferay-util:buffer var="alertMessage">
			<c:choose>
				<c:when test="<%= message != null %>">
					<liferay-ui:message key="<%= message %>" localizeKey="<%= translateMessage %>" />
				</c:when>
				<c:otherwise>
					<liferay-ui:message key='<%= (String)SessionErrors.get(portletRequest, "warning") %>' localizeKey="<%= translateMessage %>" />
				</c:otherwise>
			</c:choose>
		</liferay-util:buffer>

		<liferay-ui:alert
			icon="exclamation-full"
			message="<%= alertMessage %>"
			targetNode="<%= targetNode %>"
			timeout="<%= 0 %>"
			type="warning"
		/>

		<%= rowBreak %>
	</c:when>
	<c:when test="<%= key == null %>">
		<liferay-ui:alert
			icon="exclamation-full"
			message='<%= LanguageUtil.get(resourceBundle, "your-request-failed-to-complete") %>'
			targetNode="<%= targetNode %>"
			timeout="<%= 0 %>"
			type="danger"
		/>

		<%= rowBreak %>
	</c:when>
	<c:otherwise>
		<c:if test="<%= SessionErrors.contains(portletRequest, key) %>">
			<liferay-ui:alert
				icon="exclamation-full"
				message="<%= translateMessage ? LanguageUtil.get(resourceBundle, message) : message %>"
				targetNode="<%= targetNode %>"
				timeout="<%= 0 %>"
				type="danger"
			/>

			<%= rowBreak %>
		</c:if>
	</c:otherwise>
</c:choose>