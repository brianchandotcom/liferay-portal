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
String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(siteNavigationAdminDisplayContext.getSiteNavigationMenuName());
%>

<nav class="management-bar management-bar-light navbar navbar-expand-md">
	<div class="container">
		<ul class="navbar-nav"></ul>

		<ul class="navbar-nav">
			<li class="nav-item">
				<button class="btn btn-unstyled nav-link nav-link-monospaced" id="<portlet:namespace />showSiteNavigationMenuSettings" type="button">
					<aui:icon cssClass="icon-monospaced" image="cog" markupView="lexicon" />
				</button>
			</li>
			<li class="nav-item">
				<clay:dropdown-menu
					buttonStyle="primary"
					dropdownItems="<%= siteNavigationAdminDisplayContext.getAddSiteNavigationMenuItemDropdownItems() %>"
					icon="plus"
					style="primary"
					triggerCssClasses="nav-btn nav-btn-monospaced"
				/>
			</li>
		</ul>
	</div>
</nav>

<liferay-ui:error key="<%= InvalidSiteNavigationMenuItemOrderException.class.getName() %>" message="the-order-of-site-navigation-menu-items-is-invalid" />

<div class="container-fluid-1280 contextual-sidebar-content">
	<div class="site-navigation-menu-container">

		<%
		List<SiteNavigationMenuItem> siteNavigationMenuItems = SiteNavigationMenuItemLocalServiceUtil.getSiteNavigationMenuItems(siteNavigationAdminDisplayContext.getSiteNavigationMenuId(), 0);
		%>

		<c:choose>
			<c:when test="<%= siteNavigationMenuItems.size() > 0 %>">
				<div class="hide" data-site-navigation-menu-item-id="0"></div>

				<%
				for (SiteNavigationMenuItem siteNavigationMenuItem : siteNavigationMenuItems) {
				%>

					<liferay-util:include page="/view_site_navigation_menu_item.jsp" servletContext="<%= application %>">
						<liferay-util:param name="siteNavigationMenuItemId" value="<%= String.valueOf(siteNavigationMenuItem.getSiteNavigationMenuItemId()) %>" />
					</liferay-util:include>

				<%
				}
				%>

			</c:when>
			<c:otherwise>
				<liferay-frontend:empty-result-message
					actionDropdownItems="<%= siteNavigationAdminDisplayContext.getAddSiteNavigationMenuItemDropdownItems() %>"
					description='<%= LanguageUtil.get(request, "fortunately-it-is-very-easy-to-add-new-ones") %>'
				/>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<liferay-frontend:contextual-sidebar
	body="<%= StringPool.BLANK %>"
	componentId='<%= renderResponse.getNamespace() + "sidebar" %>'
	header="<%= StringPool.BLANK %>"
	id='<%= renderResponse.getNamespace() + "sidebar" %>'
	namespace="<%= renderResponse.getNamespace() %>"
	visible="<%= false %>"
/>

