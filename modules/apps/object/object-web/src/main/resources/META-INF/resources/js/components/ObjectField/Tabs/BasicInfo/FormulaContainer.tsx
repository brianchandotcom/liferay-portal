/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ExpressionBuilder} from '@liferay/object-js-components-web';
import React from 'react';

import {ObjectFieldErrors} from '../../ObjectFieldFormBase';

interface FormulaContainerProps {
	errors: ObjectFieldErrors;
	objectFieldSettings: ObjectFieldSetting[];
	setValues: (values: Partial<ObjectField>) => void;
}

const getNewObjectFieldSettings = (
	objectFieldSettings: ObjectFieldSetting[],
	script: string
) => {
	return [
		...(objectFieldSettings?.filter(
			(objectFieldSetting) => objectFieldSetting.name !== 'script'
		) as ObjectFieldSetting[]),
		{
			name: 'script',
			value: script,
		},
	] as ObjectFieldSetting[];
};

export function FormulaContainer({
	errors,
	objectFieldSettings,
	setValues,
}: FormulaContainerProps) {
	const currentScript = objectFieldSettings?.find(
		(objectFieldSetting) => objectFieldSetting.name === 'script'
	);

	return (
		<>
			<ExpressionBuilder
				error={errors.script}
				feedbackMessage={Liferay.Language.get(
					'use-expressions-to-create-a-condition'
				)}
				label={Liferay.Language.get('formula-builder')}
				onChange={({target: {value}}) => {
					setValues({
						objectFieldSettings: getNewObjectFieldSettings(
							objectFieldSettings,
							value
						),
					});
				}}
				onOpenModal={() => {
					const parentWindow = Liferay.Util.getOpener();

					parentWindow.Liferay.fire('openExpressionBuilderModal', {
						header: Liferay.Language.get('formula-builder'),
						onSave: (script: string) => {
							setValues({
								objectFieldSettings: getNewObjectFieldSettings(
									objectFieldSettings,
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
						source: currentScript?.value ?? '',
						validateExpressionURL: '',
					});
				}}
				placeholder={`${Liferay.Util.sub(
					Liferay.Language.get(
						'type-x-to-use-the-autocomplete-feature'
					),
					['"${"']
				)}`}
				value={(currentScript?.value as string) ?? ''}
			/>
		</>
	);
}
