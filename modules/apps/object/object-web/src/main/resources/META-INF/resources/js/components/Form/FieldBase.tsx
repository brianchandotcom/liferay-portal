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

import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {ReactNode} from 'react';

function ErrorFeedback({className, error}: IErrorFeedbackProps) {
	return (
		<ClayForm.FeedbackGroup className={classNames(className)}>
			<ClayForm.FeedbackItem>
				<span>{error}</span>
			</ClayForm.FeedbackItem>
		</ClayForm.FeedbackGroup>
	);
}

function FeedbackMessage({feedbackMessage}: IFeedbackMessageProps) {
	return (
		<ClayForm.FeedbackGroup>
			<ClayForm.FeedbackItem>
				<ClayForm.Text>{feedbackMessage}</ClayForm.Text>
			</ClayForm.FeedbackItem>
		</ClayForm.FeedbackGroup>
	);
}

function RequiredMask() {
	return (
		<>
			<span className="ml-1 reference-mark text-warning">
				<ClayIcon symbol="asterisk" />
			</span>

			<span className="hide-accessible sr-only">
				{Liferay.Language.get('mandatory')}
			</span>
		</>
	);
}

export default function FieldBase({
	children,
	className,
	disabled,
	error,
	feedbackMessage,
	id,
	label,
	required,
}: IProps) {
	return (
		<ClayForm.Group className={classNames(className, {'has-error': error})}>
			<label className={classNames({disabled})} htmlFor={id}>
				{label}

				{required && <RequiredMask />}
			</label>

			{children}

			{error && <ErrorFeedback error={error} />}

			{feedbackMessage && (
				<FeedbackMessage feedbackMessage={feedbackMessage} />
			)}
		</ClayForm.Group>
	);
}

interface IErrorFeedbackProps {
	className?: string;
	error: string;
}

interface IFeedbackMessageProps {
	feedbackMessage: string;
}

interface IProps {
	children: ReactNode;
	className?: string;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id?: string;
	label: string;
	required?: boolean;
}
