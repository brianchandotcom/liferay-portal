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
 * @deprecated since 7.2, unused
 * @module liferay-preview
 */

AUI.add(
	'liferay-preview',
	A => {
		var Lang = A.Lang;

		var ATTR_DATA_IMAGE_INDEX = 'data-imageIndex';

		var BUFFER = [];

		var CSS_IMAGE_SELECTED = 'lfr-preview-file-image-selected';

		var MAP_IMAGE_DATA = {};

		var STR_CLICK = 'click';

		var STR_CURRENT_INDEX = 'currentIndex';

		var STR_MAX_INDEX = 'maxIndex';

		var STR_SCROLLER = 'scroller';

		var STR_SRC = 'src';

		var TPL_IMAGES =
			'<a class="lfr-preview-file-image {selectedCssClass}" data-imageIndex="{index}" href="{url}" title="{displayedIndex}">' +
			'<img src="{url}" />' +
			'</a>';

		var TPL_LOADING_COUNT =
			'<span class="lfr-preview-file-loading-count"></span>';

		var TPL_LOADING_INDICATOR =
			'<div class="lfr-preview-file-loading-indicator hide">{0}&nbsp;</div>';

		var TPL_MAX_ARROW_LEFT =
			'<a href="javascript:;" class="image-viewer-control carousel-control carousel-control-prev left lfr-preview-file-arrow">' +
			Liferay.Util.getLexiconIconTpl('angle-left') +
			'</a>';

		var TPL_MAX_ARROW_RIGHT =
			'<a href="javascript:;" class="image-viewer-control carousel-control carousel-control-next right lfr-preview-file-arrow">' +
			Liferay.Util.getLexiconIconTpl('angle-right') +
			'</a>';

		var TPL_MAX_CONTROLS =
			'<span class="lfr-preview-file-image-overlay-controls"></span>';

		var MAP_EVENT_SCROLLER = {
			src: STR_SCROLLER
		};

		var Preview = A.Component.create({
			ATTRS: {
				actionContent: {
					setter: A.one
				},
				activeThumb: {
					value: null
				},
				baseImageURL: {
					value: null
				},
				currentIndex: {
					setter: '_setCurrentIndex',
					value: 0
				},
				currentPreviewImage: {
					setter: A.one
				},
				imageListContent: {
					setter: A.one
				},
				maxIndex: {
					validator: Lang.isNumber,
					value: 0
				},
				previewFileIndexNode: {
					setter: A.one
				},
				toolbar: {
					setter: A.one
				}
			},

			NAME: 'liferaypreview',

			prototype: {
				_afterCurrentIndexChange(event) {
					var instance = this;

					instance._uiSetCurrentIndex(
						event.newVal,
						event.src,
						event.prevVal
					);
				},

				_getLoadingCountNode() {
					var instance = this;

					var loadingCountNode = instance._loadingCountNode;

					if (!loadingCountNode) {
						loadingCountNode = A.Node.create(TPL_LOADING_COUNT);

						instance._loadingCountNode = loadingCountNode;
					}

					return loadingCountNode;
				},

				_getLoadingIndicator() {
					var instance = this;

					var loadingIndicator = instance._loadingIndicator;

					if (!loadingIndicator) {
						loadingIndicator = A.Node.create(
							A.Lang.sub(TPL_LOADING_INDICATOR, [
								Liferay.Language.get('loading')
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
					var instance = this;

					var maxOverlay = instance._maxOverlay;

					if (!maxOverlay) {
						var maxOverlayMask = instance._getMaxOverlayMask();

						maxOverlay = new A.Modal({
							after: {
								render() {
									maxOverlayMask.render();
								},
								visibleChange(event) {
									maxOverlayMask.set('visible', event.newVal);
								}
							},
							centered: true,
							cssClass: 'lfr-preview-file-image-overlay',
							height: '90vh',
							plugins: [Liferay.WidgetZIndex],
							width: '85vw'
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
					var instance = this;

					var maxOverlayMask = instance._maxOverlayMask;

					if (!maxOverlayMask) {
						maxOverlayMask = new A.OverlayMask({
							visible: true
						});

						instance._maxOverlayMask = maxOverlayMask;
					}

					return maxOverlayMask;
				},

				_getMaxPreviewControls() {
					var instance = this;

					var maxPreviewControls = instance._maxPreviewControls;

					if (!maxPreviewControls) {
						var arrowLeft = A.Node.create(TPL_MAX_ARROW_LEFT);
						var arrowRight = A.Node.create(TPL_MAX_ARROW_RIGHT);

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
					var instance = this;

					var maxPreviewImage = instance._maxPreviewImage;

					if (!maxPreviewImage) {
						maxPreviewImage = instance._currentPreviewImage
							.clone()
							.removeClass('lfr-preview-file-image-current');

						var id = maxPreviewImage.get('id');

						maxPreviewImage.set('id', id + 'Preview');

						instance._maxPreviewImage = maxPreviewImage;
					}

					return maxPreviewImage;
				},

				_maximizePreview() {
					var instance = this;

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
					var instance = this;

					event.preventDefault();

					var previewImage = event.currentTarget;

					var imageIndex = previewImage.attr(ATTR_DATA_IMAGE_INDEX);

					instance.set(STR_CURRENT_INDEX, imageIndex, {
						src: 'scroller'
					});
				},

				_onImageListMouseEnter(event) {
					var instance = this;

					event.preventDefault();

					var previewImage = event.currentTarget;

					var imageIndex = previewImage.attr(ATTR_DATA_IMAGE_INDEX);

					instance.set(
						STR_CURRENT_INDEX,
						imageIndex,
						MAP_EVENT_SCROLLER
					);
				},

				_onImageListScroll() {
					var instance = this;

					var imageListContentEl = instance._imageListContent.getDOM();

					var maxIndex = instance.get(STR_MAX_INDEX);

					var previewFileCountDown = instance._previewFileCountDown;

					if (
						previewFileCountDown < maxIndex &&
						imageListContentEl.scrollTop >=
							imageListContentEl.scrollHeight - 700
					) {
						var loadingIndicator = instance._getLoadingIndicator();

						if (loadingIndicator.hasClass('hide')) {
							var end = Math.min(
								maxIndex,
								previewFileCountDown + 10
							);
							var start = Math.max(0, previewFileCountDown + 1);

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
					var instance = this;

					var target = event.currentTarget;

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
					var instance = this;

					var i = 0;
					var previewFileCountDown = instance._previewFileCountDown;
					var displayedIndex;

					var currentIndex = instance.get(STR_CURRENT_INDEX);

					maxIndex = maxIndex || instance.get(STR_MAX_INDEX);

					var baseImageURL = instance._baseImageURL;

					while (
						instance._previewFileCountDown < maxIndex &&
						i++ < 10
					) {
						displayedIndex = previewFileCountDown + 1;

						MAP_IMAGE_DATA.displayedIndex = displayedIndex;
						MAP_IMAGE_DATA.selectedCssClass =
							previewFileCountDown == currentIndex
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
						var nodeList = A.NodeList.create(BUFFER.join(''));

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
					var instance = this;

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
										)
									}
								},
								{
									icon: 'icon-zoom-in',
									on: {
										click: A.bind(
											'_maximizePreview',
											instance
										)
									}
								},
								{
									icon: 'icon-circle-arrow-right',
									on: {
										click: A.bind(
											'_updateIndex',
											instance,
											1
										)
									}
								}
							]
						]
					}).render();
				},

				_setCurrentIndex(value) {
					var instance = this;

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
					var instance = this;

					var displayedIndex = value + 1;

					instance._currentPreviewImage.attr(
						STR_SRC,
						instance._baseImageURL + displayedIndex
					);
					instance._previewFileIndexNode.setContent(displayedIndex);

					var nodeList = instance._nodeList;

					var prevItem = nodeList.item(prevVal || 0);

					if (prevItem) {
						prevItem.removeClass(CSS_IMAGE_SELECTED);
					}

					if (src != STR_SCROLLER) {
						var newItem = nodeList.item(value);

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
					var instance = this;

					var currentIndex = instance.get(STR_CURRENT_INDEX);

					currentIndex += increment;

					instance.set(STR_CURRENT_INDEX, currentIndex);
				},

				bindUI() {
					var instance = this;

					instance.after(
						'currentIndexChange',
						instance._afterCurrentIndexChange
					);

					var imageListContent = instance._imageListContent;

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
					var instance = this;

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
					var instance = this;

					instance._renderToolbar();
					instance._renderImages();

					instance._actionContent.show();
				}
			}
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
			'liferay-widget-zindex'
		]
	}
);
