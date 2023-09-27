/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-calendar-list',
	(A) => {
		const AArray = A.Array;
		const Lang = A.Lang;

		const isArray = Lang.isArray;
		const isObject = Lang.isObject;

		const getClassName = A.getClassName;

		const STR_BLANK = '';

		const STR_CALENDAR_LIST = 'calendar-list';

		const STR_DOT = '.';

		const STR_ITEM = 'item';

		const STR_PLUS = '+';

		const CSS_CALENDAR_LIST_ITEM = getClassName(
			STR_CALENDAR_LIST,
			STR_ITEM
		);

		const CSS_CALENDAR_LIST_ITEM_ACTIVE = getClassName(
			STR_CALENDAR_LIST,
			STR_ITEM,
			'active'
		);

		const CSS_CALENDAR_LIST_ITEM_ARROW = getClassName(
			STR_CALENDAR_LIST,
			STR_ITEM,
			'arrow'
		);

		const CSS_CALENDAR_LIST_ITEM_COLOR = getClassName(
			STR_CALENDAR_LIST,
			STR_ITEM,
			'color'
		);

		const CSS_CALENDAR_LIST_ITEM_HOVER = getClassName(
			STR_CALENDAR_LIST,
			STR_ITEM,
			'hover'
		);

		const CSS_CALENDAR_LIST_ITEM_LABEL = getClassName(
			STR_CALENDAR_LIST,
			STR_ITEM,
			'label'
		);

		const CSS_ICON_CARET_DOWN = Liferay.Util.getLexiconIconTpl(
			'caret-bottom'
		);

		const TPL_CALENDAR_LIST_ITEM = new A.Template(
			'<tpl for="calendars">',
			'<div class="',
			CSS_CALENDAR_LIST_ITEM,
			'">',
			'<div class="',
			CSS_CALENDAR_LIST_ITEM_COLOR,
			'" {[ parent.calendars[$i].get("visible") ? ',
			"'style=\"background-color:'",
			STR_PLUS,
			'parent.calendars[$i].get("color")',
			STR_PLUS,
			'";border-color:"',
			STR_PLUS,
			'parent.calendars[$i].get("color")',
			STR_PLUS,
			'";\\""',
			" : '",
			STR_BLANK,
			"' ]}></div>",
			'<span class="',
			CSS_CALENDAR_LIST_ITEM_LABEL,
			'">{[Liferay.Util.escapeHTML(parent.calendars[$i].getDisplayName())]}</span>',
			'<tpl if="parent.calendars[$i].get(\'hasMenuItems\')">',
			'<div aria-label="' +
				Liferay.Language.get('show-actions-for-calendar-x'),
			'{[parent.calendars[$i].getDisplayName()]}' + '" class="',
			CSS_CALENDAR_LIST_ITEM_ARROW,
			'" role="button" tabindex="0">',
			CSS_ICON_CARET_DOWN,
			'</div>',
			'</tpl>',
			'</div>',
			'</tpl>'
		);

		const CalendarList = A.Component.create({
			ATTRS: {
				calendars: {
					setter: '_setCalendars',
					validator: isArray,
					value: [],
				},

				scheduler: {},

				showCalendarResourceName: {
					value: true,
				},

				simpleMenu: {
					setter: '_setSimpleMenu',
					validator: isObject,
					value: null,
					zIndex: Liferay.zIndex.MENU,
				},
			},

			NAME: 'calendar-list',

			UI_ATTRS: ['calendars'],

			prototype: {
				_clearCalendarColor(calendar) {
					const instance = this;

					const node = instance.getCalendarNode(calendar);

					const colorNode = node.one(
						STR_DOT + CSS_CALENDAR_LIST_ITEM_COLOR
					);

					colorNode.setAttribute('style', STR_BLANK);
				},

				_onCalendarColorChange(event) {
					const instance = this;

					const target = event.target;

					if (target.get('visible')) {
						instance._setCalendarColor(target, event.newVal);
					}
				},

				_onCalendarVisibleChange(event) {
					const instance = this;

					const target = event.target;

					if (event.newVal) {
						instance._setCalendarColor(target, target.get('color'));
					}
					else {
						instance._clearCalendarColor(target);
					}
				},

				_onEvents(event) {
					if (
						event.keyCode === A.Event.KeyMap.ENTER ||
						event.type === 'click'
					) {
						const instance = this;

						const target = event.target.ancestor(
							STR_DOT + CSS_CALENDAR_LIST_ITEM_ARROW,
							true,
							STR_DOT + CSS_CALENDAR_LIST_ITEM
						);

						if (target) {
							let activeNode = instance.activeNode;

							if (activeNode) {
								activeNode.removeClass(
									CSS_CALENDAR_LIST_ITEM_ACTIVE
								);
							}

							activeNode = event.currentTarget;

							instance.activeItem = instance.getCalendarByNode(
								activeNode
							);

							activeNode.addClass(CSS_CALENDAR_LIST_ITEM_ACTIVE);

							instance.activeNode = activeNode;

							const simpleMenu = instance.simpleMenu;

							simpleMenu.setAttrs({
								alignNode: target,
								toggler: target,
								visible:
									simpleMenu.get('align.node') !== target ||
									!simpleMenu.get('visible'),
							});
						}
						else {
							const calendar = instance.getCalendarByNode(
								event.currentTarget
							);

							calendar.set('visible', !calendar.get('visible'));
						}
					}
				},

				_onHoverOut(event) {
					const instance = this;

					const currentTarget = event.currentTarget;

					const calendar = instance.getCalendarByNode(currentTarget);

					if (!calendar.get('visible')) {
						instance._clearCalendarColor(calendar);
					}

					currentTarget.removeClass(CSS_CALENDAR_LIST_ITEM_HOVER);
				},

				_onHoverOver(event) {
					const instance = this;

					const currentTarget = event.currentTarget;

					const calendar = instance.getCalendarByNode(currentTarget);

					currentTarget.addClass(CSS_CALENDAR_LIST_ITEM_HOVER);

					if (!calendar.get('visible')) {
						instance._setCalendarColor(
							calendar,
							calendar.get('color')
						);
					}
				},

				_onSimpleMenuVisibleChange(event) {
					const instance = this;
					const calendar = instance.activeNode.one(
						'.calendar-list-item-arrow'
					)._node;

					if (instance.activeNode && !event.newVal) {
						calendar.setAttribute('aria-expanded', false);
						instance.activeNode.removeClass(
							CSS_CALENDAR_LIST_ITEM_ACTIVE
						);
					}
					else {
						calendar.setAttribute('aria-expanded', true);
					}
				},

				_renderCalendars() {
					const instance = this;

					const calendars = instance.get('calendars');
					const contentBox = instance.get('contentBox');
					const simpleMenu = instance.get('simpleMenu');

					instance.items = A.NodeList.create(
						TPL_CALENDAR_LIST_ITEM.parse({
							calendars,
						})
					);

					instance.items
						.all(STR_DOT + CSS_CALENDAR_LIST_ITEM_ARROW)
						.setAttribute('aria-expanded', false)
						.setAttribute('aria-controls', simpleMenu.id);

					contentBox.setContent(instance.items);
				},

				_setCalendarColor(calendar, val) {
					const instance = this;

					const node = instance.getCalendarNode(calendar);

					const colorNode = node.one(
						STR_DOT + CSS_CALENDAR_LIST_ITEM_COLOR
					);

					colorNode.setStyles({
						backgroundColor: val,
						borderColor: val,
					});
				},

				_setCalendars(val) {
					const instance = this;

					const scheduler = instance.get('scheduler');

					const showCalendarResourceName = instance.get(
						'showCalendarResourceName'
					);

					val.forEach((item, index) => {
						let calendar = item;

						if (!A.instanceOf(item, Liferay.SchedulerCalendar)) {
							calendar = new Liferay.SchedulerCalendar(item);

							val[index] = calendar;
						}

						calendar.addTarget(instance);

						calendar.set('scheduler', scheduler);
						calendar.set(
							'showCalendarResourceName',
							showCalendarResourceName
						);
					});

					return val;
				},

				_setSimpleMenu(val) {
					const instance = this;

					let result = val;

					if (val) {
						result = {
							align: {
								points: [
									A.WidgetPositionAlign.TL,
									A.WidgetPositionAlign.BL,
								],
							},
							bubbleTargets: [instance],
							constrain: true,
							host: instance,
							items: [],
							plugins: [A.Plugin.OverlayAutohide],
							visible: false,
							width: 290,
							zIndex: Liferay.zIndex.MENU,
							...(val || {}),
						};
					}

					return result;
				},

				_uiSetCalendars() {
					const instance = this;

					if (instance.get('rendered')) {
						instance._renderCalendars();
					}
				},

				add(calendar) {
					const instance = this;

					const calendars = instance.get('calendars');

					calendars.push(calendar);

					instance.set('calendars', calendars);
				},

				bindUI() {
					const instance = this;

					const contentBox = instance.get('contentBox');

					instance.on(
						'scheduler-calendar:colorChange',
						instance._onCalendarColorChange,
						instance
					);
					instance.on(
						'scheduler-calendar:visibleChange',
						instance._onCalendarVisibleChange,
						instance
					);
					instance.on(
						'simple-menu:visibleChange',
						instance._onSimpleMenuVisibleChange,
						instance
					);

					contentBox.delegate(
						['click', 'keydown'],
						instance._onEvents,
						STR_DOT + CSS_CALENDAR_LIST_ITEM,
						instance
					);

					contentBox.delegate(
						'hover',
						A.bind('_onHoverOver', instance),
						A.bind('_onHoverOut', instance),
						STR_DOT + CSS_CALENDAR_LIST_ITEM
					);
				},

				clear() {
					const instance = this;

					instance.set('calendars', []);
				},

				getCalendar(calendarId) {
					const instance = this;

					const calendars = instance.get('calendars');

					let calendar = null;

					for (let i = 0; i < calendars.length; i++) {
						const cal = calendars[i];

						if (cal.get('calendarId') === calendarId) {
							calendar = cal;

							break;
						}
					}

					return calendar;
				},

				getCalendarByNode(node) {
					const instance = this;

					const calendars = instance.get('calendars');

					return calendars[instance.items.indexOf(node)];
				},

				getCalendarNode(calendar) {
					const instance = this;

					const calendars = instance.get('calendars');

					return instance.items.item(calendars.indexOf(calendar));
				},

				initializer() {
					const instance = this;

					instance.simpleMenu = new Liferay.SimpleMenu(
						instance.get('simpleMenu')
					);
				},

				remove(calendar) {
					const instance = this;

					const calendars = instance.get('calendars');

					if (calendars.length) {
						const index = calendars.indexOf(calendar);

						if (index > -1) {
							AArray.remove(calendars, index);
						}
					}

					instance.fire('calendarRemoved', {
						calendar,
					});

					instance.set('calendars', calendars);
				},

				renderUI() {
					const instance = this;

					instance._renderCalendars();

					instance.simpleMenu.render();
				},
			},
		});

		Liferay.CalendarList = CalendarList;
	},
	'',
	{
		requires: [
			'aui-template-deprecated',
			'liferay-calendar-simple-menu',
			'liferay-scheduler',
		],
	}
);
