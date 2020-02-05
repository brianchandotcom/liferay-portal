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
	'liferay-product-navigation-simulation-device',
	A => {
		var AObject = A.Object;
		var Lang = A.Lang;

		var BODY = A.getBody();

		var CSS_SELECTED = 'selected';

		var DIALOG_ALIGN_POINTS = [
			A.WidgetPositionAlign.CC,
			A.WidgetPositionAlign.CC
		];

		var DIALOG_DEFAULTS = {
			autoHeightRatio: 1,
			autoWidthRatio: 1,
			cssClass: 'lfr-device',
			modal: false,
			resizable: false
		};

		var DIALOG_IFRAME_DEFAULTS = {
			closeOnEscape: false,
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

		var STR_HIDE = 'hide';

		var STR_INPUT = 'input';

		var STR_INPUT_HEIGHT = 'inputHeight';

		var STR_INPUT_WIDTH = 'inputWidth';

		var STR_PREVENT_TRANSITION = 'preventTransition';

		var STR_ROTATED = 'rotated';

		var TPL_DEVICE_SIZE_INFO = '{width} x {height}';

		var TPL_DEVICE_SIZE_STATUS =
			'<div class="lfr-device-size-status">' +
			'<span class="lfr-device-size-status-content"></span>' +
			'</div>';

		var TPL_SIMULATION_DEVICE = '<div class="lfr-simulation-device" />';

		var WIN = A.config.win;

		var SimulationDevice = A.Component.create({
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

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'simulationdevice',

			prototype: {
				_bindUI() {
					var instance = this;

					var eventHandles = instance._eventHandles;

					eventHandles.push(
						Liferay.on(
							'SimulationMenu:closeSimulationPanel',
							A.bind('hideDeviceDialog', instance)
						),
						Liferay.on(
							'SimulationMenu:openSimulationPanel',
							A.bind('showDeviceDialog', instance)
						),
						instance._simulationDeviceContainer.delegate(
							STR_CLICK,
							instance._onDeviceClick,
							SELECTOR_DEVICE_ITEM,
							instance
						)
					);

					var inputWidth = instance.get(STR_INPUT_WIDTH);

					if (inputWidth) {
						eventHandles.push(
							inputWidth.on(
								STR_INPUT,
								instance._onSizeInput,
								instance
							)
						);
					}

					var inputHeight = instance.get(STR_INPUT_HEIGHT);

					if (inputHeight) {
						eventHandles.push(
							inputHeight.on(
								STR_INPUT,
								instance._onSizeInput,
								instance
							)
						);
					}
				},

				_normalizeDialogAttrs(device, rotation) {
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
							dialogWidth = instance._simulationDeviceNode.width();

							dialogAutoWidth = true;
						}
					}

					if (!Lang.isNumber(dialogHeight)) {
						var heightNode = A.one(dialogHeight);

						if (heightNode) {
							dialogHeight = heightNode.val();
						}
						else {
							dialogHeight = instance._simulationDeviceNode.height();

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

				_onDeviceClick(event) {
					var instance = this;

					var deviceList = instance.get(STR_DEVICES);

					var deviceItem = event.currentTarget;

					var deviceId = deviceItem.getData(STR_DEVICE);

					var device = deviceList[deviceId];

					instance._selectedDevice = device;

					if (device) {
						if (
							deviceItem.hasClass(CSS_SELECTED) &&
							device.rotation
						) {
							deviceItem.toggleClass(STR_ROTATED);

							var icon = deviceItem.one('.icon');

							if (icon) {
								icon.toggleClass(STR_HIDE);
							}

							var iconRotate = deviceItem.one('.icon-rotate');

							if (iconRotate) {
								iconRotate.toggleClass(STR_HIDE);
							}
						}

						instance._deviceItems.removeClass(CSS_SELECTED);

						deviceItem.addClass(CSS_SELECTED);

						instance._openDeviceDialog(
							device,
							deviceItem.hasClass(STR_ROTATED)
						);
					}
				},

				_onResize(event) {
					var instance = this;

					var eventInfo = event.info;

					var offsetHeight = eventInfo.offsetHeight;
					var offsetWidth = eventInfo.offsetWidth;

					var inputHeight = instance.get(STR_INPUT_HEIGHT);

					if (inputHeight) {
						inputHeight.val(offsetHeight);
					}

					var inputWidth = instance.get(STR_INPUT_WIDTH);

					if (inputWidth) {
						inputWidth.val(offsetWidth);
					}

					var info = Lang.sub(TPL_DEVICE_SIZE_INFO, {
						height: offsetHeight,
						width: offsetWidth
					});

					instance._sizeStatusContent.html(info);
				},

				_onResizeEnd() {
					var instance = this;

					instance._sizeStatus.hide();
				},

				_onResizeStart() {
					var instance = this;

					var sizeStatus = instance._sizeStatus;

					var sizeStatusContent = instance._sizeStatusContent;

					var dialog = Liferay.Util.getWindow(instance._dialogId);

					if (!sizeStatus) {
						sizeStatus = A.Node.create(TPL_DEVICE_SIZE_STATUS);

						dialog.get(STR_BOUNDING_BOX).append(sizeStatus);

						sizeStatusContent = sizeStatus.one(
							'.lfr-device-size-status-content'
						);

						instance._sizeStatus = sizeStatus;

						instance._sizeStatusContent = sizeStatusContent;
					}

					sizeStatus.attr('className', 'lfr-device-size-status');

					sizeStatus.addClass(dialog.resize.get('activeHandle'));

					var deviceSizeInfo = Lang.sub(TPL_DEVICE_SIZE_INFO, {
						height: dialog.get('height'),
						width: dialog.get('width')
					});

					sizeStatusContent.html(deviceSizeInfo);

					sizeStatus.show();
				},

				_onSizeInput() {
					var instance = this;

					var inputHeight = instance.get(STR_INPUT_HEIGHT).val();
					var inputWidth = instance.get(STR_INPUT_WIDTH).val();

					var height = Lang.toInt(inputHeight);
					var width = Lang.toInt(inputWidth);

					Liferay.Util.getWindow(instance._dialogId);

					instance._openDeviceDialog({
						height,
						resizable: true,
						width
					});
				},

				_openDeviceDialog(device, rotation) {
					var instance = this;

					var dialog = Liferay.Util.getWindow(instance._dialogId);

					var dialogAttrs = instance._normalizeDialogAttrs(
						device,
						rotation
					);

					var simulationDeviceNode = instance._simulationDeviceNode;

					var height = dialogAttrs.size.height;
					var width = dialogAttrs.size.width;

					if (!dialog) {
						var dialogConfig = {
							align: {
								node: simulationDeviceNode,
								points: DIALOG_ALIGN_POINTS
							},
							autoSizeNode: simulationDeviceNode,
							constrain: simulationDeviceNode,
							height,
							hideOn: [],
							render: simulationDeviceNode,
							width
						};

						Liferay.Util.openWindow(
							{
								cache: false,
								dialog: A.merge(DIALOG_DEFAULTS, dialogConfig),
								dialogIframe: DIALOG_IFRAME_DEFAULTS,
								id: instance._dialogId,
								iframeId: 'simulationDeviceIframe',
								title: Liferay.Language.get(
									'simulation-peview'
								),
								uri: Liferay.Util.addParams(
									'p_l_mode=preview',
									WIN.location.href
								)
							},
							dialogWindow => {
								var dialogBoundingBox = dialogWindow.get(
									STR_BOUNDING_BOX
								);

								dialogWindow.align(
									simulationDeviceNode,
									DIALOG_ALIGN_POINTS
								);

								dialogWindow.plug(A.Plugin.SizeAnim, {
									after: {
										end() {
											var selectedDevice =
												instance._selectedDevice;

											if (selectedDevice.skin) {
												dialogBoundingBox.addClass(
													selectedDevice.skin
												);
											}

											dialogWindow.sizeanim.set(
												STR_PREVENT_TRANSITION,
												selectedDevice.preventTransition ||
													false
											);
										},
										start() {
											AObject.each(
												instance.get(STR_DEVICES),
												item => {
													if (item.skin) {
														dialogBoundingBox.removeClass(
															item.skin
														);
													}
												}
											);
										}
									},
									align: true,
									preventTransition: true
								});

								dialogBoundingBox.addClass(device.skin);

								instance._eventHandles.push(
									dialogWindow.on({
										'resize:end': A.bind(
											'_onResizeEnd',
											instance
										),
										'resize:resize': A.bind(
											'_onResize',
											instance
										),
										'resize:start': A.bind(
											'_onResizeStart',
											instance
										)
									}),
									instance.on(
										'destroy',
										A.bind('destroy', dialogWindow)
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

						dialog.iframe.node.setStyles({
							height,
							width
						});

						dialog.show();
					}

					instance._selectedDevice = device;
				},

				destructor() {
					var instance = this;

					new A.EventHandle(instance._eventHandles).detach();

					instance._simulationDeviceNode.remove();
				},

				hideDeviceDialog() {
					var instance = this;

					var dialog = Liferay.Util.getWindow(instance._dialogId);

					dialog.hide();
				},

				initializer() {
					var instance = this;

					instance._eventHandles = [];

					instance._dialogId = A.guid();

					instance._simulationDeviceNode = A.Node.create(
						Lang.sub(TPL_SIMULATION_DEVICE)
					);

					BODY.append(instance._simulationDeviceNode);

					var devices = instance.get('devices');

					AObject.some(devices, item => {
						var selected = item.selected;

						if (selected) {
							instance._openDeviceDialog(item);
						}

						return selected;
					});

					var simulationDeviceContainer = instance.byId(
						'simulationDeviceContainer'
					);

					instance._deviceItems = simulationDeviceContainer.all(
						SELECTOR_DEVICE_ITEM
					);

					instance._simulationDeviceContainer = simulationDeviceContainer;

					instance._bindUI();
				},

				showDeviceDialog() {
					var instance = this;

					var dialog = Liferay.Util.getWindow(instance._dialogId);

					dialog.show();
				}
			}
		});

		Liferay.SimulationDevice = SimulationDevice;
	},
	'',
	{
		requires: [
			'aui-dialog-iframe-deprecated',
			'aui-event-input',
			'aui-modal',
			'liferay-portlet-base',
			'liferay-product-navigation-control-menu',
			'liferay-util-window',
			'liferay-widget-size-animation-plugin'
		]
	}
);
