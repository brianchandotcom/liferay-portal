/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import i18n from '../../../../../common/I18n';
import {downloadAllKeysDetails} from './downloadActivationLicenseKey';

export function getActivationKeysActionsItems(
	accountKey,
	provisioningServerAPI,
	sessionId,
	handleAlertStatus,
	handleRedirectPage,
	handleDeactivatePage,
	productName,
	allowSelfProvisioning
) {
	const dropdownItems = [];

	if (allowSelfProvisioning) {
		dropdownItems.push(
			{
				icon: (
					<ClayIcon
						className="mr-1 rounded text-neutral-4"
						symbol="plus"
					/>
				),
				label: i18n.translate('generate-new'),
				onClick: handleRedirectPage,
			},
			{
				icon: (
					<ClayIcon
						className="mr-1 text-neutral-4"
						symbol="minus-circle"
					/>
				),
				label: i18n.translate('deactivate'),
				onClick: handleDeactivatePage,
			}
		);
	}

	dropdownItems.push({
		icon: <ClayIcon className="mr-1 text-neutral-4" symbol="download" />,
		label: i18n.translate('export-all-key-details-csv'),
		onClick: async () => {
			const downloadedAggregated = await downloadAllKeysDetails(
				accountKey,
				provisioningServerAPI,
				sessionId,
				productName
			);

			return handleAlertStatus(downloadedAggregated);
		},
	});

	return dropdownItems;
}
