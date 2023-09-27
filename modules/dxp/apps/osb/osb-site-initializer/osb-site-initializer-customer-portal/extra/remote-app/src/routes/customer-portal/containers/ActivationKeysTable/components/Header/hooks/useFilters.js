/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import i18n from '~/common/I18n';
import {ACTIVATION_STATUS} from '../../../utils/constants/activationStatus';
import {INITIAL_FILTER} from '../../../utils/constants/initialFilter';

const COMPLIMENTARY = 'Complimentary';
const SUBSCRIPTION = 'Subscription';

export default function useFilters(setFilterTerm, productName, baseFilter) {
	const [filters, setFilters] = useState(INITIAL_FILTER);

	useEffect(() => {
		let initialFilter = `active eq true and ${baseFilter}`;
		let hasFilterPill = false;

		if (filters.searchTerm) {
			const seachTermSplitted = filters.searchTerm
				.split(' ')
				.map((term) => {
					return `(contains(name, '${term}') or contains(description, '${term}') or contains(hostName, '${term}') or ipAddresses/any(s:contains(s, '${term}')) or macAddresses/any(s:contains(s, '${term}')))`;
				})
				.join(' and ');

			initialFilter = initialFilter.concat(` and (${seachTermSplitted})`);
		}

		if (filters.instanceSizes.value.length) {
			hasFilterPill = true;

			const instanceSizesFilter = `(${filters.instanceSizes.value.reduce(
				(accumulatorInstanceSizesFilter, instanceSize, index) => {
					return `${accumulatorInstanceSizesFilter}${
						index > 0 ? ' or ' : ''
					}contains(sizing,'${instanceSize}')`;
				},
				''
			)})`;

			initialFilter = initialFilter.concat(` and ${instanceSizesFilter}`);
		}

		if (filters.productVersions.value.length) {
			hasFilterPill = true;

			const productVersionsFilter = `(${filters.productVersions.value.reduce(
				(accumulatorProductVersionsFilter, productVersion, index) => {
					return `${accumulatorProductVersionsFilter}${
						index > 0 ? ' or ' : ''
					}${productVersion
						.split(' ')
						.map((version) => {
							return `contains(productVersion, '${version}')`;
						})
						.join(' and ')}`;
				},
				''
			)})`;

			initialFilter = initialFilter.concat(
				` and ${productVersionsFilter}`
			);
		}

		if (filters.status.value.length) {
			hasFilterPill = true;

			const now = new Date().toISOString();
			const statusFilter = filters.status.value.reduce(
				(accumulatorStatusFilter, status, index) => {
					let filter = '';
					if (status === ACTIVATION_STATUS.expired.title) {
						filter = `(expirationDate lt ${now})`;
					}

					if (status === ACTIVATION_STATUS.activated.title) {
						filter = `(startDate le ${now} and expirationDate gt ${now})`;
					}

					if (status === ACTIVATION_STATUS.notActivated.title) {
						filter = `(startDate gt ${now})`;
					}

					return `${accumulatorStatusFilter}${
						index > 0 ? ' or ' : ''
					}${filter}`;
				},
				''
			);

			initialFilter = initialFilter.concat(` and (${statusFilter})`);
		}

		if (filters.environmentTypes.value.length) {
			hasFilterPill = true;

			const environmentTypesFilter = filters.environmentTypes.value.reduce(
				(accumulatorEnvironmentTypesFilter, environmentType, index) => {
					environmentType = i18n.translateForAPI(environmentType);

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

					if (
						environmentType === 'Production' &&
						!filters.environmentTypes.value.includes(
							'Non-Production'
						)
					) {
						return `${accumulatorEnvironmentTypesFilter}${
							index > 0 ? ' or ' : ''
						}contains(productName, '${environmentType}') and not contains(productName, 'Non-Production')`;
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

		if (
			Object.values(filters.expirationDate.value).some((date) => !!date)
		) {
			const filterDates = [];
			hasFilterPill = true;

			if (filters.expirationDate.value.onOrBefore) {
				filterDates.push(
					`expirationDate lt ${filters.expirationDate.value.onOrBefore.toISOString()}`
				);
			}
			if (filters.expirationDate.value.onOrAfter) {
				filterDates.push(
					`expirationDate ge ${filters.expirationDate.value.onOrAfter.toISOString()}`
				);
			}

			initialFilter = initialFilter.concat(
				` and (${filterDates.join(
					filters.expirationDate.value.onOrAfter >
						filters.expirationDate.value.onOrBefore
						? ' or '
						: ' and '
				)})`
			);
		}

		if (Object.values(filters.startDate.value).some((date) => !!date)) {
			const filterDates = [];
			hasFilterPill = true;

			if (filters.startDate.value.onOrBefore) {
				filterDates.push(
					`startDate lt ${filters.startDate.value.onOrBefore.toISOString()}`
				);
			}
			if (filters.startDate.value.onOrAfter) {
				filterDates.push(
					`startDate ge ${filters.startDate.value.onOrAfter.toISOString()}`
				);
			}

			initialFilter = initialFilter.concat(
				` and (${filterDates.join(
					filters.startDate.value.onOrAfter >
						filters.startDate.value.onOrBefore
						? ' or '
						: ' and '
				)})`
			);
		}

		if (
			Object.values(filters.keyType.value).every((value) => !isNaN(value))
		) {
			const filtersKeyType = [];
			let showOnPrem = false;

			if (
				!filters.keyType.value.hasOnPremise ||
				!(
					filters.keyType.value.hasVirtualCluster ||
					filters.keyType.value.hasCluster
				)
			) {
				if (
					filters.keyType.value.hasOnPremise &&
					!filters.keyType.value.maxNodes &&
					!filters.keyType.value.minNodes
				) {
					hasFilterPill = true;
					filtersKeyType.push('maxClusterNodes eq 0');
				}

				if (
					(filters.keyType.value.hasVirtualCluster ||
						filters.keyType.value.hasCluster) &&
					!filters.keyType.value.maxNodes &&
					!filters.keyType.value.minNodes
				) {
					hasFilterPill = true;
					filtersKeyType.push('maxClusterNodes gt 0');
				}
			}
			else {
				hasFilterPill = true;
			}

			if (filters.keyType.value.maxNodes) {
				hasFilterPill = true;

				filtersKeyType.push(
					`maxClusterNodes le ${filters.keyType.value.maxNodes}`
				);
				showOnPrem = filters.keyType.value.hasOnPremise;
			}

			if (filters.keyType.value.minNodes) {
				hasFilterPill = true;

				filtersKeyType.push(
					`maxClusterNodes ge ${filters.keyType.value.minNodes}`
				);
				showOnPrem = filters.keyType.value.hasOnPremise;
			}

			if (showOnPrem) {
				filtersKeyType.push(' or maxClusterNodes eq 0');
			}

			if (filtersKeyType.length) {
				const filtersKeyTypeSetted = filtersKeyType.reduce(
					(filtersAccumulator, filterKey, index) => {
						let filter = filterKey;
						if (!filterKey.startsWith(' or')) {
							filter = `${index > 0 ? ` and ${filter}` : filter}`;
						}

						return `${filtersAccumulator}${filter}`;
					},
					''
				);

				initialFilter = initialFilter.concat(
					` and (${filtersKeyTypeSetted})`
				);
			}
		}

		setFilters((previousFilter) => ({
			...previousFilter,
			hasValue: hasFilterPill,
		}));

		setFilterTerm(`${initialFilter}`);
	}, [
		baseFilter,
		filters.environmentTypes.value,
		filters.expirationDate.value,
		filters.instanceSizes.value,
		filters.keyType.value,
		filters.productVersions.value,
		filters.searchTerm,
		filters.startDate.value,
		filters.status.value,
		productName,
		setFilterTerm,
	]);

	return [filters, setFilters];
}
