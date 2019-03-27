import { Link, withRouter } from 'react-router-dom';
import Icon from '../Icon';
import { parse } from '../router/queryString';
import PortalComponent from './PortalComponent';
import React from 'react';

class HeaderMenuBackItem extends React.Component {
	render() {
		const {
			basePath,
			container,
			location: { pathname, search }
		} = this.props;

		const isFirstPage = pathname === basePath || pathname === '/';
		const query = parse(search);

		return (
			<PortalComponent container={container}>
				{!isFirstPage && query.backPath && (
					<li className="control-menu-nav-item metric-control-menu-heading">
						<Link
							className="btn btn-link back-url-link control-menu-icon"
							to={query.backPath}
						>
							<span className="icon-monospaced">
								<Icon iconName="angle-left" />
							</span>
						</Link>
					</li>
				)}
			</PortalComponent>
		);
	}
}

export default withRouter(HeaderMenuBackItem);