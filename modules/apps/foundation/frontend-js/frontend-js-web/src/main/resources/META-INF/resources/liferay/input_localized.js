AUI.add(
	'liferay-input-localized',
	function(A) {
		var Lang = A.Lang;

		var AArray = A.Array;

		var AObject = A.Object;

		var SELECTOR_LANG_VALUE = '.language-value';

		var STR_BLANK = '';

		var STR_INPUT_PLACEHOLDER = 'inputPlaceholder';

		var STR_INPUT_VALUE_CHANGE = '_onInputValueChange';

		var STR_ITEMS = 'items';

		var STR_ITEMS_ERROR = 'itemsError';

		var STR_SELECTED = 'selected';

		var STR_SUBMIT = '_onSubmit';

		var defaultLanguageId = themeDisplay.getDefaultLanguageId();
		var userLanguageId = themeDisplay.getLanguageId();

		var availableLanguages = Liferay.Language.available;

		var availableLanguageIds = AArray.dedupe(
			[defaultLanguageId, userLanguageId].concat(A.Object.keys(availableLanguages))
		);

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

					id: {
						validator: Lang.isString
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

							if (A.Lang.isString(val)) {
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
					BOUNDING_TEMPLATE: '<span />',

					INPUT_HIDDEN_TEMPLATE: '<input id="{namespace}{id}_{value}" name="{namespace}{fieldNamePrefix}{name}_{value}{fieldNameSuffix}" type="hidden" value="" />',

					ITEM_TEMPLATE: '<li class="palette-item {selectedClassName}" data-column={column} data-index={index} data-row={row} data-value="{value}">' +
						'<a class="palette-item-inner" href="javascript:;">' +
							'<img class="lfr-input-localized-flag" data-languageId="{value}" src="' + themeDisplay.getPathThemeImages() + '/language/{value}.png" />' +
							'<div class="lfr-input-localized-state {stateClass}"></div>' +
						'</a>' +
					'</li>',

					initializer: function() {
						var instance = this;

						var inputPlaceholder = instance.get(STR_INPUT_PLACEHOLDER);

						var eventHandles = [
							A.after(instance._afterRenderUI, instance, 'renderUI'),
							instance.after(
								{
									focusedChange: instance._onFocusedChange,
									select: instance._onSelectFlag
								}
							),
							Liferay.on('submitForm', A.rbind(STR_SUBMIT, instance, inputPlaceholder)),
							inputPlaceholder.get('form').on('submit', A.rbind(STR_SUBMIT, instance, inputPlaceholder))
						];

						if (!instance.get('editor')) {
							eventHandles.push(
								inputPlaceholder.on('input', A.debounce(STR_INPUT_VALUE_CHANGE, 100, instance))
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
					},

					destructor: function() {
						var instance = this;

						InputLocalized.unregister(instance.get('instanceId'));

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					activateFlags: function() {
						var instance = this;

						instance._initializeTooltip();

						instance._syncTranslatedLanguagesUI();
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
						var instance  = this;

						var boundingBox = instance.get('boundingBox');

						var inputLanguage = boundingBox.one(instance._getInputLanguageId(languageId));

						if (inputLanguage) {
							inputLanguage.remove();
						}
					},

					selectFlag: function(languageId) {
						var instance = this;

						var inputPlaceholder = instance.get(STR_INPUT_PLACEHOLDER);

						var defaultLanguageValue = instance.getValue(defaultLanguageId);

						var editor = instance.get('editor');

						var inputLanguageValue = instance.getValue(languageId) || defaultLanguageValue;

						inputPlaceholder.val(inputLanguageValue);

						inputPlaceholder.attr('dir', Liferay.Language.direction[languageId]);
						inputPlaceholder.attr('placeholder', defaultLanguageValue);

						instance._animate(inputPlaceholder);
						instance._clearFormValidator(inputPlaceholder);

						instance._fillDefaultLanguage = !defaultLanguageValue;

						if (editor) {
							editor.setHTML(inputLanguageValue);
						}

						if (instance._inputPlaceholderDescription) {
							var icon = instance._flags.one('[data-languageId="' + languageId + '"]');

							var title = '';

							if (icon) {
								title = icon.attr('title');
							}

							instance._inputPlaceholderDescription.text(title);
						}
					},

					updateInputLanguage: function(value) {
						var instance = this;

						var selectedLanguageId = instance.getSelectedLanguageId();

						var defaultInputLanguage = instance._getInputLanguage(defaultLanguageId);
						var inputLanguage = instance._getInputLanguage(selectedLanguageId);

						instance.activateFlags();

						inputLanguage.val(value);

						if (instance._fillDefaultLanguage) {
							defaultInputLanguage.val(value);
						}

						var translatedLanguages = instance.get('translatedLanguages');

						var action = 'remove';

						if (value) {
							action = 'add';
						}

						translatedLanguages[action](selectedLanguageId);
					},

					_afterRenderUI: function() {
						var instance = this;

						instance._flags = instance.get('boundingBox').one('.palette-container');
					},

					_animate: function(input) {
						var instance = this;

						var animateClass = instance.get('animateClass');

						if (animateClass) {
							input.removeClass(animateClass);

							clearTimeout(instance._animating);

							setTimeout(
								function() {
									input.addClass(animateClass).focus();
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

						var boundingBox = instance.get('boundingBox');
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

						var inputLanguage = boundingBox.one(instance._getInputLanguageId(languageId));

						if (!inputLanguage) {
							inputLanguage = A.Node.create(
								A.Lang.sub(
									instance.INPUT_HIDDEN_TEMPLATE,
									{
										fieldNamePrefix: fieldNamePrefix,
										fieldNameSuffix: fieldNameSuffix,
										id: id,
										name: name,
										namespace: namespace,
										value: languageId
									}
								)
							);

							boundingBox.append(inputLanguage);
						}

						return inputLanguage;
					},

					_getInputLanguageId: function(languageId) {
						var instance = this;

						var boundingBox = instance.get('boundingBox');
						var id = instance.get('id');
						var namespace = instance.get('namespace');

						return '#' + namespace + id + '_' + languageId;
					},

					_initializeTooltip: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						var tooltip = instance._tooltip;

						if (!tooltip) {
							tooltip = instance._tooltip = new A.TooltipDelegate(
								{
									container: boundingBox,
									formatter: function(title) {
										var flagNode = this.get('trigger');

										var value = flagNode.getData('value');

										var formattedValue = availableLanguages[value];

										if (value === defaultLanguageId) {
											formattedValue += ' - ' + Liferay.Language.get('default');
										}
										else if (value === userLanguageId) {
											formattedValue += ' - ' + Liferay.Language.get('current');
										}

										return formattedValue;
									},
									plugins: [Liferay.WidgetStack],
									position: 'bottom',
									trigger: '.palette-item',
									visible: false
								}
							);
						}

						return tooltip;
					},

					_onFocusedChange: function(event) {
						var instance = this;

						instance.activateFlags();
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

					_onSelectFlag: function(event) {
						var instance = this;

						if (!event.domEvent) {
							instance.selectFlag(event.value);
						}
					},

					_onSubmit: function(event, input) {
						var instance = this;

						if (event.form === input.get('form')) {
							instance._onInputValueChange.apply(instance, arguments);

							InputLocalized.unregister(input.attr('id'));
						}
					},

					_syncTranslatedLanguagesUI: function() {
						var instance = this;

						var flags = instance.get(STR_ITEMS);

						var translatedLanguages = instance.get('translatedLanguages');

						flags.forEach(
							function(item, index) {
								var flagNode = instance.getItemByIndex(index);

								flagNode.toggleClass(
									'lfr-input-localized',
									translatedLanguages.has(item)
								);
							}
						);
					},

					_valueFormatterFn: function() {
						return function(items, index, row, column, selected) {
							var instance = this;

							var item = items[index];

							var itemsError = instance.get(STR_ITEMS_ERROR);

							return Lang.sub(
								instance.ITEM_TEMPLATE,
								{
									column: column,
									index: index,
									row: row,
									selectedClassName: selected ? 'palette-item-selected' : STR_BLANK,
									stateClass: itemsError.indexOf(item) >= 0 ? 'lfr-input-localized-state-error' : STR_BLANK,
									value: Lang.isObject(item) ? item.value : item
								}
							);
						};
					},

					_animating: null,
					_flags: null,
					_tooltip: null
				},

				register: function(id, config) {
					var instance = this;

					config.instanceId = id;

					Liferay.component(
						id,
						function() {
							var instances = instance._instances;

							var inputLocalizedInstance = instances[id];

							var createInstance = !(inputLocalizedInstance && inputLocalizedInstance.get(STR_INPUT_PLACEHOLDER).compareTo(A.one('#' + id)));

							if (createInstance) {
								if (inputLocalizedInstance) {
									inputLocalizedInstance.destroy();
								}

								inputLocalizedInstance = new InputLocalized(config);

								instances[id] = inputLocalizedInstance;
							}

							return inputLocalizedInstance;
						}
					);

					if (config.lazy) {
						instance._registerConfiguration(id, config);
					}
					else {
						Liferay.component(id).render();
					}
				},

				unregister: function(id) {
					var instance = this;

					delete InputLocalized._instances[id];
					delete InputLocalized._registered[id];
				},

				_initializeInputLocalized: function(event, input, initialLanguageId) {
					var instance = this;

					var id = input.attr('id');

					var config = InputLocalized._registered[id];

					var languageId = initialLanguageId;

					if (config) {
						var inputLocalized = Liferay.component(id).render();

						inputLocalized._onDocFocus(event);

						if (languageId) {
							var items = inputLocalized.get(STR_ITEMS);

							var languageIndex = items.indexOf(languageId);

							if (languageIndex === -1) {
								languageId = defaultLanguageId;

								languageIndex = items.indexOf(languageId);
							}

							inputLocalized.set(STR_SELECTED, languageIndex);

							inputLocalized.selectFlag(languageId);
						}

						inputLocalized._onInputValueChange(event, input);

						delete InputLocalized._registered[id];
					}
				},

				_onFlagUserInteraction: function(event) {
					var instance = this;

					var currentTarget = event.currentTarget;

					var flag = currentTarget.one('.lfr-input-localized-flag');

					var input = currentTarget.ancestor('.input-localized').one(SELECTOR_LANG_VALUE);

					if (input && flag) {
						var languageNode = flag.ancestor('[data-languageid]', true, '.palette-item') || flag;

						InputLocalized._initializeInputLocalized(event, input, languageNode.attr('data-languageid'));
					}
				},

				_onInputUserInteraction: function(event) {
					var instance = this;

					var currentTarget = event.currentTarget;

					InputLocalized._initializeInputLocalized(event, currentTarget);
				},

				_registerConfiguration: function(id, config) {
					InputLocalized._registered[id] = config;

					if (!InputLocalized._interactionHandle) {
						var doc = A.getDoc();

						InputLocalized._interactionHandle = new A.EventHandle(
							[
								doc.delegate(['focus', 'input'], InputLocalized._onInputUserInteraction, SELECTOR_LANG_VALUE),
								doc.delegate('click', InputLocalized._onFlagUserInteraction, '.input-localized-content .palette-item')
							]
						);
					}
				},

				_instances: {},
				_registered: {}
			}
		);

		Liferay.InputLocalized = InputLocalized;

		Liferay.on(
			'destroyPortlet',
			function(event) {
				var portletNamespace = '_' + event.portletId + '_';

				AObject.each(
					Liferay.InputLocalized._instances,
					function(item, index) {
						if (item.get('namespace') === portletNamespace) {
							item.destroy();
						}
					}
				);

				AObject.each(
					Liferay.InputLocalized._registered,
					function(item, index) {
						if (item.namespace === portletNamespace) {
							Liferay.InputLocalized.unregister(index);
						}
					}
				);
			}
		);
	},
	'',
	{
		requires: ['aui-base', 'aui-component', 'aui-event-input', 'aui-palette', 'aui-set', 'aui-tooltip', 'liferay-form', 'portal-available-languages']
	}
);