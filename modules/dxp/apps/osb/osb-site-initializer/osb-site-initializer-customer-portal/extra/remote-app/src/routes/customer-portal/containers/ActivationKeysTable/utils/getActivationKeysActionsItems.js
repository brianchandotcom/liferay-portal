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
import {downloadAllKeysDetails} from './downloadActivationLicenseKey';

export function getActivationKeysActionsItems(
	accountKey,
	licenseKeyDownloadURL,
	sessionId,
	handleAlertStatus,
	handleRedirectPage,
	productName
) {
	return [
		{
			icon: (
				<ClayIcon
					className="mr-1 rounded text-neutral-4"
					symbol="plus"
				/>
			),
			label: 'Generate New',
			onClick: handleRedirectPage,
		},
		{
			icon: (
				<ClayIcon className="mr-1 text-neutral-4" symbol="download" />
			),
			label: 'Export All Key Details (csv)',
			onClick: async () => {
				const downloadedAggregated = await downloadAllKeysDetails(
					accountKey,
					licenseKeyDownloadURL,
					sessionId,
					productName
				);

				return handleAlertStatus(downloadedAggregated);
			},
		},
	];
}
