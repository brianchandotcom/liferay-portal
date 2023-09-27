/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../../../common/I18n';
import {ALERT_DOWNLOAD_TYPE} from '../../../../utils/constants/alertDownloadType';

export const ALERT_UPDATE_DXP_CLOUD_STATUS = {
	[ALERT_DOWNLOAD_TYPE.success]: i18n.translate(
		'your-lxc-sm-environments-are-ready'
	),
};
