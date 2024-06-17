/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayPanel from '@clayui/panel';
import ClayTable from '@clayui/table';
import React, {useState} from 'react';

import {WorkflowStatusLabel} from '../components/WorkflowStatusLabel';

export default function ChangeTrackingWorkflowView({
	openWorkflowAssignModal,
	workflowData,
}) {
	const MAX_ITEMS_TO_SHOW = 10;

	const [next, setNext] = useState(MAX_ITEMS_TO_SHOW);

	return (
		<div>
			<ClayTable
				borderless
				className="publications-render-table table table-autofit table-nowrap"
				hover={false}
				striped
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

							{workflowData.assignButton && (
								<ClayButton
									className="ml-2"
									displayType="secondary"
									onClick={() =>
										openWorkflowAssignModal(
											workflowData.assignButton.href,
											workflowData.assignButton.label,
											workflowData.assignButton
												.modalHeight
										)
									}
									size="xs"
								>
									{workflowData.assignButton.label}
								</ClayButton>
							)}
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
									workflowData.comments.url
								)}
							>
								{workflowData.comments.title}
							</a>
						</ClayTable.Cell>
					</ClayTable.Row>
				</ClayTable.Body>
			</ClayTable>

			<ClayPanel
				borderless="true"
				className="mt-n3"
				collapsable
				displayTitle={
					<ClayPanel.Title>
						<b> {Liferay.Language.get('activities')} </b>
					</ClayPanel.Title>
				}
				displayType="primary"
				showCollapseIcon={true}
			>
				<ClayTable borderless className="mt-n3">
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell headingCell headingTitle>
								{Liferay.Language.get('activity-description')}
							</ClayTable.Cell>

							<ClayTable.Cell headingCell headingTitle>
								{Liferay.Language.get('date')}
							</ClayTable.Cell>
						</ClayTable.Row>
					</ClayTable.Head>

					<ClayTable.Body>
						{Object.keys(workflowData.activities)
							.reverse()
							.slice(0, next)
							?.map((id) => (
								<ClayTable.Row key={id}>
									<ClayTable.Cell className="bg-white">
										{
											workflowData.activities[id]
												.description
										}
										&nbsp;
										{workflowData.activities[id].comment}
									</ClayTable.Cell>

									<ClayTable.Cell className="bg-white">
										{workflowData.activities[id].createDate}
									</ClayTable.Cell>
								</ClayTable.Row>
							))}

						{next <
							Object.keys(workflowData.activities)?.length && (
							<ClayButton
								borderless
								onClick={() =>
									setNext(next + MAX_ITEMS_TO_SHOW)
								}
							>
								{Liferay.Language.get('view-more')}
							</ClayButton>
						)}
					</ClayTable.Body>
				</ClayTable>
			</ClayPanel>
		</div>
	);
}
