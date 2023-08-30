/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import i18n from '../../../../../common/I18n';
import {TOOLTIP_CLASSNAMES_TYPES} from './constants';
import {
	downloadAggregatedActivationKey,
	downloadMultipleActivationKey,
	downloadSelectedKeysDetails,
} from './downloadActivationLicenseKey';

export function getActivationKeysDownloadItems(
	isAbleToDownloadAggregateKeys,
	selectedKeysIDs,
	provisioningServerAPI,
	sessionId,
	handleMultipleAlertStatus,
	handleAlertStatus,
	selectedKeysObjects,
	projectName,
	featureFlags
) {
	const dropdownItemsSelectedDownload = [
		{
			disabled: !isAbleToDownloadAggregateKeys,
			icon: (
				<ClayIcon className="mr-1 text-neutral-4" symbol="document" />
			),
			label: i18n.translate('aggregate-key-single-file'),
			onClick: async () => {
				const downloadedAggregated = await downloadAggregatedActivationKey(
					selectedKeysIDs,
					provisioningServerAPI,
					sessionId,
					selectedKeysObjects,
					projectName
				);

				return handleAlertStatus(downloadedAggregated);
			},
			tooltip: TOOLTIP_CLASSNAMES_TYPES.dropDownItem,
		},
		{
			icon: <ClayIcon className="mr-1 text-neutral-4" symbol="list" />,
			label: i18n.translate('individual-keys-multiple-files'),
			onClick: async () => {
				const downloadedMultiple = await downloadMultipleActivationKey(
					selectedKeysIDs,
					provisioningServerAPI,
					sessionId,
					projectName
				);

				return handleMultipleAlertStatus(downloadedMultiple);
			},
			tooltip: TOOLTIP_CLASSNAMES_TYPES.dropDownItem,
		},
	];

	if (featureFlags.includes('LPS-194304')) {
		dropdownItemsSelectedDownload.push({
			icon: (
				<ClayIcon className="mr-1 text-neutral-4" symbol="download" />
			),
			label: i18n.translate('export-selected-key-details-csv'),
			onClick: async () => {
				const downloadedAggregated = await downloadSelectedKeysDetails(
					selectedKeysIDs,
					provisioningServerAPI,
					sessionId
				);

				return handleAlertStatus(downloadedAggregated);
			},
		});
	}

	return dropdownItemsSelectedDownload;
}
