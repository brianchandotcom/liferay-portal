import './CPOptionDetail.es';
import './CPOptionList.es';
import './CPOptionValuesEditor.es';
import './CPOptionValueDetail.es';
import './CPOptionValueList.es';
import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './CPOptionsEditor.soy';

/**
 * CPOptionsEditor
 *
 */

class CPOptionsEditor extends Component {

	created() {
		this.loadOptions();
	}

	_handleAddOption() {
		this._newOptionTitle = '';
		this._currentOption = '0';
	}

	loadOptions() {
		fetch(
			this.optionsURL,
			{
				credentials: 'include',
				method: 'GET'
			}
		).then(
			response => response.json()
		).then(
			(jsonResponse) => {
				this._options = jsonResponse;

				if ((this._options && this._options.length > 0)) {
					if (!this._currentOption || this._currentOption == null) {
						this._currentOption = this._options[0].cpOptionId;
					}
				}
				else if ((this._options && this._options.length == 0)) {
					this._newOptionTitle = '';
					this._currentOption = '0';
				}
			}
		);
	}

	_handleOptionSelected(cpOptionId) {
		this._currentOption = cpOptionId;
	}

	_handleOptionSaved(event) {
		this._currentOption = event.cpOptionId;

		this.loadOptions();
	}

	_handleoptionDeleted(event) {
		this._currentOption = null;

		this.loadOptions();
	}

	_handleCancelEditing(event) {
		this._currentOption = null;

		this.loadOptions();
	}

	_handleTitleChange(newTitle) {
		if (this._currentOption == '0') {
			this._newOptionTitle = newTitle;
		}
		else {
			this._newOptionTitle = '';
		}
	}

	_handleEditValues(cpOptionId) {
		this._currentOption = cpOptionId;

		this._showValues = true;
	}

	_handleCloseValueEditor() {
		this._showValues = false;
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */

CPOptionsEditor.STATE = {
	namespace: Config.string().required(),
	optionsURL: Config.string().required(),
	optionURL: Config.string().required(),
	optionValuesURL: Config.string().required(),
	optionValueURL: Config.string().required(),
	pathThemeImages: Config.string().required(),
	_newOptionTitle: Config.string().value(''),
	_options: Config.array().value([])
};

// Register component

Soy.register(CPOptionsEditor, templates);

export default CPOptionsEditor;