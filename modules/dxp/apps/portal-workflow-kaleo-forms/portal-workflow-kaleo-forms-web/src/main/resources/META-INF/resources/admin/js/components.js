/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-kaleo-forms-components',
	(A) => {
		const KeyMap = A.Event.KeyMap;

		const Lang = A.Lang;

		const KaleoFormWizard = A.Component.create({
			ATTRS: {
				currentStep: {
					valueFn: '_valueCurrentStep',
				},

				form: {
					value: null,
				},

				tabView: {
					value: null,
				},
			},

			EXTENDS: A.Base,

			NAME: 'liferay-kaleo-form-wizard',

			prototype: {
				_afterTabSelectedChange(event) {
					const instance = this;

					const tabView = instance.tabView;

					if (event.newVal === 1) {
						const activeTabIndex = tabView.indexOf(event.target);

						const currentStep = activeTabIndex + 1;

						instance.set('currentStep', currentStep);
					}
				},

				_onTabSelectedChange(event) {
					const instance = this;

					const tabView = instance.tabView;

					if (event.newVal === 1) {
						const activeTabIndex = tabView.indexOf(event.target);

						if (!instance.validateStep(activeTabIndex)) {
							event.preventDefault();
						}
					}
				},

				_valueCurrentStep() {
					const instance = this;

					const tabView = instance.get('tabView');

					const activeTabIndex = tabView.indexOf(
						tabView.get('selection')
					);

					return activeTabIndex + 1;
				},

				bindUI() {
					const instance = this;

					instance.after(
						'tab:selectedChange',
						instance._afterTabSelectedChange,
						instance
					);
					instance.on(
						'tab:selectedChange',
						instance._onTabSelectedChange,
						instance
					);
				},

				getTabViewPanels() {
					const instance = this;

					const queries = A.TabviewBase._queries;

					return instance.tabView
						.get('contentBox')
						.all(queries.tabPanel);
				},

				initializer() {
					const instance = this;

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

				navigate(offset) {
					const instance = this;

					const tabView = instance.tabView;

					const activeTab = tabView.getActiveTab();
					const tabViewTabs = tabView.getTabs();

					const newActiveTabIndex =
						tabViewTabs.indexOf(activeTab) + offset;

					const newActiveTab = tabView.item(newActiveTabIndex);

					if (newActiveTab) {
						tabView.selectChild(newActiveTabIndex);
					}
				},

				validatePanel(panel) {
					const instance = this;

					const validator = instance.validator;

					validator.eachRule((rule, fieldName) => {
						const field = validator.getField(fieldName);

						if (panel.contains(field)) {
							validator.validateField(field);
						}
					});
				},

				validateStep(step) {
					const instance = this;

					const tabViewPanels = instance.getTabViewPanels();

					const tabViewTabs = instance.tabView.getTabs();

					let valid = true;

					instance.validator.resetAllFields();

					tabViewPanels.each((item, index) => {
						if (index <= step - 1) {
							instance.validatePanel(item);

							const tabNode = tabViewTabs.item(index);

							const tabHasError = item.one('.error-field');

							tabNode.toggleClass('section-error', tabHasError);
							tabNode.toggleClass(
								'section-success',
								!tabHasError
							);

							if (tabHasError) {
								valid = false;
							}
						}
					});

					return valid;
				},
			},
		});

		Liferay.KaleoFormWizard = KaleoFormWizard;

		const ReadOnlyFormBuilderSupport = function () {};

		ReadOnlyFormBuilderSupport.ATTRS = {
			formBuilder: {
				setter: '_setFormBuilder',
				valueFn: '_valueFormBuilder',
			},
		};

		A.mix(ReadOnlyFormBuilderSupport.prototype, {
			_afterFieldFocusedChangeReadOnlyFormBuilder() {
				const instance = this;

				instance.unselectFields();
			},

			_afterRenderReadOnlyFormBuilder() {
				const instance = this;

				instance.fieldsSortableList.destroy();
			},

			_onMouseOverFieldReadOnlyFormBuilder(event) {
				const field = A.Widget.getByNode(event.currentTarget);

				field.controlsToolbar.hide();

				field
					.get('boundingBox')
					.removeClass('form-builder-field-hover');
			},

			_setFormBuilder(val) {
				return new Liferay.FormBuilder(val);
			},

			_valueFormBuilder() {
				const instance = this;

				return {
					allowRemoveRequiredFields: false,
					enableEditing: false,
					portletNamespace: instance.get('namespace'),
					tabView: {
						render: false,
					},
					translationManager: {
						visible: false,
					},
					visible: false,
				};
			},

			initializer() {
				const instance = this;

				const formBuilder = instance.get('formBuilder');

				formBuilder.after(
					'render',
					instance._afterRenderReadOnlyFormBuilder
				);

				formBuilder.after(
					'*:focusedChange',
					instance._afterFieldFocusedChangeReadOnlyFormBuilder
				);

				formBuilder.dropContainer.delegate(
					'mouseover',
					instance._onMouseOverFieldReadOnlyFormBuilder,
					'.form-builder-field'
				);
			},
		});

		const TPL_MESSAGE = '<div class="alert alert-info">{message}</div>';

		const KaleoDefinitionPreview = A.Component.create({
			ATTRS: {
				availableDefinitions: {
					value: [],
				},

				dialog: {
					setter: '_setDialog',
					valueFn: '_valueDialog',
				},

				height: {},

				selectedDefinitionId: {},

				width: {},
			},

			AUGMENTS: [Liferay.PortletBase, ReadOnlyFormBuilderSupport],

			EXTENDS: A.Base,

			NAME: 'liferay-kaleo-definition-preview',

			prototype: {
				_onDialogKeyUp(event) {
					const instance = this;

					const availableDefinitions = instance.get(
						'availableDefinitions'
					);

					const definition = instance.getDefinition();

					const selectedIndex = availableDefinitions.indexOf(
						definition
					);

					const keyCode = event.domEvent.keyCode;

					if (KeyMap.isKey(keyCode, 'ENTER')) {
						instance.choose();
					}
					else {
						let index = -1;

						if (KeyMap.isKey(keyCode, 'LEFT')) {
							index = selectedIndex - 1;
						}
						else if (KeyMap.isKey(keyCode, 'RIGHT')) {
							index = selectedIndex + 1;
						}

						const definitionPreview = availableDefinitions[index];

						if (definitionPreview) {
							instance.select(definitionPreview.definitionId);

							instance.preview();
						}
					}
				},

				_setDialog(val) {
					return Liferay.Util.Window.getWindow(val);
				},

				_syncDialog() {
					const instance = this;

					const definition = instance.getDefinition();

					const dialog = instance.get('dialog');

					dialog.titleNode.html(definition.definitionName);

					dialog.fillHeight(dialog.bodyNode);
				},

				_syncFormBuilder() {
					const instance = this;

					const definition = instance.getDefinition();

					const formBuilder = instance.get('formBuilder');

					formBuilder.render();

					formBuilder.set('fields', definition.definitionFields);
				},

				_valueDialog() {
					const instance = this;

					const formBuilder = instance.get('formBuilder');

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
											click() {
												instance.choose();
											},
										},
									},
									{
										label: Liferay.Language.get('cancel'),
										on: {
											click() {
												instance.get('dialog').hide();
											},
										},
									},
								],
								header: [
									{
										cssClass: 'close',
										label: '\u00D7',
										on: {
											click() {
												instance.get('dialog').hide();
											},
										},
									},
								],
							},
							visible: false,
							width: instance.get('width'),
						},
						title: Liferay.Language.get('preview'),
					};
				},

				choose() {
					const instance = this;

					instance.fire('choose', instance.getDefinition());

					const dialog = instance.get('dialog');

					dialog.hide();

					const nextBtn = instance.one('.kaleo-process-next');

					if (nextBtn) {
						nextBtn.focus();
					}
				},

				getDefinition(definitionId) {
					const instance = this;

					const availableDefinitions = instance.get(
						'availableDefinitions'
					);

					definitionId =
						definitionId || instance.get('selectedDefinitionId');

					let definition;

					availableDefinitions.forEach((item) => {
						if (
							Lang.toInt(item.definitionId) ===
							Lang.toInt(definitionId)
						) {
							definition = item;

							return;
						}
					});

					return definition;
				},

				initializer() {
					const instance = this;

					const dialog = instance.get('dialog');

					dialog.bodyNode.prepend(
						Lang.sub(TPL_MESSAGE, {
							message: Liferay.Language.get(
								'press-enter-to-choose-this-field-set-or-use-arrow-keys-to-navigate-through-the-available-field-sets.-press-escape-at-anytime-to-close-this-dialog'
							),
						})
					);

					dialog.on('keyup', instance._onDialogKeyUp, instance);
				},

				preview() {
					const instance = this;

					const formBuilder = instance.get('formBuilder');

					formBuilder.show();

					instance._syncFormBuilder();

					const dialog = instance.get('dialog');

					dialog.show();

					instance._syncDialog();
				},

				select(definitionId) {
					const instance = this;

					const availableDefinitions = instance.get(
						'availableDefinitions'
					);

					let selectedDefinitionId = -1;

					availableDefinitions.forEach((item) => {
						if (
							Lang.toInt(item.definitionId) ===
							Lang.toInt(definitionId)
						) {
							selectedDefinitionId = definitionId;

							return;
						}
					});

					instance.set('selectedDefinitionId', selectedDefinitionId);
				},
			},
		});

		Liferay.KaleoDefinitionPreview = KaleoDefinitionPreview;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-datepicker-deprecated',
			'aui-tabview',
			'liferay-portlet-base',
			'liferay-portlet-dynamic-data-mapping-custom-fields',
			'liferay-util-window',
		],
	}
);
