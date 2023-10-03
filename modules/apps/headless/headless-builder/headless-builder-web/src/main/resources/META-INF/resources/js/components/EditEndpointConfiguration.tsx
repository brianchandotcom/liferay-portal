/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {sub} from 'frontend-js-web';
import React, {Dispatch, SetStateAction, useEffect, useState} from 'react';

import {Select} from './fieldComponents/Select';
import {getAllItems} from './utils/fetchUtil';

interface EditEndpointConfigurationProps {
	currentAPIApplicationId: string;
	data: Partial<APIEndpointUIData>;
	schemaAPIURLPath: string;
	setData: Dispatch<SetStateAction<Partial<APIEndpointUIData>>>;
}

export default function EditEndpointConfiguration({
	currentAPIApplicationId,
	data,
	schemaAPIURLPath,
	setData,
}: EditEndpointConfigurationProps) {
	const [responseBodySchemaOptions, setResponseBodySchemaOptions] = useState<
		SelectOption[]
	>([]);
	const [
		selectedResponseBodySchema,
		setSelectedResponseBodySchema,
	] = useState<SelectOption>();

	useEffect(() => {
		getAllItems<APISchemaItem>({
			filter: `r_apiApplicationToAPISchemas_c_apiApplicationId eq '${currentAPIApplicationId}'`,
			url: schemaAPIURLPath,
		}).then((result) => {
			const options = result
				? result.map((apiSchemas) => ({
						label: apiSchemas.name,
						value: apiSchemas.id.toString(),
				  }))
				: [];

			if (options.length) {
				setResponseBodySchemaOptions(options);
			}
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (
			data.r_responseAPISchemaToAPIEndpoints_c_apiSchemaId &&
			responseBodySchemaOptions.length
		) {
			setSelectedResponseBodySchema(
				responseBodySchemaOptions.find(
					(option) =>
						option.value ===
						data.r_responseAPISchemaToAPIEndpoints_c_apiSchemaId?.toString()
				)
			);
		}
	}, [data, responseBodySchemaOptions]);

	const handleSelectResponseBodySchema = (value: string) => {
		setData((previousValue) => ({
			...previousValue,
			r_responseAPISchemaToAPIEndpoints_c_apiSchemaId: Number(value),
		}));

		setSelectedResponseBodySchema(
			responseBodySchemaOptions.find((option) => option.value === value)
		);
	};

	const endpointFiltersInstruction = Liferay.Language.get(
		'type-any-filter-using-odata-language'
	);

	const endpointSortInstruction = Liferay.Language.get(
		'type-any-sort-using-odata-language'
	);

	return (
		<ClayForm>
			<ClayForm.Group>
				<label htmlFor="selectTrigger">
					{Liferay.Language.get('response-body-schema')}
				</label>

				<Select
					disabled={false}
					dropDownSearchAriaLabel={Liferay.Language.get(
						'search-for-a-schema-or-use-the-arrow-keys-to-navigate-and-select-a-schema-from-the-list'
					)}
					onClick={handleSelectResponseBodySchema}
					options={responseBodySchemaOptions}
					placeholder={Liferay.Language.get('select-a-schema')}
					searchable
					selectedOption={selectedResponseBodySchema}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="endpointFiltersField">
					{Liferay.Language.get('filters')}

					<ClayTooltipProvider>
						<span
							data-tooltip-align="top"
							title={`${Liferay.Language.get(
								'there-is-a-limit-of-1000-characters-in-odata-language'
							)} ${sub(
								Liferay.Language.get(
									'remember-not-to-include-the-x'
								),
								'?filter='
							)}`}
						>
							&nbsp;
							<ClayIcon symbol="question-circle-full" />
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
						setData((previousData) => ({
							...previousData,
							...(value !== ''
								? {
										apiEndpointToAPIFilters: [
											{
												...(previousData
													.apiEndpointToAPIFilters?.[0]
													?.id && {
													id:
														previousData
															.apiEndpointToAPIFilters?.[0]
															.id,
												}),
												oDataFilter: value,
											},
										],
								  }
								: {apiEndpointToAPIFilters: []}),
						}))
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
								'there-is-a-limit-of-1000-characters-in-odata-language'
							)} ${sub(
								Liferay.Language.get(
									'remember-not-to-include-the-x'
								),
								'?sort='
							)}`}
						>
							&nbsp;
							<ClayIcon symbol="question-circle-full" />
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
						setData((previousData) => ({
							...previousData,
							...(value !== ''
								? {
										apiEndpointToAPISorts: [
											{
												...(previousData
													.apiEndpointToAPISorts?.[0]
													?.id && {
													id:
														previousData
															.apiEndpointToAPISorts?.[0]
															.id,
												}),
												oDataSort: value,
											},
										],
								  }
								: {apiEndpointToAPISorts: []}),
						}))
					}
					placeholder={endpointSortInstruction}
					value={data.apiEndpointToAPISorts?.[0]?.oDataSort}
				/>
			</ClayForm.Group>
		</ClayForm>
	);
}
