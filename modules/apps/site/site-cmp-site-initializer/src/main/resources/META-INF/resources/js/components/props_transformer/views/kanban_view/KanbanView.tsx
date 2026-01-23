/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useState} from 'react';

import {
	KANBAN_COLUMN_ORDER,
	mapStateKeyToDisplayType,
	mapStateKeyToIcon,
	mapStateKeyToLabel,
} from '../../../../utils/constants';
import {IColumn, IItemsActions, ITask} from '../../../../utils/types';
import Board from './components/Board';
import {KanbanViewContext} from './context';

interface KanbanViewProps {
	dataSetId: string;
	items: ITask[];
	itemsActions: IItemsActions[];
}

function mapByStateCode(items: ITask[]): {[key: string]: IColumn} {
	const boardData: {[name: string]: IColumn} = {};

	KANBAN_COLUMN_ORDER.forEach((stateKey) => {
		boardData[stateKey] = {
			displayType: mapStateKeyToDisplayType[stateKey],
			icon: mapStateKeyToIcon[stateKey],
			key: stateKey,
			name: mapStateKeyToLabel[stateKey],
			tasks: [],
		};
	});

	items.forEach((item: ITask) => {
		const {
			state: {key},
		} = item.embedded;

		if (boardData[key]) {
			boardData[key].tasks.push(item);
		}
	});

	return boardData;
}

function KanbanView(props: KanbanViewProps) {
	const [boardData] = useState(mapByStateCode(props.items));

	const changeTaskStatus = useCallback(() => {}, []);

	return (
		<KanbanViewContext.Provider
			value={{
				boardData,
				changeTaskStatus,
				dataSetId: props.dataSetId,
				itemsActions: props.itemsActions,
			}}
		>
			<Board />
		</KanbanViewContext.Provider>
	);
}

export default KanbanView;
