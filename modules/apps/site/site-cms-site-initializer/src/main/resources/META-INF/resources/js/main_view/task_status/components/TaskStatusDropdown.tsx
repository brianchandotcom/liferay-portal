/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Badge from '@clayui/badge';
import Button, {ClayButtonWithIcon} from '@clayui/button';
import DropDown from '@clayui/drop-down';
import classnames from 'classnames';
import React from 'react';

import TaskStatusDropdownItemList from './../components/TaskStatusDropdownItemList';

function TaskStatusDropdown({
	active,
	disable,
	getTaskItems,
	processingTasks,
	setActive,
	taskClassNameId,
	taskItems,
}: any) {
	const handleActiveChange = (newActive: boolean) => {
		setActive(newActive);

		if (newActive) {
			getTaskItems();
		}
	};

	return (
		<>
			<div className="p-2">
				<span className="d-flex"></span>

				{processingTasks > 0 ? (
					<DropDown
						active={active}
						onActiveChange={handleActiveChange}
						trigger={
							<Button
								className={classnames({
									'btn-sm border-info text-info pb-1':
										!active,
									'btn-sm btn-info pb-1': active,
								})}
								displayType="secondary"
							>
								<Badge
									className={classnames({
										'mr-2 badge-info': !active,
										'mr-2 badge-light': active,
									})}
									label={processingTasks}
								/>

								{processingTasks === 1
									? Liferay.Language.get('processing-task')
									: Liferay.Language.get('processing-tasks')}
							</Button>
						}
						triggerIcon={active ? 'caret-top' : 'caret-bottom'}
					>
						<TaskStatusDropdownItemList
							items={taskItems}
							taskClassNameId={taskClassNameId}
						/>
					</DropDown>
				) : (
					<DropDown
						active={active}
						onActiveChange={handleActiveChange}
						trigger={
							<ClayButtonWithIcon
								borderless
								disabled={disable}
								displayType="unstyled"
								symbol="forms"
							></ClayButtonWithIcon>
						}
						triggerIcon={active ? 'caret-top' : 'caret-bottom'}
					>
						<TaskStatusDropdownItemList items={taskItems} />
					</DropDown>
				)}
			</div>
		</>
	);
}

export default TaskStatusDropdown;
