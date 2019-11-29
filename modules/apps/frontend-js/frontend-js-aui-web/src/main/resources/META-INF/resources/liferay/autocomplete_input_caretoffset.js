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
 * The Autocomplete Input Caretoffset Component.
 *
 * @deprecated since 7.2, unused
 * @module liferay-autocomplete-input-caretoffset
 */

AUI.add(
	'liferay-autocomplete-input-caretoffset',
	A => {
		var ANode = A.Node;

		var MIRROR_STYLES = [
			'boxSizing',
			'fontFamily',
			'fontSize',
			'fontStyle',
			'fontVariant',
			'fontWeight',
			'height',
			'letterSpacing',
			'lineHeight',
			'maxHeight',
			'minHeight',
			'padding',
			'textDecoration',
			'textIndent',
			'textTransform',
			'width',
			'wordSpacing'
		];

		var STR_INPUT_NODE = 'inputNode';

		var AutcompleteInputCaretOffset = function() {};

		AutcompleteInputCaretOffset.prototype = {
			_applyMirrorContent() {
				var instance = this;

				var input = instance.get(STR_INPUT_NODE);

				var value = input.val();

				var caretIndex = instance._getCaretIndex().start;

				if (caretIndex === value.length) {
					value += instance.TPL_CARET;
				}
				else {
					if (instance.get('caretAtTerm')) {
						caretIndex =
							instance._getPrevTrigger(value, caretIndex).index +
							1;
					}

					value =
						value.substring(0, caretIndex) +
						instance.TPL_CARET +
						value.substring(caretIndex + 1);
				}

				instance._inputMirror.html(value);

				return value;
			},

			_applyMirrorStyles() {
				var instance = this;

				var inputNode = instance.get(STR_INPUT_NODE);

				var inputMirror = instance._inputMirror;

				MIRROR_STYLES.forEach(item => {
					inputMirror.setStyle(item, inputNode.getStyle(item));
				});
			},

			_createInputMirror() {
				var instance = this;

				if (!instance._inputMirror) {
					var inputMirror = ANode.create(instance.TPL_INPUT_MIRROR);

					A.getBody().append(inputMirror);

					instance._inputMirror = inputMirror;
				}
			},

			_getCaretOffset(node) {
				var instance = this;

				instance._createInputMirror();

				instance._applyMirrorStyles();
				instance._applyMirrorContent();

				node = node || instance.get(STR_INPUT_NODE);

				var inputEl = node.getDOM();

				var scrollLeft = inputEl.scrollLeft;
				var scrollTop = inputEl.scrollTop;

				var inputCaretEl = instance._inputMirror
					.one('.input-caret')
					.getDOM();

				return {
					x: inputCaretEl.offsetLeft + scrollLeft,
					y: inputCaretEl.offsetTop - scrollTop
				};
			},

			TPL_CARET: '<span class="input-caret">&nbsp</span>',

			TPL_INPUT_MIRROR:
				'<div class="liferay-autocomplete-input-mirror"></div>'
		};

		A.Base.mix(Liferay.AutoCompleteTextarea, [AutcompleteInputCaretOffset]);
	},
	'',
	{
		requires: ['liferay-autocomplete-textarea']
	}
);
