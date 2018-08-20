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

<nav class="management-bar management-bar-light navbar navbar-expand-md site-navigation-management-bar">
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

<liferay-ui:error exception="<%= SiteNavigationMenuItemNameException.class %>">
	<liferay-ui:message arguments='<%= ModelHintsUtil.getMaxLength(SiteNavigationMenuItem.class.getName(), "name") %>' key="please-enter-a-name-with-fewer-than-x-characters" translateArguments="<%= false %>" />
</liferay-ui:error>

<div class="container-fluid-1280 contextual-sidebar-content site-navigation-content">
	<div class="lfr-search-container-wrapper site-navigation-menu-container">

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

	var destroyAddMenuItemClickHandler = function() {
		if (addMenuItemClickHandler) {
			addMenuItemClickHandler.removeListener();

			addMenuItemClickHandler = null;
		}

		Liferay.detach('<%= portletDisplay.getId() %>:portletRefreshed', destroyAddMenuItemClickHandler);
		Liferay.detach('destroyPortlet', destroyAddMenuItemClickHandler);
	};

	Liferay.on('<%= portletDisplay.getId() %>:portletRefreshed', destroyAddMenuItemClickHandler);
	Liferay.on('destroyPortlet', destroyAddMenuItemClickHandler);
</aui:script>

