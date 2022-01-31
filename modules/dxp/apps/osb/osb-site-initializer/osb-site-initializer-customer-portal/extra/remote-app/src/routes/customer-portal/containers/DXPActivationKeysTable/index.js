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
import ClaySticker from '@clayui/sticker';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {useEffect, useMemo, useState} from 'react';
import {IconButton} from '../../../../common/components';
import RoundedGroupButtons from '../../../../common/components/RoundedGroupButtons';
import Table from '../../../../common/components/Table';
import {useApplicationProvider} from '../../../../common/context/ApplicationPropertiesProvider';
import {fetchActivationKeysLicense} from '../../../../common/services/liferay/raysource-api';
import {getCurrentEndDate} from '../../../../common/utils';
import {useCustomerPortal} from '../../context';
import {getPascalCase} from '../../utils/getPascalCase';

const ACTIVATION_KEYS_LICENSE_FILTER_TYPES = {
	active: ({expirationDate, startDate}) =>
		new Date(startDate) < new Date() &&
		new Date(expirationDate) > new Date(),
	expired: ({expirationDate}) => new Date(expirationDate) < new Date(),
	notActivated: ({startDate}) => new Date(startDate) > new Date(),
};

const ACTIVATION_STATUS = {
	active: {
		color: 'success',
		id: 'active',
		title: 'Active',
	},
	all: {
		color: 'none',
		id: 'all',
		title: 'All',
	},
	expired: {
		color: 'danger',
		id: 'expired',
		title: 'Expired',
	},
	notActivated: {
		color: 'info',
		id: 'notActivated',
		title: 'Not Activated',
	},
};

const VIRTUAL_CLUSTER = 'virtual-cluster';

