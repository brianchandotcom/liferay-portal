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

import ClayEmptyState from '@clayui/empty-state';
import classNames from 'classnames';
import React from 'react';

const EmptyState = ({
	actionButton,
	className = 'pb-5 pt-6 sheet text-center',
	filtered,
	filteredMessage = Liferay.Language.get('no-results-were-found'),
	hideAnimation = false,
	message = Liferay.Language.get('there-is-no-data-at-the-moment'),
	messageClassName,
	title = null,
}) => {
	return (
		<div className={className}>
			<ClayEmptyState
				className={classNames({'text-center': hideAnimation})}
				description={filtered ? filteredMessage : message}
				imgSrc={
					!hideAnimation &&
					(filtered
						? `${themeDisplay.getPathThemeImages()}/states/search_state.gif`
						: `${themeDisplay.getPathThemeImages()}/states/empty_state.gif`)
				}
				small={messageClassName === 'small'}
				title={title}
			>
				{actionButton}
			</ClayEmptyState>
		</div>
	);
};

export default EmptyState;
