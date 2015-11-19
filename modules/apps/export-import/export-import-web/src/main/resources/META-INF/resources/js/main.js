AUI.add(
	'liferay-export-import',
	function(A) {
		var Lang = A.Lang;

		var ADate = A.Date;

		var FAILURE_TIMEOUT = 10000;

		var REGEX_LAYOUT_ID = /plid_(\d+)/;

		var RENDER_INTERVAL_IDLE = 60000;

		var RENDER_INTERVAL_IN_PROGRESS = 2000;

		var STR_CHECKED = 'checked';

		var STR_CLICK = 'click';

		var STR_EMPTY = '';

		var defaultConfig = {
			setter: '_setNode'
		};

		var ExportImport = A.Component.create(
			{
				ATTRS: {
					archivedSetupsNode: defaultConfig,
					commentsNode: defaultConfig,
					deleteMissingLayoutsNode: defaultConfig,
					deletionsNode: defaultConfig,
					exportLAR: defaultConfig,
					form: defaultConfig,
					incompleteProcessMessageNode: defaultConfig,
					layoutSetSettingsNode: defaultConfig,
					locale: STR_EMPTY,
					logoNode: defaultConfig,
					processesNode: defaultConfig,
					rangeAllNode: defaultConfig,
					rangeDateRangeNode: defaultConfig,
					rangeLastNode: defaultConfig,
					rangeLastPublishNode: defaultConfig,
					ratingsNode: defaultConfig,
					remoteAddressNode: defaultConfig,
					remoteGroupIdNode: defaultConfig,
					remotePathContextNode: defaultConfig,
					remotePortNode: defaultConfig,
					secureConnectionNode: defaultConfig,
					setupNode: defaultConfig,
					themeReferenceNode: defaultConfig,
					timeZone: STR_EMPTY,
					userPreferencesNode: defaultConfig
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'exportimport',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._bindUI();

						instance._exportLAR = config.exportLAR;
						instance._layoutsExportTreeOutput = instance.byId(config.pageTreeId + 'Output');

						instance._initLabels();

						instance._processesResourceURL = config.processesResourceURL;

						A.later(RENDER_INTERVAL_IN_PROGRESS, instance, instance._renderProcesses);
					},

					destructor: function() {
						var instance = this;

						if (instance._contentOptionsDialog) {
							instance._contentOptionsDialog.destroy();
						}

						if (instance._globalConfigurationDialog) {
							instance._globalConfigurationDialog.destroy();
						}

						if (instance._pagesDialog) {
							instance._pagesDialog.destroy();
						}

						if (instance._rangeDialog) {
							instance._rangeDialog.destroy();
						}

						if (instance._remoteDialog) {
							instance._remoteDialog.destroy();
						}

						if (instance._scheduledPublishingEventsDialog) {
							instance._scheduledPublishingEventsDialog.destroy();
						}
					},

					_bindUI: function() {
						var instance = this;

						var form = instance.get('form');

						if (form) {
							form.delegate(
								STR_CLICK,
								function(event) {
									var portletId = event.currentTarget.attr('data-portletid');

									var portletTitle = event.currentTarget.attr('data-portlettitle');

									if (!portletTitle) {
										portletTitle = Liferay.Language.get('configuration');
									}

									var configurationDialog = instance._getConfigurationDialog(portletId, portletTitle);

									configurationDialog.show();
								},
								'.configuration-link'
							);

							form.delegate(
								STR_CLICK,
								function(event) {
									var portletId = event.currentTarget.attr('data-portletid');

									var portletTitle = event.currentTarget.attr('data-portlettitle');

									if (!portletTitle) {
										portletTitle = Liferay.Language.get('content');
									}

									var contentDialog = instance._getContentDialog(portletId, portletTitle);

									contentDialog.show();
								},
								'.content-link'
							);
						}

						var contentOptionsLink = instance.byId('contentOptionsLink');

						if (contentOptionsLink) {
							contentOptionsLink.on(
								STR_CLICK,
								function(event) {
									var contentOptionsDialog = instance._getContentOptionsDialog();

									contentOptionsDialog.show();
								}
							);
						}

						var deletionsNode = instance.get('deletionsNode');

						if (deletionsNode) {
							deletionsNode.on(
								'change',
								function() {
									instance._refreshDeletions();
								}
							);
						}

						var globalConfigurationLink = instance.byId('globalConfigurationLink');

						if (globalConfigurationLink) {
							globalConfigurationLink.on(
								STR_CLICK,
								function(event) {
									var globalConfigurationDialog = instance._getGlobalConfigurationDialog();

									globalConfigurationDialog.show();
								}
							);
						}

						var pagesLink = instance.byId('pagesLink');

						if (pagesLink) {
							pagesLink.on(
								STR_CLICK,
								function(event) {
									var pagesDialog = instance._getPagesDialog();

									pagesDialog.show();
								}
							);
						}

						var rangeLink = instance.byId('rangeLink');

						if (rangeLink) {
							rangeLink.on(
								STR_CLICK,
								function(event) {
									var rangeDialog = instance._getRangeDialog();

									rangeDialog.show();
								}
							);
						}

						var remoteLink = instance.byId('remoteLink');

						if (remoteLink) {
							remoteLink.on(
								STR_CLICK,
								function(event) {
									var remoteDialog = instance._getRemoteDialog();

									remoteDialog.show();
								}
							);
						}

						var scheduledPublishingEventsLink = instance.byId('scheduledPublishingEventsLink');

						if (scheduledPublishingEventsLink) {
							scheduledPublishingEventsLink.on(
								STR_CLICK,
								function(event) {
									var scheduledPublishingEventsDialog = instance._getScheduledPublishingEventsDialog();

									scheduledPublishingEventsDialog.show();
								}
							);
						}
					},

					_getConfigurationDialog: function(portletId, portletTitle) {
						var instance = this;

						var configurationNode = instance.byId('configuration_' + portletId);

						var configurationDialog = configurationNode.getData('configurationDialog');

						if (!configurationDialog) {
							configurationNode.show();

							configurationDialog = Liferay.Util.Window.getWindow(
								{
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
														click: function(event) {
															event.domEvent.preventDefault();

															instance._setConfigurationLabels(portletId);

															configurationDialog.hide();
														}
													},
													primary: true
												},
												{
													label: Liferay.Language.get('cancel'),
													on: {
														click: function(event) {
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
								}
							);

							configurationNode.setData('configurationDialog', configurationDialog);
						}

						return configurationDialog;
					},

					_getContentDialog: function(portletId, portletTitle) {
						var instance = this;

						var contentNode = instance.byId('content_' + portletId);

						var contentDialog = contentNode.getData('contentDialog');

						if (!contentDialog) {
							contentNode.show();

							contentDialog = Liferay.Util.Window.getWindow(
								{
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
														click: function(event) {
															event.domEvent.preventDefault();

															instance._setContentLabels(portletId);

															contentDialog.hide();
														}
													},
													primary: true
												},
												{
													label: Liferay.Language.get('cancel'),
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															contentDialog.hide();
														}
													}
												}
											]
										},
										width: 400
									},
									title: portletTitle
								}
							);

							contentNode.setData('contentDialog', contentDialog);
						}

						return contentDialog;
					},

					_getContentOptionsDialog: function() {
						var instance = this;

						var contentOptionsDialog = instance._contentOptionsDialog;

						if (!contentOptionsDialog) {
							var contentOptionsNode = instance.byId('contentOptions');

							contentOptionsNode.show();

							contentOptionsDialog = Liferay.Util.Window.getWindow(
								{
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
														click: function(event) {
															event.domEvent.preventDefault();

															instance._setContentOptionsLabels();

															contentOptionsDialog.hide();
														}
													},
													primary: true
												},
												{
													label: Liferay.Language.get('cancel'),
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															contentOptionsDialog.hide();
														}
													}
												}
											]
										},
										width: 400
									},
									title: Liferay.Language.get('comments-and-ratings')
								}
							);

							instance._contentOptionsDialog = contentOptionsDialog;
						}

						return contentOptionsDialog;
					},

					_getGlobalConfigurationDialog: function() {
						var instance = this;

						var globalConfigurationDialog = instance._globalConfigurationDialog;

						if (!globalConfigurationDialog) {
							var globalConfigurationNode = instance.byId('globalConfiguration');

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
													label: Liferay.Language.get('ok'),
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															instance._setGlobalConfigurationLabels();

															globalConfigurationDialog.hide();
														}
													},
													primary: true
												},
												{
													label: Liferay.Language.get('cancel'),
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															globalConfigurationDialog.hide();
														}
													}
												}
											]
										},
										width: 400
									},
									title: Liferay.Language.get('application-configuration')
								}
							);

							instance._globalConfigurationDialog = globalConfigurationDialog;
						}

						return globalConfigurationDialog;
					},

					_getPagesDialog: function() {
						var instance = this;

						var pagesDialog = instance._pagesDialog;

						if (!pagesDialog) {
							var pagesNode = instance.byId('pages');

							pagesNode.show();

							pagesDialog = Liferay.Util.Window.getWindow(
								{
									dialog: {
										bodyContent: pagesNode,
										centered: true,
										modal: true,
										render: instance.get('form'),
										toolbars: {
											footer: [
												{
													label: Liferay.Language.get('ok'),
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															if (instance._layoutsExportTreeOutput) {
																instance._reloadForm();
															}

															pagesDialog.hide();
														}
													},
													primary: true
												},
												{
													label: Liferay.Language.get('cancel'),
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															pagesDialog.hide();
														}
													}
												}
											]
										}
									},
									title: Liferay.Language.get('pages')
								}
							);

							instance._pagesDialog = pagesDialog;
						}

						return pagesDialog;
					},

					_getRangeDialog: function() {
						var instance = this;

						var rangeDialog = instance._rangeDialog;

						if (!rangeDialog) {
							var updateDateRange = A.bind('_updateDateRange', instance);

							var rangeNode = instance.byId('range');

							rangeNode.show();

							rangeDialog = Liferay.Util.Window.getWindow(
								{
									dialog: {
										bodyContent: rangeNode,
										centered: true,
										modal: true,
										render: instance.get('form'),
										toolbars: {
											footer: [
												{
													label: Liferay.Language.get('ok'),
													on: {
														click: updateDateRange
													},
													primary: true
												},
												{
													label: Liferay.Language.get('cancel'),
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															rangeDialog.hide();
														}
													}
												}
											]
										}
									},
									title: Liferay.Language.get('date-range')
								}
							);

							rangeDialog.get('boundingBox').delegate(
								'key',
								updateDateRange,
								'enter',
								'input[type="text"]'
							);

							instance._rangeDialog = rangeDialog;
						}

						return rangeDialog;
					},

					_getRemoteDialog: function() {
						var instance = this;

						var remoteDialog = instance._remoteDialog;

						if (!remoteDialog) {
							var remoteNode = instance.byId('remote');

							remoteNode.show();

							remoteDialog = Liferay.Util.Window.getWindow(
								{
									dialog: {
										bodyContent: remoteNode,
										centered: true,
										height: 300,
										modal: true,
										render: instance.get('form'),
										toolbars: {
											footer: [
												{
													label: Liferay.Language.get('ok'),
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															instance._setRemoteLabels();

															remoteDialog.hide();
														}
													},
													primary: true
												},
												{
													label: Liferay.Language.get('cancel'),
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															remoteDialog.hide();
														}
													}
												}
											]
										},
										width: 400
									},
									title: Liferay.Language.get('remote-live-connection-settings')
								}
							);

							instance._remoteDialog = remoteDialog;
						}

						return remoteDialog;
					},

					_getScheduledPublishingEventsDialog: function() {
						var instance = this;

						var scheduledPublishingEventsDialog = instance._scheduledPublishingEventsDialog;

						if (!scheduledPublishingEventsDialog) {
							var scheduledPublishingEventsNode = instance.byId('scheduledPublishingEvents');

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
													label: Liferay.Language.get('close'),
													on: {
														click: function(event) {
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

					_getValue: function(nodeName) {
						var instance = this;

						var value = STR_EMPTY;

						var node = instance.get(nodeName);

						if (node) {
							value = node.val();
						}

						return value;
					},

					_initLabels: function() {
						var instance = this;

						instance.all('.configuration-link').each(
							function(item, index, collection) {
								instance._setConfigurationLabels(item.attr('data-portletid'));
							}
						);

						instance.all('.content-link').each(
							function(item, index, collection) {
								instance._setContentLabels(item.attr('data-portletid'));
							}
						);

						instance._refreshDeletions();
						instance._setContentOptionsLabels();
						instance._setGlobalConfigurationLabels();
						instance._setPageLabels();
						instance._setRangeLabels();
						instance._setRemoteLabels();
					},

					_isChecked: function(nodeName) {
						var instance = this;

						var node = instance.get(nodeName);

						return (node && node.attr(STR_CHECKED));
					},

					_refreshDeletions: function() {
						var instance = this;

						if (instance._isChecked('deletionsNode')) {
							var deletionsNode = instance.get('deletionsNode');

							instance.all('.deletions').each(
									function(item, index, collection) {
										item.show();
									}
							);
						}
						else {
							instance.all('.deletions').each(
									function(item, index, collection) {
										item.hide();
									}
							);
						}
					},

					_reloadForm: function() {
						var instance = this;

						var cmdNode = instance.byId('cmd');

						if ((cmdNode.val() === 'add') || (cmdNode.val() === 'update')) {
							var redirectNode = instance.byId('redirect');

							var portletURL = Liferay.PortletURL.createURL(redirectNode.val());

							portletURL.setParameter('cmd', cmdNode.val());

							if (instance._exportLAR) {
								portletURL.setParameter('mvcRenderCommandName', 'editExportConfiguration');
								portletURL.setParameter('tabs2', 'new-export-process');
								portletURL.setParameter('exportConfigurationButtons', 'custom');
							}
							else {
								portletURL.setParameter('mvcRenderCommandName', 'editPublishConfiguration');
								portletURL.setParameter('tabs2', 'new-publication-process');
								portletURL.setParameter('publishConfigurationButtons', 'custom');
							}

							var groupIdNode = instance.byId('groupId');

							if (groupIdNode) {
								portletURL.setParameter('groupId', groupIdNode.val());
							}

							var liveGroupIdNode = instance.byId('liveGroupId');

							if (liveGroupIdNode) {
								portletURL.setParameter('liveGroupId', liveGroupIdNode.val());
							}

							var privateLayoutNode = instance.byId('privateLayout');

							if (privateLayoutNode) {
								portletURL.setParameter('privateLayout', privateLayoutNode.val());
							}

							var rootNodeNameNode = instance.byId('rootNodeName');

							if (rootNodeNameNode) {
								portletURL.setParameter('rootNodeName', rootNodeNameNode.val());
							}

							redirectNode.val(portletURL.toString());
						}

						if (cmdNode) {
							cmdNode.val(STR_EMPTY);

							submitForm(instance.get('form'));
						}
					},

					_renderProcesses: function() {
						var instance = this;

						var processesNode = instance.get('processesNode');

						if (processesNode) {
							new A.TogglerDelegate(
								{
									animated: true,
									closeAllOnExpand: true,
									container: processesNode,
									content: '.background-task-status-message',
									expanded: false,
									header: '.details-link',
									on: {
										'toggler:expandedChange': function(event) {
											var header = event.target.get('header');

											var persistId = 0;

											if (!header.hasClass('toggler-header-collapsed')) {
												persistId = header.getData('persist-id');
											}

											Liferay.Store('com.liferay.exportimport.web_backgroundTaskIds', persistId);
										}
									},
									transition: {
										duration: 0.3
									}
								}
							);
						}

						if (processesNode && instance._processesResourceURL) {
							A.io.request(
								instance._processesResourceURL,
								{
									on: {
										failure: function() {
											new Liferay.Notice(
												{
													closeText: false,
													content: Liferay.Language.get('your-request-failed-to-complete') + '<button type="button" class="close">&times;</button>',
													noticeClass: 'hide',
													timeout: FAILURE_TIMEOUT,
													toggleText: false,
													type: 'warning',
													useAnimation: true
												}
											).show();
										},
										success: function(event, id, obj) {
											processesNode.empty();

											processesNode.plug(A.Plugin.ParseContent);

											processesNode.setContent(this.get('responseData'));

											var renderInterval = RENDER_INTERVAL_IDLE;

											var inProgress = !!processesNode.one('.background-task-status-in-progress');

											if (inProgress) {
												renderInterval = RENDER_INTERVAL_IN_PROGRESS;
											}

											instance._updateincompleteProcessMessage(inProgress, processesNode.one('.incomplete-process-message'));

											A.later(renderInterval, instance, instance._renderProcesses);
										}
									}
								}
							);
						}
					},

					_setConfigurationLabels: function(portletId) {
						var instance = this;

						var configurationNode = instance.byId('configuration_' + portletId);

						var inputs = configurationNode.all('.field');

						var selectedConfiguration = [];

						inputs.each(
							function(item, index, collection) {
								var checked = item.attr(STR_CHECKED);

								if (checked) {
									selectedConfiguration.push(item.attr('data-name'));
								}
							}
						);

						if (selectedConfiguration.length === 0) {
							instance.byId('PORTLET_CONFIGURATION_' + portletId).attr('checked', false);

							instance.byId('showChangeConfiguration_' + portletId).hide();
						}

						instance._setLabels('configurationLink_' + portletId, 'selectedConfiguration_' + portletId, selectedConfiguration.join(', '));
					},

					_setContentLabels: function(portletId) {
						var instance = this;

						var contentNode = instance.byId('content_' + portletId);

						var inputs = contentNode.all('.field');

						var selectedContent = [];

						inputs.each(
							function(item, index, collection) {
								var checked = item.attr(STR_CHECKED);

								if (checked) {
									selectedContent.push(item.attr('data-name'));
								}
							}
						);

						if (selectedContent.length === 0) {
							instance.byId('PORTLET_DATA_' + portletId).attr('checked', false);

							instance.byId('showChangeContent_' + portletId).hide();
						}

						instance._setLabels('contentLink_' + portletId, 'selectedContent_' + portletId, selectedContent.join(', '));
					},

					_setContentOptionsLabels: function() {
						var instance = this;

						var selectedContentOptions = [];

						if (instance._isChecked('commentsNode')) {
							selectedContentOptions.push(Liferay.Language.get('comments'));
						}

						if (instance._isChecked('ratingsNode')) {
							selectedContentOptions.push(Liferay.Language.get('ratings'));
						}

						instance._setLabels('contentOptionsLink', 'selectedContentOptions', selectedContentOptions.join(', '));
					},

					_setGlobalConfigurationLabels: function() {
						var instance = this;

						var selectedGlobalConfiguration = [];

						if (instance._isChecked('setupNode')) {
							selectedGlobalConfiguration.push(Liferay.Language.get('setup'));
						}

						if (instance._isChecked('archivedSetupsNode')) {
							selectedGlobalConfiguration.push(Liferay.Language.get('archived-setups'));
						}

						if (instance._isChecked('userPreferencesNode')) {
							selectedGlobalConfiguration.push(Liferay.Language.get('user-preferences'));
						}

						instance._setLabels('globalConfigurationLink', 'selectedGlobalConfiguration', selectedGlobalConfiguration.join(', '));
					},

					_setLabels: function(linkId, labelDivId, label) {
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

					_setNode: function(val) {
						var instance = this;

						if (Lang.isString(val)) {
							val = instance.one(val);
						}
						else {
							val = A.one(val);
						}

						return val;
					},

					_setPageLabels: function() {
						var instance = this;

						var selectedPages = [];

						if (instance._layoutsExportTreeOutput) {
							var layoutIdsInput = instance.byId('layoutIds');

							var treeView = instance._layoutsExportTreeOutput.getData('tree-view');

							var rootNode = treeView.item(0);

							if (rootNode.isChecked()) {
								layoutIdsInput.val(STR_EMPTY);

								selectedPages.push(Liferay.Language.get('all-pages'));
							}
							else {
								selectedPages.push(Liferay.Language.get('selected-pages'));
							}
						}

						if (instance._isChecked('deleteMissingLayoutsNode')) {
							selectedPages.push(Liferay.Language.get('delete-missing-layouts'));
						}

						if (instance._isChecked('layoutSetSettingsNode')) {
							selectedPages.push(Liferay.Language.get('site-pages-settings'));
						}

						if (instance._isChecked('themeReferenceNode')) {
							selectedPages.push(Liferay.Language.get('theme-settings'));
						}

						if (instance._isChecked('logoNode')) {
							selectedPages.push(Liferay.Language.get('logo'));
						}

						instance._setLabels('pagesLink', 'selectedPages', selectedPages.join(', '));
					},

					_setRangeLabels: function() {
						var instance = this;

						var selectedRange = STR_EMPTY;

						if (instance._isChecked('rangeAllNode')) {
							selectedRange = Liferay.Language.get('all');
						}
						else if (instance._isChecked('rangeLastPublishNode')) {
							selectedRange = Liferay.Language.get('from-last-publish-date');
						}
						else if (instance._isChecked('rangeDateRangeNode')) {
							selectedRange = Liferay.Language.get('date-range');
						}
						else if (instance._isChecked('rangeLastNode')) {
							selectedRange = Liferay.Language.get('last');
						}

						instance._setLabels('rangeLink', 'selectedRange', selectedRange);
					},

					_setRemoteLabels: function() {
						var instance = this;

						var selectedRemote = [];

						var remoteAddressValue = instance._getValue('remoteAddressNode');

						if (remoteAddressValue !== STR_EMPTY) {
							selectedRemote.push(remoteAddressValue);
						}

						var remotePortValue = instance._getValue('remotePortNode');

						if (remotePortValue !== STR_EMPTY) {
							selectedRemote.push(remotePortValue);
						}

						var remotePathContextValue = instance._getValue('remotePathContextNode');

						if (remotePathContextValue !== STR_EMPTY) {
							selectedRemote.push(remotePathContextValue);
						}

						var remoteGroupIdValue = instance._getValue('remoteGroupIdNode');

						if (remoteGroupIdValue !== STR_EMPTY) {
							selectedRemote.push(remoteGroupIdValue);
						}

						if (instance._isChecked('secureConnectionNode')) {
							selectedRemote.push(Liferay.Language.get('use-a-secure-network-connection'));
						}

						instance._setLabels('remoteLink', 'selectedRemote', selectedRemote.join(', '));
					},

					_updateDateRange: function(event) {
						var instance = this;

						event.preventDefault();

						var rangeDialog = instance._rangeDialog;

						var endsLater = true;
						var endsInPast = true;
						var startsInPast = true;

						if (instance._isChecked('rangeDateRangeNode')) {
							var startDatePicker = Liferay.component(instance.ns('startDateDatePicker'));
							var startTimePicker = Liferay.component(instance.ns('startTimeTimePicker'));

							var endDatePicker = Liferay.component(instance.ns('endDateDatePicker'));
							var endTimePicker = Liferay.component(instance.ns('endTimeTimePicker'));

							var startDate = startDatePicker.getDate();
							var startTime = startTimePicker.getTime();

							startDate.setHours(startTime.getHours());
							startDate.setMinutes(startTime.getMinutes());
							startDate.setSeconds(0);
							startDate.setMilliseconds(0);

							var endDate = endDatePicker.getDate();
							var endTime = endTimePicker.getTime();

							endDate.setHours(endTime.getHours());
							endDate.setMinutes(endTime.getMinutes());
							endDate.setSeconds(0);
							endDate.setMilliseconds(0);

							endsLater = ADate.isGreater(endDate, startDate);

							var localeString = instance.get('locale');
							var timeZoneString = instance.get('timeZone');

							var today = new Date(new Date().toLocaleString(localeString, {timeZone: timeZoneString}));

							endsInPast = ADate.isGreaterOrEqual(today, endDate);
							startsInPast = ADate.isGreaterOrEqual(today, startDate);
						}

						if (endsLater && endsInPast && startsInPast) {
							instance._reloadForm();

							A.all('.datepicker-popover, .timepicker-popover').hide();

							rangeDialog.hide();
						}
						else {
							var message;

							if (!endsLater) {
								message = Liferay.Language.get('end-date-must-be-greater-than-start-date');
							}
							else if (!endsInPast || !startsInPast) {
								message = Liferay.Language.get('selected-dates-cannot-be-in-the-future');
							}

							if (instance._notice) {
								instance._notice.remove();
							}

							instance._notice = new Liferay.Notice(
								{
									closeText: false,
									content: message + '<button type="button" class="close">&times;</button>',
									timeout: 10000,
									toggleText: false,
									type: 'warning'
								}
							);

							instance._notice.show();
						}
					},

					_updateincompleteProcessMessage: function(inProgress, content) {
						var instance = this;

						var incompleteProcessMessageNode = instance.get('incompleteProcessMessageNode');

						if (incompleteProcessMessageNode) {
							content.show();

							if (inProgress || incompleteProcessMessageNode.hasClass('in-progress')) {
								incompleteProcessMessageNode.setContent(content);

								if (inProgress) {
									incompleteProcessMessageNode.addClass('in-progress');

									incompleteProcessMessageNode.show();
								}
							}
						}
					}
				}
			}
		);

		Liferay.ExportImport = ExportImport;
	},
	'',
	{
		requires: ['aui-datatype', 'aui-dialog-iframe-deprecated', 'aui-io-request', 'aui-modal', 'aui-parse-content', 'aui-toggler', 'aui-tree-view', 'liferay-notice', 'liferay-portlet-base', 'liferay-portlet-url', 'liferay-store', 'liferay-util-window']
	}
);