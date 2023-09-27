<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/image_gallery_display/init.jsp" %>

<%
SearchContainer<?> igSearchContainer = (SearchContainer)request.getAttribute("view.jsp-igSearchContainer");

DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(new IGRequestHelper(request));
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
				row.setPrimaryKey(String.valueOf(fileEntry.getFileEntryId()));

				String dataOptions = StringPool.BLANK;

				FileVersion fileVersion = fileEntry.getFileVersion();

				boolean hasAudio = AudioProcessorUtil.hasAudio(fileVersion);
				boolean hasImages = ImageProcessorUtil.hasImages(fileVersion);

				boolean hasPDFImages = PDFProcessorUtil.hasImages(fileVersion);
				boolean hasVideo = VideoProcessorUtil.hasVideo(fileVersion);

				String imageURL = themeDisplay.getPathThemeImages() + "/file_system/large/" + DLUtil.getGenericName(fileEntry.getExtension()) + ".png";
				int playerHeight = 500;

				String thumbnailId = null;

				if (fileShortcut != null) {
					thumbnailId = "shortcut_" + fileShortcut.getFileShortcutId();
				}
				else {
					thumbnailId = "entry_" + fileEntry.getFileEntryId();
				}

				if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED) {
					if (hasAudio) {
						for (String audioContainer : PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS) {
							dataOptions += "&" + audioContainer + "PreviewURL=" + HtmlUtil.escapeURL(DLURLHelperUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&supportedAudio=1&audioPreview=1&type=" + audioContainer));
						}

						imageURL = DLURLHelperUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, HtmlUtil.escapeURL("&audioPreview=1") + "&supportedAudio=1&mediaGallery=1");
						playerHeight = 43;
					}
					else if (hasImages) {
						imageURL = DLURLHelperUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&imagePreview=1");
					}
					else if (hasPDFImages) {
						imageURL = DLURLHelperUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&previewFileIndex=1");
					}
					else if (hasVideo) {
						for (String videoContainer : PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS) {
							dataOptions += "&" + videoContainer + "PreviewURL=" + HtmlUtil.escapeURL(DLURLHelperUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&supportedVideo=1&videoPreview=1&type=" + videoContainer));
						}

						imageURL = DLURLHelperUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&supportedVideo=1&mediaGallery=1");
						playerHeight = PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_HEIGHT;
					}
				}

				String thumbnailSrc = null;

				if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED) {
					thumbnailSrc = DLURLHelperUtil.getThumbnailSrc(fileEntry, fileVersion, themeDisplay);
				}

				String description = fileEntry.getDescription();

				String title = fileEntry.getTitle();

				if (Validator.isNotNull(description)) {
					title += " - " + description;
				}
				%>

				<liferay-ui:search-container-column-text>
					<div class="image-link preview" <%= (hasAudio || hasVideo) ? "data-options=\"height=" + playerHeight + "&thumbnailURL=" + HtmlUtil.escapeURL(DLURLHelperUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&videoThumbnail=1")) + "&width=640" + dataOptions + "\"" : StringPool.BLANK %> description="<%= description %>" href="<%= imageURL %>" tabindex="0" thumbnailId="<%= thumbnailId %>" title="<%= title %>">
						<div class="card-type-asset entry-display-style">
							<div class="card card-interactive card-interactive-secondary">
								<div class="aspect-ratio card-item-first">
									<c:choose>
										<c:when test="<%= Validator.isNull(thumbnailSrc) %>">
											<aui:icon cssClass="aspect-ratio-item-center-middle aspect-ratio-item-fluid card-type-asset-icon" image="documents-and-media" markupView="lexicon" />
										</c:when>
										<c:otherwise>
											<img alt="<%= description %>" class="aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="<%= thumbnailSrc %>" />
										</c:otherwise>
									</c:choose>
								</div>

								<c:if test="<%= dlPortletInstanceSettingsHelper.isShowActions() %>">
									<div class="card-body">
										<div class="card-row">
											<div class="autofit-col autofit-col-expand">
												<aui:a cssClass="card-title text-truncate" href="" title="<%= HtmlUtil.escapeAttribute(title) %>">
													<%= HtmlUtil.escape(title) %>
												</aui:a>
											</div>

											<div class="autofit-col">

												<%
												IGViewFileVersionDisplayContext igViewFileVersionDisplayContext = null;

												if (fileShortcut == null) {
													igViewFileVersionDisplayContext = igDisplayContextProvider.getIGViewFileVersionActionsDisplayContext(request, response, fileEntry.getFileVersion());
												}
												else {
													igViewFileVersionDisplayContext = igDisplayContextProvider.getIGViewFileVersionActionsDisplayContext(request, response, fileShortcut);
												}
												%>

												<clay:dropdown-actions
													aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
													dropdownItems="<%= igViewFileVersionDisplayContext.getActionDropdownItems() %>"
													propsTransformer="document_library/js/DLFileEntryDropdownPropsTransformer"
												/>
											</div>
										</div>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</liferay-ui:search-container-column-text>
			</c:when>
			<c:otherwise>

				<%
				row.setCssClass("card-page-item card-page-item-directory");
				row.setPrimaryKey(String.valueOf(curFolder.getPrimaryKey()));

				request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW, row);
				%>

				<portlet:renderURL var="viewFolderURL">
					<portlet:param name="mvcRenderCommandName" value="/image_gallery_display/view" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text>
					<clay:horizontal-card
						horizontalCard="<%= new FolderHorizontalCard(dlPortletInstanceSettingsHelper, dlTrashHelper, curFolder, request, renderResponse, null, viewFolderURL) %>"
						propsTransformer="document_library/js/DLFolderDropdownPropsTransformer"
					/>
				</liferay-ui:search-container-column-text>
			</c:otherwise>
		</c:choose>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="icon"
		markupView="lexicon"
		resultRowSplitter="<%= new IGResultRowSplitter() %>"
		searchContainer="<%= igSearchContainer %>"
	/>
