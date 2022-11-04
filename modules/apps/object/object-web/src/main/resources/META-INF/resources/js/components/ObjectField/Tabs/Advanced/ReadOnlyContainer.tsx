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
	objectFieldSettings: ObjectFieldSetting[];
	requiredField: boolean;
	setValues: (value: Partial<ObjectField>) => void;
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
	objectFieldSettings,
	requiredField,
	setValues,
}: ReadOnlyContainerProps) {
	const readOnlySetting = findObjectFieldSetting(
		objectFieldSettings,
		'readOnly'
	);

	const readOnlyScriptSetting = findObjectFieldSetting(
		objectFieldSettings,
		'readOnlyScript'
	);

	const setReadOnly = (value: string) => {
		setValues({
			objectFieldSettings: [
				...objectFieldSettings?.filter(
					(objectFieldSetting) =>
						objectFieldSetting.name !== 'readOnly' &&
						objectFieldSetting.name !== 'readOnlyScript'
				),
				{
					name: 'readOnly',
					value,
				},
			],
			required:
				value === 'true' || value === 'conditional'
					? false
					: requiredField,
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
								objectFieldSettings,
								value
							),
						});
					}}
					onOpenModal={() => {
						const parentWindow = Liferay.Util.getOpener();

						parentWindow.Liferay.fire(
							'openExpressionBuilderModal',
							{
								header: Liferay.Language.get(
									'expression-builder'
								),
								onSave: (script: string) => {
									setValues({
										objectFieldSettings: updateReadOnlyScriptSetting(
											objectFieldSettings,
											script
										),
									});
								},
								placeholder: `<#-- ${Liferay.Language.get(
									'create-the-condition-of-the-read-only-state-using-expression-builder'
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
