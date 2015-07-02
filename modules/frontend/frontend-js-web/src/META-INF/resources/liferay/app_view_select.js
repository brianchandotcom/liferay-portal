AUI.add(
	'liferay-app-view-select',
	function(A) {
		var AArray = A.Array;
		var History = Liferay.HistoryManager;
		var Lang = A.Lang;
		var Util = Liferay.Util;

		var ATTR_CHECKED = 'checked';

		var CSS_SELECTABLE = 'selectable';

		var CSS_SELECTED = 'selected';

		var DISPLAY_STYLE_BUTTON_GROUP = 'displayStyleButtonGroup';

		var DISPLAY_STYLE_LIST = 'list';

		var DISPLAY_STYLE_TOOLBAR = 'displayStyleToolbar';

		var STR_CLICK = 'click';

		var STR_DISPLAY_STYLE = 'displayStyle';

		var STR_DOT = '.';

		var STR_FOCUS = 'focus';

		var STR_TOGGLE_ACTIONS_BUTTON = 'toggleActionsButton';

		var STR_TR = 'tr';

		var SELECTOR_SELECTABLE = STR_DOT + CSS_SELECTABLE;

		var SELECTOR_TR_SELECTABLE = STR_TR + STR_DOT + CSS_SELECTABLE;

		var WIN = A.config.win;

		var AppViewSelect = A.Component.create(
			{
				ATTRS: {
					checkBoxesId: {
						validator: Array.isArray
					},

					displayStyle: {
						validator: Lang.isString
					},

					displayStyleCSSClass: {
						validator: Lang.isString
					},

					displayStyleToolbar: {
						setter: A.one
					},

					portletContainerId: {
						validator: Lang.isString
					},

					selector: {
						validator: Lang.isString
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferay-app-view-select',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._portletContainer = instance.byId(instance.get('portletContainerId'));

						instance._displayStyle = instance.ns(STR_DISPLAY_STYLE);

						instance._displayStyleToolbar = instance.get(DISPLAY_STYLE_TOOLBAR);

						instance._entriesContainer = instance.byId('entriesContainer');

						instance._selectAllCheckbox = instance.byId('allRowIds');

						instance._selector = instance.get('selector');

						instance._checkBoxesId = instance.get('checkBoxesId');

						instance._displayStyleCSSClass = instance.get('displayStyleCSSClass');

						instance._eventHandles = [
							Liferay.on('liferay-app-view-move:dragStart', instance._onDragStart, instance)
						];

						instance._initHover();

						if (themeDisplay.isSignedIn()) {
							if (instance._selectAllCheckbox) {
								instance._initSelectAllCheckbox();
							}

							instance._initToggleSelect();
						}
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');
					},

					_getDisplayStyle: function(currentDisplayStyle, style) {
						var instance = this;

						var displayStyle = History.get(currentDisplayStyle) || instance.get(STR_DISPLAY_STYLE);

						if (style) {
							displayStyle = displayStyle == style;
						}

						return displayStyle;
					},

					_initHover: function() {
						var instance = this;

						instance._eventHandles.push(
							instance._entriesContainer.on([STR_FOCUS, 'blur'], instance._toggleHovered, instance)
						);
					},

					_initSelectAllCheckbox: function() {
						var instance = this;

						instance._eventHandles.push(
							instance._selectAllCheckbox.on(STR_CLICK, instance._toggleEntriesSelection, instance)
						);
					},

					_initToggleSelect: function() {
						var instance = this;

						instance._eventHandles.push(
							instance._entriesContainer.delegate(
								'change',
								instance._onEntrySelectorChange,
								STR_DOT + instance._selector,
								instance
							)
						);
					},

					_onDragStart: function(event) {
						var instance = this;

						var node = event.node;

						if (!node.hasClass(CSS_SELECTED)) {
							instance._unselectAllEntries();

							instance._toggleSelected(node);
						}
					},

					_onEntrySelectorChange: function(event) {
						var instance = this;

						instance._toggleSelected(event.currentTarget, true);

						WIN[instance.ns(STR_TOGGLE_ACTIONS_BUTTON)]();

						Util.checkAllBox(
							instance._entriesContainer,
							instance._checkBoxesId,
							instance._selectAllCheckbox
						);
					},

					_toggleEntriesSelection: function() {
						var instance = this;

						var selectAllCheckbox = instance._selectAllCheckbox;

						for (var i = 0; i < instance._checkBoxesId.length; i++) {
							Util.checkAll(
								instance._portletContainer,
								instance._checkBoxesId[i],
								selectAllCheckbox,
								SELECTOR_TR_SELECTABLE
							);
						}

						WIN[instance.ns(STR_TOGGLE_ACTIONS_BUTTON)]();

						var articleDisplayStyle = STR_DOT + instance._displayStyleCSSClass + SELECTOR_SELECTABLE;

						if (instance._getDisplayStyle(instance._displayStyle, DISPLAY_STYLE_LIST)) {
							articleDisplayStyle = SELECTOR_TR_SELECTABLE + STR_DOT + instance._displayStyleCSSClass;
						}

						var articleDisplayNodes = A.all(articleDisplayStyle);

						articleDisplayNodes.toggleClass(CSS_SELECTED, instance._selectAllCheckbox.attr(ATTR_CHECKED));
					},

					_toggleHovered: function(event) {
						var instance = this;

						if (!instance._getDisplayStyle(instance._displayStyle, DISPLAY_STYLE_LIST)) {
							var articleDisplayStyle = event.target.ancestor(STR_DOT + instance._displayStyleCSSClass);

							if (articleDisplayStyle) {
								articleDisplayStyle.toggleClass('hover', event.type == STR_FOCUS);
							}
						}
					},

					_toggleSelected: function(node, preventUpdate) {
						var instance = this;

						if (instance._getDisplayStyle(instance._displayStyle, DISPLAY_STYLE_LIST)) {
							if (!preventUpdate) {
								var input = node.one('input') || node;

								input.attr(ATTR_CHECKED, !input.attr(ATTR_CHECKED));
							}

							node = node.ancestor(SELECTOR_TR_SELECTABLE) || node;
						}
						else {
							node = node.ancestor(STR_DOT + instance._displayStyleCSSClass) || node;

							if (!preventUpdate) {
								var selectElement = node.one(STR_DOT + instance._selector);

								selectElement.attr(ATTR_CHECKED, !selectElement.attr(ATTR_CHECKED));
							}
						}

						node.toggleClass(CSS_SELECTED);
					},

					_unselectAllEntries: function() {
						var instance = this;

						instance._selectAllCheckbox.attr(CSS_SELECTED, false);

						instance._toggleEntriesSelection();
					}
				}
			}
		);

		Liferay.AppViewSelect = AppViewSelect;
	},
	'',
	{
		requires: ['liferay-app-view-move', 'liferay-history-manager', 'liferay-portlet-base']
	}
);