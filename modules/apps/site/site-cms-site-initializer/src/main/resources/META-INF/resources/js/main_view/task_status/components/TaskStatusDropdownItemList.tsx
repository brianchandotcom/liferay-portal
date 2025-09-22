/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import DropDown from '@clayui/drop-down';
import Label from '@clayui/label';
import ClayPanel from '@clayui/panel';
import {sub} from 'frontend-js-web';
import React from 'react';

import {
	ActionId,
	IBulkActionTaskItem,
	STATUS_PROPERTIES,
} from '../TaskStatusType';

import '../../../../css/components/AssetTaskStatus.scss';

import moment from 'moment';

import {TASKS_REPORT_URL, TASK_ITEMS_REPORT_URL} from '../util';
import handleMessageAndName from '../util/HandleMessageAndName';

function TaskStatusDropdownItemList({items, taskClassNameId}: any) {
	return (
		<>
			<DropDown.ItemList className="task-status" items={items}>
				{(rawTask: unknown) => {
					const {...task} = rawTask as IBulkActionTaskItem;

					const {name} = handleMessageAndName(
						task.actionName as ActionId
					);

					const properties = STATUS_PROPERTIES[task.executionStatus];

					return (
						<ClayPanel
							collapsable
							displayTitle={
								<ClayPanel.Title className="d-flex task-status-item">
									<div className="task-status-item-icon">
										<properties.component
											className={properties.iconClassName}
											displayType={
												properties?.displayType
											}
											size="sm"
											symbol={properties?.icon}
										/>
									</div>

									<div className="task-status-item-text">
										<p className="h5 m-0 mr-1">
											{Liferay.Language.get(name)}
										</p>

										<span className="d-flex">
											<p className="m-0 small text-secondary text-uppercase">
												{sub(
													Liferay.Language.get(
														'x-items'
													),
													[task.numberOfItems]
												)}
											</p>

											<span className="ml-1 mr-1 small">
												-
											</span>

											<p className="m-0 small text-secondary text-uppercase">
												{moment(
													task.dateModified
												).fromNow(false)}
											</p>
										</span>

										<p className="mb-2">
											<Label
												displayType={
													properties.labelDisplayType
												}
											>
												{Liferay.Language.get(
													properties.label
												)}
											</Label>
										</p>
									</div>
								</ClayPanel.Title>
							}
							key={task.id}
							showCollapseIcon={true}
						>
							<ClayPanel.Body className="d-flex">
								<a
									href={`${TASK_ITEMS_REPORT_URL}${taskClassNameId}/${task.id}`}
								>
									<Button
										className={
											properties.viewButtonClassName
										}
										displayType="secondary"
									>
										{Liferay.Language.get('view')}
									</Button>
								</a>
							</ClayPanel.Body>
						</ClayPanel>
					);
				}}
			</DropDown.ItemList>
			<a
				className="border-top btn btn-link text-secondary w-100"
				href={TASKS_REPORT_URL}
			>
				{Liferay.Language.get('view-all-tasks')}
			</a>
		</>
	);
}

export default TaskStatusDropdownItemList;
