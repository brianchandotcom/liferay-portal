import Component from 'metal-component';
import { Config } from 'metal-state';
import Dropdown from 'metal-dropdown';
import Soy from 'metal-soy';
import {dom, globalEval} from 'metal-dom';
import { CancellablePromise } from 'metal-promise';
import { async, core } from 'metal';

import CPOptionList from './CPOptionList.es';
import CPOptionDetail from './CPOptionDetail.es';
import CPOptionValuesEditor from './CPOptionValuesEditor.es';

import templates from './CPOptionsEditor.soy';

/**
 * CPOptionsEditor
 *
 */
class CPOptionsEditor extends Component {

	/**
	 * @inheritDoc
	 */
	constructor(opt_config, opt_element) {
		super(opt_config, opt_element);

		this.loadOptions();
	}

	_handleAddOption() {

		this._newOptionTitle = '';
		this._currentOption = "0";
	}

	loadOptions() {

		var promise = fetch(this.optionsURL, {
				credentials: 'include',
				method: 'GET',
			})
			.then(response => response.json())
			.then((jsonResponse) => {
				this._options = jsonResponse;

				if ((this._options && this._options.length > 0)) {

					if (!this._currentOption || this._currentOption == null) {
						this._currentOption = this._options[0].cpOptionId;
					}
				}
				else if((this._options && this._options.length == 0)) {

					this._newOptionTitle = '';
					this._currentOption = '0';
				}
			});
	}

	_handleOptionSelected(cpOptionId) {

		this._currentOption = cpOptionId;
	}

	_handleOptionSaved(event) {

		this._currentOption = event.cpOptionId;

		this.loadOptions();
	}

	_handleOptionDelated(event) {

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

	/**
	 * Url to get the options list
	 * @type {String}
	 */
	optionsURL: Config.string().required(),

	/**
	 * Url to get the option detail
	 * @type {String}
	 */
	optionURL: Config.string().required(),

	optionValuesURL: Config.string().required(),

	optionValueURL: Config.string().required(),

	_options : Config.array().value([]),

	_newOptionTitle: Config.string().value(''),

	namespace: Config.string().required(),

	pathThemeImages: Config.string().required()
};

// Register component
Soy.register(CPOptionsEditor, templates);

export default CPOptionsEditor;