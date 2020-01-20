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

const EmptyState = ({
	actionButton,
	className = 'border-1',
	hideAnimation,
	message,
	messageClassName,
	title,
	type = ''
}) => {
	const classNameType =
		type === 'not-found'
			? 'taglib-empty-search-result-message-header'
			: 'taglib-empty-result-message-header';

	return (
		<div
			className={`${className} pb-5 pt-6 sheet taglib-empty-result-message`}
			data-testid="emptyState"
		>
			{!hideAnimation && (
				<div
					className={classNameType}
					data-testid="emptyStateAnimation"
				/>
			)}

			{title && (
				<h3 className="text-center" data-testid="emptyStateTitle">
					{title}
				</h3>
			)}

			<div className="sheet-text text-center">
				<p className={messageClassName} data-testid="emptyStateMsg">
					{message}
				</p>

				{actionButton}
			</div>
		</div>
	);
};

export default EmptyState;
