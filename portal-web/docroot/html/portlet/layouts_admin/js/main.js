AUI.add(
	'liferay-export-import',
	function(A) {
		var Lang = A.Lang;

		var REGEX_LAYOUT_ID = /layoutId_(\d+)/;

		var STR_CHECKED = 'checked';

		var STR_CLICK = 'click';

		var STR_EMPTY = '';

		var defaultConfig = {
			setter: '_setNode'
		};

		var ExportImport = A.Component.create(
			{
				ATTRS: {
					alwaysCurrentUserIdNode: defaultConfig,
					archivedSetupsNode: defaultConfig,
					commentsNode: defaultConfig,
					copyAsNewNode: defaultConfig,
					currentUserIdNode: defaultConfig,
					deleteMissingLayoutsNode: defaultConfig,
					deletePortletDataNode: defaultConfig,
					form: defaultConfig,
					layoutSetSettingsNode: defaultConfig,
					logoNode: defaultConfig,
					mirrorNode: defaultConfig,
					mirrorWithOverwritingNode: defaultConfig,
					rangeAllNode: defaultConfig,
					rangeDateRangeNode: defaultConfig,
					rangeLastNode: defaultConfig,
					rangeLastPublishNode: defaultConfig,
					ratingsNode: defaultConfig,
					remoteAddressNode: defaultConfig,
					remoteDeletePortletDataNode: defaultConfig,
					remotePortNode: defaultConfig,
					remotePathContextNode: defaultConfig,
					remoteGroupIdNode: defaultConfig,
					secureConnectionNode: defaultConfig,
					themeNode: defaultConfig,
					themeReferenceNode: defaultConfig,
					userPreferencesNode: defaultConfig
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'exportimport',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._bindUI();

						instance._initLabels();
					},

					destructor: function() {
						var instance = this;

						if (instance._commentsAndRatingsDialog) {
							instance._commentsAndRatingsDialog.destroy();
						}

						if (instance._globalConfigurationDialog) {
							instance._globalConfigurationDialog.destroy();
						}

						if (instance._globalContentDialog) {
							instance._globalContentDialog.destroy();
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

						instance.get('form').delegate(
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

						var commentsAndRatingsLink = instance.byId('commentsAndRatingsLink');

						if (commentsAndRatingsLink) {
							commentsAndRatingsLink.on(
								STR_CLICK,
								function(event) {
									var commentsAndRatingsDialog = instance._getCommentsAndRatingsDialog();

									commentsAndRatingsDialog.show();
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

						var globalContentLink = instance.byId('globalContentLink');

						if (globalContentLink) {
							globalContentLink.on(
								STR_CLICK,
								function(event) {
									var globalContentDialog = instance._getGlobalContentDialog();

									globalContentDialog.show();
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

					_getCommentsAndRatingsDialog: function() {
						var instance = this;

						var commentsAndRatingsDialog = instance._commentsAndRatingsDialog;

						if (!commentsAndRatingsDialog) {
							var commentsAndRatingsNode = instance.byId('commentsAndRatings');

							commentsAndRatingsNode.show();

							commentsAndRatingsDialog = Liferay.Util.Window.getWindow(
								{
									dialog: {
										bodyContent: commentsAndRatingsNode,
										centered: true,
										height: 300,
										modal: true,
										render: instance.get('form'),
										toolbars: {
											footer: [
												{
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															instance._setCommentsAndRatingsLabels();

															commentsAndRatingsDialog.hide();
														}
													},
													label: Liferay.Language.get('ok'),
													primary: true
												},
												{
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															commentsAndRatingsDialog.hide();
														}
													},
													label: Liferay.Language.get('cancel')
												}
											]
										},
										width: 400
									},
									title: Liferay.Language.get('comments-and-ratings')
								}
							);

							instance._commentsAndRatingsDialog = commentsAndRatingsDialog;
						}

						return commentsAndRatingsDialog;
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
										height: 300,
										modal: true,
										render: instance.get('form'),
										toolbars: {
											footer: [
												{
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															instance._setContentLabels(portletId);

															contentDialog.hide();
														}
													},
													label: Liferay.Language.get('ok'),
													primary: true
												},
												{
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															contentDialog.hide();
														}
													},
													label: Liferay.Language.get('cancel')
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
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															instance._setGlobalConfigurationLabels();

															globalConfigurationDialog.hide();
														}
													},
													label: Liferay.Language.get('ok'),
													primary: true
												},
												{
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															globalConfigurationDialog.hide();
														}
													},
													label: Liferay.Language.get('cancel')
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

					_getGlobalContentDialog: function() {
						var instance = this;

						var globalContentDialog = instance._globalContentDialog;

						if (!globalContentDialog) {
							var globalContentNode = instance.byId('globalContent');

							globalContentNode.show();

							globalContentDialog = Liferay.Util.Window.getWindow(
								{
									dialog: {
										bodyContent: globalContentNode,
										centered: true,
										height: 300,
										modal: true,
										render: instance.get('form'),
										toolbars: {
											footer: [
												{
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															instance._setGlobalContentLabels();

															globalContentDialog.hide();
														}
													},
													label: Liferay.Language.get('ok'),
													primary: true
												},
												{
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															globalContentDialog.hide();
														}
													},
													label: Liferay.Language.get('cancel')
												}
											]
										},
										width: 400
									},
									title: Liferay.Language.get('all-content')
								}
							);

							instance._globalContentDialog = globalContentDialog;
						}

						return globalContentDialog;
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
										height: 300,
										modal: true,
										render: instance.get('form'),
										toolbars: {
											footer: [
												{
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															instance._setPageLabels();

															pagesDialog.hide();
														}
													},
													label: Liferay.Language.get('ok'),
													primary: true
												},
												{
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															pagesDialog.hide();
														}
													},
													label: Liferay.Language.get('cancel')
												}
											]
										},
										width: 400
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
							var rangeNode = instance.byId('range');

							rangeNode.show();

							rangeDialog = Liferay.Util.Window.getWindow(
								{
									dialog: {
										bodyContent: rangeNode,
										centered: true,
										height: 300,
										modal: true,
										render: instance.get('form'),
										toolbars: {
											footer: [
												{
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															instance._reloadForm();

															rangeDialog.hide();
														}
													},
													label: Liferay.Language.get('ok'),
													primary: true
												},
												{
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															rangeDialog.hide();
														}
													},
													label: Liferay.Language.get('cancel')
												}
											]
										},
										width: 400
									},
									title: Liferay.Language.get('date-range')
								}
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
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															instance._setRemoteLabels();

															remoteDialog.hide();
														}
													},
													label: Liferay.Language.get('ok'),
													primary: true
												},
												{
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															remoteDialog.hide();
														}
													},
													label: Liferay.Language.get('cancel')
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
										height: 300,
										centered: true,
										modal: true,
										render: instance.get('form'),
										toolbars: {
											footer: [
												{
													on: {
														click: function(event) {
															event.domEvent.preventDefault();

															scheduledPublishingEventsDialog.hide();
														}
													},
													label: Liferay.Language.get('close')
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

						instance.all('.content-link').each(
							function(item, index, collection) {
								instance._setContentLabels(item.attr('data-portletid'));
							}
						);

						instance._setCommentsAndRatingsLabels();
						instance._setGlobalConfigurationLabels();
						instance._setGlobalContentLabels();
						instance._setPageLabels();
						instance._setRangeLabels();
						instance._setRemoteLabels();
					},

					_isChecked: function(nodeName) {
						var instance = this;

						var node = instance.get(nodeName);

						return (node && node.attr(STR_CHECKED));
					},

					_reloadForm: function() {
						var instance = this;

						instance.byId('cmd').val(STR_EMPTY);

						submitForm(instance.get('form'));
					},

					_setCommentsAndRatingsLabels: function() {
						var instance = this;

						var selectedCommentsAndRatings = [];

						if (instance._isChecked('commentsNode')) {
							selectedCommentsAndRatings.push(Liferay.Language.get('comments'));
						}

						if (instance._isChecked('ratingsNode')) {
							selectedCommentsAndRatings.push(Liferay.Language.get('ratings'));
						}

						instance._setLabels('commentsAndRatingsLink', 'selectedCommentsAndRatings', selectedCommentsAndRatings.join(', '));
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

						instance._setLabels('contentLink_' + portletId, 'selectedContent_' + portletId, selectedContent.join(', '));
					},

					_setGlobalConfigurationLabels: function() {
						var instance = this;

						var selectedGlobalConfiguration = [];

						if (instance._isChecked('archivedSetupsNode')) {
							selectedGlobalConfiguration.push(Liferay.Language.get('archived-setups'));
						}

						if (instance._isChecked('userPreferencesNode')) {
							selectedGlobalConfiguration.push(Liferay.Language.get('user-preferences'));
						}

						instance._setLabels('globalConfigurationLink', 'selectedGlobalConfiguration', selectedGlobalConfiguration.join(', '));
					},

					_setGlobalContentLabels: function() {
						var instance = this;

						var selectedGlobalContent = [];

						if (instance._isChecked('deletePortletDataNode')) {
							selectedGlobalContent.push(Liferay.Language.get('delete-portlet-data-before-importing'));
						}

						if (instance._isChecked('mirrorNode')) {
							selectedGlobalContent.push(Liferay.Language.get('mirror'));
						}

						if (instance._isChecked('mirrorWithOverwritingNode')) {
							selectedGlobalContent.push(Liferay.Language.get('mirror-with-overwriting'));
						}

						if (instance._isChecked('copyAsNewNode')) {
							selectedGlobalContent.push(Liferay.Language.get('copy-as-new'));
						}

						if (instance._isChecked('currentUserIdNode')) {
							selectedGlobalContent.push(Liferay.Language.get('use-the-original-author'));
						}

						if (instance._isChecked('alwaysCurrentUserIdNode')) {
							selectedGlobalContent.push(Liferay.Language.get('use-the-current-user-as-author'));
						}

						instance._setLabels('globalContentLink', 'selectedGlobalContent', selectedGlobalContent.join(', '));
					},

					_setLabels: function(linkId, labelDivId, label) {
						var instance = this;

						var linkNode = instance.byId(linkId);

						if (linkNode) {
							if (label !== STR_EMPTY) {
								linkNode.html(Liferay.Language.get('change'))
							}
							else {
								linkNode.html(Liferay.Language.get('select'))
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

						var layoutsExportTreeOutput = instance.byId('layoutsExportTreeOutput');

						if (layoutsExportTreeOutput) {
							var layoutIdsInput = instance.byId('layoutIds');

							var treeView = layoutsExportTreeOutput.getData('treeInstance');

							var rootNode = treeView.item(0);

							if (rootNode.isChecked()) {
								layoutIdsInput.val(STR_EMPTY);

								selectedPages.push(Liferay.Language.get('all-pages'));
							}
							else {
								var layoutIds = [];

								treeView.eachChildren(
									function(item, index, collection) {
										if (item.isChecked()) {
											var match = REGEX_LAYOUT_ID.exec(item.get('id'));

											if (match) {
												layoutIds.push(
													{
														includeChildren: !item.hasChildNodes(),
														layoutId: match[1]
													}
												);
											}
										}
									},
									true
								);

								if (layoutIdsInput) {
									layoutIdsInput.val(A.JSON.stringify(layoutIds));
								}

								selectedPages.push(Liferay.Language.get('selected-pages'));
							}
						}

						if (instance._isChecked('deleteMissingLayoutsNode')) {
							selectedPages.push(Liferay.Language.get('delete-missing-layouts'));
						}

						if (instance._isChecked('layoutSetSettingsNode')) {
							selectedPages.push(Liferay.Language.get('site-pages-settings'));
						}

						if (instance._isChecked('themeNode')) {
							selectedPages.push(Liferay.Language.get('theme'));
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

						if (instance._isChecked('remoteDeletePortletDataNode')) {
							selectedRemote.push(Liferay.Language.get('delete-portlet-data-before-importing'));
						}

						instance._setLabels('remoteLink', 'selectedRemote', selectedRemote.join(', '));
					}
				}
			}
		);

		Liferay.ExportImport = ExportImport;
	},
	'',
	{
		requires: ['aui-dialog-iframe-deprecated', 'aui-modal', 'aui-tree-view', 'liferay-portlet-base','liferay-util-window']
	}
);