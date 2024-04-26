/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import React from 'react';

import {WorkflowStatusLabel} from '../components/WorkflowStatusLabel';

export default function ChangeTrackingWorkflowView({workflowData}) {
	return (
		<ClayTable
			borderless
			className="publications-render-table table table-autofit table-nowrap"
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
						<a href={Liferay.Util.escape(workflowData.usages)}>
							{Liferay.Language.get('view-usages')}
						</a>
					</ClayTable.Cell>
				</ClayTable.Row>

				<ClayTable.Row>
					<ClayTable.Cell className="publications-key-td table-cell-expand-small">
						{Liferay.Language.get('comments')}
					</ClayTable.Cell>

					<ClayTable.Cell className="table-cell-expand">
						{workflowData.comments}
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Body>
		</ClayTable>
	);
}
