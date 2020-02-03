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
WikiAttachmentItemSelectorViewDisplayContext wikiAttachmentItemSelectorViewDisplayContext = (WikiAttachmentItemSelectorViewDisplayContext)request.getAttribute(WikiItemSelectorWebKeys.WIKI_ATTACHMENT_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_CUR);
int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM, SearchContainer.DEFAULT_DELTA);

int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(cur, delta);

int start = startAndEnd[0];
int end = startAndEnd[1];

WikiPage wikiPage = wikiAttachmentItemSelectorViewDisplayContext.getWikiPage();

List portletFileEntries = null;
int portletFileEntriesCount = 0;

String[] mimeTypes = wikiAttachmentItemSelectorViewDisplayContext.getMimeTypes();

if (wikiPage.getAttachmentsFolderId() != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	if (wikiAttachmentItemSelectorViewDisplayContext.isSearch()) {
		SearchContext searchContext = SearchContextFactory.getInstance(request);

		if (ArrayUtil.isNotEmpty(mimeTypes)) {
			searchContext.setAttribute("mimeTypes", mimeTypes);
		}

		searchContext.setEnd(end);
		searchContext.setFolderIds(new long[] {wikiPage.getAttachmentsFolderId()});
		searchContext.setStart(start);

		Folder folder = PortletFileRepositoryUtil.getPortletFolder(wikiPage.getAttachmentsFolderId());

		Hits hits = PortletFileRepositoryUtil.searchPortletFileEntries(folder.getRepositoryId(), searchContext);

		portletFileEntriesCount = hits.getLength();

		Document[] docs = hits.getDocs();

		portletFileEntries = new ArrayList(docs.length);

		for (Document doc : docs) {
			long fileEntryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			FileEntry fileEntry = null;

			try {
				fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(fileEntryId);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Documents and Media search index is stale and contains file entry {" + fileEntryId + "}");
				}

				continue;
			}

			portletFileEntries.add(fileEntry);
		}
	}
	else {
		if (ArrayUtil.isNotEmpty(mimeTypes)) {
			portletFileEntries = wikiPage.getAttachmentsFileEntries(mimeTypes, start, end, wikiAttachmentItemSelectorViewDisplayContext.getOrderByComparator());
			portletFileEntriesCount = wikiPage.getAttachmentsFileEntriesCount(mimeTypes);
		}
		else {
			portletFileEntries = wikiPage.getAttachmentsFileEntries(start, end, wikiAttachmentItemSelectorViewDisplayContext.getOrderByComparator());
			portletFileEntriesCount = wikiPage.getAttachmentsFileEntriesCount();
		}
	}
}
%>

<liferay-item-selector:repository-entry-browser
	emptyResultsMessage='<%= LanguageUtil.get(resourceBundle, "there-are-no-wiki-attachments") %>'
	extensions="<%= ListUtil.toList(mimeTypes) %>"
	itemSelectedEventName="<%= wikiAttachmentItemSelectorViewDisplayContext.getItemSelectedEventName() %>"
	itemSelectorReturnTypeResolver="<%= wikiAttachmentItemSelectorViewDisplayContext.getItemSelectorReturnTypeResolver() %>"
	maxFileSize="<%= wikiAttachmentItemSelectorViewDisplayContext.getWikiAttachmentMaxSize() %>"
	portletURL="<%= wikiAttachmentItemSelectorViewDisplayContext.getPortletURL(request, liferayPortletResponse) %>"
	repositoryEntries="<%= portletFileEntries %>"
	repositoryEntriesCount="<%= portletFileEntriesCount %>"
	showDragAndDropZone="<%= false %>"
	tabName="<%= wikiAttachmentItemSelectorViewDisplayContext.getTitle(locale) %>"
	uploadURL="<%= wikiAttachmentItemSelectorViewDisplayContext.getUploadURL(liferayPortletResponse) %>"
/>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_wiki_web.item.selector.wiki_page_attachments_jsp");
%>