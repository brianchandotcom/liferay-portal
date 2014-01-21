AUI.add(
	'liferay-util-window',
	function(A) {
		var Lang = A.Lang;

		var DOM = A.DOM;

		var Util = Liferay.Util;
		var Window = Util.Window;

		var LiferayModal = A.Component.create(
			{
				NAME: A.Modal.NAME,

				ATTRS: {
					autoHeight: {
						value: false
					},

					autoHeightRatio: {
						value: 0.95
					},

					autoSizeNode: {
						setter: A.one
					},

					autoWidth: {
						value: false
					},

					autoWidthRatio: {
						value: 0.95
					}
				},

				EXTENDS: A.Modal,

				prototype: {
				}
			}
		);

		var MAP_DIR = Liferay.Language.direction;

		var DIR = (MAP_DIR && MAP_DIR[themeDisplay.getLanguageId()]) || 'ltr';

		var RESIZE_HANDLES = {
			ltr: 'br',
			rtl: 'bl'
		};

		A.mix(
			Window,
			{
				DEFAULTS: {
					centered: true,
					headerContent: '&nbsp;',
					modal: true,
					resizable: {
						handles: RESIZE_HANDLES[DIR]
					},
					visible: true,
					zIndex: Liferay.zIndex.WINDOW
				},

				IFRAME_SUFFIX: '_iframe_',

				TITLE_TEMPLATE: '<h3 />',

				_winResizeHandler: null,

				getByChild: function(child) {
					var instance = this;

					var node = A.one(child).ancestor('.modal', true);

					return A.Widget.getByNode(node);
				},

				getById: function(id) {
					var instance = this;

					return instance._map[id];
				},

				getWindow: function(config) {
					var instance = this;

					instance._ensureDefaultId(config);

					var modal = instance._getWindow(config);

					instance._setWindowDefaultSizeIfNeeded(modal);

					instance._bindDOMWinResizeIfNeeded();

					modal.render();

					return modal;
				},

				hideByChild: function(child) {
					var instance = this;

					return instance.getByChild(child).hide();
				},

				refreshByChild: function(child) {
					var instance = this;

					var dialog = instance.getByChild(child);

					if (dialog && dialog.io) {
						dialog.io.start();
					}
				},

				_bindDOMWinResizeIfNeeded: function() {
					var instance = this;

					if (!instance._winResizeHandler) {
						instance._winResizeHandler = A.getWin().after('windowresize', instance._syncWindowsUI, instance);
					}
				},

				_bindWindowHooks: function(modal, config) {
					var instance = this;

					var id = modal.get('id');

					var openingWindow = config.openingWindow;

					var refreshWindow = config.refreshWindow;

					modal._opener = openingWindow;
					modal._refreshWindow = refreshWindow;

					modal.after(
						'destroy',
						function(event) {
							instance._unregister(modal);

							modal = null;
						}
					);

					var liferayHandles = modal._liferayHandles;

					liferayHandles.push(
						Liferay.after(
							'hashChange',
							function(event) {
								modal.iframe.set('uri', event.uri);
							}
						)
					);

					liferayHandles.push(
						Liferay.after(
							'popupReady',
							function(event) {
								var iframeId = id + instance.IFRAME_SUFFIX;

								if (event.windowName === iframeId) {
									event.dialog = modal;
									event.details[0].dialog = modal;

									if (event.doc) {
										Util.afterIframeLoaded(event);

										var modalUtil = event.win.Liferay.Util;

										modalUtil.Window._opener = openingWindow;

										modalUtil.Window._name = id;
									}

									modal.iframe.node.focus();
								}
							}
						)
					);
				},

				_ensureDefaultId: function(config) {
					var instance = this;

					if (!Lang.isValue(config.id)) {
						config.id = A.guid();
					}

					if (!config.iframeId) {
						config.iframeId = config.id + instance.IFRAME_SUFFIX;
					}
				},

				_getWindow: function(config) {
					var instance = this;

					var id = config.id;

					var modalConfig = instance._getWindowConfig(config);

					var dialogIframeConfig = instance._getDialogIframeConfig(config);

					var modal = instance.getById(id);

					if (!modal) {
						var titleNode = A.Node.create(instance.TITLE_TEMPLATE);

						if (config.stack !== false) {
							A.mix(
								modalConfig,
								{
									plugins: [Liferay.WidgetZIndex]
								}
							);
						}

						modal = new LiferayModal(
							A.merge(
								{
									headerContent: titleNode,
									id: id
								},
								modalConfig
							)
						);

						modal.titleNode = titleNode;

						instance._register(modal);

						instance._bindWindowHooks(modal, config);
					}
					else {
						if (!config.zIndex && modal.hasPlugin('zindex')) {
							delete modalConfig.zIndex;
						}

						modal.setAttrs(modalConfig);
					}

					if (dialogIframeConfig) {
						modal.plug(A.Plugin.DialogIframe, dialogIframeConfig);
					}

					if (!Lang.isValue(config.title)) {
						config.title = instance.DEFAULTS.headerContent;
					}

					modal.titleNode.html(config.title);

					modal.fillHeight(modal.bodyNode);

					return modal;
				},

				_getWindowConfig: function(config) {
					var instance = this;

					var modalConfig = A.merge(instance.DEFAULTS, config.dialog);

					var height = modalConfig.height;

					var width = modalConfig.width;

					if (height === 'auto' || height === '' || height === undefined || height > DOM.winHeight()) {
						modalConfig.autoHeight = true;
					}

					if (width === 'auto' || width === '' || width === undefined || width > DOM.winWidth()) {
						modalConfig.autoWidth = true;
					}

					modalConfig.id = config.id;

					delete modalConfig.headerContent;

					return modalConfig;
				},

				_getDialogIframeConfig: function(config) {
					var instance = this;

					var dialogIframeConfig;

					var iframeId = config.iframeId;

					var uri = config.uri;

					if (uri) {
						if (config.cache === false) {
							uri = Liferay.Util.addParams(A.guid() + '=' + Lang.now(), uri);
						}

						dialogIframeConfig = A.merge(
							config.dialogIframe,
							{
								bindLoadHandler: function() {
									var instance = this;

									var modal = instance.get('host');

									var popupReady = false;

									var liferayHandles = modal._liferayHandles;

									liferayHandles.push(
										Liferay.on(
											'popupReady',
											function(event) {
												instance.fire('load', event);

												popupReady = true;
											}
										)
									);

									liferayHandles.push(
										instance.node.on(
											'load',
											function(event) {
												if (!popupReady) {
													Liferay.fire(
														'popupReady',
														{
															windowName: iframeId
														}
													);
												}

												popupReady = false;
											}
										)
									);
								},

								iframeId: iframeId,
								uri: uri
							}
						);
					}

					return dialogIframeConfig;
				},

				_register: function(modal) {
					var instance = this;

					var id = modal.get('id');

					modal._liferayHandles = [];

					instance._map[id] = modal;
					instance._map[id + instance.IFRAME_SUFFIX] = modal;
				},

				_setWindowDefaultSizeIfNeeded: function(modal) {
					var instance = this;

					var autoSizeNode = modal.get('autoSizeNode');

					if (modal.get('autoHeight')) {
						var height;

						if (autoSizeNode) {
							height = autoSizeNode.get('offsetHeight');
						}
						else {
							height = DOM.winHeight();
						}

						height *= modal.get('autoHeightRatio');

						modal.set('height', height);
					}

					if (modal.get('autoWidth')) {
						var width;

						if (autoSizeNode) {
							width = autoSizeNode.get('offsetWidth');
						}
						else {
							width = DOM.winWidth();
						}

						width *= modal.get('autoWidthRatio');

						modal.set('width', width);
					}
				},

				_syncWindowsUI: function() {
					var instance = this;

					var modals = instance._map;

					A.each(
						modals,
						function(modal) {
							if (modal.get('visible')) {
								instance._setWindowDefaultSizeIfNeeded(modal);

								modal.align();
							}
						}
					);
				},

				_unregister: function(modal) {
					var instance = this;

					var id = modal.get('id');

					delete instance._map[id];
					delete instance._map[id + instance.IFRAME_SUFFIX];

					A.Array.invoke(modal._liferayHandles, 'detach');
				}
			}
		);
	},
	'',
	{
		requires: ['aui-dialog-iframe-deprecated', 'aui-modal', 'event-resize', 'liferay-widget-zindex']
	}
);