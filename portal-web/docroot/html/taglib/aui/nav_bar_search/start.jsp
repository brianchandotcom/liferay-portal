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

<%@ include file="/html/taglib/aui/nav_bar_search/init.jsp" %>

<div class="collapse nav-collapse" id="<%= id %>NavbarSearchCollapse">
	<div class="navbar-search <%= cssClass %>" id="<%= id %>" <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>>
		<c:if test="<%= Validator.isNotNull(file) %>">
			<liferay-ui:search-form
				page="<%= file %>"
				searchContainer="<%= searchContainer %>"
			/>
		</c:if>

		<aui:script use="aui-base,event-outside">
			A.one('#<%= id %>NavbarBtn').on(
				'click',
				function(event) {
					var btnNavbar = event.currentTarget;

					var navbar = btnNavbar.ancestor('.navbar');

					var navbarCollapse = A.one('#<%= id %>NavbarSearchCollapse');

					var handle = Liferay.Data['<%= id %>Handle'];

					if (navbarCollapse.hasClass('open') && handle) {
						handle.detach();

						handle = null;
					}
					else {
						handle = navbarCollapse.on(
							'mousedownoutside',
							function(event) {
								if (!btnNavbar.contains(event.target)) {
									Liferay.Data['<%= id %>Handle'] = null;

									handle.detach();

									navbarCollapse.removeClass('open');

									if (navbar) {
										navbar.all('.btn-navbar, .nav').show();
									}
								}
							}
						);
					}

					navbarCollapse.toggleClass('open');

					if (navbar) {
						navbar.all('.btn-navbar, .nav').hide();
					}

					Liferay.Data['<%= id %>Handle'] = handle;
				}
			);
		</aui:script>