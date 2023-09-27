/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-autocomplete-input',
	(A) => {
		const AArray = A.Array;
		const Lang = A.Lang;

		const REGEX_TRIGGER = /trigger/g;

		const STR_PHRASE_MATCH = 'phraseMatch';

		const STR_TRIGGER = 'trigger';

		const STR_VISIBLE = 'visible';

		const TRIGGER_CONFIG_DEFAULTS = {
			activateFirstItem: true,
			resultFilters: STR_PHRASE_MATCH,
			resultHighlighter: STR_PHRASE_MATCH,
		};

		const AutoCompleteInputBase = function () {};

		AutoCompleteInputBase.ATTRS = {
			caretAtTerm: {
				validator: Lang.isBoolean,
				value: true,
			},

			inputNode: {
				setter: A.one,
				writeOnce: true,
			},

			offset: {
				validator: '_validateOffset',
				value: 10,
			},

			regExp: {
				validator(newVal) {
					return Lang.isRegExp(newVal) || Lang.isString(newVal);
				},
				value: '(?:\\strigger|^trigger)(\\w[\\s\\w]*)',
			},

			source: {},

			tplReplace: {
				validator: Lang.isString,
			},

			tplResults: {
				validator: Lang.isString,
			},

			trigger: {
				setter: AArray,
				value: '@',
			},
		};

		AutoCompleteInputBase.prototype = {
			_acResultFormatter(query, results) {
				const instance = this;

				const tplResults = instance.get('tplResults');

				return results.map((result) => {
					return Lang.sub(tplResults, result.raw);
				});
			},

			_adjustACPosition() {
				const instance = this;

				const xy = instance._getACPositionBase();

				const caretXY = instance._getCaretOffset();

				const offset = instance.get('offset');

				let offsetX = 0;
				let offsetY = 0;

				if (Array.isArray(offset)) {
					offsetX = offset[0];
					offsetY = offset[1];
				}
				else if (Lang.isNumber(offset)) {
					offsetY = offset;
				}

				const acOffset = instance._getACPositionOffset();

				xy[0] += caretXY.x + offsetX + acOffset[0];
				xy[1] += caretXY.y + offsetY + acOffset[1];

				instance.get('boundingBox').setXY(xy);
			},

			_afterACVisibleChange(event) {
				const instance = this;

				if (event.newVal) {
					instance._adjustACPosition();
				}

				instance._uiSetVisible(event.newVal);
			},

			_bindUIACIBase() {
				const instance = this;

				instance.on('query', instance._onACQuery, instance);

				instance.after(
					'visibleChange',
					instance._afterACVisibleChange,
					instance
				);
			},

			_defSelectFn(event) {
				const instance = this;

				const tplReplace = instance.get('tplReplace');

				let text = event.result.text;

				const mentionsResult = document.getElementById(
					'_com_liferay_mentions_web_portlet_MentionsPortlet_mentionsResult'
				);

				if (tplReplace) {
					text = Lang.sub(tplReplace, event.result.raw);
				}

				mentionsResult.style.display = 'none';

				instance._inputNode.focus();

				instance._updateValue(text);

				instance._ariaSay('item_selected', {
					item: event.result.text,
				});

				instance.hide();
			},

			_getRegExp() {
				const instance = this;

				let regExp = instance.get('regExp');

				if (Lang.isString(regExp)) {
					const triggersExpr =
						'[' + instance._getTriggers().join('|') + ']';

					regExp = new RegExp(
						regExp.replace(REGEX_TRIGGER, triggersExpr)
					);
				}

				return regExp;
			},

			_getTriggers() {
				const instance = this;

				if (!instance._triggers) {
					const triggers = [];

					instance.get(STR_TRIGGER).forEach((item) => {
						triggers.push(Lang.isString(item) ? item : item.term);
					});

					instance._triggers = triggers;
				}

				return instance._triggers;
			},

			_keyDown() {
				const instance = this;

				if (instance.get(STR_VISIBLE)) {
					instance._activateNextItem();
				}
			},

			_onACQuery(event) {
				const instance = this;

				const input = instance._getQuery(event.query);

				if (input) {
					instance._setTriggerConfig(input[0]);

					event.query = input.substring(1);
				}
				else {
					event.preventDefault();

					if (instance.get(STR_VISIBLE)) {
						instance.hide();
					}
				}
			},

			_processKeyUp(query) {
				const instance = this;

				if (query) {
					instance._setTriggerConfig(query[0]);

					query = query.substring(1);

					instance.sendRequest(query);
				}
				else if (instance.get(STR_VISIBLE)) {
					instance.hide();
				}
			},

			_setTriggerConfig(trigger) {
				const instance = this;

				if (trigger !== instance._trigger) {
					const triggers = instance._getTriggers();

					const triggerConfig = instance.get(STR_TRIGGER)[
						triggers.indexOf(trigger)
					];

					instance.setAttrs({
						...instance._triggerConfigDefaults,
						...triggerConfig,
					});

					instance._trigger = trigger;
				}
			},

			_syncUIPosAlign: Lang.emptyFn,

			_validateOffset(value) {
				return Array.isArray(value) || Lang.isNumber(value);
			},

			destructor() {
				const instance = this;

				new A.EventHandle(instance._eventHandles).detach();
			},

			initializer() {
				const instance = this;

				instance
					.get('boundingBox')
					.addClass('lfr-autocomplete-input-list');

				instance.set(
					'resultFormatter',
					A.bind('_acResultFormatter', instance)
				);

				instance._bindUIACIBase();

				const autocompleteAttrs = Object.keys(
					A.AutoComplete.ATTRS
				).filter((item) => {
					return item !== 'value';
				});

				instance._triggerConfigDefaults = TRIGGER_CONFIG_DEFAULTS;

				// eslint-disable-next-line prefer-object-spread
				Object.assign(
					{},
					instance._triggerConfigDefaults,
					instance.getAttrs(),
					false,
					autocompleteAttrs
				);
			},
		};

		Liferay.AutoCompleteInputBase = AutoCompleteInputBase;
	},
	'',
	{
		requires: [
			'aui-base',
			'autocomplete',
			'autocomplete-filters',
			'autocomplete-highlighters',
		],
	}
);
