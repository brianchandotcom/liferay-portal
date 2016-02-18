AUI.add(
	'liferay-portlet-journal',
	function(A) {
		var Lang = A.Lang;
		var Util = Liferay.Util;

		var SELECTOR_ACTION_NAME = '#javax-portlet-action';

		var STR_ACTION_NAME = 'javax.portlet.action';

		var STR_ADD_ARTICLE = 'addArticle';

		var STR_ARTICLE = 'article';

		var STR_ARTICLE_ID = 'articleId';

		var STR_CLICK = 'click';

		var STR_STRINGS = 'strings';

		var STR_UPDATE_ARTICLE = 'updateArticle';

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
							addTemplate: Liferay.Language.get('please-add-a-template-to-render-this-structure'),
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

						instance._createTooltip();

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						instance._tooltip.destroy();

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						var form = instance._getPrincipalForm();

						var eventHandles = [
							form.delegate('change', instance._onFormChanged, ':input', instance),
							form.on('submit', instance._onFormSubmit, instance)
						];

						var basicPreviewButton = instance.one('#basicPreviewButton');

						if (basicPreviewButton) {
							eventHandles.push(basicPreviewButton.on(STR_CLICK, instance._previewArticle, instance));

							Util.toggleDisabled(basicPreviewButton, false);
						}

						var buttonRow = instance.one('.journal-article-button-row');

						if (buttonRow) {
							eventHandles.push(buttonRow.delegate(STR_CLICK, instance._onButtonClick, 'button', instance));
						}

						instance._eventHandles = eventHandles;
					},

					_createTooltip: function() {
						var instance = this;

						instance._tooltip = new A.Tooltip(
							{
								trigger: instance.one('#basicPreviewButton'),
								visible: false,
								zIndex: Liferay.zIndex.TOOLTIP
							}
						).render();
					},

					_displayTemplateMessage: function() {
						var instance = this;

						var strings = instance.get(STR_STRINGS);

						alert(strings.addTemplate);
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

					_hasStructure: function() {
						var instance = this;

						var form = instance._getPrincipalForm();

						var ddmStructureKey = instance._getByName(form, 'ddmStructureKey');

						return ddmStructureKey && ddmStructureKey.val();
					},

					_hasTemplate: function() {
						var instance = this;

						var form = instance._getPrincipalForm();

						var ddmTemplateKey = instance._getByName(form, 'ddmTemplateKey');

						return ddmTemplateKey && ddmTemplateKey.val();
					},

					_hasUnsavedChanges: function() {
						var instance = this;

						var form = instance._getPrincipalForm();

						var unsavedChanges = instance._formChanged;

						if (!unsavedChanges && typeof CKEDITOR !== 'undefined') {
							A.Object.some(
								CKEDITOR.instances,
								function(item, index) {
									var parentForm = A.one('#' + item.element.getId()).ancestor('form');

									if (parentForm.compareTo(form)) {
										unsavedChanges = item.checkDirty();

										return unsavedChanges;
									}
								}
							);
						}

						return unsavedChanges;
					},

					_onButtonClick: function(event) {
						var instance = this;

						var actionName = event.currentTarget.attr('data-actionname');

						if (actionName) {
							var form = instance._getPrincipalForm();

							instance.one(SELECTOR_ACTION_NAME, form).val(actionName);
						}
					},

					_onFormChanged: function(event) {
						var instance = this;

						instance._formChanged = true;
					},

					_onFormSubmit: function(event) {
						var instance = this;

						event.preventDefault();

						var form = instance._getPrincipalForm();

						var actionName = instance.one(SELECTOR_ACTION_NAME, form).val();

						instance._saveArticle(actionName);
					},

					_previewArticle: function(event) {
						var instance = this;

						event.preventDefault();

						var strings = instance.get(STR_STRINGS);

						if (!instance._hasUnsavedChanges()) {
							var article = instance.get(STR_ARTICLE);

							Liferay.fire(
								'previewArticle',
								{
									title: article.title,
									uri: article.previewUrl
								}
							);
						}
						else if (confirm(strings.saveAsDraftBeforePreview)) {
							var hasStructure = instance._hasStructure();

							var hasTemplate = instance._hasTemplate();

							var updateStructureDefaultValues = instance._updateStructureDefaultValues();

							if (hasStructure && !hasTemplate && !updateStructureDefaultValues) {
								instance._displayTemplateMessage();
							}
							else {
								var form = instance._getPrincipalForm();

								instance.one(SELECTOR_ACTION_NAME, form).val('previewArticle');

								submitForm(form);
							}
						}
					},

					_saveArticle: function(actionName) {
						var instance = this;

						var form = instance._getPrincipalForm();

						if (instance._hasStructure() && !instance._hasTemplate() && !instance._updateStructureDefaultValues()) {
							instance.one(SELECTOR_ACTION_NAME, form).val('');

							instance._displayTemplateMessage();
						}
						else {
							var article = instance.get(STR_ARTICLE);

							var articleId = article.id;

							if (actionName === 'publish') {
								var workflowActionInput = instance._getByName(form, 'workflowAction');

								workflowActionInput.val(Liferay.Workflow.ACTION_PUBLISH);

								actionName = null;
							}

							if (!actionName) {
								actionName = articleId ? STR_UPDATE_ARTICLE : STR_ADD_ARTICLE;
							}

							var actionNameInput = instance._getByName(form, STR_ACTION_NAME);

							actionNameInput.val(actionName);

							if (!articleId) {
								var articleIdInput = instance._getByName(form, STR_ARTICLE_ID);
								var newArticleIdInput = instance._getByName(form, 'newArticleId');

								articleIdInput.val(newArticleIdInput.val());
							}

							submitForm(form);
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
		requires: ['aui-base', 'aui-dialog-iframe-deprecated', 'aui-tooltip', 'liferay-portlet-base', 'liferay-util-window']
	}
);