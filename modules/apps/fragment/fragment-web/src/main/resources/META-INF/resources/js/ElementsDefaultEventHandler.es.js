import OpenSimpleInputModal from 'frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class ElementsDefaultEventHandler extends PortletBase {
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
		const namespace = this.namespace;

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
					document.querySelector(`#${namespace}fragmentCollectionId`).value = selectedItem.id;
					document.querySelector(`#${namespace}fragmentEntryIds`).value = itemData.fragmentEntryId;

					submitForm(document.querySelector(`#${namespace}moveFragmentEntryFm`));
				}
			}
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
		const namespace = this.namespace;

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

									document.querySelector(`#${namespace}fragmentEntryId`).value = itemData.fragmentEntryId;
									document.querySelector(`#${namespace}fileEntryId`).value = itemValue.fileEntryId;

									submitForm(document.querySelector(`#${namespace}fragmentEntryPreviewFm`));
								}
							}
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

ElementsDefaultEventHandler.STATE = {
	namespace: Config.string(),
	spritemap: Config.string(),
};

export default ElementsDefaultEventHandler;