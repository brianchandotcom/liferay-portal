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
import classNames from 'classnames';
import React from 'react';

import ErrorFeedback from './ErrorFeedback';
import FeedbackMessage from './FeedbackMessage';
import RequiredMask from './RequiredMask';

interface IInputProps extends React.HTMLAttributes<HTMLElement> {
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id: string;
	label: string;
	name: string;
	required?: boolean;
	value: string;
}

const Input: React.FC<IInputProps> = ({
	className,
	disabled = false,
	error,
	feedbackMessage,
	id,
	label,
	name,
	onChange,
	required = false,
	value,
	...otherProps
}) => {
	return (
		<ClayForm.Group
			className={classNames(className, {
				'has-error': error,
			})}
		>
			<label className={classNames({disabled})} htmlFor={id}>
				{label}

				{required && <RequiredMask />}
			</label>

			<ClayInput
				{...otherProps}
				disabled={disabled}
				id={id}
				name={name}
				onChange={onChange}
				type="input"
				value={value}
			/>

			{error && <ErrorFeedback error={error} />}

			{feedbackMessage && (
				<FeedbackMessage feedbackMessage={feedbackMessage} />
			)}
		</ClayForm.Group>
	);
};

export default Input;
