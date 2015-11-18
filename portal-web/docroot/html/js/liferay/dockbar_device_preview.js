AUI.add(
	'liferay-dockbar-device-preview',
	function(A) {
		var AObject = A.Object;
		var Dockbar = Liferay.Dockbar;
		var Lang = A.Lang;
		var Util = Liferay.Util;

		var BODY = A.getBody();

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

		var STR_BOUNDING_BOX = 'boundingBox';

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

		var WIN = A.config.win;

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

						instance._closePanelButton = instance.byId('closePanelPreview');

						instance._devicePreviewNode = A.Node.create(Lang.sub(TPL_DEVICE_PREVIEW));

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

						var devicePreviewContainer = instance.byId('devicePreviewContainer');

						instance._deviceItems = devicePreviewContainer.all(SELECTOR_DEVICE_ITEM);

						instance._devicePreviewContainer = devicePreviewContainer;

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();

						instance._devicePreviewNode.remove();
					},

					_bindUI: function() {
						var instance = this;

						var eventHandles = instance._eventHandles;

						eventHandles.push(
							instance._closePanelButton.on(STR_CLICK, instance._closePanel, instance),
							instance._devicePreviewContainer.delegate(STR_CLICK, instance._onDeviceClick, SELECTOR_DEVICE_ITEM, instance)
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

						var dialogHeight = device.height;
						var dialogWidth = device.width;

						if (rotation) {
							dialogHeight = device.width;
							dialogWidth = device.height;
						}

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

					_onDeviceClick: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var deviceList = instance.get(STR_DEVICES);

						var deviceId = currentTarget.getData(STR_DEVICE);

						var device = deviceList[deviceId];

						instance._selectedDevice = device;

						if (device) {
							if (currentTarget.hasClass(CSS_SELECTED) && device.rotation) {
								currentTarget.toggleClass(STR_ROTATED);
							}

							currentTarget.radioClass(CSS_SELECTED);

							instance._openDeviceDialog(device, currentTarget.hasClass(STR_ROTATED));
						}
					},

					_onDialogVisibleChange: function(event) {
						var instance = this;

						if (!event.newVal) {
							instance._closePanel();
						}

						event.preventDefault();
					},

					_onResize: function(event) {
						var instance = this;

						var height = instance._setHeight(event);
						var width = instance._setWidth(event);

						instance._updateSizeInputFields(height, width);

						var info = Lang.sub(
							TPL_DEVICE_SIZE_INFO,
							{
								height: height,
								width: width
							}
						);

						instance._sizeStatusContent.html(info);
					},

					_onResizeEnd: function(event) {
						var instance = this;

						var height = instance._setHeight(event);
						var width = instance._setWidth(event);

						instance._updateSizeInputFields(height, width);

						instance._sizeStatus.hide();
					},

					_onResizeStart: function(event) {
						var instance = this;

						var sizeStatus = instance._sizeStatus;

						var sizeStatusContent = instance._sizeStatusContent;

						var dialog = Util.getWindow(instance._dialogId);

						if (!sizeStatus) {
							sizeStatus = A.Node.create(TPL_DEVICE_SIZE_STATUS);

							dialog.get(STR_BOUNDING_BOX).append(sizeStatus);

							sizeStatusContent = sizeStatus.one('.lfr-device-size-status-content');

							instance._sizeStatus = sizeStatus;

							instance._sizeStatusContent = sizeStatusContent;
						}

						sizeStatus.attr('className', 'lfr-device-size-status');

						sizeStatus.addClass(dialog.resize.get('activeHandle'));

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

						var inputHeight = instance.get(STR_INPUT_HEIGHT).val();
						var inputWidth = instance.get(STR_INPUT_WIDTH).val();

						var height = Lang.toInt(inputHeight);
						var width = Lang.toInt(inputWidth);

						instance._openDeviceDialog(
							{
								height: height,
								resizable: true,
								width: width
							}
						);

						instance._resizeIframe(height, width);
					},

					_openDeviceDialog: function(device, rotation) {
						var instance = this;

						var dialog = Util.getWindow(instance._dialogId);

						var dialogAttrs = instance._normalizeDialogAttrs(device, rotation);

						var devicePreviewNode = instance._devicePreviewNode;

						if (!dialog) {
							var dialogConfig = {
								align: {
									node: devicePreviewNode,
									points: DIALOG_ALIGN_POINTS
								},
								autoSizeNode: devicePreviewNode,
								constrain: devicePreviewNode,
								height: dialogAttrs.size.height,
								render: devicePreviewNode,
								width: dialogAttrs.size.width
							};

							Util.openWindow(
								{
									cache: false,
									dialog: A.merge(DIALOG_DEFAULTS, dialogConfig),
									dialogIframe: DIALOG_IFRAME_DEFAULTS,
									id: instance._dialogId,
									iframeId: 'devicePreviewIframe',
									uri: WIN.location.href
								},
								function(dialogWindow) {
									var dialogBoundingBox = dialogWindow.get(STR_BOUNDING_BOX);

									dialogWindow.align(devicePreviewNode, DIALOG_ALIGN_POINTS);

									dialogWindow.plug(
										A.Plugin.SizeAnim,
										{
											align: true,
											after: {
												end: function(event) {
													var selectedDevice = instance._selectedDevice;

													if (selectedDevice.skin) {
														dialogBoundingBox.addClass(selectedDevice.skin);
													}

													dialogWindow.sizeanim.set(STR_PREVENT_TRANSITION, selectedDevice.preventTransition || false);
												},
												start: function(event) {
													AObject.each(
														instance.get(STR_DEVICES),
														function(item, index, collection) {
															if (item.skin) {
																dialogBoundingBox.removeClass(item.skin);
															}
														}
													);
												}
											},
											preventTransition: true
										}
									);

									dialogBoundingBox.addClass(device.skin);

									instance._eventHandles.push(
										dialogWindow.on(
											{
												'resize:end': A.bind('_onResizeEnd', instance),
												'resize:resize': A.bind('_onResize', instance),
												'resize:start': A.bind('_onResizeStart', instance),
												'visibleChange': A.bind('_onDialogVisibleChange', instance)
											}
										)
									);
								}
							);
						}
						else {
							var dialogBoundingBox = dialog.get(STR_BOUNDING_BOX);

							dialogBoundingBox.toggleClass(STR_ROTATED, rotation);

							if (!device.preventTransition) {
								dialog.sizeanim.set(STR_PREVENT_TRANSITION, false);
							}

							dialog.setAttrs(dialogAttrs);

							dialog.show();
						}

						instance._selectedDevice = device;
					},

					_resizeIframe: function(height, width) {
						var instance = this;

						var dialog = Util.getWindow(instance._dialogId);

						dialog.iframe.node.setStyles(
							{
								height: height,
								width: width
							}
						);
					},

					_setHeight: function(event) {
						var instance = this;

						return event.info.offsetHeight - event.target.totalHSurrounding;
					},

					_setWidth: function(event) {
						var instance = this;

						return event.info.offsetWidth - event.target.totalVSurrounding;
					},

					_updateSizeInputFields: function(offsetHeight, offsetWidth) {
						var instance = this;

						var inputHeight = instance.get(STR_INPUT_HEIGHT);

						if (inputHeight) {
							inputHeight.val(offsetHeight);
						}

						var inputWidth = instance.get(STR_INPUT_WIDTH);

						if (inputWidth) {
							inputWidth.val(offsetWidth);
						}

						instance._resizeIframe(offsetHeight, offsetWidth);
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