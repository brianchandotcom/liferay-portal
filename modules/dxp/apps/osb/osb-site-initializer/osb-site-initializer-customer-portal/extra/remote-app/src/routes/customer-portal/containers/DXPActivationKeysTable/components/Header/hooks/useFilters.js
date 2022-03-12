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

import {useEffect, useState} from 'react';
import {ACTIVATION_STATUS} from '../../../utils/constants/activationStatus';

export default function useFilters(setFilterTerm) {
	const DEFAULT_FILTER = 'active eq true';
	const COMPLIMENTARY = 'Complimentary';
	const SUBSCRIPTION = 'Subscription';

	const [filters, setFilters] = useState({
		environmentTypes: [],
		expirationDate: {
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
		startDate: {
			onOrAfter: undefined,
			onOrBefore: undefined,
		},
		status: [],
	});

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

		if (Object.values(filters.expirationDate).some((date) => !!date)) {
			const filterDates = [];

			if (filters.expirationDate.onOrAfter) {
				filterDates.push(
					`expirationDate ge ${filters.expirationDate.onOrAfter}`
				);
			}

			if (filters.expirationDate.onOrBefore) {
				filterDates.push(
					`expirationDate lt ${filters.expirationDate.onOrBefore}`
				);
			}

			initialFilter = initialFilter.concat(
				` and (${filterDates.join(' and ')})`
			);
		}

		if (Object.values(filters.startDate).some((date) => !!date)) {
			const filterDates = [];

			if (filters.startDate.onOrAfter) {
				filterDates.push(`startDate ge ${filters.startDate.onOrAfter}`);
			}

			if (filters.startDate.onOrBefore) {
				filterDates.push(
					`startDate lt ${filters.startDate.onOrBefore}`
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
		filters.expirationDate,
		filters.instanceSizes,
		filters.keyType,
		filters.productVersions,
		filters.searchTerm,
		filters.startDate,
		filters.status,
		setFilterTerm,
	]);

	return [filters, setFilters];
}
