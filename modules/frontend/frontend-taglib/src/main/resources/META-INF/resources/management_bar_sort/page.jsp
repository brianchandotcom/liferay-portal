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

<%@ include file="/management_bar_sort/init.jsp" %>

<%
Map<String, String> orderColumns = (Map<String, String>)request.getAttribute("liferay-frontend:management-bar-sort:orderColumns");
String orderByCol = (String)request.getAttribute("liferay-frontend:management-bar-sort:orderByCol");
String orderByType = (String)request.getAttribute("liferay-frontend:management-bar-sort:orderByType");
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-frontend:management-bar-sort:portletURL");
%>

<c:if test="<%= !orderColumns.isEmpty() %>">
	<li class="dropdown">
		<a aria-expanded="true" class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">
			<span class="management-bar-item-title"><liferay-ui:message key="order-by" />: <liferay-ui:message key="<%= orderColumns.get(orderByCol) %>" /></span>
			<span class="icon-sort"></span>
		</a>

		<ul class="dropdown-menu">

			<%
			PortletURL orderByColURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

			orderByColURL.setParameter("orderByType", orderByType);

			for (String orderColumn : orderColumns.keySet()) {
				orderByColURL.setParameter("orderByCol", orderColumn);
			%>

				<li class="<%= orderByCol.equals(orderColumn) ? "active" : StringPool.BLANK %>">
					<aui:a href="<%= orderByColURL.toString() %>" label="<%= orderColumns.get(orderColumn) %>" />
				</li>

			<%
			}
			%>

		</ul>
	</li>
</c:if>

<%
PortletURL orderByColAscURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

orderByColAscURL.setParameter("orderByCol", orderByCol);
orderByColAscURL.setParameter("orderByType", "asc");
%>

<li>
	<liferay-frontend:management-bar-button
		active='<%= ((Validator.isNotNull(orderByType)) && orderByType.equals("asc")) %>'
		href="<%= orderByColAscURL.toString() %>"
		icon="caret-top"
		label="ascending"
	/>
</li>

<%
PortletURL orderByColDescURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

orderByColDescURL.setParameter("orderByCol", orderByCol);
orderByColDescURL.setParameter("orderByType", "desc");
%>

<li>
	<liferay-frontend:management-bar-button
		active='<%= ((Validator.isNotNull(orderByType)) && orderByType.equals("desc")) %>'
		href="<%= orderByColDescURL.toString() %>"
		icon="caret-bottom"
		label="descending"
	/>
</li>