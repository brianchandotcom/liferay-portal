AUI.add(
	'liferay-ddm-form-renderer',
	function(A) {
		var Renderer = Liferay.DDM.Renderer;

		var TPL_CONTAINER = '<div class="lfr-ddm-form-container"></div>';

		var Form = A.Component.create(
			{
				ATTRS: {
					container: {
						setter: A.one,
						valueFn: '_valueContainer'
					},

					dataProviderURL: {
						value: ''
					},

					defaultLanguageId: {
						value: themeDisplay.getDefaultLanguageId()
					},

					editingLanguageId: {
						valueFn: '_valueEditingLanguageId'
					},

					enableEvaluations: {
						value: true
					},

					portletNamespace: {
						value: ''
					},

					strings: {
						value: {
							next: Liferay.Language.get('next'),
							previous: Liferay.Language.get('previous'),
							requestErrorMessage: Liferay.Language.get('there-was-an-error-when-trying-to-validate-your-form'),
							requiredFields: Liferay.Language.get('all-fields-marked-with-x-are-required')
						}
					},

					viewMode: {
						value: false
					}
				},

				AUGMENTS: [
					Renderer.FormContextSupport,
					Renderer.FormEvaluationSupport,
					Renderer.FormFeedbackSupport,
					Renderer.FormPaginationSupport,
					Renderer.FormTabsSupport,
					Renderer.FormTemplateSupport,
					Renderer.FormValidationSupport,
					Renderer.NestedFieldsSupport
				],

				EXTENDS: A.Base,

				NAME: 'liferay-ddm-form-renderer',

				prototype: {
					_eventHandlers: [],

					initializer: function() {
						var instance = this;

						var formNode = instance.getFormNode();

						var readOnly = instance.get('readOnly');

						if (formNode && !readOnly) {
							instance._eventHandlers.push(
								formNode.on('submit', A.bind('_onSubmitForm', instance)),
								Liferay.on('submitForm', instance._onLiferaySubmitForm, instance)
							);
						}

						instance.after('render', instance._afterFormRender);
					},

					destructor: function() {
						var instance = this;

						instance.get('container').remove();

						(new A.EventHandle(instance._eventHandlers)).detach();
					},

					getEvaluationPayload: function() {
						var instance = this;

						var portletNamespace = instance.get('portletNamespace');

						var languageId = instance.get('editingLanguageId');

						return {
							languageId: languageId,
							p_auth: Liferay.authToken,
							portletNamespace: portletNamespace,
							serializedFormContext: JSON.stringify(instance.get('context'))
						};
					},

					getFormId: function() {
						var instance = this;

						var formNode = instance.getFormNode();

						if (!formNode) {
							return 0;
						}

						return formNode.getData('DDMFormInstanceId');
					},

					getFormNode: function() {
						var instance = this;

						var container = instance.get('container');

						return container.ancestor('form', true);
					},

					getSubmitButton: function() {
						var instance = this;

						var container = instance.get('container');

						var formNode = instance.getFormNode();

						return (formNode || container).one('[type="submit"]');
					},

					hasFocus: function(node) {
						var instance = this;

						var container = instance.get('container');

						var hasFocus = false;

						instance.eachNestedField(
							function(field) {
								hasFocus = field.hasFocus(node);

								return hasFocus;
							}
						);

						return hasFocus || container.contains(document.activeElement);
					},

					submit: function() {
						var instance = this;

						instance.validate(
							function(hasErrors) {
								if (!hasErrors) {
									var formNode = instance.getFormNode();

									var currentPageInput = formNode.one('#' + instance.get('portletNamespace') + 'currentPage');

									if (currentPageInput) {
										currentPageInput.set('value', instance.getCurrentPage());
									}

									instance.showLoadingFeedback();

									Liferay.fire(
										'ddmFormSubmit',
										{
											formId: instance.getFormId()
										}
									);

									Liferay.Util.submitForm(formNode);
								}
							}
						);
					},

					toJSON: function() {
						var instance = this;

						return instance.get('context');
					},

					_afterFormRender: function() {
						var instance = this;

						instance.eachNestedField(
							function(field) {
								field.render();
							}
						);

						var submitButton = instance.getSubmitButton();

						if (submitButton) {
							submitButton.attr('disabled', false);
						}

						var container = instance.get('container');

						Liferay.fire(
							Liferay.namespace('DDM').Form + ':render',
							{
								containerId: container.get('id')
							}
						);
					},

					_valueEditingLanguageId: function() {
						var instance = this;

						var portletNamespace = instance.get('portletNamespace');

						var languageId = instance._getURLParameter(portletNamespace, 'languageId');

						if (!languageId) {
							languageId = instance.get('defaultLanguageId');
						}

						return languageId;
					},

					_getURLParameter: function(portletNamespace, parameterName) {
						var currentUrl = window.location.href;

						var url = new A.Url(currentUrl);

						return url.getParameter(portletNamespace + parameterName);
					},

					_onLiferaySubmitForm: function(event) {
						var instance = this;

						if (event.form === instance.getFormNode()) {
							event.preventDefault();
						}
					},

					_onSubmitForm: function(event) {
						var instance = this;

						event.preventDefault();

						var currentPage = instance.getCurrentPage();
						var pagesTotal = instance.getPagesTotal();

						if (pagesTotal > 1 && currentPage < pagesTotal) {
							instance.nextPage();
						}
						else {
							instance.submit();
						}
					},

					_valueContainer: function() {
						var instance = this;

						return A.Node.create(TPL_CONTAINER);
					}
				}
			}
		);

		Liferay.namespace('DDM.Renderer').Form = Form;
	},
	'',
	{
		requires: ['aui-component', 'liferay-ddm-form-renderer-context', 'liferay-ddm-form-renderer-evaluation', 'liferay-ddm-form-renderer-feedback', 'liferay-ddm-form-renderer-nested-fields', 'liferay-ddm-form-renderer-pagination', 'liferay-ddm-form-renderer-tabs', 'liferay-ddm-form-renderer-template', 'liferay-ddm-form-renderer-validation', 'liferay-ddm-soy-template-util']
	}
);