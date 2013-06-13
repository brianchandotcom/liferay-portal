AUI.add(
	'liferay-dockbar-device-preview',
	function(A) {
		var AObject = A.Object;

		var Lang = A.Lang;

		var BODY = A.getBody();

		var Dockbar = Liferay.Dockbar;

		var CLASS_SELECTED = 'selected';

		var CSS_DEVICE_ITEM = '.lfr-device-item';

		var CSS_DEVICE_PREVIEW_CONTENT = '.device-preview-content';

		var CSS_SELECTED = '.' + CLASS_SELECTED;

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

		var STR_CLICK = 'click';

		var STR_INPUT = 'input';

		var STR_INPUT_HEIGHT = 'inputHeight';

		var STR_INPUT_WIDTH = 'inputWidth';

		var TPL_DEVICE_PREVIEW = '<div class="lfr-device-preview" />';

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

						instance._dialogId = A.guid();

						instance._devicePreviewContainer = instance.byId('devicePreviewContainer');
						instance._devicePreviewContent = instance._devicePreviewContainer.one(CSS_DEVICE_PREVIEW_CONTENT);
						instance._closePanelButton = instance._devicePreviewContainer.one('#closePanel');

						instance._devicePreviewNode = A.Node.create(Lang.sub(TPL_DEVICE_PREVIEW));
						BODY.append(instance._devicePreviewNode);

						var devices = instance.get('devices');

						AObject.each(
							devices,
							function (item, index, collection) {
								if (item.default) {
									instance._openDeviceDialog(item);
								}
							}
						);

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						var eventHandles = [
							instance._closePanelButton.on(STR_CLICK, instance._closePanel, instance),
							instance._devicePreviewContent.delegate(STR_CLICK, instance._onDeviceClick, CSS_DEVICE_ITEM, instance)
						]

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

						instance._eventHandles = eventHandles;
					},

					_closePanel: function() {
						var instance = this;
						
						Dockbar.togglePreviewPanel();
					},

					_normalizeDialogAttrs: function(width, height, autoWidth, autoHeight) {
						var instance = this;

						var dialogAttrs = {};

						if (!Lang.isNumber(width)) {
							var widthNode = A.one(width);

							if (widthNode) {
								width = widthNode.val();
							}
							else {
								width = instance._devicePreviewNode.width();
								autoWidth = true;
							}
						}

						if (!Lang.isNumber(height)) {
							var heightNode = A.one(height);

							if (heightNode) {
								height = heightNode.val();
							}
							else {
								height = instance._devicePreviewNode.height();
								autoHeight = true;
							}
						}

						return {
							autoHeight: autoHeight,
							autoWidth: autoWidth,
							height: height,
							width: width
						};
					},

					_openDeviceDialog: function(device) {
						var instance = this;

						var dialog = Liferay.Util.getWindow(instance._dialogId);

						var dialogAttrs = instance._normalizeDialogAttrs(device.width, device.height, false, false);

						if (!dialog) {
							var dialogConfig = {
								align: {
									node: instance._devicePreviewNode,
									points: DIALOG_ALIGN_POINTS
								},
								autoSizeNode: instance._devicePreviewNode,
								constrain: instance._devicePreviewNode,
								height: dialogAttrs.height,
								render: instance._devicePreviewNode,
								width: dialogAttrs.width
							};

							var uri = window.location.href;

							var path = window.location.pathname;

							if (A.UA && A.UA.ie < 10 && path === '/') {
								uri = uri + '?';
							}

							Liferay.Util.openWindow(
								{
									dialog: A.merge(DIALOG_DEFAULTS, dialogConfig),
									dialogIframe: DIALOG_IFRAME_DEFAULTS,
									id: instance._dialogId,
									uri: uri
								}
							);
						}
						else {
							dialog.setAttrs(dialogAttrs);
							dialog.align(instance._devicePreviewNode, DIALOG_ALIGN_POINTS);
							dialog.show();
						}
					},

					_onDeviceClick: function(event) {
						var instance = this;

						var deviceList = instance.get('devices');

						var deviceId = event.currentTarget.getData('device');

						var selectedDevice = deviceList[deviceId];

						if (selectedDevice) {
							instance._devicePreviewContainer.all(CSS_SELECTED).removeClass(CLASS_SELECTED);
							event.currentTarget.addClass(CLASS_SELECTED);

							instance._openDeviceDialog(selectedDevice);
						}
					},

					_onSizeInput: function(event) {
						var instance = this;

						instance._openDeviceDialog(
							{
								height: Lang.toInt(instance.get(STR_INPUT_HEIGHT).val()),
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
		requires: ['aui-dialog-iframe-deprecated', 'aui-event-input', 'aui-modal', 'liferay-portlet-base', 'liferay-util', 'liferay-util-window']
	}
);