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
	'liferay-calendar-simple-menu',
	A => {
		var Lang = A.Lang;

		var getClassName = A.getClassName;

		var isArray = Lang.isArray;

		var CSS_SIMPLE_MENU_ITEM = getClassName('simple-menu', 'item');

		var CSS_SIMPLE_MENU_ITEM_HIDDEN = getClassName(
			'simple-menu',
			'item',
			'hidden'
		);

		var CSS_SIMPLE_MENU_SEPARATOR = getClassName(
			'simple-menu',
			'separator'
		);

		var DEFAULT_ALIGN_POINTS = [
			A.WidgetPositionAlign.TL,
			A.WidgetPositionAlign.BL
		];

		var STR_BLANK = '';

		var STR_DASH = '-';

		var STR_DOT = '.';

		var STR_SPACE = ' ';

		var TPL_ICON = '<i class="{iconClass}"></i>';

		var TPL_SIMPLE_MENU_ITEM =
			'<li class="{cssClass}" data-id="{id}">{icon} {caption}</li>';

		var getItemHandler = A.cached((id, items) => {
			var found = null;

			items.some(item => {
				if (item.id === id) {
					found = item;
				}

				return !!found;
			});

			return found && found.fn;
		});

		var SimpleMenu = A.Component.create({
			ATTRS: {
				alignNode: {
					value: null
				},

				hiddenItems: {
					validator: isArray,
					value: []
				},

				host: {
					value: null
				},

				items: {
					validator: isArray,
					value: []
				},

				toggler: {
					setter: A.one,
					value: null
				}
			},

			AUGMENTS: [
				A.WidgetModality,
				A.WidgetPosition,
				A.WidgetPositionAlign,
				A.WidgetPositionConstrain,
				A.WidgetStack,
				A.WidgetStdMod
			],

			NAME: 'simple-menu',

			UI_ATTRS: ['hiddenItems', 'items'],

			prototype: {
				_closeMenu() {
					var instance = this;

					instance.hide();

					instance._outsideHandler.detach();

					instance._outsideHandler = null;
				},

				_onClickItems(event) {
					var instance = this;

					var items = instance.get('items');

					var id = event.currentTarget.attr('data-id');

					var handler = getItemHandler(id, items);

					if (handler) {
						instance._closeMenu();

						handler.apply(instance, arguments);
					}
				},

				_onClickOutside(event) {
					var instance = this;

					var toggler = instance.get('toggler');

					if (!toggler || !toggler.contains(event.target)) {
						instance._closeMenu();
					}
				},

				_onVisibleChange(event) {
					var instance = this;

					if (event.newVal) {
						var contentBox = instance.get('contentBox');

						instance._outsideHandler = contentBox.on(
							['mouseupoutside', 'touchendoutside'],
							instance._onClickOutside,
							instance
						);

						instance._positionMenu();
					}
				},

				_positionMenu() {
					var instance = this;

					if (instance.items.size()) {
						var Util = Liferay.Util;

						var align = {
							node: instance.get('alignNode'),
							points: DEFAULT_ALIGN_POINTS
						};

						var centered = false;
						var modal = false;
						var width = 222;

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
							width
						});
					}
				},

				_renderItems(items) {
					var instance = this;

					var contentBox = instance.get('contentBox');
					var hiddenItems = instance.get('hiddenItems');

					instance.items = A.NodeList.create();

					items.forEach(item => {
						var caption = item.caption;

						if (!Object.prototype.hasOwnProperty.call(item, 'id')) {
							item.id = A.guid();
						}

						var id = item.id;

						var cssClass = CSS_SIMPLE_MENU_ITEM;

						if (caption == STR_DASH) {
							cssClass = CSS_SIMPLE_MENU_SEPARATOR;
						}

						if (hiddenItems.indexOf(id) > -1) {
							cssClass += STR_SPACE + CSS_SIMPLE_MENU_ITEM_HIDDEN;
						}

						if (item.cssClass) {
							cssClass += STR_SPACE + item.cssClass;
						}

						var icon = STR_BLANK;

						if (item.icon) {
							icon = Lang.sub(TPL_ICON, {
								iconClass: item.icon
							});

							caption = [icon, caption].join(STR_SPACE);
						}

						var li = A.Node.create(
							Lang.sub(TPL_SIMPLE_MENU_ITEM, {
								cssClass,
								icon,
								id
							})
						);

						li.setContent(caption);

						instance.items.push(li);
					});

					contentBox.setContent(instance.items);
				},

				_uiSetHiddenItems(val) {
					var instance = this;

					if (instance.get('rendered')) {
						instance.items.each(item => {
							var id = item.attr('data-id');

							item.toggleClass(
								CSS_SIMPLE_MENU_ITEM_HIDDEN,
								val.indexOf(id) > -1
							);
						});
					}
				},

				_uiSetItems(val) {
					var instance = this;

					if (instance.get('rendered')) {
						instance._renderItems(val);
					}
				},

				CONTENT_TEMPLATE: '<ul></ul>',

				bindUI() {
					var instance = this;

					A.Event.defineOutside('touchend');

					var contentBox = instance.get('contentBox');

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
						instance.after(
							'visibleChange',
							instance._onVisibleChange,
							instance
						)
					];
				},

				destructor() {
					var instance = this;

					new A.EventHandle(instance._eventHandlers).detach();
				},

				renderUI() {
					var instance = this;

					instance.get('boundingBox').unselectable();

					instance._renderItems(instance.get('items'));
				}
			}
		});

		Liferay.SimpleMenu = SimpleMenu;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-template-deprecated',
			'event-outside',
			'event-touch',
			'widget-modality',
			'widget-position',
			'widget-position-align',
			'widget-position-constrain',
			'widget-stack',
			'widget-stdmod'
		]
	}
);
