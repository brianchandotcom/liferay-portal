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
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 * @module liferay-autocomplete-input-caretindex-sel
 */

AUI.add(
	'liferay-autocomplete-input-caretindex-sel',
	(A) => {
		const DOC = A.config.doc;

		const REGEX_NEW_LINE = /\r\n/g;

		const STR_CHARACTER = 'character';

		const STR_END_TO_END = 'EndToEnd';

		const STR_INPUT_NODE = 'inputNode';

		const STR_NEW_LINE = '\n';

		const AutcompleteInputCaretIndex = function () {};

		AutcompleteInputCaretIndex.prototype = {
			_getCaretIndex(node) {
				const instance = this;

				node = node || instance.get(STR_INPUT_NODE);

				let end = 0;
				let start = 0;

				const range = DOC.selection.createRange();

				const inputEl = node.getDOM();

				if (range && range.parentElement() === inputEl) {
					const value = inputEl.value;

					const normalizedValue = value.replace(
						REGEX_NEW_LINE,
						STR_NEW_LINE
					);

					const textInputRange = inputEl.createTextRange();

					textInputRange.moveToBookmark(range.getBookmark());

					const endRange = inputEl.createTextRange();

					endRange.collapse(false);

					const length = value.length;

					if (
						textInputRange.compareEndPoints(
							'StartToEnd',
							endRange
						) > -1
					) {
						start = end = length;
					}
					else {
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
						}
						else {
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
					start,
				};
			},

			_setCaretIndex(node, cursorIndex) {
				const instance = this;

				node = node || instance.get(STR_INPUT_NODE);

				const input = node.getDOM();

				if (input.createTextRange) {
					const val = node.val().substring(0, cursorIndex);

					let count = 0;

					const regExpNewLine = /\r\n/g;

					while (regExpNewLine.exec(val) !== null) {
						count++;
					}

					const range = input.createTextRange();

					range.move(STR_CHARACTER, cursorIndex - count);

					range.select();
				}
			},
		};

		A.Base.mix(Liferay.AutoCompleteTextarea, [AutcompleteInputCaretIndex]);
	},
	'',
	{
		requires: ['liferay-autocomplete-textarea'],
	}
);
