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
	'liferay-autocomplete-input',
	A => {
		var AArray = A.Array;
		var Lang = A.Lang;

		var REGEX_TRIGGER = /trigger/g;

		var STR_PHRASE_MATCH = 'phraseMatch';

		var STR_TRIGGER = 'trigger';

		var STR_VISIBLE = 'visible';

		var TRIGGER_CONFIG_DEFAULTS = {
			activateFirstItem: true,
			resultFilters: STR_PHRASE_MATCH,
			resultHighlighter: STR_PHRASE_MATCH
		};

		var AutoCompleteInputBase = function() {};

		AutoCompleteInputBase.ATTRS = {
			caretAtTerm: {
				validator: Lang.isBoolean,
				value: true
			},

			inputNode: {
				setter: A.one,
				writeOnce: true
			},

			offset: {
				validator: '_validateOffset',
				value: 10
			},

			regExp: {
				validator(newVal) {
					return Lang.isRegExp(newVal) || Lang.isString(newVal);
				},
				value: '(?:\\strigger|^trigger)(\\w[\\s\\w]*)'
			},

			source: {},

			tplReplace: {
				validator: Lang.isString
			},

			tplResults: {
				validator: Lang.isString
			},

			trigger: {
				setter: AArray,
				value: '@'
			}
		};

		AutoCompleteInputBase.prototype = {
			_acResultFormatter(query, results) {
				var instance = this;

				var tplResults = instance.get('tplResults');

				return results.map(result => {
					return Lang.sub(tplResults, result.raw);
				});
			},

			_adjustACPosition() {
				var instance = this;

				var xy = instance._getACPositionBase();

				var caretXY = instance._getCaretOffset();

				var offset = instance.get('offset');

				var offsetX = 0;
				var offsetY = 0;

				if (Array.isArray(offset)) {
					offsetX = offset[0];
					offsetY = offset[1];
				} else if (Lang.isNumber(offset)) {
					offsetY = offset;
				}

				var acOffset = instance._getACPositionOffset();

				xy[0] += caretXY.x + offsetX + acOffset[0];
				xy[1] += caretXY.y + offsetY + acOffset[1];

				instance.get('boundingBox').setXY(xy);
			},

			_afterACVisibleChange(event) {
				var instance = this;

				if (event.newVal) {
					instance._adjustACPosition();
				}

				instance._uiSetVisible(event.newVal);
			},

			_bindUIACIBase() {
				var instance = this;

				instance.on('query', instance._onACQuery, instance);

				instance.after(
					'visibleChange',
					instance._afterACVisibleChange,
					instance
				);
			},

			_defSelectFn(event) {
				var instance = this;

				var text = event.result.text;

				var tplReplace = instance.get('tplReplace');

				if (tplReplace) {
					text = Lang.sub(tplReplace, event.result.raw);
				}

				instance._inputNode.focus();

				instance._updateValue(text);

				instance._ariaSay('item_selected', {
					item: event.result.text
				});

				instance.hide();
			},

			_getRegExp() {
				var instance = this;

				var regExp = instance.get('regExp');

				if (Lang.isString(regExp)) {
					var triggersExpr =
						'[' + instance._getTriggers().join('|') + ']';

					regExp = new RegExp(
						regExp.replace(REGEX_TRIGGER, triggersExpr)
					);
				}

				return regExp;
			},

			_getTriggers() {
				var instance = this;

				if (!instance._triggers) {
					var triggers = [];

					instance.get(STR_TRIGGER).forEach(item => {
						triggers.push(Lang.isString(item) ? item : item.term);
					});

					instance._triggers = triggers;
				}

				return instance._triggers;
			},

			_keyDown() {
				var instance = this;

				if (instance.get(STR_VISIBLE)) {
					instance._activateNextItem();
				}
			},

			_onACQuery(event) {
				var instance = this;

				var input = instance._getQuery(event.query);

				if (input) {
					instance._setTriggerConfig(input[0]);

					event.query = input.substring(1);
				} else {
					event.preventDefault();

					if (instance.get(STR_VISIBLE)) {
						instance.hide();
					}
				}
			},

			_processKeyUp(query) {
				var instance = this;

				if (query) {
					instance._setTriggerConfig(query[0]);

					query = query.substring(1);

					instance.sendRequest(query);
				} else if (instance.get(STR_VISIBLE)) {
					instance.hide();
				}
			},

			_setTriggerConfig(trigger) {
				var instance = this;

				if (trigger !== instance._trigger) {
					var triggers = instance._getTriggers();

					var triggerConfig = instance.get(STR_TRIGGER)[
						triggers.indexOf(trigger)
					];

					instance.setAttrs(
						A.merge(instance._triggerConfigDefaults, triggerConfig)
					);

					instance._trigger = trigger;
				}
			},

			_syncUIPosAlign: Lang.emptyFn,

			_validateOffset(value) {
				return Array.isArray(value) || Lang.isNumber(value);
			},

			destructor() {
				var instance = this;

				new A.EventHandle(instance._eventHandles).detach();
			},

			initializer() {
				var instance = this;

				instance
					.get('boundingBox')
					.addClass('lfr-autocomplete-input-list');

				instance.set(
					'resultFormatter',
					A.bind('_acResultFormatter', instance)
				);

				instance._bindUIACIBase();

				var autocompleteAttrs = A.Object.keys(
					A.AutoComplete.ATTRS
				).filter(item => {
					return item !== 'value';
				});

				instance._triggerConfigDefaults = A.merge(
					TRIGGER_CONFIG_DEFAULTS
				);

				A.mix(
					instance._triggerConfigDefaults,
					instance.getAttrs(),
					false,
					autocompleteAttrs
				);
			}
		};

		Liferay.AutoCompleteInputBase = AutoCompleteInputBase;
	},
	'',
	{
		requires: [
			'aui-base',
			'autocomplete',
			'autocomplete-filters',
			'autocomplete-highlighters'
		]
	}
);
