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

<%@ include file="/side_panel_content/init.jsp" %>

<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/commerce-frontend-taglib/side_panel_content/main.css") %>" rel="stylesheet" />

<div class="side-panel-iframe">
	<c:if test="<%= Validator.isNotNull(title) %>">
		<div class="side-panel-iframe-header">
			<div class="side-panel-iframe-title">
				<h3 class="mb-0">
					<%= HtmlUtil.escape(title) %>
				</h3>
			</div>

			<button class="btn btn-unstyled side-panel-iframe-close">
				<clay:icon
					symbol="times"
				/>
			</button>
		</div>
	</c:if>

	<c:if test="<%= Validator.isNull(screenNavigatorKey) %>">
		<div class="side-panel-iframe-content">
	</c:if>