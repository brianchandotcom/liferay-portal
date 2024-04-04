/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IItemsActions} from '../index';
declare const handleActionClick: ({
	action,
	closeMenu,
	event,
	executeAsyncItemAction,
	highlightItems,
	itemData,
	itemId,
	loadData,
	onActionDropdownItemClick,
	openModal,
	openSidePanel,
	setLoading,
	toggleItemInlineEdit,
}: {
	action: IItemsActions;
	closeMenu?: any;
	event: Event;
	executeAsyncItemAction: Function;
	highlightItems: Function;
	itemData: any;
	itemId: string | number;
	loadData: Function;
	onActionDropdownItemClick: Function;
	openModal: Function;
	openSidePanel: Function;
	setLoading?: Function | undefined;
	toggleItemInlineEdit: Function;
}) => void;
export default handleActionClick;
