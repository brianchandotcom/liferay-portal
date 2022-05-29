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
import i18n from '../../../../../common/I18n';
import {TOOLTIP_CLASSNAMES_TYPES} from './constants';
import {
	downloadAggregatedActivationKey,
	downloadMultipleActivationKey,
} from './downloadActivationLicenseKey';

export function getActivationKeysDownloadItems(
	isAbleToDownloadAggregateKeys,
	selectedKeysIDs,
	provisioningServerAPI,
	sessionId,
	handleMultipleAlertStatus,
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
}
