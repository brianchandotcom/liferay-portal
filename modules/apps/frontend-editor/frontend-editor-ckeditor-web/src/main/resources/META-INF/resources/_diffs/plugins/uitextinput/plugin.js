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

(function () {
	const pluginName = 'uitextinput';

	if (CKEDITOR.plugins.get(pluginName)) {
		return;
	}

	const templateHTML =
		'<input' +
		' class="ui-textinput"' +
		' id="{id}"' +
		' onchange="CKEDITOR.tools.callFunction({changeFn},this);return false;"' +
		' onkeyup="CKEDITOR.tools.callFunction({keyupFn},event,this);"' +
		' placeholder="{placeholder}"' +
		' type="text"' +
		' value="{value}"' +
		'>';

	const template = CKEDITOR.addTemplate(
		'balloonToolbarTextInput',
		templateHTML
	);

	CKEDITOR.ui.balloonToolbarTextInput = CKEDITOR.tools.createClass({
		// eslint-disable-next-line
		$: function (definition) {
			CKEDITOR.tools.extend(this, definition, {
				modes: {wysiwyg: 1},
				name: definition.name,
				placeholder: definition.placeholder,
				value: definition.value,
			});
		},

		base: CKEDITOR.event,

		proto: {
			clear() {
				this.value = '';

				const inputElement = this.getInputElement();

				if (inputElement) {
					inputElement.setAttribute('value', this.value);
				}
			},

			getInputElement() {
				return this._editor.document.getById(this._id);
			},

			render(editor, output) {
				const id = CKEDITOR.tools.getNextId();

				this._id = id;

				const input = this;

				this._editor = editor;

				const instance = {
					input,
				};

				const changeFn = CKEDITOR.tools.addFunction((element) => {
					input.value = element.value;
				});

				instance.changeFn = changeFn;

				const keyupFn = CKEDITOR.tools.addFunction((event, element) => {
					if (event.keyCode === 13) {
						input.value = element.value;

						input.fire(
							'change',
							{
								value: element.value,
							},
							input._editor
						);
					}
					if (event.keyCode === 27) {
						input.fire('cancel');
					}
				});

				const params = {
					changeFn,
					id,
					keyupFn,
					name: this.name,
					placeholder: this.placeholder || '',
					value: this.value || '',
				};

				template.output(params, output);

				return instance;
			},

			setValue(value) {
				if (value) {
					this.value = value;

					const inputElement = this.getInputElement();

					if (inputElement) {
						inputElement.setAttribute('value', value);
					}
				}
			},
		},
	});

	CKEDITOR.plugins.add(pluginName, {});
})();
