/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import React, {ReactNode} from 'react';

import ErrorFeedback from './ErrorFeedback';
import HelpFeedback from './HelpFeedback';
import RequiredMark from './RequiredMark';

const FieldWrapper = ({
	children,
	disabled,
	errorMessage,
	feedbackId,
	fieldId,
	helpMessage = '',
	label,
	required,
}: {
	children: ReactNode;
	disabled?: boolean;
	errorMessage?: string;
	feedbackId?: string;
	fieldId: string;
	helpMessage?: string;
	label: string;
	required?: boolean;
}) => (
	<ClayForm.Group className={errorMessage ? 'has-error' : ''}>
		<label className={disabled ? 'disabled' : ''} htmlFor={fieldId}>
			{label}

			{required && <RequiredMark />}
		</label>

		{children}

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

export default FieldWrapper;
