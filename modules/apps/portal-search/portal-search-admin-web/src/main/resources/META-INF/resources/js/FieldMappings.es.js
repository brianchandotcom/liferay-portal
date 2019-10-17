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

import 'clay-button';

import 'metal';

import 'metal-component';
import {PortletBase} from 'frontend-js-web';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './FieldMappings.soy';

class FieldMappings extends PortletBase {
	attached() {
		AUI().use('aui-ace-editor', A => {
			new A.AceEditor({
				boundingBox: this.refs.wrapper,
				highlightActiveLine: false,
				mode: 'json',
				readOnly: 'true',
				tabSize: 4,
				value: this.fieldMappingsJson,
				width: '100%'
			}).render();
		});
	}

	_decreaseFontSize() {
		const aceEditorElement = this._getAceEditorElement();

		aceEditorElement.style.fontSize =
			this._getAceEditorFontSize() - 2 + 'px';
	}

	_increaseFontSize() {
		const aceEditorElement = this._getAceEditorElement();

		aceEditorElement.style.fontSize =
			this._getAceEditorFontSize() + 2 + 'px';
	}

	_getAceEditorElement() {
		return document.querySelector('.ace_editor');
	}

	_getAceEditorFontSize() {
		return parseInt(
			window
				.getComputedStyle(this._getAceEditorElement(), null)
				.getPropertyValue('font-size'),
			10
		);
	}

	_selectText(event) {
		const copyTextArea = document.querySelector('.ace_text-input');

		copyTextArea.focus();
		copyTextArea.select();

		event.currentTarget.dataset.title = Liferay.Language.get('copied');

		setTimeout(() => {
			document.execCommand('copy');
		}, 0);
	}

	_switchTheme(event) {
		const richEditorElement = document.querySelector('#richEditor');

		richEditorElement.classList.toggle('ace_dark');

		if (
			Liferay.Language.get('dark-theme') ==
			event.currentTarget.dataset.title
		) {
			event.currentTarget.dataset.title = Liferay.Language.get(
				'light-theme'
			);
		} else {
			event.currentTarget.dataset.title = Liferay.Language.get(
				'dark-theme'
			);
		}
	}
}

FieldMappings.STATE = {
	fieldMappingsJson: Config.string().required()
};

Soy.register(FieldMappings, templates);

export {FieldMappings};
export default FieldMappings;
