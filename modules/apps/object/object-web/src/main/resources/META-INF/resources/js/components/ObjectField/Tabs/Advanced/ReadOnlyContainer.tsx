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

import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import {Card, ExpressionBuilder} from '@liferay/object-js-components-web';
import React from 'react';

interface ReadOnlyContainerProps {
	disabled?: boolean;
	setValues: (value: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}

const updateReadOnlyScriptSetting = (
	objectFieldSettings: ObjectFieldSetting[],
	script: string
) => {
	return [
		...(objectFieldSettings?.filter(
			(objectFieldSetting) => objectFieldSetting.name !== 'readOnlyScript'
		) as ObjectFieldSetting[]),
		{
			name: 'readOnlyScript',
			value: script,
		},
	] as ObjectFieldSetting[];
};

const findObjectFieldSetting = (
	objectFieldSettings: ObjectFieldSetting[],
	fieldSettingName: ObjectFieldSettingName
) => {
	return objectFieldSettings?.find(
		(fieldSetting) => fieldSetting.name === fieldSettingName
	);
};

export function ReadOnlyContainer({
	disabled,
	setValues,
	values,
}: ReadOnlyContainerProps) {
	const readOnlySetting = findObjectFieldSetting(
		values.objectFieldSettings as ObjectFieldSetting[],
		'readOnly'
	);

	const readOnlyScriptSetting = findObjectFieldSetting(
		values.objectFieldSettings as ObjectFieldSetting[],
		'readOnlyScript'
	);

	const setReadOnly = (value: string) => {
		setValues({
			objectFieldSettings: [
				...(values.objectFieldSettings?.filter(
					(objectFieldSetting) =>
						objectFieldSetting.name !== 'readOnly' &&
						objectFieldSetting.name !== 'readOnlyScript'
				) as ObjectFieldSetting[]),
				{
					name: 'readOnly',
					value,
				},
			],
		});
	};

	return (
		<Card disabled={disabled} title={Liferay.Language.get('read-only')}>
			<ClayRadioGroup defaultValue={readOnlySetting?.value as string}>
				<ClayRadio
					disabled={disabled}
					label={Liferay.Language.get('true')}
					onClick={() => setReadOnly('true')}
					value="true"
				/>

				<ClayRadio
					disabled={disabled}
					label={Liferay.Language.get('false')}
					onClick={() => setReadOnly('false')}
					value="false"
				/>

				<ClayRadio
					disabled={disabled}
					label={Liferay.Language.get('conditional')}
					onClick={() => setReadOnly('conditional')}
					value="conditional"
				/>
			</ClayRadioGroup>

			{readOnlySetting?.value === 'conditional' && (
				<ExpressionBuilder
					feedbackMessage={Liferay.Language.get(
						'use-expressions-to-create-a-condition'
					)}
					label={Liferay.Language.get('expression-builder')}
					onChange={({target: {value}}) => {
						setValues({
							objectFieldSettings: updateReadOnlyScriptSetting(
								values.objectFieldSettings as ObjectFieldSetting[],
								value
							),
						});
					}}
					onOpenModal={() => {
						const parentWindow = Liferay.Util.getOpener();

						parentWindow.Liferay.fire(
							'openExpressionBuilderModal',
							{
								header: Liferay.Language.get('formula-builder'),
								onSave: (script: string) => {
									setValues({
										objectFieldSettings: updateReadOnlyScriptSetting(
											values.objectFieldSettings as ObjectFieldSetting[],
											script
										),
									});
								},
								placeholder: `<#-- ${Liferay.Util.sub(
									Liferay.Language.get(
										'add-formulas-to-calculate-values-based-on-other-fields-type-x-to-use-the-autocomplete-feature'
									),
									['"${"']
								)} -->`,
								required: false,
								source: readOnlyScriptSetting?.value ?? '',
								validateExpressionURL: '',
							}
						);
					}}
					placeholder={Liferay.Language.get('create-an-expression')}
					value={(readOnlyScriptSetting?.value as string) ?? ''}
				/>
			)}
		</Card>
	);
}
