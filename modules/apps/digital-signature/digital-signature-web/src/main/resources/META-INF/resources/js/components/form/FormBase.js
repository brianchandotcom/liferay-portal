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
		<span className="sr-only">
			{Liferay.Language.get('required')}
		</span>
	</>
);

export {ErrorFeedback, Input, HelpMessage, RequiredMark};
