/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React from 'react';

const LoadingState = ({
	className = 'border-1 pb-6 pt-6 sheet',
	message,
	messageClassName = ''
}) => (
	<div className={className} data-testid="loadingState">
		<span aria-hidden="true" className="loading-animation" />
		{message && (
			<span
				className={`text-center text-truncate ${messageClassName}`}
				data-testid="loadingStateMsg"
			>
				{message}
			</span>
		)}
	</div>
);

export default LoadingState;
