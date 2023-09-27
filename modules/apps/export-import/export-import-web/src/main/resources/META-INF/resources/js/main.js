/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-export-import-export-import',
	(A) => {
		const Lang = A.Lang;

		const ADate = A.Date;

		const FAILURE_TIMEOUT = 10000;

		const RENDER_INTERVAL_IDLE = 60000;

		const RENDER_INTERVAL_IN_PROGRESS = 2000;

		const STR_CHECKED = 'checked';

		const STR_CLICK = 'click';

		const STR_EMPTY = '';

		const STR_HIDE = 'hide';

		const defaultConfig = {
			setter: '_setNode',
		};

		const ExportImport = A.Component.create({
			ATTRS: {
				archivedSetupsNode: defaultConfig,
				commentsNode: defaultConfig,
				deletionsNode: defaultConfig,
				exportLAR: defaultConfig,
				form: defaultConfig,
				incompleteProcessMessageNode: defaultConfig,
				locale: STR_EMPTY,
				processesNode: defaultConfig,
				rangeAllNode: defaultConfig,
				rangeDateRangeNode: defaultConfig,
				rangeLastNode: defaultConfig,
				rangeLastPublishNode: defaultConfig,
				ratingsNode: defaultConfig,
				setupNode: defaultConfig,
				timeZoneOffset: 0,
				userPreferencesNode: defaultConfig,
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'exportimport',

			prototype: {
				_bindUI() {
					const instance = this;

					const form = instance.get('form');

					if (form) {
						form.delegate(
							STR_CLICK,
							(event) => {
								const portletId = event.currentTarget.attr(
									'data-portletid'
								);

								let portletTitle = event.currentTarget.attr(
									'data-portlettitle'
								);

								if (!portletTitle) {
									portletTitle = Liferay.Language.get(
										'configuration'
									);
								}

								const configurationDialog = instance._getConfigurationDialog(
									portletId,
									portletTitle
								);

								configurationDialog.show();
							},
							'.configuration-link'
						);

						form.delegate(
							STR_CLICK,
							(event) => {
								const portletId = event.currentTarget.attr(
									'data-portletid'
								);

								let portletTitle = event.currentTarget.attr(
									'data-portlettitle'
								);

								if (!portletTitle) {
									portletTitle = Liferay.Language.get(
										'content'
									);
								}

								const contentDialog = instance._getContentDialog(
									portletId,
									portletTitle
								);

								contentDialog.show();
							},
							'.content-link'
						);
					}

					const portletConfigurationNodes = document.querySelectorAll(
						'[id^=' + instance.ns('PORTLET_CONFIGURATION') + ']'
					);

					Array.prototype.forEach.call(
						portletConfigurationNodes,
						(portletConfigurationNode) => {
							portletConfigurationNode.addEventListener(
								STR_CLICK,
								() => {
									if (portletConfigurationNode.checked) {
										const id = portletConfigurationNode.id;

										const controlCheckboxes = document.querySelectorAll(
											'[data-root-control-id=' + id + ']'
										);

										if (!controlCheckboxes.length) {
											return;
										}

										Array.prototype.forEach.call(
											controlCheckboxes,
											(controlCheckbox) => {
												if (!controlCheckbox.checked) {
													controlCheckbox.click();
												}
											}
										);

										instance._setConfigurationLabels(
											id.replace(
												instance.ns(
													'PORTLET_CONFIGURATION'
												) + '_',
												''
											)
										);
									}
								}
							);
						}
					);

					const portletDataNodes = document.querySelectorAll(
						'[id^=' + instance.ns('PORTLET_DATA') + ']'
					);

					Array.prototype.forEach.call(
						portletDataNodes,
						(portletDataNode) => {
							portletDataNode.addEventListener(STR_CLICK, () => {
								if (portletDataNode.checked) {
									const id = portletDataNode.id;

									const controlCheckboxes = document.querySelectorAll(
										'[data-root-control-id=' + id + ']'
									);

									if (!controlCheckboxes.length) {
										return;
									}

									Array.prototype.forEach.call(
										controlCheckboxes,
										(controlCheckbox) => {
											if (!controlCheckbox.checked) {
												controlCheckbox.click();
											}
										}
									);

									const portletId = id.replace(
										instance.ns('PORTLET_DATA') + '_',
										''
									);

									instance._setContentLabels(portletId);

									const contentNode = instance.byId(
										'content_' + portletId
									);

									instance._storeNodeInputStates(contentNode);
								}
							});
						}
					);

					const changeToPublicLayoutsButton = instance.byId(
						'changeToPublicLayoutsButton'
					);

					if (changeToPublicLayoutsButton) {
						changeToPublicLayoutsButton.on(STR_CLICK, () => {
							instance._changeLayouts(false);
						});
					}

					const changeToPrivateLayoutsButton = instance.byId(
						'changeToPrivateLayoutsButton'
					);

					if (changeToPrivateLayoutsButton) {
						changeToPrivateLayoutsButton.on(STR_CLICK, () => {
							instance._changeLayouts(true);
						});
					}

					const contentOptionsLink = instance.byId(
						'contentOptionsLink'
					);

					if (contentOptionsLink) {
						contentOptionsLink.on(STR_CLICK, () => {
							const contentOptionsDialog = instance._getContentOptionsDialog();

							contentOptionsDialog.show();
						});
					}

					const deletionsNode = instance.get('deletionsNode');

					if (deletionsNode) {
						deletionsNode.on('change', () => {
							instance._refreshDeletions();
						});
					}

					const globalConfigurationLink = instance.byId(
						'globalConfigurationLink'
					);

					if (globalConfigurationLink) {
						globalConfigurationLink.on(STR_CLICK, () => {
							const globalConfigurationDialog = instance._getGlobalConfigurationDialog();

							globalConfigurationDialog.show();
						});
					}

					const rangeLink = instance.byId('rangeLink');

					if (rangeLink) {
						rangeLink.on(STR_CLICK, () => {
							instance._preventNameRequiredChecking();

							instance._updateDateRange();
						});
					}

					const scheduledPublishingEventsLink = instance.byId(
						'scheduledPublishingEventsLink'
					);

					if (scheduledPublishingEventsLink) {
						scheduledPublishingEventsLink.on(STR_CLICK, () => {
							const scheduledPublishingEventsDialog = instance._getScheduledPublishingEventsDialog();

							scheduledPublishingEventsDialog.show();
						});
					}
				},

				_changeLayouts(privateLayout) {
					const instance = this;

					const privateLayoutNode = instance.byId('privateLayout');

					privateLayoutNode.val(privateLayout);

					instance._preventNameRequiredChecking();

					instance._reloadForm();
				},

				_getConfigurationDialog(portletId, portletTitle) {
					const instance = this;

					const configurationNode = instance.byId(
						'configuration_' + portletId
					);

					let configurationDialog = configurationNode.getData(
						'configurationDialog'
					);

					if (!configurationDialog) {
						configurationNode.show();

						configurationDialog = Liferay.Util.Window.getWindow({
							dialog: {
								bodyContent: configurationNode,
								centered: true,
								height: 300,
								modal: true,
								render: instance.get('form'),
								toolbars: {
									footer: [
										{
											label: Liferay.Language.get('ok'),
											on: {
												click(event) {
													event.domEvent.preventDefault();

													instance._setConfigurationLabels(
														portletId
													);

													configurationDialog.hide();
												},
											},
											primary: true,
										},
										{
											label: Liferay.Language.get(
												'cancel'
											),
											on: {
												click(event) {
													event.domEvent.preventDefault();

													configurationDialog.hide();
												},
											},
										},
									],
								},
								width: 400,
							},
							title: portletTitle,
						});

						configurationNode.setData(
							'configurationDialog',
							configurationDialog
						);
					}

					return configurationDialog;
				},

				_getContentDialog(portletId, portletTitle) {
					const instance = this;

					const contentNode = instance.byId('content_' + portletId);

					let contentDialog = contentNode.getData('contentDialog');

					if (!contentDialog) {
						contentNode.show();

						contentDialog = Liferay.Util.Window.getWindow({
							dialog: {
								bodyContent: contentNode,
								centered: true,
								modal: true,
								render: instance.get('form'),
								toolbars: {
									footer: [
										{
											label: Liferay.Language.get('ok'),
											on: {
												click(event) {
													event.domEvent.preventDefault();

													instance._setContentLabels(
														portletId
													);

													instance._storeNodeInputStates(
														contentNode
													);

													contentDialog.hide();
												},
											},
											primary: true,
										},
										{
											label: Liferay.Language.get(
												'cancel'
											),
											on: {
												click(event) {
													event.domEvent.preventDefault();

													instance._restoreNodeInputStates(
														contentNode
													);

													contentDialog.hide();
												},
											},
										},
									],
								},
								width: 400,
							},
							title: portletTitle,
						});

						instance._storeNodeInputStates(contentNode);

						contentNode.setData('contentDialog', contentDialog);
					}

					return contentDialog;
				},

				_getContentOptionsDialog() {
					const instance = this;

					let contentOptionsDialog = instance._contentOptionsDialog;

					const contentOptionsNode = instance.byId('contentOptions');

					if (!contentOptionsDialog) {
						contentOptionsNode.show();

						contentOptionsDialog = Liferay.Util.Window.getWindow({
							dialog: {
								bodyContent: contentOptionsNode,
								centered: true,
								height: 300,
								modal: true,
								render: instance.get('form'),
								toolbars: {
									footer: [
										{
											label: Liferay.Language.get('ok'),
											on: {
												click(event) {
													event.domEvent.preventDefault();

													instance._setContentOptionsLabels();

													instance._storeNodeInputStates(
														contentOptionsNode
													);

													contentOptionsDialog.hide();
												},
											},
											primary: true,
										},
										{
											label: Liferay.Language.get(
												'cancel'
											),
											on: {
												click(event) {
													event.domEvent.preventDefault();

													instance._restoreNodeInputStates(
														contentOptionsNode
													);

													contentOptionsDialog.hide();
												},
											},
										},
									],
								},
								width: 400,
							},
							title: Liferay.Language.get('comments-and-ratings'),
						});

						instance._storeNodeInputStates(contentOptionsNode);

						instance._contentOptionsDialog = contentOptionsDialog;
					}

					return contentOptionsDialog;
				},

				_getGlobalConfigurationDialog() {
					const instance = this;

					let globalConfigurationDialog =
						instance._globalConfigurationDialog;

					if (!globalConfigurationDialog) {
						const globalConfigurationNode = instance.byId(
							'globalConfiguration'
						);

						globalConfigurationNode.show();

						globalConfigurationDialog = Liferay.Util.Window.getWindow(
							{
								dialog: {
									bodyContent: globalConfigurationNode,
									centered: true,
									height: 300,
									modal: true,
									render: instance.get('form'),
									toolbars: {
										footer: [
											{
												label: Liferay.Language.get(
													'ok'
												),
												on: {
													click(event) {
														event.domEvent.preventDefault();

														instance._setGlobalConfigurationLabels();

														globalConfigurationDialog.hide();
													},
												},
												primary: true,
											},
											{
												label: Liferay.Language.get(
													'cancel'
												),
												on: {
													click(event) {
														event.domEvent.preventDefault();

														globalConfigurationDialog.hide();
													},
												},
											},
										],
									},
									width: 400,
								},
								title: Liferay.Language.get(
									'application-configuration'
								),
							}
						);

						instance._globalConfigurationDialog = globalConfigurationDialog;
					}

					return globalConfigurationDialog;
				},

				_getNotificationMessage(dateChecker, rangeEndsLater) {
					const instance = this;

					let message;

					if (!instance._rangeEndsLater() || rangeEndsLater) {
						message = Liferay.Language.get(
							'end-date-must-be-greater-than-start-date'
						);
					}
					else if (
						!instance._rangeEndsInPast(dateChecker.todayUsed) ||
						!instance._rangeStartsInPast(dateChecker.todayUsed)
					) {
						message = Liferay.Language.get(
							'selected-dates-cannot-be-in-the-future'
						);
					}

					return message;
				},

				_getScheduledPublishingEventsDialog() {
					const instance = this;

					let scheduledPublishingEventsDialog =
						instance._scheduledPublishingEventsDialog;

					if (!scheduledPublishingEventsDialog) {
						const scheduledPublishingEventsNode = instance.byId(
							'scheduledPublishingEvents'
						);

						scheduledPublishingEventsNode.show();

						scheduledPublishingEventsDialog = Liferay.Util.Window.getWindow(
							{
								dialog: {
									bodyContent: scheduledPublishingEventsNode,
									centered: true,
									height: 300,
									modal: true,
									render: instance.get('form'),
									toolbars: {
										footer: [
											{
												label: Liferay.Language.get(
													'close'
												),
												on: {
													click(event) {
														event.domEvent.preventDefault();

														scheduledPublishingEventsDialog.hide();
													},
												},
											},
										],
									},
									width: 400,
								},
								title: Liferay.Language.get('scheduled-events'),
							}
						);

						instance._scheduledPublishingEventsDialog = scheduledPublishingEventsDialog;
					}

					return scheduledPublishingEventsDialog;
				},

				_getSelectedDates() {
					const instance = this;

					const startDatePicker = Liferay.component(
						instance.ns('startDateDatePicker')
					);
					const startTimePicker = Liferay.component(
						instance.ns('startTimeTimePicker')
					);

					const endDatePicker = Liferay.component(
						instance.ns('endDateDatePicker')
					);
					const endTimePicker = Liferay.component(
						instance.ns('endTimeTimePicker')
					);

					const endDate = endDatePicker.getDate();
					const endTime = endTimePicker.getTime();

					endDate.setHours(endTime.getHours());
					endDate.setMinutes(endTime.getMinutes());
					endDate.setSeconds(0);
					endDate.setMilliseconds(0);

					const startDate = startDatePicker.getDate();
					const startTime = startTimePicker.getTime();

					startDate.setHours(startTime.getHours());
					startDate.setMinutes(startTime.getMinutes());
					startDate.setSeconds(0);
					startDate.setMilliseconds(0);

					return {
						endDate,
						startDate,
					};
				},

				_getValue(nodeName) {
					const instance = this;

					let value = STR_EMPTY;

					const node = instance.get(nodeName);

					if (node) {
						value = node.val();
					}

					return value;
				},

				_initLabels() {
					const instance = this;

					instance.all('.configuration-link').each((item) => {
						instance._setConfigurationLabels(
							item.attr('data-portletid')
						);
					});

					instance.all('.content-link').each((item) => {
						instance._setContentLabels(item.attr('data-portletid'));
					});

					instance._refreshDeletions();
					instance._setContentOptionsLabels();
					instance._setGlobalConfigurationLabels();
				},

				_isBackgroundTaskInProgress() {
					const instance = this;

					const processesNode = instance.get('processesNode');

					return !!processesNode.one(
						'.background-task-status-in-progress'
					);
				},

				_isChecked(nodeName) {
					const instance = this;

					const node = instance.get(nodeName);

					return node && node.attr(STR_CHECKED);
				},

				_onViewBackgroundTaskDetails(config) {
					const instance = this;

					const node = instance.byId(instance.ns(config.nodeId));

					const bodyNode = node.cloneNode(true);

					bodyNode.show();

					let title = config.title;

					if (title) {
						title = title.trim();
					}

					if (!title) {
						title = Liferay.Language.get('process-details');
					}

					Liferay.Util.openWindow({
						dialog: {
							bodyContent: bodyNode,
						},
						title,
					});
				},

				_preventNameRequiredChecking() {
					const instance = this;

					const nameRequiredNode = instance.byId('nameRequired');

					if (nameRequiredNode) {
						nameRequiredNode.val('0');
					}
				},

				_rangeEndsInPast(today) {
					const instance = this;

					const selectedDates = instance._getSelectedDates();

					return ADate.isGreaterOrEqual(today, selectedDates.endDate);
				},

				_rangeEndsLater() {
					const instance = this;

					const selectedDates = instance._getSelectedDates();

					return ADate.isGreater(
						selectedDates.endDate,
						selectedDates.startDate
					);
				},

				_rangeStartsInPast(today) {
					const instance = this;

					const selectedDates = instance._getSelectedDates();

					return ADate.isGreaterOrEqual(
						today,
						selectedDates.startDate
					);
				},

				_refreshDeletions() {
					const instance = this;

					if (instance._isChecked('deletionsNode')) {
						instance.all('.deletions').each((item) => {
							item.show();
						});
					}
					else {
						instance.all('.deletions').each((item) => {
							item.hide();
						});
					}
				},

				_reloadForm() {
					const instance = this;

					const cmdNode = instance.byId('cmd');
					const redirectNode = instance.byId('redirect');

					if (cmdNode.val() === 'add' || cmdNode.val() === 'update') {
						const params = {
							cmd: cmdNode.val(),
						};

						if (instance._exportLAR) {
							params.mvcRenderCommandName =
								'/export_import/edit_export_configuration';
							params.tabs2 = 'new-export-process';
							params.exportConfigurationButtons = 'custom';
						}
						else {
							params.mvcRenderCommandName =
								'/export_import/edit_publish_configuration';
							params.publishConfigurationButtons = 'custom';
						}

						const groupIdNode = instance.byId('groupId');

						if (groupIdNode) {
							params.groupId = groupIdNode.val();
						}

						const liveGroupIdNode = instance.byId('liveGroupId');

						if (liveGroupIdNode) {
							params.liveGroupId = liveGroupIdNode.val();
						}

						const privateLayoutNode = instance.byId(
							'privateLayout'
						);

						if (privateLayoutNode) {
							params.privateLayout = privateLayoutNode.val();
						}

						const portletURL = Liferay.Util.PortletURL.createPortletURL(
							redirectNode.val(),
							params
						);

						redirectNode.val(portletURL.toString());
					}

					if (cmdNode) {
						const form = instance.get('form');

						const renderURL = Liferay.Util.PortletURL.createRenderURL(
							form.get('action')
						);

						instance._setDisabledCheckboxParameters(renderURL);

						form.set('action', renderURL.toString());

						const currentURL = instance.byId('currentURL');

						redirectNode.val(currentURL);

						cmdNode.val(STR_EMPTY);

						submitForm(form);
					}
				},

				_renderProcesses() {
					const instance = this;

					const checkedCheckboxes = A.all(
						'input[name="' + instance.ns('rowIds') + '"]:checked'
					);

					if (checkedCheckboxes && checkedCheckboxes.size() > 0) {
						instance._scheduleRenderProcess();

						return;
					}

					const processesNode = instance.get('processesNode');

					if (processesNode && instance._processesResourceURL) {
						Liferay.Util.fetch(instance._processesResourceURL)
							.then((response) => response.text())
							.then((response) => {
								processesNode.plug(A.Plugin.ParseContent);

								processesNode.setContent(response);

								instance._updateincompleteProcessMessage(
									instance._isBackgroundTaskInProgress(),
									processesNode.one(
										'.incomplete-process-message'
									)
								);

								instance._scheduleRenderProcess();
							})
							.catch(() => {
								Liferay.Util.openToast({
									message: Liferay.Language.get(
										'your-request-failed-to-complete'
									),
									toastProps: {
										autoClose: FAILURE_TIMEOUT,
									},
									type: 'warning',
								});
							});
					}
				},

				_restoreNodeCheckedState(node, state) {
					const val = state.value;

					if (val !== undefined) {
						node.set('checked', val);
					}
				},

				_restoreNodeHiddenState(node, state) {
					let hiddenList = node.ancestorsByClassName(STR_HIDE);

					hiddenList.each((hiddenNode) => {
						hiddenNode.removeClass(STR_HIDE);
					});

					hiddenList = state.hiddenList;

					if (hiddenList !== null) {
						hiddenList.each((node) => {
							node.addClass(STR_HIDE);
						});
					}
				},

				_restoreNodeInputStates(node) {
					const instance = this;

					let inputNodes = [];

					const inputStates = instance._nodeInputStates;

					if (node && node.getElementsByTagName) {
						inputNodes = node.getElementsByTagName('input');
					}

					inputNodes.each((node) => {
						const id = node.get('id');

						const state = inputStates[id];

						if (state !== undefined) {
							instance._restoreNodeCheckedState(node, state);
							instance._restoreNodeHiddenState(node, state);
						}
					});
				},

				_scheduleRenderProcess() {
					const instance = this;

					let renderInterval = RENDER_INTERVAL_IDLE;

					if (instance._isBackgroundTaskInProgress()) {
						renderInterval = RENDER_INTERVAL_IN_PROGRESS;
					}

					instance._renderTimer = A.later(
						renderInterval,
						instance,
						instance._renderProcesses
					);
				},

				_setConfigurationLabels(portletId) {
					const instance = this;

					const configurationNode = instance.byId(
						'configuration_' + portletId
					);

					const inputs = configurationNode.all('.field');

					const selectedConfiguration = [];

					inputs.each((item) => {
						const checked = item.attr(STR_CHECKED);

						if (checked) {
							selectedConfiguration.push(item.attr('data-name'));
						}
					});

					if (!selectedConfiguration.length) {
						instance
							.byId('PORTLET_CONFIGURATION_' + portletId)
							.attr('checked', false);

						instance
							.byId('showChangeConfiguration_' + portletId)
							.hide();
					}
					else {
						instance
							.byId('showChangeConfiguration_' + portletId)
							.show();
					}

					instance._setLabels(
						'configurationLink_' + portletId,
						'selectedConfiguration_' + portletId,
						selectedConfiguration.join(', ')
					);
				},

				_setContentLabels(portletId) {
					const instance = this;

					const contentNode = instance.byId('content_' + portletId);

					const inputs = contentNode.all('.field');

					const selectedContent = [];

					inputs.each((item) => {
						const checked = item.attr(STR_CHECKED);

						if (checked) {
							selectedContent.push(item.attr('data-name'));
						}
					});

					if (
						!selectedContent.length ||
						!instance
							.byId('PORTLET_DATA_' + portletId)
							.attr('checked')
					) {
						instance
							.byId('PORTLET_DATA_' + portletId)
							.attr('checked', false);

						instance.byId('showChangeContent_' + portletId).hide();
					}
					else {
						instance.byId('showChangeContent_' + portletId).show();
					}

					instance._setLabels(
						'contentLink_' + portletId,
						'selectedContent_' + portletId,
						selectedContent.join(', ')
					);
				},

				_setContentOptionsLabels() {
					const instance = this;

					const selectedContentOptions = [];

					if (instance._isChecked('commentsNode')) {
						selectedContentOptions.push(
							Liferay.Language.get('comments')
						);
					}

					if (instance._isChecked('ratingsNode')) {
						selectedContentOptions.push(
							Liferay.Language.get('ratings')
						);
					}

					instance._setLabels(
						'contentOptionsLink',
						'selectedContentOptions',
						selectedContentOptions.join(', ')
					);
				},

				_setDisabledCheckboxParameters(portletURL) {
					const instance = this;

					const portletDataNodes = document.querySelectorAll(
						'[id^=' + instance.ns('PORTLET_DATA') + ']'
					);

					Array.prototype.forEach.call(
						portletDataNodes,
						(portletDataNode) => {
							if (portletDataNode.type === 'checkbox') {
								const id = portletDataNode.id;

								const controlCheckboxes = document.querySelectorAll(
									'[data-root-control-id=' + id + ']'
								);

								if (!controlCheckboxes.length) {
									return;
								}

								Array.prototype.forEach.call(
									controlCheckboxes,
									(controlCheckbox) => {
										if (
											controlCheckbox.disabled &&
											controlCheckbox.checked
										) {
											portletURL.searchParams.set(
												controlCheckbox.name.replace(
													instance.NS,
													''
												),
												'true'
											);
										}
									}
								);
							}
						}
					);
				},

				_setGlobalConfigurationLabels() {
					const instance = this;

					const selectedGlobalConfiguration = [];

					if (instance._isChecked('setupNode')) {
						selectedGlobalConfiguration.push(
							Liferay.Language.get('setup')
						);
					}

					if (instance._isChecked('archivedSetupsNode')) {
						selectedGlobalConfiguration.push(
							Liferay.Language.get('configuration-templates')
						);
					}

					if (instance._isChecked('userPreferencesNode')) {
						selectedGlobalConfiguration.push(
							Liferay.Language.get('user-preferences')
						);
					}

					instance._setLabels(
						'globalConfigurationLink',
						'selectedGlobalConfiguration',
						selectedGlobalConfiguration.join(', ')
					);
				},

				_setLabels(linkId, labelDivId, label) {
					const instance = this;

					const linkNode = instance.byId(linkId);

					if (linkNode) {
						if (label !== STR_EMPTY) {
							linkNode.html(Liferay.Language.get('change'));
						}
						else {
							linkNode.html(Liferay.Language.get('select'));
						}
					}

					const labelNode = instance.byId(labelDivId);

					if (labelNode) {
						labelNode.html(label);
					}
				},

				_setNode(val) {
					const instance = this;

					if (Lang.isString(val)) {
						val = instance.one(val);
					}
					else {
						val = A.one(val);
					}

					return val;
				},

				_storeNodeInputStates(node) {
					const instance = this;

					let inputNodes = [];

					const inputStates = instance._nodeInputStates;

					if (node && node.getElementsByTagName) {
						inputNodes = node.getElementsByTagName('input');
					}

					inputNodes.each((node) => {
						let hiddenList = node.ancestorsByClassName(STR_HIDE);

						const id = node.get('id');

						const val = node.get('checked');

						if (hiddenList.size() === 0) {
							hiddenList = null;
						}

						inputStates[id] = {
							hiddenList,
							value: val,
						};
					});
				},

				_updateDateRange() {
					const instance = this;

					const dateChecker = instance.getDateRangeChecker();

					if (dateChecker.validRange) {
						instance._reloadForm();

						A.all(
							'.datepicker-popover, .timepicker-popover'
						).hide();
					}
					else {
						instance.showNotification(dateChecker);
					}
				},

				_updateincompleteProcessMessage(inProgress, content) {
					const instance = this;

					const incompleteProcessMessageNode = instance.get(
						'incompleteProcessMessageNode'
					);

					if (incompleteProcessMessageNode) {
						content.show();

						if (
							inProgress ||
							incompleteProcessMessageNode.hasClass('in-progress')
						) {
							incompleteProcessMessageNode.setContent(content);

							if (inProgress) {
								incompleteProcessMessageNode.addClass(
									'in-progress'
								);

								incompleteProcessMessageNode.show();
							}
						}
					}
				},

				destructor() {
					const instance = this;

					if (instance._contentOptionsDialog) {
						instance._contentOptionsDialog.destroy();
					}

					if (instance._globalConfigurationDialog) {
						instance._globalConfigurationDialog.destroy();
					}

					if (instance._renderTimer) {
						instance._renderTimer.cancel();
					}

					if (instance._scheduledPublishingEventsDialog) {
						instance._scheduledPublishingEventsDialog.destroy();
					}
				},

				getDateRangeChecker() {
					const instance = this;

					const today = new Date();

					const todayMS = +today;

					const clientTZOffset = today.getTimezoneOffset();

					const serverTZOffset = this.get('timeZoneOffset');

					const adjustedDate = new Date(
						todayMS + serverTZOffset + clientTZOffset * 60 * 1000
					);

					const dateRangeChecker = {
						todayUsed: adjustedDate,
						validRange: true,
					};

					if (instance._isChecked('rangeDateRangeNode')) {
						dateRangeChecker.validRange =
							instance._rangeEndsLater() &&
							instance._rangeEndsInPast(adjustedDate) &&
							instance._rangeStartsInPast(adjustedDate);
					}

					return dateRangeChecker;
				},

				initializer(config) {
					const instance = this;

					instance._bindUI();

					instance._exportLAR = config.exportLAR;
					instance._layoutsExportTreeOutput = instance.byId(
						config.pageTreeId + 'Output'
					);

					instance._nodeInputStates = [];

					instance._initLabels();

					instance._processesResourceURL =
						config.processesResourceURL;

					const viewBackgroundTaskDetailsEventName = instance.ns(
						'viewBackgroundTaskDetails'
					);

					const viewBackgroundTaskDetailsEvent = Liferay.getEvent(
						viewBackgroundTaskDetailsEventName
					);

					if (!viewBackgroundTaskDetailsEvent) {
						Liferay.on(
							viewBackgroundTaskDetailsEventName,
							instance._onViewBackgroundTaskDetails,
							instance
						);
					}

					instance._renderTimer = A.later(
						RENDER_INTERVAL_IN_PROGRESS,
						instance,
						instance._renderProcesses
					);
				},

				showNotification(dateChecker, rangeEndsLater) {
					const instance = this;

					const message = instance._getNotificationMessage(
						dateChecker,
						rangeEndsLater
					);

					Liferay.Util.openToast({
						message,
						toastProps: {
							autoClose: 10000,
							style: {left: 0, top: 0},
						},
						type: 'warning',
					});
				},
			},
		});

		Liferay.ExportImport = ExportImport;
	},
	'',
	{
		requires: [
			'aui-datatype',
			'aui-dialog-iframe-deprecated',
			'aui-modal',
			'aui-parse-content',
			'aui-toggler',
			'liferay-portlet-base',
			'liferay-util-window',
		],
	}
);
