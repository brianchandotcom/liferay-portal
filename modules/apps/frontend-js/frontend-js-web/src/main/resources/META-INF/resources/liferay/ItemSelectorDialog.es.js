import Component from 'metal-component';
import {Config} from 'metal-state';

class ItemSelectorDialog extends Component {
	close() {
		Liferay.Util.getWindow(this.eventName).hide();
		console.log('Close ItemSelectorDialog');
	}

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
						visibleChange: (event) => {
							if (!event.newVal) {
								this.selectedItem = this._selectedItem;
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

	_onItemSelected(event) {
		var currentItem = event.data;

		var dialog = Liferay.Util.getWindow(this.eventName);

		var addButton = dialog
			.getToolbar('footer')
			.get('boundingBox')
			.one('#addButton');

		Liferay.Util.toggleDisabled(addButton, !currentItem);

		this._currentItem = currentItem;
	}
}

ItemSelectorDialog.STATE = {
	dialogClasses: Config.string(),
	eventName: Config.string().required(),
	selectedItem: Config.object(),
	strings: Config.object().value({
		add: Liferay.Language.get('add-new'),
		cancel: Liferay.Language.get('cancel-new')
	}),
	title: Config.string().value(Liferay.Language.get('select-file')),
	zIndex: Config.number(),
	url: Config.string().required()
}

export default ItemSelectorDialog;