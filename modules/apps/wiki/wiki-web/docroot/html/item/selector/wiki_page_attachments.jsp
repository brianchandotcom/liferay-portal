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

PortletURL searchURL = PortletURLUtil.clone(wikiAttachmentItemSelectorViewDisplayContext.getPortletURL(), liferayPortletResponse);

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "curPageAttachments", SearchContainer.DEFAULT_DELTA, wikiAttachmentItemSelectorViewDisplayContext.getPortletURL(), null, LanguageUtil.get(request, "there-are-no-wiki-attachments"));

WikiPage wikiPage = wikiAttachmentItemSelectorViewDisplayContext.getWikiPage();

int total = 0;
List<FileEntry> results = new ArrayList<FileEntry>();

String keywords = ParamUtil.getString(request, "keywords");

if (Validator.isNotNull(keywords)) {
	SearchContext searchContext = SearchContextFactory.getInstance(request);

	searchContext.setEnd(searchContainer.getEnd());

	Folder folder = DLAppServiceUtil.getFolder(wikiPage.getAttachmentsFolderId());

	searchContext.setFolderIds(new long[] {folder.getFolderId()});

	searchContext.setStart(searchContainer.getStart());

	Hits hits = PortletFileRepositoryUtil.searchPortletFileEntries(folder.getRepositoryId(), searchContext);

	total = hits.getLength();

	Document[] docs = hits.getDocs();

	results = new ArrayList(docs.length);

	for (Document doc : docs) {
		long fileEntryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

		FileEntry fileEntry = null;

		try {
			fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Wiki attachments search index is stale and contains file entry {" + fileEntryId + "}");
			}

			continue;
		}

		results.add(fileEntry);
	}
}
else {
	total = wikiPage.getAttachmentsFileEntriesCount();
	results = wikiPage.getAttachmentsFileEntries(searchContainer.getStart(), searchContainer.getEnd());
}

searchContainer.setTotal(total);
searchContainer.setResults(results);
%>

<item-selector-ui:browser
	desiredItemSelectorReturnTypes="<%= wikiAttachmentItemSelectorCriterion.getDesiredItemSelectorReturnTypes() %>"
	displayStyle="<%= wikiAttachmentItemSelectorViewDisplayContext.getDisplayStyle(request) %>"
	displayStyleURL="<%= wikiAttachmentItemSelectorViewDisplayContext.getPortletURL() %>"
	itemSelectedEventName="<%= wikiAttachmentItemSelectorViewDisplayContext.getItemSelectedEventName() %>"
	searchContainer="<%= searchContainer %>"
	searchURL="<%= searchURL %>"
	tabName="<%= wikiAttachmentItemSelectorViewDisplayContext.getTitle(locale) %>"
/>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_wiki_web.wiki_page_attachments_jsp");
%>