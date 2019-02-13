import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class ManagementToolbarDefaultEventHandler extends PortletBase {
	callAction(event) {
		var itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	deleteEntries() {
		if (this.trashEnabled || confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			const form = this.one('#fm');

			Liferay.Util.postForm(
				form,
				this.deleteEntriesURL, {
					cmd: this.trashEnabled ? 'move_to_trash' : 'delete',
					deleteEntryIds: Liferay.Util.listCheckedExcept(form, this.ns('allRowIds'))
				}
			);
		}
	}

	handleActionItemClicked(event) {
		this.callAction(event);
	}
}

ManagementToolbarDefaultEventHandler.STATE = {
	deleteEntriesURL: Config.string(),
	namespace: Config.string(),
	trashEnabled: Config.bool()
};

export default ManagementToolbarDefaultEventHandler;