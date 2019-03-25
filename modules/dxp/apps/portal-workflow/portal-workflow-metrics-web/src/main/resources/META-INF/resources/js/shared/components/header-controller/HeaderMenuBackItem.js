import { Link, withRouter } from 'react-router-dom';
import Icon from '../Icon';
import PortalComponent from './PortalComponent';
import queryString from 'query-string';
import React from 'react';

class HeaderMenuBackItem extends React.Component {
	render() {
		const {
			basePath,
			container,
			location: { pathname, search }
		} = this.props;
		const isFirstPage = pathname === basePath || pathname === '/';
		const backPath = queryString.parse(search).backPath;

		return (
			<PortalComponent container={container}>
				{!isFirstPage && backPath && (
					<li className="control-menu-nav-item metric-control-menu-heading">
						<Link
							className="btn btn-link back-url-link control-menu-icon"
							to={backPath}
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