/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

AUI.add(
	'liferay-input-localized',
	(A) => {
		var Lang = A.Lang;

		var STR_BLANK = '';

		var STR_INPUT_PLACEHOLDER = 'inputPlaceholder';

		var STR_ITEMS = 'items';

		var STR_ITEMS_ERROR = 'itemsError';

		var STR_SELECTED = 'selected';

		var STR_SUBMIT = '_onSubmit';

		var availableLanguages = Liferay.Language.available;

		var defaultLanguageId = themeDisplay.getDefaultLanguageId();

		var userLanguageId = themeDisplay.getLanguageId();

		var availableLanguageIds = A.Array.dedupe(
			[defaultLanguageId, userLanguageId].concat(
				Object.keys(availableLanguages)
			)
		);

		var InputLocalized = A.Component.create({
			_instances: {},

			ATTRS: {
				activeLanguageIds: {
					validator: Lang.isArray,
				},

				adminMode: {
					validator: Lang.isBoolean,
					value: false,
				},

				animateClass: {
					validator: Lang.isString,
					value: 'highlight-animation',
				},

				availableLocales: {
					validator: Lang.isObject,
					value: [],
				},

				defaultLanguageId: {
					value: defaultLanguageId,
				},

				editor: {},

				fieldPrefix: {
					validator: Lang.isString,
				},

				fieldPrefixSeparator: {
					validator: Lang.isString,
				},

				frontendJsComponentsWebModule: {
					validator: Lang.isObject,
				},

				frontendJsReactWebModule: {
					validator: Lang.isObject,
				},

				frontendJsStateWebModule: {
					validator: Lang.isObject,
				},

				helpMessage: {
					validator: Lang.isString,
				},

				id: {
					validator: Lang.isString,
				},

				inputBox: {
					setter: A.one,
				},

				inputPlaceholder: {
					setter: A.one,
				},

				instanceId: {
					value: Lang.isString,
				},

				items: {
					value: availableLanguageIds,
				},

				itemsError: {
					validator: Array.isArray,
				},

				name: {
					validator: Lang.isString,
				},

				namespace: {
					validator: Lang.isString,
				},

				selected: {
					valueFn() {
						var instance = this;

						var itemsError = instance.get(STR_ITEMS_ERROR);

						var itemIndex =
							instance.get('selectedLanguageId') ||
							instance.get('defaultLanguageId');

						if (itemsError.length) {
							itemIndex = itemsError[0];
						}

						return instance.get(STR_ITEMS).indexOf(itemIndex);
					},
				},

				selectedLanguageId: {
					validator: Lang.isString,
				},

				translatedLanguages: {
					setter(val) {
						var set = new A.Set();

						if (Lang.isString(val)) {
							val.split(',').forEach(set.add, set);
						}

						return set;
					},
					value: null,
				},
			},

			EXTENDS: A.Palette,

			NAME: 'input-localized',

			prototype: {
				_State: null,

				_activeLanguageIdsAtom: null,

				_animate(input, shouldFocus) {
					var instance = this;

					var animateClass = instance.get('animateClass');

					if (animateClass) {
						input.removeClass(animateClass);

						clearTimeout(instance._animating);

						setTimeout(() => {
							input.addClass(animateClass);

							if (shouldFocus) {
								input.focus();
							}
						}, 0);

						instance._animating = setTimeout(() => {
							input.removeClass(animateClass);

							clearTimeout(instance._animating);
						}, 700);
					}
				},

				_animating: null,

				_availableLanguagesSubscription: null,

				_bindManageTranslationsButton() {
					var instance = this;

					var boundingBox = instance.get('boundingBox');

					if (instance.get('adminMode')) {
						var manageTranslationsButton = boundingBox.one(
							'#manage-translations'
						);

						instance._eventHandles.push(
							manageTranslationsButton.on(
								'click',
								A.bind('_onManageTranslationsClick', instance)
							)
						);
					}
				},

				_clearFormValidator(input) {
					var form = input.get('form');

					var liferayForm = Liferay.Form.get(form.attr('id'));

					if (liferayForm) {
						var validator = liferayForm.formValidator;

						if (A.instanceOf(validator, A.FormValidator)) {
							validator.resetAllFields();
						}
					}
				},

				_flags: null,

				_flagsInitialContent: null,

				_getInputLanguage(languageId) {
					var instance = this;

					var fieldPrefix = instance.get('fieldPrefix');
					var fieldPrefixSeparator = instance.get(
						'fieldPrefixSeparator'
					);
					var id = instance.get('id');
					var inputBox = instance.get('inputBox');
					var name = instance.get('name');
					var namespace = instance.get('namespace');

					var fieldNamePrefix = STR_BLANK;
					var fieldNameSuffix = STR_BLANK;

					if (fieldPrefix) {
						fieldNamePrefix = fieldPrefix + fieldPrefixSeparator;
						fieldNameSuffix = fieldPrefixSeparator;
					}

					var inputLanguage = inputBox.one(
						instance._getInputLanguageId(languageId)
					);

					if (!inputLanguage) {
						inputLanguage = A.Node.create(
							A.Lang.sub(instance.INPUT_HIDDEN_TEMPLATE, {
								fieldNamePrefix,
								fieldNameSuffix,
								id,
								name: A.Lang.String.escapeHTML(name),
								namespace,
								value: languageId,
							})
						);

						inputBox.append(inputLanguage);
					}

					return inputLanguage;
				},

				_getInputLanguageId(languageId) {
					var instance = this;

					var id = instance.get('id');
					var namespace = instance.get('namespace');

					return '#' + namespace + id + '_' + languageId;
				},

				_moveDefaultLanguageFlagToFirstPosition(defaultLanguageId) {
					var instance = this;

					var flags = instance._flags.getDOMNode();

					var languageNode = flags.querySelector(
						'[data-languageid="' + defaultLanguageId + '"]'
					)?.parentElement;

					if (languageNode) {
						flags.removeChild(languageNode);

						flags.insertBefore(
							languageNode,
							flags.firstElementChild
						);
					}
				},

				_onActiveLanguageIdsChange(activeLanguageIds) {
					var instance = this;

					instance.set('activeLanguageIds', activeLanguageIds);

					instance._renderActiveLanguageIds();
				},

				_onDefaultLocaleChanged(event) {
					var instance = this;

					var prevDefaultLanguageId = instance.get(
						'defaultLanguageId'
					);
					var prevDefaultValue = instance.getValue(
						prevDefaultLanguageId
					);

					if (!prevDefaultValue) {
						instance.removeInputLanguage(prevDefaultLanguageId);
						instance.updateInputLanguage(
							prevDefaultValue,
							prevDefaultLanguageId
						);
					}

					var defaultLanguageId = event.item.getAttribute(
						'data-value'
					);

					instance.set('defaultLanguageId', defaultLanguageId);

					instance._updateTranslationStatus(defaultLanguageId);
					instance._updateTranslationStatus(prevDefaultLanguageId);

					instance._moveDefaultLanguageFlagToFirstPosition(
						defaultLanguageId
					);

					Liferay.fire('inputLocalized:localeChanged', {
						item: event.item,
						source: instance,
					});
				},

				_onInputValueChange(event, input) {
					var instance = this;

					var editor = instance.get('editor');

					var value;

					if (editor) {
						value = editor.getHTML();
					}
					else {
						input = input || event.currentTarget;

						value = input.val();
					}

					instance.updateInputLanguage(value);
				},

				_onLocaleChanged(event) {
					var instance = this;

					var languageId = event.item.getAttribute('data-value');

					instance.selectFlag(languageId, event.source === instance);
				},

				_onManageTranslationsClick() {
					var instance = this;

					var modalContainerId =
						instance.get('namespace') + 'modal-container';

					if (!document.getElementById(modalContainerId)) {
						var modalContainer = document.createElement('div');
						document.body.appendChild(modalContainer);
					}

					var availableLocales = instance.get('availableLocales');

					var locales = instance.get('items').map((languageId) => {
						var displayName = availableLocales[languageId];

						var label = languageId.replace(/_/, '-');

						return {
							displayName,
							id: languageId,
							label,
							symbol: label.toLowerCase(),
						};
					});

					var translations = instance
						.get('translatedLanguages')
						.values()
						.reduce((accumulator, item) => {
							var language = item.replace(/_/, '-');

							if (!accumulator[language]) {
								accumulator[language] = language;
							}

							return accumulator;
						}, {});

					var props = {
						activeLanguageIds: instance.get('activeLanguageIds'),
						availableLocales: locales,
						defaultLanguageId: instance.get('defaultLanguageId'),
						onClose(newActiveLanguageIds) {
							instance._State.writeAtom(
								instance._activeLanguageIdsAtom,
								newActiveLanguageIds
							);
						},
						translations,
						visible: true,
					};

					instance
						.get('frontendJsReactWebModule')
						.render(
							instance.get('frontendJsComponentsWebModule')
								.TranslationAdminModal,
							props,
							modalContainer
						);
				},

				_onSelectFlag(event) {
					var instance = this;

					var languageId = event.item.getAttribute('data-value');

					instance._State.writeAtom(
						instance._selectedLanguageIdAtom,
						languageId
					);

					if (!event.domEvent) {
						Liferay.fire('inputLocalized:localeChanged', {
							item: event.item,
							source: instance,
						});
					}
				},

				_onSelectedLanguageIdChange(languageId) {
					var instance = this;

					instance.selectFlag(languageId);
				},

				_onSubmit(event, input) {
					var instance = this;

					if (event.form === input.get('form')) {
						instance._onInputValueChange.apply(instance, arguments);

						InputLocalized.unregister(input.attr('id'));
					}
				},

				_renderActiveLanguageIds() {
					var instance = this;

					var activeLanguageIds = instance.get('activeLanguageIds');

					var defaultLanguageId = instance.get('defaultLanguageId');

					var translatedLanguages = instance.get(
						'translatedLanguages'
					);

					var selectedLanguageId = instance.getSelectedLanguageId();

					var currentFlagsNode = instance._flags.getDOMNode();

					var newFlagsNode = instance._flagsInitialContent
						.cloneNode(true)
						.getDOMNode();

					var changeToDefault;

					Object.entries(instance.get('availableLocales')).forEach(
						(entry) => {
							var key = entry[0];

							var localeNode = newFlagsNode.querySelector(
								'[data-languageid="' + key + '"]'
							)?.parentElement;

							if (!activeLanguageIds.includes(key)) {
								if (localeNode) {
									newFlagsNode.removeChild(localeNode);
								}

								instance.removeInputLanguage(key);
								translatedLanguages['remove'](key);

								if (key === selectedLanguageId) {
									changeToDefault = true;
								}
							}
							else {
								var currentlocaleNode = currentFlagsNode.querySelector(
									'[data-languageid="' + key + '"]'
								)?.parentElement;

								if (currentlocaleNode) {
									localeNode.innerHTML =
										currentlocaleNode.innerHTML;
								}
							}
						}
					);

					currentFlagsNode.innerHTML = newFlagsNode.innerHTML;

					Object.entries(instance.get('availableLocales')).forEach(
						([key]) => {
							this._updateTranslationStatus(key);
						}
					);

					var boundingBox = instance.get('boundingBox');
					instance._flags = boundingBox.one('.palette-container');

					if (changeToDefault) {
						instance._onSelectFlag({
							item: currentFlagsNode.querySelector(
								'[data-languageid="' + defaultLanguageId + '"]'
							),
						});
					}

					if (instance.get('adminMode')) {
						instance._bindManageTranslationsButton();
					}
				},

				_selectedLanguageIdAtom: null,

				_selectedLanguageIdSubscription: null,

				_updateHelpMessage(languageId) {
					var instance = this;

					var helpMessage = instance.get('helpMessage');

					if (!instance.get('editor')) {
						var defaultLanguageId = instance.get(
							'defaultLanguageId'
						);

						if (languageId !== defaultLanguageId) {
							helpMessage = instance.getValue(defaultLanguageId);
						}

						helpMessage = Liferay.Util.escapeHTML(helpMessage);
					}

					instance
						.get('inputBox')
						.next('.form-text')
						.setHTML(helpMessage);
				},

				_updateInputPlaceholderDescription(languageId) {
					var instance = this;

					if (instance._inputPlaceholderDescription) {
						var icon = instance._flags.one(
							'[data-languageId="' + languageId + '"]'
						);

						var title = '';

						if (icon) {
							title = icon.attr('title');
						}

						instance._inputPlaceholderDescription.text(title);
					}
				},

				_updateSelectedItem(languageId) {
					var instance = this;

					instance._flags.all('.active').toggleClass('active');

					var selectedLanguageId =
						languageId || instance.getSelectedLanguageId();

					var flagNode = instance._flags.one(
						'[data-languageid="' + selectedLanguageId + '"]'
					);

					if (flagNode) {
						flagNode.toggleClass('active');
					}
				},

				_updateTranslationStatus(languageId) {
					var instance = this;

					var translatedLanguages = instance.get(
						'translatedLanguages'
					);

					var translationStatus = Liferay.Language.get(
						'not-translated'
					);
					var translationStatusCssClass = 'warning';

					if (translatedLanguages.has(languageId)) {
						translationStatus = Liferay.Language.get('translated');
						translationStatusCssClass = 'success';
					}

					if (languageId === instance.get('defaultLanguageId')) {
						translationStatus = Liferay.Language.get('default');
						translationStatusCssClass = 'info';
					}

					var languageStatusNode = instance._flags.one(
						'[data-languageid="' +
							languageId +
							'"] .taglib-text-icon'
					);

					if (languageStatusNode) {
						languageStatusNode.setHTML(
							A.Lang.sub(instance.TRANSLATION_STATUS_TEMPLATE, {
								languageId: languageId.replace(/_/, '-'),
								translationStatus,
								translationStatusCssClass,
							})
						);
					}
				},

				_updateTrigger(languageId) {
					var instance = this;

					languageId = languageId.replace('_', '-');

					var triggerContent = A.Lang.sub(instance.TRIGGER_TEMPLATE, {
						flag: Liferay.Util.getLexiconIconTpl(
							languageId.toLowerCase()
						),
						languageId,
					});

					instance
						.get('inputBox')
						.one('.input-localized-trigger')
						.setHTML(triggerContent);
				},

				INPUT_HIDDEN_TEMPLATE:
					'<input id="{namespace}{id}_{value}" name="{namespace}{fieldNamePrefix}{name}_{value}{fieldNameSuffix}" type="hidden" value="" />',

				TRANSLATION_STATUS_TEMPLATE:
					'{languageId} <span class="dropdown-item-indicator-end w-auto"><span class="label label-{translationStatusCssClass}">{translationStatus}</span></span>',

				TRIGGER_TEMPLATE:
					'<span class="inline-item">{flag}</span><span class="btn-section">{languageId}</span>',

				destructor() {
					var instance = this;

					InputLocalized.unregister(instance.get('instanceId'));

					new A.EventHandle(instance._eventHandles).detach();

					if (instance._availableLanguagesSubscription) {
						instance._availableLanguagesSubscription.dispose();
					}

					if (instance._selectedLanguageIdSubscription) {
						instance._selectedLanguageIdSubscription.dispose();
					}
				},

				getSelectedLanguageId() {
					var instance = this;

					var items = instance.get(STR_ITEMS);
					var selected = instance.get(STR_SELECTED);

					return (
						items[selected] || instance.get('selectedLanguageId')
					);
				},

				getValue(languageId) {
					var instance = this;

					if (!Lang.isValue(languageId)) {
						languageId = defaultLanguageId;
					}

					return instance._getInputLanguage(languageId).val();
				},

				initializer() {
					var instance = this;

					var inputPlaceholder = instance.get(STR_INPUT_PLACEHOLDER);

					var eventHandles = [
						inputPlaceholder
							.get('form')
							.on(
								'submit',
								A.rbind(STR_SUBMIT, instance, inputPlaceholder)
							),
						instance.after(
							'select',
							instance._onSelectFlag,
							instance
						),
						Liferay.after(
							'inputLocalized:defaultLocaleChanged',
							A.bind('_onDefaultLocaleChanged', instance)
						),
						Liferay.on(
							'inputLocalized:localeChanged',
							A.bind('_onLocaleChanged', instance)
						),
						Liferay.on(
							'submitForm',
							A.rbind(STR_SUBMIT, instance, inputPlaceholder)
						),
					];

					if (!instance.get('editor')) {
						eventHandles.push(
							inputPlaceholder.on(
								'input',
								A.debounce('_onInputValueChange', 100, instance)
							)
						);
					}

					instance._eventHandles = eventHandles;

					var boundingBox = instance.get('boundingBox');

					boundingBox.plug(A.Plugin.NodeFocusManager, {
						descendants: '.palette-item a',
						keys: {
							next: 'down:39,40',
							previous: 'down:37,38',
						},
					});

					instance._inputPlaceholderDescription = boundingBox.one(
						'#' + inputPlaceholder.attr('id') + '_desc'
					);
					instance._flags = boundingBox.one('.palette-container');

					instance._State = instance.get(
						'frontendJsStateWebModule'
					).State;

					instance._selectedLanguageIdAtom = instance.get(
						'frontendJsComponentsWebModule'
					).selectedLanguageIdAtom;

					var selectedLanguageIdAtom =
						instance._selectedLanguageIdAtom;

					instance._selectedLanguageIdSubscription = instance._State.subscribe(
						selectedLanguageIdAtom,
						A.bind('_onSelectedLanguageIdChange', instance)
					);

					var activeLanguageIds = instance.get('activeLanguageIds');

					if (activeLanguageIds) {
						instance._activeLanguageIdsAtom = instance.get(
							'frontendJsComponentsWebModule'
						).activeLanguageIdsAtom;

						instance._flagsInitialContent = instance._flags.cloneNode(
							true
						);

						instance._renderActiveLanguageIds();

						var activeLanguageIdsAtom =
							instance._activeLanguageIdsAtom;

						if (instance.get('adminMode')) {
							instance._State.writeAtom(
								activeLanguageIdsAtom,
								activeLanguageIds
							);
						}

						instance._availableLanguagesSubscription = instance._State.subscribe(
							activeLanguageIdsAtom,
							A.bind('_onActiveLanguageIdsChange', instance)
						);
					}
				},

				removeInputLanguage(languageId) {
					var instance = this;

					var inputBox = instance.get('inputBox');

					var inputLanguage = inputBox.one(
						instance._getInputLanguageId(languageId)
					);

					if (inputLanguage) {
						inputLanguage.remove();
					}
				},

				selectFlag(languageId, shouldFocus) {
					var instance = this;

					if (!Lang.isValue(languageId)) {
						languageId = defaultLanguageId;
					}

					var inputPlaceholder = instance.get(STR_INPUT_PLACEHOLDER);

					var defaultLanguageValue = instance.getValue(
						defaultLanguageId
					);

					var inputLanguageValue = instance.getValue(languageId);

					instance._animate(inputPlaceholder, shouldFocus);
					instance._clearFormValidator(inputPlaceholder);

					instance._fillDefaultLanguage = !defaultLanguageValue;

					instance.set(
						'selected',
						parseInt(instance.get('items').indexOf(languageId), 10)
					);

					instance.updateInput(inputLanguageValue);

					instance._updateInputPlaceholderDescription(languageId);
					instance._updateHelpMessage(languageId);
					instance._updateTrigger(languageId);
					instance._updateSelectedItem(languageId);
				},

				updateInput(value) {
					var instance = this;

					var inputPlaceholder = instance.get(STR_INPUT_PLACEHOLDER);

					var editor = instance.get('editor');

					if (editor) {
						editor.setHTML(value);
					}
					else {
						inputPlaceholder.val(value);

						inputPlaceholder.attr(
							'dir',
							Liferay.Language.direction[
								instance.getSelectedLanguageId()
							]
						);
					}
				},

				updateInputLanguage(value, languageId) {
					var instance = this;

					var selectedLanguageId =
						languageId || instance.getSelectedLanguageId();

					if (!Lang.isValue(selectedLanguageId)) {
						selectedLanguageId = defaultLanguageId;
					}

					var defaultInputLanguage = instance._getInputLanguage(
						defaultLanguageId
					);
					var inputLanguage = instance._getInputLanguage(
						selectedLanguageId
					);

					inputLanguage.val(value);

					if (selectedLanguageId === defaultLanguageId) {
						if (instance._fillDefaultLanguage) {
							defaultInputLanguage.val(value);
						}
					}

					var translatedLanguages = instance.get(
						'translatedLanguages'
					);

					var action = 'remove';

					if (value) {
						action = 'add';
					}

					translatedLanguages[action](selectedLanguageId);

					instance._updateTranslationStatus(selectedLanguageId);
				},

				updateInputPlaceholder(editor) {
					var instance = this;

					var inputPlaceholder = instance.get(STR_INPUT_PLACEHOLDER);

					var nativeEditor = editor.getNativeEditor();

					var newPlaceholder = A.one(
						'#' + nativeEditor.element.getId()
					);

					if (inputPlaceholder.compareTo(newPlaceholder)) {
						return;
					}

					instance.set(STR_INPUT_PLACEHOLDER, newPlaceholder);
				},
			},

			register(id, config) {
				var instance = this;

				config.instanceId = id;

				var instances = instance._instances;

				var inputLocalizedInstance = instances[id];

				var createInstance = !(
					inputLocalizedInstance &&
					inputLocalizedInstance
						.get(STR_INPUT_PLACEHOLDER)
						.compareTo(A.one('#' + id))
				);

				if (createInstance) {
					if (inputLocalizedInstance) {
						inputLocalizedInstance.destroy();
					}

					inputLocalizedInstance = new InputLocalized(config);
					inputLocalizedInstance._bindUIPalette();

					instances[id] = inputLocalizedInstance;
				}

				var portletId = inputLocalizedInstance
					.get('namespace')
					.replace(/^_|_$/gm, '');

				Liferay.component(id, inputLocalizedInstance, {
					portletId,
				});
			},

			unregister(id) {
				delete InputLocalized._instances[id];
			},
		});

		Liferay.InputLocalized = InputLocalized;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-component',
			'aui-event-input',
			'aui-palette',
			'aui-set',
			'liferay-form',
		],
	}
);
