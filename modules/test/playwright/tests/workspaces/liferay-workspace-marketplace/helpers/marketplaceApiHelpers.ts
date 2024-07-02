/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export async function createMarketplaceAccountUserCatalog({
	accountName,
	accountType,
	apiHelpers,
}) {
	try {
		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: accountName,
			type: accountType,
		});

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			account.id,
			['test@liferay.com']
		);

		const catalog =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
				accountId: account.id,
			});

		return {account, catalog};
	}
	catch (error) {
		console.error('Error when trying to create test account', error);
		throw error;
	}
}

export default {createMarketplaceAccountUserCatalog};
