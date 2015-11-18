<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/aui/nav_bar/init.jsp" %>

<div class="navbar <%= cssClass %>" id="<%= id %>" <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>>
	<div class="navbar-inner">
		<div class="container">
			<span id="<%= id %>ResponsiveButton">
				<%= responsiveButtons %>
			</span>

			<span id="<%= id %>bodyContent">
				<%= bodyContentString %>
			</span>

			<aui:script use="aui-base,event-outside,liferay-menu-toggle">
				A.all('#<%= id %>ResponsiveButton .btn-navbar').each(
					function(item, index, collection) {
						var contentId = item.attr('id');
						var navId = item.attr('data-navid');

						var toggleMenu = new Liferay.MenuToggle(
							{
								content: '#' + navId + 'NavbarCollapse, #<%= id %>ResponsiveButton #' + contentId,
								toggleTouch: true,
								trigger: '#<%= id %>ResponsiveButton #' + contentId
							}
						);
					}
				);
			</aui:script>
		</div>
	</div>
</div>