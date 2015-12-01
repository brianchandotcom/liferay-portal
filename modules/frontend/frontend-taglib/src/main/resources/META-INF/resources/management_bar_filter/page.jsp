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

<%@ include file="/management_bar_filter/init.jsp" %>

<%
List<ManagementBarFilterItem> filterItems = (List<ManagementBarFilterItem>)request.getAttribute("liferay-frontend:management-bar-filter:filterItems");
String label = (String)request.getAttribute("liferay-frontend:management-bar-filter:label");
String value = (String)request.getAttribute("liferay-frontend:management-bar-filter:value");
%>

<c:if test="<%= filterItems.size() > 0 %>">
	<li class="dropdown">
		<a aria-expanded="true" class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">
			<span class="management-bar-item-title"><liferay-ui:message key="<%= label %>" />: <liferay-ui:message key="<%= value %>" /></span>
			<span class="icon-sort"></span>
		</a>

		<ul class="dropdown-menu">

			<%
			for (ManagementBarFilterItem curItem : filterItems) {
			%>

				<li class="<%= Validator.equals(curItem.getLabel(), value) ? "active" : StringPool.BLANK %>">
					<aui:a href="<%= curItem.getURL() %>" label="<%= curItem.getLabel() %>" />
				</li>

			<%
			}
			%>

		</ul>
	</li>
</c:if>