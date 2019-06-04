import React from 'react';

/**
 * @class
 * @memberof shared/components/list
 */
export default class EmptyState extends React.Component {
	render() {
		const {
			actionButton,
			className = 'border-1',
			hideAnimation,
			message,
			messageClassName,
			title,
			type = ''
		} = this.props;

		const classNameType =
			type === 'not-found'
				? 'taglib-empty-search-result-message-header'
				: 'taglib-empty-result-message-header';

		return (
			<div
				className={`${className} pb-5 pt-6 sheet taglib-empty-result-message`}
			>
				{!hideAnimation && <div className={classNameType} />}

				{title && <h3 className='text-center'>{title}</h3>}

				<div className='sheet-text text-center'>
					<p className={messageClassName}>{message}</p>

					{actionButton}
				</div>
			</div>
		);
	}
}