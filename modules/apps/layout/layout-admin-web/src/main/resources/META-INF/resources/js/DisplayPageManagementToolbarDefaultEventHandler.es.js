import {DefaultEventHandler, openSimpleInputModal} from 'frontend-js-web';
import {Config} from 'metal-state';

class DisplayPageManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	addDisplayPage(itemData) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('add-display-page-template'),
			formSubmitURL: itemData.addDisplayPageURL,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			namespace: this.namespace,
			spritemap: this.spritemap
		});
	}

	deleteSelectedDisplayPages() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}
}

DisplayPageManagementToolbarDefaultEventHandler.STATE = {
	spritemap: Config.string()
};

export default DisplayPageManagementToolbarDefaultEventHandler;
