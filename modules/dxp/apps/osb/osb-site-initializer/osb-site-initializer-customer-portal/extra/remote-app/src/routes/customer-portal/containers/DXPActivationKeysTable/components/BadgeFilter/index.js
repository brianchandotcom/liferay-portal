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

import {useCallback} from 'react';
import Button from '../../../../../../common/components/Button';
import getCurrentEndDate from '../../../../../../common/utils/getCurrentEndDate';
import {INITIAL_FILTER} from '../../utils/constants/initialFilter';

const BadgeFilter = ({
	activationKeysLength,
	className,
	filtersState: [filters, setFilters],
}) => {
	const getDatesDisplay = useCallback((dateFilterState) => {
		const dateDisplays = [];

		if (dateFilterState.value?.onOrAfter) {
			const todayDNE = new Date();
			todayDNE.setFullYear(todayDNE.getFullYear() + 100);

			if (new Date(dateFilterState.value?.onOrAfter) >= todayDNE) {
				return 'DNE';
			}

			dateDisplays.push(
				getCurrentEndDate(dateFilterState.value?.onOrAfter)
			);
		}

		if (dateFilterState.value?.onOrBefore) {
			dateDisplays.push(
				getCurrentEndDate(dateFilterState.value?.onOrBefore)
			);
		}

		return dateDisplays.join(' – ');
	}, []);

	const getKeyTypeDisplay = useCallback((filterKeyType) => {
		const keyTypesDisplay = [];

		if (filterKeyType.value?.hasOnPremise) {
			keyTypesDisplay.push('On-Premise');
		}

		if (filterKeyType.value?.hasVirtualCluster) {
			if (
				!(
					filterKeyType.value?.minNodes ||
					filterKeyType.value?.maxNodes
				)
			) {
				keyTypesDisplay.push(`Virtual Cluster`);
			} else if (
				filterKeyType.value?.minNodes === filterKeyType.value?.maxNodes
			) {
				keyTypesDisplay.push(
					`Virtual Cluster (${filterKeyType.value?.minNodes} nodes)`
				);
			} else {
				const nodesDisplay = [];

				if (filterKeyType.value?.minNodes) {
					nodesDisplay.push(filterKeyType.value?.minNodes);
				}

				if (filterKeyType.value?.maxNodes) {
					nodesDisplay.push(filterKeyType.value?.maxNodes);
				}

				keyTypesDisplay.push(
					`Virtual Cluster (${nodesDisplay.join('-')} nodes)`
				);
			}
		}

		return (
			<p>
				<b>{filterKeyType.name}:</b>

				{keyTypesDisplay.join(', ')}
			</p>
		);
	}, []);

	return (
		<div className={className}>
			{!!filters.searchTerm && (
				<p>
					{activationKeysLength}
					result
					{activationKeysLength > 1 ? 's' : ''}
					for &quot;
					{filters.searchTerm}&quot;
				</p>
			)}

			{!!Object.values(filters.keyType.value).some(
				(value) => !!value
			) && (
				<>
					{getKeyTypeDisplay(filters.keyType)}{' '}
					<Button
						onClick={() =>
							setFilters((previousFilters) => ({
								...previousFilters,
								keyType: {
									...previousFilters.keyType,
									value: [],
								},
							}))
						}
					>
						LIMPA keyTypes
					</Button>
				</>
			)}

			{!!filters.environmentTypes.value?.length && (
				<div>
					<p>
						<b>{filters.environmentTypes.name}:</b>

						{filters.environmentTypes.value.join(', ')}
					</p>

					<Button
						onClick={() =>
							setFilters((previousFilters) => ({
								...previousFilters,
								environmentTypes: {
									...previousFilters.environmentTypes,
									value: [],
								},
							}))
						}
					>
						LIMPA environmentTypes
					</Button>
				</div>
			)}

			{!!filters.instanceSizes.value?.length && (
				<div>
					<p>
						<b>{filters.instanceSizes.name}:</b>

						{filters.instanceSizes.value.join(', ')}
					</p>

					<Button
						onClick={() =>
							setFilters((previousFilters) => ({
								...previousFilters,
								instanceSizes: {
									...previousFilters.instanceSizes,
									value: [],
								},
							}))
						}
					>
						LIMPA instanceSizes
					</Button>
				</div>
			)}

			{!!filters.productVersions.value?.length && (
				<div>
					<p>
						<b>{filters.productVersions.name}:</b>

						{filters.productVersions.value.join(', ')}
					</p>

					<Button
						onClick={() =>
							setFilters((previousFilters) => ({
								...previousFilters,
								productVersions: {
									...previousFilters.productVersions,
									value: [],
								},
							}))
						}
					>
						LIMPA productVersions
					</Button>
				</div>
			)}

			{!!filters.status.value?.length && (
				<div>
					<p>
						<b>{filters.status.name}:</b>

						{filters.status.value.join(', ')}
					</p>

					<Button
						onClick={() =>
							setFilters((previousFilters) => ({
								...previousFilters,
								status: {
									...previousFilters.status,
									value: [],
								},
							}))
						}
					>
						LIMPA status
					</Button>
				</div>
			)}

			{!!(
				filters.expirationDate.value.onOrAfter ||
				filters.expirationDate.value.onOrBefore
			) && (
				<div>
					<p>
						<b>{filters.expirationDate.name}:</b>

						{getDatesDisplay(filters.expirationDate)}
					</p>

					<Button
						onClick={() =>
							setFilters((previousFilters) => ({
								...previousFilters,
								expirationDate: {
									...previousFilters.expirationDate,
									value: {
										onOrAfter: undefined,
										onOrBefore: undefined,
									},
								},
							}))
						}
					>
						LIMPA expirationDate
					</Button>
				</div>
			)}

			{!!(
				filters.startDate.value.onOrAfter ||
				filters.startDate.value.onOrBefore
			) && (
				<div>
					<p>
						<b>{filters.startDate.name}:</b>

						{getDatesDisplay(filters.startDate)}
					</p>

					<Button
						onClick={() =>
							setFilters((previousFilters) => ({
								...previousFilters,
								startDate: {
									...previousFilters.startDate,
									value: {
										onOrAfter: undefined,
										onOrBefore: undefined,
									},
								},
							}))
						}
					>
						LIMPA startDate
					</Button>
				</div>
			)}

			<Button
				onClick={() =>
					setFilters({
						...INITIAL_FILTER,
						searchTerm: filters.searchTerm,
					})
				}
			>
				Clear All Filters
			</Button>
		</div>
	);
};

export default BadgeFilter;
