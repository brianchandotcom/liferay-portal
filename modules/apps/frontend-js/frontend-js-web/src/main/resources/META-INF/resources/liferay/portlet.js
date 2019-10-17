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

(function(A, Liferay) {
	var Lang = A.Lang;

	var Util = Liferay.Util;

	var STR_HEAD = 'head';

	var TPL_NOT_AJAXABLE = '<div class="alert alert-info">{0}</div>';

	var Portlet = {
		_defCloseFn(event) {
			event.portlet.remove(true);

			if (!event.nestedPortlet) {
				var formData = Liferay.Util.objectToFormData({
					cmd: 'delete',
					doAsUserId: event.doAsUserId,
					p_auth: Liferay.authToken,
					p_l_id: event.plid,
					p_p_id: event.portletId,
					p_v_l_s_g_id: themeDisplay.getSiteGroupId()
				});

				Liferay.Util.fetch(
					themeDisplay.getPathMain() + '/portal/update_layout',
					{
						body: formData,
						method: 'POST'
					}
				).then(response => {
					if (response.ok) {
						Liferay.fire('updatedLayout');
					}
				});
			}
		},

		_loadMarkupHeadElements(response) {
			var markupHeadElements = response.markupHeadElements;

			if (markupHeadElements && markupHeadElements.length) {
				var head = A.one(STR_HEAD);

				head.append(markupHeadElements);

				var container = A.Node.create('<div />');

				container.plug(A.Plugin.ParseContent);

				container.setContent(markupHeadElements);
			}
		},

		_loadPortletFiles(response, loadHTML) {
			var footerCssPaths = response.footerCssPaths || [];
			var headerCssPaths = response.headerCssPaths || [];

			var javascriptPaths = response.headerJavaScriptPaths || [];

			javascriptPaths = javascriptPaths.concat(
				response.footerJavaScriptPaths || []
			);

			var body = A.getBody();

			var head = A.one(STR_HEAD);

			if (headerCssPaths.length) {
				A.Get.css(headerCssPaths, {
					insertBefore: head.get('firstChild').getDOM()
				});
			}

			var lastChild = body.get('lastChild').getDOM();

			if (footerCssPaths.length) {
				A.Get.css(footerCssPaths, {
					insertBefore: lastChild
				});
			}

			var responseHTML = response.portletHTML;

			if (javascriptPaths.length) {
				A.Get.script(javascriptPaths, {
					onEnd() {
						loadHTML(responseHTML);
					}
				});
			} else {
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
			var instance = this;

			var id = Util.getPortletId(portletId.id || portletId);

			return id in instance._staticPortlets;
		},

		list: [],

		readyCounter: 0,

		refreshLayout(_portletBoundary) {},

		register(portletId) {
			var instance = this;

			if (instance.list.indexOf(portletId) < 0) {
				instance.list.push(portletId);
			}
		}
	};

	Liferay.provide(
		Portlet,
		'add',
		function(options) {
			var instance = this;

			Liferay.fire('initLayout');

			var doAsUserId =
				options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();
			var plid = options.plid || themeDisplay.getPlid();
			var portletData = options.portletData;
			var portletId = options.portletId;
			var portletItemId = options.portletItemId;

			var placeHolder = options.placeHolder;

			if (!placeHolder) {
				placeHolder = A.Node.create(
					'<div class="loading-animation" />'
				);
			} else {
				placeHolder = A.one(placeHolder);
			}

			var beforePortletLoaded = options.beforePortletLoaded;
			var onCompleteFn = options.onComplete;

			var onComplete = function(portlet, portletId) {
				if (onCompleteFn) {
					onCompleteFn(portlet, portletId);
				}

				instance.list.push(portlet.portletId);

				if (portlet) {
					portlet.attr('data-qa-id', 'app-loaded');
				}

				Liferay.fire('addPortlet', {
					portlet
				});
			};

			var container = null;

			if (Liferay.Layout && Liferay.Layout.INITIALIZED) {
				container = Liferay.Layout.getActiveDropContainer();
			}

			if (!container) {
				return;
			}

			var currentColumnId = Util.getColumnId(container.attr('id'));

			var portletPosition = 0;

			if (options.placeHolder) {
				var column = placeHolder.get('parentNode');

				if (!column) {
					return;
				}

				placeHolder.addClass('portlet-boundary');

				var columnPortlets = column.all('.portlet-boundary');
				var nestedPortlets = column.all('.portlet-nested-portlets');

				portletPosition = columnPortlets.indexOf(placeHolder);

				var nestedPortletOffset = 0;

				nestedPortlets.some(nestedPortlet => {
					var nestedPortletIndex = columnPortlets.indexOf(
						nestedPortlet
					);

					if (
						nestedPortletIndex !== -1 &&
						nestedPortletIndex < portletPosition
					) {
						nestedPortletOffset += nestedPortlet
							.all('.portlet-boundary')
							.size();
					} else if (nestedPortletIndex >= portletPosition) {
						return true;
					}
				});

				portletPosition -= nestedPortletOffset;

				currentColumnId = Util.getColumnId(column.attr('id'));
			}

			var url = themeDisplay.getPathMain() + '/portal/update_layout';

			var data = {
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
				portletData
			};

			var firstPortlet = container.one('.portlet-boundary');
			var hasStaticPortlet = firstPortlet && firstPortlet.isStatic;

			if (!options.placeHolder && !options.plid) {
				if (!hasStaticPortlet) {
					container.prepend(placeHolder);
				} else {
					firstPortlet.placeAfter(placeHolder);
				}
			}

			data.currentURL = Liferay.currentURL;

			instance.addHTML({
				beforePortletLoaded,
				data,
				onComplete,
				placeHolder,
				url
			});
		},
		['aui-base']
	);

	Liferay.provide(
		Portlet,
		'addHTML',
		function(options) {
			var instance = this;

			var portletBoundary = null;

			var beforePortletLoaded = options.beforePortletLoaded;
			var data = options.data;
			var dataType = 'HTML';
			var onComplete = options.onComplete;
			var placeHolder = options.placeHolder;
			var url = options.url;

			if (data && Lang.isString(data.dataType)) {
				dataType = data.dataType;
			}

			dataType = dataType.toUpperCase();

			var addPortletReturn = function(html) {
				var container = placeHolder.get('parentNode');

				var portletBound = A.Node.create('<div></div>');

				portletBound.plug(A.Plugin.ParseContent);

				portletBound.setContent(html);

				portletBound = portletBound.one('> *');

				var portletId;

				if (portletBound) {
					var id = portletBound.attr('id');

					portletId = Util.getPortletId(id);

					portletBound.portletId = portletId;

					placeHolder.hide();
					placeHolder.placeAfter(portletBound);

					placeHolder.remove();

					instance.refreshLayout(portletBound);

					if (window.location.hash) {
						window.location.href = window.location.hash;
					}

					portletBoundary = portletBound;

					var Layout = Liferay.Layout;

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
				} else {
					placeHolder.remove();
				}

				return portletId;
			};

			if (beforePortletLoaded) {
				beforePortletLoaded(placeHolder);
			}

			Liferay.Util.fetch(url, {
				body: Liferay.Util.objectToURLSearchParams(data),
				method: 'POST'
			})
				.then(response => {
					if (dataType === 'JSON') {
						return response.json();
					} else {
						return response.text();
					}
				})
				.then(response => {
					if (dataType === 'HTML') {
						addPortletReturn(response);
					} else if (response.refresh) {
						addPortletReturn(response.portletHTML);
					} else {
						Portlet._loadMarkupHeadElements(response);
						Portlet._loadPortletFiles(response, addPortletReturn);
					}

					if (!data || !data.preventNotification) {
						Liferay.fire('updatedLayout');
					}
				})
				.catch(error => {
					var message =
						typeof error === 'string'
							? error
							: Liferay.Language.get(
									'there-was-an-unexpected-error.-please-refresh-the-current-page'
							  );

					Liferay.Util.openToast({
						message,
						title: Liferay.Language.get('error'),
						type: 'danger'
					});
				});
		},
		['aui-parse-content']
	);

	Liferay.provide(
		Portlet,
		'close',
		function(portlet, skipConfirm, options) {
			var instance = this;

			portlet = A.one(portlet);

			if (
				portlet &&
				(skipConfirm ||
					confirm(
						Liferay.Language.get(
							'are-you-sure-you-want-to-remove-this-component'
						)
					))
			) {
				var portletId = portlet.portletId;

				var portletIndex = instance.list.indexOf(portletId);

				if (portletIndex >= 0) {
					instance.list.splice(portletIndex, 1);
				}

				options = Portlet._mergeOptions(portlet, options);

				Portlet.destroyComponents(portletId);

				Liferay.fire('destroyPortlet', options);

				Liferay.fire('closePortlet', options);
			} else {
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
				var portletId =
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
		'minimize',
		(portlet, el, options) => {
			options = options || {};

			var doAsUserId =
				options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();
			var plid = options.plid || themeDisplay.getPlid();

			portlet = A.one(portlet);

			if (portlet) {
				var content = portlet.one('.portlet-content-container');

				if (content) {
					var restore = content.hasClass('hide');

					content.toggle();
					portlet.toggleClass('portlet-minimized');

					var link = A.one(el);

					if (link) {
						var title = restore
							? Liferay.Language.get('minimize')
							: Liferay.Language.get('restore');

						link.attr('alt', title);
						link.attr('title', title);

						var linkText = link.one('.taglib-text-icon');

						if (linkText) {
							linkText.html(title);
						}

						var icon = link.one('i');

						if (icon) {
							icon.removeClass('icon-minus icon-resize-vertical');

							if (restore) {
								icon.addClass('icon-minus');
							} else {
								icon.addClass('icon-resize-vertical');
							}
						}
					}

					var formData = Liferay.Util.objectToFormData({
						cmd: 'minimize',
						doAsUserId,
						p_auth: Liferay.authToken,
						p_l_id: plid,
						p_p_id: portlet.portletId,
						p_p_restore: restore,
						p_v_l_s_g_id: themeDisplay.getSiteGroupId()
					});

					Liferay.Util.fetch(
						themeDisplay.getPathMain() + '/portal/update_layout',
						{
							body: formData,
							method: 'POST'
						}
					).then(response => {
						if (response.ok && restore) {
							var data = {
								doAsUserId,
								p_l_id: plid,
								p_p_boundary: false,
								p_p_id: portlet.portletId,
								p_p_isolated: true
							};

							portlet.plug(A.Plugin.ParseContent);

							portlet.load(
								themeDisplay.getPathMain() +
									'/portal/render_portlet?' +
									A.QueryString.stringify(data)
							);
						}
					});
				}
			}
		},
		['aui-parse-content', 'node-load', 'querystring-stringify']
	);

	Liferay.provide(
		Portlet,
		'onLoad',
		function(options) {
			var instance = this;

			var canEditTitle = options.canEditTitle;
			var columnPos = options.columnPos;
			var isStatic = options.isStatic == 'no' ? null : options.isStatic;
			var namespacedId = options.namespacedId;
			var portletId = options.portletId;
			var refreshURL = options.refreshURL;
			var refreshURLData = options.refreshURLData;

			if (isStatic) {
				instance.registerStatic(portletId);
			}

			var portlet = A.one('#' + namespacedId);

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

					var events = 'focus';

					if (!A.UA.touch) {
						events = ['focus', 'mousemove'];
					}

					var handle = portlet.on(events, () => {
						Util.portletTitleEdit({
							doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
							obj: portlet,
							plid: themeDisplay.getPlid(),
							portletId
						});

						handle.detach();
					});
				}
			}

			Liferay.fire('portletReady', {
				portlet,
				portletId
			});

			instance.readyCounter++;

			if (instance.readyCounter === instance.list.length) {
				Liferay.fire('allPortletsReady', {
					portletId
				});
			}
		},
		['aui-base', 'aui-timer', 'event-move']
	);

	Liferay.provide(
		Portlet,
		'refresh',
		function(portlet, data) {
			var instance = this;

			portlet = A.one(portlet);

			if (portlet) {
				data = data || portlet.refreshURLData || {};

				if (
					!Object.prototype.hasOwnProperty.call(
						data,
						'portletAjaxable'
					)
				) {
					data.portletAjaxable = true;
				}

				var id = portlet.attr('portlet');

				var url = portlet.refreshURL;

				var placeHolder = A.Node.create(
					'<div class="loading-animation" id="p_p_id' + id + '" />'
				);

				if (data.portletAjaxable && url) {
					portlet.placeBefore(placeHolder);

					portlet.remove(true);

					Portlet.destroyComponents(portlet.portletId);

					var params = {};

					var urlPieces = url.split('?');

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
									portletId
								}
							);
						},
						placeHolder,
						url
					});
				} else if (!portlet.getData('pendingRefresh')) {
					portlet.setData('pendingRefresh', true);

					var nonAjaxableContentMessage = Lang.sub(TPL_NOT_AJAXABLE, [
						Liferay.Language.get(
							'this-change-will-only-be-shown-after-you-refresh-the-page'
						)
					]);

					var portletBody = portlet.one('.portlet-body');

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
		function(portletId) {
			var instance = this;

			var Node = A.Node;

			if (Node && portletId instanceof Node) {
				portletId = portletId.attr('id');
			} else if (portletId.id) {
				portletId = portletId.id;
			}

			var id = Util.getPortletId(portletId);

			instance._staticPortlets[id] = true;
		},
		['aui-base']
	);

	Liferay.provide(
		Portlet,
		'openWindow',
		options => {
			var bodyCssClass = options.bodyCssClass;
			var destroyOnHide = options.destroyOnHide;
			var namespace = options.namespace;
			var portlet = options.portlet;
			var subTitle = options.subTitle;
			var title = options.title;
			var uri = options.uri;

			portlet = A.one(portlet);

			if (portlet && uri) {
				var portletTitle =
					portlet.one('.portlet-title') ||
					portlet.one('.portlet-title-default');

				var titleHtml = title;

				if (portletTitle) {
					if (portlet.one('#cpPortletTitle')) {
						titleHtml =
							portletTitle
								.one('.portlet-title-text')
								.outerHTML() +
							' - ' +
							titleHtml;
					} else {
						titleHtml = portletTitle.text() + ' - ' + titleHtml;
					}
				}

				if (subTitle) {
					titleHtml +=
						'<div class="portlet-configuration-subtitle small"><span class="portlet-configuration-subtitle-text">' +
						subTitle +
						'</span></div>';
				}

				Liferay.Util.openWindow(
					{
						cache: false,
						dialog: {
							destroyOnHide
						},
						dialogIframe: {
							bodyCssClass,
							id: namespace + 'configurationIframe',
							uri
						},
						id: namespace + 'configurationIframeDialog',
						title: titleHtml,
						uri
					},
					dialog => {
						dialog.once('drag:init', () => {
							dialog.dd.addInvalid(
								'.portlet-configuration-subtitle-text'
							);
						});
					}
				);
			}
		},
		['liferay-util-window']
	);

	Liferay.publish('closePortlet', {
		defaultFn: Portlet._defCloseFn
	});

	Liferay.publish('allPortletsReady', {
		fireOnce: true
	});

	// Backwards compatability

	Portlet.ready = function(fn) {
		Liferay.on('portletReady', event => {
			fn(event.portletId, event.portlet);
		});
	};

	Liferay.Portlet = Portlet;
})(AUI(), Liferay);
