import {jsonToUrl} from '../../util/url';
import React from 'react';

/**
 * Router link.
 * @extends React.Component
 */
export default class Link extends React.Component {
	render() {
		const {children, className, query = {}, text, title, to} = this.props;

		return (
			<a
				className={className}
				data-senna-off
				href={jsonToUrl({query, title, to})}
			>
				{text || children}
			</a>
		);
	}
}