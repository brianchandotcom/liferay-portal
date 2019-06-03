import {Config} from 'metal-state';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import Soy from 'metal-soy';

import getConnectedComponent from '../../store/ConnectedComponent.es';
import {
	HIDE_MAPPING_TYPE_DIALOG,
	SELECT_MAPPEABLE_TYPE,
	UPDATE_LAST_SAVE_DATE
} from '../../actions/actions.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';
import templates from './SelectMappingTypeForm.soy';

/**
 * SelectMappingTypeForm
 */
class SelectMappingTypeForm extends PortletBase {
	/**
	 * @inheritdoc
	 * @review
	 */
	prepareStateForRender(state) {
		let nextState = state;

		if (state.selectedMappingTypes) {
			if (state.selectedMappingTypes.type) {
				nextState = setIn(
					nextState,
					['_mappingTypes'],
					[state.selectedMappingTypes.type]
				);

				nextState = setIn(
					nextState,
					['_selectedMappingTypeId'],
					state.selectedMappingTypes.type.id
				);
			}

			if (state.selectedMappingTypes.subtype) {
				nextState = setIn(
					nextState,
					['_mappingSubtypes'],
					[state.selectedMappingTypes.subtype]
				);

				nextState = setIn(
					nextState,
					['_selectedMappingSubtypeId'],
					state.selectedMappingTypes.subtype.id
				);
			}
		}

		return nextState;
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	rendered() {
		if (!this._mappingTypes) {
			this._loadMappingTypes();
		}

		if (
			this._mappingTypes &&
			this._mappingTypes.length === 1 &&
			!this._selectedMappingTypeId
		) {
			this._handleMappingTypeSelectChange();
		}

		if (
			this._mappingSubtypes &&
			this._mappingSubtypes.length === 1 &&
			!this._selectedMappingSubtypeId
		) {
			this._handleMappingSubtypeSelectChange();
		}
	}

	/**
	 * Close asset type selection dialog
	 * @private
	 * @review
	 */
	_handleCancelButtonClick() {
		this._mappingSubtypes = [];
		this._mappingTypes = null;
		this._selectedMappingTypeId = '';
		this._selectedMappingSubtypeId = '';

		this.store.dispatch({
			type: HIDE_MAPPING_TYPE_DIALOG
		});
	}

	/**
	 * Callback executed when a mapping subtype has been selected.
	 * @private
	 * @review
	 */
	_handleMappingSubtypeSelectChange() {
		const selectInput = this.refs.selectMappingSubtype;

		const mappingSubtype = this._mappingSubtypes.find(
			_mappingSubtype => _mappingSubtype.id === selectInput.value
		);

		this._selectedMappingSubtypeId = mappingSubtype.id;
	}

	/**
	 * Callback executed when a mapping type has been selected.
	 * It loads a mapping subtype list if necessary.
	 * @private
	 * @review
	 */
	_handleMappingTypeSelectChange() {
		const selectInput = this.refs.selectMappingType;

		const mappingType = this._mappingTypes.find(
			_mappingType => _mappingType.id === selectInput.value
		);

		this._selectedMappingTypeId = mappingType.id;
		this._mappingSubtypes = [];

		this._loadMappingSubtypes();
	}

	/**
	 * Sends selected mapping type and subtype to the server
	 * and closes this dialog.
	 * @private
	 * @review
	 */
	_handleSubmitButtonClick() {
		const mappingTypes = {};
		const subtype = this._mappingSubtypes.find(
			_subtype => _subtype.id === this._selectedMappingSubtypeId
		);
		const type = this._mappingTypes.find(
			_type => _type.id === this._selectedMappingTypeId
		);

		if (subtype) {
			mappingTypes.subtype = {
				id: this._selectedMappingSubtypeId,
				label: subtype.label
			};
		}

		if (type) {
			mappingTypes.type = {
				id: this._selectedMappingTypeId,
				label: type.label
			};
		}

		this.store
			.dispatch({
				type: HIDE_MAPPING_TYPE_DIALOG
			})
			.dispatch({
				mappingTypes,
				selectedMappingSubtypeId: this._selectedMappingSubtypeId,
				selectedMappingTypeId: this._selectedMappingTypeId,
				type: SELECT_MAPPEABLE_TYPE
			})
			.dispatch({
				lastSaveDate: new Date(),
				type: UPDATE_LAST_SAVE_DATE
			});
	}

	/**
	 * Load a list of mapping subtypes.
	 * @private
	 * @return {Promise}
	 * @review
	 */
	_loadMappingSubtypes() {
		this._mappingSubtypes = null;

		return this.fetch(this.getInfoClassTypesURL, {
			classNameId: this._selectedMappingTypeId
		})
			.then(response => response.json())
			.then(response => {
				this._mappingSubtypes = response;
			});
	}

	/**
	 * Load a list of mapping types.
	 * @private
	 * @return {Promise}
	 * @review
	 */
	_loadMappingTypes() {
		return this.fetch(this.getInfoDisplayContributorsURL, {})
			.then(response => response.json())
			.then(response => {
				this._mappingTypes = response;
			});
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
SelectMappingTypeForm.STATE = {
	/**
	 * List of available mapping types
	 * @default null
	 * @instance
	 * @memberOf SelectMappingTypeForm
	 * @private
	 * @review
	 * @type {Array<{
	 *   id: !string,
	 *   label: !string
	 * }>}
	 */
	_mappingTypes: Config.arrayOf(
		Config.shapeOf({
			id: Config.string().required(),
			label: Config.string().required()
		})
	).value(null),

	/**
	 * List of available mapping subtypes
	 * @default null
	 * @instance
	 * @memberOf SelectMappingTypeForm
	 * @private
	 * @review
	 * @type {Array<{
	 *   id: !string,
	 *   label: !string
	 * }>}
	 */
	_mappingSubtypes: Config.arrayOf(
		Config.shapeOf({
			id: Config.string().required(),
			label: Config.string().required()
		})
	).value([]),

	/**
	 * String with the selected mapping type id
	 * @default ''
	 * @instance
	 * @memberOf SelectMappingTypeForm
	 * @private
	 * @review
	 * @type {string}
	 */
	_selectedMappingTypeId: Config.string()
		.internal()
		.value(''),

	/**
	 * String with the selected mapping subtype id
	 * @default ''
	 * @instance
	 * @memberOf SelectMappingTypeForm
	 * @private
	 * @review
	 * @type {string}
	 */
	_selectedMappingSubtypeId: Config.string()
		.internal()
		.value('')
};

const ConnectedSelectMappingTypeForm = getConnectedComponent(
	SelectMappingTypeForm,
	[
		'classPK',
		'getInfoClassTypesURL',
		'getInfoDisplayContributorsURL',
		'portletNamespace',
		'savingChanges',
		'selectedMappingTypes'
	]
);

Soy.register(ConnectedSelectMappingTypeForm, templates);

export {ConnectedSelectMappingTypeForm, SelectMappingTypeForm};
export default ConnectedSelectMappingTypeForm;
