import OpenSimpleInputModal from 'frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es';
import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';
import {Config} from 'metal-state';

class FragmentEntryDropdownDefaultEventHandler extends DefaultEventHandler {
	copyFragmentEntry(itemData) {
		this.one('#fragmentCollectionId').value = itemData.fragmentCollectionId;
		this.one('#fragmentEntryIds').value = itemData.fragmentEntryId;

		submitForm(this.one('#fragmentEntryFm'), itemData.copyFragmentEntryURL);
	}

	deleteFragmentEntry(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._send(itemData.deleteFragmentEntryURL);
		}
	}

	deleteFragmentEntryPreview(itemData) {
		this._send(itemData.deleteFragmentEntryPreviewURL);
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
					this.one('#fragmentEntryIds').value =
						itemData.fragmentEntryId;

					submitForm(
						this.one('#fragmentEntryFm'),
						itemData.moveFragmentEntryURL
					);
				}
			}.bind(this)
		);
	}

	renameFragmentEntry(itemData) {
		OpenSimpleInputModal({
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
		});
	}

	updateFragmentEntryPreview(itemData) {
		AUI().use('liferay-item-selector-dialog', A => {
			const itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: this.ns('changePreview'),
				on: {
					selectedItemChange: function(event) {
						const selectedItem = event.newVal;

						if (selectedItem) {
							const itemValue = JSON.parse(selectedItem.value);

							this.one('#fragmentEntryId').value =
								itemData.fragmentEntryId;
							this.one('#fileEntryId').value =
								itemValue.fileEntryId;

							submitForm(this.one('#fragmentEntryPreviewFm'));
						}
					}.bind(this)
				},
				'strings.add': Liferay.Language.get('ok'),
				title: Liferay.Language.get('fragment-thumbnail'),
				url: itemData.itemSelectorURL
			});

			itemSelectorDialog.open();
		});
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

FragmentEntryDropdownDefaultEventHandler.STATE = {
	copyFragmentEntryURL: Config.string(),
	fragmentCollectionId: Config.string(),
	moveFragmentEntryURL: Config.string(),
	spritemap: Config.string()
};

export default FragmentEntryDropdownDefaultEventHandler;
