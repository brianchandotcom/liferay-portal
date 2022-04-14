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

import React, {ChangeEventHandler, useState} from 'react';

import Editor from '../Editor/Editor';
import InputLocalized from '../Form/InputLocalized/InputLocalized';
import Select from '../Form/Select';
import ObjectValidationFormBase, {
	ObjectValidationErrors,
} from '../ObjectValidationFormBase';

function BasicInfo({
	componentLabel,
	defaultLocale,
	disabled,
	errors,
	handleChange,
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
			<div className="sheet">
				<h2 className="sheet-title">{componentLabel}</h2>

				<InputLocalized
					disabled={disabled}
					error={errors.name}
					label={Liferay.Language.get('label')}
					locales={locales}
					onSelectedLocaleChange={setSelectedLocale}
					onTranslationsChange={(label) => setValues({name: label})}
					required
					selectedLocale={locale}
					translations={values.name as LocalizedValue<string>}
				/>

				<ObjectValidationFormBase
					disabled={disabled}
					errors={errors}
					handleChange={handleChange}
					objectValidationTypes={[
						{
							label: 'Groovy',
						},
					]}
					setValues={setValues}
					values={values}
				/>
			</div>

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
	setValues,
	values,
}: IConditions) {
	const [locale, setSelectedLocale] = useState(
		defaultLocale as {
			label: string;
			symbol: string;
		}
	);

	return (
		<>
			<div className="sheet">
				<h2 className="sheet-title">
					{Liferay.Language.get('groovy')}
				</h2>

				<Editor
					content={values.script}
					disabled={disabled}
					setValues={setValues}
				/>
			</div>

			<div className="mt-4 sheet">
				<h2 className="sheet-title">
					{Liferay.Language.get('error-message')}
				</h2>

				<InputLocalized
					disabled={disabled}
					error={errors.errorLabel}
					label={Liferay.Language.get('message')}
					locales={locales}
					onSelectedLocaleChange={setSelectedLocale}
					onTranslationsChange={(message) =>
						setValues({errorLabel: message})
					}
					required
					selectedLocale={locale}
					translations={values.errorLabel as LocalizedValue<string>}
				/>
			</div>
		</>
	);
}

function TriggerEventContainer({disabled, eventTypes}: ITriggerEventProps) {
	return (
		<div className="mt-4 sheet">
			<h2 className="sheet-title">
				{Liferay.Language.get('trigger-event')}
			</h2>

			<Select
				disabled={disabled}
				label={Liferay.Language.get('event')}
				options={eventTypes}
				value={0}
			/>
		</div>
	);
}

interface ITriggerEventProps {
	disabled: boolean;
	eventTypes: string[];
}

interface IBasicInfo {
	componentLabel: string;
	defaultLocale: {label: string; symbol: string};
	disabled: boolean;
	errors: ObjectValidationErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	locales: Array<any>;
	setValues: (values: Partial<ObjectValidation>) => void;
	values: Partial<ObjectValidation>;
}

interface IConditions {
	defaultLocale: {label: string; symbol: string};
	disabled: boolean;
	errors: ObjectValidationErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	locales: Array<any>;
	setValues: (values: Partial<ObjectValidation>) => void;
	values: Partial<ObjectValidation>;
}

export {BasicInfo, Conditions};
