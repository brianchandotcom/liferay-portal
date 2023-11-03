/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ICreationActionItem} from '../../management_bar/components/CreationMenu';
declare const filterCreationActions: ({
	customActions,
	globalCollectionActions,
}: {
	customActions: Array<ICreationActionItem>;
	globalCollectionActions: any;
}) => Array<ICreationActionItem>;
export default filterCreationActions;
