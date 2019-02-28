import autobind from 'autobind-decorator';
import Icon from '../Icon';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 * */
export default class PageItem extends React.Component {
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
					{page}
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