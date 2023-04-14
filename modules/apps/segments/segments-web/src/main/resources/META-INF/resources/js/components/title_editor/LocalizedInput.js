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

import {ClayInput} from '@clayui/form';
import getCN from 'classnames';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

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
	const hasErrorRef = useRef(false);
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

		const newValues = {
			...values,
			[currentLang]: value,
		};

		hasErrorRef.current = !onValidateValues(newValues);

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
		setValues(newValues);

		handleChange(event, newValues, hasErrorRef.current);
	};

	const onValidateValues = (values) => {
		const parsedValue =
			values[defaultLang] && values[defaultLang].replace(/\s/g, '');

		return !!parsedValue;
	};

	const inputGroupItemClasses = getCN('input-group-item ml-2', {
		'has-error': hasErrorRef.current,
	});

	return (
		<div className="input-group input-localized input-localized-input">
			<LocalizedDropdown
				availableLanguages={availableLanguages}
				defaultLang={defaultLang}
				initialLang={initialLanguageId}
				initialOpen={initialOpen}
				onLanguageChange={onLanguageChange}
			/>

			<div className={inputGroupItemClasses}>
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
