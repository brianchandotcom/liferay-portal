AUI.add(
	'liferay-portlet-journal',
	function(A) {
		var Journal = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'journal',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance.articleId = config.articleId;
						instance.portletNamespace = config.namespace;
					},

					displayTemplateMessage: function() {
						var templateMessage = Liferay.Language.get('please-add-a-template-to-render-this-structure');

						alert(templateMessage);
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

					updateStructureDefaultValues: function() {
						var instance = this;

						var form = instance.getPrincipalForm();

						var classNameId = instance.getByName(form, 'classNameId');

						return (classNameId && classNameId.val() > 0);
					},

					getByName: function(currentForm, name, withoutNamespace) {
						var instance = this;

						var inputName = withoutNamespace ? name : instance.portletNamespace + name;

						return A.one(currentForm).one('[name=' + inputName + ']');
					},

					getPrincipalForm: function(formName) {
						var instance = this;

						return A.one('form[name=' + instance.portletNamespace + (formName || 'fm1') + ']');
					},

					saveArticle: function(cmd) {
						var instance = this;

						var form = instance.getPrincipalForm();

						if (instance.hasStructure() && !instance.hasTemplate() && !instance.updateStructureDefaultValues()) {
							instance.displayTemplateMessage();
						}
						else {
							if (!cmd) {
								cmd = instance.articleId ? 'update' : 'add';
							}

							var articleIdInput = instance.getByName(form, 'articleId');
							var cmdInput = instance.getByName(form, 'cmd');
							var newArticleIdInput = instance.getByName(form, 'newArticleId');
							var workflowActionInput = instance.getByName(form, 'workflowAction');

							if (cmd == 'publish') {
								workflowActionInput.val(Liferay.Workflow.ACTION_PUBLISH);

								cmd = instance.articleId ? 'update' : 'add';
							}

							cmdInput.val(cmd);

							if (!instance.articleId) {
								articleIdInput.val(newArticleIdInput.val());
							}

							submitForm(form);
						}
					},

					translateArticle: function() {
						var instance = this;

						var form = instance.getPrincipalForm();

						var cmdInput = instance.getByName(form, 'cmd');

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
		requires: ['aui-base', 'liferay-portlet-base']
	}
);