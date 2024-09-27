/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {Text} from '@clayui/core';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {
	Dispatch,
	SetStateAction,
	useCallback,
	useEffect,
	useState,
} from 'react';

import {Select} from '../fieldComponents/Select';
import {DEFAULT_LANGUAGE_ID} from '../utils/constants';
import {fetchJSON, getAllItems} from '../utils/fetchUtil';
import {removeLeadingForwardSlash} from '../utils/string';

interface PathParameterConfigurationProps {
	data: Partial<APIEndpointUIData>;
	displayError: EndpointDataError;
	selectedResponseBodySchema: SelectOption | undefined;
	setData: Dispatch<SetStateAction<Partial<APIEndpointUIData>>>;
}

export default function PathParameterConfiguration({
	data,
	displayError,
	selectedResponseBodySchema,
	setData,
}: PathParameterConfigurationProps) {
	const [pathParameterOptions, setPathParameterOptions] = useState<
		SelectOption[]
	>([]);

	const [selectedPathParameter, setSelectedPathParameter] =
		useState<SelectOption>();

	useEffect(() => {
		setSelectedPathParameter(
			pathParameterOptions.find(
				(option) => option.value === data.pathParameter
			)
		);
	}, [pathParameterOptions, data.pathParameter]);

	const getObjectFieldLabel = (objectField: ObjectDefinition): string => {
		return (
			objectField.label[
				DEFAULT_LANGUAGE_ID as keyof LocalizedValue<string>
			] ||
			Object.values(objectField.label)[0] ||
			objectField.name
		);
	};

	const handleSelectPathParameter = (value: string) => {
		setData((previousValue) => ({
			...previousValue,
			pathParameter: value,
		}));
	};

	const fetchFilteredObjectDefinitionsByExternalReferenceCode = (
		externalReferenceCode: string
	): Promise<ObjectDefinition[]> => {
		return getAllItems<ObjectDefinition>({
			filter: `label eq 'ID' or label eq 'External Reference Code' or unique eq true`,
			url: `/o/object-admin/v1.0/object-definitions/by-external-reference-code/${externalReferenceCode}/object-fields`,
		});
	};

	const fetchSchemaByAPISchemaID = (
		schemaId: string
	): Promise<APISchemaItem> => {
		return fetchJSON<APISchemaItem>({
			input: `/o/headless-builder/schemas/${schemaId}`,
		});
	};

	const fetchPathParameterOptions = useCallback((schemaId: string) => {
		fetchSchemaByAPISchemaID(schemaId).then((response) => {
			fetchFilteredObjectDefinitionsByExternalReferenceCode(
				response.mainObjectDefinitionERC
			).then((response) => {
				const options = response
					? response.map((objectField) => ({
							label: getObjectFieldLabel(objectField),
							value: objectField.name,
						}))
					: [];

				if (options.length) {
					setPathParameterOptions(options);
				}
			});
		});
	}, []);

	useEffect(() => {
		if (data.r_responseAPISchemaToAPIEndpoints_l_apiSchemaId) {
			fetchPathParameterOptions(
				data.r_responseAPISchemaToAPIEndpoints_l_apiSchemaId?.toString()
			);
		}
	}, [
		data.r_responseAPISchemaToAPIEndpoints_l_apiSchemaId,
		fetchPathParameterOptions,
	]);

	return (
		<>
			{selectedResponseBodySchema && (
				<>
					<ClayForm.Group
						className={classNames({
							'has-error': displayError.pathParameter,
						})}
					>
						<label htmlFor="selectTrigger">
							{Liferay.Language.get('path-parameter-property')}

							<span className="ml-1 reference-mark text-warning">
								<ClayIcon symbol="asterisk" />
							</span>
						</label>

						<Select
							dropDownSearchAriaLabel={Liferay.Language.get(
								'search-for-a-parameter-property-or-use-the-arrow-keys-to-navigate-and-select-a-parameter-property-from-the-list'
							)}
							onClick={handleSelectPathParameter}
							options={pathParameterOptions}
							placeholder={Liferay.Language.get(
								'select-an-option'
							)}
							searchable
							selectedOption={selectedPathParameter}
						/>

						{displayError.pathParameter && (
							<ClayAlert
								className="mt-2"
								displayType="danger"
								title={Liferay.Language.get(
									'please-select-a-path-parameter-property'
								)}
								variant="feedback"
							></ClayAlert>
						)}

						<Text as="p" id="hostTextPreview" weight="lighter">
							{sub(
								Liferay.Language.get(
									'this-property-from-the-schema-will-be-mapped-to-path-parameter-x'
								),
								removeLeadingForwardSlash(data.parameter!)
							)}
						</Text>
					</ClayForm.Group>

					<ClayForm.Group>
						<label>
							{Liferay.Language.get('path-parameter-description')}
						</label>

						<textarea
							aria-label={Liferay.Language.get(
								'add-a-description-here'
							)}
							autoComplete="off"
							className="form-control"
							id="pathParameter"
							onChange={({target: {value}}) =>
								setData((previousData) => ({
									...previousData,
									pathParameterDescription: value,
								}))
							}
							placeholder={Liferay.Language.get(
								'add-a-description-here'
							)}
							value={data.pathParameterDescription}
						/>
					</ClayForm.Group>
				</>
			)}
		</>
	);
}
