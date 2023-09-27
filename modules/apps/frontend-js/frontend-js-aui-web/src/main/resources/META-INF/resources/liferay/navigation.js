/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-navigation',
	(A) => {
		const ANode = A.Node;
		const Lang = A.Lang;

		const STR_EMPTY = '';

		const TPL_LINK = '<a href="{url}">{pageTitle}</a>';

		const TPL_TAB_LINK =
			'<a href="{url}">' + '<span>{pageTitle}</span>' + '</a>';

		/**
		 * OPTIONS
		 *
		 * Required
		 * navBlock {string|object}: A selector or DOM element of the navigation.
		 */

		const Navigation = A.Component.create({
			ATTRS: {
				navBlock: {
					lazyAdd: false,
					setter(value) {
						value = A.one(value);

						if (!value) {
							value = A.Attribute.INVALID_VALUE;
						}

						return value;
					},
					value: null,
				},
			},

			EXTENDS: A.Base,

			NAME: 'navigation',

			prototype: {
				_createTempTab(tpl) {
					const tempLink = Lang.sub(tpl, {
						pageTitle: STR_EMPTY,
						url: '#',
					});

					const tempTab = ANode.create('<li>');

					tempTab.append(tempLink);

					return tempTab;
				},

				initializer() {
					const instance = this;

					const navBlock = instance.get('navBlock');

					if (navBlock) {
						const navListSelector =
							Liferay.Data.NAV_LIST_SELECTOR || '> ul';

						const navItemSelector =
							Liferay.Data.NAV_ITEM_SELECTOR ||
							navListSelector + '> li';

						const navItemChildToggleSelector =
							Liferay.Data.NAV_ITEM_CHILD_TOGGLE_SELECTOR ||
							'> span';

						const navList = navBlock.one(navListSelector);

						instance._navItemChildToggleSelector = navItemChildToggleSelector;
						instance._navItemSelector = navItemSelector;
						instance._navListSelector = navListSelector;

						instance._navList = navList;

						instance._tempTab = instance._createTempTab(
							TPL_TAB_LINK
						);
						instance._tempChildTab = instance._createTempTab(
							TPL_LINK
						);
					}
				},
			},
		});

		Liferay.Navigation = Navigation;
	},
	'',
	{
		requires: ['aui-component', 'event-mouseenter'],
	}
);