const DXPActivationKeysTable = () => {
	const [{assetsPath, project, sessionId}] = useCustomerPortal();
	const {licenseKeyDownloadURL} = useApplicationProvider();

	const [activationKeys, setActivationKeys] = useState([]);
	const [activationKeysFiltered, setActivationKeysFiltered] = useState([]);
	const [totalCount, setTotalCount] = useState(0);

	const [filterStatusBar, setFilterStatusBar] = useState('all');

	const [activePage, setActivePage] = useState(1);
	const [itemsPerPage, setItemsPerPage] = useState(5);
	const [isLoadingActivationKeys, setIsLoadingActivationKeys] = useState(
		false
	);

	useEffect(() => {
		setIsLoadingActivationKeys(true);
		const fetchActivationKeysData = async () => {
			const {items} = await fetchActivationKeysLicense(
				project.accountKey,
				licenseKeyDownloadURL,
				encodeURI('active eq true'),
				1,
				9999,
				sessionId
			);
			if (items) {
				setActivationKeys(items);
			}

			setIsLoadingActivationKeys(false);
		};
		fetchActivationKeysData();
	}, [licenseKeyDownloadURL, project.accountKey, sessionId]);

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
			setTotalCount(activationKeysFilterData.length);
			setActivationKeysFiltered(
				activationKeysFilterData.slice(
					itemsPerPage * activePage - itemsPerPage,
					itemsPerPage * activePage
				)
			);
		}
	}, [activationKeys, activePage, filterStatusBar, itemsPerPage]);

	const columns = useMemo(
		() => [
			{
				accessor: 'envName',
				bodyClass: 'border-0',
				expanded: true,
				header: {
					description: 'Description',
					name: 'Environment Name',
					styles: 'bg-transparent',
				},
			},
			{
				accessor: 'keyType',
				bodyClass: 'border-0',
				header: {
					description: 'Host Name / Cluster Size',
					name: 'Key Type',
					noWrap: true,
					styles: 'bg-transparent',
				},
			},
			{
				accessor: 'envType',
				bodyClass: 'border-0',
				header: {
					name: 'Environment Type',
					styles: 'bg-transparent text-neutral-10 font-weight-bold',
				},
			},
			{
				accessor: 'expirationDate',
				bodyClass: 'border-0',
				header: {
					name: 'Exp. Date',
					styles: 'bg-transparent text-neutral-10 font-weight-bold',
				},
				noWrap: true,
			},
			{
				accessor: 'status',
				align: 'center',
				bodyClass: 'border-0',
				header: {
					name: 'Status',
					styles: 'bg-transparent text-neutral-10 font-weight-bold',
				},
			},
			{
				accessor: 'download',
				align: 'center',
				bodyClass: 'border-0',
				header: {
					name: <ClayIcon symbol="download" />,
					styles: 'bg-transparent text-neutral-10 font-weight-bold',
				},
			},
		],
		[]
	);

	return (
		<div>
			<div className="align-center cp-dxp-activation-key-container d-flex justify-content-between">
				<h3 className="m-0">Activation Keys</h3>

				<RoundedGroupButtons
					groupButtons={[
						{
							label: `${ACTIVATION_STATUS.all.title} (${
								activationKeys?.statusBar?.allTotalCount || 0
							})`,
							value: ACTIVATION_STATUS.all.id,
						},
						{
							label: `${ACTIVATION_STATUS.active.title} (${
								activationKeys?.statusBar?.activeTotalCount || 0
							})`,
							value: ACTIVATION_STATUS.active.id,
						},
						{
							label: `${ACTIVATION_STATUS.notActivated.title} (${
								activationKeys?.statusBar
									?.notActiveTotalCount || 0
							})`,
							value: ACTIVATION_STATUS.notActivated.id,
						},
						{
							label: `${ACTIVATION_STATUS.expired.title} (${
								activationKeys?.statusBar?.expiredTotalCount ||
								0
							})`,
							value: ACTIVATION_STATUS.expired.id,
						},
					]}
					onChange={(value) => {
						setFilterStatusBar(value);
						setItemsPerPage(5);
					}}
				/>
			</div>

			<ClayTooltipProvider
				contentRenderer={(props) => {
					const activationNames = props.title.split(',');

					if (activationNames.length) {
						return (
							<div>
								<p className="font-weight-bold m-0">
									{activationNames[0]}
								</p>

								<p className="font-weight-normal m-0 text-paragraph-sm">
									{activationNames[1]}
								</p>
							</div>
						);
					}

					return activationNames[0];
				}}
				delay={100}
			>
				<Table
					className="border-0 cp-dxp-activation-key-table mt-5"
					columns={columns}
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
						envType: (() => {
							const productName = getPascalCase(
								activationKey.licenseEntryType
							)
								.split('-')
								.join(' ');

							const productDescription = activationKey.complimentary
								? 'Complimentary'
								: 'Subscription';

							return (
								<div>
									<p className="font-weight-bold m-0 text-neutral-10">
										{productName.replace(
											'Production',
											'Prod'
										)}
									</p>

									<p className="font-weight-normal m-0 text-neutral-7 text-paragraph-sm">
										{productDescription}
									</p>
								</div>
							);
						})(),
						expirationDate: (() => {
							const unlimitedLicenseDate = new Date().setFullYear(
								new Date().getFullYear() + 100
							);
							if (
								new Date(activationKey.expirationDate) >=
								new Date(unlimitedLicenseDate)
							) {
								return (
									<p
										className="cp-dxp-activation-key-cell-small font-weight-bold m-0 text-neutral-10"
										title={['This key does not expire']}
									>
										DNE
									</p>
								);
							}

							return (
								<p className="font-weight-bold m-0 text-neutral-10">
									{getCurrentEndDate(
										activationKey.expirationDate
									)}
								</p>
							);
						})(),
						keyType: (() => {
							const hasVirtualCluster =
								activationKey.licenseEntryType ===
								VIRTUAL_CLUSTER;

							return (
								<div className="align-items-start d-flex">
									{hasVirtualCluster && (
										<img
											className="mr-1"
											src={`${assetsPath}/assets/virtual_cluster.svg`}
										/>
									)}

									<div>
										<p className="font-weight-bold m-0 text-neutral-10">
											{hasVirtualCluster
												? 'Virtual Cluster'
												: 'On-Premise'}
										</p>

										<p className="font-weight-normal m-0 text-neutral-7 text-paragraph-sm text-truncate">
											{hasVirtualCluster
												? `${activationKey.maxClusterNodes} Cluster Nodes (Keys)`
												: activationKey.hostName || '-'}
										</p>
									</div>
								</div>
							);
						})(),
						status: (() => {
							let activationStatus = ACTIVATION_STATUS.active;

							if (
								new Date() < new Date(activationKey.startDate)
							) {
								activationStatus =
									ACTIVATION_STATUS.notActivated;
							} else if (
								new Date() >
								new Date(activationKey.expirationDate)
							) {
								activationStatus = ACTIVATION_STATUS.expired;
							}

							return (
								<div
									className="w-100"
									title={[activationStatus.title]}
								>
									<ClaySticker
										className="bg-transparent"
										displayType={activationStatus.color}
										shape="circle"
										size="sm"
									>
										<ClayIcon symbol="circle" />
									</ClaySticker>
								</div>
							);
						})(),
					}))}
				/>
			</ClayTooltipProvider>
		</div>
	);
};

export default DXPActivationKeysTable;
