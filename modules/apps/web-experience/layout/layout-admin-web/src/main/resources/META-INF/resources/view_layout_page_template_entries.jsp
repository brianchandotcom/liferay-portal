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
LayoutPageTemplateDisplayContext layoutPageTemplateDisplayContext = new LayoutPageTemplateDisplayContext(renderRequest, renderResponse, request);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(layoutPageTemplateDisplayContext.getLayoutPageTemplateCollectionRedirect());

renderResponse.setTitle(layoutPageTemplateDisplayContext.getLayoutPageTemplateCollectionTitle());
%>

<liferay-frontend:management-bar
	disabled="<%= layoutPageTemplateDisplayContext.isDisabledLayoutPageTemplateEntriesManagementBar() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="layoutPageTemplateEntries"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= currentURLObj %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= layoutPageTemplateDisplayContext.getOrderByCol() %>"
			orderByType="<%= layoutPageTemplateDisplayContext.getOrderByType() %>"
			orderColumns="<%= layoutPageTemplateDisplayContext.getOrderColumns() %>"
			portletURL="<%= currentURLObj %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<aui:form cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="layoutPageTemplateEntries"
		searchContainer="<%= layoutPageTemplateDisplayContext.getLayoutPageTemplateEntriesSearchContainer() %>"
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
				<liferay-frontend:icon-vertical-card
					cssClass="entry-display-style"
					icon="page"
					resultRow="<%= row %>"
					rowChecker="<%= searchContainer.getRowChecker() %>"
					title="<%= layoutPageTemplateEntry.getName() %>"
				>
					<liferay-frontend:vertical-card-header>
						<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - layoutPageTemplateEntry.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
					</liferay-frontend:vertical-card-header>
				</liferay-frontend:icon-vertical-card>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= layoutPageTemplateDisplayContext.getDisplayStyle() %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>