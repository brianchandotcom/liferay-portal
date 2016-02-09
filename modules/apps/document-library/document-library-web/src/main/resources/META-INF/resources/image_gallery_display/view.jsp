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
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long defaultFolderId = dlPortletInstanceSettings.getDefaultFolderId();

long folderId = BeanParamUtil.getLong(folder, request, "folderId", defaultFolderId);

boolean defaultFolderView = false;

if ((folder == null) && (defaultFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	defaultFolderView = true;
}

if (defaultFolderView) {
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

if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

Map<String, Object> contextObjects = new HashMap<String, Object>();

contextObjects.put("dlPortletInstanceSettings", dlPortletInstanceSettings);

DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(igRequestHelper);

String[] mediaGalleryMimeTypes = dlPortletInstanceSettings.getMimeTypes();

List fileEntries = DLAppServiceUtil.getGroupFileEntries(scopeGroupId, 0, folderId, mediaGalleryMimeTypes, status, 0, SearchContainer.MAX_DELTA, null);
%>

<liferay-ddm:template-renderer className="<%= FileEntry.class.getName() %>" contextObjects="<%= contextObjects %>" displayStyle="<%= displayStyle %>" displayStyleGroupId="<%= displayStyleGroupId %>" entries="<%= fileEntries %>">

	<%
	String topLink = ParamUtil.getString(request, "topLink", "home");

	long assetCategoryId = ParamUtil.getLong(request, "categoryId");
	String assetTagName = ParamUtil.getString(request, "tag");

	boolean useAssetEntryQuery = (assetCategoryId > 0) || Validator.isNotNull(assetTagName);

	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("mvcRenderCommandName", "/image_gallery_display/view");
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

	<portlet:actionURL name="/document_library/edit_file_entry" var="restoreTrashEntriesURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
	</portlet:actionURL>

	<liferay-trash:undo
		portletURL="<%= restoreTrashEntriesURL %>"
	/>

	<c:choose>
		<c:when test="<%= useAssetEntryQuery %>">
			<liferay-ui:categorization-filter
				assetType="images"
				portletURL="<%= portletURL %>"
			/>

			<%
			SearchContainer igSearchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

			long[] classNameIds = {PortalUtil.getClassNameId(DLFileEntryConstants.getClassName()), PortalUtil.getClassNameId(DLFileShortcutConstants.getClassName())};

			AssetEntryQuery assetEntryQuery = new AssetEntryQuery(classNameIds, igSearchContainer);

			assetEntryQuery.setEnablePermissions(true);
			assetEntryQuery.setExcludeZeroViewCount(false);

			int total = AssetEntryServiceUtil.getEntriesCount(assetEntryQuery);

			igSearchContainer.setTotal(total);

			List results = AssetEntryServiceUtil.getEntries(assetEntryQuery);

			igSearchContainer.setResults(results);

			mediaGalleryMimeTypes = null;

			request.setAttribute("view.jsp-igSearchContainer", igSearchContainer);
			request.setAttribute("view.jsp-mediaGalleryMimeTypes", mediaGalleryMimeTypes);
			%>

			<liferay-util:include page="/image_gallery_display/view_images.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= topLink.equals("home") %>'>
			<c:if test="<%= folder != null %>">
				<aui:col width="<%= 100 %>">
					<liferay-ui:header
						localizeTitle="<%= false %>"
						title="<%= folder.getName() %>"
					/>
				</aui:col>
			</c:if>

			<%
			SearchContainer igSearchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

			int foldersCount = DLAppServiceUtil.getFoldersCount(repositoryId, folderId, true);

			int total = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId, folderId, status, mediaGalleryMimeTypes, true);

			int imagesCount = total - foldersCount;

			igSearchContainer.setTotal(total);

			List results = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(repositoryId, folderId, status, mediaGalleryMimeTypes, true, igSearchContainer.getStart(), igSearchContainer.getEnd(), igSearchContainer.getOrderByComparator());

			igSearchContainer.setResults(results);

			request.setAttribute("view.jsp-mediaGalleryMimeTypes", mediaGalleryMimeTypes);
			request.setAttribute("view.jsp-igSearchContainer", igSearchContainer);
			%>

			<div id="<portlet:namespace />imageGalleryAssetInfo">
				<c:if test="<%= folder != null %>">
					<div class="lfr-asset-description">
						<%= HtmlUtil.escape(folder.getDescription()) %>
					</div>

					<div class="lfr-asset-metadata">
						<div class="icon-calendar lfr-asset-icon">
							<liferay-ui:message arguments="<%= dateFormatDate.format(folder.getModifiedDate()) %>" key="last-updated-x" translateArguments="<%= false %>" />
						</div>

						<%
						AssetRendererFactory<?> dlFolderAssetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFolder.class.getName());
						%>

						<div class="lfr-asset-icon">
							<liferay-ui:icon icon="<%= dlFolderAssetRendererFactory.getIconCssClass() %>" markupView="lexicon" />

							<%= foldersCount %> <liferay-ui:message key='<%= (foldersCount == 1) ? "subfolder" : "subfolders" %>' />
						</div>

						<%
						AssetRendererFactory<?> dlFileEntryAssetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());
						%>

						<div class="last lfr-asset-icon">
							<liferay-ui:icon icon="<%= dlFileEntryAssetRendererFactory.getIconCssClass() %>" markupView="lexicon" />

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

				<liferay-util:include page="/image_gallery_display/view_images.jsp" servletContext="<%= application %>" />
			</div>

			<%
			if (folder != null) {
				IGUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

				if (!defaultFolderView && portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
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

			SearchContainer igSearchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, null);

			int total = DLAppServiceUtil.getGroupFileEntriesCount(repositoryId, groupImagesUserId, defaultFolderId, mediaGalleryMimeTypes, status);

			igSearchContainer.setTotal(total);

			List results = DLAppServiceUtil.getGroupFileEntries(repositoryId, groupImagesUserId, defaultFolderId, mediaGalleryMimeTypes, status, igSearchContainer.getStart(), igSearchContainer.getEnd(), igSearchContainer.getOrderByComparator());

			igSearchContainer.setResults(results);

			request.setAttribute("view.jsp-mediaGalleryMimeTypes", mediaGalleryMimeTypes);
			request.setAttribute("view.jsp-igSearchContainer", igSearchContainer);
			%>

			<aui:row>
				<liferay-ui:header
					title="<%= topLink %>"
				/>

				<liferay-util:include page="/image_gallery_display/view_images.jsp" servletContext="<%= application %>" />
			</aui:row>

			<%
			PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, topLink), currentURL);

			PortalUtil.setPageSubtitle(LanguageUtil.get(request, topLink), request);
			%>

		</c:when>
	</c:choose>
</liferay-ddm:template-renderer>