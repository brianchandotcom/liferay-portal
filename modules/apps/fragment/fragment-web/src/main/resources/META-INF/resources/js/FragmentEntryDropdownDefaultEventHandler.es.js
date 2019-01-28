import OpenSimpleInputModal from 'frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class FragmentEntryDropdownDefaultEventHandler extends PortletBase {
	handleItemClicked(event) {
		const itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	deleteFragmentEntry(itemData) {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			this._send(itemData.deleteFragmentEntryURL);
		}
	}

	moveFragmentEntry(itemData) {
		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					destroyOnHide: true,
					modal: true
				},
				eventName: this.ns('selectFragmentCollection'),
				id: this.ns('selectFragmentCollection'),
				title: Liferay.Language.get('select-collection'),
				uri: itemData.selectFragmentCollectionURL
			},
			function(selectedItem) {
				if (selectedItem) {
					this.one('#fragmentCollectionId').value = selectedItem.id;
					this.one('#fragmentEntryIds').value = itemData.fragmentEntryId;

					submitForm(this.one('#moveFragmentEntryFm'));
				}
			}.bind(this)
		);
	}

	renameFragmentEntry(itemData) {
		OpenSimpleInputModal(
			{
				dialogTitle: Liferay.Language.get('rename-fragment'),
				formSubmitURL: itemData.updateFragmentEntryURL,
				idFieldName: 'id',
				idFieldValue: itemData.fragmentEntryId,
				mainFieldLabel: Liferay.Language.get('name'),
				mainFieldName: 'name',
				mainFieldPlaceholder: Liferay.Language.get('name'),
				mainFieldValue: itemData.fragmentEntryName,
				namespace: this.namespace,
				spritemap: this.spritemap
			}
		);
	}

	updateFragmentEntryPreview(itemData) {
		AUI().use(
			'liferay-item-selector-dialog',
			A => {
				const itemSelectorDialog = new A.LiferayItemSelectorDialog(
					{
						eventName: this.ns('changePreview'),
						on: {
							selectedItemChange: function(event) {
								const selectedItem = event.newVal;

								if (selectedItem) {
									const itemValue = JSON.parse(selectedItem.value);

									this.one('#fragmentEntryId').value = itemData.fragmentEntryId;
									this.one('#fileEntryId').value = itemValue.fileEntryId;

									submitForm(this.one('#fragmentEntryPreviewFm'));
								}
							}.bind(this)
						},
						'strings.add': Liferay.Language.get('ok'),
						title: Liferay.Language.get('fragment-thumbnail'),
						url: itemData.itemSelectorURL
					}
				);

				itemSelectorDialog.open();
			}
		);
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

FragmentEntryDropdownDefaultEventHandler.STATE = {
	namespace: Config.string(),
	spritemap: Config.string()
};

export default FragmentEntryDropdownDefaultEventHandler;