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
