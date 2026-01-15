/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useSelector} from '../contexts/StateContext';
import selectRenamingItemUuid from '../selectors/selectRenamingItemUuid';
import {Uuid} from '../types/Uuid';

export default function useIsBeingRenamed() {
	const renamingItemUuid = useSelector(selectRenamingItemUuid);

	return (uuid: Uuid) => renamingItemUuid === uuid;
}
