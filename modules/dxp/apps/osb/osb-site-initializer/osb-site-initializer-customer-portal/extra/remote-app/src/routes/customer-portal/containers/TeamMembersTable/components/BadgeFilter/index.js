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

import Button from '../../../../../../common/components/Button';

import BadgePillFilter from '../../../../components/BadgePillFilter';
import {INITIAL_FILTER} from '../../utils/constants/initialFilter';

const BadgeFilter = ({
	activationKeysLength,
	loading,
	filtersState: [filters, setFilters],
}) => {
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
					{!!filters.roles.value?.length && (
						<BadgePillFilter
							filterName={filters.roles.name}
							filterValue={filters.roles.value.join(', ')}
							onClick={() =>
								setFilters((previousFilters) => ({
									...previousFilters,
									roles: {
										...previousFilters.roles,
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

					{!!filters.supportSeat.value?.length && (
						<BadgePillFilter
							filterName={filters.supportSeat.name}
							filterValue={filters.supportSeat.value.join(', ')}
							onClick={() =>
								setFilters((previousFilters) => ({
									...previousFilters,
									supportSeat: {
										...previousFilters.supportSeat,
										value: [],
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
