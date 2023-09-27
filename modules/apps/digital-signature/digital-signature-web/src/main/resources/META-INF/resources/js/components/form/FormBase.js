/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

const ErrorFeedback = ({className, error}) => (
	<ClayForm.FeedbackGroup className={classNames(className)}>
		<ClayForm.FeedbackItem>
			<span>{error}</span>
		</ClayForm.FeedbackItem>
	</ClayForm.FeedbackGroup>
);

const HelpMessage = ({message}) => (
	<span
		className="inline-item-after lfr-portal-tooltip tooltip-icon"
		title={message}
	>
		<ClayIcon symbol="question-circle-full" />
	</span>
);

const Input = ({
	className,
	disabled,
	error,
	feedbackMessage,
	id,
	label,
	name,
	required,
	type = 'text',
	...restProps
}) => {
	const inputId = id || name;

	return (
		<ClayForm.Group
			className={classNames(className, 'w-100', {
				'has-error': error,
			})}
		>
			<label className={disabled ? 'disabled' : ''} htmlFor={inputId}>
				{label}

				{required && <RequiredMark />}
			</label>

			<ClayInput
				{...restProps}
				className="form-control"
				component={type === 'textarea' ? 'textarea' : 'input'}
				disabled={disabled}
				id={inputId}
				name={name}
				type={type}
			/>

			{typeof error === 'string' && <ErrorFeedback error={error} />}

			{feedbackMessage && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						<ClayForm.Text>{feedbackMessage}</ClayForm.Text>
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
	);
};

const RequiredMark = () => (
	<>
		<span className="inline-item-after reference-mark text-warning">
			<ClayIcon symbol="asterisk" />
		</span>
		<span className="hide-accessible sr-only">
			{Liferay.Language.get('required')}
		</span>
	</>
);

export {ErrorFeedback, Input, HelpMessage, RequiredMark};
