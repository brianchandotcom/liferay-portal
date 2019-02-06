import dom from 'metal-dom';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class OrganizationsManagementToolbarDefaultEventHandler extends PortletBase {
	callAction(event) {
		const itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	deleteSelectedOrganizations() {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			submitForm(this.one('#fm'));
		}
	}

	handleActionItemClicked(event) {
		this.callAction(event);
	}

	handleCreationButtonClicked() {
		AUI().use(
			'liferay-item-selector-dialog',
			A => {
				const itemSelectorDialog = new A.LiferayItemSelectorDialog(
					{
						eventName: this.ns('selectOrganizations'),
						on: {
							selectedItemChange: function(event) {
								const selectedItem = event.newVal;

								if (selectedItem) {
									let addGroupOrganizationsFm = this.one('#addGroupOrganizationsFm');

									selectedItem.forEach(
										item => {
											dom.append(addGroupOrganizationsFm, item)
										}
									);

									submitForm(addGroupOrganizationsFm);
								}
							}.bind(this)
						},
						'strings.add': Liferay.Language.get('done'),
						title: Liferay.Language.get('assign-organizations-to-this-site'),
						url: this.selectOrganizationsURL
					}
				);

				itemSelectorDialog.open();
			}
		);
	}
}

OrganizationsManagementToolbarDefaultEventHandler.STATE = {
	namespace: Config.string(),
	selectOrganizationsURL: Config.string()
};

export default OrganizationsManagementToolbarDefaultEventHandler;