/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayCheckbox} from '@clayui/form';
import ClayTable from '@clayui/table';
import {sub} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import {
	SCHEMA_SELECTED_EVENT,
	TEMPLATE_SELECTED_EVENT,
	TEMPLATE_SOILED_EVENT,
} from './constants';
import getFieldsFromSchema from './getFieldsFromSchema';

function FieldsTable({portletNamespace}) {
	const [fields, setFields] = useState([]);
	const [selectedFields, setSelectedFields] = useState([]);
	const useTemplateMappingRef = useRef();

	useEffect(() => {
		const handleSchemaUpdated = (event) => {
			if (event.schema) {
				const newFields = getFieldsFromSchema(event.schema);

				const formattedFields = [
					...newFields.required,
					...newFields.optional,
				];

				setFields(formattedFields);

				if (!useTemplateMappingRef.current) {
					setSelectedFields(formattedFields);
				}
			}
			else {
				setFields([]);

				if (!useTemplateMappingRef.current) {
					setSelectedFields([]);
				}
			}
		};

		const handleTemplateUpdate = ({template}) => {
			if (template) {
				useTemplateMappingRef.current = true;

				setSelectedFields(
					Object.keys(template.mappings).map((fields) => ({
						name: fields,
					}))
				);
			}
			else {
				useTemplateMappingRef.current = false;
			}
		};

		const handleTemplateSoiled = () => {
			useTemplateMappingRef.current = false;
		};

		Liferay.on(SCHEMA_SELECTED_EVENT, handleSchemaUpdated);
		Liferay.on(TEMPLATE_SELECTED_EVENT, handleTemplateUpdate);
		Liferay.on(TEMPLATE_SOILED_EVENT, handleTemplateSoiled);

		return () => {
			Liferay.detach(SCHEMA_SELECTED_EVENT, handleSchemaUpdated);
			Liferay.detach(TEMPLATE_SELECTED_EVENT, handleTemplateUpdate);
			Liferay.detach(TEMPLATE_SOILED_EVENT, handleTemplateSoiled);
		};
	}, []);

	if (!fields.length) {
		return null;
	}

	return (
		<div className="card d-flex flex-column">
			<h4 className="card-header py-3">
				{Liferay.Language.get('fields')}
			</h4>

			<ClayAlert
				className="m-3"
				displayType="info"
				title={`${Liferay.Language.get('info')}:`}
				variant="inline"
			>
				{Liferay.Language.get(
					'select-fields-to-include-in-the-exported-file'
				)}
			</ClayAlert>

			<div className="card-body p-0">
				<ClayTable hover={false} responsive={true}>
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell headingCell>
								<ClayCheckbox
									checked={
										!!selectedFields.length &&
										selectedFields.length === fields.length
									}
									indeterminate={
										!!selectedFields.length &&
										selectedFields.length < fields.length
									}
									onChange={() => {
										if (
											selectedFields.length ===
											fields.length
										) {
											setSelectedFields([]);
										}
										else {
											setSelectedFields(fields);
										}
									}}
								/>
							</ClayTable.Cell>

							<ClayTable.Cell
								className="table-cell-expand-small"
								headingCell
							>
								{Liferay.Language.get('attribute-code')}
							</ClayTable.Cell>
						</ClayTable.Row>
					</ClayTable.Head>

					<ClayTable.Body id="fieldsTableBody">
						{fields.map((field) => {
							const included = selectedFields.some(
								(selectedField) =>
									selectedField.name === field.name
							);

							return (
								<ClayTable.Row key={field.name}>
									<ClayTable.Cell>
										<ClayCheckbox
											aria-label={sub(
												Liferay.Language.get(
													'select-x'
												),
												field.name
											)}
											checked={included}
											id={`${portletNamespace}fieldName_${field.name}`}
											name={`${portletNamespace}fieldName`}
											onChange={() => {
												Liferay.fire(
													TEMPLATE_SOILED_EVENT
												);

												if (included) {
													setSelectedFields(
														selectedFields.filter(
															(selected) =>
																selected.name !==
																field.name
														)
													);
												}
												else {
													setSelectedFields([
														...selectedFields,
														field,
													]);
												}
											}}
											value={field.name}
										/>
									</ClayTable.Cell>

									<ClayTable.Cell>
										<label
											htmlFor={`${portletNamespace}fieldName_${field.name}`}
										>
											{field.name}
										</label>
									</ClayTable.Cell>
								</ClayTable.Row>
							);
						})}
					</ClayTable.Body>
				</ClayTable>
			</div>
		</div>
	);
}

export default FieldsTable;
