/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {sub} from 'frontend-js-web';
import React, {Dispatch, SetStateAction} from 'react';

import {STR_BLANK} from '../utils/constants';

interface FiltersAndSortingProps {
	data: Partial<APIEndpointUIData>;
	setData: Dispatch<SetStateAction<Partial<APIEndpointUIData>>>;
}

export default function FiltersAndSorting({
	data,
	setData,
}: FiltersAndSortingProps) {
	const endpointFiltersInstruction = Liferay.Language.get(
		'add-a-filter-using-odata'
	);

	const endpointSortInstruction = Liferay.Language.get(
		'add-a-sort-using-odata'
	);

	const handleODataFilterChange = (value: string) => {
		setData((previousData) => ({
			...previousData,
			...(value !== STR_BLANK
				? {
						apiEndpointToAPIFilters: [
							{
								...(previousData.apiEndpointToAPIFilters?.[0]
									?.id && {
									id: previousData
										.apiEndpointToAPIFilters?.[0].id,
								}),
								oDataFilter: value,
							},
						],
					}
				: {apiEndpointToAPIFilters: []}),
		}));
	};

	const handleODataSortChange = (value: string) => {
		setData((previousData) => ({
			...previousData,
			...(value !== STR_BLANK
				? {
						apiEndpointToAPISorts: [
							{
								...(previousData.apiEndpointToAPISorts?.[0]
									?.id && {
									id: previousData.apiEndpointToAPISorts?.[0]
										.id,
								}),
								oDataSort: value,
							},
						],
					}
				: {apiEndpointToAPISorts: []}),
		}));
	};

	return (
		<>
			<ClayForm.Group>
				<label htmlFor="endpointFiltersField">
					{Liferay.Language.get('filters')}

					<ClayTooltipProvider>
						<span
							data-tooltip-align="top"
							title={`${Liferay.Language.get(
								'odata-cannot-exceed-1000-characters'
							)} ${sub(
								Liferay.Language.get(
									'remember-not-to-include-x'
								),
								'?filter='
							)}`}
						>
							<ClayIcon
								className="ml-1"
								symbol="question-circle-full"
							/>
						</span>
					</ClayTooltipProvider>
				</label>

				<Text as="p" id="hostTextPreview" size={2} weight="lighter">
					/?filter=
				</Text>

				<textarea
					aria-label={endpointFiltersInstruction}
					autoComplete="off"
					className="form-control"
					id="endpointFiltersField"
					onChange={({target: {value}}) =>
						handleODataFilterChange(value)
					}
					placeholder={endpointFiltersInstruction}
					value={data.apiEndpointToAPIFilters?.[0]?.oDataFilter}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="endpointSortingField">
					{Liferay.Language.get('sorting')}

					<ClayTooltipProvider>
						<span
							data-tooltip-align="top"
							title={`${Liferay.Language.get(
								'odata-cannot-exceed-1000-characters'
							)} ${sub(
								Liferay.Language.get(
									'remember-not-to-include-x'
								),
								'?sort='
							)}`}
						>
							<ClayIcon
								className="ml-1"
								symbol="question-circle-full"
							/>
						</span>
					</ClayTooltipProvider>
				</label>

				<Text as="p" id="hostTextPreview" size={2} weight="lighter">
					/?sort=
				</Text>

				<textarea
					aria-label={endpointSortInstruction}
					autoComplete="off"
					className="form-control"
					id="endpointSortingField"
					onChange={({target: {value}}) =>
						handleODataSortChange(value)
					}
					placeholder={endpointSortInstruction}
					value={data.apiEndpointToAPISorts?.[0]?.oDataSort}
				/>
			</ClayForm.Group>
		</>
	);
}
