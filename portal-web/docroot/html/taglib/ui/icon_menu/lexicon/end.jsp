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
<%@ include file="/html/taglib/init.jsp" %>

<%
String direction = (String)request.getAttribute("liferay-ui:icon-menu:direction");
String id = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon-menu:id"));
boolean scroll = GetterUtil.getBoolean(request.getAttribute("liferay-ui:icon-menu:scroll"));
%>

		</ul>
	<c:if test="<%= scroll %>">
		</div>
	</c:if>
</div>

<aui:script require="metal-dropdown/src/Dropdown">
	var Dropdown = metalDropdownSrcDropdown.default;

	var iconMenuDropdown = new Dropdown(
		{
			alignElementSelector: '#<%= id %> .dropdown-toggle',
			element: '#<%= id %>',
			events: {
				click: {
					fn: function(event) {
						iconMenuDropdown.expanded = false;
					},
					selector: 'li'
				}
			},
			position: '<%= direction %>'
		}
	).render();
</aui:script>