import {Config} from 'metal-state';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import Soy from 'metal-soy';

import 'frontend-js-web/liferay/compat/modal/Modal.es';
import {
	HIDE_MAPPING_TYPE_DIALOG,
	SELECT_MAPPEABLE_TYPE
} from '../../actions/actions.es';
import {Store} from '../../store/store.es';
import templates from './SelectMappingTypeDialog.soy';

/**
 * SelectMappingTypeDialog
 */
class SelectMappingTypeDialog extends PortletBase {

	/**
	 * @inheritDoc
	 * @review
	 */
	rendered() {
		if (this.visible && !this._mappingTypes) {
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
		this.store
			.dispatchAction(
				HIDE_MAPPING_TYPE_DIALOG
			);
	}

	/**
	 * Callback executed when a mapping subtype has been selected.
	 * @private
	 * @review
	 */
	_handleMappingSubtypeSelectChange() {
		const selectInput = this.refs.modal.refs.selectMappingSubtype;

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
		const selectInput = this.refs.modal.refs.selectMappingType;

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

		this._savingChanges = true;

		this.store
			.dispatchAction(
				SELECT_MAPPEABLE_TYPE,
				{
					mappingTypes,
					selectedMappingSubtypeId: this._selectedMappingSubtypeId,
					selectedMappingTypeId: this._selectedMappingTypeId
				}
			)
			.dispatchAction(
				HIDE_MAPPING_TYPE_DIALOG
			);
	}

	/**
	 * Change asset type selection dialog visibility.
	 * @param {{newVal: bool}} change
	 * @private
	 * @review
	 */
	_handleVisibleChanged(change) {
		this.store
			.dispatchAction(
				HIDE_MAPPING_TYPE_DIALOG
			);

		if (!change.newVal) {
			this._mappingSubtypes = [];
			this._mappingTypes = [];
			this._savingChanges = false;
			this._selectedMappingTypeId = '';
			this._selectedMappingSubtypeId = '';
		}
	}

	/**
	 * Load a list of mapping subtypes.
	 * @private
	 * @return {Promise}
	 * @review
	 */
	_loadMappingSubtypes() {
		this._mappingSubtypes = null;

		return this.fetch(
			this.getAssetClassTypesURL,
			{
				classNameId: this._selectedMappingTypeId
			}
		)
			.then(
				response => response.json()
			)
			.then(
				response => {
					this._mappingSubtypes = response;
				}
			);
	}

	/**
	 * Load a list of mapping types.
	 * @private
	 * @return {Promise}
	 * @review
	 */
	_loadMappingTypes() {
		return this.fetch(this.getAssetDisplayContributorsURL, {})
			.then(
				response => response.json()
			)
			.then(
				response => {
					this._mappingTypes = response;
				}
			);
	}

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
SelectMappingTypeDialog.STATE = {

	/**
	 * Class primary key used for storing changes.
	 * @default undefined
	 * @instance
	 * @memberOf SelectMappingTypeDialog
	 * @review
	 * @type {!string}
	 */
	classPK: Config.string().required(),

	/**
	 * URL for obtaining the asset types for which asset display pages can be
	 * created.
	 * @default undefined
	 * @instance
	 * @memberOf SelectMappingTypeDialog
	 * @review
	 * @type {!string}
	 */
	getAssetClassTypesURL: Config.string().required(),

	/**
	 * URL for obtaining the asset types for which asset display pages can be
	 * created.
	 * @default undefined
	 * @instance
	 * @memberOf SelectMappingTypeDialog
	 * @review
	 * @type {!string}
	 */
	getAssetDisplayContributorsURL: Config.string().required(),

	/**
	 * Portlet namespace needed for prefixing form inputs
	 * @default undefined
	 * @instance
	 * @memberOf SelectMappingTypeDialog
	 * @review
	 * @type {!string}
	 */
	portletNamespace: Config.string().required(),

	/**
	 * Path of the available icons.
	 * @default undefined
	 * @instance
	 * @memberOf SelectMappingTypeDialog
	 * @review
	 * @type {!string}
	 */
	spritemap: Config.string().required(),

	/**
	 * Store instance
	 * @default undefined
	 * @instance
	 * @memberOf SelectMappingTypeDialog
	 * @review
	 * @type {Store}
	 */
	store: Config.instanceOf(Store),

	/**
	 * List of available mapping types
	 * @default null
	 * @instance
	 * @memberOf SelectMappingTypeDialog
	 * @private
	 * @review
	 * @type {Array<{
	 *   id: !string,
	 *   label: !string
	 * }>}
	 */
	_mappingTypes: Config
		.arrayOf(
			Config.shapeOf(
				{
					id: Config.string().required(),
					label: Config.string().required()
				}
			)
		)
		.value(null),

	/**
	 * List of available mapping subtypes
	 * @default null
	 * @instance
	 * @memberOf SelectMappingTypeDialog
	 * @private
	 * @review
	 * @type {Array<{
	 *   id: !string,
	 *   label: !string
	 * }>}
	 */
	_mappingSubtypes: Config
		.arrayOf(
			Config.shapeOf(
				{
					id: Config.string().required(),
					label: Config.string().required()
				}
			)
		)
		.value([]),

	/**
	 * Flag indicating if changes are being sent to the server
	 * @default false
	 * @instance
	 * @memberOf SelectMappingTypeDialog
	 * @private
	 * @review
	 * @type {boolean}
	 */
	_savingChanges: Config
		.bool()
		.internal()
		.value(false),

	/**
	 * String with the selected mapping type id
	 * @default ''
	 * @instance
	 * @memberOf SelectMappingTypeDialog
	 * @private
	 * @review
	 * @type {string}
	 */
	_selectedMappingTypeId: Config
		.string()
		.internal()
		.value(''),

	/**
	 * String with the selected mapping subtype id
	 * @default ''
	 * @instance
	 * @memberOf SelectMappingTypeDialog
	 * @private
	 * @review
	 * @type {string}
	 */
	_selectedMappingSubtypeId: Config
		.string()
		.internal()
		.value('')

};

Soy.register(SelectMappingTypeDialog, templates);

export {SelectMappingTypeDialog};
export default SelectMappingTypeDialog;