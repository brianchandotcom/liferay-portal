import dom from 'metal-dom';
import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';

class OrganizationsManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteSelectedOrganizations() {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			submitForm(this.one('#fm'));
		}
	}

	selectOrganizations(itemData) {
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
											dom.append(addGroupOrganizationsFm, item);
										}
									);

									submitForm(addGroupOrganizationsFm);
								}
							}.bind(this)
						},
						'strings.add': Liferay.Language.get('done'),
						title: Liferay.Language.get('assign-organizations-to-this-site'),
						url: itemData.selectOrganizationsURL
					}
				);

				itemSelectorDialog.open();
			}
		);
	}
}

export default OrganizationsManagementToolbarDefaultEventHandler;