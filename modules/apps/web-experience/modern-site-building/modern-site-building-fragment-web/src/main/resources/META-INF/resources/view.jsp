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
renderResponse.setTitle(LanguageUtil.get(request, "fragment-collections"));
%>

<liferay-frontend:management-bar
	disabled="<%= msbFragmentDisplayContext.isDisabledMSBFragmentCollectionsManagementBar() %>"
	includeCheckBox="true"
	searchContainerId="msbFragmentCollections"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon"} %>'
			portletURL="<%= currentURLObj %>"
			selectedDisplayStyle="<%= msbFragmentDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form action="<%= currentURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="msbFragmentCollections"
		searchContainer="<%= msbFragmentDisplayContext.getMSBFragmentCollectionsSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.modern.site.building.fragment.model.MSBFragmentCollection"
			keyProperty="msbFragmentCollectionId"
			modelVar="msbFragmentCollection"
		>

			<%
			row.setCssClass("entry-card lfr-asset-folder");
			%>

			<liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text colspan="<%= 2 %>">
					<liferay-frontend:horizontal-card
						resultRow="<%= row %>"
						rowChecker="<%= searchContainer.getRowChecker() %>"
						text="<%= HtmlUtil.escape(msbFragmentCollection.getName()) %>"
					>
						<liferay-frontend:horizontal-card-col>
							<liferay-frontend:horizontal-card-icon
								icon="documents-and-media"
							/>
						</liferay-frontend:horizontal-card-col>
					</liferay-frontend:horizontal-card>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= msbFragmentDisplayContext.getDisplayStyle() %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>