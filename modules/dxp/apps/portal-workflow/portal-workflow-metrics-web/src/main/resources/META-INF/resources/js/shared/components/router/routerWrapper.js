import { Link, Redirect, withRouter } from 'react-router-dom';
import { parse, stringify } from './queryString';
import React from 'react';

class BackLinkWrapper extends React.Component {
	render() {
		const { backPath, children, className } = this.props;

		return <Link children={children} className={className} to={backPath} />;
	}
}

class BackRedirectWrapper extends React.Component {
	render() {
		const { backPath } = this.props;

		return <Redirect to={backPath} />;
	}
}

class ChildLinkWrapper extends React.Component {
	render() {
		const { children, className, currentPath, to } = this.props;

		return (
			<Link
				children={children}
				className={className}
				to={{
					pathname: to,
					search: stringify({ backPath: currentPath })
				}}
			/>
		);
	}
}

const withParams = Component =>
	withRouter(
		({ location: { pathname, search }, match: { params }, ...props }) => (
			<Component
				{...params}
				{...parse(search)}
				{...props}
				currentPath={pathname + search}
			/>
		)
	);

const BackLink = withParams(BackLinkWrapper);
const BackRedirect = withParams(BackRedirectWrapper);
const ChildLink = withParams(ChildLinkWrapper);

export { BackLink, BackRedirect, ChildLink };