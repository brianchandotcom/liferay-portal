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
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "vocabularies"), null);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= assetCategoriesDisplayContext.getAssetVocabulariesNavigationItems() %>"
/>

<clay:management-toolbar
	actionDropdownItems="<%= assetCategoriesDisplayContext.getVocabulariesActionItemsDropdownItems() %>"
	clearResultsURL="<%= assetCategoriesDisplayContext.getVocabulariesClearResultsURL() %>"
	componentId="assetVocabulariesManagementToolbar"
	creationMenu="<%= assetCategoriesDisplayContext.isShowVocabulariesAddButton() ? assetCategoriesDisplayContext.getVocabulariesCreationMenu() : null %>"
	disabled="<%= assetCategoriesDisplayContext.isDisabledVocabulariesManagementBar() %>"
	filterDropdownItems="<%= assetCategoriesDisplayContext.getVocabulariesFilterItemsDropdownItems() %>"
	itemsTotal="<%= assetCategoriesDisplayContext.getVocabulariesTotalItems() %>"
	searchActionURL="<%= assetCategoriesDisplayContext.getVocabulariesSearchActionURL() %>"
	searchContainerId="assetVocabularies"
	searchFormName="searchFm"
	sortingOrder="<%= assetCategoriesDisplayContext.getOrderByType() %>"
	sortingURL="<%= assetCategoriesDisplayContext.getVocabulariesSortingURL() %>"
	viewTypeItems="<%= assetCategoriesDisplayContext.getVocabulariesViewTypeItems() %>"
/>

<portlet:actionURL name="deleteVocabulary" var="deleteVocabularyURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteVocabularyURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showGuestGroup="<%= false %>"
		showLayout="<%= false %>"
		showParentGroups="<%= false %>"
	/>

	<liferay-ui:search-container
		id="assetVocabularies"
		searchContainer="<%= assetCategoriesDisplayContext.getVocabulariesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.kernel.model.AssetVocabulary"
			keyProperty="vocabularyId"
			modelVar="vocabulary"
		>
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
						<h6 class="text-default">
							<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - vocabulary.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
						</h6>

						<h5>
							<aui:a href="<%= (rowURL != null) ? rowURL.toString() : null %>"><%= HtmlUtil.escape(vocabulary.getTitle(locale)) %></aui:a>
						</h5>

						<h6 class="text-default">
							<%= HtmlUtil.escape(vocabulary.getDescription(locale)) %>
						</h6>

						<h6 class="text-default">
							<strong><liferay-ui:message key="number-of-categories" /></strong>:

							<c:choose>
								<c:when test="<%= assetCategoriesDisplayContext.isFlattenedNavigationAllowed() %>">
									<liferay-ui:message arguments="<%= vocabulary.getCategoriesCount() %>" key="flatten-x" translateArguments="<%= false %>" />
								</c:when>
								<c:otherwise>
									<%= vocabulary.getCategoriesCount() %>
								</c:otherwise>
							</c:choose>
						</h6>

						<h6 class="text-default">
							<strong><liferay-ui:message key="asset-type" /></strong>: <%= assetCategoriesDisplayContext.getAssetType(vocabulary) %>
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/vocabulary_action.jsp"
					/>
				</c:when>
				<c:when test='<%= Objects.equals(assetCategoriesDisplayContext.getDisplayStyle(), "icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							actionJsp="/vocabulary_action.jsp"
							actionJspServletContext="<%= application %>"
							icon="vocabulary"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							subtitle="<%= vocabulary.getDescription(locale) %>"
							title="<%= vocabulary.getName() %>"
							url="<%= rowURL != null ? rowURL.toString() : null %>"
						>
							<liferay-frontend:vertical-card-header>
								<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - vocabulary.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
							</liferay-frontend:vertical-card-header>

							<liferay-frontend:vertical-card-footer>
								<%= assetCategoriesDisplayContext.getAssetType(vocabulary) %>
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:icon-vertical-card>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(assetCategoriesDisplayContext.getDisplayStyle(), "list") %>'>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						href="<%= rowURL %>"
						name="name"
						value="<%= HtmlUtil.escape(vocabulary.getTitle(locale)) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="description"
						value="<%= HtmlUtil.escape(vocabulary.getDescription(locale)) %>"
					/>

					<liferay-ui:search-container-column-date
						name="create-date"
						property="createDate"
					/>

					<liferay-ui:search-container-column-text
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

<aui:script>
	window.<portlet:namespace />deleteSelectedVocabularies = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm(document.querySelector('#<portlet:namespace />fm'));
		}
	}
</aui:script>