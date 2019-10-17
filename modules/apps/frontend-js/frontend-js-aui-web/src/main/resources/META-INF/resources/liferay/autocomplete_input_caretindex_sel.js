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
 * The Autocomplete Input Caretindex Sel Component.
 *
 * @deprecated since 7.2, unused
 * @module liferay-autocomplete-input-caretindex-sel
 */

AUI.add(
	'liferay-autocomplete-input-caretindex-sel',
	A => {
		var DOC = A.config.doc;

		var REGEX_NEW_LINE = /\r\n/g;

		var STR_CHARACTER = 'character';

		var STR_END_TO_END = 'EndToEnd';

		var STR_INPUT_NODE = 'inputNode';

		var STR_NEW_LINE = '\n';

		var AutcompleteInputCaretIndex = function() {};

		AutcompleteInputCaretIndex.prototype = {
			_getCaretIndex(node) {
				var instance = this;

				node = node || instance.get(STR_INPUT_NODE);

				var end = 0;
				var start = 0;

				var range = DOC.selection.createRange();

				var inputEl = node.getDOM();

				if (range && range.parentElement() === inputEl) {
					var value = inputEl.value;

					var normalizedValue = value.replace(
						REGEX_NEW_LINE,
						STR_NEW_LINE
					);

					var textInputRange = inputEl.createTextRange();

					textInputRange.moveToBookmark(range.getBookmark());

					var endRange = inputEl.createTextRange();

					endRange.collapse(false);

					var length = value.length;

					if (
						textInputRange.compareEndPoints(
							'StartToEnd',
							endRange
						) > -1
					) {
						start = end = length;
					} else {
						start = -textInputRange.moveStart(
							STR_CHARACTER,
							-length
						);
						start +=
							normalizedValue.slice(0, start).split(STR_NEW_LINE)
								.length - 1;

						if (
							textInputRange.compareEndPoints(
								STR_END_TO_END,
								endRange
							) > -1
						) {
							end = length;
						} else {
							end = -textInputRange.moveEnd(
								STR_CHARACTER,
								-length
							);
							end +=
								normalizedValue
									.slice(0, end)
									.split(STR_NEW_LINE).length - 1;
						}
					}
				}

				return {
					end,
					start
				};
			},

			_setCaretIndex(node, cursorIndex) {
				var instance = this;

				node = node || instance.get(STR_INPUT_NODE);

				var input = node.getDOM();

				if (input.createTextRange) {
					var val = node.val().substring(0, cursorIndex);

					var count = 0;

					var regExpNewLine = /\r\n/g;

					while (regExpNewLine.exec(val) !== null) {
						count++;
					}

					var range = input.createTextRange();

					range.move(STR_CHARACTER, cursorIndex - count);

					range.select();
				}
			}
		};

		A.Base.mix(Liferay.AutoCompleteTextarea, [AutcompleteInputCaretIndex]);
	},
	'',
	{
		requires: ['liferay-autocomplete-textarea']
	}
);
