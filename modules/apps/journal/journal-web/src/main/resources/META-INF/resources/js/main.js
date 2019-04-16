AUI.add(
	'liferay-portlet-journal',
	function(A) {
		var Lang = A.Lang;

		var SELECTOR_ACTION_NAME = '#javax-portlet-action';

		var Journal = A.Component.create(
			{
				ATTRS: {
					article: {
						validator: Lang.isObject,
						value: {}
					},

					strings: {
						validator: Lang.isObject,
						value: {
							saveAsDraftBeforePreview: Liferay.Language.get('in-order-to-preview-your-changes,-the-web-content-is-saved-as-a-draft')
						}
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'journal',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						var form = instance._getPrincipalForm();

						var eventHandles = [
							form.on('submit', instance._onFormSubmit, instance)
						];

						var buttonRow = instance.one('.journal-article-button-row');

						if (buttonRow) {
							eventHandles.push(buttonRow.delegate('click', instance._onButtonClick, 'button', instance));
						}

						eventHandles.push(Liferay.on('inputLocalized:localeChanged', instance._onLocaleChange.bind(instance)));

						instance._eventHandles = eventHandles;
					},

					_getByName: function(currentForm, name, withoutNamespace) {
						var instance = this;

						if (!withoutNamespace) {
							name = instance.ns(name);
						}

						return instance.one('[name=' + name + ']', currentForm);
					},

					_getPrincipalForm: function(formName) {
						var instance = this;

						return instance.one('form[name=' + instance.ns(formName || 'fm1') + ']');
					},

					_onButtonClick: function(event) {
						var instance = this;

						var actionName = event.currentTarget.attr('data-actionname');

						if (actionName) {
							var form = instance._getPrincipalForm();

							instance.one(SELECTOR_ACTION_NAME, form).val(actionName);
						}
					},

					_onFormSubmit: function(event) {
						var instance = this;

						event.preventDefault();

						var form = instance._getPrincipalForm();

						var actionName = instance.one(SELECTOR_ACTION_NAME, form).val();

						instance._saveArticle(actionName);
					},

					_onLocaleChange: function(event) {
						var defaultLanguageId = themeDisplay.getDefaultLanguageId();
						var instance = this;
						var selectedLanguageId = event.source && event.source.getSelectedLanguageId();

						if (selectedLanguageId) {
							instance._updateLocalizableInput('descriptionMapAsXML', defaultLanguageId, selectedLanguageId);

							instance._updateLocalizableInput('titleMapAsXML', defaultLanguageId, selectedLanguageId);
						}
					},

					_saveArticle: function(actionName) {
						var instance = this;

						var form = instance._getPrincipalForm();

						var article = instance.get('article');

						var articleId = article.id;

						if (actionName === 'publish') {
							var workflowActionInput = instance._getByName(form, 'workflowAction');

							workflowActionInput.val(Liferay.Workflow.ACTION_PUBLISH);

							actionName = null;
						}

						if (!actionName) {
							actionName = articleId ? 'updateArticle' : 'addArticle';
						}

						var actionNameInput = instance._getByName(form, 'javax.portlet.action');

						actionNameInput.val(actionName);

						if (!articleId) {
							var articleIdInput = instance._getByName(form, 'articleId');
							var newArticleIdInput = instance._getByName(form, 'newArticleId');

							articleIdInput.val(newArticleIdInput.val());
						}

						submitForm(form);
					},

					_updateLocalizableInput: function(componentId, defaultLanguageId, selectedLanguageId) {
						var instance = this;

						var inputComponent = Liferay.component(instance.ns(componentId));

						if (inputComponent) {
							var inputSelectedValue = inputComponent.getValue(selectedLanguageId);

							if (inputSelectedValue === '') {
								var inputDefaultValue = inputComponent.getValue(defaultLanguageId);

								inputComponent.updateInputLanguage(inputDefaultValue);

								inputComponent.selectFlag(selectedLanguageId);
							}
						}
					},

					_updateStructureDefaultValues: function() {
						var instance = this;

						var form = instance._getPrincipalForm();

						var classNameId = instance._getByName(form, 'classNameId');

						return (classNameId && classNameId.val() > 0);
					}
				}
			}
		);

		Liferay.Portlet.Journal = Journal;
	},
	'',
	{
		requires: ['aui-base', 'aui-dialog-iframe-deprecated', 'liferay-portlet-base', 'liferay-util-window']
	}
);