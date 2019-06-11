import {DefaultEventHandler, openSimpleInputModal} from 'frontend-js-web';
import {Config} from 'metal-state';

class LayoutPageTemplateEntryManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	addLayoutPageTemplateEntry(itemData) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('add-page-template'),
			formSubmitURL: itemData.addPageTemplateURL,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			namespace: this.namespace,
			spritemap: this.spritemap
		});
	}

	deleteLayoutPageTemplateEntries() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}
}

LayoutPageTemplateEntryManagementToolbarDefaultEventHandler.STATE = {
	spritemap: Config.string()
};

export default LayoutPageTemplateEntryManagementToolbarDefaultEventHandler;
