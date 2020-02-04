/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

const LAYOUT_COLUMN_ITEM_DROPDOWN_ITEMS = [
	{
		label: Liferay.Language.get('view'),
		name: 'viewLayoutURL'
	},

	{
		label: Liferay.Language.get('preview'),
		name: 'previewLayoutURL'
	},

	{
		label: Liferay.Language.get('edit-conversion-draft'),
		name: 'editConversionLayoutURL'
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
		 * Handle copy layout click in order to show simple input modal.
		 * @param {Event} event
		 * @private
		 */
		handleClick: event => {
			event.preventDefault();

			Liferay.Util.openWindow({
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
			});
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
				{...event, currentTarget: event.target.element},
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
		label: Liferay.Language.get('convert-to-content-page'),
		name: 'convertLayoutURL'
	},

	{
		label: Liferay.Language.get('convert-to-content-page-and-preview'),
		name: 'layoutConversionPreviewURL'
	},

	{
		label: Liferay.Language.get('discard-conversion-draft'),
		name: 'deleteLayoutConversionPreviewURL'
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
