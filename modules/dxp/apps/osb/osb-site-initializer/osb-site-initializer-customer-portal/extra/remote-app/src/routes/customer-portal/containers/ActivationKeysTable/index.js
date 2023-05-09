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
import {useLocation, useOutletContext} from 'react-router-dom';
import i18n from '../../../../common/I18n';
import RoundedGroupButtons from '../../../../common/components/RoundedGroupButtons';
import Table from '../../../../common/components/Table';
import {useAppPropertiesContext} from '../../../../common/contexts/AppPropertiesContext';
import {ALERT_DOWNLOAD_TYPE} from '../../utils/constants/alertDownloadType';
import DownloadAlert from './components/DownloadAlert';
import ActivationKeysTableHeader from './components/Header';
import useFilters from './components/Header/hooks/useFilters';
import ModalKeyDetails from './components/ModalKeyDetails';
import useGetActivationKeysData from './hooks/useGetActivationKeysData';
import usePagination from './hooks/usePagination';
import useStatusCountNavigation from './hooks/useStatusCountNavigation';
import {ACTIVATE_COLUMNS} from './utils/constants';
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

const messageNewKeyGeneratedAlert = i18n.translate(
	'activation-key-was-generated-successfully'
);

const messageDeactivateKey = i18n.translate(
	'activation-keys-were-deactivated-successfully'
);

const ActivationKeysTable = ({
	initialFilter,
	productName,
	project,
	sessionId,
}) => {
	const {provisioningServerAPI} = useAppPropertiesContext();
	const [isVisibleModal, setIsVisibleModal] = useState(false);
	const [downloadStatus, setDownloadStatus] = useState('');
	const {state} = useLocation();
	const {setHasQuickLinksPanel, setHasSideMenu} = useOutletContext();

	useEffect(() => {
		setHasQuickLinksPanel(true);
		setHasSideMenu(true);
	}, [setHasSideMenu, setHasQuickLinksPanel]);

	const [
		newKeyGeneratedAlertStatus,
		setNewKeyGeneratedAlertStatus,
	] = useState(state?.newKeyGeneratedAlert ? 'success' : '');

	const [deactivatedKeyAlertStatus, setDeactivatedKeyAlertStatus] = useState(
		state?.deactivateKeyAlert ? 'success' : ''
	);

	const {
		activationKeysState: [activationKeys, setActivationKeys],
		loading,
		setFilterTerm,
	} = useGetActivationKeysData(project, initialFilter);

	const {
		navigationGroupButtons,
		statusfilterByTitle: [statusFilter, setStatusFilter],
	} = useStatusCountNavigation(activationKeys);

	const {activationKeysByStatusPaginated, paginationConfig} = usePagination(
		activationKeys,
		statusFilter
	);

	const [filters, setFilters] = useFilters(
		setFilterTerm,
		productName,
		initialFilter
	);

	const [currentActivationKey, setCurrentActivationKey] = useState();
	const [activationKeysIdChecked, setActivationKeysIdChecked] = useState([]);

	const {observer, onClose} = useModal({
		onClose: () => setIsVisibleModal(false),
	});

	const activationKeysByStatusPaginatedChecked = useMemo(
		() =>
			activationKeys.filter(({id}) =>
				activationKeysIdChecked.includes(id)
			) || [],
		[activationKeys, activationKeysIdChecked]
	);

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
							provisioningServerAPI,
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
		[handleAlertStatus, provisioningServerAPI, project.name, sessionId]
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
					<div className="align-center cp-activation-key-container d-flex justify-content-between mb-2">
						<h3 className="m-0">
							{i18n.translate('activation-keys')}
						</h3>

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
							productName={productName}
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
							className="border-0 cp-activation-key-table"
							columns={ACTIVATE_COLUMNS}
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
								{i18n.translate(
									'no-activation-keys-found-with-this-search-criteria'
								)}
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

			{!!newKeyGeneratedAlertStatus && (
				<DownloadAlert
					downloadStatus={newKeyGeneratedAlertStatus}
					message={messageNewKeyGeneratedAlert}
					setDownloadStatus={setNewKeyGeneratedAlertStatus}
				/>
			)}

			{!!deactivatedKeyAlertStatus && (
				<DownloadAlert
					downloadStatus={deactivatedKeyAlertStatus}
					message={messageDeactivateKey}
					setDownloadStatus={setDeactivatedKeyAlertStatus}
				/>
			)}
		</>
	);
};

export default ActivationKeysTable;
