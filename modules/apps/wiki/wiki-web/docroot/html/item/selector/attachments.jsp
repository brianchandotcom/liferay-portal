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

<%@ include file="/html/item/selector/init.jsp" %>

<%
WikiAttachmentItemSelectorViewDisplayContext wikiAttachmentItemSelectorViewDisplayContext = (WikiAttachmentItemSelectorViewDisplayContext)request.getAttribute(WikiAttachmentItemSelectorView.WIKI_ATTACHMENT_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

WikiAttachmentItemSelectorCriterion wikiAttachmentItemSelectorCriterion = wikiAttachmentItemSelectorViewDisplayContext.getWikiAttachmentItemSelectorCriterion();

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "curPageAttachments", SearchContainer.DEFAULT_DELTA, wikiAttachmentItemSelectorViewDisplayContext.getPortletURL(), null, null);

WikiPage wikiPage = wikiAttachmentItemSelectorViewDisplayContext.getWikiPage();

searchContainer.setTotal(wikiPage.getAttachmentsFileEntriesCount());
searchContainer.setResults(wikiPage.getAttachmentsFileEntries(searchContainer.getStart(), searchContainer.getEnd()));
%>

<item-selector-ui:browser
	displayStyle="<%= wikiAttachmentItemSelectorViewDisplayContext.getDisplayStyle(request) %>"
	displayStyleURL="<%= wikiAttachmentItemSelectorViewDisplayContext.getPortletURL() %>"
	itemSelectedEventName="<%= wikiAttachmentItemSelectorViewDisplayContext.getItemSelectedEventName() %>"
	itemSelectorReturnType="<%= ItemSelectorBrowserReturnTypeUtil.getFirstAvailableItemSelectorReturnType(wikiAttachmentItemSelectorCriterion.getDesiredItemSelectorReturnTypes()) %>"
	searchContainer="<%= searchContainer %>"
	tabName="<%= wikiAttachmentItemSelectorViewDisplayContext.getTitle(locale) %>"
/>