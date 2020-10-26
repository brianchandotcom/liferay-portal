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
import React from 'react';

const ErrorFeedback = ({error}) => {
	return (
		<ClayForm.FeedbackGroup>
			<ClayForm.FeedbackItem>
				<span>{error}</span>
			</ClayForm.FeedbackItem>
		</ClayForm.FeedbackGroup>
	);
};

const HelpMessage = ({message}) => {
	return (
		<span
			className="inline-item-after lfr-portal-tooltip tooltip-icon"
			data-title={message}
		>
			<ClayIcon symbol="question-circle-full" />
		</span>
	);
};

const RequiredMark = () => {
	return (
		<>
			<span className="inline-item-after reference-mark text-warning">
				<ClayIcon symbol="asterisk" />
			</span>
			<span className="hide-accessible">
				{Liferay.Language.get('required')}
			</span>
		</>
	);
};

export {ErrorFeedback, HelpMessage, RequiredMark};
