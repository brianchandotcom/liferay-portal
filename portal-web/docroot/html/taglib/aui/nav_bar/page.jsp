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
			<%= responsiveButtons %>

			<%= bodyContentString %>

			<aui:script use="aui-base,event-outside">
				A.one('#<%= id %>').delegate(
					'click',
					function(event) {
						var btnNavbar = event.currentTarget;

						var navId = btnNavbar.attr('data-navId');

						var navbarCollapse = A.one('#' + navId + 'NavbarCollapse');

						if (navbarCollapse) {
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

											btnNavbar.removeClass('open');
											navbarCollapse.removeClass('open');
										}
									}
								);
							}

							btnNavbar.toggleClass('open');
							navbarCollapse.toggleClass('open');

							Liferay.Data['<%= id %>Handle'] = handle;
						}
					},
					'.btn-navbar'
				);
			</aui:script>
		</div>
	</div>
</div>