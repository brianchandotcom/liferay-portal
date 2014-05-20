AUI.add(
	'liferay-journal-content',
	function(A) {
		var Lang = A.Lang;

		var SELECTOR_AVAILABLE_TRANSLATIONS_LINKS = '#availableTranslationsLinks';

		var SELECTOR_TRANSLATIONS_MESSAGE = '#translationsMessage';

		var STR_CHANGE_DEFAULT_LANGUAGE = 'changeDefaultLanguage';

		var STR_CLICK = 'click';

		var STR_DDM = 'ddm';

		var STR_DEFAULT_LANGUAGE_SELECTOR = 'defaultLanguageSelector';

		var STR_HASH = '#';

		var STR_LANGUAGE_ID = 'languageId';

		var STR_SELECT_STRUCTURE = 'selectStructure';

		var STR_SELECT_TEMPLATE = 'selectTemplate';

		var STR_STRINGS = 'strings';

		var STR_URLS = 'urls';

		var TPL_CHANGE_DEFAULT_LOCALE_URL = '{baseURL}&{namespace}defaultLanguageId={languageId}';

		var TPL_EDIT_TRANSLATION_URL = '{baseURL}&{namespace}toLanguageId={languageId}';

		var TPL_LANGUAGE_INPUT = '<input name="{namespace}available_locales" type="hidden" value="{languageId}" />';

		var TPL_TRANSLATION = '<a class="journal-article-translation lfr-token" href="{uri}" id="{namespace}journal-article-translation-link-{languageId}">' +
			'<img alt="" src="{pathThemeImages}/language/{languageId}.png" />{language}' +
		'</a>';

		var WIN = A.config.win;

		var JournalContent = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'journalcontent',

				ATTRS: {
					changeDefaultLanguage: {
						setter: A.one
					},

					ddm: {
						validator: Lang.isObject,
						value: {}
					},

					defaultLanguage: {
						setter: A.one
					},

					defaultLanguageSelector: {
						setter: A.one
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
							changeDefaultLanguage: Liferay.Language.get('changing-the-default-language-will-delete-all-unsaved-content'),
							draft: Liferay.Language.get('draft'),
							editStructure: Liferay.Language.get('editing-the-current-structure-will-delete-all-unsaved-content'),
							editTemplate: Liferay.Language.get('editing-the-current-template-will-delete-all-unsaved-content'),
							selectStructure: Liferay.Language.get('selecting-a-new-structure-will-change-the-available-input-fields-and-available-templates'),
							selectTemplate: Liferay.Language.get('selecting-a-new-template-will-delete-all-unsaved-content'),
							structures: Liferay.Language.get('structures'),
							templates: Liferay.Language.get('templates'),
							webContentTranslation: Liferay.Language.get('web-content-translation')
						}
					},

					urls: {
						validator: Lang.isObject,
						value: {}
					}
				},

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						var eventHandles = [
							Liferay.on(instance.ns('postProcessTranslation'), instance._postProcessTranslation, instance)
						];

						var changeDefaultLanguage = instance.get(STR_CHANGE_DEFAULT_LANGUAGE);

						if (changeDefaultLanguage) {
							eventHandles.push(
								changeDefaultLanguage.on(STR_CLICK, instance._changeDefaultLanguage, instance)
							);
						}

						var defaultLanguageSelector = instance.get(STR_DEFAULT_LANGUAGE_SELECTOR);

						if (defaultLanguageSelector) {
							eventHandles.push(
								defaultLanguageSelector.on('change', instance._changeArticleDefaultLocale, instance)
							);
						}

						var editTemplate = instance.get('editTemplate');

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

						eventHandles.push(
							A.getBody().delegate(STR_CLICK, instance._openEditTranslationWindow, '.journal-article-translation', instance)
						);

						instance._eventHandles = eventHandles;
					},

					_changeArticleDefaultLocale: function() {
						var instance = this;

						var urls = instance.get(STR_URLS);

						var defaultLanguageId = instance.get(STR_DEFAULT_LANGUAGE_SELECTOR).get('value');

						WIN.location.href = Lang.sub(
							TPL_CHANGE_DEFAULT_LOCALE_URL,
							{
								baseURL: urls.updateDefaultLanguage,
								languageId: defaultLanguageId,
								namespace: instance.NS
							}
						);
					},

					_changeDefaultLanguage: function() {
						var instance = this;

						var strings = instance.get(STR_STRINGS);

						if (confirm(strings.changeDefaultLanguage)) {
							var defaultLanguage = instance.get('defaultLanguage');
							var defaultLanguageSelector = instance.get(STR_DEFAULT_LANGUAGE_SELECTOR);
							var changeDefaultLanguage = instance.get(STR_CHANGE_DEFAULT_LANGUAGE);

							defaultLanguageSelector.show();
							defaultLanguageSelector.focus();

							changeDefaultLanguage.hide();
							defaultLanguage.hide();
						}
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

									instance.one('#structureId').val(event.ddmstructurekey);

									instance.one('#templateId').val('');

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
					},

					_openEditTranslationWindow: function(event) {
						var instance = this;

						var strings = instance.get(STR_STRINGS);

						var currentTarget = event.currentTarget;

						Liferay.Util.openWindow(
							{
								destroyOnHide: true,
								id: instance.ns('journalArticleTranslationDialog'),
								title: strings.webContentTranslation,
								uri: currentTarget.attr('href')
							}
						);

						event.preventDefault();
					},

					_postProcessTranslation: function(event) {
						var instance = this;

						var newLanguageId = event.newLanguageId;

						var strings = instance.get(STR_STRINGS);

						var form = instance._getPrincipalForm();

						instance.one('#formDate', form).val(event.formDate);
						instance.one('#version', form).val(event.newVersion);

						var taglibWorkflowStatus = instance.one('.taglib-workflow-status');

						var statusNode = taglibWorkflowStatus.one('.workflow-status strong');

						statusNode.html(event.newStatusMessage);

						var versionNode = taglibWorkflowStatus.one('.workflow-version strong');

						versionNode.html(event.newVersion);

						var availableTranslationContainer = instance.one('#availableTranslationContainer');

						var translationLink = instance.one('#journal-article-translation-link-' + newLanguageId, availableTranslationContainer);

						if (event.cmd == 'delete_translation') {
							var availableLocales = instance.one('#availableLocales' + newLanguageId);

							if (availableLocales) {
								availableLocales.remove();
							}

							if (translationLink) {
								translationLink.remove();
							}

							A.byIdNS(instance.NS, 'journal-article-translation-link-' + newLanguageId).ancestor('li').show();

							var availableLocales = availableTranslationContainer.all('a.lfr-token');

							if (availableLocales.size() === 0) {
								availableTranslationContainer.removeClass('contains-translations');

								instance.one(SELECTOR_AVAILABLE_TRANSLATIONS_LINKS).hide();
								instance.one(SELECTOR_TRANSLATIONS_MESSAGE).hide();
							}
						}
						else if (!translationLink) {
							var availableTranslationsLinks = instance.one(SELECTOR_AVAILABLE_TRANSLATIONS_LINKS);

							var translationsMessage = instance.one(SELECTOR_TRANSLATIONS_MESSAGE);

							statusNode.replaceClass('workflow-status-approved', 'workflow-status-draft');
							statusNode.replaceClass('label-success', 'label-info');

							statusNode.html(strings.draft);

							availableTranslationContainer.addClass('contains-translations');

							availableTranslationsLinks.show();
							translationsMessage.show();

							var editTranslationURL = Lang.sub(
								TPL_EDIT_TRANSLATION_URL,
								{
									baseURL: instance.get(STR_URLS).editTranslation,
									languageId: newLanguageId,
									namespace: instance.NS
								}
							);

							translationLink = A.Node.create(
								Lang.sub(
									TPL_TRANSLATION,
									{
										language: event.newLanguage,
										languageId: newLanguageId,
										namespace: instance.NS,
										pathThemeImages: themeDisplay.getPathThemeImages(),
										uri: editTranslationURL
									}
								)
							);

							availableTranslationsLinks.append(translationLink);

							A.byIdNS(instance.NS, 'journal-article-translation-link-' + newLanguageId).ancestor('li').hide();

							var languageInput = A.Node.create(
								TPL_LANGUAGE_INPUT,
								{
									languageId: newLanguageId,
									namespace: instance.NS
								}
							);

							form.append(languageInput);
						}

						Liferay.fire(
							'closeWindow',
							{
								id: instance.ns('journalArticleTranslationDialog')
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