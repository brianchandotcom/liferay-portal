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
import {useActivationKeys} from '../../context';
import {actionTypes} from '../../context/reducer';
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
		{
			activationKeys,
			activationKeysFilteredByConditions,
			toSearchAndFilterKeys,
		},
		dispatch,
	] = useActivationKeys();

	useEffect(() => {
		searchAndFilter();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [toSearchAndFilterKeys.toSearchTerm, toSearchAndFilterKeys.sizing]);

	function searchAndFilter() {
		const searchedActivationKeysByConditions = activationKeys.filter(
			(activationKey) =>
				activationKey.name
					.toLowerCase()
					.includes(toSearchAndFilterKeys.toSearchTerm) +
				activationKey.description
					.toLowerCase()
					.includes(toSearchAndFilterKeys.toSearchTerm) +
				activationKey.macAddresses
					.toLowerCase()
					.includes(toSearchAndFilterKeys.toSearchTerm) +
				activationKey.ipAddresses
					.toLowerCase()
					.includes(toSearchAndFilterKeys.toSearchTerm) +
				activationKey.hostName
					.toLowerCase()
					.includes(toSearchAndFilterKeys.toSearchTerm)
		);

		const filteredActivationKeysByConditions = toSearchAndFilterKeys
			.sizing[0]
			? searchedActivationKeysByConditions.filter((activationKey) =>
					toSearchAndFilterKeys.sizing.includes(activationKey.sizing)
			  )
			: searchedActivationKeysByConditions;

		dispatch({
			payload: filteredActivationKeysByConditions,
			type: actionTypes.UPDATE_ACTIVATION_KEYS_FILTERED_BY_CONDITIONS,
		});
	}

	// eslint-disable-next-line no-console
	console.log('toSearchAndFilterKeys: ', toSearchAndFilterKeys);

	// eslint-disable-next-line no-console
	console.log(
		'activationKeysFilteredByConditions: ',
		activationKeysFilteredByConditions
	);

	return (
		<>
			<Search />

			<KeyTypeFilter />

			<EnvironmentTypeFilter />

			<StartDateFilter />

			<ExpirationDateFilter />

			<StatusFilter />

			<ProductVersionFilter />

			<InstanceSizeFilter />
		</>
	);
};
export default Filter;
