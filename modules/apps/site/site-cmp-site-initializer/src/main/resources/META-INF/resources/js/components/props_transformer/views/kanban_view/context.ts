/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {IColumn, IItemsActions, IKanbanState} from '../../../../utils/types';

interface IKanbanContext {
	boardData: {[k: string]: IColumn};
	changeTaskStatus: React.Dispatch<React.SetStateAction<IKanbanState>>;
	dataSetId: string;
	itemsActions: IItemsActions[];
}

export const KanbanViewContext = React.createContext({} as IKanbanContext);
