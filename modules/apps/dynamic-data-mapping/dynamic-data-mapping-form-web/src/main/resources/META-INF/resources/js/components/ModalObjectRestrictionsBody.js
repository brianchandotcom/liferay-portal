/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import {getFieldsGroupedByTypes} from 'data-engine-js-components-web';
import React from 'react';

const ModalObjectRestrictionsSection = ({children, description, title}) => {
	return (
		<>
			<p>{title}</p>

			<ClayPanel displayTitle={description} displayType="secondary">
				<ClayPanel.Body>{children}</ClayPanel.Body>
			</ClayPanel>
		</>
	);
};

const UnmappedRequiredObjectFields = ({fields}) => {
	const fieldsTypeString = [
		Liferay.Language.get('checkbox-multiple-field-type-label'),
		Liferay.Language.get('color-field-type-label'),
		Liferay.Language.get('grid-field-type-label'),
		Liferay.Language.get('radio-field-type-label'),
		Liferay.Language.get('rich-text-field-type-label'),
		Liferay.Language.get('select-field-type-label'),
		Liferay.Language.get('text-field-type-label'),
	];

	const fieldTypeDecimalNumeric = Liferay.Language.get('decimal-number');
	const fieldTypeImage = Liferay.Language.get('image-field-type-label');
	const fieldTypeIntegerNumeric = Liferay.Language.get('integer-number');

	const fieldTypes = {
		bigdecimal: fieldTypeDecimalNumeric,
		blob: fieldTypeImage,
		double: fieldTypeDecimalNumeric,
		integer: fieldTypeIntegerNumeric,
		long: fieldTypeIntegerNumeric,
		string: fieldsTypeString,
	};

	const requiredObjectFieldsGroupedByType = getFieldsGroupedByTypes(fields);

	return (
		<ModalObjectRestrictionsSection
			description={Liferay.Language.get(
				'unmapped-object-required-fields'
			)}
			title={Liferay.Language.get(
				'to-save-this-form-all-required-fields-of-the-selected-object-need-to-be-mapped'
			)}
		>
			{requiredObjectFieldsGroupedByType.map(({fields, type}) => {
				const fieldType = fieldTypes[type.toLowerCase()];

				return (
					<div key={type}>
						<strong className="text-capitalize">{type}</strong>

						{fieldType && (
							<span className="text-muted">
								{` (${
									Array.isArray(fieldType)
										? fieldType.join(', ')
										: fieldType
								})`}
							</span>
						)}

						<ol>
							{fields.map(({label, name}) => (
								<li key={name}>
									{label[themeDisplay.getDefaultLanguageId()]}
								</li>
							))}
						</ol>
					</div>
				);
			})}
		</ModalObjectRestrictionsSection>
	);
};

const UnmappedFormFields = ({fields}) => {
	const formFieldsGroupedByType = getFieldsGroupedByTypes(fields);

	return (
		<ModalObjectRestrictionsSection
			description={Liferay.Language.get('unmapped-form-fields')}
			title={Liferay.Language.get(
				'all-fields-in-this-form-must-be-mapped-to-a-field-in-the-object'
			)}
		>
			{formFieldsGroupedByType.map(({fields, type}) => (
				<div key={type}>
					<strong className="text-capitalize">{type}</strong>

					<ol>
						{fields.map(({fieldName, label}) => (
							<li key={fieldName}>{label}</li>
						))}
					</ol>
				</div>
			))}
		</ModalObjectRestrictionsSection>
	);
};

const ModalObjectRestrictionsBody = ({
	unmappedFormFields,
	unmappedRequiredObjectFields,
}) => (
	<>
		{!!unmappedRequiredObjectFields.length && (
			<UnmappedRequiredObjectFields
				fields={unmappedRequiredObjectFields}
			/>
		)}

		{!!unmappedFormFields.length && (
			<UnmappedFormFields fields={unmappedFormFields} />
		)}
	</>
);

export default ModalObjectRestrictionsBody;
