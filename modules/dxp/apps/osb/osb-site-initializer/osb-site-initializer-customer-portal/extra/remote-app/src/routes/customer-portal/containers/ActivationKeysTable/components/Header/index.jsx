/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {useCallback, useMemo, useState} from 'react';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import i18n from '../../../../../../common/I18n';
import {ROLE_TYPES} from '../../../../../../common/utils/constants';
import {ALERT_DOWNLOAD_TYPE} from '../../../../utils/constants/alertDownloadType';
import {has100YearsDifference} from '../../utils';
import {ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT} from '../../utils/constants/alertAggregateKeysDownloadText';
import {ALERT_ACTIVATION_MULTIPLE_KEYS_DOWNLOAD_TEXT} from '../../utils/constants/alertMultipleKeysDownloadText';
import {DOWNLOADABLE_LICENSE_KEYS} from '../../utils/constants/downlodableLicenseKeys';
import ActionButton from '../ActionButton';
import BadgeFilter from '../BadgeFilter';
import DeactivateButton from '../Deactivate';
import DownloadAlert from '../DownloadAlert';
import Filter from '../Filter';
import RenewButton from '../Renew';
import useGetAccountUserAccount from './hooks/useGetAccountUserAccount';

const ActivationKeysTableHeader = ({
	activationKeysByStatusPaginatedChecked,
	activationKeysState,
	project,
	productName,
	sessionId,
	loading,
	filterState: [filters, setFilters],
}) => {
	const [activationKeys, setActivationKeys] = activationKeysState;

	const {
		userAccountsState: [userAccounts],
	} = useGetAccountUserAccount(project);

	const isAdminOrPartnerManager = useMemo(() => {
		const currentUser = userAccounts?.find(
			({id}) => id === +Liferay.ThemeDisplay.getUserId()
		);

		if (currentUser) {
			const hasAdminRoles = currentUser?.roles?.some(
				(role) =>
					role === ROLE_TYPES.admin.key ||
					role === ROLE_TYPES.partnerManager.key
			);

			return hasAdminRoles;
		}
	}, [userAccounts]);

	const {featureFlags} = useAppPropertiesContext();

	const [status, setStatus] = useState({
		deactivate: '',
		downloadAggregated: '',
		downloadMultiple: '',
	});

	const filterCheckedActivationKeys = useMemo(
		() =>
			activationKeysByStatusPaginatedChecked.reduce(
				(
					filterCheckedActivationKeysAccumulator,
					activationKeyChecked,
					index
				) =>
					`${filterCheckedActivationKeysAccumulator}${
						index > 0 ? '&' : ''
					}licenseKeyIds=${activationKeyChecked.id}`,
				''
			),
		[activationKeysByStatusPaginatedChecked]
	);

	const isAbleToDownloadAggregateKeys = useMemo(() => {
		const [
			firstActivationKeyChecked,
			...restActivationKeysChecked
		] = activationKeysByStatusPaginatedChecked;

		return restActivationKeysChecked.every(
			(activationKeyChecked) =>
				DOWNLOADABLE_LICENSE_KEYS.above71DXPVersion(
					firstActivationKeyChecked,
					activationKeyChecked
				) ||
				DOWNLOADABLE_LICENSE_KEYS.below71DXPVersion(
					firstActivationKeyChecked,
					activationKeyChecked
				)
		);
	}, [activationKeysByStatusPaginatedChecked]);

	const handleDeactivate = useCallback(
		() =>
			setActivationKeys((previousActivationKeys) =>
				previousActivationKeys.filter(
					(activationKey) =>
						!activationKeysByStatusPaginatedChecked.find(
							({id}) => activationKey.id === id
						)
				)
			),
		[activationKeysByStatusPaginatedChecked, setActivationKeys]
	);

	const allowSelfProvisioning = project.allowSelfProvisioning;

	function isBulkRenewAvailable(items) {
		const firstItem = items[0];

		for (const item of items) {
			if (
				item.productName !== firstItem.productName ||
				item.expirationDate !== firstItem.expirationDate ||
				item.startDate !== firstItem.startDate ||
				has100YearsDifference(item.startDate, item.expirationDate)
			) {
				return false;
			}
		}

		return true;
	}
	const bulkRenewAvailable = isBulkRenewAvailable(
		activationKeysByStatusPaginatedChecked
	);

	return (
		<>
			<div className="bg-neutral-1 d-flex flex-column pb-1 pt-3 px-3 rounded">
				<div className="d-flex">
					<Filter
						activationKeys={activationKeys}
						filtersState={[filters, setFilters]}
					/>

					<div className="align-items-center d-flex ml-auto">
						{!!activationKeysByStatusPaginatedChecked.length && (
							<>
								<p className="font-weight-semi-bold m-0 ml-auto pr-2 text-neutral-10">
									{i18n.sub('x-of-x-keys-selected', [
										activationKeysByStatusPaginatedChecked.length,
										activationKeys.length,
									])}
								</p>

								{isAdminOrPartnerManager &&
									allowSelfProvisioning && (
										<DeactivateButton
											deactivateKeysStatus={
												status.deactivate
											}
											filterCheckedActivationKeys={
												filterCheckedActivationKeys
											}
											handleDeactivate={handleDeactivate}
											sessionId={sessionId}
											setDeactivateKeysStatus={(value) =>
												setStatus((previousStatus) => ({
													...previousStatus,
													deactivate: value,
												}))
											}
										/>
									)}
							</>
						)}

						{featureFlags.includes('ISSD-78') &&
							isAdminOrPartnerManager &&
							allowSelfProvisioning &&
							activationKeysByStatusPaginatedChecked.length >=
								2 &&
							bulkRenewAvailable && (
								<RenewButton
									activationKeysByStatusPaginatedChecked={
										activationKeysByStatusPaginatedChecked
									}
									filterCheckedActivationKeys={
										filterCheckedActivationKeys
									}
									identifier="renew"
								/>
							)}

						<ActionButton
							activationKeysByStatusPaginatedChecked={
								activationKeysByStatusPaginatedChecked
							}
							filterCheckedActivationKeys={
								filterCheckedActivationKeys
							}
							identifier="action"
							isAbleToDownloadAggregateKeys={
								isAbleToDownloadAggregateKeys
							}
							isAdminOrPartnerManager={isAdminOrPartnerManager}
							productName={productName}
							project={project}
							sessionId={sessionId}
							setStatus={setStatus}
						/>
					</div>
				</div>

				<BadgeFilter
					activationKeysLength={activationKeys?.length}
					filtersState={[filters, setFilters]}
					loading={loading}
				/>
			</div>

			{status.downloadAggregated && (
				<DownloadAlert
					downloadStatus={status.downloadAggregated}
					message={
						ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT[
							status.downloadAggregated
						]
					}
					setDownloadStatus={(value) =>
						setStatus((previousStatus) => ({
							...previousStatus,
							downloadAggregated: value,
						}))
					}
				/>
			)}

			{status.downloadMultiple && (
				<DownloadAlert
					downloadStatus={status.downloadMultiple}
					message={
						ALERT_ACTIVATION_MULTIPLE_KEYS_DOWNLOAD_TEXT[
							status.downloadMultiple
						]
					}
					setDownloadStatus={(value) =>
						setStatus((previousStatus) => ({
							...previousStatus,
							downloadMultiple: value,
						}))
					}
				/>
			)}

			{status.deactivate === ALERT_DOWNLOAD_TYPE.success && (
				<DownloadAlert
					downloadStatus="success"
					message={i18n.translate(
						'activation-keys-were-deactivated-successfully'
					)}
					setDownloadStatus={(value) =>
						setStatus((previousStatus) => ({
							...previousStatus,
							deactivate: value,
						}))
					}
				/>
			)}

			{!isAbleToDownloadAggregateKeys && (
				<ClayAlert className="my-2" displayType="info">
					{i18n.translate(
						'to-download-an-aggregate-key-select-keys-with-identical-type-start-date-end-date-and-instance-size'
					)}
				</ClayAlert>
			)}
		</>
	);
};

export default ActivationKeysTableHeader;
