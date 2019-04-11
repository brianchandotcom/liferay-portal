import EmptyState from './EmptyState';
import LoadingState from '../loading/LoadingState';
import React from 'react';

/**
 * ListView.
 * @extends React.Component
 */
export default class ListView extends React.Component {
	render() {
		const {
			children,
			emptyActionButton,
			emptyMessageClassName,
			emptyMessageText,
			emptyTitleText,
			errorMessageText,
			fetching,
			loading,
			searching
		} = this.props;

		const hideAnimation = emptyMessageClassName || errorMessageText;

		const errorRender = secondaryRender =>
			errorMessageText ? (
				<EmptyState
					actionButton={emptyActionButton}
					hideAnimation={hideAnimation}
					message={errorMessageText}
					messageClassName={emptyMessageClassName}
					type="error"
				/>
			) : (
				secondaryRender
			);

		const emptyFetchRender = secondaryRender =>
			fetching ? (
				<EmptyState
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