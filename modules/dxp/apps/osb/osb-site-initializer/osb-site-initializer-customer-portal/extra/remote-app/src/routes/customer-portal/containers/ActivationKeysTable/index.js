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
import {ButtonWithIcon} from '@clayui/core';
import {useModal} from '@clayui/modal';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {useCallback, useEffect, useMemo, useState} from 'react';
import RoundedGroupButtons from '../../../../common/components/RoundedGroupButtons';
import Table from '../../../../common/components/Table';
import {useApplicationProvider} from '../../../../common/context/AppPropertiesProvider';
import {ALERT_DOWNLOAD_TYPE} from '../../utils/constants/alertDownloadType';
import DownloadAlert from './components/DownloadAlert';
import ActivationKeysTableHeader from './components/Header';
import useFilters from './components/Header/hooks/useFilters';
import ModalKeyDetails from './components/ModalKeyDetails';
import useGetActivationKeysData from './hooks/useGetActivationKeysData';
import usePagination from './hooks/usePagination';
import useStatusCountNavigation from './hooks/useStatusCountNavigation';
import {COLUMNS} from './utils/constants';
import {ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT} from './utils/constants/alertAggregateKeysDownloadText';
import {
	EnvironmentTypeColumn,
	ExpirationDateColumn,
	KeyTypeColumn,
	StatusColumn,
} from './utils/constants/columns-definitions';
import {downloadActivationLicenseKey} from './utils/downloadActivationLicenseKey';
import {getActivationKeyDownload} from './utils/getActivationKeyDownload';
import {getTooltipContentRenderer} from './utils/getTooltipContentRenderer';

const ActivationKeysTable = ({project, sessionId}) => {
	const {licenseKeyDownloadURL} = useApplicationProvider();
	const [isVisibleModal, setIsVisibleModal] = useState(false);
	const [downloadStatus, setDownloadStatus] = useState('');

	const {
		activationKeysState: [activationKeys, setActivationKeys],
		loading,
		setFilterTerm,
	} = useGetActivationKeysData(project, sessionId);

	const {
		navigationGroupButtons,
		statusfilterByTitle: [statusFilter, setStatusFilter],
	} = useStatusCountNavigation(activationKeys);

	const {activationKeysByStatusPaginated, paginationConfig} = usePagination(
		activationKeys,
		statusFilter
	);

	const [filters, setFilters] = useFilters(setFilterTerm);

	const [currentActivationKey, setCurrentActivationKey] = useState();
	const [activationKeysIdChecked, setActivationKeysIdChecked] = useState([]);

	const {observer, onClose} = useModal({
		onClose: () => setIsVisibleModal(false),
	});

	const activationKeysByStatusPaginatedChecked = useMemo(
		() =>
			activationKeysByStatusPaginated.filter(({id}) =>
				activationKeysIdChecked.includes(id)
			) || [],
		[activationKeysByStatusPaginated, activationKeysIdChecked]
	);

	useEffect(() => {
		if (activationKeysByStatusPaginated.length) {
			setActivationKeysIdChecked([]);
		}
	}, [activationKeysByStatusPaginated]);

	const handleAlertStatus = useCallback((hasSuccessfullyDownloadedKeys) => {
		setDownloadStatus(
			hasSuccessfullyDownloadedKeys
				? ALERT_DOWNLOAD_TYPE.success
				: ALERT_DOWNLOAD_TYPE.danger
		);
	}, []);

	const getActivationKeysRows = useCallback(
		(activationKey) => ({
			customClickOnRow: () => {
				setCurrentActivationKey(activationKey);
				setIsVisibleModal(true);
			},
			download: (
				<ButtonWithIcon
					displayType="null"
					onClick={() =>
						getActivationKeyDownload(
							licenseKeyDownloadURL,
							sessionId,
							handleAlertStatus,
							activationKey,
							project.name
						)
					}
					small
					symbol="download"
				/>
			),
			envName: (
				<div title={[activationKey.name, activationKey.description]}>
					<p className="font-weight-bold m-0 text-neutral-10 text-truncate">
						{activationKey.name}
					</p>

					<p className="font-weight-normal m-0 text-neutral-7 text-paragraph-sm text-truncate">
						{activationKey.description}
					</p>
				</div>
			),
			envType: <EnvironmentTypeColumn activationKey={activationKey} />,
			expirationDate: (
				<ExpirationDateColumn activationKey={activationKey} />
			),
			id: activationKey.id,
			keyType: <KeyTypeColumn activationKey={activationKey} />,
			status: <StatusColumn activationKey={activationKey} />,
		}),
		[handleAlertStatus, licenseKeyDownloadURL, project.name, sessionId]
	);

	return (
		<>
			{isVisibleModal && (
				<ModalKeyDetails
					currentActivationKey={currentActivationKey}
					downloadActivationLicenseKey={downloadActivationLicenseKey}
					isVisibleModal={isVisibleModal}
					observer={observer}
					onClose={onClose}
					project={project}
					sessionId={sessionId}
				/>
			)}
			<ClayTooltipProvider
				contentRenderer={({title}) => getTooltipContentRenderer(title)}
				delay={100}
			>
				<div>
					<div className="align-center cp-dxp-activation-key-container d-flex justify-content-between mb-2">
						<h3 className="m-0">Activation Keys</h3>

						<RoundedGroupButtons
							groupButtons={navigationGroupButtons}
							handleOnChange={(value) => setStatusFilter(value)}
						/>
					</div>

					<div className="mt-4 py-2">
						<ActivationKeysTableHeader
							activationKeysByStatusPaginatedChecked={
								activationKeysByStatusPaginatedChecked
							}
							activationKeysState={[
								activationKeys,
								setActivationKeys,
							]}
							filterState={[filters, setFilters]}
							loading={loading}
							project={project}
							sessionId={sessionId}
						/>
					</div>

					{!!activationKeysByStatusPaginated.length && (
						<Table
							checkboxConfig={{
								checkboxesChecked: activationKeysIdChecked,
								setCheckboxesChecked: setActivationKeysIdChecked,
							}}
							className="border-0 cp-dxp-activation-key-table"
							columns={COLUMNS}
							hasCheckbox
							hasPagination
							isLoading={loading}
							paginationConfig={paginationConfig}
							rows={activationKeysByStatusPaginated.map(
								(activationKey) =>
									getActivationKeysRows(activationKey)
							)}
						/>
					)}

					{!activationKeysByStatusPaginated.length &&
						(filters.searchTerm || filters.hasValue) && (
							<div className="d-flex justify-content-center py-4">
								No activation keys found with this search
								criteria.
							</div>
						)}
				</div>
			</ClayTooltipProvider>

			{!!downloadStatus && (
				<DownloadAlert
					downloadStatus={downloadStatus}
					message={
						ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT[
							downloadStatus
						]
					}
					setDownloadStatus={setDownloadStatus}
				/>
			)}
		</>
	);
};

export default ActivationKeysTable;
