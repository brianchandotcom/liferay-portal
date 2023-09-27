/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-util-window',
	(A) => {
		const DOM = A.DOM;
		const Lang = A.Lang;
		const UA = A.UA;

		const IE = UA.ie;

		const Util = Liferay.Util;
		const Window = Util.Window;

		const IE9 = IE === 9;

		const IE11 = IE === 11;

		const setWidth = function (modal, width) {
			if (IE9) {
				modal.set('width', width + 1);
				modal.set('width', width);
			}
		};

		const LiferayModal = A.Component.create({
			ATTRS: {
				autoHeight: {
					value: false,
				},

				autoHeightRatio: {
					value: 0.95,
				},

				autoSizeNode: {
					setter: A.one,
				},

				autoWidth: {
					value: false,
				},

				autoWidthRatio: {
					value: 0.95,
				},

				toolbarCssClass: {
					value: {
						footer: 'ml-auto',
						header: 'order-1',
					},
				},

				toolbars: {
					valueFn() {
						const instance = this;

						return {
							header: [
								{
									cssClass: 'close',
									discardDefaultButtonCssClasses: true,
									labelHTML:
										'<svg class="lexicon-icon" focusable="false"><use href="' +
										Liferay.Icons.spritemap +
										'#times" /><title>' +
										Liferay.Language.get('close') +
										'</title></svg>',
									on: {
										click(event) {
											instance.hide();

											event.domEvent.stopPropagation();
										},
									},
									render: true,
								},
							],
						};
					},
				},
			},
			// eslint-disable-next-line @liferay/aui/no-modal
			EXTENDS: A.Modal,
			// eslint-disable-next-line @liferay/aui/no-modal
			NAME: A.Modal.NAME,

			prototype: {},
		});

		A.mix(Window, {
			_bindDOMWinResizeIfNeeded() {
				const instance = this;

				if (!instance._winResizeHandler) {
					instance._winResizeHandler = A.getWin().after(
						'windowresize',
						instance._syncWindowsUI,
						instance
					);
				}
			},

			_bindWindowHooks(modal, config) {
				const instance = this;

				const id = modal.get('id');

				const openingWindow = config.openingWindow;

				const refreshWindow = config.refreshWindow;

				modal._opener = openingWindow;
				modal._refreshWindow = refreshWindow;

				modal.after('destroy', () => {
					if (modal._opener) {
						const openerInFrame = !!modal._opener.frameElement;

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

				const liferayHandles = modal._liferayHandles;

				liferayHandles.push(
					Liferay.after('hashChange', (event) => {
						modal.iframe.set('uri', event.uri);
					})
				);

				liferayHandles.push(
					Liferay.after('popupReady', (event) => {
						const iframeId = id + instance.IFRAME_SUFFIX;

						if (event.windowName === iframeId) {
							event.dialog = modal;
							event.details[0].dialog = modal;

							const iframeNode = modal.iframe.node;

							const iframeElement = iframeNode.getDOM();

							if (event.doc) {
								const modalUtil = event.win.Liferay.Util;

								modalUtil.Window._opener = modal._opener;

								modalUtil.Window._name = id;

								iframeElement.onload = function () {
									Util.afterIframeLoaded(event);
								};
							}

							iframeNode.focus();
						}
					})
				);
			},

			_ensureDefaultId(config) {
				const instance = this;

				if (!Lang.isValue(config.id)) {
					config.id = A.guid();
				}

				if (!config.iframeId) {
					config.iframeId = config.id + instance.IFRAME_SUFFIX;
				}
			},

			_getDialogIframeConfig(config) {
				let dialogIframeConfig;

				const iframeId = config.iframeId;

				let uri = config.uri;

				if (uri) {
					if (config.cache === false) {
						uri = Liferay.Util.addParams(
							A.guid() + '=' + Date.now(),
							uri
						);
					}

					const iframeURL = new URL(uri);

					const namespace = iframeURL.searchParams.get('p_p_id');

					const bodyCssClass = ['dialog-iframe-popup'];

					if (
						config.dialogIframe &&
						config.dialogIframe.bodyCssClass
					) {
						bodyCssClass.push(config.dialogIframe.bodyCssClass);
					}

					iframeURL.searchParams.set(
						`_${namespace}_bodyCssClass`,
						bodyCssClass.join(' ')
					);

					uri = iframeURL.toString();

					const defaultDialogIframeConfig = {
						bodyCssClass: '',
					};

					dialogIframeConfig = {
						...defaultDialogIframeConfig,
						...config.dialogIframe,
						bindLoadHandler() {
							const instance = this;

							const modal = instance.get('host');

							let popupReady = false;

							const liferayHandles = modal._liferayHandles;

							liferayHandles.push(
								Liferay.on('popupReady', (event) => {
									instance.fire('load', event);

									popupReady = true;
								})
							);

							liferayHandles.push(
								instance.node.on('load', () => {
									if (!popupReady) {
										Liferay.fire('popupReady', {
											windowName: iframeId,
										});
									}

									popupReady = false;
								})
							);
						},

						iframeId,
						iframeTitle: config.title || '',
						uri,
					};
				}

				return dialogIframeConfig;
			},

			_getWindow(config) {
				const instance = this;

				const id = config.id;

				const modalConfig = instance._getWindowConfig(config);

				const dialogIframeConfig = instance._getDialogIframeConfig(
					config
				);

				let modal = instance.getById(id);

				if (!modal) {
					const titleNode = A.Node.create(instance.TITLE_TEMPLATE);

					if (config.stack !== false) {
						A.mix(modalConfig, {
							plugins: [Liferay.WidgetZIndex],
						});
					}

					modal = new LiferayModal({
						cssClass: 'modal-full-screen',
						headerContent: titleNode,
						id,
						...modalConfig,
					});

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

					const openingWindow = config.openingWindow;

					modal._opener = openingWindow;
					modal._refreshWindow = config.refreshWindow;

					instance._map[id]._opener = openingWindow;

					modal.setAttrs(modalConfig);
				}

				if (dialogIframeConfig) {
					modal.iframeConfig = dialogIframeConfig;
					modal.plug(A.Plugin.DialogIframe, dialogIframeConfig);

					// LPS-93620

					const originalFn = modal.iframe._onLoadIframe;

					modal.iframe._onLoadIframe = function () {
						try {
							originalFn.call(this);
						}
						catch (error) {}
					};

					const boundingBox = modal.get('boundingBox');

					boundingBox.addClass('cadmin');
					boundingBox.addClass('dialog-iframe-modal');
					boundingBox.addClass('modal');
					boundingBox.addClass('show');
				}

				if (!Lang.isValue(config.title)) {
					config.title = '';
				}

				modal.titleNode.html(Lang.String.escapeHTML(config.title));

				modal.fillHeight(modal.bodyNode);

				return modal;
			},

			_getWindowConfig(config) {
				const instance = this;

				const modalConfig = {
					...instance.DEFAULTS,
					...config.dialog,
				};

				const height = modalConfig.height;
				const width = modalConfig.width;

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
				const instance = this;

				const id = modal.get('id');

				modal._liferayHandles = [];

				instance._map[id] = modal;
				instance._map[id + instance.IFRAME_SUFFIX] = modal;
			},

			_resetFocus(modal) {
				const contentBox = modal.get('contentBox');

				const input = contentBox.one('input[type=text]');

				if (input) {
					input.getDOM().focus();
				}
			},

			_setWindowDefaultSizeIfNeeded(modal) {
				const autoSizeNode = modal.get('autoSizeNode');

				if (modal.get('autoHeight')) {
					let height;

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

				const widthInitial = modal.get('width');

				if (widthInitial !== 'auto') {
					if (modal.get('autoWidth')) {
						let width;

						if (autoSizeNode) {
							width = autoSizeNode.get('offsetWidth');
						}
						else {
							width = DOM.winWidth();
						}

						width *= modal.get('autoWidthRatio');

						if (width !== widthInitial) {
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
				const instance = this;

				const modals = instance._map;

				A.each(modals, (modal) => {
					if (modal.get('visible')) {
						instance._setWindowDefaultSizeIfNeeded(modal);

						modal.align();
					}
				});
			},

			_unregister(modal) {
				const instance = this;

				const id = modal.get('id');

				delete instance._map[id];
				delete instance._map[id + instance.IFRAME_SUFFIX];

				A.Array.invoke(modal._liferayHandles, 'detach');
			},

			_winResizeHandler: null,

			DEFAULTS: {
				centered: true,
				modal: true,
				visible: true,
				zIndex: Liferay.zIndex.WINDOW,
			},

			IFRAME_SUFFIX: '_iframe_',

			TITLE_TEMPLATE: '<div class="modal-title" />',

			getByChild(child) {
				const node = A.one(child).ancestor('.modal', true);

				return A.Widget.getByNode(node);
			},

			getWindow(config) {
				const instance = this;

				instance._ensureDefaultId(config);

				const modal = instance._getWindow(config);

				instance._bindDOMWinResizeIfNeeded();

				modal.render();

				instance._setWindowDefaultSizeIfNeeded(modal);

				// LPS-106470, LPS-109906 resize modal mask

				const mask = modal.get('maskNode');

				if (mask.getStyle('position') === 'absolute') {
					mask.setStyle('height', '100%');
					mask.setStyle(
						'top',
						document.documentElement.scrollTop + 'px'
					);
					mask.setStyle('width', '100%');
				}

				modal.align();

				return modal;
			},

			hideByChild(child) {
				const instance = this;

				return instance.getByChild(child).hide();
			},

			refreshByChild(child) {
				const instance = this;

				const dialog = instance.getByChild(child);

				if (dialog && dialog.io) {
					dialog.io.start();
				}
			},
		});
	},
	'',
	{
		requires: [
			'aui-dialog-iframe-deprecated',
			'aui-modal',
			'aui-url',
			'event-resize',
			'liferay-widget-zindex',
		],
	}
);
