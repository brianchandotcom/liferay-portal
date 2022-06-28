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
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 * @module liferay-autocomplete-input-caretoffset
 */

AUI.add(
	'liferay-autocomplete-input-caretoffset',
	(A) => {
		const ANode = A.Node;

		const MIRROR_STYLES = [
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
			'wordSpacing',
		];

		const STR_INPUT_NODE = 'inputNode';

		const AutcompleteInputCaretOffset = function () {};

		AutcompleteInputCaretOffset.prototype = {
			_applyMirrorContent() {
				const instance = this;

				const input = instance.get(STR_INPUT_NODE);

				let value = input.val();

				let caretIndex = instance._getCaretIndex().start;

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
				const instance = this;

				const inputNode = instance.get(STR_INPUT_NODE);

				const inputMirror = instance._inputMirror;

				MIRROR_STYLES.forEach((item) => {
					inputMirror.setStyle(item, inputNode.getStyle(item));
				});
			},

			_createInputMirror() {
				const instance = this;

				if (!instance._inputMirror) {
					const inputMirror = ANode.create(instance.TPL_INPUT_MIRROR);

					// eslint-disable-next-line @liferay/aui/no-get-body
					A.getBody().append(inputMirror);

					instance._inputMirror = inputMirror;
				}
			},

			_getCaretOffset(node) {
				const instance = this;

				instance._createInputMirror();

				instance._applyMirrorStyles();
				instance._applyMirrorContent();

				node = node || instance.get(STR_INPUT_NODE);

				const inputEl = node.getDOM();

				const scrollLeft = inputEl.scrollLeft;
				const scrollTop = inputEl.scrollTop;

				const inputCaretEl = instance._inputMirror
					.one('.input-caret')
					.getDOM();

				return {
					x: inputCaretEl.offsetLeft + scrollLeft,
					y: inputCaretEl.offsetTop - scrollTop,
				};
			},

			TPL_CARET: '<span class="input-caret">&nbsp</span>',

			TPL_INPUT_MIRROR:
				'<div class="liferay-autocomplete-input-mirror"></div>',
		};

		A.Base.mix(Liferay.AutoCompleteTextarea, [AutcompleteInputCaretOffset]);
	},
	'',
	{
		requires: ['liferay-autocomplete-textarea'],
	}
);
