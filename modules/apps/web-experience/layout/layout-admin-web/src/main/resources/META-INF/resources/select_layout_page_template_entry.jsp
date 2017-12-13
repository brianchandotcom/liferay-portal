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
long layoutPageTemplateCollectionId = ParamUtil.getLong(request, "layoutPageTemplateCollectionId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(String.valueOf(layoutsAdminDisplayContext.getPortletURL()));

renderResponse.setTitle("select-template");
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		List<LayoutPageTemplateCollection> layoutPageTemplateCollections = LayoutPageTemplateCollectionServiceUtil.getLayoutPageTemplateCollections(themeDisplay.getScopeGroupId());

		for (LayoutPageTemplateCollection layoutPageTemplateCollection : layoutPageTemplateCollections) {
			String selectLayoutPageTemplateEntryURL = layoutsAdminDisplayContext.getSelectLayoutPageTemplateEntryURL(layoutPageTemplateCollection.getLayoutPageTemplateCollectionId());
		%>

			<aui:nav-item href="<%= selectLayoutPageTemplateEntryURL %>" label="<%= layoutPageTemplateCollection.getName() %>" selected="<%= Objects.equals(layoutPageTemplateCollectionId, layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()) %>" />

		<%
		}
		%>

	</aui:nav>
</aui:nav-bar>

<aui:form cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="layoutPageTemplateEntries"
		total="<%= LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntriesCount(themeDisplay.getScopeGroupId(), layoutPageTemplateCollectionId) %>"
	>
		<liferay-ui:search-container-results
			results="<%= LayoutPageTemplateEntryLocalServiceUtil.getLayoutPageTemplateEntries(themeDisplay.getScopeGroupId(), layoutPageTemplateCollectionId, searchContainer.getStart(), searchContainer.getEnd(), null) %>"
		/>

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
					actionJspServletContext="<%= application %>"
					cssClass='<%= renderResponse.getNamespace() + "add-layout-action-option" %>'
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

		<liferay-ui:search-iterator displayStyle="icon" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>