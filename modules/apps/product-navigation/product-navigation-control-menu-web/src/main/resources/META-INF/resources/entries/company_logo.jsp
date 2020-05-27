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

<%
String portletTitle = (String)request.getAttribute(ProductNavigationControlMenuWebKeys.PORTLET_TITLE);
%>

<li class="control-menu-nav-item control-menu-nav-item-content">
	<clay:content-row
		verticalAlign="center"
	>
		<clay:content-col
			cssClass="c-pr-1"
		>
			<img alt="" class="company-logo" height="32px" src="<%= themeDisplay.getPathImage() + "/company_logo?img_id=" + company.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(company.getLogoId()) %>" />
		</clay:content-col>

		<clay:content-col>
			<span class="control-menu-level-1-heading text-truncate" data-qa-id="headerTitle"><%= HtmlUtil.escape(portletTitle) %></span>
		</clay:content-col>
	</clay:content-row>
</li>