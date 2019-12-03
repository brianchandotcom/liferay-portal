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

<c:if test="<%= siteNavigationAdminDisplayContext.hasUpdatePermission() %>">
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
</c:if>

<div class="container-fluid-1280 contextual-sidebar-content site-navigation-content">
	<div class="lfr-search-container-wrapper site-navigation-menu-container">
		<liferay-ui:error embed="<%= false %>" key="<%= InvalidSiteNavigationMenuItemOrderException.class.getName() %>" message="the-order-of-site-navigation-menu-items-is-invalid" />

		<liferay-ui:error embed="<%= false %>" exception="<%= SiteNavigationMenuItemNameException.class %>">
			<liferay-ui:message arguments='<%= ModelHintsUtil.getMaxLength(SiteNavigationMenuItem.class.getName(), "name") %>' key="please-enter-a-name-with-fewer-than-x-characters" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<liferay-ui:error embed="<%= false %>" exception="<%= NoSuchLayoutException.class %>" message="the-page-could-not-be-found" />

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
		'*[data-type="add-button"] .dropdown-item',
		function(event) {
			Liferay.Util.openInDialog(event, {
				dialog: {
					destroyOnHide: true
				},
				dialogIframe: {
					bodyCssClass: 'dialog-with-footer'
				},
				id: '<portlet:namespace/>addMenuItem',
				title: event.delegateTarget.title || event.delegateTarget.innerText,
				uri: event.delegateTarget.href
			});
		}
	);

	var destroyAddMenuItemClickHandler = function() {
		if (addMenuItemClickHandler) {
			addMenuItemClickHandler.removeListener();

			addMenuItemClickHandler = null;
		}

		Liferay.detach(
			'<%= portletDisplay.getId() %>:portletRefreshed',
			destroyAddMenuItemClickHandler
		);
		Liferay.detach('destroyPortlet', destroyAddMenuItemClickHandler);
	};

	Liferay.on(
		'<%= portletDisplay.getId() %>:portletRefreshed',
		destroyAddMenuItemClickHandler
	);
	Liferay.on('destroyPortlet', destroyAddMenuItemClickHandler);
</aui:script>

<%
StringBundler sb = new StringBundler(6);

sb.append("metal-dom/src/dom as dom, metal-dom/src/globalEval as globalEval, ");
sb.append("frontend-js-web/liferay/util/form/object_to_form_data.es as objectToFormDataModule, ");
sb.append(npmResolvedPackageName);
sb.append("/js/SiteNavigationMenuEditor.es as siteNavigationMenuEditorModule, ");
sb.append(npmResolvedPackageName);
sb.append("/js/SiteNavigationMenuItemDOMHandler.es as siteNavigationMenuItemDOMHandlerModule");
%>

