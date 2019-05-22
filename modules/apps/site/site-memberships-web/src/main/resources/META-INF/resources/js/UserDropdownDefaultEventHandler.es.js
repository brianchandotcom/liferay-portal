import dom from 'metal-dom';
import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';

class UserDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteGroupUsers(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, itemData.deleteGroupUsersURL);
		}
	}

	assignSiteRoles(itemData) {
		AUI().use('liferay-item-selector-dialog', A => {
			const itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: this.ns('selectUsersRoles'),
				on: {
					selectedItemChange: function(event) {
						const selectedItem = event.newVal;

						if (selectedItem) {
							let editUserGroupRoleFm = this.one(
								'#editUserGroupRoleFm'
							);

							selectedItem.forEach(item => {
								dom.append(editUserGroupRoleFm, item);
							});

							submitForm(
								editUserGroupRoleFm,
								itemData.editUserGroupRoleURL
							);
						}
					}.bind(this)
				},
				'strings.add': Liferay.Language.get('done'),
				title: Liferay.Language.get('assign-site-roles'),
				url: itemData.assignSiteRolesURL
			});

			itemSelectorDialog.open();
		});
	}
}

export default UserDropdownDefaultEventHandler;
