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
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import SaveTemplate from '../SaveTemplate';
import {
	FILE_SCHEMA_EVENT,
	SCHEMA_SELECTED_EVENT,
	TEMPLATE_SELECTED_EVENT,
	TEMPLATE_SOILED,
} from '../constants';
import getFieldsFromSchema from '../getFieldsFromSchema';
import ImportMappingItem from './ImportMappingItem';
import ImportSubmit from './ImportSubmit';

function ImportForm({
	backUrl,
	formDataQuerySelector,
	formImportURL,
	formSaveAsTemplateURL,
	portletNamespace,
}) {
	const [fileFields, setFileFields] = useState();
	const [dbFields, setDbFields] = useState();
	const [fieldsSelections, setFieldsSelections] = useState({});
	const useTemplateMappingRef = useRef();

	const onFieldChange = useCallback((selectedItem, field) => {
		setFieldsSelections((prevSelections) => ({
			...prevSelections,
			[field]: selectedItem,
		}));

		Liferay.fire(TEMPLATE_SOILED);
	}, []);

	useEffect(() => {
		if (dbFields && fileFields && !useTemplateMappingRef.current) {
			const newFieldsSelection = {};

			const sameFieldFound = fileFields?.filter((f) =>
				dbFields.some((item) => item.value === f)
			);

			fileFields?.forEach((field) => {
				if (sameFieldFound.includes(field)) {
					newFieldsSelection[field] = field;
				}
				else {
					newFieldsSelection[field] = null;
				}
			});
			setFieldsSelections(newFieldsSelection);
		}
	}, [dbFields, fileFields]);

	useEffect(() => {
		function handleSchemaUpdated(event) {
			const newSchema = event.schema;
			if (newSchema) {
				const newDBFields = getFieldsFromSchema(newSchema);
				setDbFields(newDBFields);
			}
		}

		function handleFileSchemaUpdate(event) {
			const fileSchema = event.schema;
			setFileFields(fileSchema);
		}

		function handleTemplateSelect(event) {
			const {template} = event;

			if (template) {
				useTemplateMappingRef.current = true;
				setFieldsSelections(template.mapping);
			}
			else {
				useTemplateMappingRef.current = false;
			}
		}

		const handleTemplateDirty = () => {
			useTemplateMappingRef.current = false;
		};

		Liferay.on(SCHEMA_SELECTED_EVENT, handleSchemaUpdated);
		Liferay.on(FILE_SCHEMA_EVENT, handleFileSchemaUpdate);
		Liferay.on(TEMPLATE_SELECTED_EVENT, handleTemplateSelect);
		Liferay.on(TEMPLATE_SOILED, handleTemplateDirty);

		return () => {
			Liferay.detach(SCHEMA_SELECTED_EVENT, handleSchemaUpdated);
			Liferay.detach(FILE_SCHEMA_EVENT, handleFileSchemaUpdate);
			Liferay.detach(TEMPLATE_SELECTED_EVENT, handleTemplateSelect);
			Liferay.detach(TEMPLATE_SOILED, handleTemplateDirty);
		};
	}, []);

	const selectableFields =
		dbFields?.filter(
			(field) =>
				!Object.values(fieldsSelections).find(
					(selected) => selected === field.value
				)
		) || [];

	const hasSelectedField = Object.values(fieldsSelections).find(
		(selection) => selection !== null
	);

	const disableButtons = !(hasSelectedField && dbFields && fileFields);

	return (
		<>
			{fileFields && dbFields && (
				<div className="card import-mapping-table">
					<h4 className="card-header">
						{Liferay.Language.get('import-mappings')}
					</h4>

					<div className="card-body">
						<div className="lfr-form-content">
							<div className="autofit-section">
								{fileFields?.map((field) => (
									<ImportMappingItem
										field={field}
										key={field}
										onChange={onFieldChange}
										portletNamespace={portletNamespace}
										selectableFields={selectableFields}
										selectedField={fieldsSelections[field]}
									/>
								))}
							</div>
						</div>
					</div>
				</div>
			)}
			<div className="mt-4" id="formButtons">
				<div className="sheet-footer">
					<ClayLink className="btn btn-secondary" href={backUrl}>
						{Liferay.Language.get('cancel')}
					</ClayLink>

					<span>
						<SaveTemplate
							forceDisable={disableButtons}
							formSaveAsTemplateDataQuerySelector={
								formDataQuerySelector
							}
							formSaveAsTemplateURL={formSaveAsTemplateURL}
							portletNamespace={portletNamespace}
						/>
					</span>

					<ImportSubmit
						disabled={disableButtons}
						formDataQuerySelector={formDataQuerySelector}
						formImportURL={formImportURL}
						portletNamespace={portletNamespace}
					/>
				</div>
			</div>
		</>
	);
}

ImportForm.propTypes = {
	backUrl: PropTypes.string.isRequired,
	formDataQuerySelector: PropTypes.string.isRequired,
	formImportURL: PropTypes.string.isRequired,
	formSaveAsTemplateURL: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
};

export default ImportForm;
