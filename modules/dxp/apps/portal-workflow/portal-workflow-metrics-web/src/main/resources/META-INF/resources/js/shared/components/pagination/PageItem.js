import { Link, withRouter } from 'react-router-dom';
import autobind from 'autobind-decorator';
import Icon from '../Icon';
import pathToRegexp from 'path-to-regexp';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 */
class PageItem extends React.Component {
	@autobind
	setPage() {
		const { disabled, onChangePage, page } = this.props;

		if (!disabled) {
			onChangePage(page);
		}
	}

	render() {
		const {
			disabled,
			highlighted,
			location: { search },
			match,
			page,
			type
		} = this.props;
		const classNames = ['page-item'];
		const params = Object.assign({}, match.params, { page });
		const path = pathToRegexp.compile(match.path);

		if (disabled) {
			classNames.push('disabled');
		}
		if (highlighted) {
			classNames.push('active');
		}

		const renderLink = () => {
			if (type) {
				const isNext = type === 'next';

				const iconType = isNext ? 'angle-right' : 'angle-left';
				const displayType = isNext
					? Liferay.Language.get('next')
					: Liferay.Language.get('previous');

				return (
					<Link
						className="page-link"
						to={{
							pathname: path(params),
							search
						}}
					>
						<Icon iconName={iconType} />

						<span className="sr-only">{displayType}</span>
					</Link>
				);
			}

			return (
				<Link
					className="page-link"
					to={{
						pathname: path(params),
						search
					}}
				>
					{page}
				</Link>
			);
		};

		return (
			<li className={classNames.join(' ')} onClick={this.setPage}>
				{renderLink()}
			</li>
		);
	}
}

export default withRouter(PageItem);