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
import {FieldFeedback, useFeatureFlag} from 'data-engine-js-components-web';
import React, {ChangeEventHandler, useRef, useState} from 'react';

import Card from '../Card/Card';
import Sidebar from '../Editor/Sidebar/Sidebar';
import InputLocalized from '../Form/InputLocalized/InputLocalized';
import Select from '../Form/Select';
import {ObjectValidationErrors} from '../ObjectValidationFormBase';

import './ObjectValidationTabs.scss';
import CodeMirrorEditor from '../CodeMirrorEditor';
import Input from '../Form/Input';

function BasicInfo({
	componentLabel,
	defaultLocale,
	disabled,
	errors,
	locales,
	setValues,
	values,
}: IBasicInfo) {
	const [locale, setSelectedLocale] = useState(
		defaultLocale as {
			label: string;
			symbol: string;
		}
	);

	return (
		<>
			<Card title={componentLabel}>
				<InputLocalized
					disabled={disabled}
					error={errors.name}
					label={Liferay.Language.get('label')}
					locales={locales}
					onSelectedLocaleChange={setSelectedLocale}
					onTranslationsChange={(label) => setValues({name: label})}
					placeholder={Liferay.Language.get('add-a-label')}
					required
					selectedLocale={locale}
					translations={values.name as LocalizedValue<string>}
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

function Conditions({
	defaultLocale,
	disabled,
	errors,
	locales,
	objectValidationRuleElements,
	setValues,
	values,
}: IConditions) {
	const [locale, setSelectedLocale] = useState(
		defaultLocale as {
			label: string;
			symbol: string;
		}
	);
	const editorRef = useRef<CodeMirror.Editor>();
	const flags = useFeatureFlag();
	const emptyScriptError = errors.script;
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
			<div className="lfr-objects__no-padding-card">
				<Card
					title={values.engineLabel!}
					tooltip={engine === 'ddm' ? ddmTooltip : null}
				>
					<div className="lfr-objects__object-validation-editor-sidebar-container">
						<div className="lfr-objects__object-validation-editor-container">
							<CodeMirrorEditor
								editorRef={editorRef}
								error={emptyScriptError}
								onChange={(script) => setValues({script})}
								options={{
									lineWrapping: true,
									mode:
										engine === 'groovy' ? 'groovy' : 'null',
									readOnly: disabled,
									value: values.script ?? '',
								}}
								placeholder={placeholder}
							/>

							<div className="has-error mb-3">
								<FieldFeedback
									errorMessage={emptyScriptError}
								/>
							</div>
						</div>

						{flags['LPS-147651'] && (
							<Sidebar
								editorRef={editorRef}
								objectValidationRuleElements={
									objectValidationRuleElements
								}
							/>
						)}
					</div>
				</Card>
			</div>

			<Card title={Liferay.Language.get('error-message')}>
				<InputLocalized
					disabled={disabled}
					error={errors.errorLabel}
					label={Liferay.Language.get('message')}
					locales={locales}
					onSelectedLocaleChange={setSelectedLocale}
					onTranslationsChange={(message) =>
						setValues({errorLabel: message})
					}
					placeholder={Liferay.Language.get('add-an-error-message')}
					required
					selectedLocale={locale}
					translations={values.errorLabel as LocalizedValue<string>}
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
	defaultLocale: {label: string; symbol: string};
	disabled: boolean;
	errors: ObjectValidationErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	locales: Array<any>;
	setValues: (values: Partial<ObjectValidation>) => void;
	values: Partial<ObjectValidation>;
}

interface IBasicInfo extends ITabs {
	componentLabel: string;
}

interface IConditions extends ITabs {
	objectValidationRuleElements: ObjectValidationRuleElement[];
}

export {BasicInfo, Conditions};
