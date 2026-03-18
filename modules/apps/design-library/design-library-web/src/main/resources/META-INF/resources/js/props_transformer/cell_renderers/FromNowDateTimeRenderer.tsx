/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {dateUtils} from 'frontend-js-web';
import React from 'react';

export function FromNowDateTimeRenderer({value}: {value: string}) {
	return <time dateTime={value}>{dateUtils.fromNow(new Date(value))}</time>;
}
