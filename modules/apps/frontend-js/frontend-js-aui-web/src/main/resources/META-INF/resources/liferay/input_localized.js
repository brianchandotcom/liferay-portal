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
	A => {
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
				A.Object.keys(availableLanguages)
			)
		);

		var InputLocalized = A.Component.create({
			_instances: {},

			ATTRS: {
				animateClass: {
					validator: Lang.isString,
					value: 'highlight-animation'
				},

				defaultLanguageId: {
					value: defaultLanguageId
				},

				editor: {},

				fieldPrefix: {
					validator: Lang.isString
				},

				fieldPrefixSeparator: {
					validator: Lang.isString
				},

				helpMessage: {
					validator: Lang.isString
				},

				id: {
					validator: Lang.isString
				},

				inputBox: {
					setter: A.one
				},

				inputPlaceholder: {
					setter: A.one
				},

				instanceId: {
					value: Lang.isString
				},

				items: {
					value: availableLanguageIds
				},

				itemsError: {
					validator: Array.isArray
				},

				name: {
					validator: Lang.isString
				},

				namespace: {
					validator: Lang.isString
				},

				selected: {
					valueFn() {
						var instance = this;

						var itemsError = instance.get(STR_ITEMS_ERROR);

						var itemIndex = instance.get('defaultLanguageId');

						if (itemsError.length) {
							itemIndex = itemsError[0];
						}

						return instance.get(STR_ITEMS).indexOf(itemIndex);
					}
				},

				translatedLanguages: {
					setter(val) {
						var set = new A.Set();

						if (Lang.isString(val)) {
							val.split(',').forEach(set.add, set);
						}

						return set;
					},
					value: null
				}
			},

			EXTENDS: A.Palette,

			NAME: 'input-localized',

			prototype: {
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
								value: languageId
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

					Liferay.fire('inputLocalized:localeChanged', {
						item: event.item,
						source: instance
					});
				},

				_onInputValueChange(event, input) {
					var instance = this;

					var editor = instance.get('editor');

					var value;

					if (editor) {
						value = editor.getHTML();
					} else {
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

				_onSelectFlag(event) {
					var instance = this;

					if (!event.domEvent) {
						Liferay.fire('inputLocalized:localeChanged', {
							item: event.item,
							source: instance
						});
					}
				},

				_onSubmit(event, input) {
					var instance = this;

					if (event.form === input.get('form')) {
						instance._onInputValueChange.apply(instance, arguments);

						InputLocalized.unregister(input.attr('id'));
					}
				},

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
						'untranslated'
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
								languageId,
								translationStatus,
								translationStatusCssClass
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
						languageId
					});

					instance
						.get('inputBox')
						.one('.input-localized-trigger')
						.setHTML(triggerContent);
				},

				INPUT_HIDDEN_TEMPLATE:
					'<input id="{namespace}{id}_{value}" name="{namespace}{fieldNamePrefix}{name}_{value}{fieldNameSuffix}" type="hidden" value="" />',

				TRANSLATION_STATUS_TEMPLATE:
					'{languageId} <span class="label label-{translationStatusCssClass}">{translationStatus}</span>',

				TRIGGER_TEMPLATE:
					'<span class="inline-item">{flag}</span><span class="btn-section">{languageId}</span>',

				destructor() {
					var instance = this;

					InputLocalized.unregister(instance.get('instanceId'));

					new A.EventHandle(instance._eventHandles).detach();
				},

				getSelectedLanguageId() {
					var instance = this;

					var items = instance.get(STR_ITEMS);
					var selected = instance.get(STR_SELECTED);

					return items[selected];
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
						Liferay.on(
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
						)
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
							previous: 'down:37,38'
						}
					});

					instance._inputPlaceholderDescription = boundingBox.one(
						'#' + inputPlaceholder.attr('id') + '_desc'
					);
					instance._flags = boundingBox.one('.palette-container');
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
					} else {
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
				}
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

				Liferay.component(id, inputLocalizedInstance);
			},

			unregister(id) {
				delete InputLocalized._instances[id];
			}
		});

		Liferay.InputLocalized = InputLocalized;

		Liferay.on('destroyPortlet', event => {
			var portletNamespace = '_' + event.portletId + '_';

			A.Object.each(Liferay.InputLocalized._instances, item => {
				if (item.get('namespace') === portletNamespace) {
					item.destroy();
				}
			});
		});
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
			'portal-available-languages'
		]
	}
);
