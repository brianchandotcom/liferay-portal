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
	<li>
		<aui:select inlineField="<%= true %>" inlineLabel="left" label="order-by" name="orderByCol">

			<%
			for (String columnTitle : orderColumns.keySet()) {
			%>

				<aui:option label="<%= columnTitle %>" selected="<%= orderByCol.equals(orderColumns.get(columnTitle)) %>" value="<%= orderColumns.get(columnTitle) %>" />

			<%
			}
			%>

		</aui:select>
	</li>
</c:if>

<li class="<%= ((Validator.isNotNull(orderByType)) && orderByType.equals("asc")) ? "active" : StringPool.BLANK %>">

	<%
	PortletURL orderByColAscURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

	orderByColAscURL.setParameter("orderByCol", orderByCol);
	orderByColAscURL.setParameter("orderByType", "asc");
	%>

	<a class="btn hidden-xs" href="<%= orderByColAscURL %>"><span class="icon-caret-up icon-monospaced"></span></a>
</li>

<li class="<%= ((Validator.isNotNull(orderByType)) && orderByType.equals("desc")) ? "active" : StringPool.BLANK %>">

	<%
	PortletURL orderByColDescURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

	orderByColDescURL.setParameter("orderByCol", orderByCol);
	orderByColDescURL.setParameter("orderByType", "desc");
	%>

	<a class="btn hidden-xs" href="<%= orderByColDescURL %>"><span class="icon-caret-down icon-monospaced"></span></a>
</li>

<aui:script>

	<%
	PortletURL orderByTypeURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

	orderByTypeURL.setParameter("orderByType", orderByType);
	%>

	var orderByCol = $('#<%= namespace %>orderByCol');

	orderByCol.on(
		'change',
		function(event) {
			var uri = '<%= orderByTypeURL %>';

			uri = Liferay.Util.addParams('<%= namespace %>orderByCol=' + orderByCol.val(), uri);

			window.location.href = uri;
		}
	);
</aui:script>