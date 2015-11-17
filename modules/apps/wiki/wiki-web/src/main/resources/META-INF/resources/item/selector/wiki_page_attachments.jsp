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
WikiAttachmentItemSelectorViewDisplayContext wikiAttachmentItemSelectorViewDisplayContext = (WikiAttachmentItemSelectorViewDisplayContext)request.getAttribute(WikiAttachmentItemSelectorView.WIKI_ATTACHMENT_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

WikiAttachmentItemSelectorCriterion wikiAttachmentItemSelectorCriterion = wikiAttachmentItemSelectorViewDisplayContext.getWikiAttachmentItemSelectorCriterion();

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "curPageAttachments", SearchContainer.DEFAULT_DELTA, wikiAttachmentItemSelectorViewDisplayContext.getPortletURL(request, liferayPortletResponse), null, LanguageUtil.get(resourceBundle, "there-are-no-wiki-attachments"));

String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

OrderByComparator<?> orderByComparator = DLUtil.getRepositoryModelOrderByComparator(orderByCol, orderByType);

searchContainer.setOrderByComparator(orderByComparator);

WikiPage wikiPage = wikiAttachmentItemSelectorViewDisplayContext.getWikiPage();

int total = 0;
List<FileEntry> results = new ArrayList<FileEntry>();

if (wikiPage.getAttachmentsFolderId() != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	if (wikiAttachmentItemSelectorViewDisplayContext.isSearch()) {
		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setFolderIds(new long[] {wikiPage.getAttachmentsFolderId()});
		searchContext.setStart(searchContainer.getStart());

		Folder folder = DLAppServiceUtil.getFolder(wikiPage.getAttachmentsFolderId());

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
					_log.warn("Documents and Media search index is stale and contains file entry {" + fileEntryId + "}");
				}

				continue;
			}

			results.add(fileEntry);
		}
	}
	else {
		total = wikiPage.getAttachmentsFileEntriesCount();
		results = wikiPage.getAttachmentsFileEntries(searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
	}
}

searchContainer.setTotal(total);
searchContainer.setResults(results);
%>

<liferay-item-selector:repository-entry-browser
	desiredItemSelectorReturnTypes="<%= wikiAttachmentItemSelectorCriterion.getDesiredItemSelectorReturnTypes() %>"
	itemSelectedEventName="<%= wikiAttachmentItemSelectorViewDisplayContext.getItemSelectedEventName() %>"
	portletURL="<%= wikiAttachmentItemSelectorViewDisplayContext.getPortletURL(request, liferayPortletResponse) %>"
	searchContainer="<%= searchContainer %>"
	tabName="<%= wikiAttachmentItemSelectorViewDisplayContext.getTitle(locale) %>"
	uploadURL="<%= wikiAttachmentItemSelectorViewDisplayContext.getUploadURL(liferayPortletResponse) %>"
/>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_wiki_web.item.selector.wiki_page_attachments_jsp");
%>