<aui:script require="site-navigation-menu-web/js/SiteNavigationMenuEditor.es as siteNavigationMenuEditorModule, site-navigation-menu-web/js/SiteNavigationMenuItemDOMHandler.es as siteNavigationMenuItemDOMHandlerModule">
	var changed = false;
	var showSiteNavigationMenuSettingsButtonClickHandler = null;
	var sidebar = null;
	var sidebarBodyChangeHandler = null;
	var siteNavigationMenuEditor = null;
	var siteNavigationMenuItemRemoveButtonClickHandler = null;
	var siteNavigationMenuItemRemoveButtonKeyupHandler = null;

	var closeSidebar = function() {
		let form = document.querySelector('#<portlet:namespace />fm');
		let error = form ? form.querySelector("[role='alert']") : null;

		var saveChanges = false;

		if (changed) {
			if (!error) {
				saveChanges = confirm(
					'<liferay-ui:message key="you-have-unsaved-changes.-do-you-want-to-save-them" />'
				);
			}
		}

		if (saveChanges) {
			AUI().use(
				['aui-base'],
				function(A) {
					var form = A.one('#<portlet:namespace />sidebarBody form');

					if (form) {
						form.submit();
					}
				}
			);
		}
		else {
			if (!error) {
				if (sidebarBodyChangeHandler) {
					sidebarBodyChangeHandler.detach();

					sidebarBodyChangeHandler = null;
				}

				sidebar.body = '';
				sidebar.visible = false;

				changed = false;
			}
		}

		return !saveChanges;
	};

	var handleSelectedMenuItemChanged = function(event) {
		const siteNavigationMenuItem = event.newVal;

		if (!closeSidebar() || !siteNavigationMenuItem) {
			return;
		}

		var data = Liferay.Util.ns(
			'<portlet:namespace />',
			{
				redirect: '<%= currentURL %>',
				siteNavigationMenuItemId: siteNavigationMenuItem.dataset.siteNavigationMenuItemId
			}
		);

		openSidebar(siteNavigationMenuItem.dataset.title);

		AUI().use(
			['aui-base'],
			function(A) {
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
			}
		);
	};

	var handlePortletDestroy = function() {
		sidebar.dispose();
		siteNavigationMenuEditor.dispose();
		siteNavigationMenuItemRemoveButtonClickHandler.detach();
		siteNavigationMenuItemRemoveButtonKeyupHandler.detach();
		showSiteNavigationMenuSettingsButtonClickHandler.detach();

		sidebar = null;
		siteNavigationMenuEditor = null;
		siteNavigationMenuItemRemoveButtonClickHandler = null;
		siteNavigationMenuItemRemoveButtonKeyupHandler = null;
		showSiteNavigationMenuSettingsButtonClickHandler = null;

		Liferay.detach('<%= portletDisplay.getId() %>:portletRefreshed', handlePortletDestroy);
		Liferay.detach('destroyPortlet', handlePortletDestroy);
	};

	var handleShowSiteNavigationMenuSettingsButtonClick = function(event) {
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

		siteNavigationMenuItemDOMHandlerModule.unselectAll();

		openSidebar('<%= HtmlUtil.escape(siteNavigationAdminDisplayContext.getSiteNavigationMenuName()) %>');

		AUI().use(
			['aui-base'],
			function(A) {
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
	};

	var handleSidebarBodyChange = function() {
		changed = true;
	};

	var handleSidebarCloseButtonClick = function() {
		closeSidebar();
		siteNavigationMenuItemDOMHandlerModule.unselectAll();
	};

	var handleSiteNavigationMenuItemRemoveIconClick = function(event) {
		event.stopPropagation();
	};

	var handleSiteNavigationMenuItemRemoveIconKeyup = function(event) {
		if (event.which === 32) {
			event.preventDefault();
			event.stopPropagation();
			event.target.getDOMNode().click();
		}
	};

	var openSidebar = function(title) {
		sidebar.body = '<div id="<portlet:namespace />sidebarBody"><div class="loading-animation"></div></div>';
		sidebar.header = '<div class="autofit-row sidebar-section"><div class="autofit-col autofit-col-expand"><h4 class="component-title"><span class="text-truncate-inline"><span class="text-truncate">' + title + '</span></span></h4></div><div class="autofit-col"><button class="btn btn-monospaced btn-unstyled" id="<portlet:namespace />sidebarHeaderButton" type="button"><span class="icon-monospaced"><aui:icon image="times" markupView="lexicon" /></span></button></div></div>';
		sidebar.visible = true;
	};

	var setSidebarBody = function(content) {
		AUI().use(
			['aui-base', 'aui-parse-content'],
			function(A) {
				var sidebarBody = A.one('#<portlet:namespace />sidebarBody');
				var sidebarHeaderButton = A.one('#<portlet:namespace />sidebarHeaderButton');

				if (sidebarBody) {
					sidebarBody.plug(A.Plugin.ParseContent);

					sidebarBody.setContent(content);
					sidebarBodyChangeHandler = sidebarBody.on('change', handleSidebarBodyChange);
				}

				if (sidebarHeaderButton) {
					sidebarHeaderButton.on('click', handleSidebarCloseButtonClick);
				}
			}
		);
	};

	Liferay.componentReady(
		'<portlet:namespace />sidebar'
	)
	.then(
		function(_sidebar) {
			sidebar = _sidebar;

			sidebar.on('hide', closeSidebar);

			siteNavigationMenuEditor = new siteNavigationMenuEditorModule.default(
				{
					editSiteNavigationMenuItemParentURL: '<portlet:actionURL name="/navigation_menu/edit_site_navigation_menu_item_parent"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>',
					namespace: '<portlet:namespace />'
				}
			);

			siteNavigationMenuEditor.on(
				'selectedMenuItemChanged',
				handleSelectedMenuItemChanged
			);

			AUI().use(
				['aui-base'],
				function(A) {
					siteNavigationMenuItemRemoveButtonClickHandler = A
						.all('.site-navigation-menu-item__remove-icon')
						.on('click', handleSiteNavigationMenuItemRemoveIconClick);

					siteNavigationMenuItemRemoveButtonKeyupHandler = A
						.all('.site-navigation-menu-item__remove-icon')
						.on('keyup', handleSiteNavigationMenuItemRemoveIconKeyup);

					showSiteNavigationMenuSettingsButtonClickHandler = A
						.one('#<portlet:namespace />showSiteNavigationMenuSettings')
						.on('click', handleShowSiteNavigationMenuSettingsButtonClick);
				}
			);

			Liferay.on('<%= portletDisplay.getId() %>:portletRefreshed', handlePortletDestroy);
			Liferay.on('destroyPortlet', handlePortletDestroy);
		}
	);
</aui:script>