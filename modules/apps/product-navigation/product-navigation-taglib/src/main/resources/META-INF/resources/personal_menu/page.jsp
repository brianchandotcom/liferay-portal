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
String namespace = StringUtil.randomId() + StringPool.UNDERLINE;

boolean expanded = (boolean)request.getAttribute("liferay-product-navigation:personal-menu:expanded");
String label = (String)request.getAttribute("liferay-product-navigation:personal-menu:label");
%>

<style type="text/css">
	#clay_dropdown_portal .dropdown-menu-personal-menu {
		max-height: none;
	}

	#clay_dropdown_portal .dropdown-menu-personal-menu .dropdown-item-indicator {
		padding-right: 0.5rem;
	}

	div.personal-menu-dropdown .btn {
		border-radius: 5000px;
	}

	div.personal-menu-dropdown .dropdown-item {
		color: #6B6C7E;
	}
</style>

<div class="personal-menu-dropdown" id="<%= namespace + "personal_menu_dropdown" %>">
	<button aria-expanded="true" aria-haspopup="true" class="btn btn-unstyled dropdown-toggle" id="<%= namespace + "personal_menu_dropdown_toggle" %>" ref="triggerButton" type="button">
		<%= label %>
	</button>
</div>

<%
ResourceURL resourceURL = PortletURLFactoryUtil.create(request, PersonalMenuPortletKeys.PERSONAL_MENU, PortletRequest.RESOURCE_PHASE);

resourceURL.setParameter("currentURL", themeDisplay.getURLCurrent());
resourceURL.setParameter("portletId", themeDisplay.getPpid());
resourceURL.setResourceID("/get_personal_menu_items");
%>

<aui:script require="clay-dropdown/src/ClayDropdown as ClayDropdown,metal-dom/src/dom as dom, metal-position/src/Position">
	var toggle = document.getElementById(
		'<%= namespace + "personal_menu_dropdown_toggle" %>'
	);

	if (toggle) {
		dom.once(toggle, 'click', function(event) {
			Liferay.Util.fetch('<%= resourceURL.toString() %>', {
				method: 'GET'
			})
				.then(function(response) {
					return response.json();
				})
				.then(function(personalMenuItems) {
					var personalMenu = new ClayDropdown.default(
						{
							element:
								'#<%= namespace + "personal_menu_dropdown_toggle" %>',
							events: {
								willAttach: function(event) {
									if (<%= expanded %>) {
										this.expanded = true;
									}

									var toggleButton = this.element.querySelector(
										'button'
									);

									toggleButton.focus();

									var dropdown = this;

									this.refs.dropdown.refs.portal.on(
										'menuExpanded',
										function(event) {
											var Position =
												metalPositionSrcPosition.default;

											var menu = this.element;
											var menuRegion = Position.getRegion(
												menu
											);

											if (menuRegion.top < 0) {
												var body = document.querySelector(
													'body'
												);
												var bodyRegion = Position.getRegion(
													body
												);

												menu.style.top =
													bodyRegion.top + 'px';
											}
										}
									);

									this.refs.dropdown.refs.portal.on(
										'rendered',
										function(event) {
											if (dropdown.expanded) {
												this.element.classList.add(
													'dropdown-menu-personal-menu'
												);
												this.element.classList.remove(
													'dropdown-menu-indicator-start'
												);

												this.emit('menuExpanded', event);
											}
										}
									);
								}
							},
							items: personalMenuItems,
							itemsIconAlignment: 'right',
							label: toggle.innerHTML,
							showToggleIcon: false,
							spritemap:
								'<%= themeDisplay.getPathThemeImages().concat("/clay/icons.svg") %>'
						},
						'#<%= namespace + "personal_menu_dropdown" %>'
					);

					Liferay.once('beforeNavigate', function(event) {
						personalMenu.refs.dropdown.refs.portal.detach();
					});
				});
		});
	}
</aui:script>