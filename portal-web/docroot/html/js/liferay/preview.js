AUI.add(
	'liferay-preview',
	function(A) {
		var Lang = A.Lang;

		var ATTR_DATA_IMAGE_INDEX = 'data-imageIndex';

		var BUFFER = [];

		var CSS_IMAGE_SELECTED = 'lfr-preview-file-image-selected';

		var STR_SCROLLER = 'scroller';

		var MAP_EVENT_SCROLLER = {
			src: STR_SCROLLER
		};

		var MAP_IMAGE_DATA = {};

		var TPL_IMAGES = '<a class="lfr-preview-file-image {selectedCssClass}" data-imageIndex="{index}" href="{url}" title="{displayedIndex}"><img src="{url}" /></a>';

		var TPL_LOADING_COUNT = '<span class="lfr-preview-file-loading-count"></span>';

		var TPL_LOADING_INDICATOR = '<div class="lfr-preview-file-loading-indicator hide">{0}&nbsp;</div>';

		var TPL_MAX_ARROW_LEFT = '<a href="javascript:;" class="image-viewer-control carousel-control left lfr-preview-file-arrow">‹</a>';

		var TPL_MAX_ARROW_RIGHT = '<a href="javascript:;" class="image-viewer-control carousel-control right lfr-preview-file-arrow">›</a>';

		var TPL_MAX_CONTROLS = '<span class="lfr-preview-file-image-overlay-controls"></span>';

		var Preview = A.Component.create(
			{
				NAME: 'liferaypreview',
				ATTRS: {
					currentIndex: {
						value: 0,
						setter: '_setCurrentIndex'
					},
					activeThumb: {
						value: null
					},
					actionContent: {
						setter: A.one
					},
					maxIndex: {
						value: 0,
						validator: Lang.isNumber
					},
					baseImageURL: {
						value: null
					},
					imageListContent: {
						setter: A.one
					},
					toolbar: {
						setter: A.one
					},
					currentPreviewImage: {
						setter: A.one
					},
					previewFileIndexNode: {
						setter: A.one
					}
				},
				prototype: {
					initializer: function() {
						var instance = this;

						instance._actionContent = instance.get('actionContent');
						instance._baseImageURL = instance.get('baseImageURL');
						instance._currentPreviewImage = instance.get('currentPreviewImage');
						instance._previewFileIndexNode = instance.get('previewFileIndexNode');
						instance._imageListContent = instance.get('imageListContent');

						instance._hideLoadingIndicator = A.debounce(
							function() {
								instance._getLoadingIndicator().hide();
							},
							250
						);
					},

					renderUI: function() {
						var instance = this;

						instance._renderToolbar();
						instance._renderImages();

						instance._actionContent.show();
					},

					bindUI: function() {
						var instance = this;

						instance.after('currentIndexChange', instance._afterCurrentIndexChange);

						var imageListContent = instance._imageListContent;

						imageListContent.delegate('mouseenter', instance._onImageListMouseEnter, 'a', instance);
						imageListContent.delegate('click', instance._onImageListClick, 'a', instance);

						imageListContent.on('scroll', instance._onImageListScroll, instance);
					},

					_afterCurrentIndexChange: function(event) {
						var instance = this;

						instance._uiSetCurrentIndex(event.newVal, event.src, event.prevVal);
					},

					_onImageListClick: function(event) {
						var instance = this;

						event.preventDefault();

						var previewImage = event.currentTarget;

						var imageIndex = previewImage.attr(ATTR_DATA_IMAGE_INDEX);

						instance.set('currentIndex', imageIndex, {src: 'scroller'});
					},

					_onImageListMouseEnter: function(event) {
						var instance = this;

						event.preventDefault();

						var previewImage = event.currentTarget;

						var imageIndex = previewImage.attr(ATTR_DATA_IMAGE_INDEX);

						instance.set('currentIndex', imageIndex, MAP_EVENT_SCROLLER);
					},

					_onImageListScroll: function(event) {
						var instance = this;

						var imageListContentEl = instance._imageListContent.getDOM();

						var maxIndex = instance.get('maxIndex');

						var previewFileCountDown = instance._previewFileCountDown;

						if (previewFileCountDown < maxIndex && imageListContentEl.scrollTop >= (imageListContentEl.scrollHeight - 700)) {
							var loadingIndicator = instance._getLoadingIndicator();

							if (loadingIndicator.hasClass('hide')) {
								var end = Math.min(maxIndex, previewFileCountDown + 10);
								var start = Math.max(0, previewFileCountDown + 1);

								instance._getLoadingCountNode().html(start + ' - ' + end);

								loadingIndicator.show();

								setTimeout(
									function() {
										instance._renderImages(maxIndex);
									},
									350
								);
							}
						}
					},

					_maximizePreview: function(event) {
						var instance = this;

						instance._getMaxOverlay().show();
					},

					_getLoadingCountNode: function() {
						var instance = this;

						var loadingCountNode = instance._loadingCountNode;

						if (!loadingCountNode) {
							loadingCountNode = A.Node.create(TPL_LOADING_COUNT);

							instance._loadingCountNode = loadingCountNode;
						}

						return loadingCountNode;
					},

					_getLoadingIndicator: function() {
						var instance = this;

						var loadingIndicator = instance._loadingIndicator;

						if (!loadingIndicator) {
							loadingIndicator = A.Node.create(A.Lang.sub(TPL_LOADING_INDICATOR, [Liferay.Language.get('loading')]));

							loadingIndicator.append(instance._getLoadingCountNode());

							instance._imageListContent.get('parentNode').append(loadingIndicator);

							instance._loadingIndicator = loadingIndicator;
						}

						return loadingIndicator;
					},

					_getMaxPreviewControls: function() {
						var instance = this;

						var maxPreviewControls = instance._maxPreviewControls;

						if (!maxPreviewControls) {
							var arrowLeft = A.Node.create(TPL_MAX_ARROW_LEFT);
							var arrowRight = A.Node.create(TPL_MAX_ARROW_RIGHT);

							maxPreviewControls = A.Node.create(TPL_MAX_CONTROLS);

							maxPreviewControls.append(arrowLeft);
							maxPreviewControls.append(arrowRight);
							
							maxPreviewControls.delegate('click', instance._onMaxPreviewControlsClick, '.lfr-preview-file-arrow', instance);

							instance._maxPreviewControls = maxPreviewControls;
						}

						return maxPreviewControls;
					},

					_getMaxPreviewImage: function() {
						var instance = this;

						var maxPreviewImage = instance._maxPreviewImage;

						if (!maxPreviewImage) {
							maxPreviewImage = instance._currentPreviewImage.clone().removeClass('lfr-preview-file-image-current');

							instance._maxPreviewImage = maxPreviewImage;
						}

						return maxPreviewImage;
					},

					_getMaxOverlayMask: function() {
						var instance = this;

						var maxOverlayMask = instance._maxOverlayMask;

						if (!maxOverlayMask) {
							maxOverlayMask = new A.OverlayMask(
								{
									visible: true
								}
							);

							instance._maxOverlayMask = maxOverlayMask;
						}

						return maxOverlayMask;
					},

					_getMaxOverlay: function() {
						var instance = this;

						var maxOverlay = instance._maxOverlay;

						if (!maxOverlay) {
							var maxOverlayMask = instance._getMaxOverlayMask();

							maxOverlay = new A.Modal(
								{
									after: {
										render: function(event) {
											maxOverlayMask.render();
										},
										visibleChange: function(event) {
											maxOverlayMask.set('visible', event.newVal);
										}
									},
									centered: true,
									cssClass: 'lfr-preview-file-image-overlay',
									height: '90%',
									width: '85%',
									zIndex: 1005
								}
							).render();

							maxOverlay.getStdModNode(A.WidgetStdMod.BODY).append(instance._getMaxPreviewImage());

							maxOverlay.get('boundingBox').append(instance._getMaxPreviewControls());

							instance._maxOverlay = maxOverlay;
						}

						return maxOverlay;
					},

					_onMaxPreviewControlsClick: function(event) {
						var instance = this;

						var target = event.currentTarget;

						var maxOverlay = instance._getMaxOverlay();

						if (target.hasClass('lfr-preview-file-arrow')) {
							if (target.hasClass('right')) {
								instance._updateIndex(1);
							}
							else if (target.hasClass('left')) {
								instance._updateIndex(-1);
							}

							instance._getMaxPreviewImage().attr('src', instance._baseImageURL + (instance.get('currentIndex') + 1));
						}
					},

					_renderImages: function(maxIndex) {
						var instance = this;

						var i = 0;
						var previewFileCountDown = instance._previewFileCountDown;
						var displayedIndex;

						var currentIndex = instance.get('currentIndex');

						maxIndex = maxIndex || instance.get('maxIndex');

						var baseImageURL = instance._baseImageURL;

						while (instance._previewFileCountDown < maxIndex && i++ < 10) {
							displayedIndex = previewFileCountDown + 1;

							MAP_IMAGE_DATA.displayedIndex = displayedIndex;
							MAP_IMAGE_DATA.selectedCssClass = (previewFileCountDown == currentIndex ? CSS_IMAGE_SELECTED : '');
							MAP_IMAGE_DATA.index = previewFileCountDown;
							MAP_IMAGE_DATA.url = baseImageURL + displayedIndex;

							BUFFER[BUFFER.length] = Lang.sub(TPL_IMAGES, MAP_IMAGE_DATA);

							previewFileCountDown = ++instance._previewFileCountDown;
						}

						if (BUFFER.length) {
							var nodeList = A.NodeList.create(BUFFER.join(''));

							if (!instance._nodeList) {
								instance._nodeList = nodeList;
							}
							else {
								instance._nodeList = instance._nodeList.concat(nodeList);
							}

							instance._imageListContent.append(nodeList);

							BUFFER.length = 0;
						}

						instance._hideLoadingIndicator();
					},

					_renderToolbar: function() {
						var instance = this;

						instance._toolbar = new A.Toolbar(
							{
								boundingBox: instance.get('toolbar'),
								children: [
									[
										{
											icon: 'icon-circle-arrow-left',
											on: {
												click: A.bind('_updateIndex', instance, -1)
											}
										},
										{
											icon: 'icon-zoom-in',
											on: {
												click: A.bind('_maximizePreview', instance)
											}
										},
										{
											icon: 'icon-circle-arrow-right',
											on: {
												click: A.bind('_updateIndex', instance, 1)
											}
										}
									]
								]
							}
						).render();
					},

					_setCurrentIndex: function(value) {
						var instance = this;

						value = parseInt(value, 10);

						if (isNaN(value)) {
							value = A.Attribute.INVALID_VALUE;
						}
						else {
							value = Math.min(Math.max(value, 0), instance.get('maxIndex') - 1);
						}

						return value;
					},

					_updateIndex: function(increment) {
						var instance = this;

						var currentIndex = instance.get('currentIndex');

						currentIndex += increment;

						instance.set('currentIndex', currentIndex);
					},

					_uiSetCurrentIndex: function(value, src, prevVal) {
						var instance = this;

						var displayedIndex = value + 1;

						instance._currentPreviewImage.attr('src', instance._baseImageURL + displayedIndex);
						instance._previewFileIndexNode.setContent(displayedIndex);

						var nodeList = instance._nodeList;

						var prevItem = nodeList.item(prevVal || 0);

						if (prevItem) {
							prevItem.removeClass(CSS_IMAGE_SELECTED);
						}

						if (src != STR_SCROLLER) {
							var newItem = nodeList.item(value);

							if (newItem) {
								instance._imageListContent.set('scrollTop', newItem.get('offsetTop'));

								newItem.addClass(CSS_IMAGE_SELECTED);
							}
						}
					},

					_previewFileCountDown: 0
				}
			}
		);

		Liferay.Preview = Preview;
	},
	'',
	{
		requires: ['aui-base', 'aui-modal', 'aui-overlay-mask-deprecated', 'aui-toolbar']
	}
);