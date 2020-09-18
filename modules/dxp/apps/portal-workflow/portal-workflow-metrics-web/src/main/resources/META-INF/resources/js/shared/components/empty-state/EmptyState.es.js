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
	className = 'pb-5 pt-6 sheet',
	filtered,
	filteredMessage = Liferay.Language.get('no-results-were-found'),
	hideAnimation,
	message = Liferay.Language.get('there-is-no-data-at-the-moment'),
	messageClassName,
	title,
}) => {
	const animationClassName = `taglib-empty-${
		filtered ? 'search-' : ''
	}result-message-header`;

	return (
		<div className={`${className} taglib-empty-result-message`}>
			{!hideAnimation && <div className={animationClassName} />}

			{title && <h3 className="text-center">{title}</h3>}

			<div className="sheet-text text-center">
				<p className={messageClassName}>
					{filtered ? filteredMessage : message}
				</p>

				{actionButton}
			</div>
		</div>
	);
};

export default EmptyState;
