/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {MultiSelectItem} from './types';
interface IProps {
	accountEntryId: number;
	availableAccountRoles: MultiSelectItem[];
	inviteAccountUsersURL: string;
	portletNamespace: string;
	redirectURL: string;
}
declare function InviteUsersForm({
	accountEntryId,
	availableAccountRoles,
	inviteAccountUsersURL,
	portletNamespace,
	redirectURL,
}: IProps): JSX.Element;
export default InviteUsersForm;
