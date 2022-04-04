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

import './FieldBase.scss';

function Feedback({errorMessage, helpMessage, warningMessage}: IFeedbackProps) {
	return (
		<ClayForm.FeedbackGroup className="lfr-object__field-base-feedback">
			{errorMessage && (
				<ClayForm.FeedbackItem>
					<ClayForm.FeedbackIndicator symbol="exclamation-full" />

					{errorMessage}
				</ClayForm.FeedbackItem>
			)}

			{warningMessage && !errorMessage && (
				<ClayForm.FeedbackItem>
					<ClayForm.FeedbackIndicator symbol="warning-full" />

					{warningMessage}
				</ClayForm.FeedbackItem>
			)}

			{helpMessage && <div>{helpMessage}</div>}
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
	errorMessage,
	helpMessage,
	id,
	label,
	required,
	warningMessage,
}: IProps) {
	return (
		<ClayForm.Group
			className={classNames(className, {
				'has-error': errorMessage,
				'has-warning': warningMessage && !errorMessage,
			})}
		>
			<label className={classNames({disabled})} htmlFor={id}>
				{label}

				{required && <RequiredMask />}
			</label>

			{children}

			{(errorMessage || helpMessage || warningMessage) && (
				<Feedback
					errorMessage={errorMessage}
					helpMessage={helpMessage}
					warningMessage={warningMessage}
				/>
			)}
		</ClayForm.Group>
	);
}

interface IFeedbackProps {
	errorMessage?: string;
	helpMessage?: string;
	warningMessage?: string;
}

interface IProps {
	children: ReactNode;
	className?: string;
	disabled?: boolean;
	errorMessage?: string;
	helpMessage?: string;
	id?: string;
	label: string;
	required?: boolean;
	warningMessage?: string;
}
