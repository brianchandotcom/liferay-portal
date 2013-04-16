AUI.add(
	'export-layouts',
	function(A) {
		var Lang = A.Lang;

		var CHECKED = 'checked';

		var CLICK = 'click';

		var REGEX_LAYOUT_ID = /layoutId_(\d+)/;

		var SET_NODE = '_setNode';

		var STR_EMPTY = '';

		var ExportLayouts = A.Component.create(
			{
				ATTRS: {
					archivedSetupsNode: {
						setter: SET_NODE
					},
					categoriesNode: {
						setter: SET_NODE
					},
					layoutSetSettingsNode: {
						setter: SET_NODE
					},
					logoNode: {
						setter: SET_NODE
					},
					rangeAllNode: {
						setter: SET_NODE
					},
					rangeDateRangeNode: {
						setter: SET_NODE
					},
					rangeLastNode: {
						setter: SET_NODE
					},
					rangeLastPublishNode: {
						setter: SET_NODE
					},
					themeNode: {
						setter: SET_NODE
					},
					themeReferenceNode: {
						setter: SET_NODE
					},
					userPreferencesNode: {
						setter: SET_NODE
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'exportlayouts',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._attachChangeLinks();
					},

					destructor: function() {
						var instance = this;

						if (instance._changeContentDialog) {
							instance._changeContentDialog.destroy();
						}

						if (instance._changeGlobalConfigurationDialog) {
							instance._changeGlobalConfigurationDialog.destroy();
						}

						if (instance._changeGlobalContentDialog ) {
							instance._changeGlobalContentDialog.destroy();
						}

						if (instance._changePagesDialog) {
							instance._changePagesDialog.destroy();
						}

						if (instance._changeRangeDialog) {
							instance._changeRangeDialog.destroy();
						}
					},

					_attachChangeLinks: function() {
						var instance = this;

						instance.byId('fm1').delegate(
							CLICK,
							function(event) {
								var portletId = event.currentTarget.attr('data-portletid');

								var changeContentDialog = instance._getChangeContentDialog(portletId);

								changeContentDialog.show();
							},
							'.change-content-link a'
						);

						instance.byId('changeGlobalConfigurationLink').on(
							CLICK,
							function(event) {
								var changeGlobalConfigurationDialog = instance._getChangeGlobalConfigurationDialog();

								changeGlobalConfigurationDialog.show();
							}
						);

						instance.byId('changeGlobalContentLink').on(
							CLICK,
							function(event) {
								var changeGlobalContentDialog = instance._getChangeGlobalContentDialog();

								changeGlobalContentDialog.show();
							}
						);

						instance.byId('changePagesLink').on(
							CLICK,
							function(event) {
								var changePagesDialog = instance._getChangePagesDialog();

								changePagesDialog.show();
							}
						);

						instance.byId('changeRangeLink').on(
							CLICK,
							function(event) {
								var changeRangeDialog = instance._getChangeRangeDialog();

								changeRangeDialog.show();
							}
						);
					},

					_getChangeContentDialog: function(portletId) {
						var instance = this;

						var changeContentDialog = instance._changeContentDialog;

						if (!changeContentDialog) {
							var changeContentNode = instance.byId('changeContent_' + portletId);

							changeContentNode.show();

							var contentBox;

							changeContentDialog = new A.Dialog(
								{
									align: Liferay.Util.Window.ALIGN_CENTER,
									bodyContent: changeContentNode,
									centered: true,
									modal: true,
									title: Liferay.Language.get('content-to-export'),
									width: 400,
									buttons: [
										{
											handler: function() {
												var inputs = contentBox.all('.aui-field-input-choice');

												var selectedContent = [];

												A.each(
													inputs,
													function(item, index, collection) {
														var checked = item.attr(CHECKED);

														if (checked) {
															var itemName = item.attr('id');

															itemName = itemName.substring(0, itemName.length - 8);
															itemName = itemName.substring(itemName.lastIndexOf('_') + 1, itemName.length);

															selectedContent.push(Liferay.Language.get(itemName));
														}
													}
												);

												instance._refreshSelectedLabel('selectedContent_' + portletId, selectedContent.join(', '));

												this.hide();
											},
											label: Liferay.Language.get('ok')
										},
										{
											handler: function() {
												this.hide();
											},
											label: Liferay.Language.get('cancel')
										}
									]
								}
							);

							changeContentDialog.render();

							contentBox = changeContentDialog.get('contentBox');

							instance._changeContentDialog = changeContentDialog;
						}

						return changeContentDialog;
					},

					_getChangeGlobalConfigurationDialog: function() {
						var instance = this;

						var changeGlobalConfigurationDialog = instance._changeGlobalConfigurationDialog;

						if (!changeGlobalConfigurationDialog) {
							var changeGlobalConfigurationNode = instance.byId('changeGlobalConfiguration');

							changeGlobalConfigurationNode.show();

							changeGlobalConfigurationDialog = new A.Dialog(
								{
									align: Liferay.Util.Window.ALIGN_CENTER,
									bodyContent: changeGlobalConfigurationNode,
									buttons: [
										{
											handler: function() {
												var selectedGlobalConfiguration = [];

												if (instance.get('archivedSetupsNode').attr(CHECKED)) {
													selectedGlobalConfiguration.push(Liferay.Language.get('archived-setups'));
												}

												if (instance.get('userPreferencesNode').attr(CHECKED)) {
													selectedGlobalConfiguration.push(Liferay.Language.get('user-preferences'));
												}

												instance._refreshSelectedLabel('selectedGlobalConfiguration', selectedGlobalConfiguration.join(', '));

												this.hide();
											},
											label: Liferay.Language.get('ok')
										},
										{
											handler: function() {
												this.hide();
											},
											label: Liferay.Language.get('cancel')
										}
									],
									centered: true,
									modal: true,
									title: Liferay.Language.get('application-configuration'),
									width: 400
								}
							);

							changeGlobalConfigurationDialog.render();

							instance._changeGlobalConfigurationDialog = changeGlobalConfigurationDialog;
						}

						return changeGlobalConfigurationDialog;
					},

					_getChangeGlobalContentDialog: function() {
						var instance = this;

						var changeGlobalContentDialog = instance._changeGlobalContentDialog;

						if (!changeGlobalContentDialog) {
							var changeGlobalContentNode = instance.byId('changeGlobalContent');

							changeGlobalContentNode.show();

							changeGlobalContentDialog = new A.Dialog(
								{
									align: Liferay.Util.Window.ALIGN_CENTER,
									buttons: [
										{
											handler: function() {
												var selectedGlobalContent = STR_EMPTY;

												if (instance.get('categoriesNode').attr(CHECKED)) {
													selectedGlobalContent = Liferay.Language.get('categories');
												}

												instance._refreshSelectedLabel('selectedGlobalContent', selectedGlobalContent);

												this.hide();
											},
											label: Liferay.Language.get('ok')
										},
										{
											handler: function() {
												this.hide();
											},
											label: Liferay.Language.get('cancel')
										}
									],
									bodyContent: changeGlobalContentNode,
									centered: true,
									modal: true,
									title: Liferay.Language.get('content-to-export'),
									width: 400
								}
							);

							changeGlobalContentDialog.render();

							instance._changeGlobalContentDialog = changeGlobalContentDialog;
						}

						return changeGlobalContentDialog;
					},

					_getChangePagesDialog: function() {
						var instance = this;

						var changePagesDialog = instance._changePagesDialog;

						if (!changePagesDialog) {
							var changePagesNode = instance.byId('changePages');

							changePagesNode.show();

							changePagesDialog = new A.Dialog(
								{
									align: Liferay.Util.Window.ALIGN_CENTER,
									bodyContent: changePagesNode,
									buttons: [
										{
											handler: function() {
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

													if (instance.get('layoutSetSettingsNode').attr(CHECKED)) {
														selectedPages.push(Liferay.Language.get('site-pages-settings'));
													}

													if (instance.get('themeNode').attr(CHECKED)) {
														selectedPages.push(Liferay.Language.get('theme'));
													}

													if (instance.get('themeReferenceNode').attr(CHECKED)) {
														selectedPages.push(Liferay.Language.get('theme-settings'));
													}

													if (instance.get('logoNode').attr(CHECKED)) {
														selectedPages.push(Liferay.Language.get('logo'));
													}

													instance._refreshSelectedLabel('selectedPages', selectedPages.join(', '));
												}

												this.hide();
											},
											label: Liferay.Language.get('ok')
										},
										{
											handler: function() {
												this.hide();
											},
											label: Liferay.Language.get('cancel')
										}
									],
									modal: true,
									title: Liferay.Language.get('pages'),
									width: 400
								}
							);

							changePagesDialog.render();

							instance._changePagesDialog = changePagesDialog;
						}

						return changePagesDialog;
					},

					_getChangeRangeDialog: function() {
						var instance = this;

						var changeRangeDialog = instance._changeRangeDialog;

						if (!changeRangeDialog) {
							var changeRangeNode = instance.byId('changeRange');

							changeRangeNode.show();

							changeRangeDialog = new A.Dialog(
								{
									align: Liferay.Util.Window.ALIGN_CENTER,
									bodyContent: changeRangeNode,
									buttons: [
										{
											handler: function() {
												var selectedRange = STR_EMPTY;

												if (instance.get('rangeAllNode').attr(CHECKED)) {
													selectedRange = Liferay.Language.get('all');
												}
												else if (instance.get('rangeLastPublishNode').attr(CHECKED)) {
													selectedRange = Liferay.Language.get('from-last-publish-date');
												}
												else if (instance.get('rangeDateRangeNode').attr(CHECKED)) {
													selectedRange = Liferay.Language.get('date-range');
												}
												else if (instance.get('rangeLastNode').attr(CHECKED)) {
													selectedRange = Liferay.Language.get('last');
												}

												instance._refreshSelectedLabel('selectedRange', selectedRange);

												this.hide();
											},
											label: Liferay.Language.get('ok')
										},
										{
											handler: function() {
												this.hide();
											},
											label: Liferay.Language.get('cancel')
										}
									],
									centered: true,
									modal: true,
									title: Liferay.Language.get('content-to-export'),
									width: 400
								}
							);

							changeRangeDialog.render();

							instance._changeRangeDialog = changeRangeDialog;
						}

						return changeRangeDialog;
					},

					_refreshSelectedLabel: function(labelDivId, label) {
						var instance = this;

						var labelNode = instance.byId(labelDivId);

						if (labelNode) {
							labelNode.html(label);
						}
					},

					_setNode: function(value) {
						var instance = this;

						if (Lang.isString(value)) {
							value = instance.byId(value);
						}

						return value;
					}
				}
			}
		);

		Liferay.ExportLayouts = ExportLayouts;
	},
	'',
	{
		requires: ['aui-dialog', 'liferay-portlet-base']
	}
);