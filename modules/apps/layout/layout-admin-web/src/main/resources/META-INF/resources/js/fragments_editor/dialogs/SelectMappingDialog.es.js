import {Config} from 'metal-state';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import Soy from 'metal-soy';

import templates from './SelectMappingDialog.soy';

/**
 * List of editable types and their compatibilities
 * with the corresponding mappeable types
 * @review
 * @see DDMStructureClassType.java for compatible types
 * @type {!object}
 */

const COMPATIBLE_TYPES = {
	html: [
		'ddm-date',
		'ddm-decimal',
		'ddm-integer',
		'ddm-number',
		'ddm-text-html',
		'text',
		'textarea'
	],

	image: [
		'ddm-image',
		'image'
	],

	'rich-text': [
		'ddm-date',
		'ddm-decimal',
		'ddm-integer',
		'ddm-number',
		'ddm-text-html',
		'text',
		'textarea'
	],

	text: [
		'ddm-date',
		'ddm-decimal',
		'ddm-integer',
		'ddm-number',
		'text',
		'textarea'
	]
};

/**
 * SelectMappingDialog
 */

class SelectMappingDialog extends PortletBase {

	/**
	 * @inheritDoc
	 * @review
	 */

	prepareStateForRender(state) {
		const editableType = state.editableType;

		const mappeableFields = state._mappeableFields ?
			state._mappeableFields.map(
				mappeableField => (
					{
						enabled: (
							COMPATIBLE_TYPES[editableType] &&
							COMPATIBLE_TYPES[editableType]
								.indexOf(mappeableField.type) !== -1
						),
						key: mappeableField.key,
						label: mappeableField.label
					}
				)
			) : null;

		return Object.assign(
			{},
			state,
			{_mappeableFields: mappeableFields}
		);
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	rendered() {
		if (
			this.visible &&
			!this._loadingMappeableFields &&
			!this._mappeableFields
		) {
			this._loadMappeableFields();
		}
	}

	/**
	 * Emit a mappeableFieldSelected event
	 * @param {string} [key='']
	 * @private
	 * @review
	 */

	_emitMappeableFieldSelected(key = '') {
		this.emit(
			'mappeableFieldSelected',
			{
				editableId: this.editableId,
				fragmentEntryLinkId: this.fragmentEntryLinkId,
				key
			}
		);

		this.visible = false;
	}

	/**
	 * Changes visible property to false
	 * @private
	 * @review
	 */

	_handleCancelButtonClick() {
		this.visible = false;
	}

	/**
	 * Unmaps the existing editable field
	 * @private
	 * @review
	 */

	_handleUnmapButtonClick() {
		this._emitMappeableFieldSelected('');
	}

	/**
	 * Handle a mappeable field click and propagate it's selection
	 * @param {Event} event
	 * @private
	 * @review
	 */

	_handleMappeableFieldLinkClick(event) {
		this._emitMappeableFieldSelected(
			event.delegateTarget.dataset.key
		);
	}

	/**
	 * Change asset type selection dialog visibility.
	 * @private
	 * @review
	 */

	_handleVisibleChanged(change) {
		if (this.visible !== change.newVal) {
			this.visible = change.newVal;
		}
	}

	/**
	 * Load the list of mappeable fields from the server
	 * @private
	 * @review
	 */

	_loadMappeableFields() {
		const classNameId = this.selectedMappingTypes.type ?
			this.selectedMappingTypes.type.id : '';

		const classTypeId = this.selectedMappingTypes.subtype ?
			this.selectedMappingTypes.subtype.id : '';

		this._loadingMappeableFields = true;
		this._mappeableFields = null;

		return this.fetch(
			this.mappingFieldsURL,
			{
				classNameId,
				classTypeId
			}
		).then(
			response => response.json()
		).then(
			responseContent => {
				this._loadingMappeableFields = false;
				this._mappeableFields = responseContent;
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

SelectMappingDialog.STATE = {

	/**
	 * EditableId of the field that is being mapped
	 * @default ''
	 * @instance
	 * @memberOf SelectMappingDialog
	 * @review
	 * @type {string}
	 */

	editableId: Config
		.string()
		.value(''),

	/**
	 * Editable type that is being mapped.
	 * This should match the corresponding mappeableField type in order
	 * to be available.
	 * @default ''
	 * @instance
	 * @memberOf SelectMappingDialog
	 * @review
	 * @type {string}
	 */

	editableType: Config
		.string()
		.value(''),

	/**
	 * FragmentEntryLinkId of the field that is being mapped
	 * @default ''
	 * @instance
	 * @memberOf SelectMappingDialog
	 * @review
	 * @type {string}
	 */

	fragmentEntryLinkId: Config
		.string()
		.value(''),

	/**
	 * Mapped field ID of the field that is being mapped
	 * @default ''
	 * @instance
	 * @memberOf SelectMappingDialog
	 * @private
	 * @review
	 * @type {string}
	 */

	mappedFieldId: Config
		.string()
		.value(''),

	/**
	 * URL for getting the list of mapping fields
	 * @default undefined
	 * @instance
	 * @memberOf SelectMappingDialog
	 * @review
	 * @type {!string}
	 */

	mappingFieldsURL: Config.string().required(),

	/**
	 * Selected mapping types
	 * @default {}
	 * @instance
	 * @memberOf SelectMappingDialog
	 * @review
	 * @type {{
	 *   subtype: {
	 *   	id: !string,
	 *   	label: !string
	 *   },
	 *   type: {
	 *   	id: !string,
	 *   	label: !string
	 *   }
	 * }}
	 */

	selectedMappingTypes: Config
		.shapeOf(
			{
				subtype: Config.shapeOf(
					{
						id: Config.string().required(),
						label: Config.string().required()
					}
				),
				type: Config.shapeOf(
					{
						id: Config.string().required(),
						label: Config.string().required()
					}
				)
			}
		)
		.value({}),

	/**
	 * Path of the available icons.
	 * @default undefined
	 * @instance
	 * @memberOf SelectMappingDialog
	 * @review
	 * @type {!string}
	 */

	spritemap: Config
		.string()
		.required(),

	/**
	 * Flag indicating if the SelectMappingDialog should be shown
	 * @default false
	 * @instance
	 * @memberOf SelectMappingDialog
	 * @review
	 * @type {boolean}
	 */

	visible: Config
		.bool()
		.value(false),

	/**
	 * Flag indicating if mappeable fields are being loaded
	 * @default false
	 * @instance
	 * @memberOf SelectMappingDialog
	 * @private
	 * @review
	 * @type {boolean}
	 */

	_loadingMappeableFields: Config
		.bool()
		.value(false),

	/**
	 * List of mappeable fields being shown as options
	 * @default null
	 * @instance
	 * @memberOf SelectMappingDialog
	 * @review
	 * @private
	 * @type {null|Array<{
	 *   key: !string,
	 *   label: !string
	 * }>}
	 */

	_mappeableFields: Config
		.arrayOf(
			Config.shapeOf(
				{
					key: Config.string().required(),
					label: Config.string().required(),
					type: Config.string().required()
				}
			)
		)
		.value(null)

};

Soy.register(SelectMappingDialog, templates);

export {SelectMappingDialog};
export default SelectMappingDialog;