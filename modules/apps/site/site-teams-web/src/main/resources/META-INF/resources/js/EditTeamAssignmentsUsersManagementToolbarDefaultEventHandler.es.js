import dom from 'metal-dom';
import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';

class EditTeamAssignmentsUsersManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	selectUser(itemData) {
		AUI().use('liferay-item-selector-dialog', A => {
			const itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: this.ns('selectUser'),
				on: {
					selectedItemChange: function(event) {
						const selectedItem = event.newVal;

						if (selectedItem) {
							let addTeamUsersFm = this.one('#addTeamUsersFm');

							selectedItem.forEach(item => {
								dom.append(addTeamUsersFm, item);
							});

							submitForm(addTeamUsersFm);
						}
					}.bind(this)
				},
				title: itemData.title,
				url: itemData.selectUserURL
			});

			itemSelectorDialog.open();
		});
	}

	deleteUsers() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}
}

export default EditTeamAssignmentsUsersManagementToolbarDefaultEventHandler;
