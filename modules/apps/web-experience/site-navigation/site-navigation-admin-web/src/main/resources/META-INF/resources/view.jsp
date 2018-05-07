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

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= siteNavigationAdminDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	actionItems="<%= siteNavigationAdminDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= siteNavigationAdminDisplayContext.getClearResultsURL() %>"
	componentId="siteNavigationMenuWebManagementToolbar"
	creationMenu="<%= siteNavigationAdminDisplayContext.isShowAddButton() ? siteNavigationAdminDisplayContext.getCreationMenu() : null %>"
	filterItems="<%= siteNavigationAdminDisplayContext.getFilterDropdownItems() %>"
	searchActionURL="<%= siteNavigationAdminDisplayContext.getSearchActionURL() %>"
	searchContainerId="siteNavigationMenus"
	searchFormName="searchFm"
	sortingOrder="<%= siteNavigationAdminDisplayContext.getOrderByType() %>"
	sortingURL="<%= siteNavigationAdminDisplayContext.getSortingURL() %>"
	totalItems="<%= siteNavigationAdminDisplayContext.getTotalItems() %>"
	viewTypes="<%= siteNavigationAdminDisplayContext.getViewTypeItems() %>"
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
							<aui:a href="<%= editSiteNavigationMenuURL %>">
								<%= HtmlUtil.escape(siteNavigationMenu.getName()) %>
							</aui:a>
						</h5>

						<h6 class="text-default">
							<liferay-ui:message key="<%= siteNavigationMenu.getTypeKey() %>" />
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/site_navigation_menu_action.jsp"
					/>
				</c:when>
				<c:when test='<%= displayStyle.equals("icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							actionJsp="/site_navigation_menu_action.jsp"
							actionJspServletContext="<%= application %>"
							icon="list"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							title="<%= HtmlUtil.escape(siteNavigationMenu.getName()) %>"
							url="<%= editSiteNavigationMenuURL %>"
						>
							<liferay-frontend:vertical-card-sticker-bottom>
								<liferay-ui:user-portrait
									cssClass="sticker sticker-bottom"
									userId="<%= siteNavigationMenu.getUserId() %>"
								/>
							</liferay-frontend:vertical-card-sticker-bottom>

							<liferay-frontend:vertical-card-header>
								<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - siteNavigationMenu.getModifiedDate().getTime(), true), HtmlUtil.escape(siteNavigationMenu.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= true %>" />
							</liferay-frontend:vertical-card-header>

							<liferay-frontend:vertical-card-footer>
								<liferay-ui:message key="<%= siteNavigationMenu.getTypeKey() %>" />
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:icon-vertical-card>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						href="<%= editSiteNavigationMenuURL %>"
						name="title"
						value="<%= HtmlUtil.escape(siteNavigationMenu.getName()) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content text-center"
						name="add-new-pages"
						value='<%= siteNavigationMenu.isAuto() ? LanguageUtil.get(request, "yes") : StringPool.BLANK %>'
					/>

					<liferay-ui:search-container-column-text
						name="marked-as"
						value="<%= LanguageUtil.get(request, siteNavigationMenu.getTypeKey()) %>"
					/>

					<liferay-ui:search-container-column-text
						name="author"
						value="<%= HtmlUtil.escape(PortalUtil.getUserName(siteNavigationMenu)) %>"
					/>

					<liferay-ui:search-container-column-date
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
	window.<portlet:namespace />addNavigationMenuMenuItem = function(event) {
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
	}

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

	window.<portlet:namespace />deleteSelectedSiteNavigationMenus = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm($(document.<portlet:namespace />fm));
		}
	}

	function handleDestroyPortlet() {
		renameSiteNavigationMenuClickHandler.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>