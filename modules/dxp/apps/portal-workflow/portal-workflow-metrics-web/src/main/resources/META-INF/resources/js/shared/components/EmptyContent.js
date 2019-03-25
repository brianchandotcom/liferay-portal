import React from 'react';

/**
 * @class
 * @memberof shared/components
 */
export default class EmptyContent extends React.Component {
	render() {
		const { message, title, type = '' } = this.props;
		const classNameType =
			type === 'not-found'
				? 'taglib-empty-search-result-message-header'
				: 'taglib-empty-result-message-header';

		return (
			<div className="sheet taglib-empty-result-message">
				<div className={classNameType} />

				<h3 className="text-center">{title}</h3>

				<div className="sheet-text text-center">
					<p>{message}</p>
				</div>
			</div>
		);
	}
}