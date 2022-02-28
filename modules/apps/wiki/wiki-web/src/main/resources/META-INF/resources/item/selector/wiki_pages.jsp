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

<%@ include file="/item/selector/init.jsp" %>

<%
WikiPageItemSelectorViewDisplayContext wikiPageItemSelectorViewDisplayContext = (WikiPageItemSelectorViewDisplayContext)request.getAttribute(WikiItemSelectorWebKeys.WIKI_PAGE_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

SearchContainer<WikiPage> wikiPagesSearchContainer = wikiPageItemSelectorViewDisplayContext.getSearchContainer(request, liferayPortletResponse, renderRequest);
%>

<style type="text/css">
	.portlet-item-selector .wiki-page-item {
		cursor: pointer;
	}
</style>

<%
String searchURL = HttpUtil.removeParameter(
	PortletURLBuilder.create(
		PortletURLUtil.clone(currentURLObj, liferayPortletResponse)
	).setParameter(
		"resetCur", true
	).buildString(),
	liferayPortletResponse.getNamespace() + "keywords");
%>

<clay:management-toolbar
	clearResultsURL="<%= searchURL %>"
	itemsTotal="<%= wikiPagesSearchContainer.getTotal() %>"
	searchActionURL="<%= searchURL %>"
	selectable="<%= false %>"
	showCreationMenu="<%= false %>"
/>

<clay:container-fluid
	cssClass="lfr-item-viewer"
	id='<%= liferayPortletResponse.getNamespace() + "wikiPagesSelectorContainer" %>'
>
	<liferay-ui:search-container
		id="wikiPagesSearchContainer"
		searchContainer="<%= wikiPagesSearchContainer %>"
		total="<%= wikiPagesSearchContainer.getTotal() %>"
	>
		<liferay-ui:search-container-results
			results="<%= wikiPagesSearchContainer.getResults() %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.wiki.model.WikiPage"
			cssClass="wiki-page-item"
			keyProperty="pageId"
			modelVar="curPage"
		>
			<liferay-ui:search-container-column-icon
				icon="wiki-page"
			/>

			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
			>

				<%
				Date modifiedDate = curPage.getModifiedDate();

				String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
				%>

				<h5 class="text-default">
					<c:choose>
						<c:when test="<%= Validator.isNotNull(curPage.getUserName()) %>">
							<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(curPage.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message arguments="<%= modifiedDateDescription %>" key="modified-x-ago" />
						</c:otherwise>
					</c:choose>
				</h5>

				<%
				WikiPageItemSelectorReturnTypeResolver wikiPageItemSelectorReturnTypeResolver = wikiPageItemSelectorViewDisplayContext.getWikiPageItemSelectorReturnTypeResolver();
				%>

				<h4>
					<a class="wiki-page" data-title="<%= wikiPageItemSelectorReturnTypeResolver.getTitle(curPage, themeDisplay) %>" data-value="<%= wikiPageItemSelectorReturnTypeResolver.getValue(curPage, themeDisplay) %>" href="javascript:;">
						<%= curPage.getTitle() %>
					</a>
				</h4>

				<h5 class="text-default">
					<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= curPage.getStatus() %>" />
				</h5>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
			searchContainer="<%= wikiPagesSearchContainer %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<aui:script use="liferay-search-container">
	var Util = Liferay.Util;

	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />wikiPagesSearchContainer'
	);

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		(event) => {
			var selectedItem = event.currentTarget;

			var linkItem = selectedItem.one('.wiki-page');

			Util.getOpener().Liferay.fire(
				'<%= wikiPageItemSelectorViewDisplayContext.getItemSelectedEventName() %>',
				{
					data: {
						title: linkItem.attr('data-title'),
						value: linkItem.attr('data-value'),
					},
				}
			);

			selectedItem.siblings().removeClass('active');
			selectedItem.addClass('active');
		},
		'.list-group-item'
	);
</aui:script>