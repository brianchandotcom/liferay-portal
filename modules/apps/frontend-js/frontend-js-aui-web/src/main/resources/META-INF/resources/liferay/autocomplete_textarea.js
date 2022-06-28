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

/**
 * The Autocomplete Textarea Component.
 *
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 * @module liferay-autocomplete-textarea
 */

AUI.add(
	'liferay-autocomplete-textarea',
	(A) => {
		const KeyMap = A.Event.KeyMap;
		const Lang = A.Lang;

		const KEY_DOWN = KeyMap.DOWN;

		const KEY_LIST = [
			KEY_DOWN,
			KeyMap.LEFT,
			KeyMap.RIGHT,
			KeyMap.UP,
		].join();

		const STR_INPUT_NODE = 'inputNode';

		const STR_SPACE = ' ';

		const AutoCompleteTextarea = function () {};

		AutoCompleteTextarea.prototype = {
			_bindUIACTextarea() {
				const instance = this;

				const inputNode = instance.get(STR_INPUT_NODE);

				instance._eventHandles = [
					inputNode.on(
						'key',
						A.bind('_onKeyUp', instance),
						'up:' + KEY_LIST
					),
				];
			},

			_getACPositionBase() {
				const instance = this;

				return instance.get(STR_INPUT_NODE).getXY();
			},

			_getACPositionOffset() {
				const instance = this;

				const inputNode = instance.get(STR_INPUT_NODE);

				return [0, Lang.toInt(inputNode.getStyle('fontSize'))];
			},

			_getACVal() {
				const instance = this;

				return instance.get(STR_INPUT_NODE).val();
			},

			_getPrevTrigger(content, position) {
				const instance = this;

				let result = -1;

				let trigger = null;

				const triggers = instance._getTriggers();

				for (let i = position; i >= 0; --i) {
					const triggerIndex = triggers.indexOf(content[i]);

					if (triggerIndex >= 0) {
						result = i;
						trigger = triggers[triggerIndex];

						break;
					}
				}

				return {
					index: result,
					value: trigger,
				};
			},

			_getQuery(val) {
				const instance = this;

				let result = null;

				const caretIndex = instance._getCaretIndex();

				if (caretIndex) {
					val = val.substring(0, caretIndex.start);

					instance._getTriggers().forEach((item) => {
						const lastTriggerIndex = val.lastIndexOf(item);

						if (lastTriggerIndex >= 0) {
							val = val.substring(lastTriggerIndex);

							const regExp = instance._getRegExp();

							const res = regExp.exec(val);

							if (
								res &&
								res.index + res[1].length + item.length ===
									val.length &&
								(!result || val.length < result.length)
							) {
								result = val;
							}
						}
					});
				}

				return result;
			},

			_onKeyUp(event) {
				const instance = this;

				const acVisible = instance.get('visible');

				if (!acVisible || event.isKeyInSet('left', 'right')) {
					const inputNode = instance.get(STR_INPUT_NODE);

					const query = instance._getQuery(inputNode.val());

					instance._processKeyUp(query);
				}
			},

			_setACVal(text) {
				const instance = this;

				const inputNode = instance.get(STR_INPUT_NODE);

				inputNode.val(text);
			},

			_updateValue(text) {
				const instance = this;

				const caretIndex = instance._getCaretIndex();

				if (caretIndex) {
					let val = instance._getACVal();

					if (val) {
						const lastTrigger = instance._getPrevTrigger(
							val,
							caretIndex.start
						);

						const lastTriggerIndex = lastTrigger.index;

						if (lastTriggerIndex >= 0) {
							const prefix = val.substring(0, lastTriggerIndex);

							val = val.substring(lastTriggerIndex);

							const regExp = instance._getRegExp();

							const res = regExp.exec(val);

							if (res) {
								const restText = val.substring(
									res[1].length + 1
								);

								let spaceAdded = 1;

								if (
									!restText.length ||
									restText[0] !== STR_SPACE
								) {
									text += STR_SPACE;

									spaceAdded = 0;
								}

								const resultText =
									prefix + lastTrigger.value + text;

								const resultEndPos =
									resultText.length + spaceAdded;

								instance._setACVal(resultText + restText);

								instance._setCaretIndex(
									instance.get(STR_INPUT_NODE),
									resultEndPos
								);
							}
						}
					}
				}
			},

			destructor() {
				const instance = this;

				if (instance._inputMirror) {
					instance._inputMirror.remove();
				}
			},

			initializer() {
				const instance = this;

				instance._bindUIACTextarea();
			},
		};

		Liferay.AutoCompleteTextarea = A.Base.create(
			'liferayautocompletetextarea',
			A.AutoComplete,
			[Liferay.AutoCompleteInputBase, AutoCompleteTextarea],
			{},
			{
				CSS_PREFIX: A.ClassNameManager.getClassName('aclist'),
			}
		);
	},
	'',
	{
		requires: ['liferay-autocomplete-input'],
	}
);
