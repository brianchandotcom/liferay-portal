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
import CheckboxFilter from '../../../components/CheckboxFilter';

export function getDropDownAvailableFields(
	availableFields,
	filters,
	setFilters
) {
	return {
		x0a0: [
			{
				child: 'x0a1',
				disabled: !availableFields.roles.length,
				title: 'Roles',
			},
			{
				child: 'x0a2',
				disabled: !availableFields.status.length,
				title: 'Status',
			},
			{
				child: 'x0a3',
				disabled: !availableFields.supportSeat.length,
				title: 'Support Seat',
			},
		],
		x0a1: [
			{
				child: (
					<CheckboxFilter
						availableItems={availableFields.roles}
						clearCheckboxes={!filters.roles.value?.length}
						setFilters={setFilters}
						updateFilters={(checkedItems) =>
							setFilters((previousFilters) => ({
								...previousFilters,
								roles: {
									...previousFilters.roles,
									value: checkedItems,
								},
							}))
						}
					/>
				),
				type: 'component',
			},
		],
		x0a2: [
			{
				child: (
					<CheckboxFilter
						availableItems={availableFields.status}
						clearCheckboxes={!filters.status.value?.length}
						setFilters={setFilters}
						updateFilters={(checkedItems) =>
							setFilters((previousFilters) => ({
								...previousFilters,
								status: {
									...previousFilters.status,
									value: checkedItems,
								},
							}))
						}
					/>
				),
				type: 'component',
			},
		],
		x0a3: [
			{
				child: (
					<CheckboxFilter
						availableItems={availableFields.supportSeat}
						clearCheckboxes={!filters.supportSeat.value?.length}
						setFilters={setFilters}
						updateFilters={(checkedItems) =>
							setFilters((previousFilters) => ({
								...previousFilters,
								supportSeat: {
									...previousFilters.supportSeat,
									value: checkedItems,
								},
							}))
						}
					/>
				),
				type: 'component',
			},
		],
	};
}
