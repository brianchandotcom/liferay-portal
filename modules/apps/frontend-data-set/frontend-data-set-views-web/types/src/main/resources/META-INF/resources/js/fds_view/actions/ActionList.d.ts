/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IFDSAction} from '../Actions';
interface IFDSActionListProps {
	createFDSAction: () => void;
	deleteFDSAction: ({item}: {item: IFDSAction}) => void;
	editFDSAction: ({item}: {item: IFDSAction}) => void;
	fdsActions: Array<IFDSAction>;
	noItemsButtonLabel: string;
	updateFDSActionsOrder: ({order}: {order: string}) => void;
}
declare const ActionList: ({
	createFDSAction,
	deleteFDSAction,
	editFDSAction,
	fdsActions,
	noItemsButtonLabel,
	updateFDSActionsOrder,
}: IFDSActionListProps) => JSX.Element;
export default ActionList;
