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
import {getFieldsGroupedByTypes} from 'data-engine-js-components-web/js/utils/objectFields';
import React from 'react';

const ObjectRestrictionSection = ({children, description, title}) => {
	return (
		<>
			<p>{title}</p>

			<ClayPanel displayTitle={description} displayType="secondary">
				<ClayPanel.Body>{children}</ClayPanel.Body>
			</ClayPanel>
		</>
	);
};

const ModalObjectRestrictionsBody = ({
	unmappedFormFields,
	unmappedRequiredObjectFields,
}) => {
	const formFieldsGroupedByType = getFieldsGroupedByTypes(unmappedFormFields);
	const requiredObjectFieldsGroupedByType = getFieldsGroupedByTypes(
		unmappedRequiredObjectFields
	);

	return (
		<>
			{!!unmappedRequiredObjectFields.length && (
				<ObjectRestrictionSection
					description={Liferay.Language.get(
						'unmapped-object-required-fields'
					)}
					title={Liferay.Language.get(
						'to-save-this-form-all-required-field-of-the-selected-object-need-to-be-mapped'
					)}
				>
					{requiredObjectFieldsGroupedByType.map(({fields, type}) => (
						<div key={type}>
							<strong className="text-capitalize">{type}</strong>

							<ol>
								{fields.map(({name}) => (
									<li key={name}>{name}</li>
								))}
							</ol>
						</div>
					))}
				</ObjectRestrictionSection>
			)}

			{!!unmappedFormFields.length && (
				<ObjectRestrictionSection
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
				</ObjectRestrictionSection>
			)}
		</>
	);
};

export default ModalObjectRestrictionsBody;
