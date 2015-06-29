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
DLItemSelectorViewDisplayContext dlItemSelectorViewDisplayContext = (DLItemSelectorViewDisplayContext)request.getAttribute(DLItemSelectorView.DL_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

ItemSelectorCriterion itemSelectorCriterion = dlItemSelectorViewDisplayContext.getItemSelectorCriterion();

PortletURL searchURL = PortletURLUtil.clone(dlItemSelectorViewDisplayContext.getPortletURL(), liferayPortletResponse);

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "curDocuments", SearchContainer.DEFAULT_DELTA, dlItemSelectorViewDisplayContext.getPortletURL(), null, LanguageUtil.get(request, "there-are-no-documents-or-media-files-in-this-folder"));

List results = null;
int total = 0;

String[] mimeTypes = dlItemSelectorViewDisplayContext.getMimeTypes();
long repositoryId = dlItemSelectorViewDisplayContext.getRepositoryId(request);
long folderId = dlItemSelectorViewDisplayContext.getFolderId(request);

String keywords = ParamUtil.getString(request, "keywords");

if (Validator.isNotNull(keywords)) {
	SearchContext searchContext = SearchContextFactory.getInstance(request);

	searchContext.setAttribute("mimeTypes", mimeTypes);

	searchContext.setEnd(searchContainer.getEnd());

	searchContext.setStart(searchContainer.getStart());

	Hits hits = DLAppServiceUtil.search(repositoryId, searchContext);

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
	total = DLAppServiceUtil.getFileEntriesCount(repositoryId, folderId, mimeTypes);
	results = DLAppServiceUtil.getFileEntries(repositoryId, folderId, mimeTypes, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
}

searchContainer.setTotal(total);
searchContainer.setResults(results);
%>

<item-selector-ui:browser
	desiredItemSelectorReturnTypes="<%= itemSelectorCriterion.getDesiredItemSelectorReturnTypes() %>"
	displayStyle="<%= dlItemSelectorViewDisplayContext.getDisplayStyle(request) %>"
	displayStyleURL="<%= dlItemSelectorViewDisplayContext.getPortletURL() %>"
	itemSelectedEventName="<%= dlItemSelectorViewDisplayContext.getItemSelectedEventName() %>"
	searchContainer="<%= searchContainer %>"
	searchURL="<%= searchURL %>"
	tabName="<%= dlItemSelectorViewDisplayContext.getTitle(locale) %>"
/>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_document_library_item_selector_web.documents_jsp");
%>