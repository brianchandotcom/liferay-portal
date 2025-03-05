/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayToggle} from '@clayui/form';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {useSelector, useStateDispatch} from '../contexts/StateContext';
import selectPublishedFields from '../selectors/selectPublishedFields';
import selectStructureStatus from '../selectors/selectStructureStatus';
import {Field, TextField} from '../utils/field';
import Input from './Input';

export default function getTextFieldComponents(): {
	FirstSectionComponent?: React.FC<{field: Field}>;
	SecondSectionComponent?: React.FC<{field: Field}>;
} {
	return {
		SecondSectionComponent,
	};
}

function SecondSectionComponent({field}: {field: Field}) {
	const textField = field as TextField;

	const dispatch = useStateDispatch();
	const status = useSelector(selectStructureStatus);
	const publishedFields = useSelector(selectPublishedFields);

	const isPublished =
		status === 'published' && publishedFields.has(field.name);

	const [enableLimitCharacters, setEnableLimitCharacters] = useState(
		!!textField.settings.maxLength
	);

	return (
		<>
			<ClayForm.Group className="mb-3">
				<ClayToggle
					disabled={isPublished}
					label={Liferay.Language.get('accept-unique-values-only')}
					onToggle={(value) => {
						dispatch({
							name: field.name,
							settings: {
								...textField.settings,
								uniqueValues: value,
							},
							type: 'update-field',
						});
					}}
					toggled={textField.settings.uniqueValues}
				/>
			</ClayForm.Group>
			<ClayForm.Group className="mb-3">
				<ClayToggle
					label={Liferay.Language.get('limit-characters')}
					onToggle={(value) => {
						setEnableLimitCharacters(value);

						if (!value) {
							dispatch({
								name: field.name,
								settings: {
									uniqueValues:
										textField.settings.uniqueValues,
								},
								type: 'update-field',
							});
						}
					}}
					toggled={enableLimitCharacters}
				/>
			</ClayForm.Group>
			{enableLimitCharacters ? (
				<ClayForm.Group className="mb-3">
					<Input
						helpMessage={sub(
							Liferay.Language.get(
								'set-the-maximum-number-of-characters-accepted-this-value-cant-be-less-than-x-or-greater-than-x'
							),
							'1',
							'280'
						)}
						inputProps={{
							max: 280,
							min: 1,
							type: 'number',
						}}
						label={Liferay.Language.get(
							'maximum-number-of-characters'
						)}
						onValueChange={(value) => {
							dispatch({
								name: field.name,
								settings: {
									...textField.settings,
									maxLength: parseInt(value, 10),
									showCounter: true,
								},
								type: 'update-field',
							});
						}}
						required
						value={String(textField.settings.maxLength)}
					/>
				</ClayForm.Group>
			) : null}
		</>
	);
}
