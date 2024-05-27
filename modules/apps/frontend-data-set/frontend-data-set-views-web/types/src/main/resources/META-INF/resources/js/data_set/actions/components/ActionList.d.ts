/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IAction} from '../Actions';
declare const ActionList: ({
	actions,
	createAction,
	creationMenuItemLabel,
	deleteAction,
	editAction,
	noItemsButtonLabel,
	updateActionsOrder,
}: {
	actions: Array<IAction>;
	createAction: () => void;
	creationMenuItemLabel: string;
	deleteAction: ({item}: {item: IAction}) => void;
	editAction: ({item}: {item: IAction}) => void;
	noItemsButtonLabel: string;
	updateActionsOrder: ({order}: {order: string}) => void;
}) => JSX.Element;
export default ActionList;
