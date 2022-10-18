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

import ClayAlert from '@clayui/alert';
import {useCallback, useEffect, useMemo, useState} from 'react';
import i18n from '../../../../../../common/I18n';
import {ROLE_TYPES} from '../../../../../../common/utils/constants';
import useGetAccountUserAccount from '../../../../pages/Project/TeamMembers/components/TeamMembersTable/hooks/useGetAccountUserAccounts';
import {ALERT_DOWNLOAD_TYPE} from '../../../../utils/constants/alertDownloadType';
import {ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT} from '../../utils/constants/alertAggregateKeysDownloadText';
import {ALERT_ACTIVATION_MULTIPLE_KEYS_DOWNLOAD_TEXT} from '../../utils/constants/alertMultipleKeysDownloadText';
import {DOWNLOADABLE_LICENSE_KEYS} from '../../utils/constants/downlodableLicenseKeys';
import ActionButton from '../ActionButton';
import BadgeFilter from '../BadgeFilter';
import DeactivateButton from '../Deactivate';
import DownloadAlert from '../DownloadAlert';
import Filter from '../Filter';

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

	const [isAdminOrPartnerManager, setIsAdminOrPartnerManager] = useState();

	const filteredAccounts = userAccounts?.filter((userAccount) =>
		userAccount?.roles?.some(
			(role) =>
				role === ROLE_TYPES.admin.key ||
				role === ROLE_TYPES.partnerManager.key
		)
	);

	useEffect(() => {
		if (filteredAccounts.length) {
			setIsAdminOrPartnerManager(true);
		}
		else {
			setIsAdminOrPartnerManager(false);
		}
	}, [isAdminOrPartnerManager, filteredAccounts]);

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
									{i18n.sub('x-keys-selected', [
										activationKeysByStatusPaginatedChecked.length,
									])}
								</p>
								{isAdminOrPartnerManager && (
									<DeactivateButton
										deactivateKeysStatus={status.deactivate}
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

						<ActionButton
							activationKeysByStatusPaginatedChecked={
								activationKeysByStatusPaginatedChecked
							}
							filterCheckedActivationKeys={
								filterCheckedActivationKeys
							}
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
