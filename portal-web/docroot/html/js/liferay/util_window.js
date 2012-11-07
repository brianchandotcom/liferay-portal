AUI.add(
	'liferay-util-window',
	function(A) {
		var Lang = A.Lang;
		var Util = Liferay.Util;
		var Window = Util.Window;

		Util.incrementWindowXY = function(decrement) {
			var incrementor = Window.XY_INCREMENTOR;
			var windowXY = Window.XY;

			if (decrement) {
				incrementor *= -1;
			}

			windowXY[0] += incrementor;
			windowXY[1] += incrementor;
		};

		var CONFIG_DEFAULTS_DIALOG = {
			draggable: true,
			stack: false,
			width: 720,
			xy: Window.XY,
			after: {
				visibleChange: function(event) {
					Util.incrementWindowXY(!event.newVal);
				},
				render: function(event) {
					Util.incrementWindowXY();
				}
			}
		};

		Util._openWindow = function(config, callback) {
			var openingWindow = config.openingWindow;

			var refreshWindow = config.refreshWindow;
			var title = config.title;
			var uri = config.uri;

			var id = config.id || A.guid();

			if (config.cache === false) {
				uri = Liferay.Util.addParams(A.guid() + '=' + Lang.now(), uri);
			}

			var dialog = Window._map[id];

			var defaultDialogConfig = null;

			if (!dialog) {
				var dialogConfig = config.dialog || {};

				var dialogIframeConfig = config.dialogIframe || {};

				var openingUtil = A.Object.getValue(openingWindow, 'Liferay.Util'.split('.'));

				if (openingUtil) {
					var openingWindowName = openingUtil.getWindowName();

					var openingDialog = Window._map[openingWindowName];

					if (openingDialog) {
						defaultDialogConfig = {
							draggable: openingDialog.get('draggable'),
							stack: openingDialog.get('stack')
						};
					}
				}

				dialogConfig = A.merge(CONFIG_DEFAULTS_DIALOG, defaultDialogConfig, dialogConfig);

				A.mix(
					dialogIframeConfig,
					{
						bindLoadHandler: function() {
							var instance = this;

							var popupReady = false;

							Liferay.on(
								'popupReady',
								function(event) {
									instance.fire('load', event);

									popupReady = true;
								}
							);

							instance.node.on(
								'load',
								function(event) {
									if (!popupReady) {
										Liferay.fire(
											'popupReady',
											{
												windowName: id
											}
										);
									}

									popupReady = false;
								}
							);
						},
						id: id,
						iframeId: id,
						uri: uri
					}
				);

				if (!('zIndex' in dialogConfig)) {
					dialogConfig.zIndex = (++Liferay.zIndex.WINDOW);
				}

				dialog = new A.Dialog(dialogConfig).plug(A.Plugin.DialogIframe, dialogIframeConfig);

				Window._map[id] = dialog;

				dialog._opener = openingWindow;
				dialog._refreshWindow = refreshWindow;

				dialog.after(
					'destroy',
					function(event) {
						dialog = null;

						delete Window._map[id];
					}
				);

				Liferay.after(
					'hashChange',
					function(event) {
						dialog.iframe.set('uri', event.uri);
					}
				);

				Liferay.after(
					'popupReady',
					function(event) {
						if (event.windowName == id) {
							event.dialog = dialog;
							event.details[0].dialog = dialog;

							if (event.doc) {
								Util.afterIframeLoaded(event);

								var dialogUtil = event.win.Liferay.Util;

								dialogUtil.Window._opener = openingWindow;

								dialogUtil.Window._name = id;
							}

							dialog.iframe.node.focus();
						}
					}
				);

				dialog.render();
			}
			else {
				if (!dialog.get('visible')) {
					dialog.show();

					dialog.iframe.node.focus();

					dialog.iframe.set('uri', uri);
				}

				dialog._syncUIPosAlign();
			}

			if (dialog.get('stack')) {
				A.DialogManager.bringToTop(dialog);
			}

			dialog.set('title', title);

			if (Lang.isFunction(callback)) {
				callback(dialog);
			}

			return dialog;
		};
	},
	'',
	{
		requires: ['aui-dialog', 'aui-dialog-iframe']
	}
);