import dom from 'metal-dom';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class UsersManagementToolbarDefaultEventHandler extends PortletBase {
	callAction(event) {
		const itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	deleteSelectedUsers() {
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
						eventName: this.ns('selectUsers'),
						on: {
							selectedItemChange: function(event) {
								const selectedItem = event.newVal;

								if (selectedItem) {
									let addGroupUsersFm = this.one('#addGroupUsersFm');

									selectedItem.forEach(
										item => {
											dom.append(addGroupUsersFm, item)
										}
									);

									submitForm(addGroupUsersFm);
								}
							}.bind(this)
						},
						'strings.add': Liferay.Language.get('done'),
						title: Liferay.Language.get('assign-users-to-this-site'),
						url: this.selectUsersURL
					}
				);

				itemSelectorDialog.open();
			}
		);
	}

	handleFilterItemClicked(event) {
		this.callAction(event);
	}

	removeUserSiteRole(itemData) {
		if (confirm(itemData.message)) {
			submitForm(this.one('#fm'), itemData.removeUserSiteRoleURL);
		}
	}

	selectRoles(itemData) {
		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					destroyOnHide: true,
					modal: true
				},
				eventName: this.ns('selectSiteRole'),
				title: Liferay.Language.get('select-site-role'),
				uri: itemData.selectRolesURL
			},
			function(event) {
				location.href = Liferay.Util.addParams(`${this.ns('roleId')}=${event.id}`, itemData.viewRoleURL);
			}.bind(this)
		);
	}

	selectSiteRole(itemData) {
		AUI().use(
			'liferay-item-selector-dialog',
			A => {
				const itemSelectorDialog = new A.LiferayItemSelectorDialog(
					{
						eventName: this.ns('selectSiteRole'),
						on: {
							selectedItemChange: function(event) {
								let selectedItem = event.newVal;

								if (selectedItem) {
									let fm = this.one('#fm');

									selectedItem.forEach(
										item => {
											dom.append(fm, item)
										}
									);

									submitForm(fm, itemData.editUsersSiteRolesURL);
								}
							}.bind(this)
						},
						'strings.add': Liferay.Language.get('done'),
						title: Liferay.Language.get('assign-site-roles'),
						url: itemData.selectSiteRoleURL
					}
				);

				itemSelectorDialog.open();
			}
		);
	}
}

UsersManagementToolbarDefaultEventHandler.STATE = {
	namespace: Config.string(),
	selectUsersURL: Config.string()
};

export default UsersManagementToolbarDefaultEventHandler;