<aui:script require="metal-dom/src/all/dom as dom">
	var addMenuItemClickHandler = dom.delegate(
		document.body,
		'click',
		'.dropdown-item',
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
					title: event.delegateTarget.title || event.delegateTarget.innerText,
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

<aui:script require="site-navigation-menu-web/js/SiteNavigationMenuEditor.es as siteNavigationMenuEditorModule">
	var siteNavigationMenuEditor = new siteNavigationMenuEditorModule.default(
		{
			editSiteNavigationMenuItemParentURL: '<portlet:actionURL name="/navigation_menu/edit_site_navigation_menu_item_parent"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>',
			menuContainerSelector: '.site-navigation-menu-container',
			menuItemContainerSelector: '.container-item',
			menuItemSelector: '.site-navigation-menu-item .lfr-card-title-text a',
			namespace: '<portlet:namespace />'
		}
	);

	function handlePortletDestroy() {
		if (siteNavigationMenuEditor) {
			siteNavigationMenuEditor.dispose();

			siteNavigationMenuEditor = null;
		}

		Liferay.detach('destroyPortlet', handlePortletDestroy);
	}

	Liferay.on('destroyPortlet', handlePortletDestroy);
</aui:script>

<aui:script use="aui-base,aui-parse-content">
	Liferay.componentReady(
		'<portlet:namespace/>sidebar'
	)
	.then(
		function(sidebar) {
			var changed = false;
			var sidebarBodyChangeHandler = null;

			function openSidebar(title) {
				sidebar.body = '<div id="<portlet:namespace />sidebarBody"><div class="loading-animation"></div></div>';
				sidebar.header = title + '<button class="btn btn-monospaced btn-sm btn-unstyled" id="<portlet:namespace />sidebarHeaderButton" type="button"><aui:icon image="angle-right" markupView="lexicon" /></button>';
				sidebar.visible = true;
			}

			function closeSidebar () {
				var saveChanges = !changed ? false : confirm(
					'<liferay-ui:message key="you-have-unsaved-changes.-do-you-want-to-save-them" />'
				);

				if (!changed || !saveChanges) {
					if (sidebarBodyChangeHandler) {
						sidebarBodyChangeHandler.detach();

						sidebarBodyChangeHandler = null;
					}

					sidebar.body = '';
					sidebar.visible = false;

					changed = false;

					return true;
				}
				else if (saveChanges) {
					var form = A.one('#<portlet:namespace />sidebarBody form');

					if (form) {
						form.submit();
					}

					return false;
				}

				return false;
			}

			sidebar.on('hide', closeSidebar);

			function handleSidebarBodyChange() {
				changed = true;
			}

			function setSidebarBody(content) {
				var sidebarBody = A.one('#<portlet:namespace />sidebarBody');
				var sidebarHeaderButton = A.one('#<portlet:namespace />sidebarHeaderButton');

				if (sidebarBody) {
					sidebarBody.plug(A.Plugin.ParseContent);

					sidebarBody.setContent(content);
					sidebarBodyChangeHandler = sidebarBody.on('change', handleSidebarBodyChange);
				}

				if (sidebarHeaderButton) {
					sidebarHeaderButton.on('click', closeSidebar);
				}
			}

			A.one('.site-navigation-menu-container').delegate(
				'click',
				function(event) {
					if (!closeSidebar()) {
						event.stopPropagation();

						return;
					}

					var currentTarget = event.currentTarget;

					var siteNavigationMenuItem = currentTarget.ancestor('.site-navigation-menu-item');

					var data = Liferay.Util.ns(
						'<portlet:namespace />',
						{
							redirect: '<%= currentURL %>',
							siteNavigationMenuItemId: siteNavigationMenuItem.attr('data-site-navigation-menu-item-id')
						}
					);

					openSidebar(siteNavigationMenuItem.attr('data-title'));

					A.io.request(
						'<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/edit_site_navigation_menu_item.jsp" /></portlet:renderURL>',
						{
							data: data,
							on: {
								success: function(event, id, obj) {
									var responseData = this.get('responseData');

									setSidebarBody(responseData);
								}
							}
						}
					);
				},
				'.site-navigation-menu-item .lfr-card-title-text a'
			);

			A.one('#<portlet:namespace />showSiteNavigationMenuSettings').on(
				'click',
				function() {
					if (!closeSidebar()) {
						event.stopPropagation();

						return;
					}

					var data = Liferay.Util.ns(
						'<portlet:namespace />',
						{
							redirect: '<%= currentURL %>',
							siteNavigationMenuId: <%= siteNavigationAdminDisplayContext.getSiteNavigationMenuId() %>
						}
					);

					openSidebar('<%= HtmlUtil.escape(siteNavigationAdminDisplayContext.getSiteNavigationMenuName()) %>');

					A.io.request(
						'<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/site_navigation_menu_settings.jsp" /></portlet:renderURL>',
						{
							data: data,
							on: {
								success: function(event, id, obj) {
									var responseData = this.get('responseData');

									setSidebarBody(responseData);
								}
							}
						}
					);
				}
			);

			function handleDestroyPortlet() {
				sidebar.dispose();

				Liferay.detach('destroyPortlet', handleDestroyPortlet);
			}

			Liferay.on('destroyPortlet', handleDestroyPortlet);
		}
	);
</aui:script>