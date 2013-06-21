AUI.add(
	'liferay-dockbar-device-preview',
	function(A) {
		var AObject = A.Object;
		var AUA = A.UA;
		var WIN = A.config.win;

		var Lang = A.Lang;

		var BODY = A.getBody();

		var Dockbar = Liferay.Dockbar;

		var CSS_SELECTED = 'selected';

		var DIALOG_ALIGN_POINTS = [A.WidgetPositionAlign.CC, A.WidgetPositionAlign.CC];

		var DIALOG_DEFAULTS = {
			autoHeightRatio: 1,
			autoWidthRatio: 1,
			cssClass: 'lfr-device',
			modal: false,
			resizable: false
		};

		var DIALOG_IFRAME_DEFAULTS = {
			gutter: {
				bottom: 0,
				left: 0,
				right: 0,
				top: 0
			}
		};

		var SELECTOR_DEVICE_ITEM = '.lfr-device-item';

		var SELECTOR_DEVICE_PREVIEW_CONTENT = '.device-preview-content';

		var SELECTOR_SELECTED = '.' + CSS_SELECTED;

		var STR_CLICK = 'click';

		var STR_DEVICE = 'device';

		var STR_DEVICES = 'devices';

		var STR_INPUT = 'input';

		var STR_INPUT_HEIGHT = 'inputHeight';

		var STR_INPUT_WIDTH = 'inputWidth';

		var STR_PREVENT_TRANSITION = 'preventTransition';

		var STR_ROTATED = 'rotated';

		var TPL_DEVICE_PREVIEW = '<div class="lfr-device-preview" />';

		var TPL_DEVICE_SIZE_INFO = '{width} x {height}';

		var TPL_DEVICE_SIZE_STATUS = '<div class="lfr-device-size-status"><span class="lfr-device-size-status-content"></span></div>';

		var DevicePreview = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'devicepreview',

				ATTRS: {
					devices: {
						validator: Lang.isObject
					},
					inputHeight: {
						setter: A.one
					},
					inputWidth: {
						setter: A.one
					}
				},

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._eventHandles = [];

						instance._dialogId = A.guid();

						var devicePreviewContainer = instance.byId('devicePreviewContainer');

						instance._devicePreviewContent = devicePreviewContainer.one(SELECTOR_DEVICE_PREVIEW_CONTENT);
						instance._closePanelButton = devicePreviewContainer.one('#closePanel');

						instance._devicePreviewNode = A.Node.create(Lang.sub(TPL_DEVICE_PREVIEW));
						instance._deviceSkin = A.Node.create('<div class="lfr-device-skin smartphone"></div>');
						BODY.append(instance._devicePreviewNode);

						var devices = instance.get('devices');

						AObject.some(
							devices,
							function(item, index, collection) {
								var selected = item.selected;

								if (selected) {
									instance._openDeviceDialog(item);
								}

								return selected;
							}
						);

						instance._deviceItems = devicePreviewContainer.all(SELECTOR_DEVICE_ITEM);

						instance._devicePreviewContainer = devicePreviewContainer;

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						var eventHandles = instance._eventHandles;

						eventHandles.push(
							instance._closePanelButton.on(STR_CLICK, instance._closePanel, instance),
							instance._devicePreviewContent.delegate(STR_CLICK, instance._onDeviceClick, SELECTOR_DEVICE_ITEM, instance)
						);

						var inputWidth = instance.get(STR_INPUT_WIDTH);

						if (inputWidth) {
							eventHandles.push(
								inputWidth.on(STR_INPUT, instance._onSizeInput, instance)
							);
						}

						var inputHeight = instance.get(STR_INPUT_HEIGHT);

						if (inputHeight) {
							eventHandles.push(
								inputHeight.on(STR_INPUT, instance._onSizeInput, instance)
							);
						}
					},

					_closePanel: function() {
						var instance = this;

						Dockbar.togglePreviewPanel();
					},

					_normalizeDialogAttrs: function(device, rotation) {
						var instance = this;

						var dialogAutoHeight = false;

						var dialogAutoWidth = false;

						var dialogHeight = rotation ? device.width : device.height;

						var dialogWidth = rotation ? device.height : device.width;

						var dialogAttrs = {};

						if (!Lang.isNumber(dialogWidth)) {
							var widthNode = A.one(dialogWidth);

							if (widthNode) {
								dialogWidth = widthNode.val();
							}
							else {
								dialogWidth = instance._devicePreviewNode.width();
								dialogAutoWidth = true;
							}
						}

						if (!Lang.isNumber(dialogHeight)) {
							var heightNode = A.one(dialogHeight);

							if (heightNode) {
								dialogHeight = heightNode.val();
							}
							else {
								dialogHeight = instance._devicePreviewNode.height();
								dialogAutoHeight = true;
							}
						}

						return {
							autoHeight: dialogAutoHeight,
							autoWidth: dialogAutoWidth,
							resizable: device.resizable,
							size: {
								height: dialogHeight,
								width: dialogWidth
							}
						};
					},

					_openDeviceDialog: function(device, rotation) {
						var instance = this;

						var dialog = Liferay.Util.getWindow(instance._dialogId);

						var dialogAttrs = instance._normalizeDialogAttrs(device, rotation);

						if (!dialog) {
							var dialogConfig = {
								align: {
									node: instance._devicePreviewNode,
									points: DIALOG_ALIGN_POINTS
								},
								autoSizeNode: instance._devicePreviewNode,
								constrain: instance._devicePreviewNode,
								height: dialogAttrs.size.height,
								render: instance._devicePreviewNode,
								width: dialogAttrs.size.width
							};

							var uri = WIN.location.href;

							var path = WIN.location.pathname;

							if (AUA.ie && AUA.ie < 10 && path === '/') {
								uri += '?';
							}

							Liferay.Util.openWindow(
								{
									dialog: A.merge(DIALOG_DEFAULTS, dialogConfig),
									dialogIframe: DIALOG_IFRAME_DEFAULTS,
									id: instance._dialogId,
									uri: uri
								},
								function(dialogWindow) {
									dialogWindow.align(instance._devicePreviewNode, DIALOG_ALIGN_POINTS);

									dialogWindow.plug(
										A.Plugin.SizeAnim,
										{
											align: true,
											after: {
												end: function(event) {
													instance._deviceSkin.addClass(instance._selectedDevice.skin);

													dialogWindow.sizeanim.set(STR_PREVENT_TRANSITION, instance._selectedDevice.preventTransition || false);
												},
												start: function(event) {
													instance._deviceSkin.set('className', 'lfr-device-skin');
												}
											},
											preventTransition: true
										}
									);

									instance._eventHandles.push(
										dialogWindow.on('resize:end', instance._onResizeEnd, instance),
										dialogWindow.on('resize:resize', instance._onResize, instance),
										dialogWindow.on('resize:start', instance._onResizeStart, instance)
									);

									dialogWindow.get('boundingBox').prepend(instance._deviceSkin);
								}
							);
						}
						else {
							if (!device.preventTransition) {
								dialog.sizeanim.set(STR_PREVENT_TRANSITION, false);
							}

							dialog.setAttrs(dialogAttrs);

							dialog.show();
						}

						if (rotation) {
							instance._deviceSkin.addClass('rotated');
						}
						else {
							instance._deviceSkin.removeClass('rotated');
						}

						instance._selectedDevice = device;
					},

					_onDeviceClick: function(event) {
						var instance = this;

						var deviceList = instance.get(STR_DEVICES);

						var deviceItem = event.currentTarget;

						var deviceId = deviceItem.getData(STR_DEVICE);

						var device = deviceList[deviceId];

						instance._selectedDevice = device;

						if (device) {
							var deviceSelected = deviceItem.hasClass(CSS_SELECTED);

							instance._deviceItems.removeClass(CSS_SELECTED);

							if (deviceSelected && deviceItem.hasClass('lfr-device-rotation')) {
								deviceItem.toggleClass(STR_ROTATED);
							}

							deviceItem.addClass(CSS_SELECTED);

							instance._openDeviceDialog(device, deviceItem.hasClass(STR_ROTATED));
						}
					},

					_onResize: function(event) {
						var instance = this;

						var inputHeight = instance.get(STR_INPUT_HEIGHT);

						if (inputHeight) {
							inputHeight.val(event.info.offsetHeight);
						}

						var inputWidth = instance.get(STR_INPUT_WIDTH);

						if (inputWidth) {
							inputWidth.val(event.info.offsetWidth);
						}

						var info = Lang.sub(
							TPL_DEVICE_SIZE_INFO,
							{
								height: event.info.offsetHeight,
								width: event.info.offsetWidth
							}
						);

						instance._sizeStatusContent.html(info);
					},

					_onResizeEnd: function(event) {
						var instance = this;

						instance._sizeStatus.hide();
					},

					_onResizeStart: function(event) {
						var instance = this;

						var sizeStatus = instance._sizeStatus;

						var sizeStatusContent = instance._sizeStatusContent;

						var dialog = Liferay.Util.getWindow(instance._dialogId);

						if (!sizeStatus) {
							sizeStatus = A.Node.create(TPL_DEVICE_SIZE_STATUS);

							dialog.get('boundingBox').append(sizeStatus);

							sizeStatusContent = sizeStatus.one('.lfr-device-size-status-content');

							instance._sizeStatus = sizeStatus;

							instance._sizeStatusContent = sizeStatusContent;
						}

						sizeStatus.set('className', 'lfr-device-size-status');

						var activehandle = dialog.resize.get('activeHandle');

						sizeStatus.addClass(activehandle);

						var deviceSizeInfo = Lang.sub(
							TPL_DEVICE_SIZE_INFO,
							{
								height: dialog.get('height'),
								width: dialog.get('width')
							}
						);

						sizeStatusContent.html(deviceSizeInfo);

						sizeStatus.show();
					},

					_onSizeInput: function(event) {
						var instance = this;

						instance._openDeviceDialog(
							{
								height: Lang.toInt(instance.get(STR_INPUT_HEIGHT).val()),
								resizable: true,
								width: Lang.toInt(instance.get(STR_INPUT_WIDTH).val())
							}
						);
					}
				}
			}
		);

		Dockbar.DevicePreview = DevicePreview;
	},
	'',
	{
		requires: ['aui-dialog-iframe-deprecated', 'aui-event-input', 'aui-modal', 'liferay-portlet-base', 'liferay-util-window', 'liferay-widget-size-animation-plugin']
	}
);