<aui:script require="<%= sb.toString() %>">
	var changed = false;
	var showSiteNavigationMenuSettingsButtonClickHandler = null;
	var sidebar = null;
	var sidebarBodyChangeHandler = null;
	var sidebarHeaderButtonClickEventListener = null;
	var siteNavigationMenuEditor = null;
	var siteNavigationMenuItemRemoveButtonClickHandler = null;
	var siteNavigationMenuItemRemoveButtonKeyupHandler = null;

	var closeSidebar = function() {
		var form = document.querySelector('#<portlet:namespace />fm');

		var error = form ? form.querySelector('[role="alert"]') : null;

		var saveChanges = false;

		if (changed) {
			if (!error) {
				saveChanges = confirm(
					'<liferay-ui:message key="you-have-unsaved-changes.-do-you-want-to-save-them" />'
				);
			}
		}

		if (saveChanges) {
			var sidebarForm = document.querySelector(
				'#<portlet:namespace />sidebarBody form'
			);

			if (sidebarForm) {
				sidebarForm.submit();
			}
		} else {
			if (sidebarHeaderButtonClickEventListener) {
				sidebarHeaderButtonClickEventListener.removeListener();
				sidebarHeaderButtonClickEventListener = null;
			}

			if (!error) {
				if (sidebarBodyChangeHandler) {
					sidebarBodyChangeHandler.removeListener();

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
		var siteNavigationMenuItem = event.newVal;

		if (!closeSidebar() || !siteNavigationMenuItem) {
			return;
		}

		openSidebar(
			siteNavigationMenuItem.dataset.title,
			'<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/edit_site_navigation_menu_item.jsp" /></portlet:renderURL>',
			{
				redirect: '<%= currentURL %>',
				siteNavigationMenuItemId:
					siteNavigationMenuItem.dataset.siteNavigationMenuItemId
			}
		);
	};

	var handlePortletDestroy = function() {
		sidebar.dispose();
		siteNavigationMenuEditor.dispose();
		siteNavigationMenuItemRemoveButtonClickHandler.removeListener();
		siteNavigationMenuItemRemoveButtonKeyupHandler.removeListener();
		showSiteNavigationMenuSettingsButtonClickHandler.removeListener();

		sidebar = null;
		siteNavigationMenuEditor = null;
		siteNavigationMenuItemRemoveButtonClickHandler = null;
		siteNavigationMenuItemRemoveButtonKeyupHandler = null;
		showSiteNavigationMenuSettingsButtonClickHandler = null;

		Liferay.detach(
			'<%= portletDisplay.getId() %>:portletRefreshed',
			handlePortletDestroy
		);
		Liferay.detach('destroyPortlet', handlePortletDestroy);
	};

	var handleShowSiteNavigationMenuSettingsButtonClick = function(event) {
		if (!closeSidebar()) {
			event.stopPropagation();

			return;
		}

		siteNavigationMenuItemDOMHandlerModule.unselectAll();

		openSidebar(
			'<%= HtmlUtil.escape(siteNavigationAdminDisplayContext.getSiteNavigationMenuName()) %>',
			'<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/site_navigation_menu_settings.jsp" /></portlet:renderURL>',
			{
				redirect: '<%= currentURL %>',
				siteNavigationMenuId:
					'<%= siteNavigationAdminDisplayContext.getSiteNavigationMenuId() %>'
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

	var openSidebar = function(title, url, data) {
		sidebar.body =
			'<div id="<portlet:namespace />sidebarBody"><div class="loading-animation"></div></div>';
		sidebar.header =
			'<div class="autofit-row sidebar-section"><div class="autofit-col autofit-col-expand"><h4 class="component-title"><span class="text-truncate-inline"><span class="text-truncate">' +
			title +
			'</span></span></h4></div><div class="autofit-col"><button class="btn btn-monospaced btn-unstyled" id="<portlet:namespace />sidebarHeaderButton" type="button"><span class="icon-monospaced"><aui:icon image="times" markupView="lexicon" /></span></button></div></div>';
		sidebar.visible = true;

		Liferay.Util.fetch(url, {
			body: objectToFormDataModule.default(
				Liferay.Util.ns('<portlet:namespace />', data)
			),
			method: 'POST'
		})
			.then(function(response) {
				return response.text();
			})
			.then(function(responseContent) {
				var sidebarBody = document.getElementById(
					'<portlet:namespace />sidebarBody'
				);

				var sidebarHeaderButton = document.getElementById(
					'<portlet:namespace />sidebarHeaderButton'
				);

				if (sidebarBody) {
					sidebarBody.innerHTML = responseContent;

					globalEval.default.runScriptsInElement(sidebarBody);

					sidebarBodyChangeHandler = dom.on(
						sidebarBody,
						'change',
						handleSidebarBodyChange
					);
				}

				if (sidebarHeaderButton) {
					sidebarHeaderButtonClickEventListener = dom.on(
						sidebarHeaderButton,
						'click',
						handleSidebarCloseButtonClick
					);
				}
			});
	};

	<c:if test="<%= siteNavigationAdminDisplayContext.hasUpdatePermission() %>">
		Liferay.componentReady('<portlet:namespace />sidebar').then(function(_sidebar) {
			sidebar = _sidebar;

			sidebar.on('hide', closeSidebar);

			siteNavigationMenuEditor = new siteNavigationMenuEditorModule.default({
				editSiteNavigationMenuItemParentURL:
					'<portlet:actionURL name="/navigation_menu/edit_site_navigation_menu_item_parent"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>',
				namespace: '<portlet:namespace />'
			});

			siteNavigationMenuEditor.on(
				'selectedMenuItemChanged',
				handleSelectedMenuItemChanged
			);

			siteNavigationMenuItemRemoveButtonClickHandler = dom.delegate(
				document.body,
				'click',
				'.site-navigation-menu-item__remove-icon',
				handleSiteNavigationMenuItemRemoveIconClick
			);

			siteNavigationMenuItemRemoveButtonKeyupHandler = dom.delegate(
				document.body,
				'keyup',
				'.site-navigation-menu-item__remove-icon',
				handleSiteNavigationMenuItemRemoveIconKeyup
			);

			showSiteNavigationMenuSettingsButtonClickHandler = dom.on(
				'#<portlet:namespace />showSiteNavigationMenuSettings',
				'click',
				handleShowSiteNavigationMenuSettingsButtonClick
			);

			Liferay.on(
				'<%= portletDisplay.getId() %>:portletRefreshed',
				handlePortletDestroy
			);
			Liferay.on('destroyPortlet', handlePortletDestroy);
		});
	</c:if>
</aui:script>