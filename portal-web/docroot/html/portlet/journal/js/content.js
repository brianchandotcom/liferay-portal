AUI.add(
	'liferay-journal-content',
	function(A) {
		var AArray = A.Array;

		var Lang = A.Lang;

		var STR_CLICK = 'click';

		var STR_DDM = 'ddm';

		var STR_SELECT_STRUCTURE = 'selectStructure';

		var STR_SELECT_TEMPLATE = 'selectTemplate';

		var STR_STRINGS = 'strings';

		var STR_TRANSLATION_MANAGER = 'translationManager';

		var STR_DESCRIPTION_INPUT_LOCALIZED = 'descriptionInputLocalized';

		var STR_TITLE_INPUT_LOCALIZED = 'titleInputLocalized';

		var STR_URLS = 'urls';

		var WIN = A.config.win;

		var JournalContent = A.Component.create(
			{
				ATTRS: {
					ddm: {
						validator: Lang.isObject,
						value: {}
					},

					descriptionInputLocalized: {
					},

					editStructure: {
						setter: A.one
					},

					editTemplate: {
						setter: A.one
					},

					selectStructure: {
						setter: A.one
					},

					selectTemplate: {
						setter: A.one
					},

					strings: {
						validator: Lang.isObject,
						value: {
							draft: Liferay.Language.get('draft'),
							editStructure: Liferay.Language.get('editing-the-current-structure-will-delete-all-unsaved-content'),
							editTemplate: Liferay.Language.get('editing-the-current-template-will-delete-all-unsaved-content'),
							selectStructure: Liferay.Language.get('selecting-a-new-structure-will-change-the-available-input-fields-and-available-templates'),
							selectTemplate: Liferay.Language.get('selecting-a-new-template-will-delete-all-unsaved-content'),
							structures: Liferay.Language.get('structures'),
							templates: Liferay.Language.get('templates')
						}
					},

					titleInputLocalized: {
					},

					translationManager: {
					},

					urls: {
						validator: Lang.isObject,
						value: {}
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'journalcontent',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._bindUI();
						instance._renderUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						var editTemplate = instance.get('editTemplate');

						var eventHandles = [];

						if (editTemplate) {
							eventHandles.push(
								editTemplate.on(STR_CLICK, instance._editTemplate, instance)
							);
						}

						var editStructure = instance.get('editStructure');

						if (editStructure) {
							eventHandles.push(
								editStructure.on(STR_CLICK, instance._editStructure, instance)
							);
						}

						var selectTemplate = instance.get(STR_SELECT_TEMPLATE);

						if (selectTemplate) {
							eventHandles.push(
								selectTemplate.on(STR_CLICK, instance._openDDMTemplateSelector, instance)
							);
						}

						var selectStructure = instance.get(STR_SELECT_STRUCTURE);

						if (selectStructure) {
							eventHandles.push(
								selectStructure.on(STR_CLICK, instance._openDDMStructureSelector, instance)
							);
						}

						var translationManager = instance.get(STR_TRANSLATION_MANAGER);

						if (translationManager) {
							eventHandles.push(
								translationManager.after('editingLocaleChange', instance._afterEditingLocaleChange, instance)
							);
						}

						instance._eventHandles = eventHandles;
					},

					_renderUI: function() {
						var instance = this;

						instance.get(STR_DESCRIPTION_INPUT_LOCALIZED).render();
						instance.get(STR_TITLE_INPUT_LOCALIZED).render();
					},

					_afterEditingLocaleChange: function(event) {
						var instance = this;

						var descriptionInputLocalized = instance.get(STR_DESCRIPTION_INPUT_LOCALIZED);

						var titleInputLocalized = instance.get(STR_TITLE_INPUT_LOCALIZED);

						var items = descriptionInputLocalized.get('items');

						var editingLocale = event.newVal;

						var selectedIndex = AArray.indexOf(items, editingLocale);

						descriptionInputLocalized.set('selected', selectedIndex);
						descriptionInputLocalized.selectFlag(editingLocale);

						titleInputLocalized.set('selected', selectedIndex);
						titleInputLocalized.selectFlag(editingLocale);
					},

					_editStructure: function(event) {
						var instance = this;

						var strings = instance.get(STR_STRINGS);

						if (confirm(strings.editStructure)) {
							var urls = instance.get(STR_URLS);

							Liferay.Util.openWindow(
								{
									id: A.guid(),
									refreshWindow: WIN,
									title: strings.structures,
									uri: urls.editStructure
								}
							);
						}
					},

					_editTemplate: function(event) {
						var instance = this;

						var strings = instance.get(STR_STRINGS);

						if (confirm(strings.editTemplate)) {
							var urls = instance.get(STR_URLS);

							Liferay.Util.openWindow(
								{
									id: A.guid(),
									title: strings.templates,
									uri: urls.editTemplate
								}
							);
						}
					},

					_getPrincipalForm: function(formName) {
						var instance = this;

						return instance.one('form[name=' + instance.ns(formName || 'fm1') + ']');
					},

					_openDDMStructureSelector: function() {
						var instance = this;

						var ddm = instance.get(STR_DDM);

						var strings = instance.get(STR_STRINGS);

						Liferay.Util.openDDMPortlet(
							{
								basePortletURL: ddm.basePortletURL,
								classPK: ddm.classPK,
								dialog: {
									destroyOnHide: true
								},
								eventName: instance.ns(STR_SELECT_STRUCTURE),
								groupId: ddm.groupId,
								refererPortletName: ddm.refererPortletName,
								showAncestorScopes: true,
								struts_action: '/dynamic_data_mapping/select_structure',
								title: strings.structures
							},
							function(event) {
								var form = instance._getPrincipalForm();

								var ddmStructureId = instance.one('#ddmStructureId');

								if (confirm(strings.selectStructure) && (ddmStructureId.val() != event.ddmstructureid)) {
									ddmStructureId.val(event.ddmstructureid);

									instance.one('#ddmStructureKey').val(event.ddmstructurekey);

									instance.one('#ddmTemplateKey').val('');

									submitForm(form, null, false, false);
								}
							}
						);
					},

					_openDDMTemplateSelector: function() {
						var instance = this;

						var ddm = instance.get(STR_DDM);

						var strings = instance.get(STR_STRINGS);

						Liferay.Util.openDDMPortlet(
							{
								basePortletURL: ddm.basePortletURL,
								classNameId: ddm.classNameId,
								classPK: ddm.classPK,
								dialog: {
									destroyOnHide: true
								},
								eventName: instance.ns(STR_SELECT_TEMPLATE),
								groupId: ddm.groupId,
								refererPortletName: ddm.refererPortletName,
								showAncestorScopes: true,
								struts_action: '/dynamic_data_mapping/select_template',
								templateId: ddm.templateId,
								title: strings.templates
							},
							function(event) {
								if (confirm(strings.selectTemplate)) {
									var form = instance._getPrincipalForm();

									var ddmTemplateId = instance.one('#ddmTemplateId');

									ddmTemplateId.val(event.ddmtemplateid);

									submitForm(form, null, false, false);
								}
							}
						);
					}
				}
			}
		);

		Liferay.Portlet.JournalContent = JournalContent;
	},
	'',
	{
		requires: ['aui-base', 'liferay-portlet-base']
	}
);