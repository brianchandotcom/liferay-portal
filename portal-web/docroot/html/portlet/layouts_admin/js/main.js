AUI.add(
	'export-layouts',
	function(A) {
		var Lang = A.Lang;

		var REGEX_LAYOUT_ID = /layoutId_(\d+)/;

		var STR_CHECKED = 'checked';

		var STR_CLICK = 'click';

		var STR_EMPTY = '';

		var defaultConfig = {
			setter: '_setNode'
		};

		var ExportLayouts = A.Component.create(
			{
				ATTRS: {
					archivedSetupsNode: defaultConfig,
					categoriesNode: defaultConfig,
					layoutSetSettingsNode: defaultConfig,
					logoNode: defaultConfig,
					rangeAllNode: defaultConfig,
					rangeDateRangeNode: defaultConfig,
					rangeLastNode: defaultConfig,
					rangeLastPublishNode: defaultConfig,
					themeNode: defaultConfig,
					themeReferenceNode: defaultConfig,
					userPreferencesNode: defaultConfig
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'exportlayouts',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						if (instance._contentDialog) {
							instance._contentDialog.destroy();
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
					},

					_bindUI: function() {
						var instance = this;

						instance.byId('fm1').delegate(
							STR_CLICK,
							function(event) {
								var portletId = event.currentTarget.attr('data-portletid');

								var contentDialog = instance._getContentDialog(portletId);

								contentDialog.show();
							},
							'.content-link a'
						);

						instance.byId('globalConfigurationLink').on(
							STR_CLICK,
							function(event) {
								var globalConfigurationDialog = instance._getGlobalConfigurationDialog();

								globalConfigurationDialog.show();
							}
						);

						instance.byId('globalContentLink').on(
							STR_CLICK,
							function(event) {
								var globalContentDialog = instance._getGlobalContentDialog();

								globalContentDialog.show();
							}
						);

						instance.byId('pagesLink').on(
							STR_CLICK,
							function(event) {
								var pagesDialog = instance._getPagesDialog();

								pagesDialog.show();
							}
						);

						instance.byId('rangeLink').on(
							STR_CLICK,
							function(event) {
								var rangeDialog = instance._getRangeDialog();

								rangeDialog.show();
							}
						);
					},

					_getContentDialog: function(portletId) {
						var instance = this;

						var contentDialog = instance._contentDialog;

						if (!contentDialog) {
							var contentNode = instance.byId('content_' + portletId);

							contentNode.show();

							contentDialog = new A.Dialog(
								{
									align: Liferay.Util.Window.ALIGN_CENTER,
									bodyContent: contentNode,
									centered: true,
									modal: true,
									title: Liferay.Language.get('content-to-export'),
									width: 400,
									buttons: [
										{
											handler: function() {
												instance._handleContent(portletId);

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
							).render();

							instance._contentDialog = contentDialog;
						}

						return contentDialog;
					},

					_getGlobalConfigurationDialog: function() {
						var instance = this;

						var globalConfigurationDialog = instance._globalConfigurationDialog;

						if (!globalConfigurationDialog) {
							var globalConfigurationNode = instance.byId('globalConfiguration');

							globalConfigurationNode.show();

							globalConfigurationDialog = new A.Dialog(
								{
									align: Liferay.Util.Window.ALIGN_CENTER,
									bodyContent: globalConfigurationNode,
									buttons: [
										{
											handler: function() {
												instance._handleGlobalConfiguration();

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
							).render();

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

							globalContentDialog = new A.Dialog(
								{
									align: Liferay.Util.Window.ALIGN_CENTER,
									buttons: [
										{
											handler: function() {
												instance._handleGlobalContent();

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
									bodyContent: globalContentNode,
									centered: true,
									modal: true,
									title: Liferay.Language.get('content-to-export'),
									width: 400
								}
							).render();

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

							pagesDialog = new A.Dialog(
								{
									align: Liferay.Util.Window.ALIGN_CENTER,
									bodyContent: pagesNode,
									buttons: [
										{
											handler: function() {
												instance._handlePages();

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
							).render();

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

							rangeDialog = new A.Dialog(
								{
									align: Liferay.Util.Window.ALIGN_CENTER,
									bodyContent: rangeNode,
									buttons: [
										{
											handler: function() {
												instance._handleRange();

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
							).render();

							instance._rangeDialog = rangeDialog;
						}

						return rangeDialog;
					},

					_handleContent: function(portletId) {
						var instance = this;

						var contentDialog = instance._getContentDialog();

						var inputs = contentDialog.get('boundingBox').all('.aui-field-input-choice');

						var selectedContent = [];

						inputs.each(
							function(item, index, collection) {
								var checked = item.attr(STR_CHECKED);

								if (checked) {
									selectedContent.push(item.attr('data-name'));
								}
							}
						);

						instance._refreshSelectedLabel('selectedContent_' + portletId, selectedContent.join(', '));
					},

					_handleGlobalConfiguration: function() {
						var instance = this;

						var selectedGlobalConfiguration = [];

						if (instance.get('archivedSetupsNode').attr(STR_CHECKED)) {
							selectedGlobalConfiguration.push(Liferay.Language.get('archived-setups'));
						}

						if (instance.get('userPreferencesNode').attr(STR_CHECKED)) {
							selectedGlobalConfiguration.push(Liferay.Language.get('user-preferences'));
						}

						instance._refreshSelectedLabel('selectedGlobalConfiguration', selectedGlobalConfiguration.join(', '));
					},

					_handleGlobalContent: function() {
						var instance = this;

						var selectedGlobalContent = STR_EMPTY;

						if (instance.get('categoriesNode').attr(STR_CHECKED)) {
							selectedGlobalContent = Liferay.Language.get('categories');
						}

						instance._refreshSelectedLabel('selectedGlobalContent', selectedGlobalContent);
					},

					_handlePages: function() {
						var instance = this;

						var layoutsExportTreeOutput = instance.byId('layoutsExportTreeOutput');

						if (layoutsExportTreeOutput) {
							var selectedPages = [];

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

							if (instance.get('layoutSetSettingsNode').attr(STR_CHECKED)) {
								selectedPages.push(Liferay.Language.get('site-pages-settings'));
							}

							if (instance.get('themeNode').attr(STR_CHECKED)) {
								selectedPages.push(Liferay.Language.get('theme'));
							}

							if (instance.get('themeReferenceNode').attr(STR_CHECKED)) {
								selectedPages.push(Liferay.Language.get('theme-settings'));
							}

							if (instance.get('logoNode').attr(STR_CHECKED)) {
								selectedPages.push(Liferay.Language.get('logo'));
							}

							instance._refreshSelectedLabel('selectedPages', selectedPages.join(', '));
						}
					},

					_handleRange: function() {
						var instance = this;

						var selectedRange = STR_EMPTY;

						if (instance.get('rangeAllNode').attr(STR_CHECKED)) {
							selectedRange = Liferay.Language.get('all');
						}
						else if (instance.get('rangeLastPublishNode').attr(STR_CHECKED)) {
							selectedRange = Liferay.Language.get('from-last-publish-date');
						}
						else if (instance.get('rangeDateRangeNode').attr(STR_CHECKED)) {
							selectedRange = Liferay.Language.get('date-range');
						}
						else if (instance.get('rangeLastNode').attr(STR_CHECKED)) {
							selectedRange = Liferay.Language.get('last');
						}

						instance._refreshSelectedLabel('selectedRange', selectedRange);
					},

					_refreshSelectedLabel: function(labelDivId, label) {
						var instance = this;

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

						return val;
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