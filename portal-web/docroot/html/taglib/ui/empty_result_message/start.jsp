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

<%@ include file="/html/taglib/ui/empty_result_message/init.jsp" %>

<%
String message = GetterUtil.getString((String)request.getAttribute("liferay-ui:empty-result-message:message"));
%>

<c:choose>
	<c:when test="<%= compact %>">
		<p class="text-secondary">
			<liferay-ui:message key="<%= message %>" />
		</p>
	</c:when>
	<c:otherwise>
		<div class="mt-4 taglib-empty-result-message">
			<div class="<%= (String)request.getAttribute("liferay-ui:empty-result-message:cssClass") %>"></div>

			<c:if test="<%= Validator.isNotNull(message) %>">
				<div class="mt-2 text-center text-secondary">
					<liferay-ui:message key="<%= message %>" />
				</div>
			</c:if>
	</c:otherwise>
</c:choose>