import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class ManagementToolbarDefaultEventHandler extends PortletBase {
	created() {
		Liferay.on(
			this.ns('selectAddMenuItem'),
			function(event) {
				location.href = Liferay.Util.addParams(this.ns('ddmStructureKey') + '=' + event.ddmStructureKey, this.addArticleURL);
			}
		);
	}

	callAction(event) {
		var itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action]();
		}
	}

	deleteEntries() {
		if (this.trashEnabled || confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-the-selected-entries'))) {
			Liferay.fire(
				this.ns('editEntry'),
				{
					action: this.trashEnabled ? 'moveEntriesToTrash' : 'deleteEntries'
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

	handleActionItemClicked(event) {
		this.callAction(event);
	}

	handleCreationMenuMoreButtonClicked() {
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

	handleFilterItemClicked(event) {
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
}

ManagementToolbarDefaultEventHandler.STATE = {
	addArticleURL: Config.string(),
	folderId: Config.string(),
	namespace: Config.string(),
	openViewMoreStructuresURL: Config.string(),
	selectEntityURL: Config.string(),
	trashEnabled: Config.bool(),
	viewDDMStructureArticlesURL: Config.string()
};

export default ManagementToolbarDefaultEventHandler;