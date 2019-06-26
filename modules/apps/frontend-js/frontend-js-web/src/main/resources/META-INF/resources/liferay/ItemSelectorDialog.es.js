import Component from 'metal-component';
import {Config} from 'metal-state';

/**
 * Shows a dialog and handles the selected item.
 */
class ItemSelectorDialog extends Component {
	/**
	 * Close the dialog.
	 *
	 * @review
	 */
	close() {
		Liferay.Util.getWindow(this.eventName).hide();
	}

	/**
	 * Open the dialog.
	 *
	 * @review
	 */
	open() {
		this._currentItem = null;
		this._selectedItem = null;

		const eventName = this.eventName;
		const strings = this.strings;
		const zIndex = this.zIndex;

		Liferay.Util.selectEntity(
			{
				dialog: {
					cssClass: this.dialogClasses,
					constrain: true,
					destroyOnHide: true,
					modal: true,
					on: {
						visibleChange: event => {
							if (!event.newVal) {
								this.selectedItem = this._selectedItem;

								this.emit('selectedItemChange', {
									selectedItem: this.selectedItem
								});
							}
						}
					},
					'toolbars.footer': [
						{
							cssClass: 'btn-link close-modal',
							id: 'cancelButton',
							label: strings.cancel,
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
							label: strings.add,
							on: {
								click: () => {
									this._selectedItem = this._currentItem;
									this.close();
								}
							}
						}
					],
					zIndex: zIndex
				},
				eventName: eventName,
				id: eventName,
				stack: !zIndex,
				title: this.title,
				uri: this.url
			},
			this._onItemSelected.bind(this)
		);
	}

	/**
	 * Saves the current item that has been selected in the dialog,
	 * and disables the Add button is is empty.
	 *
	 * @param {EventFacade} event
	 *
	 * @private
	 * @review
	 */
	_onItemSelected(event) {
		const currentItem = event.data;

		const dialog = Liferay.Util.getWindow(this.eventName);

		const addButton = dialog
			.getToolbar('footer')
			.get('boundingBox')
			.one('#addButton');

		Liferay.Util.toggleDisabled(addButton, !currentItem);

		this._currentItem = currentItem;
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

ItemSelectorDialog.STATE = {
	/**
	 * Css classes to pass to the dialog.
	 *
	 * @instance
	 * @review
	 * @type {String}
	 */
	dialogClasses: Config.string(),

	/**
	 * Event name
	 *
	 * @instance
	 * @review
	 * @type {String}
	 */
	eventName: Config.string().required(),

	/**
	 * The selected item in the dialog.
	 *
	 * @instance
	 * @review
	 * @type {Object}
	 */
	selectedItem: Config.object(),

	/**
	 * String literals used in the dialog.
	 *
	 * @instance
	 * @review
	 * @type {Object}
	 */
	strings: Config.object().value({
		add: Liferay.Language.get('add'),
		cancel: Liferay.Language.get('cancel')
	}),

	/**
	 * Dialog's title
	 *
	 * @instance
	 * @review
	 * @type {String}
	 */
	title: Config.string().value(Liferay.Language.get('select-file')),

	/**
	 * Dialog's zIndex
	 *
	 * @instance
	 * @review
	 * @type {Number}
	 */
	zIndex: Config.number(),

	/**
	 * Url that will open the dialog.
	 *
	 * @instance
	 * @review
	 * @type {String}
	 */
	url: Config.string().required()
};

export default ItemSelectorDialog;
