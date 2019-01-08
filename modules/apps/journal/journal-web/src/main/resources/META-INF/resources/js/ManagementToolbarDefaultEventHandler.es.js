import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class ManagementToolbarDefaultEventHandler extends PortletBase {
	callAction(event) {
		var itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action]();
		}
	}

	deleteEntries() {
		if (this.isTrashEnabled || confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-the-selected-entries'))) {
			Liferay.fire(
				this.ns('editEntry'),
				{
					action: this.isTrashEnabled ? 'moveEntriesToTrash' : 'deleteEntries'
				}
			);
		}
	}

	expireEntries() {
		Liferay.fire(
			this.ns('editEntry'),
			{
				action: 'expireEntries'
			}
		);
	}

	handleFilterItemClicked(event) {
		this.callAction(event);
	}

	handleActionItemClicked(event) {
		this.callAction(event);
	}

	moveEntries() {
		Liferay.fire(
			this.ns('editEntry'),
			{
				action: 'moveEntries'
			}
		);
	}

	openDDMStructuresSelector() {
		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					modal: true
				},
				eventName: this.ns('selectDDMStructure'),
				title: Liferay.Language.get('structures'),
				uri: this.selectEntityURL
			},
			function(event) {
				location.href = this.viewDDMStructureArticlesURL;
			}
		);
	}

	openViewMoreDDMStructuresSelector() {
		Liferay.Util.openWindow(
			{
				dialog: {
					destroyOnHide: true,
					modal: true
				},
				id: this.ns('selectAddMenuItem'),
				title: Liferay.Language.get('more'),
				uri: this.openViewMoreStructuresURL
			}
		);
	}
}

ManagementToolbarDefaultEventHandler.STATE = {
	folderId: Config.string(),
	namespace: Config.string(),
	openViewMoreStructuresURL: Config.string(),
	selectEntityURL: Config.string(),
	trashEnabled: Config.bool(),
	viewDDMStructureArticlesURL: Config.string()
};

export default ManagementToolbarDefaultEventHandler;