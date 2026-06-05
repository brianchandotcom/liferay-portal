/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Label from '@clayui/label';
import React from 'react';

import {IRoomObjectEntry} from '../../../common/utils/types';

type TDisplayType = 'danger' | 'info' | 'secondary' | 'success' | 'warning';

export const STATUS_DISPLAY_TYPES: {[label: string]: TDisplayType} = {
	'approved': 'success',
	'denied': 'danger',
	'draft': 'secondary',
	'expired': 'danger',
	'in-trash': 'info',
	'inactive': 'secondary',
	'incomplete': 'warning',
	'pending': 'info',
	'scheduled': 'info',
};

const RoomStatusRenderer = ({value}: {value?: IRoomObjectEntry['status']}) => {
	if (!value) {
		return <>--</>;
	}

	return (
		<Label
			displayType={STATUS_DISPLAY_TYPES[value.label] || 'secondary'}
			inverse
		>
			{value.label_i18n || value.label}
		</Label>
	);
};

export default RoomStatusRenderer;
