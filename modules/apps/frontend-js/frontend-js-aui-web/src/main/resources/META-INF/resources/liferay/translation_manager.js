/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-translation-manager',
	(A) => {
		const AArray = A.Array;
		const Lang = A.Lang;
		const Node = A.Node;

		const CSS_ACTIONS = 'lfr-actions';

		const CSS_AVAILABLE_TRANSLATIONS =
			'lfr-translation-manager-available-translations';

		const CSS_AVAILABLE_TRANSLATIONS_LINKS =
			'lfr-translation-manager-available-translations-links';

		const CSS_CHANGE_DEFAULT_LOCALE =
			'lfr-translation-manager-change-default-locale';

		const CSS_COMPONENT = 'list-unstyled';

		const CSS_DEFAULT_LOCALE = 'lfr-translation-manager-default-locale';

		const CSS_DEFAULT_LOCALE_LABEL =
			'lfr-translation-manager-default-locale-label';

		const CSS_DEFAULT_LOCALE_TEXT =
			'lfr-translation-manager-default-locale-text';

		const CSS_DELETE_TRANSLATION =
			'lfr-translation-manager-delete-translation';

		const CSS_DIRECTION_DOWN = 'direction-down';

		const CSS_EXTENDED = 'lfr-extended';

		const CSS_HELPER_HIDDEN = 'hide';

		const CSS_ICON_MENU = 'lfr-translation-manager-icon-menu';

		const CSS_SHOW_ARROW = 'show-arrow';

		const CSS_TRANSLATION = 'lfr-translation-manager-translation';

		const CSS_TRANSLATION_EDITING =
			'lfr-translation-manager-translation-editing';

		const CSS_TRANSLATION_ITEM = 'lfr-translation-manager-translation-item';

		const MSG_DEACTIVATE_LANGUAGE = Liferay.Language.get(
			'are-you-sure-you-want-to-deactivate-this-language'
		);

		const STR_BLANK = '';

		const STR_DOT = '.';

		const STR_SPACE = ' ';

		const TPL_LOCALE_IMAGE =
			'<img src="' +
			themeDisplay.getPathThemeImages() +
			'/language/{locale}.png" />';

		const TPL_AVAILABLE_TRANSLATIONS_LINKS_NODE =
			'<span class="' + CSS_AVAILABLE_TRANSLATIONS_LINKS + '"></span>';

		const TPL_AVAILABLE_TRANSLATIONS_NODE =
			'<div class="' +
			CSS_AVAILABLE_TRANSLATIONS +
			'">' +
			'<label>' +
			Liferay.Language.get('available-translations') +
			'</label>' +
			'</div>';

		const TPL_AVAILABLE_TRANSLATION_LINK =
			'<span class="label label-default label-lg ' +
			CSS_TRANSLATION +
			' {cssClass}" locale="{locale}">' +
			TPL_LOCALE_IMAGE +
			'{displayName} ' +
			'<span class="' +
			CSS_DELETE_TRANSLATION +
			'">' +
			Liferay.Util.getLexiconIconTpl('times') +
			'</span>' +
			'</span>';

		const TPL_CHANGE_DEFAULT_LOCALE =
			'<a href="javascript:void(0);">' +
			Liferay.Language.get('change') +
			'</a>';

		const TPL_DEFAULT_LOCALE_LABEL_NODE =
			'<label>' + Liferay.Language.get('default-language') + ':</label>';

		const TPL_DEFAULT_LOCALE_NODE =
			'<select class="' +
			[CSS_HELPER_HIDDEN, 'field-input-menu'].join(STR_SPACE) +
			'"></select>';

		const TPL_DEFAULT_LOCALE_TEXT_NODE =
			'<span class="' +
			CSS_TRANSLATION +
			'">' +
			TPL_LOCALE_IMAGE +
			'{displayName}</span>';

		const TPL_ICON_MENU_NODE =
			'<ul class="' +
			[
				CSS_ICON_MENU,
				CSS_COMPONENT,
				CSS_ACTIONS,
				CSS_DIRECTION_DOWN,
				'max-display-items-15',
				CSS_EXTENDED,
				CSS_SHOW_ARROW,
			].join(STR_SPACE) +
			'">' +
			'<li class="lfr-trigger">' +
			'<strong>' +
			'<a class="text-nowrap" href="javascript:void(0);">' +
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

		const TPL_ICON_NODE =
			'<li class="' +
			CSS_TRANSLATION_ITEM +
			'">' +
			'<a class="taglib-icon" href="javascript:void(0);" lang="{0}">' +
			'<img class="icon" src="' +
			themeDisplay.getPathThemeImages() +
			'/language/{0}.png">{1}' +
			'</a>' +
			'</li>';

		const TPL_OPTION = '<option value="{0}">{1}</option>';

		const TranslationManager = A.Component.create({
			ATTRS: {
				availableLocales: {
					validator: Array.isArray,
					valueFn: '_valueAvailableLocales',
				},

				availableTranslationsLinksNode: {
					valueFn: '_valueAvailableTranslationsLinksNode',
				},

				availableTranslationsNode: {
					valueFn: '_valueAvailableTranslationsNode',
				},

				changeDefaultLocaleNode: {
					valueFn: '_valueChangeDefaultLocaleNode',
				},

				changeableDefaultLanguage: {
					validator: Lang.isBoolean,
					value: true,
				},

				defaultLocale: {
					validator: Lang.isString,
					value: 'en_US',
				},

				defaultLocaleLabelNode: {
					valueFn: '_valueDefaultLocaleLabelNode',
				},

				defaultLocaleNode: {
					valueFn: '_valueDefaultLocaleNode',
				},

				defaultLocaleTextNode: {
					valueFn: '_valueDefaultLocaleTextNode',
				},

				editingLocale: {
					lazyAdd: false,
					setter: '_setEditingLocale',
					valueFn: '_valueEditingLocale',
				},

				iconMenuNode: {
					valueFn: '_valueIconMenuNode',
				},

				localesMap: {
					setter: '_setLocalesMap',
					validator: Lang.isObject,
					value: {},
					writeOnce: true,
				},

				portletNamespace: {
					value: STR_BLANK,
				},

				readOnly: {
					validator: Lang.isBoolean,
					value: false,
				},
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
				iconMenuNode: STR_DOT + CSS_ICON_MENU,
			},

			NAME: 'translationmanager',

			UI_ATTRS: [
				'availableLocales',
				'defaultLocale',
				'editingLocale',
				'readOnly',
			],

			prototype: {
				_afterDefaultLocaleChange(event) {
					const instance = this;

					instance.set('editingLocale', event.newVal);
				},

				_getFormattedBuffer(tpl) {
					const instance = this;

					const localesMap = instance.get('localesMap');

					const buffer = [];
					const tplBuffer = [];

					let html;

					instance._locales.forEach((item) => {
						tplBuffer[0] = item;
						tplBuffer[1] = localesMap[item];

						html = Lang.sub(tpl, tplBuffer);

						buffer.push(html);
					});

					return buffer;
				},

				_getMenuOverlay() {
					const instance = this;

					return A.Widget.getByNode(instance._menuOverlayNode);
				},

				_onClickDefaultLocaleTextNode() {
					const instance = this;

					instance._resetEditingLocale();
				},

				_onClickTranslation(event) {
					const instance = this;

					const locale = event.currentTarget.attr('locale');

					if (event.target.hasClass(CSS_DELETE_TRANSLATION)) {
						Liferay.Util.openConfirmModal({
							message: MSG_DEACTIVATE_LANGUAGE,
							onConfirm: (isConfirmed) => {
								if (isConfirmed) {
									instance.deleteAvailableLocale(locale);

									if (
										locale === instance.get('editingLocale')
									) {
										instance._resetEditingLocale();
									}
								}
							},
						});
					}
					else {
						instance.set('editingLocale', locale);
					}
				},

				_onClickTranslationItem(event) {
					const instance = this;

					const link = event.currentTarget.one('a');

					const locale = link.attr('lang');

					instance.addAvailableLocale(locale);

					instance.set('editingLocale', locale);

					instance._getMenuOverlay().hide();
				},

				_onDefaultLocaleNodeChange(event) {
					const instance = this;

					instance.set('defaultLocale', event.target.val());

					instance.toggleDefaultLocales();
				},

				_resetEditingLocale() {
					const instance = this;

					instance.set(
						'editingLocale',
						instance.get('defaultLocale')
					);
				},

				_setEditingLocale(val) {
					const instance = this;

					const localesMap = instance.get('localesMap');

					// eslint-disable-next-line @liferay/aui/no-object
					return A.Object.hasKey(localesMap, val)
						? val
						: instance._valueEditingLocale();
				},

				_setLocalesMap(val) {
					const instance = this;

					const locales = Object.keys(val);

					if (locales.length !== 0) {
						this.syncAvailableLocales(locales);
					}

					locales.sort();

					instance._locales = locales;

					return val;
				},

				_uiSetAvailableLocales(val) {
					const instance = this;

					const defaultLocale = instance.get('defaultLocale');
					const editingLocale = instance.get('editingLocale');
					const localesMap = instance.get('localesMap');
					const readOnly = instance.get('readOnly');

					const buffer = [];

					const tplBuffer = {
						cssClass: STR_BLANK,
						displayName: STR_BLANK,
						locale: STR_BLANK,
					};

					val.forEach((item) => {
						if (defaultLocale !== item) {
							tplBuffer.cssClass =
								editingLocale === item
									? CSS_TRANSLATION_EDITING
									: STR_BLANK;

							tplBuffer.displayName = localesMap[item];
							tplBuffer.locale = item;

							const html = Lang.sub(
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
					const instance = this;

					const optionNode = instance._defaultLocaleNode.one(
						'option[value=' + val + ']'
					);

					if (optionNode) {
						let content = Lang.sub(TPL_LOCALE_IMAGE, {
							locale: val,
						});

						content += optionNode.getContent();

						instance._defaultLocaleTextNode.setContent(content);
					}

					instance._uiSetAvailableLocales(
						instance.get('availableLocales')
					);
				},

				_uiSetEditingLocale(val) {
					const instance = this;

					const availableTranslationsLinksNode =
						instance._availableTranslationsLinksNode;

					const availableTranslationsLinksItems = availableTranslationsLinksNode.all(
						STR_DOT + CSS_TRANSLATION
					);

					const defaultLocaleTextNode =
						instance._defaultLocaleTextNode;

					availableTranslationsLinksItems.removeClass(
						CSS_TRANSLATION_EDITING
					);
					defaultLocaleTextNode.removeClass(CSS_TRANSLATION_EDITING);

					let localeNode;

					if (val === instance.get('defaultLocale')) {
						localeNode = defaultLocaleTextNode;
					}
					else {
						localeNode = availableTranslationsLinksNode.one(
							'span[locale=' + val + ']'
						);
					}

					if (localeNode) {
						localeNode.addClass(CSS_TRANSLATION_EDITING);
					}
				},

				_uiSetReadOnly(val) {
					const instance = this;

					instance._iconMenuNode.toggle(!val);
				},

				_valueAvailableLocales() {
					const instance = this;

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
					const instance = this;

					const node = Node.create(TPL_DEFAULT_LOCALE_NODE);

					const buffer = instance._getFormattedBuffer(TPL_OPTION);

					node.append(buffer.join(''));

					return node;
				},

				_valueDefaultLocaleTextNode() {
					const instance = this;

					const defaultLocale = instance.get('defaultLocale');
					const localesMap = instance.get('localesMap');

					const html = Lang.sub(TPL_DEFAULT_LOCALE_TEXT_NODE, {
						displayName: localesMap[defaultLocale],
						locale: defaultLocale,
					});

					return Node.create(html);
				},

				_valueEditingLocale() {
					const instance = this;

					return instance.get('defaultLocale');
				},

				_valueIconMenuNode() {
					const instance = this;

					const buffer = instance._getFormattedBuffer(TPL_ICON_NODE);

					const html = Lang.sub(TPL_ICON_MENU_NODE, {
						menuItems: buffer.join(STR_BLANK),
					});

					return Node.create(html);
				},

				addAvailableLocale(locale) {
					const instance = this;

					const availableLocales = instance.get('availableLocales');

					if (availableLocales.indexOf(locale) === -1) {
						availableLocales.push(locale);

						instance.set('availableLocales', availableLocales);
					}

					instance.fire('addAvailableLocale', {
						locale,
					});
				},

				bindUI() {
					const instance = this;

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
					const instance = this;

					const availableLocales = instance.get('availableLocales');

					AArray.removeItem(availableLocales, locale);

					instance.set('availableLocales', availableLocales);

					instance.fire('deleteAvailableLocale', {
						locale,
					});
				},

				renderUI() {
					const instance = this;

					const availableTranslationsLinksNode = instance.get(
						'availableTranslationsLinksNode'
					);
					const availableTranslationsNode = instance.get(
						'availableTranslationsNode'
					);
					const changeableDefaultLanguage = instance.get(
						'changeableDefaultLanguage'
					);
					const defaultLocaleLabelNode = instance.get(
						'defaultLocaleLabelNode'
					);
					const defaultLocaleNode = instance.get('defaultLocaleNode');
					const defaultLocaleTextNode = instance.get(
						'defaultLocaleTextNode'
					);
					const iconMenuNode = instance.get('iconMenuNode');

					const contentBox = instance.get('contentBox');

					availableTranslationsNode.append(
						availableTranslationsLinksNode
					);

					let nodes = [
						defaultLocaleLabelNode,
						defaultLocaleTextNode,
						defaultLocaleNode,
					];

					if (changeableDefaultLanguage) {
						const changeDefaultLocaleNode = instance.get(
							'changeDefaultLocaleNode'
						);

						instance._changeDefaultLocaleNode = changeDefaultLocaleNode;

						nodes.push(changeDefaultLocaleNode);
					}

					nodes = nodes.concat([
						iconMenuNode,
						availableTranslationsNode,
					]);

					const nodeList = new A.NodeList(nodes);

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
					const instance = this;

					const availableLocales = instance.get('availableLocales');

					instance.set(
						'availableLocales',
						AArray.filter(availableLocales, (item) => {
							return AArray.indexOf(locales, item) > -1;
						})
					);
				},

				toggleDefaultLocales() {
					const instance = this;

					const defaultLocaleNode = instance._defaultLocaleNode;
					const defaultLocaleTextNode =
						instance._defaultLocaleTextNode;

					defaultLocaleNode.toggle();
					defaultLocaleTextNode.toggle();

					let text;

					if (defaultLocaleNode.test(':hidden')) {
						text = Liferay.Language.get('change');
					}
					else {
						text = Liferay.Language.get('cancel');
					}

					instance._changeDefaultLocaleNode.text(text);
				},
			},
		});

		Liferay.TranslationManager = TranslationManager;
	},
	'',
	{
		requires: ['aui-base', 'liferay-menu'],
	}
);
