import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';
import {dom, globalEval} from 'metal-dom';
import {CancellablePromise} from 'metal-promise';
import {async, core} from 'metal';

import CPOptionValueList from './CPOptionValueList.es';
import CPOptionValueDetail from './CPOptionValueDetail.es';

import templates from './CPOptionValuesEditor.soy';

/**
 * CPOptionValuesEditor
 *
 */

class CPOptionValuesEditor extends Component {

	constructor(opt_config, opt_parentElement) {
		super(opt_config, opt_parentElement);

		this.on('showChanged', this._handleShowChange);
		this.on('cpOptionIdChanged', this._handleShowChange);
	}

	_handleClose() {
		this.emit('close');
	}

	_handleAddOptionValue() {
		this._newOptionValueTitle = '';
		this._currentOptionValue = '0';
	}

	loadOptionValues() {
		if (this.cpOptionId === undefined || this.cpOptionId == '0') {
			this._newOptionValueTitle = '';
			this._currentOptionValue = '0';

			return;
		}

		var url = new URL(this.optionValuesURL);

		url.searchParams.append(this.namespace + 'cpOptionId', this.cpOptionId);

		var promise = fetch(url, {
			credentials: 'include',
			method: 'GET'
		})
			.then(response => response.json())
			.then((jsonResponse) => {

				this._optionValues = jsonResponse;

				if ((this._optionValues && this._optionValues.length > 0)) {
					if (!this._currentOptionValue || this._currentOptionValue == null) {
						this._currentOptionValue = this._optionValues[0].cpOptionValueId;
					}
				}
				else if ((this._optionValues && this._optionValues.length == 0)) {
					this._newOptionValueTitle = '';
					this._currentOptionValue = '0';
				}

			});
	}

	_handleShowChange(event) {
		this.loadOptionValues();
	}

	_handleOptionValueSelected(cpOptionValueId) {
		this._currentOptionValue = cpOptionValueId;
	}

	_handleOptionValueSaved(event) {
		this._currentOptionValue = event.cpOptionValueId;

		this.loadOptionValues();
	}

	_handleOptionValueDelated(event) {
		this._currentOptionValue = null;

		this.loadOptionValues();
	}

	_handleCancelEditing(event) {
		this._currentOptionValue = null;

		this.loadOptionValues();
	}

	_handleTitleChange(newTitle) {
		if (this._currentOptionValue == '0') {
			this._newOptionValueTitle = newTitle;
		}
		else {
			this._newOptionValueTitle = '';
		}
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */

CPOptionValuesEditor.STATE = {
	cpOptionId: Config.string().required(),
	namespace: Config.string().required(),
	optionValueURL: Config.string().required(),
	optionValuesURL: Config.string().required(),
	pathThemeImages: Config.string().required(),
	show: Config.bool().value(false),
	_newOptionValueTitle: Config.string().value(''),
	_optionValues: Config.array().value([])
};

// Register component

Soy.register(CPOptionValuesEditor, templates);

export default CPOptionValuesEditor;