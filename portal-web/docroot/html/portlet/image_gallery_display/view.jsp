<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/image_gallery_display/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "home");

Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long defaultFolderId = GetterUtil.getLong(preferences.getValue("rootFolderId", StringPool.BLANK), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", defaultFolderId);

if ((folder == null) && (defaultFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = DLAppLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

long repositoryId = scopeGroupId;

if (folder != null) {
	repositoryId = folder.getRepositoryId();
}

int status = WorkflowConstants.STATUS_APPROVED;

if (permissionChecker.isCompanyAdmin() || permissionChecker.isGroupAdmin(scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

long assetCategoryId = ParamUtil.getLong(request, "categoryId");
String assetTagName = ParamUtil.getString(request, "tag");

String assetCategoryTitle = null;
String assetVocabularyTitle = null;

if (assetCategoryId != 0) {
	AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getAssetCategory(assetCategoryId);

	assetCategory = assetCategory.toEscapedModel();

	assetCategoryTitle = assetCategory.getTitle(locale);

	AssetVocabulary assetVocabulary = AssetVocabularyLocalServiceUtil.getAssetVocabulary(assetCategory.getVocabularyId());

	assetVocabulary = assetVocabulary.toEscapedModel();

	assetVocabularyTitle = assetVocabulary.getTitle(locale);
}

boolean useAssetEntryQuery = (assetCategoryId > 0) || Validator.isNotNull(assetTagName);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/image_gallery_display/view");
portletURL.setParameter("topLink", topLink);
portletURL.setParameter("folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-defaultFolderId", String.valueOf(defaultFolderId));

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-repositoryId", String.valueOf(repositoryId));

request.setAttribute("view.jsp-viewFolder", Boolean.TRUE.toString());

request.setAttribute("view.jsp-useAssetEntryQuery", String.valueOf(useAssetEntryQuery));

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />

<c:choose>
	<c:when test="<%= useAssetEntryQuery %>">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(assetCategoryTitle) && Validator.isNotNull(assetTagName) %>">
				<h1 class="entry-title">
					<liferay-ui:message arguments="<%= new String[] {assetVocabularyTitle, assetCategoryTitle, assetTagName} %>" key="images-with-x-x-and-tag-x" />
				</h1>
			</c:when>
			<c:otherwise>
				<c:if test="<%= Validator.isNotNull(assetCategoryTitle) %>">
					<h1 class="entry-title">
						<liferay-ui:message arguments="<%= new String[] {assetVocabularyTitle, assetCategoryTitle} %>" key="images-with-x-x" />
					</h1>
				</c:if>

				<c:if test="<%= Validator.isNotNull(assetTagName) %>">
					<h1 class="entry-title">
						<liferay-ui:message arguments="<%= assetTagName %>" key="images-with-tag-x" />
					</h1>
				</c:if>
			</c:otherwise>
		</c:choose>

		<%
		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

		long[] classNameIds = {PortalUtil.getClassNameId(DLFileEntryConstants.getClassName()), PortalUtil.getClassNameId(DLFileShortcut.class.getName())};

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery(classNameIds, searchContainer);

		assetEntryQuery.setExcludeZeroViewCount(false);

		int total = AssetEntryServiceUtil.getEntriesCount(assetEntryQuery);

		searchContainer.setTotal(total);

		List results = AssetEntryServiceUtil.getEntries(assetEntryQuery);

		searchContainer.setResults(results);

		List scores = null;

		String[] mediaGalleryMimeTypes = null;
		%>

		<%@ include file="/html/portlet/image_gallery_display/view_images.jspf" %>

		<%
		if (portletName.equals(PortletKeys.MEDIA_GALLERY_DISPLAY)) {
			PortalUtil.addPageKeywords(assetTagName, request);
			PortalUtil.addPageKeywords(assetCategoryTitle, request);
		}
		%>

	</c:when>
	<c:when test='<%= topLink.equals("home") %>'>
		<aui:layout>
			<c:if test="<%= folder != null %>">
				<liferay-ui:header
					localizeTitle="<%= false %>"
					title="<%= folder.getName() %>"
				/>
			</c:if>

			<%
			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

			String[] mediaGalleryMimeTypes = DLUtil.getMediaGalleryMimeTypes(preferences, renderRequest);

			int foldersCount = DLAppServiceUtil.getFoldersCount(repositoryId, folderId, false);

			int total = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId, folderId, status, mediaGalleryMimeTypes, false);

			int imagesCount = total - foldersCount;

			searchContainer.setTotal(total);

			List results = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(repositoryId, folderId, status, mediaGalleryMimeTypes, false, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

			searchContainer.setResults(results);

			List scores = null;
			%>

			<aui:column columnWidth="<%= showFolderMenu ? 75 : 100 %>" cssClass="lfr-asset-column lfr-asset-column-details" first="<%= true %>">
				<div id="<portlet:namespace />imageGalleryAssetInfo">
					<c:if test="<%= folder != null %>">
						<div class="lfr-asset-description">
							<%= HtmlUtil.escape(folder.getDescription()) %>
						</div>

						<div class="lfr-asset-metadata">
							<div class="lfr-asset-icon lfr-asset-date">
								<%= LanguageUtil.format(pageContext, "last-updated-x", dateFormatDate.format(folder.getModifiedDate())) %>
							</div>

							<div class="lfr-asset-icon lfr-asset-subfolders">
								<%= foldersCount %> <liferay-ui:message key='<%= (foldersCount == 1) ? "subfolder" : "subfolders" %>' />
							</div>

							<div class="lfr-asset-icon lfr-asset-items last">
								<%= imagesCount %> <liferay-ui:message key='<%= (imagesCount == 1) ? "image" : "images" %>' />
							</div>
						</div>

						<liferay-ui:custom-attributes-available className="<%= DLFolderConstants.getClassName() %>">
							<liferay-ui:custom-attribute-list
								className="<%= DLFolderConstants.getClassName() %>"
								classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
								editable="<%= false %>"
								label="<%= true %>"
							/>
						</liferay-ui:custom-attributes-available>
					</c:if>

					<%@ include file="/html/portlet/image_gallery_display/view_images.jspf" %>

				</div>
			</aui:column>

			<c:if test="<%= showFolderMenu %>">
				<aui:column columnWidth="<%= 25 %>" cssClass="lfr-asset-column lfr-asset-column-actions" last="<%= true %>">
					<div class="lfr-asset-summary">
						<liferay-ui:icon
							cssClass="lfr-asset-avatar"
							image='<%= "../file_system/large/" + ((total > 0) ? "folder_full_image" : "folder_empty") %>'
							message='<%= (folder != null) ? folder.getName() : LanguageUtil.get(pageContext, "home") %>'
						/>

						<div class="lfr-asset-name">
							<h4><%= (folder != null) ? folder.getName() : LanguageUtil.get(pageContext, "home") %></h4>
						</div>
					</div>

					<%
					request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
					%>

					<liferay-util:include page="/html/portlet/document_library/folder_action.jsp" />
				</aui:column>
			</c:if>
		</aui:layout>

		<%
		if (folder != null) {
			IGUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

			if (portletName.equals(PortletKeys.MEDIA_GALLERY_DISPLAY)) {
				PortalUtil.setPageSubtitle(folder.getName(), request);
				PortalUtil.setPageDescription(folder.getDescription(), request);
			}
		}
		%>

	</c:when>
	<c:when test='<%= topLink.equals("mine") || topLink.equals("recent") %>'>

		<%
		long groupImagesUserId = 0;

		if (topLink.equals("mine") && themeDisplay.isSignedIn()) {
			groupImagesUserId = user.getUserId();
		}

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, null);

		String[] mediaGalleryMimeTypes = DLUtil.getMediaGalleryMimeTypes(preferences, renderRequest);

		int total = DLAppServiceUtil.getGroupFileEntriesCount(repositoryId, groupImagesUserId, defaultFolderId, mediaGalleryMimeTypes, status);

		searchContainer.setTotal(total);

		List results = DLAppServiceUtil.getGroupFileEntries(repositoryId, groupImagesUserId, defaultFolderId, mediaGalleryMimeTypes, status, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

		searchContainer.setResults(results);
		%>

		<aui:layout>
			<liferay-ui:header
				title="<%= topLink %>"
			/>

			<%
			List scores = null;
			%>

			<%@ include file="/html/portlet/image_gallery_display/view_images.jspf" %>
		</aui:layout>

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, topLink), currentURL);

		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, topLink), request);
		%>

	</c:when>
</c:choose>