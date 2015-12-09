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

<%@ include file="/management_bar_navigation/init.jsp" %>

<%
String[] navigationKeys = (String[])request.getAttribute("liferay-frontend:management-bar-navigation:navigationKeys");
String navigationParam = (String)request.getAttribute("liferay-frontend:management-bar-navigation:navigationParam");
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-frontend:management-bar-navigation:portletURL");
%>

<c:if test="<%= ArrayUtil.isNotEmpty(navigationKeys) %>">

	<%
	String navigationKey = ParamUtil.getString(request, navigationParam, navigationKeys[0]);
	%>

	<li class="dropdown">
		<a aria-expanded="true" class="dropdown-toggle" data-qa-id="filter" data-toggle="dropdown" href="javascript:;">
			<span class="management-bar-item-title"><liferay-ui:message key="<%= navigationKey %>" /></span>
			<span class="icon-sort"></span>
		</a>

		<ul class="dropdown-menu" data-qa-id="filterValues">

			<%
			PortletURL navigationURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

			for (String curNavigationKey : navigationKeys) {
				navigationURL.setParameter(navigationParam, curNavigationKey);
			%>

				<li class="<%= curNavigationKey.equals(navigationKey) ? "active" : StringPool.BLANK %>">
					<aui:a href="<%= navigationURL.toString() %>" label="<%= curNavigationKey %>" />
				</li>

			<%
			}
			%>

		</ul>
	</li>
</c:if>