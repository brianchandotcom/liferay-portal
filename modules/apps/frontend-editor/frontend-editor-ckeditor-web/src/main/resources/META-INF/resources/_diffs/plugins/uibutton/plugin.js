/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const pluginName = 'uibutton';

	if (CKEDITOR.plugins.get(pluginName)) {
		return;
	}

	CKEDITOR.ui.balloonToolbarButton = CKEDITOR.tools.createClass({
		// eslint-disable-next-line
		$: function (definition) {
			CKEDITOR.tools.extend(this, definition, {
				balloonToolbar: null,
				click: definition.click,
				command: definition.command,
				cssClass: definition.cssClass,
				icon: definition.icon,
				label: definition.label,
				modes: {wysiwyg: 1},
				title: definition.title,
			});
		},

		base: CKEDITOR.event,

		proto: {
			render(editor, output) {
				const id = CKEDITOR.tools.getNextId();

				this._id = id;

				const button = this;

				this._editor = editor;

				const instance = {
					button,
					execute() {
						if (typeof button.click === 'function') {
							button.click();
						}
						else if (button.command) {
							editor.execCommand(button.command);
						}
					},
					id,
				};

				const template = new CKEDITOR.template((data) => {
					const output = [
						`<a class="cke_button cke_button__{name} {cssClass}" hidefocus="true" id="{id}" onclick="CKEDITOR.tools.callFunction({clickFn},event,this);return false;" role="button" tabindex="-1" title="{title}">`,
					];

					if (data.icon) {
						output.push(
							'<span class="cke_button_icon cke_button__{icon}_icon">&nbsp;</span>'
						);
					}

					if (data.label) {
						output.push(
							'<span class="cke_button_label cke_button__{label}_label" style="display:inline-block;">{label}</span>'
						);
					}

					output.push('</a>');

					return output.join('');
				});

				const clickFn = CKEDITOR.tools.addFunction(() => {
					instance.execute();
				});

				const params = {
					clickFn,
					command: this.command ? this.command : '',
					cssClass: this.cssClass ? this.cssClass : '',
					icon: this.icon,
					id,
					label: this.label,
					title: this.title || '',
				};

				template.output(params, output);

				return instance;
			},
		},
	});

	CKEDITOR.ui.balloonToolbarButton.handler = {
		create(definition) {
			return new CKEDITOR.ui.balloonToolbarButton(definition);
		},
	};

	CKEDITOR.UI_BALLOON_TOOLBAR_BUTTON = 'balloonToolbarButton';

	CKEDITOR.tools.extend(CKEDITOR.ui.prototype, {
		addBalloonToolbarButton(name, definition) {
			this.add(name, CKEDITOR.UI_BALLOON_TOOLBAR_BUTTON, definition);
		},
	});

	CKEDITOR.plugins.add(pluginName, {
		beforeInit(editor) {
			editor.ui.addHandler(
				CKEDITOR.UI_BALLOON_TOOLBAR_BUTTON,
				CKEDITOR.ui.balloonToolbarButton.handler
			);
		},
	});
})();
