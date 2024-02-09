/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-calendar-simple-color-picker',
	(A) => {
		const AArray = A.Array;
		const KeyMap = A.Event.KeyMap;
		const Lang = A.Lang;

		const STR_BLANK = '';

		const STR_DOT = '.';

		const getClassName = A.getClassName;

		const CSS_SIMPLE_COLOR_PICKER_ITEM = getClassName(
			'simple-color-picker',
			'item'
		);

		const CSS_SIMPLE_COLOR_PICKER_ITEM_SELECTED = getClassName(
			'simple-color-picker',
			'item',
			'selected'
		);

		const TPL_COLOR_ALERT =
			'<span aria-live="assertive" class="sr-only" role="alert"></span>';

		const TPL_SIMPLE_COLOR_PICKER_ITEM = new A.Template(
			'<tpl for="pallete">',
			'<button aria-label="{.}" class="',
			CSS_SIMPLE_COLOR_PICKER_ITEM,
			'" style="background-color: {.}',
			'; border-color:',
			'{.};',
			'" role="radio"></button>',
			'</tpl>'
		);

		const SimpleColorPicker = A.Component.create({
			ATTRS: {
				color: {
					setter(val) {
						return val.toUpperCase();
					},
					validator: Lang.isString,
					value: STR_BLANK,
				},

				host: {
					value: null,
				},

				pallete: {
					setter(val) {
						return AArray.invoke(val, 'toUpperCase');
					},
					validator: Lang.isArray,
					value: [
						'#d96666',
						'#e67399',
						'#b373b3',
						'#8c66d9',
						'#668cb3',
						'#668cd9',
						'#59bfb3',
						'#65ad89',
						'#4cb052',
						'#8cbf40',
						'#bfbf4d',
						'#e0c240',
						'#f2a640',
						'#e6804d',
						'#be9494',
						'#a992a9',
						'#8997a5',
						'#94a2be',
						'#85aaa5',
						'#a7a77d',
						'#c4a883',
						'#c7561e',
						'#b5515d',
						'#c244ab',
					],
				},

				trigger: {
					value: null,
				},
			},

			NAME: 'simple-color-picker',

			UI_ATTRS: ['color', 'pallete'],

			prototype: {
				_focusItem(index) {
					const instance = this;

					const items = instance.items;

					const size = items.size();

					if (index !== undefined) {
						index = (index + size) % size;

						const item = items.item(index);

						item.getDOMNode().focus();
					}
				},

				_onClickColor(event) {
					const instance = this;

					const pallete = instance.get('pallete');

					const color =
						pallete[instance.items.indexOf(event.currentTarget)];

					instance.set('color', color);

					instance.colorAlert.setContent(
						Lang.sub(Liferay.Language.get('color-x-selected'), [
							color,
						])
					);
				},

				_onKeyDownColor(event) {
					const instance = this;

					const items = instance.items;

					const currentIndex = items.indexOf(event.currentTarget);

					const {keyCode} = event;

					if (keyCode === KeyMap.ESC) {
						event.preventDefault();
						event.stopPropagation();

						const trigger = instance.trigger;

						if (trigger) {
							trigger.focus();
						}
					}
					else if (
						keyCode === KeyMap.DOWN ||
						keyCode === KeyMap.RIGHT
					) {
						event.preventDefault();

						instance._focusItem(currentIndex + 1);
					}
					else if (
						keyCode === KeyMap.UP ||
						keyCode === KeyMap.LEFT
					) {
						event.preventDefault();

						instance._focusItem(currentIndex - 1);
					}
					else if (
						keyCode === KeyMap.SPACE ||
						keyCode === KeyMap.ENTER
					) {
						event.preventDefault();
						event.stopPropagation();

						instance._onClickColor(event);
					}
				},

				_renderColorAlert() {
					const instance = this;

					instance.colorAlert = A.Node.create(TPL_COLOR_ALERT);

					const contentBox = instance.get('contentBox');

					contentBox.prepend(instance.colorAlert);
				},

				_renderPallete() {
					const instance = this;

					instance.items = A.NodeList.create(
						TPL_SIMPLE_COLOR_PICKER_ITEM.parse({
							pallete: instance.get('pallete'),
						})
					);

					const contentBox = instance.get('contentBox');

					contentBox.setAttribute('role', 'radiogroup');

					contentBox.setContent(instance.items);
				},

				_uiSetColor(val) {
					const instance = this;

					const pallete = instance.get('pallete');

					instance.items.removeClass(
						CSS_SIMPLE_COLOR_PICKER_ITEM_SELECTED
					);

					instance.items.setAttribute('aria-checked', 'false');

					const newNode = instance.items.item(pallete.indexOf(val));

					if (newNode) {
						newNode.addClass(CSS_SIMPLE_COLOR_PICKER_ITEM_SELECTED);
						newNode.setAttribute('aria-checked', 'true');
					}

					const contentBox = instance.get('contentBox');

					contentBox.setAttribute(
						'aria-label',
						Lang.sub(
							Liferay.Language.get(
								'color-picker.-color-selected-x.-use-arrow-keys-to-move-to-different-colors.-press-enter-or-space-to-select-a-color.-press-escape-to-leave-the-color-picker'
							),
							[val]
						)
					);
				},

				_uiSetPallete() {
					const instance = this;

					if (instance.get('rendered')) {
						instance._renderPallete();
					}
				},

				bindUI() {
					const instance = this;

					const contentBox = instance.get('contentBox');

					contentBox.delegate(
						'click',
						instance._onClickColor,
						STR_DOT + CSS_SIMPLE_COLOR_PICKER_ITEM,
						instance
					);
					contentBox.delegate(
						'keydown',
						instance._onKeyDownColor,
						STR_DOT + CSS_SIMPLE_COLOR_PICKER_ITEM,
						instance
					);
				},

				focus(trigger) {
					const instance = this;

					instance.trigger = trigger;

					instance.items.first().focus();
				},

				renderUI() {
					const instance = this;

					instance._renderPallete();

					instance._renderColorAlert();
				},
			},
		});

		Liferay.SimpleColorPicker = SimpleColorPicker;
	},
	'',
	{
		requires: ['aui-base', 'aui-template-deprecated'],
	}
);
