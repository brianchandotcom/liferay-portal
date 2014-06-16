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

<%@ include file="/html/taglib/ui/panel/init.jsp" %>

<div class="panel panel-default <%= cssClass %>" id="<%= id %>">
	<div class="panel-heading <%= headerCssClass %>" data-persist-id="<%= persistState ? id : StringPool.BLANK %>">
		<div class="panel-toggle">
			<c:if test="<%= Validator.isNotNull(iconCssClass) %>">
				<i class="<%= iconCssClass %>"></i>
			</c:if>

			<span class="title-text">
				<liferay-ui:message key="<%= title %>" />
			</span>

			<c:if test="<%= Validator.isNotNull(helpMessage) %>">
				<liferay-ui:icon-help message="<%= helpMessage %>" />
			</c:if>
		</div>
	</div>
	<div class="<%= contentCssClass %>" id="<%= id %>Content">
		<div class="panel-body">