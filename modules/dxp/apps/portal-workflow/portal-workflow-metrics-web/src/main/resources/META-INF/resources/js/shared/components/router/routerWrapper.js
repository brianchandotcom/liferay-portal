import { Link, Redirect, withRouter } from 'react-router-dom';
import queryString from 'query-string';
import React from 'react';

class BackLinkWrapper extends React.Component {
	render() {
		return (
			<Link
				children={this.props.children}
				className={this.props.className}
				to={getBackPathFromProps(this.props)}
			/>
		);
	}
}

class BackRedirectWrapper extends React.Component {
	render() {
		return <Redirect to={getBackPathFromProps(this.props)} />;
	}
}

class ChildLinkWrapper extends React.Component {
	render() {
		const backPath = this.props.location.pathname + this.props.location.search;

		return (
			<Link
				children={this.props.children}
				className={this.props.className}
				to={{
					pathname: this.props.to,
					search: queryString.stringify({ backPath })
				}}
			/>
		);
	}
}

function getBackPathFromProps(props) {
	const {
		location: { search }
	} = props;

	return queryString.parse(search).backPath;
}

const BackLink = withRouter(BackLinkWrapper);
const BackRedirect = withRouter(BackRedirectWrapper);
const ChildLink = withRouter(ChildLinkWrapper);

export { BackLink, BackRedirect, ChildLink };