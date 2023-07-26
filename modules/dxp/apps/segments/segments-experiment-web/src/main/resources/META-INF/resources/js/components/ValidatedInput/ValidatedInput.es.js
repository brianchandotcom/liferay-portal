/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useId} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

function ValidatedInput({
	autofocus = false,
	errorMessage,
	label,
	onBlur = () => {},
	onChange = () => {},
	onFocus = () => {},
	onValidationChange = () => {},
	required = false,
	value = '',
}) {
	const inputId = useId();
	const [invalid, setInvalid] = useState(false);
	const nodeRef = useRef();

	const updateInvalid = (newInvalid) => {
		setInvalid((previousInvalid) => {
			if (newInvalid !== previousInvalid) {
				onValidationChange(newInvalid);
			}

			return newInvalid;
		});
	};

	const onNameInputBlur = (event) => {
		if (!value.trim().length) {
			updateInvalid(true);
		}

		onBlur(event);
	};

	const onNameInputFocus = (event) => {
		updateInvalid(false);
		onFocus(event);
	};

	useEffect(() => {
		if (nodeRef.current && autofocus) {
			nodeRef.current.focus();
		}
	}, [autofocus]);

	return (
		<ClayForm.Group className={invalid ? 'has-error' : ''}>
			{label && (
				<>
					<label htmlFor={inputId}>{label}</label>
					<ClayIcon
						className="lexicon-icon-sm ml-1 reference-mark text-warning"
						style={{verticalAlign: 'super'}}
						symbol="asterisk"
					/>
				</>
			)}

			<ClayInput
				id={inputId}
				maxLength="75"
				onBlur={onNameInputBlur}
				onChange={onChange}
				onFocus={onNameInputFocus}
				ref={nodeRef}
				required={required}
				type="text"
				value={value}
			/>

			{invalid && errorMessage && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						<ClayForm.FeedbackIndicator symbol="exclamation-full" />

						{errorMessage}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
	);
}

ValidatedInput.propTypes = {
	autofocus: PropTypes.bool,
	errorMessage: PropTypes.string,
	label: PropTypes.string,
	onBlur: PropTypes.func,
	onChange: PropTypes.func,
	onFocus: PropTypes.func,
	onValidationChange: PropTypes.func,
	value: PropTypes.string,
};

export default ValidatedInput;
