import OpenSimpleInputModal from 'frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class DisplayPageManagementToolbarDefaultEventHandler extends PortletBase {
	callAction(event) {
		const itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	deleteSelectedDisplayPages() {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			submitForm(this.one('#fm'));
		}
	}

	handleActionItemClicked(event) {
		this.callAction(event);
	}

	handleCreationButtonClicked() {
		OpenSimpleInputModal(
			{
				dialogTitle: Liferay.Language.get('add-display-page'),
				formSubmitURL: this.addDisplayPageURL,
				mainFieldLabel: Liferay.Language.get('name'),
				mainFieldName: 'name',
				mainFieldPlaceholder: Liferay.Language.get('name'),
				namespace: this.namespace,
				spritemap: this.spritemap
			}
		);
	}
}

DisplayPageManagementToolbarDefaultEventHandler.STATE = {
	addDisplayPageURL: Config.string(),
	namespace: Config.string(),
	spritemap: Config.string()
};

export default DisplayPageManagementToolbarDefaultEventHandler;