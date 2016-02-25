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
boolean compact = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:empty-result-message:compact"));
String message = GetterUtil.getString((String)request.getAttribute("liferay-ui:empty-result-message:message"));
boolean search = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:empty-result-message:search"));
%>

<div class="card-horizontal main-content-card taglib-empty-result-message">
	<div class="card-row card-row-padded">
		<c:choose>
			<c:when test="<%= search %>">
				<div class="taglib-empty-search-result-message-header"></div>
			</c:when>
			<c:otherwise>
				<div class="taglib-empty-result-message-header"></div>
			</c:otherwise>
		</c:choose>

		<div class="card-footer">
			<div class="card-dm-details">
				<p class="text-center text-muted">
					<c:if test="<%= Validator.isNotNull(message) %>">
						<liferay-ui:message key="<%= message %>" />
					</c:if>