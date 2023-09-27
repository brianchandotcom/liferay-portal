/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import LocalizedDropdown from './LocalizedDropdown';

export default function LocalizedInput({
	availableLanguages: initialAvailableLanguages,
	defaultLang,
	initialLanguageId,
	initialOpen = false,
	initialValues = {},
	onChange = () => {},
	placeholder = '',
	readOnly = false,
}) {
	const [availableLanguages, setAvailableLanguages] = useState(
		Object.keys(initialAvailableLanguages).map((key) => {
			const value = initialAvailableLanguages[key];

			return {
				hasValue: !!initialValues[key],
				key,
				value,
			};
		})
	);
	const [currentLang, setCurrentLang] = useState(initialLanguageId);
	const [currentValue, setCurrentValue] = useState(
		initialValues[initialLanguageId] || ''
	);
	const [hasError, setHasError] = useState(false);
	const [values, setValues] = useState(initialValues);

	const handleChange = (event, values, hasError) => {
		onChange(event, values, hasError);
	};

	const onLanguageChange = (langKey) => {
		setCurrentLang(langKey);
		setCurrentValue(values[langKey] || '');
	};

	const onInputChange = (event) => {
		event.persist();

		const value = event.target.value;

		const nextValues = {
			...values,
			[currentLang]: value,
		};

		const nextHasError = !onValidateValues(nextValues);

		setAvailableLanguages(
			availableLanguages.map((lang) => {
				let newLang = lang;

				if (lang.key === currentLang) {
					newLang = {
						...lang,
						hasValue: value !== '',
					};
				}

				return newLang;
			})
		);
		setCurrentValue(value);
		setValues(nextValues);
		setHasError(nextHasError);

		handleChange(event, nextValues, nextHasError);
	};

	const onValidateValues = (values) => {
		const parsedValue =
			values[defaultLang] && values[defaultLang].replace(/\s/g, '');

		return !!parsedValue;
	};

	return (
		<div className="input-group input-localized input-localized-input">
			<LocalizedDropdown
				availableLanguages={availableLanguages}
				defaultLang={defaultLang}
				initialLang={initialLanguageId}
				initialOpen={initialOpen}
				onLanguageChange={onLanguageChange}
			/>

			<div
				className={classNames('input-group-item ml-2', {
					'has-error': hasError,
				})}
			>
				<ClayInput
					aria-label={placeholder}
					className="field form-control-inline language-value rounded"
					data-testid="localized-main-input"
					onChange={onInputChange}
					placeholder={placeholder}
					readOnly={readOnly}
					type="text"
					value={currentValue}
				/>
			</div>
		</div>
	);
}

LocalizedInput.propTypes = {
	availableLanguages: PropTypes.object.isRequired,
	initialLanguageId: PropTypes.string.isRequired,
	initialOpen: PropTypes.bool,
	initialValues: PropTypes.object,
	onChange: PropTypes.func,
	placeholder: PropTypes.string,
	readOnly: PropTypes.bool,
};
