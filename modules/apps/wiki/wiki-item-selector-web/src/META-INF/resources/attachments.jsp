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
String displayStyle = ParamUtil.getString(request, "displayStyle");

PortletURL portletURL = (PortletURL)request.getAttribute(WikiAttachmentItemSelectorView.PORTLET_URL);

SearchContainer wikiAttachmentsSearchContainer = new SearchContainer(renderRequest, null, null, "curPageAttachments", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

WikiAttachmentItemSelectorCriterion wikiAttachmentItemSelectorCriterion = (WikiAttachmentItemSelectorCriterion)request.getAttribute(WikiAttachmentItemSelectorView.WIKI_ATTACHMENT_ITEM_SELECTOR_CRITERION);

WikiPageResource pageResource = WikiPageResourceLocalServiceUtil.fetchWikiPageResource(wikiAttachmentItemSelectorCriterion.getWikiPageResourceId());

WikiPage wikiPage = WikiPageLocalServiceUtil.getPage(pageResource.getNodeId(), pageResource.getTitle());

wikiAttachmentsSearchContainer.setTotal(wikiPage.getAttachmentsFileEntriesCount());
wikiAttachmentsSearchContainer.setResults(wikiPage.getAttachmentsFileEntries(wikiAttachmentsSearchContainer.getStart(), wikiAttachmentsSearchContainer.getEnd()));
%>

<item-selector-ui:browser
	displayStyle="<%= displayStyle %>"
	idPrefix="wikiAttachments"
	returnType="<%= ReturnType.parseFirst(wikiAttachmentItemSelectorCriterion.getDesiredReturnTypes()) %>"
	searchContainer="<%= wikiAttachmentsSearchContainer %>"
	tabName="attachments"
/>