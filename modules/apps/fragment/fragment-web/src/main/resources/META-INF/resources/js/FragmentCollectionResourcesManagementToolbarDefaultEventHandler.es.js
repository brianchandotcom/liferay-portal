import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';

class FragmentCollectionResourcesManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	addFragmentCollectionResource(itemData) {
		AUI().use('liferay-item-selector-dialog', A => {
			const itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: this.ns('uploadFragmentCollectionResource'),
				on: {
					selectedItemChange: function(event) {
						const selectedItem = event.newVal;

						if (selectedItem) {
							const itemValue = JSON.parse(selectedItem.value);

							this.one('#fileEntryId').value =
								itemValue.fileEntryId;

							submitForm(
								this.one('#fragmentCollectionResourceFm')
							);
						}
					}.bind(this)
				},
				'strings.add': Liferay.Language.get('ok'),
				title: Liferay.Language.get(
					'upload-fragment-collection-resource'
				),
				url: itemData.itemSelectorURL
			});

			itemSelectorDialog.open();
		});
	}

	deleteSelectedFragmentCollectionResources(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(
				this.one('#fm'),
				itemData.deleteFragmentCollectionResourcesURL
			);
		}
	}
}

export default FragmentCollectionResourcesManagementToolbarDefaultEventHandler;
