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
SiteNavigationAdminManagementToolbarDisplayContext siteNavigationAdminManagementToolbarDisplayContext = new SiteNavigationAdminManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, siteNavigationAdminDisplayContext);
%>

<clay:management-toolbar-v2
	displayContext="<%= siteNavigationAdminManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="/site_navigation_admin/delete_site_navigation_menu" var="deleteSitaNavigationMenuURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteSitaNavigationMenuURL %>" cssClass="container-fluid container-fluid-max-xl" name="fm">
	<liferay-ui:search-container
		id="siteNavigationMenus"
		searchContainer="<%= siteNavigationAdminDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.site.navigation.model.SiteNavigationMenu"
			keyProperty="siteNavigationMenuId"
			modelVar="siteNavigationMenu"
		>

			<%
			row.setData(
				HashMapBuilder.<String, Object>put(
					"actions", siteNavigationAdminManagementToolbarDisplayContext.getAvailableActions(siteNavigationMenu)
				).build());
			%>

			<portlet:renderURL var="editSiteNavigationMenuURL">
				<portlet:param name="mvcPath" value="/edit_site_navigation_menu.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="siteNavigationMenuId" value="<%= String.valueOf(siteNavigationMenu.getSiteNavigationMenuId()) %>" />
			</portlet:renderURL>

			<c:choose>
				<c:when test='<%= Objects.equals(siteNavigationAdminDisplayContext.getDisplayStyle(), "descriptive") %>'>
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

						<span class="text-default">
							<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(siteNavigationMenu.getUserName()), createDateDescription} %>" key="x-created-x-ago" />
						</span>

						<h2 class="h5">
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
						</h2>

						<span class="text-default">
							<liferay-ui:message key="<%= siteNavigationMenu.getTypeKey() %>" />
						</span>
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

					<%
					Group scopeGroup = themeDisplay.getScopeGroup();
					%>

					<c:if test="<%= !scopeGroup.isCompany() %>">
						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand-smaller"
							name="add-new-pages"
							value='<%= siteNavigationMenu.isAuto() ? LanguageUtil.get(request, "yes") : StringPool.BLANK %>'
						/>
					</c:if>

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
			displayStyle="<%= siteNavigationAdminDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script require="frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as openSimpleInputModal,frontend-js-web/liferay/delegate/delegate.es as delegateModule" sandbox="<%= true %>">
	var delegate = delegateModule.default;

	var renameSiteNavigationMenuClickHandler = delegate(
		document.body,
		'click',
		'.<portlet:namespace />update-site-navigation-menu-action-option > a',
		function (event) {
			var data = event.delegateTarget.dataset;

			event.preventDefault();

			openSimpleInputModal.default({
				dialogTitle:
					'<liferay-ui:message key="rename-site-navigation-menu" />',
				formSubmitURL: data.formSubmitUrl,
				idFieldName: 'id',
				idFieldValue: data.idFieldValue,
				mainFieldLabel: '<liferay-ui:message key="name" />',
				mainFieldName: 'name',
				mainFieldPlaceholder: '<liferay-ui:message key="name" />',
				mainFieldValue: data.mainFieldValue,
				namespace: '<portlet:namespace />',
				spritemap:
					'<%= themeDisplay.getPathThemeImages() %>/clay/icons.svg',
			});
		}
	);

	function handleDestroyPortlet() {
		renameSiteNavigationMenuClickHandler.dispose();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>

<liferay-frontend:component
	componentId="<%= siteNavigationAdminManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/ManagementToolbarDefaultEventHandler"
/>