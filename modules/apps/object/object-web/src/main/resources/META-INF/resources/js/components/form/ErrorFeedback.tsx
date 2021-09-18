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
import classNames from 'classnames';
import React from 'react';

interface IErrorFeedbackProps extends React.HTMLAttributes<HTMLElement> {
	error: string;
}

const ErrorFeedback: React.FC<IErrorFeedbackProps> = ({className, error}) => (
	<ClayForm.FeedbackGroup className={classNames(className)}>
		<ClayForm.FeedbackItem>
			<span>{error}</span>
		</ClayForm.FeedbackItem>
	</ClayForm.FeedbackGroup>
);

export default ErrorFeedback;
