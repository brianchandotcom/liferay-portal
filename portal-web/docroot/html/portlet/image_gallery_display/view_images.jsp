<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
Long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));
String[] mediaGalleryMimeTypes = (String[])request.getAttribute("view.jsp-mediaGalleryMimeTypes");
List results = (List)request.getAttribute("view.jsp-results");
SearchContainer searchContainer = (SearchContainer)request.getAttribute("view.jsp-searchContainer");
%>

<c:choose>
	<c:when test="<%= results.isEmpty() %>">
		<div class="alert alert-info">
			<%= LanguageUtil.get(pageContext, "there-are-no-media-files-in-this-folder") %>
		</div>
	</c:when>
	<c:otherwise>
		<div class="taglib-search-iterator-page-iterator-top">
			<liferay-ui:search-paginator id="pageIteratorTop" searchContainer="<%= searchContainer %>" />
		</div>
	</c:otherwise>
</c:choose>

<div>

	<%
	for (int i = 0; i < results.size(); i++) {
		Object result = results.get(i);
	%>

		<%@ include file="/html/portlet/document_library/cast_result.jspf" %>

		<c:choose>
			<c:when test="<%= fileEntry != null %>">

				<%
				String thumbnailId = null;

				if (fileShortcut != null) {
					thumbnailId = "shortcut_" + fileShortcut.getFileShortcutId();
				}
				else {
					thumbnailId = "entry_" + fileEntry.getFileEntryId();
				}
				%>

				<c:if test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.VIEW) %>">

					<%
					FileVersion fileVersion = fileEntry.getFileVersion();

					boolean hasAudio = AudioProcessorUtil.hasAudio(fileVersion);
					boolean hasImages = ImageProcessorUtil.hasImages(fileVersion);
					boolean hasPDFImages = PDFProcessorUtil.hasImages(fileVersion);
					boolean hasVideo = VideoProcessorUtil.hasVideo(fileVersion);

					String href = themeDisplay.getPathThemeImages() + "/file_system/large/" + DLUtil.getGenericName(fileEntry.getExtension()) + ".png";
					String src = DLUtil.getThumbnailSrc(fileEntry, fileVersion, null, themeDisplay);

					int playerHeight = 500;

					String dataOptions = StringPool.BLANK;

					if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED) {
						if (hasAudio) {
							href = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, HtmlUtil.escapeURL("&audioPreview=1") + "&supportedAudio=1&mediaGallery=1");

							for (String audioContainer : PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS) {
								dataOptions += "&" + audioContainer + "PreviewURL=" + HtmlUtil.escapeURL(DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&supportedAudio=1&audioPreview=1&type=" + audioContainer));
							}

							playerHeight = 43;
						}
						else if (hasImages) {
							href = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&imagePreview=1");
						}
						else if (hasPDFImages) {
							href = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&previewFileIndex=1");
						}
						else if (hasVideo) {
							href = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&supportedVideo=1&mediaGallery=1");

							for (String videoContainer : PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS) {
								dataOptions += "&" + videoContainer + "PreviewURL=" + HtmlUtil.escapeURL(DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&supportedVideo=1&videoPreview=1&type=" + videoContainer));
							}
						}
					}
					%>

					<div class="image-icon">
						<a class="image-link preview" <%= (hasAudio || hasVideo) ? "data-options=\"height=" + playerHeight + "&thumbnailURL=" + HtmlUtil.escapeURL(DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&videoThumbnail=1")) + "&width=640" + dataOptions + "\"" : StringPool.BLANK %> href="<%= href %>" thumbnailId="<%= thumbnailId %>" title="<%= HtmlUtil.escape(fileEntry.getTitle()) + " - " + HtmlUtil.escape(fileEntry.getDescription()) %>">
							<span class="image-thumbnail" style="<%= DLUtil.getThumbnailStyle(false, 4) %>">
								<img alt="<%= HtmlUtil.escape(fileEntry.getTitle()) + " - " + HtmlUtil.escape(fileEntry.getDescription()) %>" border="no" src="<%= src %>" style="max-height: <%= PropsValues.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT %>px; max-width: <%= PropsValues.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH %>px;" />

								<c:if test="<%= fileShortcut != null %>">
									<img alt="<liferay-ui:message key="shortcut" />" class="shortcut-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_link.png" />
								</c:if>

								<c:if test="<%= fileEntry.isCheckedOut() %>">
									<img alt="<liferay-ui:message key="locked" />" class="locked-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_lock.png" />
								</c:if>
							</span>

							<span class="image-title"><%= HtmlUtil.escape(fileVersion.getTitle()) %></span>
						</a>
					</div>

					<c:if test="<%= showActions %>">
						<div class="hide" id="<portlet:namespace />buttonsContainer_<%= thumbnailId %>">
							<div class="buttons-container float-container" id="<portlet:namespace />buttons_<%= thumbnailId %>">
								<%@ include file="/html/portlet/image_gallery_display/image_action.jspf" %>
							</div>
						</div>
					</c:if>

					<%
					List assetTags = AssetTagServiceUtil.getTags(DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());
					%>

					<div id="<portlet:namespace />categorizationContainer_<%= fileEntry.getFileEntryId() %>" style="display: none;">
						<span <%= !assetTags.isEmpty() ? "class=\"has-tags\"" : "" %>>
							<liferay-ui:asset-categories-summary
								className="<%= DLFileEntryConstants.getClassName() %>"
								classPK="<%= fileEntry.getFileEntryId() %>"
							/>
						</span>

						<liferay-ui:asset-tags-summary
							className="<%= DLFileEntryConstants.getClassName() %>"
							classPK="<%= fileEntry.getFileEntryId() %>"
						/>
					</div>
				</c:if>
			</c:when>

			<c:when test="<%= curFolder != null %>">
				<portlet:renderURL var="viewFolderURL">
					<portlet:param name="struts_action" value="/image_gallery_display/view" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
				</portlet:renderURL>

				<c:choose>
					<c:when test="<%= curFolder.isMountPoint() %>">

						<%
						try {
							int curFoldersCount = DLAppServiceUtil.getFoldersCount(curFolder.getRepositoryId(), curFolder.getFolderId());

							String folderImageSrc = themeDisplay.getPathThemeImages() + "/file_system/large/drive.png";
						%>

							<div class="image-icon">
								<a class="image-link" href="<%= viewFolderURL.toString() %>" title="<%= HtmlUtil.escape(curFolder.getName()) + " - " + HtmlUtil.escape(curFolder.getDescription()) %>">
									<span class="image-thumbnail">
										<img alt="" border="no" src="<%= folderImageSrc %>" style="max-height: <%= PropsValues.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT %>px; max-width: <%= PropsValues.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH %>px;" />
									</span>

									<span class="image-title"><%= HtmlUtil.escape(StringUtil.shorten(curFolder.getName(), 60)) %></span>
								</a>
							</div>

						<%
						}
						catch (Exception e) {
							String folderImageSrc = themeDisplay.getPathThemeImages() + "/file_system/large/drive_error.png";
						%>

							<div class="image-icon">
								<span class="image-thumbnail error" title="<%= LanguageUtil.get(pageContext, "an-unexpected-error-occurred-while-connecting-to-the-repository") %>">
									<img alt="" border="no" src="<%= folderImageSrc %>" style="max-height: <%= PropsValues.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT %>px; max-width: <%= PropsValues.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH %>px;" />

									<span class="image-title"><%= HtmlUtil.escape(StringUtil.shorten(curFolder.getName(), 60)) %></span>
								</span>
							</div>

						<%
						}
						%>

					</c:when>
					<c:otherwise>
						<div class="image-icon">
							<a class="image-link" href="<%= viewFolderURL.toString() %>" title="<%= HtmlUtil.escape(curFolder.getName()) + " - " + HtmlUtil.escape(curFolder.getDescription()) %>">

								<%
									int curFoldersCount = DLAppServiceUtil.getFoldersCount(curFolder.getRepositoryId(), curFolder.getFolderId());

									int curImagesCount = 0;

									if (mediaGalleryMimeTypes != null) {
										curImagesCount = DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(curFolder.getRepositoryId(), curFolder.getFolderId(), WorkflowConstants.STATUS_APPROVED, mediaGalleryMimeTypes);
									}
									else {
										curImagesCount = DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(curFolder.getRepositoryId(), curFolder.getFolderId(), WorkflowConstants.STATUS_APPROVED);
									}

									String folderImageSrc = themeDisplay.getPathThemeImages() + "/file_system/large/folder_empty.png";

									if ((curFoldersCount + curImagesCount) > 0) {
										folderImageSrc = themeDisplay.getPathThemeImages() + "/file_system/large/folder_full_image.png";
									}
								%>

								<span class="image-thumbnail">
									<img alt="" border="no" src="<%= folderImageSrc %>" style="max-height: <%= PropsValues.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT %>px; max-width: <%= PropsValues.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH %>px;" />
								</span>

								<span class="image-title"><%= HtmlUtil.escape(StringUtil.shorten(curFolder.getName(), 60)) %></span>
							</a>
						</div>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<div style="float: left; margin: 100px 10px 0px;">
					<img alt="<liferay-ui:message key="image" />" border="no" src="<%= themeDisplay.getPathThemeImages() %>/application/forbidden_action.png" />
				</div>
			</c:otherwise>
		</c:choose>

	<%
	}
	%>

</div>

<c:if test="<%= !results.isEmpty() %>">
	<div class="taglib-search-iterator-page-iterator-bottom">
		<liferay-ui:search-paginator id="pageIteratorBottom" searchContainer="<%= searchContainer %>" />
	</div>
</c:if>

<%
PortletURL embeddedPlayerURL = renderResponse.createRenderURL();

embeddedPlayerURL.setParameter("struts_action", "/image_gallery_display/embedded_player");
embeddedPlayerURL.setWindowState(LiferayWindowState.POP_UP);
%>

<aui:script use="aui-image-viewer-gallery,aui-image-viewer-media">
	var viewportRegion = A.getDoc().get('viewportRegion');

	var maxHeight = (viewportRegion.height / 2);
	var maxWidth = (viewportRegion.width / 2);

	var imageGallery = new A.ImageGallery(
		{
			after: {
				render: function(event) {
					var instance = this;

					var footerNode = instance.footerNode;

					instance._actions = A.Node.create('<div class="lfr-image-gallery-actions"></div>');

					if (footerNode) {
						footerNode.append(instance._actions);
					}
				}

				<c:if test="<%= showActions %>">
					, load: function(event) {
						var instance = this;

						var currentLink = instance.getCurrentLink();

						var thumbnailId = currentLink.attr('thumbnailId');

						var actions = instance._actions;

						if (actions) {
							var defaultAction = A.one('#<portlet:namespace />buttonsContainer_' + thumbnailId);

							actions.empty();

							var action = defaultAction.clone().show();

							actions.append(action);
						}
					}
				</c:if>
			},
			delay: 5000,
			links: '#<portlet:namespace />imageGalleryAssetInfo .image-link.preview',
			maxHeight: maxHeight,
			maxWidth: maxWidth,
			playingLabel: '(<liferay-ui:message key="playing" />)',
			plugins: [
				{
					cfg: {
						'providers.liferay': {
							container: '<iframe frameborder="0" width="{width}" height="{height}" scrolling="no" src="<%= embeddedPlayerURL.toString() %>&<portlet:namespace />thumbnailURL={thumbnailURL}&<portlet:namespace />mp3PreviewURL={mp3PreviewURL}&<portlet:namespace />mp4PreviewURL={mp4PreviewURL}&<portlet:namespace />oggPreviewURL={oggPreviewURL}&<portlet:namespace />ogvPreviewURL={ogvPreviewURL}"></iframe>',
							matcher: /(.+)&mediaGallery=1/,
							options: A.merge(
								A.MediaViewerPlugin.DEFAULT_OPTIONS,
								{
									'thumbnailURL': '',
									'mp3PreviewURL': '',
									'mp4PreviewURL': '',
									'oggPreviewURL': '',
									'ogvPreviewURL': ''
								}
							),
							mediaRegex: /(.+)&mediaGallery=1/
						}
					},
					fn: A.MediaViewerPlugin
				}
			],
			zIndex: Liferay.zIndex.WINDOW
		}
	).render();
</aui:script>