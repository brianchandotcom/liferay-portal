/* eslint-disable no-console */
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

import {useEffect} from 'react';
import {Button} from '../../../../../../common/components';
import DropDownWithDrillDown from '../../../../components/DropDownWithDrillDown';
import {useActivationKeys} from '../../context';
import {actionTypes} from '../../context/reducer';
import {
	getEnvironmentType,
	getInstanceSize,
	getProductDescription,
	getStatusActivationTag,
	getsDoesNotExpire,
} from '../../utils';
import {getKeyType} from '../../utils/getKeyType';
import EnvironmentTypeFilter from './components/EnvironmentType';
import ExpirationDateFilter from './components/ExpirationDate';
import InstanceSizeFilter from './components/InstanceSize';
import KeyTypeFilter from './components/KeyType';
import ProductVersionFilter from './components/ProductVersion';
import Search from './components/Search';
import StartDateFilter from './components/StartDate';
import StatusFilter from './components/Status';

const Filter = () => {
	const [
		{activationKeys, toSearchAndFilterKeys},
		dispatch,
	] = useActivationKeys();
	console.log(
		'🚀 ~ file: index.js ~ line 268 ~ Filter ~ toSearchAndFilterKeys',
		toSearchAndFilterKeys
	);

	useEffect(() => {
		const searchedActivationKeysByConditions = activationKeys.filter(
			(activationKey) =>
				activationKey.name
					.toLowerCase()
					.includes(toSearchAndFilterKeys.toSearchTerm) ||
				activationKey.description
					.toLowerCase()
					.includes(toSearchAndFilterKeys.toSearchTerm) ||
				activationKey.macAddresses
					.toLowerCase()
					.includes(toSearchAndFilterKeys.toSearchTerm) ||
				activationKey.ipAddresses
					.toLowerCase()
					.includes(toSearchAndFilterKeys.toSearchTerm) ||
				activationKey.hostName
					.toLowerCase()
					.includes(toSearchAndFilterKeys.toSearchTerm)
		);

		const filteredActivationKeysBySizing = toSearchAndFilterKeys.sizing[0]
			? searchedActivationKeysByConditions.filter((activationKey) =>
					toSearchAndFilterKeys.sizing.includes(
						getInstanceSize(activationKey.sizing)
					)
			  )
			: searchedActivationKeysByConditions;

		const filteredActivationKeysByProductVersion = toSearchAndFilterKeys
			.productVersion[0]
			? filteredActivationKeysBySizing.filter((activationKey) =>
					toSearchAndFilterKeys.productVersion.includes(
						activationKey.productVersion
					)
			  )
			: filteredActivationKeysBySizing;

		const filteredActivationKeysByStatus = toSearchAndFilterKeys.status[0]
			? filteredActivationKeysByProductVersion.filter((activationKey) =>
					toSearchAndFilterKeys.status.includes(
						getStatusActivationTag(activationKey)?.title
					)
			  )
			: filteredActivationKeysByProductVersion;

		const filteredActivationKeysByComplimentary = toSearchAndFilterKeys
			.complimentary[0]
			? filteredActivationKeysByStatus.filter((activationKey) =>
					toSearchAndFilterKeys.complimentary.includes(
						getProductDescription(activationKey.complimentary)
					)
			  )
			: filteredActivationKeysByStatus;

		const filteredActivationKeysByEnvironmentType = toSearchAndFilterKeys
			.productName[0]
			? filteredActivationKeysByComplimentary.filter((activationKey) =>
					toSearchAndFilterKeys.productName.includes(
						getEnvironmentType(activationKey.productName)
					)
			  )
			: filteredActivationKeysByComplimentary;

		const filteredActivationKeysByStartDateOnorAfter = isValidDate(
			toSearchAndFilterKeys.startDate[0]
		)
			? filteredActivationKeysByEnvironmentType.filter(
					(activationKey) =>
						new Date(activationKey.startDate) >=
						toSearchAndFilterKeys.startDate[0]
			  )
			: filteredActivationKeysByEnvironmentType;

		const filteredActivationKeysByStartDateOnorBefore = isValidDate(
			toSearchAndFilterKeys.startDate[1]
		)
			? filteredActivationKeysByStartDateOnorAfter.filter(
					(activationKey) =>
						new Date(activationKey.startDate) <=
						toSearchAndFilterKeys.startDate[1]
			  )
			: filteredActivationKeysByStartDateOnorAfter;

		const filteredActivationKeysByExpirationDateOnorAfter = isValidDate(
			toSearchAndFilterKeys.expirationDate[0]
		)
			? filteredActivationKeysByStartDateOnorBefore.filter(
					(activationKey) =>
						new Date(activationKey.expirationDate) >=
						toSearchAndFilterKeys.expirationDate[0]
			  )
			: filteredActivationKeysByStartDateOnorBefore;

		const filteredActivationKeysByExpirationDateOnorBefore = isValidDate(
			toSearchAndFilterKeys.expirationDate[1]
		)
			? filteredActivationKeysByExpirationDateOnorAfter.filter(
					(activationKey) =>
						new Date(activationKey.expirationDate) <=
						toSearchAndFilterKeys.expirationDate[1]
			  )
			: filteredActivationKeysByExpirationDateOnorAfter;

		const filteredActivationKeysByDoesNotExpire = toSearchAndFilterKeys
			.dne[0]
			? filteredActivationKeysByExpirationDateOnorBefore.filter(
					(activationKey) =>
						toSearchAndFilterKeys.dne.includes(
							getsDoesNotExpire(activationKey.expirationDate)
						)
			  )
			: filteredActivationKeysByExpirationDateOnorBefore;

		const filteredActivationKeysByOnPremise = toSearchAndFilterKeys
			.licenseEntryType[0]
			? filteredActivationKeysByDoesNotExpire.filter((activationKey) =>
					toSearchAndFilterKeys.licenseEntryType.includes(
						getKeyType(activationKey.licenseEntryType)
					)
			  )
			: filteredActivationKeysByDoesNotExpire;

		const filteredActivationKeysByVirtualClusterAndMinNodes = toSearchAndFilterKeys
			.maxClusterNodes[0]
			? filteredActivationKeysByOnPremise.filter(
					(activationKey) =>
						activationKey.maxClusterNodes >=
						toSearchAndFilterKeys.maxClusterNodes[0]
			  )
			: filteredActivationKeysByOnPremise;

		const filteredActivationKeysByVirtualClusterAndMaxNodes = toSearchAndFilterKeys
			.maxClusterNodes[1]
			? filteredActivationKeysByVirtualClusterAndMinNodes.filter(
					(activationKey) =>
						activationKey.maxClusterNodes <=
						toSearchAndFilterKeys.maxClusterNodes[1]
			  )
			: filteredActivationKeysByVirtualClusterAndMinNodes;

		dispatch({
			payload: filteredActivationKeysByVirtualClusterAndMaxNodes,
			type: actionTypes.UPDATE_ACTIVATION_KEYS_FILTERED_BY_CONDITIONS,
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		toSearchAndFilterKeys.toSearchTerm,
		toSearchAndFilterKeys.sizing,
		toSearchAndFilterKeys.productVersion,
		toSearchAndFilterKeys.status,
		toSearchAndFilterKeys.complimentary,
		toSearchAndFilterKeys.productName,
		toSearchAndFilterKeys.startDate,
		toSearchAndFilterKeys.expirationDate,
		toSearchAndFilterKeys.dne,
		toSearchAndFilterKeys.licenseEntryType,
		toSearchAndFilterKeys.maxClusterNodes,
	]);

	function isValidDate(date) {
		return date instanceof Date && !isNaN(date);
	}

	return (
		<div className="d-flex flex-column">
			<div className="d-flex">
				<Search />

				<DropDownWithDrillDown
					initialActiveMenu="x0a0"
					menus={{
						x0a0: [
							{child: 'x0a1', title: 'Key Type'},
							{child: 'x0a2', title: 'Environment Type'},
							{child: 'x0a4', title: 'Start Date'},
							{child: 'x0a5', title: 'Expiration Date'},
							{child: 'x0a6', title: 'Status'},
							{child: 'x0a7', title: 'Product Version'},
							{child: 'x0a8', title: 'Instance Size'},
						],

						x0a1: [{child: <KeyTypeFilter />, type: 'component'}],
						x0a2: [
							{
								child: <EnvironmentTypeFilter />,
								type: 'component',
							},
						],

						x0a4: [{child: <StartDateFilter />, type: 'component'}],
						x0a5: [
							{
								child: <ExpirationDateFilter />,
								type: 'component',
							},
						],
						x0a6: [
							{
								child: <StatusFilter />,
								type: 'component',
							},
						],
						x0a7: [
							{
								child: <ProductVersionFilter />,
								type: 'component',
							},
						],
						x0a8: [
							{child: <InstanceSizeFilter />, type: 'component'},
						],
					}}
					trigger={
						<Button
							borderless
							className="btn-secondary p-2"
							prependIcon="filter"
						>
							Filter
						</Button>
					}
				/>
			</div>
		</div>
	);
};

export default Filter;
