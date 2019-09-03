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

<%@ include file="/flags/init.jsp" %>

<%
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-flags:flags:data");
String elementClasses = (String)request.getAttribute("liferay-flags:flags:elementClasses");
String id = StringUtil.randomId() + StringPool.UNDERLINE + "id";
String message = (String)request.getAttribute("liferay-flags:flags:message");
boolean onlyIcon = GetterUtil.getBoolean(request.getAttribute("liferay-flags:flags:onlyIcon"));
%>

<div class="taglib-flags <%= Validator.isNotNull(elementClasses) ? elementClasses : "" %>" id="<%= id %>">
	<c:choose>
		<c:when test="<%= onlyIcon %>">
			<clay:button
				disabled="<%= true %>"
				elementClasses="btn-outline-borderless btn-outline-secondary lfr-portal-tooltip"
				icon="flag-empty"
				monospaced="<%= true %>"
				size="sm"
				style="secondary"
				title="<%= message %>"
			/>
		</c:when>
		<c:otherwise>
			<clay:button
				disabled="<%= true %>"
				elementClasses="btn-outline-borderless btn-outline-secondary"
				icon="flag-empty"
				label="<%= message %>"
				size="sm"
				style="secondary"
			/>
		</c:otherwise>
	</c:choose>

	<react:component
		data="<%= data %>"
		module="flags/js/index.es"
	/>
</div>