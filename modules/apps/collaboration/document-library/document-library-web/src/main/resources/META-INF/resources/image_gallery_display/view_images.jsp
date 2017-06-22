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
SearchContainer igSearchContainer = (SearchContainer)request.getAttribute("view.jsp-igSearchContainer");

DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(igRequestHelper);
%>

<liferay-ui:search-container
	emptyResultsMessage="there-are-no-media-files-in-this-folder"
	searchContainer="<%= igSearchContainer %>"
>
	<liferay-ui:search-container-row
		className="Object"
		modelVar="result"
	>
		<%@ include file="/document_library/cast_result.jspf" %>

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

				FileVersion fileVersion = fileEntry.getFileVersion();

				boolean hasAudio = AudioProcessorUtil.hasAudio(fileVersion);
				boolean hasImages = ImageProcessorUtil.hasImages(fileVersion);
				boolean hasPDFImages = PDFProcessorUtil.hasImages(fileVersion);
				boolean hasVideo = VideoProcessorUtil.hasVideo(fileVersion);

				String imageURL = themeDisplay.getPathThemeImages() + "/file_system/large/" + DLUtil.getGenericName(fileEntry.getExtension()) + ".png";

				int playerHeight = 500;

				String dataOptions = StringPool.BLANK;

				if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED) {
					if (hasAudio) {
						imageURL = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, HtmlUtil.escapeURL("&audioPreview=1") + "&supportedAudio=1&mediaGallery=1");

						for (String audioContainer : PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS) {
							dataOptions += "&" + audioContainer + "PreviewURL=" + HtmlUtil.escapeURL(DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&supportedAudio=1&audioPreview=1&type=" + audioContainer));
						}

						playerHeight = 43;
					}
					else if (hasImages) {
						imageURL = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&imagePreview=1");
					}
					else if (hasPDFImages) {
						imageURL = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&previewFileIndex=1");
					}
					else if (hasVideo) {
						imageURL = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&supportedVideo=1&mediaGallery=1");

						for (String videoContainer : PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS) {
							dataOptions += "&" + videoContainer + "PreviewURL=" + HtmlUtil.escapeURL(DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&supportedVideo=1&videoPreview=1&type=" + videoContainer));
						}
					}
				}

				String title = fileEntry.getTitle();

				if (Validator.isNotNull(fileEntry.getDescription())) {
					title += " - " + fileEntry.getDescription();
				}

				row.setCssClass("lfr-asset-item");
				%>

				<liferay-ui:search-container-column-text>
					<div class="image-link preview" <%= (hasAudio || hasVideo) ? "data-options=\"height=" + playerHeight + "&thumbnailURL=" + HtmlUtil.escapeURL(DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&videoThumbnail=1")) + "&width=640" + dataOptions + "\"" : StringPool.BLANK %> href="<%= imageURL %>" thumbnailId="<%= thumbnailId %>" title="<%= HtmlUtil.escapeAttribute(title) %>">
						<c:choose>
							<c:when test="<%= Validator.isNull(imageURL) %>">
								<liferay-frontend:icon-vertical-card
									actionJsp='<%= dlPortletInstanceSettingsHelper.isShowActions() ? "/image_gallery_display/image_action.jsp" : StringPool.BLANK %>'
									actionJspServletContext="<%= application %>"
									cssClass="entry-display-style"
									icon="documents-and-media"
									resultRow="<%= row %>"
									title="<%= dlPortletInstanceSettingsHelper.isShowActions() ? fileEntry.getTitle() : StringPool.BLANK %>"
								/>
							</c:when>
							<c:otherwise>
								<liferay-frontend:vertical-card
									actionJsp='<%= dlPortletInstanceSettingsHelper.isShowActions() ? "/image_gallery_display/image_action.jsp" : StringPool.BLANK %>'
									actionJspServletContext="<%= application %>"
									cssClass="entry-display-style"
									imageUrl="<%= imageURL %>"
									resultRow="<%= row %>"
									title="<%= dlPortletInstanceSettingsHelper.isShowActions() ? fileEntry.getTitle() : StringPool.BLANK %>"
								/>
							</c:otherwise>
						</c:choose>
					</div>
				</liferay-ui:search-container-column-text>
			</c:when>
			<c:otherwise>
				<portlet:renderURL var="viewFolderURL">
					<portlet:param name="mvcRenderCommandName" value="/image_gallery_display/view" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
				</portlet:renderURL>

				<%
				row.setCssClass("lfr-asset-folder");
				%>

				<liferay-ui:search-container-column-text>
					<liferay-frontend:horizontal-card
						actionJsp='<%= dlPortletInstanceSettingsHelper.isShowActions() ? "/document_library/folder_action.jsp" : StringPool.BLANK %>'
						actionJspServletContext="<%= application %>"
						resultRow="<%= row %>"
						text="<%= curFolder.getName() %>"
						url="<%= viewFolderURL %>"
					>
						<liferay-frontend:horizontal-card-col>
							<liferay-frontend:horizontal-card-icon
								icon='<%= curFolder.isMountPoint() ? "repository" : "folder" %>'
							/>
						</liferay-frontend:horizontal-card-col>
					</liferay-frontend:horizontal-card>
				</liferay-ui:search-container-column-text>
			</c:otherwise>
		</c:choose>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator displayStyle="icon" markupView="lexicon" resultRowSplitter="<%= new IGResultRowSplitter() %>" searchContainer="<%= igSearchContainer %>" />
</liferay-ui:search-container>

<%
PortletURL embeddedPlayerURL = renderResponse.createRenderURL();

embeddedPlayerURL.setParameter("mvcPath", "/image_gallery_display/embedded_player.jsp");
embeddedPlayerURL.setWindowState(LiferayWindowState.POP_UP);
%>

<aui:script use="aui-image-viewer,aui-image-viewer-media">
	var viewportRegion = A.getDoc().get('viewportRegion');

	var maxHeight = (viewportRegion.height);
	var maxWidth = (viewportRegion.width);

	var playingVideoIndex = -1;

	var imageViewer = new A.ImageViewer(
		{
			after: {
				<c:if test="<%= dlPortletInstanceSettingsHelper.isShowActions() %>">
					load: function(event) {
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
			infoTemplate: '<%= LanguageUtil.format(request, "image-x-of-x", new String[] {"{current}", "{total}"}, false) %>',
			links: '#<portlet:namespace />imageGalleryAssetInfo .image-link.preview',
			maxHeight: maxHeight,
			maxWidth: maxWidth,
			on: {
				'currentIndexChange': function() {
					if (playingVideoIndex != -1) {
						Liferay.fire('<portlet:namespace />ImageViewer:currentIndexChange');

						playingVideoIndex = -1;
					}
				}
			},
			playingLabel: '(<liferay-ui:message key="playing" />)',
			plugins: [
				{
					cfg: {
						'providers.liferay': {
							container: '<iframe frameborder="0" height="{height}" scrolling="no" src="<%= embeddedPlayerURL.toString() %>&<portlet:namespace />thumbnailURL={thumbnailURL}&<portlet:namespace />mp3PreviewURL={mp3PreviewURL}&<portlet:namespace />mp4PreviewURL={mp4PreviewURL}&<portlet:namespace />oggPreviewURL={oggPreviewURL}&<portlet:namespace />ogvPreviewURL={ogvPreviewURL}" width="{width}"></iframe>',
							matcher: /(.+)&mediaGallery=1/,
							mediaRegex: /(.+)&mediaGallery=1/,
							options: A.merge(
								A.MediaViewerPlugin.DEFAULT_OPTIONS,
								{
									'mp3PreviewURL': '',
									'mp4PreviewURL': '',
									'oggPreviewURL': '',
									'ogvPreviewURL': '',
									'thumbnailURL': ''
								}
							)
						}
					},
					fn: A.MediaViewerPlugin
				}
			],
			zIndex: ++Liferay.zIndex.WINDOW
		}
	).render();

	Liferay.on('<portlet:namespace />Video:play', function() {
		imageViewer.pause();

		playingVideoIndex = this.get('currentIndex');
	});

	var onClickLinksDefaultFn = imageViewer._onClickLinks;

	imageViewer._onClickLinks = function(event) {
		if (!event.target.ancestor('.dropdown')) {
			onClickLinksDefaultFn.call(this, event);
		}
	};

	imageViewer.set('links', '#<portlet:namespace />imageGalleryAssetInfo .image-link.preview');
</aui:script>