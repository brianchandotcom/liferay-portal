/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayToggle} from '@clayui/form';
import React from 'react';

import {useSelector, useStateDispatch} from '../contexts/StateContext';
import selectPublishedFields from '../selectors/selectPublishedFields';
import {Field, NumericField} from '../utils/field';

export default function getNumericFieldComponents(): {
	FirstSectionComponent?: React.FC<{field: Field}>;
	SecondSectionComponent?: React.FC<{field: Field}>;
} {
	return {
		SecondSectionComponent,
	};
}

function SecondSectionComponent({field}: {field: Field}) {
	const numericField = field as NumericField;

	const dispatch = useStateDispatch();
	const publishedFields = useSelector(selectPublishedFields);

	const isPublished = publishedFields.has(field.uuid);

	return (
		<ClayForm.Group className="mb-3">
			<ClayToggle
				disabled={isPublished}
				label={Liferay.Language.get('accept-unique-values-only')}
				onToggle={(value) => {
					dispatch({
						settings: {
							...numericField.settings,
							uniqueValues: value,
						},
						type: 'update-field',
						uuid: field.uuid,
					});
				}}
				toggled={numericField.settings.uniqueValues}
			/>
		</ClayForm.Group>
	);
}
