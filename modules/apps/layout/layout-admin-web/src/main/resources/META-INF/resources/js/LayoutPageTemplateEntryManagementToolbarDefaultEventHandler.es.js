import OpenSimpleInputModal from 'frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class LayoutPageTemplateEntryManagementToolbarDefaultEventHandler extends PortletBase {
	addLayoutPageTemplateEntry(itemData) {
		OpenSimpleInputModal(
			{
				dialogTitle: Liferay.Language.get('add-page-template'),
				formSubmitURL: itemData.addPageTemplateURL,
				mainFieldLabel: Liferay.Language.get('name'),
				mainFieldName: 'name',
				mainFieldPlaceholder: Liferay.Language.get('name'),
				namespace: this.namespace,
				spritemap: this.spritemap
			}
		);
	}

	callAction(event) {
		const itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	deleteLayoutPageTemplateEntries() {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			submitForm(this.one('#fm'));
		}
	}

	handleActionItemClicked(event) {
		this.callAction(event);
	}

	handleCreationMenuItemClicked(event) {
		this.callAction(event);
	}

}

LayoutPageTemplateEntryManagementToolbarDefaultEventHandler.STATE = {
	namespace: Config.string(),
	spritemap: Config.string()
};

export default LayoutPageTemplateEntryManagementToolbarDefaultEventHandler;