import OpenSimpleInputModal from 'frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es';
import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';
import {Config} from 'metal-state';

class DisplayPageManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteSelectedDisplayPages() {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			submitForm(this.one('#fm'));
		}
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
	spritemap: Config.string()
};

export default DisplayPageManagementToolbarDefaultEventHandler;