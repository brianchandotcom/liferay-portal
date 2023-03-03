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

<div aria-label="<%= Validator.isNotNull(title) ? title : StringPool.BLANK %>" class="info-panel sidenav-menu-slider" role="tabpanel" tabindex="-1">
	<div class="sidebar sidebar-light sidenav-menu">
		<c:if test="<%= closeButton %>">
			<clay:button
				aria-label='<%= Validator.isNotNull(title) ? LanguageUtil.format(request, "close-x", title, false) : StringPool.BLANK %>'
				borderless="<%= true %>"
				cssClass="d-flex sidenav-close"
				displayType="secondary"
				monospaced="<%= true %>"
				outline="<%= true %>"
				small="<%= true %>"
				title='<%= LanguageUtil.get(request, "close") %>'
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