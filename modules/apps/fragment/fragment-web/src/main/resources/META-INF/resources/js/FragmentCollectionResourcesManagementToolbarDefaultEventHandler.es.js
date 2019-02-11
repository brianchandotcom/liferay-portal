import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class FragmentCollectionResourcesManagementToolbarDefaultEventHandler extends PortletBase {
	callAction(event) {
		var itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	deleteSelectedFragmentCollectionResources() {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			submitForm(this.one('#fm'), this.deleteFragmentCollectionResourcesURL);
		}
	}


	handleActionItemClicked(event) {
		this.callAction(event);
	}

	handleCreationButtonClicked(event) {
		AUI().use(
			'liferay-item-selector-dialog',
			A => {
				const itemSelectorDialog = new A.LiferayItemSelectorDialog(
					{
						eventName: this.ns('uploadFragmentCollectionResource'),
						on: {
							selectedItemChange: function(event) {
								const selectedItem = event.newVal;

								if (selectedItem) {

									const itemValue = JSON.parse(selectedItem.value);

									this.one('#fileEntryId').value = itemValue.fileEntryId;

									submitForm(this.one('#fragmentCollectionResourceFm'));
								}
							}.bind(this)
						},
						'strings.add': Liferay.Language.get('ok'),
						title: Liferay.Language.get('upload-fragment-collection-resource'),
						url: this.itemSelectorURL
					}
				);

				itemSelectorDialog.open();
			}
		);
	}
}

FragmentCollectionResourcesManagementToolbarDefaultEventHandler.STATE = {
	deleteFragmentCollectionResourcesURL: Config.string(),
	itemSelectorURL: Config.string(),
	namespace: Config.string()
};

export default FragmentCollectionResourcesManagementToolbarDefaultEventHandler;