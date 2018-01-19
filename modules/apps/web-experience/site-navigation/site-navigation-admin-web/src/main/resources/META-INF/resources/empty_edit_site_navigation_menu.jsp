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

<div class="container-fluid-1280">
	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<div class="text-center">
				<div>
					<liferay-ui:message key="this-menu-is-empty" />
				</div>

				<div>
					<liferay-ui:message key="please-add-elements" />
				</div>
			</div>

			<div class="row" id="<portlet:namespace/>siteNavigationMenuItemTypes">

				<%
				for (SiteNavigationMenuItemType siteNavigationMenuItemType : siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemTypes()) {
				%>

					<div class="col-md-2">
						<div class="card card-type-asset">
							<div class="aspect-ratio card-item-first">
								<div class="aspect-ratio-item-center-middle aspect-ratio-item-fluid">
									<liferay-ui:icon
										icon="<%= siteNavigationMenuItemType.getIcon() %>"
										markupView="lexicon"
									/>
								</div>
							</div>

							<div class="card-body">
								<div class="card-row">
									<div class="flex-col flex-col-expand">
										<div class="card-title text-center text-truncate">
											<portlet:renderURL var="addSiteNavigationMenuItemRedirectURL">
												<portlet:param name="mvcPath" value="/add_site_navigation_menu_item_redirect.jsp" />
												<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
											</portlet:renderURL>

											<liferay-portlet:renderURL var="addURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
												<portlet:param name="mvcPath" value="/add_site_navigation_menu_item.jsp" />
												<portlet:param name="redirect" value="<%= addSiteNavigationMenuItemRedirectURL %>" />
												<portlet:param name="siteNavigationMenuId" value="<%= String.valueOf(siteNavigationAdminDisplayContext.getSiteNavigationMenuId()) %>" />
												<portlet:param name="type" value="<%= siteNavigationMenuItemType.getType() %>" />
											</liferay-portlet:renderURL>

											<aui:a cssClass="add-menu-item-link" href="<%= addURL %>" label="<%= siteNavigationMenuItemType.getLabel(locale) %>" title="<%= siteNavigationMenuItemType.getLabel(locale) %>" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

				<%
				}
				%>

			</div>
		</aui:fieldset>
	</aui:fieldset-group>
</div>

<aui:script require="metal-dom/src/all/dom as dom">
	var addMenuItemClickHandler = dom.delegate(
		document.body,
		'click',
		'.add-menu-item-link',
		function(event) {
			Liferay.Util.openInDialog(
				event,
				{
					dialog: {
						destroyOnHide: true
					},
					dialogIframe: {
						bodyCssClass: 'dialog-with-footer'
					},
					id: '<portlet:namespace/>addMenuItem',
					title: event.delegateTarget.title,
					uri: event.delegateTarget.href
				}
			);
		}
	);

	function handleDestroyPortlet() {
		addMenuItemClickHandler.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>