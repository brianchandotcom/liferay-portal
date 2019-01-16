import React from 'react';

export default class EmptyContent extends React.Component {
	render() {
		const {message} = this.props;
		return (
			<div className="sheet taglib-empty-result-message">
				<div className="taglib-empty-result-message-header" />
				<div className="sheet-text text-center"> {message} </div>
			</div>
		);
	}
}