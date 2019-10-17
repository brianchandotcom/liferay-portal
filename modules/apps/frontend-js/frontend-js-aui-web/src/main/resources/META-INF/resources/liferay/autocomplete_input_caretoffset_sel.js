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
 * The Autocomplete Input Caretoffset Sel Component.
 *
 * @deprecated since 7.2, unused
 * @module liferay-autocomplete-input-caretoffset-sel
 */

AUI.add(
	'liferay-autocomplete-input-caretoffset-sel',
	A => {
		var Lang = A.Lang;

		var DOC = A.config.doc;

		var AutcompleteInputCaretOffset = function() {};

		AutcompleteInputCaretOffset.prototype = {
			_getCaretOffset(node) {
				var instance = this;

				node = node || instance.get('inputNode');

				node.focus();

				var range = DOC.selection.createRange();

				var xy = node.getXY();

				return {
					x: range.boundingLeft - xy[0],
					y:
						Lang.toInt(range.boundingTop) -
						xy[1] +
						node.get('scrollTop') +
						DOC.documentElement.scrollTop
				};
			}
		};

		A.Base.mix(Liferay.AutoCompleteTextarea, [AutcompleteInputCaretOffset]);
	},
	'',
	{
		requires: ['liferay-autocomplete-textarea']
	}
);
