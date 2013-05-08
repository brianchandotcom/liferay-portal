;(function(A, Liferay) {
	var Util = Liferay.Util;

	var arrayIndexOf = A.Array.indexOf;

	var TPL_NOT_AJAXABLE = '<div class="alert alert-info">{0}</div>';

	var Portlet = {
		list: [],

		isStatic: function(portletId) {
			var instance = this;

			var id = Util.getPortletId(portletId.id || portletId);

			return (id in instance._staticPortlets);
		},

		refreshLayout: function(portletBoundary) {
		},

		_defCloseFn: function(event) {
			var instance = this;

			event.portlet.remove(true);

			A.io.request(
				themeDisplay.getPathMain() + '/portal/update_layout',
				{
					after: {
						success: function() {
							Liferay.fire('updatedLayout');
						}
					},
					data: {
						cmd: 'delete',
						doAsUserId: event.doAsUserId,
						p_auth: Liferay.authToken,
						p_l_id: event.plid,
						p_p_id: event.portletId,
						p_v_l_s_g_id: themeDisplay.getSiteGroupId()
					}
				}
			);
		},

		_loadPortletFiles: function(response, loadHTML) {
			var headerCssPaths = response.headerCssPaths || [];
			var footerCssPaths = response.footerCssPaths || [];

			var javascriptPaths = response.headerJavaScriptPaths || [];

			javascriptPaths = javascriptPaths.concat(response.footerJavaScriptPaths || []);

			var head = A.one('head');
			var body = A.getBody();

			if (headerCssPaths.length) {
				A.Get.css(
					headerCssPaths,
					{
						insertBefore: head.get('firstChild').getDOM(),
						onSuccess: function(event) {
							if (Liferay.Browser.isIe()) {
								A.all('body link').appendTo(head);

								A.all('link.lfr-css-file').each(
									function(item, index, collection) {
										document.createStyleSheet(item.get('href'));
									}
								);
							}
						}
					}
				);
			}

			var lastChild = body.get('lastChild').getDOM();

			if (footerCssPaths.length) {
				A.Get.css(
					footerCssPaths,
					{
						insertBefore: lastChild
					}
				);
			}

			var responseHTML = response.portletHTML;

			if (javascriptPaths.length) {
				A.Get.script(
					javascriptPaths,
					{
						onEnd: function(obj) {
							loadHTML(responseHTML);
						}
					}
				);
			}
			else {
				loadHTML(responseHTML);
			}
		},

		_staticPortlets: {}
	};

	Liferay.provide(
		Portlet,
		'add',
		function(options) {
			var instance = this;

			Liferay.fire('initLayout');

			var plid = options.plid || themeDisplay.getPlid();
			var portletData = options.portletData;
			var portletId = options.portletId;
			var portletItemId = options.portletItemId;
			var doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();

			var placeHolder = options.placeHolder;

			if (!placeHolder) {
				placeHolder = A.Node.create('<div class="loading-animation" />');
			}
			else {
				placeHolder = A.one(placeHolder);
			}

			var positionOptions = options.positionOptions;
			var beforePortletLoaded = options.beforePortletLoaded;
			var onCompleteFn = options.onComplete;

			var onComplete = function(portlet, portletId) {
				if (onCompleteFn) {
					onCompleteFn(portlet, portletId);
				}

				Liferay.fire(
					'addPortlet',
					{
						portlet: portlet
					}
				);
			};

			var container = null;

			if (Liferay.Layout && Liferay.Layout.INITIALIZED) {
				container = Liferay.Layout.getActiveDropContainer();
			}

			if (!container) {
				return;
			}

			var portletPosition = 0;
			var currentColumnId = Util.getColumnId(container.attr('id'));

			if (options.placeHolder) {
				var column = placeHolder.get('parentNode');

				if (!column) {
					return;
				}

				placeHolder.addClass('portlet-boundary');

				portletPosition = column.all('.portlet-boundary').indexOf(placeHolder);

				currentColumnId = Util.getColumnId(column.attr('id'));
			}

			var url = themeDisplay.getPathMain() + '/portal/update_layout';

			var data = {
				cmd: 'add',
				dataType: 'json',
				doAsUserId: doAsUserId,
				portletData: portletData,
				p_auth: Liferay.authToken,
				p_l_id: plid,
				p_p_col_id: currentColumnId,
				p_p_col_pos: portletPosition,
				p_p_id: portletId,
				p_p_i_id: portletItemId,
				p_p_isolated: true,
				p_v_l_s_g_id: themeDisplay.getSiteGroupId()
			};

			var firstPortlet = container.one('.portlet-boundary');
			var hasStaticPortlet = (firstPortlet && firstPortlet.isStatic);

			if (!options.placeHolder && !options.plid) {
				if (!hasStaticPortlet) {
					container.prepend(placeHolder);
				}
				else {
					firstPortlet.placeAfter(placeHolder);
				}
			}

			if (themeDisplay.isFreeformLayout()) {
				container.prepend(placeHolder);
			}

			data.currentURL = Liferay.currentURL;

			return instance.addHTML(
				{
					beforePortletLoaded: beforePortletLoaded,
					data: data,
					onComplete: onComplete,
					placeHolder: placeHolder,
					url: url
				}
			);
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
			var dataType = 'html';
			var onComplete = options.onComplete;
			var placeHolder = options.placeHolder;
			var url = options.url;

			if (data && data.dataType) {
				dataType = data.dataType;
			}

			var addPortletReturn = function(html) {
				var container = placeHolder.get('parentNode');

				var portletBound = A.Node.create('<div></div>');

				portletBound.plug(A.Plugin.ParseContent);

				portletBound.setContent(html);
				portletBound = portletBound.get('firstChild');

				var id = portletBound.attr('id');

				var portletId = Util.getPortletId(id);

				portletBound.portletId = portletId;

				placeHolder.hide();
				placeHolder.placeAfter(portletBound);

				placeHolder.remove();

				instance.refreshLayout(portletBound);

				Util.addInputType(portletBound);

				if (window.location.hash) {
					window.location.hash = 'p_' + portletId;
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

				return portletId;
			};

			if (beforePortletLoaded) {
				beforePortletLoaded(placeHolder);
			}

			A.io.request(
				url,
				{
					after: {
						success: function() {
							if (!data || !data.preventNotification) {
								Liferay.fire('updatedLayout');
							}
						}
					},
					data: data,
					dataType: dataType,
					on: {
						failure: function(event, id, obj) {
							placeHolder.hide();

							placeHolder.placeAfter('<div class="alert alert-error">' + Liferay.Language.get('there-was-an-unexpected-error.-please-refresh-the-current-page') + '</div>');
						},
						success: function(event, id, obj) {
							var instance = this;

							var response = instance.get('responseData');

							if (dataType == 'html') {
								addPortletReturn(response);
							}
							else if (response.refresh) {
								addPortletReturn(response.portletHTML);
							}
							else {
								Portlet._loadPortletFiles(response, addPortletReturn);
							}
						}
					}
				}
			);
		},
		['aui-io-request', 'aui-parse-content']
	);

	Liferay.provide(
		Portlet,
		'close',
		function(portlet, skipConfirm, options) {
			var instance = this;

			portlet = A.one(portlet);

			if (portlet && (skipConfirm || confirm(Liferay.Language.get('are-you-sure-you-want-to-remove-this-component')))) {
				options = options || {};

				options.plid = options.plid || themeDisplay.getPlid();
				options.doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();
				options.portlet = portlet;
				options.portletId = portlet.portletId;

				Liferay.fire('closePortlet', options);
			}
			else {
				self.focus();
			}
		},
		['aui-io-request']
	);

	Liferay.provide(
		Portlet,
		'minimize',
		function(portlet, el, options) {
			var instance = this;

			options = options || {};

			var plid = options.plid || themeDisplay.getPlid();
			var doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();

			portlet = A.one(portlet);

			if (portlet) {
				var content = portlet.one('.portlet-content-container');

				if (content) {
					var restore = content.hasClass('hide');

					content.toggle();
					portlet.toggleClass('portlet-minimized');

					var link = A.one(el);

					if (link) {
						var img = link.one('img');

						if (img) {
							var title = (restore) ? Liferay.Language.get('minimize') : Liferay.Language.get('restore');

							var imgSrc = themeDisplay.getPathThemeImages() + '/portlet/';

							if (restore) {
								imgSrc += 'minimize.png';
							}
							else {
								imgSrc += 'restore.png';
							}

							img.attr('alt', title);
							img.attr('title', title);

							link.attr('title', title);
							img.attr('src', imgSrc);

							img.setStyles(
								{
									backgroundImage: 'none',
									height: 16,
									width: 16
								}
							);
						}
					}

					var html = '';
					var portletBody = content.one('.portlet-body');

					if (portletBody) {
						html = portletBody.html();
					}

					var hasBodyContent = !!(A.Lang.trim(html));

					if (hasBodyContent) {
						content.unplug(A.Plugin.IO);
					}
					else {
						content.plug(
							A.Plugin.IO,
							{
								autoLoad: false,
								data: {
									doAsUserId: doAsUserId,
									p_l_id: plid,
									p_p_id: portlet.portletId,
									p_p_state: 'exclusive'
								},
								showLoading: false,
								uri: themeDisplay.getPathMain() + '/portal/render_portlet'
							}
						);
					}

					A.io.request(
						themeDisplay.getPathMain() + '/portal/update_layout',
						{
							after: {
								success: function() {
									if (restore && content.io) {
										content.io.start();
									}
								}
							},
							data: {
								cmd: 'minimize',
								doAsUserId: doAsUserId,
								p_auth: Liferay.authToken,
								p_l_id: plid,
								p_p_id: portlet.portletId,
								p_p_restore: restore,
								p_v_l_s_g_id: themeDisplay.getSiteGroupId()
							}
						}
					);
				}
			}
		},
		['aui-io']
	);

	Liferay.provide(
		Portlet,
		'onLoad',
		function(options) {
			var instance = this;

			var canEditTitle = options.canEditTitle;
			var columnPos = options.columnPos;
			var isStatic = (options.isStatic == 'no') ? null : options.isStatic;
			var namespacedId = options.namespacedId;
			var portletId = options.portletId;
			var refreshURL = options.refreshURL;

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

				// Functions to run on portlet load

				if (canEditTitle) {
					var handle = portlet.on(
						['focus', 'mousedown', 'mousemove'],
						function(event) {
							Util.portletTitleEdit(
								{
									doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
									obj: portlet,
									plid: themeDisplay.getPlid(),
									portletId: portletId
								}
							);

							handle.detach();
						}
					);
				}

				Liferay.fire(
					'portletReady',
					{
						portlet: portlet,
						portletId: portletId
					}
				);

				var list = instance.list;

				var index = arrayIndexOf(list, portletId);

				if (index > -1) {
					list.splice(index, 1);

					if (!list.length) {
						Liferay.fire(
							'allPortletsReady',
							{
								portletId: portletId
							}
						);
					}
				}
			}
		},
		['aui-base', 'aui-task-manager']
	);

	Liferay.provide(
		Portlet,
		'refresh',
		function(portlet, data) {
			var instance = this;

			portlet = A.one(portlet);

			if (portlet) {
				data = data || {};

				if (!A.Object.owns(data, 'portletAjaxable')) {
					data.portletAjaxable = true;
				}

				var id = portlet.attr('portlet');

				var url = portlet.refreshURL;

				var placeHolder = A.Node.create('<div class="loading-animation" id="p_load' + id + '" />');

				if (data.portletAjaxable && url) {
					portlet.placeBefore(placeHolder);

					portlet.remove(true);

					var params = {};

					var urlPieces = url.split('?');

					if (urlPieces.length > 1) {
						params = A.QueryString.parse(urlPieces[1]);

						delete params.dataType;

						url = urlPieces[0];
					}

					instance.addHTML(
						{
							data: A.mix(params, data, true),
							onComplete: function(portlet, portletId) {
								portlet.refreshURL = url;

								Liferay.fire(
									portlet.portletId + ':portletRefreshed',
									{
										portlet: portlet,
										portletId: portletId
									}
								);
							},
							placeHolder: placeHolder,
							url: url
						}
					);
				}
				else if (!portlet.getData('pendingRefresh')) {
					portlet.setData('pendingRefresh', true);

					var nonAjaxableContentMessage = A.Lang.sub(
						TPL_NOT_AJAXABLE,
						[Liferay.Language.get('this-change-will-only-be-shown-after-you-refresh-the-page')]
					);

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
			}
			else if (portletId.id) {
				portletId = portletId.id;
			}

			var id = Util.getPortletId(portletId);

			instance._staticPortlets[id] = true;
		},
		['aui-base']
	);

	Liferay.provide(
		Portlet,
		'openConfiguration',
		function(portlet, portletId, configurationURL, namespacedId) {
			var instance = this;

			portlet = A.one(portlet);

			if (portlet && configurationURL) {
				var title = portlet.one('.portlet-title') || portlet.one('.portlet-title-default');

				var titleHtml = title.html();

				if (portlet.one('#cpPortletTitle')) {
					titleHtml = title.one('.portlet-title-text').outerHTML();
				}

				Liferay.Util.openWindow(
					{
						cache: false,
						dialogIframe: {
							id: namespacedId + 'configurationIframe',
							uri: configurationURL
						},
						id: namespacedId + 'configurationIframeDialog',
						title: titleHtml + ' - ' + Liferay.Language.get('configuration'),
						uri: configurationURL
					}
				);
			}
		},
		['liferay-util-window']
	);

	Liferay.provide(
		Portlet,
		'loadCSSEditor',
		function(portletId) {
			Liferay.PortletCSS.init(portletId);
		},
		['liferay-look-and-feel']
	);

	Liferay.publish(
		'closePortlet',
		{
			defaultFn: Portlet._defCloseFn
		}
	);

	Liferay.publish(
		'allPortletsReady',
		{
			fireOnce: true
		}
	);

	// Backwards compatability

	Portlet.ready = function(fn) {
		Liferay.on(
			'portletReady',
			function(event) {
				fn(event.portletId, event.portlet);
			}
		);
	};

	Liferay.Portlet = Portlet;
})(AUI(), Liferay);