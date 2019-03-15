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

<liferay-ui:tabs
	names="Alerts,Badges,Buttons,Cards,Dropdowns,Form Elements,Icons,Labels,Links,Management Toolbars,Navigation Bars,Progress Bars,Stickers,Tables"
	refresh="<%= false %>"
>

	<%
	String[] sections = {"alerts", "badges", "buttons", "cards", "dropdowns", "form_elements", "icons", "labels", "links", "management_toolbars", "navigation_bars", "progress_bars", "stickers", "tables"};

	for (int i = 0; i < sections.length; i++) {
		String partial ="/partials/" + sections[i] + ".jsp";
	%>

		<liferay-ui:section>
			<div class="container-fluid-1280">
				<liferay-util:include page="<%= partial %>" servletContext="<%= application %>" />
			</div>
		</liferay-ui:section>

	<%
	}
	%>

</liferay-ui:tabs>