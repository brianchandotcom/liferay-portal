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

import ClayIcon from '@clayui/icon';
import {useFeatureFlag} from 'data-engine-js-components-web';
import React, {ChangeEventHandler, useState} from 'react';

import Card from '../Card/Card';
import Editor from '../Editor/Editor';
import Sidebar from '../Editor/Sidebar/Sidebar';
import {useChannel} from '../Editor/Sidebar/useChannel';
import InputLocalized from '../Form/InputLocalized/InputLocalized';
import Select from '../Form/Select';
import ObjectValidationFormBase, {
	ObjectValidationErrors,
} from '../ObjectValidationFormBase';

import '../Editor/Editor.scss';

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

				<ObjectValidationFormBase
					disabled={disabled}
					errors={errors}
					objectValidationTypeLabel={values.engineLabel!}
					setValues={setValues}
					values={values}
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
	const inputChannel = useChannel();
	const flags = useFeatureFlag();

	return (
		<>
			<div className="lfr-objects__object-data-validation-alt-sheet">
				<div className="lfr-objects__object-data-validation-title-container">
					<h2 className="sheet-title">{values.engineLabel}</h2>
					&nbsp;
					{values.engine === 'ddm' && (
						<span
							data-tooltip-align="top"
							title={Liferay.Language.get(
								'use-the-expression-builder-to-define-the-format-of-a-valid-object-entry'
							)}
						>
							<ClayIcon
								className="lfr-objects__edit-object-field-tooltip-icon"
								symbol="question-circle-full"
							/>
						</span>
					)}
				</div>

				<div className="lfr-objects__object-data-validation-editor-container">
					<Editor
						content={values.script}
						disabled={disabled}
						inputChannel={inputChannel}
						setValues={setValues}
					/>

					{flags['LPS-147651'] && (
						<Sidebar
							inputChannel={inputChannel}
							objectValidationRuleElements={
								objectValidationRuleElements
							}
						/>
					)}
				</div>
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
				disabled={disabled}
				label={Liferay.Language.get('event')}
				options={eventTypes}
				value={0}
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
