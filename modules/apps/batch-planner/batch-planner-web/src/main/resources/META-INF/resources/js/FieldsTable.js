/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayCheckbox} from '@clayui/form';
import ClayTable from '@clayui/table';
import React, {useEffect, useRef, useState} from 'react';

import {
	SCHEMA_SELECTED_EVENT,
	TEMPLATE_SELECTED_EVENT,
	TEMPLATE_SOILED,
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

				setFields(newFields);
				if (!useTemplateMappingRef.current) {
					setSelectedFields(newFields);
				}
			}
			else {
				setFields([]);
				if (!useTemplateMappingRef.current) {
					setSelectedFields([]);
				}
			}
		};

		const handleTemplateUpdate = (event) => {
			const {template} = event;

			if (template) {
				useTemplateMappingRef.current = true;
				setSelectedFields(
					Object.keys(template.mapping).map((fields) => ({
						label: fields,
						value: fields,
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
		Liferay.on(TEMPLATE_SOILED, handleTemplateSoiled);

		return () => {
			Liferay.detach(SCHEMA_SELECTED_EVENT, handleSchemaUpdated);
			Liferay.detach(TEMPLATE_SELECTED_EVENT, handleTemplateUpdate);
			Liferay.detach(TEMPLATE_SOILED, handleTemplateSoiled);
		};
	}, []);

	if (!fields.length) {
		return null;
	}

	return (
		<div className="card d-flex flex-column">
			<h4 className="card-header py-3">
				{Liferay.Language.get('entity-attributes')}
			</h4>

			<div className="card-body p-0">
				<ClayTable borderless hover={false} responsive={false}>
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell headingCell>
								<ClayCheckbox
									checked={
										selectedFields.length > 0 &&
										selectedFields.length === fields.length
									}
									indeterminate={
										selectedFields.length > 0 &&
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

					<ClayTable.Body>
						{fields.map((field) => {
							const included = selectedFields.some(
								(selectedField) =>
									selectedField.value === field.value
							);

							return (
								<ClayTable.Row key={field.label}>
									<ClayTable.Cell>
										<ClayCheckbox
											checked={included}
											id={`${portletNamespace}fieldName_${field.label}`}
											name={`${portletNamespace}fieldName`}
											onChange={() => {
												if (useTemplateMappingRef) {
													Liferay.fire(
														TEMPLATE_SOILED
													);
												}
												if (included) {
													setSelectedFields(
														selectedFields.filter(
															(selected) =>
																selected.value !==
																field.value
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
											value={field.value}
										/>
									</ClayTable.Cell>

									<ClayTable.Cell>
										<label
											htmlFor={`${portletNamespace}fieldName_${field.label}`}
										>
											{field.label}
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
