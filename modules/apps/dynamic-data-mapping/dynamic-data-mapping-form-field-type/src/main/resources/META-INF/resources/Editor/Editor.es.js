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

import '../FieldBase/FieldBase.es';

import './EditorRegister.soy';

import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './Editor.soy';

class Editor extends Component {
	attached() {
		AUI().use('liferay-alloy-editor', A => {
			this.A = A;

			this._createEditor(A);
		});
	}

	disposed() {
		if (this._alloyEditor) {
			this._alloyEditor.destroy();
		}
	}

	shouldUpdate() {
		return false;
	}

	syncEditingLanguageId() {
		const {value} = this;

		this.syncValue(value, null, true);
	}

	syncValue(value, prevVal, force = false) {
		const {_alloyEditor} = this;

		if (_alloyEditor && _alloyEditor.getHTML() !== value) {
			const nativeEditor = _alloyEditor.getNativeEditor();
			const {hasFocus} = nativeEditor.focusManager;

			if (force || !hasFocus) {
				nativeEditor.setData(value);
			}
		}
	}

	_createEditor(A) {
		const {name, readOnly, value} = this;
		const editorNode = this.element.querySelector('.alloy-editor');

		if (readOnly) {
			return;
		}

		editorNode.innerHTML = value;

		window[name] = {};

		this._alloyEditor = new A.LiferayAlloyEditor({
			contents: value,
			editorConfig: {
				extraPlugins: 'ae_placeholder,ae_selectionregion,ae_uicore',
				removePlugins:
					'contextmenu,elementspath,floatingspace,image,link,liststyle,magiclineresize,table,tabletools,toolbar',
				spritemap: `${themeDisplay.getPathThemeImages()}/lexicon/icons.svg`,
				srcNode: A.one(editorNode),
				toolbars: {
					add: {
						buttons: ['hline', 'table']
					},
					styles: {
						selections: AlloyEditor.Selections,
						tabIndex: 1
					}
				}
			},
			namespace: name,
			onChangeMethod: this._onChangeEditor.bind(this),
			plugins: [],
			textMode: false
		}).render();

		this._alloyEditor
			.getNativeEditor()
			.on('actionPerformed', this._onActionPerformed.bind(this));
	}

	_onActionPerformed(event) {
		const {
			data: {props}
		} = event;

		if (!props.command) {
			this._onChangeEditor(event);
		}
	}

	_onChangeEditor(event) {
		this.emit('fieldEdited', {
			fieldInstance: this,
			originalEvent: event,
			value: this._alloyEditor.getHTML()
		});
	}
}

Editor.STATE = {
	/**
	 * @default undefined
	 * @instance
	 * @memberof Editor
	 * @type {?string}
	 */

	editingLanguageId: Config.string(),

	/**
	 * @default false
	 * @instance
	 * @memberof Editor
	 * @type {?bool}
	 */

	evaluable: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Editor
	 * @type {?(string|undefined)}
	 */

	fieldName: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Editor
	 * @type {?(string|undefined)}
	 */

	id: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Editor
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Editor
	 * @type {?(string|undefined)}
	 */

	name: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Editor
	 * @type {?(string|undefined)}
	 */

	placeholder: Config.string(),

	/**
	 * @default false
	 * @instance
	 * @memberof Editor
	 * @type {?bool}
	 */

	readOnly: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FieldBase
	 * @type {?(bool|undefined)}
	 */

	repeatable: Config.bool(),

	/**
	 * @default false
	 * @instance
	 * @memberof Editor
	 * @type {?(bool|undefined)}
	 */

	required: Config.bool().value(false),

	/**
	 * @default true
	 * @instance
	 * @memberof Editor
	 * @type {?(bool|undefined)}
	 */

	showLabel: Config.bool().value(true),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Editor
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Editor
	 * @type {?(string|undefined)}
	 */

	tip: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	tooltip: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Text
	 * @type {?(string|undefined)}
	 */

	type: Config.string().value('editor'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Editor
	 * @type {?(string|undefined)}
	 */

	value: Config.string().value('')
};

Soy.register(Editor, templates);

export default Editor;
