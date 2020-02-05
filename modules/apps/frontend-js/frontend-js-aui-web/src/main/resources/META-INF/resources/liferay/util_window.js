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

AUI.add(
	'liferay-util-window',
	A => {
		var DOM = A.DOM;
		var Lang = A.Lang;
		var UA = A.UA;

		var IE = UA.ie;

		var Util = Liferay.Util;
		var Window = Util.Window;

		var IE9 = IE == 9;

		var IE11 = IE == 11;

		var setWidth = function(modal, width) {
			if (IE9) {
				modal.set('width', width + 1);
				modal.set('width', width);
			}
		};

		var LiferayModal = A.Component.create({
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
				},

				toolbars: {
					valueFn() {
						var instance = this;

						return {
							header: [
								{
									cssClass: 'close',
									discardDefaultButtonCssClasses: true,
									labelHTML:
										'<svg class="lexicon-icon" focusable="false"><use data-href="' +
										Liferay.ThemeDisplay.getPathThemeImages() +
										'/lexicon/icons.svg#times" /><title>' +
										Liferay.Language.get('close') +
										'</title></svg>',
									on: {
										click(event) {
											instance.hide();

											event.domEvent.stopPropagation();
										}
									},
									render: true
								}
							]
						};
					}
				}
			},

			EXTENDS: A.Modal,

			NAME: A.Modal.NAME,

			prototype: {}
		});

		A.mix(Window, {
			_bindDOMWinResizeIfNeeded() {
				var instance = this;

				if (!instance._winResizeHandler) {
					instance._winResizeHandler = A.getWin().after(
						'windowresize',
						instance._syncWindowsUI,
						instance
					);
				}
			},

			_bindWindowHooks(modal, config) {
				var instance = this;

				var id = modal.get('id');

				var openingWindow = config.openingWindow;

				var refreshWindow = config.refreshWindow;

				modal._opener = openingWindow;
				modal._refreshWindow = refreshWindow;

				modal.after('destroy', () => {
					if (modal._opener) {
						var openerInFrame = !!modal._opener.frameElement;

						if (openerInFrame) {
							if (IE9) {
								instance._syncWindowsUI();
							}
							else if (IE11) {
								instance._resetFocus(modal);
							}
						}
					}

					instance._unregister(modal);

					modal = null;
				});

				var liferayHandles = modal._liferayHandles;

				liferayHandles.push(
					Liferay.after('hashChange', event => {
						modal.iframe.set('uri', event.uri);
					})
				);

				liferayHandles.push(
					Liferay.after('popupReady', event => {
						var iframeId = id + instance.IFRAME_SUFFIX;

						if (event.windowName === iframeId) {
							event.dialog = modal;
							event.details[0].dialog = modal;

							if (event.doc) {
								Util.afterIframeLoaded(event);

								var modalUtil = event.win.Liferay.Util;

								modalUtil.Window._opener = modal._opener;

								modalUtil.Window._name = id;
							}

							var iframeNode = modal.iframe.node;

							iframeNode.focus();

							if (UA.ios) {
								iframeNode.attr('scrolling', 'no');
							}
						}
					})
				);
			},

			_ensureDefaultId(config) {
				var instance = this;

				if (!Lang.isValue(config.id)) {
					config.id = A.guid();
				}

				if (!config.iframeId) {
					config.iframeId = config.id + instance.IFRAME_SUFFIX;
				}
			},

			_getDialogIframeConfig(config) {
				var dialogIframeConfig;

				var iframeId = config.iframeId;

				var uri = config.uri;

				if (uri) {
					if (config.cache === false) {
						uri = Liferay.Util.addParams(
							A.guid() + '=' + Date.now(),
							uri
						);
					}

					var iframeURL = new A.Url(uri);

					var namespace = iframeURL.getParameter('p_p_id');

					var bodyCssClass = ['dialog-iframe-popup'];

					if (
						config.dialogIframe &&
						config.dialogIframe.bodyCssClass
					) {
						bodyCssClass.push(config.dialogIframe.bodyCssClass);
					}

					iframeURL.addParameter(
						'_' + namespace + '_bodyCssClass',
						bodyCssClass.join(' ')
					);

					uri = iframeURL.toString();

					var defaultDialogIframeConfig = {
						bodyCssClass: ''
					};

					dialogIframeConfig = A.merge(
						defaultDialogIframeConfig,
						config.dialogIframe,
						{
							bindLoadHandler() {
								var instance = this;

								var modal = instance.get('host');

								var popupReady = false;

								var liferayHandles = modal._liferayHandles;

								liferayHandles.push(
									Liferay.on('popupReady', event => {
										instance.fire('load', event);

										popupReady = true;
									})
								);

								liferayHandles.push(
									instance.node.on('load', () => {
										if (!popupReady) {
											Liferay.fire('popupReady', {
												windowName: iframeId
											});
										}

										popupReady = false;
									})
								);
							},

							iframeId,
							iframeTitle: config.title || '',
							uri
						}
					);
				}

				return dialogIframeConfig;
			},

			_getWindow(config) {
				var instance = this;

				var id = config.id;

				var modalConfig = instance._getWindowConfig(config);

				var dialogIframeConfig = instance._getDialogIframeConfig(
					config
				);

				var modal = instance.getById(id);

				if (!modal) {
					var titleNode = A.Node.create(instance.TITLE_TEMPLATE);

					if (config.stack !== false) {
						A.mix(modalConfig, {
							plugins: [Liferay.WidgetZIndex]
						});
					}

					modal = new LiferayModal(
						A.merge(
							{
								cssClass: 'modal-full-screen',
								headerContent: titleNode,
								id
							},
							modalConfig
						)
					);

					Liferay.once('screenLoad', () => {
						modal.destroy();
					});

					modal.titleNode = titleNode;

					instance._register(modal);

					instance._bindWindowHooks(modal, config);
				}
				else {
					if (!config.zIndex && modal.hasPlugin('zindex')) {
						delete modalConfig.zIndex;
					}

					var openingWindow = config.openingWindow;

					modal._opener = openingWindow;
					modal._refreshWindow = config.refreshWindow;

					instance._map[id]._opener = openingWindow;

					modal.setAttrs(modalConfig);
				}

				if (dialogIframeConfig) {
					modal.iframeConfig = dialogIframeConfig;
					modal.plug(A.Plugin.DialogIframe, dialogIframeConfig);

					// LPS-93620

					var originalFn = modal.iframe._onLoadIframe;

					modal.iframe._onLoadIframe = function() {
						try {
							originalFn.call(this);
						}
						catch (err) {}
					};

					modal.get('boundingBox').addClass('dialog-iframe-modal');
				}

				if (!Lang.isValue(config.title)) {
					config.title = '&nbsp;';
				}

				modal.titleNode.html(config.title);

				modal.fillHeight(modal.bodyNode);

				return modal;
			},

			_getWindowConfig(config) {
				var instance = this;

				var modalConfig = A.merge(instance.DEFAULTS, config.dialog);

				var height = modalConfig.height;
				var width = modalConfig.width;

				if (
					height === 'auto' ||
					height === '' ||
					height === undefined ||
					height > DOM.winHeight()
				) {
					modalConfig.autoHeight = true;
				}

				if (
					width === 'auto' ||
					width === '' ||
					width === undefined ||
					width > DOM.winWidth()
				) {
					modalConfig.autoWidth = true;
				}

				modalConfig.id = config.id;

				return modalConfig;
			},

			_register(modal) {
				var instance = this;

				var id = modal.get('id');

				modal._liferayHandles = [];

				instance._map[id] = modal;
				instance._map[id + instance.IFRAME_SUFFIX] = modal;
			},

			_resetFocus(modal) {
				var contentBox = modal.get('contentBox');

				var input = contentBox.one('input[type=text]');

				if (input) {
					input.getDOM().focus();
				}
			},

			_setWindowDefaultSizeIfNeeded(modal) {
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

					if (modal.get('height') === 'auto') {
						modal._fillMaxHeight(height);
					}
					else {
						modal.set('height', height);
					}
				}

				var widthInitial = modal.get('width');

				if (widthInitial !== 'auto') {
					if (modal.get('autoWidth')) {
						var width;

						if (autoSizeNode) {
							width = autoSizeNode.get('offsetWidth');
						}
						else {
							width = DOM.winWidth();
						}

						width *= modal.get('autoWidthRatio');

						if (width != widthInitial) {
							modal.set('width', width);
						}
						else {
							setWidth(modal, widthInitial);
						}
					}
					else {
						setWidth(modal, modal.get('width'));
					}
				}
			},

			_syncWindowsUI() {
				var instance = this;

				var modals = instance._map;

				A.each(modals, modal => {
					if (modal.get('visible')) {
						instance._setWindowDefaultSizeIfNeeded(modal);

						modal.align();
					}
				});
			},

			_unregister(modal) {
				var instance = this;

				var id = modal.get('id');

				delete instance._map[id];
				delete instance._map[id + instance.IFRAME_SUFFIX];

				A.Array.invoke(modal._liferayHandles, 'detach');
			},

			_winResizeHandler: null,

			DEFAULTS: {
				centered: true,
				modal: true,
				visible: true,
				zIndex: Liferay.zIndex.WINDOW
			},

			IFRAME_SUFFIX: '_iframe_',

			TITLE_TEMPLATE: '<h3 class="modal-title" />',

			getByChild(child) {
				var node = A.one(child).ancestor('.modal', true);

				return A.Widget.getByNode(node);
			},

			getWindow(config) {
				var instance = this;

				instance._ensureDefaultId(config);

				var modal = instance._getWindow(config);

				instance._bindDOMWinResizeIfNeeded();

				modal.render();

				instance._setWindowDefaultSizeIfNeeded(modal);

				// LPS-106470 resize modal mask

				var mask = modal.get('maskNode');

				if (mask.getStyle('position') == 'absolute') {
					mask.setStyle('height', '100%');
					mask.setStyle('width', '100%');
				}

				modal.align();

				return modal;
			},

			hideByChild(child) {
				var instance = this;

				return instance.getByChild(child).hide();
			},

			refreshByChild(child) {
				var instance = this;

				var dialog = instance.getByChild(child);

				if (dialog && dialog.io) {
					dialog.io.start();
				}
			}
		});
	},
	'',
	{
		requires: [
			'aui-dialog-iframe-deprecated',
			'aui-modal',
			'aui-url',
			'event-resize',
			'liferay-widget-zindex'
		]
	}
);
