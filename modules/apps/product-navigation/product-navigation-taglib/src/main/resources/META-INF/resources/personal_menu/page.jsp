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

<%@ include file="/personal_menu/init.jsp" %>

<%
boolean expanded = (boolean)request.getAttribute("liferay-product-navigation:personal-menu:expanded");
String label = (String)request.getAttribute("liferay-product-navigation:personal-menu:label");
%>

<style type="text/css">
	#personal_menu_dropdown .btn:focus {
		box-shadow: none;
	}
</style>

<div id="personal_menu_dropdown">
	<div id="personal_menu_dropdown_toggle" style="cursor: pointer;">
		<%= label %>
	</div>

	<div id="clay_dropdown_portal"></div>
</div>

<aui:script require="clay-dropdown/src/ClayDropdown as ClayDropdown,metal-dom/src/dom as dom">
	var toggle = document.getElementById('personal_menu_dropdown_toggle');

	if (toggle) {
		dom.once(
			toggle,
			'click',
			function(event) {
				fetch(
					'<liferay-portlet:resourceURL id="/get_personal_menu_items" portletName="<%= PersonalMenuPortletKeys.PERSONAL_MENU %>" />',
					{
						credentials: 'include',
						method: 'GET'
					}
				).then(
					function(response) {
						return response.json();
					}
				).then(
					function(personalMenuItems) {
						new ClayDropdown.default(
							{
								element: '#personal_menu_dropdown_toggle',
								events: {
									'willAttach': function(event) {
										if (<%= expanded %>) {
											this.expanded = true;
										}

										var menu = this.refs.dropdown.refs.portal.refs.menu;

										menu.style.maxHeight = 'fit-content';
									}
								},
								items: personalMenuItems,
								label: toggle.innerHTML,
								showToggleIcon: false,
								spritemap: '<%= themeDisplay.getPathThemeImages().concat("/clay/icons.svg") %>'
							},
							'#personal_menu_dropdown'
						);
					}
				);
			}
		);
	}
</aui:script>