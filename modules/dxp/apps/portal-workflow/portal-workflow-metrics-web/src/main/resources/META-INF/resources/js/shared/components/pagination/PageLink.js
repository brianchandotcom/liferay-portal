import {Link, withRouter} from 'react-router-dom';
import pathToRegexp from 'path-to-regexp';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 */
class PageLink extends React.Component {
	render() {
		const {
			location: {search},
			match: {params, path},
			page
		} = this.props;

		const pathname = pathToRegexp.compile(path)(
			Object.assign({}, params, {page})
		);

		return (
			<li className="page-item">
				<Link className="page-link" to={{pathname, search}}>
					<span className="sr-only" />
					{page}
				</Link>
			</li>
		);
	}
}

export default withRouter(PageLink);
