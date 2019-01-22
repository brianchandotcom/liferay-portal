import OpenSimpleInputModal from 'frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class ManagementToolbarDefaultEventHandler extends PortletBase {
	addFragmentEntry(itemData) {
		OpenSimpleInputModal(
			{
				dialogTitle: itemData.title,
				formSubmitURL: itemData.addFragmentEntryURL,
				mainFieldLabel: Liferay.Language.get('name'),
				mainFieldName: 'name',
				mainFieldPlaceholder: Liferay.Language.get('name'),
				namespace: this.namespace,
				spritemap: this.spritemap
			}
		);
	}

	callAction(event) {
		var itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	deleteSelectedFragmentEntries() {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			submitForm(document.querySelector(`#${this.namespace}fm`), this.deleteFragmentEntriesURL);
		}
	}

	exportSelectedFragmentEntries() {
		submitForm(document.querySelector(`#${this.namespace}fm`), this.exportFragmentEntriesURL);
	}

	handleActionItemClicked(event) {
		this.callAction(event);
	}

	handleCreationMenuItemClicked(event) {
		this.callAction(event);
	}

	moveSelectedFragmentEntries() {
		const fragmentEntryIds = Liferay.Util.listCheckedExcept(document.querySelector(`#${this.namespace}fm`), this.ns('allRowIds'));
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
				uri: this.selectFragmentCollectionURL
			},
			function(selectedItem) {
				if (selectedItem) {
					document.querySelector(`#${namespace}fragmentCollectionId`).value = selectedItem.id;
					document.querySelector(`#${namespace}fragmentEntryIds`).value = fragmentEntryIds;

					submitForm(document.querySelector(`#${namespace}moveFragmentEntryFm`));
				}
			}
		);
	}
}

ManagementToolbarDefaultEventHandler.STATE = {
	deleteFragmentEntriesURL: Config.string(),
	exportFragmentEntriesURL: Config.string(),
	namespace: Config.string(),
	selectFragmentCollectionURL: Config.string(),
	spritemap: Config.string()
};

export default ManagementToolbarDefaultEventHandler;