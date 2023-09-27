/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable react/jsx-curly-brace-presence */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {
	ExpressionBuilder,
	SidebarCategory,
	Toggle,
} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import {LearnMessage, LearnResourcesContext} from 'frontend-js-components-web';
import React, {useEffect, useState} from 'react';

import {
	getDefaultValueFieldSettings,
	getUpdatedDefaultValueFieldSettings,
	getUpdatedDefaultValueType,
} from '../../../../utils/defaultValues';
import {removeFieldSettings} from '../../../../utils/fieldSettings';
import ListTypeDefaultValueSelect from '../../DefaultValueFields/ListTypeDefaultValueSelect';
import {ObjectFieldErrors} from '../../ObjectFieldFormBase';
interface DefaultValueContainerProps {
	creationLanguageId: Liferay.Language.Locale;
	disabled?: boolean;
	errors: ObjectFieldErrors;
	learnResources: object;
	objectFieldBusinessType: ObjectFieldBusinessType;
	objectFieldSettings: ObjectFieldSetting[];
	setValues: (value: Partial<ObjectField>) => void;
	sidebarElements: SidebarCategory[];
	values: Partial<ObjectField>;
}

export interface InputAsValueFieldComponentProps {
	creationLanguageId: Liferay.Language.Locale;
	defaultValue?: ObjectFieldSettingValue;
	error?: string;
	label: string;
	placeholder?: string;
	required?: boolean;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}

type InputAsValueFieldComponents = {
	[key in ObjectFieldBusinessType]: React.FC<InputAsValueFieldComponentProps>;
};

const InputAsValueFieldComponents: Partial<InputAsValueFieldComponents> = {
	Picklist: ListTypeDefaultValueSelect,
};

export function DefaultValueContainer({
	creationLanguageId,
	errors,
	learnResources,
	setValues,
	sidebarElements,
	values,
}: DefaultValueContainerProps) {
	const {defaultValue, defaultValueType} = getDefaultValueFieldSettings(
		values
	);

	const [defaultValueToggleEnabled, setDefaultValueToggleEnabled] = useState(
		!!defaultValueType && !!defaultValue
	);

	const [defaultValueTypeSelection, setDefaultValueTypeSelection] = useState(
		defaultValueType || 'inputAsValue'
	);

	useEffect(() => {
		if (values.state) {
			setDefaultValueToggleEnabled(true);
			setDefaultValueTypeSelection('inputAsValue');
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values]);

	const handleToggle = (toggled: boolean) => {
		if (!toggled) {
			setValues({
				objectFieldSettings: removeFieldSettings(
					['defaultValueType', 'defaultValue'],
					values
				),
			});
		}
		else {
			setValues({
				objectFieldSettings: getUpdatedDefaultValueType(
					values,
					'inputAsValue'
				),
			});
		}
		setDefaultValueToggleEnabled(toggled);
	};

	const InputAsValueFieldComponent =
		InputAsValueFieldComponents[
			values.businessType as keyof InputAsValueFieldComponents
		];

	return (
		<>
			{!values.state && (
				<ClayAlert displayType="info" title="Info">
					{Liferay.Language.get(
						'enter-a-value-or-use-expressions-to-set-default-values'
					)}
					&nbsp;
					<LearnResourcesContext.Provider value={learnResources}>
						<LearnMessage
							className="alert-link"
							resource="object-web"
							resourceKey="general"
						/>
					</LearnResourcesContext.Provider>
				</ClayAlert>
			)}

			{!values.state && (
				<Toggle
					label={Liferay.Language.get('use-default-value')}
					onToggle={(toggled) => {
						handleToggle(toggled);
					}}
					toggled={defaultValueToggleEnabled}
				/>
			)}

			{defaultValueToggleEnabled && !values.state && (
				<ClayButton.Group>
					<ClayButton
						className={classNames({
							active:
								defaultValueTypeSelection === 'inputAsValue',
						})}
						displayType="secondary"
						onClick={() => {
							setDefaultValueTypeSelection('inputAsValue');
							setValues({
								objectFieldSettings: getUpdatedDefaultValueType(
									values,
									'inputAsValue'
								),
							});
						}}
						size="sm"
					>
						{Liferay.Language.get('input-as-value')}
					</ClayButton>

					<ClayButton
						className={classNames({
							active:
								defaultValueTypeSelection ===
								'expressionBuilder',
						})}
						displayType="secondary"
						onClick={() => {
							setDefaultValueTypeSelection('expressionBuilder');
							setValues({
								objectFieldSettings: getUpdatedDefaultValueType(
									values,
									'expressionBuilder'
								),
							});
						}}
						size="sm"
					>
						{Liferay.Language.get('expression-builder')}
					</ClayButton>
				</ClayButton.Group>
			)}

			{defaultValueToggleEnabled &&
				defaultValueTypeSelection === 'inputAsValue' &&
				InputAsValueFieldComponent && (
					<InputAsValueFieldComponent
						creationLanguageId={creationLanguageId}
						defaultValue={
							defaultValueType === 'inputAsValue' && defaultValue
						}
						error={errors.defaultValue}
						label={
							!values.state
								? Liferay.Language.get('default-value')
								: Liferay.Language.get('input-as-value')
						}
						required
						setValues={setValues}
						values={values}
					/>
				)}

			{defaultValueToggleEnabled &&
				defaultValueTypeSelection === 'expressionBuilder' && (
					<ExpressionBuilder
						error={errors.defaultValue}
						feedbackMessage={Liferay.Language.get(
							'use-expressions-to-create-a-condition'
						)}
						label={Liferay.Language.get('default-value')}
						onChange={({target: {value}}) => {
							setValues({
								objectFieldSettings: getUpdatedDefaultValueFieldSettings(
									values,
									value,
									'expressionBuilder'
								),
							});
						}}
						onOpenModal={() => {
							const parentWindow = Liferay.Util.getOpener();

							parentWindow.Liferay.fire(
								'openExpressionBuilderModal',
								{
									eventSidebarElements: sidebarElements,
									onSave: (script: string) => {
										setValues({
											objectFieldSettings: getUpdatedDefaultValueFieldSettings(
												values,
												script,
												'expressionBuilder'
											),
										});
									},
									placeholder: `<#-- ${Liferay.Language.get(
										'create-a-condition-to-set-the-default-value'
									)} -->`,
									required: false,
									source:
										defaultValueType ===
											'expressionBuilder' && defaultValue
											? defaultValue
											: '',
									validateExpressionURL: '',
								}
							);
						}}
						required
						value={
							defaultValueType === 'expressionBuilder'
								? (defaultValue as string)
								: ''
						}
					/>
				)}
		</>
	);
}
