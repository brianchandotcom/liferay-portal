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
import BadgePillFilter from '../../../../components/BadgePillFilter';
import {INITIAL_FILTER} from '../../utils/constants/initialFilter';

const DNE_YEARS = 100;

const BadgeFilter = ({
	activationKeysLength,
	loading,
	filtersState: [filters, setFilters],
}) => {
	const getDatesDisplay = useCallback((dateFilterState) => {
		const dateDisplays = [];

		if (dateFilterState.value?.onOrAfter) {
			const todayDNE = new Date();
			todayDNE.setFullYear(todayDNE.getFullYear() + DNE_YEARS);

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

	const getKeyTypeDisplay = useCallback(
		(filterKeyType) => {
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
				}
				else if (
					filterKeyType.value?.minNodes ===
					filterKeyType.value?.maxNodes
				) {
					keyTypesDisplay.push(
						`Virtual Cluster (${filterKeyType.value?.minNodes} nodes)`
					);
				}
				else {
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
				<BadgePillFilter
					filterName={filterKeyType.name}
					filterValue={keyTypesDisplay.join(', ')}
					onClick={() =>
						setFilters((previousFilters) => ({
							...previousFilters,
							keyType: {
								...previousFilters.keyType,
								value: {
									hasOnPremise: undefined,
									hasVirtualCluster: undefined,
									maxNodes: '',
									minNodes: '',
								},
							},
						}))
					}
				/>
			);
		},
		[setFilters]
	);

	return (
		<>
			<div className="d-flex">
				{!!filters.searchTerm && !loading && (
					<p className="font-weight-semi-bold m-0 mt-3 text-paragraph-sm">
						{activationKeysLength} {}
						result
						{activationKeysLength > 1 ? 's ' : ' '}
						for &quot;
						{filters.searchTerm}&quot;
					</p>
				)}
			</div>
			<div className="bd-highlight d-flex">
				<div className="bd-highlight col d-flex flex-wrap pl-0 pt-2 w-100">
					{!!Object.values(filters.keyType.value).some(
						(value) => !!value
					) && getKeyTypeDisplay(filters.keyType)}

					{!!filters.environmentTypes.value?.length && (
						<BadgePillFilter
							filterName={filters.environmentTypes.name}
							filterValue={filters.environmentTypes.value.join(
								', '
							)}
							onClick={() =>
								setFilters((previousFilters) => ({
									...previousFilters,
									environmentTypes: {
										...previousFilters.environmentTypes,
										value: [],
									},
								}))
							}
						/>
					)}

					{!!filters.instanceSizes.value?.length && (
						<BadgePillFilter
							filterName={filters.instanceSizes.name}
							filterValue={filters.instanceSizes.value.join(', ')}
							onClick={() =>
								setFilters((previousFilters) => ({
									...previousFilters,
									instanceSizes: {
										...previousFilters.instanceSizes,
										value: [],
									},
								}))
							}
						/>
					)}

					{!!filters.productVersions.value?.length && (
						<BadgePillFilter
							filterName={filters.productVersions.name}
							filterValue={filters.productVersions.value.join(
								', '
							)}
							onClick={() =>
								setFilters((previousFilters) => ({
									...previousFilters,
									productVersions: {
										...previousFilters.productVersions,
										value: [],
									},
								}))
							}
						/>
					)}

					{!!filters.status.value?.length && (
						<BadgePillFilter
							filterName={filters.status.name}
							filterValue={filters.status.value.join(', ')}
							onClick={() =>
								setFilters((previousFilters) => ({
									...previousFilters,
									status: {
										...previousFilters.status,
										value: [],
									},
								}))
							}
						/>
					)}

					{!!(
						filters.expirationDate.value.onOrAfter ||
						filters.expirationDate.value.onOrBefore
					) && (
						<BadgePillFilter
							filterName={filters.expirationDate.name}
							filterValue={getDatesDisplay(
								filters.expirationDate
							)}
							onClick={() =>
								setFilters((previousFilters) => ({
									...previousFilters,
									expirationDate: {
										...previousFilters.expirationDate,
										value: {
											onOrAfter: false,
											onOrBefore: false,
										},
									},
								}))
							}
						/>
					)}

					{!!(
						filters.startDate.value.onOrAfter ||
						filters.startDate.value.onOrBefore
					) && (
						<BadgePillFilter
							filterName={filters.startDate.name}
							filterValue={getDatesDisplay(filters.startDate)}
							onClick={() =>
								setFilters((previousFilters) => ({
									...previousFilters,
									startDate: {
										...previousFilters.startDate,
										value: {
											onOrAfter: false,
											onOrBefore: false,
										},
									},
								}))
							}
						/>
					)}
				</div>

				<div className="bd-highlight flex-shrink-2 pt-2">
					{filters.hasValue && (
						<Button
							borderless
							className="link"
							onClick={() => {
								setFilters({
									...INITIAL_FILTER,
									searchTerm: filters.searchTerm,
								});
							}}
							prependIcon="times-circle"
							small
						>
							Clear All Filters
						</Button>
					)}
				</div>
			</div>
		</>
	);
};

export default BadgeFilter;
