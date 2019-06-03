import OpenSimpleInputModal from 'frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es';
import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';
import {Config} from 'metal-state';

class ManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	addFragmentEntry(itemData) {
		OpenSimpleInputModal({
			dialogTitle: itemData.title,
			formSubmitURL: itemData.addFragmentEntryURL,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			namespace: this.namespace,
			spritemap: this.spritemap
		});
	}

	copySelectedFragmentEntries() {
		const fragmentEntryIds = Liferay.Util.listCheckedExcept(
			this.one('#fm'),
			this.ns('allRowIds')
		);

		this.one('#fragmentCollectionId').value = this.fragmentCollectionId;
		this.one('#fragmentEntryIds').value = fragmentEntryIds;

		submitForm(this.one('#fragmentEntryFm'), this.copyFragmentEntryURL);
	}

	deleteSelectedFragmentEntries() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'), this.deleteFragmentEntriesURL);
		}
	}

	exportSelectedFragmentEntries() {
		submitForm(this.one('#fm'), this.exportFragmentEntriesURL);
	}

	moveSelectedFragmentEntries() {
		const fragmentEntryIds = Liferay.Util.listCheckedExcept(
			this.one('#fm'),
			this.ns('allRowIds')
		);

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
				uri: this.selectFragmentCollectionURL
			},
			function(selectedItem) {
				if (selectedItem) {
					this.one('#fragmentCollectionId').value = selectedItem.id;
					this.one('#fragmentEntryIds').value = fragmentEntryIds;

					submitForm(
						this.one('#fragmentEntryFm'),
						this.moveFragmentEntryURL
					);
				}
			}.bind(this)
		);
	}
}

ManagementToolbarDefaultEventHandler.STATE = {
	copyFragmentEntryURL: Config.string(),
	deleteFragmentEntriesURL: Config.string(),
	exportFragmentEntriesURL: Config.string(),
	fragmentCollectionId: Config.string(),
	moveFragmentEntryURL: Config.string(),
	selectFragmentCollectionURL: Config.string(),
	spritemap: Config.string()
};

export default ManagementToolbarDefaultEventHandler;
