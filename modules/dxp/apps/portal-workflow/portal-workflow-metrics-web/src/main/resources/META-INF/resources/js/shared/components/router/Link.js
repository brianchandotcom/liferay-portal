import React from 'react';

/**
 * Router link.
 * @extends React.Component
 */
export default class Link extends React.Component {
	getUrl() {
		const {query = {}, title, to} = this.props;

		if (title) {
			query['_title'] = title;
		}

		query['_path'] = to;

		const url = Object.keys(query).reduce(
			(old, key) => `${old}&${key}=${query[key]}`,
			''
		);

		return `#${url}`;
	}

	render() {
		const {className, text, type} = this.props;

		return (
			<a className={className} data-senna-off href={this.getUrl()} type={type}>
				{text}
			</a>
		);
	}
}