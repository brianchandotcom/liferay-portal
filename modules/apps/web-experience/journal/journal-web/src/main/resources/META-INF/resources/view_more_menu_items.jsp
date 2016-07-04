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
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_more_menu_items.jsp");
portletURL.setParameter("folderId", String.valueOf(journalDisplayContext.getFolderId()));
%>

<liferay-ui:error exception="<%= MaxAddMenuFavItemsException.class %>" message='<%= LanguageUtil.format(resourceBundle, "you-cannot-add-more-than-x-favorites", journalWebConfiguration.maxAddMenuItems()) %>' />

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="all-menu-items" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container>

		<%
		List<DDMStructure> ddmStructures = JournalFolderServiceUtil.getDDMStructures(PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId), journalDisplayContext.getFolderId(), journalDisplayContext.getRestrictionType());
		%>

		<liferay-ui:search-container-results
			results="<%= ddmStructures %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.dynamic.data.mapping.model.DDMStructure"
			cssClass="selectable"
			escapedModel="<%= true %>"
			modelVar="ddmStructure"
		>
			<liferay-ui:search-container-column-text
				name="menu-item-name"
				truncate="<%= true %>"
				value="<%= ddmStructure.getUnambiguousName(ddmStructures, themeDisplay.getScopeGroupId(), locale) %>"
			/>

			<liferay-ui:search-container-column-text
				name="user"
				property="userName"
			/>

			<liferay-ui:search-container-column-text
				name="created"
			>
				<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - ddmStructure.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				name="add-to-favorites"
				path="/view_more_menu_items_actions.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
	</liferay-ui:search-container>
</div>