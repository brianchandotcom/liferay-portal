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
 * The Autocomplete Input Caretindex Component.
 *
 * @deprecated since 7.2, unused
 * @module liferay-autocomplete-input-caretindex
 */

AUI.add(
	'liferay-autocomplete-input-caretindex',
	A => {
		var STR_INPUT_NODE = 'inputNode';

		var AutcompleteInputCaretIndex = function() {};

		AutcompleteInputCaretIndex.prototype = {
			_getCaretIndex(node) {
				var instance = this;

				node = node || instance.get(STR_INPUT_NODE);

				var input = node.getDOM();

				return {
					end: input.selectionStart,
					start: input.selectionStart
				};
			},

			_setCaretIndex(node, caretIndex) {
				var instance = this;

				node = node || instance.get(STR_INPUT_NODE);

				var input = node.getDOM();

				Liferay.Util.focusFormField(input);

				input.setSelectionRange(caretIndex, caretIndex);
			}
		};

		A.Base.mix(Liferay.AutoCompleteTextarea, [AutcompleteInputCaretIndex]);
	},
	'',
	{
		requires: ['liferay-autocomplete-textarea']
	}
);
