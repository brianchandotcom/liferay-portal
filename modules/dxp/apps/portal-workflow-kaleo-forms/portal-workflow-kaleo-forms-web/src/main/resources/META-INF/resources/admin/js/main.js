/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-kaleo-forms-admin',
	(A) => {
		const Lang = A.Lang;

		const STEPS_MAP = {
			DETAILS: 1,
			FIELDS: 2,
			FORMS: 4,
			WORKFLOW: 3,
		};

		const KaleoFormsAdmin = A.Component.create({
			ATTRS: {
				currentURL: {
					value: null,
				},

				form: {
					value: null,
				},

				kaleoProcessId: {
					value: null,
				},

				portletId: {
					value: null,
				},

				saveInPortletSessionURL: {
					value: null,
				},

				tabView: {
					value: null,
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'liferay-kaleo-forms-admin',

			prototype: {
				_afterCurrentStepChange(event) {
					const instance = this;

					const sessionMap = instance._getSessionMap();

					instance.saveInPortletSession(sessionMap);

					const currentStep = event.newVal;

					if (currentStep !== STEPS_MAP.DETAILS) {
						let currentName =
							sessionMap['name' + themeDisplay.getLanguageId()];

						if (!currentName) {
							currentName =
								sessionMap[
									'name' + themeDisplay.getDefaultLanguageId()
								];
						}

						currentName = Liferay.Util.escapeHTML(currentName);

						instance
							.one(
								'.control-menu-level-1-nav .tools-control-group .control-menu-level-1-heading'
							)
							.setContent(currentName);
					}

					if (currentStep === STEPS_MAP.FORMS) {
						instance._loadFormsStep();
					}

					instance._hideSuccessMessage();

					instance.syncUI();
				},

				_afterValidateField(event) {
					const instance = this;

					const tabView = instance.get('tabView');

					const activeTab = tabView.getActiveTab();

					const tabViewTabs = tabView.getTabs();

					const activeTabIndex = tabViewTabs.indexOf(activeTab);

					const tabViewPanels = instance.formWizard.getTabViewPanels();

					const activePanel = tabViewPanels.item(activeTabIndex);

					if (activePanel.contains(event.validator.field)) {
						const currentStepValid =
							event.type.indexOf('errorField') === -1;

						instance.updateNavigationControls(currentStepValid);
					}
				},

				_getInputLocalizedValuesMap(inputLocalized, name) {
					const localizedValuesMap = {};

					const translatedLanguages = inputLocalized
						.get('translatedLanguages')
						.values();

					localizedValuesMap[
						'translatedLanguages' + Lang.String.capitalize(name)
					] = translatedLanguages.join();

					translatedLanguages.forEach((item) => {
						localizedValuesMap[
							name + item
						] = inputLocalized.getValue(item);
					});

					return localizedValuesMap;
				},

				_getSessionMap() {
					const instance = this;

					const descriptionInputLocalized = Liferay.component(
						instance.ns('description')
					);
					const nameInputLocalized = Liferay.component(
						instance.ns('name')
					);

					const sessionMap = {
						...instance._getInputLocalizedValuesMap(
							descriptionInputLocalized,
							'description'
						),
						...instance._getInputLocalizedValuesMap(
							nameInputLocalized,
							'name'
						),
					};

					const ddmStructureId = instance
						.one('#ddmStructureId')
						.val();
					const ddmStructureName = instance
						.one('#ddmStructureName')
						.val();
					const ddmTemplateId = instance.one('#ddmTemplateId').val();
					const kaleoTaskFormPairsData = instance
						.one('#kaleoTaskFormPairsData')
						.val();
					const workflowDefinition = instance
						.one('#workflowDefinition')
						.val();

					return {
						...sessionMap,
						ddmStructureId,
						ddmStructureName,
						ddmTemplateId,
						kaleoTaskFormPairsData,
						workflowDefinition,
					};
				},

				_hideSuccessMessage() {
					const instance = this;

					const successMessageNode = instance.one('.alert-success');

					if (successMessageNode) {
						successMessageNode.hide();
					}
				},

				_isCurrentStepValid() {
					const instance = this;

					const formWizard = instance.formWizard;

					const currentStep = formWizard.get('currentStep');

					return formWizard.validateStep(currentStep);
				},

				_loadFormsStep() {
					const instance = this;

					const currentURL = instance.get('currentURL');

					const formsSearchContainer =
						'#' + instance.NS + 'formsSearchContainer';

					const kaleoProcessId = instance.get('kaleoProcessId');

					const resultsContainer = instance.one('#resultsContainer');

					const workflowDefinition = instance
						.one('#workflowDefinition')
						.val();

					const backURL = new Liferay.PortletURL(
						Liferay.PortletURL.RENDER_PHASE,
						null,
						currentURL
					);

					backURL.setParameter('historyKey', 'forms');

					const formsURL = new Liferay.PortletURL(
						Liferay.PortletURL.RENDER_PHASE,
						null,
						currentURL
					);

					formsURL.setParameter(
						'mvcPath',
						'/admin/process/task_template_search_container.jsp'
					);
					formsURL.setParameter('backURL', backURL.toString());
					formsURL.setParameter('kaleoProcessId', kaleoProcessId);
					formsURL.setParameter(
						'workflowDefinition',
						workflowDefinition
					);

					resultsContainer.plug(A.LoadingMask).loadingmask.show();

					resultsContainer.load(
						formsURL.toString(),
						formsSearchContainer,
						() => {
							A.each(
								A.all(
									formsSearchContainer +
										' .lfr-icon-menu .dropdown-toggle'
								),
								(item) => {
									Liferay.Menu.register(item.get('id'));
								}
							);

							resultsContainer.unplug(A.LoadingMask);
						}
					);
				},

				_onClickNext() {
					const instance = this;

					instance.formWizard.navigate(1);
				},

				_onClickPrev() {
					const instance = this;

					instance.formWizard.navigate(-1);
				},

				_onSubmitForm(event) {
					const instance = this;

					event.preventDefault();

					if (instance.formWizard.validateStep(STEPS_MAP.FORMS)) {
						submitForm(event.target);
					}
				},

				bindUI() {
					const instance = this;

					const form = instance.get('form');

					form.formNode.on(
						'submit',
						instance._onSubmitForm,
						instance
					);

					instance.formWizard.after(
						'currentStepChange',
						instance._afterCurrentStepChange,
						instance
					);

					instance.formWizard.validator.after(
						['errorField', 'validField'],
						instance._afterValidateField,
						instance
					);

					instance.nextBtn.on(
						'click',
						instance._onClickNext,
						instance
					);
					instance.prevBtn.on(
						'click',
						instance._onClickPrev,
						instance
					);
				},

				initializer() {
					const instance = this;

					instance.nextBtn = instance.one('.kaleo-process-next');
					instance.prevBtn = instance.one('.kaleo-process-previous');
					instance.submitBtn = instance.one('.kaleo-process-submit');

					instance.formWizard = new Liferay.KaleoFormWizard({
						form: instance.get('form'),
						tabView: instance.get('tabView'),
					});

					instance.bindUI();
					instance.syncUI();
				},

				saveInPortletSession(data) {
					const instance = this;

					// eslint-disable-next-line @liferay/aui/no-io
					A.io.request(instance.get('saveInPortletSessionURL'), {
						data: instance.ns(data),
					});
				},

				syncUI() {
					const instance = this;

					instance.updateNavigationControls();

					instance.formWizard.validator.resetAllFields();
				},

				updateNavigationControls(currentStepValid) {
					const instance = this;

					if (currentStepValid === undefined) {
						currentStepValid = instance._isCurrentStepValid();
					}

					instance.nextBtn.attr('disabled', !currentStepValid);
					instance.nextBtn.toggleClass('disabled', !currentStepValid);

					instance.submitBtn.attr('disabled', !currentStepValid);
					instance.submitBtn.toggleClass(
						'disabled',
						!currentStepValid
					);

					const formWizard = instance.formWizard;

					const currentStep = formWizard.get('currentStep');

					if (currentStep === STEPS_MAP.DETAILS) {
						instance.nextBtn.show();
						instance.prevBtn.hide();
						instance.submitBtn.hide();
					}
					else if (currentStep === STEPS_MAP.FORMS) {
						instance.nextBtn.hide();
						instance.prevBtn.show();
						instance.submitBtn.show();
					}
					else {
						instance.nextBtn.show();
						instance.prevBtn.show();
						instance.submitBtn.hide();
					}
				},
			},
		});

		const inputElement = document.querySelector('.lfr-input-text');

		inputElement.addEventListener('keydown', (event) => {
			if (event.keyCode === 13) {
				event.preventDefault();
			}
		});

		Liferay.KaleoFormsAdmin = KaleoFormsAdmin;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-loading-mask-deprecated',
			'aui-parse-content',
			'aui-url',
			'liferay-kaleo-forms-components',
			'liferay-portlet-url',
			'liferay-store',
			'node-load',
		],
	}
);
