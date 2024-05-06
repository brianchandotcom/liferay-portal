/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayPanel from '@clayui/panel';
import ClayTable from '@clayui/table';
import React, {useState} from 'react';

import {WorkflowStatusLabel} from '../components/WorkflowStatusLabel';

export default function ChangeTrackingWorkflowView({workflowData}) {
	const MAX_ITEMS_TO_SHOW = 10;

	const [next, setNext] = useState(MAX_ITEMS_TO_SHOW);

	const workflowActivities = JSON.parse(workflowData.activities);
	const workflowCommentsURL = JSON.parse(workflowData.comments);

	return (
		<div>
			<ClayTable
				borderless
				className="publications-render-table table table-autofit table-nowrap"
				hover={false}
			>
				<ClayTable.Head />

				<ClayTable.Body>
					<ClayTable.Row>
						<ClayTable.Cell className="publications-key-td table-cell-expand-small">
							{Liferay.Language.get('status')}
						</ClayTable.Cell>

						<ClayTable.Cell className="table-cell-expand">
							<WorkflowStatusLabel
								workflowStatus={workflowData.status}
							/>
						</ClayTable.Cell>
					</ClayTable.Row>

					<ClayTable.Row>
						<ClayTable.Cell className="publications-key-td table-cell-expand-small">
							{Liferay.Language.get('assigned-to')}
						</ClayTable.Cell>

						<ClayTable.Cell className="table-cell-expand">
							{workflowData.assignedTo}
						</ClayTable.Cell>
					</ClayTable.Row>

					<ClayTable.Row>
						<ClayTable.Cell className="publications-key-td table-cell-expand-small">
							{Liferay.Language.get('task-name')}
						</ClayTable.Cell>

						<ClayTable.Cell className="table-cell-expand">
							{workflowData.taskName}
						</ClayTable.Cell>
					</ClayTable.Row>

					<ClayTable.Row>
						<ClayTable.Cell className="publications-key-td table-cell-expand-small">
							{Liferay.Language.get('create-date')}
						</ClayTable.Cell>

						<ClayTable.Cell className="table-cell-expand">
							{workflowData.createDate}
						</ClayTable.Cell>
					</ClayTable.Row>

					<ClayTable.Row>
						<ClayTable.Cell className="publications-key-td table-cell-expand-small">
							{Liferay.Language.get('due-date')}
						</ClayTable.Cell>

						<ClayTable.Cell className="table-cell-expand">
							{workflowData.dueDate}
						</ClayTable.Cell>
					</ClayTable.Row>

					<ClayTable.Row>
						<ClayTable.Cell className="publications-key-td table-cell-expand-small">
							{Liferay.Language.get('usages')}
						</ClayTable.Cell>

						<ClayTable.Cell className="table-cell-expand">
							<a href={workflowData.usages}>
								{Liferay.Language.get('view-usages')}
							</a>
						</ClayTable.Cell>
					</ClayTable.Row>

					<ClayTable.Row>
						<ClayTable.Cell className="publications-key-td table-cell-expand-small">
							{Liferay.Language.get('comments')}
						</ClayTable.Cell>

						<ClayTable.Cell className="table-cell-expand">
							<a
								href={Liferay.Util.escape(
									workflowCommentsURL.url
								)}
							>
								{workflowCommentsURL.title}
							</a>
						</ClayTable.Cell>
					</ClayTable.Row>
				</ClayTable.Body>
			</ClayTable>

			<ClayTable borderless className="mt-n3">
				<ClayTable.Row>
					<ClayPanel
						borderless
						className="mb-0"
						collapsable
						displayTitle={
							<ClayPanel.Title>
								<b> {Liferay.Language.get('activities')} </b>
							</ClayPanel.Title>
						}
						displayType="primary"
						showCollapseIcon={true}
					>
						<th className="bg-white">
							{Liferay.Language.get('activity-description')}
						</th>

						<th className="bg-white">
							{Liferay.Language.get('date')}
						</th>

						{Object.keys(workflowActivities)
							.reverse()
							.slice(0, next)
							?.map((id) => (
								<tr key={id}>
									<td className="bg-white">
										{workflowActivities[id].description}
										&nbsp;
										{workflowActivities[id].comment}
									</td>

									<td className="bg-white">
										{workflowActivities[id].createDate}
									</td>
								</tr>
							))}

						{next < Object.keys(workflowActivities)?.length && (
							<ClayButton
								borderless
								onClick={() =>
									setNext(next + MAX_ITEMS_TO_SHOW)
								}
							>
								{Liferay.Language.get('view-more')}
							</ClayButton>
						)}
					</ClayPanel>
				</ClayTable.Row>
			</ClayTable>
		</div>
	);
}
