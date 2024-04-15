/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import React from 'react';

import './FieldFeedback.scss';

export function FieldFeedback({
	errorMessage,
	helpMessage,
	name,
	warningMessage,
	...otherProps
}: IProps) {
	const showWarning = warningMessage && !errorMessage;

	const message = showWarning ? warningMessage : errorMessage;
	const symbol = showWarning ? 'warning-full' : 'exclamation-full';

	return (
		<ClayForm.FeedbackGroup
			className="lfr-de__field-feedback"
			{...otherProps}
		>
			<ClayForm.FeedbackItem
				aria-live="assertive"
				id={`${name}_fieldError`}
			>
				{message && <ClayForm.FeedbackIndicator symbol={symbol} />}

				{message}
			</ClayForm.FeedbackItem>

			{helpMessage && <div id={`${name}_fieldHelp`}>{helpMessage}</div>}
		</ClayForm.FeedbackGroup>
	);
}

interface IProps extends React.HTMLAttributes<HTMLDivElement> {
	errorMessage?: string;
	helpMessage?: string;
	name?: string;
	warningMessage?: string;
}
