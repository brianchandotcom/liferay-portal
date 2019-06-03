import dom from 'metal-dom';
import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';

class EditTeamAssignmentsUserGroupsManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	selectUserGroup(itemData) {
		AUI().use('liferay-item-selector-dialog', A => {
			const itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: this.ns('selectUserGroup'),
				on: {
					selectedItemChange: function(event) {
						const selectedItem = event.newVal;

						if (selectedItem) {
							let addTeamUserGroupsFm = this.one(
								'#addTeamUserGroupsFm'
							);

							selectedItem.forEach(item => {
								dom.append(addTeamUserGroupsFm, item);
							});

							submitForm(addTeamUserGroupsFm);
						}
					}.bind(this)
				},
				title: itemData.title,
				url: itemData.selectUserGroupURL
			});

			itemSelectorDialog.open();
		});
	}

	deleteUserGroups() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}
}

export default EditTeamAssignmentsUserGroupsManagementToolbarDefaultEventHandler;
