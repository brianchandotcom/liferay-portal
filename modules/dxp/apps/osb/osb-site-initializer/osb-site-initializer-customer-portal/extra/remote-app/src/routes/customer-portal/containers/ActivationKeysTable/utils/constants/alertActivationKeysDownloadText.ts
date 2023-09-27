/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ALERT_DOWNLOAD_TYPE} from '../../../../utils/constants';

export const ALERT_ACTIVATION_KEYS_DOWNLOAD_TEXT = {
	[ALERT_DOWNLOAD_TYPE.danger]: 'Unable to export keys, please try again.',
	[ALERT_DOWNLOAD_TYPE.success]:
		'Activation key details successfully exported.',
};
