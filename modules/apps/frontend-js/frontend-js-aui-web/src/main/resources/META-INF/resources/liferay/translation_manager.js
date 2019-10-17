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
	'liferay-translation-manager',
	A => {
		var AArray = A.Array;
		var Lang = A.Lang;
		var Node = A.Node;

		var CSS_ACTIONS = 'lfr-actions';

		var CSS_AVAILABLE_TRANSLATIONS =
			'lfr-translation-manager-available-translations';

		var CSS_AVAILABLE_TRANSLATIONS_LINKS =
			'lfr-translation-manager-available-translations-links';

		var CSS_CHANGE_DEFAULT_LOCALE =
			'lfr-translation-manager-change-default-locale';

		var CSS_COMPONENT = 'list-unstyled';

		var CSS_DEFAULT_LOCALE = 'lfr-translation-manager-default-locale';

		var CSS_DEFAULT_LOCALE_LABEL =
			'lfr-translation-manager-default-locale-label';

		var CSS_DEFAULT_LOCALE_TEXT =
			'lfr-translation-manager-default-locale-text';

		var CSS_DELETE_TRANSLATION =
			'lfr-translation-manager-delete-translation';

		var CSS_DIRECTION_DOWN = 'direction-down';

		var CSS_EXTENDED = 'lfr-extended';

		var CSS_HELPER_HIDDEN = 'hide';

		var CSS_ICON_MENU = 'lfr-translation-manager-icon-menu';

		var CSS_SHOW_ARROW = 'show-arrow';

		var CSS_TRANSLATION = 'lfr-translation-manager-translation';

		var CSS_TRANSLATION_EDITING =
			'lfr-translation-manager-translation-editing';

		var CSS_TRANSLATION_ITEM = 'lfr-translation-manager-translation-item';

		var MSG_DEACTIVATE_LANGUAGE = Liferay.Language.get(
			'are-you-sure-you-want-to-deactivate-this-language'
		);

		var STR_BLANK = '';

		var STR_DOT = '.';

		var STR_SPACE = ' ';

		var TPL_LOCALE_IMAGE =
			'<img src="' +
			themeDisplay.getPathThemeImages() +
			'/language/{locale}.png" />';

		var TPL_AVAILABLE_TRANSLATIONS_LINKS_NODE =
			'<span class="' + CSS_AVAILABLE_TRANSLATIONS_LINKS + '"></span>';

		var TPL_AVAILABLE_TRANSLATIONS_NODE =
			'<div class="' +
			CSS_AVAILABLE_TRANSLATIONS +
			'">' +
			'<label>' +
			Liferay.Language.get('available-translations') +
			'</label>' +
			'</div>';

		var TPL_AVAILABLE_TRANSLATION_LINK =
			'<span class="label label-default label-lg ' +
			CSS_TRANSLATION +
			' {cssClass}" locale="{locale}">' +
			TPL_LOCALE_IMAGE +
			'{displayName} ' +
			'<i class="' +
			CSS_DELETE_TRANSLATION +
			' icon icon-remove"></i>' +
			'</span>';

		var TPL_CHANGE_DEFAULT_LOCALE =
			'<a href="javascript:;">' + Liferay.Language.get('change') + '</a>';

		var TPL_DEFAULT_LOCALE_LABEL_NODE =
			'<label>' + Liferay.Language.get('default-language') + ':</label>';

		var TPL_DEFAULT_LOCALE_NODE =
			'<select class="' +
			[CSS_HELPER_HIDDEN, 'field-input-menu'].join(STR_SPACE) +
			'"></select>';

		var TPL_DEFAULT_LOCALE_TEXT_NODE =
			'<span class="' +
			CSS_TRANSLATION +
			'">' +
			TPL_LOCALE_IMAGE +
			'{displayName}</span>';

		var TPL_ICON_MENU_NODE =
			'<ul class="' +
			[
				CSS_ICON_MENU,
				CSS_COMPONENT,
				CSS_ACTIONS,
				CSS_DIRECTION_DOWN,
				'max-display-items-15',
				CSS_EXTENDED,
				CSS_SHOW_ARROW
			].join(STR_SPACE) +
			'">' +
			'<li class="lfr-trigger">' +
			'<strong>' +
			'<a class="nobr" href="javascript:;">' +
			'<img src="' +
			themeDisplay.getPathThemeImages() +
			'/common/add.png" />' +
			'<span class="taglib-text">' +
			Liferay.Language.get('add-translation') +
			'</span>' +
			'</a>' +
			'</strong>' +
			'<ul>{menuItems}</ul>' +
			'</li>' +
			'</ul>';

		var TPL_ICON_NODE =
			'<li class="' +
			CSS_TRANSLATION_ITEM +
			'">' +
			'<a class="taglib-icon" href="javascript:;" lang="{0}">' +
			'<img class="icon" src="' +
			themeDisplay.getPathThemeImages() +
			'/language/{0}.png">{1}' +
			'</a>' +
			'</li>';

		var TPL_OPTION = '<option value="{0}">{1}</option>';

		var TranslationManager = A.Component.create({
			ATTRS: {
				availableLocales: {
					validator: Array.isArray,
					valueFn: '_valueAvailableLocales'
				},

				availableTranslationsLinksNode: {
					valueFn: '_valueAvailableTranslationsLinksNode'
				},

				availableTranslationsNode: {
					valueFn: '_valueAvailableTranslationsNode'
				},

				changeDefaultLocaleNode: {
					valueFn: '_valueChangeDefaultLocaleNode'
				},

				changeableDefaultLanguage: {
					validator: Lang.isBoolean,
					value: true
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
					setter: '_setEditingLocale',
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
				availableTranslationsLinksNode:
					STR_DOT + CSS_AVAILABLE_TRANSLATIONS_LINKS,
				availableTranslationsNode: STR_DOT + CSS_AVAILABLE_TRANSLATIONS,
				changeDefaultLocaleNode: STR_DOT + CSS_CHANGE_DEFAULT_LOCALE,
				defaultLocaleLabelNode: STR_DOT + CSS_DEFAULT_LOCALE_LABEL,
				defaultLocaleNode: STR_DOT + CSS_DEFAULT_LOCALE,
				defaultLocaleTextNode: STR_DOT + CSS_DEFAULT_LOCALE_TEXT,
				iconMenuNode: STR_DOT + CSS_ICON_MENU
			},

			NAME: 'translationmanager',

			UI_ATTRS: [
				'availableLocales',
				'defaultLocale',
				'editingLocale',
				'readOnly'
			],

			prototype: {
				_afterDefaultLocaleChange(event) {
					var instance = this;

					instance.set('editingLocale', event.newVal);
				},

				_getFormattedBuffer(tpl) {
					var instance = this;

					var localesMap = instance.get('localesMap');

					var buffer = [];
					var tplBuffer = [];

					var html;

					instance._locales.forEach(item => {
						tplBuffer[0] = item;
						tplBuffer[1] = localesMap[item];

						html = Lang.sub(tpl, tplBuffer);

						buffer.push(html);
					});

					return buffer;
				},

				_getMenuOverlay() {
					var instance = this;

					return A.Widget.getByNode(instance._menuOverlayNode);
				},

				_onClickDefaultLocaleTextNode() {
					var instance = this;

					instance._resetEditingLocale();
				},

				_onClickTranslation(event) {
					var instance = this;

					var locale = event.currentTarget.attr('locale');

					if (event.target.hasClass(CSS_DELETE_TRANSLATION)) {
						if (confirm(MSG_DEACTIVATE_LANGUAGE)) {
							instance.deleteAvailableLocale(locale);

							if (locale === instance.get('editingLocale')) {
								instance._resetEditingLocale();
							}
						}
					} else {
						instance.set('editingLocale', locale);
					}
				},

				_onClickTranslationItem(event) {
					var instance = this;

					var link = event.currentTarget.one('a');

					var locale = link.attr('lang');

					instance.addAvailableLocale(locale);

					instance.set('editingLocale', locale);

					instance._getMenuOverlay().hide();
				},

				_onDefaultLocaleNodeChange(event) {
					var instance = this;

					instance.set('defaultLocale', event.target.val());

					instance.toggleDefaultLocales();
				},

				_resetEditingLocale() {
					var instance = this;

					instance.set(
						'editingLocale',
						instance.get('defaultLocale')
					);
				},

				_setEditingLocale(val) {
					var instance = this;

					var localesMap = instance.get('localesMap');

					return A.Object.hasKey(localesMap, val)
						? val
						: instance._valueEditingLocale();
				},

				_setLocalesMap(val) {
					var instance = this;

					var locales = A.Object.keys(val);

					if (locales.length != 0) {
						this.syncAvailableLocales(locales);
					}

					locales.sort();

					instance._locales = locales;

					return val;
				},

				_uiSetAvailableLocales(val) {
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

					val.forEach(item => {
						if (defaultLocale !== item) {
							tplBuffer.cssClass =
								editingLocale === item
									? CSS_TRANSLATION_EDITING
									: STR_BLANK;

							tplBuffer.displayName = localesMap[item];
							tplBuffer.locale = item;

							var html = Lang.sub(
								TPL_AVAILABLE_TRANSLATION_LINK,
								tplBuffer
							);

							buffer.push(html);
						}
					});

					instance._availableTranslationsNode.toggle(
						!!buffer.length && !readOnly
					);

					instance._availableTranslationsLinksNode.setContent(
						buffer.join(STR_BLANK)
					);
				},

				_uiSetDefaultLocale(val) {
					var instance = this;

					var optionNode = instance._defaultLocaleNode.one(
						'option[value=' + val + ']'
					);

					if (optionNode) {
						var content = Lang.sub(TPL_LOCALE_IMAGE, {
							locale: val
						});

						content += optionNode.getContent();

						instance._defaultLocaleTextNode.setContent(content);
					}

					instance._uiSetAvailableLocales(
						instance.get('availableLocales')
					);
				},

				_uiSetEditingLocale(val) {
					var instance = this;

					var availableTranslationsLinksNode =
						instance._availableTranslationsLinksNode;

					var availableTranslationsLinksItems = availableTranslationsLinksNode.all(
						STR_DOT + CSS_TRANSLATION
					);

					var defaultLocaleTextNode = instance._defaultLocaleTextNode;

					availableTranslationsLinksItems.removeClass(
						CSS_TRANSLATION_EDITING
					);
					defaultLocaleTextNode.removeClass(CSS_TRANSLATION_EDITING);

					var localeNode;

					if (val === instance.get('defaultLocale')) {
						localeNode = defaultLocaleTextNode;
					} else {
						localeNode = availableTranslationsLinksNode.one(
							'span[locale=' + val + ']'
						);
					}

					if (localeNode) {
						localeNode.addClass(CSS_TRANSLATION_EDITING);
					}
				},

				_uiSetReadOnly(val) {
					var instance = this;

					instance._iconMenuNode.toggle(!val);
				},

				_valueAvailableLocales() {
					var instance = this;

					return [instance.get('defaultLocale')];
				},

				_valueAvailableTranslationsLinksNode() {
					return Node.create(TPL_AVAILABLE_TRANSLATIONS_LINKS_NODE);
				},

				_valueAvailableTranslationsNode() {
					return Node.create(TPL_AVAILABLE_TRANSLATIONS_NODE);
				},

				_valueChangeDefaultLocaleNode() {
					return Node.create(TPL_CHANGE_DEFAULT_LOCALE);
				},

				_valueDefaultLocaleLabelNode() {
					return Node.create(TPL_DEFAULT_LOCALE_LABEL_NODE);
				},

				_valueDefaultLocaleNode() {
					var instance = this;

					var node = Node.create(TPL_DEFAULT_LOCALE_NODE);

					var buffer = instance._getFormattedBuffer(TPL_OPTION);

					node.append(buffer.join(''));

					return node;
				},

				_valueDefaultLocaleTextNode() {
					var instance = this;

					var defaultLocale = instance.get('defaultLocale');
					var localesMap = instance.get('localesMap');

					var html = Lang.sub(TPL_DEFAULT_LOCALE_TEXT_NODE, {
						displayName: localesMap[defaultLocale],
						locale: defaultLocale
					});

					return Node.create(html);
				},

				_valueEditingLocale() {
					var instance = this;

					return instance.get('defaultLocale');
				},

				_valueIconMenuNode() {
					var instance = this;

					var buffer = instance._getFormattedBuffer(TPL_ICON_NODE);

					var html = Lang.sub(TPL_ICON_MENU_NODE, {
						menuItems: buffer.join(STR_BLANK)
					});

					return Node.create(html);
				},

				addAvailableLocale(locale) {
					var instance = this;

					var availableLocales = instance.get('availableLocales');

					if (availableLocales.indexOf(locale) === -1) {
						availableLocales.push(locale);

						instance.set('availableLocales', availableLocales);
					}

					instance.fire('addAvailableLocale', {
						locale
					});
				},

				bindUI() {
					var instance = this;

					instance.on(
						'defaultLocaleChange',
						instance._onDefaultLocaleChange,
						instance
					);
					instance.after(
						'defaultLocaleChange',
						instance._afterDefaultLocaleChange,
						instance
					);

					if (instance._changeableDefaultLanguage) {
						instance._changeDefaultLocaleNode.on(
							'click',
							instance.toggleDefaultLocales,
							instance
						);
					}

					instance._defaultLocaleNode.on(
						'change',
						instance._onDefaultLocaleNodeChange,
						instance
					);
					instance._defaultLocaleTextNode.on(
						'click',
						instance._onClickDefaultLocaleTextNode,
						instance
					);

					instance._availableTranslationsLinksNode.delegate(
						'click',
						instance._onClickTranslation,
						STR_DOT + CSS_TRANSLATION,
						instance
					);

					instance._menuOverlayNode.delegate(
						'click',
						instance._onClickTranslationItem,
						STR_DOT + CSS_TRANSLATION_ITEM,
						instance
					);

					Liferay.Menu.handleFocus(instance._iconMenuNode);
				},

				deleteAvailableLocale(locale) {
					var instance = this;

					var availableLocales = instance.get('availableLocales');

					AArray.removeItem(availableLocales, locale);

					instance.set('availableLocales', availableLocales);

					instance.fire('deleteAvailableLocale', {
						locale
					});
				},

				renderUI() {
					var instance = this;

					var availableTranslationsLinksNode = instance.get(
						'availableTranslationsLinksNode'
					);
					var availableTranslationsNode = instance.get(
						'availableTranslationsNode'
					);
					var changeableDefaultLanguage = instance.get(
						'changeableDefaultLanguage'
					);
					var defaultLocaleLabelNode = instance.get(
						'defaultLocaleLabelNode'
					);
					var defaultLocaleNode = instance.get('defaultLocaleNode');
					var defaultLocaleTextNode = instance.get(
						'defaultLocaleTextNode'
					);
					var iconMenuNode = instance.get('iconMenuNode');

					var contentBox = instance.get('contentBox');

					availableTranslationsNode.append(
						availableTranslationsLinksNode
					);

					var nodes = [
						defaultLocaleLabelNode,
						defaultLocaleTextNode,
						defaultLocaleNode
					];

					if (changeableDefaultLanguage) {
						var changeDefaultLocaleNode = instance.get(
							'changeDefaultLocaleNode'
						);

						instance._changeDefaultLocaleNode = changeDefaultLocaleNode;

						nodes.push(changeDefaultLocaleNode);
					}

					nodes = nodes.concat([
						iconMenuNode,
						availableTranslationsNode
					]);

					var nodeList = new A.NodeList(nodes);

					contentBox.append(nodeList);

					instance._availableTranslationsNode = availableTranslationsNode;
					instance._availableTranslationsLinksNode = availableTranslationsLinksNode;
					instance._changeableDefaultLanguage = changeableDefaultLanguage;
					instance._defaultLocaleNode = defaultLocaleNode;
					instance._defaultLocaleTextNode = defaultLocaleTextNode;
					instance._iconMenuNode = iconMenuNode;

					instance._menuOverlayNode = iconMenuNode.one('ul');
				},

				syncAvailableLocales(locales) {
					var instance = this;

					var availableLocales = instance.get('availableLocales');

					instance.set(
						'availableLocales',
						AArray.filter(availableLocales, item => {
							return AArray.indexOf(locales, item) > -1;
						})
					);
				},

				toggleDefaultLocales() {
					var instance = this;

					var defaultLocaleNode = instance._defaultLocaleNode;
					var defaultLocaleTextNode = instance._defaultLocaleTextNode;

					defaultLocaleNode.toggle();
					defaultLocaleTextNode.toggle();

					var text;

					if (defaultLocaleNode.test(':hidden')) {
						text = Liferay.Language.get('change');
					} else {
						text = Liferay.Language.get('cancel');
					}

					instance._changeDefaultLocaleNode.text(text);
				}
			}
		});

		Liferay.TranslationManager = TranslationManager;
	},
	'',
	{
		requires: ['aui-base', 'liferay-menu']
	}
);
