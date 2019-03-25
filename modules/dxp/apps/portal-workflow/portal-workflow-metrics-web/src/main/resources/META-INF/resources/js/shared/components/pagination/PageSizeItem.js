import {Link, withRouter} from 'react-router-dom';
import autobind from 'autobind-decorator';
import pathToRegexp from 'path-to-regexp';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 */
class PageSizeItem extends React.Component {
	@autobind
	setPageSize() {
		const {onChangePageSize, pageSize} = this.props;

		onChangePageSize(pageSize);
	}

	render() {
		const {location: {search}, match, pageSize} = this.props;
		const params = Object.assign({}, match.params, {page: 1, pageSize});
		const path = pathToRegexp.compile(match.path);

		return (
			<Link
				className="dropdown-item"
				onClick={this.setPageSize}
				to={{
					pathname: path(params),
					search
				}}
			>
				{pageSize}
			</Link>
		);
	}
}

export default withRouter(PageSizeItem);