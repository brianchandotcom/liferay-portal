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

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="siteNavigationMenus"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews="<%= siteNavigationAdminDisplayContext.getDisplayViews() %>"
			portletURL="<%= siteNavigationAdminDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= siteNavigationAdminDisplayContext.getDisplayStyle() %>"
		/>

		<c:if test="<%= siteNavigationAdminDisplayContext.isShowAddButton() %>">
			<portlet:renderURL var="addSiteNavigationMenuURL">
				<portlet:param name="mvcPath" value="/edit_site_navigation_menu.jsp" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					id="addNavigationMenuMenuItem"
					title='<%= LanguageUtil.get(request, "add-menu") %>'
					url="<%= addSiteNavigationMenuURL %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			label="all"
		>
			<liferay-frontend:management-bar-filter-item
				active="<%= true %>"
				label="all"
				url="<%= siteNavigationAdminDisplayContext.getPortletURL().toString() %>"
			/>
		</liferay-frontend:management-bar-navigation>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= siteNavigationAdminDisplayContext.getOrderByCol() %>"
			orderByType="<%= siteNavigationAdminDisplayContext.getOrderByType() %>"
			orderColumns="<%= siteNavigationAdminDisplayContext.getOrderColumns() %>"
			portletURL="<%= siteNavigationAdminDisplayContext.getPortletURL() %>"
		/>

		<li>

			<%
			PortletURL portletURL = liferayPortletResponse.createRenderURL();
			%>

			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
				<liferay-ui:input-search
					markupView="lexicon"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href="javascript:;"
			icon="trash"
			id="deleteSelectedSiteNavigationMenus"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

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
								<%= siteNavigationMenu.getName() %>
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
							title="<%= siteNavigationMenu.getName() %>"
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
						value="<%= siteNavigationMenu.getName() %>"
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

<portlet:actionURL name="/navigation_menu/add_site_navigation_menu" var="addSiteNavigationMenuURL">
	<portlet:param name="mvcPath" value="/edit_site_navigation_menu.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:script require="metal-dom/src/all/dom as dom,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands" sandbox="<%= true %>">
	var addNavigationMenuClickHandler = dom.on(
		'#<portlet:namespace />addNavigationMenuMenuItem',
		'click',
		function(event) {
			event.preventDefault();

			modalCommands.openSimpleInputModal(
				{
					dialogTitle: '<liferay-ui:message key="add-menu" />',
					formSubmitURL: '<%= addSiteNavigationMenuURL %>',
					mainFieldLabel: '<liferay-ui:message key="name" />',
					mainFieldName: 'name',
					mainFieldPlaceholder: '<liferay-ui:message key="name" />',
					namespace: '<portlet:namespace />',
					spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
				}
			);
		}
	);

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

	var deleteSelectedSiteNavigationMenusClickHandler = dom.on(
		'#<portlet:namespace />deleteSelectedSiteNavigationMenus',
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);

	function handleDestroyPortlet() {
		addNavigationMenuClickHandler.removeListener();
		renameSiteNavigationMenuClickHandler.removeListener();
		deleteSelectedSiteNavigationMenusClickHandler.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>