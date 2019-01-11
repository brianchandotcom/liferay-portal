import autobind from 'autobind-decorator';
import Icon from '../../libs/Icon';
import React from 'react';

export default class ProcessListPaginationItem extends React.Component {
	@autobind
	setPage() {
		const {page, onChangePage} = this.props;
		onChangePage(page);
	}

	render() {
		const {page, type} = this.props;

		const renderLink = () => {
			const isNext = type === 'next';
			const iconType = isNext ? 'angle-right' : 'angle-left';
			const displayType = isNext ? 'Next' : 'Previous';

			if (type) {
				return (
					<a className="page-link" href={`#${page}`} role="button">
						<Icon iconName={iconType} />

						<span className="sr-only">{displayType}</span>
					</a>
				);
			}
			return (
				<a className="page-link" href={`#${page}`}>
					{page + 1}
				</a>
			);
		};

		return (
			<li className="page-item" onClick={this.setPage}>
				{renderLink()}
			</li>
		);
	}
}