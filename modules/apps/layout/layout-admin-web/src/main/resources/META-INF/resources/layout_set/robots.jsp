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

<%@ include file="/init.jsp" %>

<%
LayoutSet layoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

String virtualHostname = layoutsAdminDisplayContext.getVirtualHostname();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="robots"
/>

<c:choose>
	<c:when test="<%= Validator.isNotNull(virtualHostname) %>">
		<aui:input helpMessage="robots-txt-help" label="set-the-robots-txt" name='<%= "TypeSettingsProperties--" + layoutSet.isPrivateLayout() + "-robots.txt--" %>' placeholder="robots" type="textarea" value="<%= layoutsAdminDisplayContext.getRobots() %>" />
	</c:when>
	<c:otherwise>
		<div class="alert alert-info">
			<liferay-ui:message key="please-set-the-virtual-host-before-you-set-the-robots-txt" />
		</div>
	</c:otherwise>
</c:choose>