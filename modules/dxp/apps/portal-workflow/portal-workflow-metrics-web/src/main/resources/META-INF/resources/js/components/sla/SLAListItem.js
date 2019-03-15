import {formatDuration} from '../../shared/util/duration';
import Icon from '../../shared/components/Icon';
import Link from '../../shared/components/router/Link';
import React from 'react';

export default class SLAListItem extends React.Component {
	render() {
		const {description, duration, name, processId} = this.props;
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
								<Link
									className="dropdown-item"
									query={{processId}}
									text={Liferay.Language.get('edit')}
									to="sla-form"
								/>
							</li>

							<li>
								<Link
									className="dropdown-item"
									query={{itemToRemove: 'test'}}
									text={Liferay.Language.get('delete')}
									to="sla-list"
								/>
							</li>
						</ul>
					</div>
				</td>
			</tr>
		);
	}
}