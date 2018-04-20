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
		this._loadingMappeableFields = true;
		this._mappeableFields = null;

		setTimeout(
			() => {
				this._loadingMappeableFields = false;
				this._mappeableFields = [
					{
						label: 'Field 1',
						key: 'field-1'
					},
					{
						label: 'Field 2',
						key: 'field-2'
					}
				];
			},
			1000
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