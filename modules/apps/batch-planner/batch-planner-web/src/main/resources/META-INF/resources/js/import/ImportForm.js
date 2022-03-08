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

import ClayLink from '@clayui/link';
import ClayTable from '@clayui/table';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import SaveTemplate from '../SaveTemplate';
import {
	CSV_HEADERS,
	FILE_SCHEMA_EVENT,
	SCHEMA_SELECTED_EVENT,
	TEMPLATE_SELECTED_EVENT,
	TEMPLATE_SOILED_EVENT,
} from '../constants';
import getFieldsFromSchema from '../getFieldsFromSchema';
import {getAvailableMappings} from '../utilities/mappings';
import ImportMappingItem from './ImportMappingItem';
import ImportSubmit from './ImportSubmit';

const TableFieldsHeader = () => (
	<ClayTable.Head>
		<ClayTable.Row>
			<ClayTable.Cell headingCell headingTitle>
				{Liferay.Language.get('destination-field')}
			</ClayTable.Cell>

			<ClayTable.Cell headingCell headingTitle>
				{Liferay.Language.get('source-file-field')}
			</ClayTable.Cell>

			<ClayTable.Cell headingCell headingTitle>
				{Liferay.Language.get('preview')}
			</ClayTable.Cell>
		</ClayTable.Row>
	</ClayTable.Head>
);
function ImportForm({
	backUrl,
	formDataQuerySelector,
	formImportURL,
	formSaveAsTemplateURL,
	mappedFields,
	portletNamespace,
}) {
	const [dbFields, setDbFields] = useState({
		optional: [],
		required: [],
	});
	const [formEvaluated, setFormEvaluated] = useState(false);
	const [fileFields, setFileFields] = useState();
	const [fileContent, setFileContent] = useState();
	const [demoFileValues, setDemoFileValues] = useState({});
	const [fieldsSelections, setFieldsSelections] = useState({});
	const [mappingsToBeEvaluated, setMappingsToBeEvaluated] = useState(
		mappedFields
	);
	const [fileContentPreview, setFileContentPreview] = useState([]);
	const useTemplateMappingRef = useRef();
	const [formIsValid, setFormIsValid] = useState(false);
	const [csvHeaders, setCsvHeaders] = useState(true);

	useEffect(() => {
		const requiredFieldNotFilled = dbFields.required
			? dbFields.required.some(
					(dbField) => !fieldsSelections[dbField.name]
			  )
			: false;

		if (
			fieldsSelections &&
			Object.keys(fieldsSelections).length > 0 &&
			dbFields.optional?.length > 0 &&
			!requiredFieldNotFilled
		) {
			setFormIsValid(true);
		}
		else {
			setFormIsValid(false);
		}
	}, [fieldsSelections, dbFields]);

	useEffect(() => {
		setFieldsSelections({});
	}, [csvHeaders]);

	useEffect(() => {
		const fieldsIndex = [];
		let filePreview;
		if (Object.keys(fieldsSelections)?.length > 0) {
			fileFields.filter((element, index) => {
				if (csvHeaders) {
					if (Object.values(fieldsSelections).indexOf(element) > -1) {
						fieldsIndex.push(index);
					}
				}
				else {
					if (
						Object.values(fieldsSelections).indexOf(
							element.toString()
						) > -1
					) {
						fieldsIndex.push(parseInt(index, 10));
					}
				}
			});
			if (!csvHeaders) {
				filePreview = fileContent?.map((row) => {
					return row.filter((element, index) => {
						if (fieldsIndex.includes(index)) {
							return element;
						}
					});
				});
			}
			else {
				filePreview = fileContent?.map((row) => {
					return row.filter((element, index) => {
						if (fieldsIndex.includes(index)) {
							return element;
						}
					});
				});
			}
			setFileContentPreview(filePreview);
		}
	}, [fileFields, fieldsSelections, fileContent, csvHeaders]);

	const updateFieldMapping = (fileField, dbFieldName) => {
		setFieldsSelections((prevSelections) => ({
			...prevSelections,
			[dbFieldName]: fileField,
		}));

		Liferay.fire(TEMPLATE_SOILED_EVENT);
	};

	useEffect(() => {
		const dbFieldsUnordered = [...dbFields.optional, ...dbFields.required];

		if (
			dbFields.optional.length + dbFields.required.length &&
			fileFields &&
			!useTemplateMappingRef.current
		) {
			const availableMappings = getAvailableMappings(
				mappingsToBeEvaluated,
				fileFields,
				dbFieldsUnordered
			);

			setFieldsSelections(availableMappings);
		}
	}, [dbFields, fileFields, mappingsToBeEvaluated]);

	useEffect(() => {
		function handleSchemaUpdated({schema}) {
			const newDBFields = getFieldsFromSchema(schema);

			setDbFields(newDBFields);
		}

		function handleFileSchemaUpdate({fileContent, firstItemDetails, schema}) {
			setFileContent(fileContent);
			setFileFields(schema);
			setDemoFileValues(firstItemDetails);
		}

		function handleTemplateSelect({template}) {
			if (template) {
				setMappingsToBeEvaluated(template.mappings);
			}
		}
		function handlesFileFormattedContent({fileContent}) {
			setFileContent(fileContent);
		}
		function handleCsvHeaders({csvContainsHeaders}) {
			setCsvHeaders(csvContainsHeaders);
		}

		function handleCsvHeaders({csvContainsHeaders}) {
			setCsvHeaders(csvContainsHeaders);
		}

		Liferay.on(CSV_HEADERS, handleCsvHeaders);
		Liferay.on(FILE_SCHEMA_EVENT, handleFileSchemaUpdate);
		Liferay.on(SCHEMA_SELECTED_EVENT, handleSchemaUpdated);
		Liferay.on(TEMPLATE_SELECTED_EVENT, handleTemplateSelect);

		return () => {
			Liferay.detach(CSV_HEADERS, handleCsvHeaders);
			Liferay.detach(FILE_SCHEMA_EVENT, handleFileSchemaUpdate);
			Liferay.detach(SCHEMA_SELECTED_EVENT, handleSchemaUpdated);
			Liferay.detach(TEMPLATE_SELECTED_EVENT, handleTemplateSelect);
		};
	}, []);

	const formIsVisible = !!(
		dbFields.optional.length + dbFields.required.length
	);

	return (
		<>
			{formIsVisible && (
				<div className="card import-mapping-table">
					<h4 className="card-header">
						{Liferay.Language.get('import-mappings')}
					</h4>

					<div className="card-body p-0">
						<ClayTable borderless hover={false}>
							<TableFieldsHeader />

							<ClayTable.Body>
								{!!dbFields.required.length && (
									<>
										<ClayTable.Row divider>
											<ClayTable.Cell
												className="text-uppercase"
												colSpan={3}
											>
												{Liferay.Language.get(
													'required-fields'
												)}
											</ClayTable.Cell>
										</ClayTable.Row>

										{dbFields.required.map((dbField) => (
											<ImportMappingItem
												dbField={dbField}
												fileFields={fileFields}
												formEvaluated={formEvaluated}
												key={dbField.name}
												portletNamespace={
													portletNamespace
												}
												previewValue={
													fieldsSelections[
														dbField.name
													] &&
													demoFileValues[
														fieldsSelections[
															dbField.name
														]
													]
												}
												required={true}
												selectedFileField={
													fieldsSelections[
														dbField.name
													] || ''
												}
												updateFieldMapping={(
													selectedFileField
												) =>
													updateFieldMapping(
														selectedFileField,
														dbField.name
													)
												}
											/>
										))}
									</>
								)}

								{!!dbFields.optional.length && (
									<>
										<ClayTable.Row divider>
											<ClayTable.Cell
												className="text-uppercase"
												colSpan={3}
											>
												{Liferay.Language.get(
													'optional-fields'
												)}
											</ClayTable.Cell>
										</ClayTable.Row>

										{dbFields.optional.map((dbField) => (
											<ImportMappingItem
												dbField={dbField}
												fileFields={fileFields}
												formEvaluated={formEvaluated}
												key={dbField.name}
												portletNamespace={
													portletNamespace
												}
												previewValue={
													fieldsSelections[
														dbField.name
													] &&
													demoFileValues[
														fieldsSelections[
															dbField.name
														]
													]
												}
												required={false}
												selectedFileField={
													fieldsSelections[
														dbField.name
													] || ''
												}
												updateFieldMapping={(
													selectedFileField
												) =>
													updateFieldMapping(
														selectedFileField,
														dbField.name
													)
												}
											/>
										))}
									</>
								)}
							</ClayTable.Body>
						</ClayTable>
					</div>
				</div>
			)}

			<div className="mt-4 sheet-footer">
				<ClayLink className="btn btn-secondary" href={backUrl}>
					{Liferay.Language.get('cancel')}
				</ClayLink>

				<SaveTemplate
					evaluateForm={() => setFormEvaluated(true)}
					formIsValid={formIsValid}
					formIsVisible={formIsVisible}
					formSaveAsTemplateDataQuerySelector={formDataQuerySelector}
					formSaveAsTemplateURL={formSaveAsTemplateURL}
					portletNamespace={portletNamespace}
					type="import"
				/>

				<ImportSubmit
					dbFields={dbFields}
					disabled={!formIsValid}
					evaluateForm={() => setFormEvaluated(true)}
					fieldsSelections={fieldsSelections}
					fileContent={fileContentPreview}
					fileFields={fileFields}
					formDataQuerySelector={formDataQuerySelector}
					formImportURL={formImportURL}
					formIsValid={formIsValid}
					formIsVisible={formIsVisible}
					portletNamespace={portletNamespace}
					setFileContent={setFileContent}
				/>
			</div>
		</>
	);
}

ImportForm.defaultProps = {
	mappedFields: {},
};

ImportForm.propTypes = {
	backUrl: PropTypes.string.isRequired,
	formDataQuerySelector: PropTypes.string.isRequired,
	formImportURL: PropTypes.string.isRequired,
	formSaveAsTemplateURL: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
};

export default ImportForm;
