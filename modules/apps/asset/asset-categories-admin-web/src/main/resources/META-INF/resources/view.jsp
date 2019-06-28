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
AssetVocabulariesManagementToolbarDisplayContext assetVocabulariesManagementToolbarDisplayContext = new AssetVocabulariesManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, assetCategoriesDisplayContext);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= assetCategoriesDisplayContext.getAssetVocabulariesNavigationItems() %>"
/>

<clay:management-toolbar
	displayContext="<%= assetVocabulariesManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="deleteVocabulary" var="deleteVocabularyURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteVocabularyURL %>" cssClass="container-fluid container-fluid-max-xl" name="fm">
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= AssetCategoryUtil.getAssetVocabulariesBreadcrumbEntries(request) %>"
	/>

	<liferay-ui:error exception="<%= InvalidAssetCategoryException.class %>">

		<%
		InvalidAssetCategoryException iace = (InvalidAssetCategoryException)errorException;
		%>

		<liferay-ui:message arguments="<%= iace.getMessageArgument(locale) %>" key="<%= iace.getMessageKey() %>" />
	</liferay-ui:error>

	<liferay-ui:search-container
		id="assetVocabularies"
		searchContainer="<%= assetCategoriesDisplayContext.getVocabulariesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.kernel.model.AssetVocabulary"
			keyProperty="vocabularyId"
			modelVar="vocabulary"
		>

			<%
			Map<String, Object> rowData = new HashMap<>();

			rowData.put("actions", assetVocabulariesManagementToolbarDisplayContext.getAvailableActions(vocabulary));

			row.setData(rowData);
			%>

			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcPath" value="/view_categories.jsp" />
				<portlet:param name="vocabularyId" value="<%= String.valueOf(vocabulary.getVocabularyId()) %>" />
			</portlet:renderURL>

			<c:choose>
				<c:when test='<%= Objects.equals(assetCategoriesDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-icon
						icon="vocabulary"
						toggleRowChecker="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<span class="text-default">
							<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - vocabulary.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
						</span>

						<h2 class="h5">
							<aui:a href="<%= (rowURL != null) ? rowURL.toString() : null %>"><%= HtmlUtil.escape(vocabulary.getTitle(locale)) %></aui:a>
						</h2>

						<span class="text-default">
							<%= HtmlUtil.escape(vocabulary.getDescription(locale)) %>
						</span>
						<span class="text-default">
							<strong><liferay-ui:message key="number-of-categories" /></strong>:

							<c:choose>
								<c:when test="<%= assetCategoriesDisplayContext.isFlattenedNavigationAllowed() %>">
									<liferay-ui:message arguments="<%= vocabulary.getCategoriesCount() %>" key="flatten-x" translateArguments="<%= false %>" />
								</c:when>
								<c:otherwise>
									<%= vocabulary.getCategoriesCount() %>
								</c:otherwise>
							</c:choose>
						</span>
						<span class="text-default">
							<strong><liferay-ui:message key="asset-type" /></strong>: <%= assetCategoriesDisplayContext.getAssetType(vocabulary) %>
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/vocabulary_action.jsp"
					/>
				</c:when>
				<c:when test='<%= Objects.equals(assetCategoriesDisplayContext.getDisplayStyle(), "list") %>'>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-200 table-title"
						href="<%= rowURL %>"
						name="name"
						value="<%= HtmlUtil.escape(vocabulary.getTitle(locale)) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-cell-minw-200"
						name="description"
						value="<%= HtmlUtil.escape(vocabulary.getDescription(locale)) %>"
					/>

					<liferay-ui:search-container-column-date
						cssClass="table-cell-ws-nowrap"
						name="create-date"
						property="createDate"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-column-text-center"
						name="number-of-categories"
					>
						<c:choose>
							<c:when test="<%= assetCategoriesDisplayContext.isFlattenedNavigationAllowed() %>">
								<liferay-ui:message arguments="<%= vocabulary.getCategoriesCount() %>" key="flatten-x" translateArguments="<%= false %>" />
							</c:when>
							<c:otherwise>
								<%= vocabulary.getCategoriesCount() %>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smallest table-cell-minw-150"
						name="asset-type"
						value="<%= assetCategoriesDisplayContext.getAssetType(vocabulary) %>"
					/>

					<liferay-ui:search-container-column-jsp
						path="/vocabulary_action.jsp"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= assetCategoriesDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<liferay-frontend:component
	componentId="<%= assetVocabulariesManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/AssetVocabulariesManagementToolbarDefaultEventHandler.es"
/>