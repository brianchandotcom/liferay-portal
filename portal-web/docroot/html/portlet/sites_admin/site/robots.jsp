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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
Long liveGroupId = (Long)request.getAttribute("site.liveGroupId");

LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroupId);

String virtualHostName = PortalUtil.getVirtualHostname(layoutSet);

String defaultRobots = RobotsUtil.getRobots(layoutSet);

String robots = ParamUtil.getString(request, "robots", defaultRobots);
%>

<liferay-ui:error-marker key="errorSection" value="robots" />

<h3><liferay-ui:message key="robots" /></h3>

<aui:fieldset label="pages">
	<c:choose>
		<c:when test="<%= Validator.isNotNull(virtualHostName) %>">
			<aui:input cols="60" label="set-the-robots-txt-for-pages" name="publicRobots" rows="15" type="textarea" value="<%= robots %>" />
		</c:when>
		<c:otherwise>
			<div class="alert alert-info">
				<liferay-ui:message key="please-set-the-virtual-host-before-you-set-the-robots-txt" />
			</div>
		</c:otherwise>
	</c:choose>
</aui:fieldset>