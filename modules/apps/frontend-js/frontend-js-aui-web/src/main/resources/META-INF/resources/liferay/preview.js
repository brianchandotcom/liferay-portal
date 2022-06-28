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
 * The Preview Component.
 *
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 * @module liferay-preview
 */

AUI.add(
	'liferay-preview',
	(A) => {
		const Lang = A.Lang;

		const ATTR_DATA_IMAGE_INDEX = 'data-imageIndex';

		const BUFFER = [];

		const CSS_IMAGE_SELECTED = 'lfr-preview-file-image-selected';

		const MAP_IMAGE_DATA = {};

		const STR_CLICK = 'click';

		const STR_CURRENT_INDEX = 'currentIndex';

		const STR_MAX_INDEX = 'maxIndex';

		const STR_SCROLLER = 'scroller';

		const STR_SRC = 'src';

		const TPL_IMAGES =
			'<a class="lfr-preview-file-image {selectedCssClass}" data-imageIndex="{index}" href="{url}" title="{displayedIndex}">' +
			'<img src="{url}" />' +
			'</a>';

		const TPL_LOADING_COUNT =
			'<span class="lfr-preview-file-loading-count"></span>';

		const TPL_LOADING_INDICATOR =
			'<div class="lfr-preview-file-loading-indicator hide">{0}&nbsp;</div>';

		const TPL_MAX_ARROW_LEFT =
			'<a href="javascript:void(0);" class="image-viewer-control carousel-control carousel-control-prev left lfr-preview-file-arrow">' +
			Liferay.Util.getLexiconIconTpl('angle-left') +
			'</a>';

		const TPL_MAX_ARROW_RIGHT =
			'<a href="javascript:void(0);" class="image-viewer-control carousel-control carousel-control-next right lfr-preview-file-arrow">' +
			Liferay.Util.getLexiconIconTpl('angle-right') +
			'</a>';

		const TPL_MAX_CONTROLS =
			'<span class="lfr-preview-file-image-overlay-controls"></span>';

		const MAP_EVENT_SCROLLER = {
			src: STR_SCROLLER,
		};

		const Preview = A.Component.create({
			ATTRS: {
				actionContent: {
					setter: A.one,
				},
				activeThumb: {
					value: null,
				},
				baseImageURL: {
					value: null,
				},
				currentIndex: {
					setter: '_setCurrentIndex',
					value: 0,
				},
				currentPreviewImage: {
					setter: A.one,
				},
				imageListContent: {
					setter: A.one,
				},
				maxIndex: {
					validator: Lang.isNumber,
					value: 0,
				},
				previewFileIndexNode: {
					setter: A.one,
				},
				toolbar: {
					setter: A.one,
				},
			},

			NAME: 'liferaypreview',

			prototype: {
				_afterCurrentIndexChange(event) {
					const instance = this;

					instance._uiSetCurrentIndex(
						event.newVal,
						event.src,
						event.prevVal
					);
				},

				_getLoadingCountNode() {
					const instance = this;

					let loadingCountNode = instance._loadingCountNode;

					if (!loadingCountNode) {
						loadingCountNode = A.Node.create(TPL_LOADING_COUNT);

						instance._loadingCountNode = loadingCountNode;
					}

					return loadingCountNode;
				},

				_getLoadingIndicator() {
					const instance = this;

					let loadingIndicator = instance._loadingIndicator;

					if (!loadingIndicator) {
						loadingIndicator = A.Node.create(
							A.Lang.sub(TPL_LOADING_INDICATOR, [
								Liferay.Language.get('loading'),
							])
						);

						loadingIndicator.append(
							instance._getLoadingCountNode()
						);

						instance._imageListContent
							.get('parentNode')
							.append(loadingIndicator);

						instance._loadingIndicator = loadingIndicator;
					}

					return loadingIndicator;
				},

				_getMaxOverlay() {
					const instance = this;

					let maxOverlay = instance._maxOverlay;

					if (!maxOverlay) {
						const maxOverlayMask = instance._getMaxOverlayMask();
						// eslint-disable-next-line @liferay/aui/no-modal
						maxOverlay = new A.Modal({
							after: {
								render() {
									maxOverlayMask.render();
								},
								visibleChange(event) {
									maxOverlayMask.set('visible', event.newVal);
								},
							},
							centered: true,
							cssClass: 'lfr-preview-file-image-overlay',
							height: '90vh',
							plugins: [Liferay.WidgetZIndex],
							width: '85vw',
						}).render();

						maxOverlay
							.getStdModNode(A.WidgetStdMod.BODY)
							.append(instance._getMaxPreviewImage());

						maxOverlay
							.get('boundingBox')
							.append(instance._getMaxPreviewControls());

						instance._maxOverlay = maxOverlay;
					}

					return maxOverlay;
				},

				_getMaxOverlayMask() {
					const instance = this;

					let maxOverlayMask = instance._maxOverlayMask;

					if (!maxOverlayMask) {
						maxOverlayMask = new A.OverlayMask({
							visible: true,
						});

						instance._maxOverlayMask = maxOverlayMask;
					}

					return maxOverlayMask;
				},

				_getMaxPreviewControls() {
					const instance = this;

					let maxPreviewControls = instance._maxPreviewControls;

					if (!maxPreviewControls) {
						const arrowLeft = A.Node.create(TPL_MAX_ARROW_LEFT);
						const arrowRight = A.Node.create(TPL_MAX_ARROW_RIGHT);

						maxPreviewControls = A.Node.create(TPL_MAX_CONTROLS);

						maxPreviewControls.append(arrowLeft);
						maxPreviewControls.append(arrowRight);

						maxPreviewControls.delegate(
							STR_CLICK,
							instance._onMaxPreviewControlsClick,
							'.lfr-preview-file-arrow',
							instance
						);

						instance._maxPreviewControls = maxPreviewControls;
					}

					return maxPreviewControls;
				},

				_getMaxPreviewImage() {
					const instance = this;

					let maxPreviewImage = instance._maxPreviewImage;

					if (!maxPreviewImage) {
						maxPreviewImage = instance._currentPreviewImage
							.clone()
							.removeClass('lfr-preview-file-image-current');

						const id = maxPreviewImage.get('id');

						maxPreviewImage.set('id', id + 'Preview');

						instance._maxPreviewImage = maxPreviewImage;
					}

					return maxPreviewImage;
				},

				_maximizePreview() {
					const instance = this;

					instance
						._getMaxPreviewImage()
						.attr(
							STR_SRC,
							instance._baseImageURL +
								(instance.get(STR_CURRENT_INDEX) + 1)
						);

					instance._getMaxOverlay().show();
				},

				_onImageListClick(event) {
					const instance = this;

					event.preventDefault();

					const previewImage = event.currentTarget;

					const imageIndex = previewImage.attr(ATTR_DATA_IMAGE_INDEX);

					instance.set(STR_CURRENT_INDEX, imageIndex, {
						src: 'scroller',
					});
				},

				_onImageListMouseEnter(event) {
					const instance = this;

					event.preventDefault();

					const previewImage = event.currentTarget;

					const imageIndex = previewImage.attr(ATTR_DATA_IMAGE_INDEX);

					instance.set(
						STR_CURRENT_INDEX,
						imageIndex,
						MAP_EVENT_SCROLLER
					);
				},

				_onImageListScroll() {
					const instance = this;

					const imageListContentEl = instance._imageListContent.getDOM();

					const maxIndex = instance.get(STR_MAX_INDEX);

					const previewFileCountDown = instance._previewFileCountDown;

					if (
						previewFileCountDown < maxIndex &&
						imageListContentEl.scrollTop >=
							imageListContentEl.scrollHeight - 700
					) {
						const loadingIndicator = instance._getLoadingIndicator();

						if (loadingIndicator.hasClass('hide')) {
							const end = Math.min(
								maxIndex,
								previewFileCountDown + 10
							);
							const start = Math.max(0, previewFileCountDown + 1);

							instance
								._getLoadingCountNode()
								.html(start + ' - ' + end);

							loadingIndicator.show();

							setTimeout(() => {
								instance._renderImages(maxIndex);
							}, 350);
						}
					}
				},

				_onMaxPreviewControlsClick(event) {
					const instance = this;

					const target = event.currentTarget;

					instance._getMaxOverlay();

					if (target.hasClass('lfr-preview-file-arrow')) {
						if (target.hasClass('right')) {
							instance._updateIndex(1);
						}
						else if (target.hasClass('left')) {
							instance._updateIndex(-1);
						}

						instance
							._getMaxPreviewImage()
							.attr(
								STR_SRC,
								instance._baseImageURL +
									(instance.get(STR_CURRENT_INDEX) + 1)
							);
					}
				},

				_previewFileCountDown: 0,

				_renderImages(maxIndex) {
					const instance = this;

					let i = 0;
					let previewFileCountDown = instance._previewFileCountDown;
					let displayedIndex;

					const currentIndex = instance.get(STR_CURRENT_INDEX);

					maxIndex = maxIndex || instance.get(STR_MAX_INDEX);

					const baseImageURL = instance._baseImageURL;

					while (
						instance._previewFileCountDown < maxIndex &&
						i++ < 10
					) {
						displayedIndex = previewFileCountDown + 1;

						MAP_IMAGE_DATA.displayedIndex = displayedIndex;
						MAP_IMAGE_DATA.selectedCssClass =
							previewFileCountDown === currentIndex
								? CSS_IMAGE_SELECTED
								: '';
						MAP_IMAGE_DATA.index = previewFileCountDown;
						MAP_IMAGE_DATA.url = baseImageURL + displayedIndex;

						BUFFER[BUFFER.length] = Lang.sub(
							TPL_IMAGES,
							MAP_IMAGE_DATA
						);

						previewFileCountDown = ++instance._previewFileCountDown;
					}

					if (BUFFER.length) {
						const nodeList = A.NodeList.create(BUFFER.join(''));

						if (!instance._nodeList) {
							instance._nodeList = nodeList;
						}
						else {
							instance._nodeList = instance._nodeList.concat(
								nodeList
							);
						}

						instance._imageListContent.append(nodeList);

						BUFFER.length = 0;
					}

					instance._hideLoadingIndicator();
				},

				_renderToolbar() {
					const instance = this;

					instance._toolbar = new A.Toolbar({
						boundingBox: instance.get('toolbar'),
						children: [
							[
								{
									icon: 'icon-circle-arrow-left',
									on: {
										click: A.bind(
											'_updateIndex',
											instance,
											-1
										),
									},
								},
								{
									icon: 'icon-zoom-in',
									on: {
										click: A.bind(
											'_maximizePreview',
											instance
										),
									},
								},
								{
									icon: 'icon-circle-arrow-right',
									on: {
										click: A.bind(
											'_updateIndex',
											instance,
											1
										),
									},
								},
							],
						],
					}).render();
				},

				_setCurrentIndex(value) {
					const instance = this;

					value = parseInt(value, 10);

					if (isNaN(value)) {
						value = A.Attribute.INVALID_VALUE;
					}
					else {
						value = Math.min(
							Math.max(value, 0),
							instance.get(STR_MAX_INDEX) - 1
						);
					}

					return value;
				},

				_uiSetCurrentIndex(value, src, prevVal) {
					const instance = this;

					const displayedIndex = value + 1;

					instance._currentPreviewImage.attr(
						STR_SRC,
						instance._baseImageURL + displayedIndex
					);
					instance._previewFileIndexNode.setContent(displayedIndex);

					const nodeList = instance._nodeList;

					const prevItem = nodeList.item(prevVal || 0);

					if (prevItem) {
						prevItem.removeClass(CSS_IMAGE_SELECTED);
					}

					if (src !== STR_SCROLLER) {
						const newItem = nodeList.item(value);

						if (newItem) {
							instance._imageListContent.set(
								'scrollTop',
								newItem.get('offsetTop')
							);

							newItem.addClass(CSS_IMAGE_SELECTED);
						}
					}
				},

				_updateIndex(increment) {
					const instance = this;

					let currentIndex = instance.get(STR_CURRENT_INDEX);

					currentIndex += increment;

					instance.set(STR_CURRENT_INDEX, currentIndex);
				},

				bindUI() {
					const instance = this;

					instance.after(
						'currentIndexChange',
						instance._afterCurrentIndexChange
					);

					const imageListContent = instance._imageListContent;

					imageListContent.delegate(
						'mouseenter',
						instance._onImageListMouseEnter,
						'a',
						instance
					);
					imageListContent.delegate(
						STR_CLICK,
						instance._onImageListClick,
						'a',
						instance
					);

					imageListContent.on(
						'scroll',
						instance._onImageListScroll,
						instance
					);
				},

				initializer() {
					const instance = this;

					instance._actionContent = instance.get('actionContent');
					instance._baseImageURL = instance.get('baseImageURL');
					instance._currentPreviewImage = instance.get(
						'currentPreviewImage'
					);
					instance._previewFileIndexNode = instance.get(
						'previewFileIndexNode'
					);
					instance._imageListContent = instance.get(
						'imageListContent'
					);

					instance._hideLoadingIndicator = A.debounce(() => {
						instance._getLoadingIndicator().hide();
					}, 250);
				},

				renderUI() {
					const instance = this;

					instance._renderToolbar();
					instance._renderImages();

					instance._actionContent.show();
				},
			},
		});

		Liferay.Preview = Preview;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-modal',
			'aui-overlay-mask-deprecated',
			'aui-toolbar',
			'liferay-widget-zindex',
		],
	}
);
