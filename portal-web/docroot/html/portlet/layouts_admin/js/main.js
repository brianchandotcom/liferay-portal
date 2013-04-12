AUI.add(
	'export-layouts',
	function(A) {
		var Lang = A.Lang;

		var isString = Lang.isString;

		var ExportLayouts = A.Component.create(
			{
				ATTRS: {
					namespace: {
						validator: isString
					},
					archivedSetupsCheckbox: {
						validator: isString
					},
					categoriesCheckbox: {
						validator: isString
					},
					layoutSetSettingsCheckbox: {
						validator: isString
					},
					logoCheckbox: {
						validator: isString
					},
					themeCheckbox: {
						validator: isString
					},
					themeReferenceCheckbox: {
						validator: isString
					},
					userPreferencesCheckbox: {
						validator: isString
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
					},

					_attachChangeLinks: function() {
						var instance = this;

						instance.byId('fm1').delegate(
							'click',
							function(event) {
								var portletId = event.currentTarget.attr('data-portletid');

								var changeContentNode = instance.byId('changeContent_' + portletId);

								var changeContentDialog = changeContentNode.getData('changeContentDialog');

								if (!changeContentDialog) {
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
													label: Liferay.Language.get('ok'),
													handler: function() {
														var inputs = contentBox.all('.aui-field-input-choice');
														var selectedContent = [];

														A.each(
															inputs,
															function(item, index, collection) {
																var checked = item.attr('checked');

																if (checked == true) {
																	var itemName = item.attr('id');

																	itemName = itemName.substring(0, itemName.length - 8);
																	itemName = itemName.substring(itemName.lastIndexOf('_') + 1, itemName.length);

																	selectedContent.push(Liferay.Language.get(itemName));
																}
															}
														);

														instance._refreshSelectedLabel('selectedContent_' + portletId, selectedContent.join(', '));

														this.hide();
													}
												},
												{
													label: Liferay.Language.get('cancel'),
													handler: function() {
														this.hide();
													}
												}
											]
										}
									);

									changeContentDialog.render();

									contentBox = changeContentDialog.get('contentBox');

									changeContentNode.setData('changeContentDialog', changeContentDialog);
								}
								else {
									changeContentDialog.show();
								}
							},
							'.change-content-link a'
						);

						instance.byId('changeGlobalConfigurationLink').on(
							'click',
							function(event) {
								var changeGlobalConfigurationNode = instance.byId('changeGlobalConfiguration');

								var changeGlobalConfigurationDialog = changeGlobalConfigurationNode.getData('changeGlobalConfigurationDialog');

								if (!changeGlobalConfigurationDialog) {
									changeGlobalConfigurationNode.show();

									changeGlobalConfigurationDialog = new A.Dialog(
										{
											align: Liferay.Util.Window.ALIGN_CENTER,
											bodyContent: changeGlobalConfigurationNode,
											centered: true,
											modal: true,
											title: Liferay.Language.get('application-configuration'),
											width: 400,
											buttons: [
												{
													label: Liferay.Language.get('ok'),
													handler: function() {
														var selectedGlobalConfiguration = [];

														if (instance.byId(instance.get('archivedSetupsCheckbox')).attr('checked') == true) {
															selectedGlobalConfiguration.push(Liferay.Language.get('archived-setups'));
														}

														if (instance.byId(instance.get('userPreferencesCheckbox')).attr('checked') == true) {
															selectedGlobalConfiguration.push(Liferay.Language.get('user-preferences'));
														}

														instance._refreshSelectedLabel('selectedGlobalConfiguration', selectedGlobalConfiguration.join(', '));

														this.hide();
													}
												},
												{
													label: Liferay.Language.get('cancel'),
													handler: function() {
														this.hide();
													}
												}
											]
										}
									);

									changeGlobalConfigurationDialog.render();

									changeGlobalConfigurationNode.setData('changeGlobalConfigurationDialog', changeGlobalConfigurationDialog);
								}
								else {
									changeGlobalConfigurationDialog.show();
								}
							}
						);

						instance.byId('changeGlobalContentLink').on(
							'click',
							function(event) {
								var changeGlobalContentNode = instance.byId('changeGlobalContent');

								var changeGlobalContentDialog = changeGlobalContentNode.getData('changeGlobalContentDialog');

								if (!changeGlobalContentDialog) {
									changeGlobalContentNode.show();

									changeGlobalContentDialog = new A.Dialog(
										{
											align: Liferay.Util.Window.ALIGN_CENTER,
											bodyContent: changeGlobalContentNode,
											centered: true,
											modal: true,
											title: Liferay.Language.get('content-to-export'),
											width: 400,
											buttons: [
												{
													label: Liferay.Language.get('ok'),
													handler: function() {
														selectedGlobalContent = '';

														if (instance.byId(instance.get('categoriesCheckbox')).attr('checked') == true) {
															selectedGlobalContent = Liferay.Language.get('categories');
														}

														instance._refreshSelectedLabel('selectedGlobalContent', selectedGlobalContent);

														this.hide();
													}
												},
												{
													label: Liferay.Language.get('cancel'),
													handler: function() {
														this.hide();
													}
												}
											]
										}
									);

									changeGlobalContentDialog.render();

									changeGlobalContentNode.setData('changeGlobalContentDialog', changeGlobalContentDialog);
								}
								else {
									changeGlobalContentDialog.show();
								}
							}
						);

						instance.byId('changePagesLink').on(
							'click',
							function(event) {
								var changePagesNode = instance.byId('changePages');

								var changePagesDialog = changePagesNode.getData('changePagesDialog');

								if (!changePagesDialog) {
									changePagesNode.show();

									changePagesDialog = new A.Dialog(
										{
											align: Liferay.Util.Window.ALIGN_CENTER,
											bodyContent: changePagesNode,
											modal: true,
											title: Liferay.Language.get('pages'),
											width: 400,
											buttons: [
												{
													label: Liferay.Language.get('ok'),
													handler: function() {
														var selectedPages = [];

														var layoutsExportTreeOutput = instance.byId('layoutsExportTreeOutput');

														if (layoutsExportTreeOutput) {
															var layoutIdsInput = instance.byId('layoutIds');

															var treeView = layoutsExportTreeOutput.getData('treeInstance');
															var rootNode = treeView.item(0);

															if (rootNode.isChecked()) {
																layoutIdsInput.val('');

																selectedPages.push(Liferay.Language.get('all-pages'));
															}
															else {
																var layoutIds = [];

																var regexLayoutId = /layoutId_(\d+)/;

																treeView.eachChildren(
																	function(item, index, collection) {
																		if (item.isChecked()) {
																			var match = regexLayoutId.exec(item.get('id'));

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

															if (instance.byId(instance.get('layoutSetSettingsCheckbox')).attr('checked') == true) {
																selectedPages.push(Liferay.Language.get('site-pages-settings'));
															}

															if (instance.byId(instance.get('themeCheckbox')).attr('checked') == true) {
																selectedPages.push(Liferay.Language.get('theme'));
															}

															if (instance.byId(instance.get('themeReferenceCheckbox')).attr('checked') == true) {
																selectedPages.push(Liferay.Language.get('theme-settings'));
															}

															if (instance.byId(instance.get('logoCheckbox')).attr('checked') == true) {
																selectedPages.push(Liferay.Language.get('logo'));
															}

															instance._refreshSelectedLabel('selectedPages', selectedPages.join(', '));
														}

														this.hide();
													}
												},
												{
													label: Liferay.Language.get('cancel'),
													handler: function() {
														this.hide();
													}
												}
											]
										}
									);

									changePagesDialog.render();

									changePagesNode.setData('changePagesDialog', changePagesDialog);
								}
								else {
									changePagesDialog.show();
								}
							}
						);

						instance.byId('changeRangeLink').on(
							'click',
							function(event) {
								var changeRangeNode = instance.byId('changeRange');

								var changeRangeDialog = changeRangeNode.getData('changeRangeDialog');

								if (!changeRangeDialog) {
									changeRangeNode.show();

									changeRangeDialog = new A.Dialog(
										{
											align: Liferay.Util.Window.ALIGN_CENTER,
											bodyContent: changeRangeNode,
											centered: true,
											modal: true,
											title: Liferay.Language.get('content-to-export'),
											width: 400,
											buttons: [
												{
													label: Liferay.Language.get('ok'),
													handler: function() {
														var selectedRange = '';

														if (instance.byId(instance.get('rangeAllCheckbox')).attr('checked') == true) {
															selectedRange = Liferay.Language.get('all');
														}
														else if (instance.byId(instance.get('rangeLastPublishCheckbox')).attr('checked') == true) {
															selectedRange = Liferay.Language.get('from-last-publish-date');
														}
														else if (instance.byId(instance.get('rangeDateRangeCheckbox')).attr('checked') == true) {
															selectedRange = Liferay.Language.get('date-range');
														}
														else if (instance.byId(instance.get('rangeLastCheckbox')).attr('checked') == true) {
															selectedRange = Liferay.Language.get('last');
														}

														instance._refreshSelectedLabel('selectedRange', selectedRange);

														this.hide();
													}
												},
												{
													label: Liferay.Language.get('cancel'),
													handler: function() {
														this.hide();
													}
												}
											]
										}
									);

									changeRangeDialog.render();

									changeRangeNode.setData('changeRangeDialog', changeRangeDialog);
								}
								else {
									changeRangeDialog.show();
								}
							}
						);
					},

					_refreshSelectedLabel: function(labelDivId, label) {
						var instance = this;

						var labelDiv = A.one('#' + instance.NS + labelDivId);

						if (labelDiv != null) {
							labelDiv.html(label);
						}
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