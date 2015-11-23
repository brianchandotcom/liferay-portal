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
String virtualHostName = PortalUtil.getVirtualHostname(layoutsAdminDisplayContext.getSelLayoutSet());

String defaultRobots = RobotsUtil.getRobots(layoutsAdminDisplayContext.getSelLayoutSet());

String robots = ParamUtil.getString(request, "robots", defaultRobots);
%>

<liferay-ui:error-marker key="errorSection" value="robots" />

<aui:fieldset>
	<c:choose>
		<c:when test="<%= Validator.isNotNull(virtualHostName) %>">
			<aui:input cols="60" label="set-the-robots-txt" name="robots" rows="15" type="textarea" value="<%= robots %>" />
		</c:when>
		<c:otherwise>
			<div class="alert alert-info">
				<liferay-ui:message key="please-set-the-virtual-host-before-you-set-the-robots-txt" />
			</div>
		</c:otherwise>
	</c:choose>
</aui:fieldset>