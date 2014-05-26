AUI.add(
	'liferay-portlet-journal',
	function(A) {
		var Lang = A.Lang;

		var STR_ADD = 'add';

		var STR_ARTICLE = 'article';

		var STR_ARTICLE_ID = 'articleId';

		var STR_CMD = 'cmd';

		var STR_STRINGS = 'strings';

		var STR_UPDATE = 'update';

		var TPL_DELETE_TRANSLATION_URL = '{baseURL}&{namespace}languageId={languageId}';

		var Journal = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'journal',

				ATTRS: {
					article: {
						validator: Lang.isObject,
						value: {}
					},

					focusFieldId: {
						validator: Lang.isString
					},

					strings: {
						validator: Lang.isObject,
						value: {
							addTemplate: Liferay.Language.get('please-add-a-template-to-render-this-structure'),
							deleteTranslationConfirmation: Liferay.Language.get('are-you-sure-you-want-to-deactivate-this-language'),
							permissions: Liferay.Language.get('permissions'),
							saveAsDraftBeforePreview: Liferay.Language.get('in-order-to-preview-your-changes,-the-web-content-will-be-saved-as-a-draft')
						}
					}
				},

				prototype: {
					initializer: function() {
						var instance = this;

						instance._createTooltip();

						instance._bindUI();

						instance._focusField();
					},

					_bindUI: function() {
						var instance = this;

						var eventHandles = [];

						var form = instance.getPrincipalForm();

						eventHandles.push(form.delegate('change', instance._onFormChanged, ':input', instance));
						eventHandles.push(form.on('submit', instance._onFormSubmit, instance));

						var basicPreviewButton = instance.one('#basicPreviewButton');

						if (basicPreviewButton) {
							eventHandles.push(basicPreviewButton.on('click', instance._previewArticle, instance));
						}

						var permissionsButton = instance.one('#articlePermissionsButton');

						if (permissionsButton) {
							eventHandles.push(permissionsButton.on('click', instance._viewArticlePermissions, instance));
						}

						var historyButton = instance.one('#articleHistoryButton');

						if (historyButton) {
							eventHandles.push(historyButton.on('click', instance._viewArticleHistory, instance));
						}

						eventHandles.push(historyButton.on('click', instance._viewArticleHistory, instance));

						var buttonRow = instance.one('.journal-article-button-row');

						eventHandles.push(buttonRow.delegate('click', instance._onButtonClick, 'button', instance));
					},

					_createTooltip: function() {
						var instance = this;

						new A.Tooltip(
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

					_focusField: function() {
						var instance = this;

						var focusFieldId = instance.get('focusFieldId');

						if (focusFieldId) {
							Liferay.Util.focusFormField(instance.one(focusFieldId));
						}
					},

					_hasStructure: function() {
						var instance = this;

						var form = instance.getPrincipalForm();

						var structureId = instance.getByName(form, 'structureId');

						return structureId && structureId.val();
					},

					_hasTemplate: function() {
						var instance = this;

						var form = instance.getPrincipalForm();

						var templateId = instance.getByName(form, 'templateId');

						return templateId && templateId.val();
					},

					_hasUnsavedChanges: function() {
						var instance = this;

						var form = instance.getPrincipalForm();

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

						var cmd = event.currentTarget.attr('data-cmd');

						if (cmd) {
							var form = instance.getPrincipalForm();

							if (cmd === 'delete_translation') {
								var strings = instance.get(STR_STRINGS);

								if (confirm(strings.deleteTranslationConfirmation)) {
									var article = instance.get(STR_ARTICLE);

									instance.one('#cmd', form).val(cmd);

									instance.one('#redirect', form).val(
										Lang.sub(
											TPL_DELETE_TRANSLATION_URL,
											{
												baseURL: article.editUrl,
												languageId: article.defaultLanguageId,
												namespace: instance.NS
											}
										)
									);

									submitForm(form);
								}
							}
							else {
								instance.one('#cmd', form).val(cmd);
							}
						}
					},

					_onFormChanged: function(event) {
						var instance = this;

						instance._formChanged = true;
					},

					_onFormSubmit: function(event) {
						var instance = this;

						event.preventDefault();

						var form = instance.getPrincipalForm();

						var cmd = instance.one('#cmd', form).val();

						if (cmd === 'translate') {
							instance.translateArticle();
						}
						else {
							instance.saveArticle(cmd);
						}
					},

					_previewArticle: function(event) {
						var instance = this;

						var strings = instance.get(STR_STRINGS);

						event.preventDefault();

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
								var form = instance.getPrincipalForm();

								instance.one('#cmd', form).val('preview');

								submitForm(form);
							}
						}
					},

					_updateStructureDefaultValues: function() {
						var instance = this;

						var form = instance.getPrincipalForm();

						var classNameId = instance.getByName(form, 'classNameId');

						return (classNameId && classNameId.val() > 0);
					},

					_viewArticleHistory: function(event) {
						var instance = this;

						event.preventDefault();

						var article = instance.get(STR_ARTICLE);

						window.location = article.viewHistoryUrl;
					},

					_viewArticlePermissions: function(event) {
						var instance = this;

						var strings = instance.get(STR_STRINGS);

						var article = instance.get(STR_ARTICLE);

						Liferay.Util.openWindow(
							{
								dialog: {
									cssClass: 'portlet-asset-categories-admin-dialog permissions-change',
									destroyOnHide: true
								},
								id: instance.ns('articlePermissions'),
								title: strings.permissions,
								uri: article.permissionsUrl
							}
						);

						event.preventDefault();
					},

					getByName: function(currentForm, name, withoutNamespace) {
						var instance = this;

						var inputName = withoutNamespace ? name : instance.NS + name;

						return A.one(currentForm).one('[name=' + inputName + ']');
					},

					getPrincipalForm: function(formName) {
						var instance = this;

						return A.one('form[name=' + instance.NS + (formName || 'fm1') + ']');
					},

					saveArticle: function(cmd) {
						var instance = this;

						var form = instance.getPrincipalForm();

						if (instance._hasStructure() && !instance._hasTemplate() && !instance._updateStructureDefaultValues()) {
							instance._displayTemplateMessage();
						}
						else {
							var article = instance.get(STR_ARTICLE);

							var articleId = article.id;

							if (!cmd) {
								cmd = articleId ? STR_UPDATE : STR_ADD;
							}

							var articleIdInput = instance.getByName(form, STR_ARTICLE_ID);
							var cmdInput = instance.getByName(form, STR_CMD);
							var newArticleIdInput = instance.getByName(form, 'newArticleId');
							var workflowActionInput = instance.getByName(form, 'workflowAction');

							if (cmd == 'publish') {
								workflowActionInput.val(Liferay.Workflow.ACTION_PUBLISH);

								cmd = articleId ? STR_UPDATE : STR_ADD;
							}

							cmdInput.val(cmd);

							if (!articleId) {
								articleIdInput.val(newArticleIdInput.val());
							}

							submitForm(form);
						}
					},

					translateArticle: function() {
						var instance = this;

						var form = instance.getPrincipalForm();

						var cmdInput = instance.getByName(form, STR_CMD);

						cmdInput.val('translate');

						submitForm(form);
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