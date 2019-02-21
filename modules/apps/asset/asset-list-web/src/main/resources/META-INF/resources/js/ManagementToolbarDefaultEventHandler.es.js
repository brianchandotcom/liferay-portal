import OpenSimpleInputModal from 'frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es';
import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';
import {Config} from 'metal-state';

class ManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	addAssetListEntry(itemData) {
		OpenSimpleInputModal(
			{
				dialogTitle: itemData.title,
				formSubmitURL: itemData.addAssetListEntryURL,
				mainFieldLabel: Liferay.Language.get('title'),
				mainFieldName: 'title',
				mainFieldPlaceholder: Liferay.Language.get('title'),
				namespace: this.namespace,
				spritemap: this.spritemap
			}
		);
	}

	deleteSelectedAssetListEntries() {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			submitForm(this.one('#fm'));
		}
	}
}

ManagementToolbarDefaultEventHandler.STATE = {
	spritemap: Config.string()
};

export default ManagementToolbarDefaultEventHandler;