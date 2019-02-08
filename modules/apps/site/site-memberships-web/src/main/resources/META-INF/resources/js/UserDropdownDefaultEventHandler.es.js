import dom from 'metal-dom';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class UserDropdownDefaultEventHandler extends PortletBase {
	handleItemClicked(event) {
		const itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	deleteGroupUsers(itemData) {
		submitForm(document.hrefFm, itemData.deleteGroupUsersURL);
	}

	assignSiteRoles(itemData) {
		AUI().use(
			'liferay-item-selector-dialog',
			A => {
				const itemSelectorDialog = new A.LiferayItemSelectorDialog(
					{
						eventName: this.ns('selectUsersRoles'),
						on: {
							selectedItemChange: function(event) {
								const selectedItem = event.newVal;

								if (selectedItem) {
									let editUserGroupRoleFm = this.one('#editUserGroupRoleFm');

									selectedItem.forEach(
										item => {
											dom.append(editUserGroupRoleFm, item);
										}
									);

									submitForm(editUserGroupRoleFm, itemData.editUserGroupRoleURL);
								}
							}.bind(this)
						},
						'strings.add': Liferay.Language.get('done'),
						title: Liferay.Language.get('assign-site-roles'),
						url: itemData.assignSiteRolesURL
					}
				);

				itemSelectorDialog.open();
			}
		);
	}
}

UserDropdownDefaultEventHandler.STATE = {
	namespace: Config.string()
};

export default UserDropdownDefaultEventHandler;