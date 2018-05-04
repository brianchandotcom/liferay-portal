AUI.add(
	'liferay-kaleo-forms-components',
	function(A) {
		var KeyMap = A.Event.KeyMap;

		var Lang = A.Lang;

		var KaleoFormWizard = A.Component.create(
			{
				ATTRS: {
					currentStep: {
						valueFn: '_valueCurrentStep'
					},

					form: {
						value: null
					},

					tabView: {
						value: null
					}
				},

				EXTENDS: A.Base,

				NAME: 'liferay-kaleo-form-wizard',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance.form = instance.get('form');

						instance.form.addTarget(instance);

						instance.tabView = instance.get('tabView');

						instance.tabView.addTarget(instance);

						instance.validator = instance.form.formValidator;

						instance.validator.set('validateOnBlur', false);

						instance.validator.set('validateOnInput', true);

						instance.validator.addTarget(instance);

						instance.bindUI();
					},

					bindUI: function() {
						var instance = this;

						instance.after('tab:selectedChange', instance._afterTabSelectedChange, instance);
						instance.on('tab:selectedChange', instance._onTabSelectedChange, instance);
					},

					_afterTabSelectedChange: function(event) {
						var instance = this;

						var tabView = instance.tabView;

						if (event.newVal === 1) {
							var activeTabIndex = tabView.indexOf(event.target);

							var currentStep = activeTabIndex + 1;

							instance.set('currentStep', currentStep);
						}
					},

					_onTabSelectedChange: function(event) {
						var instance = this;

						var tabView = instance.tabView;

						if (event.newVal === 1) {
							var activeTabIndex = tabView.indexOf(event.target);

							if (!instance.validateStep(activeTabIndex)) {
								event.preventDefault();
							}
						}
					},

					_valueCurrentStep: function() {
						var instance = this;

						var tabView = instance.get('tabView');

						var activeTabIndex = tabView.indexOf(tabView.get('selection'));

						return activeTabIndex + 1;
					},

					getTabViewPanels: function() {
						var instance = this;

						var queries = A.TabviewBase._queries;

						return instance.tabView.get('contentBox').all(queries.tabPanel);
					},

					navigate: function(offset) {
						var instance = this;

						var tabView = instance.tabView;

						var activeTab = tabView.getActiveTab();
						var tabViewTabs = tabView.getTabs();

						var newActiveTabIndex = tabViewTabs.indexOf(activeTab) + offset;

						var newActiveTab = tabView.item(newActiveTabIndex);

						if (newActiveTab) {
							tabView.selectChild(newActiveTabIndex);
						}
					},

					validatePanel: function(panel) {
						var instance = this;

						var validator = instance.validator;

						validator.eachRule(
							function(rule, fieldName) {
								var field = validator.getField(fieldName);

								if (panel.contains(field)) {
									validator.validateField(field);
								}
							}
						);
					},

					validateStep: function(step) {
						var instance = this;

						var tabViewPanels = instance.getTabViewPanels();

						var tabViewTabs = instance.tabView.getTabs();

						var valid = true;

						instance.validator.resetAllFields();

						tabViewPanels.each(
							function(item, index, collection) {
								if (index <= step - 1) {
									instance.validatePanel(item);

									var tabNode = tabViewTabs.item(index);

									var tabHasError = item.one('.error-field');

									tabNode.toggleClass('section-error', tabHasError);
									tabNode.toggleClass('section-success', !tabHasError);

									if (tabHasError) {
										valid = false;
									}
								}
							}
						);

						return valid;
					}
				}
			}
		);

		Liferay.KaleoFormWizard = KaleoFormWizard;

		var ReadOnlyFormBuilderSupport = function() {};

		ReadOnlyFormBuilderSupport.ATTRS = {
			formBuilder: {
				setter: '_setFormBuilder',
				valueFn: '_valueFormBuilder'
			}
		};

		A.mix(
			ReadOnlyFormBuilderSupport.prototype,
			{
				initializer: function() {
					var instance = this;

					var formBuilder = instance.get('formBuilder');

					formBuilder.after('render', instance._afterRenderReadOnlyFormBuilder);

					formBuilder.after('*:focusedChange', instance._afterFieldFocusedChangeReadOnlyFormBuilder);

					formBuilder.dropContainer.delegate('mouseover', instance._onMouseOverFieldReadOnlyFormBuilder, '.form-builder-field');
				},

				_afterFieldFocusedChangeReadOnlyFormBuilder: function() {
					var instance = this;

					instance.unselectFields();
				},

				_afterRenderReadOnlyFormBuilder: function() {
					var instance = this;

					instance.fieldsSortableList.destroy();
				},

				_onMouseOverFieldReadOnlyFormBuilder: function(event) {
					var field = A.Widget.getByNode(event.currentTarget);

					field.controlsToolbar.hide();

					field.get('boundingBox').removeClass('form-builder-field-hover');
				},

				_setFormBuilder: function(val) {
					return new Liferay.FormBuilder(val);
				},

				_valueFormBuilder: function() {
					var instance = this;

					return {
						allowRemoveRequiredFields: false,
						enableEditing: false,
						portletNamespace: instance.get('namespace'),
						tabView: {
							render: false
						},
						translationManager: {
							visible: false
						},
						visible: false
					};
				}
			}
		);

		var TPL_MESSAGE = '<div class="alert alert-info">{message}</div>';

		var KaleoDefinitionPreview = A.Component.create(
			{
				ATTRS: {
					availableDefinitions: {
						value: []
					},

					dialog: {
						setter: '_setDialog',
						valueFn: '_valueDialog'
					},

					height: {
					},

					selectedDefinitionId: {
					},

					width: {
					}
				},

				AUGMENTS: [Liferay.PortletBase, ReadOnlyFormBuilderSupport],

				EXTENDS: A.Base,

				NAME: 'liferay-kaleo-definition-preview',

				prototype: {
					initializer: function() {
						var instance = this;

						var dialog = instance.get('dialog');

						dialog.bodyNode.prepend(
							Lang.sub(
								TPL_MESSAGE,
								{
									message: Liferay.Language.get('press-enter-to-choose-this-field-set-or-use-arrow-keys-to-navigate-through-the-available-field-sets.-press-esc-at-anytime-to-close-this-dialog')
								}
							)
						);

						dialog.on('keyup', instance._onDialogKeyUp, instance);
					},

					_onDialogKeyUp: function(event) {
						var instance = this;

						var availableDefinitions = instance.get('availableDefinitions');

						var definition = instance.getDefinition();

						var selectedIndex = availableDefinitions.indexOf(definition);

						var keyCode = event.domEvent.keyCode;

						if (KeyMap.isKey(keyCode, 'ENTER')) {
							instance.choose();
						}
						else {
							var index = -1;

							if (KeyMap.isKey(keyCode, 'LEFT')) {
								index = selectedIndex - 1;
							}
							else if (KeyMap.isKey(keyCode, 'RIGHT')) {
								index = selectedIndex + 1;
							}

							var definitionPreview = availableDefinitions[index];

							if (definitionPreview) {
								instance.select(definitionPreview.definitionId);

								instance.preview();
							}
						}
					},

					_setDialog: function(val) {
						return Liferay.Util.Window.getWindow(val);
					},

					_syncDialog: function() {
						var instance = this;

						var definition = instance.getDefinition();

						var dialog = instance.get('dialog');

						dialog.titleNode.html(definition.definitionName);

						dialog.fillHeight(dialog.bodyNode);
					},

					_syncFormBuilder: function() {
						var instance = this;

						var definition = instance.getDefinition();

						var formBuilder = instance.get('formBuilder');

						formBuilder.render();

						formBuilder.set('fields', definition.definitionFields);
					},

					_valueDialog: function() {
						var instance = this;

						var formBuilder = instance.get('formBuilder');

						return {
							dialog: {
								bodyContent: formBuilder.get('boundingBox'),
								cssClass: 'kaleo-process-preview-fields-dialog',
								height: instance.get('height'),
								render: '#p_p_id' + instance.get('namespace'),
								toolbars: {
									footer: [
										{
											label: Liferay.Language.get('choose'),
											on: {
												click: function() {
													instance.choose();
												}
											}
										},
										{
											label: Liferay.Language.get('cancel'),
											on: {
												click: function() {
													instance.get('dialog').hide();
												}
											}
										}
									],
									header: [
										{
											cssClass: 'close',
											label: '\u00D7',
											on: {
												click: function() {
													instance.get('dialog').hide();
												}
											}
										}
									]
								},
								visible: false,
								width: instance.get('width')
							},
							title: Liferay.Language.get('preview')
						};
					},

					choose: function() {
						var instance = this;

						instance.fire('choose', instance.getDefinition());

						var dialog = instance.get('dialog');

						dialog.hide();

						var nextBtn = instance.one('.kaleo-process-next');

						if (nextBtn) {
							nextBtn.focus();
						}
					},

					getDefinition: function(definitionId) {
						var instance = this;

						var availableDefinitions = instance.get('availableDefinitions');

						definitionId = definitionId || instance.get('selectedDefinitionId');

						var definition;

						availableDefinitions.forEach(
							function(item, index) {
								if (Lang.toInt(item.definitionId) === Lang.toInt(definitionId)) {
									definition = item;
									return;
								}
							}
						);

						return definition;
					},

					preview: function() {
						var instance = this;

						var formBuilder = instance.get('formBuilder');

						formBuilder.show();

						instance._syncFormBuilder();

						var dialog = instance.get('dialog');

						dialog.show();

						instance._syncDialog();
					},

					select: function(definitionId) {
						var instance = this;

						var availableDefinitions = instance.get('availableDefinitions');

						var selectedDefinitionId = -1;

						availableDefinitions.forEach(
							function(item, index) {
								if (Lang.toInt(item.definitionId) === Lang.toInt(definitionId)) {
									selectedDefinitionId = definitionId;
									return;
								}
							}
						);

						instance.set('selectedDefinitionId', selectedDefinitionId);
					}
				}
			}
		);

		Liferay.KaleoDefinitionPreview = KaleoDefinitionPreview;
	},
	'',
	{
		requires: ['aui-base', 'aui-datepicker-deprecated', 'aui-tabview', 'liferay-portlet-base', 'liferay-portlet-dynamic-data-mapping-custom-fields', 'liferay-util-window']
	}
);