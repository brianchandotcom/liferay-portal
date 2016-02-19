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

<%@ include file="/image_gallery_display/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long repositoryId = ParamUtil.getLong(request, "repositoryId");

long breadcrumbsFolderId = ParamUtil.getLong(request, "breadcrumbsFolderId");

long searchFolderId = ParamUtil.getLong(request, "searchFolderId");

String keywords = ParamUtil.getString(request, "keywords");
%>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="mvcPath" value="/image_gallery_display/search.jsp" />
</liferay-portlet:renderURL>

<aui:form action="<%= searchURL %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
	<aui:input name="breadcrumbsFolderId" type="hidden" value="<%= breadcrumbsFolderId %>" />
	<aui:input name="searchFolderId" type="hidden" value="<%= searchFolderId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title="search"
	/>

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("mvcPath", "/image_gallery_display/search.jsp");
	portletURL.setParameter("redirect", redirect);
	portletURL.setParameter("breadcrumbsFolderId", String.valueOf(breadcrumbsFolderId));
	portletURL.setParameter("searchFolderId", String.valueOf(searchFolderId));
	portletURL.setParameter("keywords", keywords);

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, LanguageUtil.format(request, "no-entries-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>", false));

	try {
		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setAttribute("mimeTypes", dlPortletInstanceSettings.getMimeTypes());
		searchContext.setAttribute("paginationType", "more");
		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setFolderIds(new long[] {searchFolderId});
		searchContext.setKeywords(keywords);
		searchContext.setStart(searchContainer.getStart());

		Hits hits = DLAppServiceUtil.search(repositoryId, searchContext);

		searchContainer.setTotal(hits.getLength());

		List results = new ArrayList(hits.getDocs().length);

		for (int i = 0; i < hits.getDocs().length; i++) {
			Document doc = hits.doc(i);

			String entryClassName = GetterUtil.getString(doc.get(Field.ENTRY_CLASS_NAME));

			long entryClassPK = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			if (entryClassName.equals(DLFileEntry.class.getName()) || FileEntry.class.isAssignableFrom(Class.forName(entryClassName))) {
				results.add(DLAppLocalServiceUtil.getFileEntry(entryClassPK));
			}
			else if (entryClassName.equals(DLFolder.class.getName())) {
				results.add(DLAppLocalServiceUtil.getFolder(entryClassPK));
			}
		}

		searchContainer.setResults(results);
	%>

	<div id="<portlet:namespace />imageGalleryAssetInfo">
			<div class="form-search">
				<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" placeholder='<%= LanguageUtil.get(request, "keywords") %>' title='<%= LanguageUtil.get(request, "search-images") %>' />
			</div>

		<br /><br />

		<%
		Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

		long folderId = BeanParamUtil.getLong(folder, request, "folderId", rootFolderId);

		request.setAttribute("view.jsp-folderId", String.valueOf(folderId));
		request.setAttribute("view.jsp-mediaGalleryMimeTypes", dlPortletInstanceSettings.getMimeTypes());
		request.setAttribute("view.jsp-searchContainer", searchContainer);
		%>

		<liferay-util:include page="/image_gallery_display/view_images.jsp" servletContext="<%= application %>" />
	</div>

	<%
	}
	catch (Exception e) {
		_log.error(e.getMessage());
	}
	%>

</aui:form>

<%
if (searchFolderId > 0) {
	IGUtil.addPortletBreadcrumbEntries(searchFolderId, request, renderResponse);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "search") + ": " + keywords, currentURL);
%>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_document_library_web.image_gallery_display.search_jsp");
%>