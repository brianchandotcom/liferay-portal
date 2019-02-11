import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class FragmentCollectionResourcesManagementToolbarDefaultEventHandler extends PortletBase {
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
	itemSelectorURL: Config.string(),
	namespace: Config.string()
};

export default FragmentCollectionResourcesManagementToolbarDefaultEventHandler;