import autobind from 'autobind-decorator';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 * */
export default class PageSizeItem extends React.Component {
	@autobind
	setPageSize() {
		const {onChangePageSize, pageSize} = this.props;

		onChangePageSize(pageSize);
	}

	render() {
		const {pageSize} = this.props;

		return (
			<a
				className="dropdown-item"
				href={`#${pageSize}`}
				onClick={this.setPageSize}
			>
				{pageSize}
			</a>
		);
	}
}