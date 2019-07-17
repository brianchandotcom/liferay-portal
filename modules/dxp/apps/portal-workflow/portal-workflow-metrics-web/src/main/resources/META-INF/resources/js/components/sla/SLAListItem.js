import {ChildLink} from '../../shared/components/router/routerWrapper';
import {formatDuration} from '../../shared/util/duration';
import Icon from '../../shared/components/Icon';
import React from 'react';
import SLAListCardContext from './SLAListCardContext';

class SLAListItem extends React.Component {
	showConfirmDialog() {
		const {id} = this.props;

		this.context.showConfirmDialog(id);
	}

	render() {
		const {
			dateModified,
			description,
			duration,
			id,
			name,
			processId,
			status
		} = this.props;

		const blocked = status === 2;
		const durationString = formatDuration(duration);

		const blockedStatusClass = blocked ? 'text-danger' : '';

		const statusText = blocked
			? Liferay.Language.get('blocked')
			: Liferay.Language.get('running');

		let dateModifiedText = '';

		if (dateModified) {
			const date = new Date(dateModified);
			const month = date.toLocaleString(
				Liferay.ThemeDisplay.getBCP47LanguageId(),
				{month: 'short', timeZone: 'UTC'}
			);

			const dayOfMonth = `${date.getUTCDate()}`.padStart(2, '0');

			dateModifiedText = `${month} ${dayOfMonth}`;
		}

		return (
			<tr>
				<td className="table-cell-expand">
					<div className="table-list-title">
						{blocked && (
							<Icon
								elementClasses="text-danger"
								iconName="exclamation-full"
							/>
						)}{' '}
						<ChildLink to={`/sla/edit/${processId}/${id}`}>
							{name}
						</ChildLink>
					</div>
				</td>

				<td>{description}</td>

				<td className={blockedStatusClass}>{statusText}</td>

				<td>{durationString}</td>

				<td>{dateModifiedText}</td>

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
									onClick={this.showConfirmDialog.bind(this)}
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
