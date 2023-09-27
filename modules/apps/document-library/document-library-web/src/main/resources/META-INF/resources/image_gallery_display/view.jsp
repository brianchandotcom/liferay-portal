<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/image_gallery_display/init.jsp" %>

<%
IGRequestHelper igRequestHelper = new IGRequestHelper(request);

IGViewDisplayContext igViewDisplayContext = new IGViewDisplayContext(new IGRequestHelper(request), renderRequest, renderResponse);

Map<String, Object> contextObjects = HashMapBuilder.<String, Object>put(
	"dlPortletInstanceSettings", igRequestHelper.getDLPortletInstanceSettings()
).build();

Folder folder = igViewDisplayContext.getFolder();
%>

<liferay-ui:success key='<%= portletDisplay.getId() + "requestProcessed" %>' message="your-request-completed-successfully" />

<liferay-ddm:template-renderer
	className="<%= FileEntry.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle='<%= portletPreferences.getValue("displayStyle", StringPool.BLANK) %>'
	displayStyleGroupId='<%= GetterUtil.getLong(portletPreferences.getValue("displayStyleGroupId", null), themeDisplay.getScopeGroupId()) %>'
	entries="<%= DLAppServiceUtil.getGroupFileEntries(igViewDisplayContext.getRepositoryId(), 0, igViewDisplayContext.getFolderId(), igViewDisplayContext.getMediaGalleryMimeTypes(), igViewDisplayContext.getStatus(), 0, SearchContainer.MAX_DELTA, null) %>"
>

	<%
	request.setAttribute("view.jsp-rootFolderId", String.valueOf(igViewDisplayContext.getRootFolderId()));

	request.setAttribute("view.jsp-folderId", String.valueOf(igViewDisplayContext.getFolderId()));

	request.setAttribute("view.jsp-repositoryId", String.valueOf(igViewDisplayContext.getRepositoryId()));

	request.setAttribute("view.jsp-viewFolder", Boolean.TRUE.toString());

	request.setAttribute("view.jsp-useAssetEntryQuery", String.valueOf(igViewDisplayContext.isAssetEntryQuery()));

	request.setAttribute("view.jsp-portletURL", igViewDisplayContext.getPortletURL());
	%>

	<portlet:actionURL name="/document_library/edit_file_entry" var="restoreTrashEntriesURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
	</portlet:actionURL>

	<liferay-trash:undo
		portletURL="<%= restoreTrashEntriesURL %>"
	/>

	<c:choose>
		<c:when test="<%= igViewDisplayContext.isAssetEntryQuery() %>">
			<liferay-asset:categorization-filter
				assetType="images"
				portletURL="<%= igViewDisplayContext.getPortletURL() %>"
			/>

			<%
			request.setAttribute("view.jsp-igSearchContainer", igViewDisplayContext.getAssetEntrySearchContainer());
			request.setAttribute("view.jsp-mediaGalleryMimeTypes", null);
			%>

			<liferay-util:include page="/image_gallery_display/view_images.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test="<%= igViewDisplayContext.isTopLinkHome() %>">
			<c:if test="<%= folder != null %>">
				<liferay-ui:header
					localizeTitle="<%= false %>"
					title="<%= folder.getName() %>"
				/>
			</c:if>

			<%
			request.setAttribute("view.jsp-igSearchContainer", igViewDisplayContext.getHomeSearchContainer());
			request.setAttribute("view.jsp-mediaGalleryMimeTypes", igViewDisplayContext.getMediaGalleryMimeTypes());
			%>

			<div id="<portlet:namespace />imageGalleryAssetInfo">
				<c:if test="<%= folder != null %>">
					<div class="lfr-asset-description">
						<%= HtmlUtil.escape(folder.getDescription()) %>
					</div>

					<div class="lfr-asset-metadata">
						<div class="icon-calendar lfr-asset-icon">
							<liferay-ui:message arguments="<%= (folder.getModifiedDate() != null) ? dateFormatDate.format(folder.getModifiedDate()) : StringPool.BLANK %>" key="last-updated-x" translateArguments="<%= false %>" />
						</div>

						<%
						AssetRendererFactory<?> dlFolderAssetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFolder.class.getName());
						%>

						<div class="lfr-asset-icon">
							<liferay-ui:icon
								icon="<%= dlFolderAssetRendererFactory.getIconCssClass() %>"
								markupView="lexicon"
							/>

							<%= igViewDisplayContext.getFoldersCount() %> <liferay-ui:message key='<%= (igViewDisplayContext.getFoldersCount() == 1) ? "folder" : "folders" %>' />
						</div>

						<%
						AssetRendererFactory<?> dlFileEntryAssetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());
						%>

						<div class="last lfr-asset-icon">
							<liferay-ui:icon
								icon="<%= dlFileEntryAssetRendererFactory.getIconCssClass() %>"
								markupView="lexicon"
							/>

							<%= igViewDisplayContext.getImagesCount() %> <liferay-ui:message key='<%= (igViewDisplayContext.getImagesCount() == 1) ? "image" : "images" %>' />
						</div>
					</div>

					<liferay-expando:custom-attributes-available
						className="<%= DLFolderConstants.getClassName() %>"
					>
						<liferay-expando:custom-attribute-list
							className="<%= DLFolderConstants.getClassName() %>"
							classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
							editable="<%= false %>"
							label="<%= true %>"
						/>
					</liferay-expando:custom-attributes-available>
				</c:if>

				<liferay-util:include page="/image_gallery_display/view_images.jsp" servletContext="<%= application %>" />
			</div>

			<%
			if (folder != null) {
				IGUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

				if (!igViewDisplayContext.isDefaultFolderView() && portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
					PortalUtil.setPageSubtitle(folder.getName(), request);
					PortalUtil.setPageDescription(folder.getDescription(), request);
				}
			}
			%>

		</c:when>
		<c:when test="<%= igViewDisplayContext.isTopLinkMine() || igViewDisplayContext.isTopLinkRecent() %>">

			<%
			request.setAttribute("view.jsp-igSearchContainer", igViewDisplayContext.getRecentMineSearchContainer());
			request.setAttribute("view.jsp-mediaGalleryMimeTypes", igViewDisplayContext.getMediaGalleryMimeTypes());
			%>

			<clay:row>
				<liferay-ui:header
					title="<%= igViewDisplayContext.getTopLink() %>"
				/>

				<liferay-util:include page="/image_gallery_display/view_images.jsp" servletContext="<%= application %>" />
			</clay:row>

			<%
			PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, igViewDisplayContext.getTopLink()), currentURL);

			PortalUtil.setPageSubtitle(LanguageUtil.get(request, igViewDisplayContext.getTopLink()), request);
			%>

		</c:when>
	</c:choose>
</liferay-ddm:template-renderer>