<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/image_gallery/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "images-home");

IGFolder folder = (IGFolder)request.getAttribute(WebKeys.IMAGE_GALLERY_FOLDER);

long defaultFolderId = GetterUtil.getLong(preferences.getValue("rootFolderId", StringPool.BLANK), IGFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", defaultFolderId);

if ((folder == null) && (defaultFolderId != IGFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = IGFolderLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = IGFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

int foldersCount = IGFolderServiceUtil.getFoldersCount(scopeGroupId, folderId);
int imagesCount = IGImageServiceUtil.getImagesCount(scopeGroupId, folderId);

long categoryId = ParamUtil.getLong(request, "categoryId");
String tagName = ParamUtil.getString(request, "tag");

String categoryName = null;
String vocabularyName = null;

if (categoryId != 0) {
	AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getAssetCategory(categoryId);

	assetCategory = assetCategory.toEscapedModel();

	categoryName = assetCategory.getName();

	AssetVocabulary assetVocabulary = AssetVocabularyLocalServiceUtil.getAssetVocabulary(assetCategory.getVocabularyId());

	assetVocabulary = assetVocabulary.toEscapedModel();

	vocabularyName = assetVocabulary.getName();
}

boolean useAssetEntryQuery = (categoryId > 0) || Validator.isNotNull(tagName);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/image_gallery/view");
portletURL.setParameter("topLink", topLink);
portletURL.setParameter("folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-portletURL", portletURL);

request.setAttribute("view.jsp-viewFolder", Boolean.TRUE.toString());

request.setAttribute("view.jsp-useAssetEntryQuery", String.valueOf(useAssetEntryQuery));
%>

<liferay-util:include page="/html/portlet/image_gallery/top_links.jsp" />

<c:choose>
	<c:when test="<%= useAssetEntryQuery %>">
		<c:if test="<%= Validator.isNotNull(categoryName) %>">
			<h1 class="entry-title">
				<%= LanguageUtil.format(pageContext, "images-with-x-x", new String[] {vocabularyName, categoryName}) %>
			</h1>
		</c:if>

		<c:if test="<%= Validator.isNotNull(tagName) %>">
			<h1 class="entry-title">
				<%= LanguageUtil.format(pageContext, "images-with-tag-x", HtmlUtil.escape(tagName)) %>
			</h1>
		</c:if>

		<%
		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery(IGImage.class.getName(), searchContainer);

		assetEntryQuery.setExcludeZeroViewCount(false);

		int total = AssetEntryServiceUtil.getEntriesCount(assetEntryQuery);

		searchContainer.setTotal(total);

		List results = AssetEntryServiceUtil.getEntries(assetEntryQuery);

		searchContainer.setResults(results);

		List scores = null;
		%>

		<%@ include file="/html/portlet/image_gallery/view_images.jspf" %>

		<%
		if (portletName.equals(PortletKeys.IMAGE_GALLERY)) {
			PortalUtil.addPageKeywords(tagName, request);
			PortalUtil.addPageKeywords(categoryName, request);
		}
		%>

	</c:when>
	<c:when test='<%= topLink.equals("images-home") %>'>
		<aui:layout>
			<c:if test="<%= folder != null %>">
				<liferay-ui:header
					localizeTitle="<%= false %>"
					title="<%= folder.getName() %>"
				/>
			</c:if>

			<aui:column columnWidth="<%= 75 %>" cssClass="lfr-asset-column lfr-asset-column-details" first="<%= true %>">
				<liferay-ui:panel-container extended="<%= false %>" id="imageGalleryAssetInfoPanelContainer" persistState="<%= true %>">
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

						<liferay-ui:custom-attributes-available className="<%= IGFolder.class.getName() %>">
							<liferay-ui:custom-attribute-list
								className="<%= IGFolder.class.getName() %>"
								classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
								editable="<%= false %>"
								label="<%= true %>"
							/>
						</liferay-ui:custom-attributes-available>
					</c:if>

					<c:if test="<%= foldersCount > 0 %>">
						<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="imageGallerySubFoldersPanel" persistState="<%= true %>" title='<%= (folder != null) ? "subfolders" : "folders" %>'>
							<liferay-util:include page="/html/portlet/image_gallery/view_folders.jsp" />
						</liferay-ui:panel>
					</c:if>

					<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="imageGalleryEntriesPanel" persistState="<%= true %>" title="images">

						<%
						SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

						int total = IGImageServiceUtil.getImagesCount(scopeGroupId, folderId);

						searchContainer.setTotal(total);

						List results = IGImageServiceUtil.getImages(scopeGroupId, folderId, searchContainer.getStart(), searchContainer.getEnd());

						searchContainer.setResults(results);

						List scores = null;
						%>

						<%@ include file="/html/portlet/image_gallery/view_images.jspf" %>

					</liferay-ui:panel>
				</liferay-ui:panel-container>
			</aui:column>

			<aui:column columnWidth="<%= 25 %>" cssClass="lfr-asset-column lfr-asset-column-actions" last="<%= true %>">
				<div class="lfr-asset-summary">
					<liferay-ui:icon
						cssClass="lfr-asset-avatar"
						image='<%= "../file_system/large/" + (((foldersCount + imagesCount) > 0) ? "folder_full_image" : "folder_empty") %>'
						message=""
					/>

					<div class="lfr-asset-name">
						<h4><%= (folder != null) ? folder.getName() : LanguageUtil.get(pageContext, "images-home") %></h4>
					</div>
				</div>

				<%
				request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
				%>

				<liferay-util:include page="/html/portlet/image_gallery/folder_action.jsp" />
			</aui:column>
		</aui:layout>

		<%
		if (folder != null) {
			IGUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

			if (portletName.equals(PortletKeys.IMAGE_GALLERY)) {
				PortalUtil.setPageSubtitle(folder.getName(), request);
				PortalUtil.setPageDescription(folder.getDescription(), request);
			}
		}
		%>

	</c:when>
	<c:when test='<%= topLink.equals("my-images") || topLink.equals("recent-images") %>'>

		<%
		long groupImagesUserId = 0;

		if (topLink.equals("my-images") && themeDisplay.isSignedIn()) {
			groupImagesUserId = user.getUserId();
		}

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, null);

		int total = IGImageServiceUtil.getGroupImagesCount(scopeGroupId, groupImagesUserId);

		searchContainer.setTotal(total);

		List results = IGImageServiceUtil.getGroupImages(scopeGroupId, groupImagesUserId, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);
		%>

		<aui:layout>
			<liferay-ui:header
				title="<%= topLink %>"
			/>

			<%
			List scores = null;
			%>

			<%@ include file="/html/portlet/image_gallery/view_images.jspf" %>
		</aui:layout>

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, topLink), currentURL);

		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, topLink), request);
		%>

	</c:when>
</c:choose>