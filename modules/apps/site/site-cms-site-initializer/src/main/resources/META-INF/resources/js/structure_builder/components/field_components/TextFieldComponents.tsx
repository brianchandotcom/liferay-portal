/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox} from '@clayui/form';
import React from 'react';

import {useSelector, useStateDispatch} from '../../contexts/StateContext';
import selectPublishedFields from '../../selectors/selectPublishedFields';
import {Field, TextField} from '../../utils/field';
import MaxLengthInput from '../MaxLengthInput';

export default function getTextFieldComponents(): {
	FirstSectionComponent?: React.FC<{field: Field; readOnly?: boolean}>;
	SecondSectionComponent?: React.FC<{field: Field; readOnly?: boolean}>;
} {
	return {
		SecondSectionComponent,
	};
}

function SecondSectionComponent({
	field,
	readOnly,
}: {
	field: Field;
	readOnly?: boolean;
}) {
	const textField = field as TextField;

	const dispatch = useStateDispatch();
	const publishedFields = useSelector(selectPublishedFields);

	const isPublished = publishedFields.has(field.uuid);

	return (
		<>
			<ClayForm.Group className="mb-3">
				<ClayCheckbox
					checked={textField.settings.uniqueValues || false}
					disabled={isPublished || readOnly}
					label={Liferay.Language.get('accept-unique-values-only')}
					onChange={(event) => {
						dispatch({
							settings: {
								...textField.settings,
								uniqueValues: event.target.checked,
							},
							type: 'update-field',
							uuid: field.uuid,
						});
					}}
				/>
			</ClayForm.Group>

			<MaxLengthInput disabled={readOnly} field={field} />
		</>
	);
}
