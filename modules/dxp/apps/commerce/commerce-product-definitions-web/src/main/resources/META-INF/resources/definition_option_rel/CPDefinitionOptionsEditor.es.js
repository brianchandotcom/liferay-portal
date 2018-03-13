import Component from 'metal-component';
import { Config } from 'metal-state';
import Soy from 'metal-soy';
import {dom, globalEval} from 'metal-dom';
import { CancellablePromise } from 'metal-promise';
import { async, core } from 'metal';

import CPDefinitionOptionList from './CPDefinitionOptionList.es';
import CPDefinitionOptionDetail from './CPDefinitionOptionDetail.es';
import CPDefinitionOptionValuesEditor from './CPDefinitionOptionValuesEditor.es';

import templates from './CPDefinitionOptionsEditor.soy';

/**
 * CPDefinitionOptionsEditor
 *
 */
class CPDefinitionOptionsEditor extends Component {

	created() {
		this.loadOptions();
	}

	_handleAddOption() {

		var that = this;

		AUI().use('liferay-item-selector-dialog', function(A) {

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'productOptionsSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							var formData = new FormData();

							formData.set(that.namespace + 'cmd', 'add_multiple');
							formData.set(that.namespace + 'cpDefinitionId', that.cpDefinitionId);
							formData.set(that.namespace + 'cpOptionIds', selectedItems);

							var promise = fetch(that.editProductDefinitionOptionRelURL, {
									body: formData,
									credentials: 'include',
									method: 'POST',
								})
									.then(response => response.json())
							.then((jsonResponse) => {
								that.loadOptions();
							});
						}
					},
					title: Liferay.Language.get('select-options-to-add'),
					url: that.optionsItemSelectorURL
				}
			);

			itemSelectorDialog.open();
		});

	}

	loadOptions() {
		var promise = fetch(this.cpDefinitionOptionsURL, {
				credentials: 'include',
				method: 'GET',
			})
			.then(response => response.json())
			.then((jsonResponse) => {
				this._cpDefinitionOptions = jsonResponse;

				if ((this._cpDefinitionOptions && this._cpDefinitionOptions.length > 0)) {
					if (!this._currentOption || this._currentOption == null) {
						this._currentOption = this._cpDefinitionOptions[0].cpDefinitionOptionRelId;
					}
				}
			});
	}

	_handleOptionSelected(cpDefinitionOptionRelId) {
		this._showAddNewOption = false;
		this._currentOption = cpDefinitionOptionRelId;
	}

	_handleOptionSaved(event) {
		this._currentOption = event.cpDefinitionOptionRelId;

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

	_handleEditValues(cpDefinitionOptionRelId) {
		this._currentOption = cpDefinitionOptionRelId;

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
CPDefinitionOptionsEditor.STATE = {
	editProductDefinitionOptionRelURL: Config.string().required(),
	optionsItemSelectorURL: Config.string().required(),
	cpDefinitionId: Config.string().required(),
	namespace: Config.string().required(),
	optionURL: Config.string().required(),
	cpDefinitionOptionValueRelURL: Config.string().required(),
	cpDefinitionOptionValueRelsURL: Config.string().required(),
	cpDefinitionOptionsURL: Config.string().required(),
	pathThemeImages: Config.string().required(),
	_cpDefinitionOptions: Config.array().value([])
};

// Register component
Soy.register(CPDefinitionOptionsEditor, templates);

export default CPDefinitionOptionsEditor;