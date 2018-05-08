AUI.add(
	'liferay-input-localized',
	function(A) {
		var Lang = A.Lang;

		var STR_BLANK = '';

		var STR_INPUT_PLACEHOLDER = 'inputPlaceholder';

		var STR_ITEMS = 'items';

		var STR_ITEMS_ERROR = 'itemsError';

		var STR_SELECTED = 'selected';

		var STR_SUBMIT = '_onSubmit';

		var availableLanguages = Liferay.Language.available;

		var availableLanguageIds = A.Array.dedupe(
			[defaultLanguageId, userLanguageId].concat(A.Object.keys(availableLanguages))
		);

		var defaultLanguageId = themeDisplay.getDefaultLanguageId();

		var userLanguageId = themeDisplay.getLanguageId();

		var InputLocalized = A.Component.create(
			{
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
						valueFn: function() {
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
						setter: function(val) {
							var instance = this;

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
					INPUT_HIDDEN_TEMPLATE: '<input id="{namespace}{id}_{value}" name="{namespace}{fieldNamePrefix}{name}_{value}{fieldNameSuffix}" type="hidden" value="" />',

					TRANSLATION_STATUS_TEMPLATE: '{languageId} <span class="label label-{translationStatusCssClass}">{translationStatus}</span>',

					TRIGGER_TEMPLATE: '<span class="inline-item">{flag}</span><span class="btn-section">{languageId}</span>',

					initializer: function() {
						var instance = this;

						var inputPlaceholder = instance.get(STR_INPUT_PLACEHOLDER);

						var eventHandles = [
							inputPlaceholder.get('form').on('submit', A.rbind(STR_SUBMIT, instance, inputPlaceholder)),
							instance.after('select', instance._onSelectFlag, instance),
							Liferay.on('inputLocalized:localeChanged', A.bind('_onLocaleChanged', instance)),
							Liferay.on('submitForm', A.rbind(STR_SUBMIT, instance, inputPlaceholder))
						];

						if (!instance.get('editor')) {
							eventHandles.push(
								inputPlaceholder.on('input', A.debounce('_onInputValueChange', 100, instance))
							);
						}

						instance._eventHandles = eventHandles;

						var boundingBox = instance.get('boundingBox');

						boundingBox.plug(
							A.Plugin.NodeFocusManager,
							{
								descendants: '.palette-item a',
								keys: {
									next: 'down:39,40',
									previous: 'down:37,38'
								}
							}
						);

						instance._inputPlaceholderDescription = boundingBox.one('#' + inputPlaceholder.attr('id') + '_desc');
						instance._flags = boundingBox.one('.palette-container');
					},

					destructor: function() {
						var instance = this;

						InputLocalized.unregister(instance.get('instanceId'));

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					getSelectedLanguageId: function() {
						var instance = this;

						var items = instance.get(STR_ITEMS);
						var selected = instance.get(STR_SELECTED);

						return items[selected];
					},

					getValue: function(languageId) {
						var instance = this;

						if (!Lang.isValue(languageId)) {
							languageId = defaultLanguageId;
						}

						return instance._getInputLanguage(languageId).val();
					},

					removeInputLanguage: function(languageId) {
						var instance = this;

						var inputBox = instance.get('inputBox');

						var inputLanguage = inputBox.one(instance._getInputLanguageId(languageId));

						if (inputLanguage) {
							inputLanguage.remove();
						}
					},

					selectFlag: function(languageId, shouldFocus) {
						var instance = this;

						if (!Lang.isValue(languageId)) {
							languageId = defaultLanguageId;
						}

						var inputPlaceholder = instance.get(STR_INPUT_PLACEHOLDER);

						var defaultLanguageValue = instance.getValue(defaultLanguageId);

						var editor = instance.get('editor');

						var inputLanguageValue = instance.getValue(languageId);

						instance._animate(inputPlaceholder, shouldFocus);
						instance._clearFormValidator(inputPlaceholder);

						instance._fillDefaultLanguage = !defaultLanguageValue;

						instance.set('selected', parseInt(instance.get('items').indexOf(languageId)));

						if (editor) {
							editor.setHTML(inputLanguageValue);
						}
						else {
							inputPlaceholder.val(inputLanguageValue);

							inputPlaceholder.attr('dir', Liferay.Language.direction[languageId]);
						}

						instance._updateInputPlaceholderDescription(languageId);
						instance._updateHelpMessage(languageId);
						instance._updateTrigger(languageId);
						instance._updateSelectedItem(languageId);
					},

					updateInputLanguage: function(value, languageId) {
						var instance = this;

						var selectedLanguageId = languageId || instance.getSelectedLanguageId();

						if (!Lang.isValue(selectedLanguageId)) {
							selectedLanguageId = defaultLanguageId;
						}

						var defaultInputLanguage = instance._getInputLanguage(defaultLanguageId);
						var inputLanguage = instance._getInputLanguage(selectedLanguageId);

						inputLanguage.val(value);

						if (selectedLanguageId === defaultLanguageId) {
							if (instance._fillDefaultLanguage) {
								defaultInputLanguage.val(value);
							}
						}

						var translatedLanguages = instance.get('translatedLanguages');

						var action = 'remove';

						if (value) {
							action = 'add';
						}

						translatedLanguages[action](selectedLanguageId);

						instance._updateTranslationStatus(selectedLanguageId);
					},

					_animate: function(input, shouldFocus) {
						var instance = this;

						var animateClass = instance.get('animateClass');

						if (animateClass) {
							input.removeClass(animateClass);

							clearTimeout(instance._animating);

							setTimeout(
								function() {
									input.addClass(animateClass)

									if (shouldFocus) {
										input.focus();
									}
								},
								0
							);

							instance._animating = setTimeout(
								function() {
									input.removeClass(animateClass);

									clearTimeout(instance._animating);
								},
								700
							);
						}
					},

					_clearFormValidator: function(input) {
						var instance = this;

						var form = input.get('form');

						var liferayForm = Liferay.Form.get(form.attr('id'));

						if (liferayForm) {
							var validator = liferayForm.formValidator;

							if (A.instanceOf(validator, A.FormValidator)) {
								validator.resetAllFields();
							}
						}
					},

					_getInputLanguage: function(languageId) {
						var instance = this;

						var inputBox = instance.get('inputBox');
						var fieldPrefix = instance.get('fieldPrefix');
						var fieldPrefixSeparator = instance.get('fieldPrefixSeparator');
						var id = instance.get('id');
						var name = instance.get('name');
						var namespace = instance.get('namespace');

						var fieldNamePrefix = STR_BLANK;
						var fieldNameSuffix = STR_BLANK;

						if (fieldPrefix) {
							fieldNamePrefix = fieldPrefix + fieldPrefixSeparator;
							fieldNameSuffix = fieldPrefixSeparator;
						}

						var inputLanguage = inputBox.one(instance._getInputLanguageId(languageId));

						if (!inputLanguage) {
							inputLanguage = A.Node.create(
								A.Lang.sub(
									instance.INPUT_HIDDEN_TEMPLATE,
									{
										fieldNamePrefix: fieldNamePrefix,
										fieldNameSuffix: fieldNameSuffix,
										id: id,
										name: A.Lang.String.escapeHTML(name),
										namespace: namespace,
										value: languageId
									}
								)
							);

							inputBox.append(inputLanguage);
						}

						return inputLanguage;
					},

					_getInputLanguageId: function(languageId) {
						var instance = this;

						var id = instance.get('id');
						var namespace = instance.get('namespace');

						return '#' + namespace + id + '_' + languageId;
					},

					_onInputValueChange: function(event, input) {
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

					_onLocaleChanged: function(event) {
						var instance = this;

						var languageId = event.item.getAttribute('data-value');

						instance.selectFlag(languageId, event.source === instance);
					},

					_onSelectFlag: function(event) {
						var instance = this;

						if (!event.domEvent) {
							Liferay.fire(
								'inputLocalized:localeChanged',
								{
									item: event.item,
									source: instance
								}
							);
						}
					},

					_onSubmit: function(event, input) {
						var instance = this;

						if (event.form === input.get('form')) {
							instance._onInputValueChange.apply(instance, arguments);

							InputLocalized.unregister(input.attr('id'));
						}
					},

					_updateHelpMessage: function(languageId) {
						var instance = this;

						var helpMessage = instance.get('helpMessage');

						if (!instance.get('editor')) {
							var defaultLanguageId = instance.get('defaultLanguageId');

							if (languageId !== defaultLanguageId) {
								helpMessage = instance.getValue(defaultLanguageId);
							}

							helpMessage = Liferay.Util.escapeHTML(helpMessage);
						}

						instance.get('inputBox').next('.form-text').setHTML(helpMessage);
					},

					_updateInputPlaceholderDescription: function(languageId) {
						var instance = this;

						if (instance._inputPlaceholderDescription) {
							var icon = instance._flags.one('[data-languageId="' + languageId + '"]');

							var title = '';

							if (icon) {
								title = icon.attr('title');
							}

							instance._inputPlaceholderDescription.text(title);
						}
					},

					_updateSelectedItem: function(languageId) {
						var instance = this;

						instance._flags.all('.active').toggleClass('active');

						var selectedLanguageId = languageId || instance.getSelectedLanguageId();

						var flagNode = instance._flags.one('[data-languageid="' + selectedLanguageId + '"]');

						if (flagNode) {
							flagNode.toggleClass('active');
						}
					},

					_updateTranslationStatus: function(languageId) {
						var instance = this;

						var translatedLanguages = instance.get('translatedLanguages');

						var translationStatus = Liferay.Language.get('untranslated');
						var translationStatusCssClass = 'warning';

						if (translatedLanguages.has(languageId)) {
							translationStatus = Liferay.Language.get('translated');
							translationStatusCssClass = 'success';
						}

						if (languageId === instance.get('defaultLanguageId')) {
							translationStatus = Liferay.Language.get('default');
							translationStatusCssClass = 'info';
						}

						var languageStatusNode = instance._flags.one('[data-languageid="' + languageId + '"] .taglib-text-icon');

						if (languageStatusNode) {
							languageStatusNode.setHTML(
								A.Lang.sub(
									instance.TRANSLATION_STATUS_TEMPLATE,
									{
										languageId: languageId,
										translationStatus: translationStatus,
										translationStatusCssClass: translationStatusCssClass
									}
								)
							);
						}
					},

					_updateTrigger: function(languageId) {
						var instance = this;

						languageId = languageId.replace('_', '-');

						var triggerContent = A.Lang.sub(
							instance.TRIGGER_TEMPLATE,
							{
								flag: Liferay.Util.getLexiconIconTpl(languageId.toLowerCase()),
								languageId: languageId,
							}
						);

						instance.get('inputBox').one('.input-localized-trigger').setHTML(triggerContent);
					},

					_animating: null,
					_flags: null
				},

				register: function(id, config) {
					var instance = this;

					config.instanceId = id;

					var instances = instance._instances;

					var inputLocalizedInstance = instances[id];

					var createInstance = !(inputLocalizedInstance && inputLocalizedInstance.get(STR_INPUT_PLACEHOLDER).compareTo(A.one('#' + id)));

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

				unregister: function(id) {
					var instance = this;

					delete InputLocalized._instances[id];
				},

				_instances: {}
			}
		);

		Liferay.InputLocalized = InputLocalized;

		Liferay.on(
			'destroyPortlet',
			function(event) {
				var portletNamespace = '_' + event.portletId + '_';

				A.Object.each(
					Liferay.InputLocalized._instances,
					function(item, index) {
						if (item.get('namespace') === portletNamespace) {
							item.destroy();
						}
					}
				);
			}
		);
	},
	'',
	{
		requires: ['aui-base', 'aui-component', 'aui-event-input', 'aui-palette', 'aui-set', 'liferay-form', 'portal-available-languages']
	}
);