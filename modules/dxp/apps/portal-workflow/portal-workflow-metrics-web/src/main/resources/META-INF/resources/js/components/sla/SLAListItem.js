import {ChildLink} from '../../shared/components/router/routerWrapper';
import {formatDuration} from '../../shared/util/duration';
import Icon from '../../shared/components/Icon';
import React from 'react';

export default class SLAListItem extends React.Component {
	render() {
		const {description, duration, id, name, processId} = this.props;
		const durationString = formatDuration(duration);

		return (
			<tr>
				<td>
					<div className="custom-control custom-checkbox">
						<label>
							<input className="custom-control-input" type="checkbox" />
							<span className="custom-control-label" />
						</label>
					</div>
				</td>

				<td className="table-cell-expand">
					<div className="table-list-title">
						<span className="text-truncate-inline">
							<span title={name}>{name}</span>
						</span>
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
								<a>{Liferay.Language.get('delete')}</a>
							</li>
						</ul>
					</div>
				</td>
			</tr>
		);
	}
}