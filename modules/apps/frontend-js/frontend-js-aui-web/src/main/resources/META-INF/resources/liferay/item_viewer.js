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

/**
 * @deprecated As of Athanasius (7.3.x), replaced by ItemSelectorPreview.es.js
 * @module liferay-item-viewer
 */

AUI.add(
	'liferay-item-viewer',
	(A) => {
		const Do = A.Do;

		const Lang = A.Lang;

		const CSS_ACTIVE = 'active';

		const CSS_CAPTION = A.getClassName('image', 'viewer', 'caption');

		const CSS_ICON_MONOSPACED = 'icon-monospaced';

		const CSS_IMAGE_VIEWER = A.getClassName('image', 'viewer');

		const CSS_IMAGE_VIEWER_BASE = A.getClassName(CSS_IMAGE_VIEWER, 'base');

		const CSS_IMAGE_VIEWER_FOOTER = A.getClassName(
			CSS_IMAGE_VIEWER,
			'footer'
		);

		const CSS_FOOTER_CONTROL = A.getClassName(
			CSS_IMAGE_VIEWER_FOOTER,
			'control'
		);

		const CSS_FOOTER_CONTROL_LEFT = A.getClassName(
			CSS_IMAGE_VIEWER_FOOTER,
			'control',
			'left'
		);

		const CSS_FOOTER_CONTROL_LEFT_BASE = A.getClassName(
			CSS_IMAGE_VIEWER_BASE,
			'control',
			'left'
		);

		const CSS_FOOTER_CONTROL_RIGHT = A.getClassName(
			CSS_IMAGE_VIEWER_FOOTER,
			'control',
			'right'
		);

		const CSS_FOOTER_CONTROL_RIGHT_BASE = A.getClassName(
			CSS_IMAGE_VIEWER_BASE,
			'control',
			'right'
		);

		const CSS_IMAGE_CONTAINER = A.getClassName(
			CSS_IMAGE_VIEWER_BASE,
			'image',
			'container'
		);

		const CSS_IMAGE_INFO = A.getClassName(
			CSS_IMAGE_VIEWER_BASE,
			'image',
			'info'
		);

		const CSS_LOADING_ICON = A.getClassName(
			CSS_IMAGE_VIEWER_BASE,
			'loading',
			'icon'
		);

		const CSS_PREVIEW_TIMEOUT_MESSAGE = 'preview-timeout-message';

		const STR_BLANK = '';

		const STR_DOT = '.';

		const STR_RENDER_CONTROLS = 'renderControls';

		const STR_RENDER_SIDEBAR = 'renderSidebar';

		const STR_SRC_NODE = 'srcNode';

		const TPL_CLOSE =
			'<button class="close image-viewer-base-control image-viewer-close lfr-item-viewer-close" type="button"><span class="' +
			CSS_ICON_MONOSPACED +
			'">' +
			Liferay.Util.getLexiconIconTpl('angle-left') +
			'</span><span class="lfr-item-viewer-close-text text-truncate">{0}</span></button>';

		const TPL_EDIT_DIALOG_TITLE = '{edit} {title} ({copy})';

		const TPL_EDIT_ICON =
			'<a class="lfr-item-viewer-icon-info-link" href="{editItemURL}" style="right: 60px;" title="' +
			Liferay.Language.get('edit-image') +
			'"><span class="' +
			CSS_ICON_MONOSPACED +
			' lfr-item-viewer-icon-info">' +
			Liferay.Util.getLexiconIconTpl('pencil', '') +
			'</span></a>';

		const TPL_INFO_ICON =
			'<a class="lfr-item-viewer-icon-info-link" data-content=".image-viewer-focused" data-target=".image-viewer-sidenav" data-toggle="liferay-sidenav" data-type="fixed-push" href="" title="' +
			Liferay.Language.get('image-info') +
			'" ><span class="' +
			CSS_ICON_MONOSPACED +
			' lfr-item-viewer-icon-info">' +
			Liferay.Util.getLexiconIconTpl('info-circle', '') +
			'</span></a>';

		const TPL_INFO_TAB_BODY =
			'<div class="{className} fade show tab-pane" id="{tabId}">{content}</div>';

		const TPL_INFO_TAB_BODY_CONTENT =
			'<dt class="{dtClassName}">{dt}</dt><dd class="{ddClassName}">{dd}</dd>';

		const TPL_INFO_TAB_TITLE =
			'<li class="nav-item"><a aria-expanded="false" class="nav-link {className}" data-toggle="liferay-tab" href="#{tabId}">{tabTitle}</a></li>';

		const LiferayItemViewer = A.Component.create({
			ATTRS: {
				btnCloseCaption: {
					validator: Lang.isString,
					value: '',
				},

				circular: {
					value: true,
				},

				editItemURL: {
					validator: Lang.isString,
				},

				infoTemplate: {
					value: '{current} of {total}',
				},

				playing: {
					value: false,
				},

				previewTimeout: {
					validator: Lang.isNumber,
					value: 5000,
				},

				renderControls: {
					validator: Lang.isBoolean,
					value: true,
				},

				renderSidebar: {
					validator: Lang.isBoolean,
					value: true,
				},

				showPlayer: {
					value: false,
				},

				uploadItemURL: {
					validator: Lang.isString,
				},

				zIndex: {
					value: 5,
				},
			},

			EXTENDS: A.ImageViewer,

			NAME: 'image-viewer',

			NS: 'lfr-item-viewer',

			prototype: {
				_afterBindUI() {
					const instance = this;

					instance._eventHandles = instance._eventHandles.concat(
						instance._displacedMethodHandles
					);

					instance._bindSidebarEvents();
				},

				_afterGetCurrentImage() {
					const instance = this;

					let retVal;

					if (instance._showPreviewError) {
						retVal = new Do.AlterReturn(
							'Return error message node',
							instance
								.get(STR_SRC_NODE)
								.one(STR_DOT + CSS_PREVIEW_TIMEOUT_MESSAGE)
						);
					}

					return retVal;
				},

				_afterShow() {
					const instance = this;

					instance._showPreviewError = false;

					instance._timer = A.later(
						instance.get('previewTimeout'),
						instance,
						instance._showPreviewErrorMessage
					);
				},

				_afterShowCurrentImage() {
					const instance = this;

					const link = instance
						.get('links')
						.item(instance.get('currentIndex'));

					const metadata = link.getData('metadata');

					const image = instance._getCurrentImage();

					if (instance.get(STR_RENDER_CONTROLS) && metadata) {
						instance._populateImageMetadata(image, metadata);
					}
				},

				_beforeOnClickControl(event) {
					event.stopImmediatePropagation();
				},

				_beforeSyncInfoUI() {
					const instance = this;

					let retVal;

					if (!instance.get(STR_RENDER_CONTROLS)) {
						retVal = new Do.Halt();
					}

					return retVal;
				},

				_bindSidebarEvents() {
					const instance = this;

					if (
						instance.get(STR_RENDER_CONTROLS) &&
						instance.get(STR_RENDER_SIDEBAR)
					) {
						const togglers = document.querySelectorAll(
							'[data-toggle="liferay-sidenav"]'
						);

						Array.from(togglers).forEach(
							Liferay.SideNavigation.initialize
						);
					}
				},

				_destroyPreviewTimer() {
					const instance = this;

					if (instance._timer) {
						instance._timer.cancel();
						instance._timer = null;
					}
				},

				_getImageInfoNodes() {
					const instance = this;

					if (!instance._imageInfoNodes) {
						instance._imageInfoNodes = instance
							.get(STR_SRC_NODE)
							.all(STR_DOT + CSS_IMAGE_INFO);
					}

					return instance._imageInfoNodes;
				},

				_getUploadFileMetadata(file) {
					return {
						groups: [
							{
								data: [
									{
										key: Liferay.Language.get('format'),
										value: file.type,
									},
									{
										key: Liferay.Language.get('name'),
										value: file.title,
									},
								],
								title: Liferay.Language.get('file-info'),
							},
						],
					};
				},

				_onClickEditIcon(event) {
					const instance = this;

					event.preventDefault();

					const item = instance
						.get('links')
						.item(instance.get('currentIndex'));

					const itemTitle = item.dataset.title;
					const itemURL = item.dataset.url;

					const editDialogTitle = Lang.sub(TPL_EDIT_DIALOG_TITLE, {
						copy: Liferay.Language.get('copy'),
						edit: Liferay.Language.get('edit'),
						title: itemTitle,
					});

					let editEntityBaseZIndex = Liferay.zIndex.WINDOW;

					const iframeModalEl = window.parent.document.getElementsByClassName(
						'dialog-iframe-modal'
					);

					if (iframeModalEl) {
						editEntityBaseZIndex = window
							.getComputedStyle(iframeModalEl[0])
							.getPropertyValue('z-index');
					}

					Liferay.Util.editEntity(
						{
							dialog: {
								destroyOnHide: true,
								zIndex: editEntityBaseZIndex + 100,
							},
							id: instance.get('id'),
							stack: false,
							title: editDialogTitle,
							uri: instance.get('editItemURL'),
							urlParams: {
								entityURL: itemURL,
								saveFileName: itemTitle,
								saveParamName: 'imageSelectorFileName',
								saveURL: instance.get('uploadItemURL'),
							},
						},
						A.bind('_onSaveEditSuccess', instance)
					);
				},

				_onClickInfoIcon() {
					const instance = this;

					instance._getImageInfoNodes().toggle();
				},

				_onSaveEditSuccess(event) {
					const instance = this;

					const touchEnabled = A.UA.touchEnabled;

					// LPS-82848

					if (touchEnabled && instance._scrollView) {
						instance._detachSwipeEvents();
					}

					instance.appendNewLink(event.data);

					// LPS-82848

					if (touchEnabled && instance._scrollView) {
						instance._scrollView.destroy();

						instance._scrollView = new A.ScrollView(
							instance.get('swipe')
						);

						instance._plugPaginator();

						instance._scrollView.render();

						instance._attachSwipeEvents();
					}
				},

				_populateImageMetadata(image, metadata) {
					const imageViewer = image.ancestor('.image-viewer');

					const sidenavTabContent = imageViewer.one(
						'.image-viewer-sidenav .tab-content'
					);
					const sidenavTabList = imageViewer.one(
						'.image-viewer-sidenav ul'
					);

					sidenavTabContent.all('.tab-pane').remove();
					sidenavTabList.all('li').remove();

					metadata = JSON.parse(metadata);

					metadata.groups.forEach((group, index) => {
						const groupId = A.guid();

						const tabTitleNode = A.Node.create(
							Lang.sub(TPL_INFO_TAB_TITLE, {
								className: index === 0 ? CSS_ACTIVE : STR_BLANK,
								tabId: groupId,
								tabTitle: group.title,
							})
						);

						sidenavTabList.append(tabTitleNode);

						const dataStr = group.data.reduce(
							(previousValue, currentValue) => {
								return (
									previousValue +
									Lang.sub(TPL_INFO_TAB_BODY_CONTENT, {
										dd: currentValue.value,
										ddClassName: '',
										dt: currentValue.key,
										dtClassName: 'h5',
									})
								);
							},
							STR_BLANK
						);

						const tabContentNode = A.Node.create(
							Lang.sub(TPL_INFO_TAB_BODY, {
								className: index === 0 ? CSS_ACTIVE : STR_BLANK,
								content: dataStr,
								tabId: groupId,
							})
						);

						sidenavTabContent.append(tabContentNode);
					});
				},

				_renderControls() {
					const instance = this;

					const boundingBox = instance.get('boundingBox');

					const imageViewerClose = boundingBox.one(
						'.image-viewer-close'
					);

					if (!imageViewerClose) {
						this._closeEl = A.Node.create(this.TPL_CLOSE);

						boundingBox.prepend(this._closeEl);
					}
				},

				_renderFooter() {
					const instance = this;

					const container = A.Node.create(
						instance.TPL_FOOTER_CONTENT
					);

					instance.setStdModContent('footer', container);

					const captionEl = A.Node.create(instance.TPL_CAPTION);

					captionEl.selectable();

					container.append(captionEl);

					instance._captionEl = captionEl;

					if (instance.get(STR_RENDER_CONTROLS)) {
						const controlsContainer = A.Node.create(
							'<div class="image-viewer-footer-controls"></div>'
						);
						const infoEl = A.Node.create(instance.TPL_INFO);

						controlsContainer.append(
							instance.get('controlPrevious')
						);

						infoEl.selectable();

						controlsContainer.append(infoEl);

						instance._infoEl = infoEl;

						controlsContainer.append(instance.get('controlNext'));

						container.append(controlsContainer);

						if (instance.get(STR_RENDER_SIDEBAR)) {
							const infoIconEl = A.Node.create(TPL_INFO_ICON);

							container.append(infoIconEl);

							instance._infoIconEl = infoIconEl;
						}

						if (instance.get('editItemURL')) {
							const editIconEl = A.Node.create(
								Lang.sub(TPL_EDIT_ICON, {
									editItemURL: instance.get('editItemURL'),
								})
							);

							editIconEl.on(
								'click',
								A.bind('_onClickEditIcon', instance)
							);

							container.append(editIconEl);

							instance._editIconEl = editIconEl;
						}
					}
				},

				_renderSidenav() {
					const instance = this;

					const imageViewerSidenav = A.Node.create(
						instance.TPL_SIDENAV
					);

					instance.get('contentBox').prepend(imageViewerSidenav);
				},

				_setLinks(val) {
					const instance = this;

					let links;

					if (val instanceof A.NodeList) {
						links = val;
					}
					else if (A.Lang.isString(val)) {
						links = A.all(val);
					}
					else {
						links = new A.NodeList([val]);
					}

					const sources = [];

					links.each((item) => {
						sources.push(
							item.attr('href') || item.attr('data-href')
						);
					});

					if (sources.length) {
						instance.set('sources', sources);
					}

					return links;
				},

				_showPreviewErrorMessage() {
					const instance = this;

					const loadingIcon = instance
						.get(STR_SRC_NODE)
						.one('.' + CSS_LOADING_ICON);

					instance._showPreviewError = true;

					if (loadingIcon) {
						loadingIcon.hide();
					}

					instance.fire('animate');
				},

				_syncCaptionUI() {
					const instance = this;

					const links = instance.get('links');

					const link = links.item(instance.get('currentIndex'));

					const caption =
						link.attr('title') || link.attr('data-title');

					instance._captionEl.set('text', caption);
				},

				TPL_CAPTION: '<p class="' + CSS_CAPTION + '"></p>',

				TPL_CONTROL_LEFT:
					'<a class="' +
					CSS_FOOTER_CONTROL +
					' ' +
					CSS_FOOTER_CONTROL_LEFT_BASE +
					' ' +
					CSS_FOOTER_CONTROL_LEFT +
					'" href="javascript:void(0);">' +
					'<span class="' +
					CSS_ICON_MONOSPACED +
					'">' +
					Liferay.Util.getLexiconIconTpl('angle-left') +
					'</span>' +
					'</a>',

				TPL_CONTROL_RIGHT:
					'<a class="' +
					CSS_FOOTER_CONTROL +
					' ' +
					CSS_FOOTER_CONTROL_RIGHT_BASE +
					' ' +
					CSS_FOOTER_CONTROL_RIGHT +
					'" href="javascript:void(0);">' +
					'<span class="' +
					CSS_ICON_MONOSPACED +
					'">' +
					Liferay.Util.getLexiconIconTpl('angle-right') +
					'</span>' +
					'</a>',

				TPL_IMAGE_CONTAINER:
					'<div class="' +
					CSS_IMAGE_CONTAINER +
					'">' +
					'<p class="fade preview-timeout-message text-muted">' +
					Liferay.Language.get(
						'preview-image-is-taking-longer-than-expected'
					) +
					'</p>' +
					'</div>',

				TPL_SIDENAV:
					'<div class="closed image-viewer-sidenav sidenav-fixed sidenav-menu-slider sidenav-right">' +
					'<div class="image-viewer-sidenav-menu sidebar sidebar-light sidenav-menu">' +
					'<div class="sidebar-header">' +
					'<a class="' +
					CSS_ICON_MONOSPACED +
					' d-block d-sm-none image-viewer-sidenav-close sidenav-close " href="">' +
					Liferay.Util.getLexiconIconTpl('times') +
					'</a>' +
					'<h4 class="image-viewer-sidenav-header">' +
					'<ul class="nav nav-tabs"></ul>' +
					'</h4>' +
					'</div>' +
					'<div class="image-viewer-sidenav-body sidebar-body">' +
					'<div class="tab-content"></div>' +
					'</div>' +
					'</div>' +
					'</div>',

				appendNewLink(imageData) {
					const instance = this;

					const links = instance.get('links');

					const linkContainer = links.last().ancestor();

					const newLinkContainer = linkContainer.clone();

					const newLink = newLinkContainer.one('.item-preview');

					newLink.setAttribute('data-href', imageData.file.url);
					newLink.setAttribute('data-title', imageData.file.title);
					newLink.setAttribute('data-value', imageData.file.url);
					newLink.setAttribute('data-url', imageData.file.url);

					newLink.all('[style]').each((node) => {
						let styleAttr = node.getAttribute('style');

						if (styleAttr) {
							styleAttr = styleAttr.replace(
								/\burl\s*\(\s*["']?http:\/\/((?:[^"'\r\n/,]+)\/?)+["']?\s*\)/i,
								'url("' + imageData.file.url + '")'
							);

							node.setAttribute('style', styleAttr);
						}
					});

					newLink.setData(
						'metadata',
						JSON.stringify(
							instance._getUploadFileMetadata(imageData.file)
						)
					);

					linkContainer.placeAfter(newLinkContainer);

					links.push(newLink);

					instance.updateCurrentImage(imageData, newLink);

					instance.set('links', links);

					instance.set('currentIndex', links.size() - 1);
				},

				initializer() {
					const instance = this;

					instance.TPL_CLOSE = Lang.sub(TPL_CLOSE, [
						instance.get('btnCloseCaption'),
					]);

					instance._displacedMethodHandles = [
						instance.after(
							'visibleChange',
							instance._destroyPreviewTimer,
							instance
						),
						Do.after('_afterBindUI', instance, 'bindUI', instance),
						Do.after(
							'_afterGetCurrentImage',
							instance,
							'_getCurrentImage',
							instance
						),
						Do.after('_afterShow', instance, 'show', instance),
						Do.after(
							'_afterShowCurrentImage',
							instance,
							'_showCurrentImage',
							instance
						),
						Do.after(
							'_bindSidebarEvents',
							instance,
							'_afterLinksChange',
							instance
						),
						Do.before(
							'_beforeOnClickControl',
							instance,
							'_onClickControl',
							instance
						),
						Do.before(
							'_beforeSyncInfoUI',
							instance,
							'_syncInfoUI',
							instance
						),
					];
				},

				renderUI() {
					const instance = this;

					LiferayItemViewer.superclass.renderUI.apply(
						this,
						arguments
					);

					if (
						instance.get(STR_RENDER_CONTROLS) &&
						instance.get(STR_RENDER_SIDEBAR)
					) {
						instance._renderSidenav();
					}
				},

				updateCurrentImage(itemData, link) {
					const instance = this;

					link =
						link ||
						instance
							.get('links')
							.item(instance.get('currentIndex'));

					const itemFile = itemData.file;

					const itemFileURL = itemFile.url;

					const image = instance._getCurrentImage();

					if (!itemFile.mimeType.match(/image.*/)) {
						image.attr(
							'src',
							Liferay.ThemeDisplay.getPathThemeImages() +
								'/file_system/large/default.png'
						);
					}
					else {
						image.attr('src', itemFileURL);
					}

					if (itemFile.resolvedValue) {
						link.setData('value', itemFile.resolvedValue);
					}
					else {
						const imageValue = {
							fileEntryId: itemFile.fileEntryId,
							groupId: itemFile.groupId,
							title: itemFile.title,
							type: itemFile.type,
							url: itemFileURL,
							uuid: itemFile.uuid,
						};

						link.setData('value', JSON.stringify(imageValue));
					}

					return link;
				},
			},
		});

		A.LiferayItemViewer = LiferayItemViewer;
	},
	'',
	{
		requires: ['aui-component', 'aui-image-viewer', 'liferay-portlet-url'],
	}
);
