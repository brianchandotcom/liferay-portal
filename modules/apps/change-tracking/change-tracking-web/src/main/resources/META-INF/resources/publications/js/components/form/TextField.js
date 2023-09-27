/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

const TextField = ({
	ariaLabel,
	componentType,
	fieldValue,
	label,
	maxLength,
	name,
	onChange,
	placeholderValue,
	required,
	validateLength,
}) => {
	const [nameErrorMessage, setNameErrorMessage] = useState('');
	const defaultMaxLength = 75;

	const isFieldEmpty = (fieldValue) => {
		return !!fieldValue.length;
	};

	const isValidLength = () => {
		return maxLength
			? fieldValue.length <= maxLength
			: fieldValue.length <= defaultMaxLength;
	};

	const validateOnBlur = () => {
		if (required && !isFieldEmpty(fieldValue)) {
			setNameErrorMessage(Liferay.Language.get('this-field-is-required'));
		}
		else if (validateLength === true && !isValidLength()) {
			setNameErrorMessage(
				sub(
					Liferay.Language.get('value-exceeds-maximum-length-of-x'),
					maxLength ? maxLength : defaultMaxLength
				)
			);
		}
		else {
			setNameErrorMessage('');
		}
	};

	return (
		<>
			<ClayForm.Group className={nameErrorMessage ? 'has-error' : ''}>
				<label htmlFor={name ? name : null}>
					{label}

					{required && ' '}

					{required && (
						<svg
							className="lexicon-icon lexicon-icon-asterisk reference-mark"
							focusable="false"
							role="presentation"
							viewBox="0 0 512 512"
						>
							<path
								className="lexicon-icon-outline"
								d="M323.6,190l146.7-48.8L512,263.9l-149.2,47.6l93.6,125.2l-104.9,76.3l-96.1-126.4l-93.6,126.4L56.9,435.3l92.3-123.9L0,263.8l40.4-122.6L188.4,190v-159h135.3L323.6,190L323.6,190z"
							></path>
						</svg>
					)}
				</label>

				<ClayInput
					aria-label={ariaLabel ? ariaLabel : null}
					component={componentType}
					name={name ? name : null}
					onBlur={validateOnBlur}
					onChange={onChange}
					placeholder={placeholderValue ? placeholderValue : null}
					required={required ? 'required' : null}
					type="text"
					value={fieldValue}
				/>

				{nameErrorMessage && (
					<ClayForm.FeedbackGroup>
						<ClayForm.FeedbackItem>
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							{nameErrorMessage}
						</ClayForm.FeedbackItem>
					</ClayForm.FeedbackGroup>
				)}
			</ClayForm.Group>
		</>
	);
};

export default TextField;
