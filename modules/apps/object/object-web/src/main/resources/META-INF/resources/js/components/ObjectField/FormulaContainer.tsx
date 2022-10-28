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

import {Card, ExpressionBuilder} from '@liferay/object-js-components-web';
import React from 'react';

import {ObjectFieldErrors} from './ObjectFieldFormBase';

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
		<Card title={Liferay.Language.get('formula')}>
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
		</Card>
	);
}
