import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './CPDefinitionOptionValuesEditor.soy';

/**
 * CPDefinitionOptionValuesEditor
 *
 */

class CPDefinitionOptionValuesEditor extends Component {

	constructor(opt_config, opt_parentElement) {
		super(opt_config, opt_parentElement);

		this.on('showChanged', this._handleShowChange);
		this.on('cpDefinitionOptionRelIdChanged', this._handleShowChange);
	}

	_handleClose() {
		this.emit('close');
	}

	_handleAddOptionValue() {
		this._newCPDefinitionOptionValueRelTitle = '';
		this._currentCPDefinitionOptionValueRelId = '0';
	}

	loadOptionValues() {
		if (this.cpDefinitionOptionRelId === undefined || this.cpDefinitionOptionRelId == '0') {
			this._newCPDefinitionOptionValueRelTitle = '';
			this._currentCPDefinitionOptionValueRelId = '0';

			return;
		}

		var url = new URL(this.cpDefinitionOptionValueRelsURL);

		url.searchParams.append(this.namespace + 'cpDefinitionOptionRelId', this.cpDefinitionOptionRelId);

		fetch(
			url,
			{
				credentials: 'include',
				method: 'GET'
			}
		).then(
			response => response.json()
		).then(
			(jsonResponse) => {
				this._cpDefinitionOptionValueRels = jsonResponse;

				if ((this._cpDefinitionOptionValueRels && this._cpDefinitionOptionValueRels.length > 0)) {
					if (!this._currentCPDefinitionOptionValueRelId || this._currentCPDefinitionOptionValueRelId == null) {
						this._currentCPDefinitionOptionValueRelId = this._cpDefinitionOptionValueRels[0].cpDefinitionOptionValueRelId;
					}
				}
				else if ((this._cpDefinitionOptionValueRels && this._cpDefinitionOptionValueRels.length == 0)) {
					this._newCPDefinitionOptionValueRelTitle = '';
					this._currentCPDefinitionOptionValueRelId = '0';
				}
			}
		);
	}

	_handleShowChange(event) {
		this.loadOptionValues();
	}

	_handleOptionValueSelected(cpOptionValueId) {
		this._currentCPDefinitionOptionValueRelId = cpOptionValueId;
	}

	_handleOptionValueSaved(event) {
		this._currentCPDefinitionOptionValueRelId = event.cpDefinitionOptionValueRelId;

		this.loadOptionValues();
	}

	_handleOptionValueDelated(event) {
		this._currentCPDefinitionOptionValueRelId = null;

		this.loadOptionValues();
	}

	_handleCancelEditing(event) {
		this._currentCPDefinitionOptionValueRelId = null;

		this.loadOptionValues();
	}

	_handleTitleChange(newTitle) {
		if (this._currentCPDefinitionOptionValueRelId == '0') {
			this._newCPDefinitionOptionValueRelTitle = newTitle;
		}
		else {
			this._newCPDefinitionOptionValueRelTitle = '';
		}
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */

CPDefinitionOptionValuesEditor.STATE = {
	cpDefinitionOptionRelId: Config.string().required(),
	cpDefinitionOptionValueRelsURL: Config.string().required(),
	cpDefinitionOptionValueRelURL: Config.string().required(),
	namespace: Config.string().required(),
	pathThemeImages: Config.string().required(),
	show: Config.bool().value(false),
	_cpDefinitionOptionValueRels: Config.array().value([]),
	_newCPDefinitionOptionValueRelTitle: Config.string().value('')
};

// Register component

Soy.register(CPDefinitionOptionValuesEditor, templates);

export default CPDefinitionOptionValuesEditor;