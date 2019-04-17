import autobind from 'autobind-decorator';
import { ChildLink } from '../../shared/components/router/routerWrapper';
import { formatDuration } from '../../shared/util/duration';
import Icon from '../../shared/components/Icon';
import React from 'react';
import SLAListCardContext from './SLAListCardContext';

class SLAListItem extends React.Component {
	@autobind
	showConfirmDialog() {
		const { id } = this.props;

		this.context.showConfirmDialog(id);
	}

	render() {
		const { description, duration, id, name, processId } = this.props;
		const durationString = formatDuration(duration);

		return (
			<tr>
				<td className="table-cell-expand">
					<div className="table-list-title">
						<ChildLink to={`/sla/edit/${processId}/${id}`}>{name}</ChildLink>
					</div>
				</td>

				<td>{description}</td>

				<td>{durationString}</td>

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
									to={`/sla/edit/${processId}/${id}`}
								>
									{Liferay.Language.get('edit')}
								</ChildLink>
							</li>

							<li>
								<button
									className="dropdown-item"
									onClick={this.showConfirmDialog}
								>
									{Liferay.Language.get('delete')}
								</button>
							</li>
						</ul>
					</div>
				</td>
			</tr>
		);
	}
}

SLAListItem.contextType = SLAListCardContext;
export default SLAListItem;