const LAYOUT_COLUMN_ITEM_DROPDOWN_ITEMS = [
	{
		label: Liferay.Language.get('view'),
		name: 'viewLayoutURL'
	},

	{
		label: Liferay.Language.get('edit'),
		name: 'editLayoutURL'
	},

	{
		label: Liferay.Language.get('configure'),
		name: 'configureURL'
	},

	{

		/**
		 * Handle mark as home page layout click in order to set a layout as home page.
		 * @param {Event} event
		 * @private
		 */
		handleClick: event => {
			const confirmMessage = Liferay.Util.sub(
				Liferay.Language.get('do-you-want-to-replace-x-for-x-as-the-home-page'),
				event.data.item.layoutColumnItem.homePageTitle,
				event.data.item.layoutColumnItem.title
			);

			if (!confirm(confirmMessage)) {
				event.preventDefault();
			}
		},
		label: Liferay.Language.get('mark-as-home-page'),
		name: 'markAsHomePageLayoutURL'
	},

	{

		/**
		 * Handle copy layout click in order to show simple input modal.
		 * @param {Event} event
		 * @param {LayoutColumn} layoutColumn
		 * @private
		 */
		handleClick: (event, layoutColumn) => {
			event.preventDefault();

			Liferay.Util.openWindow(
				{
					dialog: {
						destroyOnHide: true,
						height: 480,
						resizable: false,
						width: 640
					},
					dialogIframe: {
						bodyCssClass: 'dialog-with-footer'
					},
					id: event.data.item.namespace + 'addLayoutDialog',
					title: Liferay.Language.get('copy-page'),
					uri: event.data.item.href
				}
			);
		},
		label: Liferay.Language.get('copy-page'),
		name: 'copyLayoutURL'

	},

	{

		/**
		 * Handle permission item click in order to open the target href in a dialog.
		 * @param {Event} event
		 * @private
		 */
		handleClick: event => {
			Liferay.Util.openInDialog(
				Object.assign(
					{},
					event,
					{
						currentTarget: event.target.element
					}
				),
				{
					dialog: {
						destroyOnHide: true
					},
					dialogIframe: {
						bodyCssClass: 'dialog-with-footer'
					},
					uri: event.data.item.href
				}
			);
		},
		label: Liferay.Language.get('permissions'),
		name: 'permissionsURL'
	},

	{
		label: Liferay.Language.get('orphan-widgets'),
		name: 'orphanPortletsURL'
	},

	{

		/**
		 * Handle delete item click in order to show a previous confirmation alert.
		 * @param {Event} event
		 * @private
		 */
		handleClick: event => {
			const deleteMessage = Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
			);

			if (!confirm(deleteMessage)) {
				event.preventDefault();
			}
		},
		label: Liferay.Language.get('delete'),
		name: 'deleteURL'
	}
];

export {LAYOUT_COLUMN_ITEM_DROPDOWN_ITEMS};
export default LAYOUT_COLUMN_ITEM_DROPDOWN_ITEMS;