/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import React from 'react';

import './FieldFeedback.scss';

export default function FieldFeedback({
	errorMessage,
	helpMessage,
	warningMessage,
	...otherProps
}: IProps) {
	if (!errorMessage && !helpMessage && !warningMessage) {
		return null;
	}

	return (
		<ClayForm.FeedbackGroup className="field-feedback" {...otherProps}>
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

interface IProps extends React.HTMLAttributes<HTMLDivElement> {
	errorMessage?: string;
	helpMessage?: string;
	warningMessage?: string;
}
