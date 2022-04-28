/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayIcon from '@clayui/icon';
import {TOOLTIP_CLASSNAMES_TYPES} from './constants';
import {
	downloadAggregatedActivationKey,
	downloadSingleFileActivationDownloadKey,
} from './downloadActivationLicenseKey';

export function getActivationKeysDownloadItems(
	isAbleToDownloadAggregateKeys,
	selectedKeysIDs,
	licenseKeyDownloadURL,
	sessionId,
	handleAlertStatus,
	selectedKeysObjects,
	projectName
) {
	return [
		{
			disabled: !isAbleToDownloadAggregateKeys,
			icon: (
				<ClayIcon className="mr-1 text-neutral-4" symbol="document" />
			),
			label: 'Aggregate Key (single file)',
			onClick: async () => {
				const downloadedAggregated = await downloadAggregatedActivationKey(
					selectedKeysIDs,
					licenseKeyDownloadURL,
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
			label: 'Individual Keys (multiple files)',
			onClick: async () => {
				const downloadedAggregated = await downloadSingleFileActivationDownloadKey(
					selectedKeysIDs,
					licenseKeyDownloadURL,
					sessionId,
					projectName
				);

				return handleAlertStatus(downloadedAggregated);
			},
			tooltip: TOOLTIP_CLASSNAMES_TYPES.dropDownItem,
		},
	];
}
