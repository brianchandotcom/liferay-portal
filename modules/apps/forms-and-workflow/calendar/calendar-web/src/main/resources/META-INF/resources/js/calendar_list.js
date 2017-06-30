AUI.add(
	'liferay-calendar-list',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;

		var isArray = Lang.isArray;
		var isObject = Lang.isObject;

		var	getClassName = A.getClassName;

		var STR_BLANK = '';

		var STR_CALENDAR_LIST = 'calendar-list';

		var STR_DOT = '.';

		var STR_ITEM = 'item';

		var STR_PLUS = '+';

		var CSS_CALENDAR_LIST_ITEM = getClassName(STR_CALENDAR_LIST, STR_ITEM);

		var CSS_CALENDAR_LIST_ITEM_ACTIVE = getClassName(STR_CALENDAR_LIST, STR_ITEM, 'active');

		var CSS_CALENDAR_LIST_ITEM_ARROW = getClassName(STR_CALENDAR_LIST, STR_ITEM, 'arrow');

		var CSS_CALENDAR_LIST_ITEM_COLOR = getClassName(STR_CALENDAR_LIST, STR_ITEM, 'color');

		var CSS_CALENDAR_LIST_ITEM_HOVER = getClassName(STR_CALENDAR_LIST, STR_ITEM, 'hover');

		var CSS_CALENDAR_LIST_ITEM_LABEL = getClassName(STR_CALENDAR_LIST, STR_ITEM, 'label');

		var CSS_ICON_CARET_DOWN = 'icon-caret-down';

		var TPL_CALENDAR_LIST_ITEM = new A.Template(
			'<tpl for="calendars">',
				'<div class="', CSS_CALENDAR_LIST_ITEM, '">',
					'<div class="', CSS_CALENDAR_LIST_ITEM_COLOR, '" {[ parent.calendars[$i].get("visible") ? ', '\'style="background-color:\'', STR_PLUS, 'parent.calendars[$i].get("color")', STR_PLUS, '";border-color:"', STR_PLUS, 'parent.calendars[$i].get("color")', STR_PLUS, '";\\""', ' : \'', STR_BLANK, '\' ]}></div>',
					'<span class="', CSS_CALENDAR_LIST_ITEM_LABEL, '">{[LString.escapeHTML(parent.calendars[$i].getDisplayName())]}</span>',
					'<div class="', CSS_CALENDAR_LIST_ITEM_ARROW, '">',
						'<i class="', CSS_ICON_CARET_DOWN, '"></i>',
					'</div>',
				'</div>',
			'</tpl>'
		);

		var CalendarList = A.Component.create(
			{
				ATTRS: {
					calendars: {
						setter: '_setCalendars',
						validator: isArray,
						value: []
					},

					scheduler: {
					},

					showCalendarResourceName: {
						value: true
					},

					simpleMenu: {
						setter: '_setSimpleMenu',
						validator: isObject,
						value: null,
						zIndex: Liferay.zIndex.MENU
					}
				},

				NAME: 'calendar-list',

				UI_ATTRS: ['calendars'],

				prototype: {
					initializer: function() {
						var instance = this;

						instance.simpleMenu = new Liferay.SimpleMenu(instance.get('simpleMenu'));
					},

					renderUI: function() {
						var instance = this;

						instance._renderCalendars();

						instance.simpleMenu.render();
					},

					bindUI: function() {
						var instance = this;

						var contentBox = instance.get('contentBox');

						instance.on('scheduler-calendar:colorChange', instance._onCalendarColorChange, instance);
						instance.on('scheduler-calendar:visibleChange', instance._onCalendarVisibleChange, instance);
						instance.on('simple-menu:visibleChange', instance._onSimpleMenuVisibleChange, instance);

						contentBox.delegate('click', instance._onClick, STR_DOT + CSS_CALENDAR_LIST_ITEM, instance);

						contentBox.delegate(
							'hover',
							A.bind('_onHoverOver', instance),
							A.bind('_onHoverOut', instance),
							STR_DOT + CSS_CALENDAR_LIST_ITEM
						);
					},

					add: function(calendar) {
						var instance = this;

						var calendars = instance.get('calendars');

						calendars.push(calendar);

						instance.set('calendars', calendars);
					},

					clear: function() {
						var instance = this;

						instance.set('calendars', []);
					},

					getCalendar: function(calendarId) {
						var instance = this;

						var calendars = instance.get('calendars');

						var calendar = null;

						for (var i = 0; i < calendars.length; i++) {
							var cal = calendars[i];

							if (cal.get('calendarId') === calendarId) {
								calendar = cal;

								break;
							}
						}

						return calendar;
					},

					getCalendarByNode: function(node) {
						var instance = this;

						var calendars = instance.get('calendars');

						return calendars[instance.items.indexOf(node)];
					},

					getCalendarNode: function(calendar) {
						var instance = this;

						var calendars = instance.get('calendars');

						return instance.items.item(calendars.indexOf(calendar));
					},

					remove: function(calendar) {
						var instance = this;

						var calendars = instance.get('calendars');

						if (calendars.length > 0) {
							var index = calendars.indexOf(calendar);

							if (index > -1) {
								AArray.remove(calendars, index);
							}
						}

						instance.fire(
							'calendarRemoved',
							{
								calendar: calendar
							}
						);

						instance.set('calendars', calendars);
					},

					_clearCalendarColor: function(calendar) {
						var instance = this;

						var node = instance.getCalendarNode(calendar);

						var colorNode = node.one(STR_DOT + CSS_CALENDAR_LIST_ITEM_COLOR);

						colorNode.setAttribute('style', STR_BLANK);
					},

					_onCalendarColorChange: function(event) {
						var instance = this;

						var target = event.target;

						if (target.get('visible')) {
							instance._setCalendarColor(target, event.newVal);
						}
					},

					_onCalendarVisibleChange: function(event) {
						var instance = this;

						var target = event.target;

						if (event.newVal) {
							instance._setCalendarColor(target, target.get('color'));
						}
						else {
							instance._clearCalendarColor(target);
						}
					},

					_onClick: function(event) {
						var instance = this;

						var target = event.target.ancestor(STR_DOT + CSS_CALENDAR_LIST_ITEM_ARROW, true, STR_DOT + CSS_CALENDAR_LIST_ITEM);

						if (target) {
							var activeNode = instance.activeNode;

							if (activeNode) {
								activeNode.removeClass(CSS_CALENDAR_LIST_ITEM_ACTIVE);
							}

							activeNode = event.currentTarget;

							instance.activeItem = instance.getCalendarByNode(activeNode);

							activeNode.addClass(CSS_CALENDAR_LIST_ITEM_ACTIVE);

							instance.activeNode = activeNode;

							var simpleMenu = instance.simpleMenu;

							simpleMenu.setAttrs(
								{
									alignNode: target,
									toggler: target,
									visible: simpleMenu.get('align.node') !== target || !simpleMenu.get('visible')
								}
							);
						}
						else {
							var calendar = instance.getCalendarByNode(event.currentTarget);

							calendar.set('visible', !calendar.get('visible'));
						}
					},

					_onHoverOut: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var calendar = instance.getCalendarByNode(currentTarget);

						if (!calendar.get('visible')) {
							instance._clearCalendarColor(calendar);
						}

						currentTarget.removeClass(CSS_CALENDAR_LIST_ITEM_HOVER);
					},

					_onHoverOver: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var calendar = instance.getCalendarByNode(currentTarget);

						currentTarget.addClass(CSS_CALENDAR_LIST_ITEM_HOVER);

						if (!calendar.get('visible')) {
							instance._setCalendarColor(calendar, calendar.get('color'));
						}
					},

					_onSimpleMenuVisibleChange: function(event) {
						var instance = this;

						if (instance.activeNode && !event.newVal) {
							instance.activeNode.removeClass(CSS_CALENDAR_LIST_ITEM_ACTIVE);
						}
					},

					_renderCalendars: function() {
						var instance = this;

						var calendars = instance.get('calendars');
						var contentBox = instance.get('contentBox');

						instance.items = A.NodeList.create(
							TPL_CALENDAR_LIST_ITEM.parse(
								{
									calendars: calendars
								}
							)
						);

						contentBox.setContent(instance.items);

					},

					_setCalendarColor: function(calendar, val) {
						var instance = this;

						var node = instance.getCalendarNode(calendar);

						var colorNode = node.one(STR_DOT + CSS_CALENDAR_LIST_ITEM_COLOR);

						colorNode.setStyles(
							{
								backgroundColor: val,
								borderColor: val
							}
						);
					},

					_setCalendars: function(val) {
						var instance = this;

						var scheduler = instance.get('scheduler');

						var showCalendarResourceName = instance.get('showCalendarResourceName');

						val.forEach(
							function(item, index) {
								var calendar = item;

								if (!A.instanceOf(item, Liferay.SchedulerCalendar)) {
									calendar = new Liferay.SchedulerCalendar(item);

									val[index] = calendar;
								}

								calendar.addTarget(instance);

								calendar.set('scheduler', scheduler);
								calendar.set('showCalendarResourceName', showCalendarResourceName);
							}
						);

						return val;
					},

					_setSimpleMenu: function(val) {
						var instance = this;

						var result = val;

						if (val) {
							result = A.merge(
								{
									align: {
										points: [A.WidgetPositionAlign.TL, A.WidgetPositionAlign.BL]
									},
									bubbleTargets: [instance],
									constrain: true,
									host: instance,
									items: [],
									plugins: [A.Plugin.OverlayAutohide],
									visible: false,
									width: 290,
									zIndex: Liferay.zIndex.MENU
								},
								val || {}
							);
						}

						return result;
					},

					_uiSetCalendars: function(val) {
						var instance = this;

						if (instance.get('rendered')) {
							instance._renderCalendars();
						}
					}
				}
			}
		);

		Liferay.CalendarList = CalendarList;
	},
	'',
	{
		requires: ['aui-template-deprecated', 'liferay-calendar-simple-menu', 'liferay-scheduler']
	}
);