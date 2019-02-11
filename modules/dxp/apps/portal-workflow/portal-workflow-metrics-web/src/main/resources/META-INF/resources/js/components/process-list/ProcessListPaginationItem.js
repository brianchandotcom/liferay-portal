import autobind from 'autobind-decorator';
import Icon from '../../shared/components/Icon';
import React from 'react';

export default class ProcessListPaginationItem extends React.Component {
	@autobind
	setPage() {
		const {onChangePage, page} = this.props;
		onChangePage(page);
	}

	render() {
		const {active, page, type} = this.props;
		const classNames = ['page-item'];

		if (active) {
			classNames.push('active');
		}

		const renderLink = () => {
			const isNext = type === 'next';
			const iconType = isNext ? 'angle-right' : 'angle-left';
			const displayType = isNext
				? Liferay.Language.get('next')
				: Liferay.Language.get('previous');

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
			<li className={classNames.join(' ')} onClick={this.setPage}>
				{renderLink()}
			</li>
		);
	}
}