/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const pluginName = 'uiselect';

	if (CKEDITOR.plugins.get(pluginName)) {
		return;
	}

	const template = new CKEDITOR.template((data) => {
		const output = [];

		if (data.icon) {
			output.push(
				'<a class="cke_button" tabindex="-1" title="{title}" hidefocus="true" role="button">'
			);
			output.push(
				'<span class="cke_button_icon cke_button__{icon}_icon">&nbsp;</span>'
			);
		}

		output.push('<select class="');

		if (data.icon) {
			output.push('ui-select-wrapper ');
		}

		output.push(
			'ui-select" id="{id}" name="{name}" onchange="CKEDITOR.tools.callFunction({changeFn},event,this);return false;">'
		);

		if (data.items) {
			data.items.forEach((item) => {
				const value = item.value
					? item.value
					: item.label
					? item.label
					: '';

				output.push(
					`<option value="${String(value)}">${item.label}</option>`
				);
			});
		}

		output.push('</select>');

		if (data.icon) {
			output.push('</a>');
		}

		return output.join('');
	});

	CKEDITOR.ui.balloonToolbarSelect = CKEDITOR.tools.createClass({
		// eslint-disable-next-line
		$: function (definition) {
			const items = Array.isArray(definition.items)
				? definition.items
				: [{label: ''}];

			const value = items[0].value
				? items[0].value
				: items[0].label
				? items[0].label
				: '';

			CKEDITOR.tools.extend(this, definition, {
				icon: definition.icon,
				items,
				modes: {wysiwyg: 1},
				name: definition.name,
				title: definition.title,
				value,
			});
		},

		base: CKEDITOR.event,

		proto: {
			render(editor, output) {
				const id = CKEDITOR.tools.getNextId();

				this._id = id;

				const select = this;

				this._editor = editor;

				const instance = {
					execute(value) {
						this.select.value = value;
					},
					id,
					select,
				};

				const changeFn = CKEDITOR.tools.addFunction(function (
					event,
					element
				) {
					event.preventDefault();

					const option = element.options[element.selectedIndex];

					const value = option.value;

					instance.execute(value);

					select.fire(
						'change',
						{
							value,
						},
						this._editor
					);

					if (select.onChange) {
						select.onChange(value);
					}
				});

				instance.changeFn = changeFn;

				const params = {
					changeFn,
					icon: this.icon,
					id,
					items: this.items,
					name: this.name,
					title: this.title,
				};

				template.output(params, output);

				if (this.onRender) {
					this.onRender(instance);
				}

				return instance;
			},
		},
	});

	CKEDITOR.ui.balloonToolbarSelect.handler = {
		create(definition) {
			return new CKEDITOR.ui.balloonToolbarSelect(definition);
		},
	};

	CKEDITOR.UI_BALLOON_TOOLBAR_SELECT = 'balloonToolbarSelect';

	CKEDITOR.tools.extend(CKEDITOR.ui.prototype, {
		addBalloonToolbarSelect(name, definition) {
			this.add(name, CKEDITOR.UI_BALLOON_TOOLBAR_SELECT, definition);
		},
	});

	CKEDITOR.plugins.add(pluginName, {
		init(editor) {
			editor.ui.addHandler(
				CKEDITOR.UI_BALLOON_TOOLBAR_SELECT,
				CKEDITOR.ui.balloonToolbarSelect.handler
			);
		},
	});
})();
