/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {Col} from '@clayui/layout';
import React, {useContext} from 'react';

import {openCMPModal} from '../../../../../utils/openCMPModal';
import {IColumn} from '../../../../../utils/types';
import StateLabel from '../../../../StateLabel';
import CreateTaskModal from '../../../../modal/CreateTaskModal';
import {KanbanViewContext} from '../context';
import Task from './Task';

import './Column.scss';

interface IColumnProps {
	column: IColumn;
}

export default function Column({
	column: {icon, key, name, tasks},
}: IColumnProps) {
	const {dataSetId} = useContext(KanbanViewContext);

	return (
		<Col className="lfr__kaban-view-column">
			<div className="lfr__kaban-view-column-header">
				<StateLabel state={{key, name}} />

				{icon.name && (
					<ClayIcon style={{color: icon.color}} symbol={icon.name} />
				)}

				<span>{tasks.length}</span>
			</div>

			<div className="lfr__kaban-view-column-task">
				<div className="lfr__kaban-view-column-task-list">
					{tasks.map((task) => {
						return <Task key={task.embedded.id} {...task} />;
					})}
				</div>

				<ClayButton
					borderless
					className="lfr__kaban-view-column-task-add-button"
					onClick={async () => {
						await openCMPModal({
							center: true,
							contentComponent: ({
								closeModal,
							}: {
								closeModal: () => void;
							}) => (
								<CreateTaskModal
									closeModal={closeModal}
									dataSetId={dataSetId}
									state={key}
								/>
							),
							size: 'md',
						});
					}}
				>
					<ClayIcon symbol="plus" />

					{Liferay.Language.get('add-task')}
				</ClayButton>
			</div>
		</Col>
	);
}
