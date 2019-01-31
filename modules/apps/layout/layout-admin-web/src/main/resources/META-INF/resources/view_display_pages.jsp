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
DisplayPageDisplayContext displayPageDisplayContext = new DisplayPageDisplayContext(renderRequest, renderResponse, request);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= layoutsAdminDisplayContext.getNavigationItems() %>"
/>

<%
DisplayPageManagementToolbarDisplayContext displayPageManagementToolbarDisplayContext = new DisplayPageManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, displayPageDisplayContext);
%>

<clay:management-toolbar
	displayContext="<%= displayPageManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="/layout/delete_layout_page_template_entry" var="deleteDisplayPageURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteDisplayPageURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:error key="<%= PortalException.class.getName() %>" message="one-or-more-entries-could-not-be-deleted" />
	<liferay-ui:error key="<%= RequiredLayoutPageTemplateEntryException.class.getName() %>" message="you-cannot-delete-asset-display-pages-that-are-used-by-one-or-more-assets" />

	<liferay-ui:search-container
		id="displayPages"
		searchContainer="<%= displayPageDisplayContext.getDisplayPagesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.layout.page.template.model.LayoutPageTemplateEntry"
			keyProperty="layoutPageTemplateEntryId"
			modelVar="layoutPageTemplateEntry"
		>

			<%
			row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
			%>

			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					verticalCard="<%= new DisplayPageVerticalCard(layoutPageTemplateEntry, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script require='<%= npmResolvedPackageName + "/js/DisplayPageDropdownDefaultEventHandler.es as DisplayPageDropdownDefaultEventHandler" %>'>
	Liferay.component(
		'<%= LayoutAdminWebKeys.DISPLAY_PAGE_DROPDOWN_DEFAULT_EVENT_HANDLER %>',
		new DisplayPageDropdownDefaultEventHandler.default(
			{
				namespace: '<portlet:namespace />',
				spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
			}
		),
		{
			destroyOnNavigate: true,
			portletId: '<%= HtmlUtil.escapeJS(portletDisplay.getId()) %>'
		}
	);
</aui:script>

<aui:script require='<%= npmResolvedPackageName + "/js/DisplayPageManagementToolbarDefaultEventHandler.es as DisplayPageManagementToolbarDefaultEventHandler" %>'>
	Liferay.component(
		'<%= displayPageManagementToolbarDisplayContext.getDefaultEventHandler() %>',
		new DisplayPageManagementToolbarDefaultEventHandler.default(
			{
				addDisplayPageURL: '<portlet:actionURL name="/layout/add_layout_page_template_entry"><portlet:param name="mvcRenderCommandName" value="/layout/edit_layout_page_template_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="type" value="<%= String.valueOf(LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE) %>" /></portlet:actionURL>',
				namespace: '<portlet:namespace />',
				spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
			}
		),
		{
			destroyOnNavigate: true,
			portletId: '<%= HtmlUtil.escapeJS(portletDisplay.getId()) %>'
		}
	);
</aui:script>