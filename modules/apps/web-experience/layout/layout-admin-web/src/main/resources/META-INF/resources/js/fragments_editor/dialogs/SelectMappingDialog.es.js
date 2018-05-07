import {Config} from 'metal-state';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import Soy from 'metal-soy';

import templates from './SelectMappingDialog.soy';

/**
 * SelectMappingDialog
 */

class SelectMappingDialog extends PortletBase {

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
	 * Changes visible property to false
	 * @private
	 * @review
	 */

	_handleCancelButtonClick() {
		this.visible = false;
	}

	/**
	 * Handle a mappeable field click and propagate it's selection
	 * @param {Event} event
	 * @private
	 * @review
	 */

	_handleMappeableFieldLinkClick(event) {
		const key = event.delegateTarget.dataset.key;

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
					label: Config.string().required()
				}
			)
		)
		.value(null)

};

Soy.register(SelectMappingDialog, templates);

export {SelectMappingDialog};
export default SelectMappingDialog;