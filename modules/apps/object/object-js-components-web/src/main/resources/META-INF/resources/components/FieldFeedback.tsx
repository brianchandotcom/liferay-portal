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
import React from 'react';

import './FieldFeedback.scss';

export function FieldFeedback({
	errorMessage,
	helpMessage,
	warningMessage,
	...otherProps
}: IProps) {
	if (!errorMessage && !helpMessage && !warningMessage) {
		return null;
	}

	return (
		<ClayForm.FeedbackGroup
			className="lfr-de__field-feedback"
			{...otherProps}
		>
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
