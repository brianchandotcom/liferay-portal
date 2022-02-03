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

import {ClayTooltipProvider} from '@clayui/tooltip';
import {useEffect, useState} from 'react';
import {IconButton} from '../../../../common/components';
import RoundedGroupButtons from '../../../../common/components/RoundedGroupButtons';
import Table from '../../../../common/components/Table';
import {useApplicationProvider} from '../../../../common/context/AppPropertiesProvider';
import {getActivationLicenseKey} from '../../../../common/services/liferay/rest/raysource/LicenseKeys';
import {useCustomerPortal} from '../../context';
import {
	ACTIVATION_KEYS_LICENSE_FILTER_TYPES,
	ACTIVATION_STATUS,
	COLUMNS,
} from './utils/constants';
import {
	EnvironmentTypeColumn,
	ExpirationDateColumn,
	KeyTypeColumn,
	StatusColumn,
} from './utils/constants/columns-definitions';
import {downloadActivationLicenseKey} from './utils/downloadActivationLicenseKey';
import {getGroupButtons} from './utils/getGroupButtons';
import {getTooltipTitles} from './utils/getTooltipTitles';

const MAX_ITEMS = 9999;
const PAGE = 1;

const DXPActivationKeysTable = () => {
	const [{assetsPath, project, sessionId}] = useCustomerPortal();
	const {licenseKeyDownloadURL} = useApplicationProvider();

	const [activationKeys, setActivationKeys] = useState([]);
	const [statusBar, setStatusBar] = useState({});

	const [activationKeysFiltered, setActivationKeysFiltered] = useState([]);
	const [totalCount, setTotalCount] = useState(5);

	const [filterStatusBar, setFilterStatusBar] = useState('all');

	const [activePage, setActivePage] = useState(1);
	const [itemsPerPage, setItemsPerPage] = useState(5);
	const [isLoadingActivationKeys, setIsLoadingActivationKeys] = useState(
		false
	);

	useEffect(() => {
		if (filterStatusBar) {
			setActivePage(1);
		}
	}, [filterStatusBar]);

	useEffect(() => {
		setIsLoadingActivationKeys(true);
		const fetchActivationKeysData = async () => {
			const {items} = await getActivationLicenseKey(
				project.accountKey,
				licenseKeyDownloadURL,
				encodeURI('active eq true'),
				PAGE,
				MAX_ITEMS,
				sessionId
			);
			if (items) {
				setActivationKeys(items);
				setStatusBar({
					activeTotalCount: items.filter((activationKey) =>
						ACTIVATION_KEYS_LICENSE_FILTER_TYPES.active(
							activationKey
						)
					).length,
					allTotalCount: items.length,
					expiredTotalCount: items.filter((activationKey) =>
						ACTIVATION_KEYS_LICENSE_FILTER_TYPES.expired(
							activationKey
						)
					).length,
					notActiveTotalCount: items.filter((activationKey) =>
						ACTIVATION_KEYS_LICENSE_FILTER_TYPES.notActivated(
							activationKey
						)
					).length,
				});
			}

			setIsLoadingActivationKeys(false);
		};
		fetchActivationKeysData();
	}, [licenseKeyDownloadURL, project, sessionId]);

	useEffect(() => {
		if (activationKeys.length) {
			const activationKeysFilterData = activationKeys.filter(
				(activationKey) =>
					ACTIVATION_KEYS_LICENSE_FILTER_TYPES[filterStatusBar]
						? ACTIVATION_KEYS_LICENSE_FILTER_TYPES[filterStatusBar](
								activationKey
						  )
						: Boolean
			);
			setTotalCount(activationKeysFilterData.length || 5);
			const activationKeysFilterByPage = activationKeysFilterData.slice(
				itemsPerPage * activePage - itemsPerPage,
				itemsPerPage * activePage
			);
			setActivationKeysFiltered(
				activationKeysFilterByPage.length
					? activationKeysFilterByPage
					: activationKeysFilterData
			);
		}
	}, [activationKeys, activePage, filterStatusBar, itemsPerPage]);

	return (
		<div>
			<div className="align-center cp-dxp-activation-key-container d-flex justify-content-between">
				<h3 className="m-0">Activation Keys</h3>

				<RoundedGroupButtons
					groupButtons={[
						getGroupButtons(
							ACTIVATION_STATUS.all,
							statusBar?.allTotalCount
						),
						getGroupButtons(
							ACTIVATION_STATUS.active,
							statusBar?.activeTotalCount
						),
						getGroupButtons(
							ACTIVATION_STATUS.notActivated,
							statusBar?.notActiveTotalCount
						),
						getGroupButtons(
							ACTIVATION_STATUS.expired,
							statusBar?.expiredTotalCount
						),
					]}
					handleOnChange={(value) => {
						setFilterStatusBar(value);
					}}
				/>
			</div>

			<ClayTooltipProvider
				contentRenderer={(props) => {
					return getTooltipTitles(props);
				}}
				delay={100}
			>
				<Table
					className="border-0 cp-dxp-activation-key-table mt-5"
					columns={COLUMNS}
					hasCheckbox
					hasPagination
					isLoading={isLoadingActivationKeys}
					paginationConfig={{
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
						totalCount,
					}}
					rows={activationKeysFiltered.map((activationKey) => ({
						download: (
							<IconButton
								displayType="null"
								onClick={() =>
									downloadActivationLicenseKey(
										activationKey.id,
										licenseKeyDownloadURL,
										sessionId
									)
								}
								small
								symbol="download"
							/>
						),
						envName: (
							<div
								title={[
									activationKey.name,
									activationKey.description,
								]}
							>
								<p className="font-weight-bold m-0 text-neutral-10 text-truncate">
									{activationKey.name}
								</p>

								<p className="font-weight-normal m-0 text-neutral-7 text-paragraph-sm text-truncate">
									{activationKey.description}
								</p>
							</div>
						),
						envType: (
							<EnvironmentTypeColumn
								activationKey={activationKey}
							/>
						),
						expirationDate: (
							<ExpirationDateColumn
								activationKey={activationKey}
							/>
						),
						keyType: (
							<KeyTypeColumn
								activationKey={activationKey}
								assetsPath={assetsPath}
							/>
						),
						status: <StatusColumn activationKey={activationKey} />,
					}))}
				/>
			</ClayTooltipProvider>
		</div>
	);
};

export default DXPActivationKeysTable;
