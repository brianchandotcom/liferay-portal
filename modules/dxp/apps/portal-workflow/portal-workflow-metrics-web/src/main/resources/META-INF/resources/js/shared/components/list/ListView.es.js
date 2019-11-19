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

import LoadingState from '../loading/LoadingState.es';
import EmptyState from './EmptyState.es';

/**
 * ListView.
 * @extends React.Component
 */
export default class ListView extends React.Component {
	render() {
		const {
			children,
			className = 'border-1',
			emptyActionButton,
			emptyMessageClassName,
			emptyMessageText,
			emptyTitleText,
			errorMessageClassName = 'small',
			errorMessageText,
			fetching,
			hideAnimation = false,
			loading,
			searching
		} = this.props;

		const errorRender = secondaryRender =>
			errorMessageText ? (
				<EmptyState
					actionButton={emptyActionButton}
					className={className}
					hideAnimation
					message={errorMessageText}
					messageClassName={errorMessageClassName}
					type="error"
				/>
			) : (
				secondaryRender
			);

		const emptyFetchRender = secondaryRender =>
			fetching ? (
				<EmptyState
					className={className}
					hideAnimation={hideAnimation}
					message={emptyMessageText}
					messageClassName={emptyMessageClassName}
					title={emptyTitleText}
				/>
			) : (
				secondaryRender
			);

		const emptySearchRender = secondaryRender =>
			searching ? (
				<EmptyState
					className={className}
					hideAnimation={hideAnimation}
					message={emptyMessageText}
					messageClassName={emptyMessageClassName}
					type="not-found"
				/>
			) : (
				secondaryRender
			);

		const loadingRender = secondaryRender =>
			loading ? <LoadingState /> : secondaryRender;

		return (
			<div>
				{loadingRender(
					errorRender(emptySearchRender(emptyFetchRender(children)))
				)}
			</div>
		);
	}
}
