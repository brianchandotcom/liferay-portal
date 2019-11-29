/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import Component from 'metal-component';
import {Config} from 'metal-state';

/**
 * Shows a dialog and handles the selected item.
 */
class ItemSelectorDialog extends Component {
	/**
	 * Closes the dialog.
	 */
	close() {
		Liferay.Util.getWindow(this.eventName).hide();
	}

	/**
	 * Opens the dialog.
	 */
	open() {
		this._currentItem = null;
		this._selectedItem = null;

		const eventName = this.eventName;

		const dialogConfig = this._getDialogConfig();

		const dialogEvents = {
			on: {
				click: event => {
					event.domEvent.preventDefault();
				},
				visibleChange: event => {
					if (!event.newVal) {
						this.selectedItem = this._selectedItem;

						this.emit('selectedItemChange', {
							selectedItem: this.selectedItem
						});
					}

					this.emit('visibleChange', {visible: event.newVal});
				}
			}
		};

		Liferay.Util.selectEntity(
			{
				dialog: {...dialogConfig, ...dialogEvents},
				eventName,
				id: eventName,
				stack: !this.zIndex,
				title: this.title,
				uri: this.url
			},
			this._onItemSelected.bind(this)
		);
	}

	_getDialogConfig() {
		const dialogConfig = {
			constrain: true,
			cssClass: this.dialogClasses,
			destroyOnHide: true,
			modal: true,
			zIndex: this.zIndex
		};

		if (!this.singleSelect) {
			const dialogFooter = [
				{
					cssClass: 'btn-link close-modal',
					id: 'cancelButton',
					label: this.buttonCancelLabel,
					on: {
						click: () => {
							this.close();
						}
					}
				},
				{
					cssClass: 'btn-primary',
					disabled: true,
					id: 'addButton',
					label: this.buttonAddLabel,
					on: {
						click: () => {
							this._selectedItem = this._currentItem;
							this.close();
						}
					}
				}
			];

			dialogConfig['toolbars.footer'] = dialogFooter;
		}

		return dialogConfig;
	}

	/**
	 * Saves the current selected item in the dialog and disables the Add
	 * button.
	 *
	 * @param {EventFacade} event The event.
	 * @private
	 */
	_onItemSelected(event) {
		const currentItem = event.data;

		if (this.singleSelect) {
			this._selectedItem = currentItem;
			this.close();
		}
		else {
			const dialog = Liferay.Util.getWindow(this.eventName);

			const addButton = dialog
				.getToolbar('footer')
				.get('boundingBox')
				.one('#addButton');

			Liferay.Util.toggleDisabled(addButton, !currentItem);
		}

		this._currentItem = currentItem;
	}
}

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
ItemSelectorDialog.STATE = {
	/**
	 * Label for the Add button.
	 *
	 * @instance
	 * @memberof ItemSelectorDialog
	 * @type {String}
	 */
	buttonAddLabel: Config.string().value(Liferay.Language.get('add')),

	/**
	 * Label for the Cancel button.
	 *
	 * @instance
	 * @memberof ItemSelectorDialog
	 * @type {String}
	 */
	buttonCancelLabel: Config.string().value(Liferay.Language.get('cancel')),

	/**
	 * CSS classes to pass to the dialog.
	 *
	 * @instance
	 * @memberof ItemSelectorDialog
	 * @type {String}
	 */
	dialogClasses: Config.string(),

	/**
	 * Event name.
	 *
	 * @instance
	 * @memberof ItemSelectorDialog
	 * @type {String}
	 */
	eventName: Config.string().required(),

	/**
	 * The selected item(s) in the dialog.
	 *
	 * @instance
	 * @memberof ItemSelectorDialog
	 * @type {Object|Object[]}
	 */
	selectedItem: Config.oneOfType([
		Config.object(),
		Config.arrayOf(Config.object())
	]),

	/**
	 * Enables single selection of item.
	 * @type {boolean}
	 */

	singleSelect: Config.bool().value(false),

	/**
	 * Dialog's title.
	 *
	 * @instance
	 * @memberof ItemSelectorDialog
	 * @type {String}
	 */
	title: Config.string().value(Liferay.Language.get('select-file')),

	/**
	 * URL that opens the dialog.
	 *
	 * @instance
	 * @memberof ItemSelectorDialog
	 * @type {String}
	 */
	url: Config.string().required(),

	/**
	 * Dialog's zIndex.
	 *
	 * @instance
	 * @memberof ItemSelectorDialog
	 * @type {Number}
	 */
	zIndex: Config.number()
};

export default ItemSelectorDialog;