</liferay-ui:search-container>

<%
PortletURL embeddedPlayerURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCPath(
	"/image_gallery_display/embedded_player.jsp"
).setWindowState(
	LiferayWindowState.POP_UP
).buildPortletURL();
%>

<aui:script use="aui-image-viewer,aui-image-viewer-media">
	var viewportRegion = A.getDoc().get('viewportRegion');

	var maxHeight = viewportRegion.height;
	var maxWidth = viewportRegion.width;

	var playingMediaIndex = -1;

	var imageViewer = new A.ImageViewer({
		after: {
			<c:if test="<%= dlPortletInstanceSettingsHelper.isShowActions() %>">
				load: function (event) {
					var instance = this;

					var currentLink = instance.getCurrentLink();

					var thumbnailId = currentLink.attr('thumbnailId');

					var actions = instance._actions;

					if (actions) {
						var defaultAction = A.one(
							'#<portlet:namespace />buttonsContainer_' + thumbnailId
						);

						actions.empty();

						var action = defaultAction.clone().show();

						actions.append(action);
					}
				},
			</c:if>
		},
		delay: 5000,
		infoTemplate:
			'<%= LanguageUtil.format(request, "image-x-of-x", new String[] {"{current}", "{total}"}, false) %>',
		links: '#<portlet:namespace />imageGalleryAssetInfo .image-link.preview',
		maxHeight: maxHeight,
		maxWidth: maxWidth,
		on: {
			currentIndexChange: function () {
				if (playingMediaIndex != -1) {
					Liferay.fire(
						'<portlet:namespace />ImageViewer:currentIndexChange'
					);

					playingMediaIndex = -1;
				}
			},
			visibleChange: function (event) {
				if (!event.newVal && playingMediaIndex != -1) {
					Liferay.fire('<portlet:namespace />ImageViewer:close');

					playingMediaIndex = -1;
				}
			},
		},
		playingLabel: '(<liferay-ui:message key="playing" />)',
		plugins: [
			{
				cfg: {
					'providers.liferay': {
						container:
							'<iframe frameborder="0" height="{height}" scrolling="no" src="<%= embeddedPlayerURL.toString() %>&<portlet:namespace />thumbnailURL={thumbnailURL}&<portlet:namespace />mp3PreviewURL={mp3PreviewURL}&<portlet:namespace />mp4PreviewURL={mp4PreviewURL}&<portlet:namespace />oggPreviewURL={oggPreviewURL}&<portlet:namespace />ogvPreviewURL={ogvPreviewURL}" width="{width}"></iframe>',
						matcher: /(.+)&mediaGallery=1/,
						mediaRegex: /(.+)&mediaGallery=1/,
						options: Object.assign(
							{},
							A.MediaViewerPlugin.DEFAULT_OPTIONS,
							{
								mp3PreviewURL: '',
								mp4PreviewURL: '',
								oggPreviewURL: '',
								ogvPreviewURL: '',
								thumbnailURL: '',
							}
						),
					},
				},
				fn: A.MediaViewerPlugin,
			},
		],
		zIndex: ++Liferay.zIndex.WINDOW,
	});

	imageViewer.TPL_CLOSE = imageViewer.TPL_CLOSE.replace(
		/<\s*span[^>]*>(.*?)<\s*\/\s*span>/,
		Liferay.Util.getLexiconIconTpl('times', 'icon-monospaced')
	);

	var TPL_PLAYER_PAUSE =
		'<span>' + Liferay.Util.getLexiconIconTpl('pause', 'glyphicon') + '</span>';
	var TPL_PLAYER_PLAY =
		'<span>' + Liferay.Util.getLexiconIconTpl('play', 'glyphicon') + '</span>';

	imageViewer.TPL_PLAYER = TPL_PLAYER_PLAY;

	imageViewer._syncPlaying = function () {
		if (this.get('playing')) {
			this._player.setHTML(TPL_PLAYER_PAUSE);
		}
		else {
			this._player.setHTML(TPL_PLAYER_PLAY);
		}
	};

	// LPS-141384

	var onKeydownDefaultFn = imageViewer._onKeydown;
	imageViewer._onKeydown = function (event) {
		onKeydownDefaultFn.call(this, event);

		var target = document.activeElement;

		if (
			!this.get('visible') &&
			event.isKey('ENTER') &&
			target.classList.contains('image-link')
		) {
			this.show();
			this.set('currentIndex', this.get('links').indexOf(target));
		}
	};

	imageViewer.render();

	Liferay.on('<portlet:namespace />Video:play', function () {
		imageViewer.pause();

		playingMediaIndex = this.get('currentIndex');
	});

	Liferay.on('<portlet:namespace />Audio:play', function () {
		imageViewer.pause();

		playingMediaIndex = this.get('currentIndex');
	});

	var onClickLinksDefaultFn = imageViewer._onClickLinks;

	imageViewer._onClickLinks = function (event) {
		if (!event.target.ancestor('.dropdown')) {
			onClickLinksDefaultFn.call(this, event);
		}
	};

	imageViewer.set(
		'links',
		'#<portlet:namespace />imageGalleryAssetInfo .image-link.preview'
	);
</aui:script>