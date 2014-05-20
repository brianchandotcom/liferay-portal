AUI.add(
	'liferay-portlet-journal',
	function(A) {
		var Lang = A.Lang;

		var STR_ADD = 'add';

		var STR_ARTICLE_ID = 'articleId';

		var STR_CMD = 'cmd';

		var STR_UPDATE = 'update';

		var Journal = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'journal',

				ATTRS: {
					articleId: {
						validator: Lang.isString
					}
				},

				prototype: {
					displayTemplateMessage: function() {
						alert(Liferay.Language.get('please-add-a-template-to-render-this-structure'));
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

					hasStructure: function() {
						var instance = this;

						var form = instance.getPrincipalForm();

						var structureId = instance.getByName(form, 'structureId');

						return structureId && structureId.val();
					},

					hasTemplate: function() {
						var instance = this;

						var form = instance.getPrincipalForm();

						var templateId = instance.getByName(form, 'templateId');

						return templateId && templateId.val();
					},

					saveArticle: function(cmd) {
						var instance = this;

						var form = instance.getPrincipalForm();

						if (instance.hasStructure() && !instance.hasTemplate() && !instance.updateStructureDefaultValues()) {
							instance.displayTemplateMessage();
						}
						else {
							var articleId = instance.get(STR_ARTICLE_ID);

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
					},

					updateStructureDefaultValues: function() {
						var instance = this;

						var form = instance.getPrincipalForm();

						var classNameId = instance.getByName(form, 'classNameId');

						return (classNameId && classNameId.val() > 0);
					}
				}
			}
		);

		Liferay.Portlet.Journal = Journal;
	},
	'',
	{
		requires: ['aui-base', 'liferay-portlet-base']
	}
);