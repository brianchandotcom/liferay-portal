/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData} from '../../types/LayoutData';
import {UPDATE_RULE} from './types';

type Props = {
	layoutData: LayoutData;
};

export default function updateRule({layoutData}: Props) {
	return {
		layoutData,
		type: UPDATE_RULE,
	} as const;
}
