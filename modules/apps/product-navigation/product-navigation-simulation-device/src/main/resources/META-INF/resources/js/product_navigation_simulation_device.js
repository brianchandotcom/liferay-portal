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
	(A) => {
		// eslint-disable-next-line @liferay/aui/no-object
		const AObject = A.Object;
		const Lang = A.Lang;

		const BODY = document.body;

		const CSS_SELECTED = 'selected';

		const DIALOG_ALIGN_POINTS = [
			A.WidgetPositionAlign.CC,
			A.WidgetPositionAlign.CC,
		];

		const DIALOG_DEFAULTS = {
			autoHeightRatio: 1,
			autoWidthRatio: 1,
			cssClass: 'lfr-device',
			modal: false,
			resizable: false,
		};

		const DIALOG_IFRAME_DEFAULTS = {
			closeOnEscape: false,
			gutter: {
				bottom: 0,
				left: 0,
				right: 0,
				top: 0,
			},
		};

		const SELECTOR_DEVICE_ITEM = '.lfr-device-item';

		const STR_BOUNDING_BOX = 'boundingBox';

		const STR_CLICK = 'click';

		const STR_DEVICE = 'device';

		const STR_DEVICES = 'devices';

		const STR_HIDE = 'hide';

		const STR_INPUT = 'input';

		const STR_INPUT_HEIGHT = 'inputHeight';

		const STR_INPUT_WIDTH = 'inputWidth';

		const STR_PREVENT_TRANSITION = 'preventTransition';

		const STR_ROTATED = 'rotated';

		const TPL_DEVICE_SIZE_INFO = '{width} x {height}';

		const TPL_DEVICE_SIZE_STATUS =
			'<div class="lfr-device-size-status">' +
			'<span class="lfr-device-size-status-content"></span>' +
			'</div>';

		const WIN = A.config.win;

		const RESIZABLE_DEVICE_CSS_CLASS = 'resizable-device';

		const createIframeURL = () => {
			const url = new URL(WIN.location.href);
			const searchParams = new URLSearchParams(url.search);
			if (searchParams.has('segmentsExperienceId')) {
				searchParams.delete('segmentsExperienceId');
			}
			searchParams.append('p_l_mode', 'preview');

			return `${url.origin}${url.pathname}?${searchParams.toString()}`;
		};

		const SimulationDevice = A.Component.create({
			ATTRS: {
				devices: {
					validator: Lang.isObject,
				},

				inputHeight: {
					setter: A.one,
				},

				inputWidth: {
					setter: A.one,
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'simulationdevice',

			prototype: {
				_bindUI() {
					const instance = this;

					const eventHandles = instance._eventHandles;

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

					const inputWidth = instance.get(STR_INPUT_WIDTH);

					if (inputWidth) {
						eventHandles.push(
							inputWidth.on(
								STR_INPUT,
								instance._onSizeInput,
								instance
							)
						);
					}

					const inputHeight = instance.get(STR_INPUT_HEIGHT);

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
					const instance = this;

					let dialogAutoHeight = false;
					let dialogAutoWidth = false;

					let dialogHeight = device.height;
					let dialogWidth = device.width;

					if (rotation) {
						dialogHeight = device.width;
						dialogWidth = device.height;
					}

					if (!Lang.isNumber(dialogWidth)) {
						const widthNode = A.one(dialogWidth);

						if (widthNode) {
							dialogWidth = widthNode.val();
						}
						else {
							dialogWidth =
								instance._simulationDeviceNode.offsetWidth;

							dialogAutoWidth = true;
						}
					}

					if (!Lang.isNumber(dialogHeight)) {
						const heightNode = A.one(dialogHeight);

						if (heightNode) {
							dialogHeight = heightNode.val();
						}
						else {
							dialogHeight =
								instance._simulationDeviceNode.offsetHeight;

							dialogAutoHeight = true;
						}
					}

					return {
						autoHeight: dialogAutoHeight,
						autoWidth: dialogAutoWidth,
						resizable: device.resizable,
						size: {
							height: dialogHeight,
							width: dialogWidth,
						},
					};
				},

				_onDeviceClick(event) {
					const instance = this;

					const deviceList = instance.get(STR_DEVICES);

					const deviceItem = event.currentTarget;

					const deviceId = deviceItem.getData(STR_DEVICE);

					const device = deviceList[deviceId];

					instance._selectedDevice = device;

					if (device) {
						if (
							deviceItem.hasClass(CSS_SELECTED) &&
							device.rotation
						) {
							deviceItem.toggleClass(STR_ROTATED);

							const icon = deviceItem.one('.icon');

							if (icon) {
								icon.toggleClass(STR_HIDE);
							}

							const iconRotate = deviceItem.one('.icon-rotate');

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
					const instance = this;

					const eventInfo = event.info;

					const offsetHeight = eventInfo.offsetHeight;
					const offsetWidth = eventInfo.offsetWidth;

					const inputHeight = instance.get(STR_INPUT_HEIGHT);

					if (inputHeight) {
						inputHeight.val(offsetHeight);
					}

					const inputWidth = instance.get(STR_INPUT_WIDTH);

					if (inputWidth) {
						inputWidth.val(offsetWidth);
					}

					const info = Lang.sub(TPL_DEVICE_SIZE_INFO, {
						height: offsetHeight,
						width: offsetWidth,
					});

					instance._sizeStatusContent.html(info);
				},

				_onResizeEnd() {
					const instance = this;

					instance._sizeStatus.hide();
				},

				_onResizeStart() {
					const instance = this;

					let sizeStatus = instance._sizeStatus;

					let sizeStatusContent = instance._sizeStatusContent;

					const dialog = Liferay.Util.getWindow(instance._dialogId);

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

					const deviceSizeInfo = Lang.sub(TPL_DEVICE_SIZE_INFO, {
						height: dialog.get('height'),
						width: dialog.get('width'),
					});

					sizeStatusContent.html(deviceSizeInfo);

					sizeStatus.show();
				},

				_onSizeInput() {
					const instance = this;

					const inputHeight = instance.get(STR_INPUT_HEIGHT).val();
					const inputWidth = instance.get(STR_INPUT_WIDTH).val();

					const height = Lang.toInt(inputHeight);
					const width = Lang.toInt(inputWidth);

					Liferay.Util.getWindow(instance._dialogId);

					instance._openDeviceDialog({
						height,
						resizable: true,
						width,
					});
				},

				_openDeviceDialog(device, rotation) {
					const instance = this;

					const dialog = Liferay.Util.getWindow(instance._dialogId);

					const dialogAttrs = instance._normalizeDialogAttrs(
						device,
						rotation
					);

					const simulationDeviceNode = instance._simulationDeviceNode;

					const height = dialogAttrs.size.height;
					const width = dialogAttrs.size.width;

					if (!dialog) {
						const dialogConfig = {
							align: {
								node: simulationDeviceNode,
								points: DIALOG_ALIGN_POINTS,
							},
							autoSizeNode: simulationDeviceNode,
							constrain: simulationDeviceNode,
							height,
							hideOn: [],
							render: simulationDeviceNode,
							width,
						};

						Liferay.Util.openWindow(
							{
								cache: false,
								dialog: {
									...DIALOG_DEFAULTS,
									...dialogConfig,
								},
								dialogIframe: DIALOG_IFRAME_DEFAULTS,
								id: instance._dialogId,
								iframeId: 'simulationDeviceIframe',
								title: Liferay.Language.get(
									'simulation-preview'
								),
								uri: createIframeURL(),
							},
							(dialogWindow) => {
								const dialogBoundingBox = dialogWindow.get(
									STR_BOUNDING_BOX
								);

								dialogBoundingBox.removeClass(
									RESIZABLE_DEVICE_CSS_CLASS
								);

								dialogWindow.align(
									simulationDeviceNode,
									DIALOG_ALIGN_POINTS
								);

								dialogWindow.plug(A.Plugin.SizeAnim, {
									after: {
										end() {
											const selectedDevice =
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
												(item) => {
													if (item.skin) {
														dialogBoundingBox.removeClass(
															item.skin
														);
													}
												}
											);
										},
									},
									align: true,
									preventTransition: true,
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
										),
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
						const dialogBoundingBox = dialog.get(STR_BOUNDING_BOX);

						dialogBoundingBox.toggleClass(STR_ROTATED, rotation);
						dialogBoundingBox.removeClass(
							RESIZABLE_DEVICE_CSS_CLASS
						);

						if (!device.preventTransition) {
							dialog.sizeanim.set(STR_PREVENT_TRANSITION, false);
						}

						if (device.resizable && !device.skin) {
							dialogBoundingBox.addClass(
								RESIZABLE_DEVICE_CSS_CLASS
							);
						}

						dialog.setAttrs(dialogAttrs);

						dialog.iframe.node.setStyles({
							height,
							width,
						});

						dialog.show();
					}

					instance._selectedDevice = device;
				},

				destructor() {
					const instance = this;

					new A.EventHandle(instance._eventHandles).detach();

					instance._simulationDeviceNode.remove();
				},

				hideDeviceDialog() {
					const instance = this;

					const dialog = Liferay.Util.getWindow(instance._dialogId);

					dialog.hide();
				},

				initializer() {
					const instance = this;

					instance._eventHandles = [];

					instance._dialogId = A.guid();

					instance._simulationDeviceNode = document.createElement(
						'div'
					);

					instance._simulationDeviceNode.className =
						'lfr-simulation-device';

					BODY.appendChild(instance._simulationDeviceNode);

					const devices = instance.get('devices');

					AObject.some(devices, (item) => {
						const selected = item.selected;

						if (selected) {
							instance._openDeviceDialog(item);
						}

						return selected;
					});

					const simulationDeviceContainer = instance.byId(
						'simulationDeviceContainer'
					);

					instance._deviceItems = simulationDeviceContainer.all(
						SELECTOR_DEVICE_ITEM
					);

					instance._simulationDeviceContainer = simulationDeviceContainer;

					instance._bindUI();
				},

				showDeviceDialog() {
					const instance = this;

					instance._simulationDeviceNode.remove();

					BODY.appendChild(instance._simulationDeviceNode);

					const dialog = Liferay.Util.getWindow(instance._dialogId);

					dialog.show();
				},
			},
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
			'liferay-widget-size-animation-plugin',
		],
	}
);
