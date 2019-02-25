import {PAGE_CHANGE} from './RouterConstants';
import React from 'react';

/**
 * Router link.
 * @extends React.Component
 */
export default class Link extends React.Component {
	render() {
		const {className, query, text, title, to, type} = this.props;

		const emitEvent = path => () => {
			document.dispatchEvent(
				new CustomEvent(PAGE_CHANGE, {
					bubbles: true,
					detail: {
						path,
						query,
						title
					}
				})
			);
		};

		return (
			<a
				className={className}
				data-senna-off
				href={`#${to}`}
				onClick={emitEvent(to)}
				type={type}
			>
				{text}
			</a>
		);
	}
}