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
BlogsItemSelectorViewDisplayContext blogsItemSelectorViewDisplayContext = (BlogsItemSelectorViewDisplayContext)request.getAttribute(BlogsItemSelectorView.BLOGS_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

BlogsItemSelectorCriterion blogsItemSelectorCriterion = blogsItemSelectorViewDisplayContext.getBlogsItemSelectorCriterion();

PortletURL searchURL = PortletURLUtil.clone(blogsItemSelectorViewDisplayContext.getPortletURL(), liferayPortletResponse);

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "curDocuments", SearchContainer.DEFAULT_DELTA, blogsItemSelectorViewDisplayContext.getPortletURL(), null, LanguageUtil.get(resourceBundle, "there-are-no-blog-attachments"));

Folder folder = blogsItemSelectorViewDisplayContext.fetchAttachmentsFolder(themeDisplay.getUserId(), scopeGroupId);

int total = 0;
List<FileEntry> results = new ArrayList<FileEntry>();

if (folder != null) {
	String keywords = ParamUtil.getString(request, "keywords");

	if (Validator.isNotNull(keywords)) {
		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setEnd(searchContainer.getEnd());
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
					_log.warn("Blogs attachments search index is stale and contains file entry {" + fileEntryId + "}");
				}

				continue;
			}

			results.add(fileEntry);
		}
	}
	else {
		total = PortletFileRepositoryUtil.getPortletFileEntriesCount(scopeGroupId, folder.getFolderId());
		results = PortletFileRepositoryUtil.getPortletFileEntries(scopeGroupId, folder.getFolderId());
	}
}

searchContainer.setTotal(total);
searchContainer.setResults(results);
%>

<item-selector-ui:browser
	desiredItemSelectorReturnTypes="<%= blogsItemSelectorCriterion.getDesiredItemSelectorReturnTypes() %>"
	displayStyle="<%= blogsItemSelectorViewDisplayContext.getDisplayStyle(request) %>"
	displayStyleURL="<%= blogsItemSelectorViewDisplayContext.getPortletURL() %>"
	itemSelectedEventName="<%= blogsItemSelectorViewDisplayContext.getItemSelectedEventName() %>"
	searchContainer="<%= searchContainer %>"
	searchURL="<%= searchURL %>"
	tabName="<%= blogsItemSelectorViewDisplayContext.getTitle(locale) %>"
/>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_blogs_item_selector_web.blogs_attachments_jsp");
%>