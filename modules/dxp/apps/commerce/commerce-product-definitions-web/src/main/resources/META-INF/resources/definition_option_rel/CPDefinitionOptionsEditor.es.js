import './CPDefinitionOptionDetail.es';
import './CPDefinitionOptionList.es';
import './CPDefinitionOptionValuesEditor.es';
import './CPDefinitionOptionValueDetail.es';
import './CPDefinitionOptionValueList.es';

import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './CPDefinitionOptionsEditor.soy';

/**
 * CPDefinitionOptionsEditor
 *
 */

class CPDefinitionOptionsEditor extends Component {

	created() {
		this.loadOptions();
        this._handleKeyUpForModal = this._handleKeyUpForModal.bind(this);
	}

	_handleAddOption() {
		var instance = this;

		AUI().use(
			'liferay-item-selector-dialog',
			(A) => {
				var itemSelectorDialog = new A.LiferayItemSelectorDialog(
					{
						eventName: 'productOptionsSelectItem',
						on: {
							selectedItemChange: function(event) {
								var selectedItems = event.newVal;

								var formData = new FormData();

								formData.set(instance.namespace + 'cmd', 'add_multiple');
								formData.set(instance.namespace + 'cpDefinitionId', instance.cpDefinitionId);
								formData.set(instance.namespace + 'cpOptionIds', selectedItems);

								fetch(
									instance.editProductDefinitionOptionRelURL,
									{
										body: formData,
										credentials: 'include',
										method: 'POST'
									}
								).then(
									response => response.json()
								).then(
									(jsonResponse) => {
										instance.loadOptions();
									}
								);
							}
						},
						title: Liferay.Language.get('select-options-to-add'),
						url: instance.optionsItemSelectorURL
					}
				);

				itemSelectorDialog.open();
			}
		);
	}

	loadOptions() {
		fetch(
			this.cpDefinitionOptionsURL,
			{
				credentials: 'include',
				method: 'GET'
			}
		).then(
			response => response.json()
		).then(
			(jsonResponse) => {
				this._cpDefinitionOptions = jsonResponse;

				if ((this._cpDefinitionOptions && this._cpDefinitionOptions.length > 0)) {
					if (!this._currentOption || this._currentOption == null) {
						this._currentOption = this._cpDefinitionOptions[0].cpDefinitionOptionRelId;
					}
				}
			}
		);
	}

	_handleOptionSelected(cpDefinitionOptionRelId) {
		this._showAddNewOption = false;
		this._currentOption = cpDefinitionOptionRelId;
	}

	_handleOptionSaved(event) {
		this._currentOption = event.cpDefinitionOptionRelId;

		this.loadOptions();

		if (event.success) {
			this._showNotification(this.successMessage, 'success');
		}
		else {
			this._showNotification(event.message, 'danger');
		}
	}

	_handleoptionDeleted(event) {
		this._currentOption = null;

		this.loadOptions();
	}

	_handleCancelEditing(event) {
		this._currentOption = null;

		this.loadOptions();
	}

	_handleKeyUpForModal(evt) {
        if (evt.code === 'Escape') {
            this._handleCloseValueEditor();
        }
    }

	_handleEditValues(cpOptionId) {
        this._currentOption = cpOptionId;
        this._showValues = true;
        document.addEventListener('keyup', this._handleKeyUpForModal);
    }

	_handleCloseValueEditor() {
        this._showValues = false;
        document.removeEventListener('keyup', this._handleKeyUpForModal);
    }

	_showNotification(message, type) {
		AUI().use(
			'liferay-notification',
			() => {
				new Liferay.Notification(
					{
						message: message,
						render: true,
						title: '',
						type: type
					}
				);
			}
		);
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */

CPDefinitionOptionsEditor.STATE = {
	cpDefinitionId: Config.string().required(),
	cpDefinitionOptionsURL: Config.string().required(),
	cpDefinitionOptionValueRelsURL: Config.string().required(),
	cpDefinitionOptionValueRelURL: Config.string().required(),
	editProductDefinitionOptionRelURL: Config.string().required(),
	namespace: Config.string().required(),
	optionsItemSelectorURL: Config.string().required(),
	optionURL: Config.string().required(),
	pathThemeImages: Config.string().required(),
	successMessage: Config.string().required(),
	_cpDefinitionOptions: Config.array().value([])
};

// Register component

Soy.register(CPDefinitionOptionsEditor, templates);

export default CPDefinitionOptionsEditor;