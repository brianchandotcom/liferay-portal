import { Link, withRouter } from 'react-router-dom';
import Icon from '../../shared/components/Icon';
import pathToRegexp from 'path-to-regexp';
import React from 'react';

/**
 * @class
 * @memberof processes-list
 * */
class ProcessListHeadItem extends React.Component {
	render() {
		const {
			location: { search },
			match: { params, path },
			name,
			title
		} = this.props;

		const sort = params && params.sort ? params.sort : `${name}:asc`;

		const [field, order] = decodeURIComponent(sort).split(':');

		const isSorted = field === name;

		const nextSort = `${name}:${isSorted && order === 'desc' ? 'asc' : 'desc'}`;

		const sortIcon = order === 'asc' ? 'order-arrow-down' : 'order-arrow-up';

		const pathname = pathToRegexp.compile(path)(
			Object.assign({}, params, { sort: nextSort })
		);

		return (
			<Link
				className="inline-item text-truncate-inline"
				to={{ pathname, search }}
			>
				<span className="text-truncate" data-title={title} title={title}>
					{title}
				</span>
				{isSorted && (
					<span className="inline-item inline-item-after">
						<Icon iconName={sortIcon} key={`${name}_icon_${sortIcon}`} />
					</span>
				)}
			</Link>
		);
	}
}

export default withRouter(ProcessListHeadItem);