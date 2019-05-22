import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';

class AssetCategoriesManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteSelectedCategories() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}

	selectCategory(itemData) {
		let namespace = this.namespace;

		AUI().use('liferay-item-selector-dialog', A => {
			const itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: this.ns('selectCategory'),
				on: {
					selectedItemChange: function(event) {
						const selectedItem = event.newVal;

						const category = selectedItem
							? selectedItem[Object.keys(selectedItem)[0]]
							: null;

						if (category) {
							location.href = Liferay.Util.addParams(
								namespace + 'categoryId=' + category.categoryId,
								itemData.viewCategoriesURL
							);
						}
					}
				},
				strings: {
					add: Liferay.Language.get('select'),
					cancel: Liferay.Language.get('cancel')
				},
				title: Liferay.Language.get('select-category'),
				url: itemData.categoriesSelectorURL
			});

			itemSelectorDialog.open();
		});
	}
}

export default AssetCategoriesManagementToolbarDefaultEventHandler;
