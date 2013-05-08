AUI.add(
	'liferay-translation-manager',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;
		var Node = A.Node;

		var CSS_HELPER_HIDDEN = 'hide';

		var CSS_ACTIONS = 'lfr-actions';

		var CSS_AVAILABLE_TRANSLATIONS = 'lfr-translation-manager-available-translations';

		var CSS_AVAILABLE_TRANSLATIONS_LINKS = 'lfr-translation-manager-available-translations-links';

		var CSS_CHANGE_DEFAULT_LOCALE = 'lfr-translation-manager-change-default-locale';

		var CSS_COMPONENT = 'lfr-component';

		var CSS_DEFAULT_LOCALE = 'lfr-translation-manager-default-locale';

		var CSS_DEFAULT_LOCALE_LABEL = 'lfr-translation-manager-default-locale-label';

		var CSS_DEFAULT_LOCALE_TEXT = 'lfr-translation-manager-default-locale-text';

		var CSS_DELETE_TRANSLATION = 'lfr-translation-manager-delete-translation';

		var CSS_DIRECTION_DOWN = 'direction-down';

		var CSS_EXTENDED = 'lfr-extended';

		var CSS_ICON_MENU = 'lfr-translation-manager-icon-menu';

		var CSS_SHOW_ARROW = 'show-arrow';

		var CSS_TRANSLATION = 'lfr-translation-manager-translation';

		var CSS_TRANSLATION_EDITING = 'lfr-translation-manager-translation-editing';

		var CSS_TRANSLATION_ITEM = 'lfr-translation-manager-translation-item';

		var MSG_DEACTIVATE_LANGUAGE = Liferay.Language.get('are-you-sure-you-want-to-deactivate-this-language');

		var STR_BLANK = '';

		var STR_DOT = '.';

		var STR_SPACE = ' ';

		var TPL_CHANGE_DEFAULT_LOCALE = '<a href="javascript:;">' +  Liferay.Language.get('change') + '</a>';

		var TPL_DEFAULT_LOCALE_LABEL_NODE = '<label>' + Liferay.Language.get('default-language') + ':</label>';

		var TPL_DEFAULT_LOCALE_NODE = '<select class="' + [CSS_HELPER_HIDDEN, 'field-input-menu'].join(STR_SPACE) + '"></select>';

		var TPL_LOCALE_IMAGE = '<img src="' + themeDisplay.getPathThemeImages() + '/language/{locale}.png" />';

		var TPL_AVAILABLE_TRANSLATION_LINK = '<span class="' + CSS_TRANSLATION + ' {cssClass}" locale="{locale}">' + TPL_LOCALE_IMAGE + '{displayName} <a class="' + CSS_DELETE_TRANSLATION + ' icon icon-close" href="javascript:;">x</a></span>';

		var TPL_AVAILABLE_TRANSLATIONS_LINKS_NODE = '<span class="' + CSS_AVAILABLE_TRANSLATIONS_LINKS + '"></span>';

		var TPL_AVAILABLE_TRANSLATIONS_NODE = '<div class="' + CSS_AVAILABLE_TRANSLATIONS + '"><label>' + Liferay.Language.get('available-translations') + '</label></div>';

		var TPL_DEFAULT_LOCALE_TEXT_NODE = '<span class="' + CSS_TRANSLATION + '">' + TPL_LOCALE_IMAGE + '{displayName}</span>';

		var TPL_ICON_MENU_NODE = '<ul class="' + [CSS_ICON_MENU, CSS_COMPONENT, CSS_ACTIONS, CSS_DIRECTION_DOWN, 'max-display-items-15', CSS_EXTENDED, CSS_SHOW_ARROW].join(STR_SPACE) + '"><li class="lfr-trigger"><strong><a class="nobr" href="javascript:;"><img src="' + themeDisplay.getPathThemeImages() + '/common/add.png" /><span class="taglib-text">' + Liferay.Language.get('add-translation') + '</span></a></strong><ul>{menuItems}</ul></li></ul>';

		var TPL_ICON_NODE = '<li class="' + CSS_TRANSLATION_ITEM + '"><a href="javascript:;" class="taglib-icon" lang="{0}"><img src="' + themeDisplay.getPathThemeImages() + '/language/{0}.png" class="icon">{1}</a></li>';

		var TPL_OPTION = '<option value="{0}">{1}</option>';

		var TranslationManager = A.Component.create(
			{
				NAME: 'translationmanager',

				ATTRS: {
					availableLocales: {
						validator: Lang.isArray,
						valueFn: '_valueAvailableLocales'
					},

					availableTranslationsNode: {
						valueFn: '_valueAvailableTranslationsNode'
					},

					availableTranslationsLinksNode: {
						valueFn: '_valueAvailableTranslationsLinksNode'
					},

					changeDefaultLocaleNode: {
						valueFn: '_valueChangeDefaultLocaleNode'
					},

					defaultLocale: {
						validator: Lang.isString,
						value: 'en_US'
					},

					defaultLocaleLabelNode: {
						valueFn: '_valueDefaultLocaleLabelNode'
					},

					defaultLocaleNode: {
						valueFn: '_valueDefaultLocaleNode'
					},

					defaultLocaleTextNode: {
						valueFn: '_valueDefaultLocaleTextNode'
					},

					editingLocale: {
						lazyAdd: false,
						validator: Lang.isString,
						valueFn: '_valueEditingLocale'
					},

					iconMenuNode: {
						valueFn: '_valueIconMenuNode'
					},

					localesMap: {
						setter: '_setLocalesMap',
						validator: Lang.isObject,
						value: {},
						writeOnce: true
					},

					portletNamespace: {
						value: STR_BLANK
					},

					readOnly: {
						validator: Lang.isBoolean,
						value: false
					}
				},

				CSS_PREFIX: 'lfr-translation-manager',

				HTML_PARSER: {
					availableTranslationsNode: STR_DOT + CSS_AVAILABLE_TRANSLATIONS,
					availableTranslationsLinksNode: STR_DOT + CSS_AVAILABLE_TRANSLATIONS_LINKS,
					changeDefaultLocaleNode: STR_DOT + CSS_CHANGE_DEFAULT_LOCALE,
					defaultLocaleLabelNode: STR_DOT + CSS_DEFAULT_LOCALE_LABEL,
					defaultLocaleNode: STR_DOT + CSS_DEFAULT_LOCALE,
					defaultLocaleTextNode: STR_DOT + CSS_DEFAULT_LOCALE_TEXT,
					iconMenuNode: STR_DOT + CSS_ICON_MENU
				},

				UI_ATTRS: ['availableLocales', 'defaultLocale', 'editingLocale', 'readOnly'],

				prototype: {
					renderUI: function() {
						var instance = this;

						var availableTranslationsNode = instance.get('availableTranslationsNode');
						var availableTranslationsLinksNode = instance.get('availableTranslationsLinksNode');
						var changeDefaultLocaleNode = instance.get('changeDefaultLocaleNode');
						var defaultLocaleNode = instance.get('defaultLocaleNode');
						var defaultLocaleLabelNode = instance.get('defaultLocaleLabelNode');
						var defaultLocaleTextNode = instance.get('defaultLocaleTextNode');
						var iconMenuNode = instance.get('iconMenuNode');

						var contentBox = instance.get('contentBox');

						availableTranslationsNode.append(availableTranslationsLinksNode);

						var nodeList = new A.NodeList(
							[
								defaultLocaleLabelNode,
								defaultLocaleTextNode,
								defaultLocaleNode,
								changeDefaultLocaleNode,
								iconMenuNode,
								availableTranslationsNode
							]
						);

						contentBox.append(nodeList);

						instance._availableTranslationsNode = availableTranslationsNode;
						instance._availableTranslationsLinksNode = availableTranslationsLinksNode;
						instance._changeDefaultLocaleNode = changeDefaultLocaleNode;
						instance._defaultLocaleNode = defaultLocaleNode;
						instance._defaultLocaleTextNode = defaultLocaleTextNode;
						instance._iconMenuNode = iconMenuNode;

						instance._menuOverlayNode = iconMenuNode.one('ul');
					},

					bindUI: function() {
						var instance = this;

						instance.on('defaultLocaleChange', instance._onDefaultLocaleChange, instance);
						instance.after('defaultLocaleChange', instance._afterDefaultLocaleChange, instance);

						instance._changeDefaultLocaleNode.on('click', instance.toggleDefaultLocales, instance);
						instance._defaultLocaleNode.on('change', instance._onDefaultLocaleNodeChange, instance);
						instance._defaultLocaleTextNode.on('click', instance._onClickDefaultLocaleTextNode, instance);

						instance._availableTranslationsLinksNode.delegate('click', instance._onClickTranslation, STR_DOT + CSS_TRANSLATION, instance);

						instance._menuOverlayNode.delegate('click', instance._onClickTranslationItem, STR_DOT + CSS_TRANSLATION_ITEM, instance);

						Liferay.Menu.handleFocus(instance._iconMenuNode);
					},

					addAvailableLocale: function(locale) {
						var instance = this;

						var availableLocales = instance.get('availableLocales');

						if (AArray.indexOf(availableLocales, locale) === -1) {
							availableLocales.push(locale);

							instance.set('availableLocales', availableLocales);
						}

						instance.fire(
							'addAvailableLocale',
							{
								locale: locale
							}
						);
					},

					deleteAvailableLocale: function(locale) {
						var instance = this;

						var availableLocales = instance.get('availableLocales');

						AArray.removeItem(availableLocales, locale);

						instance.set('availableLocales', availableLocales);

						instance.fire(
							'deleteAvailableLocale',
							{
								locale: locale
							}
						);
					},

					toggleDefaultLocales: function() {
						var instance = this;

						var defaultLocaleNode = instance._defaultLocaleNode;
						var defaultLocaleTextNode = instance._defaultLocaleTextNode;

						defaultLocaleNode.toggle();
						defaultLocaleTextNode.toggle();

						var text;

						if (defaultLocaleNode.test(':hidden')) {
							text = Liferay.Language.get('change');
						}
						else {
							text = Liferay.Language.get('cancel');
						}

						instance._changeDefaultLocaleNode.text(text);
					},

					_afterDefaultLocaleChange: function(event) {
						var instance = this;

						instance.set('editingLocale', event.newVal);
					},

					_getFormattedBuffer: function(tpl) {
						var instance = this;

						var localesMap = instance.get('localesMap');

						var buffer = [];
						var tplBuffer = [];

						var html;

						A.each(
							instance._locales,
							function(item, index, collection) {
								tplBuffer[0] = item;
								tplBuffer[1] = localesMap[item];

								html = Lang.sub(tpl, tplBuffer);

								buffer.push(html);
							}
						);

						return buffer;
					},

					_getMenuOverlay: function() {
						var instance = this;

						return A.Widget.getByNode(instance._menuOverlayNode);
					},

					_onClickDefaultLocaleTextNode: function(event) {
						var instance = this;

						instance._resetEditingLocale();
					},

					_onClickTranslation: function(event) {
						var instance = this;

						var locale = event.currentTarget.attr('locale');

						if (event.target.hasClass(CSS_DELETE_TRANSLATION)) {
							if (confirm(MSG_DEACTIVATE_LANGUAGE)) {
								instance.deleteAvailableLocale(locale);

								if (locale === instance.get('editingLocale')) {
									instance._resetEditingLocale();
								}
							}
						}
						else {
							instance.set('editingLocale', locale);
						}
					},

					_onClickTranslationItem: function(event) {
						var instance = this;

						var link = event.currentTarget.one('a');

						var locale = link.attr('lang');

						instance.addAvailableLocale(locale);

						instance.set('editingLocale', locale);

						instance._getMenuOverlay().hide();
					},

					_onDefaultLocaleNodeChange: function(event) {
						var instance = this;

						instance.set('defaultLocale', event.target.val());

						instance.toggleDefaultLocales();
					},

					_resetEditingLocale: function() {
						var instance = this;

						instance.set('editingLocale', instance.get('defaultLocale'));
					},

					_setLocalesMap: function(val) {
						var instance = this;

						var locales = A.Object.keys(val);

						locales.sort();

						instance._locales = locales;

						return val;
					},

					_uiSetAvailableLocales: function(val) {
						var instance = this;

						var defaultLocale = instance.get('defaultLocale');
						var editingLocale = instance.get('editingLocale');
						var localesMap = instance.get('localesMap');
						var readOnly = instance.get('readOnly');

						var buffer = [];

						var tplBuffer = {
							cssClass: STR_BLANK,
							displayName: STR_BLANK,
							locale: STR_BLANK
						};

						AArray.each(
							val,
							function(item, index, collection) {
								if (defaultLocale !== item) {
									tplBuffer.cssClass = (editingLocale === item) ? CSS_TRANSLATION_EDITING : STR_BLANK;

									tplBuffer.displayName = localesMap[item];
									tplBuffer.locale = item;

									html = Lang.sub(TPL_AVAILABLE_TRANSLATION_LINK, tplBuffer);

									buffer.push(html);
								}
							}
						);

						instance._availableTranslationsNode.toggle(!!buffer.length && !readOnly);

						instance._availableTranslationsLinksNode.setContent(buffer.join(STR_BLANK));
					},

					_uiSetDefaultLocale: function(val) {
						var instance = this;

						var optionNode = instance._defaultLocaleNode.one('option[value=' + val + ']');

						if (optionNode) {
							var content = Lang.sub(
								TPL_LOCALE_IMAGE,
								{
									locale: val
								}
							);

							content += optionNode.getContent();

							instance._defaultLocaleTextNode.setContent(content);
						}

						instance._uiSetAvailableLocales(instance.get('availableLocales'));
					},

					_uiSetEditingLocale: function(val) {
						var instance = this;

						var availableTranslationsLinksNode = instance._availableTranslationsLinksNode;
						var availableTranslationsLinksItems = availableTranslationsLinksNode.all(STR_DOT + CSS_TRANSLATION);

						var defaultLocaleTextNode = instance._defaultLocaleTextNode;

						availableTranslationsLinksItems.removeClass(CSS_TRANSLATION_EDITING);
						defaultLocaleTextNode.removeClass(CSS_TRANSLATION_EDITING);

						var localeNode;

						if (val === instance.get('defaultLocale')) {
							localeNode = defaultLocaleTextNode;
						}
						else {
							localeNode = availableTranslationsLinksNode.one('span[locale=' + val + ']');
						}

						if (localeNode) {
							localeNode.addClass(CSS_TRANSLATION_EDITING);
						}
					},

					_uiSetReadOnly: function(val) {
						var instance = this;

						instance._iconMenuNode.toggle(!val);
					},

					_valueAvailableLocales: function() {
						var instance = this;

						return [instance.get('defaultLocale')];
					},

					_valueAvailableTranslationsNode: function() {
						var instance = this;

						return Node.create(TPL_AVAILABLE_TRANSLATIONS_NODE);
					},

					_valueAvailableTranslationsLinksNode: function() {
						var instance = this;

						return Node.create(TPL_AVAILABLE_TRANSLATIONS_LINKS_NODE);
					},

					_valueChangeDefaultLocaleNode: function() {
						var instance = this;

						return Node.create(TPL_CHANGE_DEFAULT_LOCALE);
					},

					_valueDefaultLocaleLabelNode: function() {
						var instance = this;

						return Node.create(TPL_DEFAULT_LOCALE_LABEL_NODE);
					},

					_valueDefaultLocaleNode: function() {
						var instance = this;

						var node = Node.create(TPL_DEFAULT_LOCALE_NODE);

						var buffer = instance._getFormattedBuffer(TPL_OPTION);

						node.append(buffer.join(''));

						return node;
					},

					_valueDefaultLocaleTextNode: function() {
						var instance = this;

						var defaultLocale = instance.get('defaultLocale');
						var localesMap = instance.get('localesMap');

						var html = Lang.sub(
							TPL_DEFAULT_LOCALE_TEXT_NODE,
							{
								displayName: localesMap[defaultLocale],
								locale: defaultLocale
							}
						);

						return Node.create(html);
					},

					_valueEditingLocale: function() {
						var instance = this;

						return instance.get('defaultLocale');
					},

					_valueIconMenuNode: function() {
						var instance = this;

						var buffer = instance._getFormattedBuffer(TPL_ICON_NODE);

						var html = Lang.sub(
							TPL_ICON_MENU_NODE,
							{
								menuItems: buffer.join(STR_BLANK)
							}
						);

						return Node.create(html);
					}

				}
			}
		);

		Liferay.TranslationManager = TranslationManager;
	},
	'',
	{
		requires: ['aui-base', 'liferay-menu']
	}
);