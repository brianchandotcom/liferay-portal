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
import BadgePill from './components';

const BadgeFilter = ({
	activationKeysLength,
	loading,
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
				} else if (
					filterKeyType.value?.minNodes ===
					filterKeyType.value?.maxNodes
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
				<BadgePill
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

	// eslint-disable-next-line no-console
	console.log(loading);

	return (
		<>
			<div className="d-flex">
				{!!filters.searchTerm && !loading && (
					<p className="m-0 mt-3">
						{activationKeysLength} {}
						result
						{activationKeysLength > 1 ? 's ' : ' '}
						for &quot;
						{filters.searchTerm}&quot;
					</p>
				)}
			</div>
			<div className="bd-highlight d-flex">
				<div className="bd-highlight col d-flex pl-0 w-100">
					{!!Object.values(filters.keyType.value).some(
						(value) => !!value
					) && <>{getKeyTypeDisplay(filters.keyType)} </>}

					{!!filters.environmentTypes.value?.length && (
						<BadgePill
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
						<BadgePill
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
						<BadgePill
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
						<BadgePill
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
						<BadgePill
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
											onOrAfter: undefined,
											onOrBefore: undefined,
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
						<BadgePill
							filterName={filters.startDate.name}
							filterValue={getDatesDisplay(filters.startDate)}
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
