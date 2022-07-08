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

import 'codemirror/mode/groovy/groovy';
import {ClayToggle} from '@clayui/form';
import {
	Card,
	CodeEditor,
	Input,
	InputLocalized,
	Select,
	SidebarCategory,
} from '@liferay/object-js-components-web';
import React, {ChangeEventHandler} from 'react';

import {ObjectValidationErrors} from '../ObjectValidationFormBase';

export function BasicInfo({
	componentLabel,
	disabled,
	errors,
	setValues,
	values,
}: IBasicInfo) {
	return (
		<>
			<Card title={componentLabel}>
				<InputLocalized
					disabled={disabled}
					error={errors.name}
					label={Liferay.Language.get('label')}
					onChange={(name) => setValues({name})}
					placeholder={Liferay.Language.get('add-a-label')}
					required
					translations={values.name!}
				/>

				<Input
					disabled
					label={Liferay.Language.get('type')}
					value={values.engineLabel}
				/>

				<ClayToggle
					disabled={disabled}
					label={Liferay.Language.get('active-validation')}
					onToggle={(active) => setValues({active})}
					toggled={values.active}
				/>
			</Card>

			<TriggerEventContainer
				disabled={disabled}
				eventTypes={[Liferay.Language.get('on-submission')]}
			/>
		</>
	);
}

export function Conditions({
	disabled,
	errors,
	objectValidationRuleElements,
	setValues,
	values,
}: IConditions) {
	const engine = values.engine;
	const ddmTooltip = {
		content: Liferay.Language.get(
			'use-the-expression-builder-to-define-the-format-of-a-valid-object-entry'
		),
		symbol: 'question-circle-full',
	};
	let placeholder;

	if (engine === 'groovy') {
		placeholder = Liferay.Language.get(
			'insert-a-groovy-script-to-define-your-validation'
		);
	}
	else if (engine === 'ddm') {
		placeholder = Liferay.Language.get(
			'add-elements-from-the-sidebar-to-define-your-validation'
		);
	}
	else {
		placeholder = '';
	}

	return (
		<>
			<Card
				title={values.engineLabel!}
				tooltip={engine === 'ddm' ? ddmTooltip : null}
				viewMode="no-padding"
			>
				<CodeEditor
					error={errors.script}
					mode={engine}
					onChange={(script?: string, lineCount?: number) =>
						setValues({lineCount, script})
					}
					placeholder={placeholder}
					readOnly={disabled}
					sidebarElements={objectValidationRuleElements}
					value={values.script ?? ''}
				/>
			</Card>

			<Card title={Liferay.Language.get('error-message')}>
				<InputLocalized
					disabled={disabled}
					error={errors.errorLabel}
					label={Liferay.Language.get('message')}
					onChange={(errorLabel) => setValues({errorLabel})}
					placeholder={Liferay.Language.get('add-an-error-message')}
					required
					translations={values.errorLabel!}
				/>
			</Card>
		</>
	);
}

function TriggerEventContainer({disabled, eventTypes}: ITriggerEventProps) {
	return (
		<Card title={Liferay.Language.get('trigger-event')}>
			<Select
				defaultValue={0}
				disabled={disabled}
				label={Liferay.Language.get('event')}
				options={eventTypes}
			/>
		</Card>
	);
}

interface ITriggerEventProps {
	disabled: boolean;
	eventTypes: string[];
}

interface ITabs {
	disabled: boolean;
	errors: ObjectValidationErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	setValues: (values: Partial<ObjectValidation>) => void;
	values: Partial<ObjectValidation>;
}

interface IBasicInfo extends ITabs {
	componentLabel: string;
}

interface IConditions extends ITabs {
	objectValidationRuleElements: SidebarCategory[];
}
