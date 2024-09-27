/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {Text} from '@clayui/core';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {Dispatch, SetStateAction, useEffect, useState} from 'react';

import FiltersAndSorting from './endpointComponents/FiltersAndSorting';
import PathParameterConfiguration from './endpointComponents/PathParameterConfiguration';
import {Select} from './fieldComponents/Select';
import {HTTP_METHODS, RETRIEVE_TYPES} from './utils/constants';
import {getAllItems} from './utils/fetchUtil';

interface EditEndpointConfigurationProps {
	currentAPIApplicationId: string;
	data: Partial<APIEndpointUIData>;
	displayError: EndpointDataError;
	schemaAPIURLPath: string;
	setData: Dispatch<SetStateAction<Partial<APIEndpointUIData>>>;
}

export default function EditEndpointConfiguration({
	currentAPIApplicationId,
	data,
	displayError,
	schemaAPIURLPath,
	setData,
}: EditEndpointConfigurationProps) {
	const [schemaOptions, setSchemaOptions] = useState<SelectOption[]>([]);
	const [selectedRequestBodySchema, setSelectedRequestBodySchema] =
		useState<SelectOption>();
	const [selectedResponseBodySchema, setSelectedResponseBodySchema] =
		useState<SelectOption>();

	useEffect(() => {
		getAllItems<APISchemaItem>({
			filter: `r_apiApplicationToAPISchemas_l_apiApplicationId eq '${currentAPIApplicationId}'`,
			url: schemaAPIURLPath,
		}).then((result) => {
			const options = result
				? result.map((apiSchemas) => ({
						label: apiSchemas.name,
						value: apiSchemas.id.toString(),
					}))
				: [];

			if (options.length) {
				setSchemaOptions([
					{
						label: Liferay.Language.get('not-selected'),
						value: '0',
					},
					...options,
				]);
			}
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (schemaOptions.length) {
			if (
				data.r_responseAPISchemaToAPIEndpoints_l_apiSchemaId !==
				undefined
			) {
				setSelectedResponseBodySchema(
					schemaOptions.find(
						(option) =>
							option.value ===
							data.r_responseAPISchemaToAPIEndpoints_l_apiSchemaId?.toString()
					)
				);
			}
			if (
				data.r_requestAPISchemaToAPIEndpoints_l_apiSchemaId !==
				undefined
			) {
				setSelectedRequestBodySchema(
					schemaOptions.find(
						(option) =>
							option.value ===
							data.r_requestAPISchemaToAPIEndpoints_l_apiSchemaId?.toString()
					)
				);
			}
		}
	}, [data, schemaOptions]);

	const handleSelectBodySchema = (
		onChangeFn: Dispatch<SetStateAction<SelectOption | undefined>>,
		property: string,
		value: string
	) => {
		setData((previousValue) => ({
			...previousValue,
			[property]: Number(value),
		}));

		onChangeFn(schemaOptions.find((option) => option.value === value));
	};

	return (
		<ClayForm>
			{data.httpMethod?.key === HTTP_METHODS.POST && (
				<ClayForm.Group
					className={classNames('mb-4', {
						'has-error':
							displayError.r_requestAPISchemaToAPIEndpoints_l_apiSchemaId,
					})}
				>
					<>
						<label htmlFor="selectTrigger">
							{Liferay.Language.get('request-body-schema')}

							<span className="ml-1 reference-mark text-warning">
								<ClayIcon symbol="asterisk" />
							</span>
						</label>

						<Select
							disabled={false}
							dropDownSearchAriaLabel={Liferay.Language.get(
								'search-for-a-schema-or-use-the-arrow-keys-to-navigate-and-select-a-schema-from-the-list'
							)}
							onClick={(value) =>
								handleSelectBodySchema(
									setSelectedRequestBodySchema,
									'r_requestAPISchemaToAPIEndpoints_l_apiSchemaId',
									value
								)
							}
							options={schemaOptions}
							placeholder={Liferay.Language.get(
								'select-a-schema'
							)}
							searchable
							selectedOption={selectedRequestBodySchema}
						/>

						{displayError.r_requestAPISchemaToAPIEndpoints_l_apiSchemaId && (
							<ClayAlert
								className="mt-2"
								displayType="danger"
								title={Liferay.Language.get(
									'please-select-a-request-body-schema'
								)}
								variant="feedback"
							></ClayAlert>
						)}
					</>
					<Text
						as="p"
						color="secondary"
						id="hostTextPreview"
						size={3}
					>
						{Liferay.Language.get(
							'request-body-can-only-contain-properies-from-the-main-object'
						)}
					</Text>
				</ClayForm.Group>
			)}

			<ClayForm.Group>
				<label htmlFor="selectTrigger">
					{Liferay.Language.get('response-body-schema')}
				</label>

				<Select
					disabled={false}
					dropDownSearchAriaLabel={Liferay.Language.get(
						'search-for-a-schema-or-use-the-arrow-keys-to-navigate-and-select-a-schema-from-the-list'
					)}
					onClick={(value) =>
						handleSelectBodySchema(
							setSelectedResponseBodySchema,
							'r_responseAPISchemaToAPIEndpoints_l_apiSchemaId',
							value
						)
					}
					options={schemaOptions}
					placeholder={Liferay.Language.get('select-a-schema')}
					searchable
					selectedOption={selectedResponseBodySchema}
				/>
			</ClayForm.Group>

			{data.httpMethod?.key === HTTP_METHODS.GET &&
				data.retrieveType?.key === RETRIEVE_TYPES.SINGLE_ELEMENT && (
					<PathParameterConfiguration
						data={data}
						displayError={displayError}
						selectedResponseBodySchema={selectedResponseBodySchema}
						setData={setData}
					/>
				)}

			{data.httpMethod?.key === HTTP_METHODS.GET &&
				data.retrieveType?.key === RETRIEVE_TYPES.COLLECTION && (
					<FiltersAndSorting data={data} setData={setData} />
				)}
		</ClayForm>
	);
}
