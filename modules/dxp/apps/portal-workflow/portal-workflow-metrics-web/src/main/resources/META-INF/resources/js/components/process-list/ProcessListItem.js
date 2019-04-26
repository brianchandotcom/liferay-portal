import { AppContext } from '../AppContext';
import { ChildLink } from '../../shared/components/router/routerWrapper';
import Icon from '../../shared/components/Icon';
import React from 'react';

/**
 * @class
 * @memberof process-list
 */
class ProcessListItem extends React.Component {
	render() {
		const { defaultDelta } = this.context;
		const {
			id,
			instanceCount = '-',
			onTimeInstanceCount = '-',
			overdueInstanceCount = '-',
			title
		} = this.props;

		return (
			<tr>
				<td className="table-cell-expand table-cell-minw-200 table-title lfr-title-column">
					<ChildLink to={`/dashboard/${id}`}>
						<span>{title}</span>
					</ChildLink>
				</td>

				<td>{overdueInstanceCount}</td>

				<td>{onTimeInstanceCount}</td>

				<td>{instanceCount}</td>

				<td>
					<div className="dropdown dropdown-action">
						<a
							aria-expanded="false"
							aria-haspopup="true"
							className="component-action dropdown-toggle"
							data-toggle="dropdown"
							href="#1"
							id="dropdownAction1"
							role="button"
						>
							<Icon iconName="ellipsis-v" />
						</a>
						<ul
							aria-labelledby=""
							className="dropdown-menu dropdown-menu-right"
						>
							<li>
								<ChildLink
									className="dropdown-item"
									to={`/slas/${id}/${defaultDelta}/1`}
								>
									{Liferay.Language.get('set-up-slas')}
								</ChildLink>
							</li>
						</ul>
					</div>
				</td>
			</tr>
		);
	}
}

ProcessListItem.contextType = AppContext;
export default ProcessListItem;