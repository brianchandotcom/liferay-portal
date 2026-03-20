/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import classNames from 'classnames';
import React, {ReactNode} from 'react';

import ErrorFeedback from './ErrorFeedback';
import RequiredMark from './RequiredMark';

export default function FieldWrapper({
	children,
	className = '',
	disabled,
	errorMessage,
	feedbackId,
	fieldId,
	label,
	required,
}: {
	children: ReactNode;
	className?: string;
	disabled?: boolean;
	errorMessage?: string;
	feedbackId?: string;
	fieldId: string;
	label: string;
	required?: boolean;
}) {
	return (
		<ClayForm.Group
			className={classNames(className, {'has-error': errorMessage})}
		>
			<label
				className={classNames(className, {disabled})}
				htmlFor={fieldId}
			>
				{label}

				{required && <RequiredMark />}
			</label>

			{children}

			{errorMessage && (
				<ClayForm.FeedbackGroup id={feedbackId}>
					<ErrorFeedback message={errorMessage} />
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
	);
}
