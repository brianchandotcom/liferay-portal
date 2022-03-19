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
import {useCallback, useMemo, useState} from 'react';
import {ALERT_DOWNLOAD_TYPE} from '../../../../utils/constants/alertDownloadType';
import {ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT} from '../../utils/constants/alertAggregateKeysDownloadText';
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

	const [status, setStatus] = useState({
		deactivate: '',
		download: '',
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
								<p className="font-weight-semi-bold m-0 ml-auto text-neutral-10">
									{`${activationKeysByStatusPaginatedChecked.length} Keys Selected`}
								</p>

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

			{status.download && (
				<DownloadAlert
					downloadStatus={status.download}
					message={
						ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT[
							status.download
						]
					}
					setDownloadStatus={(value) =>
						setStatus((previousStatus) => ({
							...previousStatus,
							download: value,
						}))
					}
				/>
			)}

			{status.deactivate === ALERT_DOWNLOAD_TYPE.success && (
				<DownloadAlert
					downloadStatus="success"
					message="Activation Key(s) were deactivated successfully."
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
					To download an aggregate key, select keys with identical
					<b> Type, Start Date, End Date,</b>
					and
					<b> Instance Size</b>
				</ClayAlert>
			)}
		</>
	);
};

export default ActivationKeysTableHeader;
