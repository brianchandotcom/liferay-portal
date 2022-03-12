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
import {useEffect, useMemo, useState} from 'react';
import RoundedGroupButtons from '../../../../common/components/RoundedGroupButtons';
import Table from '../../../../common/components/Table';
import {useApplicationProvider} from '../../../../common/context/AppPropertiesProvider';
import {ALERT_DOWNLOAD_TYPE} from '../../utils/constants/alertDownloadType';
import {getActivationKeyDownload} from '../DXPActivationKeysTable/utils/getActivationKeyDownload';
import DownloadAlert from './components/DownloadAlert';
import DXPActivationKeysTableHeader from './components/Header';
import ModalKeyDetails from './components/ModalKeyDetails';
import useGetActivationKeysData from './hooks/useGetActivationKeysData';
import useStatusCountNavigation from './hooks/useStatusCountNavigation';
import {
	ACTIVATION_KEYS_LICENSE_FILTER_TYPES as FILTER_TYPES,
	COLUMNS,
} from './utils/constants';
import {ALERT_ACTIVATION_AGGREGATED_KEYS_DOWNLOAD_TEXT} from './utils/constants/alertAggregateKeysDownloadText';
import {
	EnvironmentTypeColumn,
	ExpirationDateColumn,
	KeyTypeColumn,
	StatusColumn,
} from './utils/constants/columns-definitions';
import {downloadActivationLicenseKey} from './utils/downloadActivationLicenseKey';
import {getTooltipContentRenderer} from './utils/getTooltipContentRenderer';

const DXPActivationKeysTable = ({project, sessionId}) => {
	const [downloadStatus, setDownloadStatus] = useState('');

	const [activePage, setActivePage] = useState(1);
	const [itemsPerPage, setItemsPerPage] = useState(5);
	const [currentTotalCount, setCurrentTotalCount] = useState(0);

	const [selectedActivationKey, setSelectedActivationKey] = useState();
	const [activationKeysIdChecked, setActivationKeysIdChecked] = useState([]);
	const [activationKeysFiltered, setActivationKeysFiltered] = useState([]);

	const [isVisibleModal, setIsVisibleModal] = useState(false);

	const {licenseKeyDownloadURL} = useApplicationProvider();
	const {activationKeys, loading, setFilterTerm} = useGetActivationKeysData(
		project,
		sessionId
	);
	const {
		navigationGroupButtons,
		statusfilterByTitle: [statusFilter, setStatusFilter],
	} = useStatusCountNavigation(activationKeys);

	const {observer, onClose} = useModal({
		onClose: () => setIsVisibleModal(false),
	});

	useEffect(() => {
		if (activationKeysFiltered?.length) {
			setActivationKeysIdChecked([]);
		}
	}, [activationKeysFiltered]);

	useEffect(() => {
		if (statusFilter) {
			setActivePage(1);
		}
	}, [statusFilter]);

	useEffect(() => {
		const activationKeysFiltered = activationKeys?.filter((activationKey) =>
			FILTER_TYPES[statusFilter](activationKey)
		);

		if (activationKeysFiltered) {
			setCurrentTotalCount(activationKeysFiltered.length);

			const activationKeysFilteredPerPage = activationKeysFiltered.slice(
				itemsPerPage * activePage - itemsPerPage,
				itemsPerPage * activePage
			);

			setActivationKeysFiltered(
				activationKeysFilteredPerPage?.length
					? activationKeysFilteredPerPage
					: activationKeysFiltered
			);
		}
	}, [activationKeys, activePage, itemsPerPage, statusFilter]);

	const paginationConfig = useMemo(
		() => ({
			activePage,
			itemsPerPage,
			labels: {
				paginationResults: 'Showing {0} to {1} of {2}',
				perPageItems: 'Show {0} Items',
				selectPerPageItems: '{0} Items',
			},
			listItemsPerPage: [
				{label: 5},
				{label: 10},
				{label: 20},
				{label: 50},
			],
			setActivePage,
			setItemsPerPage,
			showDeltasDropDown: true,
			totalCount: currentTotalCount,
		}),
		[activePage, currentTotalCount, itemsPerPage]
	);

	const handleAlertStatus = (hasSuccessfullyDownloadedKeys) => {
		setDownloadStatus(
			hasSuccessfullyDownloadedKeys
				? ALERT_DOWNLOAD_TYPE.success
				: ALERT_DOWNLOAD_TYPE.danger
		);
	};

	const getActivationKeysRows = (activationKey) => ({
		customClickOnRow: () => {
			setSelectedActivationKey(activationKey);
			setIsVisibleModal(true);
		},
		download: (
			<ButtonWithIcon
				displayType="null"
				onClick={() =>
					getActivationKeyDownload(
						activationKey.id,
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
		expirationDate: <ExpirationDateColumn activationKey={activationKey} />,
		id: activationKey.id,
		keyType: <KeyTypeColumn activationKey={activationKey} />,
		status: <StatusColumn activationKey={activationKey} />,
	});

	return (
		<>
			{isVisibleModal && (
				<ModalKeyDetails
					currentActivationKey={selectedActivationKey}
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
						<DXPActivationKeysTableHeader
							activationKeys={activationKeys}
							activationKeysFilteredState={[
								activationKeysFiltered,
								setActivationKeysFiltered,
							]}
							activationKeysIdChecked={activationKeysIdChecked}
							project={project}
							sessionId={sessionId}
							setFilterTerm={setFilterTerm}
						/>
					</div>

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
						rows={activationKeysFiltered.map((activationKey) =>
							getActivationKeysRows(activationKey)
						)}
					/>
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

export default DXPActivationKeysTable;
