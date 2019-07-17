import {Link, withRouter} from 'react-router-dom';
import pathToRegexp from 'path-to-regexp';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 */
class PageSizeItem extends React.Component {
	render() {
		const {
			location: {search},
			match,
			pageSize
		} = this.props;

		const params = Object.assign({}, match.params, {page: 1, pageSize});

		const pathname = pathToRegexp.compile(match.path)(params);

		return (
			<Link
				className="dropdown-item"
				to={{
					pathname,
					search
				}}
			>
				{pageSize}
			</Link>
		);
	}
}

export default withRouter(PageSizeItem);
