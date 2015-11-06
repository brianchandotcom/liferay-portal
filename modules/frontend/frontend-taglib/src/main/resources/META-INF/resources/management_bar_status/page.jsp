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

<%@ include file="/management_bar_status/init.jsp" %>

<%
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-frontend:management-bar-status:portletURL");
int status = GetterUtil.getInteger((String)request.getAttribute("liferay-frontend:management-bar-status:status"));
int[] statuses = (int[])request.getAttribute("liferay-frontend:management-bar-status:statuses");
%>

<c:if test="<%= statuses.length > 0 %>">
	<li class="dropdown">
		<a aria-expanded="true" class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">
			<span class="management-bar-item-title"><liferay-ui:message key="status" />: <liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(status) %>" /></span>
			<span class="icon-sort"></span>
		</a>

		<ul class="dropdown-menu">

			<%
			PortletURL statusURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

			for (int curStatus : statuses) {
				statusURL.setParameter("status", String.valueOf(curStatus));
			%>

				<li class="<%= (status == curStatus) ? "active" : StringPool.BLANK %>">
					<aui:a href="<%= statusURL.toString() %>" label="<%= WorkflowConstants.getStatusLabel(curStatus) %>" />
				</li>

			<%
			}
			%>

		</ul>
	</li>
</c:if>