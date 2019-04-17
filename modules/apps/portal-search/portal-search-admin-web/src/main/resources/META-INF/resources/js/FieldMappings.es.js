import 'clay-button';
import 'metal';
import 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

import templates from './FieldMappings.soy';

class FieldMappings extends PortletBase {

	attached() {
		AUI().use(
			'aui-ace-editor',
			A => {
				new A.AceEditor(
					{
						boundingBox: this.refs.wrapper,
						highlightActiveLine: false,
						mode: 'json',
						readOnly: 'true',
						tabSize: 4,
						value: this.fieldMappingsJson
					}
				).render();
			}
		);
	}

	_decreaseFontSize() {
		const aceEditorElement = this._getAceEditorElement();

		aceEditorElement.style.fontSize = this._getAceEditorFontSize() - 2 + 'px';
	}

	_increaseFontSize() {
		const aceEditorElement = this._getAceEditorElement();

		aceEditorElement.style.fontSize = this._getAceEditorFontSize() + 2 + 'px';
	}

	_getAceEditorElement() {
		return document.querySelector('.ace_editor');
	}

	_getAceEditorFontSize() {
		return parseInt(
			window.getComputedStyle(
				this._getAceEditorElement(),
				null
			).getPropertyValue('font-size'),
			10
		);
	}

	_selectText(event) {
		const copyTextArea = document.querySelector('.ace_text-input');

		copyTextArea.focus();
		copyTextArea.select();

		event.currentTarget.dataset.title = Liferay.Language.get('copied');

		setTimeout(
			function() {
				document.execCommand('copy');
			},
			0
		);
	}

	_switchTheme(event) {
		const richEditorElement = document.querySelector('#richEditor');

		richEditorElement.classList.toggle('ace_dark');

		if (Liferay.Language.get('dark-theme') == event.currentTarget.dataset.title) {
			event.currentTarget.dataset.title = Liferay.Language.get('light-theme');
		}
		else {
			event.currentTarget.dataset.title = Liferay.Language.get('dark-theme');
		}
	}
}

FieldMappings.STATE = {
	fieldMappingsJson: Config.string().required()
};

Soy.register(FieldMappings, templates);

export {FieldMappings};
export default FieldMappings;