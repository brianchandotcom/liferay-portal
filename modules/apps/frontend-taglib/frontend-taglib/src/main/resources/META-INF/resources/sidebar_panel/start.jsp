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

<%@ include file="/sidebar_panel/init.jsp" %>

<div class="info-panel sidenav-menu-slider">
	<div class="sidebar sidebar-light sidenav-menu">
		<c:if test="<%= closeButton %>">
			<clay:button
				borderless="<%= true %>"
				cssClass="d-flex d-sm-none sidenav-close"
				displayType="secondary"
				monospaced="<%= true %>"
				outline="<%= true %>"
				small="<%= true %>"
			>
				<span class="c-inner" tabindex="-1">
					<span class="inline-item">
						<clay:icon
							symbol="times"
						/>
					</span>
				</span>
			</clay:button>
		</c:if>

		<div class="info-panel-content" id="<%= namespace %>sidebarPanel">