/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable @liferay/aui/no-node */
/* eslint-disable @liferay/aui/no-one */

(function (A) {
	const Lang = A.Lang;

	const Util = Liferay.Util;

	const STR_HEAD = 'head';

	const TPL_NOT_AJAXABLE = '<div class="alert alert-info">{0}</div>';

	const Portlet = {
		...Liferay.Portlet,

		_defCloseFn(event) {
			event.portlet.remove(true);

			if (!event.nestedPortlet) {
				const formData = Liferay.Util.objectToFormData({
					cmd: 'delete',
					doAsUserId: event.doAsUserId,
					p_auth: Liferay.authToken,
					p_l_id: event.plid,
					p_p_id: event.portletId,
					p_v_l_s_g_id: themeDisplay.getSiteGroupId(),
				});

				Liferay.Util.fetch(
					themeDisplay.getPathMain() + '/portal/update_layout',
					{
						body: formData,
						method: 'POST',
					}
				).then((response) => {
					if (response.ok) {
						Liferay.fire('updatedLayout');
					}
				});
			}
		},

		_loadMarkupHeadElements(response) {
			const markupHeadElements = response.markupHeadElements;

			if (markupHeadElements && markupHeadElements.length) {
				const head = A.one(STR_HEAD);

				head.append(markupHeadElements);

				const container = A.Node.create('<div />');

				container.plug(A.Plugin.ParseContent);

				container.setContent(markupHeadElements);
			}
		},

		_loadModules(moduleJavascriptPaths) {
			return Promise.all(
				moduleJavascriptPaths.map(
					(path) =>
						new Promise((resolve) => {
							const script = document.createElement('script');

							script.src = path;
							script.type = 'module';

							script.onload = script.onreadystatechange = () => {
								if (
									this.readyState &&
									this.readyState !== 'complete' &&
									this.readyState !== 'load'
								) {
									return;
								}

								script.onload = script.onreadystatechange = null;
								script.onerror = null;

								resolve();
							};

							script.onerror = () => {
								script.onload = script.onreadystatechange = null;
								script.onerror = null;

								console.error('Unable to load', path);

								resolve();
							};

							document.head.appendChild(script);
						})
				)
			);
		},

		_loadPortletFiles(response, loadHTML) {
			const footerCssPaths = response.footerCssPaths || [];
			const headerCssPaths = response.headerCssPaths || [];

			const head = A.one(STR_HEAD);

			if (headerCssPaths.length) {
				A.Get.css(headerCssPaths, {
					insertBefore: head.get('firstChild').getDOM(),
				});
			}

			const lastChild = document.body.lastChild;

			if (footerCssPaths.length) {
				A.Get.css(footerCssPaths, {
					insertBefore: lastChild,
				});
			}

			const responseHTML = response.portletHTML;

			let javascriptPaths = response.headerJavaScriptPaths || [];

			javascriptPaths = javascriptPaths.concat(
				response.footerJavaScriptPaths || []
			);

			if (javascriptPaths.length) {
				const moduleJavascriptPaths = javascriptPaths
					.filter((path) => path.startsWith('module:'))
					.map((path) => path.substring(7));

				javascriptPaths = javascriptPaths.filter(
					(path) => !path.startsWith('module:')
				);

				Portlet._loadModules(moduleJavascriptPaths).then(() => {
					A.Get.script(javascriptPaths, {
						onEnd() {
							loadHTML(responseHTML);
						},
					});
				});
			}
			else {
				loadHTML(responseHTML);
			}
		},

		_mergeOptions(portlet, options) {
			options = options || {};

			options.doAsUserId =
				options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();
			options.plid = options.plid || themeDisplay.getPlid();
			options.portlet = portlet;
			options.portletId = portlet.portletId;

			return options;
		},

		_staticPortlets: {},

		destroyComponents(portletId) {
			Liferay.destroyComponents((_component, componentConfig) => {
				return portletId === componentConfig.portletId;
			});
		},

		isStatic(portletId) {
			const instance = this;

			const id = Util.getPortletId(portletId.id || portletId);

			return id in instance._staticPortlets;
		},

		list: [],

		readyCounter: 0,

		refreshLayout(_portletBoundary) {},

		register(portletId) {
			const instance = this;

			if (instance.list.indexOf(portletId) < 0) {
				instance.list.push(portletId);
			}
		},
	};

	Liferay.provide(
		Portlet,
		'add',
		function (options) {
			const instance = this;

			Liferay.fire('initLayout');

			const doAsUserId =
				options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();
			const plid = options.plid || themeDisplay.getPlid();
			const portletData = options.portletData;
			const portletId = options.portletId;
			const portletItemId = options.portletItemId;

			let placeHolder = options.placeHolder;

			if (!placeHolder) {
				placeHolder = A.Node.create(
					'<div class="loading-animation" />'
				);
			}
			else {
				placeHolder = A.one(placeHolder);
			}

			const beforePortletLoaded = options.beforePortletLoaded;
			const onCompleteFn = options.onComplete;

			const onComplete = function (portlet, portletId) {
				if (onCompleteFn) {
					onCompleteFn(portlet, portletId);
				}

				instance.list.push(portlet.portletId);

				if (portlet) {
					portlet.attr('data-qa-id', 'app-loaded');
				}

				Liferay.fire('addPortlet', {
					portlet,
				});
			};

			let container = null;

			if (Liferay.Layout && Liferay.Layout.INITIALIZED) {
				container = Liferay.Layout.getActiveDropContainer();
			}

			if (!container) {
				return;
			}

			const containerId = container.attr('id');

			let currentColumnId = containerId.replace(/layout-column_/, '');

			let portletPosition = 0;

			if (options.placeHolder) {
				const column = placeHolder.get('parentNode');

				if (!column) {
					return;
				}

				placeHolder.addClass('portlet-boundary');

				const columnPortlets = column.all('.portlet-boundary');
				const nestedPortlets = column.all('.portlet-nested-portlets');

				portletPosition = columnPortlets.indexOf(placeHolder);

				let nestedPortletOffset = 0;

				nestedPortlets.some((nestedPortlet) => {
					const nestedPortletIndex = columnPortlets.indexOf(
						nestedPortlet
					);

					if (
						nestedPortletIndex !== -1 &&
						nestedPortletIndex < portletPosition
					) {
						nestedPortletOffset += nestedPortlet
							.all('.portlet-boundary')
							.size();
					}
					else if (nestedPortletIndex >= portletPosition) {
						return true;
					}
				});

				portletPosition -= nestedPortletOffset;

				currentColumnId = Util.getColumnId(column.attr('id'));
			}

			const url = themeDisplay.getPathMain() + '/portal/update_layout';

			const data = {
				cmd: 'add',
				dataType: 'JSON',
				doAsUserId,
				p_auth: Liferay.authToken,
				p_l_id: plid,
				p_p_col_id: currentColumnId,
				p_p_col_pos: portletPosition,
				p_p_i_id: portletItemId,
				p_p_id: portletId,
				p_p_isolated: true,
				p_v_l_s_g_id: themeDisplay.getSiteGroupId(),
				portletData,
			};

			const firstPortlet = container.one('.portlet-boundary');
			const hasStaticPortlet = firstPortlet && firstPortlet.isStatic;

			if (!options.placeHolder && !options.plid) {
				if (!hasStaticPortlet) {
					container.prepend(placeHolder);
				}
				else {
					firstPortlet.placeAfter(placeHolder);
				}
			}

			data.currentURL = Liferay.currentURL;

			instance.addHTML({
				beforePortletLoaded,
				data,
				onComplete,
				placeHolder,
				url,
			});
		},
		['aui-base']
	);

	Liferay.provide(
		Portlet,
		'addHTML',
		function (options) {
			const instance = this;

			let portletBoundary = null;

			const beforePortletLoaded = options.beforePortletLoaded;
			const data = options.data;
			let dataType = 'HTML';
			const onComplete = options.onComplete;
			const placeHolder = options.placeHolder;
			const url = options.url;

			if (data && Lang.isString(data.dataType)) {
				dataType = data.dataType;
			}

			dataType = dataType.toUpperCase();

			const addPortletReturn = function (html) {
				const container = placeHolder.get('parentNode');

				let portletBound = A.Node.create('<div></div>');

				portletBound.plug(A.Plugin.ParseContent);

				portletBound.setContent(html);

				portletBound = portletBound.one('> *');

				let portletId;

				if (portletBound) {
					const id = portletBound.attr('id');

					portletId = Util.getPortletId(id);

					portletBound.portletId = portletId;

					placeHolder.hide();
					placeHolder.placeAfter(portletBound);

					placeHolder.remove();

					instance.refreshLayout(portletBound);

					if (window.location.hash) {
						window.location.href = encodeURI(window.location.hash);
					}

					portletBoundary = portletBound;

					const Layout = Liferay.Layout;

					if (Layout && Layout.INITIALIZED) {
						Layout.updateCurrentPortletInfo(portletBoundary);

						if (container) {
							Layout.syncEmptyColumnClassUI(container);
						}

						Layout.syncDraggableClassUI();
						Layout.updatePortletDropZones(portletBoundary);
					}

					if (onComplete) {
						onComplete(portletBoundary, portletId);
					}
				}
				else {
					placeHolder.remove();
				}

				return portletId;
			};

			if (beforePortletLoaded) {
				beforePortletLoaded(placeHolder);
			}

			Liferay.Util.fetch(url, {
				body: Liferay.Util.objectToURLSearchParams(data),
				method: 'POST',
			})
				.then((response) => {
					if (dataType === 'JSON') {
						return response.json();
					}
					else {
						return response.text();
					}
				})
				.then((response) => {
					if (dataType === 'HTML') {
						addPortletReturn(response);
					}
					else if (response.refresh) {
						addPortletReturn(response.portletHTML);
					}
					else {
						Portlet._loadMarkupHeadElements(response);
						Portlet._loadPortletFiles(response, addPortletReturn);
					}

					if (!data || !data.preventNotification) {
						Liferay.fire('updatedLayout');
					}
				})
				.catch((error) => {
					const message =
						typeof error === 'string'
							? error
							: Liferay.Language.get(
									'there-was-an-unexpected-error.-please-refresh-the-current-page'
							  );

					Liferay.Util.openToast({
						message,
						type: 'danger',
					});
				});
		},
		['aui-parse-content']
	);

	Liferay.provide(
		Portlet,
		'close',
		function (portlet, skipConfirm, options) {
			const instance = this;

			const _removeComponent = () => {
				const portletId = portlet.portletId;

				const portletIndex = instance.list.indexOf(portletId);

				if (portletIndex >= 0) {
					instance.list.splice(portletIndex, 1);
				}

				options = Portlet._mergeOptions(portlet, options);

				Portlet.destroyComponents(portletId);

				Liferay.fire('destroyPortlet', options);

				Liferay.fire('closePortlet', options);
			};

			portlet = A.one(portlet);

			if (portlet) {
				if (!skipConfirm) {
					Liferay.Util.openConfirmModal({
						message: Liferay.Language.get(
							'are-you-sure-you-want-to-remove-this-component'
						),
						onConfirm: (isConfirmed) => {
							if (isConfirmed) {
								_removeComponent();
							}
						},
					});
				}
				else {
					_removeComponent();
				}
			}
			else {
				A.config.win.focus();
			}
		},
		[]
	);

	Liferay.provide(
		Portlet,
		'destroy',
		(portlet, options) => {
			portlet = A.one(portlet);

			if (portlet) {
				const portletId =
					portlet.portletId || Util.getPortletId(portlet.attr('id'));

				Portlet.destroyComponents(portletId);

				Liferay.fire(
					'destroyPortlet',
					Portlet._mergeOptions(portlet, options)
				);
			}
		},
		['aui-node-base']
	);

	Liferay.provide(
		Portlet,
		'onLoad',
		function (options) {
			const instance = this;

			const canEditTitle = options.canEditTitle;
			const columnPos = options.columnPos;
			const isStatic =
				options.isStatic === 'no' ? null : options.isStatic;
			const namespacedId = options.namespacedId;
			const portletId = options.portletId;
			const refreshURL = options.refreshURL;
			const refreshURLData = options.refreshURLData;

			if (isStatic) {
				instance.registerStatic(portletId);
			}

			const portlet = A.one('#' + namespacedId);

			if (portlet && !portlet.portletProcessed) {
				portlet.portletProcessed = true;
				portlet.portletId = portletId;
				portlet.columnPos = columnPos;
				portlet.isStatic = isStatic;
				portlet.refreshURL = refreshURL;
				portlet.refreshURLData = refreshURLData;

				// Functions to run on portlet load

				if (canEditTitle) {

					// https://github.com/yui/yui3/issues/1808

					let events = 'focus';

					if (!A.UA.touchEnabled) {
						events = ['focus', 'mousemove'];
					}

					const handle = portlet.on(events, () => {
						Util.portletTitleEdit({
							doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
							// eslint-disable-next-line @liferay/no-abbreviations
							obj: portlet,
							plid: themeDisplay.getPlid(),
							portletId,
						});

						handle.detach();
					});
				}
			}

			Liferay.fire('portletReady', {
				portlet,
				portletId,
			});

			instance.readyCounter++;

			if (instance.readyCounter === instance.list.length) {
				Liferay.fire('allPortletsReady', {
					portletId,
				});
			}
		},
		['aui-base', 'aui-timer', 'event-move']
	);

	Liferay.provide(
		Portlet,
		'refresh',
		function (portlet, data, mergeWithRefreshURLData) {
			const instance = this;

			portlet = A.one(portlet);

			if (portlet) {
				if (mergeWithRefreshURLData) {
					data = {
						...(portlet.refreshURLData || {}),
						...(data || {}),
					};
				}
				else {
					data = data || portlet.refreshURLData || {};
				}

				if (
					!Object.prototype.hasOwnProperty.call(
						data,
						'portletAjaxable'
					)
				) {
					data.portletAjaxable = true;
				}

				const id = portlet.attr('portlet');

				let url = portlet.refreshURL;

				const placeHolder = A.Node.create(
					'<div class="loading-animation" id="p_p_id' + id + '" />'
				);

				if (data.portletAjaxable && url) {
					portlet.placeBefore(placeHolder);

					portlet.remove(true);

					Portlet.destroyComponents(portlet.portletId);

					let params = {};

					const urlPieces = url.split('?');

					if (urlPieces.length > 1) {
						params = A.QueryString.parse(urlPieces[1]);

						delete params.dataType;

						url = urlPieces[0];
					}

					instance.addHTML({
						data: A.mix(params, data, true),
						onComplete(portlet, portletId) {
							portlet.refreshURL = url;

							if (portlet) {
								portlet.attr('data-qa-id', 'app-refreshed');
							}

							Liferay.fire(
								portlet.portletId + ':portletRefreshed',
								{
									portlet,
									portletId,
								}
							);
						},
						placeHolder,
						url,
					});
				}
				else if (!portlet.getData('pendingRefresh')) {
					portlet.setData('pendingRefresh', true);

					const nonAjaxableContentMessage = Lang.sub(
						TPL_NOT_AJAXABLE,
						[
							Liferay.Language.get(
								'this-change-will-only-be-shown-after-you-refresh-the-page'
							),
						]
					);

					const portletBody = portlet.one('.portlet-body');

					portletBody.placeBefore(nonAjaxableContentMessage);

					portletBody.hide();
				}
			}
		},
		['aui-base', 'querystring-parse']
	);

	Liferay.provide(
		Portlet,
		'registerStatic',
		function (portletId) {
			const instance = this;

			const Node = A.Node;

			if (Node && portletId instanceof Node) {
				portletId = portletId.attr('id');
			}
			else if (portletId.id) {
				portletId = portletId.id;
			}

			const id = Util.getPortletId(portletId);

			instance._staticPortlets[id] = true;
		},
		['aui-base']
	);

	Liferay.publish('closePortlet', {
		defaultFn: Portlet._defCloseFn,
	});

	Liferay.publish('allPortletsReady', {
		fireOnce: true,
	});

	// Backwards compatability

	Portlet.ready = function (fn) {
		Liferay.on('portletReady', (event) => {
			fn(event.portletId, event.portlet);
		});
	};

	Liferay.Portlet = Portlet;
})(AUI());
