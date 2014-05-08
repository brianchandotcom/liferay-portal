AUI.add(
	'liferay-journal-content',
	function(A) {
		var Lang = A.Lang;

		var STR_AVAILABLE_TRANSLATIONS_LINKS = 'availableTranslationsLinks';

		var STR_CHANGE_DEFAULT_LANGUAGE = 'changeDefaultLanguage';

		var STR_CLICK = 'click';

		var STR_DDM = 'ddm';

		var STR_DEFAULT_LANGUAGE_SELECTOR = 'defaultLanguageSelector';

		var STR_HASH = '#';

		var STR_LANGUAGE_ID = 'languageId';

		var STR_TRANSLATIONS_MESSAGE = 'translationsMessage';

		var STR_SELECT_STRUCTURE = 'selectStructure';

		var STR_SELECT_TEMPLATE = 'selectTemplate';

		var STR_STRINGS = 'strings';

		var STR_URLS = 'urls';

		var TPL_CHANGE_DEFAULT_LOCALE_URL = '{baseURL}&{namespace}defaultLanguageId={languageId}';

		var TPL_EDIT_TRANSLATION_URL = '{baseURL}&{namespace}toLanguageId={languageId}';

		var TPL_LANGUAGE_INPUT = '<input name={namespace}available_locales" type="hidden" value="{languageId}" />';

		var TPL_TRANSLATION = '<a class="journal-article-translation lfr-token" href="{uri}" id="{namespace}journal-article-translation-{languageId}">' +
			'<img alt="" src="{pathThemeImages}/language/{languageId}.png" />{language}' +
			'</a>';

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
						value: {}
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

						Liferay.provide(
							window,
							instance.ns('postProcessTranslation'),
							A.bind('_postProcessTranslation', instance)
						);
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles = [];

						var changeDefaultLanguage = instance.get(STR_CHANGE_DEFAULT_LANGUAGE);

						if (changeDefaultLanguage) {
							instance._eventHandles.push(
								changeDefaultLanguage.on(STR_CLICK, instance._changeDefaultLanguage, instance)
							);
						}

						var defaultLanguageSelector = instance.get(STR_DEFAULT_LANGUAGE_SELECTOR);

						if (defaultLanguageSelector) {
							instance._eventHandles.push(
								defaultLanguageSelector.on('change', instance._changeArticleDefaultLocale, instance)
							);
						}

						var editTemplate = instance.get('editTemplate');

						if (editTemplate) {
							instance._eventHandles.push(
								editTemplate.on(STR_CLICK, instance._editTemplate, instance)
							);
						}

						var editStructure = instance.get('editStructure');

						if (editStructure) {
							instance._eventHandles.push(
								editStructure.on(STR_CLICK, instance._editStructure, instance)
							);
						}

						var selectTemplate = instance.get(STR_SELECT_TEMPLATE);

						if (selectTemplate) {
							instance._eventHandles.push(
								selectTemplate.on(STR_CLICK, instance._openDDMTemplateSelector, instance)
							);
						}

						var selectStructure = instance.get(STR_SELECT_STRUCTURE);

						if (selectStructure) {
							instance._eventHandles.push(
								selectStructure.on(STR_CLICK, instance._openDDMStructureSelector, instance)
							);
						}

						var availableTranslationContainer = A.one(STR_HASH + instance.ns('availableTranslationContainer'));

						if (availableTranslationContainer) {
							A.getBody().delegate(STR_CLICK, instance._openEditTranslationWindow, '.journal-article-translation', instance);
						}
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_changeArticleDefaultLocale: function() {
						var instance = this;

						var urls = instance.get(STR_URLS);

						var defaultLanguageId = instance.get(STR_DEFAULT_LANGUAGE_SELECTOR).get('value');

						window.location.href = Lang.sub(
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
									refreshWindow: window,
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

						return A.one('form[name=' + instance.ns(formName || 'fm1') + ']');
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

								var ddmStructureId = A.one(STR_HASH + instance.ns('ddmStructureId'));
								var structureId = A.one(STR_HASH + instance.ns('structureId'));
								var templateId = A.one(STR_HASH + instance.ns('templateId'));

								if (confirm(strings.selectStructure) && (ddmStructureId.val() != event.ddmstructureid)) {
									ddmStructureId.val(event.ddmstructureid);
									structureId.val(event.ddmstructurekey);
									templateId.val('');

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

									var ddmTemplateId = form.one(STR_HASH + instance.ns('ddmTemplateId'))

									ddmTemplateId.val(event.ddmtemplateid);

									submitForm(form, null, false, false);
								}
							}
						);
					},

					_openEditTranslationWindow: function(event) {
						var instance = this;

						var strings = instance.get(STR_STRINGS);

						var target = event.currentTarget;

						Liferay.Util.openWindow(
							{
								cache: false,
								id: target.attr('id'),
								title: strings.webContentTranslation,
								uri: target.attr('href')
							}
						);

						event.preventDefault();
					},

					_postProcessTranslation: function(formDate, cmd, newVersion, newLanguageId, newLanguage, newStatusMessage) {
						var instance = this;

						var strings = instance.get(STR_STRINGS);

						var form = instance._getPrincipalForm();

						form.one(STR_HASH + instance.ns('formDate')).val(formDate);
						form.one(STR_HASH + instance.ns('version')).val(newVersion);

						var taglibWorkflowStatus = A.one(STR_HASH + instance.ns('journalArticleWrapper') + ' .taglib-workflow-status');

						var statusNode = taglibWorkflowStatus.one('.workflow-status strong');

						statusNode.html(newStatusMessage);

						var versionNode = taglibWorkflowStatus.one('.workflow-version strong');

						versionNode.html(newVersion);

						var availableTranslationContainer = A.one(STR_HASH + instance.ns('availableTranslationContainer'));

						var translationLink = availableTranslationContainer.one(STR_HASH + instance.ns('journal-article-translation-' + newLanguageId));

						if (cmd == 'delete_translation') {
							var availableLocales = A.one(STR_HASH + instance.ns('availableLocales') + newLanguageId);

							if (availableLocales) {
								availableLocales.remove();
							}

							if (translationLink) {
								translationLink.remove();
							}

							A.one(STR_HASH + instance.ns(STR_LANGUAGE_ID) + newLanguageId).ancestor('li').show();

							var availableLocales = availableTranslationContainer.all('a.lfr-token');

							if (availableLocales.size() === 0) {
								availableTranslationContainer.removeClass('contains-translations');

								A.one(STR_HASH + instance.ns(STR_AVAILABLE_TRANSLATIONS_LINKS)).hide();
								A.one(STR_HASH + instance.ns(STR_TRANSLATIONS_MESSAGE)).hide();
							}
						}
						else if (!translationLink) {
							var availableTranslationsLinks = A.one(STR_HASH + instance.ns(STR_AVAILABLE_TRANSLATIONS_LINKS));
							var translationsMessage = A.one(STR_HASH + instance.ns(STR_TRANSLATIONS_MESSAGE));

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
										language: newLanguage,
										languageId: newLanguageId,
										namespace: instance.NS,
										pathThemeImages: themeDisplay.getPathThemeImages(),
										uri: editTranslationURL
									}
								)
							);

							availableTranslationsLinks.append(translationLink);

							A.one(STR_HASH + instance.ns(STR_LANGUAGE_ID) + newLanguageId).ancestor('li').hide();

							var languageInput = A.Node.create(
								TPL_LANGUAGE_INPUT,
								{
									languageId: newLanguageId,
									namespace: instance.NS
								}
							);

							form.append(languageInput);
						}
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