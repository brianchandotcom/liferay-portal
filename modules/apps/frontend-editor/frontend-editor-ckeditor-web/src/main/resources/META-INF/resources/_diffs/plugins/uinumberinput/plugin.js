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
	const pluginName = 'uinumberinput';

	if (CKEDITOR.plugins.get(pluginName)) {
		return;
	}

	const template = new CKEDITOR.template((data) => {
		const output = ['<div class="ui-numberinput">'];

		if (data.label) {
			output.push('<label for="{id}">{label}</label>');
		}

		output.push(
			'<input' +
				' class="ui-numberinput"' +
				' id="{id}"' +
				' min="{min}"' +
				' max="{max}"' +
				' onchange="CKEDITOR.tools.callFunction({changeFn},event);return false;" ' +
				' step="{step}"' +
				' type="number"' +
				' value="{value}" />'
		);
		output.push('</div>');

		return output.join('');
	});

	CKEDITOR.ui.balloonToolbarNumberInput = CKEDITOR.tools.createClass({
		// eslint-disable-next-line
		$: function (definition) {
			CKEDITOR.tools.extend(this, definition, {
				change: definition.change || function () {},
				label: definition.label || '',
				max: definition.max,
				min: definition.min || 0,
				modes: {wysiwyg: 1},
				step: definition.step || 1,
				value: definition.value || 0,
			});
		},

		base: CKEDITOR.event,

		proto: {
			render(editor, output) {
				const id = CKEDITOR.tools.getNextId();

				this._id = id;

				const input = this;

				this._editor = editor;

				const instance = {
					input,
				};

				const changeFn = CKEDITOR.tools.addFunction((event) => {
					event.preventDefault();

					const value = event.target.valueAsNumber;

					input.value = value;

					input.change(value);

					input.fire(
						'change',
						{
							value,
						},
						input._editor
					);
				});

				const params = {
					changeFn,
					id,
					label: this.label,
					max: this.max,
					min: this.min,
					step: this.step,
					value: this.value,
				};

				template.output(params, output);

				this._element = this._editor.document.findOne(`#${id}`);

				return instance;
			},
		},
	});

	CKEDITOR.ui.balloonToolbarNumberInput.handler = {
		create(definition) {
			return new CKEDITOR.ui.balloonToolbarNumberInput(definition);
		},
	};

	CKEDITOR.UI_BALLOON_NUMBER_INPUT = 'balloonToolbarNumberInput';

	CKEDITOR.tools.extend(CKEDITOR.ui.prototype, {
		addBalloonToolbarNumberInput(name, definition) {
			this.add(name, CKEDITOR.UI_BALLOON_NUMBER_INPUT, definition);
		},
	});

	CKEDITOR.plugins.add(pluginName, {
		init(editor) {
			editor.ui.addHandler(
				CKEDITOR.UI_BALLOON_NUMBER_INPUT,
				CKEDITOR.ui.balloonToolbarNumberInput.handler
			);
		},
	});
})();
