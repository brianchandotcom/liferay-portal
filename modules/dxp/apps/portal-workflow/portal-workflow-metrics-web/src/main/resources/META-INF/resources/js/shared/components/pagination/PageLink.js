import autobind from 'autobind-decorator';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 */
export default class PageLink extends React.Component {
	@autobind
	setPage() {
		const {disabled, onChangePage, page} = this.props;

		if (!disabled) {
			onChangePage(page);
		}
	}

	render() {
		const {page} = this.props;

		return (
			<li className="page-item" onClick={this.setPage}>
				<a className="page-link" href={`#${page}`}>
					<span className="sr-only" />
					{page}
				</a>
			</li>
		);
	}
}