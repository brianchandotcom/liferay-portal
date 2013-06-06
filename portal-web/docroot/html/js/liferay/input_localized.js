AUI.add(
	'liferay-input-localized',
	function(A) {
		var defaultLanguageId = themeDisplay.getDefaultLanguageId();
		var userLanguageId = themeDisplay.getLanguageId();

		var availableLanguages = Liferay.Language.available;

		var availableLanguageIds = A.Array.dedupe(
			[defaultLanguageId, userLanguageId].concat(A.Object.keys(availableLanguages))
		);

		var InputLocalized = A.Component.create(
			{
				ATTRS: {
					animateClass: {
						value: 'highlight-animation'
					},

					inputNamespace: {},

					inputPlaceholder: {
						setter: A.one
					},

					items: {
						value: availableLanguageIds
					},

					selected: {
						valueFn: function() {
							return A.Array.indexOf(availableLanguageIds, defaultLanguageId);
						}
					},

					translatedLanguages: {
						setter: function(val) {
							var instance = this;

							var set = new A.Set();

							if (A.Lang.isString(val)) {
								A.Array.each(val.split(','), set.add, set);
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

					INPUT_HIDDEN_TEMPLATE: '<input id="{inputNamespace}{value}" name="{inputNamespace}{value}" type="hidden" value="" />',

					ITEM_TEMPLATE: '<td class="palette-item {selectedClassName}" data-column={column} data-index={index} data-row={row} data-value="{value}">' +
						'<a href="" class="palette-item-inner" onclick="return false;">' +
							'<img class="lfr-input-localized-flag" data-languageId="{value}" src="' + themeDisplay.getPathThemeImages() + '/language/{value}.png" />' +
							'<div class="lfr-input-localized-state"></div>' +
						'</a>' +
					'</td>',

					initializer: function() {
						var instance = this;

						var inputPlaceholder = instance.get('inputPlaceholder');

						A.after(instance._afterRenderUI, instance, 'renderUI');

						instance.on(
							{
								focusedChange: instance._onFocusedChange,
								select: instance._onSelectFlag
							}
						);

						inputPlaceholder.on('input', A.debounce('_onInputValueChange', 100, instance));
					},

					getSelectedLanguageId: function() {
						var instance = this;

						var items = instance.get('items');
						var selected = instance.get('selected');

						return items[selected];
					},

					hideFlags: function() {
						var instance = this;

						instance._flags.hide();
					},

					showFlags: function() {
						var instance = this;

						instance._initializeTooltip();

						instance._flags.show();

						instance._syncTranslatedLanguagesUI();
					},

					_afterRenderUI: function() {
						var instance = this;

						instance._flags = instance.get('boundingBox').one('.palette-container');

						instance.hideFlags();
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
						var inputNamespace = instance.get('inputNamespace');

						var inputLanguage = boundingBox.one('#' + inputNamespace + languageId);

						if (!inputLanguage) {
							inputLanguage = A.Node.create(
								A.Lang.sub(
									instance.INPUT_HIDDEN_TEMPLATE,
									{
										inputNamespace: inputNamespace,
										value: languageId
									}
								)
							);

							boundingBox.append(inputLanguage);
						}

						return inputLanguage;
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

						if (event.newVal) {
							instance.showFlags();
						}
						else {
							instance.hideFlags();
						}
					},

					_onInputValueChange: function(event) {
						var instance = this;

						var selectedLanguageId = instance.getSelectedLanguageId();

						var inputLanguage = instance._getInputLanguage(selectedLanguageId);
						var defaultInputLanguage = instance._getInputLanguage(defaultLanguageId);

						instance.showFlags();

						var currentValue = event.currentTarget.val();

						inputLanguage.val(currentValue);

						if (instance._fillDefaultLanguage) {
							defaultInputLanguage.val(currentValue);
						}

						var translatedLanguages = instance.get('translatedLanguages');

						if (currentValue) {
							translatedLanguages.add(selectedLanguageId);
						}
						else {
							translatedLanguages.remove(selectedLanguageId);
						}
					},

					_onSelectFlag: function(event) {
						var instance = this;

						if (!event.domEvent) {
							var languageId = event.value;

							var inputPlaceholder = instance.get('inputPlaceholder');

							var inputLanguage = instance._getInputLanguage(languageId);

							var defaultInputLanguage = instance._getInputLanguage(defaultLanguageId);

							var defaultLanguageValue = defaultInputLanguage.val();

							inputPlaceholder.val(inputLanguage.val());

							inputPlaceholder.attr('dir', Liferay.Language.direction[languageId]);

							inputPlaceholder.attr('placeholder', defaultLanguageValue);

							instance._animate(inputPlaceholder);
							instance._clearFormValidator(inputPlaceholder);

							if (defaultLanguageValue) {
								instance._fillDefaultLanguage = false;
							}
							else {
								instance._fillDefaultLanguage = true;
							}
						}
					},

					_syncTranslatedLanguagesUI: function() {
						var instance = this;

						var flags = instance.get('items');

						var translatedLanguages = instance.get('translatedLanguages');

						A.Array.each(
							flags,
							function(item, index, collection) {
								var flagNode = instance.getItemByIndex(index);

								flagNode.toggleClass(
									'lfr-input-localized',
									translatedLanguages.has(item)
								);
							}
						);
					},

					_animating: null,
					_flags: null,
					_tooltip: null
				},

				_handleDoc: null,
				_registered: {},

				register: function(id, config) {
					var instance = this;

					InputLocalized._registered[id] = config;

					if (!InputLocalized._handleDoc) {
						InputLocalized._handleDoc = A.getDoc().delegate(['focus', 'input'], InputLocalized._onInputUserInteraction, '.language-value');
					}
				},

				_onInputUserInteraction: function(event) {
					var instance = this;

					var input = event.currentTarget;

					var id = input.attr('id');

					var config = InputLocalized._registered[id];

					if (config) {
						var inputLocalized = new InputLocalized(config).render();

						inputLocalized._onDocFocus(event);

						delete InputLocalized._registered[id];
					}
				}
			}
		);

		Liferay.InputLocalized = InputLocalized;
	},
	'',
	{
		requires: ['aui-base', 'aui-component', 'aui-event-input', 'aui-palette', 'aui-set', 'aui-tooltip', 'portal-available-languages']
	}
);