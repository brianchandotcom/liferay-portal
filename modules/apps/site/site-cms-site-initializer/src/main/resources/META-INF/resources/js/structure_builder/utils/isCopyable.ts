/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Structure} from '../types/Structure';
import {Uuid} from '../types/Uuid';
import findChild from './findChild';
import isLocked from './isLocked';
import isReferenced from './isReferenced';

export default function isCopyable({
	root,
	uuid,
}: {
	root: Structure;
	uuid: Uuid;
}): boolean {
	const item = findChild({root, uuid});

	if (!item || item.type === 'related-content') {
		return false;
	}

	if (isLocked({root, uuid})) {
		return false;
	}

	if (isReferenced({root, uuid})) {
		return false;
	}

	return true;
}
