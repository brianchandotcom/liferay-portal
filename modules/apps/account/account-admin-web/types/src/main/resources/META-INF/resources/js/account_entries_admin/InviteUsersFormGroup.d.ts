/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {InputGroup, MultiSelectItem} from './types';
declare type OnItemsChangeFn = (items: MultiSelectItem[]) => void;
interface IProps extends InputGroup {
	availableAccountRoles: MultiSelectItem[];
	index: number;
	onAccountRoleItemsChange: OnItemsChangeFn;
	onEmailAddressItemsChange: OnItemsChangeFn;
	onRemove: Function;
	portletNamespace: string;
}
declare const InviteUserFormGroup: ({
	accountRoles,
	availableAccountRoles,
	emailAddresses,
	id,
	index,
	onAccountRoleItemsChange,
	onEmailAddressItemsChange,
	onRemove,
	portletNamespace,
}: IProps) => JSX.Element;
export default InviteUserFormGroup;
