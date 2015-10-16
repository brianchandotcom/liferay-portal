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

String navigationKey = ParamUtil.getString(request, navigationParam);
%>

<c:if test="<%= ArrayUtil.isNotEmpty(navigationKeys) %>">
	<li>
		<aui:select inlineField="<%= true %>" inlineLabel="left" label="" name="<%= navigationParam %>">

			<%
			for (String curNavigationKey : navigationKeys) {
			%>

				<aui:option label="<%= curNavigationKey %>" selected="<%= curNavigationKey.equals(navigationKey) %>" value="<%= curNavigationKey %>" />

			<%
			}
			%>

		</aui:select>
	</li>
</c:if>

<aui:script>

	<%
	PortletURL navigationURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);
	%>

	var navigation = $('#<%= namespace + navigationParam %>');

	navigation.on(
		'change',
		function(event) {
			var uri = '<%= navigationURL %>';

			uri = Liferay.Util.addParams('<%= namespace + navigationParam %>=' + navigation.val(), uri);

			window.location.href = uri;
		}
	);
</aui:script>