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

import {useEffect, useRef, useState} from 'react';
import {Button} from '../../../../../../common/components';
import DropDownWithDrillDown from '../../../../components/DropDownWithDrillDown';
import {
	getDoesNotExpire,
	getEnvironmentType,
	getInstanceSize,
	getProductDescription,
	getStatusActivationTag,
	hasVirtualCluster,
} from '../../utils';
import {ACTIVATION_STATUS} from '../../utils/constants';
import CheckboxFilter from './components/CheckboxFilter';
import DateFilter from './components/DateFilter';
import ExpirationDate from './components/ExpirationDate';
import KeyTypeFilter from './components/KeyType';
import Search from './components/Search';
import getAvailableFieldsCheckboxs from './components/utils/getAvailableFieldsCheckboxs';

const DEFAULT_FILTER = 'active eq true';
const COMPLIMENTARY = 'Complimentary';
const SUBSCRIPTION = 'Subscription';

const Filter = ({activationKeys, setFilterTerm}) => {
	const countFetchActivationKeysRef = useRef(0);

	const [filters, setFilters] = useState({
		environmentTypes: [],
		expirationDates: {
			onOrAfter: undefined,
			onOrBefore: undefined,
		},
		instanceSizes: [],
		keyType: {
			hasOnPremise: undefined,
			hasVirtualCluster: undefined,
			maxNodes: '',
			minNodes: '',
		},
		productVersions: [],
		searchTerm: '',
		startDates: {
			onOrAfter: undefined,
			onOrBefore: undefined,
		},
		status: [],
	});

	const [availableFields, setAvailableFields] = useState({
		environmentTypes: [],
		hasDNE: false,
		hasVirtualCluster: false,
		instanceSizes: [],
		productVersions: [],
		status: [],
	});

	useEffect(() => {
		if (activationKeys) {
			countFetchActivationKeysRef.current = ++countFetchActivationKeysRef.current;
		}
	}, [activationKeys]);

	useEffect(() => {
		if (countFetchActivationKeysRef?.current < 3) {
			setAvailableFields(() => ({
				environmentTypes: [
					...getAvailableFieldsCheckboxs(
						activationKeys,
						({productName}) => getEnvironmentType(productName)
					),
					...getAvailableFieldsCheckboxs(
						activationKeys,
						({complimentary}) =>
							getProductDescription(complimentary)
					),
				],
				hasDNE: activationKeys?.some(({expirationDate}) =>
					getDoesNotExpire(expirationDate)
				),
				hasVirtualCluster: activationKeys?.some(({licenseEntryType}) =>
					hasVirtualCluster(licenseEntryType)
				),
				instanceSizes: getAvailableFieldsCheckboxs(
					activationKeys,
					({sizing}) => +getInstanceSize(sizing)
				),
				productVersions: getAvailableFieldsCheckboxs(
					activationKeys,
					({productVersion}) => productVersion
				),
				status: getAvailableFieldsCheckboxs(
					activationKeys,
					(activationKey) =>
						getStatusActivationTag(activationKey)?.title
				),
			}));
		}
	}, [activationKeys]);

	useEffect(() => {
		let initialFilter = DEFAULT_FILTER;

		if (filters.searchTerm) {
			const searchTermFilter = `(contains(name, '${filters.searchTerm}') or contains(description, '${filters.searchTerm}') or contains(hostName, '${filters.searchTerm}'))`;

			initialFilter = initialFilter.concat(` and ${searchTermFilter}`);
		}

		if (filters.instanceSizes.length) {
			const instanceSizesFilter = `(${filters.instanceSizes.reduce(
				(accumulatorInstanceSizesFilter, instanceSize, index) => {
					return `${accumulatorInstanceSizesFilter}${
						index > 0 ? ' or ' : ''
					}instanceSize eq 'Sizing ${instanceSize}'`;
				},
				''
			)})`;

			initialFilter = initialFilter.concat(` and ${instanceSizesFilter}`);
		}

		if (filters.productVersions.length) {
			const productVersionsFilter = `(${filters.productVersions.reduce(
				(accumulatorProductVersionsFilter, productVersion, index) => {
					return `${accumulatorProductVersionsFilter}${
						index > 0 ? ' or ' : ''
					}productVersion eq '${productVersion}'`;
				},
				''
			)})`;

			initialFilter = initialFilter.concat(
				` and ${productVersionsFilter}`
			);
		}

		if (filters.status.length) {
			const now = new Date().toISOString();
			const statusFilter = filters.status.reduce(
				(accumulatorStatusFilter, status, index) => {
					let filter = '';
					if (status === ACTIVATION_STATUS.activated.title) {
						filter = `(startDate le ${now} and expirationDate gt ${now})`;
					}

					if (status === ACTIVATION_STATUS.expired.title) {
						filter = `expirationDate lt ${now}`;
					}

					if (status === ACTIVATION_STATUS.notActivated.title) {
						filter = `startDate gt ${now}`;
					}

					return `${accumulatorStatusFilter}${
						index > 0 ? ' or ' : ''
					}${filter}`;
				},
				''
			);

			initialFilter = initialFilter.concat(` and ${statusFilter}`);
		}

		if (filters.environmentTypes.length) {
			const environmentTypesFilter = filters.environmentTypes.reduce(
				(accumulatorEnvironmentTypesFilter, environmentType, index) => {
					if (environmentType === COMPLIMENTARY) {
						return `${accumulatorEnvironmentTypesFilter}${
							index > 0 ? ' or ' : ''
						}complimentary eq true`;
					}

					if (environmentType === SUBSCRIPTION) {
						return `${accumulatorEnvironmentTypesFilter}${
							index > 0 ? ' or ' : ''
						}complimentary eq false`;
					}

					return `${accumulatorEnvironmentTypesFilter}${
						index > 0 ? ' or ' : ''
					}contains(productName, '${environmentType}')`;
				},
				''
			);

			initialFilter = initialFilter.concat(
				` and (${environmentTypesFilter})`
			);
		}

		if (Object.values(filters.expirationDates).some((date) => !!date)) {
			const filterDates = [];

			if (filters.expirationDates.onOrAfter) {
				filterDates.push(
					`expirationDate ge ${filters.expirationDates.onOrAfter}`
				);
			}

			if (filters.expirationDates.onOrBefore) {
				filterDates.push(
					`expirationDate lt ${filters.expirationDates.onOrBefore}`
				);
			}

			initialFilter = initialFilter.concat(
				` and (${filterDates.join(' and ')})`
			);
		}

		if (Object.values(filters.startDates).some((date) => !!date)) {
			const filterDates = [];

			if (filters.startDates.onOrAfter) {
				filterDates.push(
					`startDate ge ${filters.startDates.onOrAfter}`
				);
			}

			if (filters.startDates.onOrBefore) {
				filterDates.push(
					`startDate lt ${filters.startDates.onOrBefore}`
				);
			}

			initialFilter = initialFilter.concat(
				` and (${filterDates.join(' and ')})`
			);
		}

		if (Object.values(filters.keyType).every((value) => !isNaN(value))) {
			const filtersKeyType = [];

			if (
				!isNaN(filters.keyType.hasOnPremise) &&
				filters.keyType.hasOnPremise
			) {
				filtersKeyType.push("licenseEntryType ne 'virtual-cluster'");
			}

			if (
				!isNaN(filters.keyType.hasVirtualCluster) &&
				filters.keyType.hasVirtualCluster
			) {
				filtersKeyType.push("licenseEntryType eq 'virtual-cluster'");
			}

			if (filters.keyType.maxNodes) {
				filtersKeyType.push(
					`maxClusterNodes le ${filters.keyType.maxNodes}`
				);
			}

			if (filters.keyType.minNodes) {
				filtersKeyType.push(
					`maxClusterNodes ge ${filters.keyType.minNodes}`
				);
			}

			if (filtersKeyType.length) {
				initialFilter = initialFilter.concat(
					` and (${filtersKeyType.join(' and ')})`
				);
			}
		}

		setFilterTerm(`${initialFilter}`);
	}, [
		filters.environmentTypes,
		filters.expirationDates,
		filters.instanceSizes,
		filters.keyType,
		filters.productVersions,
		filters.searchTerm,
		filters.startDates,
		filters.status,
		setFilterTerm,
	]);

	return (
		<div className="d-flex flex-column">
			<div className="d-flex">
				<Search setFilters={setFilters} />

				<DropDownWithDrillDown
					initialActiveMenu="x0a0"
					menus={{
						x0a0: [
							{child: 'x0a1', title: 'Key Type'},
							{child: 'x0a2', title: 'Environment Type'},
							{child: 'x0a4', title: 'Start Date'},
							{
								child: 'x0a5',
								title: 'Expiration Date',
							},
							{child: 'x0a6', title: 'Status'},
							{child: 'x0a7', title: 'Product Version'},
							{
								child: 'x0a8',
								title: 'Instance Size',
							},
						],
						x0a1: [
							{
								child: (
									<KeyTypeFilter
										hasVirtualCluster={
											availableFields.hasVirtualCluster
										}
										setFilters={setFilters}
									/>
								),
								type: 'component',
							},
						],
						x0a2: [
							{
								child: (
									<CheckboxFilter
										availableItems={
											availableFields.environmentTypes
										}
										setFilters={setFilters}
										updateFilters={(checkedItems) =>
											setFilters((previousFilters) => ({
												...previousFilters,
												environmentTypes: checkedItems,
											}))
										}
									/>
								),
								type: 'component',
							},
						],

						x0a4: [
							{
								child: (
									<DateFilter
										updateFilters={(
											onOrAfter,
											onOrBefore
										) =>
											setFilters((previousFilters) => ({
												...previousFilters,
												startDates: {
													onOrAfter,
													onOrBefore,
												},
											}))
										}
									/>
								),
								type: 'component',
							},
						],
						x0a5: [
							{
								child: (
									<ExpirationDate
										hasDNE={availableFields.hasDNE}
										setFilters={setFilters}
									/>
								),
								type: 'component',
							},
						],
						x0a6: [
							{
								child: (
									<CheckboxFilter
										availableItems={availableFields.status}
										updateFilters={(checkedItems) =>
											setFilters((previousFilters) => ({
												...previousFilters,
												status: checkedItems,
											}))
										}
									/>
								),
								type: 'component',
							},
						],
						x0a7: [
							{
								child: (
									<CheckboxFilter
										availableItems={
											availableFields.productVersions
										}
										updateFilters={(checkedItems) =>
											setFilters((previousFilters) => ({
												...previousFilters,
												productVersions: checkedItems,
											}))
										}
									/>
								),
								type: 'component',
							},
						],
						x0a8: [
							{
								child: (
									<CheckboxFilter
										availableItems={
											availableFields.instanceSizes
										}
										setFilters={setFilters}
										updateFilters={(checkedItems) =>
											setFilters((previousFilters) => ({
												...previousFilters,
												instanceSizes: checkedItems,
											}))
										}
									/>
								),
								type: 'component',
							},
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
