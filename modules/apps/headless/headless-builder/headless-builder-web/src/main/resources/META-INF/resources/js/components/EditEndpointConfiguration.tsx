/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
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
		</ClayForm>
	);
}
