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
	'liferay-export-import-export-import',
	A => {
		var Lang = A.Lang;

		var ADate = A.Date;

		var FAILURE_TIMEOUT = 10000;

		var RENDER_INTERVAL_IDLE = 60000;

		var RENDER_INTERVAL_IN_PROGRESS = 2000;

		var STR_CHECKED = 'checked';

		var STR_CLICK = 'click';

		var STR_EMPTY = '';

		var STR_HIDE = 'hide';

		var defaultConfig = {
			setter: '_setNode'
		};

		var ExportImport = A.Component.create({
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
				userPreferencesNode: defaultConfig
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'exportimport',

			prototype: {
				_bindUI() {
					var instance = this;

					var form = instance.get('form');

					if (form) {
						form.delegate(
							STR_CLICK,
							event => {
								var portletId = event.currentTarget.attr(
									'data-portletid'
								);

								var portletTitle = event.currentTarget.attr(
									'data-portlettitle'
								);

								if (!portletTitle) {
									portletTitle = Liferay.Language.get(
										'configuration'
									);
								}

								var configurationDialog = instance._getConfigurationDialog(
									portletId,
									portletTitle
								);

								configurationDialog.show();
							},
							'.configuration-link'
						);

						form.delegate(
							STR_CLICK,
							event => {
								var portletId = event.currentTarget.attr(
									'data-portletid'
								);

								var portletTitle = event.currentTarget.attr(
									'data-portlettitle'
								);

								if (!portletTitle) {
									portletTitle = Liferay.Language.get(
										'content'
									);
								}

								var contentDialog = instance._getContentDialog(
									portletId,
									portletTitle
								);

								contentDialog.show();
							},
							'.content-link'
						);
					}

					var portletConfigurationNodes = document.querySelectorAll(
						'[id^=' + instance.ns('PORTLET_CONFIGURATION') + ']'
					);

					Array.prototype.forEach.call(
						portletConfigurationNodes,
						portletConfigurationNode => {
							portletConfigurationNode.addEventListener(
								STR_CLICK,
								() => {
									if (portletConfigurationNode.checked) {
										var id = portletConfigurationNode.id;

										var controlCheckboxes = document.querySelectorAll(
											'[data-root-control-id=' + id + ']'
										);

										if (controlCheckboxes.length === 0) {
											return;
										}

										Array.prototype.forEach.call(
											controlCheckboxes,
											controlCheckbox => {
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

					var portletDataNodes = document.querySelectorAll(
						'[id^=' + instance.ns('PORTLET_DATA') + ']'
					);

					Array.prototype.forEach.call(
						portletDataNodes,
						portletDataNode => {
							portletDataNode.addEventListener(STR_CLICK, () => {
								if (portletDataNode.checked) {
									var id = portletDataNode.id;

									var controlCheckboxes = document.querySelectorAll(
										'[data-root-control-id=' + id + ']'
									);

									if (controlCheckboxes.length === 0) {
										return;
									}

									Array.prototype.forEach.call(
										controlCheckboxes,
										controlCheckbox => {
											if (!controlCheckbox.checked) {
												controlCheckbox.click();
											}
										}
									);

									var portletId = id.replace(
										instance.ns('PORTLET_DATA') + '_',
										''
									);

									instance._setContentLabels(portletId);

									var contentNode = instance.byId(
										'content_' + portletId
									);

									instance._storeNodeInputStates(contentNode);
								}
							});
						}
					);

					var changeToPublicLayoutsButton = instance.byId(
						'changeToPublicLayoutsButton'
					);

					if (changeToPublicLayoutsButton) {
						changeToPublicLayoutsButton.on(STR_CLICK, () => {
							instance._changeLayouts(false);
						});
					}

					var changeToPrivateLayoutsButton = instance.byId(
						'changeToPrivateLayoutsButton'
					);

					if (changeToPrivateLayoutsButton) {
						changeToPrivateLayoutsButton.on(STR_CLICK, () => {
							instance._changeLayouts(true);
						});
					}

					var contentOptionsLink = instance.byId(
						'contentOptionsLink'
					);

					if (contentOptionsLink) {
						contentOptionsLink.on(STR_CLICK, () => {
							var contentOptionsDialog = instance._getContentOptionsDialog();

							contentOptionsDialog.show();
						});
					}

					var deletionsNode = instance.get('deletionsNode');

					if (deletionsNode) {
						deletionsNode.on('change', () => {
							instance._refreshDeletions();
						});
					}

					var globalConfigurationLink = instance.byId(
						'globalConfigurationLink'
					);

					if (globalConfigurationLink) {
						globalConfigurationLink.on(STR_CLICK, () => {
							var globalConfigurationDialog = instance._getGlobalConfigurationDialog();

							globalConfigurationDialog.show();
						});
					}

					var rangeLink = instance.byId('rangeLink');

					if (rangeLink) {
						rangeLink.on(STR_CLICK, () => {
							instance._preventNameRequiredChecking();

							instance._updateDateRange();
						});
					}

					var scheduledPublishingEventsLink = instance.byId(
						'scheduledPublishingEventsLink'
					);

					if (scheduledPublishingEventsLink) {
						scheduledPublishingEventsLink.on(STR_CLICK, () => {
							var scheduledPublishingEventsDialog = instance._getScheduledPublishingEventsDialog();

							scheduledPublishingEventsDialog.show();
						});
					}
				},

				_changeLayouts(privateLayout) {
					var instance = this;

					var privateLayoutNode = instance.byId('privateLayout');

					privateLayoutNode.val(privateLayout);

					instance._preventNameRequiredChecking();

					instance._reloadForm();
				},

				_getConfigurationDialog(portletId, portletTitle) {
					var instance = this;

					var configurationNode = instance.byId(
						'configuration_' + portletId
					);

					var configurationDialog = configurationNode.getData(
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
												}
											},
											primary: true
										},
										{
											label: Liferay.Language.get(
												'cancel'
											),
											on: {
												click(event) {
													event.domEvent.preventDefault();

													configurationDialog.hide();
												}
											}
										}
									]
								},
								width: 400
							},
							title: portletTitle
						});

						configurationNode.setData(
							'configurationDialog',
							configurationDialog
						);
					}

					return configurationDialog;
				},

				_getContentDialog(portletId, portletTitle) {
					var instance = this;

					var contentNode = instance.byId('content_' + portletId);

					var contentDialog = contentNode.getData('contentDialog');

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
												}
											},
											primary: true
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
												}
											}
										}
									]
								},
								width: 400
							},
							title: portletTitle
						});

						instance._storeNodeInputStates(contentNode);

						contentNode.setData('contentDialog', contentDialog);
					}

					return contentDialog;
				},

				_getContentOptionsDialog() {
					var instance = this;

					var contentOptionsDialog = instance._contentOptionsDialog;

					var contentOptionsNode = instance.byId('contentOptions');

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
												}
											},
											primary: true
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
												}
											}
										}
									]
								},
								width: 400
							},
							title: Liferay.Language.get('comments-and-ratings')
						});

						instance._storeNodeInputStates(contentOptionsNode);

						instance._contentOptionsDialog = contentOptionsDialog;
					}

					return contentOptionsDialog;
				},

				_getGlobalConfigurationDialog() {
					var instance = this;

					var globalConfigurationDialog =
						instance._globalConfigurationDialog;

					if (!globalConfigurationDialog) {
						var globalConfigurationNode = instance.byId(
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
													}
												},
												primary: true
											},
											{
												label: Liferay.Language.get(
													'cancel'
												),
												on: {
													click(event) {
														event.domEvent.preventDefault();

														globalConfigurationDialog.hide();
													}
												}
											}
										]
									},
									width: 400
								},
								title: Liferay.Language.get(
									'application-configuration'
								)
							}
						);

						instance._globalConfigurationDialog = globalConfigurationDialog;
					}

					return globalConfigurationDialog;
				},

				_getNotificationMessage(dateChecker, rangeEndsLater) {
					var instance = this;

					var message;

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
					var instance = this;

					var scheduledPublishingEventsDialog =
						instance._scheduledPublishingEventsDialog;

					if (!scheduledPublishingEventsDialog) {
						var scheduledPublishingEventsNode = instance.byId(
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
													}
												}
											}
										]
									},
									width: 400
								},
								title: Liferay.Language.get('scheduled-events')
							}
						);

						instance._scheduledPublishingEventsDialog = scheduledPublishingEventsDialog;
					}

					return scheduledPublishingEventsDialog;
				},

				_getSelectedDates() {
					var instance = this;

					var startDatePicker = Liferay.component(
						instance.ns('startDateDatePicker')
					);
					var startTimePicker = Liferay.component(
						instance.ns('startTimeTimePicker')
					);

					var endDatePicker = Liferay.component(
						instance.ns('endDateDatePicker')
					);
					var endTimePicker = Liferay.component(
						instance.ns('endTimeTimePicker')
					);

					var endDate = endDatePicker.getDate();
					var endTime = endTimePicker.getTime();

					endDate.setHours(endTime.getHours());
					endDate.setMinutes(endTime.getMinutes());
					endDate.setSeconds(0);
					endDate.setMilliseconds(0);

					var startDate = startDatePicker.getDate();
					var startTime = startTimePicker.getTime();

					startDate.setHours(startTime.getHours());
					startDate.setMinutes(startTime.getMinutes());
					startDate.setSeconds(0);
					startDate.setMilliseconds(0);

					return {
						endDate,
						startDate
					};
				},

				_getValue(nodeName) {
					var instance = this;

					var value = STR_EMPTY;

					var node = instance.get(nodeName);

					if (node) {
						value = node.val();
					}

					return value;
				},

				_initLabels() {
					var instance = this;

					instance.all('.configuration-link').each(item => {
						instance._setConfigurationLabels(
							item.attr('data-portletid')
						);
					});

					instance.all('.content-link').each(item => {
						instance._setContentLabels(item.attr('data-portletid'));
					});

					instance._refreshDeletions();
					instance._setContentOptionsLabels();
					instance._setGlobalConfigurationLabels();
				},

				_isBackgroundTaskInProgress() {
					var instance = this;

					var processesNode = instance.get('processesNode');

					return !!processesNode.one(
						'.background-task-status-in-progress'
					);
				},

				_isChecked(nodeName) {
					var instance = this;

					var node = instance.get(nodeName);

					return node && node.attr(STR_CHECKED);
				},

				_onViewBackgroundTaskDetails(config) {
					var instance = this;

					var node = instance.byId(instance.ns(config.nodeId));

					var bodyNode = node.cloneNode(true);

					bodyNode.show();

					var title = config.title;

					if (title) {
						title = title.trim();
					}

					if (!title) {
						title = Liferay.Language.get('process-details');
					}

					Liferay.Util.openWindow({
						dialog: {
							bodyContent: bodyNode
						},
						title
					});
				},

				_preventNameRequiredChecking() {
					var instance = this;

					var nameRequiredNode = instance.byId('nameRequired');

					if (nameRequiredNode) {
						nameRequiredNode.val('0');
					}
				},

				_rangeEndsInPast(today) {
					var instance = this;

					var selectedDates = instance._getSelectedDates();

					return ADate.isGreaterOrEqual(today, selectedDates.endDate);
				},

				_rangeEndsLater() {
					var instance = this;

					var selectedDates = instance._getSelectedDates();

					return ADate.isGreater(
						selectedDates.endDate,
						selectedDates.startDate
					);
				},

				_rangeStartsInPast(today) {
					var instance = this;

					var selectedDates = instance._getSelectedDates();

					return ADate.isGreaterOrEqual(
						today,
						selectedDates.startDate
					);
				},

				_refreshDeletions() {
					var instance = this;

					if (instance._isChecked('deletionsNode')) {
						instance.all('.deletions').each(item => {
							item.show();
						});
					}
					else {
						instance.all('.deletions').each(item => {
							item.hide();
						});
					}
				},

				_reloadForm() {
					var instance = this;

					var cmdNode = instance.byId('cmd');
					var redirectNode = instance.byId('redirect');

					if (cmdNode.val() === 'add' || cmdNode.val() === 'update') {
						var params = {
							cmd: cmdNode.val()
						};

						if (instance._exportLAR) {
							params.mvcRenderCommandName =
								'editExportConfiguration';
							params.tabs2 = 'new-export-process';
							params.exportConfigurationButtons = 'custom';
						}
						else {
							params.mvcRenderCommandName =
								'editPublishConfiguration';
							params.publishConfigurationButtons = 'custom';
						}

						var groupIdNode = instance.byId('groupId');

						if (groupIdNode) {
							params.groupId = groupIdNode.val();
						}

						var liveGroupIdNode = instance.byId('liveGroupId');

						if (liveGroupIdNode) {
							params.liveGroupId = liveGroupIdNode.val();
						}

						var privateLayoutNode = instance.byId('privateLayout');

						if (privateLayoutNode) {
							params.privateLayout = privateLayoutNode.val();
						}

						var rootNodeNameNode = instance.byId('rootNodeName');

						if (rootNodeNameNode) {
							params.rootNodeName = rootNodeNameNode.val();
						}

						var portletURL = Liferay.Util.PortletURL.createPortletURL(
							redirectNode.val(),
							params
						);

						redirectNode.val(portletURL.toString());
					}

					if (cmdNode) {
						var form = instance.get('form');

						var renderURL = Liferay.Util.PortletURL.createRenderURL(
							form.get('action')
						);

						instance._setDisabledCheckboxParameters(renderURL);

						form.set('action', renderURL.toString());

						var currentURL = instance.byId('currentURL');

						redirectNode.val(currentURL);

						cmdNode.val(STR_EMPTY);

						submitForm(form);
					}
				},

				_renderProcesses() {
					var instance = this;

					var checkedCheckboxes = A.all(
						'input[name="' + instance.ns('rowIds') + '"]:checked'
					);

					if (checkedCheckboxes && checkedCheckboxes.size() > 0) {
						instance._scheduleRenderProcess();

						return;
					}

					var processesNode = instance.get('processesNode');

					if (processesNode && instance._processesResourceURL) {
						Liferay.Util.fetch(instance._processesResourceURL)
							.then(response => response.text())
							.then(response => {
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
								new Liferay.Notice({
									closeText: false,
									content:
										Liferay.Language.get(
											'your-request-failed-to-complete'
										) +
										'<button aria-label="' +
										Liferay.Language.get('close') +
										'" type="button" class="close">&times;</button>',
									noticeClass: 'hide',
									timeout: FAILURE_TIMEOUT,
									toggleText: false,
									type: 'warning',
									useAnimation: true
								}).show();
							});
					}
				},

				_restoreNodeCheckedState(node, state) {
					var val = state.value;

					if (val !== undefined) {
						node.set('checked', val);
					}
				},

				_restoreNodeHiddenState(node, state) {
					var hiddenList = node.ancestorsByClassName(STR_HIDE);

					hiddenList.each(hiddenNode => {
						hiddenNode.removeClass(STR_HIDE);
					});

					hiddenList = state.hiddenList;

					if (hiddenList !== null) {
						hiddenList.each(node => {
							node.addClass(STR_HIDE);
						});
					}
				},

				_restoreNodeInputStates(node) {
					var instance = this;

					var inputNodes = [];

					var inputStates = instance._nodeInputStates;

					if (node && node.getElementsByTagName) {
						inputNodes = node.getElementsByTagName('input');
					}

					inputNodes.each(node => {
						var id = node.get('id');

						var state = inputStates[id];

						if (state !== undefined) {
							instance._restoreNodeCheckedState(node, state);
							instance._restoreNodeHiddenState(node, state);
						}
					});
				},

				_scheduleRenderProcess() {
					var instance = this;

					var renderInterval = RENDER_INTERVAL_IDLE;

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
					var instance = this;

					var configurationNode = instance.byId(
						'configuration_' + portletId
					);

					var inputs = configurationNode.all('.field');

					var selectedConfiguration = [];

					inputs.each(item => {
						var checked = item.attr(STR_CHECKED);

						if (checked) {
							selectedConfiguration.push(item.attr('data-name'));
						}
					});

					if (selectedConfiguration.length === 0) {
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
					var instance = this;

					var contentNode = instance.byId('content_' + portletId);

					var inputs = contentNode.all('.field');

					var selectedContent = [];

					inputs.each(item => {
						var checked = item.attr(STR_CHECKED);

						if (checked) {
							selectedContent.push(item.attr('data-name'));
						}
					});

					if (
						selectedContent.length === 0 ||
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
					var instance = this;

					var selectedContentOptions = [];

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
					var instance = this;

					var portletDataNodes = document.querySelectorAll(
						'[id^=' + instance.ns('PORTLET_DATA') + ']'
					);

					Array.prototype.forEach.call(
						portletDataNodes,
						portletDataNode => {
							if (portletDataNode.type === 'checkbox') {
								var id = portletDataNode.id;

								var controlCheckboxes = document.querySelectorAll(
									'[data-root-control-id=' + id + ']'
								);

								if (controlCheckboxes.length === 0) {
									return;
								}

								Array.prototype.forEach.call(
									controlCheckboxes,
									controlCheckbox => {
										if (
											controlCheckbox.disabled &&
											controlCheckbox.checked
										) {
											portletURL.setParameter(
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
					var instance = this;

					var selectedGlobalConfiguration = [];

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
					var instance = this;

					var linkNode = instance.byId(linkId);

					if (linkNode) {
						if (label !== STR_EMPTY) {
							linkNode.html(Liferay.Language.get('change'));
						}
						else {
							linkNode.html(Liferay.Language.get('select'));
						}
					}

					var labelNode = instance.byId(labelDivId);

					if (labelNode) {
						labelNode.html(label);
					}
				},

				_setNode(val) {
					var instance = this;

					if (Lang.isString(val)) {
						val = instance.one(val);
					}
					else {
						val = A.one(val);
					}

					return val;
				},

				_storeNodeInputStates(node) {
					var instance = this;

					var inputNodes = [];

					var inputStates = instance._nodeInputStates;

					if (node && node.getElementsByTagName) {
						inputNodes = node.getElementsByTagName('input');
					}

					inputNodes.each(node => {
						var hiddenList = node.ancestorsByClassName(STR_HIDE);

						var id = node.get('id');

						var val = node.get('checked');

						if (hiddenList.size() === 0) {
							hiddenList = null;
						}

						inputStates[id] = {
							hiddenList,
							value: val
						};
					});
				},

				_updateDateRange() {
					var instance = this;

					var dateChecker = instance.getDateRangeChecker();

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
					var instance = this;

					var incompleteProcessMessageNode = instance.get(
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
					var instance = this;

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
					var instance = this;

					var today = new Date();

					var todayMS = +today;

					var clientTZOffset = today.getTimezoneOffset();

					var serverTZOffset = this.get('timeZoneOffset');

					var adjustedDate = new Date(
						todayMS + serverTZOffset + clientTZOffset * 60 * 1000
					);

					var dateRangeChecker = {
						todayUsed: adjustedDate,
						validRange: true
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
					var instance = this;

					instance._bindUI();

					instance._exportLAR = config.exportLAR;
					instance._layoutsExportTreeOutput = instance.byId(
						config.pageTreeId + 'Output'
					);

					instance._nodeInputStates = [];

					instance._initLabels();

					instance._processesResourceURL =
						config.processesResourceURL;

					var viewBackgroundTaskDetailsEventName = instance.ns(
						'viewBackgroundTaskDetails'
					);

					var viewBackgroundTaskDetailsEvent = Liferay.getEvent(
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
					var instance = this;

					if (instance._notice) {
						instance._notice.remove();
					}

					var message = instance._getNotificationMessage(
						dateChecker,
						rangeEndsLater
					);

					instance._notice = new Liferay.Notice({
						animationConfig: {
							duration: 2,
							left: '0px',
							top: '0px'
						},
						closeText: false,
						content:
							message +
							'<button aria-label="' +
							Liferay.Language.get('close') +
							'" type="button" class="close">&times;</button>',
						noticeClass: 'hide',
						timeout: 10000,
						toggleText: false,
						type: 'warning',
						useAnimation: true
					});

					instance._notice.show();
				}
			}
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
			'aui-tree-view',
			'liferay-notice',
			'liferay-portlet-base',
			'liferay-util-window'
		]
	}
);
