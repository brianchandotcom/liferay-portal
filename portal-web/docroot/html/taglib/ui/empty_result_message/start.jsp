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
String cssClass = (String)request.getAttribute("liferay-ui:empty-result-message:cssClass");
String message = GetterUtil.getString((String)request.getAttribute("liferay-ui:empty-result-message:message"));
%>

<div class='card-horizontal main-content-card taglib-empty-result-message <%= compact ? "taglib-empty-result-message-compact" : StringPool.BLANK %>'>
	<div class="card-row card-row-padded">
		<div class="<%= cssClass %>"></div>

		<div class="card-footer">
			<div class="card-dm-details">
				<c:if test="<%= Validator.isNotNull(message) %>">
					<p class="text-center text-muted">
						<liferay-ui:message key="<%= message %>" />
					</p>
				</c:if>