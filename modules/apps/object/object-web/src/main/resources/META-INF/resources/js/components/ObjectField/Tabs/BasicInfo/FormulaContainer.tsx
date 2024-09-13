/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ExpressionBuilder,
	SidebarCategory,
} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import React from 'react';

import {ObjectFieldErrors} from '../../ObjectFieldFormBase';

import '../../EditObjectFieldContent.scss';

interface FormulaContainerProps {
	errors: ObjectFieldErrors;
	modelBuilder?: boolean;
	objectFieldSettings: ObjectFieldSetting[];
	onSubmit?: (editedObjectField?: Partial<ObjectField>) => void;
	setValues: (values: Partial<ObjectField>) => void;
	sidebarElements: SidebarCategory[];
	values: Partial<ObjectField>;
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
	modelBuilder = false,
	objectFieldSettings,
	onSubmit,
	setValues,
	sidebarElements,
	values,
}: FormulaContainerProps) {
	const currentScript = objectFieldSettings?.find(
		(objectFieldSetting) => objectFieldSetting.name === 'script'
	);

	return (
		<div
			className={classNames({
				'lfr-objects__edit-object-field-card-content':
					modelBuilder === false,
				'lfr-objects__edit-object-field-model-builder-panel':
					modelBuilder,
			})}
		>
			<ExpressionBuilder
				error={errors.script}
				feedbackMessage={Liferay.Language.get(
					'use-expressions-to-create-a-condition'
				)}
				label={Liferay.Language.get('formula-builder')}
				onBlur={(event) => {
					event.stopPropagation();

					if (onSubmit) {
						onSubmit();
					}
				}}
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
						eventSidebarElements: sidebarElements,
						header: Liferay.Language.get('formula-builder'),
						onSave: (script: string) => {
							setValues({
								objectFieldSettings: getNewObjectFieldSettings(
									objectFieldSettings,
									script
								),
							});

							if (onSubmit) {
								onSubmit({
									...values,
									objectFieldSettings:
										getNewObjectFieldSettings(
											objectFieldSettings,
											script
										),
								});
							}
						},
						placeholder: `<#-- ${Liferay.Language.get(
							'add-formulas-to-calculate-values-based-on-other-fields'
						)} -->`,
						required: false,
						source: currentScript?.value ?? '',
						validateExpressionURL: '',
					});
				}}
				placeholder={Liferay.Language.get('create-an-expression')}
				value={(currentScript?.value as string) ?? ''}
			/>
		</div>
	);
}
