AUI().add(
	'liferay-translation-manager',
	function(A) {
		var Lang = A.Lang;

		var CSS_HELPER_HIDDEN = 'aui-helper-hidden';

		var CSS_ACTIONS = 'lfr-actions';

		var CSS_AVAILABLE_TRANSLATIONS = 'lfr-translation-manager-available-translations';

		var CSS_AVAILABLE_TRANSLATIONS_LINKS = 'lfr-translation-manager-available-translations-links';

		var CSS_CHANGE_DEFAULT_LOCALE = 'lfr-translation-manager-change-default-locale';

		var CSS_COMPONENT = 'lfr-component';

		var CSS_DEFAULT_LOCALE = 'lfr-translation-manager-default-locale';

		var CSS_DEFAULT_LOCALE_LABEL = 'lfr-translation-manager-default-locale-label';

		var CSS_DEFAULT_LOCALE_TEXT = 'lfr-translation-manager-default-locale-text';

		var CSS_DIRECTION_DOWN = 'direction-down';

		var CSS_EXTENDED = 'lfr-extended';

		var CSS_ICON_MENU = 'lfr-translation-manager-icon-menu';

		var CSS_SHOW_ARROW = 'show-arrow';

		var CSS_TRANSLATION = 'lfr-translation-manager-translation';

		var CSS_TRANSLATION_DELETE = 'lfr-translation-manager-translation-delete';

		var CSS_TRANSLATION_EDITING = 'lfr-translation-manager-translation-editing';

		var CSS_TRANSLATION_ITEM = 'lfr-translation-manager-translation-item';

		var LOCALIZABLE_FIELD_ATTRS = ['label', 'predefinedValue', 'tip'];

		var MSG_CHANGE_DEFAULT_LANGUAGE = Liferay.Language.get('changing-the-default-language-will-delete-all-unsaved-content');

		var MSG_DEACTIVATE_LANGUAGE = Liferay.Language.get('are-you-sure-you-want-to-deactivate-this-language');

		var STR_BLANK = '';

		var STR_DOT = '.';

		var STR_SPACE = ' ';

		var TPL_CHANGE_DEFAULT_LOCALE = '<a href="javascript:;">' +  Liferay.Language.get('change') + '</a>';

		var TPL_DEFAULT_LOCALE_LABEL_NODE = '<label>' + Liferay.Language.get('default-language') + ':</label>';

		var TPL_DEFAULT_LOCALE_NODE = '<select class="' + [CSS_HELPER_HIDDEN, 'aui-field-input', 'aui-field-input-select', 'aui-field-input-menu'].join(STR_SPACE) + '"></select>';

		var TPL_LOCALE_IMAGE = '<img src="' + themeDisplay.getPathThemeImages() + '/language/{locale}.png" />';

		var TPL_AVAILABLE_TRANSLATION_LINK = '<span class="' + CSS_TRANSLATION + ' {cssClass}" locale="{locale}">' + TPL_LOCALE_IMAGE + '{displayName} <a class="' + CSS_TRANSLATION_DELETE + '" href="javascript:;">x</a></span>';

		var TPL_AVAILABLE_TRANSLATIONS_LINKS_NODE = '<span class="' + CSS_AVAILABLE_TRANSLATIONS_LINKS + '"></span>';

		var TPL_AVAILABLE_TRANSLATIONS_NODE = '<div class="' + CSS_AVAILABLE_TRANSLATIONS + '"><label>' + Liferay.Language.get('available-translations') + '</label></div>';

		var TPL_DEFAULT_LOCALE_TEXT_NODE = '<span class="' + CSS_TRANSLATION + '">' + TPL_LOCALE_IMAGE + '{displayName}</span>';

		var TPL_ICON_MENU_NODE = '<ul class="' + [CSS_ICON_MENU, CSS_COMPONENT, CSS_ACTIONS, CSS_DIRECTION_DOWN, 'max-display-items-15', CSS_EXTENDED, CSS_SHOW_ARROW].join(STR_SPACE) + '"><li class="lfr-trigger"><strong><a class="nobr" href="javascript:;"><img src="' + themeDisplay.getPathThemeImages() + '/common/add.png" /><span class="taglib-text">' + Liferay.Language.get('add-translation') + '</span></a></strong><ul>{menuItems}</ul></li></ul>';

		var TPL_ICON_NODE = '<li><a href="javascript:;" class="taglib-icon ' + CSS_TRANSLATION_ITEM + '" lang="{locale}"><img src="' + themeDisplay.getPathThemeImages() + '/language/{locale}.png" class="icon">{displayName}</a></li>';

		var TPL_OPTION = '<option value="{locale}">{displayName}</option>';

		var TranslationManager = A.Component.create(
			{
				NAME: 'translation-manager',

				ATTRS: {
					availableLocales: {
						valueFn: function() {
							var instance = this;

							return [instance.get('defaultLocale')];
						},
						validator: Lang.isArray
					},

					defaultLocale: {
						value: STR_BLANK,
						validator: Lang.isString
					},

					editingLocale: {
						valueFn: function() {
							var instance = this;

							return instance.get('defaultLocale');
						},
						lazyAdd: false,
						validator: Lang.isString
					},

					localesMap: {
						setter: function(val) {
							var instance = this;

							instance._locales = [];

							A.each(val, function(displayName, locale) {
								instance._locales.push(locale);
							});

							instance._locales.sort();

							return val;
						},
						value: {},
						validator: Lang.isObject,
						writeOnce: true
					},

					portletNamespace: {
						value: STR_BLANK
					},

					/*
					* HTML_PARSER attributes
					*/
					availableTranslationsNode: {
						valueFn: function() {
							return A.Node.create(TPL_AVAILABLE_TRANSLATIONS_NODE);
						}
					},
					availableTranslationsLinksNode: {
						valueFn: function() {
							return A.Node.create(TPL_AVAILABLE_TRANSLATIONS_LINKS_NODE);
						}
					},
					changeDefaultLocaleNode: {
						valueFn: function() {
							return A.Node.create(TPL_CHANGE_DEFAULT_LOCALE);
						}
					},
					defaultLocaleLabelNode: {
						valueFn: function() {
							return A.Node.create(TPL_DEFAULT_LOCALE_LABEL_NODE);
						}
					},
					defaultLocaleNode: {
						valueFn: '_valueDefaultLocaleNode'
					},
					defaultLocaleTextNode: {
						valueFn: '_valueDefaultLocaleTextNode'
					},
					iconMenuNode: {
						valueFn: '_valueIconMenuNode'
					}
				},

				CSS_PREFIX: 'lfr-translation-manager',

				HTML_PARSER: {
					availableTranslationsNode: function(contentBox) {
						return contentBox.one(STR_DOT + CSS_AVAILABLE_TRANSLATIONS);
					},

					availableTranslationsLinksNode: function(contentBox) {
						return contentBox.one(STR_DOT + CSS_AVAILABLE_TRANSLATIONS_LINKS);
					},

					changeDefaultLocaleNode: function(contentBox) {
						return contentBox.one(STR_DOT + CSS_CHANGE_DEFAULT_LOCALE);
					},

					defaultLocaleLabelNode: function(contentBox) {
						return contentBox.one(STR_DOT + CSS_DEFAULT_LOCALE_LABEL);
					},

					defaultLocaleNode: function(contentBox) {
						return contentBox.one(STR_DOT + CSS_DEFAULT_LOCALE);
					},

					defaultLocaleTextNode: function(contentBox) {
						return contentBox.one(STR_DOT + CSS_DEFAULT_LOCALE_TEXT);
					},

					iconMenuNode: function(contentBox) {
						return contentBox.one(STR_DOT + CSS_ICON_MENU);
					}
				},

				UI_ATTRS: ['availableLocales', 'defaultLocale', 'editingLocale'],

				prototype: {

					renderUI: function() {
						var instance = this;
						var availableTranslationsNode = instance.get('availableTranslationsNode');
						var contentBox = instance.get('contentBox');

						availableTranslationsNode.append(instance.get('availableTranslationsLinksNode'));
						contentBox.append(instance.get('defaultLocaleLabelNode'));
						contentBox.append(instance.get('defaultLocaleTextNode'));
						contentBox.append(instance.get('defaultLocaleNode'));
						contentBox.append(instance.get('changeDefaultLocaleNode'));
						contentBox.append(instance.get('iconMenuNode'));
						contentBox.append(availableTranslationsNode);

						instance.menuOverlayNode = instance.get('iconMenuNode').one('ul');
					},

					bindUI: function() {
						var instance = this;

						instance.after('defaultLocaleChange', A.bind(instance._afterDefaultLocaleChange, instance));

						instance.get('availableTranslationsLinksNode').delegate('click', A.bind(instance._onClickTranslation, instance), STR_DOT + CSS_TRANSLATION);
						instance.get('changeDefaultLocaleNode').on('click', A.bind(instance.toggleDefaultLocales, instance));
						instance.get('defaultLocaleNode').on('change', A.bind(instance._onDefaultLocaleNodeChange, instance));
						instance.get('defaultLocaleTextNode').on('click', A.bind(instance._onClickDefaultLocaleTextNode, instance));

						instance.menuOverlayNode.delegate('click', A.bind(instance._onClickTranslationItem, instance), STR_DOT + CSS_TRANSLATION_ITEM);

						Liferay.Menu.handleFocus(instance.get('iconMenuNode'));
					},

					addAvailableLocale: function(locale) {
						var instance = this;
						var availableLocales = instance.get('availableLocales');

						if (A.Array.indexOf(availableLocales, locale) === -1) {
							availableLocales.push(locale);

							instance.set('availableLocales', availableLocales);
						}
					},

					deleteAvailableLocale: function(locale) {
						var instance = this;
						var availableLocales = instance.get('availableLocales');

						A.Array.removeItem(availableLocales, locale);

						instance.set('availableLocales', availableLocales);
					},

					toggleDefaultLocales: function() {
						var instance = this;
						var defaultLocaleNode = instance.get('defaultLocaleNode');
						var defaultLocaleTextNode = instance.get('defaultLocaleTextNode');

						var state = defaultLocaleNode.hasClass(CSS_HELPER_HIDDEN) && confirm(MSG_CHANGE_DEFAULT_LANGUAGE);

						defaultLocaleNode.toggleClass(CSS_HELPER_HIDDEN, !state);
						defaultLocaleTextNode.toggleClass(CSS_HELPER_HIDDEN, state);
					},

					_onClickDefaultLocaleTextNode: function(event) {
						var instance = this;

						instance.set('editingLocale', instance.get('defaultLocale'));
					},

					_onClickTranslation: function(event) {
						var instance = this;
						var availableLocales = instance.get('availableLocales');
						var locale = event.currentTarget.attr('locale');
						var target = event.target;

						if (target.hasClass(CSS_TRANSLATION_DELETE)) {
							if (confirm(MSG_DEACTIVATE_LANGUAGE)) {
								instance.deleteAvailableLocale(locale);

								if (locale == instance.get('editingLocale')) {
									instance.set('editingLocale', instance.get('defaultLocale'));
								}
							}
						}
						else {
							instance.set('editingLocale', locale);
						}
					},

					_onClickTranslationItem: function(event) {
						var instance = this;
						var target = event.target;
						var locale = target.attr('lang');

						instance.addAvailableLocale(locale);

						instance.set('editingLocale', locale);

						instance._getMenuOverlay().hide();
					},

					_afterDefaultLocaleChange: function(event) {
						var instance = this;
						var defaultLocale = event.newVal;

						instance.set('availableLocales', [defaultLocale]);
						instance.set('editingLocale', defaultLocale);
					},

					_getMenuOverlay: function() {
						var instance = this;
						var iconMenuNode = instance.get('iconMenuNode');

						return A.Widget.getByNode(instance.menuOverlayNode);
					},

					_onDefaultLocaleNodeChange: function(event) {
						var instance = this;
						var defaultLocaleNode = event.target;

						instance.set('defaultLocale', defaultLocaleNode.val());

						instance.toggleDefaultLocales();
					},

					_uiSetAvailableLocales: function(val) {
						var instance = this;
						var buffer = [];
						var defaultLocale = instance.get('defaultLocale');
						var editingLocale = instance.get('editingLocale');
						var localesMap = instance.get('localesMap');

						A.Array.each(val, function(locale) {
							if (defaultLocale === locale) {
								return;
							}

							buffer.push(
								A.substitute(
									TPL_AVAILABLE_TRANSLATION_LINK,
									{
										cssClass: (locale === editingLocale) ? CSS_TRANSLATION_EDITING : STR_BLANK,
										displayName: localesMap[locale],
										locale: locale
									}
								)
							);
						});

						instance.get('availableTranslationsLinksNode').setContent(buffer.join(STR_BLANK));
					},

					_uiSetDefaultLocale: function(val) {
						var instance = this;

						var optionNode = instance.get('defaultLocaleNode').one('option[value=' + val + ']');

						if (optionNode) {
							var content = A.substitute(
								TPL_LOCALE_IMAGE,
								{
									locale: val
								}
							);

							content += optionNode.getContent();

							instance.get('defaultLocaleTextNode').setContent(content);
						}
					},

					_uiSetEditingLocale: function(val) {
						var instance = this;
						var availableLocales = instance.get('availableLocales');
						var availableTranslationsLinksNode = instance.get('availableTranslationsLinksNode');
						var defaultLocaleTextNode = instance.get('defaultLocaleTextNode');

						availableTranslationsLinksNode.all(STR_DOT + CSS_TRANSLATION).removeClass(CSS_TRANSLATION_EDITING);
						defaultLocaleTextNode.removeClass(CSS_TRANSLATION_EDITING);

						if (val == instance.get('defaultLocale')) {
							defaultLocaleTextNode.addClass(CSS_TRANSLATION_EDITING);
						}
						else {
							var editingLocaleNode = availableTranslationsLinksNode.one('span[locale=' + val + ']');

							if (editingLocaleNode) {
								editingLocaleNode.addClass(CSS_TRANSLATION_EDITING);
							}
						}
					},

					_valueDefaultLocaleNode: function() {
						var instance = this;
						var localesMap = instance.get('localesMap');
						var node = A.Node.create(TPL_DEFAULT_LOCALE_NODE);

						A.each(instance._locales, function(locale) {
							node.append(
								A.substitute(
									TPL_OPTION,
									{
										displayName: localesMap[locale],
										locale: locale
									}
								)
							);
						});

						return node;
					},

					_valueDefaultLocaleTextNode: function() {
						var instance = this;
						var defaultLocale = instance.get('defaultLocale');
						var localesMap = instance.get('localesMap');

						return A.Node.create(
							A.substitute(
								TPL_DEFAULT_LOCALE_TEXT_NODE,
								{
									displayName: localesMap[defaultLocale],
									locale: defaultLocale
								}
							)
						);
					},

					_valueIconMenuNode: function() {
						var instance = this;
						var localesMap = instance.get('localesMap');
						var buffer = [];

						A.each(instance._locales, function(locale) {
							buffer.push(
								A.substitute(
									TPL_ICON_NODE,
									{
										displayName: localesMap[locale],
										locale: locale
									}
								)
							);
						});

						return A.Node.create(
							A.substitute(
								TPL_ICON_MENU_NODE,
								{
									menuItems: buffer.join(STR_BLANK)
								}
							)
						);
					}

				}
			}
		);

		Liferay.TranslationManager = TranslationManager;
	},
	'',
	{
		requires: ['aui-base', 'selector-css3']
	}
);