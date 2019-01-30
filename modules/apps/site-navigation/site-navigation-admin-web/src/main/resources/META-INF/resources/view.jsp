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
String displayStyle = siteNavigationAdminDisplayContext.getDisplayStyle();
%>

<clay:management-toolbar
	actionDropdownItems="<%= siteNavigationAdminDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= siteNavigationAdminDisplayContext.getClearResultsURL() %>"
	componentId="siteNavigationMenuWebManagementToolbar"
	filterDropdownItems="<%= siteNavigationAdminDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= siteNavigationAdminDisplayContext.getTotalItems() %>"
	searchActionURL="<%= siteNavigationAdminDisplayContext.getSearchActionURL() %>"
	searchContainerId="siteNavigationMenus"
	searchFormName="searchFm"
	showCreationMenu="<%= siteNavigationAdminDisplayContext.isShowAddButton() %>"
	sortingOrder="<%= siteNavigationAdminDisplayContext.getOrderByType() %>"
	sortingURL="<%= siteNavigationAdminDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= siteNavigationAdminDisplayContext.getViewTypeItems() %>"
/>

<portlet:actionURL name="/navigation_menu/delete_site_navigation_menu" var="deleteSitaNavigationMenuURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteSitaNavigationMenuURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="siteNavigationMenus"
		searchContainer="<%= siteNavigationAdminDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.site.navigation.model.SiteNavigationMenu"
			keyProperty="siteNavigationMenuId"
			modelVar="siteNavigationMenu"
		>
			<portlet:renderURL var="editSiteNavigationMenuURL">
				<portlet:param name="mvcPath" value="/edit_site_navigation_menu.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="siteNavigationMenuId" value="<%= String.valueOf(siteNavigationMenu.getSiteNavigationMenuId()) %>" />
			</portlet:renderURL>

			<c:choose>
				<c:when test='<%= displayStyle.equals("descriptive") %>'>
					<liferay-ui:search-container-column-user
						showDetails="<%= false %>"
						userId="<%= siteNavigationMenu.getUserId() %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>

						<%
						Date createDate = siteNavigationMenu.getCreateDate();

						String createDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
						%>

						<h6 class="text-default">
							<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(siteNavigationMenu.getUserName()), createDateDescription} %>" key="x-created-x-ago" />
						</h6>

						<h5>
							<c:choose>
								<c:when test="<%= siteNavigationAdminDisplayContext.hasEditPermission() %>">
									<aui:a href="<%= editSiteNavigationMenuURL %>">
										<%= HtmlUtil.escape(siteNavigationMenu.getName()) %>
									</aui:a>
								</c:when>
								<c:otherwise>
									<%= HtmlUtil.escape(siteNavigationMenu.getName()) %>
								</c:otherwise>
							</c:choose>
						</h5>

						<h6 class="text-default">
							<liferay-ui:message key="<%= siteNavigationMenu.getTypeKey() %>" />
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/site_navigation_menu_action.jsp"
					/>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-200 table-list-title"
						href="<%= siteNavigationAdminDisplayContext.hasEditPermission() ? editSiteNavigationMenuURL : null %>"
						name="title"
						value="<%= HtmlUtil.escape(siteNavigationMenu.getName()) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smaller"
						name="add-new-pages"
						value='<%= siteNavigationMenu.isAuto() ? LanguageUtil.get(request, "yes") : StringPool.BLANK %>'
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smaller table-cell-minw-150"
						name="marked-as"
						value="<%= LanguageUtil.get(request, siteNavigationMenu.getTypeKey()) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smallest table-cell-minw-150"
						name="author"
						value="<%= HtmlUtil.escape(PortalUtil.getUserName(siteNavigationMenu)) %>"
					/>

					<liferay-ui:search-container-column-date
						cssClass="table-cell-minw-150"
						name="create-date"
						property="createDate"
					/>

					<liferay-ui:search-container-column-jsp
						path="/site_navigation_menu_action.jsp"
					/>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script require="metal-dom/src/all/dom as dom,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands" sandbox="<%= true %>">
	var addNavigationMenuMenuItem = function(event) {
		modalCommands.openSimpleInputModal(
			{
				dialogTitle: '<liferay-ui:message key="add-menu" />',
				formSubmitURL: '<portlet:actionURL name="/navigation_menu/add_site_navigation_menu"><portlet:param name="mvcPath" value="/edit_site_navigation_menu.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>',
				mainFieldLabel: '<liferay-ui:message key="name" />',
				mainFieldName: 'name',
				mainFieldPlaceholder: '<liferay-ui:message key="name" />',
				namespace: '<portlet:namespace />',
				spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
			}
		);
	};

	var renameSiteNavigationMenuClickHandler = dom.delegate(
		document.body,
		'click',
		'.<portlet:namespace />update-site-navigation-menu-action-option > a',
		function(event) {
			var data = event.delegateTarget.dataset;

			event.preventDefault();

			modalCommands.openSimpleInputModal(
				{
					dialogTitle: '<liferay-ui:message key="rename-site-navigation-menu" />',
					formSubmitURL: data.formSubmitUrl,
					idFieldName: 'id',
					idFieldValue: data.idFieldValue,
					mainFieldLabel: '<liferay-ui:message key="name" />',
					mainFieldName: 'name',
					mainFieldPlaceholder: '<liferay-ui:message key="name" />',
					mainFieldValue: data.mainFieldValue,
					namespace: '<portlet:namespace />',
					spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
				}
			);
		}
	);

	var deleteSelectedSiteNavigationMenus = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm($(document.<portlet:namespace />fm));
		}
	};

	var ACTIONS = {
		'addNavigationMenuMenuItem': addNavigationMenuMenuItem,
		'deleteSelectedSiteNavigationMenus': deleteSelectedSiteNavigationMenus
	};

	Liferay.componentReady('siteNavigationMenuWebManagementToolbar').then(
		function(managementToolbar) {
			managementToolbar.on('creationButtonClicked', addNavigationMenuMenuItem);

			managementToolbar.on(
				'actionItemClicked',
				function(event) {
					var itemData = event.data.item.data;

					if (itemData && itemData.action && ACTIONS[itemData.action]) {
						ACTIONS[itemData.action]();
					}
				}
			);
		}
	);

	function handleDestroyPortlet() {
		renameSiteNavigationMenuClickHandler.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>