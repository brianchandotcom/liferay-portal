/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import React from 'react';

import ErrorFeedback from './ErrorFeedback';
import HelpFeedback from './HelpFeedback';
import RequiredMark from './RequiredMark';

const Field = ({
	disabled,
	errorMessage,
	helpMessage = '',
	id,
	label,
	name,
	required,
	type = 'input',
	...restProps
}: {
	disabled?: boolean;
	errorMessage?: string;
	helpMessage?: string;
	id?: string;
	label: string;
	name: string;
	required?: boolean;
	type?: 'textarea' | 'input';
}) => {
	const fieldId = id ?? name;
	const feedbackId = `feedback-${fieldId}`;

	return (
		<ClayForm.Group className={errorMessage ? 'has-error' : ''}>
			<label className={disabled ? 'disabled' : ''} htmlFor={fieldId}>
				{label}

				{required && <RequiredMark />}
			</label>

			<ClayInput
				{...restProps}
				aria-describedby={(errorMessage || helpMessage) ?? feedbackId}
				className="form-control"
				component={type === 'textarea' ? 'textarea' : 'input'}
				disabled={disabled}
				id={fieldId}
				name={name}
				type={type}
			/>

			{(errorMessage || helpMessage) && (
				<ClayForm.FeedbackGroup id={feedbackId}>
					{errorMessage ? (
						<ErrorFeedback message={errorMessage} />
					) : (
						helpMessage && <HelpFeedback feedback={helpMessage} />
					)}
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
	);
};

export default Field;
