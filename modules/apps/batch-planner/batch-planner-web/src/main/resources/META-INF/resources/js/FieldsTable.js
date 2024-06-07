/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayTable from '@clayui/table';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {sub} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import {
	CSV_FORMAT,
	DISALLOWED_CSV_ENTITY_TYPES,
	EXPORT_FILE_FORMAT_SELECTED_EVENT,
	SCHEMA_SELECTED_EVENT,
	TEMPLATE_SELECTED_EVENT,
	TEMPLATE_SOILED_EVENT,
} from './constants';
import getFieldsFromSchema from './getFieldsFromSchema';

function FieldsTable({portletNamespace}) {
	const [fields, setFields] = useState([]);
	const [selectedExportFileFormat, setSelectedExportFileFormat] =
		useState('');
	const [selectedFields, setSelectedFields] = useState([]);
	const useTemplateMappingRef = useRef();

	const isForbidden = (field) =>
		field.unsupportedFormats?.includes(selectedExportFileFormat);

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

		const handleExportFileFormatUpdated = ({
			selectedExportFileFormat,
			selectedSchema,
		}) => {
			setSelectedExportFileFormat(selectedExportFileFormat);
			if (
				selectedExportFileFormat === CSV_FORMAT.toUpperCase() &&
				DISALLOWED_CSV_ENTITY_TYPES.includes(selectedSchema)
			) {
				setFields([]);
				setSelectedFields([]);
			}
		};

		Liferay.on(
			EXPORT_FILE_FORMAT_SELECTED_EVENT,
			handleExportFileFormatUpdated
		);
		Liferay.on(SCHEMA_SELECTED_EVENT, handleSchemaUpdated);
		Liferay.on(TEMPLATE_SELECTED_EVENT, handleTemplateUpdate);
		Liferay.on(TEMPLATE_SOILED_EVENT, handleTemplateSoiled);

		return () => {
			Liferay.detach(
				EXPORT_FILE_FORMAT_SELECTED_EVENT,
				handleExportFileFormatUpdated
			);
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
			<div className="card-header h4 py-3">
				{Liferay.Language.get('fields')}
			</div>

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

							<ClayTable.Cell
								className="table-cell-expand-small"
								headingCell
							/>
						</ClayTable.Row>
					</ClayTable.Head>

					<ClayTable.Body id="fieldsTableBody">
						{fields.map((field) => {
							const included =
								!isForbidden(field) &&
								selectedFields.some(
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
											disabled={isForbidden(field)}
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
											className={
												isForbidden(field)
													? 'disabled'
													: ''
											}
											htmlFor={`${portletNamespace}fieldName_${field.name}`}
										>
											{field.name}
										</label>
									</ClayTable.Cell>

									<ClayTable.Cell className="pr-5 text-right">
										{isForbidden(field) && (
											<>
												<ClayLabel displayType="info">
													{Liferay.Language.get(
														'not-supported'
													)}
												</ClayLabel>
												<ClayTooltipProvider>
													<span
														className="inline-item-after"
														title={Liferay.Language.get(
															'this-field-type-does-not-support-csv-export-file-format'
														)}
													>
														<ClayIcon
															className="text-secondary"
															symbol="question-circle-full"
														/>
													</span>
												</ClayTooltipProvider>
											</>
										)}
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
