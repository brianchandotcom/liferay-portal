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
List<SiteNavigationMenu> autoSiteNavigationMenus = layoutsAdminDisplayContext.getAutoSiteNavigationMenus();
%>

<div class="container-fluid-1280 pt-2">
	<liferay-frontend:edit-form
		action="<%= layoutsAdminDisplayContext.getAddLayoutURL() %>"
		method="post"
		name="fm"
		onSubmit="event.preventDefault();"
	>
		<liferay-frontend:edit-form-body>
			<aui:input label="name" name="name" required="<%= true %>" />

			<c:if test="<%= ListUtil.isNotEmpty(autoSiteNavigationMenus) %>">
				<liferay-ui:message key="add-this-page-to-the-following-menus" />

				<div class="auto-site-navigation-menus container mt-3">
					<div class="row">

						<%
						for (SiteNavigationMenu autoSiteNavigationMenu : autoSiteNavigationMenus) {
						%>

							<div class="col-6">
								<aui:input id='<%= "menu_" + autoSiteNavigationMenu.getSiteNavigationMenuId() %>' label="<%= autoSiteNavigationMenu.getName() %>" name="TypeSettingsProperties--siteNavigationMenuId--" type="checkbox" value="<%= autoSiteNavigationMenu.getSiteNavigationMenuId() %>" />
							</div>

						<%
						}
						%>

					</div>
				</div>
			</c:if>
		</liferay-frontend:edit-form-body>

		<liferay-frontend:edit-form-footer>
			<aui:button type="cancel" value="close" />
			<aui:button type="submit" value="save" />
		</liferay-frontend:edit-form-footer>
	</liferay-frontend:edit-form>
</div>

<aui:script require="metal-uri/src/Uri">
	var form = document.getElementById('<portlet:namespace />fm');

	var Uri = metalUriSrcUri.default;

	$(form).on(
		'submit',
		function(event) {
			var formData = new FormData();

			Array.prototype.slice.call(
				form.querySelectorAll('input')
			).forEach(
				function(input) {
					if (input.type == 'checkbox' && !input.checked) {
						return;
					}

					if (input.name && input.value) {
						formData.append(input.name, input.value);
					}
				}
			);

			fetch(
				form.action,
				{
					body: formData,
					credentials: 'include',
					method: 'POST'
				}
			).then(
				function(response) {
					return response.json();
				}
			).then(
				function(response) {
					if (response.redirectURL) {
						var redirectURL = new Uri(response.redirectURL);

						redirectURL.setParameterValue('p_p_state', 'normal');

						Liferay.fire(
							'closeWindow',
							{
								id: '<portlet:namespace />addLayoutDialog',
								redirect: redirectURL.toString()
							}
						);
					}
					else {
						new Liferay.Alert(
							{
								delay: {
									hide: 500,
									show: 0
								},
								duration: 500,
								icon: 'exclamation-circle',
								message: response.errorMessage,
								type: 'danger'
							}
						).render();
					}
				}
			);
		}
	);
</aui:script>