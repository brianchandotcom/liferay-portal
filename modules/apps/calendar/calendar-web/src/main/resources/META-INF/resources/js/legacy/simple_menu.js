/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-calendar-simple-menu',
	(A) => {
		const Lang = A.Lang;

		const getClassName = A.getClassName;

		const isArray = Lang.isArray;

		const CSS_SIMPLE_MENU_ITEM = getClassName('simple-menu', 'item');

		const CSS_SIMPLE_MENU_ITEM_HIDDEN = getClassName(
			'simple-menu',
			'item',
			'hidden'
		);

		const CSS_SIMPLE_MENU_SEPARATOR = getClassName(
			'simple-menu',
			'separator'
		);

		const DEFAULT_ALIGN_POINTS = [
			A.WidgetPositionAlign.TL,
			A.WidgetPositionAlign.BL,
		];

		const STR_BLANK = '';

		const STR_DASH = '-';

		const STR_DOT = '.';

		const STR_SPACE = ' ';

		const TPL_ICON = '<i class="{iconClass}"></i>';

		const TPL_SIMPLE_MENU_ITEM =
			'<li class="{cssClass}" data-id="{id}" role="{role}" tabindex="-1"></li>';

		const getItemHandler = A.cached((id, items, key) => {
			let found = null;

			items.some((item) => {
				if (item.id === id) {
					found = item;
				}

				return !!found;
			});

			return found && found[key];
		});

		const getClickItemHandler = A.cached((id, items) => {
			return getItemHandler(id, items, 'fn');
		});

		const getKeyDownItemHandler = A.cached((id, items) => {
			return getItemHandler(id, items, 'onKeyDown');
		});

		const SimpleMenu = A.Component.create({
			ATTRS: {
				alignNode: {
					value: null,
				},

				hiddenItems: {
					validator: isArray,
					value: [],
				},

				host: {
					value: null,
				},

				items: {
					validator: isArray,
					value: [],
				},

				toggler: {
					// eslint-disable-next-line @liferay/aui/no-one
					setter: A.one,
					value: null,
				},
			},

			AUGMENTS: [
				A.WidgetModality,
				A.WidgetPosition,
				A.WidgetPositionAlign,
				A.WidgetPositionConstrain,
				A.WidgetStack,
				A.WidgetStdMod,
			],

			NAME: 'simple-menu',

			UI_ATTRS: ['hiddenItems', 'items'],

			prototype: {
				_closeMenu() {
					const instance = this;

					instance._focusItem();

					instance.hide();

					instance._insideHandler.detach();

					instance._insideHandler = null;

					instance._outsideHandler.detach();

					instance._outsideHandler = null;

					const toggler = instance.get('toggler');

					if (toggler) {
						toggler.focus();
					}
				},

				_focusItem(index) {
					const instance = this;

					const visibleItems = instance._getVisibleItems();

					if (index !== undefined) {
						index =
							(index + visibleItems.size()) % visibleItems.size();

						const item = visibleItems.item(index);

						item.setAttribute('tabindex', 0);

						item.getDOMNode().focus();
					}

					for (let i = 0; i < visibleItems.size(); i++) {
						visibleItems
							.item(i)
							.setAttribute('tabindex', i === index ? 0 : -1);
					}
				},

				_getVisibleItems() {
					const instance = this;

					return instance.items.filter(
						':not(.' +
							CSS_SIMPLE_MENU_ITEM_HIDDEN +
							'):not(.' +
							CSS_SIMPLE_MENU_SEPARATOR +
							')'
					);
				},

				_onClickItems(event) {
					const instance = this;

					const items = instance.get('items');

					const id = event.currentTarget.attr('data-id');

					const handler = getClickItemHandler(id, items);

					if (handler) {
						handler.apply(instance, arguments);
					}

					instance._closeMenu();
				},

				_onClickOutside(event) {
					const instance = this;

					const toggler = instance.get('toggler');

					if (!toggler || !toggler.contains(event.target)) {
						instance._closeMenu();
					}
				},

				_onKeyDown(event) {
					const instance = this;

					if (
						event.keyCode === A.Event.KeyMap.ESC ||
						event.keyCode === A.Event.KeyMap.TAB
					) {
						instance._closeMenu();

						return;
					}

					const activeElement = document.activeElement;

					const visibleItems = instance._getVisibleItems();

					for (let i = 0; i < visibleItems.size(); i++) {
						const item = visibleItems.item(i);

						if (item.getDOMNode() !== activeElement) {
							continue;
						}

						if (event.keyCode === A.Event.KeyMap.UP) {
							event.preventDefault();

							instance._focusItem(i - 1);
						}
						else if (event.keyCode === A.Event.KeyMap.DOWN) {
							event.preventDefault();

							instance._focusItem(i + 1);
						}
						else if (event.keyCode === A.Event.KeyMap.ENTER) {
							visibleItems.item(i).simulate('click');
						}

						break;
					}
				},

				_onKeyDownItems(event) {
					const instance = this;

					const items = instance.get('items');

					const id = event.currentTarget.attr('data-id');

					const handler = getKeyDownItemHandler(id, items);

					if (handler) {
						handler.apply(instance, arguments);
					}
				},

				_onVisibleChange(event) {
					const instance = this;

					const visible = event.newVal;

					if (visible) {
						const contentBox = instance.get('contentBox');

						instance._insideHandler = contentBox.on(
							['keydown'],
							instance._onKeyDown,
							instance
						);

						instance._outsideHandler = contentBox.on(
							['mouseupoutside', 'touchendoutside'],
							instance._onClickOutside,
							instance
						);

						instance._positionMenu();
					}

					const toggler = instance.get('toggler');

					toggler.setAttribute('aria-expanded', visible);
				},

				_positionMenu() {
					const instance = this;

					if (instance.items.size()) {
						const Util = Liferay.Util;

						let align = {
							node: instance.get('alignNode'),
							points: DEFAULT_ALIGN_POINTS,
						};

						let centered = false;
						let modal = false;
						let width = 222;

						if (Util.isPhone() || Util.isTablet()) {
							align = null;
							centered = true;
							modal = true;
							width = '90%';
						}

						instance.setAttrs({
							align,
							centered,
							modal,
							width,
						});

						const contentBox = instance.get('contentBox');

						contentBox.getDOMNode().focus();

						this._focusItem(0);
					}
				},

				_renderItems(items) {
					const instance = this;

					const contentBox = instance.get('contentBox');
					const hiddenItems = instance.get('hiddenItems');

					instance.items = A.NodeList.create();

					items.forEach((item) => {
						let caption = item.caption;

						if (!Object.prototype.hasOwnProperty.call(item, 'id')) {
							item.id = A.guid();
						}

						const id = item.id;

						let cssClass = CSS_SIMPLE_MENU_ITEM;

						let role = 'menuitem';

						if (caption === STR_DASH) {
							cssClass = CSS_SIMPLE_MENU_SEPARATOR;
							role = '';
						}

						if (hiddenItems.indexOf(id) > -1) {
							cssClass += STR_SPACE + CSS_SIMPLE_MENU_ITEM_HIDDEN;
						}

						if (item.cssClass) {
							cssClass += STR_SPACE + item.cssClass;
						}

						let icon = STR_BLANK;

						if (item.icon) {
							icon = Lang.sub(TPL_ICON, {
								iconClass: item.icon,
							});

							caption = [icon, caption].join(STR_SPACE);
						}

						const li = A.Node.create(
							Lang.sub(TPL_SIMPLE_MENU_ITEM, {
								cssClass,
								icon,
								id,
								role,
							})
						);

						li.setContent(caption);

						instance.items.push(li);
					});

					contentBox.setContent(instance.items);
				},

				_uiSetHiddenItems(val) {
					const instance = this;

					if (instance.get('rendered')) {
						instance.items.each((item) => {
							const id = item.attr('data-id');

							item.toggleClass(
								CSS_SIMPLE_MENU_ITEM_HIDDEN,
								val.indexOf(id) > -1
							);
						});
					}
				},

				_uiSetItems(val) {
					const instance = this;

					if (instance.get('rendered')) {
						instance._renderItems(val);
					}
				},

				CONTENT_TEMPLATE:
					'<ul aria-live="polite" role="menu" tabindex="0"></ul>',

				bindUI() {
					const instance = this;

					A.Event.defineOutside('touchend');

					const contentBox = instance.get('contentBox');

					instance._eventHandlers = [
						A.getWin().on(
							'resize',
							A.debounce(instance._positionMenu, 200, instance)
						),
						contentBox.delegate(
							'click',
							instance._onClickItems,
							STR_DOT + CSS_SIMPLE_MENU_ITEM,
							instance
						),
						contentBox.delegate(
							'keydown',
							instance._onKeyDownItems,
							STR_DOT + CSS_SIMPLE_MENU_ITEM,
							instance
						),
						instance.after(
							'visibleChange',
							instance._onVisibleChange,
							instance
						),
					];
				},

				destructor() {
					const instance = this;

					new A.EventHandle(instance._eventHandlers).detach();
				},

				renderUI() {
					const instance = this;

					instance.get('boundingBox').unselectable();

					instance._renderItems(instance.get('items'));
				},
			},
		});

		Liferay.SimpleMenu = SimpleMenu;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-event-base',
			'aui-template-deprecated',
			'event-outside',
			'event-touch',
			'widget-modality',
			'widget-position',
			'widget-position-align',
			'widget-position-constrain',
			'widget-stack',
			'widget-stdmod',
		],
	}
);
