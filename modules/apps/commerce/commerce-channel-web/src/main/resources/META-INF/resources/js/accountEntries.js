/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	CommerceServiceProvider,
	ItemFinder,
	commerceEvents,
} from 'commerce-frontend-js';
import {openToast} from 'frontend-js-web';

export default function ({
	accountEntryTypes,
	channelExternalReferenceCode,
	channelId,
	dataSetId,
	rootPortletId,
}) {
	const ChannelAccountResource =
		CommerceServiceProvider.AdminChannelAPI('v1');

	function selectItem(account) {
		const accountData = {
			accountExternalReferenceCode: account.externalReferenceCode,
			accountId: account.id,
			channelExternalReferenceCode,
			channelId,
		};

		return ChannelAccountResource.addChannelAccount(channelId, accountData)
			.then(() => {
				Liferay.fire(commerceEvents.FDS_UPDATE_DISPLAY, {
					id: dataSetId,
				});
			})
			.catch((error) => {
				openToast({
					message: error.message || error.title,
					type: 'danger',
				});
			});
	}

	ItemFinder('itemFinder', 'item-finder-root-account', {
		apiUrl:
			'/o/headless-admin-user/v1.0/accounts?filter=type in (' +
			accountEntryTypes.map(
				(accountEntryType) => "'" + accountEntryType.toString() + "'"
			) +
			')',
		getSelectedItems: () => Promise.resolve([]),
		inputPlaceholder: Liferay.Language.get('find-an-account'),
		itemCreation: false,
		itemSelectedMessage: Liferay.Language.get('account-selected'),
		itemsKey: 'id',
		linkedDataSetsId: [dataSetId],
		onItemSelected: selectItem,
		pageSize: 10,
		panelHeaderLabel: Liferay.Language.get('add-accounts'),
		portletId: rootPortletId,
		schema: [
			{
				fieldName: 'name',
			},
		],
		titleLabel: Liferay.Language.get('add-existing-account'),
	});
}
